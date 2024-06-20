package com.ruoyi.quartz.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.system.domain.Item;
import com.ruoyi.system.domain.SysUserNotice;
import com.ruoyi.system.mapper.ItemMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.SysUserNoticeMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务
 */
@RestController
@RequestMapping("/monitor/jobLogTask")
public class SysJobLogTaskController extends BaseController {
    @Resource
    private ItemMapper wmsItemMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserNoticeMapper sysUserNoticeMapper;

    /**
     * 查询定时任务列表
     * Item(表)
     */
    @PreAuthorize("@ss.hasPermi('monitor:job:list')")
    @GetMapping("/list")
    public void list(Integer dayNumber) {
        // 获取当前时间
        String dayTemp = DateUtil.today();
        Integer today = Integer.valueOf(dayTemp.substring(8, 10));
        int toAdd = today + dayNumber;
        String expiryDate = dayTemp.substring(0, 8) + toAdd;
        System.out.println("expiryDate: " + expiryDate);
        List<Item> list = wmsItemMapper.queryDateList(expiryDate);
        if (CollUtil.isNotEmpty(list)) {
            for (Item item : list) {
                // 判断是否是设备物资部
                List<SysUser> userList = sysUserMapper.queyDeptIdList();
                if (CollUtil.isNotEmpty(userList)) {
                    for (SysUser user : userList) {
                        SysUserNotice sysUserNotice = new SysUserNotice();
                        sysUserNotice.setNoticeTitle(user.getNickName());
                        sysUserNotice.setNoticeType("报警");
                        sysUserNotice.setContent(item.getItemName() + "到期报警");
                        sysUserNotice.setStatus("正常");
                        sysUserNotice.setUserId(user.getUserId().toString());
                        sysUserNotice.setModelName("物资模块");
                        sysUserNotice.setYn(YnEnum.Y.getCode());
                        sysUserNoticeMapper.insert(sysUserNotice);
                    }
                }
            }
        }
    }

}
