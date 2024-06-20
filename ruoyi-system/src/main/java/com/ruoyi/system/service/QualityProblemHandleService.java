package com.ruoyi.system.service;

import com.ruoyi.system.entity.QualityProblemHandle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (QualityProblemHandle)表服务接口
 *
 * @author makejava
 * @since 2023-01-19 17:08:13
 */
public interface QualityProblemHandleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QualityProblemHandle queryById(Integer id);
    /**
     * 新增数据
     *
     * @param qualityProblemHandle 实例对象
     * @return 实例对象
     */
    QualityProblemHandle insert(QualityProblemHandle qualityProblemHandle);

    /**
     * 修改数据
     *
     * @param qualityProblemHandle 实例对象
     * @return 实例对象
     */
    QualityProblemHandle update(QualityProblemHandle qualityProblemHandle);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
