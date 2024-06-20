package com.ruoyi.system.service;

import com.ruoyi.system.domain.vo.PreventionSecurityRiskVO;
import com.ruoyi.system.entity.PreventionSecurityRisk;

import java.util.List;

/**
 * 双重预防-风险分析清单明细(PreventionSecurityRisk)表服务接口
 *
 * @author makejava
 * @since 2022-11-17 11:18:54
 */
public interface PreventionSecurityRiskService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PreventionSecurityRisk queryById(Integer id);

    /**
     * 分页查询
     *
     * @param preventionSecurityRisk 筛选条件
     * @return 查询结果
     */
    List<PreventionSecurityRisk> queryByPage(PreventionSecurityRisk preventionSecurityRisk);

    /**
     * 新增数据
     *
     * @param preventionSecurityRisk 实例对象
     * @return 实例对象
     */
    PreventionSecurityRisk insert(PreventionSecurityRisk preventionSecurityRisk);

    /**
     * 修改数据
     *
     * @param preventionSecurityRisk 实例对象
     * @return 实例对象
     */
    PreventionSecurityRisk update(PreventionSecurityRisk preventionSecurityRisk);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    Integer countUnit(Integer id);

    Integer countEvent(Integer id);

    Integer countControl(Integer id);

    Integer countTask(Integer id);

    List<PreventionSecurityRisk> queryAll(PreventionSecurityRisk preventionSecurityRisk);

    String importExcel(List<PreventionSecurityRiskVO> preventionSecurityRisks, Boolean isUpdateSupport, String operName);

    List<PreventionSecurityRiskVO> queryAllVO(PreventionSecurityRiskVO preventionSecurityRisk);
}
