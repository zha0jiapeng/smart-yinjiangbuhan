package com.ruoyi.web.controller.basic.view.whmos;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateToUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.ScheduleTbmSegment;
import com.ruoyi.system.entity.TbmWorkProgressLog;
import com.ruoyi.system.service.CurrentConstructionNewService;
import com.ruoyi.system.service.TbmWorkProgressLogService;
import com.ruoyi.system.webservice.CrccWebService;
import com.ruoyi.system.webservice.dto.DataDTO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TBM
 */
@RestController
@RequestMapping("/crccWeb")
public class CrccWebController {
    @Resource
    private CrccWebService crccWebService;
    @Autowired
    private TbmWorkProgressLogService tbmWorkProgressLogService;

    @Autowired
    private CurrentConstructionNewService currentConstructionNewService;

    @Value("${bsl.crccweb.url}")
    private String crccWebUrl;

    @Resource
    private RedisCache redisCache;

    @GetMapping("/getData")
    public AjaxResult getData() {
        DataDTO dataDTO = crccWebService.crccData(crccWebUrl, "DZ1188", "02bbii");
        return AjaxResult.success(dataDTO);
    }

    /**
     * 每小时获取一次盾头里程和当前环片计数
     */
    @GetMapping("/getDtlc")
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void getDtlc(){
        DataDTO dataDTO = crccWebService.crccData(crccWebUrl, "DZ1188", "02bbii");
        //如果数据是正常的就保存数据
        //判断是否正常，盾头里程>399.8
        if(StringUtils.isEmpty(dataDTO.getDtlc())){
            return;
        }
        if(Double.valueOf(dataDTO.getDtlc())>399.8){
            //查询最新一条数据
            QueryWrapper<TbmWorkProgressLog> qw = new QueryWrapper<>();
            qw.orderByDesc("pull_time");
            qw.last("limit 1");
            TbmWorkProgressLog logServiceOne = tbmWorkProgressLogService.getOne(qw);
            TbmWorkProgressLog tbmWorkProgressLog = new TbmWorkProgressLog();
            tbmWorkProgressLog.setDtlc(new BigDecimal(dataDTO.getDtlc()));
            tbmWorkProgressLog.setHpjsq(Integer.valueOf(dataDTO.getHpjsq()));
            tbmWorkProgressLog.setPullTime(new Date());
            tbmWorkProgressLog.setTjgzzt(dataDTO.getTjgzzt());
            tbmWorkProgressLogService.save(tbmWorkProgressLog);
            //如果当前数据比上一次的数据大就调用环片信息的计算接口
            if(logServiceOne!=null){
                if(Integer.valueOf(dataDTO.getHpjsq())>logServiceOne.getHpjsq()){
                    //计算环片计数差值
                    int diffCount = Integer.valueOf(dataDTO.getHpjsq()) - logServiceOne.getHpjsq();
                    //计算掘进的米数差值
                    BigDecimal dtlcNow = new BigDecimal(dataDTO.getDtlc());
                    BigDecimal diffMeterCount = dtlcNow.subtract(logServiceOne.getDtlc());
                    ScheduleTbmSegment scheduleTbmSegment = new ScheduleTbmSegment();
                    scheduleTbmSegment.setScheduleRealityPrice(BigDecimal.valueOf(diffCount));
                    scheduleTbmSegment.setTbmRealityPrice(diffMeterCount);
                    scheduleTbmSegment.setDayBase(DateUtils.getDate());
                    currentConstructionNewService.calculate(scheduleTbmSegment);
                }
            }
        }

    }

//    @GetMapping("/wsGetTbm")
//    public AjaxResult wsGetTbm() throws IOException {
//        List<String> dz1188 = crccWebService.wsGetTbm(crccWebUrl, "DZ1188", "02bbii");
//        return AjaxResult.success(dz1188);
//    }
}
