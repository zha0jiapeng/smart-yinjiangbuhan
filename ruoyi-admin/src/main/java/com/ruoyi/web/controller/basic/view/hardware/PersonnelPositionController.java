package com.ruoyi.web.controller.basic.view.hardware;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.PersonnelPositionBus;
import com.ruoyi.system.domain.basic.PersonnelPosition;
import com.ruoyi.system.domain.basic.PushPosition;
import com.ruoyi.system.pojo.Merchant;
import com.ruoyi.system.service.MerchantService;
import com.ruoyi.system.service.PersonnelPositionService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class PersonnelPositionController extends BaseController {
    @Autowired
    private RedisCache redisCache;

    @Resource
    private MerchantService merchantService;
    @Resource
    private PersonnelPositionService personnelPositionService;

    @GetMapping("/personnel/position/web/list")
    public TableDataInfo queryByPage(PersonnelPosition personnelPosition) {
        startPage();
        List<PersonnelPositionBus> personnelPositions = personnelPositionService.queryPersonnelPositionRelationPersonnel(personnelPosition);
        return getDataTable(personnelPositions);
    }

    /**
     * bim 查询实时人员定位信息;
     * TODO 优化1:查询今天的数据
     * TODO 优化2:后边优化数据库查询并加入索引
     *
     * @param personnelPosition 参数;
     * @return 结果;
     */
    @GetMapping("personnel/position")
    public Result<?> queryPageList(PersonnelPosition personnelPosition) {
        List<PersonnelPositionBus> response = new ArrayList<>();
        LocalDate currentDate = LocalDate.now(); // 获取当前日期
        LocalDateTime zeroHour = currentDate.atStartOfDay(); // 获取当前日期的零点时间
        long timestamp = zeroHour.toEpochSecond(ZoneOffset.ofHours(8)); // 将零点时间转换为时间戳,查询今天的数据
        personnelPosition.setUploadTime(timestamp*1000);
        List<PersonnelPositionBus> list = personnelPositionService.queryPersonnelPositionRelationPersonnel(personnelPosition);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<PersonnelPositionBus>> listMap = list.stream().collect(Collectors.groupingBy(PersonnelPositionBus::getName));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {
                    List<PersonnelPositionBus> collect = v.stream()
                            .sorted(Comparator.comparing(PersonnelPositionBus::getId).reversed()).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect)) {
                        PersonnelPositionBus personnelPositionBus = collect.stream().findFirst().orElse(new PersonnelPositionBus());
                        response.add(personnelPositionBus);
                    }
                });
            }
        }
        return Result.OK(response);
    }

    /**
     * bim 查询实时人员定位信息;
     *
     * @param personnelPosition 参数;
     * @return 结果;
     */
    @PostMapping("personnel/position/name")
    public Result<?> queryPageListBy(@RequestBody PersonnelPosition personnelPosition) {
        List<PersonnelPositionBus> response = new ArrayList<>();
        List<PersonnelPositionBus> list = personnelPositionService.queryPersonnelPositionRelationPersonnel(personnelPosition);
        if (!CollectionUtils.isEmpty(list)) {
            List<PersonnelPositionBus> collect = list.stream()
                    .sorted(Comparator.comparing(PersonnelPositionBus::getId).reversed()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                PersonnelPositionBus personnelPositionBus = collect.stream().findFirst().orElse(new PersonnelPositionBus());
                response.add(personnelPositionBus);
            }
        }
        return Result.OK(response);
    }

    /**
     * 数据推送
     *
     * @param pushPersonnel 参数
     * @return 结果
     */
    @PostMapping("send/position")
    public AjaxResult push(@RequestBody PushPosition pushPersonnel) {
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
            log.info("解密后数据{}", JSON.toJSON(pushPersonnel.getData()));
            if (!CollectionUtils.isEmpty(pushPersonnel.getData())) {
                for (PersonnelPosition val : pushPersonnel.getData()) {
                    val.setCreatedBy("sys");
                    val.setCreatedDate(new Date());
                    val.setModifyBy("sys");
                    val.setModifyDate(new Date());
                }
                personnelPositionService.saveBatch(pushPersonnel.getData());
            }
            return AjaxResult.success();
        }

        return AjaxResult.error();
    }

}
