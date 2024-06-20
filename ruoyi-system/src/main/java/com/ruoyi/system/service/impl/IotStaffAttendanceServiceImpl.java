package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.enums.WayEnum;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.WorkDateStorage;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.mapper.IotStaffAttendanceMapper;
import com.ruoyi.system.service.IotStaffAttendanceService;
import com.ruoyi.system.service.WorkDateStorageService;
import org.apache.commons.collections.MapUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("iotStaffAttendanceService")
public class IotStaffAttendanceServiceImpl extends ServiceImpl<IotStaffAttendanceMapper, IotStaffAttendance> implements IotStaffAttendanceService {

    @Resource
    private IotStaffAttendanceMapper iotStaffAttendanceMapper;
    @Resource
    private WorkDateStorageService workDateStorageService;

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    @Override
    public IotStaffAttendance queryById(Integer pid) {
        return iotStaffAttendanceMapper.queryById(pid);
    }

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @Override
    public List<IotStaffAttendance> queryByPage(IotStaffAttendance iotStaffAttendance) {
        return iotStaffAttendanceMapper.queryAllByLimit(iotStaffAttendance);
    }

    @Scheduled(cron = "0 59 23 * * ?")
    public void queryScheduled() {
        saveScheduled(null, null);
    }


    @Override
    public void saveScheduled(String dayBegin, String dayEnd) {
        List<WorkDateStorage> storageList = new ArrayList<>();

        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (StringUtils.isEmpty(dayBegin)) {
            dayBegin = dateformat.format(DateToUtils.getDayBegin());
        }
        if (StringUtils.isEmpty(dayEnd)) {
            dayEnd = dateformat.format(DateToUtils.getDayEnd());
        }

        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(dayBegin, dayEnd);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, List<IotStaffAttendance>> listMap = list.stream().collect(Collectors.groupingBy(IotStaffAttendance::getPhone));
            if (!MapUtils.isEmpty(listMap)) {
                listMap.forEach((k, v) -> {

                    WorkDateStorage workDateStorage = new WorkDateStorage();
                    workDateStorage.setPhone(k);
                    IotStaffAttendance iotStaffAttendanceIn = v.stream()
                            .filter(var -> var.getWay().equals(WayEnum.IN.getCode().toString()))
                            .findFirst().orElse(new IotStaffAttendance());
                    IotStaffAttendance iotStaffAttendanceOut = v.stream()
                            .filter(var -> var.getWay().equals(WayEnum.OUT.getCode().toString()))
                            .findFirst().orElse(new IotStaffAttendance());
                    workDateStorage.setName(iotStaffAttendanceIn.getName());
                    workDateStorage.setStartDate(DateUtils.parseDate(iotStaffAttendanceIn.getDatetime()));
                    workDateStorage.setEndDate(DateUtils.parseDate(iotStaffAttendanceOut.getDatetime()));

                    workDateStorage.setCreatedBy("系统任务");
                    workDateStorage.setCreatedDate(new Date());
                    workDateStorage.setYn(YnEnum.Y.getCode());
                    storageList.add(workDateStorage);
                });
            }
        }

        workDateStorageService.saveBatch(storageList);
    }

}
