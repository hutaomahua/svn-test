package com.lyht.business.engineering.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public interface EngineeringEvaluateVO {

	@ApiModelProperty(value = "编号")
	public Integer getId();

	@ApiModelProperty(value = "唯一nm")
	public String getNm();

	@ApiModelProperty(value = "工程类项目内码")
	public String getEngineeringNm();

	@ApiModelProperty(value = "工程类项目类型")
	public Integer getEngineeringType();

	@ApiModelProperty(value = "总体形象进度nm")
	public String getEnginProgressNm();

	@ApiModelProperty(value = "总体形象进度名称")
	public String getEnginProgressName();

	@ApiModelProperty(value = "进度评价")
	public String getProgressEvaluate();

	@ApiModelProperty(value = "实施状态")
	public String getImplementStatus();
	
	@ApiModelProperty(value = "实施012状态")
	public String getStatus();
	
	@ApiModelProperty(value = "已安置移民")
	public Integer getSettledImmigrants();

	@ApiModelProperty(value = "未安置移民")
	public Integer getUnsettledImmigrants();

	@ApiModelProperty(value = "实施信息截止年月")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getImplementInfoDate();

	@ApiModelProperty(value = "已使用资金（万元）")
	public Double getSpentFunds();

	@ApiModelProperty(value = "备注")
	public String getRemark();

	@ApiModelProperty(value = "创建人")
	public String getCreateStaff();

	@ApiModelProperty(value = "创建人名字")
	public String getCreateStaffName();

	@ApiModelProperty(value = "创建时间")
	public Date getCreateTime();
	
	@ApiModelProperty(value = "实施信息截止年月 开始时间 (用作查询条件)")
	public Date getStartTime();
	
	@ApiModelProperty(value = "实施信息截止年月 结束时间(用作查询条件)")
	public Date getEndTime();

}
