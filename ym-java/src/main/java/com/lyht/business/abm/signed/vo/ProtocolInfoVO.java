package com.lyht.business.abm.signed.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public interface ProtocolInfoVO {

	@ApiModelProperty(value = "id")
	Integer getId();
	@ApiModelProperty(value = "nm")
	String getNm();
	@ApiModelProperty(value = "协议编号")
	String getProtocolCode();
	@ApiModelProperty(value = "协议名称")
	String getProtocolName();
	@ApiModelProperty(value = "协议范围")
	String getProtocolArea();
	@ApiModelProperty(value = "协议金额（元）")
	Double getProtocolAmount();
	@ApiModelProperty(value = "签订时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	@JsonFormat(pattern = "YYYY-MM-dd", timezone = "GMT+8")
	Date getCompleteTime();
	@ApiModelProperty(value = "协议附件")
	String getFileName();
	@ApiModelProperty(value = "用来判断协议类别 0:补偿协议,1:资金代管协议")
	Integer getFlag();
	@ApiModelProperty(value = "协议完成状态 0 未完成 1 完成")
	Integer getState();
	@ApiModelProperty(value = "资金代管协议可编辑内容 ")
	String getContent();
	@ApiModelProperty(value = "是否资金代管")
	Integer getIsEscrow();
	@ApiModelProperty(value = "附件URL")
	String getFileUrl();
	@ApiModelProperty(value = "流程处理状态")
	String getStatus();
	@ApiModelProperty(value = "附件nm")
	String getFileNm();
	
	@ApiModelProperty(value = "附件数量")
	Integer getFileCount();
}
