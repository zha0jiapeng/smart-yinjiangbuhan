package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducation;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducationUser;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.AdmissionEducationMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAdmissionEducationService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAdmissionEducationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 入场三级教育Service业务层处理
 * 
 * @author mashir0
 * @date 2024-07-16
 */
@Service
public class AdmissionEducationServiceImpl extends ServiceImpl<AdmissionEducationMapper, AdmissionEducation> implements IAdmissionEducationService
{
    @Autowired
    private AdmissionEducationMapper admissionEducationMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IAdmissionEducationUserService admissionEducationUserService;
    /**
     * 查询入场三级教育
     * 
     * @param id 入场三级教育主键
     * @return 入场三级教育
     */
    @Override
    public AdmissionEducation selectAdmissionEducationById(Long id)
    {
        AdmissionEducation admissionEducation = admissionEducationMapper.selectAdmissionEducationById(id);
        List<AdmissionEducationUser> list = admissionEducationUserService.list(new LambdaQueryWrapper<AdmissionEducationUser>().eq(AdmissionEducationUser::getAdmissionEducationId, id));
        for (AdmissionEducationUser admissionEducationUser : list) {
            SysUser sysUser = userService.selectUserById(admissionEducationUser.getUserId());
            if(sysUser!=null) admissionEducationUser.setUserName(sysUser.getNickName());
        }
        admissionEducation.setAdmissionEducationUsers(list);
        return admissionEducation;
    }

    /**
     * 查询入场三级教育列表
     * 
     * @param admissionEducation 入场三级教育
     * @return 入场三级教育
     */
    @Override
    public List<AdmissionEducation> selectAdmissionEducationList(AdmissionEducation admissionEducation)
    {
        LambdaQueryWrapper<AdmissionEducation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(admissionEducation.getTrainName()),AdmissionEducation::getTrainName,admissionEducation.getTrainName());
        queryWrapper.like(StringUtils.isNotEmpty(admissionEducation.getTrainTeacherName()),AdmissionEducation::getTrainTeacherName,admissionEducation.getTrainTeacherName());
        queryWrapper.between(StringUtils.isNotEmpty(admissionEducation.getQueryDate()),AdmissionEducation::getQueryDate,admissionEducation.getTrainStartTime(),admissionEducation.getTrainEndTime());
        List<AdmissionEducation> admissionEducations = admissionEducationMapper.selectAdmissionEducationList(admissionEducation);
        for (AdmissionEducation education : admissionEducations) {
            List<AdmissionEducationUser> list = admissionEducationUserService.list(new LambdaQueryWrapper<AdmissionEducationUser>().eq(AdmissionEducationUser::getAdmissionEducationId, education.getId()));
            education.setAdmissionEducationUsers(list);
            StringBuilder userName = new StringBuilder();
            for (AdmissionEducationUser admissionEducationUser : list) {
                SysUser sysUser = userService.selectUserById(admissionEducationUser.getUserId());
                userName.append(sysUser.getNickName()).append(",");
            }
            SysDept sysDept = deptService.selectDeptById(education.getDeptId());
            if(sysDept!=null)
                education.setDeptName(sysDept.getDeptName());
            if(userName.toString().contains("\\,"))
                education.setUserNames(userName.substring(0,userName.length()-1));
        }
        return admissionEducations;
    }

    /**
     * 新增入场三级教育
     * 
     * @param admissionEducation 入场三级教育
     * @return 结果
     */
    @Override
    public int insertAdmissionEducation(AdmissionEducation admissionEducation)
    {
        admissionEducation.setCreateTime(DateUtils.getNowDate());
        int insert = admissionEducationMapper.insert(admissionEducation);
        for (AdmissionEducationUser user : admissionEducation.getAdmissionEducationUsers()) {
            AdmissionEducationUser admissionEducationUser = new AdmissionEducationUser();
            admissionEducationUser.setAdmissionEducationId(admissionEducation.getId());
            admissionEducationUser.setUserId(user.getUserId());
            admissionEducationUser.setCreateTime(DateUtils.getNowDate());
            admissionEducationUser.setUpdateTime(DateUtils.getNowDate());
            admissionEducationUserService.insertAdmissionEducationUser(admissionEducationUser);
        }
        return insert;
    }

    /**
     * 修改入场三级教育
     * 
     * @param admissionEducation 入场三级教育
     * @return 结果
     */
    @Override
    public int updateAdmissionEducation(AdmissionEducation admissionEducation)
    {
        admissionEducation.setUpdateTime(DateUtils.getNowDate());
        for (AdmissionEducationUser user : admissionEducation.getAdmissionEducationUsers()) {
            LambdaUpdateWrapper<AdmissionEducationUser> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.set(AdmissionEducationUser::getUserScore,user.getUserScore());
            queryWrapper.eq(AdmissionEducationUser::getUserId,user.getUserId());
            queryWrapper.eq(AdmissionEducationUser::getAdmissionEducationId,admissionEducation.getId());
            admissionEducationUserService.update(queryWrapper);
        }
        return admissionEducationMapper.updateAdmissionEducation(admissionEducation);
    }

    /**
     * 批量删除入场三级教育
     * 
     * @param ids 需要删除的入场三级教育主键
     * @return 结果
     */
    @Override
    public int deleteAdmissionEducationByIds(Long[] ids)
    {
        admissionEducationUserService.remove(new LambdaQueryWrapper<AdmissionEducationUser>().in(AdmissionEducationUser::getAdmissionEducationId, ids));
        return admissionEducationMapper.deleteAdmissionEducationByIds(ids);
    }

    /**
     * 删除入场三级教育信息
     * 
     * @param id 入场三级教育主键
     * @return 结果
     */
    @Override
    public int deleteAdmissionEducationById(Long id)
    {
        admissionEducationUserService.remove(new LambdaQueryWrapper<AdmissionEducationUser>().eq(AdmissionEducationUser::getAdmissionEducationId, id));
        return admissionEducationMapper.deleteAdmissionEducationById(id);
    }
}
