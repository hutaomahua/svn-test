package com.lyht.business.engineering.service;

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
import com.lyht.business.engineering.dao.EngineeringConservationDao;
import com.lyht.business.engineering.entity.EngineeringConservation;
import com.lyht.business.engineering.vo.EngineeringConservationVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class EngineeringConservationService {

	@Autowired
	private EngineeringConservationDao dao;
	
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<EngineeringConservationVO>> page(EngineeringConservationVO engineeringConservationVO,LyhtPageVO lyhtPageVO){
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<EngineeringConservationVO> page = dao.page(engineeringConservationVO.getProjectName(),pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<EngineeringConservationVO> list = (List<EngineeringConservationVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 单个删除888
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
		List<EngineeringConservation> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public EngineeringConservation findOneById(Integer id) {
		return dao.getOne(id);
	}
	
	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	public LyhtResultBody<EngineeringConservation> save(EngineeringConservation engineeringConservation) {
		// 参数校验
		if (engineeringConservation == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = engineeringConservation.getNm();
		if (StringUtils.isBlank(nm)) {
			engineeringConservation.setNm(Randomizer.generCode(10));
		}
		EngineeringConservation result = dao.save(engineeringConservation);
		return new LyhtResultBody<>(result);
	}
	
}
