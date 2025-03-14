package com.ruoyi.web.controller.basic.yinjiangbuhan.utils.Weighbridege.exception;

import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.Weighbridege.ApiGatewayError;
import lombok.Getter;

@Getter
public class MCTechOpenApiRequestException extends MCTechException {
    private final ApiGatewayError error;

    public MCTechOpenApiRequestException(String message, ApiGatewayError error) {
        super(message);

        this.error = error;
    }
}
