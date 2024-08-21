package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SysConstructionProgressLog;

import java.util.List;
import java.util.Map;

/**
 * 施工日志Service接口
 * 
 * @author mashir0
 * @date 2024-08-20
 */
public interface ISysConstructionProgressLogService extends IService<SysConstructionProgressLog>
{
    /**
     * 查询施工日志
     * 
     * @param id 施工日志主键
     * @return 施工日志
     */
    public SysConstructionProgressLog selectSysConstructionProgressLogById(Long id);

    /**
     * 查询施工日志列表
     * 
     * @param sysConstructionProgressLog 施工日志
     * @return 施工日志集合
     */
    public List<SysConstructionProgressLog> selectSysConstructionProgressLogList(SysConstructionProgressLog sysConstructionProgressLog);

    /**
     * 新增施工日志
     * 
     * @param sysConstructionProgressLog 施工日志
     * @return 结果
     */
    public int insertSysConstructionProgressLog(SysConstructionProgressLog sysConstructionProgressLog);

    /**
     * 修改施工日志
     * 
     * @param sysConstructionProgressLog 施工日志
     * @return 结果
     */
    public int updateSysConstructionProgressLog(SysConstructionProgressLog sysConstructionProgressLog);

    /**
     * 批量删除施工日志
     * 
     * @param ids 需要删除的施工日志主键集合
     * @return 结果
     */
    public int deleteSysConstructionProgressLogByIds(Long[] ids);

    /**
     * 删除施工日志信息
     * 
     * @param id 施工日志主键
     * @return 结果
     */
    public int deleteSysConstructionProgressLogById(Long id);

    List<Map<String, Object>> getSum(int type,String text);

    List<Map<String, Object>> getCurve(Integer type, String text);
}
