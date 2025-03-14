package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.EmployeeCertificates;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.EmployeeCertificatesMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IEmployeeCertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 人员证照Service业务层处理
 *
 * @author ruoyi
 * @date 2025-03-10
 */
@Service
public class EmployeeCertificatesServiceImpl extends ServiceImpl<EmployeeCertificatesMapper, EmployeeCertificates> implements IEmployeeCertificatesService {
    @Autowired(required = false)
    private EmployeeCertificatesMapper employeeCertificatesMapper;

    @Resource
    private SysWorkPeopleService workPeopleService;


    /**
     * 查询人员证照
     *
     * @param id 人员证照主键
     * @return 人员证照
     */
    @Override
    public EmployeeCertificates selectEmployeeCertificatesById(Long id) {
        return employeeCertificatesMapper.selectById(id);
    }

    /**
     * 查询人员证照列表
     *
     * @param employeeCertificates 人员证照
     * @return 人员证照
     */
    @Override
    public List<EmployeeCertificates> selectEmployeeCertificatesList(EmployeeCertificates employeeCertificates) {
        return employeeCertificatesMapper.selectList(new LambdaQueryWrapper<>(employeeCertificates));
    }

    /**
     * 新增人员证照
     *
     * @param employeeCertificates 人员证照
     * @return 结果
     */
    @Override
    public int insertEmployeeCertificates(EmployeeCertificates employeeCertificates) {
        LambdaQueryWrapper<SysWorkPeople> employeeCertificatesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeCertificatesLambdaQueryWrapper.eq(SysWorkPeople::getId, employeeCertificates.getSysWorkPeopleId());
        SysWorkPeople sysWorkPeople = workPeopleService.getOne(employeeCertificatesLambdaQueryWrapper);
        employeeCertificates.setPhone(sysWorkPeople.getPhone());
        employeeCertificates.setUserName(sysWorkPeople.getName());
        employeeCertificates.setCompany(sysWorkPeople.getCompany());
        employeeCertificates.setWorkType(sysWorkPeople.getWorkType());
        employeeCertificates.setIdCard(sysWorkPeople.getIdCard());
        employeeCertificates.setCreateTime(DateUtils.getNowDate());
        return employeeCertificatesMapper.insert(employeeCertificates);
    }

    /**
     * 修改人员证照
     *
     * @param employeeCertificates 人员证照
     * @return 结果
     */
    @Override
    public int updateEmployeeCertificates(EmployeeCertificates employeeCertificates) {
        employeeCertificates.setUpdateTime(DateUtils.getNowDate());
        return employeeCertificatesMapper.updateById(employeeCertificates);
    }

    /**
     * 批量删除人员证照
     *
     * @param ids 需要删除的人员证照主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeCertificatesByIds(Long[] ids) {
        return employeeCertificatesMapper.deleteEmployeeCertificatesByIds(ids);
    }

    /**
     * 删除人员证照信息
     *
     * @param id 人员证照主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeCertificatesById(Long id) {
        return employeeCertificatesMapper.deleteEmployeeCertificatesById(id);
    }



    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 计算即将过期和已过期的证书数量
     *
     * @return JSONObject，包含即将过期和已过期的数量
     */
    @Override
    public JSONObject calculateDueAndOverdueCount() {
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesMapper.selectList(new LambdaQueryWrapper<>(new EmployeeCertificates()));

        int dueCount = 0; // 即将过期的数量
        int overdueCount = 0; // 已过期的数量

        for (EmployeeCertificates employeeCertificates : employeeCertificatesList) {
            String expiryDate = employeeCertificates.getExpiryDate();
            if (expiryDate == null) {
                continue; // 如果过期日期为空，跳过
            }

            LocalDate expiryLocalDate = parseDate(expiryDate);
            if (expiryLocalDate == null) {
                continue; // 如果日期解析失败，跳过
            }

            if (isExpired(expiryLocalDate)) {
                overdueCount++; // 已过期
            } else if (isCloseToExpiry(expiryLocalDate, 15)) {
                dueCount++; // 即将过期
            }
        }

        // 返回统计结果
        JSONObject result = new JSONObject();
        result.put("dueCount", dueCount);
        result.put("overdueCount", overdueCount);
        return result;
    }

    /**
     * 判断日期是否已过期
     *
     * @param expiryLocalDate 过期日期
     * @return true：已过期；false：未过期
     */
    private boolean isExpired(LocalDate expiryLocalDate) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(expiryLocalDate);
    }

    /**
     * 判断日期是否接近过期时间
     *
     * @param expiryLocalDate 过期日期
     * @param days           接近的天数阈值（例如 15 天）
     * @return true：接近过期时间；false：未接近过期时间
     */
    private boolean isCloseToExpiry(LocalDate expiryLocalDate, int days) {
        LocalDate currentDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(currentDate, expiryLocalDate);
        return daysBetween <= days && daysBetween >= 0;
    }

    /**
     * 将字符串解析为 LocalDate
     *
     * @param dateStr 日期字符串，格式为 yyyy-MM-dd
     * @return LocalDate 对象，如果解析失败则返回 null
     */
    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (Exception e) {
            // 日志记录或处理解析失败的情况
            System.err.println("日期解析失败: " + dateStr);
            return null;
        }
    }


}
