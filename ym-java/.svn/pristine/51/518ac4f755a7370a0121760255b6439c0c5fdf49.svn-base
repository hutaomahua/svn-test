package com.lyht.business.info.entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "户籍表")
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "t_info_household_register")
@GenericGenerator(name = "jpa-guid", strategy = "guid")
public class InfoHouseholdRegisterEntity {

	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	@Id
	@GeneratedValue(generator = "jpa-guid")
	@Column(name = "id", nullable = false)
	private String id;

	@ApiModelProperty("行政区")
	@Column(name = "region")
	private String region;
	
	@ApiModelProperty("乡")
	@Column(name = "country")
	private String country;
	
	@ApiModelProperty("村")
	@Column(name = "village")
	private String village;
	
	@ApiModelProperty("组")
	@Column(name = "groups")
	private String groups;

	@ApiModelProperty("户主ID")
	@Column(name = "owner_id")
	private String ownerId;

	@ApiModelProperty("姓名")
	@Column(name = "name")
	private String name;

	@ApiModelProperty("与户主关系")
	@Column(name = "master_relationship")
	private String masterRelationship;

	@ApiModelProperty("性别")
	@Column(name = "gender")
	private String gender;

	@ApiModelProperty("出生年月(格式：yyyy-MM-dd HH:mm:ss)")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "birthday")
	private Date birthday;

	@ApiModelProperty("身份证号")
	@Column(name = "id_card")
	private String idCard;

	@ApiModelProperty("户口性质")
	@Column(name = "account_character")
	private String accountCharacter;

	@ApiModelProperty("户口类别")
	@Column(name = "account_type")
	private String accountType;

	@ApiModelProperty("民族")
	@Column(name = "national")
	private String national;

	@ApiModelProperty("年龄")
	@Column(name = "age")
	private Integer age;

	@ApiModelProperty("文化程度")
	@Column(name = "education_level")
	private String educationLevel;

	@ApiModelProperty("居住情况（1：居住；2：未居住）")
	@Column(name = "living_condition")
	private String livingCondition;

	@ApiModelProperty("本户地址")
	@Column(name = "home_address")
	private String homeAddress;

	@ApiModelProperty("数据状态")
	@Column(name = "status")
	private String status;

	@ApiModelProperty(value = "创建时间")
	@CreatedDate
	@Column(name = "create_time", updatable = false)
	private Date createTime;
	@ApiModelProperty(value = "创建用户")
	@CreatedBy
	@Column(name = "create_user", updatable = false)
	private String createUser;
	@ApiModelProperty(value = "修改时间")
	@LastModifiedDate
	@Column(name = "modify_time")
	private Date modifyTime;
	@ApiModelProperty(value = "修改用户")
	@LastModifiedBy
	@Column(name = "modify_user")
	private String modifyUser;

	@ApiModelProperty("备注")
	@Column(name = "remark")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getLivingCondition() {
		return livingCondition;
	}

	public void setLivingCondition(String livingCondition) {
		this.livingCondition = livingCondition;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}