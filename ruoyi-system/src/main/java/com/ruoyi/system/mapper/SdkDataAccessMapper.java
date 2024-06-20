package com.ruoyi.system.mapper;
import com.ruoyi.system.entity.SdkDataAccess;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * SDK数据接入表(SdkDataAccess)表数据库访问层
 */
public interface SdkDataAccessMapper {

    /**
     * 通过ID查询单条数据
     */
    SdkDataAccess queryById(Integer id);

    /**
     * 查询指定行数据
     */
    List<SdkDataAccess> queryAllByLimit(SdkDataAccess sdkDataAccess);

    /**
     * 统计总行数
     */
    long count(SdkDataAccess sdkDataAccess);

    /**
     * 新增数据
     */
    int insert(SdkDataAccess sdkDataAccess);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     */
    int insertBatch(@Param("entities") List<SdkDataAccess> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     */
    int insertOrUpdateBatch(@Param("entities") List<SdkDataAccess> entities);

    /**
     * 修改数据
     */
    int update(SdkDataAccess sdkDataAccess);

    /**
     * 通过主键删除数据
     */
    int deleteById(Integer id);

}

