package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.ScheduleHumanUpload;
import com.ruoyi.system.mapper.ScheduleHumanUploadMapper;
import com.ruoyi.system.service.ScheduleHumanUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleHumanUploadServiceImpl
        extends ServiceImpl<ScheduleHumanUploadMapper, ScheduleHumanUpload>
        implements ScheduleHumanUploadService {

    @Resource
    private ScheduleHumanUploadMapper scheduleHumanUploadMapper;

    @Override
    public List<ScheduleHumanUpload> queryListScheduleHumanUpload(ScheduleHumanUpload scheduleHumanUpload) {
        QueryWrapper<ScheduleHumanUpload> queryWrapper = new QueryWrapper<>();
        if (scheduleHumanUpload.getOrderByAscOrDesc().equals(YnEnum.N.getCode())) {
            queryWrapper.orderByDesc("upload_date");
        }
        return scheduleHumanUploadMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchScheduleHumanUpload(List<ScheduleHumanUpload> scheduleHumanUploads) {
        this.updateBatchById(scheduleHumanUploads);
    }

    @Override
    public List<ScheduleHumanUpload> bimNewListScheduleHumanUpload() {

        QueryWrapper<ScheduleHumanUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("reality_accumulate_accumulate");
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        queryWrapper.orderByDesc("upload_date");
        queryWrapper.last("limit 5");

        List<ScheduleHumanUpload> scheduleHumanUploads = scheduleHumanUploadMapper.selectList(queryWrapper);

        scheduleHumanUploads = scheduleHumanUploads.stream().sorted(Comparator.comparing(ScheduleHumanUpload::getUploadDate)).collect(Collectors.toList());

        return scheduleHumanUploads;
    }

}
