package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysUserNotice;
import com.ruoyi.system.mapper.SysUserNoticeMapper;
import com.ruoyi.system.service.SysUserNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserNoticeImpl implements SysUserNoticeService {

    @Resource
    private SysUserNoticeMapper sysUserNoticeMapper;

    //单条消息查询
    @Override
    public SysUserNotice selectById(Long id) {
        return sysUserNoticeMapper.selectById(id);
    }

    //消息列表
    @Override
    public List<SysUserNotice> selectList(Long lastId, Integer limit, Long userId) {
        return sysUserNoticeMapper.selectList(lastId, limit, userId);
    }

    //用户未读消息列表 TODO
    @Override
    public List<SysUserNotice> selectUnReadList(Long userId) {
        return sysUserNoticeMapper.selectUnReadList(userId);
    }

    //用户未读消息数量 TODO
    @Override
    public int selectUnReadCount(Long userId) {
        return sysUserNoticeMapper.selectUnReadCount(userId);
    }


    //新增消息
    @Override
    public int insert(SysUserNotice notice) {
        return sysUserNoticeMapper.insert(notice);
    }


    //编辑消息
    @Override
    public int update(SysUserNotice notice) {
        return sysUserNoticeMapper.update(notice);
    }


    //消息置为已读
    @Override
    public int readNotice(String type, Date readTime) {
        return sysUserNoticeMapper.readNotice(type, readTime);
    }

    //消息推送
    @Override
    public int push(Long id) {
        // 查询
        SysUserNotice notice = selectById(id);
        // 使用websocket进行推送
        return 0;
    }


    //删除消息
    @Override
    public int deleteById(Long id) {
        return sysUserNoticeMapper.deleteById(id);
    }

    //批量删除消息
    @Override
    public int deleteByIds(Long[] ids) {
        return sysUserNoticeMapper.deleteByIds(ids);
    }


}
