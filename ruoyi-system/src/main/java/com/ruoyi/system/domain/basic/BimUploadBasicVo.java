package com.ruoyi.system.domain.basic;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BimUploadBasicVo extends BaseDomain{

    private String uploadBasicId;

    @TableField(exist = false)
    private Boolean check;

    private List<Object> info;

}
