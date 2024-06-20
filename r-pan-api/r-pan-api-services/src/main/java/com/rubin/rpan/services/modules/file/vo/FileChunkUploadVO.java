package com.rubin.rpan.services.modules.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 文件分片上传返回实体
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
@ApiModel(value = "文件分片上传返回实体")
public class FileChunkUploadVO implements Serializable {

    private static final long serialVersionUID = 5389674756003881027L;

    /**
     * 是否需要合并文件 0 不需要 1 需要
     */
    @ApiModelProperty("是否需要合并文件 0 不需要 1 需要")
    private Integer mergeFlag;

    public FileChunkUploadVO() {
    }

    public Integer getMergeFlag() {
        return mergeFlag;
    }

    public void setMergeFlag(Integer mergeFlag) {
        this.mergeFlag = mergeFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileChunkUploadVO that = (FileChunkUploadVO) o;
        return Objects.equals(mergeFlag, that.mergeFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mergeFlag);
    }

    @Override
    public String toString() {
        return "FileChunkUploadVO{" +
                "mergeFlag=" + mergeFlag +
                '}';
    }

}
