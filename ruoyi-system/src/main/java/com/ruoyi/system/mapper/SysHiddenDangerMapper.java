package com.ruoyi.system.mapper;
import com.ruoyi.system.entity.SysHiddenDanger;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * <p>
 * 隐患登记 Mapper 接口
 * </p>
 *
 * @author liushuai 
 * @since 2023-02-28
 */
public interface SysHiddenDangerMapper extends BaseMapper<SysHiddenDanger> {

    void create(SysHiddenDanger sysHiddenDanger);

    List<SysHiddenDanger> list(SysHiddenDanger sysHiddenDanger);

    void delete(Integer id);

    void update(SysHiddenDanger sysHiddenDanger);

    void updateStatus(SysHiddenDanger sysHiddenDanger);

    void updateFour(SysHiddenDanger sysHiddenDanger);
}
