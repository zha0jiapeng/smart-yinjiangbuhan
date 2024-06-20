package com.ruoyi.system.service.impl;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.ProjectCalculateEnum;
import com.ruoyi.common.enums.ProjectCalculateStateEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.domain.bim.ConstructionPeopleHistory;
import com.ruoyi.system.entity.ProjectCalculate;
import com.ruoyi.system.entity.ProjectConstruction;
import com.ruoyi.system.entity.ProjectRatio;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ProjectConstructionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目施工报表(ProjectConstruction)表服务实现类
 */
@Slf4j
@Service("projectConstructionService")
public class ProjectConstructionServiceImpl implements ProjectConstructionService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private ProjectRatioMapper projectRatioMapper;
    @Resource
    private ProjectCalculateMapper projectCalculateMapper;
    @Resource
    private IotStaffAttendanceMapper iotStaffAttendanceMapper;
    @Resource
    private ProjectConstructionMapper projectConstructionMapper;
    @Resource
    private ConstructionPeopleHistoryMapper constructionPeopleHistoryMapper;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public ProjectConstruction queryById(Integer id) {
        return this.projectConstructionMapper.queryById(id);
    }

    /**
     * 分页查询
     */
    @Override
    public List<ProjectConstruction> queryByPage(ProjectConstruction projectConstruction) {
        return this.projectConstructionMapper.queryAllByLimit(projectConstruction);
    }

    @Override
    public List<ProjectConstruction> queryAll() {
        return projectConstructionMapper.queryAll();
    }

    @Override
    public List<ProjectConstruction> reportChart(ProjectConstruction projectConstruction) {
        return projectConstructionMapper.reportChart(projectConstruction);
    }

    /**
     * 新增数据
     */
    @Override
    public ProjectConstruction insert(ProjectConstruction projectConstruction) {
        this.projectConstructionMapper.insert(projectConstruction);
        return projectConstruction;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importExcel(List<ProjectConstruction> projectConstructions, Boolean isUpdateSupport, String operName) throws ParseException {
        if (StringUtils.isNull(projectConstructions) || projectConstructions.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }

        //删除历史数据
        projectConstructionMapper.delete();

        //插入日报表
        projectConstructionMapper.insertBatch(projectConstructions);

        String service = redisCache.getCacheObject("project_construction_service").toString();
        if (service.equals("true")) {
            //数据计算
            this.countConstructionScheduleList();
        }

        return "成功";
    }

    /**
     * 修改数据
     */
    @Override
    public ProjectConstruction update(ProjectConstruction projectConstruction) {
        this.projectConstructionMapper.update(projectConstruction);
        return this.queryById(projectConstruction.getId());
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.projectConstructionMapper.deleteById(id) > 0;
    }

    @Override
    public void countConstructionScheduleList() {
        // 施工进度曲线
        getConstructionSchedule();
        //投资进度
        getInvestSchedule();
        //施工人力
        human();
    }

    public void getConstructionSchedule() {
        List<String> planTimeList = Lists.newArrayList();
        List<String> okScheduleList = Lists.newArrayList();

        List<ProjectRatio> list = projectRatioMapper.queryAll();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Map<String, List<ProjectRatio>> collect = list.stream().collect(Collectors.groupingBy(ProjectRatio::getWeekStartTime));

        List<String> finalPlanTimeList = planTimeList;
        List<String> finalOkScheduleList = okScheduleList;

        collect.forEach((k, v) -> {
            finalPlanTimeList.add(k);
            List<ProjectRatio> projectRatios = v.stream()
                    .sorted(Comparator.comparing(ProjectRatio::getCumulativeCompletion).reversed()).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(projectRatios)) {
                ProjectRatio projectRatio = projectRatios.stream().findFirst().orElse(new ProjectRatio());
                finalOkScheduleList.add(projectRatio.getCumulativeCompletion());
            } else {
                finalOkScheduleList.add("0");
            }
        });

        planTimeList = planTimeList.stream()
                .sorted(Comparator.comparing(String::valueOf))
                .collect(Collectors.toList());

        okScheduleList = okScheduleList.stream()
                .sorted(Comparator.comparing(String::valueOf))
                .collect(Collectors.toList());

        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("jihuashijian", planTimeList);
        jsonObject.putOpt("wanchengjindu", okScheduleList);

        ProjectCalculate projectCalculate = new ProjectCalculate();
        projectCalculate.setModelKey(ProjectCalculateStateEnum.WORK_PROGRESS.getDesc());
        projectCalculate.setModelValue(String.valueOf(jsonObject));
        projectCalculate.setCheckState(Long.valueOf(YnEnum.Y.getCode()));
        projectCalculate.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        projectCalculate.setCreatedDate(DateUtils.getNowDate());
        projectCalculate.setYn(YnEnum.Y.getCode());

        projectCalculateMapper.insert(projectCalculate);
    }

    public void getInvestSchedule() {
        Map<String, BigDecimal> investScheduleCount = getInvestScheduleCount();
        if (MapUtils.isEmpty(investScheduleCount)) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.putOpt("yujifukuan", investScheduleCount.get(ProjectCalculateStateEnum.PREDICT_PAY_STATE.getDesc()));
        jsonObject.putOpt("yifukuan", investScheduleCount.get(ProjectCalculateStateEnum.INVESTED_PAY_STATE.getDesc()));
        jsonObject.putOpt("zhengchang", investScheduleCount.get(ProjectCalculateStateEnum.NORMAL_PAY_STATE.getDesc()));
        jsonObject.putOpt("weizhifu", investScheduleCount.get(ProjectCalculateStateEnum.NO_PAY_STATE.getDesc()));
        jsonObject.putOpt("chaoe", investScheduleCount.get(ProjectCalculateStateEnum.OVERFULFIL_PAY_STATE.getDesc()));

        ProjectCalculate projectCalculate = new ProjectCalculate();
        projectCalculate.setModelKey(ProjectCalculateStateEnum.INVEST_PROGRESS.getDesc());
        projectCalculate.setModelValue(String.valueOf(jsonObject));
        projectCalculate.setCheckState(Long.valueOf(YnEnum.Y.getCode()));
        projectCalculate.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        projectCalculate.setCreatedDate(DateUtils.getNowDate());
        projectCalculate.setYn(YnEnum.Y.getCode());

        projectCalculateMapper.insert(projectCalculate);
    }

    /**
     * 投资进度
     * 正常 = 计划完成时间 < 当前时间 的实际完成额 * 预计投资额
     * 未支付 = 已投资额 - 实际完成额
     * 超额 = 实际完成额 - 正常
     *
     * @return 计算结果
     */
    private Map<String, BigDecimal> getInvestScheduleCount() {
        Map<String, BigDecimal> map = new HashMap<>();
        ProjectRatio ratio = projectRatioMapper.queryAllMoney();
        if (ObjectUtils.isNotEmpty(ratio)) {
            // 实际完成任务(权重合)
            Float realitySum = projectConstructionMapper.getZhengChang();
            if (null != realitySum && !(Math.abs(realitySum) < 0.001)) {
                //实际完成任务(权重百分比)
                BigDecimal divide = new BigDecimal(realitySum).divide(new BigDecimal(100)).setScale(3, RoundingMode.HALF_UP);
                //实际完成额
                BigDecimal practical = ratio.getPredictInvestment().multiply(divide).setScale(3, RoundingMode.HALF_UP);
                //已投资-实际完成
                BigDecimal allCount = ratio.getAlreadyInvestment().subtract(practical).setScale(3, RoundingMode.HALF_UP);
                if (allCount.compareTo(BigDecimal.ZERO) > 0) {
                    map.put(ProjectCalculateStateEnum.OVERFULFIL_PAY_STATE.getDesc(), allCount);
                    map.put(ProjectCalculateStateEnum.NO_PAY_STATE.getDesc(), new BigDecimal(0));
                } else if (allCount.compareTo(BigDecimal.ZERO) < 0) {
                    map.put(ProjectCalculateStateEnum.OVERFULFIL_PAY_STATE.getDesc(), new BigDecimal(0));
                    map.put(ProjectCalculateStateEnum.NO_PAY_STATE.getDesc(), new BigDecimal(allCount.toString().substring(1)));
                } else if (allCount.compareTo(BigDecimal.ZERO) == 0) {
                    map.put(ProjectCalculateStateEnum.OVERFULFIL_PAY_STATE.getDesc(), new BigDecimal(0));
                    map.put(ProjectCalculateStateEnum.NO_PAY_STATE.getDesc(), new BigDecimal(0));
                }
                map.put(ProjectCalculateStateEnum.NORMAL_PAY_STATE.getDesc(), ratio.getAlreadyInvestment().min(practical));
                map.put(ProjectCalculateStateEnum.PREDICT_PAY_STATE.getDesc(), ratio.getPredictInvestment());
                map.put(ProjectCalculateStateEnum.INVESTED_PAY_STATE.getDesc(), ratio.getAlreadyInvestment());
            }
        }

        return map;
    }

    public void human() {
        //计算人天,大于2小时按照半天算,大于4小时按照天 例如:8.5
        List<Map<String, Object>> list = new ArrayList<>();

        SimpleDateFormat simpleDateFormatTwo = new SimpleDateFormat("yyyy-MM-dd");

        //历史数据
        QueryWrapper<ConstructionPeopleHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ConstructionPeopleHistory> constructionPeopleHistories = constructionPeopleHistoryMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(constructionPeopleHistories)) {
            constructionPeopleHistories.forEach(var -> {
                Map<String, Object> initMap = new HashMap<>();
                String format = simpleDateFormatTwo.format(var.getDoDate());
                initMap.put("shijian", format);
                initMap.put("jihuatouru", var.getPlanPeople());
                initMap.put("shijitouru", var.getRealityPeople());
                list.add(initMap);
            });
        }

        Map<String, Object> map = new HashMap<>();
        //时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        String format = simpleDateFormat.format(new Date());

        //计划投入人力
        Map<String, Object> cacheMap = redisCache.getCacheMap(format);
        Calendar c = Calendar.getInstance();
        // 一年内的第xx周//获取当前星期是第几周
        int week = c.get(Calendar.WEEK_OF_YEAR);
        Object plan = cacheMap.get(String.valueOf(week));
        int i = (Integer) plan / 56;

        List<BigDecimal> bigDecimalsList = new ArrayList<>();
        //实际投入人力
        List<IotStaffAttendance> iotStaffAttendances = iotStaffAttendanceMapper.queryAll(DateUtils.getDoStartDay(), DateUtils.getDoEndDay());
        if (!CollectionUtils.isEmpty(iotStaffAttendances)) {
            //根据人员分组
            Map<String, List<IotStaffAttendance>> listMap = iotStaffAttendances.stream().collect(Collectors.groupingBy(IotStaffAttendance::getPhone));
            listMap.forEach((k, v) -> {
                v = v.stream().sorted(Comparator.comparing(IotStaffAttendance::getDatetime))
                        .collect(Collectors.toList());

                BigDecimal realityPeople = new BigDecimal("0");
                if (!(v.size() % 2 == 0)) {
                    int indexOfLastElement = v.size() - 1;
                    v.remove(indexOfLastElement);
                }
                int size = v.size();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (int j = 0; j < size; j++) {
                    if (v.size() == 0) {
                        break;
                    }

                    IotStaffAttendance iotStaffAttendance = v.get(0);
                    IotStaffAttendance iotStaffAttendance1 = v.get(1);

                    Date beginTimeDate;
                    Date endTimeDate;
                    String beginTime = iotStaffAttendance.getDatetime();
                    String endTime = iotStaffAttendance1.getDatetime();

                    try {
                        beginTimeDate = dateFormat.parse(beginTime);
                        endTimeDate = dateFormat.parse(endTime);
                        long begin = beginTimeDate.getTime();
                        long end = endTimeDate.getTime();
                        //两个时间分钟差
                        int minute = (int) ((end - begin) / (1000 * 60));
                        realityPeople = realityPeople.add(new BigDecimal(minute));
                    } catch (ParseException e) {
                        log.error("两个时间分钟差 计算异常");
                    }

                    v.remove(0);
                    if (v.size() > 1) {
                        v.remove(1);
                    } else {
                        v.remove(0);
                    }
                }

                realityPeople = realityPeople.divide(new BigDecimal("60"), 2, RoundingMode.HALF_UP);
                if (realityPeople.compareTo(new BigDecimal("2")) > 0
                        || realityPeople.compareTo(new BigDecimal("2")) == 0) {
                    bigDecimalsList.add(new BigDecimal("0.5"));
                }

                if (realityPeople.compareTo(new BigDecimal("4")) > 0
                        || realityPeople.compareTo(new BigDecimal("4")) == 0) {
                    bigDecimalsList.add(new BigDecimal("1"));
                }
            });
        }

        map.put("shijian", new SimpleDateFormat(DateUtils.YYYY_MM_DD).format(new Date()));
        map.put("jihuatouru", i);
        map.put("shijitouru", bigDecimalsList.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        list.add(map);

        ConstructionPeopleHistory constructionPeopleHistory = new ConstructionPeopleHistory();
        constructionPeopleHistory.setDoDate(new Date());
        constructionPeopleHistory.setPlanPeople((double) i);
        constructionPeopleHistory.setRealityPeople(bigDecimalsList.stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
        constructionPeopleHistory.setCreatedBy("admin");
        constructionPeopleHistory.setCreatedDate(new Date());
        constructionPeopleHistory.setYn(YnEnum.Y.getCode());
        constructionPeopleHistoryMapper.insert(constructionPeopleHistory);

        ProjectCalculate projectCalculate = new ProjectCalculate();
        projectCalculate.setModelKey(ProjectCalculateEnum.PROJECT_COMPLETION_PROGRESS.getDesc());
        projectCalculate.setModelValue(JSON.toJSONString(list));
        projectCalculate.setCheckState(Long.valueOf(YnEnum.Y.getCode()));
        projectCalculate.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        projectCalculate.setCreatedDate(DateUtils.getNowDate());
        projectCalculate.setYn(YnEnum.Y.getCode());

        projectCalculateMapper.insert(projectCalculate);
    }


}
