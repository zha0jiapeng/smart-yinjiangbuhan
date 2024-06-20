package com.rubin.rpan.services.modules.patch.service.impl;

import com.rubin.rpan.services.common.constant.CommonConstant;
import com.rubin.rpan.services.common.exception.RPanException;
import com.rubin.rpan.services.modules.file.entity.RPanFile;
import com.rubin.rpan.services.modules.file.service.IFileService;
import com.rubin.rpan.services.modules.log.service.IErrorLogService;
import com.rubin.rpan.services.modules.patch.service.IPatchService;
import com.rubin.rpan.storage.local.config.LocalStorageConfig;
import com.rubin.rpan.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 项目补丁业务实现类
 */
@Service(value = "patchService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PatchServiceImpl implements IPatchService {

    @Autowired
    @Qualifier(value = "localStorageConfig")
    private LocalStorageConfig localStorageConfig;

    @Autowired
    @Qualifier(value = "fileService")
    private IFileService iFileService;

    @Autowired
    @Qualifier(value = "errorLogService")
    private IErrorLogService iErrorLogService;

    private static final Long BATCH_NUM = 500L;

    @Override
    public void transferFile(String rootFilePath, String tempFilePath) {
        // 删除模板文件夹
        cleanTempFolder(tempFilePath);
        // 转移所有的物理文件
        doTransferFile();
        // 清除空的物理文件夹
        cleanEmptyFolder(rootFilePath);
    }

    /************************************************************私有************************************************************/

    /**
     * 清除空的物理文件夹
     *
     * @param rootFilePath
     */
    private void cleanEmptyFolder(String rootFilePath) {
        try {
            FileUtil.cleanChildEmptyFolder(new File(rootFilePath));
        } catch (IOException e) {
            // TODO 集成消息通知 统一自动放入异常日志表中
            iErrorLogService.save("清理空文件夹失败，请手动操作！根文件夹路径为：" + rootFilePath, CommonConstant.ZERO_LONG);
            e.printStackTrace();
        }
    }

    /**
     * 转移所有物理文件
     */
    private void doTransferFile() {
        // 滚动修改所有文件的文件地址
        List<RPanFile> rPanFileList;
        Long scrollPointer = 1L;
        do {
            rPanFileList = iFileService.getRPanFilePage(scrollPointer, BATCH_NUM);
            if (CollectionUtils.isNotEmpty(rPanFileList)) {
                for (RPanFile rPanFile : rPanFileList) {
                    Date createTime = rPanFile.getCreateTime();
                    String sourcePath = rPanFile.getRealPath(),
                            filename = rPanFile.getFilename(),
                            targetPath = FileUtil.generateFilePath(localStorageConfig.getRootFilePath(), filename, createTime);
                    try {
                        FileUtil.moveFile(sourcePath, targetPath);
                    } catch (IOException e) {
                        // TODO 集成消息通知 统一自动放入异常日志表中
                        iErrorLogService.save("物理文件移动失败，请手动操作！文件原路径为：" + sourcePath + "，目标路径为：" + targetPath, CommonConstant.ZERO_LONG);
                        e.printStackTrace();
                        continue;
                    }
                    try {
                        rPanFile.setRealPath(targetPath);
                        iFileService.updateSelective(rPanFile);
                    } catch (RPanException e) {
                        // TODO 集成消息通知 统一自动放入异常日志表中
                        iErrorLogService.save("文件记录修改失败，请手动修改！文件记录ID为：" + rPanFile.getFileId() + "，保存的路径为：" + targetPath, CommonConstant.ZERO_LONG);
                        e.printStackTrace();
                    }
                }
                scrollPointer++;
            }
        } while (CollectionUtils.isNotEmpty(rPanFileList));

    }

    /**
     * 删除模板文件夹
     *
     * @param tempFilePath
     */
    private void cleanTempFolder(String tempFilePath) {
        try {
            FileUtil.delete(tempFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO 集成消息通知 统一自动放入异常日志表中
            iErrorLogService.save("文件物理删除失败，请手动删除！文件路径为：" + tempFilePath, CommonConstant.ZERO_LONG);
        }
    }

}
