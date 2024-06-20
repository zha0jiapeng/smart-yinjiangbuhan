package com.rubin.rpan.services.modules.file;

import com.rubin.rpan.launch.RPanLaunch;
import com.rubin.rpan.services.common.exception.RPanException;
import com.rubin.rpan.services.modules.file.constant.FileConstant;
import com.rubin.rpan.services.modules.file.entity.RPanUserFile;
import com.rubin.rpan.services.modules.file.service.IUserFileService;
import com.rubin.rpan.services.modules.file.vo.*;
import com.rubin.rpan.services.modules.user.service.IUserService;
import com.rubin.rpan.services.modules.user.vo.RPanUserVO;
import com.rubin.rpan.util.StringListUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * 用户文件业务测试类
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@SpringBootTest(classes = RPanLaunch.class)
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class UserFileServiceTest {

    @Autowired
    @Qualifier(value = "userFileService")
    private IUserFileService iUserFileService;

    @Autowired
    @Qualifier(value = "userService")
    private IUserService iUserService;

    /**
     * 测试查询文件列表成功
     */
    @Test
    @Rollback
    public void listSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(0, rPanUserFileVOList.size());
        rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId, FileConstant.DelFlagEnum.NO.getCode());
        Assert.assertEquals(0, rPanUserFileVOList.size());
        rPanUserFileVOList = iUserFileService.list(StringListUtil.longListToString(rPanUserVO.getRootFileId()));
        Assert.assertEquals(1, rPanUserFileVOList.size());
    }

    /**
     * 测试创建文件夹成功
     */
    @Test
    @Rollback
    public void createFolderSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(1, rPanUserFileVOList.size());
    }

    /**
     * 测试创建文件夹重复自动重命名成功
     */
    @Test
    @Rollback
    public void createFolderHandleSameNameSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder", userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(2, rPanUserFileVOList.size());
    }

    /**
     * 测试修改文件名称成功
     */
    @Test
    @Rollback
    public void updateFilenameSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        iUserFileService.updateFilename(rPanUserFileVO.getFileId(), rPanUserFileVO.getFilename() + "_update", userId);
        rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(1, rPanUserFileVOList.size());
    }

    /**
     * 测试修改文件名称-没有此文件失败
     */
    @Test(expected = RPanException.class)
    @Rollback
    public void updateFilenameNoThatFileFailTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        iUserFileService.updateFilename(0L, rPanUserFileVO.getFilename() + "_update", userId);
    }

    /**
     * 测试修改文件名称-未更新新文件名失败
     */
    @Test(expected = RPanException.class)
    @Rollback
    public void updateFilenameNoNewFilenameFailTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        iUserFileService.updateFilename(rPanUserFileVO.getFileId(), rPanUserFileVO.getFilename(), userId);
    }

    /**
     * 测试修改文件名称-新文件名已存在失败
     */
    @Test(expected = RPanException.class)
    @Rollback
    public void updateFilenameNewFilenameExitFailTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-2", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        iUserFileService.updateFilename(rPanUserFileVO.getFileId(), "test-folder-2", userId);
    }

    /**
     * 测试删除文件成功
     */
    @Test
    @Rollback
    public void deleteSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        iUserFileService.delete(StringListUtil.longListToString(rPanUserFileVO.getFileId()), userId);
        rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(0, rPanUserFileVOList.size());
    }

    /**
     * 测试上传文件成功
     */
    @Test
    @Rollback
    public void uploadSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        MultipartFile multipartFile = generateMultipartFile();
        iUserFileService.upload(multipartFile, rPanUserVO.getRootFileId(), userId, "111", multipartFile.getSize(), multipartFile.getOriginalFilename());
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(1, rPanUserFileVOList.size());
    }

    /**
     * 测试上传分片文件成功
     */
    @Test
    @Rollback
    public void uploadWithChunkSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            new chunkUploader(countDownLatch,
                    i,
                    10,
                    iUserFileService,
                    userId,
                    rPanUserVO.getRootFileId()).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class chunkUploader extends Thread {

        private CountDownLatch countDownLatch;
        private Integer chunk;
        private Integer chunks;
        private IUserFileService iUserFileService;
        private Long userId;
        private Long parentId;

        public chunkUploader(CountDownLatch countDownLatch, Integer chunk, Integer chunks, IUserFileService iUserFileService, Long userId, Long parentId) {
            this.countDownLatch = countDownLatch;
            this.chunk = chunk;
            this.chunks = chunks;
            this.iUserFileService = iUserFileService;
            this.userId = userId;
            this.parentId = parentId;
        }

        @Override
        public void run() {
            MultipartFile multipartFile = generateMultipartFile();
            Long totalSize = multipartFile.getSize() * chunks;
            final FileChunkUploadVO fileChunkUploadVO = iUserFileService.uploadWithChunk(multipartFile,
                    userId,
                    "111",
                    chunks,
                    chunk,
                    totalSize,
                    "test.txt");
            if (fileChunkUploadVO.getMergeFlag().equals(FileConstant.MergeFlag.READY.getCode())) {
                System.out.println("分片" + chunk + "检测到可以合并文件");
                iUserFileService.mergeChunks("test.txt", "111", parentId, totalSize, userId);
                countDownLatch.countDown();
            } else {
                countDownLatch.countDown();
            }

        }

    }

    /**
     * 测试获取文件夹树成功
     */
    @Test
    @Rollback
    public void getFolderTreeSuccessTest() {
        Long userId = register();
        List<FolderTreeNodeVO> folderTreeNodeList = iUserFileService.getFolderTree(userId);
        Assert.assertEquals(1, folderTreeNodeList.size());
    }

    /**
     * 测试转移文件(批量)成功
     */
    @Test
    @Rollback
    public void transferSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-2", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO targetFolder = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        RPanUserFileVO toBeTransferFile = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-2", rPanUserFileVO1.getFilename())).findFirst().get();
        iUserFileService.transfer(StringListUtil.longListToString(toBeTransferFile.getFileId()), targetFolder.getFileId(), userId);
        rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(1, rPanUserFileVOList.size());
    }

    /**
     * 测试转移文件(批量)目标文件不是文件夹失败
     */
    @Test(expected = RPanException.class)
    @Rollback
    public void transferTargetFileIsNotFolderFailTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        MultipartFile multipartFile = generateMultipartFile();
        iUserFileService.upload(multipartFile, rPanUserVO.getRootFileId(), userId, "111", multipartFile.getSize(), multipartFile.getOriginalFilename());
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO toBeTransferFile = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        RPanUserFileVO targetFolderFile = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test.txt", rPanUserFileVO1.getFilename())).findFirst().get();
        iUserFileService.transfer(StringListUtil.longListToString(toBeTransferFile.getFileId()), targetFolderFile.getFileId(), userId);
    }

    /**
     * 测试批量复制文件成功
     */
    @Test
    @Rollback
    public void copySuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO toBeCopiedFile = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        iUserFileService.copy(StringListUtil.longListToString(toBeCopiedFile.getFileId()), toBeCopiedFile.getParentId(), userId);
        rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(2, rPanUserFileVOList.size());
    }

    /**
     * 测试批量复制文件-要复制的文件中包含选中的目标文件夹失败
     */
    @Test(expected = RPanException.class)
    @Rollback
    public void copyFileIdsContainTargetParentIdFailTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO toBeCopiedFile = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        iUserFileService.copy(StringListUtil.longListToString(toBeCopiedFile.getFileId()), toBeCopiedFile.getFileId(), userId);
    }

    /**
     * 测试批量复制文件-目标文件不是文件夹失败
     */
    @Test(expected = RPanException.class)
    @Rollback
    public void copyTargetFileIsNotFolderFailTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        MultipartFile multipartFile = generateMultipartFile();
        iUserFileService.upload(multipartFile, rPanUserVO.getRootFileId(), userId, "111", multipartFile.getSize(), multipartFile.getOriginalFilename());
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO toBeCopiedFile = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        RPanUserFileVO targetFolderFile = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test.txt", rPanUserFileVO1.getFilename())).findFirst().get();
        iUserFileService.copy(StringListUtil.longListToString(toBeCopiedFile.getFileId()), targetFolderFile.getFileId(), userId);
    }

    /**
     * 测试通过文件名搜索文件成功
     */
    @Test
    @Rollback
    public void searchSuccessTest() {
        Long userId = register();
        String searchContent = "test-folder-1";
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), searchContent, userId);
        List<RPanUserFileSearchVO> rPanUserFileSearchVOList = iUserFileService.search(searchContent, FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(1, rPanUserFileSearchVOList.size());
    }

    /**
     * 测试查询文件详情成功
     */
    @Test
    @Rollback
    public void detailSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        Assert.assertEquals(rPanUserFileVO, iUserFileService.detail(rPanUserFileVO.getFileId(), userId));
    }

    /**
     * 测试获取面包屑列表成功
     */
    @Test
    @Rollback
    public void getBreadcrumbsSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().filter(rPanUserFileVO1 -> Objects.equals("test-folder-1", rPanUserFileVO1.getFilename())).findFirst().get();
        List<BreadcrumbVO> breadcrumbVOList = iUserFileService.getBreadcrumbs(rPanUserFileVO.getFileId(), userId);
        Assert.assertEquals(2, breadcrumbVOList.size());
    }

    /**
     * 测试批量还原用户文件的删除状态成功
     */
    @Test
    @Rollback
    public void restoreUserFilesSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        iUserFileService.delete(StringListUtil.longListToString(rPanUserFileVO.getFileId()), userId);
        rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(0, rPanUserFileVOList.size());
        iUserFileService.restoreUserFiles(StringListUtil.longListToString(rPanUserFileVO.getFileId()), userId);
        rPanUserFileVOList = iUserFileService.list(rPanUserFileVO.getParentId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(1, rPanUserFileVOList.size());
    }

    /**
     * 测试批量还原用户文件的删除状态-参数错误失败
     */
    @Test(expected = RPanException.class)
    @Rollback
    public void restoreErrorParamFailTest() {
        iUserFileService.restoreUserFiles(null, null);
    }

    /**
     * 测试物理删除用户文件成功
     */
    @Test
    @Rollback
    public void physicalDeleteUserFilesSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        MultipartFile multipartFile = generateMultipartFile();
        iUserFileService.upload(multipartFile, rPanUserVO.getRootFileId(), userId, "111", multipartFile.getSize(), multipartFile.getOriginalFilename());
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        List<Long> fileIds = rPanUserFileVOList.stream().map(RPanUserFileVO::getFileId).collect(Collectors.toList());
        iUserFileService.physicalDeleteUserFiles(StringListUtil.longListToString(fileIds), userId);
        rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        Assert.assertEquals(0, rPanUserFileVOList.size());
    }

    /**
     * 测试获取对应文件列表的所有文件以及自文件信息成功
     */
    @Test
    @Rollback
    public void allListSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        MultipartFile multipartFile = generateMultipartFile();
        iUserFileService.upload(multipartFile, rPanUserFileVO.getFileId(), userId, "111", multipartFile.getSize(), multipartFile.getOriginalFilename());
        rPanUserFileVOList = iUserFileService.allList(StringListUtil.longListToString(rPanUserFileVO.getFileId()));
        Assert.assertEquals(2, rPanUserFileVOList.size());
    }

    /**
     * 测试获取对应文件列表的所有文件以及自文件信息成功
     */
    @Test
    @Rollback
    public void getUserTopFileInfoSuccessTest() {
        Long userId = register();
        RPanUserFile userTopFileInfo = iUserFileService.getUserTopFileInfo(userId);
        Assert.assertNotNull(userTopFileInfo);
    }

    /**
     * 测试获取对应文件列表的所有文件以及自文件信息成功
     */
    @Test
    @Rollback
    public void getAllAvailableFileIdByFileIdsSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        MultipartFile multipartFile = generateMultipartFile();
        iUserFileService.upload(multipartFile, rPanUserFileVO.getFileId(), userId, "111", multipartFile.getSize(), multipartFile.getOriginalFilename());
        String allAvailableFileIdByFileIds = iUserFileService.getAllAvailableFileIdByFileIds(StringListUtil.longListToString(rPanUserFileVO.getFileId()));
        Assert.assertEquals(2, StringListUtil.string2LongList(allAvailableFileIdByFileIds).size());
    }

    /**
     * 测试验所有的父文件夹以及当前文件有效成功
     */
    @Test
    @Rollback
    public void checkAllUpFileAvailableSuccessTest() {
        Long userId = register();
        RPanUserVO rPanUserVO = info(userId);
        iUserFileService.createFolder(rPanUserVO.getRootFileId(), "test-folder-1", userId);
        List<RPanUserFileVO> rPanUserFileVOList = iUserFileService.list(rPanUserVO.getRootFileId(), FileConstant.ALL_FILE_TYPE, userId);
        RPanUserFileVO rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        MultipartFile multipartFile = generateMultipartFile();
        iUserFileService.upload(multipartFile, rPanUserFileVO.getFileId(), userId, "111", multipartFile.getSize(), multipartFile.getOriginalFilename());
        rPanUserFileVOList = iUserFileService.list(rPanUserFileVO.getFileId(), FileConstant.ALL_FILE_TYPE, userId);
        rPanUserFileVO = rPanUserFileVOList.stream().findFirst().get();
        boolean available = iUserFileService.checkAllUpFileAvailable(Arrays.asList(rPanUserFileVO.getFileId()));
        Assert.assertTrue(available);
        iUserFileService.delete(rPanUserFileVO.getParentId().toString(), userId);
        available = iUserFileService.checkAllUpFileAvailable(Arrays.asList(rPanUserFileVO.getFileId()));
        Assert.assertFalse(available);
    }

    /********************************************************************************私有********************************************************************************/

    /**
     * 注册用户
     *
     * @return
     */
    private Long register() {
        String userId = iUserService.register("test-user", "12345678", "test-question", "test-answer");
        Assert.assertNotNull(userId);
        return Long.valueOf(userId);
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    private RPanUserVO info(Long userId) {
        RPanUserVO rPanUserVO = iUserService.info(userId);
        Assert.assertNotNull(rPanUserVO);
        return rPanUserVO;
    }

    /**
     * 生成mock的MultipartFile实体
     *
     * @return
     */
    private static MultipartFile generateMultipartFile() {
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile("file", "test.txt", ",multipart/form-data", "test upload content\r\n".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return multipartFile;
    }

}
