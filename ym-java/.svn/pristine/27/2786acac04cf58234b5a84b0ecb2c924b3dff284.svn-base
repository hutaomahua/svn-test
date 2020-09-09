package com.lyht.business.engineering.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.engineering.dao.EngineeringNewTownDao;
import com.lyht.business.engineering.entity.EngineeringNewTown;
import com.lyht.business.engineering.vo.EngineeringNewTownVO;
import com.lyht.business.engineering.vo.EngineeringNewTownBean;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineeringNewTownService {

	@Autowired
	private EngineeringNewTownDao dao;


	/**
	 * 分页查询
	 *
	 * @param lyhtPageVO
	 * @return
	 */
	public  LyhtResultBody<List<EngineeringNewTownVO>> page(LyhtPageVO lyhtPageVO,
															  EngineeringNewTownVO engineeringNewTownVo) {
		Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent()-1, lyhtPageVO.getPageSize());
		Page<EngineeringNewTownVO> page = dao.findListByLike(engineeringNewTownVo.getRegion(), engineeringNewTownVo.getTownName(),pageable);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(page.getContent(), pageVO);
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
		List<EngineeringNewTown> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public EngineeringNewTown findOneById(Integer id) {
		return dao.getOne(id);
	}
	
	/**
	 * 添加 修改
	 * 
	 * @param engineeringEvaluate
	 * @return
	 */
	public LyhtResultBody<EngineeringNewTown> save(EngineeringNewTown engineeringEvaluate) {
		// 参数校验
		if (engineeringEvaluate == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = engineeringEvaluate.getNm();
		if (StringUtils.isBlank(nm)) {
			engineeringEvaluate.setNm(Randomizer.generCode(10));
		}
		EngineeringNewTown result = dao.save(engineeringEvaluate);
		return new LyhtResultBody<>(result);
	}
	
	
}
