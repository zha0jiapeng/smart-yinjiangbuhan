package com.rubin.rpan.services.modules.share.service.impl;

import com.google.common.collect.Lists;
import com.rubin.rpan.services.common.constant.CommonConstant;
import com.rubin.rpan.services.common.exception.RPanException;
import com.rubin.rpan.services.modules.file.service.IUserFileService;
import com.rubin.rpan.services.modules.file.vo.RPanUserFileVO;
import com.rubin.rpan.services.modules.share.dao.RPanShareFileMapper;
import com.rubin.rpan.services.modules.share.entity.RPanShareFile;
import com.rubin.rpan.services.modules.share.service.IShareFileService;
import com.rubin.rpan.util.StringListUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 项目分享文件业务处理类
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@Service(value = "shareFileService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ShareFileServiceImpl implements IShareFileService {

    @Autowired
    @Qualifier(value = "rPanShareFileMapper")
    private RPanShareFileMapper rPanShareFileMapper;

    @Autowired
    @Qualifier(value = "userFileService")
    private IUserFileService iUserFileService;

    /**
     * 批量保存分享文件列表
     *
     * @param shareId
     * @param shareFileIds
     * @param userId
     * @return
     */
    @Override
    public void saveBatch(Long shareId, String shareFileIds, Long userId) {
        List<RPanShareFile> rPanShareFileList = assembleRPanShareFileList(shareId, shareFileIds, userId);
        if (rPanShareFileMapper.insertBatch(rPanShareFileList) != rPanShareFileList.size()) {
            throw new RPanException("保存分享文件列表失败");
        }
    }

    /**
     * 取消分享列表
     *
     * @param shareIds
     * @return
     */
    @Override
    public void cancelShareFiles(String shareIds) {
        if (rPanShareFileMapper.deleteByShareIdList(StringListUtil.string2LongList(shareIds)) <= CommonConstant.ZERO_INT) {
            throw new RPanException("取消分享文件失败");
        }
    }

    /**
     * 通过分享id，查询文件列表信息
     *
     * @param shareId
     * @return
     */
    @Override
    public List<RPanUserFileVO> getShareFileInfos(Long shareId) {
        List<Long> fileIds = rPanShareFileMapper.selectFileIdsByShareId(shareId);
        return iUserFileService.list(StringListUtil.longListToString(fileIds));
    }

    /**
     * 通过文件id查找对应的分享id
     *
     * @param fileIds
     * @return
     */
    @Override
    public List<Long> getShareIdByFileIds(String fileIds) {
        return rPanShareFileMapper.selectShareIdByFileIds(StringListUtil.string2LongList(fileIds));
    }

    /**
     * 获取该分享的所有文件列表
     *
     * @param shareId
     * @return
     */
    @Override
    public List<RPanUserFileVO> getAllShareFileInfos(Long shareId) {
        List<Long> fileIds = rPanShareFileMapper.selectFileIdsByShareId(shareId);
        if (CollectionUtils.isEmpty(fileIds)) {
            return Lists.newArrayList();
        }
        return iUserFileService.allList(StringListUtil.longListToString(fileIds));
    }

    /**
     * 获取分享的所有关联的文件ID
     *
     * @param shareId
     * @return
     */
    @Override
    public List<Long> getFileIdsByShareId(Long shareId) {
        return rPanShareFileMapper.selectFileIdsByShareId(shareId);
    }

    /******************************************************私有****************************************************/

    /**
     * 生成分享文件列表
     *
     * @param shareId
     * @param shareFileIds
     * @param userId
     * @return
     */
    private List<RPanShareFile> assembleRPanShareFileList(Long shareId, String shareFileIds, Long userId) {
        final List<RPanShareFile> rPanShareFileList = Lists.newArrayList();
        StringListUtil.string2LongList(shareFileIds).stream().forEach(shareFileId -> {
            RPanShareFile rPanShareFile = new RPanShareFile();
            rPanShareFile.setShareId(shareId);
            rPanShareFile.setFileId(shareFileId);
            rPanShareFile.setCreateUser(userId);
            rPanShareFile.setCreateTime(new Date());
            rPanShareFileList.add(rPanShareFile);
        });

        return rPanShareFileList;
    }

}
