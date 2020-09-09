package com.lyht.business.info.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "企事业单位")
@Entity
@Table(name = "t_bank_crad_info")
public class BankCardInfoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nm")
	private String nm;

	@ApiModelProperty(value = "权属人内码")
	@Column(name = "owner_nm")
	private String ownerNm;

	@ApiModelProperty(value = "银行字典内码")
	@Column(name = "bank_nm")
	private String bankNm;

	@ApiModelProperty(value = "银行卡号")
	@Column(name = "bank_number")
	private String bankNumber;

	@ApiModelProperty(value = "银行预留手机号码")
	@Column(name = "bank_phone")
	private String bankPhone;

	@ApiModelProperty(value = "银行卡卡主")
	@Column(name = "bank_owner")
	private String bankOwner;

	@ApiModelProperty(value = "开户行")
	@Column(name = "open_bank")
	private String openBank;

	@ApiModelProperty(value = "备注")
	@Column(name = "remark")
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getBankNm() {
		return bankNm;
	}

	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getBankOwner() {
		return bankOwner;
	}

	public void setBankOwner(String bankOwner) {
		this.bankOwner = bankOwner;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
