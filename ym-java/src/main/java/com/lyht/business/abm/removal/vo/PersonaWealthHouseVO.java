package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PersonaWealthHouseVO {

	@ApiModelProperty(value = "户主姓名")
	String getOwnerName();

	@ApiModelProperty(value = "户主nm")
	String getOwnerNm();

	@ApiModelProperty(value = "id")
	Integer getId();

	@ApiModelProperty(value = "nm")
	String getNm();

	@ApiModelProperty(value = "面积")
	Double getArea();
	
	@ApiModelProperty(value = "征地范围名称")
	String getScopeName();

	@ApiModelProperty(value = "征地范围nm")
	String getScope();

	@ApiModelProperty(value = "(项目)id")
	String getProjectNm();

	@ApiModelProperty(value = "(项目)")
	String getProjectName();

	@ApiModelProperty(value = "用途")
	String getPurpose();

	@ApiModelProperty(value = "栋数")
	Integer getHouseNumber();

	@ApiModelProperty(value = "结构类型")
	String getStructureType();

	@ApiModelProperty(value = "层数")
	Integer getLayerNumber();
	
	@ApiModelProperty(value = "层高")
	Double getLayerHeight();
	
	@ApiModelProperty(value = "形状")
	String getShape();
	
	@ApiModelProperty(value = "地址")
	String getAddress();
	
	@ApiModelProperty(value = "长")
	Double getLongs();
	
	@ApiModelProperty(value = "宽")
	Double getWidth();
	
	@ApiModelProperty(value = "装修等级")
	String getDecorateGrade();
	
	@ApiModelProperty(value = "道路里程")
	String getRoadMileage();
	
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
