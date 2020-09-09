package com.lyht.business.engineering.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.engineering.dao.EngineeringEvaluateDao;
import com.lyht.business.engineering.entity.EngineeringEvaluate;
import com.lyht.business.engineering.vo.EngineeringEvaluateVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class EngineeringEvaluateService {

	@Autowired
	private EngineeringEvaluateDao dao;

	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<EngineeringEvaluateVO>> page(EngineeringEvaluateVO engineeringEvaluateVO,
			LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<EngineeringEvaluateVO> page = dao.page(engineeringEvaluateVO.getEngineeringNm(), pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<EngineeringEvaluateVO> list = (List<EngineeringEvaluateVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 根据engineeringNm 删除
	 */
	@Transactional
	public int deleteByEngineeringNm(String engineeringNm) {
		return dao.deleteByEngineeringNm(engineeringNm);
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
		List<EngineeringEvaluate> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public EngineeringEvaluate findOneById(Integer id) {
		return dao.getOne(id);
	}

	/**
	 * 添加 修改
	 * 
	 * @param engineeringEvaluate
	 * @return
	 */
	public LyhtResultBody<EngineeringEvaluate> save(EngineeringEvaluate engineeringEvaluate) {
		// 参数校验
		if (engineeringEvaluate == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		
		// 内码赋值
		String nm = engineeringEvaluate.getNm();
		if (StringUtils.isBlank(nm)) {
			engineeringEvaluate.setNm(Randomizer.generCode(10));
		}
		EngineeringEvaluate result = dao.save(engineeringEvaluate);
		return new LyhtResultBody<>(result);
	}

}
