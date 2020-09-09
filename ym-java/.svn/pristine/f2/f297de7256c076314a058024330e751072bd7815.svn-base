package com.lyht.business.pub.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 行政区域
 * 
 * @author hxl
 *
 */
@ApiModel(description = "行政区")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Entity
@Table(name = "pub_region")
public class PubRegionEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "唯一ID，修改必填")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty(value = "唯一内码，修改必填")
	private String nm;

	@ApiModelProperty(value = "行政区--编码（关联字段）")
	@Column(name = "city_code")
	private String cityCode;

	@ApiModelProperty(value = "父级id")
	@Column(name = "parent_code")
	private String parentCode;

	@ApiModelProperty(value = "行政区--名称")
	private String name;

	@ApiModelProperty(value = "行政区--全称")
	@Column(name = "merger_name")
	private String mergerName;

	@ApiModelProperty(value = "行政区--简称")
	@Column(name = "short_name")
	private String shortName;

	@ApiModelProperty(value = "行政区--简称（全）")
	@Column(name = "merger_short_name")
	private String mergerShortName;

	@ApiModelProperty(value = "行政区--级别")
	@Column(name = "level")
	private String level;

	@ApiModelProperty(value = "树的层级")
	@Column(name = "level_type")
	private Integer levelType;

	@ApiModelProperty(value = "电话号码")
	@Column(name = "telephone_code")
	private String telephoneCode;

	@ApiModelProperty(value = "邮编")
	@Column(name = "zip_code")
	private String zipCode;

	@ApiModelProperty(value = "行政区--名称（拼音）")
	@Column(name = "name_pinyin")
	private String namePinyin;

	@ApiModelProperty(value = "行政区--简称（拼音）")
	@Column(name = "name_jianpin")
	private String nameJianpin;

	@ApiModelProperty(value = "行政区--中心点")
	@Column(name = "center")
	private String center;

	@ApiModelProperty(value = "行政区--首字母")
	@Column(name = "name_first_char")
	private String nameFirstChar;

	@ApiModelProperty(value = "经度")
	@Column(name = "longitude")
	private String lng;

	@ApiModelProperty(value = "纬度")
	@Column(name = "latitude")
	private String lat;

	@ApiModelProperty(value = "状态")
	@Column(name = "status")
	private Integer status;

	@ApiModelProperty(value = "历史版本")
	@Column(name = "version")
	private String version;

	@ApiModelProperty(value = "创建时间")
	@CreationTimestamp
	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@ApiModelProperty(value = "修改时间")
	@UpdateTimestamp
	@Column(name = "update_time", insertable = false, updatable = true)
	private Date updateTime;
	
	@ApiModelProperty(value = "邮编")
	@Column(name = "region_code")
	private String regionCode;
	
	@ApiModelProperty(value = "行政区--子集")
	@Transient
	private List<PubRegionEntity> children;

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public List<PubRegionEntity> getChildren() {
		return children;
	}

	public void setChildren(List<PubRegionEntity> children) {
		this.children = children;
	}

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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMergerName() {
		return mergerName;
	}

	public void setMergerName(String mergerName) {
		this.mergerName = mergerName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMergerShortName() {
		return mergerShortName;
	}

	public void setMergerShortName(String mergerShortName) {
		this.mergerShortName = mergerShortName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getLevelType() {
		return levelType;
	}

	public void setLevelType(Integer levelType) {
		this.levelType = levelType;
	}

	public String getTelephoneCode() {
		return telephoneCode;
	}

	public void setTelephoneCode(String telephoneCode) {
		this.telephoneCode = telephoneCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	public String getNameJianpin() {
		return nameJianpin;
	}

	public void setNameJianpin(String nameJianpin) {
		this.nameJianpin = nameJianpin;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getNameFirstChar() {
		return nameFirstChar;
	}

	public void setNameFirstChar(String nameFirstChar) {
		this.nameFirstChar = nameFirstChar;
	}

	public String getLng() {
		return lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}