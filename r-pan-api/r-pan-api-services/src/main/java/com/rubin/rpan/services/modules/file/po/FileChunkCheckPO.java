package com.rubin.rpan.services.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value = "文件分片上传查询校验PO")
public class FileChunkCheckPO implements Serializable {

    private static final long serialVersionUID = -8552887859773638126L;

    @ApiModelProperty(value = "文件对应的唯一标识", required = true)
    @NotBlank(message = "文件对应的唯一标识不能为空")
    private String identifier;

    public FileChunkCheckPO() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileChunkCheckPO that = (FileChunkCheckPO) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return "FileChunkCheckPO{" +
                "identifier='" + identifier + '\'' +
                '}';
    }

}
