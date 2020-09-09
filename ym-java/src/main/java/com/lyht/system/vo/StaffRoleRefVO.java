package com.lyht.system.vo;

import io.swagger.annotations.ApiModelProperty;

public interface StaffRoleRefVO {

	@ApiModelProperty(value = "nm")
	String getNm();
	
	@ApiModelProperty(value = "code")
	String getCode();
	
	@ApiModelProperty(value = "名称")
	String getName();
	
}
