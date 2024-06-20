package com.rubin.rpan.services.modules.file.service.impl;

import com.rubin.rpan.services.common.config.RPanServicesConfig;
import com.rubin.rpan.services.common.constant.CommonConstant;
import com.rubin.rpan.services.common.exception.RPanException;
import com.rubin.rpan.services.modules.file.dao.RPanFileChunkMapper;
import com.rubin.rpan.services.modules.file.entity.RPanFileChunk;
import com.rubin.rpan.services.modules.file.service.IFileChunkService;
import com.rubin.rpan.services.modules.log.service.IErrorLogService;
import com.rubin.rpan.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 实体文件分片业务处理类
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@Service(value = "fileChunkService")
@Transactional(rollbackFor = Exception.class, propagation= Propagation.SUPPORTS)
public class FileChunkServiceImpl implements IFileChunkService {

    @Autowired
    @Qualifier(value = "rPanFileChunkMapper")
    private RPanFileChunkMapper rPanFileChunkMapper;

    @Autowired
    @Qualifier(value = "rPanServicesConfig")
    private RPanServicesConfig rPanServicesConfig;

    @Autowired
    @Qualifier(value = "errorLogService")
    private IErrorLogService iErrorLogService;

    /**
     * 保存分片文件信息
     *
     * @param userId
     * @param identifier
     * @param chunkNumber
     * @param realPath
     */
    @Override
    public void save(Long userId, String identifier, Integer chunkNumber, String realPath) {
        if (Objects.isNull(userId) || Objects.isNull(chunkNumber) || StringUtils.isBlank(identifier)) {
            throw new RPanException("保存文件分片信息失败,参数非法");
        }
        if (chunkNumber <= CommonConstant.ZERO_INT) {
            throw new RPanException("保存文件分片信息失败,分片下标必须从1开始");
        }
        RPanFileChunk rPanFileChunk = new RPanFileChunk();
        rPanFileChunk.setIdentifier(identifier);
        rPanFileChunk.setChunkNumber(chunkNumber);
        rPanFileChunk.setRealPath(realPath);
        rPanFileChunk.setExpirationTime(DateUtil.afterDays(rPanServicesConfig.getChunkFileExpirationDays()));
        rPanFileChunk.setCreateUser(userId);
        rPanFileChunk.setCreateTime(new Date());
        try {
            if (rPanFileChunkMapper.insertSelective(rPanFileChunk) != CommonConstant.ONE_INT) {
                throw new RPanException("保存文件分片信息失败");
            }
        } catch (DuplicateKeyException e) {

        }
    }

    /**
     * 获取某用户已上传的文件分片集合
     *
     * @param identifier
     * @param userId
     * @return
     */
    @Override
    public List<RPanFileChunk> getUploadedChunks(String identifier, Long userId) {
        return rPanFileChunkMapper.selectRPanFileChunkListByIdentifierAndUserId(identifier, userId);
    }

    /**
     * 删除某用户上传的特定文件标识的所有分片记录
     *
     * @param identifier
     * @param userId
     */
    @Override
    public void deleteFileChunks(String identifier, Long userId) {
        if (rPanFileChunkMapper.deleteByIdentifierAndUserId(identifier, userId) < CommonConstant.ZERO_INT) {
            iErrorLogService.save("文件分片记录删除失败，请手动删除！分片唯一标识为：" + identifier + "，用户id为：" + userId, userId);
        }
    }

    /**
     * 根据主键删除文件分片记录
     *
     * @param id
     */
    @Override
    public void deleteFileChunk(Long id) {
        if (rPanFileChunkMapper.deleteByPrimaryKey(id) != CommonConstant.ONE_INT) {
            iErrorLogService.save("文件分片记录删除失败，请手动删除！分片记录id为：" + id, CommonConstant.ZERO_LONG);
        }
    }

    /**
     * 分批查询过期的分片记录
     *
     * @param scrollPointer
     * @param batchNum
     * @return
     */
    @Override
    public List<RPanFileChunk> getExpiredFileChunkRecords(Long scrollPointer, Long batchNum) {
        return rPanFileChunkMapper.selectExpiredFileChunkRecords((scrollPointer - 1) * batchNum, batchNum);
    }

    /**
     * 查询某用户上传的某文件的分片数量
     *
     * @param identifier
     * @param userId
     * @return
     */
    @Override
    public Integer getUploadedChunkCount(String identifier, Long userId) {
        return rPanFileChunkMapper.selectUploadedChunkCount(identifier, userId);
    }

}
