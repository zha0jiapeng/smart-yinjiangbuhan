package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.basic.Worker;
import com.ruoyi.system.entity.SysPersonnel;
import com.ruoyi.system.mapper.SysPersonnelMapper;
import com.ruoyi.system.mapper.WorkerMapper;
import com.ruoyi.system.service.WorkerService;
import com.ruoyi.system.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerMapper, Worker> implements WorkerService {
    @Resource
    private SysPersonnelMapper sysPersonnelMapper;

    @Override
    public Result<?> checkWorkerLoginExam(Worker workerCheck) {

        try {
            SysPersonnel sysPersonnel = new SysPersonnel();
            //工人名称
            sysPersonnel.setUserName(workerCheck.getWorkName());
            //工人手机号
            sysPersonnel.setPhone(workerCheck.getWorkPhone());
            //工人身份证号
            sysPersonnel.setUniqueNumber(workerCheck.getCardCode());
            List<SysPersonnel> sysPersonnels = sysPersonnelMapper.queryAll(sysPersonnel);
            if (CollectionUtils.isEmpty(sysPersonnels)) {
                return Result.error("系统中未查询到工人信息。");
            }
        } catch (Exception e) {
            log.error("验证工人信息是否属于当前系统 参数:{} 异常:{}", JSON.toJSONString(workerCheck), e.toString());
            return Result.error("服务异常,稍后重试。");
        }

        return Result.ok(workerCheck);
    }

    @Override
    public Boolean workerAppLogin(Worker worker) {
        //保存工人登陆信息
        return this.save(worker);
    }
}
