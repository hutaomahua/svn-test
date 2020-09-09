package com.lyht.business.info.vo;

import java.util.Date;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "户籍导入对象")
public class InfoHouseholdRegisterImportVO {

	@ApiModelProperty("乡")
	private String country;
	
	@ApiModelProperty("村")
	private String village;
	
	@ApiModelProperty("组")
	private String groups;

	@ApiModelProperty("权属人编码")
	private String ownerId;

	@ApiModelProperty("姓名")
	private String name;

	@ApiModelProperty("与户主关系")
	private String masterRelationship;

	@ApiModelProperty("性别")
	private String gender;

	@ApiModelProperty("出生年月(格式：yyyy-MM-dd HH:mm:ss)")
	private Date birthday;

	@ApiModelProperty("身份证号")
	private String idCard;

	@ApiModelProperty("户口性质")
	private String accountCharacter;

	@ApiModelProperty("户口类别")
	private String accountType;

	@ApiModelProperty("民族")
	private String national;

	@ApiModelProperty("文化程度")
	private String educationLevel;

	@ApiModelProperty("居住情况（居住）")
	private String isLiving;
	
	@ApiModelProperty("居住情况（未居住）")
	private String unLiving;

	@ApiModelProperty("本户地址")
	private String homeAddress;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMasterRelationship() {
		return masterRelationship;
	}

	public void setMasterRelationship(String masterRelationship) {
		this.masterRelationship = masterRelationship;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAccountCharacter() {
		return accountCharacter;
	}

	public void setAccountCharacter(String accountCharacter) {
		this.accountCharacter = accountCharacter;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getIsLiving() {
		return isLiving;
	}

	public void setIsLiving(String isLiving) {
		this.isLiving = isLiving;
	}

	public String getUnLiving() {
		return unLiving;
	}

	public void setUnLiving(String unLiving) {
		this.unLiving = unLiving;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}