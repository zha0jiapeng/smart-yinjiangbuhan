package com.ruoyi.web.controller.basic.yinjiangbuhan.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.ruoyi.web.controller.basic.yinjiangbuhan.bean.Staff;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @RequestMapping("/import")
    public Map<String,Object> excelImport(@RequestParam("file") MultipartFile file ){
        try {
            // Read the Excel file
            List<Staff> staffList = EasyExcel.read(file.getInputStream())
                    .head(Staff.class)
                    .sheet()
                    .doReadSync();
            System.out.println(JSON.toJSONString(staffList));

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
