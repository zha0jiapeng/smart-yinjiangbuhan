package com.ruoyi.web.controller.basic.view.wms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Rack;
import com.ruoyi.system.mapper.convert.RackConvert;
import com.ruoyi.system.pojo.query.RackQuery;
import com.ruoyi.system.pojo.vo.RackVO;
import com.ruoyi.system.service.impl.RackService;
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
 * 货架Controller
 *
 * @author zcc
 * @date 2022-08-05
 */
@Api(description = "货架接口列表")
@RestController
@RequestMapping("/wms/rack")
public class RackController extends BaseController {
    @Autowired
    private RackService service;
    @Autowired
    private RackConvert convert;

    @ApiOperation("查询货架列表")
    //@PreAuthorize("@ss.hasPermi('wms:rack:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Rack>> list(@RequestBody RackQuery query, Pageable page) {
        List<Rack> list = service.selectList(query, page);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page) list).getTotal()));
    }

    @ApiOperation("导出货架列表")
    @PreAuthorize("@ss.hasPermi('wms:rack:export')")
    @Log(title = "货架", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, RackQuery query) {
        List<Rack> list = service.selectList(query, null);
        ExcelUtil<RackVO> util = new ExcelUtil<>(RackVO.class);
        util.exportExcels(response, this.dos2vos(list), "货架数据");
    }

    public List<RackVO> dos2vos(List<Rack> list) {
        if (list == null) {
            return null;
        } else {
            List<RackVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                Rack rack = (Rack) var3.next();
                list1.add(this.rackToRackVO(rack));
            }

            return list1;
        }
    }

    protected RackVO rackToRackVO(Rack rack) {
        if (rack == null) {
            return null;
        } else {
            RackVO rackVO = new RackVO();
            rackVO.setCreateBy(rack.getCreateBy());
            rackVO.setCreateTime(rack.getCreateTime());
            rackVO.setUpdateBy(rack.getUpdateBy());
            rackVO.setUpdateTime(rack.getUpdateTime());
            rackVO.setId(rack.getId());
            rackVO.setRackNo(rack.getRackNo());
            rackVO.setRackName(rack.getRackName());
            rackVO.setAreaId(rack.getAreaId());
            rackVO.setWarehouseId(rack.getWarehouseId());
            rackVO.setRemark(rack.getRemark());
            return rackVO;
        }
    }

    @ApiOperation("获取货架详细信息")
    @PreAuthorize("@ss.hasPermi('wms:rack:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Rack> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增货架")
    @PreAuthorize("@ss.hasPermi('wms:rack:add')")
    @Log(title = "货架", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Rack rack) {
        return ResponseEntity.ok(service.insert(rack));
    }

    @ApiOperation("修改货架")
    @PreAuthorize("@ss.hasPermi('wms:rack:edit')")
    @Log(title = "货架", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Rack rack) {
        return ResponseEntity.ok(service.update(rack));
    }

    @ApiOperation("删除货架")
    @PreAuthorize("@ss.hasPermi('wms:rack:remove')")
    @Log(title = "货架", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
