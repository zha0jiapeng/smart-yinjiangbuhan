package com.ruoyi.web.controller.basic.yinjiangbuhan.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Staff {
    @ExcelProperty("姓名")
    private String staffName;

    @ExcelProperty("有效身份证号")
    private String idCardNo;

    @ExcelProperty("身份证有效期限")
    private String idExpireDate;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("民族")
    private String nation;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("工程组织")
    private String orgId;

    @ExcelProperty("组织部门")
    private String orgDept;

    @ExcelProperty("人员类型")
    private String staffType;

    @ExcelProperty("行政职务")
    private String position;

    @ExcelProperty("项目岗位")
    private String postCode;

    @ExcelProperty("工种")
    private String workerType;

    @ExcelProperty("专业")
    private String major;

    @ExcelProperty("准入门禁等级")
    private String accessLevel;

    @ExcelProperty("劳务分包公司")
    private String laborSubCom;

    @ExcelProperty("用工状态")
    private String workStatus;

    @ExcelProperty("进场时间")
    private String comeIn;

    @ExcelProperty("出场时间")
    private String comeOut;

    @ExcelProperty("家庭地址")
    private String adress;

    @ExcelProperty("紧急联系人")
    private String emergencyName;

    @ExcelProperty("紧急联系人电话")
    private String emergencyPhone;

    @ExcelProperty("是否特种作业人员")
    private String specialWorker;

    @ExcelProperty("人员照片")
    private String photoUrl;

    @ExcelProperty("人员照片base64")
    private String photoBase64;
}
