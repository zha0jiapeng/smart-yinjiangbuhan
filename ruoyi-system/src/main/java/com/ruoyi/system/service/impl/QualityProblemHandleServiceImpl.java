package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.QualityProblemHandle;
import com.ruoyi.system.mapper.QualityProblemHandleMapper;
import com.ruoyi.system.service.QualityProblemHandleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (QualityProblemHandle)表服务实现类
 *
 * @author makejava
 * @since 2023-01-19 17:08:13
 */
@Service("qualityProblemHandleService")
public class QualityProblemHandleServiceImpl implements QualityProblemHandleService {
    @Resource
    private QualityProblemHandleMapper qualityProblemHandleMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QualityProblemHandle queryById(Integer id) {
        return this.qualityProblemHandleMapper.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param qualityProblemHandle 实例对象
     * @return 实例对象
     */
    @Override
    public QualityProblemHandle insert(QualityProblemHandle qualityProblemHandle) {
        this.qualityProblemHandleMapper.insert(qualityProblemHandle);
        return qualityProblemHandle;
    }

    /**
     * 修改数据
     *
     * @param qualityProblemHandle 实例对象
     * @return 实例对象
     */
    @Override
    public QualityProblemHandle update(QualityProblemHandle qualityProblemHandle) {
        this.qualityProblemHandleMapper.update(qualityProblemHandle);
        return this.queryById(qualityProblemHandle.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qualityProblemHandleMapper.deleteById(id) > 0;
    }
}
