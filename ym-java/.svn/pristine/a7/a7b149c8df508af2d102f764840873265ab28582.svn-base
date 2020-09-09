package com.lyht.business.abm.appropriation.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_abm_appropriation")
@EntityListeners(AuditingEntityListener.class)
public class Appropriation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "编号")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(value = "唯一nm")
	private String nm;
	
	@ApiModelProperty(value = "协议内码")
	@Column(name = "protocol_nm")
	private String protocolNm;
	
	@ApiModelProperty(value = "兑付申请编号")
	@Column(name = "application_no")
	private String applicationNo;

	@ApiModelProperty(value = "申请部门")
	@Column(name = "dept_nm")
	private String deptNm;

	@ApiModelProperty(value = "业务申办人")
	@Column(name = "applicant")
	private String applicant;

	@ApiModelProperty(value = "合同金额")
	@Column(name = "protocol_amount")
	private Double protocolAmount;

	@ApiModelProperty(value = "累计已支付金额")
	@Column(name = "paid_amount")
	private Double paidAmount;

	@ApiModelProperty(value = "未支付金额")
	@Column(name = "unpaid_amount")
	private Double unpaidAmount;

	@ApiModelProperty(value = "申请支付金额")
	@Column(name = "apply_amount")
	private Double applyAmount;

	@ApiModelProperty(value = "收款单位（人）")
	@Column(name = "receivables_object")
	private String receivablesObject;

	@ApiModelProperty(value = "款项类型")
	@Column(name = "money_type")
	private String moneyType;

	@ApiModelProperty(value = "申请时间")
	@Column(name = "apply_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date applyTime;

	@ApiModelProperty(value = "银行卡")
	@Column(name = "bank_card")
	private String bankCard;

	@ApiModelProperty(value = "支付日期")
	@Column(name = "paid_time")
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date paidTime;

	@ApiModelProperty(value = "支付方式")
	@Column(name = "paid_mode")
	private String paidMode;

	@ApiModelProperty(value = "支付款项")
	@Column(name = "paid_money")
	private String paidMoney;

	@ApiModelProperty(value = "申请人")
	@Column(name = "apply_people")
	private String applyPeople;

	@ApiModelProperty(value = "支付事由及情况说明")
	@Column(name = "paid_cause")
	private String paidCause;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态")
	private Integer flag;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public String getApplicant() {
		return applicant;
	}

	public Double getProtocolAmount() {
		return protocolAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public Double getUnpaidAmount() {
		return unpaidAmount;
	}

	public Double getApplyAmount() {
		return applyAmount;
	}

	public String getReceivablesObject() {
		return receivablesObject;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public String getBankCard() {
		return bankCard;
	}

	public Date getPaidTime() {
		return paidTime;
	}

	public String getPaidMode() {
		return paidMode;
	}

	public String getPaidMoney() {
		return paidMoney;
	}

	public String getApplyPeople() {
		return applyPeople;
	}

	public String getPaidCause() {
		return paidCause;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public void setProtocolAmount(Double protocolAmount) {
		this.protocolAmount = protocolAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public void setUnpaidAmount(Double unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public void setReceivablesObject(String receivablesObject) {
		this.receivablesObject = receivablesObject;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

	public void setPaidMode(String paidMode) {
		this.paidMode = paidMode;
	}

	public void setPaidMoney(String paidMoney) {
		this.paidMoney = paidMoney;
	}

	public void setApplyPeople(String applyPeople) {
		this.applyPeople = applyPeople;
	}

	public void setPaidCause(String paidCause) {
		this.paidCause = paidCause;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getProtocolNm() {
		return protocolNm;
	}

	public void setProtocolNm(String protocolNm) {
		this.protocolNm = protocolNm;
	}
	
}
