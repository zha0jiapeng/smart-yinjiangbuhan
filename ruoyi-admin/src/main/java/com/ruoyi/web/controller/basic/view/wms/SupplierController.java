package com.ruoyi.web.controller.basic.view.wms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Supplier;
import com.ruoyi.system.mapper.convert.SupplierConvert;
import com.ruoyi.system.pojo.query.SupplierQuery;
import com.ruoyi.system.pojo.vo.SupplierVO;
import com.ruoyi.system.service.impl.SupplierService;
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
 * 供应商Controller
 * 
 * @author zcc
 * @date 2022-08-05
 */
@Api(description ="供应商接口列表")
@RestController
@RequestMapping("/wms/supplier")
public class SupplierController extends BaseController {
    @Autowired
    private SupplierService service;
    @Autowired
    private SupplierConvert convert;

    @ApiOperation("查询供应商列表")
    //@PreAuthorize("@ss.hasPermi('wms:supplier:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Supplier>> list(@RequestBody SupplierQuery query, Pageable page) {
        List<Supplier> list = service.selectList(query, page);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page)list).getTotal()));
    }

   /* @ApiOperation("导出供应商列表")
    @PreAuthorize("@ss.hasPermi('wms:supplier:export')")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public ResponseEntity<String> export(SupplierQuery query) {
        List<Supplier> list = service.selectList(query, null);
        ExcelUtil<SupplierVO> util = new ExcelUtil<>(SupplierVO.class);
        return ResponseEntity.ok(util.writeExcel(this.dos2vos(list), "供应商数据"));
    }*/

    @ApiOperation("导出供应商列表")
    @PreAuthorize("@ss.hasPermi('wms:supplier:export')")
    @Log(title = "供应商", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, SupplierQuery query) {
        List<Supplier> list = service.selectList(query, null);
        ExcelUtil<SupplierVO> util = new ExcelUtil<>(SupplierVO.class);
        util.exportExcels(response, this.dos2vos(list), "供应商列表");
    }

    public List<SupplierVO> dos2vos(List<Supplier> list) {
        if (list == null) {
            return null;
        } else {
            List<SupplierVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Supplier supplier = (Supplier)var3.next();
                list1.add(this.supplierToSupplierVO(supplier));
            }

            return list1;
        }
    }

    protected SupplierVO supplierToSupplierVO(Supplier supplier) {
        if (supplier == null) {
            return null;
        } else {
            SupplierVO supplierVO = new SupplierVO();
            supplierVO.setCreateBy(supplier.getCreateBy());
            supplierVO.setCreateTime(supplier.getCreateTime());
            supplierVO.setUpdateBy(supplier.getUpdateBy());
            supplierVO.setUpdateTime(supplier.getUpdateTime());
            supplierVO.setId(supplier.getId());
            supplierVO.setSupplierNo(supplier.getSupplierNo());
            supplierVO.setSupplierName(supplier.getSupplierName());
            supplierVO.setAddress(supplier.getAddress());
            supplierVO.setMobileNo(supplier.getMobileNo());
            supplierVO.setTelNo(supplier.getTelNo());
            supplierVO.setContact(supplier.getContact());
            supplierVO.setLevel(supplier.getLevel());
            supplierVO.setEmail(supplier.getEmail());
            supplierVO.setRemark(supplier.getRemark());
            return supplierVO;
        }
    }

    @ApiOperation("获取供应商详细信息")
    @PreAuthorize("@ss.hasPermi('wms:supplier:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Supplier> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增供应商")
    @PreAuthorize("@ss.hasPermi('wms:supplier:add')")
    @Log(title = "供应商", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(service.insert(supplier));
    }

    @ApiOperation("修改供应商")
    @PreAuthorize("@ss.hasPermi('wms:supplier:edit')")
    @Log(title = "供应商", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(service.update(supplier));
    }

    @ApiOperation("删除供应商")
    @PreAuthorize("@ss.hasPermi('wms:supplier:remove')")
    @Log(title = "供应商", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
