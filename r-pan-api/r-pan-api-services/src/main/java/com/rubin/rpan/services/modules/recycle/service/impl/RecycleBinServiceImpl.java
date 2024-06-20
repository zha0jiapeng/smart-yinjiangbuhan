package com.rubin.rpan.services.modules.recycle.service.impl;

import com.rubin.rpan.services.modules.file.constant.FileConstant;
import com.rubin.rpan.services.modules.file.service.IUserFileService;
import com.rubin.rpan.services.modules.file.vo.RPanUserFileVO;
import com.rubin.rpan.services.modules.recycle.service.IRecycleBinService;
import com.rubin.rpan.services.modules.share.service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 回收站业务处理实现
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@Service(value = "recycleBinService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RecycleBinServiceImpl implements IRecycleBinService {

    @Autowired
    @Qualifier(value = "userFileService")
    private IUserFileService iUserFileService;

    @Autowired
    @Qualifier(value = "shareService")
    private IShareService iShareService;

    /**
     * 获取回收站的文件列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<RPanUserFileVO> list(Long userId) {
        return iUserFileService.list(null, FileConstant.ALL_FILE_TYPE, userId, FileConstant.DelFlagEnum.YES.getCode());
    }

    /**
     * 还原文件
     *
     * @param fileIds
     * @param userId
     */
    @Override
    public void restore(String fileIds, Long userId) {
        iUserFileService.restoreUserFiles(fileIds, userId);
        iShareService.refreshShareStatus(iUserFileService.getAllAvailableFileIdByFileIds(fileIds));
    }

    /**
     * 回收站删除文件
     *
     * @param fileIds
     * @param userId
     * @return
     */
    @Override
    public void delete(String fileIds, Long userId) {
        iUserFileService.physicalDeleteUserFiles(fileIds, userId);
    }

}
