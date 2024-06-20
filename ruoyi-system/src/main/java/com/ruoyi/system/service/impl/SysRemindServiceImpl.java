package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.entity.SysRemind;
import com.ruoyi.system.mapper.SysRemindMapper;
import com.ruoyi.system.service.SysRemindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 提醒表(SysRemind)表服务实现类
 *
 * @author makejava
 * @since 2022-12-17 15:16:40
 */
@Slf4j
@Service("sysRemindService")
public class SysRemindServiceImpl implements SysRemindService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private SysRemindMapper sysRemindMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRemind queryById(Integer id) {
        return this.sysRemindMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param sysRemind 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SysRemind> queryByPage(SysRemind sysRemind) {
        return this.sysRemindMapper.queryAllByLimit(sysRemind);
    }

    /**
     * 新增数据
     *
     * @param sysRemind 实例对象
     * @return 实例对象
     */
    @Override
    public SysRemind insert(SysRemind sysRemind) {
        this.sysRemindMapper.insert(sysRemind);
        return sysRemind;
    }

    @Override
    public SysRemind insert(String type, String content, Integer userId) {
        SysRemind sysRemind = new SysRemind();
        sysRemind.setRemindType(type);
        sysRemind.setRemindContent(content);
        sysRemind.setRemindUserId(userId);
        sysRemindMapper.insert(sysRemind);
        return sysRemind;
    }

    /**
     * 修改数据
     *
     * @param sysRemind 实例对象
     * @return 实例对象
     */
    @Override
    public SysRemind update(SysRemind sysRemind) {
        this.sysRemindMapper.update(sysRemind);
        return this.queryById(sysRemind.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysRemindMapper.deleteById(id) > 0;
    }

    public static Date dateAddOne(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天,整数  往后推,负数往前移动
        calendar.add(Calendar.DATE, 1);
        //这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        return date;

    }

}
