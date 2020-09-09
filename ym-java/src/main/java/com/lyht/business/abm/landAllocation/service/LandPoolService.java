package com.lyht.business.abm.landAllocation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.lyht.business.abm.landAllocation.dao.LandPoolDao;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.landAllocation.vo.LandPoolVO;
import com.lyht.util.Randomizer;

@Service
public class LandPoolService {

	@Autowired
	private LandPoolDao dao;

	public LyhtResultBody<List<LandPoolEntity>> saveAll(LandPoolEntity landPoolEntity) {
		String[] regions = landPoolEntity.getRegion().split(",");// 将全编码（父-子）分割成字符串数组
		String scope = landPoolEntity.getScope();
		String allType = landPoolEntity.getAllType();
		String typeOne = landPoolEntity.getTypeOne();
		String typeTwo = landPoolEntity.getTypeTwo();
		String typeThree = landPoolEntity.getTypeThree();
		if (regions.length == 0 || StringUtils.isBlank(scope) || StringUtils.isBlank(allType)
				|| StringUtils.isBlank(typeOne) || StringUtils.isBlank(typeTwo)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		String[] level = { "province", "city", "district", "street", "village", "group" };
		List<LandPoolEntity> list = new ArrayList<LandPoolEntity>();
		int i = 0;
		for (String region : regions) {// 循环查询 新增
			LandPoolEntity newEntity = new LandPoolEntity();
			// 根据条件查询 是否存在改该数据 不存在则新增 存在则累加
			LandPoolEntity entity = dao.queryBySixCondition(region, scope, allType, typeOne, typeTwo, typeThree);
			if (entity != null) {// 复核条件数据存在累加
				BigDecimal area = entity.getArea().add(landPoolEntity.getArea());
				newEntity.setId(entity.getId());
				newEntity.setNm(entity.getNm());
				newEntity.setRegion(entity.getRegion());
				newEntity.setRegionLevel(entity.getRegionLevel());
				newEntity.setScope(entity.getScope());
				newEntity.setAllType(entity.getAllType());
				newEntity.setTypeOne(entity.getTypeOne());
				newEntity.setTypeTwo(entity.getTypeTwo());
				newEntity.setTypeThree(entity.getTypeThree());
				newEntity.setArea(area);
				newEntity.setSeparateArea(entity.getSeparateArea());
				newEntity.setStatus(0);
			} else {// 不存在
				newEntity.setNm(Randomizer.generCode(10));
				newEntity.setRegion(region);
				newEntity.setRegionLevel(level[i]);
				newEntity.setScope(landPoolEntity.getScope());
				newEntity.setAllType(landPoolEntity.getAllType());
				newEntity.setTypeOne(landPoolEntity.getTypeOne());
				newEntity.setTypeTwo(landPoolEntity.getTypeTwo());
				newEntity.setTypeThree(landPoolEntity.getTypeThree());
				newEntity.setArea(landPoolEntity.getArea());
				newEntity.setSeparateArea(new BigDecimal(0));
				newEntity.setStatus(0);
			}
			LandPoolEntity save = dao.save(newEntity);
			list.add(save);
			i++;
		}
		return new LyhtResultBody<>(list);
	}

	/**
	 * 修改 分解状态
	 * 
	 * @param landPool
	 * @return
	 */
	public LyhtResultBody<LandPoolEntity> save(LandPoolEntity landPoolEntity) {
		// 参数校验
		if (landPoolEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		LandPoolEntity entity = dao.getOne(landPoolEntity.getId());
		entity.setStatus(landPoolEntity.getStatus());
		landPoolEntity = dao.save(entity);
		return new LyhtResultBody<>(landPoolEntity);
	}

	/**
	 * 查询列表
	 */
	public LyhtResultBody<List<LandPoolVO>> page(String region, String scope, String allType, String typeOne,
			String typeTwo, String typeThree, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map<String, Object>> page = dao.page(scope, region, allType, typeOne, typeTwo, typeThree, pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<LandPoolVO> list = (List<LandPoolVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
}
