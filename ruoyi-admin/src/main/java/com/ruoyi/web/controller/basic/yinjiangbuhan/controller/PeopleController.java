package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.MinioUtils;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.SysWorkPeopleInoutLog;
import com.ruoyi.system.mapper.SysWorkPeopleInoutLogMapper;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.Staff;
import com.ruoyi.web.controller.basic.yinjiangbuhan.listen.PeopleImportListen;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import com.ruoyi.web.core.config.MinioConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
@Slf4j
public class PeopleController {
    @Resource
    SwzkHttpUtils swzkHttpUtils;

    @Resource
    MinioUtils minioUtil;

    @Resource
    MinioConfig minioConfig;

    @Resource
    SysWorkPeopleInoutLogMapper sysWorkPeopleInoutLogMapper;

    @Resource
    private SysWorkPeopleService workPeopleService;

    @RequestMapping("/import")
    public Map<String,Object> excelImport(@RequestParam("file") MultipartFile file ){
        try {
            PeopleImportListen listener = new PeopleImportListen();
            EasyExcel.read(file.getInputStream(), Staff.class, listener).sheet().doRead();

            // Read the Excel file
            List<Staff> staffList = EasyExcel.read(file.getInputStream())
                    .head(Staff.class)
                    .sheet()
                    .doReadSync();
            savePeople(staffList);
            pushSwzk(staffList);

            return AjaxResult.success("导入成功");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/uploadPeopleSpecialWorkerFile/{idCard}")
    public AjaxResult uploadPeopleSpecialWorkerFile(@PathVariable("idCard") String idCard,@RequestParam("file") MultipartFile file){
        minioUtil.uploadFile(minioConfig.getSpecialWorkerFileBucketName(), file, idCard, "image/jpeg");
        return AjaxResult.success("上传成功");
    }

    @GetMapping("/getPeopleSpecialWorkerFile/{idCard}")
    public AjaxResult uploadPeopleSpecialWorkerFile(@PathVariable("idCard") String idCard){
        String presignedObjectUrl = minioUtil.getPresignedObjectUrl(minioConfig.getSpecialWorkerFileBucketName(), idCard);
        return AjaxResult.success(presignedObjectUrl);
    }

    @GetMapping("/simulationOut")
    private AjaxResult pushSwzk(Integer peopleId) {
        String now = DateUtil.now();
        SysWorkPeople byId = workPeopleService.getById(peopleId);
        if(byId==null) return AjaxResult.error("找不到人员");
        // Create the main map
        Map<String, Object> mainMap = new HashMap<>();

        // Add top-level fields
        mainMap.put("deviceType", "2001000010");
        mainMap.put("SN", "T99JV01D1DZS");
        mainMap.put("dataType", "200300003");
        mainMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        mainMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        mainMap.put("deviceName", "土建4标-8#-洞口Ⅰ【出场】");

        // Create the 'values' list
        List<Map<String, Object>> valuesList = new ArrayList<>();
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("reportTs", DateUtil.current());
        // Create the 'profile' map
        Map<String, Object> profileMap = new HashMap<>();
        profileMap.put("appType", "access_control");
        profileMap.put("modelId", "2053");
        profileMap.put("poiCode", "w0713001");
        profileMap.put("name", "人脸门禁");
        profileMap.put("model", "S3");
        profileMap.put("manufacture", "海康威视");
        profileMap.put("owner", "海康威视");
        profileMap.put("makeDate", "2020-05-22");
        profileMap.put("validYear", "2050-05-22");
        profileMap.put("state", "01");
        profileMap.put("installPosition", "洞口一级门禁");
        profileMap.put("x", 0);
        profileMap.put("y", 0);
        profileMap.put("z", 0);
        profileMap.put("level", "01");
        valuesMap.put("profile", profileMap);
        // Create the 'events' map
        Map<String, Object> eventsMap = new HashMap<>();
        Map<String, Object> passMap = new HashMap<>();
        passMap.put("eventType", 1);
        passMap.put("eventTs", now);
        passMap.put("describe", "");
        passMap.put("idCardNumber", byId.getIdCard());
        passMap.put("name", byId.getName());
        passMap.put("passTime", now);
        passMap.put("passDirection", "01");
        eventsMap.put("pass", passMap);

        valuesMap.put("events", eventsMap);
        // Add empty 'properties' and 'services' maps
        valuesMap.put("properties", new HashMap<String, Object>());
        valuesMap.put("services", new HashMap<String, Object>());
        // Add the valuesMap to the valuesList
        valuesList.add(valuesMap);
        mainMap.put("values", valuesList);
        log.info("模拟门禁推送：{}", JSON.toJSONString(mainMap));
        swzkHttpUtils.pushIOT(mainMap);
        insertInOutLog(now,"DS-K1T670M20231222V031801CHFH5917119",byId);
        return AjaxResult.success();
    }

    private void insertInOutLog(String now,String sn,SysWorkPeople people) {
        SysWorkPeopleInoutLog sysWorkPeopleInoutLog = new SysWorkPeopleInoutLog();
        sysWorkPeopleInoutLog.setSn(sn);
        sysWorkPeopleInoutLog.setIdCard(people.getIdCard());
       // sysWorkPeopleInoutLog.setSysWorkPeopleId(people.getId());
        sysWorkPeopleInoutLog.setMode(0);
        sysWorkPeopleInoutLog.setLogTime(now);
        sysWorkPeopleInoutLog.setName(people.getName());
        //sysWorkPeopleInoutLog.setPhone(jsonObject.get("telephone").toString());
        sysWorkPeopleInoutLog.setPhotoUrl(null);
        sysWorkPeopleInoutLog.setCreatedDate(new Date());
        sysWorkPeopleInoutLog.setModifyDate(new Date());
        sysWorkPeopleInoutLog.setCreatedBy("simulation");
        sysWorkPeopleInoutLogMapper.insert(sysWorkPeopleInoutLog);
    }

    @GetMapping("/getPeopleNumStatistics")
    public AjaxResult getPeopleNumStatistics( ){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<SysWorkPeople> queryWrapper = Wrappers.query();
        queryWrapper.select("personnel_config_type, COUNT(*) AS count")
                .groupBy("personnel_config_type");
        List<Map<String, Object>> list = workPeopleService.listMaps(queryWrapper);
        Map<Integer, Map<String, Object>> defaultMap = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            Map<String, Object> typeMap = new HashMap<>();
            typeMap.put("personnel_config_type", i);
            typeMap.put("count", 0);
            defaultMap.put(i, typeMap);
        }
        // 更新默认结果的 count 值
        for (Map<String, Object> item : list) {
            Integer type = (Integer) item.get("personnel_config_type");
            defaultMap.put(type, item);
        }

        List<Map<String, Object>> finalList = new ArrayList<>(defaultMap.values());


        int inHoleNum = sysWorkPeopleInoutLogMapper.countOnlyEnteredPeopleToday();
        map.put("inHoleNum",inHoleNum);
        map.put("items",finalList);
        return AjaxResult.success(map);
    }

    @GetMapping("/getAttendanceStatistics")
    public AjaxResult getPeopleAttendanceStatistics(String yearMonth){
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.getPeopleAttendanceStatistics(yearMonth);
        return AjaxResult.success(list);
    }

    @GetMapping("/getAttendanceStatisticsByPeopleType")
    public AjaxResult getAttendanceStatisticsByPeopleType(String year){
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.getMonthlyAttendanceCountByPersonnelConfigType(year);
        Map<String, Map<Integer, Integer>> monthDataMap = new LinkedHashMap<>();

        // 初始化所有可能的 personnel_config_type
        for (Map<String, Object> record : list) {
            String month = (String) record.get("month");
            Integer personnelConfigType = (Integer) record.get("personnel_config_type");
            Integer count = ((Long) record.get("attendance_count")).intValue();

            Map<Integer, Integer> attendanceMap = monthDataMap.computeIfAbsent(month, k -> {
                Map<Integer, Integer> initialMap = new LinkedHashMap<>();
                initialMap.put(1, 0);  // 默认值0
                initialMap.put(2, 0);
                initialMap.put(3, 0);
                initialMap.put(4, 0);
                return initialMap;
            });

            attendanceMap.put(personnelConfigType, count);
        }

        // 生成最终输出
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map.Entry<String, Map<Integer, Integer>> entry : monthDataMap.entrySet()) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("month", entry.getKey());
            resultMap.put("personnelConfigTypeAttendance", entry.getValue());
            resultList.add(resultMap);
        }

