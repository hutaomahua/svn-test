package com.lyht.business.engineering.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.engineering.dao.EngineeringCenterResidentDao;
import com.lyht.business.engineering.entity.EngineeringCenterResident;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineeringCenterResidentService {

	@Autowired
	private EngineeringCenterResidentDao dao;
	
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
		List<EngineeringCenterResident> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public EngineeringCenterResident findOneById(Integer id) {
		return dao.getOne(id);
	}
	
	/**
	 * 添加 修改
	 * 
	 * @param engineeringEvaluate
	 * @return
	 */
	public LyhtResultBody<EngineeringCenterResident> save(EngineeringCenterResident engineeringEvaluate) {
		// 参数校验
		if (engineeringEvaluate == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = engineeringEvaluate.getNm();
		if (StringUtils.isBlank(nm)) {
			engineeringEvaluate.setNm(Randomizer.generCode(10));
		}
		EngineeringCenterResident result = dao.save(engineeringEvaluate);
		return new LyhtResultBody<>(result);
	}
	
	
}
