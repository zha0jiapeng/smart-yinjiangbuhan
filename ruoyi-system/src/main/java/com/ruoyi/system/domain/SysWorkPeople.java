package com.ruoyi.system.domain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.system.domain.basic.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_work_people")
public class SysWorkPeople extends BaseDomain {

    @TableId
    private Integer id;
    // 姓名
    private String name;
    // 性别
    private Integer sex;
    // 年龄
    @TableField(exist = false)
    private Integer age;
    // 汉族
    @TableField(exist = false)
    private String hanzu;
    // 文化程度
    @TableField(exist = false)
    private String whcd;
    // 从业时间
    @TableField(exist = false)
    private String cyDate;
    // 身份证号
    private String idCard;
    // 家庭住址
    @TableField(exist = false)
    private String area;
    // 联系电话
    private String phone;
    // 家庭联系人电话
    @TableField(exist = false)
    private String homePhone;
    // 进场时间
    @TableField(exist = false)
    private String jcDate;
    // 退场时间
    @TableField(exist = false)
    private String tcDate;
    //工种
    private String workType;
    //公司
    private String company;
    //设备号
    private String cradNumber;
    //血型
    private String bloodType;
    //部门
    private Integer deptId;
    //部门名称
    private String deptName;
    /**
     * 班组
     */
    private String groupsName;
    /**
     * 积分
     */
    private BigDecimal integral;
    /**
     * 人员类型
     */
    private Integer personnelConfigType;
    /**
     * 页面名称
     */
    private String enclosureName;
    /**
     * 二维码
     */
    private String qrCode;

    //教育形式
    private String educationForm;

    //教育内容
    private String educationalContent;

    //最后考试试卷
    private String lastExamTestPaper;

    //最后考试分数
    private String lastExamScore;

    //最后考试时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastExamTime;

    //最后学习文件名称
    private String lastFileName;

    //学习文件总时长
    private String learnFileTime;

    //最后观看视频名称
    private String lastVideoName;

    //观看视频总时长
    private String watchVideoTime;

    //离场时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureDate;

    private Integer sort;

    //是否重点人员
    private Integer keyPersonnelFlag;

    @TableField(exist = false)
    private Integer appCase;

    @TableField(exist = false)
    private List<AmassDetails> amassDetails;

    @Data
    public static class AmassDetails {
        /**
         * 积分
         */
        private BigDecimal integral;
        /**
         * 积分修改日期
         */
        private Date amassUpdDate;
        /**
         * 备注
         */
        private String remarks;
        /**
         * 创建人
         */
        private String createdBy;
        /**
         * 创建时间
         */
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdDate;
        /**
         * 更新人
         */
        private String modifyBy;
        /**
         * 更新时间
         */
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date modifyDate;
    }
}
