package com.ruoyi.web.controller.basic.view;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.enums.VerifyEnum;
import com.ruoyi.common.utils.BaseVerifyUtil;
import com.ruoyi.system.domain.basic.SysStaffAttendance;
import com.ruoyi.system.service.SysStaffAttendanceService;
import com.ruoyi.system.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(description = "人员考勤")
@RestController
@RequestMapping("/sys/staff/attendance")
public class SysStaffAttendanceController {

    @Autowired
    private SysStaffAttendanceService sysStaffAttendanceService;

    @GetMapping("/list")
    public Result<?> queryPageList(SysStaffAttendance sysStaffAttendance,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        //基础参数校验
        BaseVerifyUtil.verify(null == sysStaffAttendance.getStaffId())
                .throwMessage(VerifyEnum.CHECK_STAFF_ID.getCode(), VerifyEnum.CHECK_STAFF_ID.getDesc());

        QueryWrapper<SysStaffAttendance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_id", sysStaffAttendance.getStaffId());

        Page<SysStaffAttendance> page = new Page<SysStaffAttendance>(pageNo, pageSize);
        IPage<SysStaffAttendance> pageList = sysStaffAttendanceService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

}
