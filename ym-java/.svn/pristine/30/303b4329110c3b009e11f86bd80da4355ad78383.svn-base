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
import com.lyht.business.info.dao.InfoHousesDao;
import com.lyht.business.info.entity.InfoHousesEntity;
import com.lyht.business.info.vo.InfoHousesVO;
import com.lyht.util.Randomizer;

@Service
public class InfoHousesService {
	private Logger log = LoggerFactory.getLogger(InfoHousesService.class);

	@Autowired
	private InfoHousesDao infoHousesDao;

	/**
	 * 分页查询（所有字段）
	 * @param region
	 * @param ownerName
	 * @param scope
	 * @param projectFcode
	 * @param isDirtyData 
	 * @param ownerNm 
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<InfoHousesVO>> page(String region, String ownerName, String scope, String projectFcode,
			String isDirtyData, String ownerNm, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoHousesVO> page = infoHousesDao.page(region, ownerName, scope, projectFcode, isDirtyData, ownerNm, pageable);
		// 结果集
		List<InfoHousesVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoHousesEntity
	 * @return
	 */
	public InfoHousesEntity save(InfoHousesEntity infoHousesEntity) {
		// 参数校验
		if (infoHousesEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoHousesEntity save = null;
		try {
			// 内码赋值
			String nm = infoHousesEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoHousesEntity.setNm(Randomizer.generCode(10));
			}
			save = infoHousesDao.save(infoHousesEntity);
		} catch (Exception e) {
			log.error("=====InfoHousesService=====Method：save=====param：" + infoHousesEntity, e);
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
		infoHousesDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

}
