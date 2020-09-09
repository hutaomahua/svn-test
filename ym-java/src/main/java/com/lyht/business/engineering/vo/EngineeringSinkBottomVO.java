package com.lyht.business.engineering.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public interface EngineeringSinkBottomVO {
	@ApiModelProperty(value = "唯一id")
	public Integer getId();

	@ApiModelProperty(value = "唯一nm")
	public String getNm();

	@ApiModelProperty(value = "一级类nm")
	public String getOneLevel();

	@ApiModelProperty(value = "一级类名称")
	public String getOneLevelName();

	@ApiModelProperty(value = "二级类nm")
	public String getTwoLevel();

	@ApiModelProperty(value = "二级类名称")
	public String getTwoLevelName();

	@ApiModelProperty(value = "三级类nm")
	public String getThreeLevel();

	@ApiModelProperty(value = "三级类名称")
	public String getThreeLevelName();

	@ApiModelProperty(value = "项目nm")
	public String getProjectNm();

	@ApiModelProperty(value = "项目名称(项目nm)")
	public String getProjectName();

	@ApiModelProperty(value = "行政区cityCode")
	public String getRegion();

	@ApiModelProperty(value = "行政区名称")
	public String getRegionName();

	@ApiModelProperty(value = "行政区全名称")
	public String getMergerName();

	@ApiModelProperty(value = "规划工程量")
	public String getPlanQuantities();

	@ApiModelProperty(value = "规划概算(万元)")
	public Double getPlanBudget();

	@ApiModelProperty(value = "实施责任单位")
	public String getResponsibleUnit();

	@ApiModelProperty(value = "施工图预算")
	public String getConstructionBudget();

	@ApiModelProperty(value = "是否代建")
	public String getIsReplace();
	
	@ApiModelProperty(value = "是否代建01")
	public Integer getReplace();

	@ApiModelProperty(value = "代建单位")
	public String getReplaceUnit();

	@ApiModelProperty(value = "施工设计单位")
	public String getDesignUnit();

	@ApiModelProperty(value = "施工单位")
	public String getBuildUnit();

	@ApiModelProperty(value = "监理单位")
	public String getSupervisorUnit();

	@ApiModelProperty(value = "关联规划项目权属编号")
	public String getPlanOwnershipNumber();

	@ApiModelProperty(value = "实施新址坐标")
	public String getCoordinate();

	@ApiModelProperty(value = "项目权属编号")
	public String getProjectOwnershipNumber();

	@ApiModelProperty(value = "备注")
	public String getRemark();

	@ApiModelProperty(value = "创建人nm")
	public String getCreateStaff();

	@ApiModelProperty(value = "创建人名字")
	public String getCreateStaffName();

	@ApiModelProperty(value = "创建时间")
	public Date getCreateTime();

	@ApiModelProperty(value = "修改人nm")
	public String getUpdateStaff();

	@ApiModelProperty(value = "修改人名字")
	public String getUpdateStaffName();

	@ApiModelProperty(value = "修改时间")
	public Date getUpdateTime();
	
	@ApiModelProperty(value = "工程总体形象进度")
	public String getEnginProgressName();
	
	@ApiModelProperty(value = "进度评价")
	public String getProgressEvaluate();
	
	@ApiModelProperty(value = "实施状态")
	public String getImplementStatus();
	
	@ApiModelProperty(value = "已使用资金（万元）")
	public Double getSpentFunds();
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "实时信息截止年月")
	public Date getImplementInfoDate();
	
	@ApiModelProperty(value = "附件数量")
	public Integer getFujian();
}
