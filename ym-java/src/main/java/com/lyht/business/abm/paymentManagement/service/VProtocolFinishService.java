package com.lyht.business.abm.paymentManagement.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyht.business.abm.paymentManagement.dao.VProtocolFinishDao;
import com.lyht.business.abm.paymentManagement.entity.VProtocolFinish;
import com.lyht.business.abm.paymentManagement.to.Msg;
import com.lyht.business.abm.paymentManagement.vo.OwnerSelectVO;
import com.lyht.util.CommonUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class VProtocolFinishService {

	@Autowired
	private VProtocolFinishDao dao;
	
	public List<VProtocolFinish> protocolList(){
		return dao.findAll();
	}

	public VProtocolFinish findByProtocolCode(String protocolCode) {
		return dao.findByProtocolCode(protocolCode);
	}

	public List<VProtocolFinish> getByOwnerNmAndProtocolType(String ownerNm, String protocolTypeSection) {
		List<String> typeSection = Arrays.asList(protocolTypeSection.split(","));
		return dao.getByOwnerNmAndProtocolType(ownerNm,typeSection);
	}

	/**
	 * 查询资金代管协议数
	 * @param ownernm
	 */
	public Integer getEscrowProtocolCount(String ownernm) {
		if("".equals(CommonUtil.trim(ownernm)))return 0;
		Integer count = dao.getEscrowProtocolCount(ownernm);
		return count;
	}

	public Msg<VProtocolFinish> getByCodeAndFlag(String protocolCode, Integer protocolType) {
		if("".equals(CommonUtil.trim(protocolCode)))return new Msg<VProtocolFinish>(false,"协议编码为空");
		if(protocolType == null || !(protocolType == 0 || protocolType == 1))return new Msg<VProtocolFinish>(false,"未知的协议类型");
		Msg<VProtocolFinish> result = new Msg<VProtocolFinish>(dao.getByCodeAndFlag(protocolCode,protocolType));
		return result;
	}

	/**
	 * 查询可发起兑付申请的权属人
	 * @param protocolTyoe 
	 * @return
	 */
	public List<OwnerSelectVO> getOwnerSelectList(String protocolTyoe) {
		return dao.getOwnerSelectList(protocolTyoe);
	}
	
}
