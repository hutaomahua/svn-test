package com.lyht.business.abm.removal.service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.abm.removal.dao.AbmLandDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmLandEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbmLandService {

	@Autowired
	private AbmLandDao abmLandDao;

	/**
	 * 通过户主nm查询土地
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<AbmLandEntity> findByOwnerNm(String ownerNm) {
		return JSON.parseArray(JSON.toJSONString(abmLandDao.findByOwnerNm(ownerNm)), AbmLandEntity.class);
	}

	/**
	 * 批量新增,修改
	 * 
	 * @param landList
	 */
	public List<AbmLandEntity> saveAll(List<AbmLandEntity> landList) {
		List<AbmLandEntity> saveAll = abmLandDao.saveAll(landList);
		return saveAll;
	}

}
