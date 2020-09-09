package com.lyht.business.abm.removal.vo;

import io.swagger.annotations.ApiModelProperty;

public interface PersonaWealthTreesVO {

	@ApiModelProperty(value = "户主姓名")
	String getOwnerName();
	
	@ApiModelProperty(value = "户主nm")
	String getOwnerNm();
	
	@ApiModelProperty(value = "id")
	Integer getId();
	
	@ApiModelProperty(value = "nm")
	String getNm();
	
	@ApiModelProperty(value = "类别(品种/项目)Id")
	String getProjectNm();
	
	@ApiModelProperty(value = "类别(品种/项目)")
	String getProjectName();
	
	@ApiModelProperty(value = "品种")
	String getVarieties();
	
	@ApiModelProperty(value = "规格")
	String getSpecifications();
	
	@ApiModelProperty(value = "征地范围名称")
	String getScopeName();
	
	@ApiModelProperty(value = "征地范围")
	String getScope();
	
	@ApiModelProperty(value = "单位")
	String getUnit();
	
	@ApiModelProperty(value ="树龄")
	Integer getTreeAge();
	
	@ApiModelProperty(value ="树高")
	Double getTreeHeight();
	
	@ApiModelProperty(value = "胸径")
	Double getBreastHeight();
	
	@ApiModelProperty(value = "数量")
	Double getNum();
	
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
