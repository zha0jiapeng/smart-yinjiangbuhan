package com.ruoyi.system.entity;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * SDK数据接入表(SdkDataAccess)实体类
 * @since 2023-05-29 14:33:56
 */
@Data
public class SdkDataAccess implements Serializable {
    private static final long serialVersionUID = -49554689501358978L;
    
    private Integer id;
    
    private String sId;
    
    private String orgId;
    
    private String oriRedId;
    
    private String oriOrgId;
    
    private String realOrgId;
    
    private String supplierId;
    
    private String stockbinId;
    
    private String oriOrderId;
    
    private String orderDataId;
    
    private String oriStockbinId;
    
    private String orgName;
    
    private String oriOrgName;
    
    private String stockbinName;
    
    private String supplierName;
    
    private String stockbinFullName;
    
    private String orderCode;
    
    private String supplierOrgCode;
    
    private String auditDate;
    
    private String orderDate;
    
    private String makerDate;
    
    private String recordedDate;
    
    private String remark;
    
    private String auditor;
    
    private String maker;
    
    private String plateNumber;
    
    private String weightType;
    
    private String exitTime;
    
    private String enterTime;
    
    private Integer waybillWeight;
    
    private Integer printTimes;
    
    private Integer serviceType;
    
    private Integer orderType;
    
    private Integer auxiliaryNetQuantity;
    
    private Integer deductQuantity;
    
    private Integer deductRate;
    
    private Integer orderOrigin;
    
    private String photo;
    
    private String gpyPhoto;
    
    private String signaturePhoto;
    
    private String material;
    
    private Float tareQuantity;
    
    private Float roughQuantity;
    
    private Integer isRed;
    
    private Integer isExit;
    
    private Integer isTare;
    
    private Integer isAudit;
    
    private Integer isReturn;
    
    private Integer isAffirm;
    
    private Integer isRemoved;
    
    private Integer isMultiplication;
    
    private Integer isUseOrinetQuantity;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private String version;

}

