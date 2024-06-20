package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.QualityTemplate;
import com.ruoyi.system.mapper.QualityTemplateMapper;
import com.ruoyi.system.service.QualityTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 质量模板表(QualityTemplate)表服务实现类
 *
 * @author makejava
 * @since 2022-12-25 14:48:58
 */
@Service("qualityTemplateService")
public class QualityTemplateServiceImpl implements QualityTemplateService {
    @Resource
    private QualityTemplateMapper qualityTemplateMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QualityTemplate queryById(Integer id) {
        return this.qualityTemplateMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param qualityTemplate 筛选条件
     * @return 查询结果
     */
    @Override
    public List<QualityTemplate> queryByPage(QualityTemplate qualityTemplate) {
        List<QualityTemplate> qualityTemplates = this.qualityTemplateMapper.queryAllByLimit(qualityTemplate);
        return qualityTemplates;
    }

    /**
     * 新增数据
     *
     * @param qualityTemplate 实例对象
     * @return 实例对象
     */
    @Override
    public QualityTemplate insert(QualityTemplate qualityTemplate) {
        this.qualityTemplateMapper.insert(qualityTemplate);
        return qualityTemplate;
    }

    /**
     * 修改数据
     *
     * @param qualityTemplate 实例对象
     * @return 实例对象
     */
    @Override
    public QualityTemplate update(QualityTemplate qualityTemplate) {
        this.qualityTemplateMapper.update(qualityTemplate);
        return this.queryById(qualityTemplate.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qualityTemplateMapper.deleteById(id) > 0;
    }
}
