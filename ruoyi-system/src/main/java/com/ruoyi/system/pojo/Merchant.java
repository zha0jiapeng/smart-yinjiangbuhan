package com.ruoyi.system.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户对接表
 * </p>
 *
 * @author 闫政澳
 * @since 2023-03-09
 */
@TableName("merchant")
@Data
@EqualsAndHashCode(callSuper = false)
public class Merchant implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商户编码
     */
    private String merchantCode;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商户服务
     */
    private String merchantServer;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新人
     */
    private String modifyBy;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 逻辑删除标识 1正常 0 删除
     */
    private Integer yn;


}
