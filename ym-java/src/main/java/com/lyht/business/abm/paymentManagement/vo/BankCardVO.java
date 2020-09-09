package com.lyht.business.abm.paymentManagement.vo;

import io.swagger.annotations.ApiModelProperty;

public interface BankCardVO {
	
	@ApiModelProperty(value = "银行卡内码")
	String getBanknm();
	
	@ApiModelProperty(value = "银行卡号")
	String getBanknumber();
	
	@ApiModelProperty(value = "银行名称")
	String getBankname();
	
	@ApiModelProperty(value = "开户行")
	String getOpenbank();
	
	@ApiModelProperty(value = "银行卡卡主")
	String getBankowner();
	
	@ApiModelProperty(value = "银行预留手机号码")
	String getBankphone();
	
}
