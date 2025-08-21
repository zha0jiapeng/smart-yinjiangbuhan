package com.ruoyi.web.controller.basic.view.bim;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.ruoyi.assessment.core.utils.DateUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BasicDataValueConfigEnum;
import com.ruoyi.common.enums.HiddenDangerStatus;
import com.ruoyi.common.enums.ProblemGradeEnum;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.Item;
import com.ruoyi.system.domain.PersonnelTypeCount;
import com.ruoyi.system.domain.ScheduleTbmSegment;
import com.ruoyi.system.domain.SysUserNotice;
import com.ruoyi.system.domain.basic.IotTsp;
import com.ruoyi.system.domain.bim.*;
import com.ruoyi.system.domain.bim.QualityBimDomain.Head;
import com.ruoyi.system.domain.bim.QualityBimDomain.QualityCount;
import com.ruoyi.system.domain.bim.QualityBimDomain.QualityDistribution;
import com.ruoyi.system.domain.bim.QualityBimDomain.QualityTrend;
import com.ruoyi.system.domain.bim.SecurityBimDomain.EmergencyAccidentModel;
import com.ruoyi.system.domain.bim.SecurityBimDomain.FirehouseModel;
import com.ruoyi.system.domain.bim.SecurityBimDomain.RiskGradeCountModel;
import com.ruoyi.system.domain.bim.SecurityBimDomain.SecurityArrangementModel;
import com.ruoyi.system.entity.BasicDataValueConfig;
import com.ruoyi.system.entity.SysHiddenDanger;
import com.ruoyi.system.entity.SysQuestionDescribe;
import com.ruoyi.system.mapper.SysUserNoticeMapper;
import com.ruoyi.system.pojo.query.ItemQuery;
import com.ruoyi.system.pojo.query.ReceiptOrderQuery;
import com.ruoyi.system.pojo.query.ShipmentOrderQuery;
import com.ruoyi.system.pojo.vo.ItemVO;
import com.ruoyi.system.pojo.vo.ReceiptOrderVO;
import com.ruoyi.system.pojo.vo.ShipmentOrderVO;
import com.ruoyi.system.service.*;
import com.ruoyi.system.service.impl.ItemService;
import com.ruoyi.system.service.impl.PersonnelTypeCountServiceImpl;
import com.ruoyi.system.service.impl.ReceiptOrderService;
import com.ruoyi.system.service.impl.ShipmentOrderService;
import com.ruoyi.system.utils.Result;
import com.ruoyi.web.controller.basic.yinjiangbuhan.controller.DustDetectionController;
import com.ruoyi.web.controller.basic.yinjiangbuhan.controller.TBMController;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("bim/home")
public class BimHomeController {

    @Resource
    private ScheduleTbmSegmentService scheduleTbmSegmentService;

    @Autowired
    private SysUserNoticeMapper sysUserNoticeMapper;

    @Autowired
    private BasicDataValueConfigService basicDataValueConfigService;

    @Resource
    private PersonnelTypeCountServiceImpl personnelTypeCountService;

    @Autowired
    private SysHiddenDangerService sysHiddenDangerService;

    @Resource
    private QualityProblemService qualityProblemService;

    @Autowired
    private ItemService service;

    @Autowired
    private ReceiptOrderService receiptOrderService;

    @Autowired
    private ShipmentOrderService shipmentOrderService;

    @Autowired
    private SysQuestionDescribeService sysQuestionDescribeService;

    @Resource
    private IotTspService iotTspService;

