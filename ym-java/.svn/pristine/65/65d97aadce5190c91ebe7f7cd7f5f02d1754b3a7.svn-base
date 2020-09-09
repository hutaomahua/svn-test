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
import com.lyht.business.engineering.dao.EngineeringSinkBottomDao;
import com.lyht.business.engineering.entity.EngineeringSinkBottom;
import com.lyht.business.engineering.vo.EngineeringSinkBottomVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Service
public class EngineeringSinkBottomService {

	@Autowired
	private EngineeringSinkBottomDao dao;
	
	@SuppressWarnings("unchecked")
	public LyhtResultBody<List<EngineeringSinkBottomVO>> page(EngineeringSinkBottomVO engineeringSinkBottomVO,LyhtPageVO lyhtPageVO){
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<EngineeringSinkBottomVO> page = dao.page(engineeringSinkBottomVO.getProjectName(),pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<EngineeringSinkBottomVO> list = (List<EngineeringSinkBottomVO>) JSON.parse(jsonString);
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
		List<EngineeringSinkBottom> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public EngineeringSinkBottom findOneById(Integer id) {
		return dao.getOne(id);
	}
	
	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	public LyhtResultBody<EngineeringSinkBottom> save(EngineeringSinkBottom engineeringSinkBottom) {
		// 参数校验
		if (engineeringSinkBottom == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = engineeringSinkBottom.getNm();
		if (StringUtils.isBlank(nm)) {
			engineeringSinkBottom.setNm(Randomizer.generCode(10));
		}
		EngineeringSinkBottom result = dao.save(engineeringSinkBottom);
		return new LyhtResultBody<>(result);
	}
	
	
}
