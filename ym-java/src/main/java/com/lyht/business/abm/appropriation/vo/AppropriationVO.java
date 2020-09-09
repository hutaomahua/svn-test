package com.lyht.business.abm.appropriation.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public interface AppropriationVO {

	@ApiModelProperty(value = "编号")
	public Integer getId();

	@ApiModelProperty(value = "唯一nm")
	public String getNm();

	@ApiModelProperty(value = "兑付申请编号")
	public String getApplicationNo() ;

	@ApiModelProperty(value = "合同/协议nm")
	public String getProtocolNm();

	@ApiModelProperty(value = "合同/协议编号")
	public String getProtocolNo();

	@ApiModelProperty(value = "合同/协议名称")
	public String getProtocolName();

	@ApiModelProperty(value = "申请部门")
	public String getDeptNm();

	@ApiModelProperty(value = "业务申办人")
	public String getApplicant();

	@ApiModelProperty(value = "合同金额")
	public Double getProtocolAmount();

	@ApiModelProperty(value = "累计已支付金额")
	public Double getPaidAmount();

	@ApiModelProperty(value = "未支付金额")
	public Double getUnpaidAmount();

	@ApiModelProperty(value = "申请支付金额")
	public Double getApplyAmount();

	@ApiModelProperty(value = "收款单位（人）")
	public String getReceivablesObject();

	@ApiModelProperty(value = "款项类型")
	public String getMoneyType();

	@ApiModelProperty(value = "款项类型名称")
	public String getMoneyTypeName();

	@ApiModelProperty(value = "申请时间")
	@JsonFormat(pattern = "YYYY-MM-dd",timezone = "GMT+8")
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	public Date getApplyTime();

	@ApiModelProperty(value = "开户行nm")
	public String getOpeningBank();

	@ApiModelProperty(value = "开户行名称")
	public String getOpeningBankName();

	@ApiModelProperty(value = "银行卡号")
	public String getCardNumber();

	@ApiModelProperty(value = "支付日期")
	@JsonFormat(pattern = "YYYY-MM-dd",timezone = "GMT+8")
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	public Date getPaidTime();

	@ApiModelProperty(value = "支付方式")
	public String getPaidMode();
	
	@ApiModelProperty(value = "支付方式名称")
	public String getPaidModeName();

	@ApiModelProperty(value = "支付款项")
	public String getPaidMoney();

	@ApiModelProperty(value = "申请人")
	public String getApplyPeople();

	@ApiModelProperty(value = "支付事由及情况说明")
	public String getPaidCause();

	@ApiModelProperty(value = "备注")
	public String getRemark();

	@ApiModelProperty(value = "状态")
	public Integer getFlag();
 
	@ApiModelProperty(value = "开始时间（申请时间）")
	public String getStartTime();
	
	@ApiModelProperty(value = "结束时间（申请时间）")
	public String getEndTime();

}
