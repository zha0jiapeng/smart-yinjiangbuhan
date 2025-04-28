package com.ruoyi.web.controller.basic.yinjiangbuhan.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.ApprovedOperationItems;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.EmployeeCertificates;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.MigrantWorkerPayroll;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SpecialOperationTypes;
import com.ruoyi.web.controller.basic.yinjiangbuhan.mapper.EmployeeCertificatesMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IApprovedOperationItemsService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.IEmployeeCertificatesService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.service.ISpecialOperationTypesService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
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


    @Resource
    SwzkHttpUtils swzkHttpUtils;


    @Autowired
    private IApprovedOperationItemsService approvedOperationItemsService;

    @Autowired
    private ISpecialOperationTypesService specialOperationTypesService;

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
        // 查询 EmployeeCertificates 列表
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesMapper.selectList(new LambdaQueryWrapper<>(employeeCertificates));

        // 提取所有 operationItem 值
        List<String> operationItems = employeeCertificatesList.stream()
                .map(EmployeeCertificates::getOperationItem)
                .collect(Collectors.toList());

        // 批量查询 ApprovedOperationItems
        Map<String, String> projectCodeToProjectMap = approvedOperationItemsService.list(new QueryWrapper<ApprovedOperationItems>()
                        .in("project_code", operationItems))
                .stream()
                .collect(Collectors.toMap(ApprovedOperationItems::getProjectCode, ApprovedOperationItems::getProject));

        // 设置 operationItem
        employeeCertificatesList.forEach(certificates -> {
            String project = projectCodeToProjectMap.get(certificates.getOperationItem());
            if (project != null) {
                certificates.setOperationItemName(project);
            }
        });


        List<String> workTypes = employeeCertificatesList.stream()
                .map(EmployeeCertificates::getWorkType)
                .collect(Collectors.toList());


        Map<String, String> assignmentCodeToJobTypeMap = specialOperationTypesService.list(new QueryWrapper<SpecialOperationTypes>()
                        .in("assignment_code", workTypes))
                .stream()
                .collect(Collectors.toMap(SpecialOperationTypes::getAssignmentCode, SpecialOperationTypes::getJobType));

        employeeCertificatesList.forEach(certificates -> {
            String workType = assignmentCodeToJobTypeMap.get(certificates.getWorkType());
            if (workType != null) {
                certificates.setWorkTypeName(workType);
            }
        });

        return employeeCertificatesList;
    }

    /**
     * 新增人员证照
     *
     * @param employeeCertificates 人员证照
     * @return 结果
     */
    @Override
    public int insertEmployeeCertificates(EmployeeCertificates employeeCertificates) {

        String url = employeeCertificates.getAttachmentUrl();
        try {
            // 调用方法获取图片的 Base64 编码和后缀
            Map<String, String> result = getImageBase64AndExtension(url);
            if (!result.isEmpty()) {
                employeeCertificates.setFileSuffix(result.get("extension"));
                employeeCertificates.setPhotoBase64(result.get("base64"));
            } else {
                System.out.println("Failed to retrieve the image.");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        //推送水网智科
        pushSWZK(employeeCertificates);

        employeeCertificates.setPhotoBase64("");

        employeeCertificates.setCreateTime(DateUtils.getNowDate());
        return employeeCertificatesMapper.insert(employeeCertificates);
    }


    @Override
    public void pushList() {
        List<EmployeeCertificates> employeeCertificatesList = employeeCertificatesMapper.selectList(new LambdaQueryWrapper<>(new EmployeeCertificates()));
        for (EmployeeCertificates employeeCertificates : employeeCertificatesList) {
            String url = employeeCertificates.getAttachmentUrl();
            try {
                // 调用方法获取图片的 Base64 编码和后缀
                Map<String, String> result = getImageBase64AndExtension(url);
                if (!result.isEmpty()) {
                    employeeCertificates.setFileSuffix(result.get("extension"));
                    employeeCertificates.setPhotoBase64(result.get("base64"));
                } else {
                    System.out.println("Failed to retrieve the image.");
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }

            //推送水网智科
            pushSWZK(employeeCertificates);
        }
    }


    public Map<String, String> getImageBase64AndExtension(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 检查响应码
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to fetch image: HTTP " + connection.getResponseCode());
        }

        // 读取图片数据
        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 将图片数据转换为 Base64
            byte[] imageBytes = outputStream.toByteArray();
            String base64 = Base64.getEncoder().encodeToString(imageBytes);

            // 获取图片后缀
            String extension = imageUrl.substring(imageUrl.lastIndexOf('.') + 1).toLowerCase();

            // 将结果存入 Map
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("base64", base64);
            resultMap.put("extension", extension);

            return resultMap;
        } finally {
            connection.disconnect();
        }
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
     * @param days            接近的天数阈值（例如 15 天）
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


    public void pushSWZK(EmployeeCertificates employeeCertificates) {
        // 创建根 Map
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("deviceType", "2001000103");
        rootMap.put("SN", "TZ01024719004");
        rootMap.put("dataType", "200100034");
        rootMap.put("wordAreaCode", "");
        rootMap.put("workSurface", "");
        rootMap.put("deviceName", "施工四标智慧工地系统");
        rootMap.put("managementDept", "");

        // 创建 values 列表
        List<Map<String, Object>> values = new ArrayList<>();

        // 创建 values 中的第一个对象
        Map<String, Object> value = new HashMap<>();
        value.put("reportTs", DateUtil.current());

        // 创建 properties 对象
        Map<String, Object> properties = new HashMap<>();

        // 创建 staff 列表
        List<Map<String, Object>> staff = new ArrayList<>();

        // 创建 staff 中的第一个对象
        Map<String, Object> staffMember = new HashMap<>();
        staffMember.put("idCardNo", employeeCertificates.getIdCard());
        staffMember.put("validDate", employeeCertificates.getEffectiveDate()); // 生效日期
        staffMember.put("invalidDate", employeeCertificates.getExpiryDate()); // 过期日期
        staffMember.put("lisenceName", employeeCertificates.getCertificateName()); // 证书名称
        staffMember.put("workType", employeeCertificates.getWorkType()); // 特种作业类型,见数据字典：1.11
        staffMember.put("operationItem", employeeCertificates.getOperationItem()); // 准操项目，见数据字典：1.12
        staffMember.put("licenseNo", employeeCertificates.getCertificateNumber()); // 证书编号
        staffMember.put("url", employeeCertificates.getAttachmentUrl()); // 证书地址
        staffMember.put("photoBase64", employeeCertificates.getPhotoBase64()); // 证书base64
        staffMember.put("fileSuffix", employeeCertificates.getFileSuffix()); // 证书文件后缀: 若fileBase64填了，必填
        staffMember.put("issuingAuthority", employeeCertificates.getIssuingAuthority()); // 发证机构
        staffMember.put("issuingDate", employeeCertificates.getIssuingDate()); // 发证日期
        staffMember.put("reCheckOneDate", employeeCertificates.getReCheckOneDate()); // 一次复审日期
        staffMember.put("reCheckTwoDate", employeeCertificates.getReCheckTwoDate()); // 二次复审日期

        // 将 staffMember 添加到 staff 列表
        staff.add(staffMember);

        // 将 staff 列表添加到 properties 对象
        properties.put("staff", staff);

        // 将 properties 对象添加到 value 对象
        value.put("properties", properties);

        // 将 value 对象添加到 values 列表
        values.add(value);

        // 将 values 列表添加到根对象
        rootMap.put("values", values);


        swzkHttpUtils.pushIOT(rootMap);
    }


}
