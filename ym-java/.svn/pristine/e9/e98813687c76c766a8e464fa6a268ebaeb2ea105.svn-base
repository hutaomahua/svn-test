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
import com.lyht.business.cost.dao.CostLevyLandDao;
import com.lyht.business.cost.entity.CostLevyLandEntity;
import com.lyht.business.cost.vo.CostLevyLandVO;
import com.lyht.business.cost.vo.CostOwnerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostLevyLandService {
	@Autowired
	private CostLevyLandDao costLevyLandDao;

	@Autowired
	private CostUpdateService costUpdateService;

	@Autowired
	private CostDao costDao;

	/**
	 * 新增，修改
	 * 
	 * @param costLevyLandEntity
	 * @return
	 */
	public CostLevyLandEntity save(CostLevyLandEntity costLevyLandEntity) {
		CostLevyLandEntity save = null;
		try {
			save = costLevyLandDao.save(costLevyLandEntity);
		} catch (Exception e) {
			log.error("=====CostLevyLandService=====Method：save=====param：" + costLevyLandEntity, e);
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
			costLevyLandDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostLevyLandService=====Method：delete=====param：" + id, e);
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
			List<CostLevyLandEntity> findAllById = costLevyLandDao.findAllById(idList);
			costLevyLandDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostLevyLandService=====Method：deleteAll=====param：" + ids, e);
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
	public List<CostLevyLandEntity> findByOwnerNm(String ownerNm) {
		List<CostLevyLandEntity> list = null;
		try {
			list = costLevyLandDao.findByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostLevyLandService=====Method：findByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 查询当前户征收土地补偿费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countByOwnerNm(String ownerNm) {
		int countByOwnerNm = costLevyLandDao.countByOwnerNm(ownerNm);
		return countByOwnerNm;
	}

	/**
	 * 查询当前户征收土地补偿总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountByOwnerNm(String ownerNm) {
		BigDecimal amountByOwnerNm = costLevyLandDao.amountByOwnerNm(ownerNm);
		return amountByOwnerNm;
	}

	/**
	 * 计算征收土地补偿费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostLevyLandEntity> costByOwnerNm(String ownerNm) {
		List<CostLevyLandEntity> list = null;
		try {
			// 校验1：判断是否已搬迁安置人口核定,未核定则不允许计算费用
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error("=====搬迁安置未核定，征收土地补偿费用无法计算==========CostLevyLandService=====Method：costByOwnerNm=====param："
						+ ownerNm);
				return null;
			}
			// 校验2：是否已签订协议,已签订协议不允许重新计算，直接返回历史计算结果
//			CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
//			if (costEntity != null) {
//				Integer levyLandStatus = costEntity.getLevyLandStatus();
//				if (levyLandStatus != null && levyLandStatus == CostConstant.COST_SUB_STATE_SIGNED) {
//					log.error("=====征收土地补偿,已签订协议==========CostLevyLandService=====Method：costByOwnerNm=====param：" + ownerNm);
//					list = costLevyLandDao.findByOwnerNm(ownerNm);
//					return list;
//				}
//			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countByOwnerNm = countByOwnerNm(ownerNm);
			List<CostLevyLandVO> costByOwnerNm = null;
			if (countByOwnerNm > 0) {
				costLevyLandDao.deleteByOwnerNm(ownerNm);
			}
			String nature = costOwnerVO.getNature();// 性质
			if (StringUtils.equals(nature, "移民私有")) {// 移民私有
				costByOwnerNm = costLevyLandDao.costPersonalByOwnerNm(ownerNm);
			} else {// 集体公有
				costByOwnerNm = costLevyLandDao.costCollectiveByOwnerNm(ownerNm);
			}

			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (costByOwnerNm == null || costByOwnerNm.isEmpty()) {
				costUpdateService.updateLevyLandStatus(ownerNm, CostConstant.COST_SUB_STATE_NOT_FEE, null);
				log.error("=====征收土地补偿费用 为空=====CostLevyLandService=====Method：costByOwnerNm=====param：" + ownerNm);
			} else {
				String jsonString = JSON.toJSONString(costByOwnerNm);
				List<CostLevyLandEntity> parseArray = JSON.parseArray(jsonString, CostLevyLandEntity.class);
				list = costLevyLandDao.saveAll(parseArray);
				BigDecimal amountByOwnerNm = costLevyLandDao.amountByOwnerNm(ownerNm);
				costUpdateService.updateLevyLandStatus(ownerNm, CostConstant.COST_SUB_STATE_FEE, amountByOwnerNm);
			}

			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostLevyLandService=====Method：costByOwnerNm=====param：" + ownerNm, e);
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
		List<CostLevyLandEntity> findByOwnerNm = findByOwnerNm(ownerNm);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostLevyLandEntity costLevyLandEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costLevyLandEntity.getProjectName();
				String unit = costLevyLandEntity.getUnit();
				BigDecimal price = costLevyLandEntity.getPrice();
				BigDecimal num = costLevyLandEntity.getNum();
				BigDecimal amount = costLevyLandEntity.getAmount();
				String remark = costLevyLandEntity.getRemark();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(projectName);
				rowData.add(unit);
				rowData.add(price);
				rowData.add(num);
				rowData.add(amount);
				rowData.add(remark);
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}

}
