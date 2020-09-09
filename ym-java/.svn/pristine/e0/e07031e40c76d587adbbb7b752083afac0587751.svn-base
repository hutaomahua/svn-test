package com.lyht.business.info.vo;

import java.util.List;

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
@ApiModel("土地图表汇总查询参数")
public class InfoLandAggregateParamVO {

	@ApiModelProperty("行政区--全称")
	private String mergerName;

	@ApiModelProperty("多个征地范围编码")
	private List<String> scopes;

	@ApiModelProperty("土地分类编码（landType）")
	private String landType;
	
	@ApiModelProperty("级别（0：一级；1：二级；2：三级；3：四级）")
	private Integer level;
	
}
