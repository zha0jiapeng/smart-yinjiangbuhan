package com.ruoyi.system.mapper;


import com.ruoyi.system.domain.ElPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElPaperMapper {

    List<ElPaper> selectUserList(@Param("phone") String phone);

}
