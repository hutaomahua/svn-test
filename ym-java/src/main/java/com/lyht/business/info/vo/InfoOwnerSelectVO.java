package com.lyht.business.info.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "权属人--下拉框（含身份证）")
public interface InfoOwnerSelectVO{
	
	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	Integer getId();
	@ApiModelProperty(value = "内码")
	String getNm();
	@ApiModelProperty(value = "姓名（含身份证）")
	String getName();
	@ApiModelProperty(value = "身份证")
	String getIdCard();
	@ApiModelProperty(value = "阶段")
	String getStage();
	
}