    /**
     * 首页接口
     *
     * @return 结果
     */
    @PostMapping("/home")
    @SuppressWarnings("all")
    public Result<?> home() {
        BimHomeTwoDomain bimHomeTwoDomain = new BimHomeTwoDomain();

        List<BasicDataValueConfig> basicDataValueConfigs = basicDataValueConfigService.list();

        Map<String, BasicDataValueConfig> dataValueConfigMap = basicDataValueConfigs.stream()
                .collect(Collectors.toMap(BasicDataValueConfig::getBasicKey, el -> el, (e1, e2) -> e1));

        //施工信息
        BimHomeTwoDomain.ConstructionCount constructionCount = new BimHomeTwoDomain.ConstructionCount();
        QueryWrapper<ScheduleTbmSegment> scheduleTbmSegmentQueryWrapper = new QueryWrapper<>();
        scheduleTbmSegmentQueryWrapper.eq("yn", YnEnum.Y.getCode());
        List<ScheduleTbmSegment> scheduleTbmSegmentList = scheduleTbmSegmentService.list(scheduleTbmSegmentQueryWrapper);
        if (CollectionUtils.isEmpty(scheduleTbmSegmentList)) {
            constructionCount.setSegment(0);
        } else {
            BigDecimal bigDecimal = scheduleTbmSegmentList.stream().map(ScheduleTbmSegment::getScheduleRealityPrice)
                    .filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
            constructionCount.setSegment(bigDecimal.intValue());
        }

        BasicDataValueConfig PLAN_HUMAN = dataValueConfigMap.get(BasicDataValueConfigEnum.plan_human.getCode());
        BimHomeTwoDomain.ConstructionCount constructionCountOne = JSON.parseObject(PLAN_HUMAN.getBasicValue(), BimHomeTwoDomain.ConstructionCount.class);
        constructionCount.setPersonnel(constructionCountOne.getPersonnel());

        BasicDataValueConfig PROGRESS_OVERVIEW_DETAILS = dataValueConfigMap.get(BasicDataValueConfigEnum.progress_overview_details.getCode());
        List<Map> mapList = JSON.parseArray(PROGRESS_OVERVIEW_DETAILS.getBasicValue(), Map.class);
        constructionCount.setJsonValue(JSON.toJSONString(mapList));
        bimHomeTwoDomain.setConstructionCount(constructionCount);

        //岗位分析
        BasicDataValueConfig MONITOR_TYPE_DEVICE_DISTRIBUTION = dataValueConfigMap.get(BasicDataValueConfigEnum.project_overview.getCode());
        List<BimHomeTwoDomain.BasicPost> basicPosts = JSON.parseArray(MONITOR_TYPE_DEVICE_DISTRIBUTION.getBasicValue(), BimHomeTwoDomain.BasicPost.class);
        bimHomeTwoDomain.setBasicPostList(basicPosts);

        //报警信息
        List<BimHomeTwoDomain.Call> callList = new ArrayList<>();
        List<SysUserNotice> sysUserNotices = sysUserNoticeMapper.queryAll();
        if (org.apache.commons.collections.CollectionUtils.isEmpty(sysUserNotices)) {
            bimHomeTwoDomain.setCallList(new ArrayList<>());
            return Result.OK(bimHomeTwoDomain);
        }
        sysUserNotices.forEach(var -> {
            BimHomeTwoDomain.Call call = new BimHomeTwoDomain.Call();
            call.setId(var.getId());
            call.setNoticeType(var.getNoticeType());
            call.setContent(var.getContent());
            call.setCreateDate(var.getCreatedDate());
            if (null != var.getReadUserId()) {
                call.setStatus("未处理");
            } else {
                call.setStatus("已处理");
            }
            callList.add(call);
        });

        bimHomeTwoDomain.setCallList(callList);
        return Result.OK(bimHomeTwoDomain);
    }

    @PostMapping("/video")
    public Result<?> video() {
        BimVideoDomain bimVideoDomain = new BimVideoDomain();

        List<BasicDataValueConfig> basicDataValueConfigs = basicDataValueConfigService.list();

        Map<String, BasicDataValueConfig> dataValueConfigMap = basicDataValueConfigs.stream()
                .collect(Collectors.toMap(BasicDataValueConfig::getBasicKey, el -> el, (e1, e2) -> e1));

        BasicDataValueConfig MONITOR_TYPE_DEVICE_DISTRIBUTION = dataValueConfigMap.get(BasicDataValueConfigEnum.monitor_type_device_distribution.getCode());
        BimVideoDomain.VideoTypeAnalysis videoTypeAnalysis = JSON.parseObject(MONITOR_TYPE_DEVICE_DISTRIBUTION.getBasicValue(), BimVideoDomain.VideoTypeAnalysis.class);
        bimVideoDomain.setVideoTypeAnalysis(videoTypeAnalysis);

        BasicDataValueConfig MONITOR_STATUS = dataValueConfigMap.get(BasicDataValueConfigEnum.monitor_status.getCode());
        BimVideoDomain.VideoStatusAnalysis videoStatusAnalysis = JSON.parseObject(MONITOR_STATUS.getBasicValue(), BimVideoDomain.VideoStatusAnalysis.class);
        bimVideoDomain.setVideoStatusAnalysis(videoStatusAnalysis);

        BasicDataValueConfig MONITOR_LIST = dataValueConfigMap.get(BasicDataValueConfigEnum.monitor_list.getCode());
        List<BimVideoDomain.VideoListAnalysis> videoListAnalyses = JSON.parseArray(MONITOR_LIST.getBasicValue(), BimVideoDomain.VideoListAnalysis.class);
        bimVideoDomain.setVideoListAnalyses(videoListAnalyses);

        return Result.OK(bimVideoDomain);
    }

