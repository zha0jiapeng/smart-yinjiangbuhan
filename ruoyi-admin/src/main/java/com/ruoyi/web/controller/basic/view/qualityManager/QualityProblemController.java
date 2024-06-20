package com.ruoyi.web.controller.basic.view.qualityManager;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.entity.QualityProblem;
import com.ruoyi.system.service.QualityProblemService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (QualityProblem)表控制层
 *
 * @author makejava
 * @since 2022-12-26 16:03:29
 */
@RestController
@RequestMapping("qualityProblem")
public class QualityProblemController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private QualityProblemService qualityProblemService;

    /**
     * 分页查询
     *
     * @param qualityProblem 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list")
    public TableDataInfo queryByPage(QualityProblem qualityProblem) {
        startPage();
        List<QualityProblem> qualityProblems = this.qualityProblemService.queryByPage(qualityProblem);
        return getDataTable(qualityProblems);
    }

    /**
     * 分页查询代我处理
     *
     * @param qualityProblem 筛选条件
     * @return 查询结果
     */
    @GetMapping("/listActing")
    public TableDataInfo queryByPageAndActing(QualityProblem qualityProblem) {
        startPage();
        List<QualityProblem> qualityProblems = this.qualityProblemService.queryByPageAndActing(qualityProblem);
        return getDataTable(qualityProblems);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult queryById(@PathVariable("id") Integer id) {
        return AjaxResult.success(this.qualityProblemService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qualityProblem 实体
     * @return 新增结果
     */
    @PostMapping("/create")
    public AjaxResult add(@RequestBody QualityProblem qualityProblem) {
        Integer problemType = qualityProblem.getProblemType();
        if (problemType == null) {
            return AjaxResult.error(500, "请选择问题类型");
        }
        return AjaxResult.success(this.qualityProblemService.insert(qualityProblem));
    }

    /**
     * 编辑数据
     *
     * @param qualityProblem 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody QualityProblem qualityProblem) {
        return AjaxResult.success(this.qualityProblemService.update(qualityProblem));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @GetMapping("/delete")
    public AjaxResult deleteById(Integer id) {
        return AjaxResult.success(this.qualityProblemService.deleteById(id));
    }

    /**
     * 问题审核
     *
     * @param qualityProblem 实体
     * @return 编辑结果
     */
    @PostMapping("/updateOpinion")
    public AjaxResult updateOpinion(@RequestBody QualityProblem qualityProblem) {
        return AjaxResult.success(this.qualityProblemService.updateOpinion(qualityProblem));
    }

    /**
     * 整改
     *
     * @param qualityProblem 实体
     * @return 编辑结果
     */
    @PostMapping("/updateResult")
    public AjaxResult updateResult(@RequestBody QualityProblem qualityProblem) {
        return AjaxResult.success(this.qualityProblemService.updateResult(qualityProblem));
    }

    /**
     * 复核
     *
     * @return 编辑结果
     */
    @PostMapping("/updateProgress")
    public AjaxResult updateProgress(@RequestBody QualityProblem qualityProblem) {
        return AjaxResult.success(this.qualityProblemService.updateProgress(qualityProblem));
    }

    @GetMapping("/countAll")
    public AjaxResult countAll() {
        Map<String, Object> countMap = qualityProblemService.countAll();
        return AjaxResult.success(countMap);
    }

    /**
     * 质量验收_审批
     *
     * @param qualityProblem 参数
     * @return 结果
     */
    @PostMapping("/approve")
    public AjaxResult updateStatus(@RequestBody QualityProblem qualityProblem) {
        //审批拒绝回到待整改  0.待整改 1.待审批 2.通过 3.驳回
        if (qualityProblem.getCheckStatus() == 3) {
            qualityProblem.setCheckStatus(0);
        }
        qualityProblem.setAuditTime(new Date());
        qualityProblemService.update(qualityProblem);
        return AjaxResult.success();
    }

    /**
     * 质量验收_整改
     *
     * @param qualityProblem 参数
     * @return 结果
     */
    @PostMapping("/reform")
    public AjaxResult updateFour(@RequestBody QualityProblem qualityProblem) {
        //默认待审批状态
        qualityProblem.setCheckStatus(1);
        qualityProblemService.update(qualityProblem);
        return AjaxResult.success();
    }

}

