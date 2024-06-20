package com.ruoyi.system.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.enums.ProjectCalculateEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.entity.ProjectCalculate;
import com.ruoyi.system.entity.ProjectRatio;
import com.ruoyi.system.mapper.ProjectCalculateMapper;
import com.ruoyi.system.mapper.ProjectRatioMapper;
import com.ruoyi.system.service.ProjectRatioService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 三维_累计比例(ProjectRatio)表服务实现类
 *
 * @since 2023-06-07 17:55:26
 */
@Service("projectRatioService")
public class ProjectRatioServiceImpl implements ProjectRatioService {
    @Resource
    private ProjectRatioMapper projectRatioDao;
    @Resource
    private ProjectCalculateMapper projectCalculateMapper;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public ProjectRatio queryById(Integer id) {
        return this.projectRatioDao.queryById();
    }

    /**
     * 分页查询
     */
    @Override
    public List<ProjectRatio> queryByPage(ProjectRatio projectRatio) {
        return this.projectRatioDao.queryAllByLimit(projectRatio);
    }

    /**
     * 新增数据
     */
    @Override
    public ProjectRatio insert(ProjectRatio projectRatio) {
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        // 一年内的第xx周//获取当前星期是第几周
        int week = c.get(Calendar.WEEK_OF_YEAR);
        String doDate = yyyy.format(new Date());
        projectRatio.setWeekBase(doDate + "-" + week);

        //计算当前是否第多少周
        DateTime dateTime = DateUtil.beginOfWeek(new Date());
        String format = sdf.format(dateTime);
        projectRatio.setWeekStartTime(format);

        this.projectRatioDao.insert(projectRatio);
        return projectRatio;
    }

    /**
     * 修改数据
     */
    @Override
    public ProjectRatio update(ProjectRatio projectRatio) {
        ProjectRatio ratio = projectRatioDao.queryAllMoney();
        if (ObjectUtils.isNotEmpty(ratio)) {
            projectRatio.setId(ratio.getId());
        }
        int update = this.projectRatioDao.update(projectRatio);
        if (YnEnum.Y.getCode().equals(update)) {
            List<ProjectCalculate> projectCalculates = projectCalculateMapper.queryAll();
            if (CollectionUtils.isEmpty(projectCalculates)) {
                return projectRatio;
            }

            Map<String, List<ProjectCalculate>> listMap = projectCalculates.stream().filter(val -> val.getModelKey() != null)
                    .collect(Collectors.groupingBy(ProjectCalculate::getModelKey));

            if (listMap.containsKey(ProjectCalculateEnum.INVESTMENT_PROGRESS.getDesc())) {
                List<ProjectCalculate> projectCalculateList = listMap.get(ProjectCalculateEnum.INVESTMENT_PROGRESS.getDesc());
                //根据创建时间倒叙排序
                projectCalculateList = projectCalculateList.stream().sorted(Comparator.comparing(ProjectCalculate::getCreatedDate).reversed()).collect(Collectors.toList());
                ProjectCalculate calculate = projectCalculateList.stream().findFirst().orElse(new ProjectCalculate());
                // 解析预计投资额 已投资
                String modelValue = calculate.getModelValue();
                Map map = JSON.parseObject(modelValue, Map.class);

                ProjectCalculate projectCalculate = new ProjectCalculate();
                projectCalculate.setId(calculate.getId());
                //需要修改为待审核
                projectCalculate.setCheckState(1L);
                projectCalculate.setModifyBy(SecurityUtils.getLoginUser().getUsername());
                projectCalculate.setModifyDate(DateUtils.getNowDate());

                JSONObject jsonObject = new JSONObject();
                jsonObject.putOpt("yujifukuan", projectRatio.getPredictInvestment().toString());
                jsonObject.putOpt("yifukuan", projectRatio.getAlreadyInvestment().toString());
                jsonObject.putOpt("zhengchang", map.get("zhengchang").toString());
                jsonObject.putOpt("weizhifu", map.get("weizhifu").toString());
                jsonObject.putOpt("chaoe", map.get("chaoe").toString());
                projectCalculate.setModelValue(String.valueOf(jsonObject));

                projectCalculateMapper.update(projectCalculate);
            }
        }

        return this.queryById(projectRatio.getId());
    }

    @Override
    public ProjectRatio updateBase(ProjectRatio projectRatio) {
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        // 一年内的第xx周//获取当前星期是第几周
        int week = c.get(Calendar.WEEK_OF_YEAR);
        String doDate = yyyy.format(new Date());
        projectRatio.setWeekBase(doDate + "-" + week);

        //计算当前是否第多少周
        DateTime dateTime = DateUtil.beginOfWeek(new Date());
        String format = sdf.format(dateTime);
        projectRatio.setWeekStartTime(format);

        projectRatio.setUpdateTime(new Date());
        projectRatioDao.update(projectRatio);
        return projectRatio;
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.projectRatioDao.deleteById(id) > 0;
    }
}
