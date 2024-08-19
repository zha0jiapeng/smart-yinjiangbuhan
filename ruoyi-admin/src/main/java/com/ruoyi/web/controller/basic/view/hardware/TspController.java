package com.ruoyi.web.controller.basic.view.hardware;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.IotTsp;
import com.ruoyi.system.domain.basic.IotTspCopy;
import com.ruoyi.system.domain.basic.PushPersonnelTsp;
import com.ruoyi.system.pojo.Merchant;
import com.ruoyi.system.service.IotTspService;
import com.ruoyi.system.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@RestController
public class TspController extends BaseController {

    @Autowired
    private RedisCache redisCache;

    @Resource
    private IotTspService iotTspService;
    @Resource
    private MerchantService merchantService;

    @GetMapping("tsp/list")
    public TableDataInfo queryByPage(IotTsp iotTsp) {
        startPage();
        List<IotTsp> iotTspList = iotTspService.queryByPage(iotTsp);
        return getDataTable(iotTspList);
    }

    @RequestMapping("bim/tsp/list")
    public TableDataInfo queryByPageBim() {
        List<IotTsp> iotTspLists = new ArrayList<>();
        QueryWrapper<IotTsp> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 1");
        List<IotTsp> iotTspList = iotTspService.list(queryWrapper);
        Optional<IotTsp> max = iotTspList.stream().max(Comparator.comparingDouble(IotTsp::getId));
        max.ifPresent(iotTspLists::add);
        return getDataTable(iotTspLists);
    }

    /**
     * 数据推送
     *
     * @return 结果
     */
    @PostMapping("/send/tsp")
    public AjaxResult push(@RequestBody PushPersonnelTsp pushPersonnel) {
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
                for (IotTsp iotTsp : pushPersonnel.getData()) {
                    iotTsp.setCreatedBy("sys");
                    iotTsp.setCreatedDate(new Date());
                }
                iotTspService.saveBatch(pushPersonnel.getData());
            }
            return AjaxResult.success();
        }

        return AjaxResult.error();
    }

    @GetMapping("tspCopy/list")
    public AjaxResult queryByPage() {
        IotTspCopy iotTspCopy = iotTspService.queryByPageCopy(new IotTspCopy());
        return AjaxResult.success(iotTspCopy);
    }

}
