/*
package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Delivery;
import com.ruoyi.system.pojo.dto.DeliveryDTO;
import com.ruoyi.system.pojo.vo.DeliveryVO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DeliveryConvertImpl implements DeliveryConvert {
    public DeliveryConvertImpl() {
    }

    public DeliveryDTO do2dto(Delivery source) {
        if (source == null) {
            return null;
        } else {
            DeliveryDTO deliveryDTO = new DeliveryDTO();
            deliveryDTO.setCreateBy(source.getCreateBy());
            deliveryDTO.setCreateTime(source.getCreateTime());
            deliveryDTO.setUpdateBy(source.getUpdateBy());
            deliveryDTO.setUpdateTime(source.getUpdateTime());
            deliveryDTO.setId(source.getId());
            deliveryDTO.setShipmentOrderId(source.getShipmentOrderId());
            deliveryDTO.setCarrierId(source.getCarrierId());
            deliveryDTO.setDeliveryDate(source.getDeliveryDate());
            deliveryDTO.setTrackingNo(source.getTrackingNo());
            deliveryDTO.setRemark(source.getRemark());
            return deliveryDTO;
        }
    }

    public Delivery dto2do(DeliveryDTO source) {
        if (source == null) {
            return null;
        } else {
            Delivery delivery = new Delivery();
            delivery.setCreateBy(source.getCreateBy());
            delivery.setCreateTime(source.getCreateTime());
            delivery.setUpdateBy(source.getUpdateBy());
            delivery.setUpdateTime(source.getUpdateTime());
            delivery.setId(source.getId());
            delivery.setShipmentOrderId(source.getShipmentOrderId());
            delivery.setCarrierId(source.getCarrierId());
            delivery.setDeliveryDate(source.getDeliveryDate());
            delivery.setTrackingNo(source.getTrackingNo());
            delivery.setRemark(source.getRemark());
            return delivery;
        }
    }

    public List<DeliveryVO> dos2vos(List<Delivery> list) {
        if (list == null) {
            return null;
        } else {
            List<DeliveryVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Delivery delivery = (Delivery)var3.next();
                list1.add(this.deliveryToDeliveryVO(delivery));
            }

            return list1;
        }
    }

    protected DeliveryVO deliveryToDeliveryVO(Delivery delivery) {
        if (delivery == null) {
            return null;
        } else {
            DeliveryVO deliveryVO = new DeliveryVO();
            deliveryVO.setCreateBy(delivery.getCreateBy());
            deliveryVO.setCreateTime(delivery.getCreateTime());
            deliveryVO.setUpdateBy(delivery.getUpdateBy());
            deliveryVO.setUpdateTime(delivery.getUpdateTime());
            deliveryVO.setId(delivery.getId());
            deliveryVO.setShipmentOrderId(delivery.getShipmentOrderId());
            deliveryVO.setCarrierId(delivery.getCarrierId());
            deliveryVO.setDeliveryDate(delivery.getDeliveryDate());
            deliveryVO.setTrackingNo(delivery.getTrackingNo());
            deliveryVO.setRemark(delivery.getRemark());
            return deliveryVO;
        }
    }
}
*/
