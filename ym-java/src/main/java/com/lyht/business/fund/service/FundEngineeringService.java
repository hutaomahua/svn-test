package com.lyht.business.fund.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.fund.dao.FundEngineeringDao;
import com.lyht.business.fund.entity.FundEngineering;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class FundEngineeringService {
	
	@Autowired
	private FundEngineeringDao dao;
	
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
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<FundEngineering> deleteFundEngineering = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundEngineering);
		return new LyhtResultBody<>(ids);
	}
	
	/**
	 * 根据id查询
	 */
	public FundEngineering getById(Integer id){
		return dao.getOne(id);
	}

	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	public LyhtResultBody<FundEngineering> save(FundEngineering fundEngineering) {
		// 参数校验
		if (fundEngineering == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = fundEngineering.getNm();
		if (StringUtils.isBlank(nm)) {
			fundEngineering.setNm(Randomizer.generCode(10));
		}
		FundEngineering result = dao.save(fundEngineering);
		return new LyhtResultBody<>(result);
	}

	

}
