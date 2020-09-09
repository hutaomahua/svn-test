package com.lyht.business.abm.landAllocation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.el.ELException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.lyht.Constants;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.dao.EmLandPoolDao;
import com.lyht.business.abm.landAllocation.dao.LandAuditDao;
import com.lyht.business.abm.landAllocation.dao.LandPoolDao;
import com.lyht.business.abm.landAllocation.dao.LandPoolProcessDao;
import com.lyht.business.abm.landAllocation.entity.LandAuditEntity;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.landAllocation.entity.LandPoolProcess;
import com.lyht.business.abm.production.thread.ThreadPool;
import com.lyht.business.abm.removal.dao.AbmLandDao;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.process.vo.ProcessStartVO;
import com.lyht.business.pub.dao.PubDictValueDao;
import com.lyht.business.pub.dao.PubProjectDao;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.entity.PubProjectEntity;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.system.pojo.SysAcct;
import com.lyht.util.CommonUtil;
import com.lyht.util.DateUtil;
import com.lyht.util.Randomizer;

@Service
public class LandDataSyncService {

	@Autowired
	private ProcessOperateService processOperateService;

	@Autowired
	private LandPoolDao landPoolDao;

	@Autowired
	private LandPoolProcessDao landPoolProcessDao;

	@Autowired
	private PubRegionDao regionDao;

	@Autowired
	private EmLandPoolDao emLandPoolDao;

	@Autowired
	private LandAuditDao landAuditEntityDao;

	@Autowired
	private AbmLandDao tudiDao;

	@Autowired
	private AbmOwnerDao abmOwnerDao;

	@Autowired
	private AbmLandDao landDao;

	@Autowired
	private PubDictValueDao dictDao;

	@Autowired
	private PubProjectDao projectDao;

	@Autowired
	private LandPoolProcessService landPoolProcessService;

	@Value("${iwind.process.flow.path.land.resolve}")
	private String landResolvePath;

	private String topLevel = "province";
	private String district = "DCFD44B3E9";// 维西县行政区编码
	private String districtLevel = "";// 维西县行政区级别
	private List<String> levelList = new ArrayList<String>();

	public LyhtResultBody<List<Map<String, Object>>> queryLandScope(String region, String allType, String typeOne,
			String typeTwo, String typeThree) {
		List<Map<String, Object>> list = null;
		if (StringUtils.isBlank(typeThree)) {// 无三级分类
			list = landAuditEntityDao.queryLandScope(region, allType, typeOne, typeTwo);
		} else {
			list = landAuditEntityDao.queryLandScope(region, allType, typeOne, typeTwo, typeThree);
		}
		return new LyhtResultBody<>(list);
	}

	public void sync() {
		ArrayList<LandPoolEntity> result = new ArrayList<LandPoolEntity>();
		/*
		 * 业务执行之前先清除全部数据
		 */
		landPoolDao.deleteAll();
		tudiDao.deleteAll();
		/*
		 * 初始化维西县行政区级别
		 */
		districtLevel = regionDao.getRegionLevelByCityCode(district);
		/*
		 * 获取顶级行政区，将土地总量纳入顶级行政区中
		 */
		PubRegionEntity topLevelRegion = null;
		List<PubRegionEntity> regionList = regionDao.queryRegionByLevel(topLevel);
		if (regionList.size() == 1) {
			topLevelRegion = regionList.get(0);
		} else {
			for (PubRegionEntity region : regionList) {
				if (district.equals(region.getCityCode())) {
					topLevelRegion = region;
				}
			}
		}
		if (topLevelRegion == null)
			throw new ELException();// 没有唯一的顶级行政区//或者并级行政区中找不到维西县
		List<Map<String, Object>> totalData = landPoolDao.queryTotalData();

		for (Map<String, Object> map : totalData) {
			LandPoolEntity landPool = new LandPoolEntity();
			landPool.setNm(Randomizer.generCode(10));
			landPool.setRegion(topLevelRegion.getCityCode());
			landPool.setRegionLevel(topLevelRegion.getLevel());
			landPool.setScope(CommonUtil.trim(map.get("scope")).toString());
			landPool.setAllType(CommonUtil.trim(map.get("alltype")).toString());
			landPool.setTypeOne(CommonUtil.trim(map.get("typeone")).toString());
			landPool.setTypeTwo(CommonUtil.trim(map.get("typetwo")).toString());
			landPool.setTypeThree(CommonUtil.trim(map.get("typethree")).toString());
			landPool.setStatus(0);
			landPool.setArea(CommonUtil.parseBigDecimal(map.get("area")));
			landPool.setSeparateArea(CommonUtil.parseBigDecimal(map.get("separatearea")));
			result.add(landPool);
		}
		/*
		 * 查询出全部土地数据并纳入土地分解池中
		 */
		List<Map<String, Object>> allLandData = landPoolDao.queryAllData();
		for (Map<String, Object> map : allLandData) {
			LandPoolEntity landPool = new LandPoolEntity();
			landPool.setNm(Randomizer.generCode(10));
			if ("".equals(CommonUtil.trim(map.get("region")).toString())) {
				landPool.setRegion(district);
				landPool.setRegionLevel(districtLevel);
			} else {
				landPool.setRegion(CommonUtil.trim(map.get("region")).toString());
				landPool.setRegionLevel(CommonUtil.trim(map.get("regionlevel")).toString());
			}
			landPool.setScope(CommonUtil.trim(map.get("scope")).toString());
			landPool.setAllType(CommonUtil.trim(map.get("alltype")).toString());
			landPool.setTypeOne(CommonUtil.trim(map.get("typeone")).toString());
			landPool.setTypeTwo(CommonUtil.trim(map.get("typetwo")).toString());
			landPool.setTypeThree(CommonUtil.trim(map.get("typethree")).toString());

			landPool.setArea(CommonUtil.parseBigDecimal(map.get("area")));
			landPool.setStatus(0);
			landPool.setSeparateArea(CommonUtil.parseBigDecimal(map.get("separatearea")));
			result.add(landPool);
		}
		/*
		 * 查询出全部已分解到户土地数据并纳入到户土地表中
		 */

		List<Map<String, Object>> ownerLandData = landPoolDao.queryOwnerLandData();
		ArrayList<AbmLandEntity> tudiResult = new ArrayList<AbmLandEntity>();
		for (Map<String, Object> map : ownerLandData) {
			AbmLandEntity tudi = new AbmLandEntity();
			tudi.setNm(Randomizer.generCode(10));
			tudi.setProjectNm(CommonUtil.trim(map.get("projectnm")).toString());
			tudi.setRegion(CommonUtil.trim(map.get("region")).toString());
			tudi.setScope(CommonUtil.trim(map.get("scope")).toString());
			tudi.setUnit(CommonUtil.trim(map.get("unit")).toString());
			tudi.setAllType(CommonUtil.trim(map.get("alltype")).toString());
			tudi.setTypeOne(CommonUtil.trim(map.get("typeone")).toString());
			tudi.setTypeTwo(CommonUtil.trim(map.get("typetwo")).toString());
			tudi.setTypeThree(CommonUtil.trim(map.get("typethree")).toString());
			tudi.setArea(CommonUtil.parseBigDecimal(map.get("area")));
			tudi.setIdCard(CommonUtil.trim(map.get("idcard")));
			tudi.setOwnerNm(CommonUtil.trim(map.get("ownernm")));
			tudi.setLandProjectNm(CommonUtil.trim(map.get("landprojectnm")));
			tudiResult.add(tudi);
		}
		tudiDao.saveAll(tudiResult);
		/*
		 * 查询出组已分解数
		 */
		List<Map<String, Object>> groupSeparateData = landPoolDao.queryGroupSeparateData();

		for (Map<String, Object> map : groupSeparateData) {
			LandPoolEntity landPool = new LandPoolEntity();
			landPool.setNm(Randomizer.generCode(10));
			landPool.setRegion(CommonUtil.trim(map.get("region")).toString());
			landPool.setRegionLevel(CommonUtil.trim(map.get("regionlevel")).toString());
			landPool.setScope(CommonUtil.trim(map.get("scope")).toString());
			landPool.setAllType(CommonUtil.trim(map.get("alltype")).toString());
			landPool.setTypeOne(CommonUtil.trim(map.get("typeone")).toString());
			landPool.setTypeTwo(CommonUtil.trim(map.get("typetwo")).toString());
			landPool.setTypeThree(CommonUtil.trim(map.get("typethree")).toString());

			landPool.setArea(CommonUtil.parseBigDecimal(map.get("area")));
			landPool.setSeparateArea(CommonUtil.parseBigDecimal(map.get("separatearea")));
			landPool.setStatus(0);
			for (LandPoolEntity item : result) {
				if ("group".equals(item.getRegionLevel()) && item.getRegion().equals(landPool.getRegion())
						&& item.getScope().equals(landPool.getScope())
						&& item.getAllType().equals(landPool.getAllType())
						&& item.getTypeOne().equals(landPool.getTypeOne())
						&& item.getTypeTwo().equals(landPool.getTypeTwo())
						&& item.getTypeThree().equals(landPool.getTypeThree())) {
					item.setSeparateArea(item.getSeparateArea().add(landPool.getArea()));
				}
			}
		}
		/*
		 * 根据已查询出的数据组合分解池
		 */
		levelList = regionDao.queryLevelList(null);
		for (int i = 0; i < levelList.size(); i++) {
			iterator(result, levelList.get(i));
		}
		/*
		 * 将整理好的结果集插入到数据库
		 */
		landPoolDao.saveAll(result);

	}

