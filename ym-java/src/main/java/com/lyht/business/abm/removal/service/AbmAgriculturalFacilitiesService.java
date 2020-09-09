package com.lyht.business.abm.removal.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.dao.AbmAgriculturalFacilitiesDao;
import com.lyht.business.abm.removal.entity.AbmAgriculturalFacilitiesEntity;
import com.lyht.business.abm.removal.vo.AbmAgriculturalFacilitiesVO;
import com.lyht.util.Randomizer;

@Service
public class AbmAgriculturalFacilitiesService {
	private Logger log = LoggerFactory.getLogger(AbmAgriculturalFacilitiesService.class);

	@Autowired
	private AbmAgriculturalFacilitiesDao abmAgriculturalFacilitiesDao;

	/**
	 * 查询（所有字段）
	 * 
	 * @param ownerName
	 * @return
	 */
	public LyhtResultBody<List<AbmAgriculturalFacilitiesVO>> page(LyhtPageVO lyhtPageVO, String region,
			String ownerName, String scope, String projectFcode, String isDirtyData) {
		List<AbmAgriculturalFacilitiesVO> list = abmAgriculturalFacilitiesDao.list( ownerName);
		// 结果集
		return new LyhtResultBody<>(list);
	}

	/**
	 * 新增，修改
	 * 
	 * @param AbmAgriculturalFacilitiesEntity
	 * @return
	 */
	public AbmAgriculturalFacilitiesEntity save(AbmAgriculturalFacilitiesEntity abmAgriculturalFacilitiesEntity) {
		// 参数校验
		if (abmAgriculturalFacilitiesEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		AbmAgriculturalFacilitiesEntity save = null;
		try {
			// 内码赋值
			String nm = abmAgriculturalFacilitiesEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				abmAgriculturalFacilitiesEntity.setNm(Randomizer.generCode(10));
			}
			save = abmAgriculturalFacilitiesDao.save(abmAgriculturalFacilitiesEntity);
		} catch (Exception e) {
			log.error("=====AbmAgriculturalFacilitiesService=====Method：save=====param："
					+ abmAgriculturalFacilitiesEntity, e);
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
		abmAgriculturalFacilitiesDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

}
