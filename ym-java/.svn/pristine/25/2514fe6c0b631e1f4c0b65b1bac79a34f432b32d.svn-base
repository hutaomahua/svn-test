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
import com.lyht.business.info.dao.InfoHomesteadDao;
import com.lyht.business.info.entity.InfoHomesteadEntity;
import com.lyht.business.info.vo.InfoHomesteadVO;
import com.lyht.util.Randomizer;

@Service
public class InfoHomesteadService {
	private Logger log = LoggerFactory.getLogger(InfoHomesteadService.class);

	@Autowired
	private InfoHomesteadDao infoHomesteadDao;

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
	public LyhtResultBody<List<InfoHomesteadVO>> page(String region, String ownerName, String scope, String projectFcode,
			String isDirtyData, String ownerNm, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoHomesteadVO> page = infoHomesteadDao.page(region, ownerName, scope, projectFcode, isDirtyData, ownerNm, pageable);
		// 结果集
		List<InfoHomesteadVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoHomesteadEntity
	 * @return
	 */
	public InfoHomesteadEntity save(InfoHomesteadEntity infoHomesteadEntity) {
		// 参数校验
		if (infoHomesteadEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoHomesteadEntity save = null;
		try {
			// 内码赋值
			String nm = infoHomesteadEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoHomesteadEntity.setNm(Randomizer.generCode(10));
			}
			save = infoHomesteadDao.save(infoHomesteadEntity);
		} catch (Exception e) {
			log.error("=====InfoHomesteadService=====Method：save=====param：" + infoHomesteadEntity, e);
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
		infoHomesteadDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

}
