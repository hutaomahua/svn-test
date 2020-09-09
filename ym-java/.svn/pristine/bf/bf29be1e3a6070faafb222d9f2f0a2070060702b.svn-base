package com.lyht.business.abm.landAllocation.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.landAllocation.common.constant.LandResolveConstant;
import com.lyht.business.abm.landAllocation.common.enums.LandResolveExceptionEnums;
import com.lyht.business.abm.landAllocation.dao.LandResolveDao;
import com.lyht.business.abm.landAllocation.vo.LandResolveAggregateParamVO;
import com.lyht.business.abm.landAllocation.vo.LandResolveAggregateVO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LandResolveService {

	@Autowired
	private LandResolveDao landResolveDao;

	/**
	 * 土地分解联动汇总列表查询
	 * 
	 * @param landResolveAggregateParamVO 参数对象
	 * @return
	 */
	public List<LandResolveAggregateVO> findAggregate(LandResolveAggregateParamVO landResolveAggregateParamVO) {
		// 参数校验
		if (landResolveAggregateParamVO == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		String areaType = landResolveAggregateParamVO.getAreaType();
		if (!StringUtils.equalsAny(areaType, LandResolveConstant.AREA_TYPE_ALL, LandResolveConstant.AREA_TYPE_RESOLVE,
				LandResolveConstant.AREA_TYPE_NON_RESOLVE)) {
			throw new LyhtRuntimeException(LandResolveExceptionEnums.INVALID_AREA_TYPE);
		}
		Integer level = landResolveAggregateParamVO.getLevel();
		if (level == null || level < 0 || level > 3) {// 只能查询最小级别为0，最大级别为3的数据
			throw new LyhtRuntimeException(LandResolveExceptionEnums.INVALID_LEVEL);
		}

		List<LandResolveAggregateVO> list = null;

		String cityCode = landResolveAggregateParamVO.getCityCode();
		String landType = landResolveAggregateParamVO.getLandType();// 对应级别的类型的编码

		if (level == 0) {// 一级---对应的所有大类
			list = landResolveDao.findAggregateAll(cityCode, areaType);
		} else if (level == 1) {// 二级---对应大类下的所有一级分类
			list = landResolveDao.findAggregateByAllType(cityCode, areaType, landType);
		} else if (level == 2) {// 三级---对应一级分类下的所有二级分类
			list = landResolveDao.findAggregateByTypeOne(cityCode, areaType, landType);
		} else if (level == 3) {// 四级---对应二级分类下的所有三级分类
			list = landResolveDao.findAggregateByTypeTwo(cityCode, areaType, landType);
		}
		
		if (CollectionUtils.isEmpty(list)) {
			throw new LyhtRuntimeException("没有下一级或下一级数据为空!");
		}

		return list;
	}

}
