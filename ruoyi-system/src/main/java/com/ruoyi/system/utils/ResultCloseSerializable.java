package com.ruoyi.system.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接口返回数据格式
 *
 * @author scott
 * @email jeecgos@163.com
 * @date 2019年1月19日
 */
@Data
@ApiModel(value = "接口返回对象", description = "接口返回对象")
public class ResultCloseSerializable<T> {
    /**
     * 成功标志
     */
    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @ApiModelProperty(value = "返回处理消息")
    private String message = "";

    /**
     * 返回代码
     */
    @ApiModelProperty(value = "返回代码")
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
    @ApiModelProperty(value = "返回数据对象")
    private T result;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private long timestamp = System.currentTimeMillis();

    public ResultCloseSerializable() {
    }

    /**
     * 兼容VUE3版token失效不跳转登录页面
     *
     * @param code
     * @param message
     */
    public ResultCloseSerializable(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultCloseSerializable<T> success(String message) {
        this.message = message;
        this.code = CommonConstant.SC_OK_200;
        this.success = true;
        return this;
    }

    public static <T> ResultCloseSerializable<T> ok() {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        return r;
    }

    public static <T> ResultCloseSerializable<T> ok(String msg) {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        //Result OK(String msg)方法会造成兼容性问题 issues/I4IP3D
        r.setResult((T) msg);
        r.setMessage(msg);
        return r;
    }

    public static <T> ResultCloseSerializable<T> ok(T data) {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setResult(data);
        return r;
    }

    public static <T> ResultCloseSerializable<T> OK() {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        return r;
    }

    /**
     * 此方法是为了兼容升级所创建
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultCloseSerializable<T> OK(String msg) {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage(msg);
        //Result OK(String msg)方法会造成兼容性问题 issues/I4IP3D
        r.setResult((T) msg);
        return r;
    }

    public static <T> ResultCloseSerializable<T> OK(T data) {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setResult(data);
        return r;
    }

    public static <T> ResultCloseSerializable<T> OK(String msg, T data) {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static <T> ResultCloseSerializable<T> error(String msg, T data) {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setSuccess(false);
        r.setCode(CommonConstant.SC_INTERNAL_SERVER_ERROR_500);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static <T> ResultCloseSerializable<T> error(String msg) {
        return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    public static <T> ResultCloseSerializable<T> error(int code, String msg) {
        ResultCloseSerializable<T> r = new ResultCloseSerializable<T>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public ResultCloseSerializable<T> error500(String message) {
        this.message = message;
        this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
        this.success = false;
        return this;
    }

    /**
     * 无权限访问返回结果
     */
    public static <T> ResultCloseSerializable<T> noauth(String msg) {
        return error(CommonConstant.SC_JEECG_NO_AUTHZ, msg);
    }

    @JsonIgnore
    private String onlTable;

}