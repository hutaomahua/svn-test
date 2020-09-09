package com.lyht.business.cost.vo;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("补偿费用汇总")
public class CostAggregateTreeVO {

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

	@ApiModelProperty("补偿费用总额")
	private BigDecimal amount;

	@ApiModelProperty("房屋及房屋装修补偿费用")
	private BigDecimal houseAmount;

	@ApiModelProperty("附属建筑物及农副业设施补偿费用")
	private BigDecimal buildingAmount;

	@ApiModelProperty("零星果木补偿费用")
	private BigDecimal treesAmount;

	@ApiModelProperty("成片青苗及林木补偿费用")
	private BigDecimal youngCropsAmount;

	@ApiModelProperty("其他补偿补助费用")
	private BigDecimal otherAmount;

	private List<CostAggregateTreeVO> children;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
