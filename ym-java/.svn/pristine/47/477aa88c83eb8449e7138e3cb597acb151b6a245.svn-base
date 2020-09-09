package com.lyht.business.abm.wechat.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.abm.wechat.dao.AbmWechatHouseDao;
import com.lyht.business.abm.wechat.dao.AbmWechatInfoAggregateDao;
import com.lyht.business.abm.wechat.dao.AbmWechatLandDao;
import com.lyht.business.abm.wechat.dao.AbmWechatOwnerDao;
import com.lyht.business.abm.wechat.vo.AbmWechatInfoAggregateVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionChatVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionHouseVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionLandVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionOwnerVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeAggregateVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeChatVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeHouseVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeLandVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeOwnerVO;

@Service
public class AbmWechatInfoAggregateService {

	@Autowired
	private AbmWechatInfoAggregateDao abmWechatInfoAggregateDao;

	@Autowired
	private AbmWechatOwnerDao abmWechatOwnerDao;

	@Autowired
	private AbmWechatHouseDao abmWechatHouseDao;

	@Autowired
	private AbmWechatLandDao abmWechatLandDao;

	/**
	 * 指标统计--总计查询
	 * 
	 * @return
	 */
	public AbmWechatInfoAggregateVO total() {
		long householdsTotal = abmWechatOwnerDao.count();
		BigDecimal houseAreaTotal = abmWechatHouseDao.findTotalArea();
		BigDecimal landAreaTotal = abmWechatLandDao.findTotalArea();

		AbmWechatInfoAggregateVO abmWechatInfoAggregateVO = new AbmWechatInfoAggregateVO();
		abmWechatInfoAggregateVO.setHouseholdsTotal(householdsTotal);
		abmWechatInfoAggregateVO.setHouseAreaTotal(houseAreaTotal);
		abmWechatInfoAggregateVO.setLandAreaTotal(landAreaTotal);
		return abmWechatInfoAggregateVO;
	}

	/**
	 * 按征地范围统计
	 * 
	 * @return
	 */
	public List<AbmWechatScopeAggregateVO> findAggregateByScope() {
		List<AbmWechatScopeAggregateVO> findAggregateByScope = abmWechatInfoAggregateDao.findAggregateByScope();
		return findAggregateByScope;
	}

	/**
	 * 征地范围--权属人--列表
	 * 
	 * @return
	 */
	public List<AbmWechatScopeOwnerVO> findAggregateScopeByOwner() {
		List<AbmWechatScopeOwnerVO> findAggregateScopeByOwner = abmWechatInfoAggregateDao.findAggregateScopeByOwner();
		return findAggregateScopeByOwner;
	}

	/**
	 * 征地范围--权属人--图
	 * 
	 * @return
	 */
	public List<AbmWechatScopeChatVO> findChatScopeByOwner() {
		List<AbmWechatScopeOwnerVO> findAggregateScopeByOwner = findAggregateScopeByOwner();
		List<AbmWechatScopeChatVO> result = new ArrayList<>();
		for (AbmWechatScopeOwnerVO abmWechatScopeOwnerVO : findAggregateScopeByOwner) {
			String name = abmWechatScopeOwnerVO.getScope();
			BigDecimal households = abmWechatScopeOwnerVO.getHouseholds();
			AbmWechatScopeChatVO abmWechatScopeChatVO = new AbmWechatScopeChatVO();
			abmWechatScopeChatVO.setName(name);
			abmWechatScopeChatVO.setData(households);
			result.add(abmWechatScopeChatVO);
		}
		return result;
	}

	/**
	 * 征地范围--房屋--列表
	 * 
	 * @return
	 */
	public List<AbmWechatScopeHouseVO> findAggregateScopeByHouse() {
		List<AbmWechatScopeHouseVO> findAggregateScopeByHouse = abmWechatInfoAggregateDao.findAggregateScopeByHouse();
		return findAggregateScopeByHouse;
	}

