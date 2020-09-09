package com.lyht.business.info.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.dao.InfoIndividualDao;
import com.lyht.business.info.entity.InfoIndividualEntity;
import com.lyht.business.info.vo.InfoIndividualVO;
import com.lyht.util.Randomizer;

@Service
public class InfoIndividualService {
	private Logger log = LoggerFactory.getLogger(InfoIndividualService.class);

	@Autowired
	private InfoIndividualDao infoIndividualDao;

	/**
	 * 分页查询（所有字段）
	 * @param region
	 * @param isDirtyData 
	 * @param ownerNm 
	 * @param ownerName
	 * @param scope
	 * @param projectFcode
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<InfoIndividualVO>> page(String region, String operatorName, String idCard,
			String isDirtyData, String ownerNm, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoIndividualVO> page = infoIndividualDao.page(region, operatorName, idCard, isDirtyData, ownerNm, pageable);
		// 结果集
		List<InfoIndividualVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoIndividualEntity
	 * @return
	 */
	public InfoIndividualEntity save(InfoIndividualEntity infoIndividualEntity) {
		// 参数校验
		if (infoIndividualEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoIndividualEntity save = null;
		try {
			// 内码赋值
			String nm = infoIndividualEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoIndividualEntity.setNm(Randomizer.generCode(10));
			}
			save = infoIndividualDao.save(infoIndividualEntity);
		} catch (Exception e) {
			log.error("=====InfoIndividualService=====Method：save=====param：" + infoIndividualEntity, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return save;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Integer> delete(Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		infoIndividualDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

}
