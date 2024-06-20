package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PersonnelLinkage {
    private Integer id;
    /**
     * 1:临时访客
     * 2:现场工人
     */
    private Integer type;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 工种
     */
    private String typeWork;
    /**
     * 班组
     */
    private String groups;
    /**
     * 公司
     */
    private String company;
    /**
     * 进场时间
     */
    private String inDate;
    /**
     * 人脸识别图片
     */
    private String images;
    /**
     * 来访事由
     */
    private String subjectMatter;
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
    // 家庭联系人电话
    @TableField(exist = false)
    private String homePhone;
    // 进场时间
    @TableField(exist = false)
    private String jcDate;
    // 退场时间
    @TableField(exist = false)
    private String tcDate;

    private List<Examination> examinationList;

    private List<KaoQin> kaoQinList;

    @Data
    public static class Examination {
        private String paper;

        private Integer achievement;

        private Date answerDate;
    }

    @Data
    public static class KaoQin {

        private String way;

        private String phone;

        private String dateTime;

        private String lastDate;
    }

}
