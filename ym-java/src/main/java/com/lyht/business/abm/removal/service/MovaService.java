package com.lyht.business.abm.removal.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.plan.service.ProjectPlanAdjustService;
import com.lyht.business.abm.removal.bean.MoveIdDetail;
import com.lyht.business.abm.removal.dao.MoveDao;
import com.lyht.business.abm.removal.entity.MoveIdentity;
import com.lyht.business.abm.removal.vo.MoveIdVo;
import com.lyht.business.abm.removal.vo.PlacementVO;
import com.lyht.business.abm.removal.vo.RemovalCountVO;
import com.lyht.business.info.dao.InfoFamilyDao;
import com.lyht.business.info.dao.InfoOwnerDao;
import com.lyht.business.info.entity.InfoFamilyEntity;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.vo.InfoMoveAggregateTreeVO;
import com.lyht.business.info.vo.InfoOwnerDetailVO;
import com.lyht.business.info.vo.InfoOwnerVO;
import com.lyht.business.info.vo.InfoRegionAggregateVO;
import com.lyht.business.info.vo.InfoRegionTreeVO;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovaService {
	private Logger log = LoggerFactory.getLogger(MovaService.class);
	@Autowired
	private MoveDao moveDao;

	@Autowired
	private PubRegionDao pubRegionDao;

	@Autowired
	private InfoOwnerDao infoOwnerDao;

	@Autowired
	private InfoFamilyDao infoFamilyDao;

	public LyhtResultBody getList(LyhtPageVO lyhtPageVO, MoveIdVo moveIdVo) {

		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
//        Page<MoveIdDetail> page = moveDao.getList(pageable, moveIdVo.getName(),moveIdVo.getRegion());
		Page<MoveIdDetail> page = moveDao.getList(pageable, moveIdVo.getName());
		List<MoveIdDetail> result = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());

		return new LyhtResultBody<>(result, pageVO);
	}

	public LyhtResultBody<List<Map>> getListKy(LyhtPageVO lyhtPageVO, String region, String name, Integer count,
			String home) {
		Page<Map> page = getDate(lyhtPageVO, region, name, count, home, 0);
//    	String jsonString = JSON.toJSONString();
		List<Map> list = new ArrayList();
		list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	public Page<Map> getDate(LyhtPageVO lyhtPageVO, String region, String name, Integer count, String home, int num) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = moveDao.getListKy(region, name, count + num, home, pageable);
		List<Map> list = page.getContent();
		if (list == null || list.size() == 0) {
			// 循环100次后还是无数据，则认为没有下一户信息，返回
			// 此处逻辑有问题，解决现有bug时用该写法，按户上下切换时优化
			if (num > 20) {
				return page;
			}
			// 当前根据id查询出来的没有户数，则继续递归下一id进行查询
			return getDate(lyhtPageVO, region, name, count, home, num + 1);
		}
		return page;
	}

	public LyhtResultBody<List<Map>> getQxList(LyhtPageVO lyhtPageVO, String region, String name, Integer count) {

		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = moveDao.getQxList(region, name, count, pageable);
//    	String jsonString = JSON.toJSONString();
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	public List<Map> list(String region, String name, Integer isno) {
		return moveDao.list(region, name, isno);
	}

	public List<Map> getRegion(Integer type, String name) {
		return moveDao.getRegion(type, name);
	}

	// 搬迁可研
	public LyhtResultBody<List<Map>> getBQKY(LyhtPageVO lyhtPageVO, String region, String name, String research,
			String researchType) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = moveDao.getBQKY(pageable, region, name, research, researchType);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	// 搬迁实施 权属人
	public LyhtResultBody<List<Map>> getHomeSs(LyhtPageVO lyhtPageVO, String region, String name, String research,
			String researchType, String idCard) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = moveDao.getHomeSs(pageable, region, name, research, researchType, idCard);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	// 搬迁实施 权属人
	public LyhtResultBody<List<Map>> getHomeSss(LyhtPageVO lyhtPageVO, String region, String name, String research,
			String researchType, String idCard) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = moveDao.getHomeSss(pageable, region, name, research, researchType, idCard);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	@Transactional
	public void updateAnzhi(String type, Integer id) {
		moveDao.updateAnzhi(type, id);
	}

	@Transactional
	public void updateAnzhiss(String type, Integer id) {
		moveDao.updateAnzhiss(type, id);
	}

	public String ojbk() {
		for (int i = 0; i < 10; i++) {
			MoveIdentity s = moveDao.getId(i);
			if (s != null) {
				// 身份证不为空
				if (s.getOwnerNm() != null && !s.getOwnerNm().equals("")) {
					List<Map> list = moveDao.getNm(s.getOwnerNm());
					if (list.size() > 0)// 查询到了身份证 就把权属人复制给 搬迁
					{
						String nm = list.get(0).get("nm").toString();// 权属人nm
						s.setOwnerNm(nm);
						moveDao.save(s);
					}
				} else {
					moveDao.deleteById(i);// 身份证为空就删除
				}
			}

		}
		return "";
	}

	/**
	 * 获取搬迁安置行政区汇总树
	 * 
	 * @return
	 */
	public List<RemovalCountVO> getRemovalAggreate() {
		List<Map<String, Object>> removalCount = moveDao.getRemovalCount();
		String jsonString = JSON.toJSONString(removalCount);
		List<RemovalCountVO> parseArray = JSON.parseArray(jsonString, RemovalCountVO.class);

		List<RemovalCountVO> getAggregateTree = getRegionTree(parseArray);

		return getAggregateTree;
	}

	/**
	 * 行政区树
	 * 
	 * @param list
	 * @return
	 */
	private List<RemovalCountVO> getRegionTree(List<RemovalCountVO> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		// step1：遍历找出所有根节点，并递归子节点
		List<RemovalCountVO> rootList = new ArrayList<>();
		int startSerialNumber = 1;
		for (RemovalCountVO removalCountVO : list) {
			String level = removalCountVO.getLevel();
			if (StringUtils.equals(level, "district")) {
				// 生成序号
				removalCountVO.setSerialNumber(String.valueOf(startSerialNumber));
				rootList.add(removalCountVO);
				startSerialNumber++;
			}
		}
		if (!rootList.isEmpty()) {
			list.removeAll(rootList);
			for (RemovalCountVO removalCountVO : rootList) {
				getRegionChildren(list, removalCountVO);
			}
			// 清空空数据
			clearMoveEmptyData(rootList);
			return rootList;
		}
		return null;
	}

	/**
	 * 行政区树子集
	 * 
	 * @param list
	 * @param root
	 * @return
	 */
	private RemovalCountVO getRegionChildren(List<RemovalCountVO> list, RemovalCountVO root) {
		if (list == null || list.isEmpty() || root == null) {
			return root;
		}

		// root属性
		String cityCode = root.getCitycode();// 父ID
		String parentSerialNumber = root.getSerialNumber();// 序号
		Integer satisfyHourse = root.getSatisfyHourse();// 已界定户数
		Integer notSatisfyHourse = root.getNotSatisfyHourse();// 为界定户数
		Integer satisfyNumber = root.getSatisfyNumber();// 已界定人数
		Integer notSatisfyNumber = root.getNotSatisfyNumber();// 未界定人数
		Integer sumHourse = root.getSumHourse();// 总户数
		Integer sumNumber = root.getSumNumber();// 总人数
		List<String> parentCodes = root.getParentcodes();// 所有上级ID

		// step1：遍历找出子节点
		List<RemovalCountVO> children = new ArrayList<>();
		for (RemovalCountVO removalCountVO : list) {
			String parentCode = removalCountVO.getParentcode();
			if (StringUtils.equalsIgnoreCase(cityCode, parentCode)) {
				children.add(removalCountVO);
			}
		}

		// step2：拼装树、删除已匹配对应父节点的节点,并遍历下一级
		if (!children.isEmpty()) {
			list.removeAll(children);
			int startSerialNumber = 1;
			for (RemovalCountVO removalCountVO : children) {
				// 生成序号
				String serialNumber = null;
				if (StringUtils.isNotBlank(parentSerialNumber)) {
					serialNumber = parentSerialNumber + "." + startSerialNumber;
				} else {
					serialNumber = String.valueOf(startSerialNumber);
				}
				removalCountVO.setSerialNumber(serialNumber);
				startSerialNumber++;
				// 获取所有的上级ID
				List<String> childrenParentCodes = new ArrayList<>();
				if (parentCodes == null || parentCodes.isEmpty()) {
					childrenParentCodes.add(cityCode);
				} else {
					childrenParentCodes.addAll(parentCodes);
					childrenParentCodes.add(cityCode);
				}
				removalCountVO.setParentcodes(childrenParentCodes);
				// 递归
				RemovalCountVO aggregateTree = getRegionChildren(list, removalCountVO);
				// 统计父节点的总值
				Integer satisfyHourse2 = aggregateTree.getSatisfyHourse();
				satisfyHourse += satisfyHourse2;
				Integer satisfyNumber2 = aggregateTree.getSatisfyNumber();
				satisfyNumber += satisfyNumber2;
				Integer notSatisfyHourse2 = aggregateTree.getNotSatisfyHourse();
				notSatisfyHourse += notSatisfyHourse2;
				Integer notSatisfyNumber2 = aggregateTree.getNotSatisfyNumber();
				notSatisfyNumber += notSatisfyNumber2;
				Integer sumHourse2 = aggregateTree.getSumHourse();
				sumHourse += sumHourse2;
				Integer sumNumber2 = aggregateTree.getSumNumber();
				sumNumber += sumNumber2;
			}
			root.setSatisfyHourse(satisfyHourse);
			root.setSatisfyNumber(satisfyNumber);
			root.setNotSatisfyHourse(notSatisfyHourse);
			root.setNotSatisfyNumber(notSatisfyNumber);
			root.setSumHourse(sumHourse);
			root.setSumNumber(sumNumber);
			root.setChildren(children);
		}

		return root;
	}

	/**
	 * 递归清空空数据
	 * 
	 * @param dataList
	 */
	public void clearMoveEmptyData(List<RemovalCountVO> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			List<RemovalCountVO> removeList = new ArrayList<>();
			for (RemovalCountVO removalCountVO : dataList) {
				if (removalCountVO != null) {
					Integer sumHourse = removalCountVO.getSumHourse();
					Integer sumNumber = removalCountVO.getSumNumber();
					if (CommonUtil.isZeroOrNull(sumHourse) && CommonUtil.isZeroOrNull(sumNumber)) {
						// 被清楚的空数据
						removeList.add(removalCountVO);
					} else {
						// 递归
						List<RemovalCountVO> children = removalCountVO.getChildren();
						clearMoveEmptyData(children);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(removeList)) {
				dataList.removeAll(removeList);
			}
		}
	}

	public List<RemovalCountVO> getRemovalCount() {
		// 查出所有组
		List<Map<String, Object>> list = pubRegionDao.getRemovalCount();
		List<RemovalCountVO> removalCountVos = new ArrayList<>();
		list.forEach(v -> {
			RemovalCountVO removalCountVo = new RemovalCountVO();
			int satisfyHourse = Integer.parseInt(v.get("satisfyHourse").toString());
			int notSatisfyHourse = Integer.parseInt(v.get("NotSatisfyHourse").toString());
			int sumHourse = Integer.parseInt(v.get("SumHourse").toString());
			int satisfyNumber = Integer.parseInt(v.get("satisfyNumber").toString());
			int notSatisfyNumber = Integer.parseInt(v.get("notSatisfyNumber").toString());
			int sumNumber = Integer.parseInt(v.get("sumNumber").toString());

			if (satisfyHourse > 0 || notSatisfyHourse > 0 || sumHourse > 0 || satisfyNumber > 0 || notSatisfyNumber > 0
					|| sumNumber > 0) {
				String citycode = (String) v.get("citycode");
				String parentcode = (String) v.get("parentcode");
				String name = (String) v.get("name");
				String level = (String) v.get("level");
				String mergerShortName = (String) v.get("mergerShortName");
				removalCountVo.setSatisfyHourse(satisfyHourse);
				removalCountVo.setNotSatisfyHourse(notSatisfyHourse);
				removalCountVo.setSumHourse(sumHourse);
				removalCountVo.setSatisfyNumber(satisfyNumber);
				removalCountVo.setNotSatisfyNumber(notSatisfyNumber);
				removalCountVo.setSumNumber(sumNumber);
				removalCountVo.setCitycode(citycode);
				removalCountVo.setParentcode(parentcode);
				removalCountVo.setMergerShortName(mergerShortName);
				removalCountVo.setName(name);
				removalCountVo.setLevel(level);
				removalCountVo.setState(1);
				removalCountVos.add(removalCountVo);
			}
		});
		List<RemovalCountVO> removalCountVos3 = new ArrayList<>();
		List<PubRegionEntity> all = pubRegionDao.list(null, null, null, "维西", null);
		Map<String, Object> map = new HashMap<>(all.size());
		all.stream().forEach(v -> map.put((String) v.getName(), v.getCityCode()));
		List<RemovalCountVO> tree = tree(removalCountVos, removalCountVos3, 2, map);
		Collections.reverse(tree);
		RemovalCountVO removalCountVo = tree.get(0);
		// 获取到省 cod
		String cityCode = removalCountVo.getCitycode();
		tree.remove(removalCountVo);
		removalCountVo.setSerialNumber("1");
		RemovalCountVO removalCountVo1 = tree2(tree, cityCode, removalCountVo);
		ArrayList<RemovalCountVO> objects = Lists.newArrayList();
		objects.add(removalCountVo1);
		return tree3(objects, map);
	}

	public List<RemovalCountVO> tree3(List<RemovalCountVO> removalCountVos, Map<String, Object> map) {
		for (RemovalCountVO removalCountVo : removalCountVos) {
			String mergerShortName = removalCountVo.getMergerShortName();
			if (StringUtils.isNotBlank(mergerShortName)) {
				String[] split = mergerShortName.split(",");
				ArrayList<String> strings = new ArrayList<>(Arrays.asList(split));
				ArrayList<String> objects = Lists.newArrayList();
				strings.stream().forEach(k -> objects.add((String) map.get(k)));
				removalCountVo.setParentcodes(objects);
				List<RemovalCountVO> children = removalCountVo.getChildren();
				if (children.size() > 0) {
					tree3(children, map);
				}
			} else {
				continue;
			}
		}
		return removalCountVos;
	}

	public RemovalCountVO tree2(List<RemovalCountVO> tree, String cityCode, RemovalCountVO removalCountVo) {
		int code = 1;
		List<RemovalCountVO> removalCountVos = getRemovalCountVoByCityCode(cityCode, tree);
		tree.removeAll(removalCountVos);
		for (RemovalCountVO r : removalCountVos) {
			String serialNumber = removalCountVo.getSerialNumber() + "." + code;
			r.setSerialNumber(serialNumber);

			tree2(tree, r.getCitycode(), r);
			code++;
		}
		removalCountVo.setChildren(removalCountVos);
		return removalCountVo;
	}

	public List<RemovalCountVO> getRemovalCountVoByCityCode(String cityCode, List<RemovalCountVO> tre) {
		List<RemovalCountVO> removalCountVos = new ArrayList<>();
		for (RemovalCountVO removalCountVo : tre) {
			if (removalCountVo.getParentcode().equals(cityCode)) {
				removalCountVos.add(removalCountVo);
			}
		}
		return removalCountVos;
	}

	public List<RemovalCountVO> tree(List<RemovalCountVO> removalCountVos, List<RemovalCountVO> removalCountVos3,
			int isOne, Map<String, Object> map1) {
		removalCountVos3.addAll(removalCountVos);
		List<RemovalCountVO> removalCountVos2 = new ArrayList<>();
		if (removalCountVos.size() == 1 && null == removalCountVos.get(0).getParentcode()) {
			return removalCountVos3;
		}
		Map<String, List<RemovalCountVO>> map = removalCountVos.stream()
				.collect(Collectors.groupingBy(RemovalCountVO::getParentcode));
		int finalIsOne = isOne;
		int num = 0;
		Set<String> strings = map.keySet();
		List<PubRegionEntity> regionEntities = pubRegionDao.findByCityCodeIn(strings);
		Map<String, List<PubRegionEntity>> map2 = regionEntities.stream()
				.collect(Collectors.groupingBy(PubRegionEntity::getCityCode));

		map.keySet().stream().forEach(v -> {
			RemovalCountVO removalCountVo = new RemovalCountVO();

			List<RemovalCountVO> removalCountVos1 = map.get(v);
			int satisfyHourse = removalCountVos1.stream().mapToInt(RemovalCountVO::getSatisfyHourse).sum();
			int notSatisfyHourse = removalCountVos1.stream().mapToInt(RemovalCountVO::getNotSatisfyHourse).sum();
			int sumHourse = removalCountVos1.stream().mapToInt(RemovalCountVO::getSumHourse).sum();
			int satisfyNumber = removalCountVos1.stream().mapToInt(RemovalCountVO::getSatisfyNumber).sum();
			int notSatisfyNumber = removalCountVos1.stream().mapToInt(RemovalCountVO::getNotSatisfyNumber).sum();
			int sumNumber = removalCountVos1.stream().mapToInt(RemovalCountVO::getSumNumber).sum();
			if (satisfyHourse > 0 || notSatisfyHourse > 0 || sumHourse > 0 || satisfyNumber > 0 || notSatisfyNumber > 0
					|| sumNumber > 0) {
				PubRegionEntity regionEntity = null;
				List<PubRegionEntity> pubRegionEntities = map2.get(v);
				if (pubRegionEntities.size() > 0) {
					regionEntity = pubRegionEntities.get(0);
				}
				if (null != regionEntity) {
					removalCountVo.setSatisfyHourse(satisfyHourse);
					removalCountVo.setNotSatisfyHourse(notSatisfyHourse);
					removalCountVo.setSumHourse(sumHourse);
					removalCountVo.setSatisfyNumber(satisfyNumber);
					removalCountVo.setNotSatisfyNumber(notSatisfyNumber);
					removalCountVo.setSumNumber(sumNumber);
					removalCountVo.setCitycode(regionEntity.getCityCode());
					removalCountVo.setParentcode(regionEntity.getParentCode());
					removalCountVo.setName(regionEntity.getName());
					removalCountVo.setLevel(regionEntity.getLevel());
					removalCountVo.setMergerShortName(regionEntity.getMergerName());
					removalCountVo.setState(finalIsOne);
					removalCountVo.setSerialNumber((num + 1) + "");
					removalCountVos2.add(removalCountVo);
				}
			}
		});
		if (removalCountVos2.size() > 0) {
			tree(removalCountVos2, removalCountVos3, finalIsOne + 1, map1);
		} else {
			return removalCountVos3;
		}
		return removalCountVos3;
	}

	public List<PlacementVO> getPlacementCount(String nm, Integer isSatisfy) {
		PubRegionEntity pubRegionEntity = pubRegionDao.findByCityCode(nm);
		if (6 == pubRegionEntity.getLevelType()) {
			return null;
		}
		ArrayList<String> objects = Lists.newArrayList();
		objects.add(nm);
		ArrayList<String> ss = Lists.newArrayList();
		ArrayList<String> strings = tree2(objects, ss);

		Map<String, String> map = new HashMap(4);
		map.put("5866417EC7", "农村集中安置");
		map.put("E0AD4AB501", "集镇集中安置");
		map.put("2E8EDB1C69", "分散后靠");
		map.put("C7B441FEE9", "分散货币");
		return tree3(map, strings, isSatisfy);
	}

	public List<PlacementVO> tree3(Map<String, String> map, ArrayList<String> stringss, Integer isSatisfy) {
		int one = 1;
		List<PlacementVO> placementVOSS = new ArrayList<>();
		Set<String> strings = map.keySet();
		for (String s : strings) {
			List<InfoOwnerEntity> infoOwnerEntities = null;
			List<InfoFamilyEntity> infoFamilyEntities = null;
			if (null == isSatisfy) {
				infoOwnerEntities = infoOwnerDao.findAllByPlaceTypeAndRegionIn(s, stringss);
				infoFamilyEntities = infoFamilyDao.findAllByPlaceTypeAndRegionIn(s, stringss);
			} else {
				if (1 == isSatisfy) {
					infoOwnerEntities = infoOwnerDao.findAllByPlaceTypeAndRegionInAndIsSatisfy(s, stringss, null);
					infoFamilyEntities = infoFamilyDao.findAllByPlaceTypeAndRegionInAndIsSatisfy(s, stringss, null);
				}
				if (2 == isSatisfy) {
					infoOwnerEntities = infoOwnerDao.findAllByPlaceTypeAndRegionInAndIsSatisfy(s, stringss, "2");
					infoFamilyEntities = infoFamilyDao.findAllByPlaceTypeAndRegionInAndIsSatisfy(s, stringss, "2");
				}
			}
			if (null == infoOwnerEntities || infoFamilyEntities.size() == 0) {
				continue;
			}
			PlacementVO placementVO = new PlacementVO();
			placementVO.setSerialNumber(one + "");
			placementVO.setPlaceName(map.get(s));
			placementVO.setSumHourse(infoOwnerEntities.size());
			placementVO.setSumNumber(infoFamilyEntities.size());
			Map<String, List<InfoOwnerEntity>> map1 = infoOwnerEntities.stream()
					.collect(Collectors.groupingBy(InfoOwnerEntity::getPlaceName));
			Map<String, List<InfoFamilyEntity>> map2 = infoFamilyEntities.stream()
					.collect(Collectors.groupingBy(InfoFamilyEntity::getPlaceName));
			ArrayList<PlacementVO> placementVOS = Lists.newArrayList();
			Set<String> strings1 = map1.keySet();
			int i = 1;
			for (String s1 : strings1) {
				PlacementVO placementVO1 = new PlacementVO();
				placementVO1.setSerialNumber(placementVO.getSerialNumber() + "." + i);
				placementVO1.setPlaceName(s1);
				placementVO1.setSumHourse(map1.get(s1).size());
				if (null == map2.get(s1)) {
					placementVO1.setSumNumber(0);
				} else {
					placementVO1.setSumNumber(map2.get(s1).size());
				}
				placementVOS.add(placementVO1);
				i++;
			}
			placementVO.setPlacementVOList(placementVOS);
			one++;
			placementVOSS.add(placementVO);
		}
		return placementVOSS;
	}

	public ArrayList<String> tree2(List<String> nm, ArrayList<String> ss) {
		List<PubRegionEntity> pubRegionEntities = pubRegionDao.findAllByParentCodeIn(nm);
		if (pubRegionEntities.size() > 0) {
			if (pubRegionEntities.get(0).getLevelType() != 6) {
				List<String> cityCodes = pubRegionEntities.stream().map(PubRegionEntity::getCityCode)
						.collect(Collectors.toList());
				tree2(cityCodes, ss);
			} else {
				ss.addAll(pubRegionEntities.stream().map(PubRegionEntity::getCityCode).collect(Collectors.toList()));
			}
		}
		return ss;
	}
}