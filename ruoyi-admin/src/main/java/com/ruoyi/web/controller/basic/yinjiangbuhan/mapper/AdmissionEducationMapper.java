package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducation;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducationUser;

import java.util.List;

/**
 * 入场三级教育Mapper接口
 * 
 * @author mashir0
 * @date 2024-07-16
 */
public interface AdmissionEducationMapper  extends BaseMapper<AdmissionEducation>
{
    /**
     * 查询入场三级教育
     * 
     * @param id 入场三级教育主键
     * @return 入场三级教育
     */
    public AdmissionEducation selectAdmissionEducationById(Long id);

    /**
     * 查询入场三级教育列表
     * 
     * @param admissionEducation 入场三级教育
     * @return 入场三级教育集合
     */
    public List<AdmissionEducation> selectAdmissionEducationList(AdmissionEducation admissionEducation);

    /**
     * 新增入场三级教育
     * 
     * @param admissionEducation 入场三级教育
     * @return 结果
     */
    public int insertAdmissionEducation(AdmissionEducation admissionEducation);

    /**
     * 修改入场三级教育
     * 
     * @param admissionEducation 入场三级教育
     * @return 结果
     */
    public int updateAdmissionEducation(AdmissionEducation admissionEducation);

    /**
     * 删除入场三级教育
     * 
     * @param id 入场三级教育主键
     * @return 结果
     */
    public int deleteAdmissionEducationById(Long id);

    /**
     * 批量删除入场三级教育
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAdmissionEducationByIds(Long[] ids);
}
