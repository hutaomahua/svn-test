package com.lyht.business.fund.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "预备/独立费用")
@Entity
@Table(name = "t_fund_cost")
public class FundCost implements Serializable {

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
	 * 支付时间
	 */
	@Column(name = "payment_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "支付时间")
	private Date paymentTime;

	/**
	 * 费用类别
	 */
	@Column(name = "amount_type")
	@ApiModelProperty(value = "费用类别")
	private String amountType;

	/**
	 * 金额(万元)
	 */
	@ApiModelProperty(value = "金额(万元)")
	private Double amount;

	/**
	 * 类型(企业/地方政府)
	 */
	@Column(name = "organ_type")
	@ApiModelProperty(value = "类型(企业/地方政府)")
	private String organType;

	/**
	 * 支付单位
	 */
	@Column(name = "organ_name")
	@ApiModelProperty(value = "支付单位")
	private String organNm;

	/**
	 * 行政区
	 */
	@ApiModelProperty(value = "行政区 ")
	private String region;

	/**
	 * 建设征地范围
	 */
	@ApiModelProperty(value = "建设征地范围")
	private String area;
	/**
	 * 单价/费率(元/--)
	 */
	@Column(name = "unit_price")
	@ApiModelProperty(value = "单价/费率(元/--)")
	private Double unitPrice;

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

	/**
	 * 经办人
	 */
	@ApiModelProperty(value = "经办人")
	private String manager;

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

	public String getAmountType() {
		return amountType;
	}

	public Double getAmount() {
		return amount;
	}

	public String getOrganType() {
		return organType;
	}

	public String getOrganNm() {
		return organNm;
	}

	public String getRegion() {
		return region;
	}

	public String getArea() {
		return area;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public String getRemark() {
		return remark;
	}

	public String getStage() {
		return stage;
	}

	public String getManager() {
		return manager;
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

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public void setOrganNm(String organNm) {
		this.organNm = organNm;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
