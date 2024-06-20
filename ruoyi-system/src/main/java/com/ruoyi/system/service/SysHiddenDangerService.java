package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.domain.SysDanger;
import com.ruoyi.system.entity.SysHiddenDanger;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 隐患登记 服务类
 * </p>
 *
 * @author liushuai 
 * @since 2023-02-28
 */
public interface SysHiddenDangerService extends IService<SysHiddenDanger> {

    void create(SysHiddenDanger sysDanger);

    void delete(Integer id);

    void update(SysHiddenDanger sysHiddenDanger);

    List<SysHiddenDanger> queryByPage(SysHiddenDanger sysHiddenDanger);

    void updateStatus(SysHiddenDanger sysHiddenDanger);

    void updateFour(SysHiddenDanger sysHiddenDanger);

    List<Map<String, String>> exportProblem(Integer type);

    List<Map<String, String>> exportProblemZg(Integer type);

}
