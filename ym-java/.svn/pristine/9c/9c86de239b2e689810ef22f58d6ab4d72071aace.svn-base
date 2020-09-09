package com.lyht.business.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.info.dao.InfoEnterpriseDao;
import com.lyht.business.info.vo.InfoEnterpriseDetailVO;

@Service
public class InfoEnterpriseService {
	
	@Autowired
	private InfoEnterpriseDao infoEnterpriseDao;
	
	public InfoEnterpriseDetailVO findOneByNm(String nm) {
		return infoEnterpriseDao.findOneByNm(nm);
	}
	
}
