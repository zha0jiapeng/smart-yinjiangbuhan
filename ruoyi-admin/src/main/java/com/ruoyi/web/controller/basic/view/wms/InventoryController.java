package com.ruoyi.web.controller.basic.view.wms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.mapper.convert.InventoryConvert;
import com.ruoyi.system.domain.Inventory;
import com.ruoyi.system.pojo.query.InventoryQuery;
import com.ruoyi.system.pojo.vo.InventoryVO;
import com.ruoyi.system.service.impl.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * 库存Controller
 * 
 * @author zcc
 * @date 2022-08-05
 */
@Api(description ="库存接口列表")
@RestController
@RequestMapping("/wms/inventory")
public class InventoryController extends BaseController {
    @Autowired
    private InventoryService service;
    @Autowired
    private InventoryConvert convert;

    @ApiOperation("查询库存列表")
    @PreAuthorize("@ss.hasPermi('wms:inventory:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<InventoryVO>> list(@RequestBody InventoryQuery query, Pageable page) {
        return ResponseEntity.ok(service.queryPage(query, page));
    }

    @ApiOperation("导出库存列表")
    @PreAuthorize("@ss.hasPermi('wms:inventory:export')")
    @Log(title = "库存", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, InventoryQuery query) {
        List<Inventory> list;
        list = service.selectList(query, null);
        ExcelUtil<InventoryVO> util = new ExcelUtil<>(InventoryVO.class);
        util.exportExcels(response, convert.dos2vos(list), "库存数据");
    }

    @ApiOperation("获取库存详细信息")
    @PreAuthorize("@ss.hasPermi('wms:inventory:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Inventory> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增库存")
    @PreAuthorize("@ss.hasPermi('wms:inventory:add')")
    @Log(title = "库存", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(service.insert(inventory));
    }

    @ApiOperation("修改库存")
    @PreAuthorize("@ss.hasPermi('wms:inventory:edit')")
    @Log(title = "库存", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(service.update(inventory));
    }

    @ApiOperation("删除库存")
    @PreAuthorize("@ss.hasPermi('wms:inventory:remove')")
    @Log(title = "库存", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
