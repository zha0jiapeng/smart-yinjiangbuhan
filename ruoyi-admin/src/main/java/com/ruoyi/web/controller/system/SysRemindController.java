package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.entity.SysRemind;
import com.ruoyi.system.service.SysRemindService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 提醒表(SysRemind)表控制层
 *
 * @author makejava
 * @since 2022-12-17 15:16:40
 */
@RestController
@RequestMapping("sysRemind")
public class SysRemindController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private SysRemindService sysRemindService;

    /**
     * 分页查询
     *
     * @param sysRemind 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(SysRemind sysRemind) {
        startPage();
        sysRemind.setRemindUserId(SecurityUtils.getUserId().intValue());
        sysRemind.setWhetherDelete(1);
        List<SysRemind> sysReminds = this.sysRemindService.queryByPage(sysRemind);
        return getDataTable(sysReminds);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.sysRemindService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysRemind 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody SysRemind sysRemind) {
        return AjaxResult.success(this.sysRemindService.insert(sysRemind));
    }

    /**
     * 编辑数据
     *
     * @param sysRemind 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysRemind sysRemind) {
        return AjaxResult.success(this.sysRemindService.update(sysRemind));
    }
}

