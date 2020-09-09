package com.lyht.business.abm.removal.service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.abm.removal.dao.AbmHousesDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmHouseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbmHouseService {

	@Autowired
	private AbmHousesDao abmHousesDao;

	/**
	 * 通过户主nm查询房屋
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<AbmHouseEntity> findByOwnerNm(String ownerNm) {
		return JSON.parseArray(JSON.toJSONString(abmHousesDao.findByOwnerNm(ownerNm)), AbmHouseEntity.class);
	}

	/**
	 * 批量新增，修改
	 * 
	 * @param houseList
	 * @return
	 */
	public List<AbmHouseEntity> saveAll(List<AbmHouseEntity> houseList) {
		List<AbmHouseEntity> saveAll = abmHousesDao.saveAll(houseList);
		return saveAll;
	}

}