	/**
	 * 征地范围--房屋--图
	 * 
	 * @return
	 */
	public List<AbmWechatScopeChatVO> findChatScopeByHouse() {
		List<AbmWechatScopeHouseVO> findAggregateScopeByHouse = findAggregateScopeByHouse();
		List<AbmWechatScopeChatVO> result = new ArrayList<>();
		for (AbmWechatScopeHouseVO abmWechatScopeHouseVO : findAggregateScopeByHouse) {
			String name = abmWechatScopeHouseVO.getScope();
			BigDecimal houseArea = abmWechatScopeHouseVO.getHouseArea();
			AbmWechatScopeChatVO abmWechatScopeChatVO = new AbmWechatScopeChatVO();
			abmWechatScopeChatVO.setName(name);
			abmWechatScopeChatVO.setData(houseArea);
			result.add(abmWechatScopeChatVO);
		}
		return result;
	}

	/**
	 * 征地范围--土地--列表
	 * 
	 * @return
	 */
	public List<AbmWechatScopeLandVO> findAggregateScopeByLand() {
		List<AbmWechatScopeLandVO> findAggregateScopeByLand = abmWechatInfoAggregateDao.findAggregateScopeByLand();
		return findAggregateScopeByLand;
	}

	/**
	 * 征地范围--土地--图
	 * 
	 * @return
	 */
	public List<AbmWechatScopeChatVO> findChatScopeByLand() {
		List<AbmWechatScopeLandVO> findAggregateScopeByLand = findAggregateScopeByLand();
		List<AbmWechatScopeChatVO> result = new ArrayList<>();
		for (AbmWechatScopeLandVO abmWechatScopeLandVO : findAggregateScopeByLand) {
			String name = abmWechatScopeLandVO.getScope();
			BigDecimal landArea = abmWechatScopeLandVO.getLandArea();
			AbmWechatScopeChatVO abmWechatScopeChatVO = new AbmWechatScopeChatVO();
			abmWechatScopeChatVO.setName(name);
			abmWechatScopeChatVO.setData(landArea);
			result.add(abmWechatScopeChatVO);
		}
		return result;
	}

//	/**
//	 * 按行政区域统计
//	 * 
//	 * @param mergerName
//	 * @return
//	 */
//	public List<AbmWechatRegionAggregateVO> findAggregateByRegion(String mergerName, String type) {
//		if (!StringUtils.equalsAnyIgnoreCase(type, AbmWechatConstant.AGGREGATE_TYPE_OWNER,
//				AbmWechatConstant.AGGREGATE_TYPE_HOUSE, AbmWechatConstant.AGGREGATE_TYPE_LAND)) {
//			throw new LyhtRuntimeException("指标类型错误！（owner：权属人；house：房屋；土地：land）");
//		}
//		List<AbmWechatRegionAggregateVO> findAggregateByRegion = abmWechatInfoAggregateDao
//				.findAggregateByRegion(mergerName, type);
//		return findAggregateByRegion;
//	}

	/**
	 * 行政区--权属人--列表
	 * 
	 * @return
	 */
	public List<AbmWechatRegionOwnerVO> findAggregateRegionByOwner(String parentCode) {
		if (StringUtils.isBlank(parentCode)) {
			parentCode = "E06A0AEF47";// 默认传维西县的cityCode
		}
		List<AbmWechatRegionOwnerVO> findAggregateRegionByOwner = abmWechatInfoAggregateDao
				.findAggregateRegionByOwner(parentCode);
		return findAggregateRegionByOwner;
	}