	public void iterator(ArrayList<LandPoolEntity> result, String level) {
		System.err.println(level);
		// 获取当前处理级别数据
		for (int i = 0; i < result.size(); i++) {
			LandPoolEntity item = result.get(i);
			if (level.equals(item.getRegionLevel())) {
				PubRegionEntity parentRegion = regionDao.getParentRegionByCityCode(item.getRegion(), null);
				if (parentRegion == null)
					return;
				// 获取当前处理级别的父级数据，最终要改动这部分数据
				Boolean flag = false;
				for (int y = 0; y < result.size(); y++) {
					LandPoolEntity pItem = result.get(y);
					if (pItem.getRegion().equals(parentRegion.getCityCode()) && item.getScope().equals(pItem.getScope())
							&& item.getAllType().equals(pItem.getAllType())
							&& item.getTypeOne().equals(pItem.getTypeOne())
							&& item.getTypeTwo().equals(pItem.getTypeTwo())
							&& item.getTypeThree().equals(pItem.getTypeThree())) {
						flag = true;
						pItem.setSeparateArea(pItem.getSeparateArea().add(item.getArea()));
						if (!topLevel.equals(parentRegion.getLevel())) {
							pItem.setArea(pItem.getArea().add(item.getArea()));
						}
					}
				}
				// 结果集中不存在父级池，新增父级池
				if (!flag) {
					LandPoolEntity patentLandPool = new LandPoolEntity();
					patentLandPool.setNm(Randomizer.generCode(10));
					patentLandPool.setRegion(parentRegion.getCityCode());
					patentLandPool.setRegionLevel(parentRegion.getLevel());
					patentLandPool.setScope(item.getScope());
					patentLandPool.setAllType(item.getAllType());
					patentLandPool.setTypeOne(item.getTypeOne());
					patentLandPool.setTypeTwo(item.getTypeTwo());
					patentLandPool.setTypeThree(item.getTypeThree());
					patentLandPool.setArea(item.getArea());
					patentLandPool.setStatus(0);
					patentLandPool.setSeparateArea(item.getArea());
					result.add(patentLandPool);
				}
			}
		}
	}

