package com.ruoyi.web.controller.basic.view.bim;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.domain.basic.BimUploadBasic;
import com.ruoyi.system.domain.basic.BimUploadBasicVo;
import com.ruoyi.system.service.BimUploadBasicService;
import com.ruoyi.system.utils.Result;
import com.ruoyi.system.utils.ResultCloseSerializable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("bim/upload/basic")
public class BimUploadBasicController {

    @Resource
    private BimUploadBasicService bimUploadBasicService;

    @PostMapping("/query")
    public ResultCloseSerializable<?> query(@RequestBody BimUploadBasic bimUploadBasic) {
        BimUploadBasic bimUploadBasicResponse = new BimUploadBasic();

        QueryWrapper<BimUploadBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("upload_basic_id", bimUploadBasic.getUploadBasicId());
        List<BimUploadBasic> bimUploadBasics = bimUploadBasicService.list(queryWrapper);

        if (CollectionUtils.isEmpty(bimUploadBasics)) {
            bimUploadBasicResponse.setCheck(false);
            return ResultCloseSerializable.ok(bimUploadBasicResponse);
        }

        bimUploadBasicResponse = bimUploadBasics.stream().findFirst().orElse(new BimUploadBasic());
        String bimUploadBasicResponseInfo = bimUploadBasicResponse.getInfo();
        BimUploadBasicVo bimUploadBasicVo = JSON.parseObject(bimUploadBasicResponseInfo, BimUploadBasicVo.class);
        bimUploadBasicVo.setCheck(true);

        return ResultCloseSerializable.OK(bimUploadBasicVo);
    }

    @PostMapping(value = "/save")
    public Result<?> save(@RequestBody BimUploadBasic bimUploadBasic) {
        log.info("查询三维上传数据 参数:{}", JSON.toJSONString(bimUploadBasic));
        bimUploadBasicService.save(bimUploadBasic);

        return Result.ok();
    }

    @PostMapping(value = "/update")
    public Result<?> update(@RequestBody BimUploadBasicVo bimUploadBasicVo) {
        log.info("查询三维上传数据 参数:{}", JSON.toJSONString(bimUploadBasicVo));
        BimUploadBasic bimUploadBasic = new BimUploadBasic();
        bimUploadBasic.setUploadBasicId(bimUploadBasicVo.getUploadBasicId());
        bimUploadBasic.setInfo(JSON.toJSONString(bimUploadBasicVo));

        QueryWrapper<BimUploadBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("upload_basic_id", bimUploadBasicVo.getUploadBasicId());
        List<BimUploadBasic> bimUploadBasics = bimUploadBasicService.list(queryWrapper);
        if (CollectionUtils.isEmpty(bimUploadBasics)) {
            bimUploadBasicService.save(bimUploadBasic);
            return Result.ok();
        }

        BimUploadBasic bimUploadBasicResponse = bimUploadBasics.stream().findFirst().orElse(new BimUploadBasic());
        bimUploadBasic.setId(bimUploadBasicResponse.getId());

        bimUploadBasicService.updateById(bimUploadBasic);

        return Result.ok();
    }


}
