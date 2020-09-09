package com.lyht.business.abm.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.abm.wechat.dao.AbmWechatFamilyDao;
import com.lyht.business.abm.wechat.vo.AbmWechatFamilyVO;

@Service
public class AbmWechatFamilyService {

	@Autowired
	private AbmWechatFamilyDao abmWechatFamilyDao;

	/**
	 * 人口信息查询
	 * 
	 * @param ownerNm 户主nm
	 * @return
	 */
	public List<AbmWechatFamilyVO> list(String ownerNm) {
		List<AbmWechatFamilyVO> list = abmWechatFamilyDao.list(ownerNm);
		return list;
	}

}
