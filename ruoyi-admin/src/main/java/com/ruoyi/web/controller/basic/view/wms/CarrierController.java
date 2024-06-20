package com.ruoyi.web.controller.basic.view.wms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Carrier;
import com.ruoyi.system.mapper.convert.CarrierConvert;
import com.ruoyi.system.pojo.query.CarrierQuery;
import com.ruoyi.system.pojo.vo.CarrierVO;
import com.ruoyi.system.service.impl.CarrierService;
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
 * 承运商Controller
 *
 * @author zcc
 * @date 2022-08-05
 */
@Api(description = "承运商接口列表")
@RestController
@RequestMapping("/wms/carrier")
public class CarrierController extends BaseController {
    @Autowired
    private CarrierService service;
    @Autowired
    private CarrierConvert convert;

    @ApiOperation("查询承运商列表")
    @PreAuthorize("@ss.hasPermi('wms:carrier:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Carrier>> list(@RequestBody CarrierQuery query, Pageable page) {
        List<Carrier> list = service.selectList(query, page);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page) list).getTotal()));
    }

    @ApiOperation("导出承运商列表")
    @PreAuthorize("@ss.hasPermi('wms:carrier:export')")
    @Log(title = "承运商", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, CarrierQuery query) {
        List<Carrier> list = service.selectList(query, null);
        ExcelUtil<CarrierVO> util = new ExcelUtil<>(CarrierVO.class);
        util.exportExcels(response, this.dos2vos(list), "承运商数据");
    }

    public List<CarrierVO> dos2vos(List<Carrier> list) {
        if (list == null) {
            return null;
        } else {
            List<CarrierVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                Carrier carrier = (Carrier) var3.next();
                list1.add(this.carrierToCarrierVO(carrier));
            }

            return list1;
        }
    }

    protected CarrierVO carrierToCarrierVO(Carrier carrier) {
        if (carrier == null) {
            return null;
        } else {
            CarrierVO carrierVO = new CarrierVO();
            carrierVO.setCreateBy(carrier.getCreateBy());
            carrierVO.setCreateTime(carrier.getCreateTime());
            carrierVO.setUpdateBy(carrier.getUpdateBy());
            carrierVO.setUpdateTime(carrier.getUpdateTime());
            carrierVO.setId(carrier.getId());
            carrierVO.setCarrierNo(carrier.getCarrierNo());
            carrierVO.setCarrierName(carrier.getCarrierName());
            carrierVO.setAddress(carrier.getAddress());
            carrierVO.setMobile(carrier.getMobile());
            carrierVO.setTel(carrier.getTel());
            carrierVO.setContact(carrier.getContact());
            carrierVO.setLevel(carrier.getLevel());
            carrierVO.setEmail(carrier.getEmail());
            carrierVO.setRemark(carrier.getRemark());
            return carrierVO;
        }
    }

    @ApiOperation("获取承运商详细信息")
    @PreAuthorize("@ss.hasPermi('wms:carrier:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Carrier> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增承运商")
    @PreAuthorize("@ss.hasPermi('wms:carrier:add')")
    @Log(title = "承运商", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Carrier carrier) {
        return ResponseEntity.ok(service.insert(carrier));
    }

    @ApiOperation("修改承运商")
    @PreAuthorize("@ss.hasPermi('wms:carrier:edit')")
    @Log(title = "承运商", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Carrier carrier) {
        return ResponseEntity.ok(service.update(carrier));
    }

    @ApiOperation("删除承运商")
    @PreAuthorize("@ss.hasPermi('wms:carrier:remove')")
    @Log(title = "承运商", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
