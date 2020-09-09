package com.lyht.business.abm.paymentManagement.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PubFilesVO {

	@ApiModelProperty(value = "主键id")
	String getId();
	
	@ApiModelProperty(value = "记录内码")
	String getNm();
	
	@ApiModelProperty(value = "关联表名")
	String getTableName();
	
	@ApiModelProperty(value = "关联表内码")
	String getTablePkColumn();
	
	@ApiModelProperty(value = "文件名")
	String getFileName();
	
	@ApiModelProperty(value = "文件类型")
	String getFileType();
	
	@ApiModelProperty(value = "文件大小")
	String getFileSize();
	
	@ApiModelProperty(value = "上传人")
	String getUploadStaffName();
	
	@ApiModelProperty(value = "上传时间")
	String getUploadTime();
	
	@ApiModelProperty(value = "文件路径")
	String getFileUrl();
	
}
