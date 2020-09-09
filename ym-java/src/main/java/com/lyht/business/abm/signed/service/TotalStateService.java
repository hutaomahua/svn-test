package com.lyht.business.abm.signed.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.signed.dao.TotalStateDao;
import com.lyht.business.abm.signed.entity.TotalState;
import com.lyht.util.Randomizer;

@Service
public class TotalStateService {
	@Autowired
	private TotalStateDao dao;
	
	/**
	 * 添加 修改
	 *
	 * @param t_funds_info
	 * @return
	 */
	public LyhtResultBody<TotalState> save(TotalState totalState) {
		// 参数校验
		if (totalState == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		// 内码赋值
		String nm = totalState.getNm();
		if (StringUtils.isBlank(nm)) {
			totalState.setNm(Randomizer.generCode(10));
		}
		totalState.setProtocolState(0);
		TotalState result = dao.save(totalState);
		return new LyhtResultBody<>(result);
	}

	public TotalState  findByOwnerNm(String ownerNm) {
		TotalState findByOwnerNm = dao.findByOwnerNm(ownerNm);
		return findByOwnerNm;
	}
}
