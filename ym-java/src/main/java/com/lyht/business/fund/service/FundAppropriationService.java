package com.lyht.business.fund.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.fund.dao.FundAppropriationDao;
import com.lyht.business.fund.entity.FundAppropriation;
import com.lyht.business.fund.vo.FundAppropriationDetail;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class FundAppropriationService {
	
	@Autowired
	private FundAppropriationDao dao;
	
	/**
	 * 查询列表 分页查询
	 */
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<FundAppropriationDetail>> page(LyhtPageVO lyhtPageVO,String projectName,String word){
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = dao.page(projectName, word, pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<FundAppropriationDetail> list = (List<FundAppropriationDetail>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
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
		List<FundAppropriation> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public FundAppropriation findOneById(Integer id) {
		return dao.getOne(id);
	}
	
	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	public LyhtResultBody<FundAppropriation> save(FundAppropriation fundAppropriation) {
		// 参数校验
		if (fundAppropriation == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = fundAppropriation.getNm();
		if (StringUtils.isBlank(nm)) {
			fundAppropriation.setNm(Randomizer.generCode(10));
		}
		FundAppropriation result = dao.save(fundAppropriation);
		return new LyhtResultBody<>(result);
	}

	
}
