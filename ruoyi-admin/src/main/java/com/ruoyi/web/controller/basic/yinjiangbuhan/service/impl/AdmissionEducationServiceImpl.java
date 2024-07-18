package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducation;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.AdmissionEducationUser;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.AdmissionEducationMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAdmissionEducationService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IAdmissionEducationUserService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private SysWorkPeopleService sysWorkPeopleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IAdmissionEducationUserService admissionEducationUserService;

    @Resource
    SwzkHttpUtils swzkHttpUtils;
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
            SysWorkPeople workPeople = sysWorkPeopleService.getById(admissionEducationUser.getUserId());
            if(workPeople!=null) admissionEducationUser.setUserName(workPeople.getName());
        }
        SysDept sysDept = deptService.selectDeptById(admissionEducation.getDeptId());
        if(sysDept!=null) {
            admissionEducation.setDeptName(sysDept.getDeptName());
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
        List<AdmissionEducation> admissionEducations = list(queryWrapper);
        for (AdmissionEducation education : admissionEducations) {
            List<AdmissionEducationUser> list = admissionEducationUserService.list(new LambdaQueryWrapper<AdmissionEducationUser>().eq(AdmissionEducationUser::getAdmissionEducationId, education.getId()));
            education.setAdmissionEducationUsers(list);
            StringBuilder userName = new StringBuilder();
            for (AdmissionEducationUser admissionEducationUser : list) {
                SysWorkPeople workPeople = sysWorkPeopleService.getById(admissionEducationUser.getUserId());
                if(workPeople!=null)   userName.append(workPeople.getName()).append(",");
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
        pushSwzk(admissionEducation);
        return admissionEducationMapper.updateAdmissionEducation(admissionEducation);
    }

    private void pushSwzk(AdmissionEducation admissionEducation) {

        // Root map
        Map<String, Object> root = new HashMap<>();
        root.put("deviceType", "2001000100");
        root.put("SN", "ED04024717001");
        root.put("dataType", "200300100");
        root.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        root.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        root.put("deviceName", "安全教育培训");

        // List of values
        List<Map<String, Object>> values = new ArrayList<>();

        // Value map
        Map<String, Object> value = new HashMap<>();
        value.put("reportTs", DateUtil.current());

        // Profile map
        Map<String, Object> profile = new HashMap<>();
        profile.put("appType", "education");
        profile.put("modelId", "2078");
        profile.put("poiCode", "w23090601");
        profile.put("name", "安全教育培训");
        value.put("profile", profile);

        // Properties map
        Map<String, Object> properties = new HashMap<>();
        value.put("properties", properties);

        // Events map
        Map<String, Object> events = new HashMap<>();
        events.put("number", admissionEducation.getId()+"");
        events.put("type", "three");
        events.put("startTime", DateUtil.formatDateTime(admissionEducation.getTrainStartTime()));
        events.put("endTime",DateUtil.formatDateTime(admissionEducation.getTrainEndTime()));
        events.put("organization", "中铁十八局土建四标引江补汉项目部");
        events.put("dept", admissionEducation.getDeptName());
        events.put("title", admissionEducation.getTrainName());
        events.put("teacher", admissionEducation.getTrainTeacherName());
        events.put("location", "项目部");
        events.put("content", admissionEducation.getTrainContent());

        // Exam list
        List<Map<String, Object>> examList = new ArrayList<>();
        List<AdmissionEducationUser> admissionEducationUsers = admissionEducation.getAdmissionEducationUsers();
        for (AdmissionEducationUser admissionEducationUser : admissionEducationUsers) {
            Map<String, Object> exam1 = new HashMap<>();
            exam1.put("name", admissionEducationUser.getUserName());
            exam1.put("grade", admissionEducationUser.getUserScore());
            SysWorkPeople byId = sysWorkPeopleService.getById(admissionEducationUser.getUserId());
            if(byId!=null) {
                exam1.put("idcard", byId.getIdCard());
            }
            exam1.put("isPass", admissionEducationUser.getUserScore()>=80?1:0);
            examList.add(exam1);
        }


        events.put("exam", examList);
        value.put("events", events);

        // Services map
        Map<String, Object> services = new HashMap<>();
        value.put("services", services);

        values.add(value);
        root.put("values", values);

        swzkHttpUtils.pushIOT(root);
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
