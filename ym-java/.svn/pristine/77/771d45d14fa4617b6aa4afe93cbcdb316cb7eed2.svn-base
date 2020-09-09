package com.lyht.business.abm.removal.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.common.constant.AbmConstant;
import com.lyht.business.abm.removal.dao.AbmAggregateDao;
import com.lyht.business.abm.removal.dao.AbmEnterpriseDao;
import com.lyht.business.abm.removal.dao.AbmHomesteadDao;
import com.lyht.business.abm.removal.dao.AbmHousesDao;
import com.lyht.business.abm.removal.dao.AbmHousesDecorationDao;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.dao.AbmTreesDao;
import com.lyht.business.abm.removal.vo.AbmAggregateCardVO;
import com.lyht.business.abm.removal.vo.AbmAggregateChatVO;
import com.lyht.business.abm.removal.vo.AbmAggregateTreeVO;
import com.lyht.business.abm.removal.vo.AbmMoveAggregateCardVO;
import com.lyht.business.abm.removal.vo.AbmOwnerAggregateVO;
import com.lyht.business.abm.removal.vo.AbmRegionAggregateVO;
import com.lyht.business.abm.removal.vo.AbmRegionTreeVO;
import com.lyht.util.CommonUtil;

@Service
public class AbmAggregateService {

	@Autowired
	private AbmOwnerDao abmOwnerDao;
	@Autowired
	private AbmEnterpriseDao abmEnterpriseDao;
	@Autowired
	private AbmHousesDao abmHousesDao;
	@Autowired
	private AbmHousesDecorationDao abmHousesDecorationDao;
	@Autowired
	private AbmTreesDao abmTreesDao;
	@Autowired
	private AbmHomesteadDao abmHomesteadDao;
	@Autowired
	private AbmAggregateDao abmAggregateDao;

	/**
	 * 获取实物指标汇总树--按行政区汇总
	 * 
	 * @return
	 */
	public List<AbmRegionTreeVO> findRegionTree() {
		List<AbmRegionAggregateVO> findRegionAggregate = abmOwnerDao.findRegionAggregate();
		String jsonString = JSON.toJSONString(findRegionAggregate);
		List<AbmRegionTreeVO> parseArray = JSON.parseArray(jsonString, AbmRegionTreeVO.class);

		List<AbmRegionTreeVO> getAggregateTree = getRegionTree(parseArray);

		return getAggregateTree;
	}

	/**
	 * 获取行政区汇总树
	 * 
	 * @param list
	 * @return
	 */
	private List<AbmRegionTreeVO> getRegionTree(List<AbmRegionTreeVO> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		// step1：遍历找出所有根节点，并递归子节点
		List<AbmRegionTreeVO> rootList = new ArrayList<>();
		int startSerialNumber = 1;
		for (AbmRegionTreeVO abmRegionTreeVO : list) {
			String level = abmRegionTreeVO.getLevel();
			if (StringUtils.equals(level, "3")) {
				// 生成序号
				abmRegionTreeVO.setSerialNumber(String.valueOf(startSerialNumber));
				rootList.add(abmRegionTreeVO);
				startSerialNumber++;
			}
		}
		if (!rootList.isEmpty()) {
			list.removeAll(rootList);
			for (AbmRegionTreeVO abmRegionTreeVO : rootList) {
				getRegionChildren(list, abmRegionTreeVO);
			}
			// 清空空数据
			clearRegionEmptyData(rootList);
			return rootList;
		}
		return null;
	}

	/**
	 * 递归行政区子节点
	 * 
	 * @param list
	 * @param root
	 * @return
	 */
	private AbmRegionTreeVO getRegionChildren(List<AbmRegionTreeVO> list, AbmRegionTreeVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}

		// root属性
		String cityCode = root.getCityCode();// 父ID
		String parentSerialNumber = root.getSerialNumber();// 序号
		BigDecimal population = root.getPopulation();// 人口
		BigDecimal enterNumber = root.getEnterNumber();// 企业数量
		BigDecimal houseArea = root.getHouseArea();// 房屋面积
		BigDecimal houseDecorationArea = root.getHouseDecorationArea();// 房屋装修面积
		BigDecimal treeNumber = root.getTreeNumber();// 零星树木数量
		BigDecimal homesteadArea = root.getHomesteadArea();// 宅基地面积
		List<String> parentCodes = root.getParentCodes();// 所有上级ID

