package com.lyht.business.abm.removal.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

public interface AbmHomesteadVO {

	@ApiModelProperty(value = "id")
	Integer getId();
	
	@ApiModelProperty(value = "nm")
	String getNm();
	
	@ApiModelProperty(value = "项目nm")
	String getProjectNm();
	
	@ApiModelProperty(value = "项目名称 明码")
	String getProjectName();
	
	@ApiModelProperty(value = "行政区cityCode")
	String getRegion();
	
	@ApiModelProperty(value = "行政区明码")
	String getMergerName();
	
	@ApiModelProperty(value = "数据状态")
	String getStatus();
	
	@ApiModelProperty(value = "权属性质 nm")
	String getOwnerNature();
	
	@ApiModelProperty(value = "权属性质 明码")
	String getOwnerNatureName();
	
	@ApiModelProperty(value = "备注")
	String getRemark();
	
	@ApiModelProperty(value = "阶段")
	String getStage();
	
	@ApiModelProperty(value = "指标类型 nm")
	String getZblx();
	
	@ApiModelProperty(value = "指标类型  明码")
	String getZblxName();
	
	@ApiModelProperty(value = "征地范围 nm")
	String getScope();
	
	@ApiModelProperty(value = "征地范围 明码")
	String getScopeName();
	
	@ApiModelProperty(value = "权属人nm")
	String getOwnerNm();
	
	@ApiModelProperty(value = "面积")
	BigDecimal getArea();
	
	@ApiModelProperty("经度")
	String getLgtd();

	@ApiModelProperty("纬度")
	String getLttd();

	
	@ApiModelProperty("高程")
	String getAltd();

	@ApiModelProperty("图幅号")
	String getInMap();
	
	@ApiModelProperty(value = "附件数量")
	Integer getFileCount();
}
