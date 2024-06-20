package com.rubin.rpan.services.modules.file.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.rubin.rpan.services.common.constant.CommonConstant;
import com.rubin.rpan.services.common.exception.RPanException;
import com.rubin.rpan.services.modules.file.dao.RPanFileMapper;
import com.rubin.rpan.services.modules.file.entity.RPanFile;
import com.rubin.rpan.services.modules.file.entity.RPanFileChunk;
import com.rubin.rpan.services.modules.file.service.IFileChunkService;
import com.rubin.rpan.services.modules.file.service.IFileService;
import com.rubin.rpan.services.modules.log.service.IErrorLogService;
import com.rubin.rpan.storage.StorageManager;
import com.rubin.rpan.util.FileUtil;
import com.rubin.rpan.util.IdGenerator;
import com.rubin.rpan.util.StringListUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 物理文件业务处理类
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@Service(value = "fileService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class FileServiceImpl implements IFileService {

    @Autowired
    @Qualifier(value = "rPanFileMapper")
    private RPanFileMapper rPanFileMapper;

    @Autowired
    @Qualifier(value = "storageManager")
    private StorageManager storageManager;

    @Autowired
    @Qualifier(value = "errorLogService")
    private IErrorLogService iErrorLogService;

    @Autowired
    @Qualifier(value = "fileChunkService")
    private IFileChunkService iFileChunkService;

    /**
     * 保存物理文件
     *
     * @param file
     * @param userId
     * @param identifier
     * @param totalSize
     * @return
     */
    @Override
    public RPanFile save(MultipartFile file, Long userId, String identifier, Long totalSize, String suffix) {
        RPanFile rPanFile = assembleRPanFile(uploadFile(file, suffix), userId, identifier, totalSize, suffix);
        try {
            saveFileInfo(rPanFile);
        } catch (Exception e) {
            if (rPanFile != null) {
                try {
                    storageManager.delete(null, rPanFile.getRealPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // TODO 集成消息通知 统一自动放入异常日志表中
                    iErrorLogService.save("文件物理删除失败，请手动删除！文件路径为：" + rPanFile.getRealPath(), userId);
                }
            }
            throw new RPanException("上传失败");
        }
        return rPanFile;
    }

    /**
     * 保存分片文件
     *
     * @param file
     * @param userId
     * @param identifier
     * @param totalChunks
     * @param chunkNumber
     * @param totalSize
     * @param filename
     * @return 是否全部上传完成
     */
    @Override
    public synchronized boolean saveWithChunk(MultipartFile file, Long userId, String identifier, Integer totalChunks, Integer chunkNumber, Long totalSize, String filename) {
        String filePath = uploadFileWithChunk(file, identifier, totalChunks, chunkNumber, totalSize, FileUtil.getFileSuffix(filename), userId);
        iFileChunkService.save(userId, identifier, chunkNumber, filePath);
        Integer uploadedChunkCount = iFileChunkService.getUploadedChunkCount(identifier, userId);
        return uploadedChunkCount.intValue() == totalChunks.intValue();
    }

    /**
     * 删除物理文件
     *
     * @param fileIds
     * @return
     */
    @Override
    public void delete(String fileIds) {
        if (StringUtils.isBlank(fileIds)) {
            throw new RPanException("文件id不能为空");
        }
        // TODO 集成MQ 优化成异步消息操作 加上重试机制
        deletePhysicalFiles(fileIds);
        deleteFileInfos(fileIds);
    }

    /**
     * 获取实体文件详情
     *
     * @param realFileId
     * @return
     */
    @Override
    public RPanFile getFileDetail(Long realFileId) {
        RPanFile rPanFile = rPanFileMapper.selectByPrimaryKey(realFileId);
        if (Objects.isNull(rPanFile)) {
            throw new RPanException("实体文件不存在");
        }
        return rPanFile;
    }

    /**
     * 根据文件的唯一标识获取文件列表
     *
     * @param identifier
     * @return
     */
    @Override
    public List<RPanFile> selectByIdentifier(String identifier) {
        return rPanFileMapper.selectByIdentifier(identifier);
    }

    /**
     * 获取已上传的分片号集合
     *
     * @param identifier
     * @param userId
     * @return
     */
    @Override
    public List<Integer> getUploadedChunkNumbers(String identifier, Long userId) {
        List<RPanFileChunk> rPanFileChunks = iFileChunkService.getUploadedChunks(identifier, userId);
        List<Integer> uploadedChunkNumbers = Lists.newArrayListWithCapacity(64);
        if (CollectionUtils.isNotEmpty(rPanFileChunks)) {
            rPanFileChunks.stream().forEach(rPanFileChunk -> uploadedChunkNumbers.add(rPanFileChunk.getChunkNumber()));
        }
        return uploadedChunkNumbers;
    }

    /**
     * 合并文件分片
     *
     * @param identifier
     * @param totalSize
     * @param userId
     * @return
     */
    @Override
    public RPanFile mergeChunks(String identifier, Long totalSize, Long userId, String filename) {
        RPanFile rPanFile = assembleRPanFile(doMergeChunks(identifier, userId), userId, identifier, totalSize, filename);
        try {
            saveFileInfo(rPanFile);
        } catch (Exception e) {
            if (rPanFile != null) {
                try {
                    storageManager.delete(null, rPanFile.getRealPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // TODO 集成消息通知 统一自动放入异常日志表中
                    iErrorLogService.save("文件物理删除失败，请手动删除！文件路径为：" + rPanFile.getRealPath(), userId);
                }
            }
            throw new RPanException("合并文件失败");
        }
        return rPanFile;
    }

    /**
     * 批量查询记录
     *
     * @param scrollPointer
     * @param batchNum
     * @return
     */
    @Override
    public List<RPanFile> getRPanFilePage(Long scrollPointer, Long batchNum) {
        return rPanFileMapper.selectRPanFileList((scrollPointer - 1) * batchNum, batchNum);
    }

    /**
     * 灵活更新表信息
     *
     * @param rPanFile
     */
    @Override
    public void updateSelective(RPanFile rPanFile) {
        if (Objects.isNull(rPanFile)) {
            throw new RPanException("文件实体不能为空");
        }
        if (rPanFileMapper.updateByPrimaryKeySelective(rPanFile) != CommonConstant.ONE_INT) {
            throw new RPanException("文件信息更新失败");
        }
    }

    /************************************************************************私有************************************************************************/

    /**
     * 保存物理文件信息
     *
     * @param rPanFile
     */
    private void saveFileInfo(RPanFile rPanFile) {
        if (rPanFileMapper.insertSelective(rPanFile) != CommonConstant.ONE_INT) {
            throw new RPanException("文件信息保存失败");
        }
    }

    /**
     * 上传物理文件
     *
     * @param file
     * @param suffix
     */
    private String uploadFile(MultipartFile file, String suffix) {
        try {
            return storageManager.store(file.getInputStream(), file.getSize(), suffix);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RPanException("上传失败");
        }
    }

    /**
     * 拼装物理文件信息
     *
     * @param filePath
     * @param userId
     * @param identifier
     * @param totalSize
     * @param suffix
     * @return
     */
    private RPanFile assembleRPanFile(String filePath, Long userId, String identifier, Long totalSize, String suffix) {
        RPanFile rPanFile = new RPanFile();
        String newFileName = FileUtil.getFilename(filePath);
        rPanFile.setFileId(IdGenerator.nextId());
        rPanFile.setFilename(newFileName);
        rPanFile.setRealPath(filePath);
        rPanFile.setFileSize(String.valueOf(totalSize));
        rPanFile.setFileSizeDesc(FileUtil.getFileSizeDesc(totalSize));
        rPanFile.setFileSuffix(suffix);
        rPanFile.setFilePreviewContentType(FileUtil.getContentType(filePath));
        rPanFile.setIdentifier(identifier);
        rPanFile.setCreateUser(userId);
        rPanFile.setCreateTime(new Date());
        return rPanFile;
    }

    /**
     * 删除物理文件信息
     *
     * @param fileIds
     * @return
     */
    private void deleteFileInfos(String fileIds) {
        List<String> fileIdList = Splitter.on(CommonConstant.COMMON_SEPARATOR).splitToList(fileIds);
        if (rPanFileMapper.deleteBatch(fileIdList) != fileIdList.size()) {
            throw new RPanException("删除物理文件信息失败");
        }
    }

    /**
     * 批量删除物理文件
     *
     * @param fileIds
     * @return
     */
    private void deletePhysicalFiles(String fileIds) {
        List<RPanFile> rPanFileList = rPanFileMapper.selectByFileIdList(StringListUtil.string2LongList(fileIds));
        rPanFileList.stream().forEach(rPanFile -> {
            try {
                storageManager.delete(null, rPanFile.getRealPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 上传分片文件
     *
     * @param file
     * @param identifier
     * @param totalChunks
     * @param chunkNumber
     * @param totalSize
     * @param suffix
     * @param userId
     * @return 文件存储路径
     */
    private String uploadFileWithChunk(MultipartFile file, String identifier, Integer totalChunks, Integer chunkNumber, Long totalSize, String suffix, Long userId) {
        try {
            return storageManager.storeWithChunk(file.getInputStream(), identifier, totalChunks, chunkNumber, totalSize, file.getSize(), suffix, userId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RPanException("上传失败!");
        }
    }

    /**
     * 执行合并文件操作
     *
     * @param identifier
     * @param userId
     * @return 合并后文件的路径
     */
    private String doMergeChunks(String identifier, Long userId) {
        List<RPanFileChunk> rPanFileChunkList = iFileChunkService.getUploadedChunks(identifier, userId);
        if (CollectionUtils.isNotEmpty(rPanFileChunkList)) {
            List<String> filePaths = rPanFileChunkList.stream().map(RPanFileChunk::getRealPath).collect(Collectors.toList());
            String filePath;
            try {
                filePath = storageManager.mergeChunks(FileUtil.generateChunkKey(identifier, userId), filePaths);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RPanException("合并失败");
            }
            iFileChunkService.deleteFileChunks(identifier, userId);
            return filePath;
        }
        throw new RPanException("为查询到分片信息，合并失败");
    }

}

