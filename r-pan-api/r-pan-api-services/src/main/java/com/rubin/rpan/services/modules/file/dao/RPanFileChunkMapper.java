package com.rubin.rpan.services.modules.file.dao;

import com.rubin.rpan.services.modules.file.entity.RPanFileChunk;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件分片信息持久层接口
 */
@Repository("rPanFileChunkMapper")
public interface RPanFileChunkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RPanFileChunk record);

    int insertSelective(RPanFileChunk record);

    RPanFileChunk selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RPanFileChunk record);

    int updateByPrimaryKey(RPanFileChunk record);

    /**
     * 查询某用户已上传的文件分片集合
     *
     * @param identifier
     * @param userId
     * @return
     */
    List<RPanFileChunk> selectRPanFileChunkListByIdentifierAndUserId(@Param("identifier") String identifier, @Param("userId") Long userId);

    /**
     * 删除某用户上传的特定文件标识的所有分片记录
     *
     * @param identifier
     * @param userId
     * @return
     */
    int deleteByIdentifierAndUserId(@Param("identifier") String identifier, @Param("userId") Long userId);

    /**
     * 分批查询过期的分片记录
     *
     * @param batchIndex
     * @param batchSize
     * @return
     */
    List<RPanFileChunk> selectExpiredFileChunkRecords(@Param("batchIndex") Long batchIndex, @Param("batchSize") Long batchSize);

    /**
     * 查询某用户上传的某文件的分片数量
     *
     * @param identifier
     * @param userId
     * @return
     */
    Integer selectUploadedChunkCount(@Param("identifier") String identifier, @Param("userId") Long userId);

}