    /**
     * 安全管理
     *
     * @return 结果
     */
    @PostMapping("/security")
    public Result<?> security() {
        SecurityBimDomain securityBimDomain = new SecurityBimDomain();

        boolean flag = false;
        AtomicInteger ok = new AtomicInteger();
        AtomicInteger audit = new AtomicInteger();
        List<SysHiddenDanger> inspectionPlans = null;
        //隐患信息 列表
        SecurityBimDomain.AngerInfoModel angerInfoModel = new SecurityBimDomain.AngerInfoModel();
        ArrayList<SecurityBimDomain.AngerInfoModel> angerInfoModelArrayList = Lists.newArrayList();
        inspectionPlans = this.sysHiddenDangerService.queryByPage(new SysHiddenDanger());
        if (!CollectionUtils.isEmpty(inspectionPlans)) {
            flag = true;
            inspectionPlans.forEach(val -> {
                QueryWrapper<SysQuestionDescribe> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", val.getProblemId());
                SysQuestionDescribe describeServiceOne = sysQuestionDescribeService.getOne(queryWrapper);
                if (null != describeServiceOne) {
                    val.setProblemBase(describeServiceOne.getProblemContent());
                }

                SecurityBimDomain.AngerInfoModel angerModel = new SecurityBimDomain.AngerInfoModel();
                //0:待整改 1:待审核 2:审核通过 3:驳回
                if (val.getStatus() != null && val.getStatus().equals(HiddenDangerStatus.UNDER.getCode())) {
                    angerModel.setState(HiddenDangerStatus.UNDER.getDesc());

                    audit.getAndIncrement();
                }
                if (val.getStatus() != null && val.getStatus().equals(HiddenDangerStatus.AUDIT.getCode())) {
                    angerModel.setState(HiddenDangerStatus.AUDIT.getDesc());
                    audit.getAndIncrement();
                }
                if (val.getStatus() != null && val.getStatus().equals(HiddenDangerStatus.OK.getCode())) {
                    angerModel.setState(HiddenDangerStatus.OK.getDesc());
                    ok.getAndIncrement();
                }

                angerModel.setStatus(val.getStatus());
                angerModel.setId(val.getId());
                angerModel.setInfo(val.getProblem());
                angerModel.setTime(val.getCreateTime());
                angerModel.setRegion(val.getAreaName());
                angerModel.setProblemdescription(val.getProblem());
                angerModel.setReportPerson(val.getCreateBy());
                angerModel.setReporTime(val.getCreateTime());
                angerModel.setRectifier(val.getDutyPerson());
                if (null != val.getWithinDate()) {
                    angerModel.setRectifierTime(DateUtils.formatDate(val.getWithinDate(), "yyyy-MM-dd HH:mm:ss"));
                }
                angerModel.setAfterRectificationImage(val.getFileUrl());
                if (!CollectionUtils.isEmpty(val.getSysHiddenDangerFiles())) {
                    if (null != val.getSysHiddenDangerFiles().get(0)) {
                        angerModel.setBeforeRectificationImage(val.getSysHiddenDangerFiles().get(0).getFileUrl());
                    }
                }
                angerInfoModelArrayList.add(angerModel);
            });
        }
        angerInfoModel.setArray(angerInfoModelArrayList);
        securityBimDomain.setDaizhenggaiyinhuanxinxi(angerInfoModel);

        //隐患排查统计
        if (flag) {
            SecurityBimDomain.AngerPaichaCountModel angerPaichaCountModel = new SecurityBimDomain.AngerPaichaCountModel();
            angerPaichaCountModel.setZhongda(String.valueOf(inspectionPlans.stream().filter(val -> val.getProblemGrade().equals(ProblemGradeEnum.Z.getCode())).count()));
            angerPaichaCountModel.setYanzhong(String.valueOf(inspectionPlans.stream().filter(val -> val.getProblemGrade().equals(ProblemGradeEnum.T.getCode())).count()));
            angerPaichaCountModel.setYiban(String.valueOf(inspectionPlans.stream().filter(val -> val.getProblemGrade().equals(ProblemGradeEnum.Y.getCode())).count()));
            angerPaichaCountModel.setZhengchang(String.valueOf(inspectionPlans.stream().filter(val -> val.getProblemGrade().equals(ProblemGradeEnum.ZC.getCode())).count()));
            securityBimDomain.setYinhuanpaichutongji(angerPaichaCountModel);
        }

        //人员信息
        SecurityBimDomain.PersonnelStatistics personnelStatistics = new SecurityBimDomain.PersonnelStatistics();
        PersonnelTypeCount personnelTypeCount = personnelTypeCountService.personnelTypeCount();
        BeanUtils.copyProperties(personnelTypeCount, personnelStatistics);
        securityBimDomain.setPersonnelStatistics(personnelStatistics);

        if (flag) {
            //本月安全应急简报
            SecurityArrangementModel securityArrangementModel = new SecurityArrangementModel();
            securityArrangementModel.setXiancunyinhuan(audit.toString());
            securityArrangementModel.setXiancunfengxian(audit.toString());
            securityArrangementModel.setZhenggaizhong(audit.toString());
            securityArrangementModel.setBenyuexinzengfengxian(audit.toString());

            securityArrangementModel.setBenyuexinzhengyinhuan(String.valueOf(inspectionPlans.size()));
            securityArrangementModel.setFengxianyujing("0");
            securityArrangementModel.setBenyuezhenggai(ok.toString());
            securityArrangementModel.setGuankong(audit.toString());
            securityBimDomain.setBenyueanquanyingjijianbao(securityArrangementModel);
        }

        //四色图 写死不变
        RiskGradeCountModel riskGradeCountModel = new RiskGradeCountModel();
        riskGradeCountModel.setTeda("24");
        riskGradeCountModel.setZhongda("3");
        riskGradeCountModel.setJiaoda("2");
        riskGradeCountModel.setYiban("4");
        securityBimDomain.setFengxiandengjitongji(riskGradeCountModel);

        //暂无数据
        SecurityBimDomain.AreaGradeCountModel areaGradeCountModel = new SecurityBimDomain.AreaGradeCountModel();
        ArrayList<SecurityBimDomain.AreaGradeCountModel> areaGradeCountModelArrayList = Lists.newArrayList();
        SecurityBimDomain.AreaGradeCountModel areaModel = new SecurityBimDomain.AreaGradeCountModel();
        areaModel.setTeda("");
        areaModel.setZhongda("");
        areaModel.setJiaoda("");
        areaModel.setYiban("");
        areaGradeCountModelArrayList.add(areaModel);
        areaGradeCountModel.setArray(areaGradeCountModelArrayList);
        securityBimDomain.setQuyufengxiantongji(areaGradeCountModel);

        //暂无数据
        SecurityBimDomain.RiskAngerModel riskAngerModel = new SecurityBimDomain.RiskAngerModel();
        ArrayList<SecurityBimDomain.RiskAngerModel> newArrayList = Lists.newArrayList();
        SecurityBimDomain.RiskAngerModel riskModel = new SecurityBimDomain.RiskAngerModel();
        riskModel.setFengxian("");
        riskModel.setYinhuan("");
        newArrayList.add(riskModel);
        riskAngerModel.setQuannian(newArrayList);
        securityBimDomain.setFengxianyinhuanxinzhengqushi(riskAngerModel);

        //暂无数据
        FirehouseModel firehouseModel = new FirehouseModel();
        ArrayList<FirehouseModel> firehouseList = Lists.newArrayList();
        FirehouseModel fModel = new FirehouseModel();
        fModel.setFangzhididian("");
        fModel.setWuzi("");
        fModel.setX("");
        fModel.setY("");
        firehouseList.add(fModel);
        firehouseModel.setArray(firehouseList);
        securityBimDomain.setXiaofangzhan(firehouseModel);

        //暂无数据
        EmergencyAccidentModel emergencyAccidentModel = new EmergencyAccidentModel();
        ArrayList<EmergencyAccidentModel> emergencyList = Lists.newArrayList();
        EmergencyAccidentModel emergencyModel = new EmergencyAccidentModel();
        emergencyModel.setFangzhididian("");
        emergencyModel.setWuzi("");
        emergencyModel.setX("");
        emergencyModel.setY("");
        emergencyList.add(emergencyModel);
        emergencyAccidentModel.setArray(emergencyList);
        securityBimDomain.setYingjishigugui(emergencyAccidentModel);

        //安全问题统计
        if (flag) {
            SecurityBimDomain.SecureProblemClass secureProblemClass = new SecurityBimDomain.SecureProblemClass();

            List<String> secureProblemName = new ArrayList<>();
            List<Integer> secureProblemNumber = new ArrayList<>();

            AtomicReference<String> secureProblemNameQiTa = new AtomicReference<>("");
            AtomicReference<Integer> secureProblemNumberQiTa = new AtomicReference<>(0);

            Map<String, List<SysHiddenDanger>> listMap = inspectionPlans.stream().collect(Collectors.groupingBy(SysHiddenDanger::getProblemBase));
            listMap.forEach((k, v) -> {
                if ("其它".equals(k)) {
                    secureProblemNameQiTa.set("其它");
                    secureProblemNumberQiTa.set(v.size());
                } else {
                    secureProblemName.add(k);
                    secureProblemNumber.add(v.size());
                }
            });

            secureProblemName.add(secureProblemNameQiTa.get());
            secureProblemNumber.add(secureProblemNumberQiTa.get());

            secureProblemClass.setSecureProblemName(secureProblemName);
            secureProblemClass.setSecureProblemNumber(secureProblemNumber);
            securityBimDomain.setSecureProblemClassCount(secureProblemClass);
        }

        return Result.OK(securityBimDomain);
    }

