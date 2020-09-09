package com.lyht.business.cost.vo;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.lyht.business.cost.entity.CostAgriculturalFacilitiesEntity;
import com.lyht.business.cost.entity.CostBuildingEntity;
import com.lyht.business.cost.entity.CostHomesteadEntity;
import com.lyht.business.cost.entity.CostHousesDecorationEntity;
import com.lyht.business.cost.entity.CostHousesEntity;
import com.lyht.business.cost.entity.CostIndividualEntity;
import com.lyht.business.cost.entity.CostInfrastructureEntity;
import com.lyht.business.cost.entity.CostLevyLandEntity;
import com.lyht.business.cost.entity.CostOtherEntity;
import com.lyht.business.cost.entity.CostProduceLandEntity;
import com.lyht.business.cost.entity.CostProducePopulationEntity;
import com.lyht.business.cost.entity.CostRelocationAllowanceEntity;
import com.lyht.business.cost.entity.CostTreesEntity;
import com.lyht.business.cost.entity.CostYoungCropsEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("总补偿费用")
public class CostVO {

	@ApiModelProperty("总补偿费用记录")
	private CostDetailVO costDetailVO;

	@ApiModelProperty("房屋补偿费用明细")
	private List<CostHousesEntity> costHousesList;

	@ApiModelProperty("房屋装修补偿费用明细")
	private List<CostHousesDecorationEntity> costHousesDecorationList;

	@ApiModelProperty("附属建筑物补偿费用明细")
	private List<CostBuildingEntity> costBuildingList;

	@ApiModelProperty("农副业设施补偿费用明细")
	private List<CostAgriculturalFacilitiesEntity> costAgriculturalFacilitiesList;

	@ApiModelProperty("零星树木补偿费用明细")
	private List<CostTreesEntity> costTreesList;

	@ApiModelProperty("个体工商户补偿费用明细")
	private List<CostIndividualEntity> costIndividualList;

	@ApiModelProperty("搬迁补助费用明细")
	private List<CostRelocationAllowanceEntity> costRelocationAllowanceList;

	@ApiModelProperty("其他补助费用明细")
	private List<CostOtherEntity> costOtherList;

	@ApiModelProperty("困难户补助费用明细")
	private List<CostOtherEntity> costDifficultList;

	@ApiModelProperty("搬迁安置基础设施补助费用明细")
	private List<CostInfrastructureEntity> costInfrastructureList;

	@ApiModelProperty("宅基地补偿费用明细")
	private List<CostHomesteadEntity> costHomesteadList;
	
	@ApiModelProperty("征收土地补偿费用明细")
	private List<CostLevyLandEntity> costLevyLandList;
	
	@ApiModelProperty("成片青苗及果木补偿费用明细")
	private List<CostYoungCropsEntity> costYoungCropsList;
	
	@ApiModelProperty("生产安置人口补偿费用明细")
	private List<CostProducePopulationEntity> costProducePopulationList;
	
	@ApiModelProperty("生产安置土地补偿费用明细")
	private List<CostProduceLandEntity> costProduceLandList;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
