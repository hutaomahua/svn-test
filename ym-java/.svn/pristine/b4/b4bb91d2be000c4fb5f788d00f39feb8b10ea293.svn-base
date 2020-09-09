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
import com.lyht.business.cost.dao.CostProducePopulationDao;
import com.lyht.business.cost.entity.CostProducePopulationEntity;
import com.lyht.business.cost.vo.CostOwnerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostProducePopulationService {
	@Autowired
	private CostProducePopulationDao costProducePopulationDao;

	@Autowired
	private CostUpdateService costUpdateService;

	@Autowired
	private CostDao costDao;

	/**
	 * 新增，修改
	 * 
	 * @param costProducePopulationEntity
	 * @return
	 */
	public CostProducePopulationEntity save(CostProducePopulationEntity costProducePopulationEntity) {
		CostProducePopulationEntity save = null;
		try {
			save = costProducePopulationDao.save(costProducePopulationEntity);
		} catch (Exception e) {
			log.error("=====CostProducePopulationService=====Method：save=====param：" + costProducePopulationEntity, e);
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
			costProducePopulationDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostProducePopulationService=====Method：delete=====param：" + id, e);
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
			List<CostProducePopulationEntity> findAllById = costProducePopulationDao.findAllById(idList);
			costProducePopulationDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostProducePopulationService=====Method：deleteAll=====param：" + ids, e);
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
	public List<CostProducePopulationEntity> findByOwnerNm(String ownerNm) {
		List<CostProducePopulationEntity> list = null;
		try {
			list = costProducePopulationDao.findByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostProducePopulationService=====Method：findByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 查询当前户生产安置人口费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countByOwnerNm(String ownerNm) {
		int countByOwnerNm = costProducePopulationDao.countByOwnerNm(ownerNm);
		return countByOwnerNm;
	}

	/**
	 * 查询当前户生产安置人口总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountByOwnerNm(String ownerNm) {
		BigDecimal amountByOwnerNm = costProducePopulationDao.amountByOwnerNm(ownerNm);
		return amountByOwnerNm;
	}

	/**
	 * 计算生产安置人口费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostProducePopulationEntity> costByOwnerNm(String ownerNm) {
		List<CostProducePopulationEntity> list = null;
		try {
			// 校验1：判断是否已搬迁安置人口核定,未核定则不允许计算费用
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error(
						"=====搬迁安置未核定，生产安置人口费用无法计算==========CostProducePopulationService=====Method：costByOwnerNm=====param："
								+ ownerNm);
				return null;
			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countByOwnerNm = countByOwnerNm(ownerNm);
			List<CostProducePopulationEntity> costProducePopulationEntityList = null;
			if (countByOwnerNm > 0) {
				costProducePopulationDao.deleteByOwnerNm(ownerNm);
			}

			Integer produceNum = costOwnerVO.getProduceNum();
			if (produceNum != null && produceNum > 0) {// 前置条件1：生产安置人口 ≥ 1
				String village = costOwnerVO.getVillage();// 安置地点（村）
				//if (StringUtils.equals(village, "兰永")) {// 兰永
					costProducePopulationEntityList = costProducePopulation(costOwnerVO);
				//}
			}

			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (costProducePopulationEntityList == null || costProducePopulationEntityList.isEmpty()) {
				costUpdateService.updateProducePopulationStatus(ownerNm, CostConstant.COST_SUB_STATE_NOT_FEE, null);
				log.error("=====生产安置人口费用 为空=====CostProducePopulationService=====Method：costByOwnerNm=====param："
						+ ownerNm);
			} else {
				list = costProducePopulationDao.saveAll(costProducePopulationEntityList);
				BigDecimal amountByOwnerNm = costProducePopulationDao.amountByOwnerNm(ownerNm);
				costUpdateService.updateProducePopulationStatus(ownerNm, CostConstant.COST_SUB_STATE_FEE, amountByOwnerNm);
			}

			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostProducePopulationService=====Method：costByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 生产安置人口费用（兰永）
	 * 
	 * @param costOwnerVO
	 * @return
	 */
	public List<CostProducePopulationEntity> costProducePopulation(CostOwnerVO costOwnerVO) {
		Integer produceNumber = costOwnerVO.getProduceNum();
		String ownerNm = costOwnerVO.getOwnerNm();
		BigDecimal produceNum = new BigDecimal(produceNumber);

		List<CostProducePopulationEntity> costProducePopulationEntityList = new ArrayList<>();
		CostProducePopulationEntity ra = new CostProducePopulationEntity();
		ra.setOwnerNm(ownerNm);
		ra.setProjectName("逐年补偿");
		BigDecimal price = new BigDecimal(290);
		ra.setPrice(price);
		ra.setUnit("元/人·月");
		ra.setNum(produceNum);
		BigDecimal amount = price.multiply(produceNum);
		ra.setAmount(amount);
		costProducePopulationEntityList.add(ra);

		return costProducePopulationEntityList;
	}

}
