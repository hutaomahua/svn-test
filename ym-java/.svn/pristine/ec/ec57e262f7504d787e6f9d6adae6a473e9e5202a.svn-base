package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("土地汇总--树")
@Data
public class InfoLandAggregateTreeVO {

	@ApiModelProperty("序号")
	private String serialNumber;

	@ApiModelProperty("行政区--编码")
	private String cityCode;

	@ApiModelProperty("行政区--父级编码")
	private String parentCode;
	
	@ApiModelProperty("行政区--所有上级编码")
	private List<String> parentCodes;

	@ApiModelProperty("行政区--名称")
	private String name;

	@ApiModelProperty("行政区--全称")
	private String mergerName;
	
	@ApiModelProperty("行政区--级别")
	private String level;
	
	@ApiModelProperty("总面积")
	private BigDecimal total;

	@ApiModelProperty("枢纽工程建设区--合计")
	private BigDecimal reservoirTotal;

	@ApiModelProperty("书库淹没影响区--合计")
	private BigDecimal pivotTotal;

	@ApiModelProperty("枢纽临时")
	private BigDecimal temporary;

	@ApiModelProperty("枢纽永久")
	private BigDecimal permanent;

	@ApiModelProperty("水库淹没区")
	private BigDecimal flood;

	@ApiModelProperty("水库影响区")
	private BigDecimal influence;

	@ApiModelProperty("垫高（临时）")
	private BigDecimal raise;

	@ApiModelProperty("集镇新址占地区")
	private BigDecimal newTown;

	@ApiModelProperty("子集")
	private List<InfoLandAggregateTreeVO> children;

}
