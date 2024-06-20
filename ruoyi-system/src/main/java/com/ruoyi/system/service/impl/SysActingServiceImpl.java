package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.system.entity.SysActing;
import com.ruoyi.system.mapper.SysActingMapper;
import com.ruoyi.system.service.SysActingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 代办表(SysActing)表服务实现类
 *
 * @author makejava
 * @since 2022-11-29 15:23:34
 */
@Service("sysActingService")
public class SysActingServiceImpl implements SysActingService {
    @Resource
    private SysActingMapper sysActingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysActing queryById(Integer id) {
        return this.sysActingMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param sysActing 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SysActing> queryByPage(SysActing sysActing) {
        return this.sysActingMapper.queryAllByLimit(sysActing);
    }

    @Override
    public void insertBatch(List<SysActing> sysActingList) {
        sysActingMapper.insertBatch(sysActingList);
    }

    /**
     * 新增数据
     *
     * @param sysActing 实例对象
     * @return 实例对象
     */
    @Override
    public SysActing insert(SysActing sysActing) {
        this.sysActingMapper.insert(sysActing);
        return sysActing;
    }

    /**
     * 修改数据
     *
     * @param sysActing 实例对象
     * @return 实例对象
     */
    @Override
    public SysActing update(SysActing sysActing) {
        this.sysActingMapper.update(sysActing);
        return this.queryById(sysActing.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysActingMapper.deleteById(id) > 0;
    }

    /**
     *  完成待办任务
     * @param titleName
     * @param titleType
     * @param id
     */
    @Override
    public void completeActing(String titleName, String titleType, Integer id) {
        SysActing sysActing = new SysActing();
        sysActing.setTitleName(titleName);
        sysActing.setTitleType(titleType);
        sysActing.setSourceId(id);
        sysActing.setCompleteTime(DateUtil.now());
        sysActing.setState(1);
        sysActingMapper.completeActing(sysActing);
    }

    @Override
    public void updateExecutorUser(SysActing sysActing) {
        sysActingMapper.updateExecutorUser(sysActing);
    }
}
