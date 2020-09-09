package com.lyht.business.abm.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.abm.wechat.dao.AbmWechatInfoDao;
import com.lyht.business.abm.wechat.vo.AbmWechatInfoVO;

@Service
public class AbmWechatInfoService {

	@Autowired
	private AbmWechatInfoDao abmWechatInfoDao;

	/**
	 * 指标信息查询
	 * 
	 * @param ownerNm 户主nm
	 * @return
	 */
	public List<AbmWechatInfoVO> list(String ownerNm) {
		List<AbmWechatInfoVO> list = abmWechatInfoDao.list(ownerNm);
		return list;
	}

}
