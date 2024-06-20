/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Carrier;
import com.ruoyi.system.pojo.dto.CarrierDTO;
import com.ruoyi.system.pojo.vo.CarrierVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CarrierConvertImpl implements CarrierConvert {
    public CarrierConvertImpl() {
    }

    public CarrierDTO do2dto(Carrier source) {
        if (source == null) {
            return null;
        } else {
            CarrierDTO carrierDTO = new CarrierDTO();
            carrierDTO.setCreateBy(source.getCreateBy());
            carrierDTO.setCreateTime(source.getCreateTime());
            carrierDTO.setUpdateBy(source.getUpdateBy());
            carrierDTO.setUpdateTime(source.getUpdateTime());
            carrierDTO.setId(source.getId());
            carrierDTO.setCarrierNo(source.getCarrierNo());
            carrierDTO.setCarrierName(source.getCarrierName());
            carrierDTO.setAddress(source.getAddress());
            carrierDTO.setMobile(source.getMobile());
            carrierDTO.setTel(source.getTel());
            carrierDTO.setContact(source.getContact());
            carrierDTO.setLevel(source.getLevel());
            carrierDTO.setEmail(source.getEmail());
            carrierDTO.setRemark(source.getRemark());
            return carrierDTO;
        }
    }

    public Carrier dto2do(CarrierDTO source) {
        if (source == null) {
            return null;
        } else {
            Carrier carrier = new Carrier();
            carrier.setCreateBy(source.getCreateBy());
            carrier.setCreateTime(source.getCreateTime());
            carrier.setUpdateBy(source.getUpdateBy());
            carrier.setUpdateTime(source.getUpdateTime());
            carrier.setId(source.getId());
            carrier.setCarrierNo(source.getCarrierNo());
            carrier.setCarrierName(source.getCarrierName());
            carrier.setAddress(source.getAddress());
            carrier.setMobile(source.getMobile());
            carrier.setTel(source.getTel());
            carrier.setContact(source.getContact());
            carrier.setLevel(source.getLevel());
            carrier.setEmail(source.getEmail());
            carrier.setRemark(source.getRemark());
            return carrier;
        }
    }

    public List<CarrierVO> dos2vos(List<Carrier> list) {
        if (list == null) {
            return null;
        } else {
            List<CarrierVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Carrier carrier = (Carrier)var3.next();
                list1.add(this.carrierToCarrierVO(carrier));
            }

            return list1;
        }
    }

    protected CarrierVO carrierToCarrierVO(Carrier carrier) {
        if (carrier == null) {
            return null;
        } else {
            CarrierVO carrierVO = new CarrierVO();
            carrierVO.setCreateBy(carrier.getCreateBy());
            carrierVO.setCreateTime(carrier.getCreateTime());
            carrierVO.setUpdateBy(carrier.getUpdateBy());
            carrierVO.setUpdateTime(carrier.getUpdateTime());
            carrierVO.setId(carrier.getId());
            carrierVO.setCarrierNo(carrier.getCarrierNo());
            carrierVO.setCarrierName(carrier.getCarrierName());
            carrierVO.setAddress(carrier.getAddress());
            carrierVO.setMobile(carrier.getMobile());
            carrierVO.setTel(carrier.getTel());
            carrierVO.setContact(carrier.getContact());
            carrierVO.setLevel(carrier.getLevel());
            carrierVO.setEmail(carrier.getEmail());
            carrierVO.setRemark(carrier.getRemark());
            return carrierVO;
        }
    }
}
*/
