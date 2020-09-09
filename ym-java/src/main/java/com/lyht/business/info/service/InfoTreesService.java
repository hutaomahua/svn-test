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
import com.lyht.business.info.dao.InfoTreesDao;
import com.lyht.business.info.entity.InfoTreesEntity;
import com.lyht.business.info.vo.InfoTreesVO;
import com.lyht.util.Randomizer;

@Service
public class InfoTreesService {
	private Logger log = LoggerFactory.getLogger(InfoTreesService.class);

	@Autowired
	private InfoTreesDao infoTreesDao;

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
	public LyhtResultBody<List<InfoTreesVO>> page(String region, String ownerName, String scope, String projectFcode,
			String isDirtyData, String ownerNm, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoTreesVO> page = infoTreesDao.page(region, ownerName, scope, projectFcode, isDirtyData, ownerNm, pageable);
		// 结果集
		List<InfoTreesVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoTreesEntity
	 * @return
	 */
	public InfoTreesEntity save(InfoTreesEntity infoTreesEntity) {
		// 参数校验
		if (infoTreesEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoTreesEntity save = null;
		try {
			// 内码赋值
			String nm = infoTreesEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoTreesEntity.setNm(Randomizer.generCode(10));
			}
			save = infoTreesDao.save(infoTreesEntity);
		} catch (Exception e) {
			log.error("=====InfoTreesService=====Method：save=====param：" + infoTreesEntity, e);
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
		infoTreesDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

}
