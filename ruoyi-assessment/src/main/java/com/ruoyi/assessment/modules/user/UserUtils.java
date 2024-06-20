package com.ruoyi.assessment.modules.user;

import com.ruoyi.assessment.core.api.ApiError;
import com.ruoyi.assessment.core.exception.ServiceException;
import com.ruoyi.assessment.modules.sys.user.dto.response.SysUserLoginDTO;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 用户静态工具类
 * @author bool
 */
public class UserUtils {


    /**
     * 获取当前登录用户的ID
     * @param throwable
     * @return
     */
    public static String getUserId(boolean throwable){
        try {
           // return ((SysUserLoginDTO) SecurityUtils.getSubject().getPrincipal()).getId();
            return null;
        }catch (Exception e){
            if(throwable){
                throw new ServiceException(ApiError.ERROR_10010002);
            }
            return null;
        }
    }

    /**
     * 获取当前登录用户的ID
     * @param throwable
     * @return
     */
    public static boolean isAdmin(boolean throwable){
        /*try {
            SysUserLoginDTO dto = ((SysUserLoginDTO) SecurityUtils.getSubject().getPrincipal());
            return dto.getRoles().contains("sa");
        }catch (Exception e){
            if(throwable){
                throw new ServiceException(ApiError.ERROR_10010002);
            }
        }*/

        return false;
    }

    /**
     * 获取当前登录用户的ID，默认是会抛异常的
     * @return
     */
    public static String getUserId(){
        return getUserId(true);
    }
}
