package com.lyht.business.engineering.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(value = "临时用地复垦实施信息表")
public interface EngineeringReclaimInfoVO {

	@ApiModelProperty(value = "唯一id")
	Integer getId();

	@ApiModelProperty(value = "唯一nm")
	String getNm();

	@ApiModelProperty(value = "所属范围名称")
	String getAreaName();

	@ApiModelProperty(value = "专业类别名称")
	String getMagorName();

	@ApiModelProperty(value = "安置类型名称")
	String getLandName();

	@ApiModelProperty(value = "行政区域")
	String getDiming();

	@ApiModelProperty(value = "地块编号")
	String getLandNm();

	@ApiModelProperty(value = "行政区")
	String getRegion();

	@ApiModelProperty(value = "行政区域全称")
	String getMergerName();
	@ApiModelProperty(value = "专业大类")
	String getMajorType();

	@ApiModelProperty(value = "所属区域")
	String getAreaNm();

	@ApiModelProperty(value = "规划工程量")
	String getPlanQuantities();

	@ApiModelProperty(value = "规划概算(万元)")
	Double getPlanBudget();

	@ApiModelProperty(value = "进度评价")
	String getProgressEvaluate();

	@ApiModelProperty(value = "实施责任单位")
	String getResponsibleUnit();

	@ApiModelProperty(value = "施工图预算")
	String getConstructionBudget();

	@ApiModelProperty(value = "是否代建")
	Integer getIsReplace();

	@ApiModelProperty(value = "代建单位")
	String getReplaceUnit();

	@ApiModelProperty(value = "施工设计单位")
	String getDesignUnit();

	@ApiModelProperty(value = "施工单位")
	String getBuildUnit();

	@ApiModelProperty(value = "监理单位")
	String getSupervisorUnit();

	@ApiModelProperty(value = "关联规划项目权属编号")
	String getPlanOwnershipNumber();

	@ApiModelProperty(value = "实施新址坐标")
	String getCoordinate();

	@ApiModelProperty(value = "项目权属编号")
	String getProjectOwnershipNumber();

	@ApiModelProperty(value = "备注")
	String getRemark();


}
