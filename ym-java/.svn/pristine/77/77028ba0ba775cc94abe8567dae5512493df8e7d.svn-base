package com.lyht.business.abm.removal.service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.abm.removal.dao.AbmHousesDecorationDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmHousesDecorationEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbmHouseDecorationService {

	@Autowired
	private AbmHousesDecorationDao abmHousesDecorationDao;

	/**
	 * 通过户主nm查询房屋装修
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<AbmHousesDecorationEntity> findByOwnerNm(String ownerNm) {
		return JSON.parseArray(JSON.toJSONString(abmHousesDecorationDao.findByOwnerNm(ownerNm)), AbmHousesDecorationEntity.class);
	}

	/**
	 * 批量新增，修改
	 * 
	 * @param housesDecorationList
	 * @return
	 */
	public List<AbmHousesDecorationEntity> saveAll(List<AbmHousesDecorationEntity> housesDecorationList) {
		List<AbmHousesDecorationEntity> saveAll = abmHousesDecorationDao.saveAll(housesDecorationList);
		return saveAll;
	}

}
