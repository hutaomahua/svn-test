package com.lyht.business.abm.landAllocation.service;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.dao.LandPoolDao;
import com.lyht.business.abm.landAllocation.dao.LandPoolProcessDao;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.landAllocation.entity.LandPoolProcess;
import com.lyht.business.abm.removal.dao.AbmLandDao;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.abm.review.dao.PersonalWealthDataDao;
import com.lyht.business.abm.review.entity.PersonalWealth;
import com.lyht.business.abm.review.entity.PersonalWealthData;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class LandPoolProcessService {

	@Autowired
	private LandPoolProcessDao dao;

	@Autowired
	private PersonalWealthDataDao dataDao;

	@Autowired
	private LandPoolDao landPoolDao;

	@Autowired
	private AbmLandDao landDao;

	/**
	 * 查询可分解面积
	 * 
	 * @param region
	 * @param scope
	 * @param allType
	 * @param typeOne
	 * @param typeTwo
	 * @param typeThree
	 * @return
	 */
	public LyhtResultBody<Double> queryDecomposableArea(Double number, String region, String scope, String allType,
			String typeOne, String typeTwo, String typeThree, String ownerNm) {
		if (StringUtils.isBlank(region) || StringUtils.isBlank(scope) || StringUtils.isBlank(allType)
				|| StringUtils.isBlank(typeOne) || StringUtils.isBlank(typeTwo) || StringUtils.isBlank(number + "")
				|| StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		AbmLandEntity abmLandEntity = new AbmLandEntity();
		if (StringUtils.isBlank(typeThree)) {
			abmLandEntity = landDao.getByOwnerNmAndTypeTwo(ownerNm, typeTwo);
		} else {
			abmLandEntity = landDao.getByOwnerNmAndTypeThree(ownerNm, typeThree);
		}

		LandPoolEntity landPoolEntity = null;
		try {
			landPoolEntity = landPoolDao.queryBySixCondition(region, scope, allType, typeOne, typeTwo, typeThree);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}

		Double area = 0.00;
		if (landPoolEntity != null) {
			area = dao.getResidualArea(landPoolEntity.getId());
		}
		if (abmLandEntity != null) {// 将number目标值减去原土地面积得到需要的分解值 再用area原可分解面积减去当前需要的分解值得到当前剩余的可分解面积
			Double temp = number - abmLandEntity.getArea().doubleValue();// 当前所需分解面积
			//
			area = area - temp;// 再用area原可分解面积减去当前需要的分解值得到当前剩余的可分解面积
		}
		area = Double.parseDouble(String.format("%.2f", area));
		return new LyhtResultBody<>(area);
	}

	/**
	 * 添加 修改
	 * 
	 * @param landPoolProcess
	 * @return
	 */
	public LyhtResultBody<LandPoolProcess> save(LandPoolProcess landPoolProcess) {
		// 参数校验
		if (landPoolProcess == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		Integer id = landPoolProcess.getId();
		if (CommonUtil.isEmpty(id)) {
			String nm = Randomizer.generCode(10);
			landPoolProcess.setNm(nm);
		}
		LandPoolProcess result = dao.save(landPoolProcess);
		return new LyhtResultBody<>(result);
	}

	/**
	 * 删除已完成操作数据
	 * 
	 * @param personalWealth
	 */
	public void deleteData(PersonalWealth personalWealth) {
		PersonalWealthData personalWealthData = dataDao.findByMasterNm(personalWealth.getNm());
		if (StringUtils.isNotBlank(personalWealthData.getLandJson())) {// 判断是否存在土地数据
			List<AbmLandEntity> list = (List<AbmLandEntity>) JSON.parseArray(personalWealthData.getLandJson(),
					AbmLandEntity.class);
			if (list.size() > 0) {
				for (AbmLandEntity abmLandEntity : list) {
					// 删除存在分解面积关联表中关联数据 以防计算偏差
					LandPoolEntity landPoolEntity = landPoolDao.queryBySixCondition(abmLandEntity.getRegion(),
							abmLandEntity.getScope(), abmLandEntity.getAllType(), abmLandEntity.getTypeOne(),
							abmLandEntity.getTypeTwo(), abmLandEntity.getTypeThree());
					dao.deleteByWealthNmAndPoolId(personalWealth.getNm(), landPoolEntity.getId());
				}

			}

		}
	}

}
