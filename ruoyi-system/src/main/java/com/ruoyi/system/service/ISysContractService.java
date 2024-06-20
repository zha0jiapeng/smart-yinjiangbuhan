package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.basic.SysContract;

public interface ISysContractService extends IService<SysContract> {

    SysContract insert(SysContract sysContract);

}
