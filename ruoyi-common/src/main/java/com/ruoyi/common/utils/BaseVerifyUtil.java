package com.ruoyi.common.utils;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.function.BranchFunction;
import com.ruoyi.common.utils.function.ThrowMessageFunction;

public class BaseVerifyUtil {

    public static ThrowMessageFunction verify(boolean b){

        return (code,errorMessage) -> {
            if (b){
                throw new ServiceException(errorMessage,code);
            }
        };

    }



    public static BranchFunction branch(Boolean b){

        return ((trueHandler, falseHandler) -> {
            if (b){
                trueHandler.run();
            }else {
                falseHandler.run();
            }
        });

    }
}
