package com.ruoyi.system.domain.basic;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("bim_upload_basic")
public class BimUploadBasic extends BaseDomain {

    private String uploadBasicId;

    @TableField(exist = false)
    private Boolean check;

    //@JSONField(serializeUsing=StringToJsonSerializer.class)
    private String info;

}