		// step1：遍历找出子节点
		List<AbmRegionTreeVO> children = new ArrayList<>();
		for (AbmRegionTreeVO abmRegionTreeVO : list) {
			String parentCode = abmRegionTreeVO.getParentCode();
			if (StringUtils.equalsIgnoreCase(cityCode, parentCode)) {
				children.add(abmRegionTreeVO);
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (AbmRegionTreeVO abmRegionTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				abmRegionTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 获取所有的上级ID
				List<String> childrenParentCodes = new ArrayList<>();
				if (parentCodes == null || parentCodes.isEmpty()) {
					childrenParentCodes.add(cityCode);
				} else {
					childrenParentCodes.addAll(parentCodes);
					childrenParentCodes.add(cityCode);
				}
				abmRegionTreeVO.setParentCodes(childrenParentCodes);
				// 递归
				AbmRegionTreeVO aggregateTree = getRegionChildren(list, abmRegionTreeVO);
				// 统计父节点的总值
				BigDecimal population2 = aggregateTree.getPopulation();
				population = population.add(population2);
				BigDecimal enterNumber2 = aggregateTree.getEnterNumber();
				enterNumber = enterNumber.add(enterNumber2);
				BigDecimal houseArea2 = aggregateTree.getHouseArea();
				houseArea = houseArea.add(houseArea2);
				BigDecimal houseDecorationArea2 = aggregateTree.getHouseDecorationArea();
				houseDecorationArea = houseDecorationArea.add(houseDecorationArea2);
				BigDecimal treeNumber2 = aggregateTree.getTreeNumber();
				treeNumber = treeNumber.add(treeNumber2);
				BigDecimal homesteadArea2 = aggregateTree.getHomesteadArea();
				homesteadArea = homesteadArea.add(homesteadArea2);
			}
			root.setPopulation(population);
			root.setEnterNumber(enterNumber);
			root.setHouseArea(houseArea);
			root.setHouseDecorationArea(houseDecorationArea);
			root.setTreeNumber(treeNumber);
			root.setHomesteadArea(homesteadArea);
			root.setChildren(children);
		}

		return root;
	}

