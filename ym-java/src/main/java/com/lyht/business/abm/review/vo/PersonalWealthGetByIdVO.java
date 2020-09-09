package com.lyht.business.abm.review.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PersonalWealthGetByIdVO {

	@ApiModelProperty(value = "权属人")
	String getName();

	@ApiModelProperty(value = "权属人nm")
	String getNm();

	@ApiModelProperty(value = "身份证号")
	String getIdCard();

	@ApiModelProperty(value = "复核申请人")
	String getReviewer();

	@ApiModelProperty(value = "复核申请编号")
	String getApplicationNumber();

	@ApiModelProperty(value = "复核项目")
	String getReviewProject();

	@ApiModelProperty(value = "征地范围")
	String getScope();

	@ApiModelProperty(value = "复核申请原由")
	String getReviewReason();
	
	@ApiModelProperty(value = "复核申请附件名称")
	String getReviewFileName();
	
	@ApiModelProperty(value = "复核申请附件地址URL")
	String getReviewFileUrl();
	
	@ApiModelProperty(value = "变更申请附件名称")
	String getChangeFileName();
	
	@ApiModelProperty(value = "变更申请附件地址URL")
	String getChangeFileUrl();

}
