package com.rubin.rpan.launch.launcher;

import com.rubin.rpan.schedule.RPanScheduleManager;
import com.rubin.rpan.storage.local.config.LocalStorageConfig;
import com.rubin.rpan.storage.local.task.EmptyFolderCleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 空文件夹清理器启动器
 */
@Component(value = "emptyFolderCleanerLauncher")
@ConditionalOnProperty(prefix = "rpan.storage.processor", name = "type", havingValue = "com.rubin.rpan.storage.local.LocalStorageProcessor")
public class EmptyFolderCleanerLauncher implements RPanLaunchedProcessor {

    private final static String CRON = "1 0 0 * * ? ";

    @Autowired
    @Qualifier(value = "localStorageConfig")
    private LocalStorageConfig localStorageConfig;

    @Autowired
    @Qualifier(value = "rPanScheduleManager")
    private RPanScheduleManager rPanScheduleManager;

    @Override
    public void run(String... args) throws Exception {
        rPanScheduleManager.startTask(new EmptyFolderCleaner(localStorageConfig.getRootFilePath(), localStorageConfig.getChunksPath()), CRON);
    }

}