	/**
	 * 查询土地面积详情
	 *
	 * @param cityCode 行政编码
	 * @param status   土地状态
	 * @param level    下钻的层数
	 * @return
	 */
	public List<Map<String, Object>> queryAreaInfo(String cityCode, String status, int level) {
		if (StringUtils.isBlank(cityCode) || StringUtils.isBlank(status)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		try {
			return emLandPoolDao.queryLandData(cityCode, status, level, false);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 查询土地分解数据 改了核心代码 不需要详情了 待优化
	 *
	 * @param cityCode
	 * @param level
	 * @return
	 */
	public List<Map<String, Object>> landResolve(String cityCode, int level) {
		try {
			List<Map<String, Object>> landData = emLandPoolDao.queryLandData(cityCode, null, level, true),
					rData = new ArrayList<>();
			Map<String, Object> cz = new HashMap<>();
			for (Map<String, Object> olandDatum : landData) {
				String oneLandTypeCode = olandDatum.get("landTypeCode") != null
						? olandDatum.get("landTypeCode").toString()
						: null;
				Boolean oneIsNull = oneLandTypeCode == null;
				// 判断是否为空或是否存在于数组
				if (oneIsNull || cz.containsKey(oneLandTypeCode)) {
					continue;
				}
				// 存放统一土地类型相关数据Map
				Map<String, Object> landType = new HashMap<>();
				landType.put("landType", olandDatum.get("landType"));
				landType.put("landTypeCode", oneLandTypeCode);
				cz.put(oneLandTypeCode, oneLandTypeCode);
				BigDecimal area = new BigDecimal(0), useArea = new BigDecimal(0), remainArea = new BigDecimal(0);
				for (Map<String, Object> tlandDatum : landData) {
					String twoLandTypeCode = tlandDatum.get("landTypeCode") != null
							? tlandDatum.get("landTypeCode").toString()
							: null;
					Boolean twoIsNull = oneLandTypeCode == null;
					if (twoIsNull) {
						continue;
					}
					// 如果土地类型相同 则组合在一起
					if (oneLandTypeCode.equals(twoLandTypeCode)) {
						// 把相同的各个面积相加
						BigDecimal areaTemp = tlandDatum.get("area") != null
								? new BigDecimal(tlandDatum.get("area").toString())
								: new BigDecimal(0),
								useAreaTemp = tlandDatum.get("useArea") != null
										? new BigDecimal(tlandDatum.get("useArea").toString())
										: new BigDecimal(0),
								remainAreaTemp = tlandDatum.get("remainArea") != null
										? new BigDecimal(tlandDatum.get("remainArea").toString())
										: new BigDecimal(0);
						// 总面积相加
						area = area.add(areaTemp);
						// 以分解面积相加
						useArea = useArea.add(useAreaTemp);
						// 待分解面积相加
						remainArea = remainArea.add(remainAreaTemp);
					}
				}
				// 存入总的各个面积 方便前端的图形展示
				landType.put("areaSum", area);
				landType.put("useAreaSum", useArea);
				landType.put("remainAreaSum", remainArea);
				rData.add(landType);
			}
			return rData;
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 查询土地分解数据 统计图表数据
	 * 
	 * @param cityCode
	 * @param typeAll
	 * @param typeOne
	 * @param typeTwo
	 * @param typeThree
	 * @return
	 */
	public List<Map<String, Object>> queryLandChart(String cityCode, String typeAll, String typeOne, String typeTwo,
			String typeThree, Boolean isLast, String status) {
		try {
			List<Map<String, Object>> maps = emLandPoolDao.queryLandChart(cityCode, typeAll, typeOne, typeTwo,
					typeThree, isLast, status);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 根据行政区获得可分解的土地类型
	 * 
	 * @param cityCode
	 * @param typeAll
	 * @param typeOne
	 * @param typeTwo
	 * @param typeThree
	 * @return
	 */
	public List<Map<String, Object>> getLandType(String cityCode, String typeAll, String typeOne, String typeTwo,
			String typeThree, Boolean isLast) {
		try {
			// 根据行政区编码获得所有的土地类型
			List<Map<String, Object>> maps = emLandPoolDao.queryLandType(cityCode, typeAll, typeOne, typeTwo, typeThree,
					isLast);
			return maps;
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 查询子行政区
	 *
	 * @param cityCode
	 * @return
	 */
	@SuppressWarnings({ "null", "rawtypes", "unchecked" })
	public List<Map<String, Object>> queryChildCity(String cityCode) {
		try {
			List<Map<String, Object>> citys = landPoolDao.queryChildCity(cityCode);
			// 如果该行政区不存在子行政区 则去查询户主信息
			if (citys == null || citys.size() == 0) {
				// 根据行政区编码查询户主信息
				List list = new ArrayList();
				List<Map<String, Object>> userList = landPoolDao.queryAllUserInfos(cityCode);
				if (userList != null || userList.size() != 0) {
					for (Map<String, Object> map : userList) {
						String protocolState = map.get("protocolState").toString();
						if (protocolState.equals("0")) {
							list.add(map);
						}
					}
				}
				return list;
			}
			// 将查询出的行政区转为树形结构
			citys = toTree(citys, cityCode, true, 0, null, new ArrayList<>());
			return citys;
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	@Autowired
	private LandAuditService landAuditService;

	public void updateState() {
		try {
			// 使用线程池创建线程
			ThreadPool.pool.execute(new Runnable() {
				@Override
				public void run() {
					// 查询在土地分解审核表中未完成的数据 并且在流程表里面不是处理中状态
					List<Map<String, Object>> incomplete = landAuditEntityDao.findIncomplete();
					if (incomplete == null || incomplete.size() == 0) {
						return;
					}
					for (Map<String, Object> map : incomplete) {
						String status = map.get("status") != null ? map.get("status").toString() : null;
						Integer id = map.get("id") != null ? Integer.valueOf(map.get("id").toString()) : 0;
						if ("Approved".equals(status)) { // 流程通过了
							landAuditService.landAudit(id, "64FABB5F0D", null);
						} else if ("Rejected".equals(status)) { // 流程被拒绝了
							landAuditService.landAudit(id, "36845517AF", null);
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询土地分解用户列表数据
	 * 
	 * @param cityName
	 * @param uname
	 * @param idCard
	 * @return
	 */
	public LyhtResultBody<List> queryLandInfoByRegion(String mergerName, String name, String idCard,
			LyhtPageVO lyhtPageVO) {
		try {
			Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
			// 查询所有土地分解审核信息
			Page<Map<String, Object>> page = landPoolDao.queryLandInfoByRegion(mergerName, name, idCard, pageable);
			LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
					page.getTotalElements(), lyhtPageVO.getSorter());
			return new LyhtResultBody<>(page.getContent(), pageVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}

	}

	public LyhtResultBody<List> queryTypeList(String cityCode) {
		return new LyhtResultBody<>(landPoolDao.queryTypeList(cityCode));
	}

	/**
	 * 查询土地分解用户列表数据
	 * 
	 * @return
	 */
	public LyhtResultBody<List> queryScopeList(String cityName, String typeNm, int level, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		// 查询所有土地分解审核信息
		Page<Map<String, Object>> page = null;
		if (0 == level) {
			page = landPoolDao.queryTypeList(cityName, pageable);
		} else if (1 == level) {
			page = landPoolDao.queryOneTypeList(cityName, typeNm, pageable);
		} else if (2 == level) {
			page = landPoolDao.queryTwoTypeList(cityName, typeNm, pageable);
		} else if (3 == level) {
			page = landPoolDao.queryThreeTypeList(cityName, typeNm, pageable);
		}

		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(page.getContent(), pageVO);

	}

	/**
	 * 查询土地分解用户列表数据
	 * 
	 * @return
	 */
	public LyhtResultBody<Map> queryOwnerList(String cityCode, String typeAll, String typeOne, String typeTwo,
			String typeThree, LyhtPageVO lyhtPageVO) {
		try {
			Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
			// 查询所有土地分解审核信息
			Page<Map<String, Object>> page = landPoolDao.queryOwnerList(cityCode, typeAll, typeOne, typeTwo, typeThree,
					pageable);
			List<Map<String, Object>> list = page.getContent();
			List<Map<String, Object>> typeList = landPoolDao.queryTypeList(cityCode);
			List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
			// List<Map<String, Object>> countList = landPoolDao.countList(cityCode);
			Map map = new HashMap();

			// 循环当前页的数据
			int i = 0;
			for (Map<String, Object> maps : list) {
				Map<String, Object> newOne = new HashMap<>();
				newOne.putAll(maps);
				for (Map<String, Object> typeMap : typeList) {
					Object sum = new Object();
					if ("2".equals(typeMap.get("level").toString())) {
						sum = landPoolDao.countOwnerTypeTwo(typeMap.get("nm").toString().substring(3),
								maps.get("owner_nm").toString());
					} else {
						sum = landPoolDao.countOwnerTypeThree(typeMap.get("nm").toString().substring(3),
								maps.get("owner_nm").toString());

					}
					newOne.put(typeMap.get("nm").toString(), sum);
				}
				lists.add(i, newOne);
				i++;
			}
			map.put("datalist", lists);
			map.put("title", typeList);
			LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
					page.getTotalElements(), lyhtPageVO.getSorter());
			return new LyhtResultBody<>(map, pageVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}

	}

	/**
	 * 查询土地分解所有统计列表数据
	 * 
	 * @param cityCode
	 * @param uname
	 * @param idCard
	 * @return
	 */
	public List<Map<String, Object>> queryStatisticsList(String cityCode, String uname, String idCard) {
		// 更新数据
//        updateState();
		try {
			List<Map<String, Object>> mapList = landPoolDao.queryStatisticsList();
			/*
			 * List<Map<String, Object>> userList = null; if(StringUtils.isNotBlank(uname)
			 * || StringUtils.isNotBlank(idCard)){ userList =
			 * landPoolDao.queryLandInfo(uname, idCard); }else{ userList =
			 * landPoolDao.queryLandInfo(); }
			 */
			// 存储所有用户的行政区编码，相同的行政区只会存储一次
			List<String> cityCodes = new ArrayList<>();
			boolean cityIsNotBlank = StringUtils.isNotBlank(cityCode);
			/*
			 * if ((StringUtils.isNotBlank(uname) || StringUtils.isNotBlank(idCard)) &&
			 * userList != null && userList.size() > 0) { for (Map<String, Object> map :
			 * userList) { Object code = map.get("parentCode"); if (code != null) { String
			 * sCode = code.toString(); if(cityCodes.contains(sCode)){ continue; }
			 * cityCodes.add(sCode); } } }
			 */
			// 筛选下拉框行政区
			if (cityIsNotBlank) {
				mapList = findNode(mapList, Arrays.asList(cityCode), new HashMap<>(), true);
			}
			// 筛选用户的行政区
			if (StringUtils.isNotBlank(uname) || StringUtils.isNotBlank(idCard)) {
				mapList = findNode(mapList, cityCodes, new HashMap<>(), false);
			}
			// 转为树形结构
			mapList = toTree(mapList, "DCFD44B3E9", true, 0, null, new ArrayList<>());
			// 如果行政区为空 直接返回
			if (mapList == null) {
				return null;
			}
			/*
			 * if (userList != null && userList.size() > 0) { mapList = addUsers(mapList,
			 * userList); }
			 */
			return mapList;
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	public List<Map<String, Object>> addUsers(List<Map<String, Object>> cityList, List<Map<String, Object>> userList) {
		List<Map<String, Object>> rData = new ArrayList<>();
		for (Map<String, Object> city : cityList) {
			Map<String, Object> rMap = new HashMap<>();
			rMap.putAll(city);
			rData.add(rMap);
			Object ochildern = city.get("children");
			if (ochildern != null) {
				List<Map<String, Object>> childern = (List<Map<String, Object>>) ochildern;
				childern = addUsers(childern, userList);
				rMap.put("children", childern);
				continue;
			}
			// 当前行政区编码
			String cityCode = city.get("cityCode") != null ? city.get("cityCode").toString() : "";
			// 用来存储当前行政区下的所有用户的数组
			List<Map<String, Object>> users = new ArrayList<>();
			String serialS = city.get("serial") != null ? city.get("serial").toString() : "";
			int serial = 1;
			for (Map<String, Object> user : userList) {
				String parentCode = user.get("parentCode") != null ? user.get("parentCode").toString() : "";
				// 如果两个节点是父子级关系 则把该用户添加到该行政区下
				if (cityCode.equals(parentCode)) {
					Map<String, Object> temp = new HashMap<>();
					// 处理序号
					if (StringUtils.isNotBlank(serialS)) {
						temp.putAll(user);
						temp.put("serial", serialS + "." + serial);
						serial++;
					}
					users.add(temp);
				}
			}
			rMap.put("userList", users);
		}
		return rData;
	}

	/**
	 * 根据权属人编码查询该户的土地面积情况
	 *
	 * @param ownerNm 权属人编码
	 * @return
	 */
	public List<Map<String, Object>> queryUserLandInfo(String ownerNm) {
		try {
			if (StringUtils.isBlank(ownerNm)) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
			}
			return landPoolDao.queryUserLandInfo(ownerNm);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	@Transactional
	public void save(LandPoolEntity landPoolEntity) {
		landPoolDao.save(landPoolEntity);
	}

	/**
	 * 添加土地分解审核信息
	 *
	 * @param landAuditEntities
	 */
	@Transactional
	public void saveAll(List<LandAuditEntity> landAuditEntities, HttpServletRequest request) {
		try {
			if (landAuditEntities == null || landAuditEntities.size() == 0) {
				throw new LyhtRuntimeException("数据不能为空！");
			}
			HttpSession session = request.getSession();
			Object user = session.getAttribute(Constants.SESSION_ACCT);
			if (user == null) {
				throw new LyhtRuntimeException("请登陆！");
			}
			SysAcct acctBean = (SysAcct) user;
			Map<String, Object> tdata = new HashMap<>();
			for (LandAuditEntity landAuditEntity : landAuditEntities) {
				landAuditEntity.setFgState("0");
				if (tdata.size() == 0) {
					String sourceLevel = landAuditEntityDao.findRegionLevel(landAuditEntity.getSourceRegion());
					boolean nmNotBlank = StringUtils.isNotBlank(landAuditEntity.getNm());
					if (nmNotBlank) {
						// 如果用户内码不为空 则为分解到户
						tdata = findCityType(sourceLevel, null, true);
						/*
						 * Map<String, Object> familyInfo =
						 * landAuditEntityDao.findFamilyInfo(landAuditEntity.getNm());
						 * landAuditEntity.setTargetRegion(familyInfo.get("region").toString());
						 */
						// 更改bug,成良歌
						Map<String, Object> ownerInfo = landAuditEntityDao.findOwnerInfo(landAuditEntity.getNm());
						landAuditEntity.setTargetRegion(ownerInfo.get("region").toString());
					} else {
						// 否则是分解到行政区
						String targetLevel = landAuditEntityDao.findRegionLevel(landAuditEntity.getTargetRegion());
						tdata = findCityType(sourceLevel, targetLevel, false);
					}
				}
				// 转换下时间 否则存入数据库中会出现时区不同的问题
				TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
				landAuditEntity.setApplyDate(DateUtil.parseDate(new Date()));

				// 发起审核流程
//                ProcessOperateVO processOperateVO = new ProcessOperateVO();
//                ProcessStartVO processStartVO = new ProcessStartVO();
				if (tdata.size() != 2) {
					break;
				}
//                Map<String, String> taskData = new HashMap<>();
//                Map<String, Object> tempData = new HashMap<>();
//                taskData.put("cityType", tdata.get("cityType") + "");
//                tempData.put("resolveArea", landAuditEntity.getResolveArea().toString());
//                tempData.put("separateArea", landAuditEntity.getSeparateArea().toString());
//                //保存行政区全称
//                tempData.put("sourceRegion", landPoolDao.findRegionMergerName(landAuditEntity.getSourceRegion()));
				if (landAuditEntity.getTargetRegion() != null) {
					String regionMergerName = landPoolDao.findRegionMergerName(landAuditEntity.getTargetRegion());
					// 如果是分解到户的 则在行政区后面添加上用户名
					if (landAuditEntity.getNm() != null) {
						/*
						 * AbmFamilyEntity byNm = abmFamilyDao.findByNm(landAuditEntity.getNm());
						 * regionMergerName += regionMergerName + "/" + byNm.getNm();
						 */
						// 更改人：成良歌
						AbmOwnerEntity byName = abmOwnerDao.findByNm(landAuditEntity.getNm());
						regionMergerName = regionMergerName + "/" + byName.getName();
					}
					// tempData.put("targetRegion", regionMergerName);
				}
				// 拼接土地类型
				Map<String, Object> mlandType = emLandPoolDao.findLandType(landAuditEntity.getTypeLevel(),
						landAuditEntity.getLandType());
				StringBuffer landType = new StringBuffer();
				landType.append(
						mlandType.get("allTypeName") != null ? "/" + mlandType.get("allTypeName").toString() : "");
				landType.append(
						mlandType.get("typeOneName") != null ? "/" + mlandType.get("typeOneName").toString() : "");
				landType.append(
						mlandType.get("typeTwoName") != null ? "/" + mlandType.get("typeTwoName").toString() : "");
				landType.append(
						mlandType.get("typeThreeName") != null ? "/" + mlandType.get("typeThreeName").toString() : "");
				// tempData.put("landType", landType.toString());

//                String sourceRegions = tempData.get("sourceRegion").toString();
//                String targetRegions = tempData.get("targetRegion").toString();
//                String sourceRegion = sourceRegions.substring(sourceRegions.lastIndexOf(",")+1);
//                String targetRegion = targetRegions.substring(targetRegions.lastIndexOf(",")+1);
//                taskData.put("name", sourceRegion+"土地分解到"+targetRegion);
//                String string = JSON.toJSONString(tempData);
//                taskData.put("data",string);
				// 设置审核的数据 数据量太大可能会出现字符串截断的错误
				/*
				 * processStartVO.setData(taskData);
				 * processStartVO.setFlowPath(tdata.get("flowPath") + ""); String processId =
				 * processService.processStart(processStartVO, request);
				 */

//                processOperateVO.setData(taskData);
//                processOperateVO.setFlowPath(landResolvePath);
//                String processId = processOperateService.processStart(processOperateVO, request);//更改人：成良歌
				// 保存得到的审核流程id
				// landAuditEntity.setProcessId(processId);
				landAuditEntity.setAuditCode("D2B5A40E22");
				landAuditEntity = landAuditEntityDao.save(landAuditEntity);

				// 新增一条数据到t_abm_land_pool_process
				LandPoolProcess landPoolProcess = new LandPoolProcess();
				landPoolProcess.setArea(landAuditEntity.getResolveArea());
				landPoolProcess.setAuditId(landAuditEntity.getId());
				LandPoolEntity landPoolEntity = new LandPoolEntity();
				if (landAuditEntity.getTypeLevel() == 2) {
					landPoolEntity = landPoolDao.queryByFourCondition(landAuditEntity.getSourceRegion(),
							landAuditEntity.getScope(), landAuditEntity.getLandType(), null);
				} else {
					landPoolEntity = landPoolDao.queryByFourCondition(landAuditEntity.getSourceRegion(),
							landAuditEntity.getScope(), null, landAuditEntity.getLandType());
				}

				if (landPoolEntity != null) {
					landPoolProcess.setPoolId(landPoolEntity.getId());
				}
				landPoolProcessService.save(landPoolProcess);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 分解 提交流程
	 * 
	 * @return
	 */
	@Transactional
	public LyhtResultBody<LandAuditEntity> startProcess(Integer id, HttpServletRequest request) {
		LandAuditEntity landAuditEntity = landAuditEntityDao.getOne(id);
		String targetRegionName = landAuditEntityDao.getTargetRegionNameForProcess(id);
		// 发起审核流程
		ProcessOperateVO processOperateVO = new ProcessOperateVO();
		ProcessStartVO processStartVO = new ProcessStartVO();
		Map<String, String> taskData = new HashMap<>();
		Map<String, Object> tempData = new HashMap<>();
		taskData.put("cityType", landAuditEntity.getCityType());
		tempData.put("resolveArea", landAuditEntity.getResolveArea().toString());
		tempData.put("separateArea", landAuditEntity.getSeparateArea().toString());
		// 保存行政区全称
		tempData.put("sourceRegion", landPoolDao.findRegionMergerName(landAuditEntity.getSourceRegion()));
		tempData.put("targetRegion", landAuditEntity.getTargetRegion());
		processOperateVO.setData(taskData);
		processOperateVO.setFlowPath(landResolvePath);
		tempData.put("landType", landAuditEntity.getLandType());

		String sourceRegions = tempData.get("sourceRegion").toString();
		String targetRegions = tempData.get("targetRegion").toString();
		String sourceRegion = sourceRegions.substring(sourceRegions.lastIndexOf(",") + 1);
		String targetRegion = targetRegions.substring(targetRegions.lastIndexOf(",") + 1);
		taskData.put("name", sourceRegion + "土地分解到" + targetRegionName);
		String string = JSON.toJSONString(tempData);
		taskData.put("data", string);
		String processId = processOperateService.processStart(processOperateVO, request);
		landAuditEntity.setProcessId(processId);
		HttpSession session = request.getSession();
		Object user = session.getAttribute(Constants.SESSION_ACCT);
		if (user == null) {
			throw new LyhtRuntimeException("请登陆！");
		}
		SysAcct acctBean = (SysAcct) user;
		Map<String, Object> tdata = new HashMap<>();
		// 设置申请人为当前用户
		landAuditEntity.setApplyUser(acctBean.getName());
		// 设置为审核状态为审核中 字典表中的关联分类 dict_sh_state
		landAuditEntity.setAuditCode("D30E1EE520");
		landAuditEntityDao.save(landAuditEntity);
		return new LyhtResultBody<>(landAuditEntity);
	}

	/**
	 * 批量发起流程
	 * 
	 * @param ids
	 * @param request
	 * @return
	 */
	@Transactional
	public LyhtResultBody<String> startProcessBatch(String ids, HttpServletRequest request) {
		String[] idArray = ids.split(",");
		for (int i = 0; i < idArray.length; i++) {
			LandAuditEntity landAuditEntity = landAuditEntityDao.getOne(Integer.parseInt(idArray[i]));
			// 发起审核流程
			ProcessOperateVO processOperateVO = new ProcessOperateVO();
			ProcessStartVO processStartVO = new ProcessStartVO();
			Map<String, String> taskData = new HashMap<>();
			Map<String, Object> tempData = new HashMap<>();
			taskData.put("cityType", landAuditEntity.getCityType());
			tempData.put("resolveArea", landAuditEntity.getResolveArea().toString());
			tempData.put("separateArea", landAuditEntity.getSeparateArea().toString());
			// 保存行政区全称
			tempData.put("sourceRegion", landPoolDao.findRegionMergerName(landAuditEntity.getSourceRegion()));
			tempData.put("targetRegion", landAuditEntity.getTargetRegion());
			processOperateVO.setData(taskData);
			processOperateVO.setFlowPath(landResolvePath);
			tempData.put("landType", landAuditEntity.getLandType());

			String sourceRegions = tempData.get("sourceRegion").toString();
			String targetRegions = tempData.get("targetRegion").toString();
			String sourceRegion = sourceRegions.substring(sourceRegions.lastIndexOf(",") + 1);
			String targetRegion = targetRegions.substring(targetRegions.lastIndexOf(",") + 1);
			PubRegionEntity regionEntity = regionDao.findByCityCode(targetRegion);
			taskData.put("name", sourceRegion + "土地分解到" + regionEntity.getName());
			String string = JSON.toJSONString(tempData);
			taskData.put("data", string);
			System.out.println(landAuditEntity);
			String processId = processOperateService.processStart(processOperateVO, request);
			landAuditEntity.setProcessId(processId);
			HttpSession session = request.getSession();
			Object user = session.getAttribute(Constants.SESSION_ACCT);
			if (user == null) {
				throw new LyhtRuntimeException("请登陆！");
			}
			SysAcct acctBean = (SysAcct) user;
			Map<String, Object> tdata = new HashMap<>();
			// 设置申请人为当前用户
			landAuditEntity.setApplyUser(acctBean.getName());
			// 设置为审核状态为审核中 字典表中的关联分类 dict_sh_state
			landAuditEntity.setAuditCode("D30E1EE520");
			landAuditEntityDao.save(landAuditEntity);
		}

		return new LyhtResultBody<>(ids);
	}

	/**
	 * 分解 流程审批完成回调 isSuccess 0 成功 1 拒绝
	 * 
	 * @return
	 */
	@Transactional
	public LyhtResultBody<LandAuditEntity> callBack(String processId, Integer isSuccess) {
		LandAuditEntity landAuditEntity = landAuditEntityDao.findByProcessId(processId);
		if (isSuccess == 0) {
			landAuditEntity.setAuditCode("64FABB5F0D");
		} else if (isSuccess == 1) {
			landAuditEntity.setAuditCode("36845517AF");
		} else {
			landAuditEntity.setAuditCode("D2B5A40E22");
		}
		landAuditEntity = landAuditEntityDao.save(landAuditEntity);
		LandPoolEntity landPoolEntity = new LandPoolEntity();
		if (landAuditEntity.getTypeLevel() == 2) {
			landPoolEntity = landPoolDao.queryByFourCondition(landAuditEntity.getSourceRegion(),
					landAuditEntity.getScope(), landAuditEntity.getLandType(), null);
		} else {
			landPoolEntity = landPoolDao.queryByFourCondition(landAuditEntity.getSourceRegion(),
					landAuditEntity.getScope(), null, landAuditEntity.getLandType());
		}
		if (isSuccess == 0) {// 成功
			LandPoolProcess landPoolProcess = landPoolProcessDao.queryByAuditNmAndPoolId(landAuditEntity.getId(),
					landPoolEntity.getId());

			landPoolDao.updateSeparateArea(landPoolProcess.getArea() + landPoolEntity.getSeparateArea().doubleValue(),
					landPoolEntity.getId());// 修改源行政区的已分解面积
			if (StringUtils.isBlank(landAuditEntity.getNm())) {// 分解至地区
				LandPoolEntity landPool = new LandPoolEntity();
				landPool = landPoolDao.queryByFourCondition(landAuditEntity.getTargetRegion(),
						landAuditEntity.getScope(), landAuditEntity.getLandType(), null);
				landPoolDao.updateArea(landPoolProcess.getArea() + landPoolEntity.getSeparateArea().doubleValue(),
						landPoolEntity.getId());// 修改目标行政区的总分解面积
			} else {// 分解至权属人
				AbmLandEntity abmLandEntity = new AbmLandEntity();
				if (landAuditEntity.getTypeLevel() == 2) {
					abmLandEntity = tudiDao.getByOwnerNmAndTypeTwo(landAuditEntity.getNm(),
							landAuditEntity.getLandType());
				} else {
					abmLandEntity = tudiDao.getByOwnerNmAndTypeThree(landAuditEntity.getNm(),
							landAuditEntity.getLandType());
				}
				if (abmLandEntity != null) {// 存在累加
					abmLandEntity
							.setArea(abmLandEntity.getArea().add(new BigDecimal(landAuditEntity.getResolveArea())));
					tudiDao.save(abmLandEntity);
				} else {// 不存在 则新增
					PubDictValue dictValue = null;
					AbmLandEntity abmLand = new AbmLandEntity();
					String nm = Randomizer.generCode(10);
					abmLand.setNm(nm);
					abmLand.setAllType(landPoolEntity.getAllType());
					abmLand.setTypeOne(landPoolEntity.getTypeOne());
					abmLand.setTypeTwo(landPoolEntity.getTypeTwo());
					abmLand.setTypeThree(landPoolEntity.getTypeThree());
					abmLand.setOwnerNm(landAuditEntity.getNm());
					abmLand.setRegion(landAuditEntity.getTargetRegion());
					abmLand.setScope(landPoolEntity.getScope());
					abmLand.setUnit("亩");
					abmLand.setArea(new BigDecimal(landPoolProcess.getArea()));
					if (StringUtils.isBlank(landPoolEntity.getTypeThree())) {// 无三级分类
						dictValue = dictDao.findByNm(landPoolEntity.getTypeTwo());
						String name = dictValue.getName();
						if (name.equals("其他园地")) {
							abmLand.setProjectNm("108");
							abmLand.setLandProjectNm("319");
						}
						if (landPoolEntity.getScope().equals("CE81C0FA94")) {// 永久占地 枢纽永久
							if (name.equals("水田")) {
								abmLand.setProjectNm("97");
								abmLand.setLandProjectNm("282");
							}
						}
						
						
						
					} else {// 三级分类
						dictValue = dictDao.findByNm(landPoolEntity.getTypeThree());
						String name = dictValue.getName();
						if (name.equals("板栗成园地")) {
							abmLand.setProjectNm("102");
							abmLand.setLandProjectNm("313");
						} else if (name.equals("板栗幼园地")) {
							abmLand.setProjectNm("103");
							abmLand.setLandProjectNm("314");
						} else if (name.equals("核桃成园地")) {
							abmLand.setProjectNm("104");
							abmLand.setLandProjectNm("315");
						} else if (name.equals("核桃幼园地")) {
							abmLand.setLandProjectNm("316");
							abmLand.setProjectNm("105");
						} else if (name.equals("葡萄成园地")) {
							abmLand.setLandProjectNm("317");
							abmLand.setProjectNm("106");
						} else if (name.equals("葡萄幼园地")) {
							abmLand.setProjectNm("107");
							abmLand.setLandProjectNm("318");
						} else if (name.equals("经济林")) {
							abmLand.setProjectNm("110");
						} else if (name.equals("用材林")) {
							abmLand.setProjectNm("111");
						} else if (name.equals("灌木林")) {
							abmLand.setProjectNm("112");
						} else if (name.equals("疏林地")) {
							abmLand.setProjectNm("113");
						}
						if (landPoolEntity.getScope().equals("CE81C0FA94")) {// 永久占地 枢纽永久
							if (name.equals("旱地")) {
								abmLand.setProjectNm("98");
								abmLand.setLandProjectNm("283");
							} else if (name.equals("轮闲地")) {
								abmLand.setProjectNm("285");
							} else if (name.equals("陡坡地")) {
								abmLand.setProjectNm("284");
							}
						}
					}

					tudiDao.save(abmLand);
				}
			}
		}
		landPoolProcessDao.deleteByAuditNmAndPoolId(landAuditEntity.getId(), landPoolEntity.getId());// 删除关联表中数据
		return new LyhtResultBody<>(landAuditEntity);
	}

	public LyhtResultBody<List> queryLandInfoTable(String cityCode, String status, LyhtPageVO lyhtPageVO) {
		try {
			Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
			// 查询所有土地分解审核信息
			Page<Map> page = landPoolDao.queryLandInfoTable(cityCode, status, pageable);
			;
			LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
					page.getTotalElements(), lyhtPageVO.getSorter());
			return new LyhtResultBody<>(page.getContent(), pageVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 转为树形结构
	 *
	 * @param mapList
	 * @param pCode   父节点编码
	 * @param isOne   是否第一次进入
	 * @param level   级别
	 * @param parent  父节点的序列号 1.1.2
	 * @param parents 配合前端做查询时展开所用到的，为当前节点所有父节点编码
	 * @return
	 */
	public List<Map<String, Object>> toTree(List<Map<String, Object>> mapList, String pCode, Boolean isOne, int level,
			String parent, List<String> parents) {
		List<Map<String, Object>> rData = new ArrayList<>();
		// 定义序号
		int serial = 1;
		for (Map<String, Object> map : mapList) {
			Map<String, Object> data = new HashMap<>();
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			if (parentCode.equals(pCode) || (cityCode.equals(pCode)) && isOne) {
				List<String> tempParents = new ArrayList<>();
				tempParents.addAll(parents);
				tempParents.add(cityCode);
				// 前端处理缩进时使用
				data.put("level", level);
				String serialS = isOne ? serial + "" : parent + "." + serial;
				if (!isOne) {
					data.put("serial", parent + "." + serial);
				} else {
					data.put("serial", serial);
				}
				serial++;
				// 寻找该节点的子节点
				List<Map<String, Object>> children = toTree(mapList, cityCode, false, level + 1, serialS, tempParents);
				data.putAll(map);
				data.put("parents", parents);
				// 判断该节点是否含有子节点 如果没有则设置标识为最后一个节点
				if (children != null && children.size() > 0) {
					data.put("children", children);
				} else {
					data.put("lastNode", true);
				}
				rData.add(data);
			}
		}
		return rData;
	}

	/**
	 * 根据传入的节点素组 返回节点数组的所有父级和子级元素 待优化
	 * 
	 * @param mapList 所有的行政区数据
	 * @param codes   需要查找父级行政区的行政区的编码
	 * @param czPNode 已经存在的父级行政区
	 * @param flag    是否需要查找子行政区
	 * @return
	 */
	public List<Map<String, Object>> findNode(List<Map<String, Object>> mapList, List<String> codes,
			Map<String, Object> czPNode, Boolean flag) {
		List<Map<String, Object>> rData = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			// 如果该行政区的编码存在于传入的行政区数组的编码中 并且czPNode中不存在 则进行父子级行政区的查找
			if (codes.contains(cityCode) && !czPNode.containsKey(cityCode)) {
				Map<String, Object> temp = new HashMap<>();
				czPNode.put(cityCode, cityCode);
				temp.put("open", true);
				temp.putAll(map);
				rData.add(temp);
				// 假如父节点不为空 则去查找所有的父级节点
				if (StringUtils.isNotBlank(parentCode)) {
					// 临时存储单个父节点编码
					List<String> pcode = Arrays.asList(parentCode);
					// 返回父节点
					List<Map<String, Object>> node = findNode(mapList, pcode, czPNode, false);
					rData.addAll(node);
				}
			}
		}
		// 是否需要查询子行政区 子行政区只要查询一次就行了
		if (flag) {
			for (String code : codes) {
				List<Map<String, Object>> child = findChild(mapList, code);
				rData.addAll(child);
			}
		}
		return rData;
	}

	/**
	 * 查询子节点元素
	 *
	 * @param mapList
	 * @param codes   节点数组
	 * @return
	 */
	public List<Map<String, Object>> findChild(List<Map<String, Object>> mapList, String codes) {
		List<Map<String, Object>> rData = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			if (parentCode.equals(codes)) {
				rData.add(map);
				// 返回子节点
				List<Map<String, Object>> node = findChild(mapList, cityCode);
				rData.addAll(node);
			}
		}
		return rData;
	}

	// 省
	private static final String PROVINCE = "province";
	// 市
	private static final String CITY = "city";
	// 县
	private static final String DISTRICT = "district";
	// 乡
	private static final String STREET = "street";
	// 村
	private static final String VILLAGE = "village";
	// 组
	private static final String GROUP = "group";

	/**
	 * 根据传入的源行政区和目标行政区，获得流程审核的类型
	 * 
	 * @param source
	 * @param target
	 * @param flag   是否是分解到户
	 * @return
	 */
	public Map<String, Object> findCityType(String source, String target, Boolean flag) {
		Map<String, Object> rData = new HashMap<>();
		int type = 0;
		String flowPath = "Zny.Workflow.智慧征地移民云平台.移民.";
		if (flag) { // 如果是分解到户
			if (PROVINCE.equals(source)) { // 省分解至户
				flowPath += "土地省市分解至户";
			} else if (CITY.equals(source)) { // 市分解至户
				type = 1;
				flowPath += "土地省市分解至户";
			} else if (DISTRICT.equals(source)) { // 县分解至户
				flowPath += "土地县乡分解至户";
			} else if (STREET.equals(source)) { // 乡分解至户
				type = 1;
				flowPath += "土地县乡分解至户";
			} else if (VILLAGE.equals(source)) { // 村分解至户
				flowPath += "土地村组分解至户";
			} else if (GROUP.equals(source)) { // 组分解至户
				type = 1;
				flowPath += "土地村组分解至户";
			}
		} else if (GROUP.equals(target)) { // 如果事分解到组
			if (PROVINCE.equals(source)) { // 省分解至组
				flowPath += "土地省市分解至组";
			} else if (CITY.equals(source)) { // 市分解至组
				type = 1;
				flowPath += "土地省市分解至组";
			} else if (DISTRICT.equals(source)) { // 县分解至组
				flowPath += "土地县乡分解至组";
			} else if (STREET.equals(source)) { // 乡分解至组
				type = 1;
				flowPath += "土地县乡分解至组";
			} else if (VILLAGE.equals(source)) { // 村分解至组
				flowPath += "土地村分解至组";
			}
		} else if (VILLAGE.equals(target)) { // 如果是分解到村
			if (PROVINCE.equals(source)) { // 省分解至村
				flowPath += "土地省市分解至村";
			} else if (CITY.equals(source)) { // 市分解至村
				flowPath += "土地省市分解至村";
				type = 1;
			} else if (DISTRICT.equals(source)) { // 县分解至村
				flowPath += "土地县乡分解至村";
			} else if (STREET.equals(source)) { // 乡分解至村
				flowPath += "土地县乡分解至村";
				type = 1;
			}
		} else if (STREET.equals(target)) { // 如果是分解到乡
			if (PROVINCE.equals(source)) { // 省分解至乡
				flowPath += "土地省市分解至乡";
			} else if (CITY.equals(source)) { // 市分解至乡
				type = 1;
				flowPath += "土地省市分解至乡";
			} else if (DISTRICT.equals(source)) { // 县分解至乡
				flowPath += "土地县分解至乡";
			}
		} else if (CITY.equals(target)) { // 如果分解到县
			if (PROVINCE.equals(source)) { // 省分解至县
				flowPath += "土地省市分解至县";
			} else if (CITY.equals(source)) { // 市分解至县
				type = 1;
				flowPath += "土地省市分解至县";
			}
		} else if (PROVINCE.equals(source) && CITY.equals(target)) { // 省分解至市
			flowPath += "土地省分解至市";
		} else {
			flowPath += "土地省市分解至户";
		}
		// 设置流程中的判断条件数据
		rData.put("cityType", type);
		// 设置调用哪个流程，中南院的流程系统 是根据名字来调用的
		rData.put("flowPath", flowPath);
		return rData;
	}

	/**
	 * 查询可分解面积 number 输入 面积
	 */
	public LyhtResultBody<Double> queryArea(Integer id, Double number, String region, String scope, String allType,
			String typeOne, String typeTwo, String typeThree) {
		LyhtResultBody<Double> result = new LyhtResultBody<>();
		if (CommonUtil.isEmpty(number)) {
			number = 0.00;
		}
		LandPoolEntity landPoolEntity = landPoolDao.queryBySixCondition(region, scope, allType, typeOne, typeTwo,
				typeThree);
		Double area = 0.00;// 可分解面积
		if (landPoolEntity != null) {
			area = landPoolProcessDao.getResidualArea(landPoolEntity.getId());
		}
		Double spilt = 0.00;
		if (id != null && id > 0) {
			AbmLandEntity land = landDao.getOne(id);
			// 修改后面积在原基础上增加或减少的面积
			BigDecimal oldArea = land.getArea();
			Double userArea = new BigDecimal(Double.toString(number)).subtract(oldArea).doubleValue();
			spilt = area;
			if ((userArea) > area) {// 如果更改面积减去原始面积大于可分解面积
				result.setMsg("超出可分解范围");
			} else {
				spilt = new BigDecimal(Double.toString(area)).subtract(new BigDecimal(Double.toString(userArea)))
						.doubleValue();
			}
		} else {// 新增时 直接 与可分解面积 比较
			if (number > area) {
				result.setMsg("超出可分解范围");
			} else {
				spilt = new BigDecimal(Double.toString(area)).subtract(new BigDecimal(Double.toString(number)))
						.doubleValue();
			}
		}
		result.setList(spilt);
		return result;
	}

	public LyhtResultBody<Double> queryArea(String region, String scope, String allType, String typeOne, String typeTwo,
			String typeThree) {
		return new LyhtResultBody<>(landPoolDao.queryArea(region, scope, allType, typeOne, typeTwo, typeThree));
	}

}
