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

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "t_doc_distribute")
@Entity
@ApiModel(value = "档案分发表)")
@EntityListeners(AuditingEntityListener.class)
public class DocDistribute implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 唯一id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "id")
	private Integer id;
	/**
	 * 内码
	 */
	@ApiModelProperty(value="nm")
	private String nm;
	
	/**
	 * 来源表
	 */
	@ApiModelProperty(value = "来源表")
	@Column(name ="source_table")
	private String sourceTable;
	
	/**
	 * 分发数据内码
	 */
	@ApiModelProperty(value = "分发数据内码")
	@Column(name = "data_nm")
	private String dataNm;
	
	/**
	 * 接收人员
	 */
	@ApiModelProperty(value = "接收人员")
	@Column(name = "staff_nm")
	private String staffNm;
	
	/**
	 * 档案类型
	 */
	@ApiModelProperty(value = "档案类型")
	@Column(name = "doc_type")
	private String docType;
	
	/**
	 * 分发时间
	 */
	@CreatedDate
	@ApiModelProperty(value ="分发时间")
	@DateTimeFormat
	@Column(name = "issue_time")
	private Date issueTime;

	/**
	 * 分发人
	 */
	@CreatedBy
	@ApiModelProperty(value = "分发人")
	@Column(name = "issue_staff_nm")
	private String issueStaffNm;

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public String getDataNm() {
		return dataNm;
	}

	public String getStaffNm() {
		return staffNm;
	}

	public String getDocType() {
		return docType;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public String getIssueStaffNm() {
		return issueStaffNm;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public void setDataNm(String dataNm) {
		this.dataNm = dataNm;
	}

	public void setStaffNm(String staffNm) {
		this.staffNm = staffNm;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public void setIssueStaffNm(String issueStaffNm) {
		this.issueStaffNm = issueStaffNm;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
