package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.basic.CarAccess;
import com.ruoyi.system.mapper.CarAccessMapper;
import com.ruoyi.system.service.CarAccessService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 车辆进出场地记录表(CarAccess)表服务实现类
 *
 * @author makejava
 * @since 2022-12-15 20:08:47
 */
@Service("carAccessService")
public class CarAccessServiceImpl  extends ServiceImpl<CarAccessMapper, CarAccess> implements CarAccessService {
    @Resource
    private CarAccessMapper carAccessMapper;

    /**
     * 新增数据
     *
     * @param carAccess 实例对象
     * @return 实例对象
     */
    @Override
    public CarAccess insert(CarAccess carAccess) {
        this.carAccessMapper.insert(carAccess);
        return carAccess;
    }

    @Override
    public String importExcel(List<CarAccess> carAccesss, Boolean isUpdateSupport, String operName) {
        if (CollectionUtils.isEmpty(carAccesss) || carAccesss.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        this.saveBatch(carAccesss);
        return "成功";
    }

}
