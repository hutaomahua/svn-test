package com.lyht.business.info.vo;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("实物指标汇总--分项")
@Data
public class InfoMoveAggregateTreeVO {

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

	@ApiModelProperty("总户数")
	private BigDecimal households;

	@ApiModelProperty("总人口")
	private BigDecimal population;

	@ApiModelProperty("枢纽工程建设区--合计(户数)")
	private BigDecimal reservoirTotalHouseholds;

	@ApiModelProperty("枢纽工程建设区--合计(人口)")
	private BigDecimal reservoirTotalPopulation;

	@ApiModelProperty("水库淹没影响区--合计(户数)")
	private BigDecimal pivotTotalHouseholds;

	@ApiModelProperty("水库淹没影响区--合计(人口)")
	private BigDecimal pivotTotalPopulation;

	@ApiModelProperty("集镇新址占地区(户数)")
	private BigDecimal newTownHouseholds;

	@ApiModelProperty("集镇新址占地区(人口)")
	private BigDecimal newTownPopulation;

	@ApiModelProperty("总户数")
	private BigDecimal householdsProgramme;

	@ApiModelProperty("总人口")
	private BigDecimal populationProgramme;

	@ApiModelProperty("枢纽工程建设区--合计(户数)")
	private BigDecimal reservoirTotalHouseholdsProgramme;

	@ApiModelProperty("枢纽工程建设区--合计(人口)")
	private BigDecimal reservoirTotalPopulationProgramme;

	@ApiModelProperty("水库淹没影响区--合计(户数)")
	private BigDecimal pivotTotalHouseholdsProgramme;

	@ApiModelProperty("水库淹没影响区--合计(人口)")
	private BigDecimal pivotTotalPopulationProgramme;

	@ApiModelProperty("集镇新址占地区(户数)")
	private BigDecimal newTownHouseholdsProgramme;

	@ApiModelProperty("集镇新址占地区(人口)")
	private BigDecimal newTownPopulationProgramme;

	@ApiModelProperty("子集")
	private List<InfoMoveAggregateTreeVO> children;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
