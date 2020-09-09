package com.lyht.business.abm.review.service;

import java.util.List;

import javax.transaction.Transactional;

import com.lyht.business.abm.removal.service.AbmLandService;
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
import com.lyht.business.abm.review.dao.PersonalWealthDao;
import com.lyht.business.abm.review.dao.PersonalWealthDataDao;
import com.lyht.business.abm.review.entity.PersonalWealth;
import com.lyht.business.abm.review.entity.PersonalWealthData;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class PersonalWealthDataService {

	@Autowired
	private PersonalWealthDataDao dao;

	@Autowired
	private AbmLandDao landDao;

	@Autowired
	private PersonalWealthDao wealthDao;

	@Autowired
	private LandPoolDao landPoolDao;

	@Autowired
	private LandPoolProcessDao landPoolProcessDao;
	@Autowired
	private AbmLandService abmLandService;

	/**
	 * 添加 修改
	 *
	 * @param personalWealthData
	 * @return
	 */
	@Transactional
	public LyhtResultBody<PersonalWealthData> save(PersonalWealthData personalWealthData) {

		// 参数校验
		if (personalWealthData == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		if (!CommonUtil.isEmpty(personalWealthData.getId())) {// id 不为空的时候
			PersonalWealthData data = dao.getOne(personalWealthData.getId());
			personalWealthData.setNm(data.getNm());

		}
		// 内码赋值
		String nm = personalWealthData.getNm();
		if (CommonUtil.isEmpty(nm) || StringUtils.isBlank(nm)) {
			personalWealthData.setNm(Randomizer.generCode(10));
		}

		if (CommonUtil.isEmpty(personalWealthData.getId())) {
			if (StringUtils.isNoneBlank(personalWealthData.getMasterNm())) {
				PersonalWealthData data = dao.findByMasterNm(personalWealthData.getMasterNm());
				if (data != null) {
					personalWealthData.setId(data.getId());
				}
			}
		}
		System.out.println(personalWealthData.getImmigrantPopulationJson());
		if (!CommonUtil.isEmpty(personalWealthData.getId())) {
			PersonalWealthData data = dao.getOne(personalWealthData.getId());
			if (data != null) {
				if (personalWealthData.getImmigrantPopulationJson().equals("[]")) {
					if (StringUtils.isNotBlank(data.getImmigrantPopulationJson())) {
						personalWealthData.setImmigrantPopulationJson(data.getImmigrantPopulationJson());
					} else {
						personalWealthData.setImmigrantPopulationJson(null);
					}
				}
				if (personalWealthData.getFitmentJson().equals("[]")) {
					if (StringUtils.isNotBlank(data.getFitmentJson())) {
						personalWealthData.setFitmentJson(data.getFitmentJson());
					} else {
						personalWealthData.setFitmentJson(null);
					}
				}
				if (personalWealthData.getHouseJson().equals("[]")) {
					if (StringUtils.isNotBlank(data.getHouseJson())) {
						personalWealthData.setHouseJson(data.getHouseJson());
					} else {
						personalWealthData.setHouseJson(null);
					}

				}
				if (personalWealthData.getIndividualHouseholdJson().equals("[]")) {
					if (StringUtils.isNotBlank(data.getIndividualHouseholdJson())) {
						personalWealthData.setIndividualHouseholdJson(data.getIndividualHouseholdJson());
					} else {
						personalWealthData.setIndividualHouseholdJson(null);
					}
				}
				if (personalWealthData.getLandJson().equals("[]")) {
					if (StringUtils.isNotBlank(data.getLandJson())) {
						personalWealthData.setLandJson(data.getLandJson());
					} else {
						personalWealthData.setLandJson(null);
					}
				}
				if (personalWealthData.getOtherJson().equals("[]")) {
					if (StringUtils.isNotBlank(data.getOtherJson())) {
						personalWealthData.setOtherJson(data.getOtherJson());
					} else {
						personalWealthData.setOtherJson(null);
					}
				}
				if (personalWealthData.getTreeJson().equals("[]")) {
					if (StringUtils.isNotBlank(data.getTreeJson())) {
						personalWealthData.setTreeJson(data.getTreeJson());
					} else {
						personalWealthData.setTreeJson(null);
					}
				}
			}
		}
		if (CommonUtil.isEmpty(personalWealthData.getId())) {
			if (personalWealthData.getImmigrantPopulationJson().equals("[]")) {
				personalWealthData.setImmigrantPopulationJson(null);
			}
			if (personalWealthData.getFitmentJson().equals("[]")) {
				personalWealthData.setFitmentJson(null);
			}
			if (personalWealthData.getHouseJson().equals("[]")) {
				personalWealthData.setHouseJson(null);
			}
			if (personalWealthData.getIndividualHouseholdJson().equals("[]")) {
				personalWealthData.setIndividualHouseholdJson(null);
			}
			if (personalWealthData.getLandJson().equals("[]")) {
				personalWealthData.setLandJson(null);
			}
			if (personalWealthData.getOtherJson().equals("[]")) {
				personalWealthData.setOtherJson(null);
			}
			if (personalWealthData.getTreeJson().equals("[]")) {
				personalWealthData.setTreeJson(null);
			}
		}
		PersonalWealthData result = new PersonalWealthData();
		if (CommonUtil.isEmpty(personalWealthData.getId())) {
			result = dao.save(personalWealthData);
		} else {
			dao.updateData(personalWealthData.getId(), personalWealthData.getImmigrantPopulationJson(),
					personalWealthData.getLandJson(), personalWealthData.getHouseJson(),
					personalWealthData.getTreeJson(), personalWealthData.getOtherJson(),
					personalWealthData.getFitmentJson(), personalWealthData.getIndividualHouseholdJson());
		}

		PersonalWealth personalWealth = wealthDao.findByNm(result.getMasterNm());
		if (StringUtils.isNoneBlank(result.getLandJson()) && !result.getLandJson().equals("[]")) {
			String jsonString = result.getLandJson();
			List<AbmLandEntity> newList = (List<AbmLandEntity>) JSON.parseArray(jsonString, AbmLandEntity.class);// 拿到更改后数据
			List<AbmLandEntity> oldList = abmLandService.findByOwnerNm(personalWealth.getOwnerNm());// 拿到原始数据
			// 将原始数据 与新数据比较 拿到差距值大于或小于0的数据查到分解池中id 后绑定插入数据到land_pool_process表中
			for (int i = 0; i < newList.size(); i++) {
				AbmLandEntity newEntity = newList.get(i);
				AbmLandEntity oldEntity = oldList.get(i);
				Double newArea = newEntity.getArea().doubleValue();// 更改后面积
				Double oldArea = oldEntity.getArea().doubleValue();// 原始面积
				if ((newArea - oldArea) != 0) {// 如果偏差值不为0 则数据发生改变
					LandPoolProcess landPoolProcess = new LandPoolProcess();
					LandPoolEntity landPoolEntity = landPoolDao.queryBySixCondition(newEntity.getRegion(),
							newEntity.getScope(), newEntity.getAllType(), newEntity.getTypeOne(),
							newEntity.getTypeTwo(), newEntity.getTypeThree());
					landPoolProcess.setPoolId(landPoolEntity.getId());
					landPoolProcess.setWealthNm(personalWealth.getNm());
					landPoolProcessDao.save(landPoolProcess);
				}
			}
		}
		return new LyhtResultBody<>(result);
	}

}