	/**
	 * 递归清楚空数据
	 * 
	 * @param dataList
	 */
	public void clearRegionEmptyData(List<AbmRegionTreeVO> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			List<AbmRegionTreeVO> removeList = new ArrayList<>();
			for (AbmRegionTreeVO abmRegionTreeVO : dataList) {
				if (abmRegionTreeVO != null) {
					BigDecimal population = abmRegionTreeVO.getPopulation();
					BigDecimal enterNumber = abmRegionTreeVO.getEnterNumber();
					BigDecimal houseArea = abmRegionTreeVO.getHouseArea();
					BigDecimal houseDecorationArea = abmRegionTreeVO.getHouseDecorationArea();
					BigDecimal treeNumber = abmRegionTreeVO.getTreeNumber();
					BigDecimal homesteadArea = abmRegionTreeVO.getHomesteadArea();
					if (CommonUtil.isZeroOrNull(population) && CommonUtil.isZeroOrNull(enterNumber)
							&& CommonUtil.isZeroOrNull(houseArea) && CommonUtil.isZeroOrNull(houseDecorationArea)
							&& CommonUtil.isZeroOrNull(treeNumber) && CommonUtil.isZeroOrNull(homesteadArea)) {
						// 被清楚的空数据
						removeList.add(abmRegionTreeVO);
					} else {
						// 递归
						List<AbmRegionTreeVO> children = abmRegionTreeVO.getChildren();
						clearRegionEmptyData(children);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(removeList)) {
				dataList.removeAll(removeList);
			}
		}
	}

	/**
	 * 实物指标汇总--人口
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<AbmAggregateCardVO> findPopulationAggregate(String mergerName) {
		List<AbmAggregateCardVO> findPopulationAggregate = abmOwnerDao.findPopulationAggregate(mergerName);
		return findPopulationAggregate;
	}

	/**
	 * 实物指标汇总--企事业单位
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<AbmAggregateTreeVO> findEnterAggregate(String mergerName) {
		List<AbmAggregateCardVO> findOwnerAggregate = abmEnterpriseDao.findEnterAggregate(mergerName);
		String jsonString = JSON.toJSONString(findOwnerAggregate);
		List<AbmAggregateTreeVO> children = JSON.parseArray(jsonString, AbmAggregateTreeVO.class);
		AbmAggregateTreeVO root = new AbmAggregateTreeVO();

		BigDecimal reservoirTotal = BigDecimal.valueOf(0);
		BigDecimal pivotTotal = BigDecimal.valueOf(0);
		BigDecimal temporary = BigDecimal.valueOf(0);
		BigDecimal permanent = BigDecimal.valueOf(0);
		BigDecimal flood = BigDecimal.valueOf(0);
		BigDecimal influence = BigDecimal.valueOf(0);
		BigDecimal raise = BigDecimal.valueOf(0);
		BigDecimal newTown = BigDecimal.valueOf(0);

		int serialNumber = 1;
		for (AbmAggregateTreeVO abmAggregateTreeVO : children) {
			BigDecimal reservoirTotal2 = abmAggregateTreeVO.getReservoirTotal();
			reservoirTotal = reservoirTotal.add(reservoirTotal2);
			BigDecimal pivotTotal2 = abmAggregateTreeVO.getPivotTotal();
			pivotTotal = pivotTotal.add(pivotTotal2);
			BigDecimal temporary2 = abmAggregateTreeVO.getTemporary();
			temporary = temporary.add(temporary2);
			BigDecimal permanent2 = abmAggregateTreeVO.getPermanent();
			permanent = permanent.add(permanent2);
			BigDecimal flood2 = abmAggregateTreeVO.getFlood();
			flood = flood.add(flood2);
			BigDecimal influence2 = abmAggregateTreeVO.getInfluence();
			influence = influence.add(influence2);
			BigDecimal raise2 = abmAggregateTreeVO.getRaise();
			raise = raise.add(raise2);
			BigDecimal newTown2 = abmAggregateTreeVO.getNewTown();
			newTown = newTown.add(newTown2);
			// 生成序号
			abmAggregateTreeVO.setSerialNumber("1." + serialNumber);
			serialNumber++;
		}

		root.setReservoirTotal(reservoirTotal);
		root.setPivotTotal(pivotTotal);
		root.setFlood(flood);
		root.setInfluence(influence);
		root.setNewTown(newTown);
		root.setPermanent(permanent);
		root.setRaise(raise);
		root.setTemporary(temporary);
		root.setProject("合计");
		root.setUnit("个");
		root.setSerialNumber("1");
		root.setChildren(children);
		List<AbmAggregateTreeVO> result = new ArrayList<>();
		result.add(root);
		return result;
	}

	/**
	 * 实物指标汇总--房屋
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<AbmAggregateTreeVO> findHouseAggregate(String mergerName) {
		List<AbmAggregateCardVO> findHouseAggregate = abmHousesDao.findHouseAggregate(mergerName);
		String jsonString = JSON.toJSONString(findHouseAggregate);
		List<AbmAggregateTreeVO> list = JSON.parseArray(jsonString, AbmAggregateTreeVO.class);
		AbmAggregateTreeVO root = null;
		for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
			String project = abmAggregateTreeVO.getProject();
			if (StringUtils.equals(project, "房屋")) {
				root = abmAggregateTreeVO;
				root.setSerialNumber("1");
			}
		}
		if (root != null) {
			list.remove(root);
		}
		getHouseChildren(list, root);
		List<AbmAggregateTreeVO> result = new ArrayList<>();
		result.add(root);
		return result;
	}

	/**
	 * 递归房屋子节点
	 * 
	 * @param list
	 * @param root
	 * @return
	 */
	public AbmAggregateTreeVO getHouseChildren(List<AbmAggregateTreeVO> list, AbmAggregateTreeVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}
		String project = root.getProject();
		String parentSerialNumber = root.getSerialNumber();
		BigDecimal reservoirTotal = root.getReservoirTotal();
		BigDecimal pivotTotal = root.getPivotTotal();
		BigDecimal flood = root.getFlood();
		BigDecimal influence = root.getInfluence();
		BigDecimal newTown = root.getNewTown();
		BigDecimal permanent = root.getPermanent();
		BigDecimal raise = root.getRaise();
		BigDecimal temporary = root.getTemporary();

		// step1：找出子节点
		List<AbmAggregateTreeVO> children = new ArrayList<>();
		if (StringUtils.equals(project, "房屋")) {
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (!StringUtils.contains(project2, ",")) {
					children.add(abmAggregateTreeVO);
				}
			}
		} else {
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.contains(project2, project)) {
					children.add(abmAggregateTreeVO);
				}
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (AbmAggregateTreeVO abmAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				abmAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 递归
				AbmAggregateTreeVO houseChildren = getHouseChildren(list, abmAggregateTreeVO);
				// 统计父节点的总值
				BigDecimal reservoirTotal2 = houseChildren.getReservoirTotal();
				reservoirTotal = reservoirTotal.add(reservoirTotal2);
				BigDecimal pivotTotal2 = houseChildren.getPivotTotal();
				pivotTotal = pivotTotal.add(pivotTotal2);
				BigDecimal flood2 = houseChildren.getFlood();
				flood = flood.add(flood2);
				BigDecimal influence2 = houseChildren.getInfluence();
				influence = influence.add(influence2);
				BigDecimal newTown2 = houseChildren.getNewTown();
				newTown = newTown.add(newTown2);
				BigDecimal permanent2 = houseChildren.getPermanent();
				permanent = permanent.add(permanent2);
				BigDecimal raise2 = houseChildren.getRaise();
				raise = raise.add(raise2);
				BigDecimal temporary2 = houseChildren.getTemporary();
				temporary = temporary.add(temporary2);
			}

