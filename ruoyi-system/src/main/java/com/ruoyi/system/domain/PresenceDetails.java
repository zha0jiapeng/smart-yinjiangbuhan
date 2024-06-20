package com.ruoyi.system.domain;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PresenceDetails {

    /**
     * 总数
     */
    private String presentPeople;
    /**
     * 施工
     */
    private String constructors;
    /**
     * 业主
     */
    private String owner;
    /**
     * 管理
     */
    private String management;
    /**
     * 监理
     */
    private String supervisor;
    /**
     * 班组
     */
    private String constructionPersonnel;
    /**
     * 访客
     */
    private String visitor;



    /**
     * 在场人数
     */
    private Integer count;

    private List<PresenceFrom> presenceFromList;

    @Data
    public static class PresenceFrom {
        /**
         * 1:临时访客  sys_personnel
         * 2:现场工人  lot_staff_attendance
         */
        private Integer type;
        /**
         * 姓名
         */
        private String name;
        /**
         * 班组
         */
        private String groupName;
        /**
         * 工种
         */
        private String workType;
        /**
         * 性别
         */
        private Integer sex;
        /**
         * 手机号
         */
        private String phone;
        /**
         * 最后一次进场时间
         */
        private String lastInDate;
        /**
         * 来访事由
         */
        private String matter;
        /**
         * 公司名称
         */
        private String corporate;

        private Integer workConfigType;

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

}
