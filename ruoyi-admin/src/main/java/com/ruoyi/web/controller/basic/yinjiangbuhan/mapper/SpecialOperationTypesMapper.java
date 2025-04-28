package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.SpecialOperationTypes;

/**
 * 特种作业类型Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-19
 */
public interface SpecialOperationTypesMapper extends BaseMapper<SpecialOperationTypes>
{
    /**
     * 查询特种作业类型
     * 
     * @param id 特种作业类型主键
     * @return 特种作业类型
     */
    public SpecialOperationTypes selectSpecialOperationTypesById(Long id);

    /**
     * 查询特种作业类型列表
     * 
     * @param specialOperationTypes 特种作业类型
     * @return 特种作业类型集合
     */
    public List<SpecialOperationTypes> selectSpecialOperationTypesList(SpecialOperationTypes specialOperationTypes);

    /**
     * 新增特种作业类型
     * 
     * @param specialOperationTypes 特种作业类型
     * @return 结果
     */
    public int insertSpecialOperationTypes(SpecialOperationTypes specialOperationTypes);

    /**
     * 修改特种作业类型
     * 
     * @param specialOperationTypes 特种作业类型
     * @return 结果
     */
    public int updateSpecialOperationTypes(SpecialOperationTypes specialOperationTypes);

    /**
     * 删除特种作业类型
     * 
     * @param id 特种作业类型主键
     * @return 结果
     */
    public int deleteSpecialOperationTypesById(Long id);

    /**
     * 批量删除特种作业类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSpecialOperationTypesByIds(Long[] ids);
}
