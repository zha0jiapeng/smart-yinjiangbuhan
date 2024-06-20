package com.ruoyi.system.service.impl;

import com.ruoyi.system.entity.InspectionRoute;
import com.ruoyi.system.mapper.InspectionRouteMapper;
import com.ruoyi.system.service.InspectionRouteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 巡检路线表(InspectionRoute)表服务实现类
 *
 * @author makejava
 * @since 2022-11-25 17:11:06
 */
@Service("inspectionRouteService")
public class InspectionRouteServiceImpl implements InspectionRouteService {
    @Resource
    private InspectionRouteMapper inspectionRouteMapper;


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public InspectionRoute queryById(Integer id) {
        return this.inspectionRouteMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param inspectionRoute 筛选条件
     * @return 查询结果
     */
    @Override
    public List<InspectionRoute> queryByPage(InspectionRoute inspectionRoute) {
        return this.inspectionRouteMapper.queryAllByLimit(inspectionRoute);
    }

    /**
     * 新增数据
     *
     * @param inspectionRoute 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionRoute insert(InspectionRoute inspectionRoute) {
        this.inspectionRouteMapper.insert(inspectionRoute);
        return inspectionRoute;
    }

    /**
     * 修改数据
     *
     * @param inspectionRoute 实例对象
     * @return 实例对象
     */
    @Override
    public InspectionRoute update(InspectionRoute inspectionRoute) {
        this.inspectionRouteMapper.update(inspectionRoute);
        return this.queryById(inspectionRoute.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.inspectionRouteMapper.deleteById(id) > 0;
    }

    @Override
    public void updateStopCheck(InspectionRoute inspectionRoute) {
        inspectionRouteMapper.update(inspectionRoute);
    }
}
