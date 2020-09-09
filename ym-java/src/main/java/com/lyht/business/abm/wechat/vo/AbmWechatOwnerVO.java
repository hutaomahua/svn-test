package com.lyht.business.abm.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "（微信小程序）权属人")
public interface AbmWechatOwnerVO {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	Integer getId();

	@ApiModelProperty(value = "唯一内码（新增不填，修改必填）")
	String getNm();

	@ApiModelProperty("行政区--编码")
	String getCityCode();

	@ApiModelProperty("征地范围--编码")
	String getScope();

	@ApiModelProperty("姓名")
	String getName();

	@ApiModelProperty("身份证号")
	String getIdCard();

	@ApiModelProperty("经度")
	String getLgtd();

	@ApiModelProperty("纬度")
	String getLttd();

	@ApiModelProperty("高程")
	String getAltd();

	@ApiModelProperty("图幅号")
	String getInMap();

	@ApiModelProperty("行政区--全称")
	String getMergerName();

	@ApiModelProperty("征地范围--值")
	String getScopeValue();

}