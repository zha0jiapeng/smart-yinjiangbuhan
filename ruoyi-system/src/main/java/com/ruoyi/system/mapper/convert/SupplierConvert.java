package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Supplier;
import com.ruoyi.system.pojo.dto.SupplierDTO;
import com.ruoyi.system.pojo.vo.SupplierVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 供应商  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface SupplierConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    SupplierDTO do2dto(Supplier source);

    /**
     * @param source DTO
     * @return DO
     */
    Supplier dto2do(SupplierDTO source);

    List<SupplierVO> dos2vos(List<Supplier> list);
}
