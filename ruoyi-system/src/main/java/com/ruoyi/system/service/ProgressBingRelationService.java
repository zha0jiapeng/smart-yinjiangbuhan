package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.ProgressBingRelation;

import java.util.List;

public interface ProgressBingRelationService extends IService<ProgressBingRelation> {
    /**
     * 查询项目与模型绑定列表
     *
     * @param request 参数
     * @return 结果
     */
    List<ProgressBingRelation> selectList(ProgressBingRelation request);
}