    /**
     * 质量管理
     *
     * @return 结果
     */
    @PostMapping("/quality")
    public Result<?> quality() {
        QualityBimDomain qualityBimDomain = new QualityBimDomain();

        //质量问题列表
        boolean flag = false;
        QualityBimDomain.QualityProblem qualityProblem = new QualityBimDomain.QualityProblem();
        ArrayList<QualityBimDomain.QualityProblem> problemArrayList = Lists.newArrayList();

        List<com.ruoyi.system.entity.QualityProblem> qualityProblems =
                qualityProblemService.queryByPage(new com.ruoyi.system.entity.QualityProblem());

        if (!CollectionUtils.isEmpty(qualityProblems)) {
            flag = true;
            qualityProblems.forEach(val -> {
                QualityBimDomain.QualityProblem qualityModel = new QualityBimDomain.QualityProblem();
                if (val.getCheckStatus() != null && val.getCheckStatus().equals(HiddenDangerStatus.UNDER.getCode())) {
                    qualityModel.setState(HiddenDangerStatus.UNDER.getDesc());
                }
                if (val.getCheckStatus() != null && val.getCheckStatus().equals(HiddenDangerStatus.AUDIT.getCode())) {
                    qualityModel.setState(HiddenDangerStatus.AUDIT.getDesc());
                }
                if (val.getCheckStatus() != null && val.getCheckStatus().equals(HiddenDangerStatus.OK.getCode())) {
                    qualityModel.setState(HiddenDangerStatus.OK.getDesc());
                }

                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                if (null != val.getCreateTime()) {
                    String dateStr = val.getCreateTime().format(fmt);
                    qualityModel.setTime(dateStr);
                    qualityModel.setReporTime(dateStr);
                }

                qualityModel.setId(val.getId());
                qualityModel.setStatus(val.getCheckStatus());
                qualityModel.setInfo(val.getProblemInfo());
                qualityModel.setRegion(val.getRegion());
                qualityModel.setProblemdescription(val.getProblemInfo());
                qualityModel.setReportPerson(val.getCreateUserName());
                qualityModel.setRectifier(val.getProblemAuditUserName());
                qualityModel.setRectifierTime(DateUtils.formatDate(val.getAbarbeitungTime(), "yyyy-MM-dd HH:mm:ss"));
                qualityModel.setBeforeRectificationImage(val.getProblemFileUrl());
                qualityModel.setAfterRectificationImage(val.getFileUrl());
                problemArrayList.add(qualityModel);
            });
        }
        qualityProblem.setArray(problemArrayList);
        qualityBimDomain.setDaizhenggaizhiliangwenti(qualityProblem);

        //质量问题负责人
        if (flag) {
            QualityBimDomain.Head head = new Head();
            List<String> name = new ArrayList<>();
            List<String> value = new ArrayList<>();

            Map<String, List<com.ruoyi.system.entity.QualityProblem>> collect = qualityProblems.stream()
                    .collect(Collectors.groupingBy(com.ruoyi.system.entity.QualityProblem::getProblemAuditUserName));
            if (MapUtils.isNotEmpty(collect)) {
                collect.forEach((k, v) -> {
                    if ("admin".equals(k)) {
                        name.add("管理员");
                    } else {
                        name.add(k);
                    }
                    value.add(String.valueOf(v.size()));
                });

                head.setName(name);
                head.setValue(value);
            }

            qualityBimDomain.setFuzeren(head);
        }

        //质量问题等级
        if (flag) {
            QualityBimDomain.QualityGrade qualityGrade = new QualityBimDomain.QualityGrade();
            qualityGrade.setZhongda(String.valueOf(qualityProblems.stream().filter(val -> val.getProblemLevel().equals("重大")).count()));
            qualityGrade.setZhongdeng(String.valueOf(qualityProblems.stream().filter(val -> val.getProblemLevel().equals("中等")).count()));
            qualityGrade.setPutong(String.valueOf(qualityProblems.stream().filter(val -> val.getProblemLevel().equals("普通")).count()));
            qualityBimDomain.setZhiliangwentidengjifenbu(qualityGrade);
        }

        //质量问题统计
        if (flag) {
            Map<String, List<com.ruoyi.system.entity.QualityProblem>> collect = qualityProblems.stream()
                    .collect(Collectors.groupingBy(com.ruoyi.system.entity.QualityProblem::getMoonBase));

            QualityCount qualityCount = new QualityCount();
            ArrayList<String> newArrayList = Lists.newArrayList();
            String str = "01,02,03,04,05,06,07,08,09,10,11,12";
            String[] split = str.split(",");
            for (String var : split) {
                if (collect.containsKey(var)) {
                    newArrayList.add(String.valueOf(collect.get(var).size()));
                } else {
                    newArrayList.add("0");
                }
            }
            qualityCount.setQuannian(newArrayList);
            qualityBimDomain.setZhiliangwentitongji(qualityCount);
        }

        //质量问题分类
        if (flag) {
            Map<String, List<com.ruoyi.system.entity.QualityProblem>> collect = qualityProblems.stream()
                    .collect(Collectors.groupingBy(com.ruoyi.system.entity.QualityProblem::getPatternBase));
            QualityDistribution qualityDistribution = new QualityDistribution();
            if (collect.containsKey("模板")) {
                qualityDistribution.setMoban(String.valueOf(collect.get("模板").size()));
            } else {
                qualityDistribution.setMoban("0");
            }
            if (collect.containsKey("钢筋")) {
                qualityDistribution.setGangjin(String.valueOf(collect.get("钢筋").size()));
            } else {
                qualityDistribution.setGangjin("0");
            }
            if (collect.containsKey("混凝土")) {
                qualityDistribution.setHunningtu(String.valueOf(collect.get("混凝土").size()));
            } else {
                qualityDistribution.setHunningtu("0");
            }
            if (collect.containsKey("其他")) {
                qualityDistribution.setQiti(String.valueOf(collect.get("其他").size()));
            } else {
                qualityDistribution.setQiti("0");
            }
            qualityBimDomain.setZhiliangwentifenbu(qualityDistribution);
        }

        //质量问题整改用时
        if (flag) {
            Map<String, List<com.ruoyi.system.entity.QualityProblem>> collect = qualityProblems.stream()
                    .collect(Collectors.groupingBy(com.ruoyi.system.entity.QualityProblem::getMoonBase));
            QualityBimDomain.QualityRectification qualityRectification = new QualityBimDomain.QualityRectification();
            ArrayList<String> newArrayList = Lists.newArrayList();
            String str = "01,02,03,04,05,06,07,08,09,10,11,12";
            String[] split = str.split(",");

            for (String var : split) {
                if (collect.containsKey(var)) {
                    Integer i = 0;
                    List<com.ruoyi.system.entity.QualityProblem> qualityProblemList = collect.get(var);
                    for (com.ruoyi.system.entity.QualityProblem pro : qualityProblemList) {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String dateStr = pro.getCreateTime().format(fmt);
                        long dateDiff = DateToUtils.dateDiff(DateUtils.parseDate(dateStr), pro.getAbarbeitungTime());
                        i += (int) TimeUnit.MILLISECONDS.toHours(dateDiff);
                    }
                    newArrayList.add(String.valueOf(i));
                } else {
                    newArrayList.add("0");
                }
            }
            qualityRectification.setQuannian(newArrayList);
            qualityBimDomain.setZhiliangwentizhenggaiyongshi(qualityRectification);
        }

        //暂无数据
        QualityTrend qualityTrend = new QualityTrend();
        ArrayList<String> trendArrayList = Lists.newArrayList();
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        trendArrayList.add("0");
        qualityTrend.setQuannian(trendArrayList);
        qualityBimDomain.setZhiliangwentifashengqushi(qualityTrend);

        return Result.OK(qualityBimDomain);
    }

