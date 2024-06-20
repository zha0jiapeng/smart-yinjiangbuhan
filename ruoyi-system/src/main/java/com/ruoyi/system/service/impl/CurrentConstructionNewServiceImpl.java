package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.IntegerConstant;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.ScheduleTbmSegment;
import com.ruoyi.system.entity.CurrentConstructionNew;
import com.ruoyi.system.mapper.CurrentConstructionNewMapper;
import com.ruoyi.system.service.CurrentConstructionNewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrentConstructionNewServiceImpl
        extends ServiceImpl<CurrentConstructionNewMapper, CurrentConstructionNew>
        implements CurrentConstructionNewService {

    @Autowired
    private CurrentConstructionNewMapper currentConstructionNewMapper;


    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void calculate(ScheduleTbmSegment scheduleTbmSegment) {
     /*   BigDecimal value = new BigDecimal("1.4");
        BigDecimal divide = scheduleTbmSegment.getTbmRealityPrice().divide(value, 0, RoundingMode.HALF_UP);
        int index = divide.intValue();*/

        BigDecimal schedulePlanPrice = scheduleTbmSegment.getScheduleRealityPrice();
        int index = schedulePlanPrice.intValue();

        if (index == IntegerConstant.ZERO) {
            return;
        }

        QueryWrapper<CurrentConstructionNew> wrapper = new QueryWrapper<>();
        wrapper.isNull("construction_date");
        wrapper.orderByAsc("id");
        wrapper.last("LIMIT " + index);

        List<CurrentConstructionNew> currentConstructionNews = currentConstructionNewMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(currentConstructionNews)) {
            List<Integer> collect = currentConstructionNews.stream().map(CurrentConstructionNew::getId).collect(Collectors.toList());
            CurrentConstructionNew currentConstructionNewStart = currentConstructionNews.get(IntegerConstant.ZERO);
            CurrentConstructionNew currentConstructionNewEnd = currentConstructionNews.get(currentConstructionNews.size() - IntegerConstant.ONE);

            currentConstructionNews.forEach(var -> {
                var.setTbmRealityPrice(scheduleTbmSegment.getTbmRealityPrice());
                var.setScheduleRealityPrice(new BigDecimal(index));
                var.setConstructionDate(scheduleTbmSegment.getDayBase());

                if (var.getId().equals(currentConstructionNewStart.getId())) {
                    var.setPileNumberEnd(currentConstructionNewEnd.getPileNumber());
                    var.setDataFlag(IntegerConstant.ONE);
                    var.setIds(JSON.toJSONString(collect));
                }

                ArrayList<Object> arrayList = new ArrayList<>();
                var.setBasicValueInfoTop(JSON.toJSONString(arrayList));
                var.setBasicValueInfoBottom(JSON.toJSONString(arrayList));
                var.setBasicValueInfoLeft(JSON.toJSONString(arrayList));
                var.setBasicValueInfoRight(JSON.toJSONString(arrayList));

                var.setHeadFlag(IntegerConstant.ONE);
                var.setBodyFlag(IntegerConstant.ONE);

//                var.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
                var.setCreatedDate(new Date());
                var.setYn(YnEnum.Y.getCode());
            });

            updateBatchById(currentConstructionNews);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCurrentConstructionNew(List<CurrentConstructionNew> currentConstructionNews) {
        this.updateBatchById(currentConstructionNews);

        CurrentConstructionNew constructionNew = currentConstructionNews.stream().findFirst().orElse(new CurrentConstructionNew());
        QueryWrapper<CurrentConstructionNew> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", constructionNew.getPid());
        CurrentConstructionNew one = this.getOne(queryWrapper);
        List<Integer> idList = JSON.parseArray(one.getIds(), Integer.class);

        QueryWrapper<CurrentConstructionNew> newQueryWrapper = new QueryWrapper<>();
        newQueryWrapper.in("id", idList);
        List<CurrentConstructionNew> listPage = this.list(newQueryWrapper);
        if (!CollectionUtils.isEmpty(listPage)) {
            List<Integer> collect = listPage.stream().map(CurrentConstructionNew::getBodyFlag).collect(Collectors.toList());
            int status = 1;

            if (collect.contains(IntegerConstant.ONE) && collect.contains(IntegerConstant.TWO)) {
                //待补充
                status = 2;
            } else if (!collect.contains(IntegerConstant.ONE)) {
                //已完成
                status = 3;
            }

            List<CurrentConstructionNew> currentConstructionNewsList = new ArrayList<>();
            for (CurrentConstructionNew currentConstructionNewFor : listPage) {
                CurrentConstructionNew currentConstructionNew = new CurrentConstructionNew();
                currentConstructionNew.setId(currentConstructionNewFor.getId());
                currentConstructionNew.setHeadFlag(status);
                currentConstructionNew.setModifyBy(SecurityUtils.getLoginUser().getUsername());
                currentConstructionNew.setModifyDate(new Date());
                currentConstructionNewsList.add(currentConstructionNew);
            }

            this.updateBatchById(currentConstructionNewsList);
        }
    }

}
