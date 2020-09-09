package com.lyht.business.engineering.service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.engineering.dao.EngineeringReclaimInfoDao;
import com.lyht.business.engineering.entity.EngineeringReclaimInfo;
import com.lyht.business.engineering.vo.EngineeringReclaimInfoVO;
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
public class EngineeringReclaimInfoService {

	@Autowired
	private EngineeringReclaimInfoDao dao;

	/**
	 * 分页查询
	 *
	 * @param lyhtPageVO
	 * @return
	 */
	public  LyhtResultBody<List<EngineeringReclaimInfoVO>> page(LyhtPageVO lyhtPageVO,
																EngineeringReclaimInfoVO engineeringReclaimInfoVo) {
		Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent()-1, lyhtPageVO.getPageSize());
		Page<EngineeringReclaimInfoVO> page = dao.findListByLike(engineeringReclaimInfoVo.getRegion(), engineeringReclaimInfoVo.getLandNm(),pageable);

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
		List<EngineeringReclaimInfo> deleteFundCost = dao.findAllById(idList);
		dao.deleteInBatch(deleteFundCost);
		return new LyhtResultBody<>(ids);
	}

	/**
	 * 查询单个
	 */
	public EngineeringReclaimInfo findOneById(Integer id) {
		return dao.getOne(id);
	}
	
	/**
	 * 添加 修改
	 * 
	 * @param engineeringEvaluate
	 * @return
	 */
	public LyhtResultBody<EngineeringReclaimInfo> save(EngineeringReclaimInfo engineeringEvaluate) {
		// 参数校验
		if (engineeringEvaluate == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = engineeringEvaluate.getNm();
		if (StringUtils.isBlank(nm)) {
			engineeringEvaluate.setNm(Randomizer.generCode(10));
		}
		EngineeringReclaimInfo result = dao.save(engineeringEvaluate);
		return new LyhtResultBody<>(result);
	}
	
	
}
