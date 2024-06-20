package com.ruoyi.system.mapper.convert;

import com.ruoyi.system.domain.Customer;
import com.ruoyi.system.pojo.dto.CustomerDTO;
import com.ruoyi.system.pojo.vo.CustomerVO;
import org.mapstruct.Mapper;

import java.util.List;
/**
 * 客户  DO <=> DTO <=> VO / BO / Query
 *
 * @author zcc
 */
@Mapper(componentModel = "spring")
public interface CustomerConvert  {

    /**
     * @param source DO
     * @return DTO
     */
    CustomerDTO do2dto(Customer source);

    /**
     * @param source DTO
     * @return DO
     */
    Customer dto2do(CustomerDTO source);

    List<CustomerVO> dos2vos(List<Customer> list);
}
