package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户Mapper接口
 * 
 * @author zcc
 */
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 查询客户列表
     *
     * @param customer 客户
     * @return 客户集合
     */
    List<Customer> selectByEntity(Customer customer);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);
}