    /**
     * 设备管理
     *
     * @return 结果
     */
    @PostMapping("/equipment")
    public Result<?> equipment() {
        EquipmentBimDomain equipmentBimDomain = new EquipmentBimDomain();
        List<EquipmentBimDomain.DeviceDetails> array = new ArrayList<>();
        List<Item> items = service.selectList(new ItemQuery(), null);
        List<ItemVO> list = service.toVos(items);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(val -> {
                EquipmentBimDomain.DeviceDetails deviceDetails = new EquipmentBimDomain.DeviceDetails();
                deviceDetails.setShebeimingcheng(val.getItemName());
                if (val.getStatusBase().equals(YnEnum.Y.getCode())) {
                    deviceDetails.setShebeizhuangtai(YnEnum.Y.getDesc());
                } else {
                    deviceDetails.setShebeizhuangtai(YnEnum.N.getDesc());
                }
                deviceDetails.setCreateTime(val.getCreateTime());
                array.add(deviceDetails);
            });
        }
        EquipmentBimDomain.Device device = new EquipmentBimDomain.Device();
        device.setNormal(String.valueOf((int) list.stream().filter(var -> var.getStatusBase().equals(YnEnum.Y.getCode())).count()));
        device.setError(String.valueOf((int) list.stream().filter(var -> var.getStatusBase().equals(YnEnum.N.getCode())).count()));
        device.setArray(array);
        equipmentBimDomain.setShebeidianwei(device);

