package com.ruoyi.web.controller.basic.view;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoyi.assessment.core.api.ApiRest;
import com.ruoyi.common.utils.qrcode.QRCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("create/qr")
public class CreateQrCodeController {

    @Value("${fastdfs.query.url}")
    private String url;

    @Value("${app.work.login.url}")
    private String appWorkLoginUrl;

    @Autowired
    private FastFileStorageClient storageClient;

    @GetMapping
    public ApiRest<?> save() {
        String link = "";
        QRCodeUtils qrCode = new QRCodeUtils(100, 100);
        qrCode.setMargin(1);
        String content = "http://218.17.102.70:39080/in/input/worker";
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
        log.info("填报工人信息二维码" + link);

        return null;
    }

}
