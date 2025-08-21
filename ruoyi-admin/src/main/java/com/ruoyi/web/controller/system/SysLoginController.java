package com.ruoyi.web.controller.system;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.web.controller.common.CaptchaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 登录方法
     *
     * @return 结果
     */
    @PostMapping("/login/singleSsignOn")
    public String loginSingleSsignOn() throws IOException {
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("杨佳卫");
        loginBody.setPassword("jiawei1314");
        String result1 = HttpUtil.get("http://127.0.0.1:8097/captchaAPI");
        JSONObject jsonObject = JSON.parseObject(result1);
        loginBody.setCode(jsonObject.get("codeAPI").toString());
        loginBody.setUuid(jsonObject.get("uuid").toString());
        System.out.println("UUID: " + jsonObject.get("uuid"));
        System.out.println("Code: " + jsonObject.get("codeAPI"));
        String requestBody = JSON.toJSONString(loginBody);

        String response = HttpUtil.post("http://127.0.0.1:8097/login", requestBody);
        return response;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
