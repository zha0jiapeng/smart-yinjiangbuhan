package com.rubin.rpan.util;

import com.rubin.rpan.launch.RPanLaunch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

/**
 * 文件工具测试类
 */
@SpringBootTest(classes = RPanLaunch.class)
@RunWith(SpringRunner.class)
public class FileUtilTest {

    /**
     * 测试检查空文件夹方法
     *
     * @throws IOException
     */
    @Test
    public void testCheckEmptyFolder() throws IOException {
        File userHome = new File(System.getProperty("user.home"));
        File tempFolder = new File(userHome.getAbsolutePath() + File.separator + "temp");
        for (int i = 0; i < 3; i++) {
            FileUtil.createFile(tempFolder.getAbsolutePath() + File.separator + "temp" + i + File.separator + i + ".txt");
        }
        Assert.assertFalse(FileUtil.checkIsEmptyFolder(tempFolder));
        for (int i = 0; i < 3; i++) {
            FileUtil.delete(tempFolder.getAbsolutePath() + File.separator + "temp" + i + File.separator + i + ".txt");
        }
        Assert.assertTrue(FileUtil.checkIsEmptyFolder(tempFolder));
        FileUtil.delete(tempFolder.getAbsolutePath());
    }

    /**
     * 测试移动物理文件
     *
     * @throws IOException
     */
    @Test
    public void testMoveFile() throws IOException {
        File userHome = new File(System.getProperty("user.home"));
        String sourcePath = userHome.getAbsolutePath() + File.separator + "temp" + File.separator + "test.txt";
        String targetPath = userHome.getAbsolutePath() + File.separator + "temp1" + File.separator + "test1.txt";
        FileUtil.createFile(userHome.getAbsolutePath() + File.separator + "temp" + File.separator + "test.txt");
        FileUtil.moveFile(sourcePath, targetPath);
    }

}
