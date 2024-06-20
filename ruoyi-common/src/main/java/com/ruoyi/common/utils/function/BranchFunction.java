package com.ruoyi.common.utils.function;

/**
 * @Author zyc
 * @Date 2022/2/16 11:21
 */
@FunctionalInterface
public interface BranchFunction {


    /**
     * 分支操作
     *
     * @param trueHandler 为true时要进行的操作
     * @param falseHandler 为false时要进行的操作
     * @return void
     **/
    void branchHandler(Runnable trueHandler,Runnable falseHandler);
}
