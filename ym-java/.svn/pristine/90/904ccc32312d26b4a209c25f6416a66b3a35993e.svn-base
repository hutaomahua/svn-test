package com.lyht.business.info.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.common.constant.InfoConstant;
import com.lyht.business.info.common.enums.InfoLandExceptionEnums;
import com.lyht.business.info.dao.InfoEnterpriseDao;
import com.lyht.business.info.dao.InfoHomesteadDao;
import com.lyht.business.info.dao.InfoHousesDao;
import com.lyht.business.info.dao.InfoHousesDecorationDao;
import com.lyht.business.info.dao.InfoLandDao;
import com.lyht.business.info.dao.InfoOwnerDao;
import com.lyht.business.info.dao.InfoTreesDao;
import com.lyht.business.info.vo.InfoAggregateCardVO;
import com.lyht.business.info.vo.InfoAggregateTreeVO;
import com.lyht.business.info.vo.InfoLandAggregateCardVO;
import com.lyht.business.info.vo.InfoLandAggregateParamVO;
import com.lyht.business.info.vo.InfoLandAggregateTreeVO;
import com.lyht.business.info.vo.InfoLandAggregateVO;
import com.lyht.business.info.vo.InfoOwnerAggregateVO;
import com.lyht.business.info.vo.InfoRegionAggregateVO;
import com.lyht.business.info.vo.InfoRegionTreeVO;
import com.lyht.util.CommonUtil;

@Service
public class InfoAggregateService {

	@Autowired
	private InfoOwnerDao infoOwnerDao;
	@Autowired
	private InfoEnterpriseDao infoEnterpriseDao;
	@Autowired
	private InfoHousesDao infoHousesDao;
	@Autowired
	private InfoHousesDecorationDao infoHousesDecorationDao;
	@Autowired
	private InfoTreesDao infoTreesDao;
	@Autowired
	private InfoHomesteadDao infoHomesteadDao;
	@Autowired
	private InfoLandDao infoLandDao;

	/**
	 * 获取实物指标汇总树--按行政区汇总
	 * 
	 * @return
	 */
	public List<InfoRegionTreeVO> findRegionTree() {
		List<InfoRegionAggregateVO> findRegionAggregate = infoOwnerDao.findRegionAggregate();
		String jsonString = JSON.toJSONString(findRegionAggregate);
		List<InfoRegionTreeVO> parseArray = JSON.parseArray(jsonString, InfoRegionTreeVO.class);

		List<InfoRegionTreeVO> getAggregateTree = getRegionTree(parseArray);

		return getAggregateTree;
	}

