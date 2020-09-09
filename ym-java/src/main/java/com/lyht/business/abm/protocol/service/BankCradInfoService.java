package com.lyht.business.abm.protocol.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.protocol.dao.BankCradInfoDao;
import com.lyht.business.abm.protocol.entity.BankCradInfo;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class BankCradInfoService {
	
	@Autowired
	BankCradInfoDao dao;

	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Integer> delete(Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		dao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public LyhtResultBody<String> batchDel(String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<BankCradInfo> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public BankCradInfo findOneById(Integer id) {
		return dao.getOne(id);
	}

	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	public BankCradInfo save(BankCradInfo bankCradInfo) {
		// 参数校验
		if (bankCradInfo == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = bankCradInfo.getNm();
		if (StringUtils.isBlank(nm)) {
			bankCradInfo.setNm(Randomizer.generCode(10));
		}
		BankCradInfo result = dao.save(bankCradInfo);
		return result;
	}

	
}
