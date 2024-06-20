package com.ruoyi.system.entity;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * 人员信息表(SysPersonnel)实体类
 */
@Data
public class SysPersonnel implements Serializable {
    private static final long serialVersionUID = -71904707062420156L;
    
    private Integer id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 性别（1-男 2-女）
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 公司名称
     */
    private String corporate;
    /**
     * 身份证号
     */
    private String uniqueNumber;
    /**
     * 电话号
     */
    private String phone;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 工种
     */
    private String jobType;
    /**
     * 工班
     */
    private String jobWork;
    /**
     * 进场时间
     */
    private String approachTime;


    private String approachTimeStart;

    private String approachTimeEnd;


    /**
     * 安全培训记录名称
     */
    private String safetyTrainingName;
    /**
     * 安全培训记录ID
     */
    private Integer safetyTrainingId;
    /**
     * 签字照片文件名称
     */
    private String signFileName;
    /**
     * 签字照片文件路径
     */
    private String signFileUrl;
    
    private Date createTime;
    
    private Date updateTime;

    private String matter;

    /**
     * 进出 1:进 2:出
     */
    private Integer status;

    private Integer approveStatus;

    // 年
    private String yearCase;
    // 月
    private String monthCase;
    // 日
    private String dayCase;
    // 周
    private String weekCase;
    // 小时
    private String hourCase;

}






