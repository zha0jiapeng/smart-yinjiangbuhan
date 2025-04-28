package com.ruoyi.web.controller.basic.yinjiangbuhan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.web.controller.basic.yinjiangbuhan.domain.ApprovedOperationItems;

import java.util.List;

/**
 * 准操项目Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-19
 */
public interface ApprovedOperationItemsMapper extends BaseMapper<ApprovedOperationItems>
{
    /**
     * 查询准操项目
     * 
     * @param id 准操项目主键
     * @return 准操项目
     */
    public ApprovedOperationItems selectApprovedOperationItemsById(Long id);

    /**
     * 查询准操项目列表
     * 
     * @param approvedOperationItems 准操项目
     * @return 准操项目集合
     */
    public List<ApprovedOperationItems> selectApprovedOperationItemsList(ApprovedOperationItems approvedOperationItems);

    /**
     * 新增准操项目
     * 
     * @param approvedOperationItems 准操项目
     * @return 结果
     */
    public int insertApprovedOperationItems(ApprovedOperationItems approvedOperationItems);

    /**
     * 修改准操项目
     * 
     * @param approvedOperationItems 准操项目
     * @return 结果
     */
    public int updateApprovedOperationItems(ApprovedOperationItems approvedOperationItems);

    /**
     * 删除准操项目
     * 
     * @param id 准操项目主键
     * @return 结果
     */
    public int deleteApprovedOperationItemsById(Long id);

    /**
     * 批量删除准操项目
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApprovedOperationItemsByIds(Long[] ids);
}
