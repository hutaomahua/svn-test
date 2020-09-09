package com.lyht.business.doc.pojo;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

public class DocDistributeVO {

	private Integer id;
	
	private String nm;
	
	private String sourceTable;
	
	private String dataNm;
	
	private String dName;
	
	private String docType;
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8" )
	private Date issueTime;
	
	private String staffName;
	
	private String staffNm;
	
	private String issueName;
	
	private String issueNm;

	public String getDataNm() {
		return dataNm;
	}

	public void setDataNm(String dataNm) {
		this.dataNm = dataNm;
	}

	public Integer getId() {
		return id;
	}

	public String getNm() {
		return nm;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public String getdName() {
		return dName;
	}

	public String getDocType() {
		return docType;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public String getStaffName() {
		return staffName;
	}

	public String getStaffNm() {
		return staffNm;
	}

	public String getIssueName() {
		return issueName;
	}

	public String getIssueNm() {
		return issueNm;
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

	public void setdName(String dName) {
		this.dName = dName;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setStaffNm(String staffNm) {
		this.staffNm = staffNm;
	}

	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}

	public void setIssueNm(String issueNm) {
		this.issueNm = issueNm;
	}
	
}
