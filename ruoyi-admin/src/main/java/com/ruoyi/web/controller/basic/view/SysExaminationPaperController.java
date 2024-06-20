package com.ruoyi.web.controller.basic.view;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoyi.assessment.core.api.ApiRest;
import com.ruoyi.assessment.core.api.controller.BaseController;
import com.ruoyi.assessment.core.api.dto.BaseIdsReqDTO;
import com.ruoyi.common.utils.BaseVerifyUtil;
import com.ruoyi.common.utils.FastDFSClientUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.qrcode.QRCodeUtils;
import com.ruoyi.system.domain.basic.SysExaminationPaper;
import com.ruoyi.system.service.SysExaminationPaperService;
import com.ruoyi.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(tags = {"考卷管理"})
@RestController
@RequestMapping("/sys/api/examination/paper")
public class SysExaminationPaperController extends BaseController {
    @Value("${fastdfs.query.url}")
    private String url;
    @Value("${app.work.login.url}")
    private String appWorkLoginUrl;
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;
    @Autowired
    private SysExaminationPaperService sysExaminationPaperService;

    @GetMapping("/list")
    public Result<?> queryPageList(SysExaminationPaper reqDTO,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<SysExaminationPaper> queryWrapper = new QueryWrapper<>();
        Page<SysExaminationPaper> page = new Page<SysExaminationPaper>(pageNo, pageSize);
        IPage<SysExaminationPaper> pageList = sysExaminationPaperService.page(page, queryWrapper);
        if (null != pageList && !CollectionUtils.isEmpty(pageList.getRecords())) {
            pageList.getRecords().forEach(resp -> {
                if (StringUtils.isNotEmpty(resp.getEnclosureUrl())) {
                    resp.setEnclosureUrl(url + resp.getEnclosureUrl());
                }
            });
        }
        return Result.OK(pageList);
    }

    @ApiOperation(value = "添加或修改")
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ApiRest<?> save(@RequestBody SysExaminationPaper sysExaminationPaper) {
        //先保存基础信息
        sysExaminationPaper.setCreatedDate(new Date());
        sysExaminationPaperService.save(sysExaminationPaper);

        String link = "";
        QRCodeUtils qrCode = new QRCodeUtils(100, 100);
        qrCode.setMargin(1);
        String content = appWorkLoginUrl + "/" + sysExaminationPaper.getId();
        BufferedImage image = qrCode.getBufferedImage(content);
        try {
            //以流的方式将图片上传到fastdfs上：
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            // 调用FastDFS中的接口将数据流保存到服务器返回图片地址
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, inputStream.available(), "png", null);
            link = storePath.getFullPath();
        } catch (IOException ex) {
            log.error("新增题库 发生异常:{}", ex.toString());
        }

        //记录二维码实际对应的链接
        sysExaminationPaper.setEnclosureName(content);
        //将服务器地址拼接进
        sysExaminationPaper.setEnclosureUrl(link);
        log.info("在线考试 生成二维码参数:{}", JSON.toJSONString(sysExaminationPaper));
        //更新二维码信息
        sysExaminationPaperService.updateById(sysExaminationPaper);

        return super.success(sysExaminationPaper);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ApiRest<?> edit(@RequestBody BaseIdsReqDTO reqDTO) {

        QueryWrapper<SysExaminationPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", reqDTO.getIds());
        List<SysExaminationPaper> sysExaminationPapers = sysExaminationPaperService.list(queryWrapper);
        BaseVerifyUtil.verify(CollectionUtils.isEmpty(sysExaminationPapers)).throwMessage(10000, "删除试卷错误");

        sysExaminationPapers.forEach(resp -> {
            String enclosureUrl = resp.getEnclosureUrl();
            if (StringUtils.isNotEmpty(enclosureUrl)) {
                fastDFSClientUtil.delFile(enclosureUrl);
            }
        });

        //根据ID删除
        sysExaminationPaperService.removeByIds(reqDTO.getIds());
        return super.success();
    }

}
