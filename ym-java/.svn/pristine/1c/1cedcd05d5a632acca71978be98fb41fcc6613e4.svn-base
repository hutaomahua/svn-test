package com.lyht.business.engineering.entity;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "工程类项目管理实施进度表")
@Entity
@Table(name = "t_engineering_evaluate")
@EntityListeners(AuditingEntityListener.class)
public class EngineeringEvaluate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ApiModelProperty(value = "唯一nm")
	private String nm;

	@ApiModelProperty(value = "工程类项目内码")
	@Column(name = "engineering_nm")
	private String engineeringNm;

	@ApiModelProperty(value = "工程类项目类型")
	@Column(name = "engineering_type")
	private Integer engineeringType;

	@ApiModelProperty(value = "总体形象进度")
	@Column(name = "engin_progress_nm")
	private String enginProgressNm;

	@ApiModelProperty(value = "进度评价")
	@Column(name = "progress_evaluate")
	private String progressEvaluate;

	@ApiModelProperty(value = "实施状态")
	@Column(name = "implement_status")
	private Integer implementStatus;

	@ApiModelProperty(value = "已安置移民")
	@Column(name = "settled_immigrants")
	private Integer settledImmigrants;

	@ApiModelProperty(value = "未安置移民")
	@Column(name = "unsettled_immigrants")
	private Integer unsettledImmigrants;

	@ApiModelProperty(value = "实施信息截止年月")
	@Column(name = "Implement_info_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date implementInfoDate;

	@ApiModelProperty(value = "已使用资金（万元）")
	@Column(name = "spent_funds")
	private Double spentFunds;

	@ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

	@ApiModelProperty(value = "创建人")
	@Column(name = "create_staff")
	@CreatedBy
	private String createStaff;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time")
	@CreatedDate
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getEngineeringNm() {
		return engineeringNm;
	}

	public Integer getEngineeringType() {
		return engineeringType;
	}

	public String getEnginProgressNm() {
		return enginProgressNm;
	}

	public String getProgressEvaluate() {
		return progressEvaluate;
	}

	public Integer getImplementStatus() {
		return implementStatus;
	}

	public Integer getSettledImmigrants() {
		return settledImmigrants;
	}

	public Integer getUnsettledImmigrants() {
		return unsettledImmigrants;
	}

	public Date getImplementInfoDate() {
		return implementInfoDate;
	}

	public Double getSpentFunds() {
		return spentFunds;
	}

	public String getRemark() {
		return remark;
	}

	public String getCreateStaff() {
		return createStaff;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setEngineeringNm(String engineeringNm) {
		this.engineeringNm = engineeringNm;
	}

	public void setEngineeringType(Integer engineeringType) {
		this.engineeringType = engineeringType;
	}

	public void setEnginProgressNm(String enginProgressNm) {
		this.enginProgressNm = enginProgressNm;
	}

	public void setProgressEvaluate(String progressEvaluate) {
		this.progressEvaluate = progressEvaluate;
	}

	public void setImplementStatus(Integer implementStatus) {
		this.implementStatus = implementStatus;
	}

	public void setSettledImmigrants(Integer settledImmigrants) {
		this.settledImmigrants = settledImmigrants;
	}

	public void setUnsettledImmigrants(Integer unsettledImmigrants) {
		this.unsettledImmigrants = unsettledImmigrants;
	}

	public void setImplementInfoDate(Date implementInfoDate) {
		this.implementInfoDate = implementInfoDate;
	}

	public void setSpentFunds(Double spentFunds) {
		this.spentFunds = spentFunds;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreateStaff(String createStaff) {
		this.createStaff = createStaff;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
