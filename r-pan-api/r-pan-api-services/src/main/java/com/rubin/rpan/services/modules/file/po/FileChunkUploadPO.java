package com.rubin.rpan.services.modules.file.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value = "文件分片上传PO")
public class FileChunkUploadPO implements Serializable {

    private static final long serialVersionUID = 3240046102111232523L;

    @ApiModelProperty(value = "文件名称", required = true)
    @NotBlank(message = "文件名称不能为空")
    private String filename;

    @ApiModelProperty(value = "文件对应的唯一标识", required = true)
    @NotBlank(message = "文件对应的唯一标识不能为空")
    private String identifier;

    @ApiModelProperty(value = "总分片数", required = true)
    @NotNull(message = "总分片数不能为空")
    private Integer totalChunks;

    @ApiModelProperty(value = "分片下标，从1开始", required = true)
    @NotNull(message = "分片下标不能为空")
    private Integer chunkNumber;

    @ApiModelProperty(value = "当前分片大小", required = true)
    @NotNull(message = "当前分片大小不能为空")
    private Long currentChunkSize;

    @ApiModelProperty(value = "文件大小", required = true)
    @NotNull(message = "文件大小不能为空")
    private Long totalSize;

    @ApiModelProperty(value = "上传文件", required = true)
    @NotNull(message = "上传文件不能为空")
    private MultipartFile file;

    public FileChunkUploadPO() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
    }

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public Long getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(Long currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileChunkUploadPO that = (FileChunkUploadPO) o;
        return Objects.equals(filename, that.filename) &&
                Objects.equals(identifier, that.identifier) &&
                Objects.equals(totalChunks, that.totalChunks) &&
                Objects.equals(chunkNumber, that.chunkNumber) &&
                Objects.equals(currentChunkSize, that.currentChunkSize) &&
                Objects.equals(totalSize, that.totalSize) &&
                Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename, identifier, totalChunks, chunkNumber, currentChunkSize, totalSize, file);
    }

    @Override
    public String toString() {
        return "FileChunkUploadPO{" +
                "filename='" + filename + '\'' +
                ", identifier='" + identifier + '\'' +
                ", totalChunks=" + totalChunks +
                ", chunkNumber=" + chunkNumber +
                ", currentChunkSize=" + currentChunkSize +
                ", totalSize=" + totalSize +
                ", file=" + file +
                '}';
    }

}
