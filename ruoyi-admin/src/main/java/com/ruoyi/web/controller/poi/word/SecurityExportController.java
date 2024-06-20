package com.ruoyi.web.controller.poi.word;

import com.ruoyi.system.service.SysHiddenDangerService;
import com.ruoyi.web.controller.poi.PoiWordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("security/export")
public class SecurityExportController {

    @Autowired
    private SysHiddenDangerService sysHiddenDangerService;

    @GetMapping("w/{type}")
    public void queryByIdW(@PathVariable("type") Integer type, HttpServletRequest request, HttpServletResponse response) {
        // 设置响应头
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "安全质检问题.docx");
        OutputStream out = null;

        String year = new SimpleDateFormat("yyyy").format(new Date());
        String moon = new SimpleDateFormat("MM").format(new Date());
        String day = new SimpleDateFormat("dd").format(new Date());

        String val = "";
        if (type.equals(1)) {
            val = "日";
        } else if (type.equals(2)) {
            val = "周";
        } else {
            val = "月";
        }

        String docName = year + "年" + moon + "月" + day + "日项目部周检安全质量" + val + "检查简报";

        try {
            String path = "/work/aqwt.docx";
            InputStream is = Files.newInputStream(new File(path).toPath());
            XWPFDocument doc = new XWPFDocument(is);

            //文本替换
            Map<String, String> param = new HashMap<String, String>();
            param.put("year", year);
            param.put("moon", moon);
            param.put("day", day);
            XWPFTable table1 = PoiWordUtil.getTable(doc, 0);
            PoiWordUtil.replaceTableParams(table1, param);

            //根据id查看安全问题
            List<XWPFParagraph> allXWPFParagraphs = doc.getParagraphs();
            for (XWPFParagraph xwpfParagraph : allXWPFParagraphs) {
                List<XWPFRun> runs = xwpfParagraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null && !text.equals("")) {
                        //指定位置开始创建表格 用able作为标记
                        if (text.equals("able")) {
                            //自定义生成list测试数据
                            List<Map<String, String>> list = sysHiddenDangerService.exportProblem(type);

                            //动态集合格式封装成list<map>  这里定义map的key集合
                            List<String> keyList = new ArrayList<String>();
                            keyList.add("id");
                            keyList.add("areaName");
                            keyList.add("problemBase");
                            keyList.add("problem");
                            keyList.add("changeDescribe");
                            keyList.add("dutyPerson");
                            keyList.add("changeDescribe");
                            keyList.add("fileUrl");

                            //表的行数
                            int rows = list.size();
                            //设置表头集合
                            List<String> textList = new ArrayList<String>();
                            textList.add("序号");
                            textList.add("检查地点");
                            textList.add("问题分类");
                            textList.add("不符合项/隐患描述");
                            textList.add("改进措施");
                            textList.add("整改人");
                            textList.add("完成日期");
                            textList.add("现场存在问题的照片");

                            //表的列数
                            int cols = textList.size();
                            XmlCursor cursor = xwpfParagraph.getCTP().newCursor();
                            XWPFTable tableOne = doc.insertNewTbl(cursor);
                            //样式控制
                            CTTbl ttbl = tableOne.getCTTbl();
                            CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
                            CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
                            CTJc cTJc = tblPr.addNewJc();
                            cTJc.setVal(STJc.Enum.forString("center"));//表格居中
                            tblWidth.setW(new BigInteger("15000"));//每个表格宽度
                            tblWidth.setType(STTblWidth.DXA);
                            //表格的表头创建,去上边textList表头集合的字段
                            XWPFTableRow tableRowTitle = tableOne.getRow(0);
                            tableRowTitle.getCell(0).setText(textList.get(0));
                            tableRowTitle.addNewTableCell().setText(textList.get(1));
                            tableRowTitle.addNewTableCell().setText(textList.get(2));
                            tableRowTitle.addNewTableCell().setText(textList.get(3));
                            tableRowTitle.addNewTableCell().setText(textList.get(4));
                            tableRowTitle.addNewTableCell().setText(textList.get(5));
                            tableRowTitle.addNewTableCell().setText(textList.get(6));
                            tableRowTitle.addNewTableCell().setText(textList.get(7));
                            for (Map<String, String> cardMap : list) {
                                //遍历list 得到要导出的每一行表格数据,数据结构是map
                                XWPFTableRow createRow = tableOne.createRow();
                                for (int i = 0; i < keyList.size(); i++) {
                                    if (keyList.get(i).equals("fileUrl")) {
                                        int width = 125;
                                        int height = 125;
                                        try {
                                            URL url = new URL(cardMap.get(keyList.get(i)));
                                            InputStream imageInputStream = url.openStream();
                                            byte[] byteArray = IOUtils.toByteArray(imageInputStream);
                                            XWPFTableCell cell = createRow.getCell(i);
                                            cell.removeParagraph(0);
                                            XWPFParagraph paragraph = cell.addParagraph();
                                            XWPFRun imageCellRun = paragraph.createRun();
                                            imageCellRun.addPicture(new ByteArrayInputStream(byteArray), Document.PICTURE_TYPE_JPEG, "", Units.toEMU(width), Units.toEMU(height));
                                        } catch (Exception e) {
                                            log.error("image error:", e);
                                        }
                                    } else {
                                        createRow.getCell(i).setText(cardMap.get(keyList.get(i)));
                                        createRow.setHeight(2000);
                                    }
                                }
                            }
                            run.setText("", 0);
                        } else {
                            for (Map.Entry<String, String> entry : param.entrySet()) {
                                String key = entry.getKey();
                                int i = text.indexOf(key);
                                if (text.contains(key)) {
                                    text = text.replace(key, entry.getValue());
                                    run.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }
            response.setStatus(200);
            //配置导出的word名称&后缀
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(docName + ".docx", "UTF-8"));
            out = response.getOutputStream();
            doc.write(out);
        } catch (Exception e) {
            log.error("word导出异常");
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                log.error("关闭流信息失败{}", e.toString());
            }
        }
    }

