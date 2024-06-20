package com.ruoyi.common.utils;

import com.alibaba.fastjson2.JSON;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoyi.common.utils.qrcode.QRCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@Component
public class CreateQrUtils {
    @Value("${fastdfs.query.url}")
    private String url;
    @Autowired
    private FastFileStorageClient storageClient;

    public String createQr(QrContent qrContent, String... arg) {
        String link = "";
        QRCodeUtils qrCode = new QRCodeUtils(100, 100);
        qrCode.setMargin(1);
        String content = JSON.toJSONString(qrContent);
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
            log.error("生成二维码服务异常 发生异常:{}", ex.toString());
        }
        return link;
    }

}
