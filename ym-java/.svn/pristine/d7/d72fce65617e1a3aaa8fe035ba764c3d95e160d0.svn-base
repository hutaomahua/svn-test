package com.lyht.business.abm.paymentManagement.vo;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;

public class PaymentInfoVO {

	@ApiModelProperty(value = "户主信息")
	private OwnerInfoVO owner;
	
	@ApiModelProperty(value = "户主银行卡信息")
	private List<BankCardVO> bankCard;
	
	@ApiModelProperty(value = "协议信息")
	private List<Map<String, Object>> protocol;

	public OwnerInfoVO getOwner() {
		return owner;
	}

	public void setOwner(OwnerInfoVO owner) {
		this.owner = owner;
	}

	public List<BankCardVO> getBankCard() {
		return bankCard;
	}

	public void setBankCard(List<BankCardVO> bankCard) {
		this.bankCard = bankCard;
	}

	public List<Map<String, Object>> getProtocol() {
		return protocol;
	}

	public void setProtocol(List<Map<String, Object>> protocol) {
		this.protocol = protocol;
	}

}
