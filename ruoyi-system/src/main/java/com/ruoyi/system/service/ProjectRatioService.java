package com.ruoyi.system.service;
import com.ruoyi.system.entity.ProjectRatio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;

/**
 * 三维_累计比例(ProjectRatio)表服务接口
 * @since 2023-06-07 17:55:25
 */
public interface ProjectRatioService {

    /**
     * 通过ID查询单条数据
     */
    ProjectRatio queryById(Integer id);

    /**
     * 分页查询
     */
    List<ProjectRatio> queryByPage(ProjectRatio projectRatio);

    /**
     * 新增数据
     */
    ProjectRatio insert(ProjectRatio projectRatio);

    /**
     * 修改数据
     */
    ProjectRatio update(ProjectRatio projectRatio);

    ProjectRatio updateBase(ProjectRatio projectRatio);

    /**
     * 通过主键删除数据
     */
    boolean deleteById(Integer id);

}
