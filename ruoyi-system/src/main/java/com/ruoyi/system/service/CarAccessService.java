package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.CarAccess;

import java.util.List;

/**
 * 车辆进出场地记录表(CarAccess)表服务接口
 *
 * @author makejava
 * @since 2022-12-15 20:08:45
 */
public interface CarAccessService extends IService<CarAccess> {
    /**
     * 新增数据
     *
     * @param carAccess 实例对象
     * @return 实例对象
     */
    CarAccess insert(CarAccess carAccess);

    String importExcel(List<CarAccess> carAccesss, Boolean isUpdateSupport, String operName);

}
