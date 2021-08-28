
package com.iskech.mybatis.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ---------------------------------------------------------------------------
 * 类名称   ：OrderLog.java
 * @author ：lijx
 * ---------------------------------------------------------------------------
 */


public class OrderLog implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Long id;
    protected String traceId;

    protected String createdBy;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    protected Timestamp creationDate;
    protected String updatedBy;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    protected Timestamp updationDate;
    protected Long enabledFlag = 1L;

    /**
     * 字段名称：订单表id
     */
    private Long cargoOrderId;

    /**
     * 字段名称：货主需求订单编码
     */
    private String cargoOrderCode;

    /**
     * 字段名称：询价扩展编号
     */
    private String askOrderNo;

    /**
     * 字段名称：状态（100：待询价, 200：询价完成）
     */
    private Integer status;

    /**
     * 字段名称：询价类型（1跨越，2.云鸟，3.福佑，4.司机(外请个人) 5.平台司机）
     */
    private String inquiryType;

    /**
     * 字段名称：备注
     */
    private String remark;

    /**
     * 字段名称：1.未报价短信提醒 2.未确认下单短信提醒
     */
    private Integer isSms;

    /**
     * 字段名称：创建人名称（业务使用）
     */
    private String createdByName;

    /**
     * 字段名称：修改人名称
     */
    private String updatedByName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdationDate() {
        return updationDate;
    }

    public void setUpdationDate(Timestamp updationDate) {
        this.updationDate = updationDate;
    }

    public Long getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Long enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public Long getCargoOrderId() {
        return cargoOrderId;
    }

    public void setCargoOrderId(Long cargoOrderId) {
        this.cargoOrderId = cargoOrderId;
    }

    public String getCargoOrderCode() {
        return cargoOrderCode;
    }

    public void setCargoOrderCode(String cargoOrderCode) {
        this.cargoOrderCode = cargoOrderCode;
    }

    public String getAskOrderNo() {
        return askOrderNo;
    }

    public void setAskOrderNo(String askOrderNo) {
        this.askOrderNo = askOrderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsSms() {
        return isSms;
    }

    public void setIsSms(Integer isSms) {
        this.isSms = isSms;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }
}