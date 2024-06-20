package com.rubin.rpan.services.modules.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 检查文件分片返回实体
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@ApiModel(value = "检查文件分片返回实体")
public class CheckFileChunkUploadVO implements Serializable {

    private static final long serialVersionUID = 6892336729844208482L;

    /**
     * 已上传的分片下标集合
     */
    @ApiModelProperty("已上传的分片下标集合")
    private List<Integer> uploadedChunks;

    public CheckFileChunkUploadVO() {
    }

    public List<Integer> getUploadedChunks() {
        return uploadedChunks;
    }

    public void setUploadedChunks(List<Integer> uploadedChunks) {
        this.uploadedChunks = uploadedChunks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckFileChunkUploadVO that = (CheckFileChunkUploadVO) o;
        return Objects.equals(uploadedChunks, that.uploadedChunks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uploadedChunks);
    }

    @Override
    public String toString() {
        return "CheckFileChunkUploadVO{" +
                "uploadedChunks=" + uploadedChunks +
                '}';
    }

}