    @GetMapping("g/{type}")
    public void queryByIdG(@PathVariable("type") Integer type, HttpServletRequest request, HttpServletResponse response) {
        // 设置响应头
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "安全质检问题.docx");
        OutputStream out = null;

        String year = new SimpleDateFormat("yyyy").format(new Date());
        String moon = new SimpleDateFormat("MM").format(new Date());
        String day = new SimpleDateFormat("dd").format(new Date());

        String val = "";
        if (type.equals(1)) {
            val = "日";
        } else if (type.equals(2)) {
            val = "周";
        } else {
            val = "月";
        }

        String docName = year + "年" + moon + "月" + day + "日项目部周检安全质量" + val + "检回复单";

        try {
            String path = "/work/aqhf.docx";
            InputStream is = Files.newInputStream(new File(path).toPath());
            XWPFDocument doc = new XWPFDocument(is);

            //文本替换
            Map<String, String> param = new HashMap<String, String>();
            param.put("year", year);
            param.put("moon", moon);
            param.put("day", day);
//            XWPFTable table1 = PoiWordUtil.getTable(doc, 0);
//            PoiWordUtil.replaceTableParams(table1, param);

            //根据id查看安全问题
            List<XWPFParagraph> allXWPFParagraphs = doc.getParagraphs();
            for (XWPFParagraph xwpfParagraph : allXWPFParagraphs) {
                List<XWPFRun> runs = xwpfParagraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null && !text.equals("")) {
                        //指定位置开始创建表格 用able作为标记
                        if (text.equals("able")) {
                            //自定义生成list测试数据
                            List<Map<String, String>> list = sysHiddenDangerService.exportProblemZg(type);

                            //动态集合格式封装成list<map>  这里定义map的key集合
                            List<String> keyList = new ArrayList<String>();
                            keyList.add("id");
                            keyList.add("areaName");
                            keyList.add("problemBase");
                            keyList.add("problem");
                            keyList.add("changeDescribe");
                            keyList.add("dutyPerson");
                            keyList.add("changeDescribe");
                            keyList.add("fileUrl");

                            //表的行数
                            int rows = list.size();
                            //设置表头集合
                            List<String> textList = new ArrayList<String>();
                            textList.add("序号");
                            textList.add("检查地点");
                            textList.add("问题分类");
                            textList.add("不符合项/隐患描述");
                            textList.add("改进措施");
                            textList.add("责任人");
                            textList.add("完成日期");
                            textList.add("整改后照片");

                            //表的列数
                            int cols = textList.size();
                            XmlCursor cursor = xwpfParagraph.getCTP().newCursor();
                            XWPFTable tableOne = doc.insertNewTbl(cursor);
                            //样式控制
                            CTTbl ttbl = tableOne.getCTTbl();
                            CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
                            CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
                            CTJc cTJc = tblPr.addNewJc();
                            cTJc.setVal(STJc.Enum.forString("center"));//表格居中
                            tblWidth.setW(new BigInteger("15000"));//每个表格宽度
                            tblWidth.setType(STTblWidth.DXA);
                            //表格的表头创建,去上边textList表头集合的字段
                            XWPFTableRow tableRowTitle = tableOne.getRow(0);
                            tableRowTitle.getCell(0).setText(textList.get(0));
                            tableRowTitle.addNewTableCell().setText(textList.get(1));
                            tableRowTitle.addNewTableCell().setText(textList.get(2));
                            tableRowTitle.addNewTableCell().setText(textList.get(3));
                            tableRowTitle.addNewTableCell().setText(textList.get(4));
                            tableRowTitle.addNewTableCell().setText(textList.get(5));
                            tableRowTitle.addNewTableCell().setText(textList.get(6));
                            tableRowTitle.addNewTableCell().setText(textList.get(7));
                            for (Map<String, String> cardMap : list) {
                                //遍历list 得到要导出的每一行表格数据,数据结构是map
                                XWPFTableRow createRow = tableOne.createRow();
                                for (int i = 0; i < keyList.size(); i++) {
                                    if (keyList.get(i).equals("fileUrl")) {
                                        int width = 125;
                                        int height = 125;
                                        try {

                                            if (null != cardMap.get(keyList.get(i))) {
                                                URL url = new URL(cardMap.get(keyList.get(i)));
                                                InputStream imageInputStream = url.openStream();
                                                byte[] byteArray = IOUtils.toByteArray(imageInputStream);
                                                XWPFTableCell cell = createRow.getCell(i);
                                                cell.removeParagraph(0);
                                                XWPFParagraph paragraph = cell.addParagraph();
                                                XWPFRun imageCellRun = paragraph.createRun();
                                                imageCellRun.addPicture(new ByteArrayInputStream(byteArray), Document.PICTURE_TYPE_JPEG, "", Units.toEMU(width), Units.toEMU(height));
                                            }
                                        } catch (Exception e) {
                                            log.error("image error:", e);
                                        }
                                    } else {
                                        createRow.getCell(i).setText(cardMap.get(keyList.get(i)));
                                        createRow.setHeight(2000);
                                    }
                                }
                            }
                            run.setText("", 0);
                        } else {
                            for (Map.Entry<String, String> entry : param.entrySet()) {
                                String key = entry.getKey();
                                int i = text.indexOf(key);
                                if (text.contains(key)) {
                                    text = text.replace(key, entry.getValue());
                                    run.setText(text, 0);
                                }
                            }
                        }
                    }
                }
            }
            response.setStatus(200);
            //配置导出的word名称&后缀
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(docName + ".docx", "UTF-8"));
            out = response.getOutputStream();
            doc.write(out);
        } catch (Exception e) {
            log.error("word导出异常");
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                log.error("关闭流信息失败{}", e.toString());
            }
        }
    }

}
