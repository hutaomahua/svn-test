package com.lyht.business.engineering.entity;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "集镇新址建设实施信息表")
@Entity
@Table(name = "t_engineering_new_town")
@EntityListeners(AuditingEntityListener.class)
public class EngineeringNewTown implements Serializable {

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

	@ApiModelProperty(value = "所属范围")
	@Column(name = "area_nm")
	private String areaNm;

	@ApiModelProperty(value = "专业类别")
	@Column(name = "major_type")
	private String majorType;

	@ApiModelProperty(value = "行政区")
	private String region;

	@ApiModelProperty(value = "集镇名称")
	@Column(name = "town_name")
	private String townName;

	@ApiModelProperty(value = "安置类型")
	@Column(name = "set_type")
	private String setType;

	@ApiModelProperty(value = "规划安置规模")
	@Column(name = "project_place_scale")
	private String projectPlaceScale;

	@ApiModelProperty(value = "规划概算(万元)")
	@Column(name = "project_budget")
	private Double projectBudget;

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

	@ApiModelProperty(value = "滞后原因")
	@Column(name = "lag_reason")
	private String lagReason;

	@ApiModelProperty(value = "处理人")
	@Column(name = "dealing_staff")
	private String dealingStaff;

	@ApiModelProperty(value = "创建人")
	@Column(name = "create_staff")
	@CreatedBy
	private String createStaff;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time")
	@CreatedDate
	private Date createTime;

	@ApiModelProperty(value = "修改人")
	@Column(name = "update_staff")
	@LastModifiedBy
	private String updateStaff;

	@ApiModelProperty(value = "修改时间")
	@Column(name = "update_time")
	@LastModifiedDate
	private Date updateTime;


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
		return projectBudget;
	}

	public void setProjectBudget(Double projectBudget) {
		this.projectBudget = projectBudget;
	}

	public String getResponsibleUnit() {
		return responsibleUnit;
	}

	public void setResponsibleUnit(String responsibleUnit) {
		this.responsibleUnit = responsibleUnit;
	}

	public String getConstructionBudget() {
		return constructionBudget;
	}

	public void setConstructionBudget(String constructionBudget) {
		this.constructionBudget = constructionBudget;
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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
