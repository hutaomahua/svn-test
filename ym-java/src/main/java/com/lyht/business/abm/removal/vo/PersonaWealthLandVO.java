package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PersonaWealthLandVO {
	
	@ApiModelProperty(value = "户主姓名")
	String getOwnerName();
	
	@ApiModelProperty(value = "户主nm")
	String getOwnerNm();
	
	@ApiModelProperty(value = "id")
	Integer getId();
	
	@ApiModelProperty(value = "nm")
	String getNm();
	
	@ApiModelProperty(value = "一级地类nm")
	String getTypeOne();
	
	@ApiModelProperty(value = "二级地类nm")
	String getTypeTwo();
	
	@ApiModelProperty(value = "三级地类nm")
	String getTypeThree();
	
	@ApiModelProperty(value = "一级地类name")
	String getOneName();
	
	@ApiModelProperty(value = "二级地类name")
	String getTwoName();
	
	@ApiModelProperty(value = "三级地类name")
	String getThreeName();
	
	@ApiModelProperty(value = "大类")
	String getAllTypeName();
	
	@ApiModelProperty(value = "大类nm")
	String getAllType();
	
	@ApiModelProperty(value = "面积")
	Double getArea();
	
	@ApiModelProperty(value = "征地范围名称")
	String getScopeName();
	
	@ApiModelProperty(value = "征地范围nm")
	String getScope();
	
	@ApiModelProperty(value ="地块编号")
	String getLandNm();
	
	@ApiModelProperty(value = "单位")
	String getUnit();
	
	@ApiModelProperty(value = "经度")
	String getLgtd();

	@ApiModelProperty(value = "纬度")
	String getLttd();

	@ApiModelProperty(value = "图幅号")
	String getInMap();
	
	@ApiModelProperty(value = "高程")
	String getAltd();
	
	@ApiModelProperty(value = "行政区全称")
	String getMergerName();
	
	@ApiModelProperty(value = "行政区编码")
	String getRegion();
	
	@ApiModelProperty(value = "备注")
	String getRemark();

	@ApiModelProperty("文件数量")
	Integer getFileCount();
	
}
