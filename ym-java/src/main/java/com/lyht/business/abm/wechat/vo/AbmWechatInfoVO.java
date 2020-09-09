package com.lyht.business.abm.wechat.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "（微信小程序）指标信息")
public interface AbmWechatInfoVO {

	@ApiModelProperty(value = "项目分类")
	String getType();

	@ApiModelProperty("项目名称")
	String getProject();

	@ApiModelProperty("单位")
	String getUnit();

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("数量")
	BigDecimal getNum();

	@ApiModelProperty("数量（单位）")
	String getValue();

}