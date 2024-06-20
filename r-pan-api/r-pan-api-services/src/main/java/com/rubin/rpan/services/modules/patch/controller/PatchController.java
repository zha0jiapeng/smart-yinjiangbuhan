package com.rubin.rpan.services.modules.patch.controller;

import com.rubin.rpan.services.common.response.R;
import com.rubin.rpan.services.modules.patch.po.TransferFilePatchPO;
import com.rubin.rpan.services.modules.patch.service.IPatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目补丁相关rest接口返回
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@RestController
@Validated
@Api(tags = "补丁")
@RequestMapping("patch")
public class PatchController {

    @Autowired
    @Qualifier(value = "patchService")
    private IPatchService iPatchService;

    /**
     * 转移旧的物理文件到新的物理文件路径下
     *
     * @param transferFilePatchPO
     * @return
     */
    @ApiOperation(
            value = "转移旧的物理文件到新的物理文件路径下",
            notes = "该接口提供了转移旧的物理文件到新的物理文件路径下的功能",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @PostMapping("file/transfer")
    public R transferFile(@Validated @RequestBody TransferFilePatchPO transferFilePatchPO) {
        iPatchService.transferFile(transferFilePatchPO.getRootFilePath(), transferFilePatchPO.getTempFilePath());
        return R.success();
    }

}
