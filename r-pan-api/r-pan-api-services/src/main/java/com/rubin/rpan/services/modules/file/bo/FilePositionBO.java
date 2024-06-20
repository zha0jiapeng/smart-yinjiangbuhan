package com.rubin.rpan.services.modules.file.bo;

import java.io.Serializable;
import java.util.Objects;

/**
 * 文件位置处理类
 */
public class FilePositionBO implements Serializable {

    private static final long serialVersionUID = -2301680106151135087L;

    /**
     * 文件ID
     */
    private Long fileId;

    /**
     * 文件名称
     */
    private String filename;

    public FilePositionBO() {
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilePositionBO that = (FilePositionBO) o;
        return Objects.equals(fileId, that.fileId) &&
                Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, filename);
    }

    @Override
    public String toString() {
        return "FilePositionBO{" +
                "fileId=" + fileId +
                ", filename='" + filename + '\'' +
                '}';
    }

}
