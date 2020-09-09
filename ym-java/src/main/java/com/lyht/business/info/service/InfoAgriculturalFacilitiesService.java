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
import com.lyht.business.info.dao.InfoAgriculturalFacilitiesDao;
import com.lyht.business.info.entity.InfoAgriculturalFacilitiesEntity;
import com.lyht.business.info.vo.InfoAgriculturalFacilitiesVO;
import com.lyht.util.Randomizer;

@Service
public class InfoAgriculturalFacilitiesService {
	private Logger log = LoggerFactory.getLogger(InfoAgriculturalFacilitiesService.class);

	@Autowired
	private InfoAgriculturalFacilitiesDao infoAgriculturalFacilitiesDao;

	/**
	 * 分页查询（所有字段）
	 * 
	 * @param region
	 * @param ownerName
	 * @param scope
	 * @param projectFcode
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<InfoAgriculturalFacilitiesVO>> page(LyhtPageVO lyhtPageVO, String region,
			String ownerName, String scope, String projectFcode, String isDirtyData) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoAgriculturalFacilitiesVO> page = infoAgriculturalFacilitiesDao.page(region, ownerName, scope,
				projectFcode, isDirtyData, pageable);
		// 结果集
		List<InfoAgriculturalFacilitiesVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoAgriculturalFacilitiesEntity
	 * @return
	 */
	public InfoAgriculturalFacilitiesEntity save(InfoAgriculturalFacilitiesEntity infoAgriculturalFacilitiesEntity) {
		// 参数校验
		if (infoAgriculturalFacilitiesEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoAgriculturalFacilitiesEntity save = null;
		try {
			// 内码赋值
			String nm = infoAgriculturalFacilitiesEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoAgriculturalFacilitiesEntity.setNm(Randomizer.generCode(10));
			}
			save = infoAgriculturalFacilitiesDao.save(infoAgriculturalFacilitiesEntity);
		} catch (Exception e) {
			log.error("=====InfoAgriculturalFacilitiesService=====Method：save=====param："
					+ infoAgriculturalFacilitiesEntity, e);
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
		infoAgriculturalFacilitiesDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

}