	/**
	 * 行政区--权属人--图
	 * 
	 * @return
	 */
	public List<AbmWechatRegionChatVO> findChatRegionByOwner(String parentCode) {
		List<AbmWechatRegionOwnerVO> findAggregateRegionByOwner = findAggregateRegionByOwner(parentCode);
		if (CollectionUtils.isEmpty(findAggregateRegionByOwner)) {
			return null;
		}
		List<AbmWechatRegionChatVO> result = new ArrayList<>();
		for (AbmWechatRegionOwnerVO abmWechatRegionOwnerVO : findAggregateRegionByOwner) {
			String name = abmWechatRegionOwnerVO.getName();
			BigDecimal households = abmWechatRegionOwnerVO.getHouseholds();
			String cityCode = abmWechatRegionOwnerVO.getCityCode();
			AbmWechatRegionChatVO abmWechatRegionChatVO = new AbmWechatRegionChatVO();
			abmWechatRegionChatVO.setCityCode(cityCode);
			abmWechatRegionChatVO.setName(name);
			abmWechatRegionChatVO.setData(households);
			result.add(abmWechatRegionChatVO);
		}
		return result;
	}

	/**
	 * 行政区--房屋--列表
	 * 
	 * @return
	 */
	public List<AbmWechatRegionHouseVO> findAggregateRegionByHouse(String parentCode) {
		if (StringUtils.isBlank(parentCode)) {
			parentCode = "E06A0AEF47";// 默认传维西县的cityCode
		}
		List<AbmWechatRegionHouseVO> findAggregateRegionByHouse = abmWechatInfoAggregateDao
				.findAggregateRegionByHouse(parentCode);// 查询维西县下所有
		return findAggregateRegionByHouse;
	}

	/**
	 * 行政区--房屋--图
	 * 
	 * @return
	 */
	public List<AbmWechatRegionChatVO> findChatRegionByHouse(String parentCode) {
		List<AbmWechatRegionHouseVO> findAggregateRegionByHouse = findAggregateRegionByHouse(parentCode);
		if (CollectionUtils.isEmpty(findAggregateRegionByHouse)) {
			return null;
		}
		List<AbmWechatRegionChatVO> result = new ArrayList<>();
		for (AbmWechatRegionHouseVO abmWechatRegionHouseVO : findAggregateRegionByHouse) {
			String name = abmWechatRegionHouseVO.getName();
			BigDecimal houseArea = abmWechatRegionHouseVO.getHouseArea();
			String cityCode = abmWechatRegionHouseVO.getCityCode();
			AbmWechatRegionChatVO abmWechatRegionChatVO = new AbmWechatRegionChatVO();
			abmWechatRegionChatVO.setCityCode(cityCode);
			abmWechatRegionChatVO.setName(name);
			abmWechatRegionChatVO.setData(houseArea);
			result.add(abmWechatRegionChatVO);
		}
		return result;
	}

	/**
	 * 行政区--土地--列表
	 * 
	 * @return
	 */
	public List<AbmWechatRegionLandVO> findAggregateRegionByLand(String parentCode) {
		if (StringUtils.isBlank(parentCode)) {
			parentCode = "E06A0AEF47";// 默认传维西县的cityCode
		}
		List<AbmWechatRegionLandVO> findAggregateRegionByLand = abmWechatInfoAggregateDao
				.findAggregateRegionByLand(parentCode);// 查询维西县下所有
		return findAggregateRegionByLand;
	}

	/**
	 * 行政区--土地--图
	 * 
	 * @return
	 */
	public List<AbmWechatRegionChatVO> findChatRegionByLand(String parentCode) {
		List<AbmWechatRegionLandVO> findAggregateRegionByLand = findAggregateRegionByLand(parentCode);
		if (CollectionUtils.isEmpty(findAggregateRegionByLand)) {
			return null;
		}
		List<AbmWechatRegionChatVO> result = new ArrayList<>();
		for (AbmWechatRegionLandVO abmWechatRegionLandVO : findAggregateRegionByLand) {
			String name = abmWechatRegionLandVO.getName();
			BigDecimal landArea = abmWechatRegionLandVO.getLandArea();
			String cityCode = abmWechatRegionLandVO.getCityCode();
			AbmWechatRegionChatVO abmWechatRegionChatVO = new AbmWechatRegionChatVO();
			abmWechatRegionChatVO.setCityCode(cityCode);
			abmWechatRegionChatVO.setName(name);
			abmWechatRegionChatVO.setData(landArea);
			result.add(abmWechatRegionChatVO);
		}
		return result;
	}

}
