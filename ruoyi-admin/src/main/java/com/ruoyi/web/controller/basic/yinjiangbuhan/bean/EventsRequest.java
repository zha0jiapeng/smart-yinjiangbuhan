package com.ruoyi.web.controller.basic.yinjiangbuhan.bean;

import java.util.ArrayList;

/**
 * 查询门禁事件
 */
public class EventsRequest {
    private Integer pageNo;
    private Integer pageSize;
    /**
     * 门禁点唯一标识数组，最大支持10个门禁点，
     */
    private ArrayList<String> doorIndexCodes;
    /**
     * 门禁点名称，支持模糊查询
     */
    private String doorName;
    /**
     * 读卡器唯一标识数组，最大支持50个读卡器，
     */
    private ArrayList<String> readerDevIndexCodes;
    /**
     * 开始时间（事件开始时间，采用ISO8601时间格式，与endTime配对使用，不能单独使用，时间范围最大不能超过3个月）
     */
    private String startTime;
    /**
     * 结束时间（事件结束时间，采用ISO8601时间格式，最大长度32个字符，与startTime配对使用，不能单独使用，时间范围最大不能超过3个月）
     */
    private String endTime;
    /**
     * 入库开始时间，采用ISO8601时间格式，与receiveEndTime配对使用，不能单独使用，时间范围最大不能超过3个月，
     */
    private String receiveStartTime;
    private String receiveEndTime;
    /**
     * 门禁点所在区域，
     */
    private String doorRegionIndexCode;
    private ArrayList<Integer> eventTypes;
    /**
     * 人员姓名(支持中英文字符，不能包含 ' / \ : * ? " < > |，支持模糊搜索,长度不超过32位)
     */
    private String personName;
    /**
     * 排序字段（支持personName、doorName、eventTime填写排序的字段名称
     */
    private String sort;
    private String order;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public ArrayList<String> getDoorIndexCodes() {
        return doorIndexCodes;
    }

    public void setDoorIndexCodes(ArrayList<String> doorIndexCodes) {
        this.doorIndexCodes = doorIndexCodes;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public ArrayList<String> getReaderDevIndexCodes() {
        return readerDevIndexCodes;
    }

    public void setReaderDevIndexCodes(ArrayList<String> readerDevIndexCodes) {
        this.readerDevIndexCodes = readerDevIndexCodes;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReceiveStartTime() {
        return receiveStartTime;
    }

    public void setReceiveStartTime(String receiveStartTime) {
        this.receiveStartTime = receiveStartTime;
    }

    public String getReceiveEndTime() {
        return receiveEndTime;
    }

    public void setReceiveEndTime(String receiveEndTime) {
        this.receiveEndTime = receiveEndTime;
    }

    public String getDoorRegionIndexCode() {
        return doorRegionIndexCode;
    }

    public void setDoorRegionIndexCode(String doorRegionIndexCode) {
        this.doorRegionIndexCode = doorRegionIndexCode;
    }

    public ArrayList<Integer> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(ArrayList<Integer> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

