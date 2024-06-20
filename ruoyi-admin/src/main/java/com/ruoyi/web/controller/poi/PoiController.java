package com.ruoyi.web.controller.poi;

import cn.hutool.core.collection.CollUtil;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * 操作word
 * @author zhou
 */
@RestController
public class PoiController {
    // http://localhost:8080/poi/barCharts
    @GetMapping("/poi/barCharts")
    public void barCharts(HttpServletResponse response) {
        try (InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("word/word.docx");
             ServletOutputStream out = response.getOutputStream()) {
            if (is == null) {
                throw new RuntimeException("未找到模板信息");
            }
            XWPFDocument doc = new XWPFDocument(is);
            this.doBarCharts(PoiChartsTools.getPOIXMLDocumentPart(doc,
                    "/word/charts/chart1.xml"));
            this.doLineChart(PoiChartsTools.getPOIXMLDocumentPart(doc,
                    "/word/charts/chart2.xml"));
            this.doPieCharts(PoiChartsTools.getPOIXMLDocumentPart(doc,
                    "/word/charts/chart3.xml"));
            XWPFTable table1 = PoiWordUtil.getTable(doc, 0);
            XWPFTable table2 = PoiWordUtil.getTable(doc, 1);
            this.insertTable(table1);
            this.replaceTable(table2);
 
            Map<String, String> map = new HashMap<>();
            map.put("var", "D:/fjFile/20210529111249.jpg");
            PoiWordUtil. replaceParams(doc,map);
 
            // 图片数据
            Map<String, String> imgMap = new HashMap<>();
            imgMap.put("img", "D:/fjFile/20210529111249.jpg");
            PoiWordUtil.doParagraphs(doc,imgMap);
 
            response.setContentType("application/octet-stream");
            String filename = "test.docx";
            response.setHeader("Content-disposition", "attachment;filename="
                    + URLEncoder.encode(filename, "UTF-8"));
            doc.write(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 处理图表数据-柱状图
     */
    private void doBarCharts(POIXMLDocumentPart chart1) {
        // 行 标题
        List<String> titleArr = CollUtil.newArrayList("类别", "系列1", "系列2", "系列3");
        // 字段名
        List<String> fldNameArr = CollUtil.newArrayList("item1", "item2", "item3", "item4");
        // 数据集合
        List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 5; i++) {
            Map<String, String> base1 = new HashMap<>(4);
            base1.put("item1", "柱状图" + i);
            base1.put("item2", "100");
            base1.put("item3", "20");
            base1.put("item4", "3");
            listItemsByType.add(base1);
        }
        PoiChartsTools.replaceBarCharts(chart1, titleArr, fldNameArr, listItemsByType);
    }
 
 
    /**
     * 处理图表数据-折线图
     */
    private void doLineChart(POIXMLDocumentPart chart) {
        // 行 标题
        List<String> titleArr = CollUtil.newArrayList("类别", "系列1", "系列2", "系列3");
        // 字段名
        List<String> fldNameArr = CollUtil.newArrayList("item1", "item2", "item3", "item4");
        // 数据集合
        List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 5; i++) {
            Map<String, String> base1 = new HashMap<>(4);
            base1.put("item1", "折线图" + i);
            base1.put("item2", "100");
            base1.put("item3", "20");
            base1.put("item4", "3");
            listItemsByType.add(base1);
        }
        PoiChartsTools.replaceLineCharts(chart, titleArr, fldNameArr, listItemsByType);
    }
 
    /**
     * 处理图表数据-饼图
     */
    private void doPieCharts(POIXMLDocumentPart chart) {
        // 行 标题
        List<String> titleArr = CollUtil.newArrayList("类别", "系列1", "系列2", "系列3");
        // 字段名
        List<String> fldNameArr = CollUtil.newArrayList("item1", "item2", "item3", "item4");
        // 数据集合
        List<Map<String, String>> listItemsByType = new ArrayList<Map<String, String>>();
        for (int i = 1; i < 5; i++) {
            Map<String, String> base1 = new HashMap<>(4);
            base1.put("item1", "饼图" + i);
            base1.put("item2", "100");
            base1.put("item3", "20");
            base1.put("item4", "3");
            listItemsByType.add(base1);
        }
        PoiChartsTools.replacePieCharts(chart, titleArr, fldNameArr, listItemsByType);
    }
 
    /**
     * 处理表格-新增行
     */
    private void insertTable(XWPFTable table) {
        List<List<String[]>> tableList = new ArrayList<>();
        List<String[]> table1 = new ArrayList<>();
        table1.add(new String[]{"1", "张三", "男", "22", "186xxxxxxxx"});
        table1.add(new String[]{"2", "李四", "女", "23", "187xxxxxxxx"});
        table1.add(new String[]{"3", "王五", "男", "24", "188xxxxxxxx"});
        table1.add(new String[]{"4", "赵六", "女", "25", "189xxxxxxxx"});
        tableList.add(table1);
        PoiWordUtil.insertTable(table, table1, 2);
        PoiWordUtil.mergeCellsHorizontal(table, 0, 0, 1);
        PoiWordUtil.mergeCellsHorizontal(table, 0, 3, 4);
    }
 
    /**
     * 处理表格-替换表格中信息
     */
    private void replaceTable(XWPFTable table) {
        Map<String, String> params = new HashMap<>();
        params.put("name", "XXXWord");
        params.put("start", "2021-04-21");
        params.put("end", "2021-04-28");
        PoiWordUtil.replaceTableParams(table, params);
    }
 
 
}