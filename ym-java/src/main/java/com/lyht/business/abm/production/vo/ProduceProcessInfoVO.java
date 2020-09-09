package com.lyht.business.abm.production.vo;

import io.swagger.annotations.ApiModelProperty;

public interface ProduceProcessInfoVO {

	@ApiModelProperty(value = "名字")
	String getName();

	@ApiModelProperty(value = "范围")
	String getScope();

	@ApiModelProperty(value = "身份证号")
	String getIdCard();

	@ApiModelProperty(value = "行政区")
	String getMergerName();

	@ApiModelProperty(value = "申请时间")
	String getCreateTime();

	@ApiModelProperty(value = "户口类型")
	String getHouseholds();

	@ApiModelProperty(value = "文件地址")
	String getFileUrl();

	@ApiModelProperty(value = "文件名称")
	String getFileName();
}
