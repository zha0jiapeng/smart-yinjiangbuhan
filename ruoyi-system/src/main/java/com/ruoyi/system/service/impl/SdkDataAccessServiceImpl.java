package com.ruoyi.system.service.impl;
import com.ruoyi.system.entity.SdkDataAccess;
import com.ruoyi.system.mapper.SdkDataAccessMapper;
import com.ruoyi.system.service.SdkDataAccessService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import javax.annotation.Resource;
import java.util.List;

/**
 * SDK数据接入表(SdkDataAccess)表服务实现类
 * @since 2023-05-29 14:33:58
 */
@Service("sdkDataAccessService")
public class SdkDataAccessServiceImpl implements SdkDataAccessService {
    @Resource
    private SdkDataAccessMapper sdkDataAccessDao;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public SdkDataAccess queryById(Integer id) {
        return this.sdkDataAccessDao.queryById(id);
    }

    /**
     * 分页查询
     * @return 查询结果
     */
    @Override
    public List<SdkDataAccess> queryByPage(SdkDataAccess sdkDataAccess) {
        return this.sdkDataAccessDao.queryAllByLimit(sdkDataAccess);
    }

    /**
     * 新增数据
     */
    @Override
    public SdkDataAccess insert(SdkDataAccess sdkDataAccess) {
        this.sdkDataAccessDao.insert(sdkDataAccess);
        return sdkDataAccess;
    }

    /**
     * 修改数据
     */
    @Override
    public SdkDataAccess update(SdkDataAccess sdkDataAccess) {
        this.sdkDataAccessDao.update(sdkDataAccess);
        return this.queryById(sdkDataAccess.getId());
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sdkDataAccessDao.deleteById(id) > 0;
    }
}
