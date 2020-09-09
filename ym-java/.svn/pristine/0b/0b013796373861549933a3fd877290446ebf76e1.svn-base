package com.lyht.business.info.vo;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "房屋装修")
public interface InfoHouseholdRegisterVO {
	
	@ApiModelProperty(value = "唯一ID(新增不填，修改必填)")
	String getId();

	@ApiModelProperty("行政区")
	String getRegion();
	
	@ApiModelProperty("乡")
	String getCountry();
	
	@ApiModelProperty("村")
	String getVillage();
	
	@ApiModelProperty("组")
	String getGroups();

	@ApiModelProperty("权属人编码")
	String getOwnerId();

	@ApiModelProperty("姓名")
	String getName();

	@ApiModelProperty("与户主关系")
	String getMasterRelationship();

	@ApiModelProperty("性别")
	String getGender();

	@ApiModelProperty("出生年月")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	Date getBirthday();

	@ApiModelProperty("身份证号")
	String getIdCard();

	@ApiModelProperty("户口性质")
	String getAccountCharacter();

	@ApiModelProperty("户口类别")
	String getAccountType();

	@ApiModelProperty("民族")
	String getNational();

	@ApiModelProperty("年龄")
	Integer getAge();

	@ApiModelProperty("文化程度")
	String getEducationLevel();

	@ApiModelProperty("居住情况（1：居住；2：未居住）")
	String getLivingCondition();

	@ApiModelProperty("本户地址")
	String getHomeAddress();

	@ApiModelProperty("数据状态")
	String getStatus();

	@ApiModelProperty(value = "创建时间")
	Date getCreateTime();
	@ApiModelProperty(value = "创建用户")
	String getCreateUser();
	@ApiModelProperty(value = "修改时间")
	@Column(name = "modify_time")
	Date getModifyTime();
	@ApiModelProperty(value = "修改用户")
	String getModifyUser();

	@ApiModelProperty("行政区域全称-值")
	String getRegionValue();
	
	@ApiModelProperty("户主名称-值")
	String getOwnerName();
	
	@ApiModelProperty("户主身份证-值")
	String getOwnerIdCard();
	
	@ApiModelProperty("与户主关系-值")
	String getMasterRelationshipValue();
	
	@ApiModelProperty("户口性质-值")
	String getAccountCharacterValue();
	
	@ApiModelProperty("户口类型-值")
	String getAccountTypeValue();
	
	@ApiModelProperty("民族-值")
	String getNationalValue();
	
	@ApiModelProperty("文化程度-值")
	String getEducationLevelValue();

}