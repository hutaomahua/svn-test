package com.lyht.business.fund.service;

import java.util.List;

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
import com.lyht.business.fund.dao.FundCostDao;
import com.lyht.business.fund.entity.FundCost;
import com.lyht.business.fund.vo.FundCostVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class FundCostService {

	@Autowired
	private FundCostDao dao;

	/**
	 * 分页查询
	 * 
	 * @param amountType 费用类型 独立费用/预备费用
	 * @param organType  类型 企业/地方政府
	 * @param word       模糊查询 企业/地方政府名称 项目名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<FundCostVO>> page(LyhtPageVO lyhtPageVO,String amountType, String organType, String word) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<FundCostVO> page = dao.page(amountType, organType, word,pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<FundCostVO> list = (List<FundCostVO>) JSON.parse(jsonString);
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
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<FundCost> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}
	
	/**
	 * 根据id查询
	 */
	public FundCost getById(Integer id){
		return dao.getOne(id);
	}

	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	public LyhtResultBody<FundCost> save(FundCost fundCost) {
		// 参数校验
		if (fundCost == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = fundCost.getNm();
		if (StringUtils.isBlank(nm)) {
			fundCost.setNm(Randomizer.generCode(10));
		}
		FundCost result = dao.save(fundCost);
		return new LyhtResultBody<>(result);
	}

}
