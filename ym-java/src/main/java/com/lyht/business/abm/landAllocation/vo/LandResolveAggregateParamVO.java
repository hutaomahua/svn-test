package com.lyht.business.abm.landAllocation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 土地分解联动汇总列表查询参数
 * 
 * @author hxl
 *
 */
@Data
@ApiModel("土地分解联动汇总列表查询参数")
public class LandResolveAggregateParamVO {

	@ApiModelProperty("行政区域编码")
	private String cityCode;

	@ApiModelProperty("查询的面积类型(all：总面积；resolve：已分解面积；unresolve：待分解面积)")
	private String areaType;

	@ApiModelProperty("土地对应的分类的编码（NM）")
	private String landType;
	
	@ApiModelProperty("级别（0：一级；1：二级；2：三级；3：四级）")
	private Integer level;
	
}
