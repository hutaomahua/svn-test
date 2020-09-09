package com.lyht.business.fund.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 预备/独立费用查询返回实体
 * 
 * @author wzw
 *
 */
public interface FundCostVO {

	@ApiModelProperty(value = "id")
	Integer getId();
	
	@ApiModelProperty(value = "nm")
	String getNm();

	@ApiModelProperty(value = "支付时间")
	String getPaymentTime();

	@ApiModelProperty(value = "费用类型")
	String getAmountType();
	
	@ApiModelProperty(value = "金额")
	Double getAmount();
	
	@ApiModelProperty(value = "单位类型 地方政府/企业")
	String getOrganType();

	@ApiModelProperty(value = "单位名称")
	String getOrganName();
	
	@ApiModelProperty(value = "单价")
	Double getUnitPrice();
	
	@ApiModelProperty(value = "备注")
	String getRemark();
	
	@ApiModelProperty(value = "阶段")
	String getStage();
	
	@ApiModelProperty(value = "阶段nm")
	 String getStageNm();
	
	@ApiModelProperty(value = "项目名称")
	 String getProjectName();
	
	@ApiModelProperty(value = "项目nm")
	 String getProjectNm();
	
	@ApiModelProperty(value = "行政区")
	 String getRegion();
	
	@ApiModelProperty(value = "行政区城市编号")
	 String getCityCode();
	
	@ApiModelProperty(value = "行政区全称")
	 String getMergerName();
	
	@ApiModelProperty(value = "经办人姓名")
	 String getManager();
	
	@ApiModelProperty(value = "建设征地范围nm")
	 String getAreaNm();
	
	@ApiModelProperty(value ="建设征地范围")
	 String getArea();
	
	@ApiModelProperty(value ="附件数量")
	 Integer getFujian();

}
