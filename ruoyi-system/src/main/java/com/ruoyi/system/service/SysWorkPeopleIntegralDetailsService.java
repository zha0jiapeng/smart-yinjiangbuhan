package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.SysWorkPeopleIntegralDetails;

import java.util.List;

public interface SysWorkPeopleIntegralDetailsService extends IService<SysWorkPeopleIntegralDetails> {

    SysWorkPeopleIntegralDetails insert(SysWorkPeopleIntegralDetails sysWorkPeopleIntegralDetails);

    List<SysWorkPeopleIntegralDetails> queryByPid(Integer pid);

}
