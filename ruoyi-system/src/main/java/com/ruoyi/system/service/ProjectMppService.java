package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.ProjectMpp;

import java.io.File;
import java.util.List;

public interface ProjectMppService  extends IService<ProjectMpp> {

    public void readMmpFileToDB(File file);

    /**
     * 导入数据
     *
     * @param projectMppList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importProjectMpp(List<ProjectMpp> projectMppList, Boolean isUpdateSupport, String operName);

    public List<TreeSelect> queryTree(ProjectMpp projectMpp);

    public void updateProjectMpp(ProjectMpp projectMpp);

}
