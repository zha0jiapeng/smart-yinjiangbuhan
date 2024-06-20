package com.ruoyi.common.utils;

import lombok.Data;

@Data
public class QrContent {

    private String path;

    private Content data;

    @Data
    public static class Content{

        private String productCode;

        private String productName;

    }

}
