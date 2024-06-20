package com.ruoyi.common.utils.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

//二维码工具类（使用ZXingjar包）
public class QRCodeUtils {
    // 默认宽为300
    private Integer width = 600;
    // 默认高为300
    private Integer height = 600;
    // 默认二维码图片格式
    private String imageFormat = "png";
    // 默认二维码字符编码
    private String charType = "utf-8";
    // 默认二维码的容错级别

    // 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
    private ErrorCorrectionLevel corretionLevel = ErrorCorrectionLevel.M;
    // 二维码与图片的边缘
    private Integer margin = 0;
    // 二维码参数
    private Map<EncodeHintType, Object> encodeHits = new HashMap<EncodeHintType, Object>();


    public QRCodeUtils(Integer width, Integer height, String imageFormat, String charType, ErrorCorrectionLevel corretionLevel, Integer margin) {
        if (width != null) {
            this.width = width;
        }
        if (height != null) {
            this.height = height;
        }
        if (imageFormat != null) {
            this.imageFormat = imageFormat;
        }
        if (charType != null) {
            this.charType = charType;
        }
        if (corretionLevel != null) {
            this.corretionLevel = corretionLevel;
        }
        if (margin != null) {
            this.margin = margin;
        }
    }

    public QRCodeUtils(Integer width, Integer height, String imageFormat, String charType,
                       ErrorCorrectionLevel corretionLevel) {
        this(width, height, imageFormat, charType, corretionLevel, null);
    }

    public QRCodeUtils(Integer width, Integer height, String imageFormat, String charType, Integer margin) {
        this(width, height, imageFormat, charType, null, margin);
    }

    public QRCodeUtils(Integer width, Integer height, String imageFormat, String charType) {
        this(width, height, imageFormat, charType, null, null);
    }

    public QRCodeUtils(Integer width, Integer height, String imageFormat) {
        this(width, height, imageFormat, null, null, null);
    }

    public QRCodeUtils(Integer width, Integer height) {
        this(width, height, null, null, null, null);
    }

    public QRCodeUtils() {
    }

    // 初始化二维码的参数
    private void initialParamers() {
        // 字符编码
        encodeHits.put(EncodeHintType.CHARACTER_SET, this.charType);
        // 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        encodeHits.put(EncodeHintType.ERROR_CORRECTION, this.corretionLevel);
        // 二维码与图片边距
        encodeHits.put(EncodeHintType.MARGIN, margin);
    }

    // 得到二维码图片
    public BufferedImage getBufferedImage(String content) {
        initialParamers();
        BufferedImage bufferedImage = null;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, this.width,
                    this.height, this.encodeHits);


            //去掉白边
            int[] rec = bitMatrix.getEnclosingRectangle();
            int resWidth = rec[2] + 1;
            int resHeight = rec[3] + 1;
            BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
            resMatrix.clear();
            for (int i = 0; i < resWidth; i++) {
                for (int j = 0; j < resHeight; j++) {
                    if (bitMatrix.get(i + rec[0], j + rec[1])) {
                        resMatrix.set(i, j);
                    }
                }
            }
            //2
            int width = resMatrix.getWidth();
            int height = resMatrix.getHeight();
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, resMatrix.get(x, y) == true ?
                            Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        return bufferedImage;
    }

    // 将二维码保存到输出流中
    public void writeToStream(String content, OutputStream os) {
        initialParamers();
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, this.width, this.height,
                    this.encodeHits);
            MatrixToImageWriter.writeToStream(matrix, this.imageFormat, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 将二维码图片保存为文件
    public void createQrImage(String content, File file) {
        initialParamers();
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, this.width, this.height, this.encodeHits);
            MatrixToImageWriter.writeToFile(matrix, this.imageFormat, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 将二维码图片保存到指定路径
    public void createQrImage(String content, String path) {
        initialParamers();
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, this.width, this.height, this.encodeHits);
            MatrixToImageWriter.writeToPath(matrix, this.imageFormat, new File(path).toPath());
            //MatrixToImageWriter.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void newcreateQrImage(String content, String path) {
        initialParamers();
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, this.width, this.height, this.encodeHits);
            // MatrixToImageWriter.writeToPath(matrix, this.imageFormat, new File(path).toPath());
            //MatrixToImageWriter.w

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //识别图片二维码
    public String decodeQrImage(File file) {
        String content = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap image = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> decodeHits = new HashMap<DecodeHintType, Object>();
            decodeHits.put(DecodeHintType.CHARACTER_SET, this.charType);
            Result result = new MultiFormatReader().decode(image, decodeHits);
            content = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public String getCharType() {
        return charType;
    }

    public void setCharType(String charType) {
        this.charType = charType;
    }

    public ErrorCorrectionLevel getCorretionLevel() {
        return corretionLevel;
    }

    public void setCorretionLevel(ErrorCorrectionLevel corretionLevel) {
        this.corretionLevel = corretionLevel;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public Map<EncodeHintType, Object> getHits() {
        return encodeHits;
    }

    public void setHits(Map<EncodeHintType, Object> hits) {
        this.encodeHits = hits;
    }

}
