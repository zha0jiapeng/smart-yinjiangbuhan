package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysUserNotice;

import java.util.Date;
import java.util.List;

/**
 * 站内信 数据层
 *
 */
public interface SysUserNoticeMapper
{
    /**
     * 查询指定消息
     * 
     * @param id 公告ID
     * @return 公告信息
     */
    public SysUserNotice selectById(Long id);

    /**
     * 查询消息列表
     * 
     * @param notice 消息
     * @return 公告集合
     */
    public List<SysUserNotice> selectList(Long lastId, Integer limit, Long userId);

    /**
     * 查询用户未读消息
     *
     */
    public List<SysUserNotice> selectUnReadList(Long userId);

    public int selectUnReadCount(Long userId);

    public List<SysUserNotice> queryAll();

    /**
     * 新增消息
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int insert(SysUserNotice notice);

    /**
     * 修改消息
     * 
     * @param notice 消息
     * @return 结果
     */
    public int update(SysUserNotice notice);

    /**
     * 消息置为已读
     *
     * @param type 消息类型
     * @return 结果
     */
    public int readNotice(String type, Date readTime);

    /**
     * 删除消息
     * 
     * @param id 公告ID
     * @return 结果
     */
    public int deleteById(Long id);

    /**
     * 批量删除消息
     * 
     * @param ids 需要删除的公告ID
     * @return 结果
     */
    public int deleteByIds(Long[] ids);
}
