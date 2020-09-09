package com.lyht.business.abm.paymentManagement.pojo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public class ApplyDetail {

	@ApiModelProperty(value = "申办人")
	private String proposer;

	@ApiModelProperty(value = "申办人部门")
	private String proposerdept;

	@ApiModelProperty(value = "申请时间")
	private String applytime;

	@ApiModelProperty(value = "收款人")
	private String payee;

	@ApiModelProperty(value = "支付方式")
	private String paymentmethod;

	@ApiModelProperty(value = "卡号")
	private String bankcard;

	@ApiModelProperty(value = "开户银行")
	private String depositbank;

	@ApiModelProperty(value = "支付事由及情况说明")
	private String description;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "协议编号")
	private String protocolcode;

	@ApiModelProperty(value = "协议类型")
	private Integer protocoltype;
	
	@ApiModelProperty(value = "协议总额")
	private BigDecimal protocolamount;
	
	@ApiModelProperty(value = "已申请金额")
	private BigDecimal protocolpayed;
	
	@ApiModelProperty(value = "剩余金额")
	private BigDecimal protocolsurplus;
	
	@ApiModelProperty(value = "申请金额")
	private BigDecimal applyamount ;

	@ApiModelProperty(value = "批次")
	private Integer batch;

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public String getProposerdept() {
		return proposerdept;
	}

	public void setProposerdept(String proposerdept) {
		this.proposerdept = proposerdept;
	}

	public String getApplytime() {
		return applytime;
	}

	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	public String getDepositbank() {
		return depositbank;
	}

	public void setDepositbank(String depositbank) {
		this.depositbank = depositbank;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProtocolcode() {
		return protocolcode;
	}

	public void setProtocolcode(String protocolcode) {
		this.protocolcode = protocolcode;
	}

	public Integer getProtocoltype() {
		return protocoltype;
	}

	public void setProtocoltype(Integer protocoltype) {
		this.protocoltype = protocoltype;
	}

	public BigDecimal getProtocolamount() {
		return protocolamount;
	}

	public void setProtocolamount(BigDecimal protocolamount) {
		this.protocolamount = protocolamount;
	}

	public BigDecimal getProtocolpayed() {
		return protocolpayed;
	}

	public void setProtocolpayed(BigDecimal protocolpayed) {
		this.protocolpayed = protocolpayed;
	}

	public BigDecimal getProtocolsurplus() {
		return protocolsurplus;
	}

	public void setProtocolsurplus(BigDecimal protocolsurplus) {
		this.protocolsurplus = protocolsurplus;
	}

	public BigDecimal getApplyamount() {
		return applyamount;
	}

	public void setApplyamount(BigDecimal applyamount) {
		this.applyamount = applyamount;
	}

	public Integer getBatch() {
		return batch;
	}

	public void setBatch(Integer batch) {
		this.batch = batch;
	}
	
}
