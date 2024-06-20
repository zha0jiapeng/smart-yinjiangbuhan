package com.ruoyi.system.domain.file;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("sys_file_package")
public class SysFilePackage {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 父id
     */
    private Integer pid;
    /**
     * 文件夹名称
     */
    private String name;
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
     * 逻辑删除表示 1正常 0删除
     */
    private Integer yn;

    @TableField(exist = false)
    private Boolean Leaf;

    /**
     * 文件
     */
    @TableField(exist = false)
    private List<SysFileManage> sysFileManages;

}
