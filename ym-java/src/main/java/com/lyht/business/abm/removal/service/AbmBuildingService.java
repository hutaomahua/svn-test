package com.lyht.business.abm.removal.service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.abm.removal.dao.AbmBuildingDao;
import com.lyht.business.abm.removal.entity.AbmBuildingEntity;

import java.util.List;

import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbmBuildingService {

	@Autowired
	private AbmBuildingDao abmBuildingDao;

	/**
	 * 通过户主nm查询附属建筑物
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<AbmBuildingEntity> findByOwnerNm(String ownerNm) {
		return JSON.parseArray(JSON.toJSONString(abmBuildingDao.findByOwnerNm(ownerNm)), AbmBuildingEntity.class);
	}

	/**
	 * 批量新增，修改
	 * 
	 * @param buildingList
	 * @return
	 */
	public List<AbmBuildingEntity> saveAll(List<AbmBuildingEntity> buildingList) {
		List<AbmBuildingEntity> saveAll = abmBuildingDao.saveAll(buildingList);
		return saveAll;
	}

}
