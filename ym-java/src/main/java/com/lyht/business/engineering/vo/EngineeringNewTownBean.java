package com.lyht.business.engineering.vo;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "集镇新址建设实施信息表")
public class EngineeringNewTownBean {

	@ApiModelProperty(value = "唯一id")
	private Integer  id;
		
	@ApiModelProperty(value = "唯一nm")
	private String  nm;
	@ApiModelProperty(value = "所属范围名称")
	@JSONField(name = "area_name")
	private String  areaName;
	@ApiModelProperty(value = "专业类别名称")
	private String  magorName;
	@ApiModelProperty(value = "行政区域全称")
	private String  mergerName;
	@ApiModelProperty(value = "安置类型名称")
	private String  setName;
	@ApiModelProperty(value = "行政区域")
	private String  diming;
	@ApiModelProperty(value = "所属范围")
	private String  areaNm;

	@ApiModelProperty(value = "专业类别")
	private String  majorType;

	@ApiModelProperty(value = "行政区")
	private String  region;

	@ApiModelProperty(value = "集镇名称")
	private String  townName;

	@ApiModelProperty(value = "安置类型")
	private String  setType;

	@ApiModelProperty(value = "规划安置规模")
	private String  projectPlaceScale;

	@ApiModelProperty(value = "规划概算(万元)")
	private Double  ProjectBudget;
	@ApiModelProperty(value = "进度截至年月")
	private Date  implementInfoDate;
	@ApiModelProperty(value = "总概算资金")
	private Double  spentFunds;
	@ApiModelProperty(value = "规划安置(人)")
	private Integer  immigrants;
	@ApiModelProperty(value = "实施责任单位")
	private String  responsibleUnit;

	@ApiModelProperty(value = "施工图预算")
	private String  constructionBud;

	@ApiModelProperty(value = "是否代建")
	private Integer  isReplace;

	@ApiModelProperty(value = "代建单位")
	private String  replaceUnit;

	@ApiModelProperty(value = "施工设计单位")
	private String  designUnit;

	@ApiModelProperty(value = "施工单位")
	private String  buildUnit;

	@ApiModelProperty(value = "监理单位")
	private String  supervisorUnit;

	@ApiModelProperty(value = "关联规划项目权属编号")
	private String  planOwnershipNumber;

	@ApiModelProperty(value = "实施新址坐标")
	private String  coordinate;

	@ApiModelProperty(value = "项目权属编号")
	private String  projectOwnershipNumber;

	@ApiModelProperty(value = "备注")
	private String  remark;

	@ApiModelProperty(value = "滞后原因")
	private String  lagReason;

	@ApiModelProperty(value = "处理人")
	private String  dealingStaff;

	@ApiModelProperty(value = "创建人")
	private String  createStaff;

	@ApiModelProperty(value = "创建时间")
	private Date  createTime;

	@ApiModelProperty(value = "修改人")
	private String  updateStaff;

	@ApiModelProperty(value = "修改时间")
	private Date  updateTime;

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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getMagorName() {
		return magorName;
	}

	public void setMagorName(String magorName) {
		this.magorName = magorName;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getDiming() {
		return diming;
	}

	public void setDiming(String diming) {
		this.diming = diming;
	}

	public String getAreaNm() {
		return areaNm;
	}

	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}

	public String getMajorType() {
		return majorType;
	}

	public void setMajorType(String majorType) {
		this.majorType = majorType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public String getProjectPlaceScale() {
		return projectPlaceScale;
	}

	public void setProjectPlaceScale(String projectPlaceScale) {
		this.projectPlaceScale = projectPlaceScale;
	}

	public Double getProjectBudget() {
		return ProjectBudget;
	}

	public void setProjectBudget(Double projectBudget) {
		ProjectBudget = projectBudget;
	}

	public Date getImplementInfoDate() {
		return implementInfoDate;
	}

	public void setImplementInfoDate(Date implementInfoDate) {
		this.implementInfoDate = implementInfoDate;
	}

	public Double getSpentFunds() {
		return spentFunds;
	}

	public void setSpentFunds(Double spentFunds) {
		this.spentFunds = spentFunds;
	}

	public Integer getImmigrants() {
		return immigrants;
	}

	public void setImmigrants(Integer immigrants) {
		this.immigrants = immigrants;
	}

	public String getResponsibleUnit() {
		return responsibleUnit;
	}

	public void setResponsibleUnit(String responsibleUnit) {
		this.responsibleUnit = responsibleUnit;
	}

	public String getConstructionBud() {
		return constructionBud;
	}

	public void setConstructionBud(String constructionBud) {
		this.constructionBud = constructionBud;
	}

	public Integer getIsReplace() {
		return isReplace;
	}

	public void setIsReplace(Integer isReplace) {
		this.isReplace = isReplace;
	}

	public String getReplaceUnit() {
		return replaceUnit;
	}

	public void setReplaceUnit(String replaceUnit) {
		this.replaceUnit = replaceUnit;
	}

	public String getDesignUnit() {
		return designUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public String getBuildUnit() {
		return buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public String getSupervisorUnit() {
		return supervisorUnit;
	}

	public void setSupervisorUnit(String supervisorUnit) {
		this.supervisorUnit = supervisorUnit;
	}

	public String getPlanOwnershipNumber() {
		return planOwnershipNumber;
	}

	public void setPlanOwnershipNumber(String planOwnershipNumber) {
		this.planOwnershipNumber = planOwnershipNumber;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getProjectOwnershipNumber() {
		return projectOwnershipNumber;
	}

	public void setProjectOwnershipNumber(String projectOwnershipNumber) {
		this.projectOwnershipNumber = projectOwnershipNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLagReason() {
		return lagReason;
	}

	public void setLagReason(String lagReason) {
		this.lagReason = lagReason;
	}

	public String getDealingStaff() {
		return dealingStaff;
	}

	public void setDealingStaff(String dealingStaff) {
		this.dealingStaff = dealingStaff;
	}

	public String getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(String createStaff) {
		this.createStaff = createStaff;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateStaff() {
		return updateStaff;
	}

	public void setUpdateStaff(String updateStaff) {
		this.updateStaff = updateStaff;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMergerName() {
		return mergerName;
	}

	public void setMergerName(String mergerName) {
		this.mergerName = mergerName;
	}
}
