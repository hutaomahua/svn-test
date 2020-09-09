package com.lyht.business.abm.paymentManagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *作者： 阎晓辉
 *脚本日期:2020年2月19日 1:24:20
 *说明: 
*/
@Entity
@Table(name = "t_abm_payment_confirmation_batch")
@ApiModel(description = "兑付确认批次表")
public class PaymentConfirmationBatch {

	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id" , nullable = false )
    private Integer id;
	
    @ApiModelProperty(value = "记录内码")
	@Column(name = "nm")
	private String nm;
	
    @ApiModelProperty(value = "批次号")
	@Column(name = "batch_num")
	private String batchNum;
	
    @ApiModelProperty(value = "行政区")
	@Column(name = "region")
	private String region;
	
    @ApiModelProperty(value = "操作人")
	@Column(name = "proposer")
	private String proposer;
	
    @ApiModelProperty(value = "操作时间")
	@Column(name = "confirm_time")
	private String confirmTime;
	
    @ApiModelProperty(value = "兑付申请流程实例id")
	@Column(name = "process_id")
	private String processId;
    
    @ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
