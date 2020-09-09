package com.lyht.business.doc.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "t_doc_info")
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "政策法规/政府档案/业主档案/设代档案/综合监理档案/独立评估档案")
public class DocInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 内码
	 */
	@Column(name = "nm")
	private String nm;
	/**
	 * 档案类型
	 */
	@Column(name = "d_type")
	private String dType;
	/**
	 * 档案编号
	 */
	@Column(name = "d_code")
	private String dCode;
	/**
	 * 档案目录分类
	 */
	@Column(name = "d_directory_type")
	private String dDirectoryType;
	/**
	 * 档案名称
	 */
	@Column(name = "d_name")
	private String dName;
	/**
	 * 责任部门
	 */
	@Column(name = "dept_nm")
	private String deptNm;
	/**
	 * 归档人
	 */
	@CreatedBy
	@Column(name = "staff_nm", updatable = false)
	private String staffNm;
	/**
	 * 文号
	 */
	@Column(name = "doc_number")
	private String docNumber;
	/**
	 * 来源
	 */
	@Column(name = "source")
	private String source;

	/**
	 * 归档时间
	 */
	@ApiModelProperty(value = "创建时间")
	@CreationTimestamp
	@Column(name = "pigeonhole_date", updatable = false)
	private Date pigeonholeDate;
	/**
	 * 最后更新时间
	 */
	@ApiModelProperty(value = "修改时间")
	@UpdateTimestamp
	@Column(name = "last_time", insertable = false, updatable = true)
	private Date lastTime;
	/**
	 * 行政区
	 */
	@ApiModelProperty(value = "行政区")
	@Column(name = "region")
	private String region;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 状态
	 */
	@Column(name = "flag")
	private Integer flag;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getdType() {
		return dType;
	}

	public String getdCode() {
		return dCode;
	}

	public String getdDirectoryType() {
		return dDirectoryType;
	}

	public String getdName() {
		return dName;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public String getStaffNm() {
		return staffNm;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public String getSource() {
		return source;
	}

	public Date getPigeonholeDate() {
		return pigeonholeDate;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public String getRegion() {
		return region;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setdType(String dType) {
		this.dType = dType;
	}

	public void setdCode(String dCode) {
		this.dCode = dCode;
	}

	public void setdDirectoryType(String dDirectoryType) {
		this.dDirectoryType = dDirectoryType;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public void setStaffNm(String staffNm) {
		this.staffNm = staffNm;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setPigeonholeDate(Date pigeonholeDate) {
		this.pigeonholeDate = pigeonholeDate;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}