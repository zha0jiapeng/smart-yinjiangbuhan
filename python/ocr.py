import ddddocr

if __name__ == '__main__':
    # 创建 ddddocr 识别器
    ocr = ddddocr.DdddOcr()

    # 读取验证码图片
    with open('/Users/domi/Source/smart-yinjiangbuhan/python/captcha.png', 'rb') as f:
        image = f.read()
    # 进行识别
    result = ocr.classification(image)
    print(f"OCR:{result}")
