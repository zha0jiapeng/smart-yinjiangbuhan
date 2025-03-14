package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.EmployeeCertificates;

import java.util.List;

/**
 * 人员证照Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-10
 */
public interface EmployeeCertificatesMapper extends BaseMapper<EmployeeCertificates>
{
    /**
     * 查询人员证照
     * 
     * @param id 人员证照主键
     * @return 人员证照
     */
    public EmployeeCertificates selectEmployeeCertificatesById(Long id);

    /**
     * 查询人员证照列表
     * 
     * @param employeeCertificates 人员证照
     * @return 人员证照集合
     */
    public List<EmployeeCertificates> selectEmployeeCertificatesList(EmployeeCertificates employeeCertificates);

    /**
     * 新增人员证照
     * 
     * @param employeeCertificates 人员证照
     * @return 结果
     */
    public int insertEmployeeCertificates(EmployeeCertificates employeeCertificates);

    /**
     * 修改人员证照
     * 
     * @param employeeCertificates 人员证照
     * @return 结果
     */
    public int updateEmployeeCertificates(EmployeeCertificates employeeCertificates);

    /**
     * 删除人员证照
     * 
     * @param id 人员证照主键
     * @return 结果
     */
    public int deleteEmployeeCertificatesById(Long id);

    /**
     * 批量删除人员证照
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmployeeCertificatesByIds(Long[] ids);
}
