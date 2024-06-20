package com.rubin.rpan.storage.local.task;

import com.rubin.rpan.schedule.RPanScheduleTask;
import com.rubin.rpan.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 空文件夹清理器
 */
public class EmptyFolderCleaner implements RPanScheduleTask {

    private final static Logger log = LoggerFactory.getLogger(EmptyFolderCleaner.class);

    private String rootFilesScanPath;
    private String rootChunksScanPath;

    public EmptyFolderCleaner(String rootFilesScanPath, String rootChunksScanPath) {
        this.rootFilesScanPath = rootFilesScanPath;
        this.rootChunksScanPath = rootChunksScanPath;
    }

    /**
     * 获取执行器名称
     *
     * @return
     */
    @Override
    public String getName() {
        return "空文件夹清理器";
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        log.info(getName() + "空文件夹清理任务开始！");
        doClean(new File(rootFilesScanPath));
        doClean(new File(rootChunksScanPath));
        log.info(getName() + "空文件夹清理任务结束！");
    }

    /**
     * 清理根目录根文件夹里面的空文件夹
     */
    private void doClean(File rootFolder) {
        try {
            FileUtil.cleanChildEmptyFolder(rootFolder);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO 集成消息通知 统一自动放入异常日志表中
        }
    }

}
