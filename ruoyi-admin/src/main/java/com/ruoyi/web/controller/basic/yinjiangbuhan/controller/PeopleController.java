package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.Staff;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.SwzkHttpUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Resource
    SwzkHttpUtils swzkHttpUtils;
    @RequestMapping("/import")
    public Map<String,Object> excelImport(@RequestParam("file") MultipartFile file ){
        try {
            // Read the Excel file
            List<Staff> staffList = EasyExcel.read(file.getInputStream())
                    .head(Staff.class)
                    .sheet()
                    .doReadSync();
            zuzhuang(staffList);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void zuzhuang(List<Staff> staffList) {
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
