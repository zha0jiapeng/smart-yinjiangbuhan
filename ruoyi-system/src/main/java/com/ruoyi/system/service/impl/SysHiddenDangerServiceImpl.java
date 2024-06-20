package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.entity.SysHiddenDanger;
import com.ruoyi.system.entity.SysHiddenDangerFiles;
import com.ruoyi.system.entity.SysQuestionDescribe;
import com.ruoyi.system.mapper.SysHiddenDangerMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysHiddenDangerFilesService;
import com.ruoyi.system.service.SysHiddenDangerService;
import com.ruoyi.system.service.SysQuestionDescribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 隐患登记 服务实现类
 * </p>
 *
 * @author liushuai
 * @since 2023-02-28
 */
@Service
public class SysHiddenDangerServiceImpl extends ServiceImpl<SysHiddenDangerMapper, SysHiddenDanger> implements SysHiddenDangerService {
    @Autowired
    private SysHiddenDangerMapper sysHiddenDangerMapper;

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private SysQuestionDescribeService sysQuestionDescribeService;

    @Autowired
    private SysHiddenDangerFilesService sysHiddenDangerFilesService;

    @Override
    public void create(SysHiddenDanger sysHiddenDanger) {
        sysHiddenDangerMapper.create(sysHiddenDanger);
    }

    @Override
    public void delete(Integer id) {
        sysHiddenDangerMapper.delete(id);
    }

    @Override
    public void update(SysHiddenDanger sysHiddenDanger) {
        sysHiddenDangerMapper.update(sysHiddenDanger);
    }

    @Override
    public List<SysHiddenDanger> queryByPage(SysHiddenDanger sysHiddenDanger) {
        List<SysHiddenDanger> inspectionPlans = sysHiddenDangerMapper.list(sysHiddenDanger);

        if (!CollectionUtils.isEmpty(inspectionPlans)) {
            inspectionPlans.forEach(val -> {
                if (!StringUtils.isBlank(val.getDutyPersonId())) {
                    SysUser sysUser = iSysUserService.selectUserById(Long.valueOf(val.getDutyPersonId()));
                    if (null != sysUser) {
                        val.setDutyPerson(sysUser.getNickName());
                    }
                }

                QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", val.getProblemId());
                SysQuestionDescribe describeServiceOne = sysQuestionDescribeService.getOne(queryWrapper);
                if (null != describeServiceOne) {
                    val.setProblemBase(describeServiceOne.getProblemContent());
                }

                List<SysHiddenDangerFiles> sysHiddenDangerFiles = new ArrayList<>();

                String fileId = val.getFileId();
                if (!StringUtils.isBlank(fileId)) {
                    fileId = fileId.substring(0, fileId.length() - 1);
                    if (fileId.contains(",")) {
                        String[] split = fileId.split(",");
                        QueryWrapper<SysHiddenDangerFiles> filesQueryWrapper = new QueryWrapper<>();
                        filesQueryWrapper.in("id", Arrays.asList(split));
                        sysHiddenDangerFiles.addAll(sysHiddenDangerFilesService.list(filesQueryWrapper));
                    } else {
                        QueryWrapper<SysHiddenDangerFiles> filesQueryWrapper = new QueryWrapper<>();
                        filesQueryWrapper.eq("id", fileId);
                        SysHiddenDangerFiles one = sysHiddenDangerFilesService.getOne(filesQueryWrapper);
                        sysHiddenDangerFiles.add(one);
                    }
                }

                val.setSysHiddenDangerFiles(sysHiddenDangerFiles);

            });
        }

        return inspectionPlans;
    }

    @Override
    public void updateStatus(SysHiddenDanger sysHiddenDanger) {
        sysHiddenDangerMapper.updateStatus(sysHiddenDanger);
    }

    @Override
    public void updateFour(SysHiddenDanger sysHiddenDanger) {
        sysHiddenDangerMapper.updateFour(sysHiddenDanger);
    }

