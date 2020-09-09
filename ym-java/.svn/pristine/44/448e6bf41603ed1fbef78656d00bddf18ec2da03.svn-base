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
import com.lyht.business.cost.dao.CostDao;
import com.lyht.business.cost.dao.CostHomesteadDao;
import com.lyht.business.cost.entity.CostHomesteadEntity;
import com.lyht.business.cost.vo.CostHomesteadVO;
import com.lyht.business.cost.vo.CostOwnerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostHomesteadService {
	@Autowired
	private CostHomesteadDao costHomesteadDao;

	@Autowired
	private CostUpdateService costUpdateService;

	@Autowired
	private CostDao costDao;

	/**
	 * 新增，修改
	 * 
	 * @param costHomesteadEntity
	 * @return
	 */
	public CostHomesteadEntity save(CostHomesteadEntity costHomesteadEntity) {
		CostHomesteadEntity save = null;
		try {
			save = costHomesteadDao.save(costHomesteadEntity);
		} catch (Exception e) {
			log.error("=====CostHomesteadService=====Method：save=====param：" + costHomesteadEntity, e);
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
			costHomesteadDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostHomesteadService=====Method：delete=====param：" + id, e);
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
			List<CostHomesteadEntity> findAllById = costHomesteadDao.findAllById(idList);
			costHomesteadDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostHomesteadService=====Method：deleteAll=====param：" + ids, e);
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
	public List<CostHomesteadEntity> findByOwnerNm(String ownerNm) {
		List<CostHomesteadEntity> list = null;
		try {
			list = costHomesteadDao.findByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostHomesteadService=====Method：findByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 查询当前户宅基地补偿费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countByOwnerNm(String ownerNm) {
		int countByOwnerNm = costHomesteadDao.countByOwnerNm(ownerNm);
		return countByOwnerNm;
	}

	/**
	 * 查询当前户宅基地补偿总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountByOwnerNm(String ownerNm) {
		BigDecimal amountByOwnerNm = costHomesteadDao.amountByOwnerNm(ownerNm);
		return amountByOwnerNm;
	}

	/**
	 * 计算宅基地补偿费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostHomesteadEntity> costByOwnerNm(String ownerNm) {
		List<CostHomesteadEntity> list = null;
		try {
			// 校验1：判断是否已搬迁安置人口核定,未核定则不允许计算费用
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error("=====搬迁安置未核定，宅基地补偿费用无法计算==========CostHomesteadService=====Method：costByOwnerNm=====param："
						+ ownerNm);
				return null;
			}
			// 校验2：是否已签订协议,已签订协议不允许重新计算，直接返回历史计算结果
//			CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
//			if (costEntity != null) {
//				Integer homesteadStatus = costEntity.getHomesteadStatus();
//				if (homesteadStatus != null
//						&& homesteadStatus == CostConstant.COST_HOMESTEAD_STATE_SIGNED) {
//					log.error("=====宅基地补偿,已签订协议==========CostHomesteadService=====Method：costByOwnerNm=====param：" + ownerNm);
//					list = costHomesteadDao.findByOwnerNm(ownerNm);
//					return list;
//				}
//			}
			// 校验3：搬迁安置类型是否为空
			String placeTypeCode = costOwnerVO.getPlaceTypeCode();// 搬迁安置类型
			if (StringUtils.isBlank(placeTypeCode)) {
				log.error("=====搬迁安置类型为空，宅基地补偿费用无法计算==========CostHomesteadService=====Method：costByOwnerNm=====param："
						+ ownerNm);
				return null;
			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countByOwnerNm = countByOwnerNm(ownerNm);
			List<CostHomesteadEntity> costHomesteadEntityList = null;
			if (countByOwnerNm > 0) {
				costHomesteadDao.deleteByOwnerNm(ownerNm);
			}
			Integer status = null;
			if (StringUtils.equalsAny(placeTypeCode, CostConstant.MOVE_TYPE_ONCE, CostConstant.MOVE_TYPE_THRICE)) {// 前置条件2：分散安置
				List<CostHomesteadVO> costDispersedByOwnerNm = costHomesteadDao.costDispersedByOwnerNm(ownerNm);
				String jsonString = JSON.toJSONString(costDispersedByOwnerNm);
				costHomesteadEntityList = JSON.parseArray(jsonString, CostHomesteadEntity.class);
				status = CostConstant.COST_HOMESTEAD_STATE_DISPERSED;
			} else {// 前置条件2：集中安置
				List<CostHomesteadVO> costDispersedByOwnerNm = costHomesteadDao.costFocusByOwnerNm(ownerNm);
				String jsonString = JSON.toJSONString(costDispersedByOwnerNm);
				costHomesteadEntityList = JSON.parseArray(jsonString, CostHomesteadEntity.class);
				status = CostConstant.COST_HOMESTEAD_STATE_FOCUS;
			}

			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (costHomesteadEntityList == null || costHomesteadEntityList.isEmpty()) {
				costUpdateService.updateHomesteadStatus(ownerNm, CostConstant.COST_HOMESTEAD_STATE_NOT_FEE, null);
				log.error("=====宅基地补偿费用 为空=====CostHomesteadService=====Method：costByOwnerNm=====param：" + ownerNm);
			} else {
				list = costHomesteadDao.saveAll(costHomesteadEntityList);
				BigDecimal amountByOwnerNm = costHomesteadDao.amountByOwnerNm(ownerNm);
				costUpdateService.updateHomesteadStatus(ownerNm, status, amountByOwnerNm);
			}

			status = null;
			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostHomesteadService=====Method：costByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容(分散)
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getDispersedDataList(String ownerNm) {
		List<CostHomesteadEntity> findByOwnerNm = costHomesteadDao.findByOwnerNmAndType(ownerNm,
				CostConstant.HOMESTEAD_DISPERSED);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostHomesteadEntity costHomesteadEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costHomesteadEntity.getProjectName();
				String area = costHomesteadEntity.getArea().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String price = costHomesteadEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costHomesteadEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(projectName);
				rowData.add(new BigDecimal(area));
				rowData.add(new BigDecimal(price));
				rowData.add(new BigDecimal(amount));
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容(集中)
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getFocusDataList(String ownerNm) {
		List<CostHomesteadEntity> findByOwnerNm = costHomesteadDao.findByOwnerNmAndType(ownerNm,
				CostConstant.HOMESTEAD_FOCUS);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostHomesteadEntity costHomesteadEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costHomesteadEntity.getProjectName();
				String area = costHomesteadEntity.getArea().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String deductionArea = costHomesteadEntity.getDeductionArea().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String surplusArea = costHomesteadEntity.getSurplusArea().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String price = costHomesteadEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costHomesteadEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(projectName);
				rowData.add(new BigDecimal(area));
				rowData.add(new BigDecimal(deductionArea));
				rowData.add(new BigDecimal(surplusArea));
				rowData.add(new BigDecimal(price));
				rowData.add(new BigDecimal(amount));
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}

}
