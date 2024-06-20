package com.ruoyi.web.controller.basic.view.wms;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Item;
import com.ruoyi.system.mapper.convert.ItemConvert;
import com.ruoyi.system.pojo.query.ItemQuery;
import com.ruoyi.system.pojo.vo.ItemVO;
import com.ruoyi.system.service.impl.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * 物料Controller
 *
 * @author zcc
 * @date 2022-08-05
 */
@Slf4j
@Api(description = "物料接口列表")
@RestController
@RequestMapping("/wms/item")
public class ItemController extends BaseController {
    @Autowired
    private ItemService service;
    @Autowired
    private ItemConvert convert;

    @ApiOperation("查询物料列表")
    @PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<ItemVO>> list(@RequestBody ItemQuery query, Pageable page) {
        if (null != query && !StringUtils.isEmpty(query.getItemName())) {
            query.setItemNameLike(query.getItemName());
        }
        log.info("查询物料列表 参数:{}", JSON.toJSONString(query));
        List<Item> items = service.selectList(query, page);
        List<ItemVO> list = service.toVos(items);

        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page) items).getTotal()));
    }

    @ApiOperation("查询物料列表")
    @PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/all")
    public ResponseEntity<List<ItemVO>> all(@RequestBody ItemQuery query) {
        List<Item> items = service.selectList(query, null);
        return ResponseEntity.ok(convert.dos2vos(items));
    }

//    @ApiOperation("导出物料列表")
//    @PreAuthorize("@ss.hasPermi('wms:item:export')")
//    @Log(title = "物料", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public ResponseEntity<String> export(ItemQuery query) {
//        List<Item> list = service.selectList(query, null);
//        ExcelUtil<ItemVO> util = new ExcelUtil<>(ItemVO.class);
//        return ResponseEntity.ok(util.writeExcel(this.dos2vos(list), "物料数据"));
//    }

    @ApiOperation("导出物料列表")
    @PreAuthorize("@ss.hasPermi('wms:item:export')")
    @Log(title = "物料", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, ItemQuery query) {
        List<Item> list = service.selectList(query, null);
        ExcelUtil<ItemVO> util = new ExcelUtil<>(ItemVO.class);
        util.exportExcels(response, this.dos2vos(list), "物料列表");
    }

    public List<ItemVO> dos2vos(List<Item> list) {
        if (list == null) {
            return null;
        } else {
            List<ItemVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                Item item = (Item) var3.next();
                list1.add(this.toVo(item));
            }

            return list1;
        }
    }

    public ItemVO toVo(Item source) {
        if (source == null) {
            return null;
        } else {
            ItemVO itemVO = new ItemVO();
            itemVO.setCreateBy(source.getCreateBy());
            itemVO.setCreateTime(source.getCreateTime());
            itemVO.setUpdateBy(source.getUpdateBy());
            itemVO.setUpdateTime(source.getUpdateTime());
            itemVO.setId(source.getId());
            itemVO.setItemNo(source.getItemNo());
            itemVO.setItemName(source.getItemName());
            itemVO.setItemType(source.getItemType());
            itemVO.setUnit(source.getUnit());
            itemVO.setRackId(source.getRackId());
            itemVO.setAreaId(source.getAreaId());
            itemVO.setWarehouseId(source.getWarehouseId());
            itemVO.setQuantity(source.getQuantity());
            itemVO.setExpiryDate(source.getExpiryDate());
            itemVO.setRemark(source.getRemark());
            itemVO.setDelFlag(source.getDelFlag());
            return itemVO;
        }
    }

    @ApiOperation("获取物料详细信息")
    @PreAuthorize("@ss.hasPermi('wms:item:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemVO> getInfo(@PathVariable("id") Long id) {
        Item item = service.selectById(id);
        ItemVO itemVO = service.toVo(item);
        return ResponseEntity.ok(itemVO);
    }

    @ApiOperation("新增物料")
    @PreAuthorize("@ss.hasPermi('wms:item:add')")
    @Log(title = "物料", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Item item) {
        return ResponseEntity.ok(service.insert(item));
    }

    @ApiOperation("修改物料")
    @PreAuthorize("@ss.hasPermi('wms:item:edit')")
    @Log(title = "物料", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Item item) {
        return ResponseEntity.ok(service.update(item));
    }

    @ApiOperation("删除物料")
    @PreAuthorize("@ss.hasPermi('wms:item:remove')")
    @Log(title = "物料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
}
