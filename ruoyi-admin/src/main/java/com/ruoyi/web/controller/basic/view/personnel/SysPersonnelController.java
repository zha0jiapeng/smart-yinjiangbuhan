package com.ruoyi.web.controller.basic.view.personnel;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.InOrOutEnum;
import com.ruoyi.common.enums.VerifyEnum;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.token.JwtUtil;
import com.ruoyi.system.domain.FlowPathConfig;
import com.ruoyi.system.domain.basic.PushPersonnel;
import com.ruoyi.system.entity.SysPersonnel;
import com.ruoyi.system.pojo.Merchant;
import com.ruoyi.system.service.FlowPathConfigService;
import com.ruoyi.system.service.MerchantService;
import com.ruoyi.system.service.SysPersonnelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 人员信息表(SysPersonnel)表控制层
 *
 * @author makejava
 * @since 2022-12-25 14:31:10
 */
@Slf4j
@RestController
@RequestMapping("sysPersonnel")
public class SysPersonnelController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private FlowPathConfigService flowPathConfigService;
    @Resource
    private SysPersonnelService sysPersonnelService;
    @Resource
    private MerchantService merchantService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 分页查询
     *
     * @param sysPersonnel 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(SysPersonnel sysPersonnel) {
        startPage();
        List<SysPersonnel> sysPersonnels = sysPersonnelService.queryByPage(sysPersonnel);
        return getDataTable(sysPersonnels);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.sysPersonnelService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysPersonnel 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody SysPersonnel sysPersonnel) {
        return AjaxResult.success(this.sysPersonnelService.insert(sysPersonnel));
    }

    /**
     * 编辑数据
     *
     * @param sysPersonnel 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysPersonnel sysPersonnel) {
        return AjaxResult.success(this.sysPersonnelService.update(sysPersonnel));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.sysPersonnelService.deleteById(id));
    }

    /**
     * 商户获取token
     *
     * @param merchant 商户信息
     * @return 结果
     */
    @PostMapping("/token")
    public AjaxResult token(@RequestBody Merchant merchant) {
        log.info("商户获取token 参数:{}", JSON.toJSON(merchant));
        if (null == merchant) {
            return AjaxResult.error("参数异常!");
        }
        if (StringUtils.isEmpty(merchant.getMerchantCode())) {
            return AjaxResult.error("商户编码不可为空!");
        }
        if (StringUtils.isEmpty(merchant.getMerchantName())) {
            return AjaxResult.error("商户名称不可为空!");
        }

        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_code", merchant.getMerchantCode());
        queryWrapper.eq("merchant_name", merchant.getMerchantName());
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        //根据商户编码查询商户是否存在
        Merchant one = merchantService.getOne(queryWrapper);

        if (null == one) {
            return AjaxResult.error("该商户未获得授权!");
        }

        String jwt = JwtUtil.createJWT(merchant.getMerchantCode());
        if (StringUtils.isEmpty(jwt)) {
            return AjaxResult.error("生成token异常!");
        }

        redisCache.setCacheObject(merchant.getMerchantCode(), jwt, 30, TimeUnit.MINUTES);

        return AjaxResult.success(jwt);
    }

    /**
     * 数据推送(访客信息)
     *
     * @param pushPersonnel 参数
     * @return 结果
     */
    @PostMapping("/push")
    public AjaxResult push(@RequestBody PushPersonnel pushPersonnel) {
        log.info("数据推送push 参数:{}", JSON.toJSON(pushPersonnel));
        if (null == pushPersonnel) {
            return AjaxResult.error("参数异常!");
        }
        if (StringUtils.isEmpty(pushPersonnel.getMerchantCode())) {
            return AjaxResult.error("商户编码不可为空!");
        }
        if (StringUtils.isEmpty(pushPersonnel.getToken())) {
            return AjaxResult.error("token不能为空!");
        }
        if (StringUtils.isEmpty(pushPersonnel.getData())) {
            return AjaxResult.error("推送数据为空!");
        }

        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_code", pushPersonnel.getMerchantCode());
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        //根据商户编码查询商户是否存在
        Merchant one = merchantService.getOne(queryWrapper);

        if (null == one) {
            return AjaxResult.error("该商户未获得授权!");
        }

        //token验证
        Boolean aBoolean = redisCache.hasKey(pushPersonnel.getMerchantCode());
        if (!aBoolean) {
            return AjaxResult.error(201, "token失效");
        }

        if (!redisCache.getCacheObject(pushPersonnel.getMerchantCode()).equals(pushPersonnel.getToken())) {
            return AjaxResult.error(201, "token错误");
        }

        if (!StringUtils.isEmpty(pushPersonnel.getData())) {
            SysPersonnel sysPersonnel = JSON.parseObject(pushPersonnel.getData(), SysPersonnel.class);
            log.info("解密后数据{}", JSON.toJSON(sysPersonnel));


            sysPersonnel.setYearCase(DateUtils.dateTimeNow(DateUtils.YYYY));
            sysPersonnel.setMonthCase(DateUtils.dateTimeNow("MM"));
            sysPersonnel.setDayCase(DateUtils.dateTimeNow("dd"));
            Calendar instance = Calendar.getInstance();
            instance.set(Calendar.DAY_OF_MONTH, Integer.parseInt(DateUtils.dateTimeNow("dd")));
            int i = instance.get(Calendar.WEEK_OF_MONTH);
            sysPersonnel.setWeekCase(String.valueOf(i));
            sysPersonnel.setHourCase(DateUtils.dateTimeNow("HH"));

            if (null != sysPersonnel.getStatus() && sysPersonnel.getStatus().equals(InOrOutEnum.IN.getCode())) {
                //待审批
                sysPersonnel.setApproveStatus(3);
            } else {
                //无须审批
                sysPersonnel.setApproveStatus(0);
            }

            sysPersonnelService.insert(sysPersonnel);
            return AjaxResult.success();
        }

        return AjaxResult.error();
    }


    /**
     * 审批接口
     *
     * @param sysPersonnel 参数
     * @return 结果
     */
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody SysPersonnel sysPersonnel) {
        log.info("审批接口 参数:{}", JSON.toJSONString(sysPersonnel));

        //参数校验
        BaseVerifyUtil.verify(null == sysPersonnel.getId())
                .throwMessage(VerifyEnum.ID_NULL.getCode(), VerifyEnum.ID_NULL.getDesc());

        //如果拒绝了,直接更新数据库返回
        SysPersonnel updSysPersonnel = new SysPersonnel();
        updSysPersonnel.setId(sysPersonnel.getId());
        if (sysPersonnel.getApproveStatus().equals(InOrOutEnum.OUT.getCode())) {
            updSysPersonnel.setApproveStatus(InOrOutEnum.OUT.getCode());
            sysPersonnelService.update(updSysPersonnel);
            return AjaxResult.success();
        }

        QueryWrapper<FlowPathConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        queryWrapper.eq("flow_path_code", Constants.VISITOR_AUTH_CODE);
        List<FlowPathConfig> list = flowPathConfigService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return AjaxResult.error("未配置审批流程!");
        }

        List<Integer> flowReviewedByIds = list.stream().map(FlowPathConfig::getFlowReviewedById).collect(Collectors.toList());

        //获取当前登录用户
        Long userId = SecurityUtils.getLoginUser().getUserId();

        if (!flowReviewedByIds.contains(userId.intValue())) {
            return AjaxResult.error("未获得审批权限,无法完成!");
        }

        //调用外部接口
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("token", "2C3800983FE64F9983E7001607EE4FF8");
        paramMap.put("userName", sysPersonnel.getUserName());
        paramMap.put("phone", sysPersonnel.getPhone());
        paramMap.put("status", sysPersonnel.getStatus());
        String sendPost = HttpUtils.sendPost("http://121.40.123.6:9010/api/VisitorAppointment/Approved", JSON.toJSONString(paramMap));
        log.info("调用三方访客授权系统 参数:{} 结果:{}", JSON.toJSONString(paramMap), sendPost);

        if (StringUtils.isEmpty(sendPost)) {
            return AjaxResult.error("调用访客服务商失败");
        }

        HashMap hashMap = JSON.parseObject(sendPost, HashMap.class);
        if (hashMap.isEmpty() || !hashMap.containsKey("code")) {
            return AjaxResult.error("调用访客服务商失败 code");
        }

        if (!hashMap.get("code").equals(InOrOutEnum.IN.getCode())) {
            return AjaxResult.error(hashMap.get("msg").toString());
        }

        //数据更新
        updSysPersonnel.setApproveStatus(InOrOutEnum.IN.getCode());
        sysPersonnelService.update(updSysPersonnel);

        return AjaxResult.success();
    }

}

