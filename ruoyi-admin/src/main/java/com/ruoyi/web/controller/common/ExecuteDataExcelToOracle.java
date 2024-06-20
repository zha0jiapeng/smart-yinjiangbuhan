package com.ruoyi.web.controller.common;

import com.ruoyi.system.entity.QualityTask;
import com.ruoyi.system.pojo.dto.QualityTaskDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExecuteDataExcelToOracle {

    public static void main(String[] args) {

        try {
            List<QualityTaskDTO> datas = loadExcel("C:\\Users\\Administrator\\Desktop\\质量任务.xlsx");//需要替换
            System.out.println(datas);
            //batchInsert(datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取excel中的数据
     *
     * @param filepath
     * @return
     * @throws Exception
     */
    public static List<QualityTaskDTO> loadExcel(String filepath) throws Exception {
        StringBuilder failureMsg = new StringBuilder();
        File file = new File(filepath);
        Workbook wb = WorkbookFactory.create(new FileInputStream(file));
        Sheet sheet = wb.getSheetAt(0);
        List<QualityTaskDTO> list = new ArrayList<>();//将数据添加到数据一行一行的添加到集合中，作为插入数据的入参
        Row row = null;
        int num = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String taskName = row.getCell(0).getStringCellValue();
            String taskDescribe = row.getCell(1).getStringCellValue();
            String projName = row.getCell(2).getStringCellValue();
            String taskSource = row.getCell(3).getStringCellValue();
            String taskState = row.getCell(4).getStringCellValue();
            String nickName = row.getCell(5).getStringCellValue();
            String deptName = row.getCell(6).getStringCellValue();
            String userName = row.getCell(7).getStringCellValue();

            if (taskName != null && taskName != "" && taskDescribe != null && taskDescribe != "" && projName != null && projName != "" && taskSource != null && taskSource != "" &&
                    taskState != null && taskState != "" && nickName != null && nickName != "" && deptName != null && deptName != "" && userName != null && userName != "") {
                QualityTaskDTO model = new QualityTaskDTO();
                model.setTaskName(taskName);
                model.setTaskDescribe(taskDescribe);
                model.setProjName(projName);
                model.setTaskSource(taskSource);
                model.setTaskState(taskState);
                model.setNickName(nickName);
                model.setDeptName(deptName);
                model.setUserName(userName);
                list.add(model);
                num++;
            } else {
                failureMsg.append("第").append(i + 1).append("行的必填！").append("<br/>");
                System.out.println(failureMsg.append("第").append(i + 1).append("行的必填！").append("<br/>"));
                /*failureMsg.insert(0, "很抱歉，导入失败！共 " + num + " 条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());*/
            }
        }
        if (list.size() > 0) {
            for (QualityTaskDTO taskDTO : list) {
                QualityTask qualityTask = new QualityTask();
                qualityTask.setTaskName(taskDTO.getTaskName());
                qualityTask.setTaskDescribe(taskDTO.getTaskDescribe());
                qualityTask.setProjName(taskDTO.getProjName());
                qualityTask.setTaskSource(taskDTO.getTaskSource());
                if ("已处理".equals(taskDTO.getTaskState())) {
                    qualityTask.setTaskState(0);
                } else if ("待处理".equals(taskDTO.getTaskState())) {
                    qualityTask.setTaskState(1);
                }
                System.out.println(qualityTask);
                //ahseH0009DangerAMapper.add(dangerEntity);
            }
        }
        return list;
    }

    /**
     * 批量执行插入数据
     *
     * @param datas
     */
    public static void batchInsert(List<QualityTaskDTO> datas) {
        long startTime = System.currentTimeMillis();
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //如果是oracle就不需要替换，如果是mysql就需要替换
            conn = DriverManager.getConnection("jdbc:mysql://114.117.242.249:3306/baishiling", "root", "WdLzRujUs8oDQ5fI"); //需要替换
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO quality_task VALUES (?,?,?,?,?,?,?,?,?,?)"); //需要替换

            int num = 0;
            for (QualityTaskDTO v : datas) {
                num++;
                stmt.setString(1, v.getTaskName());
                stmt.setString(2, v.getTaskDescribe());
                stmt.setString(3, v.getProjName());
                stmt.setString(4, v.getTaskState());
                stmt.setString(5, v.getNickName());
                stmt.setString(6, v.getDeptName());
                stmt.setString(7, v.getUserName());
                stmt.addBatch();
                // 每5万，提交一次
                if (num > 50000) {
                    stmt.executeBatch();
                    conn.commit();
                    num = 0;
                }
            }
            stmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("方法执行时间：" + (endTime - startTime) + "ms");
        }
    }


}
