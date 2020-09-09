package com.lyht.business.pub.entity;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "pub_files")
public class PubFilesEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	/**
	 * 唯一编号
	 */
	@Column(name = "nm")
	private String nm;
	/**
	 * 附件关联表
	 */
	@Column(name = "table_name")
	private String tableName;
	/**
	 * 附件关联表主键
	 */
	@Column(name = "table_pk_column")
	private String tablePkColumn;
	/**
	 * 附件名称
	 */
	@Column(name = "file_name")
	private String fileName;
	/**
	 * 附件类型
	 */
	@Column(name = "file_type")
	private String fileType;
	/**
	 * 附件大小
	 */
	@Column(name = "file_size")
	private String fileSize;
	/**
	 * 上传人
	 */
	@Column(name = "upload_staff_name")
	private String uploadStaffName;
	/**
	 * 上传时间
	 */
	@Column(name = "upload_time")
	private String uploadTime;
	/**
	 * 存储路径
	 */
	@Column(name = "file_url")
	private String fileUrl;
	
	/**
	 * 文件分类
	 */
	@ApiModelProperty(value = "文件分类")
	@Column(name = "subject")
	private String subject;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNm() {
		return this.nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTablePkColumn() {
		return this.tablePkColumn;
	}

	public void setTablePkColumn(String tablePkColumn) {
		this.tablePkColumn = tablePkColumn;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadStaffName() {
		return this.uploadStaffName;
	}

	public void setUploadStaffName(String uploadStaffName) {
		this.uploadStaffName = uploadStaffName;
	}

	public String getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}