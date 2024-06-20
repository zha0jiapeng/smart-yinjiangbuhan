package com.ruoyi.system.service;

import com.ruoyi.system.entity.QualityTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 质量模板表(QualityTemplate)表服务接口
 *
 * @author makejava
 * @since 2022-12-25 14:48:58
 */
public interface QualityTemplateService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QualityTemplate queryById(Integer id);

    /**
     * 分页查询
     *
     * @param qualityTemplate 筛选条件
     * @return 查询结果
     */
    List<QualityTemplate> queryByPage(QualityTemplate qualityTemplate);

    /**
     * 新增数据
     *
     * @param qualityTemplate 实例对象
     * @return 实例对象
     */
    QualityTemplate insert(QualityTemplate qualityTemplate);

    /**
     * 修改数据
     *
     * @param qualityTemplate 实例对象
     * @return 实例对象
     */
    QualityTemplate update(QualityTemplate qualityTemplate);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
