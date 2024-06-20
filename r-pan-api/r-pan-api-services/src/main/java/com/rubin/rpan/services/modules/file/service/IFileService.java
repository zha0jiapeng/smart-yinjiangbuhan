package com.rubin.rpan.services.modules.file.service;

import com.rubin.rpan.services.modules.file.entity.RPanFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 实体文件业务处理接口
 * Created by RubinChu on 2021/1/22 下午 4:11
 */
public interface IFileService {

    RPanFile save(MultipartFile file, Long userId, String identifier, Long totalSize, String suffix);

    boolean saveWithChunk(MultipartFile file, Long userId, String identifier, Integer totalChunks, Integer chunkNumber, Long totalSize, String filename);

    void delete(String fileIds);

    RPanFile getFileDetail(Long realFileId);

    List<RPanFile> selectByIdentifier(String identifier);

    List<Integer> getUploadedChunkNumbers(String identifier, Long userId);

    RPanFile mergeChunks(String identifier, Long totalSize, Long userId, String filename);

    List<RPanFile> getRPanFilePage(Long scrollPointer, Long batchNum);

    void updateSelective(RPanFile rPanFile);

}
