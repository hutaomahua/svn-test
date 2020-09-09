package com.lyht.business.doc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_doc_immigrant")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "移民档案")
public class DocImmigrant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id")
	private Integer id;
	/**
	 * 内码
	 */
	@Column(name = "nm")
	@ApiModelProperty(value = "nm")
	private String nm;
	
	/**
	 * 档案目录分类
	 */
	@Column(name = "d_directory_type")
	@ApiModelProperty(value  = "档案目录分类")
	private String dDirectoryType;
	
	/**
	 *行政区 
	 */
	@ApiModelProperty(value = "行政区")
	private String region;
	
	/**
	 * 档案编码
	 */
	@ApiModelProperty(value = "档案编码")
	@Column(name = "d_number")
	private String dNumber;
	
	/**
	 * 档案名称
	 */
	@ApiModelProperty(value = "档案名称")
	@Column(name = "d_name")
	private String dName;
	
	/**
	 * 归档人
	 */
	@ApiModelProperty(value = "归档人")
	@Column(name = "staff_nm")
	private String staffNm;
	
	/**
	 * 归档时间
	 */
	@ApiModelProperty(value = "归档时间")
	@Column(name = "pigeonhole_date")
	@CreatedDate
	private Date pigeonholeDate;
	
	/**
	 * 关联信息表
	 */
	@ApiModelProperty(value = "关联信息表")
	@Column(name = "info_table")
	private String infoTable;
	
	/**
	 * 关联主键值
	 */
	@ApiModelProperty(value = "关联主键值")
	@Column(name = "info_id")
	private Integer infoId;
	
	/**
	 * 移民档案类型（权属人/企事业单位）
	 */
	@ApiModelProperty(value ="移民档案类型（权属人/企事业单位）")
	@Column(name = "object_nm")
	private String objectNm;
	
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private String state;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getdDirectoryType() {
		return dDirectoryType;
	}

	public String getRegion() {
		return region;
	}

	public String getdNumber() {
		return dNumber;
	}

	public String getdName() {
		return dName;
	}

	public String getStaffNm() {
		return staffNm;
	}

	public Date getPigeonholeDate() {
		return pigeonholeDate;
	}

	public String getInfoTable() {
		return infoTable;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public String getObjectNm() {
		return objectNm;
	}

	public String getRemark() {
		return remark;
	}

	public String getState() {
		return state;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setdDirectoryType(String dDirectoryType) {
		this.dDirectoryType = dDirectoryType;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setdNumber(String dNumber) {
		this.dNumber = dNumber;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public void setStaffNm(String staffNm) {
		this.staffNm = staffNm;
	}

	public void setPigeonholeDate(Date pigeonholeDate) {
		this.pigeonholeDate = pigeonholeDate;
	}

	public void setInfoTable(String infoTable) {
		this.infoTable = infoTable;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public void setObjectNm(String objectNm) {
		this.objectNm = objectNm;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
