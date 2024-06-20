package com.rubin.rpan.services.common.aspect;

import com.rubin.rpan.cache.CacheManager;
import com.rubin.rpan.services.common.constant.CommonConstant;
import com.rubin.rpan.services.common.response.R;
import com.rubin.rpan.services.common.response.ResponseCode;
import com.rubin.rpan.util.JwtUtil;
import com.rubin.rpan.util.UserIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 请求登录验证
 * Created by RubinChu on 2021/1/24 21:34
 */
@Aspect
@Component
public class RPanLoginAspect {

    private static final Logger log = LoggerFactory.getLogger(RPanLoginAspect.class);

    /**
     * 登录认证参数名称
     */
    private static final String LOGIN_AUTHENTICATION_PARAM_NAME = "authorization";

    /**
     * 请求头token的key
     */
    private final static String TOKEN_KEY = "Authorization";

    /**
     * 切点入口
     */
    private final String POINT_CUT = "@annotation(com.rubin.rpan.services.common.annotation.NeedLogin)";


    @Autowired
    @Qualifier(value = "cacheManager")
    private CacheManager cacheManager;

    /**
     * 切点
     */
    @Pointcut(value = POINT_CUT)
    public void loginAuth() {
    }

   // @Around("loginAuth()")
    public Object loginAuth(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String uri = request.getRequestURI();
        log.debug("成功拦截到请求,uri为:{}", uri);
        if (!checkAndSaveUserId(request)) {
            log.warn("成功拦截到请求,uri为:{}, 检测到用户未登录,将跳转至登录页面", uri);
            return R.fail(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        log.debug("成功拦截到请求,uri为:{}, 请求通过", uri);
        return proceedingJoinPoint.proceed();
    }

    /**
     * 检查并保存登录用户的ID
     * 此处会实现单设备登录功能 所以本套代码未考虑并发
     *
     * @param request
     * @return
     */
    private boolean checkAndSaveUserId(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(LOGIN_AUTHENTICATION_PARAM_NAME);
        }

        if (StringUtils.isBlank(token)) {
            return false;
        }

        boolean bearer = token.contains("Bearer ");
        if (bearer) {
            token = token.replaceAll("Bearer ", "");
        }

        Object uuid = JwtUtil.analyzeToken(token, CommonConstant.LOGIN_USER_ID);
        if (Objects.isNull(uuid)) {
            return false;
        }

        String userKey = getTokenKey(uuid.toString());
        Map cacheObject = (Map) cacheManager.get(userKey);
        if (Objects.isNull(cacheObject)) {
            return false;
        }

        Object userId = cacheObject.get("userId");
        Object redisValue = cacheObject.get("token");
        if (Objects.equals(redisValue, token)) {
            saveUserId(userId);
            return true;
        }

        return false;
    }

    private String getTokenKey(String uuid) {
        return "login_tokens:" + uuid;
    }

    /**
     * 保存用户ID到对应线程上
     *
     * @param userId
     */
    private void saveUserId(Object userId) {
        UserIdUtil.set(Long.valueOf(String.valueOf(userId)));
    }

}
