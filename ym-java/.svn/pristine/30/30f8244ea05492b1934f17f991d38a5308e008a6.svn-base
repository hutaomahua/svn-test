package com.lyht.business.fund.entity;

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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资金拨付台账
 * 
 * @author wzw
 *
 */
@ApiModel(value = "资金拨付台账")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_fund_appropriation")
public class FundAppropriation implements Serializable {

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
	 * 所属项目分类
	 */
	@ApiModelProperty(value = "所属项目分类")
	@Column(name = "pay_project_nm")
	private String payProjectNm;

	/**
	 * 项目名称
	 */
	@ApiModelProperty(value = "项目名称")
	@Column(name = "project_name")
	private String projectName;
	/**
	 * 合同名称
	 */
	@ApiModelProperty(value = "合同名称")
	@Column(name = "contract_name")
	private String contractName;
	/**
	 * 合同金额
	 */
	@ApiModelProperty(value = "合同金额")
	@Column(name = "contract_amount")
	private Double contractAmount;
	/**
	 * 支付时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8" )
	@ApiModelProperty(value = "支付时间")
	@Column(name = "pay_time")
	private Date payTime;
	/**
	 * 累计支付金额
	 */
	@ApiModelProperty(value = "累计支付金额")
	@Column(name = "sun_amount")
	private Double sunAmount;
	
	/**
	 * 支付金额
	 */
	@ApiModelProperty(value = "支付金额")
	@Column(name ="pay_number")
	private Double payNumber;
	/**
	 * 是否完成支付
	 */
	@ApiModelProperty(value = "是否完成支付")
	@Column(name = "is_pay")
	private Integer isPay;
	
	/**
	 * 收款方
	 */
	@ApiModelProperty(value = "收款方")
	@Column(name = "but_the")
	private String butThe;
	
	/**
	 * 处理人
	 */
	@ApiModelProperty(value = "处理人")
	@Column(name = "dealing_staff")
	private String dealingStaff;
	
	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	@Column(name = "create_staff")
	@CreatedBy
	private String createStaff;
	
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time")
	@CreatedDate
	private Date createTime;
	
	/**
	 * 修改人
	 */
	@ApiModelProperty(value = "修改人")
	@Column(name = "update_staff")
	@LastModifiedBy
	private String update_staff;
	
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@Column(name = "update_time")
	@LastModifiedDate
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getPayProjectNm() {
		return payProjectNm;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getContractName() {
		return contractName;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public Date getpayTime() {
		return payTime;
	}

	public Double getsunAmount() {
		return sunAmount;
	}

	public Double getPayNumber() {
		return payNumber;
	}

	public Integer getIsPay() {
		return isPay;
	}

	public String getButThe() {
		return butThe;
	}

	public String getDealingStaff() {
		return dealingStaff;
	}

	public String getCreateStaff() {
		return createStaff;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getUpdate_staff() {
		return update_staff;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setPayProjectNm(String payProjectNm) {
		this.payProjectNm = payProjectNm;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public void setpayTime(Date payTime) {
		this.payTime = payTime;
	}

	public void setsunAmount(Double sunAmount) {
		this.sunAmount = sunAmount;
	}

	public void setPayNumber(Double payNumber) {
		this.payNumber = payNumber;
	}

	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}

	public void setButThe(String butThe) {
		this.butThe = butThe;
	}

	public void setDealingStaff(String dealingStaff) {
		this.dealingStaff = dealingStaff;
	}

	public void setCreateStaff(String createStaff) {
		this.createStaff = createStaff;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdate_staff(String update_staff) {
		this.update_staff = update_staff;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
}
