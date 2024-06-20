package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.entity.SysPersonnel;
import com.ruoyi.system.mapper.SysPersonnelMapper;
import com.ruoyi.system.service.SysPersonnelService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人员信息表(SysPersonnel)表服务实现类
 *
 * @author makejava
 * @since 2022-12-25 14:31:15
 */
@Service("sysPersonnelService")
public class SysPersonnelServiceImpl implements SysPersonnelService {
    @Resource
    private SysPersonnelMapper sysPersonnelMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysPersonnel queryById(Integer id) {
        return this.sysPersonnelMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param sysPersonnel 筛选条件
     * @return 查询结果
     */
    @Override
    public List<SysPersonnel> queryByPage(SysPersonnel sysPersonnel) {
        return sysPersonnelMapper.queryAllByLimit(sysPersonnel);
    }

    /**
     * 新增数据
     *
     * @param sysPersonnel 实例对象
     * @return 实例对象
     */
    @Override
    public SysPersonnel insert(SysPersonnel sysPersonnel) {
        this.sysPersonnelMapper.insert(sysPersonnel);
        return sysPersonnel;
    }

    /**
     * 修改数据
     *
     * @param sysPersonnel 实例对象
     * @return 实例对象
     */
    @Override
    public SysPersonnel update(SysPersonnel sysPersonnel) {
        this.sysPersonnelMapper.update(sysPersonnel);
        return this.queryById(sysPersonnel.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysPersonnelMapper.deleteById(id)> 0;
    }
}
