package com.rubin.rpan.services.modules.file.service;

import com.rubin.rpan.services.modules.file.entity.RPanFileChunk;

import java.util.List;

/**
 * 实体文件分片业务处理接口
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
public interface IFileChunkService {

    void save(Long userId, String identifier, Integer chunkNumber, String realPath);

    List<RPanFileChunk> getUploadedChunks(String identifier, Long userId);

    void deleteFileChunks(String identifier, Long userId);

    void deleteFileChunk(Long id);

    List<RPanFileChunk> getExpiredFileChunkRecords(Long scrollPointer, Long batchNum);

    Integer getUploadedChunkCount(String identifier, Long userId);

}
