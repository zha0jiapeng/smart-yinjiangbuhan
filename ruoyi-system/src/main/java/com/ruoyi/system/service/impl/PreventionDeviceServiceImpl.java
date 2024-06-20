package com.ruoyi.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.vo.PreventionDeviceVO;
import com.ruoyi.system.entity.PreventionDevice;
import com.ruoyi.system.mapper.PreventionDeviceMapper;
import com.ruoyi.system.service.PreventionDeviceService;
import com.ruoyi.system.service.PreventionSecurityRiskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 双重预防-装置管理(PreventionDevice)表服务实现类
 *
 * @author makejava
 * @since 2022-11-17 11:18:35
 */
@Service("preventionDeviceService")
public class PreventionDeviceServiceImpl implements PreventionDeviceService {

    @Resource
    private PreventionDeviceMapper preventionDeviceDao;

    @Resource
    private PreventionSecurityRiskService preventionSecurityRiskService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PreventionDevice queryById(Integer id) {
        return this.preventionDeviceDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param preventionDevice 筛选条件
     * @return 查询结果
     */
    @Override
    public List<PreventionDeviceVO> queryByPage(PreventionDevice preventionDevice) {

        List<PreventionDevice> preventionDevices = preventionDeviceDao.queryAllByLimit(preventionDevice);

        List<PreventionDeviceVO> preventionDeviceVOList = new ArrayList<>();

        preventionDevices.forEach(i -> {
            PreventionDeviceVO preventionDeviceVO = new PreventionDeviceVO();
            BeanUtils.copyBeanProp(preventionDeviceVO,i);
            // 获取分析单元数
            Integer unit = preventionSecurityRiskService.countUnit(i.getId());
            preventionDeviceVO.setUnitCount(unit);
            // 获取风险事件数
            Integer event = preventionSecurityRiskService.countEvent(i.getId());
            preventionDeviceVO.setEventCount(event);
            // 获取管控措施数
            Integer control = preventionSecurityRiskService.countControl(i.getId());
            preventionDeviceVO.setControlCount(control);
            // 获取排查任务数量
            Integer task = preventionSecurityRiskService.countTask(i.getId());
            preventionDeviceVO.setTaskCount(task);
            preventionDeviceVOList.add(preventionDeviceVO);
        });
        return preventionDeviceVOList;
    }

    /**
     * 新增数据
     *
     * @param preventionDevice 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionDevice insert(PreventionDevice preventionDevice) {
        this.preventionDeviceDao.insert(preventionDevice);
        return preventionDevice;
    }

    /**
     * 修改数据
     *
     * @param preventionDevice 实例对象
     * @return 实例对象
     */
    @Override
    public PreventionDevice update(PreventionDevice preventionDevice) {
        this.preventionDeviceDao.update(preventionDevice);
        return this.queryById(preventionDevice.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.preventionDeviceDao.deleteById(id) > 0;
    }

    /**
     * 统计包保履职责任需要的数据
     * @param dangerName
     * @return
     */
    @Override
    public String getAllCountByDangerName(String dangerName,String dutyInfoName,String dutyType) {
        StringBuffer str = new StringBuffer();
        if (dutyType.equals("技术负责人")) {
            String year = StrUtil.sub(dutyInfoName, 0, 4);
            String quarter = StrUtil.sub(dutyInfoName, 6, 7);
            String startTime = getStartTime(quarter, year);
            String endTime = getEndTime(quarter, year);
            // 获取排查次数
            Integer checkCount = preventionDeviceDao.getCheckCount(dangerName,startTime,endTime);
            // 获取隐患统计
            List<Map<String,Object>> list = preventionDeviceDao.hiddenCount(dangerName,startTime,endTime);
            // 隐患总数量 hiddenTotal
            // 治理完成隐患 hiddenComplete
            // 未整改隐患 hiddenNotCorrected
            // 超期隐患 hiddenTimeout
            Map<String,Integer> map = new HashMap<>();
            list.forEach(i -> {
                String hiddenProgress = i.get("hidden_progress").toString();
                if (hiddenProgress.equals("待整改")) {
                    map.put("hiddenNotCorrected",Integer.valueOf(i.get("progress_count").toString()));
                } else if (hiddenProgress.equals("完成")) {
                    map.put("hiddenComplete",Integer.valueOf(i.get("progress_count").toString()));
                } else if (hiddenProgress.equals("超时")) {
                    map.put("hiddenTimeout",Integer.valueOf(i.get("progress_count").toString()));
                } else {
                    if (map.get("hiddenTotal") == null) {
                        map.put("hiddenTotal",Integer.valueOf(i.get("progress_count").toString()));
                    } else {
                        map.put("hiddenTotal",Integer.valueOf(i.get("progress_count").toString()) + map.get("hiddenTotal"));
                    }
                }
            });

            str.append("一、排查次数：");
            str.append(checkCount);
            str.append("\n");

            str.append("二、发现隐患：");
            Integer hiddenTotal = map.get("hiddenTotal") == null ? 0 : map.get("hiddenTotal");
            str.append(hiddenTotal);
            str.append("\n");

            str.append("三、治理完成隐患：");
            Integer hiddenComplete = map.get("hiddenComplete") == null ? 0 : map.get("hiddenComplete");
            str.append(hiddenComplete);
            str.append("\n");

            str.append("四、未整改隐患：");
            Integer hiddenNotCorrected = map.get("hiddenNotCorrected") == null ? 0 : map.get("hiddenNotCorrected");
            str.append(hiddenNotCorrected);
            str.append("\n");

            str.append("五、超期隐患：");
            Integer hiddenTimeout = map.get("hiddenTimeout") == null ? 0 : map.get("hiddenTimeout");
            str.append(hiddenTimeout);
            return str.toString();
        } else {
            String year = StrUtil.sub(dutyInfoName, 0, 4);
            String week = StrUtil.sub(dutyInfoName, 6, 7);
            String weeks = StrUtil.sub(dutyInfoName, 7, 8);
            if (!weeks.equals("周")) {
                week = week + weeks;
            }
            String[] strings = weekToDayFormate(Integer.valueOf(year), Integer.valueOf(week));
            String startTime = strings[0];
            String endTime = strings[1];
            // 获取排查次数
            Integer checkCount = preventionDeviceDao.getCheckCount(dangerName,startTime,endTime);
            // 获取隐患统计
            List<Map<String,Object>> list = preventionDeviceDao.hiddenCount(dangerName,startTime,endTime);
            // 隐患总数量 hiddenTotal
            // 治理完成隐患 hiddenComplete
            // 未整改隐患 hiddenNotCorrected
            // 超期隐患 hiddenTimeout
            Map<String,Integer> map = new HashMap<>();
            list.forEach(i -> {
                String hiddenProgress = i.get("hidden_progress").toString();
                if (hiddenProgress.equals("待整改")) {
                    map.put("hiddenNotCorrected",Integer.valueOf(i.get("progress_count").toString()));
                } else if (hiddenProgress.equals("完成")) {
                    map.put("hiddenComplete",Integer.valueOf(i.get("progress_count").toString()));
                } else if (hiddenProgress.equals("超时")) {
                    map.put("hiddenTimeout",Integer.valueOf(i.get("progress_count").toString()));
                } else {
                    if (map.get("hiddenTotal") == null) {
                        map.put("hiddenTotal",Integer.valueOf(i.get("progress_count").toString()));
                    } else {
                        map.put("hiddenTotal",Integer.valueOf(i.get("progress_count").toString()) + map.get("hiddenTotal"));
                    }
                }
            });

            str.append("一、排查次数：");
            str.append(checkCount);
            str.append("\n");

            str.append("二、发现隐患：");
            Integer hiddenTotal = map.get("hiddenTotal") == null ? 0 : map.get("hiddenTotal");
            str.append(hiddenTotal);
            str.append("\n");

            str.append(";");

            str.append("一、治理完成隐患：");
            Integer hiddenComplete = map.get("hiddenComplete") == null ? 0 : map.get("hiddenComplete");
            str.append(hiddenComplete);
            str.append("\n");

            str.append("二、未整改隐患：");
            Integer hiddenNotCorrected = map.get("hiddenNotCorrected") == null ? 0 : map.get("hiddenNotCorrected");
            str.append(hiddenNotCorrected);
            str.append("\n");

            str.append("三、超期隐患：");
            Integer hiddenTimeout = map.get("hiddenTimeout") == null ? 0 : map.get("hiddenTimeout");
            str.append(hiddenTimeout);

            return str.toString();
        }
    }

    @Override
    public List<PreventionDevice> listByLevel() {
        return preventionDeviceDao.listByLevel();
    }

    // 根据year年的第week周，查询本周的起止时间
    public static String[] weekToDayFormate(int year, int week){
        Calendar calendar = Calendar.getInstance();
        // ①.设置该年份的开始日期：第一个月的第一天
        calendar.set(year,0,1);
        // ②.计算出第一周还剩几天：+1是因为1号是1天
        int dayOfWeek = 7 - calendar.get(Calendar.DAY_OF_WEEK) + 1;
        // ③.周数减去第一周再减去要得到的周
        week = week - 2;
        // ④.计算起止日期
        calendar.add(Calendar.DAY_OF_YEAR,week * 7 + dayOfWeek);
//        System.out.println("开始日期：" + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
//        System.out.println("结束日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        return new String[]{start,end};
    }

    public static String getStartTime(String quarter,String year) {
        if (quarter.equals("1")) {
            return year + "-01-01 00:00:00";
        } else if (quarter.equals("2")) {
            return year + "-04-01 00:00:00";
        } else if (quarter.equals("3")) {
            return year + "-07-01 00:00:00";
        } else if (quarter.equals("4")) {
            return year + "-10-01 00:00:00";
        }
        return "";
    }

    public static String getEndTime(String quarter,String year) {
        if (quarter.equals("1")) {
            return year + "-03-31 00:00:00";
        } else if (quarter.equals("2")) {
            return year + "-06-30 00:00:00";
        } else if (quarter.equals("3")) {
            return year + "-09-30 00:00:00";
        } else if (quarter.equals("4")) {
            return year + "-12-31 00:00:00";
        }
        return "";
    }
}
