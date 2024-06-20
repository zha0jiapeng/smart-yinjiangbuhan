package com.ruoyi.web.controller.basic.view.danger;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.qrcode.QRCodeUtils;
import com.ruoyi.system.domain.SecureStudyFileConfig;
import com.ruoyi.system.service.SecureStudyFileConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/secure/study/file/config")
public class SecureStudyFileConfigController extends BaseController {
    @Value("${fastdfs.query.url}")
    private String url;
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private SecureStudyFileConfigService secureStudyFileConfigService;

    @GetMapping("/list")
    public TableDataInfo list(SecureStudyFileConfig secureStudyFileConfig) {
        startPage();
        List<SecureStudyFileConfig> secureStudyFileConfigs = secureStudyFileConfigService.list();
        if (!CollectionUtils.isEmpty(secureStudyFileConfigs)) {
            secureStudyFileConfigs.forEach(var -> {
                var.setZrCode(url + var.getZrCode());
            });
        }
        return getDataTable(secureStudyFileConfigs);
    }

    @PostMapping("/add")
    public AjaxResult create(@RequestBody SecureStudyFileConfig secureStudyFileConfig) {
        secureStudyFileConfig.setCreatedBy(getUsername());
        secureStudyFileConfig.setCreatedDate(new Date());
        secureStudyFileConfig.setYn(YnEnum.Y.getCode());

        secureStudyFileConfigService.save(secureStudyFileConfig);

        String link = "";
        QRCodeUtils qrCode = new QRCodeUtils(100, 100);
        qrCode.setMargin(1);
        String content = "http://218.17.102.70:39080/in/input/user2" + "/" + secureStudyFileConfig.getId();
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

        SecureStudyFileConfig secureStudyFileConfigServiceById = secureStudyFileConfigService.getById(secureStudyFileConfig.getId());
        secureStudyFileConfigServiceById.setEnclosureName(content);
        secureStudyFileConfigServiceById.setZrCode(link);
        secureStudyFileConfigService.updateById(secureStudyFileConfigServiceById);

        return AjaxResult.success();
    }

    @GetMapping("/details")
    public AjaxResult details(@RequestParam(name = "id", required = true) String id) {
        SecureStudyFileConfig configServiceById = secureStudyFileConfigService.getById(id);
        return AjaxResult.success(configServiceById);
    }

    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody SecureStudyFileConfig secureStudyFileConfig) {
        secureStudyFileConfigService.updateById(secureStudyFileConfig);
        return AjaxResult.success();
    }

    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestParam(name = "id", required = true) String id) {
        secureStudyFileConfigService.removeById(id);
        return AjaxResult.success();
    }

}
