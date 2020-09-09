package com.lyht.business.info.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.info.dao.BankCardInfoDao;
import com.lyht.business.info.entity.BankCardInfoEntity;
import com.lyht.business.info.to.Msg;
import com.lyht.business.info.vo.BankVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class BankCardInfoService {

	@Autowired
	private BankCardInfoDao bankCardInfoDao;
	
	public List<BankCardInfoEntity> list(){
		List<BankCardInfoEntity> findAll = bankCardInfoDao.findAll();
		return findAll;
	}
	
	public Msg<BankCardInfoEntity> saveOrUpdata(BankCardInfoEntity bankCardInfo) {
		if("".equals(CommonUtil.trim(bankCardInfo.getNm()))){			
			bankCardInfo.setBankNm(Randomizer.generCode(10));
		}
		BankCardInfoEntity entity = bankCardInfoDao.save(bankCardInfo);
		return new Msg<BankCardInfoEntity>(entity);
	}
	
	/**
	 * 获取字典表中的银行列表
	 * @return 
	 */
	public List<BankVO> getBankDict() {
		List<BankVO> list = bankCardInfoDao.getBankDict();
		return list;
	}
	
}
