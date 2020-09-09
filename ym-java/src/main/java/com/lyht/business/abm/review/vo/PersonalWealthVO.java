package com.lyht.business.abm.review.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PersonalWealthVO {
	@ApiModelProperty(value = "权属人id")
	Integer getId();

	@ApiModelProperty(value = "权属人nm")
	String getNm();

	@ApiModelProperty(value = "行政区")
	String getMergerName();
	
	@ApiModelProperty(value = "行政区 region")
	String getRegion();

	@ApiModelProperty(value = "权属人姓名")
	String getName();

	@ApiModelProperty(value = "身份证号")
	String getIdCard();

	@ApiModelProperty(value = "民族")
	String getNational();

	@ApiModelProperty(value = "户口类型")
	String getHouseholdsType();

	@ApiModelProperty(value = "移民人口")
	String getIPopulation();

	@ApiModelProperty(value = "征地范围")
	String getScope();
	
	@ApiModelProperty(value = "征地范围name")
	String getScopeName();

	@ApiModelProperty(value = "复核状态变更状态 复核状态:-2：拒绝，-1：驳回，0：未发起，1：复核中，2：复核通过\r\n" + 
			"            变更状态 :3：发起变更，4：变更通过，-3：驳回，-4：拒绝\r\n")
	Integer getReviewState();
	
	@ApiModelProperty(value = "公示状态 -1：待公式， 0：未公示， ")
	Integer getGsState();

	@ApiModelProperty(value = "复核申请记录id")
	Integer getReviewId();

	@ApiModelProperty(value = "复核流程id")
	String getReviewProcessId();

	@ApiModelProperty(value = "变更申请id")
	String getChangeProcessId();
	
	@ApiModelProperty(value = "业务数据nm")
	String getMasterNm();
	
	@ApiModelProperty(value = "分户状态 流程（英文）")
	String getSplitStatus();
	
	@ApiModelProperty(value = "分户状态 流程（中文）")
	String getSplitCnStatus();
	
	@ApiModelProperty(value = "分户状态")
	String getSplitHouseholdState();
	
	@ApiModelProperty(value = "协议签订状态")
	Integer getProtocolState();
	
	@ApiModelProperty(value = "协议签订开始状态  0：未开始   1：已开始")
	Integer getProtocolStatus();
	
	@ApiModelProperty(value = "复核次数是否达到上限")
	Boolean getWhether();
	
	@ApiModelProperty(value = "上限次数")
	Integer getTimeCaps();
}
