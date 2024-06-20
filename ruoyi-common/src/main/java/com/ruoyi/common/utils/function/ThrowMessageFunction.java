package com.ruoyi.common.utils.function;



/**
 * @Author zyc
 * @Date 2022/2/15 17:33
 */
@FunctionalInterface
public interface ThrowMessageFunction {


    /**
     *  校验
     * @param code
     * @param message
     */
    void throwMessage(int code,String message);
}
