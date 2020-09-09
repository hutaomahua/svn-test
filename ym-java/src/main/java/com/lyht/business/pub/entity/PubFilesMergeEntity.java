package com.lyht.business.pub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "pub_files_merge")
@GenericGenerator(name = "jpa-guid", strategy = "guid")
public class PubFilesMergeEntity implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * GUID主键
	 */
	@Id
	@GeneratedValue(generator = "jpa-guid")
	@Column(name = "id", nullable = false)
	private String id;
	
	@ApiModelProperty("附件表主键")
	@Column(name = "file_nm")
	private String fileNm;
	
	@ApiModelProperty("附件关联表")
	@Column(name = "table_name")
	private String tableName;
	
	@ApiModelProperty("附件关联表主键")
	@Column(name = "table_pk_column")
	private String tablePkColumn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTablePkColumn() {
		return tablePkColumn;
	}

	public void setTablePkColumn(String tablePkColumn) {
		this.tablePkColumn = tablePkColumn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
