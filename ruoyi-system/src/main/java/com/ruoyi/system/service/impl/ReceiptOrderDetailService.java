package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ruoyi.system.domain.ReceiptOrderDetail;
import com.ruoyi.system.mapper.ReceiptOrderDetailMapper;
import com.ruoyi.system.mapper.convert.ReceiptOrderDetailConvert;
import com.ruoyi.system.pojo.query.ReceiptOrderDetailQuery;
import com.ruoyi.system.pojo.vo.ReceiptOrderDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 入库单详情Service业务层处理
 *
 *
 * @author zcc
 */
@Slf4j
@Service
public class ReceiptOrderDetailService {
    @Autowired
    private ReceiptOrderDetailMapper receiptOrderDetailMapper;
    @Autowired
    private ReceiptOrderDetailConvert convert;

    public ReceiptOrderDetailVO toVo(ReceiptOrderDetail item){
        ReceiptOrderDetailVO itemVO = this.toVoReceiptOrderDetail(item);

        List<Long> place = new LinkedList<>();
        if(itemVO.getWarehouseId()!=null){
            place.add(itemVO.getWarehouseId());
        }
        if(itemVO.getAreaId()!=null){
            place.add(itemVO.getAreaId());
        }
        if(itemVO.getRackId()!=null){
            place.add(itemVO.getRackId());
        }
        itemVO.setPlace(place);
        return itemVO;
    }

    public ReceiptOrderDetailVO toVoReceiptOrderDetail(ReceiptOrderDetail source) {
        if (source == null) {
            return null;
        } else {
            ReceiptOrderDetailVO receiptOrderDetailVO = new ReceiptOrderDetailVO();
            receiptOrderDetailVO.setCreateBy(source.getCreateBy());
            receiptOrderDetailVO.setCreateTime(source.getCreateTime());
            receiptOrderDetailVO.setUpdateBy(source.getUpdateBy());
            receiptOrderDetailVO.setUpdateTime(source.getUpdateTime());
            receiptOrderDetailVO.setId(source.getId());
            receiptOrderDetailVO.setReceiptOrderId(source.getReceiptOrderId());
            receiptOrderDetailVO.setItemId(source.getItemId());
            receiptOrderDetailVO.setPlanQuantity(source.getPlanQuantity());
            receiptOrderDetailVO.setRealQuantity(source.getRealQuantity());
            receiptOrderDetailVO.setRackId(source.getRackId());
            receiptOrderDetailVO.setRemark(source.getRemark());
            receiptOrderDetailVO.setWarehouseId(source.getWarehouseId());
            receiptOrderDetailVO.setAreaId(source.getAreaId());
            receiptOrderDetailVO.setReceiptOrderStatus(source.getReceiptOrderStatus());
            receiptOrderDetailVO.setDelFlag(source.getDelFlag());
            return receiptOrderDetailVO;
        }
    }


    public List<ReceiptOrderDetailVO> toVos(List<ReceiptOrderDetail> items){
        List<ReceiptOrderDetailVO> list = this.dos2vos(items);
        list.forEach(itemVO ->{
            List<Long> place = new LinkedList<>();
            if(itemVO.getWarehouseId()!=null){
                place.add(itemVO.getWarehouseId());
            }
            if(itemVO.getAreaId()!=null){
                place.add(itemVO.getAreaId());
            }
            if(itemVO.getRackId()!=null){
                place.add(itemVO.getRackId());
            }
            itemVO.setPlace(place);
        });
        return list;
    }

    public List<ReceiptOrderDetailVO> dos2vos(List<ReceiptOrderDetail> list) {
        if (list == null) {
            return null;
        } else {
            List<ReceiptOrderDetailVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                ReceiptOrderDetail receiptOrderDetail = (ReceiptOrderDetail)var3.next();
                list1.add(this.toVo(receiptOrderDetail));
            }

            return list1;
        }
    }

    /**
     * 查询入库单详情
     *
     * @param id 入库单详情主键
     * @return 入库单详情
     */
    public ReceiptOrderDetail selectById(Long id) {
        return receiptOrderDetailMapper.selectById(id);
    }

    /**
     * 查询入库单详情列表
     *
     * @param query 查询条件
     * @param page 分页条件
     * @return 入库单详情
     */
    public List<ReceiptOrderDetail> selectList(ReceiptOrderDetailQuery query, Pageable page) {
        if (page != null) {
            PageHelper.startPage(page.getPageNumber() + 1, page.getPageSize());
        }
        QueryWrapper<ReceiptOrderDetail> qw = new QueryWrapper<>();
        qw.eq("del_flag",0);
        Long receiptOrderId = query.getReceiptOrderId();
        if (receiptOrderId != null) {
            qw.eq("receipt_order_id", receiptOrderId);
        }
        Long itemId = query.getItemId();
        if (itemId != null) {
            qw.eq("item_id", itemId);
        }
        BigDecimal planQuantity = query.getPlanQuantity();
        if (planQuantity != null) {
            qw.eq("plan_quantity", planQuantity);
        }
        BigDecimal realQuantity = query.getRealQuantity();
        if (realQuantity != null) {
            qw.eq("real_quantity", realQuantity);
        }
        Long rackId = query.getRackId();
        if (rackId != null) {
            qw.eq("rack_id", rackId);
        }
        Long warehouseId = query.getWarehouseId();
        if (warehouseId != null) {
            qw.eq("warehouse_id", warehouseId);
        }
        Long areaId = query.getAreaId();
        if (areaId != null) {
            qw.eq("area_id", areaId);
        }
        Integer receiptOrderStatus = query.getReceiptOrderStatus();
        if (receiptOrderStatus != null) {
            qw.eq("receipt_order_status", receiptOrderStatus);
        }
        return receiptOrderDetailMapper.selectList(qw);
    }

    /**
     * 新增入库单详情
     *
     * @param receiptOrderDetail 入库单详情
     * @return 结果
     */
    public int insert(ReceiptOrderDetail receiptOrderDetail) {
        receiptOrderDetail.setDelFlag(0);
        receiptOrderDetail.setCreateTime(LocalDateTime.now());
        return receiptOrderDetailMapper.insert(receiptOrderDetail);
    }

    /**
     * 修改入库单详情
     *
     * @param receiptOrderDetail 入库单详情
     * @return 结果
     */
    public int update(ReceiptOrderDetail receiptOrderDetail) {
        return receiptOrderDetailMapper.updateById(receiptOrderDetail);
    }

    /**
     * 批量删除入库单详情
     *
     * @param ids 需要删除的入库单详情主键
     * @return 结果
     */
    public int deleteByIds(Long[] ids) {
        return receiptOrderDetailMapper.updateDelFlagByIds(ids);
    }

    /**
     * 删除入库单详情信息
     *
     * @param id 入库单详情主键
     * @return 结果
     */
    public int deleteById(Long id) {
        Long[] ids = {id};
        return receiptOrderDetailMapper.updateDelFlagByIds(ids);
    }
}
