package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.constant.CommonConstant;
import com.ruoyi.system.domain.InventoryHistory;
import com.ruoyi.system.mapper.InventoryHistoryMapper;
import com.ruoyi.system.mapper.convert.InventoryHistoryConvert;
import com.ruoyi.system.pojo.query.InventoryHistoryQuery;
import com.ruoyi.system.pojo.vo.InventoryHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 库存记录Service业务层处理
 *
 * @author zcc
 */
@Service
public class InventoryHistoryService {
    @Autowired
    private InventoryHistoryMapper inventoryHistoryMapper;
    @Autowired
    private InventoryHistoryConvert inventoryHistoryConvert;
    @Autowired
    private InventoryService inventoryService;

    /**
     * 查询库存记录
     *
     * @param id 库存记录主键
     * @return 库存记录
     */
    public InventoryHistory selectById(Long id) {
        return inventoryHistoryMapper.selectById(id);
    }

    /**
     * 查询库存记录列表
     *
     * @param query 查询条件
     * @return 库存记录
     */
    public List<InventoryHistoryVO> selectList(InventoryHistoryQuery query) {
        List<InventoryHistory> list = queryInventoryHistories(query);
        List<InventoryHistoryVO> res = this.dos2vos(list);
        inventoryService.injectAreaAndItemInfo(res);
        return res;
    }

    private List<InventoryHistory> queryInventoryHistories(InventoryHistoryQuery query) {
        QueryWrapper<InventoryHistory> qw = new QueryWrapper<>();
        qw.eq("del_flag", 0);
        Long formId = query.getFormId();
        if (formId != null) {
            qw.eq("form_id", formId);
        }
        Integer formType = query.getFormType();
        if (formType != null) {
            qw.eq("form_type", formType);
        }
        Long itemId = query.getItemId();
        if (itemId != null) {
            qw.eq("item_id", itemId);
        }
        if (query.getWarehouseId() != null) {
            qw.eq("warehouse_id", query.getWarehouseId());
        }
        if (query.getAreaId() != null) {
            qw.eq("area_id", query.getAreaId());
        }
        Long rackId = query.getRackId();
        if (rackId != null) {
            qw.eq("rack_id", rackId);
        }
        BigDecimal quantity = query.getQuantity();
        if (quantity != null) {
            qw.eq("quantity", quantity);
        }
        List<InventoryHistory> list = inventoryHistoryMapper.selectList(qw);
        return list;
    }

    public Page<InventoryHistoryVO> selectList(InventoryHistoryQuery query, Pageable page) {
        PageHelper.startPage(page.getPageNumber() + 1, page.getPageSize());
        List<InventoryHistory> list = queryInventoryHistories(query);
        List<InventoryHistoryVO> res = this.dos2vos(list);
        inventoryService.injectAreaAndItemInfo(res);
        return new PageImpl<>(res, page, ((com.github.pagehelper.Page)list).getTotal());
    }

    public List<InventoryHistoryVO> dos2vos(List<InventoryHistory> list) {
        if (list == null) {
            return null;
        } else {
            List<InventoryHistoryVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                InventoryHistory inventoryHistory = (InventoryHistory)var3.next();
                list1.add(this.inventoryHistoryToInventoryHistoryVO(inventoryHistory));
            }

            return list1;
        }
    }

    protected InventoryHistoryVO inventoryHistoryToInventoryHistoryVO(InventoryHistory inventoryHistory) {
        if (inventoryHistory == null) {
            return null;
        } else {
            InventoryHistoryVO inventoryHistoryVO = new InventoryHistoryVO();
            inventoryHistoryVO.setCreateBy(inventoryHistory.getCreateBy());
            inventoryHistoryVO.setCreateTime(inventoryHistory.getCreateTime());
            inventoryHistoryVO.setUpdateBy(inventoryHistory.getUpdateBy());
            inventoryHistoryVO.setUpdateTime(inventoryHistory.getUpdateTime());
            inventoryHistoryVO.setId(inventoryHistory.getId());
            inventoryHistoryVO.setFormId(inventoryHistory.getFormId());
            inventoryHistoryVO.setFormType(inventoryHistory.getFormType());
            inventoryHistoryVO.setItemId(inventoryHistory.getItemId());
            inventoryHistoryVO.setRackId(inventoryHistory.getRackId());
            inventoryHistoryVO.setWarehouseId(inventoryHistory.getWarehouseId());
            inventoryHistoryVO.setAreaId(inventoryHistory.getAreaId());
            inventoryHistoryVO.setQuantity(inventoryHistory.getQuantity());
            inventoryHistoryVO.setRemark(inventoryHistory.getRemark());
            return inventoryHistoryVO;
        }
    }

    /**
     * 新增库存记录
     *
     * @param inventoryHistory 库存记录
     * @return 结果
     */
    public int insert(InventoryHistory inventoryHistory) {
        inventoryHistory.setDelFlag(0);
        inventoryHistory.setCreateTime(LocalDateTime.now());
        return inventoryHistoryMapper.insert(inventoryHistory);
    }

    /**
     * 修改库存记录
     *
     * @param inventoryHistory 库存记录
     * @return 结果
     */
    public int update(InventoryHistory inventoryHistory) {
        return inventoryHistoryMapper.updateById(inventoryHistory);
    }

    /**
     * 批量删除库存记录
     *
     * @param ids 需要删除的库存记录主键
     * @return 结果
     */
    public int deleteByIds(Long[] ids) {
        return inventoryHistoryMapper.updateDelFlagByIds(ids);
    }

    /**
     * 删除库存记录信息
     *
     * @param id 库存记录主键
     * @return 结果
     */
    public int deleteById(Long id) {
        Long[] ids = {id};
        return inventoryHistoryMapper.updateDelFlagByIds(ids);
    }

    public int batchInsert(List<InventoryHistory> list) {
        if (CollUtil.isEmpty(list)) {
            return 0;
        }
        int re = 0;
        for (int s = 0; s < list.size(); s += CommonConstant.BATCH_SIZE) {
            re += inventoryHistoryMapper.batchInsert(list.subList(s, Math.min(s + CommonConstant.BATCH_SIZE, list.size())));
        }
        return re;
    }
}
