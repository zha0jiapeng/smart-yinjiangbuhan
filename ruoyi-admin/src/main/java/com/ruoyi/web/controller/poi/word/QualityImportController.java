package com.ruoyi.web.controller.poi.word;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.QualityImportService;
import com.ruoyi.web.controller.poi.PoiWordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("quality/import")
public class QualityImportController {
    @Autowired
    private PoiWordUtil poiWordUtil;
    @Resource
    private QualityImportService qualityImportService;

    @PostMapping
    public AjaxResult importTestingInfo(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = file.getOriginalFilename();
            if (null == name || StringUtils.isEmpty(name)) {
                return AjaxResult.error("请上传正确的文件！");
            }

            if (!name.contains(".doc") && !name.contains(".docx")) {
                return AjaxResult.error("请上传正确格式的文件！");
            }

            if (name.contains("docx")) {
                byte[] byteArr = file.getBytes();
                InputStream inputStream = new ByteArrayInputStream(byteArr);
                XWPFDocument doc = new XWPFDocument(inputStream);
                XWPFTable table = PoiWordUtil.getTable(doc, 1);

                Map<String, List<String>> result = new LinkedHashMap<>();
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    int i = 0;
                    String key = null;
                    List<String> list = new ArrayList<>(16);

                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        StringBuilder sb = new StringBuilder();
                        List<XWPFParagraph> paragraphs = cell.getParagraphs();
                        for (XWPFParagraph paragraph : paragraphs) {
                            sb.append(poiWordUtil.getParagraphText(paragraph));
                        }
                        if (i == 0) {
                            key = sb.toString();
                        } else {
                            String value = sb.toString();
                            list.add(Objects.deepEquals(value, "") ? null : value.replace(",", ""));
                        }
                        i++;
                    }
                    result.put(key, list);
                }
                log.info("质量检查 上传word文档 拼装结果:{}", JSON.toJSONString(result));
                qualityImportService.importTestingDocx(result);

            } else {
                List<List<String>> listValue = poiWordUtil.doDoc(file, 2);
                log.info("质量检查 上传word文档 拼装结果:{}", JSON.toJSONString(listValue));
                qualityImportService.importTestingDoc(listValue);
            }
        } catch (Exception e) {
            log.error("质量检查 上传word文档 异常信息:{}", e.toString());
        }

        return AjaxResult.success("导入成功！");
    }


    @PostMapping("reply")
    public AjaxResult importTestingReplyInfo(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = file.getOriginalFilename();
            if (null == name || StringUtils.isEmpty(name)) {
                return AjaxResult.error("请上传正确的文件！");
            }

            if (!name.contains(".doc") && !name.contains(".docx")) {
                return AjaxResult.error("请上传正确格式的文件！");
            }

            if (name.contains("docx")) {
                byte[] byteArr = file.getBytes();
                InputStream inputStream = new ByteArrayInputStream(byteArr);
                XWPFDocument doc = new XWPFDocument(inputStream);
                XWPFTable table = PoiWordUtil.getTable(doc, 0);

                Map<String, List<String>> result = new LinkedHashMap<>();
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    int i = 0;
                    String key = null;
                    List<String> list = new ArrayList<>(16);

                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        StringBuilder sb = new StringBuilder();
                        List<XWPFParagraph> paragraphs = cell.getParagraphs();
                        for (XWPFParagraph paragraph : paragraphs) {
                            sb.append(poiWordUtil.getParagraphText(paragraph));
                        }
                        if (i == 0) {
                            key = sb.toString();
                        } else {
                            String value = sb.toString();
                            list.add(Objects.deepEquals(value, "") ? null : value.replace(",", ""));
                        }
                        i++;
                    }
                    result.put(key, list);
                }

                log.info("质量检查 回复 上传word文档 拼装结果:{}", JSON.toJSONString(result));
                qualityImportService.importTestingReplyDocx(result);

            } else {
                List<List<String>> listValue = poiWordUtil.doDoc(file, 1);
                log.info("质量检查 回复 上传word文档 拼装结果:{}", JSON.toJSONString(listValue));
                qualityImportService.importTestingReplyDoc(listValue);
            }
        } catch (Exception e) {
            log.error("质量检查 回复 上传word文档 异常信息:{}", e.toString());
        }

        return AjaxResult.success("导入成功！");
    }

}



