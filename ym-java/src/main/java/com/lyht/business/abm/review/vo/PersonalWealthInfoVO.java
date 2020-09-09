package com.lyht.business.abm.review.vo;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModelProperty;

public class PersonalWealthInfoVO {
	
	@ApiModelProperty(value = "权属人")
	private String name;
	
	@ApiModelProperty(value = "nm")
	private String nm;
	
	@ApiModelProperty(value = "身份证号")
	private String idCard;
	
	@ApiModelProperty(value = "复核申请人")
	private String reviewer;
	
	@ApiModelProperty(value = "复核申请编号")
	private String applicationNumber;
	
	@ApiModelProperty(value = "复核项目")
	private String reviewProject;
	
	@ApiModelProperty(value = "征地范围")
	private String scope;
	
	@ApiModelProperty(value = "申请原由")
	private String reviewReason;
	
	@ApiModelProperty(value = "复核申请附件名称")
	private String reviewFileName;
	
	@ApiModelProperty(value = "复核申请附件地址URL")
	private String reviewFileUrl;
	
	@ApiModelProperty(value = "复核申请附件名称")
	private String changeFileName;
	
	@ApiModelProperty(value = "复核申请附件地址URL")
	private String changeFileUrl;
	
	public String getChangeFileName() {
		return changeFileName;
	}

	public void setChangeFileName(String changeFileName) {
		this.changeFileName = changeFileName;
	}

	public String getChangeFileUrl() {
		return changeFileUrl;
	}

	public void setChangeFileUrl(String changeFileUrl) {
		this.changeFileUrl = changeFileUrl;
	}

	public String getReviewFileName() {
		return reviewFileName;
	}

	public void setReviewFileName(String reviewFileName) {
		this.reviewFileName = reviewFileName;
	}

	public String getReviewFileUrl() {
		return reviewFileUrl;
	}

	public void setReviewFileUrl(String reviewFileUrl) {
		this.reviewFileUrl = reviewFileUrl;
	}

	public String getReviewReason() {
		return reviewReason;
	}

	public void setReviewReason(String reviewReason) {
		this.reviewReason = reviewReason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getReviewProject() {
		return reviewProject;
	}

	public void setReviewProject(String reviewProject) {
		this.reviewProject = reviewProject;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
