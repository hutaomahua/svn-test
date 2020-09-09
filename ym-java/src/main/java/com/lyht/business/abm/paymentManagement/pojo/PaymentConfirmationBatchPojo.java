package com.lyht.business.abm.paymentManagement.pojo;

import io.swagger.annotations.ApiModelProperty;

public class PaymentConfirmationBatchPojo {
	
	@ApiModelProperty(value = "行政区域（模糊查询）")
	private String region;
	
	@ApiModelProperty(value = "批次号（模糊查询）")
	private String batchnum;
	
	@ApiModelProperty(value = "户主姓名（模糊查询）")
	private String ownername;

	@ApiModelProperty(value = "身份证（模糊查询）")
	private String idcard;

	@ApiModelProperty(value = "协议名称（模糊查询）")
	private String protocolname;

	@ApiModelProperty(value = "开始时间")
	private String st;

	@ApiModelProperty(value = "结束时间")
	private String et;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBatchnum() {
		return batchnum;
	}

	public void setBatchnum(String batchnum) {
		this.batchnum = batchnum;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getProtocolname() {
		return protocolname;
	}

	public void setProtocolname(String protocolname) {
		this.protocolname = protocolname;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
	}
	
}
