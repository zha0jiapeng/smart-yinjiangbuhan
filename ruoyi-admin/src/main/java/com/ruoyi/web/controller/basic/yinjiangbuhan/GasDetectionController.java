package com.ruoyi.web.controller.basic.yinjiangbuhan;

import com.ruoyi.web.controller.basic.yinjiangbuhan.enums.IndexType;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.Modbus4jReadUtil;
import com.ruoyi.web.controller.basic.yinjiangbuhan.utils.ModbusTcpMaster;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gasDetection")
public class GasDetectionController {

    @RequestMapping("/list")
    public List<Map<String,Object>> getGasGasDetection(){
        ModbusMaster master = new ModbusTcpMaster().getSlave("192.168.103.178", 6066);
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i =0; i <18;i++) {
            Map<String,Object> map = new HashMap<>();
            Number number = Modbus4jReadUtil.readHoldingRegister(master, 1, i, DataType.TWO_BYTE_INT_UNSIGNED, "");
            Integer flagByType = IndexType.getFlagByType(i);
            String name = IndexType.getNameByType(i);
            Integer value = number.intValue();
            if(flagByType ==0){
                value = new BigDecimal(value).divide(new BigDecimal(10),0, RoundingMode.HALF_UP).intValue();
            }
            map.put(name,value);
        }
        return list;
    }

}