	/**
	 * 获取行政区汇总树
	 * 
	 * @param list
	 * @return
	 */
	private List<InfoRegionTreeVO> getRegionTree(List<InfoRegionTreeVO> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		// step1：遍历找出所有根节点，并递归子节点
		List<InfoRegionTreeVO> rootList = new ArrayList<>();
		int startSerialNumber = 1;
		for (InfoRegionTreeVO infoRegionTreeVO : list) {
			String level = infoRegionTreeVO.getLevel();
			if (StringUtils.equals(level, "3")) {
				// 生成序号
				infoRegionTreeVO.setSerialNumber(String.valueOf(startSerialNumber));
				rootList.add(infoRegionTreeVO);
				startSerialNumber++;
			}
		}
		if (!rootList.isEmpty()) {
			list.removeAll(rootList);
			for (InfoRegionTreeVO infoRegionTreeVO : rootList) {
				getRegionChildren(list, infoRegionTreeVO);
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
	private InfoRegionTreeVO getRegionChildren(List<InfoRegionTreeVO> list, InfoRegionTreeVO root) {
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
		List<InfoRegionTreeVO> children = new ArrayList<>();
		for (InfoRegionTreeVO infoRegionTreeVO : list) {
			String parentCode = infoRegionTreeVO.getParentCode();
			if (StringUtils.equalsIgnoreCase(cityCode, parentCode)) {
				children.add(infoRegionTreeVO);
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (InfoRegionTreeVO infoRegionTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				infoRegionTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 获取所有的上级ID
				List<String> childrenParentCodes = new ArrayList<>();
				if (parentCodes == null || parentCodes.isEmpty()) {
					childrenParentCodes.add(cityCode);
				} else {
					childrenParentCodes.addAll(parentCodes);
					childrenParentCodes.add(cityCode);
				}
				infoRegionTreeVO.setParentCodes(childrenParentCodes);
				// 递归
				InfoRegionTreeVO aggregateTree = getRegionChildren(list, infoRegionTreeVO);
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
	public void clearRegionEmptyData(List<InfoRegionTreeVO> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			List<InfoRegionTreeVO> removeList = new ArrayList<>();
			for (InfoRegionTreeVO infoRegionTreeVO : dataList) {
				if (infoRegionTreeVO != null) {
					BigDecimal population = infoRegionTreeVO.getPopulation();
					BigDecimal enterNumber = infoRegionTreeVO.getEnterNumber();
					BigDecimal houseArea = infoRegionTreeVO.getHouseArea();
					BigDecimal houseDecorationArea = infoRegionTreeVO.getHouseDecorationArea();
					BigDecimal treeNumber = infoRegionTreeVO.getTreeNumber();
					BigDecimal homesteadArea = infoRegionTreeVO.getHomesteadArea();
					if (CommonUtil.isZeroOrNull(population) && CommonUtil.isZeroOrNull(enterNumber)
							&& CommonUtil.isZeroOrNull(houseArea) && CommonUtil.isZeroOrNull(houseDecorationArea)
							&& CommonUtil.isZeroOrNull(treeNumber) && CommonUtil.isZeroOrNull(homesteadArea)) {
						// 被清楚的空数据
						removeList.add(infoRegionTreeVO);
					} else {
						// 递归
						List<InfoRegionTreeVO> children = infoRegionTreeVO.getChildren();
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
	public List<InfoAggregateCardVO> findPopulationAggregate(String mergerName) {
		List<InfoAggregateCardVO> findPopulationAggregate = infoOwnerDao.findPopulationAggregate(mergerName);
		return findPopulationAggregate;
	}

	/**
	 * 实物指标汇总--企事业单位
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<InfoAggregateTreeVO> findEnterAggregate(String mergerName) {
		List<InfoAggregateCardVO> findOwnerAggregate = infoEnterpriseDao.findEnterAggregate(mergerName);
		String jsonString = JSON.toJSONString(findOwnerAggregate);
		List<InfoAggregateTreeVO> children = JSON.parseArray(jsonString, InfoAggregateTreeVO.class);
		InfoAggregateTreeVO root = new InfoAggregateTreeVO();

		BigDecimal reservoirTotal = BigDecimal.valueOf(0);
		BigDecimal pivotTotal = BigDecimal.valueOf(0);
		BigDecimal temporary = BigDecimal.valueOf(0);
		BigDecimal permanent = BigDecimal.valueOf(0);
		BigDecimal flood = BigDecimal.valueOf(0);
		BigDecimal influence = BigDecimal.valueOf(0);
		BigDecimal raise = BigDecimal.valueOf(0);
		BigDecimal newTown = BigDecimal.valueOf(0);

		int serialNumber = 1;
		for (InfoAggregateTreeVO infoAggregateTreeVO : children) {
			BigDecimal reservoirTotal2 = infoAggregateTreeVO.getReservoirTotal();
			reservoirTotal = reservoirTotal.add(reservoirTotal2);
			BigDecimal pivotTotal2 = infoAggregateTreeVO.getPivotTotal();
			pivotTotal = pivotTotal.add(pivotTotal2);
			BigDecimal temporary2 = infoAggregateTreeVO.getTemporary();
			temporary = temporary.add(temporary2);
			BigDecimal permanent2 = infoAggregateTreeVO.getPermanent();
			permanent = permanent.add(permanent2);
			BigDecimal flood2 = infoAggregateTreeVO.getFlood();
			flood = flood.add(flood2);
			BigDecimal influence2 = infoAggregateTreeVO.getInfluence();
			influence = influence.add(influence2);
			BigDecimal raise2 = infoAggregateTreeVO.getRaise();
			raise = raise.add(raise2);
			BigDecimal newTown2 = infoAggregateTreeVO.getNewTown();
			newTown = newTown.add(newTown2);
			// 生成序号
			infoAggregateTreeVO.setSerialNumber("1." + serialNumber);
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
		List<InfoAggregateTreeVO> result = new ArrayList<>();
		result.add(root);
		return result;
	}

	/**
	 * 实物指标汇总--房屋
	 * 
	 * @param mergerName
	 * @return
	 */
	public List<InfoAggregateTreeVO> findHouseAggregate(String mergerName) {
		List<InfoAggregateCardVO> findHouseAggregate = infoHousesDao.findHouseAggregate(mergerName);
		String jsonString = JSON.toJSONString(findHouseAggregate);
		List<InfoAggregateTreeVO> list = JSON.parseArray(jsonString, InfoAggregateTreeVO.class);
		InfoAggregateTreeVO root = null;
		for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
			String project = infoAggregateTreeVO.getProject();
			if (StringUtils.equals(project, "房屋")) {
				root = infoAggregateTreeVO;
				root.setSerialNumber("1");
			}
		}
		if (root != null) {
			list.remove(root);
		}
		getHouseChildren(list, root);
		List<InfoAggregateTreeVO> result = new ArrayList<>();
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
	private InfoAggregateTreeVO getHouseChildren(List<InfoAggregateTreeVO> list, InfoAggregateTreeVO root) {
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
		List<InfoAggregateTreeVO> children = new ArrayList<>();
		if (StringUtils.equals(project, "房屋")) {
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (!StringUtils.contains(project2, ",")) {
					children.add(infoAggregateTreeVO);
				}
			}
		} else {
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.contains(project2, project)) {
					children.add(infoAggregateTreeVO);
				}
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (InfoAggregateTreeVO infoAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				infoAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 递归
				InfoAggregateTreeVO houseChildren = getHouseChildren(list, infoAggregateTreeVO);
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
	public List<InfoAggregateTreeVO> findHouseDecorationAggregate(String mergerName) {
		List<InfoAggregateCardVO> findHouseAggregate = infoHousesDecorationDao.findHouseDecorationAggregate(mergerName);
		String jsonString = JSON.toJSONString(findHouseAggregate);
		List<InfoAggregateTreeVO> list = JSON.parseArray(jsonString, InfoAggregateTreeVO.class);
		InfoAggregateTreeVO root = null;
		for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
			String project = infoAggregateTreeVO.getProject();
			if (StringUtils.equals(project, "房屋装修")) {
				root = infoAggregateTreeVO;
				root.setSerialNumber("1");
			}
		}
		if (root != null) {
			list.remove(root);
		}
		getHouseDecorationChildren(list, root);
		List<InfoAggregateTreeVO> result = new ArrayList<>();
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
	private InfoAggregateTreeVO getHouseDecorationChildren(List<InfoAggregateTreeVO> list, InfoAggregateTreeVO root) {
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
		List<InfoAggregateTreeVO> children = new ArrayList<>();
		if (StringUtils.equals(project, "房屋装修")) {
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (!StringUtils.contains(project2, ",")) {
					children.add(infoAggregateTreeVO);
				}
			}
		} else {
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.contains(project2, project)) {
					children.add(infoAggregateTreeVO);
				}
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (InfoAggregateTreeVO infoAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				infoAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 递归
				InfoAggregateTreeVO houseChildren = getHouseDecorationChildren(list, infoAggregateTreeVO);
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
	public List<InfoAggregateTreeVO> findTreesAggregate(String mergerName) {
		List<InfoAggregateCardVO> findHouseAggregate = infoTreesDao.findTreesAggregate(mergerName);
		String jsonString = JSON.toJSONString(findHouseAggregate);
		List<InfoAggregateTreeVO> list = JSON.parseArray(jsonString, InfoAggregateTreeVO.class);
		InfoAggregateTreeVO root = null;
		for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
			String project = infoAggregateTreeVO.getProject();
			if (StringUtils.equals(project, "零星树木")) {
				root = infoAggregateTreeVO;
				root.setSerialNumber("1");
			}
		}
		if (root != null) {
			list.remove(root);
		}
		getTreesChildren(list, root);
		List<InfoAggregateTreeVO> result = new ArrayList<>();
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
	private InfoAggregateTreeVO getTreesChildren(List<InfoAggregateTreeVO> list, InfoAggregateTreeVO root) {
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
		List<InfoAggregateTreeVO> children = new ArrayList<>();
		if (StringUtils.equals(project, "零星树木")) {
			List<String> superId = new ArrayList<>();
			superId.add("287");
			superId.add("447");
			List<String> findTreeProjectName = infoTreesDao.findTreeProjectName(superId);
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(infoAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "果树")) {
			List<String> superId = new ArrayList<>();
			superId.add("288");
			superId.add("448");
			List<String> findTreeProjectName = infoTreesDao.findTreeProjectName(superId);
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(infoAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "经济树")) {
			List<String> superId = new ArrayList<>();
			superId.add("303");
			superId.add("464");
			List<String> findTreeProjectName = infoTreesDao.findTreeProjectName(superId);
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(infoAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "用材树")) {
			List<String> superId = new ArrayList<>();
			superId.add("306");
			superId.add("467");
			List<String> findTreeProjectName = infoTreesDao.findTreeProjectName(superId);
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(infoAggregateTreeVO);
				}
			}
		} else if (StringUtils.equals(project, "景观树")) {
			List<String> superId = new ArrayList<>();
			superId.add("309");
			superId.add("470");
			List<String> findTreeProjectName = infoTreesDao.findTreeProjectName(superId);
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.equalsAny(project2, findTreeProjectName.toArray(new String[findTreeProjectName.size()]))) {
					children.add(infoAggregateTreeVO);
				}
			}
		} else {
			for (InfoAggregateTreeVO infoAggregateTreeVO : list) {
				String project2 = infoAggregateTreeVO.getProject();
				if (StringUtils.contains(project2, project)) {
					children.add(infoAggregateTreeVO);
				}
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (InfoAggregateTreeVO infoAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				infoAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 递归
				InfoAggregateTreeVO houseChildren = getTreesChildren(list, infoAggregateTreeVO);
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
	public List<InfoAggregateCardVO> findHomesteadAggregate(String mergerName) {
		List<InfoAggregateCardVO> findHomesteadAggregate = infoHomesteadDao.findHomesteadAggregate(mergerName);
		return findHomesteadAggregate;
	}

	/**
	 * 实物指标汇总--卡片
	 * 
	 * @param mergerName
	 * @param type
	 * @return
	 */
	public List<InfoAggregateTreeVO> findAggregateCard(String mergerName, String type) {
		List<InfoAggregateTreeVO> list = null;
		if (StringUtils.equals(InfoConstant.AGGREGATE_CARD_POPULATION, type)) {
			List<InfoAggregateCardVO> findPopulationAggregate = findPopulationAggregate(mergerName);
			String jsonString = JSON.toJSONString(findPopulationAggregate);
			list = JSON.parseArray(jsonString, InfoAggregateTreeVO.class);
		} else if (StringUtils.equals(InfoConstant.AGGREGATE_CARD_ENTER, type)) {
			list = findEnterAggregate(mergerName);
		} else if (StringUtils.equals(InfoConstant.AGGREGATE_CARD_HOUSE, type)) {
			list = findHouseAggregate(mergerName);
		} else if (StringUtils.equals(InfoConstant.AGGREGATE_CARD_DECORATION, type)) {
			list = findHouseDecorationAggregate(mergerName);
		} else if (StringUtils.equals(InfoConstant.AGGREGATE_CARD_TREES, type)) {
			list = findTreesAggregate(mergerName);
		} else if (StringUtils.equals(InfoConstant.AGGREGATE_CARD_HOMESTEAD, type)) {
			List<InfoAggregateCardVO> findHomesteadAggregate = findHomesteadAggregate(mergerName);
			String jsonString = JSON.toJSONString(findHomesteadAggregate);
			list = JSON.parseArray(jsonString, InfoAggregateTreeVO.class);
		}
		return list;
	}

	/**
	 * 实物指标汇总--户主/权属人
	 * 
	 * @param mergerName
	 * @return
	 */
	public LyhtResultBody<List<InfoOwnerAggregateVO>> findOwnerAggregate(String mergerName, LyhtPageVO lyhtPageVO) {
		Integer current = lyhtPageVO.getCurrent();
		Integer pageSize = lyhtPageVO.getPageSize();
		List<InfoOwnerAggregateVO> findOwnerAggregate = infoOwnerDao.findOwnerAggregate(mergerName, current, pageSize);
		// 结果集
		int count = infoOwnerDao.countOwnerAggregate(mergerName);
		Integer totalPages = count / pageSize;
		if ((count % pageSize) > 0) {
			totalPages++;
		}
		LyhtPageVO pageVO = new LyhtPageVO(current, totalPages, pageSize, Long.valueOf(count), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(findOwnerAggregate, pageVO);
	}

	/**
	 * 实物指标汇总--土地--树
	 * 
	 * @param mergerName 行政区全称
	 * @return
	 */
	public List<InfoLandAggregateTreeVO> findLandAggregate(String mergerName) {
		List<InfoLandAggregateVO> findLandAggregate = infoLandDao.findLandAggregate(mergerName);
		String jsonString = JSON.toJSONString(findLandAggregate);
		List<InfoLandAggregateTreeVO> list = JSON.parseArray(jsonString, InfoLandAggregateTreeVO.class);
		// step1：遍历找出所有根节点，并递归子节点
		List<InfoLandAggregateTreeVO> rootList = new ArrayList<>();
		int startSerialNumber = 1;
		for (InfoLandAggregateTreeVO infoLandAggregateTreeVO : list) {
			String level = infoLandAggregateTreeVO.getLevel();
			if (StringUtils.equals(level, "3")) {
				// 生成序号
				infoLandAggregateTreeVO.setSerialNumber(String.valueOf(startSerialNumber));
				rootList.add(infoLandAggregateTreeVO);
				startSerialNumber++;
			}
		}
		if (!rootList.isEmpty()) {
			list.removeAll(rootList);
			for (InfoLandAggregateTreeVO infoLandAggregateTreeVO : rootList) {
				getLandChildren(list, infoLandAggregateTreeVO);
			}
			// 递归清空空数据
			clearLandEmptyData(rootList);
			return rootList;
		}
		return null;
	}

	/**
	 * 递归获取土地子节点
	 * 
	 * @param list
	 * @param root
	 * @return
	 */
	private InfoLandAggregateTreeVO getLandChildren(List<InfoLandAggregateTreeVO> list, InfoLandAggregateTreeVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}

		// root属性
		String cityCode = root.getCityCode();// 父ID
		String parentSerialNumber = root.getSerialNumber();// 序号
		BigDecimal total = root.getTotal();
		BigDecimal reservoirTotal = root.getReservoirTotal();
		BigDecimal pivotTotal = root.getPivotTotal();
		BigDecimal flood = root.getFlood();
		BigDecimal influence = root.getInfluence();
		BigDecimal newTown = root.getNewTown();
		BigDecimal permanent = root.getPermanent();
		BigDecimal raise = root.getRaise();
		BigDecimal temporary = root.getTemporary();
		List<String> parentCodes = root.getParentCodes();// 所有上级ID

		// step1：遍历找出子节点
		List<InfoLandAggregateTreeVO> children = new ArrayList<>();
		for (InfoLandAggregateTreeVO infoLandAggregateTreeVO : list) {
			String parentCode = infoLandAggregateTreeVO.getParentCode();
			if (StringUtils.equalsIgnoreCase(cityCode, parentCode)) {
				children.add(infoLandAggregateTreeVO);
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (InfoLandAggregateTreeVO infoLandAggregateTreeVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				infoLandAggregateTreeVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 获取所有的上级ID
				List<String> childrenParentCodes = new ArrayList<>();
				if (parentCodes == null || parentCodes.isEmpty()) {
					childrenParentCodes.add(cityCode);
				} else {
					childrenParentCodes.addAll(parentCodes);
					childrenParentCodes.add(cityCode);
				}
				infoLandAggregateTreeVO.setParentCodes(childrenParentCodes);
				// 递归
				InfoLandAggregateTreeVO aggregateTree = getLandChildren(list, infoLandAggregateTreeVO);
				// 统计父节点的总值
				BigDecimal total2 = aggregateTree.getTotal();
				total = total.add(total2);
				BigDecimal reservoirTotal2 = aggregateTree.getReservoirTotal();
				reservoirTotal = reservoirTotal.add(reservoirTotal2);
				BigDecimal pivotTotal2 = aggregateTree.getPivotTotal();
				pivotTotal = pivotTotal.add(pivotTotal2);
				BigDecimal flood2 = aggregateTree.getFlood();
				flood = flood.add(flood2);
				BigDecimal influence2 = aggregateTree.getInfluence();
				influence = influence.add(influence2);
				BigDecimal newTown2 = aggregateTree.getNewTown();
				newTown = newTown.add(newTown2);
				BigDecimal permanent2 = aggregateTree.getPermanent();
				permanent = permanent.add(permanent2);
				BigDecimal raise2 = aggregateTree.getRaise();
				raise = raise.add(raise2);
				BigDecimal temporary2 = aggregateTree.getTemporary();
				temporary = temporary.add(temporary2);
			}
			root.setTotal(total);
			root.setReservoirTotal(reservoirTotal);
			root.setPivotTotal(pivotTotal);
			root.setFlood(flood);
			root.setInfluence(influence);
			root.setNewTown(newTown);
			root.setPermanent(permanent);
			root.setRaise(raise);
			root.setTemporary(temporary);

			// 找出有数据的行政区，并关联父节点
			root.setChildren(children);
		}

		return root;
	}

	/**
	 * 递归清楚空数据
	 * 
	 * @param dataList
	 */
	public void clearLandEmptyData(List<InfoLandAggregateTreeVO> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			List<InfoLandAggregateTreeVO> removeList = new ArrayList<>();
			for (InfoLandAggregateTreeVO infoLandAggregateTreeVO : dataList) {
				if (infoLandAggregateTreeVO != null) {
					BigDecimal total = infoLandAggregateTreeVO.getTotal();
					if (CommonUtil.isZeroOrNull(total)) {
						// 被清楚的空数据
						removeList.add(infoLandAggregateTreeVO);
					} else {
						// 递归
						List<InfoLandAggregateTreeVO> children = infoLandAggregateTreeVO.getChildren();
						clearLandEmptyData(children);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(removeList)) {
				dataList.removeAll(removeList);
			}
		}
	}

	/**
	 * 实物指标汇总--土地--图表联动
	 * 
	 * @param infoLandAggregateParamVO
	 * @return
	 */
	public List<InfoLandAggregateCardVO> findLandAggregateByLevel(InfoLandAggregateParamVO infoLandAggregateParamVO) {
		// 参数校验
		if (infoLandAggregateParamVO == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		Integer level = infoLandAggregateParamVO.getLevel();
		if (level == null || level < 0 || level > 3) {// 只能查询最小级别为0，最大级别为3的数据
			throw new LyhtRuntimeException(InfoLandExceptionEnums.INVALID_LEVEL);
		}

		List<InfoLandAggregateCardVO> list = null;

		String mergerName = infoLandAggregateParamVO.getMergerName();// 行政区--全称
		String landType = infoLandAggregateParamVO.getLandType();// 对应级别的类型的编码
		List<String> scopes = infoLandAggregateParamVO.getScopes();// 征地范围

		boolean isAll = (scopes == null || scopes.isEmpty() ? true : false);
		if (level == 0) {// 一级---对应的所有大类
			list = infoLandDao.findAggregateAll(mergerName, scopes, isAll);
		} else if (level == 1) {// 二级---对应大类下的所有一级分类
			list = infoLandDao.findAggregateByAllType(mergerName, scopes, landType, isAll);
		} else if (level == 2) {// 三级---对应一级分类下的所有二级分类
			list = infoLandDao.findAggregateByTypeOne(mergerName, scopes, landType, isAll);
		} else if (level == 3) {// 四级---对应二级分类下的所有三级分类
			list = infoLandDao.findAggregateByTypeTwo(mergerName, scopes, landType, isAll);
		}
		
		if (CollectionUtils.isEmpty(list)) {
			throw new LyhtRuntimeException("没有下一级或下一级数据为空!");
		}

		return list;
	}

}
