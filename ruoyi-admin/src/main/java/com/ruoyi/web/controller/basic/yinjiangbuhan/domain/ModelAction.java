package com.ruoyi.web.controller.basic.yinjiangbuhan.domain;

/**
 * Created by yangqinghua on 2022/3/5.
 */

public class ModelAction {
    private String modelName;//模型名称
    private String treeNodeID;//模型TreeNodeID
    private String objId;//模型ObjId
    private String type;//子类型
    private char direction; //方向
    private Integer value;//值/mm
    private Integer stableTime; //持续时间(ms)
    private Integer frameNumber;//总帧数
    private String groups;//分组
    private String remark;//备注
    private String site;//动画文件地址
    private Integer begin;//动画文件的开始名称
    private Integer over;//动画文件的结束名称
    private String materialId;//材质Id
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTreeNodeID() {
        return treeNodeID;
    }

    public void setTreeNodeID(String treeNodeID) {
        this.treeNodeID = treeNodeID;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getStableTime() {
        return stableTime;
    }

    public void setStableTime(Integer stableTime) {
        this.stableTime = stableTime;
    }

//

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getOver() {
        return over;
    }

    public void setOver(Integer over) {
        this.over = over;
    }

    public Integer getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(Integer frameNumber) {
        this.frameNumber = frameNumber;
    }
}
