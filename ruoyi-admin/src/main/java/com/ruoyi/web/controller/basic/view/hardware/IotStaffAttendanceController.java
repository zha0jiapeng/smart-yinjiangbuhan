package com.ruoyi.web.controller.basic.view.hardware;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.domain.basic.PushPersonnelStaffAttendance;
import com.ruoyi.system.pojo.Merchant;
import com.ruoyi.system.service.IotStaffAttendanceService;
import com.ruoyi.system.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class IotStaffAttendanceController extends BaseController {
    @Autowired
    private RedisCache redisCache;

    @Resource
    private MerchantService merchantService;

    @Autowired
    private IotStaffAttendanceService iotStaffAttendanceService;

    @GetMapping("staff/attendance/list")
    public TableDataInfo queryByPage(IotStaffAttendance iotStaffAttendance) {
        startPage();
        QueryWrapper<IotStaffAttendance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<IotStaffAttendance> iotStaffAttendances = iotStaffAttendanceService.list(queryWrapper);
        return getDataTable(iotStaffAttendances);
    }

    /**
     * 数据推送
     *
     * @return 结果
     */
    @PostMapping("/send/attendance")
    public AjaxResult push(@RequestBody PushPersonnelStaffAttendance pushPersonnel) {
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
            if (!CollectionUtils.isEmpty(pushPersonnel.getData())) {
                for (IotStaffAttendance iotStaffAttendance : pushPersonnel.getData()) {
                    iotStaffAttendance.setYearCase(DateUtils.dateTimeNow(DateUtils.YYYY));
                    iotStaffAttendance.setMonthCase(DateUtils.dateTimeNow("MM"));
                    iotStaffAttendance.setDayCase(DateUtils.dateTimeNow("dd"));
                    iotStaffAttendance.setWeekCase(getWeeksInMonthOfDate());
                    iotStaffAttendance.setHourCase(DateUtils.dateTimeNow("HH"));
                }
                iotStaffAttendanceService.saveBatch(pushPersonnel.getData());
            }
            return AjaxResult.success();
        }

        return AjaxResult.error();
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping("/iotStaffAttendance/queryList")
    public TableDataInfo queryList(IotStaffAttendance iotStaffAttendance) {
        startPage();
        List<IotStaffAttendance> inspectionPlans = iotStaffAttendanceService.queryByPage(iotStaffAttendance);
        return getDataTable(inspectionPlans);
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/iotStaffAttendance/{pid}")
    public AjaxResult queryById(@PathVariable("pid") Integer pid) {
        return AjaxResult.success(iotStaffAttendanceService.queryById(pid));
    }

    @GetMapping("/saveScheduled")
    public AjaxResult saveScheduled(@RequestBody HashMap<String, String> param) {
        iotStaffAttendanceService.saveScheduled(param.get("key1"), param.get("key2"));
        return AjaxResult.success();
    }

    public static String getWeeksInMonthOfDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //设置每周第一天为周一 默认每周第一天为周日
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //获取当前日期所在周周日
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH));
    }

}
