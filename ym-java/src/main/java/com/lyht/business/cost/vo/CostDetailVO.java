package com.lyht.business.cost.vo;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("总补偿费用明细")
public interface CostDetailVO {

	@ApiModelProperty("唯一ID")
	String getId();

	@ApiModelProperty("行政区编码")
	String getRegionNm();

	@ApiModelProperty("行政区")
	String getRegion();

	@ApiModelProperty("征地范围编码")
	String getScopeNm();

	@ApiModelProperty("征地范围")
	String getScope();

	@ApiModelProperty("指标类型")
	String getZblx();

	@ApiModelProperty("户主编码")
	String getOwnerNm();

	@ApiModelProperty("户主姓名")
	String getOwnerName();

	@ApiModelProperty("身份证号")
	String getIdCard();

	@ApiModelProperty("搬迁安置费用")
	BigDecimal getMoveAmount();

	@ApiModelProperty("生产安置费用（元/月）")
	BigDecimal getProductionAmount();

	@ApiModelProperty("数据状态(0：正常，1：部分已计算，2：计算完成)")
	Integer getStatus();

	@ApiModelProperty("房屋补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getHouseStatus();

	@ApiModelProperty("房屋装修补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getHouseDecorationStatus();

	@ApiModelProperty("附属建筑物补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getBuildingStatus();

	@ApiModelProperty("农副业设施补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getAgriculturalFacilitiesStatus();

	@ApiModelProperty("零星树木补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getTreesStatus();

	@ApiModelProperty("个体工商户补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getIndividualStatus();

	@ApiModelProperty("搬迁补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿（远迁），允许签订协议，2：有补偿（近迁），允许签订协议）")
	Integer getRelocationAllowanceStatus();

	@ApiModelProperty("其他补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿（农村），允许签订协议，2：有补偿（集镇），允许签订协议）")
	Integer getOtherStatus();

	@ApiModelProperty("困难户补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getDifficultStatus();

	@ApiModelProperty("基础设施补助状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getInfrastructureStatus();

	@ApiModelProperty("宅基地补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿（分散安置），允许签订协议，2：有补偿（集中安置），允许签订协议）")
	Integer getHomesteadStatus();

	@ApiModelProperty("征收土地补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getLevyLandStatus();

	@ApiModelProperty("成片青苗及果木补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getYoungCropsStatus();
	
	@ApiModelProperty("生产安置人口补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getProducePopulationStatus();
	
	@ApiModelProperty("生产安置土地补偿状态(-1：无补偿，不允许签订协议，0：正常，1：有补偿，允许签订协议）")
	Integer getProduceLandStatus();

	@ApiModelProperty("房屋及装修补偿费用(元)")
	BigDecimal getHouseAmount();

	@ApiModelProperty("附属建筑物及农副业设施费用(元)")
	BigDecimal getBuildingAmount();

	@ApiModelProperty("零星果木补偿费用(元)")
	BigDecimal getTreesAmount();
	
	@ApiModelProperty("青苗及林木补偿费用(元)")
	BigDecimal getYoungCropsAmount();

	@ApiModelProperty("其他补偿补助费用(元)")
	BigDecimal getOtherAmount();


}
