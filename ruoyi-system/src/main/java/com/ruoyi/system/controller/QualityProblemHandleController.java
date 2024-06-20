package com.ruoyi.system.controller;

import com.ruoyi.system.entity.QualityProblemHandle;
import com.ruoyi.system.service.QualityProblemHandleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (QualityProblemHandle)表控制层
 *
 * @author makejava
 * @since 2023-01-19 17:08:12
 */
@RestController
@RequestMapping("qualityProblemHandle")
public class QualityProblemHandleController {
    /**
     * 服务对象
     */
    @Resource
    private QualityProblemHandleService qualityProblemHandleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<QualityProblemHandle> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.qualityProblemHandleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param qualityProblemHandle 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<QualityProblemHandle> add(QualityProblemHandle qualityProblemHandle) {
        return ResponseEntity.ok(this.qualityProblemHandleService.insert(qualityProblemHandle));
    }

    /**
     * 编辑数据
     *
     * @param qualityProblemHandle 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<QualityProblemHandle> edit(QualityProblemHandle qualityProblemHandle) {
        return ResponseEntity.ok(this.qualityProblemHandleService.update(qualityProblemHandle));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.qualityProblemHandleService.deleteById(id));
    }

}

