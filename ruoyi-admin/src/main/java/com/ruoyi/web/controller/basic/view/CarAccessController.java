package com.ruoyi.web.controller.basic.view;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.enums.YnEnum;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.basic.CarAccess;
import com.ruoyi.system.service.CarAccessService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 车辆进出场地记录表(CarAccessLog)表控制层
 *
 * @author makejava
 * @since 2022-12-15 20:04:02
 */
@Slf4j
@RestController
@RequestMapping("car/access")
public class CarAccessController {
    /**
     * 服务对象
     */
    @Resource
    private CarAccessService carAccessLogService;

    @GetMapping
    public Result<?> queryPageList(CarAccess carAccess,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<CarAccess> queryWrapper = new QueryWrapper<>(carAccess);
        queryWrapper.orderByDesc("created_date");
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        Page<CarAccess> page = new Page<>(pageNo, pageSize);
        IPage<CarAccess> pageList = carAccessLogService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    @PostMapping
    public ResponseEntity<CarAccess> add(@RequestBody CarAccess carAccessLog) {
        carAccessLog.setCreatedBy(SecurityUtils.getLoginUser().getUsername());
        carAccessLog.setCreatedDate(new Date());
        carAccessLog.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        carAccessLog.setModifyDate(new Date());
        carAccessLog.setYn(YnEnum.Y.getCode());
        return ResponseEntity.ok(this.carAccessLogService.insert(carAccessLog));
    }

    @PutMapping
    public Result<?> edit(@RequestBody CarAccess carAccess) {
        log.info("车辆信息 更新:{}", JSON.toJSON(carAccess));
        carAccess.setModifyBy(SecurityUtils.getLoginUser().getUsername());
        carAccess.setModifyDate(new Date());
        carAccessLogService.updateById(carAccess);
        return Result.OK("编辑成功!");
    }

    @DeleteMapping
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        carAccessLogService.removeById(id);
        return Result.OK("删除成功!");
    }

    @Log(title = "作业场地防护设施", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<CarAccess> util = new ExcelUtil<>(CarAccess.class);
        List<CarAccess> carAccesss = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = carAccessLogService.importExcel(carAccesss, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @Log(title = "车辆进出场地记录表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response) {
        QueryWrapper<CarAccess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yn", YnEnum.Y.getCode());
        List<CarAccess> list = carAccessLogService.list(queryWrapper);
        ExcelUtil<CarAccess> util = new ExcelUtil<>(CarAccess.class);
        util.exportExcel(response, list, "车辆进出场地记录表");
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<CarAccess> util = new ExcelUtil<>(CarAccess.class);
        util.importTemplateExcel(response, "车辆进出场地记录表");
    }

    public String getUsername() {
        return SecurityUtils.getLoginUser().getUsername();
    }

}

