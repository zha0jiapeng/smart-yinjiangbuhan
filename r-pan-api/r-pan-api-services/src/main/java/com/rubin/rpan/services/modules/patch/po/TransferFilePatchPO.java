package com.rubin.rpan.services.modules.patch.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * 转移物理文件PO
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@ApiModel(value = "转移物理文件PO")
public class TransferFilePatchPO implements Serializable {

    private static final long serialVersionUID = 4356002623279266826L;

    @ApiModelProperty(value = "原来物理文件存储根目录", required = true)
    @NotBlank(message = "原来物理文件存储根目录不能为空")
    private String rootFilePath;

    @ApiModelProperty(value = "原来模板文件存储根目录", required = true)
    @NotBlank(message = "原来模板文件存储根目录不能为空")
    private String tempFilePath;

    public TransferFilePatchPO() {
    }

    public String getRootFilePath() {
        return rootFilePath;
    }

    public void setRootFilePath(String rootFilePath) {
        this.rootFilePath = rootFilePath;
    }

    public String getTempFilePath() {
        return tempFilePath;
    }

    public void setTempFilePath(String tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferFilePatchPO that = (TransferFilePatchPO) o;
        return Objects.equals(rootFilePath, that.rootFilePath) &&
                Objects.equals(tempFilePath, that.tempFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rootFilePath, tempFilePath);
    }

    @Override
    public String toString() {
        return "TransferFilePatchPO{" +
                "rootFilePath='" + rootFilePath + '\'' +
                ", tempFilePath='" + tempFilePath + '\'' +
                '}';
    }

}
