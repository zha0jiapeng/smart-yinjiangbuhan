package com.ruoyi.web.controller.basic.view.wms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Area;
import com.ruoyi.system.mapper.convert.AreaConvert;
import com.ruoyi.system.pojo.query.AreaQuery;
import com.ruoyi.system.pojo.vo.AreaVO;
import com.ruoyi.system.service.impl.AreaService;
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
 * 货区Controller
 *
 * @author zcc
 * @date 2022-08-05
 */
@Api(description = "货区接口列表")
@RestController
@RequestMapping("/wms/area")
public class AreaController extends BaseController {
    @Autowired
    private AreaService service;
    @Autowired
    private AreaConvert convert;

    @ApiOperation("查询货区列表")
    //@PreAuthorize("@ss.hasPermi('wms:area:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Area>> list(@RequestBody AreaQuery query, Pageable page) {
        List<Area> list = service.selectList(query, page);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page) list).getTotal()));
    }

    @ApiOperation("导出货区列表")
    @PreAuthorize("@ss.hasPermi('wms:area:export')")
    @Log(title = "货区", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, AreaQuery query) {
        List<Area> list = service.selectList(query, null);
        ExcelUtil<AreaVO> util = new ExcelUtil<>(AreaVO.class);
        util.exportExcels(response, this.dos2vos(list), "货区数据");
    }

    public List<AreaVO> dos2vos(List<Area> list) {
        if (list == null) {
            return null;
        } else {
            List<AreaVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                Area area = (Area) var3.next();
                list1.add(this.areaToAreaVO(area));
            }

            return list1;
        }
    }

    public AreaVO areaToAreaVO(Area area) {
        if (area == null) {
            return null;
        } else {
            AreaVO areaVO = new AreaVO();
            areaVO.setCreateBy(area.getCreateBy());
            areaVO.setCreateTime(area.getCreateTime());
            areaVO.setUpdateBy(area.getUpdateBy());
            areaVO.setUpdateTime(area.getUpdateTime());
            areaVO.setId(area.getId());
            areaVO.setAreaNo(area.getAreaNo());
            areaVO.setAreaName(area.getAreaName());
            areaVO.setWarehouseId(area.getWarehouseId());
            areaVO.setRemark(area.getRemark());
            return areaVO;
        }
    }

    @ApiOperation("获取货区详细信息")
    @PreAuthorize("@ss.hasPermi('wms:area:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Area> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增货区")
    @PreAuthorize("@ss.hasPermi('wms:area:add')")
    @Log(title = "货区", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Area area) {
        return ResponseEntity.ok(service.insert(area));
    }

    @ApiOperation("修改货区")
    @PreAuthorize("@ss.hasPermi('wms:area:edit')")
    @Log(title = "货区", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Area area) {
        return ResponseEntity.ok(service.update(area));
    }

    @ApiOperation("删除货区")
    @PreAuthorize("@ss.hasPermi('wms:area:remove')")
    @Log(title = "货区", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
