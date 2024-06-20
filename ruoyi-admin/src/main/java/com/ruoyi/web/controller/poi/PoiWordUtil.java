package com.ruoyi.web.controller.poi;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.FastDFSClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhou
 */
@Slf4j
@Component
public class PoiWordUtil {

    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;

    /**
     * @param doc        docx解析对象
     * @param tableIndex 第几个表格
     */
    public static XWPFTable getTable(XWPFDocument doc, int tableIndex) {
        return doc.getTables().get(tableIndex);
    }

    /**
     * 为表格插入行数，此处不处理表头，所以从第二行开始
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     * @param index     在几行后开始插入数据 1为第一行
     */
    public static void insertTable(XWPFTable table, List<String[]> tableList, int index) {
        //创建与数据一致的行数
        for (int i = 0; i < tableList.size(); i++) {
            table.createRow();
        }
        int length = table.getRows().size() - index;
        for (int i = 0; i < length; i++) {
            XWPFTableRow newRow = table.getRow(i + index);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                cell.setText(tableList.get(i)[j]);
            }
        }
    }


    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public static void replaceParams(XWPFDocument doc, Map<String, String> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph paragraph;
        while (iterator.hasNext()) {
            paragraph = iterator.next();
            replaceParam(paragraph, params);
        }
    }


    /**
     * 替换所有表格里面的变量
     *
     * @param doc 要替换的文档
     */
    public static void replaceAllTableParams(XWPFDocument doc, Map<String, String> testMap) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
            if (matcher(table.getText()).find()) {
                rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        paras = cell.getParagraphs();
                        for (XWPFParagraph para : paras) {
                            replaceParam(para, testMap);
                        }
                    }
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     */
    public static void replaceTableParams(XWPFTable table, Map<String, String> testMap) {
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
        if (matcher(table.getText()).find()) {
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceParam(para, testMap);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    public static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }

    /**
     * 替换段落里面的变量
     *
     * @param paragraph 要替换的段落
     * @param params    参数
     */
    public static void replaceParam(XWPFParagraph paragraph, Map<String, String> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        StringBuilder runText = new StringBuilder();
        if (matcher(paragraph.getParagraphText()).find()) {
            runs = paragraph.getRuns();
            int j = runs.size();
            for (int i = 0; i < j; i++) {
                runText.append(runs.get(0).toString());
                //保留最后一个段落，在这段落中替换值，保留段落样式
                if (!((j - 1) == i)) {
                    paragraph.removeRun(0);
                }
            }
            matcher = matcher(runText.toString());
            if (matcher.find()) {
                while ((matcher = matcher(runText.toString())).find()) {
                    runText = new StringBuilder(matcher.replaceFirst(String.valueOf(params.get(matcher.group(1)))));
                }
                runs.get(0).setText(runText.toString(), 0);
            }
        }

    }

    /**
     * word单元格列合并 都以0开始计算
     *
     * @param table     表格
     * @param row       合并列所在行
     * @param startCell 开始列
     * @param endCell   结束列
     * @date 2020年4月8日 下午4:43:54
     */
    public static void mergeCellsHorizontal(XWPFTable table, int row, int startCell, int endCell) {
        for (int i = startCell; i <= endCell; i++) {
            XWPFTableCell cell = table.getRow(row).getCell(i);
            if (i == startCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * word单元格行合并
     *
     * @param table    表格
     * @param col      合并行所在列
     * @param startRow 开始行
     * @param endRow   结束行
     * @date 2020年4月8日 下午4:46:18
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int startRow, int endRow) {
        for (int i = startRow; i <= endRow; i++) {
            XWPFTableCell cell = table.getRow(i).getCell(col);
            if (i == startRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }


    /**
     * 替换图片
     * key 替换字段名称，与word中相同
     * value 图片地址
     */
    public static void doParagraphs(XWPFDocument doc, Map<String, String> imgMap) {
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        String imgkey = text.replaceAll("\\{\\{@", "").replaceAll("}}", "");
                        String imgPath = imgMap.get(imgkey);
                        if (imgPath != null) {
                            if (imgPath.startsWith("http")) {
                                imgPath = saveToFile(imgPath);
                            }
                            saveLocalImg(run, imgPath);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取本地保存图片文件流
     *
     * @param imgPath
     * @return FileInputStream
     * @throws Exception
     */
    public static void saveLocalImg(XWPFRun run, String imgPath) {
        try (FileInputStream pictureData = new FileInputStream(imgPath)) {
            run.setText("", 0);
            run.addPicture(pictureData,
                    Document.PICTURE_TYPE_PNG, "img.png", Units.toEMU(200), Units.toEMU(200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取网络图片流
     *
     * @return
     */
    public static String saveToFile(String destUrl) {
        HttpURLConnection httpUrl = null;
        byte[] buf = new byte[1024];
        int size = 0;
        StringBuilder append = new StringBuilder()
                .append("D:/fjFile/")
                .append(IdUtil.simpleUUID())
                .append(".png");
        String fileUrl = append.toString();
        try {
            URL url = new URL(destUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setConnectTimeout(5 * 1000);
            httpUrl.setReadTimeout(5 * 1000);
            httpUrl.connect();
            try (BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
                 FileOutputStream fos = new FileOutputStream(fileUrl)) {
                while ((size = bis.read(buf)) != -1) {
                    fos.write(buf, 0, size);
                }
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpUrl != null) {
                    httpUrl.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileUrl;
    }

    public String getParagraphText(XWPFParagraph paragraph) {
        StringBuilder runText = new StringBuilder();
        // 获取段落中所有内容
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs.isEmpty()) {
            return runText.toString();
        }

        for (XWPFRun run : runs) {
            List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
            if (CollectionUtils.isEmpty(embeddedPictures)) {
                runText.append(run.text());
            } else {
                for (XWPFPicture picture : run.getEmbeddedPictures()) {
                    //获取图片的字节流
                    byte[] pictureData = picture.getPictureData().getData();
                    MultipartFile multipartFile = FileConvert.encodeToMultipartFile(pictureData);

                    try {
                        String url = fastDFSClientUtil.uploadFile(multipartFile);
                        runText.append(url);
                    } catch (Exception e) {
                        log.error("PoiWordUtil getParagraphText 执行异常:", e);
                    }

                }
            }
        }
        return runText.toString();
    }

    public List<List<String>> doDoc(MultipartFile file, int index) throws Exception {

        try {
            InputStream is = file.getInputStream();
            HWPFDocument doc = new HWPFDocument(is);
            PicturesTable picturesTable = doc.getPicturesTable();
            List<Picture> pictureList = picturesTable.getAllPictures();

            Map<Integer, Picture> lookup = new HashMap<Integer, Picture>();
            for (Picture p : pictureList) {
                lookup.put(p.getStartOffset(), p);
            }

            Range r = doc.getRange();
            TableIterator itpre = new TableIterator(r);
            int total = 0;
            while (itpre.hasNext()) {
                itpre.next();
                total += 1;
            }
            TableIterator it = new TableIterator(r);

            int set = index;
            int num = set;
            for (int i = 0; i < set - 1; i++) {
                it.hasNext();
                it.next();
            }

            List<List<String>> tableList = new ArrayList<>();
            while (it.hasNext()) {
                Table tb = (Table) it.next();
                System.out.println("这是第" + num + "个表的数据");
                for (int i = 0; i < tb.numRows(); i++) {
                    List<String> rowList = new ArrayList<>();
                    TableRow tr = tb.getRow(i);
                    for (int j = 0; j < tr.numCells(); j++) {
                        TableCell td = tr.getCell(j);
                        for (int k = 0; k < td.numParagraphs(); k++) {
                            Paragraph para = td.getParagraph(k);
                            String s = para.text();
                            if (null != s && !s.isEmpty()) {
                                s = s.substring(0, s.length() - 1);
                            }

                            String val = Objects.deepEquals(s, "") ? null : s.replace(",", "");
                            if (null == val) {
                                continue;
                            }
                            rowList.add(val);

                            try {
                                for (int n = 0; n < para.numCharacterRuns(); n++) {
                                    if (picturesTable.hasPicture(para.getCharacterRun(n))) {
                                        //Picture picture = lookup.get(n);

                                        log.info("Picture found at offset {}", para.getCharacterRun(n).getPicOffset());
                                        Picture picture = lookup.get(para.getCharacterRun(n).getPicOffset());

                                        byte[] content = picture.getContent();
                                        if (null != content) {
                                            MultipartFile multipartFile = FileConvert.encodeToMultipartFile(content);
                                            String url = fastDFSClientUtil.uploadFile(multipartFile);
                                            rowList.add(url);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                log.error("PoiWordUtil doDoc 执行异常:", e);
                            }

                            System.out.print(s + "[" + i + "," + j + "]" + "\t");
                        }
                    }

                    tableList.add(rowList);
                }
                // 过滤多余的表格
                while (num < total) {
                    it.hasNext();
                    it.next();
                    num += 1;
                }
            }

            return tableList;
        } catch (IOException e) {
            throw new Exception("文件读取失败 doc文件", e);
        }

    }


}