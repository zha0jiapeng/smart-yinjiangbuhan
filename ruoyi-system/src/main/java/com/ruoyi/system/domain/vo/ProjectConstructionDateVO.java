package com.ruoyi.system.domain.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * 项目施工报表(ProjectConstruction)实体类
 * @since 2023-06-01 14:05:22
 */
@Data
public class ProjectConstructionDateVO {

    /**
     * 实际开始时间
     */
    private String actualBegin;
    /**
     * 实际完成时间
     */
    private String actualFinish;
    /**
     * 实际工期/天
     */
    private String actualDay;

}

