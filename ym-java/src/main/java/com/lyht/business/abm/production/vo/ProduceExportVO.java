package com.lyht.business.abm.production.vo;

import io.swagger.annotations.ApiModelProperty;

public interface ProduceExportVO {
	
	@ApiModelProperty(value = "姓名")
	String getName();

	@ApiModelProperty(value = "户口类型")
	String getHouseholdsType();
	
	@ApiModelProperty(value = "与户主关系")
	String getMasterRelationship();
	
	@ApiModelProperty(value = "民族")
	String getNational();
	
	@ApiModelProperty(value = "性别")
	String getGender();
	
	@ApiModelProperty(value = "年龄")
	Integer getAge();
	
	@ApiModelProperty(value = "身份证号")
	String getIdCard();
	
	@ApiModelProperty(value = "指定生产安置人口")
	String getIsProduce();
	
}
