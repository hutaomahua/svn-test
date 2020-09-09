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
import com.lyht.business.engineering.dao.EngineeringImplementationInfoDao;
import com.lyht.business.engineering.entity.EngineeringImplementationInfo;
import com.lyht.business.engineering.vo.EngineeringImplementationInfoVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class EngineeringImplementationInfoService {
	
	@Autowired
	private EngineeringImplementationInfoDao dao;
	
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<EngineeringImplementationInfoVO>> page(EngineeringImplementationInfoVO engineeringImplementationInfoVO,LyhtPageVO lyhtPageVO){
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<EngineeringImplementationInfoVO> page = dao.page(engineeringImplementationInfoVO.getProjectName(),pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<EngineeringImplementationInfoVO> list = (List<EngineeringImplementationInfoVO>) JSON.parse(jsonString);
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
		List<EngineeringImplementationInfo> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public EngineeringImplementationInfo findOneById(Integer id) {
		return dao.getOne(id);
	}
	
	/**
	 * 添加 修改
	 * 
	 * @param engineeringImplementationInfo
	 * @return
	 */
	public LyhtResultBody<EngineeringImplementationInfo> save(EngineeringImplementationInfo engineeringImplementationInfo) {
		// 参数校验
		if (engineeringImplementationInfo == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = engineeringImplementationInfo.getNm();
		if (StringUtils.isBlank(nm)) {
			engineeringImplementationInfo.setNm(Randomizer.generCode(10));
		}
		EngineeringImplementationInfo result = dao.save(engineeringImplementationInfo);
		return new LyhtResultBody<>(result);
	}
}
