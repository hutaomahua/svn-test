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
import com.lyht.business.cost.dao.CostHousesDao;
import com.lyht.business.cost.dao.CostOtherDao;
import com.lyht.business.cost.entity.CostEntity;
import com.lyht.business.cost.entity.CostOtherEntity;
import com.lyht.business.cost.vo.CostOwnerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostOtherService {
	@Autowired
	private CostOtherDao costOtherDao;

	@Autowired
	private CostHousesDao costHouseDao;

	@Autowired
	private CostDao costDao;

	@Autowired
	private CostUpdateService costUpdateService;

	/**
	 * 新增，修改
	 * 
	 * @param costOtherEntity
	 * @return
	 */
	public CostOtherEntity save(CostOtherEntity costOtherEntity) {
		CostOtherEntity save = null;
		try {
			save = costOtherDao.save(costOtherEntity);
		} catch (Exception e) {
			log.error("=====CostOtherService=====Method：save=====param：" + costOtherEntity, e);
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
			costOtherDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostOtherService=====Method：delete=====param：" + id, e);
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
			List<CostOtherEntity> findAllById = costOtherDao.findAllById(idList);
			costOtherDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostOtherService=====Method：deleteAll=====param：" + ids, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return ids;
	}

	/**
	 * 按户主nm查询其他补助明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<CostOtherEntity> findOtherCostByOwnerNm(String ownerNm) {
		List<CostOtherEntity> list = null;
		try {
			list = costOtherDao.findOtherCostByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostOtherService=====Method：findOtherCostByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 按户主nm查询困难户补助明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<CostOtherEntity> findDifficultCostByOwnerNm(String ownerNm) {
		List<CostOtherEntity> list = null;
		try {
			list = costOtherDao.findDifficultCostByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostOtherService=====Method：findDifficultCostByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 查询当前户其他补助费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countOtherCostByOwnerNm(String ownerNm) {
		int countOtherCostByOwnerNm = costOtherDao.countOtherCostByOwnerNm(ownerNm);
		return countOtherCostByOwnerNm;
	}

	/**
	 * 查询当前户困难户补助费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countDifficultCostByOwnerNm(String ownerNm) {
		int countDifficultCostByOwnerNm = costOtherDao.countDifficultCostByOwnerNm(ownerNm);
		return countDifficultCostByOwnerNm;
	}

	/**
	 * 查询当前户其他补助总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountOtherCostByOwnerNm(String ownerNm) {
		BigDecimal amountOtherCostByOwnerNm = costOtherDao.amountOtherCostByOwnerNm(ownerNm);
		return amountOtherCostByOwnerNm;
	}

	/**
	 * 查询当前户困难户补助总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountDifficultCostByOwnerNm(String ownerNm) {
		BigDecimal amountDifficultCostByOwnerNm = costOtherDao.amountDifficultCostByOwnerNm(ownerNm);
		return amountDifficultCostByOwnerNm;
	}

	/**
	 * 计算其他补助费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostOtherEntity> costOtherByOwnerNm(String ownerNm) {
		List<CostOtherEntity> list = null;
		try {
			// 校验1：判断是否已搬迁安置人口核定,未核定则不允许计算费用
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error("=====搬迁安置未核定，其他补助费用无法计算==========CostOtherService=====Method：costByOwnerNm=====param："
						+ ownerNm);
				return null;
			}
			// 校验2：是否已签订协议,已签订协议不允许重新计算，直接返回历史计算结果
//			CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
//			if (costEntity != null) {
//				Integer otherStatus = costEntity.getOtherStatus();
//				if (otherStatus != null && otherStatus == CostConstant.COST_OTHER_STATE_SIGNED) {
//					log.error("=====其他补助,已签订协议==========CostOtherService=====Method：costByOwnerNm=====param：" + ownerNm);
//					list = costOtherDao.findOtherCostByOwnerNm(ownerNm);
//					return list;
//				}
//			}
			// 校验3：指标类型是否为空
			String zblx = costOwnerVO.getZblx();// 指标类型
			if (StringUtils.isBlank(zblx)) {
				log.error("=====指标类型为空，其他补助费用无法计算==========CostOtherService=====Method：costByOwnerNm=====param："
						+ ownerNm);
			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countOtherCostByOwnerNm = countOtherCostByOwnerNm(ownerNm);
			List<CostOtherEntity> costOtherEntityList = null;
			if (countOtherCostByOwnerNm > 0) {
				costOtherDao.deleteOtherCostByOwnerNm(ownerNm);
			}
			Integer status = null;
			Integer moveNum = costOwnerVO.getMoveNum();
			if (moveNum != null && moveNum > 0) {// 前置条件1：搬迁安置人口 ≥ 1
				if (StringUtils.equals(zblx, "农村")) {// 农村
					costOtherEntityList = getRura(costOwnerVO);
					status = CostConstant.COST_OTHER_STATE_RURA;
				} else if (StringUtils.equals(zblx, "集镇")) {// 集镇
					costOtherEntityList = getUrban(costOwnerVO);
					status = CostConstant.COST_OTHER_STATE_URBAN;
				}
			}

			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (costOtherEntityList == null || costOtherEntityList.isEmpty()) {
				costUpdateService.updateOtherStatus(ownerNm, CostConstant.COST_OTHER_STATE_NOT_FEE, null);
				log.error("=====其他补助费用 为空==========CostOtherService=====Method：costByOwnerNm=====param：" + ownerNm);
			} else {
				list = costOtherDao.saveAll(costOtherEntityList);
				BigDecimal amountOtherCostByOwnerNm = costOtherDao.amountOtherCostByOwnerNm(ownerNm);
				costUpdateService.updateOtherStatus(ownerNm, status, amountOtherCostByOwnerNm);
			}

			status = null;
			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostOtherService=====Method：costOtherByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 计算困难户补助费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostOtherEntity> costDifficultByOwnerNm(String ownerNm) {
		List<CostOtherEntity> list = null;
		try {
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			// 校验1：判断是否已搬迁安置人口核定,未核定则不允许计算费用
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error(
						"=====搬迁安置未核定，困难户补助费用无法计算==========CostOtherService=====Method：costDifficultByOwnerNm=====param："
								+ ownerNm);
				return null;
			}
			// 校验2：房屋与框架补偿费用未计算，不能计算困难户补助
			CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
			if (costEntity == null || costEntity.getHouseStatus() == null
					|| costEntity.getHouseStatus() == CostConstant.COST_SUB_STATE_NORMAL) {
				log.error(
						"=====房屋与框架补偿费用未计算，困难户补助费用无法计算==========CostOtherService=====Method：costDifficultByOwnerNm=====param："
								+ ownerNm);
				return null;
			}

			// 校验3：是否已签订协议,已签订协议不允许重新计算，直接返回历史计算结果
//			Integer difficultStatus = costEntity.getDifficultStatus();
//			if (difficultStatus != null && difficultStatus == CostConstant.COST_SUB_STATE_SIGNED) {
//				list = costOtherDao.findOtherCostByOwnerNm(ownerNm);
//				log.error("=====困难户补助,已签订协议==========CostOtherService=====Method：costDifficultByOwnerNm=====param：" + ownerNm);
//				return list;
//			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countDifficultCostByOwnerNm = countDifficultCostByOwnerNm(ownerNm);
			if (countDifficultCostByOwnerNm > 0) {
				costOtherDao.deleteDifficultCostByOwnerNm(ownerNm);
			}
			Integer moveNum = costOwnerVO.getMoveNum();
			if (moveNum != null && moveNum > 0) {// 前置条件1：搬迁安置人口 ≥ 1
				Integer houseStatus = costEntity.getHouseStatus();
				if (houseStatus == CostConstant.COST_SUB_STATE_FEE) {// 前置条件2：房屋补偿费用已计算
					BigDecimal amountMainHouse = costHouseDao.amountMainHouseByOwnerNm(ownerNm);
					BigDecimal framePrice = costHouseDao.framePrice(ownerNm);
					if (amountMainHouse != null && framePrice != null && amountMainHouse.compareTo(BigDecimal.ZERO) != 0
							&& framePrice.compareTo(BigDecimal.ZERO) != 0) {// 前置条件3：正房补偿费用与框架单价不能为空
						BigDecimal moveNumber = new BigDecimal(moveNum);
						BigDecimal standard = moveNumber.multiply(new BigDecimal(25)).multiply(framePrice);// 该户搬迁安置人数*25平米*框架结构单价
						if (standard.compareTo(amountMainHouse) > 0) {// 前置条件4：搬迁安置人口数*25平米*框架结构单价 > 正房补偿费用
							BigDecimal result = standard.subtract(amountMainHouse);
							CostOtherEntity costOtherEntity = new CostOtherEntity();
							costOtherEntity.setOwnerNm(ownerNm);
							costOtherEntity.setProjectName("建房困难户补助");
							costOtherEntity.setUnit("元");
							costOtherEntity.setType(CostConstant.OTHER_DIFFICULT);
							costOtherEntity.setAmount(result);
							CostOtherEntity save = costOtherDao.save(costOtherEntity);

							list = new ArrayList<>();
							list.add(save);
						}
					}
				}
			}
			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (list == null || list.isEmpty()) {
				costUpdateService.updateDifficultStatus(ownerNm, CostConstant.COST_SUB_STATE_NOT_FEE, null);
				log.error("=====困难户补助费用 为空==========CostOtherService=====Method：costByOwnerNm=====param：" + ownerNm);
			} else {
				list = costOtherDao.saveAll(list);
				BigDecimal amountDifficultCostByOwnerNm = costOtherDao.amountDifficultCostByOwnerNm(ownerNm);
				costUpdateService.updateDifficultStatus(ownerNm, CostConstant.COST_SUB_STATE_FEE, amountDifficultCostByOwnerNm);
			}

			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostOtherService=====Method：costDifficultByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 其他补助（农村）费用
	 * 
	 * @param costOwnerVO
	 * @return
	 */
	public List<CostOtherEntity> getRura(CostOwnerVO costOwnerVO) {
		Integer moveNumber = costOwnerVO.getMoveNum();
		String ownerNm = costOwnerVO.getOwnerNm();
		BigDecimal moveNum = new BigDecimal(moveNumber);// 家庭人口数
		BigDecimal households = new BigDecimal(1);// 户数

		List<CostOtherEntity> costOtherEntityList = new ArrayList<>();
		CostOtherEntity ra1 = new CostOtherEntity();
		ra1.setOwnerNm(ownerNm);
		ra1.setProjectName("移民建房补助");
		BigDecimal price1 = new BigDecimal(1000);
		ra1.setPrice(price1);
		ra1.setUnit("元/人");
		ra1.setNum(moveNum);
		ra1.setType(CostConstant.OTHER_RURA);
		BigDecimal amount1 = price1.multiply(moveNum);
		ra1.setAmount(amount1);
		costOtherEntityList.add(ra1);

		CostOtherEntity ra2 = new CostOtherEntity();
		ra2.setOwnerNm(ownerNm);
		ra2.setProjectName("农村移民沼气池或太阳能建设补助");
		BigDecimal price2 = new BigDecimal(1800);
		ra2.setPrice(price2);
		ra2.setUnit("元/户");
		ra2.setNum(households);
		ra2.setType(CostConstant.OTHER_RURA);
		BigDecimal amount2 = price2.multiply(households);
		ra2.setAmount(amount2);
		costOtherEntityList.add(ra2);

		CostOtherEntity ra3 = new CostOtherEntity();
		ra3.setOwnerNm(ownerNm);
		ra3.setProjectName("移民室内照明、供水设施补助费");
		BigDecimal price3 = new BigDecimal(200);
		ra3.setPrice(price3);
		ra3.setUnit("元/人");
		ra3.setNum(moveNum);
		ra3.setType(CostConstant.OTHER_RURA);
		BigDecimal amount3 = price3.multiply(moveNum);
		ra3.setAmount(amount3);
		costOtherEntityList.add(ra3);

		return costOtherEntityList;
	}

	/**
	 * 近迁搬迁补助费用
	 * 
	 * @param costOwnerVO
	 * @return
	 */
	public List<CostOtherEntity> getUrban(CostOwnerVO costOwnerVO) {
		Integer moveNumber = costOwnerVO.getMoveNum();
		String ownerNm = costOwnerVO.getOwnerNm();
		BigDecimal moveNum = new BigDecimal(moveNumber);

		List<CostOtherEntity> costOtherEntityList = new ArrayList<>();
		CostOtherEntity ra1 = new CostOtherEntity();
		ra1.setOwnerNm(ownerNm);
		ra1.setProjectName("移民建房补助");
		BigDecimal price1 = new BigDecimal(1000);
		ra1.setPrice(price1);
		ra1.setUnit("元/人");
		ra1.setNum(moveNum);
		ra1.setType(CostConstant.OTHER_URBAN);
		BigDecimal amount1 = price1.multiply(moveNum);
		ra1.setAmount(amount1);
		costOtherEntityList.add(ra1);

		CostOtherEntity ra2 = new CostOtherEntity();
		ra2.setOwnerNm(ownerNm);
		ra2.setProjectName("移民室内照明、供水设施补助费");
		BigDecimal price2 = new BigDecimal(120);
		ra2.setPrice(price2);
		ra2.setUnit("元/人");
		ra2.setNum(moveNum);
		ra2.setType(CostConstant.OTHER_URBAN);
		BigDecimal amount2 = price2.multiply(moveNum);
		ra2.setAmount(amount2);
		costOtherEntityList.add(ra2);

		return costOtherEntityList;
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容(农村)
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getRuraDataList(String ownerNm) {
		List<CostOtherEntity> findByOwnerNm = costOtherDao.findByOwnerNmAndType(ownerNm, CostConstant.OTHER_RURA);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostOtherEntity costOtherEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costOtherEntity.getProjectName();
				String unit = costOtherEntity.getUnit();
				String price = costOtherEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String num = costOtherEntity.getNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costOtherEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String remark = costOtherEntity.getRemark();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(projectName);
				rowData.add(unit);
				rowData.add(new BigDecimal(price));
				rowData.add(new BigDecimal(num));
				rowData.add(new BigDecimal(amount));
				rowData.add(remark);
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容(集镇)
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getUrbanDataList(String ownerNm) {
		List<CostOtherEntity> findByOwnerNm = costOtherDao.findByOwnerNmAndType(ownerNm, CostConstant.OTHER_URBAN);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostOtherEntity costOtherEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costOtherEntity.getProjectName();
				String unit = costOtherEntity.getUnit();
				String price = costOtherEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String num = costOtherEntity.getNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costOtherEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String remark = costOtherEntity.getRemark();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(projectName);
				rowData.add(unit);
				rowData.add(new BigDecimal(price));
				rowData.add(new BigDecimal(num));
				rowData.add(new BigDecimal(amount));
				rowData.add(remark);
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容(困难户)
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getDifficultDataList(String ownerNm) {
		List<CostOtherEntity> findByOwnerNm = costOtherDao.findByOwnerNmAndType(ownerNm, CostConstant.OTHER_DIFFICULT);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostOtherEntity costOtherEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costOtherEntity.getProjectName();
				String unit = costOtherEntity.getUnit();
				String price = costOtherEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String num = costOtherEntity.getNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costOtherEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String remark = costOtherEntity.getRemark();
				int serialNumber = 1 + i;// 序号
				rowData.add(serialNumber);
				rowData.add(projectName);
				rowData.add(unit);
				rowData.add(new BigDecimal(price));
				rowData.add(new BigDecimal(num));
				rowData.add(new BigDecimal(amount));
				rowData.add(remark);
				listData.add(rowData);
			}
			return listData;
		}

		return null;
	}

}
