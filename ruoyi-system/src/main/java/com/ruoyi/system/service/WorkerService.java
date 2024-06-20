package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.CarAccess;
import com.ruoyi.system.domain.basic.Worker;
import com.ruoyi.system.utils.Result;

public interface WorkerService  extends IService<Worker> {

    /**
     * 校验工人登录信息
     *
     * @param workerCheck 参数
     * @return 结果
     */
    Result<?> checkWorkerLoginExam(Worker workerCheck);

    /**
     * 工人app登陆考试（保存工人信息,查找对应试卷）
     *
     * @param worker 登陆信息
     * @return 结果
     */
    Boolean workerAppLogin(Worker worker);
}
