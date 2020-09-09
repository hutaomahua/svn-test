package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("基础数据汇总--按行政区汇总数据")
@Data
public class InfoRegionTreeVO {

	@ApiModelProperty("序号")
	private String serialNumber;

	@ApiModelProperty("行政区域名称")
	private String regionName;

	@ApiModelProperty("行政区域编码")
	private String cityCode;

	@ApiModelProperty("行政区域父级编码")
	private String parentCode;
	
	@ApiModelProperty("行政区域所有上级编码")
	private List<String> parentCodes;

	@ApiModelProperty("行政区域全称")
	private String mergerName;

	@ApiModelProperty("行政区域级别")
	private String level;

	@ApiModelProperty("人口(人)")
	private BigDecimal population;

	@ApiModelProperty("企事业单位数量(个)")
	private BigDecimal enterNumber;

	@ApiModelProperty("房屋(㎡)")
	private BigDecimal houseArea;

	@ApiModelProperty("房屋装修(㎡)")
	private BigDecimal houseDecorationArea;

	@ApiModelProperty("零星果木(棵)")
	private BigDecimal treeNumber;

	@ApiModelProperty("宅基地(㎡)")
	private BigDecimal homesteadArea;

	private List<InfoRegionTreeVO> children;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
