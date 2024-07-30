package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import java.util.List;
import com.ruoyi.system.domain.CarAccess;

/**
 * 车辆通行记录Mapper接口
 * 
 * @author mashir0
 * @date 2024-07-30
 */
public interface CarAccessMapper 
{
    /**
     * 查询车辆通行记录
     * 
     * @param id 车辆通行记录主键
     * @return 车辆通行记录
     */
    public CarAccess selectCarAccessById(Long id);

    /**
     * 查询车辆通行记录列表
     * 
     * @param carAccess 车辆通行记录
     * @return 车辆通行记录集合
     */
    public List<CarAccess> selectCarAccessList(CarAccess carAccess);

    /**
     * 新增车辆通行记录
     * 
     * @param carAccess 车辆通行记录
     * @return 结果
     */
    public int insertCarAccess(CarAccess carAccess);

    /**
     * 修改车辆通行记录
     * 
     * @param carAccess 车辆通行记录
     * @return 结果
     */
    public int updateCarAccess(CarAccess carAccess);

    /**
     * 删除车辆通行记录
     * 
     * @param id 车辆通行记录主键
     * @return 结果
     */
    public int deleteCarAccessById(Long id);

    /**
     * 批量删除车辆通行记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCarAccessByIds(Long[] ids);
}
