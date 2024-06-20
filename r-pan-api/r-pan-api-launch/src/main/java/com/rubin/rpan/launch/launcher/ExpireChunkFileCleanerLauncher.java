package com.rubin.rpan.launch.launcher;

import com.rubin.rpan.schedule.RPanScheduleManager;
import com.rubin.rpan.services.common.task.ExpireChunkFileCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 清理过期临时分片文件任务启动器
 */
@Component(value = "expireChunkFileCleanerLauncher")
public class ExpireChunkFileCleanerLauncher implements RPanLaunchedProcessor {

    private final static String CRON = "1 0 0 * * ? ";

    @Autowired
    @Qualifier(value = "rPanScheduleManager")
    private RPanScheduleManager rPanScheduleManager;

    @Autowired
    @Qualifier(value = "expireChunkFileCleaner")
    private ExpireChunkFileCleaner expireChunkFileCleaner;

    @Override
    public void run(String... args) throws Exception {
        rPanScheduleManager.startTask(expireChunkFileCleaner, CRON);
    }

}
