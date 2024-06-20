package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysUserNotice;

import java.util.Date;
import java.util.List;

public interface SysUserNoticeService {
    /**
     * 查询指定消息
     */
    SysUserNotice selectById(Long id);

    /**
     * 查询消息列表
     */
    //List<SysUserNotice> selectList(SysUserNotice notice);
    List<SysUserNotice> selectList(Long lastId, Integer limit, Long userId);

    List<SysUserNotice> selectUnReadList(Long userId);

    public int selectUnReadCount(Long userId);
    /**
     * 新增消息
     */
    int insert(SysUserNotice notice);

    /**
     * 修改消息
     */
    int update(SysUserNotice notice);

    /**
     * 消息置为已读
     */
    int readNotice(String type, Date readTime);

    /**
     * 消息推送
     */
    int push(Long id);

    /**
     * 删除消息
     */
    int deleteById(Long id);

    /**
     * 批量删除消息
     */
    int deleteByIds(Long[] ids);
}
