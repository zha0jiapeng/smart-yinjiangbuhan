package com.ruoyi.web.controller.basic.view;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.qrcode.QRCodeUtils;
import com.ruoyi.system.domain.FlowPathConfig;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.SysWorkPeopleIntegralDetails;
import com.ruoyi.system.domain.basic.IotStaffAttendance;
import com.ruoyi.system.domain.vo.SysWorkPeopleVO;
import com.ruoyi.system.mapper.IotStaffAttendanceMapper;
import com.ruoyi.system.mapper.SysWorkPeopleMapper;
import com.ruoyi.system.service.FlowPathConfigService;
import com.ruoyi.system.service.SysWorkPeopleIntegralDetailsService;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("work/people")
public class SysWorkPeopleController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private SysWorkPeopleService sysWorkPeopleService;
    @Resource
    private SysWorkPeopleMapper sysWorkPeopleMapper;
    @Resource
    private SysWorkPeopleIntegralDetailsService sysWorkPeopleIntegralDetailsService;

    @Autowired
    private FlowPathConfigService flowPathConfigService;

    @Resource
    private IotStaffAttendanceMapper iotStaffAttendanceMapper;

    @Autowired
    private FastFileStorageClient storageClient;

    @GetMapping
    public TableDataInfo list(SysWorkPeople sysWorkPeople) {
        startPage();
        QueryWrapper<SysWorkPeople> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysWorkPeople.getName())) {
            queryWrapper.eq("name", sysWorkPeople.getName());
        }
        if (StringUtils.isNotEmpty(sysWorkPeople.getPhone())) {
            queryWrapper.eq("phone", sysWorkPeople.getPhone());
        }
        if (StringUtils.isNotEmpty(sysWorkPeople.getWorkType())) {
            queryWrapper.eq("work_type", sysWorkPeople.getWorkType());
        }
        if (null != sysWorkPeople.getYn()) {
            queryWrapper.eq("yn", YnEnum.N.getCode());
        } else {
            queryWrapper.eq("yn", YnEnum.Y.getCode());
        }
        queryWrapper.orderByDesc("integral");
        List<SysWorkPeople> listPage = sysWorkPeopleService.list(queryWrapper);

        if (!CollectionUtils.isEmpty(listPage)) {
            List<Integer> collect = listPage.stream().map(SysWorkPeople::getId).collect(Collectors.toList());
            QueryWrapper<SysWorkPeopleIntegralDetails> wrapper = new QueryWrapper<>();
            wrapper.in("pid", collect);
            List<SysWorkPeopleIntegralDetails> list = sysWorkPeopleIntegralDetailsService.list(wrapper);

            if (!CollectionUtils.isEmpty(list)) {
                Map<Integer, List<SysWorkPeopleIntegralDetails>> listMap = list.stream().collect(Collectors.groupingBy(SysWorkPeopleIntegralDetails::getPid));
                for (SysWorkPeople var : listPage) {
                    List<SysWorkPeopleIntegralDetails> sysWorkPeopleIntegralDetails = listMap.get(var.getId());
                    if (!CollectionUtils.isEmpty(sysWorkPeopleIntegralDetails)) {
                        sysWorkPeopleIntegralDetails = sysWorkPeopleIntegralDetails.stream()
                                .sorted(Comparator.comparing(SysWorkPeopleIntegralDetails::getId).reversed()).collect(Collectors.toList());
                        List<SysWorkPeople.AmassDetails> amassDetailsList = new ArrayList<>();
                        for (SysWorkPeopleIntegralDetails sysWorkPeopleIntegralDetail : sysWorkPeopleIntegralDetails) {
                            SysWorkPeople.AmassDetails amassDetails = new SysWorkPeople.AmassDetails();
                            BeanUtils.copyBeanProp(amassDetails, sysWorkPeopleIntegralDetail);
                            amassDetails.setIntegral(sysWorkPeopleIntegralDetail.getIntegral());
                            amassDetails.setRemarks(sysWorkPeopleIntegralDetail.getIntegralDetails());
                            amassDetailsList.add(amassDetails);
                        }
                        var.setAmassDetails(amassDetailsList);
                    }
                }
            }
        }

        return getDataTable(listPage);
    }

    @PostMapping
    public AjaxResult add(@RequestBody SysWorkPeople sysWorkPeople) {

        //根据手机号 + 名称判断是否已经存在
        QueryWrapper<SysWorkPeople> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(sysWorkPeople.getName())) {
            queryWrapper.eq("name", sysWorkPeople.getName());
        }
        if (StringUtils.isNotEmpty(sysWorkPeople.getPhone())) {
            queryWrapper.eq("phone", sysWorkPeople.getPhone());
        }

        List<SysWorkPeople> list = sysWorkPeopleService.list(queryWrapper);

        if (!CollectionUtils.isEmpty(list)) {
            return AjaxResult.error("已经存在");
        }

        if (null != sysWorkPeople.getAppCase() && sysWorkPeople.getAppCase().equals(YnEnum.Y.getCode())) {
            sysWorkPeople.setCreatedBy(sysWorkPeople.getName());
            sysWorkPeople.setCreatedDate(new Date());
            sysWorkPeople.setModifyBy(sysWorkPeople.getName());
            sysWorkPeople.setModifyDate(new Date());
            sysWorkPeople.setYn(YnEnum.Y.getCode());

            this.sysWorkPeopleService.insert(sysWorkPeople);

            String link = "";
            QRCodeUtils qrCode = new QRCodeUtils(100, 100);
            qrCode.setMargin(1);
            String content = "http://218.17.102.70:39080/aqjf/sm" + "/" + sysWorkPeople.getId();
            BufferedImage image = qrCode.getBufferedImage(content);
            try {
                //以流的方式将图片上传到fastdfs上：
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                // 调用FastDFS中的接口将数据流保存到服务器返回图片地址
                StorePath storePath = storageClient.uploadImageAndCrtThumbImage(inputStream, inputStream.available(), "png", null);
                link = storePath.getFullPath();
            } catch (IOException ex) {
                log.error("新增题库 发生异常:{}", ex.toString());
            }

            SysWorkPeopleVO sysWorkPeopleVO = new SysWorkPeopleVO();
            sysWorkPeopleVO.setId(sysWorkPeople.getId());

            //记录二维码实际对应的链接
            sysWorkPeopleVO.setEnclosureName(content);
            //将服务器地址拼接进
            sysWorkPeopleVO.setQrCode(link);

            log.info("更新参数:{}", JSON.toJSONString(sysWorkPeopleVO));

            sysWorkPeopleMapper.updateByCode(sysWorkPeopleVO);

            return AjaxResult.success();
        }

        sysWorkPeople.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        sysWorkPeople.setCreatedDate(new Date());
        sysWorkPeople.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        sysWorkPeople.setModifyDate(new Date());
        sysWorkPeople.setYn(YnEnum.Y.getCode());
        this.sysWorkPeopleService.insert(sysWorkPeople);
        return AjaxResult.success();
    }

    @PutMapping
    public Result<?> edit(@RequestBody SysWorkPeople sysWorkPeople) {
        sysWorkPeople.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        sysWorkPeople.setModifyDate(new Date());
        sysWorkPeopleService.updateById(sysWorkPeople);
        return Result.OK("编辑成功!");
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        sysWorkPeopleService.removeById(id);
        return Result.OK("删除成功!");
    }

    @Log(title = "现场工人信息", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysWorkPeople> util = new ExcelUtil<>(SysWorkPeople.class);
        List<SysWorkPeople> sysWorkPeople = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = "";//sysWorkPeopleService.importExcel(sysWorkPeople, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @Log(title = "现场工人信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        QueryWrapper<SysWorkPeople> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<SysWorkPeople> list = sysWorkPeopleService.list(queryWrapper);
        ExcelUtil<SysWorkPeople> util = new ExcelUtil<>(SysWorkPeople.class);
        util.exportExcel(response, list, "现场工人信息");
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<SysWorkPeople> util = new ExcelUtil<>(SysWorkPeople.class);
        util.importTemplateExcel(response, "现场工人信息");
    }

    @PostMapping("/integral/detail")
    public AjaxResult addIntegralDetail(@RequestBody SysWorkPeopleIntegralDetails sysWorkPeopleIntegralDetails) {
        QueryWrapper<FlowPathConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        queryWrapper.eq("flow_path_code", Constants.SECURE_INTEGRAL_DECREASE);
        List<FlowPathConfig> list = flowPathConfigService.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return AjaxResult.error("未配置审批流程!");
        }

        List<Integer> flowReviewedByIds = list.stream().map(FlowPathConfig::getFlowReviewedById).collect(Collectors.toList());

        //获取当前登录用户
        Long userId = SecurityUtils.getLoginUser().getUserId();

        if (!flowReviewedByIds.contains(userId.intValue())) {
            return AjaxResult.error("未获得审批权限,无法完成!");
        }

        if (null != sysWorkPeopleIntegralDetails.getType() && sysWorkPeopleIntegralDetails.getType().equals(YnEnum.N.getCode())) {
            sysWorkPeopleIntegralDetails.setIntegral(sysWorkPeopleIntegralDetails.getIntegral().negate());
        }

        sysWorkPeopleIntegralDetails.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        sysWorkPeopleIntegralDetails.setCreatedDate(new Date());
        sysWorkPeopleIntegralDetails.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        sysWorkPeopleIntegralDetails.setModifyDate(new Date());
        sysWorkPeopleIntegralDetails.setYn(YnEnum.Y.getCode());
        this.sysWorkPeopleIntegralDetailsService.insert(sysWorkPeopleIntegralDetails);

        List<SysWorkPeopleIntegralDetails> workPeopleIntegralDetails =
                this.sysWorkPeopleIntegralDetailsService.queryByPid(sysWorkPeopleIntegralDetails.getPid());
        if (!CollectionUtils.isEmpty(workPeopleIntegralDetails)) {
            BigDecimal bigDecimal = BigDecimal.ZERO;
            for (SysWorkPeopleIntegralDetails sysWorkPeopleIntegralDetailsCase : workPeopleIntegralDetails) {
                BigDecimal varIntegral = sysWorkPeopleIntegralDetailsCase.getIntegral();
                if (null != varIntegral) {
                    System.out.println(varIntegral);
                    bigDecimal = bigDecimal.add(varIntegral);
                }
            }

            SysWorkPeople sysWorkPeople = new SysWorkPeople();
            sysWorkPeople.setId(sysWorkPeopleIntegralDetails.getPid());
            sysWorkPeople.setIntegral(bigDecimal);
            sysWorkPeopleMapper.updateIntegralByPid(sysWorkPeople);
        } else {
            SysWorkPeople sysWorkPeople = new SysWorkPeople();
            sysWorkPeople.setId(sysWorkPeopleIntegralDetails.getPid());
            sysWorkPeople.setIntegral(new BigDecimal(0));
            sysWorkPeopleMapper.updateIntegralByPid(sysWorkPeople);
        }

        return AjaxResult.success();
    }

    public String getUsername() {
        return SecurityUtils.getLoginUser().getUsername();
    }

    @GetMapping("/integral/detial/{pid}")
    public ResponseEntity<List<SysWorkPeopleIntegralDetails>> queryByPid(@PathVariable(name = "pid") Integer pid) {
        return ResponseEntity.ok(this.sysWorkPeopleIntegralDetailsService.queryByPid(pid));
    }

    @PostMapping("kaoqin")
    public AjaxResult kaoqin(@RequestBody SysWorkPeople sysWorkPeople) {
        List<IotStaffAttendance> attendanceList = new ArrayList<>();

        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dayBegin = dateformat.format(DateToUtils.getDayBegin());
        String dayEnd = dateformat.format(DateToUtils.getDayEnd());
        List<IotStaffAttendance> list = iotStaffAttendanceMapper.queryAll(dayBegin, dayEnd);

        if (CollectionUtils.isEmpty(list)) {
            return AjaxResult.success(attendanceList);
        }

        Map<String, List<IotStaffAttendance>> collect = list.stream().filter(val -> val.getPhone() != null)
                .collect(Collectors.groupingBy(IotStaffAttendance::getPhone));

        if (!MapUtils.isEmpty(collect)) {
            collect.forEach((k, v) -> {
                IotStaffAttendance iotStaffAttendance = new IotStaffAttendance();
                iotStaffAttendance.setPhone(k);
                if (!CollectionUtils.isEmpty(v)) {

                }
                attendanceList.add(iotStaffAttendance);
            });
        }

        return AjaxResult.success(attendanceList);
    }


    @PostMapping("/updateByCode")
    public AjaxResult updateByCode(@RequestBody SysWorkPeopleVO sysWorkPeopleVO) {
        Integer update = sysWorkPeopleMapper.updateByCode(sysWorkPeopleVO);
        if (1 != update) {
            return AjaxResult.error("更新失败");
        }
        return AjaxResult.success("更新成功");
    }

    @PostMapping("/departure")
    public AjaxResult departure(@RequestBody SysWorkPeople sysWorkPeople) {
        sysWorkPeople.setYn(YnEnum.N.getCode());
        sysWorkPeople.setDepartureDate(new Date());
        sysWorkPeopleMapper.updWorkPeopleDeparture(sysWorkPeople);
        return AjaxResult.success("离场成功");
    }

    @PostMapping("/getWork")
    public AjaxResult getCompany(@RequestBody SysWorkPeople sysWorkPeople) {
        QueryWrapper<SysWorkPeople> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("work_type", sysWorkPeople.getWorkType());
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<SysWorkPeople> sysWorkPeopleList = sysWorkPeopleMapper.selectList(queryWrapper);

        return AjaxResult.success(sysWorkPeopleList);
    }

    @PostMapping("/sync")
    public AjaxResult syncWorkPeople(@RequestBody List<SysWorkPeople> sysWorkPeople) {
        if (CollectionUtils.isEmpty(sysWorkPeople)) {
            return AjaxResult.error("同步失败");
        }
        AtomicInteger updateCount = new AtomicInteger(0);
        AtomicInteger insertCount = new AtomicInteger(0);
        sysWorkPeople.stream().filter(Objects::nonNull)
                .filter(p -> StringUtils.isNoneBlank(p.getName(), p.getIdCard()))
                .peek(p -> {
                    p.setYn(ObjectUtils.defaultIfNull(p.getYn(), YnEnum.Y.getCode()));
                    p.setPhone(StringUtils.defaultIfBlank(p.getPhone(), ""));
                    p.setWorkType(StringUtils.defaultIfBlank(p.getWorkType(), "劳务人员"));
                    p.setCompany(StringUtils.defaultIfBlank(p.getCompany(), "土建4标"));
                    p.setGroupsName(StringUtils.defaultIfBlank(p.getGroupsName(), "项目部"));
                    p.setSex(ObjectUtils.defaultIfNull(p.getSex(), 1));
                }).filter(p -> {
                    QueryWrapper<SysWorkPeople> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("id_card", p.getIdCard());
                    queryWrapper.eq("company", p.getCompany());
                    final List<SysWorkPeople> people = sysWorkPeopleMapper.selectList(queryWrapper);
                    // by idcard company查询 已存在的更新
                    if (CollectionUtils.isNotEmpty(people)) {
                        people.stream().peek(
                                o -> {
                                    o.setPhone(p.getPhone());
                                    o.setYn(p.getYn());
                                    o.setName(p.getName());
                                    o.setWorkType(p.getWorkType());
                                    o.setGroupsName(p.getGroupsName());
                                    o.setSex(p.getSex());
                                    o.setModifyBy("system_sync");
                                    o.setModifyDate(new Date());
                                    updateCount.getAndIncrement();
                                }
                        ).forEach(sysWorkPeopleMapper::updateById);
                        return false;
                    }
                    return true;
                }).filter(p -> YnEnum.Y.getCode().equals(p.getYn())) // 过滤已删除的用户 不入库
                .peek(p -> {
                    // 创建时间和创建人
                    p.setCreatedDate(new Date());
                    p.setCreatedBy("system_sync");
                    insertCount.getAndIncrement();
                }).forEach(sysWorkPeopleMapper::insert);
        final Map<String, Integer> result = new HashMap<>();
        result.put("insertCount", insertCount.get());
        result.put("updateCount", updateCount.get());
        return AjaxResult.success("同步成功", result);
    }

}
