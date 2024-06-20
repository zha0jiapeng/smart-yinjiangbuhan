package com.ruoyi.web.controller.basic.view.bim;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.BimAddress;
import com.ruoyi.system.service.BimAddressService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("bim/address")
public class BimAddressController {

    @Autowired
    private BimAddressService bimAddressService;

    @PostMapping("/getBimAddress")
    public Result<?> getBimAddress() {
        QueryWrapper<BimAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<BimAddress> list = bimAddressService.list();
        if (!CollectionUtils.isEmpty(list)) {
            List<BimAddress> updBimAddressList = new ArrayList<>();
            for (BimAddress var : list) {
                BimAddress bimAddress = new BimAddress();
                bimAddress.setId(var.getId());
                bimAddress.setYn(YnEnum.N.getCode());
                updBimAddressList.add(bimAddress);
            }
            bimAddressService.updateBatchById(updBimAddressList);
        }
        return Result.OK(list);
    }

}
