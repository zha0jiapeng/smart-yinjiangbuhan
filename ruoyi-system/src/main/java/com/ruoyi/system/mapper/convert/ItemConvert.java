package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Item;
import com.ruoyi.system.pojo.dto.ItemDTO;
import com.ruoyi.system.pojo.vo.ItemVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 物料  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface ItemConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    ItemDTO do2dto(Item source);

    /**
     * @param source DTO
     * @return DO
     */
    Item dto2do(ItemDTO source);

    /**
     * @param source DO
     * @return VO
     */
    ItemVO toVo(Item source);

    List<ItemVO> dos2vos(List<Item> list);
}
