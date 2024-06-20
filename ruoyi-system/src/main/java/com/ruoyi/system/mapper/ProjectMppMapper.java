package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.ProjectMpp;

public interface ProjectMppMapper extends BaseMapper<ProjectMpp> {

    /**
     * 插入project数据
     * @param project
     * @return
     */
    int addProjectSelective(ProjectMpp project);

    void updateProjectMpp(ProjectMpp projectMpp);
}
