package com.ruoyi.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.SysWorkPeople;
import com.ruoyi.system.domain.vo.SysWorkPeopleVO;

public interface SysWorkPeopleMapper extends BaseMapper<SysWorkPeople> {

    Integer updateByCode(SysWorkPeopleVO sysWorkPeopleVO);


    Integer updateIntegralByPid(SysWorkPeople sysWorkPeople);

    void updWorkPeopleDeparture(SysWorkPeople sysWorkPeople);

}
