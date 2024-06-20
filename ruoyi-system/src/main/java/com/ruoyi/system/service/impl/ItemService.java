package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.utils.CreateQrUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.Item;
import com.ruoyi.common.utils.QrContent;
import com.ruoyi.system.mapper.ItemMapper;
import com.ruoyi.system.mapper.convert.ItemConvert;
import com.ruoyi.system.pojo.query.ItemQuery;
import com.ruoyi.system.pojo.vo.ItemVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 物料Service业务层处理
 *
 * @author zcc
 */
@Slf4j
@Service
public class ItemService {

    @Resource
    private ItemMapper itemMapper;
    @Resource
    private ItemConvert convert;
    @Value("${fastdfs.query.url}")
    private String url;

    @Autowired
    private CreateQrUtils createQrUtils;

    public List<ItemVO> toVos(List<Item> items) {
        List<ItemVO> list = this.dos2vos(items);
        list.forEach(itemVO -> {
            List<Long> place = new LinkedList<>();
            if (itemVO.getWarehouseId() != null) {
                place.add(itemVO.getWarehouseId());
            }
            if (itemVO.getAreaId() != null) {
                place.add(itemVO.getAreaId());
            }
            if (itemVO.getRackId() != null) {
                place.add(itemVO.getRackId());
            }
            itemVO.setPlace(place);
        });
        return list;
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

    public ItemVO toVo(Item item) {
        ItemVO itemVO = this.toVoConvert(item);

        List<Long> place = new LinkedList<>();
        if (itemVO.getWarehouseId() != null) {
            place.add(itemVO.getWarehouseId());
        }
        if (itemVO.getAreaId() != null) {
            place.add(itemVO.getAreaId());
        }
        if (itemVO.getRackId() != null) {
            place.add(itemVO.getRackId());
        }
        itemVO.setPlace(place);
        return itemVO;
    }

    public ItemVO toVoConvert(Item source) {
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
            itemVO.setZxingUrl(url + source.getZxingUrl());
            itemVO.setStatusBase(source.getStatusBase());
            return itemVO;
        }
    }

    /**
     * 查询物料
     *
     * @param id 物料主键
     * @return 物料
     */
    public Item selectById(Long id) {
        return itemMapper.selectById(id);
    }

    /**
     * 查询物料列表
     *
     * @param query 查询条件
     * @param page  分页条件
     * @return 物料
     */
    public List<Item> selectList(ItemQuery query, Pageable page) {
        if (page != null) {
            PageHelper.startPage(page.getPageNumber() + 1, page.getPageSize());
        }
        QueryWrapper<Item> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        if (!StrUtil.isEmpty(query.getSearch())) {
            String search = query.getSearch();
            qw.eq("create_by", SecurityUtils.getUserId());
            qw.and((qw1) -> {
                qw1 = qw1.like("item_no", search)
                        .or()
                        .like("item_name", search);
                if (search.matches("^\\d+$")) {
                    qw1.eq("id", Long.valueOf(search));
                }
            });
            return itemMapper.selectList(qw);
        }
        if (!CollUtil.isEmpty(query.getIds())) {
            qw.in("id", query.getIds());
        }
        qw.eq("del_flag", 0);
        String itemNo = query.getItemNo();
        if (!StringUtils.isEmpty(itemNo)) {
            qw.eq("item_no", itemNo);
        }

        String itemNameLike = query.getItemNameLike();
        if (!StringUtils.isEmpty(itemNameLike)) {
            qw.like("item_name", itemNameLike);
        }
        String itemType = query.getItemType();
        if (!StringUtils.isEmpty(itemType)) {
            qw.eq("item_type", itemType);
        }
        String unit = query.getUnit();
        if (!StringUtils.isEmpty(unit)) {
            qw.eq("unit", unit);
        }
        Long rackId = query.getRackId();
        if (rackId != null) {
            qw.eq("rack_id", rackId);
        }
        Long areaId = query.getAreaId();
        if (areaId != null) {
            qw.eq("area_id", areaId);
        }
        Long warehouseId = query.getWarehouseId();
        if (warehouseId != null) {
            qw.eq("warehouse_id", warehouseId);
        }
        BigDecimal quantity = query.getQuantity();
        if (quantity != null) {
            qw.eq("quantity", quantity);
        }
        LocalDateTime expiryDate = query.getExpiryDate();
        if (expiryDate != null) {
            qw.eq("expiry_date", expiryDate);
        }
        return itemMapper.selectList(qw);
    }

    /**
     * 新增物料
     *
     * @param item 物料
     * @return 结果
     */
    public int insert(Item item) {
        item.setDelFlag(0);
        item.setCreateTime(LocalDateTime.now());
        int insert = itemMapper.insert(item);
        QrContent qrContent = new QrContent();
        qrContent.setPath("/pages/wms/in-out-input");

        QrContent.Content content = new QrContent.Content();
        content.setProductCode(item.getId().toString());
        content.setProductName(item.getItemName());
        qrContent.setData(content);

        String qr = createQrUtils.createQr(qrContent, item.getItemNo());
        item.setZxingUrl(qr);

        Item update = new Item();
        update.setId(item.getId());
        update.setZxingUrl(qr);
        itemMapper.updateInfo(update);
        return insert;
    }

    /**
     * 修改物料
     *
     * @param item 物料
     * @return 结果
     */
    public int update(Item item) {

        boolean contains = item.getZxingUrl().contains(url);
        if (contains) {
            item.setZxingUrl(item.getZxingUrl().replaceAll(url, ""));
        }

        return itemMapper.updateById(item);
    }

    /**
     * 批量删除物料
     *
     * @param ids 需要删除的物料主键
     * @return 结果
     */
    public int deleteByIds(Long[] ids) {
        return itemMapper.updateDelFlagByIds(ids);
    }

    /**
     * 删除物料信息
     *
     * @param id 物料主键
     * @return 结果
     */
    public int deleteById(Long id) {
        Long[] ids = {id};
        return itemMapper.updateDelFlagByIds(ids);
    }

    public List<Item> selectByIdIn(Collection<Long> ids) {
        QueryWrapper<Item> qw = new QueryWrapper<>();
        qw.in("id", ids);
        return itemMapper.selectList(qw);
    }
}
