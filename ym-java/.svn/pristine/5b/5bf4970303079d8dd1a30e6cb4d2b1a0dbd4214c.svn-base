package com.lyht.business.cost.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.cost.common.constant.CostConstant;
import com.lyht.business.cost.common.enums.LyhtCostExceptionEnums;
import com.lyht.business.cost.dao.CostDao;
import com.lyht.business.cost.entity.CostEntity;

@Service
public class CostUpdateService {

	@Autowired
	private CostDao costDao;

	/**
	 * 更新房屋补偿费用状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param houseStatus
	 * @return
	 */
	public CostEntity updateHouseStatus(String ownerNm, Integer houseStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (houseStatus != null) {
			costEntity.setHouseStatus(houseStatus);
		}
		// step3：重置金额
		costEntity.setHouseAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新房屋装修补偿费用状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param houseDecorationStatus
	 * @return
	 */
	public CostEntity updateHouseDecorationStatus(String ownerNm, Integer houseDecorationStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (houseDecorationStatus != null) {
			costEntity.setHouseDecorationStatus(houseDecorationStatus);
		}
		// step3：重置金额
		costEntity.setHouseDecorationAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新附属建筑物补偿费用状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param buildingStatus
	 * @return
	 */
	public CostEntity updateBuildingStatus(String ownerNm, Integer buildingStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (buildingStatus != null) {
			costEntity.setBuildingStatus(buildingStatus);
		}
		// step3：重置金额
		costEntity.setBuildingAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新农副业设施补偿费用状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param agriculturalFacilitiesStatus
	 * @return
	 */
	public CostEntity updateAgriculturalFacilitiesStatus(String ownerNm, Integer agriculturalFacilitiesStatus,
			BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (agriculturalFacilitiesStatus != null) {
			costEntity.setAgriculturalFacilitiesStatus(agriculturalFacilitiesStatus);
		}
		// step3：重置金额
		costEntity.setAgriculturalFacilitiesAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新零星树木补偿费用状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param treesStatus
	 * @return
	 */
	public CostEntity updateTreesStatus(String ownerNm, Integer treesStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (treesStatus != null) {
			costEntity.setTreesStatus(treesStatus);
		}
		// step3：重置金额
		costEntity.setTreesAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新个体工商户补偿费用状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param individualStatus
	 * @return
	 */
	public CostEntity updateIndividualStatus(String ownerNm, Integer individualStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (individualStatus != null) {
			costEntity.setIndividualStatus(individualStatus);
		}
		// step3：重置金额
		costEntity.setIndividualAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新搬迁补助状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param relocationAllowanceStatus
	 * @return
	 */
	public CostEntity updateRelocationAllowanceStatus(String ownerNm, Integer relocationAllowanceStatus,
			BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (relocationAllowanceStatus != null) {
			costEntity.setRelocationAllowanceStatus(relocationAllowanceStatus);
		}
		// step3：重置金额
		costEntity.setRelocationAllowanceAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新其他补助状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param otherStatus
	 * @return
	 */
	public CostEntity updateOtherStatus(String ownerNm, Integer otherStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (otherStatus != null) {
			costEntity.setOtherStatus(otherStatus);
		}
		// step3：重置金额
		costEntity.setOtherAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新困难户补助状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param difficultStatus
	 * @return
	 */
	public CostEntity updateDifficultStatus(String ownerNm, Integer difficultStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (difficultStatus != null) {
			costEntity.setDifficultStatus(difficultStatus);
		}
		// step3：重置金额
		costEntity.setDifficultAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新搬迁安置基础设施补助状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param difficultStatus
	 * @return
	 */
	public CostEntity updateInfrastructureStatus(String ownerNm, Integer infrastructureStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (infrastructureStatus != null) {
			costEntity.setInfrastructureStatus(infrastructureStatus);
		}
		// step3：重置金额
		costEntity.setInfrastructureAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新宅基地补偿状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param difficultStatus
	 * @return
	 */
	public CostEntity updateHomesteadStatus(String ownerNm, Integer homesteadStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (homesteadStatus != null) {
			costEntity.setHomesteadStatus(homesteadStatus);
		}
		// step3：重置金额
		costEntity.setHomesteadAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新征收土地补偿状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param difficultStatus
	 * @return
	 */
	public CostEntity updateLevyLandStatus(String ownerNm, Integer levyLandStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (levyLandStatus != null) {
			costEntity.setLevyLandStatus(levyLandStatus);
		}
		// step3：重置金额
		costEntity.setLevyLandAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新成片青苗及果木补偿状态（默认 0：正常）
	 * 
	 * @param ownerNm
	 * @param difficultStatus
	 * @return
	 */
	public CostEntity updateYoungCropsStatus(String ownerNm, Integer youngCropsStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (youngCropsStatus != null) {
			costEntity.setYoungCropsStatus(youngCropsStatus);
		}
		// step3：重置金额
		costEntity.setYoungCropsAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}
	
	/**
	 * 更新生产安置人口补偿状态
	 * @param ownerNm
	 * @param producePopulationStatus
	 * @param amount
	 * @return
	 */
	public CostEntity updateProducePopulationStatus(String ownerNm, Integer producePopulationStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (producePopulationStatus != null) {
			costEntity.setProducePopulationStatus(producePopulationStatus);
		}
		// step3：重置金额
		costEntity.setProducePopulationAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}
	
	public CostEntity updateProduceLandStatus(String ownerNm, Integer produceLandStatus, BigDecimal amount) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}

		// step2：修改状态
		if (produceLandStatus != null) {
			costEntity.setProduceLandStatus(produceLandStatus);
		}
		// step3：重置金额
		costEntity.setProduceLandAmount(amount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
	 * 
	 * @param ownerNm
	 */
	public CostEntity updateStatus(String ownerNm) {
		// step1：获取当前补偿费用总记录
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			costEntity = new CostEntity();
			costEntity.setOwnerNm(ownerNm);
		}
		int totalCount = 0;
		int costCount = 0;// 已计算栏目数

		// step2：统计各项补偿费用计算情况
		Integer houseStatus = costEntity.getHouseStatus();
		totalCount++;
		if (houseStatus != null) {
			if (houseStatus == CostConstant.COST_SUB_STATE_FEE || houseStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer houseDecorationStatus = costEntity.getHouseDecorationStatus();
		totalCount++;
		if (houseDecorationStatus != null) {
			if (houseDecorationStatus == CostConstant.COST_SUB_STATE_FEE
					|| houseDecorationStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer buildingStatus = costEntity.getBuildingStatus();
		totalCount++;
		if (buildingStatus != null) {
			if (buildingStatus == CostConstant.COST_SUB_STATE_FEE
					|| buildingStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer agriculturalFacilitiesStatus = costEntity.getAgriculturalFacilitiesStatus();
		totalCount++;
		if (agriculturalFacilitiesStatus != null) {
			if (agriculturalFacilitiesStatus == CostConstant.COST_SUB_STATE_FEE
					|| agriculturalFacilitiesStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer treesStatus = costEntity.getTreesStatus();
		totalCount++;
		if (treesStatus != null) {
			if (treesStatus == CostConstant.COST_SUB_STATE_FEE || treesStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer individualStatus = costEntity.getIndividualStatus();
		totalCount++;
		if (individualStatus != null) {
			if (individualStatus == CostConstant.COST_SUB_STATE_FEE
					|| individualStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer relocationAllowanceStatus = costEntity.getRelocationAllowanceStatus();
		totalCount++;
		if (relocationAllowanceStatus != null) {
			if (relocationAllowanceStatus == CostConstant.COST_RELOCATION_STATE_REMOTE
					|| relocationAllowanceStatus == CostConstant.COST_RELOCATION_STATE_RECENT
					|| relocationAllowanceStatus == CostConstant.COST_RELOCATION_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer otherStatus = costEntity.getOtherStatus();
		totalCount++;
		if (otherStatus != null) {
			if (otherStatus == CostConstant.COST_OTHER_STATE_RURA || otherStatus == CostConstant.COST_OTHER_STATE_URBAN
					|| otherStatus == CostConstant.COST_OTHER_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer difficultStatus = costEntity.getDifficultStatus();
		totalCount++;
		if (difficultStatus != null) {
			if (difficultStatus == CostConstant.COST_SUB_STATE_FEE
					|| difficultStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer infrastructureStatus = costEntity.getInfrastructureStatus();
		totalCount++;
		if (infrastructureStatus != null) {
			if (infrastructureStatus == CostConstant.COST_SUB_STATE_FEE
					|| infrastructureStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		Integer homesteadStatus = costEntity.getHomesteadStatus();
		totalCount++;
		if (homesteadStatus != null) {
			if (homesteadStatus == CostConstant.COST_HOMESTEAD_STATE_DISPERSED
					|| homesteadStatus == CostConstant.COST_HOMESTEAD_STATE_FOCUS
					|| homesteadStatus == CostConstant.COST_HOMESTEAD_STATE_NOT_FEE) {
				costCount++;
			}
		}
		// 暂不计算--征收土地
//		Integer levyLandStatus = costEntity.getLevyLandStatus();
//		totalCount ++;
//		if (levyLandStatus != null) {
//			if (levyLandStatus == CostConstant.COST_SUB_STATE_FEE || levyLandStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
//				costCount++;
//			}
//		}
		Integer youngCropsStatus = costEntity.getYoungCropsStatus();
		totalCount++;
		if (youngCropsStatus != null) {
			if (youngCropsStatus == CostConstant.COST_SUB_STATE_FEE
					|| youngCropsStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		
		Integer producePopulationStatus = costEntity.getProducePopulationStatus();
		totalCount++;
		if (producePopulationStatus != null) {
			if (producePopulationStatus == CostConstant.COST_SUB_STATE_FEE
					|| producePopulationStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
				costCount++;
			}
		}
		
//		Integer produceLandStatus = costEntity.getProduceLandStatus();
//		totalCount++;
//		if (produceLandStatus != null) {
//			if (produceLandStatus == CostConstant.COST_SUB_STATE_FEE
//					|| produceLandStatus == CostConstant.COST_SUB_STATE_NOT_FEE) {
//				costCount++;
//			}
//		}

		// step3：修改状态
		if (costCount == totalCount) {// 完成计算
			costEntity.setStatus(CostConstant.COST_STATE_CALCULATED);
		} else if (costCount == 0) {// 未计算
			costEntity.setStatus(CostConstant.COST_STATE_NORMAL);
		} else {// 部分已计算
			costEntity.setStatus(CostConstant.COST_STATE_PART_CALCULATED);
		}
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新当前户的搬迁安置总额
	 * 
	 * @param ownerNm
	 */
	public CostEntity updateMoveAmount(String ownerNm) {
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			throw new LyhtRuntimeException(LyhtCostExceptionEnums.COST_NON_EXIST);
		}
		BigDecimal moveAmount = costDao.costMoveAmountByOwnerNm(ownerNm);
		costEntity.setMoveAmount(moveAmount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

	/**
	 * 更新当前户的生产安置总额
	 * 
	 * @param ownerNm
	 */
	public CostEntity updateProductionAmount(String ownerNm) {
		CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
		if (costEntity == null) {
			throw new LyhtRuntimeException(LyhtCostExceptionEnums.COST_NON_EXIST);
		}
		BigDecimal produceAmount = costDao.costProductionAmountByOwnerNm(ownerNm);
		costEntity.setProductionAmount(produceAmount);
		CostEntity save = costDao.save(costEntity);
		return save;
	}

}
