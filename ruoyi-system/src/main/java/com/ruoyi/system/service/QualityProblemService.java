package com.ruoyi.system.service;

import com.ruoyi.system.entity.QualityProblem;
import com.ruoyi.system.entity.QualityTesting;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * (QualityProblem)表服务接口
 *
 * @author makejava
 * @since 2022-12-26 16:03:29
 */
public interface QualityProblemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QualityProblem queryById(Integer id);

    /**
     * 分页查询
     *
     * @param qualityProblem 筛选条件
     * @return 查询结果
     */
    List<QualityProblem> queryByPage(QualityProblem qualityProblem);

    /**
     * 新增数据
     *
     * @param qualityProblem 实例对象
     * @return 实例对象
     */
    QualityProblem insert(QualityProblem qualityProblem);

    /**
     * 修改数据
     *
     * @param qualityProblem 实例对象
     * @return 实例对象
     */
    QualityProblem update(QualityProblem qualityProblem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    String updateOpinion(QualityProblem qualityProblem);

    String updateResult(QualityProblem qualityProblem);

    String updateProgress(QualityProblem qualityProblem);

    Map<String, Object> countAll();

    List<QualityProblem> queryByPageAndActing(QualityProblem qualityProblem);

}
