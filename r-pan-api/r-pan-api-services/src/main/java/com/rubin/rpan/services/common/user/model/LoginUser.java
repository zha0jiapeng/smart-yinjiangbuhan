package com.rubin.rpan.services.common.user.model;


/**
 * 登录用户身份权限
 *
 * @author ruoyi
 */

public class LoginUser {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
