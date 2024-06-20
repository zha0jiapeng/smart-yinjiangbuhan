package com.ruoyi.web.controller.basic.view.prevention;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.PreventionHiddenRegister;
import com.ruoyi.system.service.PreventionHiddenRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 隐患录入记录表(PreventionHiddenRegister)表控制层
 *
 * @author makejava
 * @since 2022-11-22 17:20:34
 */
@RestController
@RequestMapping("preventionHiddenRegister")
public class PreventionHiddenRegisterController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private PreventionHiddenRegisterService preventionHiddenRegisterService;

    /**
     * 分页查询
     *
     * @param preventionHiddenRegister 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(PreventionHiddenRegister preventionHiddenRegister) {
        startPage();
        List<PreventionHiddenRegister> preventionHiddenRegisters = preventionHiddenRegisterService.queryByPage(preventionHiddenRegister);
        return getDataTable(preventionHiddenRegisters);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<PreventionHiddenRegister> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.preventionHiddenRegisterService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param preventionHiddenRegister 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody PreventionHiddenRegister preventionHiddenRegister) {
        preventionHiddenRegister.setHiddenProgress("待审核");
        return AjaxResult.success(this.preventionHiddenRegisterService.insert(preventionHiddenRegister));
    }

    /**
     * 编辑数据
     *
     * @param preventionHiddenRegister 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody PreventionHiddenRegister preventionHiddenRegister) {
        return AjaxResult.success(this.preventionHiddenRegisterService.update(preventionHiddenRegister));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.preventionHiddenRegisterService.deleteById(id));
    }

    /**
     *  统计分析
     * @return
     */
    @GetMapping("/countHidden")
    public AjaxResult countHidden(Integer deptId) {
        Map<String,List<Map<String, Object>>> map = preventionHiddenRegisterService.countHidden(deptId);
        return AjaxResult.success(map);
    }

    /**
     * 隐患审核
     * @return
     */
    @PostMapping("/updateHiddenState")
    public AjaxResult updateHiddenState(@RequestBody PreventionHiddenRegister preventionHiddenRegister) {
        preventionHiddenRegisterService.updateHiddenState(preventionHiddenRegister);
        return AjaxResult.success();
    }

    /**
     * 流转人
     *
     * @param preventionHiddenRegister 实体
     * @return 编辑结果
     */
    @PostMapping("/updateRectificationUserName")
    public AjaxResult updateRectificationUserName(@RequestBody PreventionHiddenRegister preventionHiddenRegister) {
        this.preventionHiddenRegisterService.updateRectificationUserName(preventionHiddenRegister);
        return AjaxResult.success();
    }

    /**
     * 隐患整改
     *
     * @param preventionHiddenRegister 实体
     * @return 编辑结果
     */
    @PostMapping("/updateRectification")
    public AjaxResult updateRectification(@RequestBody PreventionHiddenRegister preventionHiddenRegister) {
        this.preventionHiddenRegisterService.updateRectification(preventionHiddenRegister);
        return AjaxResult.success();
    }

    /**
     * 复核隐患
     *
     * @param preventionHiddenRegister 实体
     * @return 编辑结果
     */
    @PostMapping("/updateReview")
    public AjaxResult updateReview(@RequestBody PreventionHiddenRegister preventionHiddenRegister) {
        preventionHiddenRegisterService.updateReview(preventionHiddenRegister);
        return AjaxResult.success();
    }
}

