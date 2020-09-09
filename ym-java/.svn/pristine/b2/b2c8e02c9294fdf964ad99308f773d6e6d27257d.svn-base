package com.lyht.business.abm.household.vo;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分户详情")
public interface AbmSplitHouseholdProcessVO {

	@ApiModelProperty("户主NM")
	String getOwnerNm();

	@ApiModelProperty("户主姓名")
	String getOwnerName();

	@ApiModelProperty("户主身份证")
	String getIdCard();

	@ApiModelProperty("民族")
	String getNational();

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("行政区--全称")
	String getMergerName();

	@ApiModelProperty(value = "家庭成员总数")
	Integer getPopulation();

	@ApiModelProperty(value = "房屋总面积")
	BigDecimal getHouseArea();

	@ApiModelProperty(value = "房屋装修总面积")
	BigDecimal getHouseDecorationArea();

	@ApiModelProperty(value = "土地总面积")
	BigDecimal getLandArea();

	@ApiModelProperty("分户申请-签字表附件--编码")
	String getSignFileNm();

	@ApiModelProperty("分户申请-签字表附件--名称")
	String getSignFileName();

	@ApiModelProperty("分户申请-签字表附件--类型")
	String getSignFileType();

	@ApiModelProperty("分户申请-签字表附件--url")
	String getSignFileUrl();

	@ApiModelProperty("分户申请-流程ID")
	String getApplyProcessId();

	@ApiModelProperty("分户申请-流程名称")
	String getApplyProcessName();

	@ApiModelProperty("分户申请-流程发起时间")
	Date getApplyProcessApplyTime();

	@ApiModelProperty("分户申请-流程状态")
	String getApplyProcessStatus();

	@ApiModelProperty("分户申请-流程中文状态")
	String getApplyProcessCnStatus();

	@ApiModelProperty("分户JSON数据")
	String getSplitJsonData();

	@ApiModelProperty("分户-流程ID")
	String getProcessId();

	@ApiModelProperty("分户-流程名称")
	String getProcessName();

	@ApiModelProperty("分户-流程发起时间")
	Date getProcessApplyTime();

	@ApiModelProperty("分户-流程状态")
	String getProcessStatus();

	@ApiModelProperty("分户-流程中文状态")
	String getProcessCnStatus();

	@ApiModelProperty("分户状态")
	String getSplitHouseholdState();

	@ApiModelProperty("备注")
	String getRemark();

}
