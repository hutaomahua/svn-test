package com.lyht.business.abm.paymentManagement.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PaymentConfirmationVO {
	
	@ApiModelProperty(value = "记录内码")
	String getNm();

	@ApiModelProperty(value = "权属人")
	String getOwner();
	
	@ApiModelProperty(value = "权属人内码")
	String getOwnernm();
	
	@ApiModelProperty(value = "身份证号码")
	String getIdcard();
	
	@ApiModelProperty(value = "收款人")
	String getPayee();
	
	@ApiModelProperty(value = "银行卡号")
	String getBankcard();
	
	@ApiModelProperty(value = "开户行")
	String getDepositbank();
	
	@ApiModelProperty(value = "协议编号")
	String getProtocolcode();
	
	@ApiModelProperty(value = "协议名称")
	String getProtocolname();
	
	@ApiModelProperty(value = "协议类型 1资金代管协议 0补偿协议")
	String getProtocoltype();
	
	@ApiModelProperty(value = "协议可申请金额（元）")
	String getProtocolamount();
	
	@ApiModelProperty(value = "申请金额（元）")
	String getApplyamount();
	
	@ApiModelProperty(value = "申请批次")
	String getApplybatch();
	
	@ApiModelProperty(value = "业务申办人")
	String getProposer();
	
	@ApiModelProperty(value = "申请时间")
	String getApplytime();
	
}
