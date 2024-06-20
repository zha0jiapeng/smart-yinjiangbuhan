package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.SysActing;
import com.ruoyi.system.service.SysActingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 代办表(SysActing)表控制层
 *
 * @author makejava
 * @since 2022-11-29 15:23:34
 */
@RestController
@RequestMapping("sysActing")
public class SysActingController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private SysActingService sysActingService;

    /**
     * 分页查询
     *
     * @param sysActing 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(SysActing sysActing) {
        startPage();
        List<SysActing> sysActings = this.sysActingService.queryByPage(sysActing);
        return getDataTable(sysActings);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.sysActingService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sysActing 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody SysActing sysActing) {
        return AjaxResult.success(this.sysActingService.insert(sysActing));
    }

    /**
     * 编辑数据
     *
     * @param sysActing 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysActing sysActing) {
        return AjaxResult.success(this.sysActingService.update(sysActing));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.sysActingService.deleteById(id));
    }

    @GetMapping
    public AjaxResult queryCount() {

        return AjaxResult.success();
    }

}

