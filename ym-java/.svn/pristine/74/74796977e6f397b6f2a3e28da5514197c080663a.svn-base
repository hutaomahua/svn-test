package com.lyht.business.abm.paymentManagement.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 *作者： 阎晓辉
 *脚本日期:2020年2月28日 19:42:41
 *说明: 
*/
@Entity
@Table(name = "v_protocol_finish")
@ApiModel(description = "已签订协议")
public class VProtocolFinish {

	@ApiModelProperty(value = "主键")
    @Id
    @Column(name = "id" , nullable = false )
	private String id;

	@ApiModelProperty(value = "协议nm")
	@Column(name = "nm")
	private String nm;

	@ApiModelProperty(value = "权属人nm")
	@Column(name = "ownerNm")
	private String ownerNm;

	@ApiModelProperty(value = "协议编号")
	@Column(name = "protocolCode")
	private String protocolCode;

	@ApiModelProperty(value = "协议名称")
	@Column(name = "protocolName")
	private String protocolName;

	@ApiModelProperty(value = "协议签订范围（协议签订项）")
	@Column(name = "protocolArea")
	private String protocolArea;

	@ApiModelProperty(value = "协议金额")
	@Column(name = "protocolAmount")
	private BigDecimal protocolAmount;
	
	//第一次发起申请展示时减去资金代管的协议金额
	@ApiModelProperty(value = "挪用金额")
	@Column(name = "protocolEscrow")
	private BigDecimal protocolEscrow;

	@ApiModelProperty(value = "协议类型（0：补偿协议  1：资金代管协议）")
	@Column(name = "protocolType")
	private Integer protocolType;
	
	@ApiModelProperty(value = "附件名称")
	@Column(name = "fileName")
	private String fileName;
	
	@ApiModelProperty(value = "附件服务器路径")
	@Column(name = "fileUrl")
	private String fileUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getProtocolArea() {
		return protocolArea;
	}

	public void setProtocolArea(String protocolArea) {
		this.protocolArea = protocolArea;
	}

	public BigDecimal getProtocolAmount() {
		return protocolAmount;
	}

	public void setProtocolAmount(BigDecimal protocolAmount) {
		this.protocolAmount = protocolAmount;
	}

	public BigDecimal getProtocolEscrow() {
		return protocolEscrow;
	}

	public void setProtocolEscrow(BigDecimal protocolEscrow) {
		this.protocolEscrow = protocolEscrow;
	}

	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
