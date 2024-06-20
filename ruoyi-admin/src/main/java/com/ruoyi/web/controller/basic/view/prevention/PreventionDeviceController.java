package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.vo.PreventionDeviceVO;
import com.ruoyi.system.entity.PreventionDevice;
import com.ruoyi.system.service.PreventionDeviceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 双重预防-装置管理(PreventionDevice)表控制层
 *
 * @author makejava
 * @since 2022-11-17 11:18:35
 */
@RestController
@RequestMapping("/preventionDevice")
public class PreventionDeviceController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionDeviceService preventionDeviceService;

    /**
     * 分页查询
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionDevice preventionDevice) {
        startPage();
        List<PreventionDeviceVO> preventionDeviceVOS = preventionDeviceService.queryByPage(preventionDevice);
        return getDataTable(preventionDeviceVOS);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.preventionDeviceService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionDevice 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionDevice preventionDevice) {
        return AjaxResult.success(this.preventionDeviceService.insert(preventionDevice));
    }

    /**
     * 编辑数据
     *
     * @param preventionDevice 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionDevice preventionDevice) {
        return AjaxResult.success(this.preventionDeviceService.update(preventionDevice));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionDeviceService.deleteById(id));
    }


    @GetMapping("/listByLevel")
    public AjaxResult list() {
        List<PreventionDevice> preventionDevices = preventionDeviceService.listByLevel();
        return AjaxResult.success(preventionDevices);
    }
}