        EquipmentBimDomain.WmsOrderCount wmsOrderCount = new EquipmentBimDomain.WmsOrderCount();
        List<ReceiptOrderVO> receiptOrderVOS = receiptOrderService.selectList(new ReceiptOrderQuery());
        wmsOrderCount.setInOrder(receiptOrderVOS.size());

        List<ShipmentOrderVO> shipmentOrderVOS = shipmentOrderService.queryLists(new ShipmentOrderQuery());
        wmsOrderCount.setOutOrder(shipmentOrderVOS.size());

        wmsOrderCount.setTransferOrder(0);
        equipmentBimDomain.setWmsOrderCount(wmsOrderCount);

        return Result.OK(equipmentBimDomain);
    }


    /**
     * top栏信息
     */
    @GetMapping("/top")
    public Map<String, Object> top() {

        QueryWrapper<IotTsp> iotTspQueryWrapper = new QueryWrapper<>();
        iotTspQueryWrapper.orderByDesc("id");
        iotTspQueryWrapper.last("limit 1");
        IotTsp iotTsp = iotTspService.getOne(iotTspQueryWrapper);

        // 创建 topinfo 的内容
        Map<String, Object> topinfo = new HashMap<>();
        //天气
        topinfo.put("tianqi", iotTsp.getWeather());
        //气压
        topinfo.put("qiya", iotTsp.getPressure()+" kPa");
        //温度
        topinfo.put("wendu", iotTsp.getTemperature() + " ℃");
        //湿度
        topinfo.put("shidu", iotTsp.getHumidity() + " %rh");
        //风向
        topinfo.put("fengxiang", iotTsp.getWindDirection());
        //风速
        topinfo.put("fengsu", iotTsp.getWindSpeed() + " m/s");

        // 创建 result 的内容
        Map<String, Object> result = new HashMap<>();
        result.put("topinfo", topinfo);

        // 创建最终 JSON
        Map<String, Object> finalJson = new HashMap<>();
        finalJson.put("result", result);
        return finalJson;
    }

}
