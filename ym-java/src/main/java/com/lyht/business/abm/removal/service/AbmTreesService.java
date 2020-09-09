package com.lyht.business.abm.removal.service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.abm.removal.dao.AbmTreesDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmTreesEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbmTreesService {

	@Autowired
	private AbmTreesDao abmTreesDao;

	/**
	 * 通过户主nm查询零星树木
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<AbmTreesEntity> findByOwnerNm(String ownerNm) {
		return JSON.parseArray(JSON.toJSONString(abmTreesDao.findByOwnerNm(ownerNm)), AbmTreesEntity.class);
	}

	/**
	 * 批量新增，修改
	 * 
	 * @param treeList
	 * @return
	 */
	public List<AbmTreesEntity> saveAll(List<AbmTreesEntity> treeList) {
		List<AbmTreesEntity> saveAll = abmTreesDao.saveAll(treeList);
		return saveAll;
	}

}
