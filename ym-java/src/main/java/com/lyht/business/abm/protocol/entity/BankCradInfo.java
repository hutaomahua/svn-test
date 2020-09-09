package com.lyht.business.abm.protocol.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "银行卡信息表")
@Entity
@Table(name = "t_bank_crad_info")
public class BankCradInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ApiModelProperty(value = "唯一nm")
	private String nm;

	@ApiModelProperty(value = "权属人nm")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "银行")
	@Column(name = "bank_nm")
	private String bankNm;
	@ApiModelProperty(value = "卡号")
	@Column(name = "bank_number")
	private String bankNumber;
	@ApiModelProperty(value = "银行预留手机号")
	@Column(name = "bank_phone")
	private String bankPhone;
	@ApiModelProperty(value = "开户人")
	@Column(name = "bank_owner")
	private String bankOwner;
	@ApiModelProperty(value = "开户行")
	@Column(name = "open_bank")
	private String openBank;
	@ApiModelProperty(value = "备注")
	private String remark;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getOwnerNm() {
		return ownerNm;
	}

	public String getBankNm() {
		return bankNm;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public String getBankOwner() {
		return bankOwner;
	}

	public String getOpenBank() {
		return openBank;
	}

	public String getRemark() {
		return remark;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setOwnerNm(String ownerNm) {
		this.ownerNm = ownerNm;
	}

	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
