package com.ruoyi.system.domain.file;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_file_manage")
public class SysFileManage {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 文件包id
     */
    private Integer filePackageId;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件地址
     */
    private String fileUrl;
    /**
     * 文件大小
     */
    private Double fileSize;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
