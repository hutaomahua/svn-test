package com.lyht.business.fund.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工程类项目资金
 * 
 * @author wzw
 *
 */
@ApiModel(value = "工程类项目资金")
@Entity
@Table(name = "t_fund_engineering")
public class FundEngineering implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一ID，修改必填
	 */
	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 唯一内码
	 */
	@ApiModelProperty(value = "唯一内码")
	private String nm;

	/**
	 * 项目
	 */
	@ApiModelProperty(value = "项目")
	@Column(name = "project_nm")
	private String projectNm;

	/**
	 * 时间
	 */
	@ApiModelProperty(value = "时间")
	@Column(name = "payment_time")
	private Date paymentTime;

	/**
	 * 金额
	 */
	@Column(name = "金额")
	@ApiModelProperty(value = "金额")
	private Double paymentAmount;

	/**
	 * 拨付单位
	 */
	@Column(name = "payment_organ")
	@ApiModelProperty(value = "拨付单位")
	private String paymentOrgan;

	/**
	 * 项目基本信息
	 */
	@Column(name = "prj_basic_nm")
	@ApiModelProperty(value = "项目基本信息")
	private String prjBasicNm;

	/**
	 * 支付号
	 */
	@ApiModelProperty(value = "支付号")
	@Column(name = "payment_number")
	private String paymentNumber;

	/**
	 * 摘要说明
	 */
	@ApiModelProperty(value = "摘要说明")
	private String remark;

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段")
	private String stage;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getProjectNm() {
		return projectNm;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public String getPaymentOrgan() {
		return paymentOrgan;
	}

	public String getPrjBasicNm() {
		return prjBasicNm;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public String getRemark() {
		return remark;
	}

	public String getStage() {
		return stage;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setProjectNm(String projectNm) {
		this.projectNm = projectNm;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public void setPaymentOrgan(String paymentOrgan) {
		this.paymentOrgan = paymentOrgan;
	}

	public void setPrjBasicNm(String prjBasicNm) {
		this.prjBasicNm = prjBasicNm;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