			root.setReservoirTotal(reservoirTotal);
			root.setPivotTotal(pivotTotal);
			root.setFlood(flood);
			root.setInfluence(influence);
			root.setNewTown(newTown);
			root.setPermanent(permanent);
			root.setRaise(raise);
			root.setTemporary(temporary);
			root.setChildren(children);
		}

		return root;
	}

	/**
	 * 实物指标汇总--房屋装修
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<AbmAggregateTreeVO> findHouseDecorationAggregate(String mergerName) {
		List<AbmAggregateCardVO> findHouseAggregate = abmHousesDecorationDao.findHouseDecorationAggregate(mergerName);
		String jsonString = JSON.toJSONString(findHouseAggregate);
		List<AbmAggregateTreeVO> list = JSON.parseArray(jsonString, AbmAggregateTreeVO.class);
		AbmAggregateTreeVO root = null;
		for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
			String project = abmAggregateTreeVO.getProject();
			if (StringUtils.equals(project, "房屋装修")) {
				root = abmAggregateTreeVO;
				root.setSerialNumber("1");
			}
		}
		if (root != null) {
			list.remove(root);
		}
		getHouseDecorationChildren(list, root);
		List<AbmAggregateTreeVO> result = new ArrayList<>();
		result.add(root);
		return result;
	}

	/**
	 * 递归房屋装修子节点
	 * 
	 * @param list
	 * @param root
	 * @return
	 */
	public AbmAggregateTreeVO getHouseDecorationChildren(List<AbmAggregateTreeVO> list, AbmAggregateTreeVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}
		String project = root.getProject();
		String parentSerialNumber = root.getSerialNumber();
		BigDecimal reservoirTotal = root.getReservoirTotal();
		BigDecimal pivotTotal = root.getPivotTotal();
		BigDecimal flood = root.getFlood();
		BigDecimal influence = root.getInfluence();
		BigDecimal newTown = root.getNewTown();
		BigDecimal permanent = root.getPermanent();
		BigDecimal raise = root.getRaise();
		BigDecimal temporary = root.getTemporary();

		// step1：找出子节点
		List<AbmAggregateTreeVO> children = new ArrayList<>();
		if (StringUtils.equals(project, "房屋装修")) {
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (!StringUtils.contains(project2, ",")) {
					children.add(abmAggregateTreeVO);
				}
			}
		} else {
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.contains(project2, project)) {
					children.add(abmAggregateTreeVO);
				}
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (AbmAggregateTreeVO abmAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				abmAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 递归
				AbmAggregateTreeVO houseChildren = getHouseDecorationChildren(list, abmAggregateTreeVO);
				// 统计父节点的总值
				BigDecimal reservoirTotal2 = houseChildren.getReservoirTotal();
				reservoirTotal = reservoirTotal.add(reservoirTotal2);
				BigDecimal pivotTotal2 = houseChildren.getPivotTotal();
				pivotTotal = pivotTotal.add(pivotTotal2);
				BigDecimal flood2 = houseChildren.getFlood();
				flood = flood.add(flood2);
				BigDecimal influence2 = houseChildren.getInfluence();
				influence = influence.add(influence2);
				BigDecimal newTown2 = houseChildren.getNewTown();
				newTown = newTown.add(newTown2);
				BigDecimal permanent2 = houseChildren.getPermanent();
				permanent = permanent.add(permanent2);
				BigDecimal raise2 = houseChildren.getRaise();
				raise = raise.add(raise2);
				BigDecimal temporary2 = houseChildren.getTemporary();
				temporary = temporary.add(temporary2);
			}

			root.setReservoirTotal(reservoirTotal);
			root.setPivotTotal(pivotTotal);
			root.setFlood(flood);
			root.setInfluence(influence);
			root.setNewTown(newTown);
			root.setPermanent(permanent);
			root.setRaise(raise);
			root.setTemporary(temporary);
			root.setChildren(children);
		}

		return root;
	}

	/**
	 * 实物指标汇总--零星树木
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<AbmAggregateTreeVO> findTreesAggregate(String mergerName) {
		List<AbmAggregateCardVO> findHouseAggregate = abmTreesDao.findTreesAggregate(mergerName);
		String jsonString = JSON.toJSONString(findHouseAggregate);
		List<AbmAggregateTreeVO> list = JSON.parseArray(jsonString, AbmAggregateTreeVO.class);
		AbmAggregateTreeVO root = null;
		for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
			String project = abmAggregateTreeVO.getProject();
			if (StringUtils.equals(project, "零星树木")) {
				root = abmAggregateTreeVO;
				root.setSerialNumber("1");
			}
		}
		if (root != null) {
			list.remove(root);
		}
		getTreesChildren(list, root);
		List<AbmAggregateTreeVO> result = new ArrayList<>();
		result.add(root);
		return result;
	}

	/**
	 * 递归零星树木子节点
	 * 
	 * @param list
	 * @param root
	 * @return
	 */
	public AbmAggregateTreeVO getTreesChildren(List<AbmAggregateTreeVO> list, AbmAggregateTreeVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}
		String project = root.getProject();
		String parentSerialNumber = root.getSerialNumber();
		BigDecimal reservoirTotal = root.getReservoirTotal();
		BigDecimal pivotTotal = root.getPivotTotal();
		BigDecimal flood = root.getFlood();
		BigDecimal influence = root.getInfluence();
		BigDecimal newTown = root.getNewTown();
		BigDecimal permanent = root.getPermanent();
		BigDecimal raise = root.getRaise();
		BigDecimal temporary = root.getTemporary();

		// step1：找出子节点
		List<AbmAggregateTreeVO> children = new ArrayList<>();
		if (StringUtils.equals(project, "零星树木")) {
			List<String> superId = new ArrayList<>();
			superId.add("287");
			superId.add("447");
			List<String> findTreeProjectName = abmTreesDao.findTreeProjectName(superId);
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(abmAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "果树")) {
			List<String> superId = new ArrayList<>();
			superId.add("288");
			superId.add("448");
			List<String> findTreeProjectName = abmTreesDao.findTreeProjectName(superId);
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(abmAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "经济树")) {
			List<String> superId = new ArrayList<>();
			superId.add("303");
			superId.add("464");
			List<String> findTreeProjectName = abmTreesDao.findTreeProjectName(superId);
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(abmAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "用材树")) {
			List<String> superId = new ArrayList<>();
			superId.add("306");
			superId.add("467");
			List<String> findTreeProjectName = abmTreesDao.findTreeProjectName(superId);
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(abmAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "景观树")) {
			List<String> superId = new ArrayList<>();
			superId.add("309");
			superId.add("470");
			List<String> findTreeProjectName = abmTreesDao.findTreeProjectName(superId);
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(abmAggregateTreeVO);
				}
			}
		} else {
			for (AbmAggregateTreeVO abmAggregateTreeVO : list) {
				String project2 = abmAggregateTreeVO.getProject();
				if (StringUtils.contains(project2, project)) {
					children.add(abmAggregateTreeVO);
				}
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (AbmAggregateTreeVO abmAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				abmAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 递归
				AbmAggregateTreeVO houseChildren = getTreesChildren(list, abmAggregateTreeVO);
				// 统计父节点的总值
				BigDecimal reservoirTotal2 = houseChildren.getReservoirTotal();
				reservoirTotal = reservoirTotal.add(reservoirTotal2);
				BigDecimal pivotTotal2 = houseChildren.getPivotTotal();
				pivotTotal = pivotTotal.add(pivotTotal2);
				BigDecimal flood2 = houseChildren.getFlood();
				flood = flood.add(flood2);
				BigDecimal influence2 = houseChildren.getInfluence();
				influence = influence.add(influence2);
				BigDecimal newTown2 = houseChildren.getNewTown();
				newTown = newTown.add(newTown2);
				BigDecimal permanent2 = houseChildren.getPermanent();
				permanent = permanent.add(permanent2);
				BigDecimal raise2 = houseChildren.getRaise();
				raise = raise.add(raise2);
				BigDecimal temporary2 = houseChildren.getTemporary();
				temporary = temporary.add(temporary2);
			}

			root.setReservoirTotal(reservoirTotal);
			root.setPivotTotal(pivotTotal);
			root.setFlood(flood);
			root.setInfluence(influence);
			root.setNewTown(newTown);
			root.setPermanent(permanent);
			root.setRaise(raise);
			root.setTemporary(temporary);
			root.setChildren(children);
		}

		return root;
	}

	/**
	 * 实物指标汇总--宅基地
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<AbmAggregateCardVO> findHomesteadAggregate(String mergerName) {
		List<AbmAggregateCardVO> findHomesteadAggregate = abmHomesteadDao.findHomesteadAggregate(mergerName);
		return findHomesteadAggregate;
	}

	/**
	 * 实物指标汇总--卡片
	 * 
	 * @param mergerName
	 * @param type
	 * @return
	 */
	public List<AbmAggregateTreeVO> findAggregateCard(String mergerName, String type) {
		List<AbmAggregateTreeVO> list = null;
		if (StringUtils.equals(AbmConstant.AGGREGATE_CARD_POPULATION, type)) {
			List<AbmAggregateCardVO> findPopulationAggregate = findPopulationAggregate(mergerName);
			String jsonString = JSON.toJSONString(findPopulationAggregate);
			list = JSON.parseArray(jsonString, AbmAggregateTreeVO.class);
		} else if (StringUtils.equals(AbmConstant.AGGREGATE_CARD_ENTER, type)) {
			list = findEnterAggregate(mergerName);
		} else if (StringUtils.equals(AbmConstant.AGGREGATE_CARD_HOUSE, type)) {
			list = findHouseAggregate(mergerName);
		} else if (StringUtils.equals(AbmConstant.AGGREGATE_CARD_DECORATION, type)) {
			list = findHouseDecorationAggregate(mergerName);
		} else if (StringUtils.equals(AbmConstant.AGGREGATE_CARD_TREES, type)) {
			list = findTreesAggregate(mergerName);
		} else if (StringUtils.equals(AbmConstant.AGGREGATE_CARD_HOMESTEAD, type)) {
			List<AbmAggregateCardVO> findHomesteadAggregate = findHomesteadAggregate(mergerName);
			String jsonString = JSON.toJSONString(findHomesteadAggregate);
			list = JSON.parseArray(jsonString, AbmAggregateTreeVO.class);
		}
		return list;
	}

	/**
	 * 实物指标汇总--户主/权属人
	 * 
	 * @param mergerName
	 * @return
	 */
	public LyhtResultBody<List<AbmOwnerAggregateVO>> findOwnerAggregate(String mergerName, LyhtPageVO lyhtPageVO) {
		Integer current = lyhtPageVO.getCurrent();
		Integer pageSize = lyhtPageVO.getPageSize();
		List<AbmOwnerAggregateVO> findOwnerAggregate = abmOwnerDao.findOwnerAggregate(mergerName, current, pageSize);
		// 结果集
		int count = abmOwnerDao.countOwnerAggregate(mergerName);
		Integer totalPages = count / pageSize;
		if ((count % pageSize) > 0) {
			totalPages++;
		}
		LyhtPageVO pageVO = new LyhtPageVO(current, totalPages, pageSize, Long.valueOf(count), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(findOwnerAggregate, pageVO);
	}

	/**
	 * 搬迁安置--汇总卡片
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<AbmMoveAggregateCardVO> findMoveAggregateCard(String mergerName, String type) {
		List<AbmMoveAggregateCardVO> findMoveAggregateCard = new ArrayList<>();
		if ("1".equals(type)) {
			findMoveAggregateCard = abmOwnerDao.findMoveAggregateCard(mergerName);
		} else if ("2".equals(type)) {
			findMoveAggregateCard = abmOwnerDao.findMoveAggregateCardIsSatisfy(mergerName);
		} else if ("3".equals(type)) {
			findMoveAggregateCard = abmOwnerDao.findMoveAggregateCardIsNotSatisfy(mergerName);
		}
		return findMoveAggregateCard;
	}

	/**
	 * 按模块统计总户数|已完成户数|未完成户数
	 * 
	 * @return
	 */
	public List<AbmAggregateChatVO> findAggregateChat() {
		List<AbmAggregateChatVO> findAggregateChat = abmAggregateDao.findAggregateChat();
		return findAggregateChat;
	}

}
