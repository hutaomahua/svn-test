package com.lyht.business.cost.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.cost.common.constant.CostConstant;
import com.lyht.business.cost.dao.CostDao;
import com.lyht.business.cost.dao.CostProduceLandDao;
import com.lyht.business.cost.entity.CostProduceLandEntity;
import com.lyht.business.cost.vo.CostOwnerVO;
import com.lyht.business.cost.vo.CostProduceLandVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostProduceLandService {
	@Autowired
	private CostProduceLandDao costProduceLandDao;

	@Autowired
	private CostUpdateService costUpdateService;

	@Autowired
	private CostDao costDao;

	/**
	 * 新增，修改
	 * 
	 * @param costProduceLandEntity
	 * @return
	 */
	public CostProduceLandEntity save(CostProduceLandEntity costProduceLandEntity) {
		CostProduceLandEntity save = null;
		try {
			save = costProduceLandDao.save(costProduceLandEntity);
		} catch (Exception e) {
			log.error("=====CostProduceLandService=====Method：save=====param：" + costProduceLandEntity, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return save;
	}

	/**
	 * 删除（by id）
	 * 
	 * @param id
	 * @return
	 */
	public String delete(String id) {
		try {
			costProduceLandDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostProduceLandService=====Method：delete=====param：" + id, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return id;
	}

	/**
	 * 删除（by ids）
	 * 
	 * @param ids
	 * @return
	 */
	public String deleteAll(String ids) {
		try {
			List<String> idList = Arrays.asList(StringUtils.split(ids, ","));
			List<CostProduceLandEntity> findAllById = costProduceLandDao.findAllById(idList);
			costProduceLandDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostProduceLandService=====Method：deleteAll=====param：" + ids, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return ids;
	}

	/**
	 * 按户主nm查询
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<CostProduceLandEntity> findByOwnerNm(String ownerNm) {
		List<CostProduceLandEntity> list = null;
		try {
			list = costProduceLandDao.findByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostProduceLandService=====Method：findByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 查询当前户生产安置土地费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countByOwnerNm(String ownerNm) {
		int countByOwnerNm = costProduceLandDao.countByOwnerNm(ownerNm);
		return countByOwnerNm;
	}

	/**
	 * 查询当前户生产安置土地总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountByOwnerNm(String ownerNm) {
		BigDecimal amountByOwnerNm = costProduceLandDao.amountByOwnerNm(ownerNm);
		return amountByOwnerNm;
	}

	/**
	 * 计算生产安置土地费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostProduceLandEntity> costByOwnerNm(String ownerNm) {
		List<CostProduceLandEntity> list = null;
		try {
			// 校验1：判断是否已搬迁安置土地核定,未核定则不允许计算费用
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error(
						"=====搬迁安置未核定，生产安置土地费用无法计算==========CostProduceLandService=====Method：costByOwnerNm=====param："
								+ ownerNm);
				return null;
			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countByOwnerNm = countByOwnerNm(ownerNm);
			List<CostProduceLandEntity> costProduceLandEntityList = null;
			if (countByOwnerNm > 0) {
				costProduceLandDao.deleteByOwnerNm(ownerNm);
			}

			String village = costOwnerVO.getVillage();// 安置地点（村）
			if (!StringUtils.equals(village, "兰永")) {// 非兰永
				costProduceLandEntityList = costProduceLand(ownerNm);
			}

			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (costProduceLandEntityList == null || costProduceLandEntityList.isEmpty()) {
				costUpdateService.updateProduceLandStatus(ownerNm, CostConstant.COST_SUB_STATE_NOT_FEE, null);
				log.error("=====生产安置土地费用 为空=====CostProduceLandService=====Method：costByOwnerNm=====param：" + ownerNm);
			} else {
				list = costProduceLandDao.saveAll(costProduceLandEntityList);
				BigDecimal amountByOwnerNm = costProduceLandDao.amountByOwnerNm(ownerNm);
				costUpdateService.updateProduceLandStatus(ownerNm, CostConstant.COST_SUB_STATE_FEE,
						amountByOwnerNm);
			}

			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostProduceLandService=====Method：costByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 生产安置土地费用（非兰永）
	 * 
	 * @param costOwnerVO
	 * @return
	 */
	public List<CostProduceLandEntity> costProduceLand(String ownerNm) {
		List<CostProduceLandEntity> costProduceLandEntityList = new ArrayList<>();
		List<CostProduceLandVO> costByOwnerNm = costProduceLandDao.costByOwnerNm(ownerNm);
		// 水田
		CostProduceLandEntity paddy = new CostProduceLandEntity();
		BigDecimal paddyNum = BigDecimal.valueOf(0);
		BigDecimal paddyPrice = BigDecimal.valueOf(278.31);
		paddy.setPrice(paddyPrice);
		paddy.setOwnerNm(ownerNm);
		paddy.setProjectName("水田");
		paddy.setUnit("元/亩·月");
		// 旱地
		CostProduceLandEntity dry = new CostProduceLandEntity();
		BigDecimal dryNum = BigDecimal.valueOf(0);
		BigDecimal dryPrice = BigDecimal.valueOf(166.98);
		dry.setPrice(dryPrice);
		dry.setOwnerNm(ownerNm);
		dry.setProjectName("旱地");
		dry.setUnit("元/亩·月");
		// 陡坡地
		CostProduceLandEntity slope = new CostProduceLandEntity();
		BigDecimal slopeNum = BigDecimal.valueOf(0);
		BigDecimal slopePrice = BigDecimal.valueOf(166.98);
		slope.setPrice(slopePrice);
		slope.setOwnerNm(ownerNm);
		slope.setProjectName("陡坡地");
		slope.setUnit("元/亩·月");
		// 板栗成园地
		CostProduceLandEntity chestnutOldGarden = new CostProduceLandEntity();
		BigDecimal chestnutOldGardenNum = BigDecimal.valueOf(0);
		BigDecimal chestnutOldGardenPrice = BigDecimal.valueOf(278.31);
		chestnutOldGarden.setPrice(chestnutOldGardenPrice);
		chestnutOldGarden.setOwnerNm(ownerNm);
		chestnutOldGarden.setProjectName("板栗成园地");
		chestnutOldGarden.setUnit("元/亩·月");
		// 板栗幼园地
		CostProduceLandEntity chestnutYoungGarden = new CostProduceLandEntity();
		BigDecimal chestnutYoungGardenNum = BigDecimal.valueOf(0);
		BigDecimal chestnutYoungGardenPrice = BigDecimal.valueOf(222.64);
		chestnutYoungGarden.setPrice(chestnutYoungGardenPrice);
		chestnutYoungGarden.setOwnerNm(ownerNm);
		chestnutYoungGarden.setProjectName("板栗幼园地");
		chestnutYoungGarden.setUnit("元/亩·月");
		// 核桃成园地
		CostProduceLandEntity walnutOldGarden = new CostProduceLandEntity();
		BigDecimal walnutOldGardenNum = BigDecimal.valueOf(0);
		BigDecimal walnutOldGardenPrice = BigDecimal.valueOf(278.31);
		walnutOldGarden.setPrice(walnutOldGardenPrice);
		walnutOldGarden.setOwnerNm(ownerNm);
		walnutOldGarden.setProjectName("核桃成园地");
		walnutOldGarden.setUnit("元/亩·月");
		// 核桃幼园地
		CostProduceLandEntity walnutYoungGarden = new CostProduceLandEntity();
		BigDecimal walnutYoungGardenNum = BigDecimal.valueOf(0);
		BigDecimal walnutYoungGardenPrice = BigDecimal.valueOf(222.64);
		walnutYoungGarden.setPrice(walnutYoungGardenPrice);
		walnutYoungGarden.setOwnerNm(ownerNm);
		walnutYoungGarden.setProjectName("核桃幼园地");
		walnutYoungGarden.setUnit("元/亩·月");
		// 葡萄成园地
		CostProduceLandEntity grapeOldGarden = new CostProduceLandEntity();
		BigDecimal grapeOldGardenNum = BigDecimal.valueOf(0);
		BigDecimal grapeOldGardenPrice = BigDecimal.valueOf(500.95);
		grapeOldGarden.setPrice(grapeOldGardenPrice);
		grapeOldGarden.setOwnerNm(ownerNm);
		grapeOldGarden.setProjectName("葡萄成园地");
		grapeOldGarden.setUnit("元/亩·月");
		// 葡萄幼园地
		CostProduceLandEntity grapeYoungGarden = new CostProduceLandEntity();
		BigDecimal grapeYoungGardenNum = BigDecimal.valueOf(0);
		BigDecimal grapeYoungGardenPrice = BigDecimal.valueOf(222.64);
		grapeYoungGarden.setPrice(grapeYoungGardenPrice);
		grapeYoungGarden.setOwnerNm(ownerNm);
		grapeYoungGarden.setProjectName("葡萄幼园地");
		grapeYoungGarden.setUnit("元/亩·月");
		// 其他园地
		CostProduceLandEntity other = new CostProduceLandEntity();
		BigDecimal otherNum = BigDecimal.valueOf(0);
		BigDecimal otherPrice = BigDecimal.valueOf(166.98);
		other.setPrice(otherPrice);
		other.setOwnerNm(ownerNm);
		other.setProjectName("其他园地");
		other.setUnit("元/亩·月");

		for (CostProduceLandVO costProduceLandVO : costByOwnerNm) {
			String projectName = costProduceLandVO.getProjectName();
			BigDecimal num = costProduceLandVO.getNum();
			if (StringUtils.equals(projectName, "水田")) {
				if (num != null) {
					paddyNum = paddyNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "旱地")) {
				if (num != null) {
					dryNum = dryNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "陡坡地")) {
				if (num != null) {
					slopeNum = slopeNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "板栗成园地")) {
				if (num != null) {
					chestnutOldGardenNum = chestnutOldGardenNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "板栗幼园地")) {
				if (num != null) {
					chestnutYoungGardenNum = chestnutYoungGardenNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "核桃成园地")) {
				if (num != null) {
					walnutOldGardenNum = walnutOldGardenNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "核桃幼园地")) {
				if (num != null) {
					walnutYoungGardenNum = walnutYoungGardenNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "葡萄成园地")) {
				if (num != null) {
					grapeOldGardenNum = grapeOldGardenNum.add(num);
				}
			} else if (StringUtils.equals(projectName, "葡萄幼园地")) {
				if (num != null) {
					grapeYoungGardenNum = grapeYoungGardenNum.add(num);
				}
			} else if (StringUtils.contains(projectName, "园地")) {
				if (num != null) {
					otherNum = otherNum.add(num);
				}
			}
		}

		// 水田
		BigDecimal paddyAmount = paddyPrice.multiply(paddyNum).multiply(BigDecimal.valueOf(1));// 单价 * 数量（亩） * 系数
		paddy.setAmount(paddyAmount);
		paddy.setNum(paddyNum);
		costProduceLandEntityList.add(paddy);
		// 旱地
		BigDecimal dryAmount = dryPrice.multiply(dryNum).multiply(BigDecimal.valueOf(0.6));// 单价 * 数量（亩） * 系数
		dry.setAmount(dryAmount);
		dry.setNum(dryNum);
		costProduceLandEntityList.add(dry);
		// 陡坡地
		BigDecimal slopeAmount = slopePrice.multiply(slopeNum).multiply(BigDecimal.valueOf(0.6));// 单价 * 数量（亩） * 系数
		slope.setAmount(slopeAmount);
		slope.setNum(slopeNum);
		costProduceLandEntityList.add(slope);
		// 板栗成园地
		BigDecimal chestnutOldGardenAmount = chestnutOldGardenPrice.multiply(chestnutOldGardenNum)
				.multiply(BigDecimal.valueOf(1));// 单价 * 数量（亩） * 系数
		chestnutOldGarden.setAmount(chestnutOldGardenAmount);
		chestnutOldGarden.setNum(chestnutOldGardenNum);
		costProduceLandEntityList.add(chestnutOldGarden);
		// 板栗幼园地
		BigDecimal chestnutYoungGardenAmount = chestnutYoungGardenPrice.multiply(chestnutYoungGardenNum)
				.multiply(BigDecimal.valueOf(0.8));// 单价 * 数量（亩） * 系数
		chestnutYoungGarden.setAmount(chestnutYoungGardenAmount);
		chestnutYoungGarden.setNum(chestnutYoungGardenNum);
		costProduceLandEntityList.add(chestnutYoungGarden);
		// 核桃成园地
		BigDecimal walnutOldGardenAmount = walnutOldGardenPrice.multiply(walnutOldGardenNum)
				.multiply(BigDecimal.valueOf(1));// 单价 * 数量（亩） * 系数
		walnutOldGarden.setAmount(walnutOldGardenAmount);
		walnutOldGarden.setNum(walnutOldGardenNum);
		costProduceLandEntityList.add(walnutOldGarden);
		// 核桃幼园地
		BigDecimal walnutYoungGardenAmount = walnutYoungGardenPrice.multiply(walnutYoungGardenNum)
				.multiply(BigDecimal.valueOf(0.8));// 单价 * 数量（亩） * 系数
		walnutYoungGarden.setAmount(walnutYoungGardenAmount);
		walnutYoungGarden.setNum(walnutYoungGardenNum);
		costProduceLandEntityList.add(walnutYoungGarden);
		// 葡萄成园地
		BigDecimal grapeOldGardenAmount = grapeOldGardenPrice.multiply(grapeOldGardenNum)
				.multiply(BigDecimal.valueOf(1.8));// 单价 * 数量（亩） * 系数
		grapeOldGarden.setAmount(grapeOldGardenAmount);
		grapeOldGarden.setNum(grapeOldGardenNum);
		costProduceLandEntityList.add(grapeOldGarden);
		// 葡萄幼园地
		BigDecimal grapeYoungGardenAmount = grapeYoungGardenPrice.multiply(grapeYoungGardenNum)
				.multiply(BigDecimal.valueOf(0.8));// 单价 * 数量（亩） * 系数
		grapeYoungGarden.setAmount(grapeYoungGardenAmount);
		grapeYoungGarden.setNum(grapeYoungGardenNum);
		costProduceLandEntityList.add(grapeYoungGarden);
		// 其他园地
		BigDecimal otherAmount = otherPrice.multiply(otherNum).multiply(BigDecimal.valueOf(0.6));// 单价 * 数量（亩） * 系数
		other.setAmount(otherAmount);
		other.setNum(otherNum);
		costProduceLandEntityList.add(other);

		return costProduceLandEntityList;
	}

}
