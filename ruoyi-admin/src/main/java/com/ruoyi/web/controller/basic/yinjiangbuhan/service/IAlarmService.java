package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Alarm;

import java.util.List;


/**
 * 报警Service接口
 * 
 * @author liang
 * @date 2024-08-21
 */
public interface IAlarmService  extends IService<Alarm>
{
    /**
     * 查询报警
     * 
     * @param id 报警主键
     * @return 报警
     */
    public Alarm selectAlarmById(Long id);

    /**
     * 查询报警列表
     * 
     * @param alarm 报警
     * @return 报警集合
     */
    public List<Alarm> selectAlarmList(Alarm alarm);

    /**
     * 新增报警
     * 
     * @param alarm 报警
     * @return 结果
     */
    public int insertAlarm(Alarm alarm);

    /**
     * 修改报警
     * 
     * @param alarm 报警
     * @return 结果
     */
    public int updateAlarm(Alarm alarm);

    /**
     * 批量删除报警
     * 
     * @param ids 需要删除的报警主键集合
     * @return 结果
     */
    public int deleteAlarmByIds(Long[] ids);

    /**
     * 删除报警信息
     * 
     * @param id 报警主键
     * @return 结果
     */
    public int deleteAlarmById(Long id);
}