        Map<String, Object> finalResult = new HashMap<>();
        finalResult.put("year", year);
        finalResult.put("data", resultList);
        return AjaxResult.success(finalResult);
    }

    @GetMapping("/getPeopleAttendanceStatisticsByWorkType")
    public Map<String,Object> getPeopleAttendanceStatisticsByWorkType(String today){
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.getPeopleAttendanceStatisticsByWorkType(today);
        return AjaxResult.success(list);
    }

    @GetMapping("/getPeopleAttendanceStatisticsByCompany")
    public AjaxResult getPeopleAttendanceStatisticsByCompany(String today){
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.getPeopleAttendanceStatisticsByCompany(today);
        return AjaxResult.success(list);
    }

    @GetMapping("/getPeopleAttendanceForLast7Days")
    public AjaxResult getPeopleAttendanceForLast7Days(){
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.countDailyAttendanceForLast7Days();
        return AjaxResult.success(list);
    }

    //重点人员考勤率
    @GetMapping("/getAttendanceRateByPersonnelConfigType")
    public AjaxResult getAttendanceRateByPersonnelConfigType(){
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.getAttendanceRateByPersonnelConfigType();
        return AjaxResult.success(list);
    }

    //重点人员考勤列表
    @GetMapping("/getAttendanceRateListKeyList")
    public AjaxResult getAttendanceRateListKeyList(@RequestParam("type") Integer type){
        System.out.println(type);
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.getAttendanceRateListKeyList(type);
        return AjaxResult.success(list);
    }

    //工人考勤统计
    @GetMapping("/getAttendanceRateByPeopleId")
    public AjaxResult getWorkerAttendance(
            @RequestParam("peopleId") Long peopleId,
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        LocalDate monthStart = LocalDate.of(year, month, 1);
        LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

        // 获取某个月的天数
        int totalDaysInMonth = monthStart.lengthOfMonth();

        // 查询实际出勤次数
        int attendanceCount = sysWorkPeopleInoutLogMapper.getAttendanceCountForWorker(peopleId, monthStart, monthEnd);

        // 计算缺勤次数
        int absentDays = totalDaysInMonth - attendanceCount;

        // 组装返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("totalDaysInMonth", totalDaysInMonth);
        result.put("attendanceCount", attendanceCount);
        result.put("absentDays", absentDays);
        result.put("peopleId", peopleId);
        return AjaxResult.success(result);
    }

    @GetMapping("/getStayStatistics")
    public Map<String,Object> getStayStatistics(){
        Map<String,Object> response = new HashMap<>();
        List<Map<String, Object>> list = sysWorkPeopleInoutLogMapper.getStayStatistics();
        Map<Object, List<Map<String, Object>>> mapp = list.stream().collect(Collectors.groupingBy(person -> {
            long hoursStayed = (long) person.get("hours_stayed");
            if (hoursStayed >= 0 && hoursStayed < 12) {
                return "onsite_people_list";
            } else if (hoursStayed >= 12 && hoursStayed <= 24) {
                return "onsite_people_over12_list";
            } else {
                return "onsite_people_over24_list";
            }
        }));
        response.put("onsite_people_data",mapp);
        HttpResponse execute = HttpRequest.post("http://192.168.1.200:9501/push/list")
                .body(com.alibaba.fastjson.JSON.toJSONString(new HashMap()), ContentType.JSON.getValue())
                .execute();
        JSONObject jsonObject = JSONUtil.parseObj(execute.body());
        JSONArray data = jsonObject.getJSONArray("data");
        List<String> names= new ArrayList<>();
        for (Object datum : data) {
            JSONObject item = (JSONObject) datum;
            JSONObject userInfo = item.getJSONObject("user_info");
            String number = userInfo.getStr("number");
            String name = userInfo.getStr("user_name");
            String regex = "^\\d{6}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}(\\d|X|x)$";
            boolean isValid = ReUtil.isMatch(regex, number);
            boolean isValid2 = name.length()>4;
            if(isValid && isValid2) {
                names.add(name);
            }
        }
        List<String> namesList = list.stream()
                .map(map -> (String) map.get("name")) // Extract "name" value
                .filter(Objects::nonNull) // Optional: filter out null values if any
                .collect(Collectors.toList());
        List<String> difference = new ArrayList<>(namesList);
        difference.removeAll(names); // 从 listA 中移除所有存在于 listB 的元素
        Integer onsitePeopleCount = list.size();
        BigDecimal divide = new BigDecimal(difference.size()).divide(new BigDecimal(onsitePeopleCount), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(0,RoundingMode.HALF_UP);
        response.put("wear_rate",divide.compareTo(new BigDecimal(100))>0?100:divide);
        response.put("dis_wear_people",difference);
        return AjaxResult.success(response);
    }




    private void savePeople(List<Staff> staffList) {
        List<SysWorkPeople> list = new ArrayList<>();
        for (Staff staff : staffList) {
            SysWorkPeople workPeople = workPeopleService.getOne(
                    new LambdaQueryWrapper<SysWorkPeople>()
                            .eq(SysWorkPeople::getIdCard, staff.getIdCardNo())
                    , false);

            if(workPeople == null) {
                workPeople = new SysWorkPeople();
                workPeople.setCreatedDate(new Date());
            }else{
                workPeople.setModifyDate(new Date());
            }
            log.info("staff.getStaffName:{},{}",staff.getStaffName(),staff.getBimStaffType());
            workPeople.setName(staff.getStaffName());
            workPeople.setSex(staff.getSex().equals("男")?1:0);
            workPeople.setPhone(staff.getPhone());
            workPeople.setIdCard(staff.getIdCardNo());
            workPeople.setWorkType(staff.getWorkerType());
            workPeople.setCompany(staff.getLaborSubCom());
            workPeople.setKeyPersonnelFlag(staff.getKeyPersonnelFlag().equals("是")?1:0);
            workPeople.setSpecialWorkerFlag(staff.getSpecialWorker().equals("是")?1:0);

            switch (staff.getBimStaffType()){
                case "建设单位" : workPeople.setPersonnelConfigType(1); break;
                case "设计单位" : workPeople.setPersonnelConfigType(2); break;
                case "监理单位" : workPeople.setPersonnelConfigType(3); break;
                case "施工单位" : workPeople.setPersonnelConfigType(4); break;
                default:
            }
            workPeople.setYn(1);
            list.add(workPeople);
        }
        workPeopleService.saveOrUpdateBatch(list);
    }




    public void pushSwzk(List<Staff> staffList) {
        // 顶层结构
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("deviceType", "2001000101");
        dataMap.put("SN", "DB0727167DB22268E055000000000005");
        dataMap.put("dataType", "200300027");
        dataMap.put("bidCode", "YJBH-SSZGX_BD-SG-205");
        dataMap.put("workAreaCode", "YJBH-SSZGX_GQ-08");
        dataMap.put("workSurface", "");
        dataMap.put("deviceName", "施工四标智慧工地系统");
        dataMap.put("managementDept", "QY-NSDBJT-JHSW_JGB1");

        // values 数组
        List<Map<String, Object>> valuesList = new ArrayList<>();

        // values 内的单个元素
        Map<String, Object> valuesItem = new HashMap<>();
        valuesItem.put("reportTs", DateUtil.current());

        // properties 结构
        Map<String, Object> propertiesMap = new HashMap<>();

//        // staff 内的单个元素
//        Map<String, Object> staffItem = new HashMap<>();
//        staffItem.put("workerType", "修刀工");
//        staffItem.put("workStatus", "已进场");
//        staffItem.put("nation", "汉族");
//        staffItem.put("idCardNo", "453002190010234315");
//        staffItem.put("sex", "男");
//        staffItem.put("adress", "深圳市福田区下梅林");
//        staffItem.put("comeOut", "");
//        staffItem.put("staffType", "管理人员");
//        staffItem.put("orgId", "土建施工A2标");
//        staffItem.put("emergencyName", "李四");
//        staffItem.put("idExpireDate", "2023-01-01");
//        staffItem.put("phone", "13100000000");
//        staffItem.put("comeIn", "2020-08-07");
//        staffItem.put("specialWorker", "否");
//        staffItem.put("staffName", "张三");
//        staffItem.put("laborSubCom", "深圳市建造工科技有限公司");
//        staffItem.put("emergencyPhone", "13100000001");
//        staffItem.put("accessLevel", "二级");
//        staffItem.put("photoBase64", "xxxxx");
//
//        // 将 staffItem 加入到 staffList
//        staffList.add(staffItem);

        // 将 staffList 加入到 propertiesMap
        propertiesMap.put("staff", staffList);

        // 将 propertiesMap 加入到 valuesItem
        valuesItem.put("properties", propertiesMap);

        // 将 valuesItem 加入到 valuesList
        valuesList.add(valuesItem);

        // 将 valuesList 加入到顶层结构
        dataMap.put("values", valuesList);

        swzkHttpUtils.pushIOT(dataMap);
    }

}
