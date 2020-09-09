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
import com.lyht.business.info.dao.InfoLandDao;
import com.lyht.business.info.entity.InfoLandEntity;
import com.lyht.business.info.vo.InfoLandVO;
import com.lyht.util.Randomizer;

@Service
public class InfoLandService {
	private Logger log = LoggerFactory.getLogger(InfoLandService.class);

	@Autowired
	private InfoLandDao infoLandDao;

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
	public LyhtResultBody<List<InfoLandVO>> page(String region, String ownerName, String scope, String projectFcode, String idCard,
			String isDirtyData, String ownerNm, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoLandVO> page = infoLandDao.page(region, ownerName, scope, projectFcode, idCard, isDirtyData, ownerNm, pageable);
		// 结果集
		List<InfoLandVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoLandEntity
	 * @return
	 */
	public InfoLandEntity save(InfoLandEntity infoLandEntity) {
		// 参数校验
		if (infoLandEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoLandEntity save = null;
		try {
			// 内码赋值
			String nm = infoLandEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoLandEntity.setNm(Randomizer.generCode(10));
			}
			save = infoLandDao.save(infoLandEntity);
		} catch (Exception e) {
			log.error("=====InfoLandService=====Method：save=====param：" + infoLandEntity, e);
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
		infoLandDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

}
