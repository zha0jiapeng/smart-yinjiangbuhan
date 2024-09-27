package com.ruoyi.web.controller.basic.yinjiangbuhan.listen;

import java.util.*;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.service.SysWorkPeopleService;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.Staff;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class PeopleImportListen implements ReadListener<Staff> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<SysWorkPeople> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private List<Staff> staffs = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private SysWorkPeopleService sysWorkPeopleService;




    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param sysWorkPeopleService
     */
    public PeopleImportListen(SysWorkPeopleService sysWorkPeopleService) {
        this.sysWorkPeopleService = sysWorkPeopleService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param context
     */
    @Override
    public void invoke(Staff staff, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(staff));
        SysWorkPeople workPeople = getSysWorkPeople(staff);
        cachedDataList.add(workPeople);
        staffs.add(staff);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private SysWorkPeople getSysWorkPeople(Staff staff) {
        SysWorkPeople workPeople = sysWorkPeopleService.getOne(
                new LambdaQueryWrapper<SysWorkPeople>()
                        .eq(SysWorkPeople::getIdCard, staff.getIdCardNo())
                , false);

        if(workPeople == null) {
            workPeople = new SysWorkPeople();
            workPeople.setCreatedDate(new Date());
        }else{
            workPeople.setModifyDate(new Date());
        }
        log.info("staff.getStaffName:{},{}", staff.getStaffName(), staff.getBimStaffType());
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
        return workPeople;
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        sysWorkPeopleService.saveOrUpdateBatch(cachedDataList);
        pushSwzk(staffs);
        log.info("存储数据库成功！");
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

        // 将 staffList 加入到 propertiesMap
        propertiesMap.put("staff", staffList);

        // 将 propertiesMap 加入到 valuesItem
        valuesItem.put("properties", propertiesMap);

        // 将 valuesItem 加入到 valuesList
        valuesList.add(valuesItem);

        // 将 valuesList 加入到顶层结构
        dataMap.put("values", valuesList);

        SwzkHttpUtils.pushIOT(dataMap);
    }
}