    @Override
    public List<Map<String, String>> exportProblem(Integer type) {
        List<Map<String, String>> mapList = new ArrayList<>();
        SysHiddenDanger queryWrapper = new SysHiddenDanger();
        queryWrapper.setTaskType(type);
        if (type.equals(1)) {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dayBegin = dateformat.format(DateToUtils.getDayBegin());
            String dayEnd = dateformat.format(DateToUtils.getDayEnd());
            queryWrapper.setStateDate(dayBegin);
            queryWrapper.setEndDate(dayEnd);
        } else if (type.equals(2)) {
            Calendar c = Calendar.getInstance();
            // 一年内的第xx周//获取当前星期是第几周
            Integer week = c.get(Calendar.WEEK_OF_YEAR);
            queryWrapper.setWeek(String.valueOf(week));
        } else {
            String mm = new SimpleDateFormat("MM").format(new Date());
            queryWrapper.setMonth(mm);
        }
        List<SysHiddenDanger> sysHiddenDangers = sysHiddenDangerMapper.list(queryWrapper);
        if (!CollectionUtils.isEmpty(sysHiddenDangers)) {
            List<SysHiddenDanger> collect = sysHiddenDangers.stream().filter(val -> val.getStatus().equals(YnEnum.N.getCode())
                    || val.getStatus().equals(YnEnum.Y.getCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                AtomicReference<Integer> i = new AtomicReference<>(0);
                collect.forEach(var -> {
                    i.getAndSet(i.get() + 1);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", i.toString());
                    map.put("areaName", var.getAreaName());
                    QueryWrapper<SysQuestionDescribe> wrapper = new QueryWrapper<>();
                    wrapper.eq("id", var.getProblemId());
                    SysQuestionDescribe describeServiceOne = sysQuestionDescribeService.getOne(wrapper);
                    if (null != describeServiceOne) {
                        map.put("problemBase", describeServiceOne.getProblemContent());
                    }
                    map.put("problem", var.getProblem());
                    map.put("changeDescribe", var.getChangeDescribe());
                    map.put("dutyPerson", var.getDutyPerson());
                    String year = new SimpleDateFormat("yyyy-MM-dd").format(var.getWithinDate());
                    map.put("changeDescribe", year);

                    String fileId = var.getFileId();
                    if (!StringUtils.isBlank(fileId)) {
                        fileId = fileId.substring(0, fileId.length() - 1);
                        if (fileId.contains(",")) {
                            String[] split = fileId.split(",");
                            QueryWrapper<SysHiddenDangerFiles> filesQueryWrapper = new QueryWrapper<>();
                            filesQueryWrapper.in("id", Arrays.asList(split));
                            List<SysHiddenDangerFiles> list = sysHiddenDangerFilesService.list(filesQueryWrapper);
                            if (!CollectionUtils.isEmpty(list)) {
                                SysHiddenDangerFiles sysHiddenDangerFiles = list.stream().findFirst().orElse(new SysHiddenDangerFiles());
                                map.put("fileUrl", sysHiddenDangerFiles.getFileUrl());
                            }
                        } else {
                            QueryWrapper<SysHiddenDangerFiles> filesQueryWrapper = new QueryWrapper<>();
                            filesQueryWrapper.eq("id", fileId);
                            SysHiddenDangerFiles one = sysHiddenDangerFilesService.getOne(filesQueryWrapper);
                            if (null != one) {
                                map.put("fileUrl", one.getFileUrl());
                            }
                        }
                    }
                    mapList.add(map);
                });
            }
        }

        return mapList;
    }

    @Override
    public List<Map<String, String>> exportProblemZg(Integer type) {
        List<Map<String, String>> mapList = new ArrayList<>();
        SysHiddenDanger queryWrapper = new SysHiddenDanger();
        queryWrapper.setTaskType(type);

        if (type.equals(1)) {
            DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dayBegin = dateformat.format(DateToUtils.getDayBegin());
            String dayEnd = dateformat.format(DateToUtils.getDayEnd());
            queryWrapper.setStateDate(dayBegin);
            queryWrapper.setEndDate(dayEnd);
        } else if (type.equals(2)) {
            Calendar c = Calendar.getInstance();
            // 一年内的第xx周//获取当前星期是第几周
            Integer week = c.get(Calendar.WEEK_OF_YEAR);
            queryWrapper.setWeek(String.valueOf(week));
        } else {
            String mm = new SimpleDateFormat("MM").format(new Date());
            queryWrapper.setMonth(mm);
        }

        List<SysHiddenDanger> sysHiddenDangers = sysHiddenDangerMapper.list(queryWrapper);
        if (!CollectionUtils.isEmpty(sysHiddenDangers)) {
            List<SysHiddenDanger> collect = sysHiddenDangers.stream().filter(val -> val.getStatus().equals(2)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                AtomicReference<Integer> i = new AtomicReference<>(0);
                collect.forEach(var -> {
                    i.getAndSet(i.get() + 1);
                    Map<String, String> map = new HashMap<>();
                    map.put("id", i.toString());
                    map.put("areaName", var.getAreaName());
                    QueryWrapper<SysQuestionDescribe> wrapper = new QueryWrapper<>();
                    wrapper.eq("id", var.getProblemId());
                    SysQuestionDescribe describeServiceOne = sysQuestionDescribeService.getOne(wrapper);
                    if (null != describeServiceOne) {
                        map.put("problemBase", describeServiceOne.getProblemContent());
                    }
                    map.put("problem", var.getProblem());
                    map.put("changeDescribe", var.getChangeDescribe());
                    map.put("dutyPerson", var.getDutyPerson());
                    String year = new SimpleDateFormat("yyyy-MM-dd").format(var.getWithinDate());
                    map.put("changeDescribe", year);
                    map.put("fileUrl", var.getFileUrl());
                    mapList.add(map);
                });
            }
        }

        return mapList;
    }

}
