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

@ApiModel(value = "补偿类项目资金")
@Entity
@Table(name = "t_fund_compensate")
public class FundCompensate implements Serializable{

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
	 * 权属人编码
	 */
	@ApiModelProperty(value = "权属人编码")
	@Column(name = "owner_nm")
	private String ownerNm;
	
	/**
	 * 支付时间
	 */
	@ApiModelProperty(value = "支付时间")
	@Column(name = "payment_time")
	private Date paymentTime;
	
	/**
	 * 单价
	 */
	@ApiModelProperty(value ="单价")
	private Double unit;
	
	/**
	 * 流水明细
	 */
	@ApiModelProperty(value = "流水明细")
	private String detailed;
	
	/**
	 * 总价
	 */
	@ApiModelProperty(value = "总价")
	private Double total;
	
	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段")
	private String stage;
	
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getProjectNm() {
		return projectNm;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public Double getUnit() {
		return unit;
	}

	public String getDetailed() {
		return detailed;
	}

	public Double getTotal() {
		return total;
	}

	public String getStage() {
		return stage;
	}

	public String getRemark() {
		return remark;
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

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public void setUnit(Double unit) {
		this.unit = unit;
	}

	public void setDetailed(String detailed) {
		this.detailed = detailed;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
