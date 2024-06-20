package com.rubin.rpan.services.modules.log.constant;

/**
 * 错误日志常量类
 */
public class LogConstant {

    /**
     * 日志状态枚举类
     */
    public enum LogStatusEnum {

        /**
         * 待处理
         */
        PENDING(0),
        /**
         * 已处理
         */
        PROCESSED(1);

        private Integer code;

        LogStatusEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

    }

}
