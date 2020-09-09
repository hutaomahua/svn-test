package com.lyht.business.engineering.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "临时用地复垦实施信息表")
@Entity
@Table(name = "t_engineering_reclaim_info")
public class EngineeringReclaimInfo implements Serializable {

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

	@ApiModelProperty(value = "地块编号")
	@Column(name = "land_nm")
	private String landNm;

	@ApiModelProperty(value = "行政区")
	private String region;

	@ApiModelProperty(value = "专业大类")
	@Column(name = "major_type")
	private String majorType;

	@ApiModelProperty(value = "所属区域")
	@Column(name = "area_nm")
	private String areaNm;

	@ApiModelProperty(value = "规划工程量")
	@Column(name = "plan_quantities")
	private String planQuantities;

	@ApiModelProperty(value = "规划概算(万元)")
	@Column(name = "plan_budget")
	private Double planBudget;

	@ApiModelProperty(value = "实施责任单位")
	@Column(name = "responsible_unit")
	private String responsibleUnit;

	@ApiModelProperty(value = "施工图预算")
	@Column(name = "construction_budget")
	private String constructionBudget;

	@ApiModelProperty(value = "是否代建")
	@Column(name = "is_replace")
	private Integer isReplace;

	@ApiModelProperty(value = "代建单位")
	@Column(name = "replace_unit")
	private String replaceUnit;

	@ApiModelProperty(value = "施工设计单位")
	@Column(name = "design_unit")
	private String designUnit;

	@ApiModelProperty(value = "施工单位")
	@Column(name = "build_unit")
	private String buildUnit;

	@ApiModelProperty(value = "监理单位")
	@Column(name = "supervisor_unit")
	private String supervisorUnit;

	@ApiModelProperty(value = "关联规划项目权属编号")
	@Column(name = "plan_ownership_number")
	private String planOwnershipNumber;

	@ApiModelProperty(value = "实施新址坐标")
	private String coordinate;

	@ApiModelProperty(value = "项目权属编号")
	@Column(name = "project_ownership_number")
	private String projectOwnershipNumber;

	@ApiModelProperty(value = "备注")
	private String remark;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getLandNm() {
		return landNm;
	}

	public String getRegion() {
		return region;
	}

	public String getMajorType() {
		return majorType;
	}

	public String getAreaNm() {
		return areaNm;
	}

	public String getPlanQuantities() {
		return planQuantities;
	}

	public Double getPlanBudget() {
		return planBudget;
	}

	public String getResponsibleUnit() {
		return responsibleUnit;
	}

	public String getConstructionBudget() {
		return constructionBudget;
	}

	public Integer getIsReplace() {
		return isReplace;
	}

	public String getReplaceUnit() {
		return replaceUnit;
	}

	public String getDesignUnit() {
		return designUnit;
	}

	public String getBuildUnit() {
		return buildUnit;
	}

	public String getSupervisorUnit() {
		return supervisorUnit;
	}

	public String getPlanOwnershipNumber() {
		return planOwnershipNumber;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public String getProjectOwnershipNumber() {
		return projectOwnershipNumber;
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

	public void setLandNm(String landNm) {
		this.landNm = landNm;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setMajorType(String majorType) {
		this.majorType = majorType;
	}

	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}

	public void setPlanQuantities(String planQuantities) {
		this.planQuantities = planQuantities;
	}

	public void setPlanBudget(Double planBudget) {
		this.planBudget = planBudget;
	}

	public void setResponsibleUnit(String responsibleUnit) {
		this.responsibleUnit = responsibleUnit;
	}

	public void setConstructionBudget(String constructionBudget) {
		this.constructionBudget = constructionBudget;
	}

	public void setIsReplace(Integer isReplace) {
		this.isReplace = isReplace;
	}

	public void setReplaceUnit(String replaceUnit) {
		this.replaceUnit = replaceUnit;
	}

	public void setDesignUnit(String designUnit) {
		this.designUnit = designUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public void setSupervisorUnit(String supervisorUnit) {
		this.supervisorUnit = supervisorUnit;
	}

	public void setPlanOwnershipNumber(String planOwnershipNumber) {
		this.planOwnershipNumber = planOwnershipNumber;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public void setProjectOwnershipNumber(String projectOwnershipNumber) {
		this.projectOwnershipNumber = projectOwnershipNumber;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
