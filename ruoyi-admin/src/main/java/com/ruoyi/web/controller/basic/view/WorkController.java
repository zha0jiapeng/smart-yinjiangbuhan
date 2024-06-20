package com.ruoyi.web.controller.basic.view;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.assessment.modules.exam.dto.ExamDTO;
import com.ruoyi.assessment.modules.exam.dto.response.ExamOnlineRespDTO;
import com.ruoyi.assessment.modules.exam.service.ExamService;
import com.ruoyi.assessment.modules.paper.service.PaperService;
import com.ruoyi.common.enums.VerifyEnum;
import com.ruoyi.common.utils.BaseVerifyUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.basic.SysExaminationPaper;
import com.ruoyi.system.domain.basic.Worker;
import com.ruoyi.system.service.SysExaminationPaperService;
import com.ruoyi.system.service.WorkerService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("work")
public class WorkController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private ExamService examService;
    @Autowired
    private WorkerService workerCheckService;
    @Autowired
    private SysExaminationPaperService sysExaminationPaperService;

    @PostMapping("/check")
    public Result<?> check(@RequestBody Worker workerCheck) {

        BaseVerifyUtil.verify(StringUtils.isEmpty(workerCheck.getWorkName()))
                .throwMessage(VerifyEnum.CHECK_WORK_NAME.getCode(), VerifyEnum.CHECK_WORK_NAME.getDesc());

        if (StringUtils.isEmpty(workerCheck.getWorkPhone())) {
            return Result.error("工人手机号不可为空");
        }
        if (StringUtils.isEmpty(workerCheck.getCardCode())) {
            return Result.error("工人身份证号不可为空");
        }
        return workerCheckService.checkWorkerLoginExam(workerCheck);
    }

    @PostMapping("app/login")
    public Result<?> appLogin(@RequestBody Worker worker) {
        if (StringUtils.isEmpty(worker.getWorkName())) {
            return Result.error("工人名称不可为空");
        }
        if (StringUtils.isEmpty(worker.getWorkPhone())) {
            return Result.error("工人手机号不可为空");
        }
        if (StringUtils.isEmpty(worker.getCardCode())) {
            return Result.error("工人身份证号不可为空");
        }
        Boolean save = workerCheckService.workerAppLogin(worker);
        if (!save) {
            return Result.error("保存工人信息失败");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("worker", worker);
        return Result.ok(response);
    }

    @PostMapping("app/exam")
    public Result<?> appExam(@RequestBody Worker worker) {
        if (null == worker.getId()) {
            return Result.error("试卷id不能为空");
        }

        QueryWrapper<SysExaminationPaper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", worker.getId());
        SysExaminationPaper sysExaminationPaper = sysExaminationPaperService.getOne(queryWrapper);
        if (null == sysExaminationPaper || StringUtils.isEmpty(sysExaminationPaper.getVolumeIds())) {
            return Result.error("查询考卷信息失败");
        }

        List<String> ids = new ArrayList<>();
        String volumeIds = sysExaminationPaper.getVolumeIds();
        if (volumeIds.contains(",")) {
            String[] split = volumeIds.split(",");
            ids.addAll(Arrays.asList(split));
        } else {
            ids.add(volumeIds);
        }

        ExamDTO examDTO = new ExamDTO();
        examDTO.setIds(ids);
        List<ExamOnlineRespDTO> examOnlineRespDTOS = examService.onlineOne(examDTO);
        if (CollectionUtils.isEmpty(examOnlineRespDTOS)) {
            return Result.error("查询试卷信息失败");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("exam", examOnlineRespDTOS);

        return Result.ok(response);
    }

}
