package com.ruoyi.web.controller.basic.view.wms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Warehouse;
import com.ruoyi.system.mapper.convert.WarehouseConvert;
import com.ruoyi.system.pojo.query.WarehouseQuery;
import com.ruoyi.system.pojo.vo.WarehouseVO;
import com.ruoyi.system.service.impl.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 仓库Controller
 *
 * @author zcc
 * @date 2022-08-05
 */
@Api(description = "仓库接口列表")
@RestController
@RequestMapping("/wms/warehouse")
public class WarehouseController extends BaseController {
    @Autowired
    private WarehouseService service;
    @Autowired
    private WarehouseConvert convert;

    @ApiOperation("查询仓库列表")
    //@PreAuthorize("@ss.hasPermi('wms:warehouse:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Warehouse>> list(@RequestBody WarehouseQuery query, Pageable page) {
        List<Warehouse> list = service.selectList(query, page);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page) list).getTotal()));
    }

  /*  @ApiOperation("导出仓库列表")
    @PreAuthorize("@ss.hasPermi('wms:warehouse:export')")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(WarehouseQuery query) {
        List<Warehouse> list = service.selectList(query, null);
        ExcelUtil<WarehouseVO> util = new ExcelUtil<>(WarehouseVO.class);
        util.exportExcel(this.dos2vos(list), "仓库数据");
        return AjaxResult.success();
    }*/

    @ApiOperation("导出仓库列表")
    @PreAuthorize("@ss.hasPermi('wms:warehouse:export')")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, WarehouseQuery query) {
        List<Warehouse> list = service.selectList(query, null);
        ExcelUtil<WarehouseVO> util = new ExcelUtil<>(WarehouseVO.class);
        util.exportExcels(response, this.dos2vos(list), "仓库列表");
    }


    public List<WarehouseVO> dos2vos(List<Warehouse> list) {
        if (list == null) {
            return null;
        } else {
            List<WarehouseVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                Warehouse warehouse = (Warehouse) var3.next();
                list1.add(this.warehouseToWarehouseVO(warehouse));
            }

            return list1;
        }
    }

    protected WarehouseVO warehouseToWarehouseVO(Warehouse warehouse) {
        if (warehouse == null) {
            return null;
        } else {
            WarehouseVO warehouseVO = new WarehouseVO();
            warehouseVO.setCreateBy(warehouse.getCreateBy());
            warehouseVO.setCreateTime(warehouse.getCreateTime());
            warehouseVO.setUpdateBy(warehouse.getUpdateBy());
            warehouseVO.setUpdateTime(warehouse.getUpdateTime());
            warehouseVO.setId(warehouse.getId());
            warehouseVO.setWarehouseNo(warehouse.getWarehouseNo());
            warehouseVO.setWarehouseName(warehouse.getWarehouseName());
            warehouseVO.setRemark(warehouse.getRemark());
            return warehouseVO;
        }
    }


    @ApiOperation("获取仓库详细信息")
    @PreAuthorize("@ss.hasPermi('wms:warehouse:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Warehouse> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增仓库")
    @PreAuthorize("@ss.hasPermi('wms:warehouse:add')")
    @Log(title = "仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(service.insert(warehouse));
    }

    @ApiOperation("修改仓库")
    @PreAuthorize("@ss.hasPermi('wms:warehouse:edit')")
    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(service.update(warehouse));
    }

    @ApiOperation("删除仓库")
    @PreAuthorize("@ss.hasPermi('wms:warehouse:remove')")
    @Log(title = "仓库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
