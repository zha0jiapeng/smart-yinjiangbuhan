package com.ruoyi.system.service;
import com.ruoyi.system.entity.SdkDataAccess;
import java.util.List;

/**
 * SDK数据接入表(SdkDataAccess)表服务接口
 * @since 2023-05-29 14:33:57
 */
public interface SdkDataAccessService {

    /**
     * 通过ID查询单条数据
     */
    SdkDataAccess queryById(Integer id);

    /**
     * 分页查询
     */
    List<SdkDataAccess> queryByPage(SdkDataAccess sdkDataAccess);

    /**
     * 新增数据
     */
    SdkDataAccess insert(SdkDataAccess sdkDataAccess);

    /**
     * 修改数据
     */
    SdkDataAccess update(SdkDataAccess sdkDataAccess);

    /**
     * 通过主键删除数据
     */
    boolean deleteById(Integer id);

}
