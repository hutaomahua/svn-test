package com.lyht.business.abm.history.service;

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
import com.lyht.business.abm.history.dao.OwnerInfoHistoryDao;
import com.lyht.business.abm.history.entity.OwnerInfoHistory;
import com.lyht.business.abm.history.vo.OwnerInfoHistoryVO;
import com.lyht.util.Randomizer;

@Service
public class OwnerInfoHistoryService {

	@Autowired
	private OwnerInfoHistoryDao dao;

	public LyhtResultBody<List<OwnerInfoHistoryVO>> page(LyhtPageVO lyhtPageVO, String name,String startTime,String endTime,String nm) {
		// 分页,排序
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<OwnerInfoHistoryVO> page = dao.page(name,startTime,endTime,nm,pageable);
		// 结果集
		List<OwnerInfoHistoryVO> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 添加 修改
	 *
	 * @param OwnerInfoHistory
	 * @return
	 */
	public LyhtResultBody<OwnerInfoHistory> save(OwnerInfoHistory ownerInfoHistory) {
		// 参数校验
		if (ownerInfoHistory == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = ownerInfoHistory.getNm();
		if (StringUtils.isBlank(nm)) {
			ownerInfoHistory.setNm(Randomizer.generCode(10));
		}
		OwnerInfoHistory result = dao.save(ownerInfoHistory);
		return new LyhtResultBody<>(result);
	}

}
