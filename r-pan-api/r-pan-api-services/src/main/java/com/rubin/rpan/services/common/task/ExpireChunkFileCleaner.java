package com.rubin.rpan.services.common.task;

import com.rubin.rpan.schedule.RPanScheduleTask;
import com.rubin.rpan.services.common.constant.CommonConstant;
import com.rubin.rpan.services.modules.file.entity.RPanFileChunk;
import com.rubin.rpan.services.modules.file.service.IFileChunkService;
import com.rubin.rpan.services.modules.log.service.IErrorLogService;
import com.rubin.rpan.storage.StorageManager;
import com.rubin.rpan.util.DateUtil;
import com.rubin.rpan.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 过期的分片文件清理器
 */
@Component(value = "expireChunkFileCleaner")
public class ExpireChunkFileCleaner implements RPanScheduleTask {

    private final static Logger log = LoggerFactory.getLogger(ExpireChunkFileCleaner.class);

    private static final Long BATCH_NUM = 500L;

    @Autowired
    @Qualifier(value = "fileChunkService")
    private IFileChunkService iFileChunkService;

    @Autowired
    @Qualifier(value = "errorLogService")
    private IErrorLogService iErrorLogService;

    @Autowired
    @Qualifier(value = "storageManager")
    private StorageManager storageManager;

    @Override
    public void run() {
        log.info(getName() + "过期文件清理任务开始！");
        // 滚动清理所有的过期临时文件
        List<RPanFileChunk> expiredFileChunkRecords;
        Long scrollPointer = 1L;
        do {
            expiredFileChunkRecords = iFileChunkService.getExpiredFileChunkRecords(scrollPointer, BATCH_NUM);
            if (CollectionUtils.isNotEmpty(expiredFileChunkRecords)) {
                expiredFileChunkRecords.stream().forEach(rPanFileChunk -> {
                    try {
                        storageManager.delete(FileUtil.generateChunkKey(rPanFileChunk.getIdentifier(), rPanFileChunk.getCreateUser()), rPanFileChunk.getRealPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        iErrorLogService.save("文件物理删除失败，请手动删除！文件路径：" + rPanFileChunk.getRealPath(), CommonConstant.ZERO_LONG);
                    }
                    iFileChunkService.deleteFileChunk(rPanFileChunk.getId());
                    log.info("清理了文件：" + rPanFileChunk.getRealPath());
                });
                scrollPointer++;
            }
        } while (CollectionUtils.isNotEmpty(expiredFileChunkRecords));
        log.info(getName() + "过期文件清理任务结束！");
    }

    @Override
    public String getName() {
        return "过期文件清理器";
    }

}
