package com.ruoyi.assessment.modules.repo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.assessment.modules.repo.dto.request.RepoReqDTO;
import com.ruoyi.assessment.modules.repo.dto.response.RepoRespDTO;
import com.ruoyi.assessment.modules.repo.entity.Repo;
import org.apache.ibatis.annotations.Param;

/**
* <p>
* 题库Mapper
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
public interface RepoMapper extends BaseMapper<Repo> {

    /**
     * 分页查询题库
     * @param page
     * @param query
     * @return
     */
    IPage<RepoRespDTO> paging(Page page, @Param("query") RepoReqDTO query);

}
