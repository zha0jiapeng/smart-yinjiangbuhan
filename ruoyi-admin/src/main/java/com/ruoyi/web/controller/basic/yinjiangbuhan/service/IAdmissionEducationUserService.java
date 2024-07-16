package com.ruoyi.web.controller.basic.yinjiangbuhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducationUser;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.Device;

import java.util.List;

/**
 * 入场三级教育用户Service接口
 * 
 * @author mashir0
 * @date 2024-07-16
 */
public interface IAdmissionEducationUserService  extends IService<AdmissionEducationUser>
{
    /**
     * 查询入场三级教育用户
     * 
     * @param id 入场三级教育用户主键
     * @return 入场三级教育用户
     */
    public AdmissionEducationUser selectAdmissionEducationUserById(Long id);

    /**
     * 查询入场三级教育用户列表
     * 
     * @param admissionEducationUser 入场三级教育用户
     * @return 入场三级教育用户集合
     */
    public List<AdmissionEducationUser> selectAdmissionEducationUserList(AdmissionEducationUser admissionEducationUser);

    /**
     * 新增入场三级教育用户
     * 
     * @param admissionEducationUser 入场三级教育用户
     * @return 结果
     */
    public int insertAdmissionEducationUser(AdmissionEducationUser admissionEducationUser);

    /**
     * 修改入场三级教育用户
     * 
     * @param admissionEducationUser 入场三级教育用户
     * @return 结果
     */
    public int updateAdmissionEducationUser(AdmissionEducationUser admissionEducationUser);

    /**
     * 批量删除入场三级教育用户
     * 
     * @param ids 需要删除的入场三级教育用户主键集合
     * @return 结果
     */
    public int deleteAdmissionEducationUserByIds(Long[] ids);

    /**
     * 删除入场三级教育用户信息
     * 
     * @param id 入场三级教育用户主键
     * @return 结果
     */
    public int deleteAdmissionEducationUserById(Long id);
}
