package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.ScheduleHumanUpload;
import com.ruoyi.system.entity.CurrentConstruction;
import com.ruoyi.system.mapper.CurrentConstructionMapper;
import com.ruoyi.system.mapper.ScheduleHumanUploadMapper;
import com.ruoyi.system.service.CurrentConstructionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrentConstructionServiceImpl
        extends ServiceImpl<CurrentConstructionMapper, CurrentConstruction>
        implements CurrentConstructionService {

    @Resource
    private CurrentConstructionMapper currentConstructionMapper;
    @Resource
    private ScheduleHumanUploadMapper scheduleHumanUploadMapper;

    @Override
    public void updateCurrentConstruction(CurrentConstruction currentConstruction) {
        currentConstructionMapper.updateCurrentConstruction(currentConstruction);
    }

    @Override
    @Scheduled(cron = "0 0/5 * * * ?")
    public void bimNewListSchedule() {
        QueryWrapper<ScheduleHumanUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("reality_accumulate_accumulate");
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        queryWrapper.orderByDesc("upload_date");
        queryWrapper.last("limit 1");

        ScheduleHumanUpload scheduleHumanUploads = scheduleHumanUploadMapper.selectOne(queryWrapper);
        if (scheduleHumanUploads != null) {
            //更新currentConstruction
            UpdateWrapper<CurrentConstruction> updateActualWrapper = new UpdateWrapper<>();
            updateActualWrapper.set("current_schedule", scheduleHumanUploads.getRealityAccumulateAccumulate() + "%");
            updateActualWrapper.set("current_quantity", scheduleHumanUploads.getRealityAccumulateAccumulate() + "%");

            updateActualWrapper.set("current_construction", new BigDecimal(scheduleHumanUploads.getRealityAccumulateAccumulate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("6050")).toString());

            updateActualWrapper.eq("relation_id", "current");
            this.update(updateActualWrapper);

            UpdateWrapper<CurrentConstruction> updatePlanWrapper = new UpdateWrapper<>();
            updatePlanWrapper.set("current_schedule", scheduleHumanUploads.getPlanAccumulateAccumulate() + "%");
            updatePlanWrapper.set("current_quantity", scheduleHumanUploads.getPlanAccumulateAccumulate() + "%");

            updatePlanWrapper.set("current_construction", new BigDecimal(scheduleHumanUploads.getPlanAccumulateAccumulate())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("6050")).toString());

            updatePlanWrapper.eq("relation_id", "plan");
            this.update(updatePlanWrapper);
        }
    }

}
