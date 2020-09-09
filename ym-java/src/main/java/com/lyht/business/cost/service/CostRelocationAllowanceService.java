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
import com.lyht.business.cost.dao.CostRelocationAllowanceDao;
import com.lyht.business.cost.entity.CostRelocationAllowanceEntity;
import com.lyht.business.cost.vo.CostOwnerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CostRelocationAllowanceService {
	@Autowired
	private CostRelocationAllowanceDao costRelocationAllowanceDao;

	@Autowired
	private CostUpdateService costUpdateService;

	@Autowired
	private CostDao costDao;

	/**
	 * 新增，修改
	 * 
	 * @param costRelocationAllowanceEntity
	 * @return
	 */
	public CostRelocationAllowanceEntity save(CostRelocationAllowanceEntity costRelocationAllowanceEntity) {
		CostRelocationAllowanceEntity save = null;
		try {
			save = costRelocationAllowanceDao.save(costRelocationAllowanceEntity);
		} catch (Exception e) {
			log.error("=====CostRelocationAllowanceService=====Method：save=====param：" + costRelocationAllowanceEntity,
					e);
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
			costRelocationAllowanceDao.deleteById(id);
		} catch (Exception e) {
			log.error("=====CostRelocationAllowanceService=====Method：delete=====param：" + id, e);
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
			List<CostRelocationAllowanceEntity> findAllById = costRelocationAllowanceDao.findAllById(idList);
			costRelocationAllowanceDao.deleteInBatch(findAllById);
		} catch (Exception e) {
			log.error("=====CostRelocationAllowanceService=====Method：deleteAll=====param：" + ids, e);
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
	public List<CostRelocationAllowanceEntity> findByOwnerNm(String ownerNm) {
		List<CostRelocationAllowanceEntity> list = null;
		try {
			list = costRelocationAllowanceDao.findByOwnerNm(ownerNm);
		} catch (Exception e) {
			log.error("=====CostRelocationAllowanceService=====Method：findByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 查询当前户搬迁补助费用记录是否存在（记录数）
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countByOwnerNm(String ownerNm) {
		int countByOwnerNm = costRelocationAllowanceDao.countByOwnerNm(ownerNm);
		return countByOwnerNm;
	}

	/**
	 * 查询当前户搬迁补助总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	public BigDecimal amountByOwnerNm(String ownerNm) {
		BigDecimal amountByOwnerNm = costRelocationAllowanceDao.amountByOwnerNm(ownerNm);
		return amountByOwnerNm;
	}

	/**
	 * 计算搬迁补助费（按户主NM计算）
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public List<CostRelocationAllowanceEntity> costByOwnerNm(String ownerNm) {
		List<CostRelocationAllowanceEntity> list = null;
		try {
			// 校验1：判断是否已搬迁安置人口核定,未核定则不允许计算费用
			CostOwnerVO costOwnerVO = costDao.findCostOwner(ownerNm);
			if (costOwnerVO == null || !StringUtils.equals(costOwnerVO.getIsSatisfy(), CostConstant.MOVE_CHECKED)) {
				log.error(
						"=====搬迁安置未核定，搬迁补助费用无法计算==========CostRelocationAllowanceService=====Method：costByOwnerNm=====param："
								+ ownerNm);
				return null;
			}
			// 校验2：是否已签订协议,已签订协议不允许重新计算，直接返回历史计算结果
//			CostEntity costEntity = costDao.findByOwnerNm(ownerNm);
//			if (costEntity != null) {
//				Integer relocationAllowanceStatus = costEntity.getRelocationAllowanceStatus();
//				if (relocationAllowanceStatus != null && relocationAllowanceStatus == CostConstant.COST_RELOCATION_STATE_SIGNED) {
//					log.error("=====搬迁补助,已签订协议==========CostRelocationAllowanceService=====Method：costByOwnerNm=====param：" + ownerNm);
//					list = costRelocationAllowanceDao.findByOwnerNm(ownerNm);
//					return list;
//				}
//			}
			// step1：查看是否有计算结果，如果有则重新计算
			int countByOwnerNm = countByOwnerNm(ownerNm);
			List<CostRelocationAllowanceEntity> costRelocationAllowanceEntityList = null;
			if (countByOwnerNm > 0) {
				costRelocationAllowanceDao.deleteByOwnerNm(ownerNm);
			}

			Integer status = null;
			Integer moveNum = costOwnerVO.getMoveNum();
			if (moveNum != null && moveNum > 0) {// 前置条件1：搬迁安置人口 ≥ 1
				String towns = costOwnerVO.getTowns();// 安置地点（乡镇）
				if (StringUtils.equals(towns, "保和")) {// 远迁
					costRelocationAllowanceEntityList = getRemote(costOwnerVO);
					status = CostConstant.COST_RELOCATION_STATE_REMOTE;
				} else {// 近迁
					costRelocationAllowanceEntityList = getRecent(costOwnerVO);
					status = CostConstant.COST_RELOCATION_STATE_RECENT;
				}
			}

			// step2：判断是否有计算结果和保存计算结果，并修改该项目的计算状态
			if (costRelocationAllowanceEntityList == null || costRelocationAllowanceEntityList.isEmpty()) {
				costUpdateService.updateRelocationAllowanceStatus(ownerNm, CostConstant.COST_RELOCATION_STATE_NOT_FEE,
						null);
				log.error("=====搬迁补助费用 为空=====CostRelocationAllowanceService=====Method：costByOwnerNm=====param："
						+ ownerNm);
			} else {
				list = costRelocationAllowanceDao.saveAll(costRelocationAllowanceEntityList);
				BigDecimal amountByOwnerNm = costRelocationAllowanceDao.amountByOwnerNm(ownerNm);
				costUpdateService.updateRelocationAllowanceStatus(ownerNm, status, amountByOwnerNm);
			}

			// step3：修改当前户总补偿费用记录状态
			costUpdateService.updateStatus(ownerNm);// 更新当前户的补偿费用计算状态（如果不存在，则新增一条记录并改变状态）
			costUpdateService.updateMoveAmount(ownerNm);// 更新当前户的搬迁安置补偿费用总额
			costUpdateService.updateProductionAmount(ownerNm);// 更新当前户的生产安置补偿费用总额
		} catch (Exception e) {
			log.error("=====CostRelocationAllowanceService=====Method：costByOwnerNm=====param：" + ownerNm, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return list;
	}

	/**
	 * 远迁搬迁补助费用
	 * 
	 * @param costOwnerVO
	 * @return
	 */
	public List<CostRelocationAllowanceEntity> getRemote(CostOwnerVO costOwnerVO) {
		Integer moveNumber = costOwnerVO.getMoveNum();
		String ownerNm = costOwnerVO.getOwnerNm();
		BigDecimal moveNum = new BigDecimal(moveNumber);

		List<CostRelocationAllowanceEntity> costRelocationAllowanceEntityList = new ArrayList<>();
		CostRelocationAllowanceEntity ra1 = new CostRelocationAllowanceEntity();
		ra1.setOwnerNm(ownerNm);
		ra1.setProjectName("人员搬迁补助费,交通补助费");
		BigDecimal price1 = new BigDecimal(160);
		ra1.setPrice(price1);
		ra1.setUnit("元/人");
		ra1.setNum(moveNum);
		ra1.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount1 = price1.multiply(moveNum);
		ra1.setAmount(amount1);
		costRelocationAllowanceEntityList.add(ra1);

		CostRelocationAllowanceEntity ra2 = new CostRelocationAllowanceEntity();
		ra2.setOwnerNm(ownerNm);
		ra2.setProjectName("人员搬迁补助费,搬迁保险费");
		BigDecimal price2 = new BigDecimal(120);
		ra2.setPrice(price2);
		ra2.setUnit("元/人");
		ra2.setNum(moveNum);
		ra2.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount2 = price2.multiply(moveNum);
		ra2.setAmount(amount2);
		costRelocationAllowanceEntityList.add(ra2);

		CostRelocationAllowanceEntity ra3 = new CostRelocationAllowanceEntity();
		ra3.setOwnerNm(ownerNm);
		ra3.setProjectName("人员搬迁补助费,途中食宿费");
		BigDecimal price3 = new BigDecimal(40);
		ra3.setPrice(price3);
		ra3.setUnit("元/人");
		ra3.setNum(moveNum);
		ra3.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount3 = price3.multiply(moveNum);
		ra3.setAmount(amount3);
		costRelocationAllowanceEntityList.add(ra3);

		CostRelocationAllowanceEntity ra4 = new CostRelocationAllowanceEntity();
		ra4.setOwnerNm(ownerNm);
		ra4.setProjectName("人员搬迁补助费,途中医疗费");
		BigDecimal price4 = new BigDecimal(20);
		ra4.setPrice(price4);
		ra4.setUnit("元/人");
		ra4.setNum(moveNum);
		ra4.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount4 = price4.multiply(moveNum);
		ra4.setAmount(amount4);
		costRelocationAllowanceEntityList.add(ra4);

		CostRelocationAllowanceEntity ra5 = new CostRelocationAllowanceEntity();
		ra5.setOwnerNm(ownerNm);
		ra5.setProjectName("人员搬迁补助费,搬迁误工补助费");
		BigDecimal price5 = new BigDecimal(133);
		ra5.setPrice(price5);
		ra5.setUnit("元/人");
		ra5.setNum(moveNum);
		ra5.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount5 = price5.multiply(moveNum);
		ra5.setAmount(amount5);
		costRelocationAllowanceEntityList.add(ra5);

		CostRelocationAllowanceEntity ra6 = new CostRelocationAllowanceEntity();
		ra6.setOwnerNm(ownerNm);
		ra6.setProjectName("物资设备搬迁运输补助费,汽车运输");
		BigDecimal price6 = new BigDecimal(150);
		ra6.setPrice(price6);
		ra6.setUnit("元/人");
		ra6.setNum(moveNum);
		ra6.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount6 = price6.multiply(moveNum);
		ra6.setAmount(amount6);
		costRelocationAllowanceEntityList.add(ra6);

		CostRelocationAllowanceEntity ra7 = new CostRelocationAllowanceEntity();
		ra7.setOwnerNm(ownerNm);
		ra7.setProjectName("物资设备搬迁运输补助费,人工运输");
		BigDecimal price7 = new BigDecimal(300);
		ra7.setPrice(price7);
		ra7.setUnit("元/人");
		ra7.setNum(moveNum);
		ra7.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount7 = price7.multiply(moveNum);
		ra7.setAmount(amount7);
		costRelocationAllowanceEntityList.add(ra7);

		CostRelocationAllowanceEntity ra8 = new CostRelocationAllowanceEntity();
		ra8.setOwnerNm(ownerNm);
		ra8.setProjectName("物资设备搬迁运输补助费,物资设备损失补偿费");
		BigDecimal price8 = new BigDecimal(500);
		ra8.setPrice(price8);
		ra8.setUnit("元/人");
		ra8.setNum(moveNum);
		ra8.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount8 = price8.multiply(moveNum);
		ra8.setAmount(amount8);
		costRelocationAllowanceEntityList.add(ra8);

		CostRelocationAllowanceEntity ra9 = new CostRelocationAllowanceEntity();
		ra9.setOwnerNm(ownerNm);
		ra9.setProjectName("建房期补助费,住房补助");
		BigDecimal price9 = new BigDecimal(10905);
		ra9.setPrice(price9);
		ra9.setUnit("元/人");
		ra9.setNum(moveNum);
		ra9.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount9 = price9.multiply(moveNum);
		ra9.setAmount(amount9);
		costRelocationAllowanceEntityList.add(ra9);

		CostRelocationAllowanceEntity ra10 = new CostRelocationAllowanceEntity();
		ra10.setOwnerNm(ownerNm);
		ra10.setProjectName("建房期补助费,交通补助费");
		BigDecimal price10 = new BigDecimal(160);
		ra10.setPrice(price10);
		ra10.setUnit("元/人");
		ra10.setNum(moveNum);
		ra10.setType(CostConstant.MOVE_REMOTE);
		BigDecimal amount10 = price10.multiply(moveNum);
		ra10.setAmount(amount10);
		costRelocationAllowanceEntityList.add(ra10);

		return costRelocationAllowanceEntityList;
	}

	/**
	 * 近迁搬迁补助费用
	 * 
	 * @param costOwnerVO
	 * @return
	 */
	public List<CostRelocationAllowanceEntity> getRecent(CostOwnerVO costOwnerVO) {
		Integer moveNumber = costOwnerVO.getMoveNum();
		String ownerNm = costOwnerVO.getOwnerNm();
		BigDecimal moveNum = new BigDecimal(moveNumber);

		List<CostRelocationAllowanceEntity> costRelocationAllowanceEntityList = new ArrayList<>();
		CostRelocationAllowanceEntity ra1 = new CostRelocationAllowanceEntity();
		ra1.setOwnerNm(ownerNm);
		ra1.setProjectName("人员搬迁补助费,交通补助费");
		BigDecimal price1 = new BigDecimal(40);
		ra1.setPrice(price1);
		ra1.setUnit("元/人");
		ra1.setNum(moveNum);
		ra1.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount1 = price1.multiply(moveNum);
		ra1.setAmount(amount1);
		costRelocationAllowanceEntityList.add(ra1);

		CostRelocationAllowanceEntity ra2 = new CostRelocationAllowanceEntity();
		ra2.setOwnerNm(ownerNm);
		ra2.setProjectName("人员搬迁补助费,搬迁保险费");
		BigDecimal price2 = new BigDecimal(120);
		ra2.setPrice(price2);
		ra2.setUnit("元/人");
		ra2.setNum(moveNum);
		ra2.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount2 = price2.multiply(moveNum);
		ra2.setAmount(amount2);
		costRelocationAllowanceEntityList.add(ra2);

		CostRelocationAllowanceEntity ra3 = new CostRelocationAllowanceEntity();
		ra3.setOwnerNm(ownerNm);
		ra3.setProjectName("人员搬迁补助费,途中食宿费");
		BigDecimal price3 = new BigDecimal(40);
		ra3.setPrice(price3);
		ra3.setUnit("元/人");
		ra3.setNum(moveNum);
		ra3.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount3 = price3.multiply(moveNum);
		ra3.setAmount(amount3);
		costRelocationAllowanceEntityList.add(ra3);

		CostRelocationAllowanceEntity ra4 = new CostRelocationAllowanceEntity();
		ra4.setOwnerNm(ownerNm);
		ra4.setProjectName("人员搬迁补助费,途中医疗费");
		BigDecimal price4 = new BigDecimal(20);
		ra4.setPrice(price4);
		ra4.setUnit("元/人");
		ra4.setNum(moveNum);
		ra4.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount4 = price4.multiply(moveNum);
		ra4.setAmount(amount4);
		costRelocationAllowanceEntityList.add(ra4);

		CostRelocationAllowanceEntity ra5 = new CostRelocationAllowanceEntity();
		ra5.setOwnerNm(ownerNm);
		ra5.setProjectName("人员搬迁补助费,搬迁误工补助费");
		BigDecimal price5 = new BigDecimal(133);
		ra5.setPrice(price5);
		ra5.setUnit("元/人");
		ra5.setNum(moveNum);
		ra5.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount5 = price5.multiply(moveNum);
		ra5.setAmount(amount5);
		costRelocationAllowanceEntityList.add(ra5);

		CostRelocationAllowanceEntity ra6 = new CostRelocationAllowanceEntity();
		ra6.setOwnerNm(ownerNm);
		ra6.setProjectName("物资设备搬迁运输补助费,汽车运输");
		BigDecimal price6 = new BigDecimal(38);
		ra6.setPrice(price6);
		ra6.setUnit("元/人");
		ra6.setNum(moveNum);
		ra6.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount6 = price6.multiply(moveNum);
		ra6.setAmount(amount6);
		costRelocationAllowanceEntityList.add(ra6);

		CostRelocationAllowanceEntity ra7 = new CostRelocationAllowanceEntity();
		ra7.setOwnerNm(ownerNm);
		ra7.setProjectName("物资设备搬迁运输补助费,人工运输");
		BigDecimal price7 = new BigDecimal(300);
		ra7.setPrice(price7);
		ra7.setUnit("元/人");
		ra7.setNum(moveNum);
		ra7.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount7 = price7.multiply(moveNum);
		ra7.setAmount(amount7);
		costRelocationAllowanceEntityList.add(ra7);

		CostRelocationAllowanceEntity ra8 = new CostRelocationAllowanceEntity();
		ra8.setOwnerNm(ownerNm);
		ra8.setProjectName("物资设备搬迁运输补助费,物资设备损失补偿费");
		BigDecimal price8 = new BigDecimal(500);
		ra8.setPrice(price8);
		ra8.setUnit("元/人");
		ra8.setNum(moveNum);
		ra8.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount8 = price8.multiply(moveNum);
		ra8.setAmount(amount8);
		costRelocationAllowanceEntityList.add(ra8);

		CostRelocationAllowanceEntity ra9 = new CostRelocationAllowanceEntity();
		ra9.setOwnerNm(ownerNm);
		ra9.setProjectName("建房期补助费,住房补助");
		BigDecimal price9 = new BigDecimal(10905);
		ra9.setPrice(price9);
		ra9.setUnit("元/人");
		ra9.setNum(moveNum);
		ra9.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount9 = price9.multiply(moveNum);
		ra9.setAmount(amount9);
		costRelocationAllowanceEntityList.add(ra9);

		CostRelocationAllowanceEntity ra10 = new CostRelocationAllowanceEntity();
		ra10.setOwnerNm(ownerNm);
		ra10.setProjectName("建房期补助费,交通补助费");
		BigDecimal price10 = new BigDecimal(40);
		ra10.setPrice(price10);
		ra10.setUnit("元/人");
		ra10.setNum(moveNum);
		ra10.setType(CostConstant.MOVE_RECENT);
		BigDecimal amount10 = price10.multiply(moveNum);
		ra10.setAmount(amount10);
		costRelocationAllowanceEntityList.add(ra10);

		return costRelocationAllowanceEntityList;
	}

	/**
	 * 获取导出补偿费用手册对应项的sheet内容(近迁)
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getRecentDataList(String ownerNm) {
		List<CostRelocationAllowanceEntity> findByOwnerNm = costRelocationAllowanceDao.findByOwnerNmAndType(ownerNm,
				CostConstant.MOVE_RECENT);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostRelocationAllowanceEntity costRelocationAllowanceEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costRelocationAllowanceEntity.getProjectName();
				String unit = costRelocationAllowanceEntity.getUnit();
				String price = costRelocationAllowanceEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String num = costRelocationAllowanceEntity.getNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costRelocationAllowanceEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
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

	/**
	 * 获取导出补偿费用手册对应项的sheet内容(远迁)
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<List<Object>> getRemoteDataList(String ownerNm) {
		List<CostRelocationAllowanceEntity> findByOwnerNm = costRelocationAllowanceDao.findByOwnerNmAndType(ownerNm,
				CostConstant.MOVE_REMOTE);
		if (findByOwnerNm != null && !findByOwnerNm.isEmpty()) {
			List<List<Object>> listData = new ArrayList<>();
			for (int i = 0; i < findByOwnerNm.size(); i++) {
				CostRelocationAllowanceEntity costRelocationAllowanceEntity = findByOwnerNm.get(i);
				List<Object> rowData = new ArrayList<>();
				String projectName = costRelocationAllowanceEntity.getProjectName();
				String unit = costRelocationAllowanceEntity.getUnit();
				String price = costRelocationAllowanceEntity.getPrice().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String num = costRelocationAllowanceEntity.getNum().setScale(4, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
				String amount = costRelocationAllowanceEntity.getAmount().setScale(2, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros().toPlainString();
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
