package com.lyht.business.cost.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.cost.common.constant.CostConstant;
import com.lyht.business.cost.dao.CostBuildingDao;
import com.lyht.business.cost.dao.CostDao;
import com.lyht.business.cost.entity.CostBuildingEntity;
import com.lyht.business.cost.vo.CostBuildingVO;
import com.lyht.business.cost.vo.CostOwnerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostBuildingService {
	@Autowired
	private CostBuildingDao costBuildingDao;

	@Autowired
	private CostUpdateService costUpdateService;

	@Autowired
	private CostDao costDao;

	/**
	 * 新增，修改
	 * 
	 * @param costBuildingEntity
	 * @return
	 */
	public CostBuildingEntity save(CostBuildingEntity costBuildingEntity) {
		CostBuildingEntity save = null;
		try {
			save = costBuildingDao.save(costBuildingEntity);
		} catch (Exception e) {
			log.error("=====CostBuildingService=====Method：save=====param：" + costBuildingEntity, e);
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
			costBuildingDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostBuildingService=====Method：delete=====param：" + id, e);
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
			List<CostBuildingEntity> findAllById = costBuildingDao.findAllById(idList);
			costBuildingDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostBuildingService=====Method：deleteAll=====param：" + ids, e);
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
	public List<CostBuildingEntity> findByOwnerNm(String ownerNm) {
		List<CostBuildingEntity> list = null;
		try {
			list = costBuildingDao.findByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostBuildingService=====Method：findByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 查询当前户附属建筑物补偿费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countByOwnerNm(String ownerNm) {
		int countByOwnerNm = costBuildingDao.countByOwnerNm(ownerNm);
		return countByOwnerNm;
	}

	/**
	 * 查询当前户附属建筑物补偿总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountByOwnerNm(String ownerNm) {
		BigDecimal amountByOwnerNm = costBuildingDao.amountByOwnerNm(ownerNm);
		return amountByOwnerNm;
	}

	/**
	 * 计算附属建筑物补偿费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostBuildingEntity> costByOwnerNm(String ownerNm) {
		List<CostBuildingEntity> list = null;
		try {
			// 校验1：判断是否已搬迁安置人口核定,未核定则不允许计算费用
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error("=====搬迁安置未核定，附属建筑物补偿费用无法计算==========CostBuildingService=====Method：costByOwnerNm=====param："
						+ ownerNm);
				return null;
			}
			// 校验2：是否已签订协议,已签订协议不允许重新计算，直接返回历史计算结果
//			CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
//			if (costEntity != null) {
//				Integer buildingStatus = costEntity.getBuildingStatus();
//				if (buildingStatus != null && buildingStatus == CostConstant.COST_SUB_STATE_SIGNED) {
//					log.error("=====附属建筑物补偿,已签订协议==========CostBuildingService=====Method：costByOwnerNm=====param：" + ownerNm);
//					list = costBuildingDao.findByOwnerNm(ownerNm);
//					return list;
//				}
//			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countByOwnerNm = countByOwnerNm(ownerNm);
			List<CostBuildingVO> findCostByOwnerNm = null;
			if (countByOwnerNm == 0) {
				findCostByOwnerNm = costBuildingDao.findCostByOwnerNm(ownerNm);
			} else {
				costBuildingDao.deleteByOwnerNm(ownerNm);
				findCostByOwnerNm = costBuildingDao.findCostByOwnerNm(ownerNm);
			}
			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (findCostByOwnerNm != null && !findCostByOwnerNm.isEmpty()) {
				String jsonString = JSON.toJSONString(findCostByOwnerNm);
				List<CostBuildingEntity> parseArray = JSON.parseArray(jsonString, CostBuildingEntity.class);
				list = costBuildingDao.saveAll(parseArray);
				BigDecimal amountByOwnerNm = costBuildingDao.amountByOwnerNm(ownerNm);
				costUpdateService.updateBuildingStatus(ownerNm, CostConstant.COST_SUB_STATE_FEE, amountByOwnerNm);
			} else {
				costUpdateService.updateBuildingStatus(ownerNm, CostConstant.COST_SUB_STATE_NOT_FEE, null);
				log.error("=====附属建筑物补偿费用 为空=====CostBuildingService=====Method：costByOwnerNm=====param：" + ownerNm);
			}

			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostBuildingService=====Method：costByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getDataList(String ownerNm) {
		List<CostBuildingEntity> findByOwnerNm = findByOwnerNm(ownerNm);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostBuildingEntity costBuildingEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costBuildingEntity.getProjectName();
				String unit = costBuildingEntity.getUnit();
				String price = costBuildingEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String num = costBuildingEntity.getNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costBuildingEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(projectName);
				rowData.add(unit);
				rowData.add(new BigDecimal(price));
				rowData.add(new BigDecimal(num));
				rowData.add(new BigDecimal(amount));
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}
}
