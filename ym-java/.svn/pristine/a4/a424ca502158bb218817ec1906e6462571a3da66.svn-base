package com.lyht.business.abm.production.service;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.production.dao.ProductionAuditDao;
import com.lyht.business.abm.production.dao.ProductionDao;
import com.lyht.business.abm.production.thread.ThreadPool;
import com.lyht.business.abm.production.vo.ProduceCardStatisticsVO;
import com.lyht.business.abm.production.vo.ProduceProcessInfoVO;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.review.vo.PersonalWealthVO;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.service.ProcessService;
import com.lyht.util.AmountToChineseUtil;
import com.lyht.util.CommonUtil;
import com.lyht.util.FileDownUtil;
import com.lyht.util.WordReplaceKeyUtil;
import com.lyht.util.WordTOPDF;

import cn.hutool.poi.excel.BigExcelWriter;

/**
 * @version: V1.0
 * @author: hjs
 * @className: ProductionService
 * @packageName: com.lyht.business.abm.production.service
 * @description: （类作用）
 * @data: 2020年02月25日 10:35
 * @see []
 **/
@Service
public class ProductionService {
	private static Logger log = LoggerFactory.getLogger(ProductionService.class);

	@Autowired
	private ProductionDao productionDao;

	@Autowired
	private ProcessService processService;

	@Autowired
	private ProductionAuditService productionAuditService;

	@Autowired
	private ProductionAuditDao auditDao;

	@Autowired
	private ProcessOperateService processOperateService; // 注入流程服务实现类

	@Value("${iwind.process.flow.path.production}") // 注入配置的流程路径（eclipse debug请转unicode，部署后无影响）
	private String physicalFlowPath;

	@Autowired
	private AbmOwnerDao ownerDao;

	@Autowired
	private AbmFamilyDao familyDao;

	/**
	 * 跳过生产安置 直接将生产安置状态 is_produce 变为2 完成
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public LyhtResultBody<Object> directlyToComplete(String nms) {
		if (StringUtils.isBlank(nms)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		try {
			String[] nmArray = nms.split(",");
			for (String nm : nmArray) {
				productionDao.updateOwner("2", nm);
			}
		} catch (Exception e) {
			log.error("失败", e);
		}
		return new LyhtResultBody<>();
	}

	/**
	 * 根据行政区编码查询生产安置户主列表数据
	 * 
	 * @param uname      户主名称
	 * @param idCard     身份证
	 * @param cityName   城市名称
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<Map<String, Object>>> findInfo(String uname, String idCard, String cityName,String nm,
			LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);

		// 查询所有土地分解审核信息
		Page<Map<String, Object>> page = null;
		page = productionDao.findUsersList(cityName, uname, idCard,nm, pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		Integer people = 0;
		List<Map<String, Object>> result = (List<Map<String, Object>>) JSON.parse(jsonString);
		for (Map<String, Object> mp : result) {
			BigDecimal finalArea = new BigDecimal(0);
			String ownerNm = mp.get("ownerNm") + "";
			List<Map<String, Object>> list = productionDao.getLandInfoByOwnerNm(ownerNm);
			// 测算生产安置人口
			List<Map<String, Object>> coefficients = productionDao.getConvertCoefficient();
			// 获取小组人均根底当量
			String region = mp.get("region") + "";
			Double equivalen = productionDao.getCultivatedLandEquivalentGroup(region);
			if (!CommonUtil.isEmpty(equivalen)) {
				BigDecimal landEquivalen = new BigDecimal(equivalen).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal man = new BigDecimal(0);// 人口数量
				/**
				 * 测算生产安置人口
				 */
				for (Map<String, Object> m : list) {// 遍历土地
					if ((m.get("scope") + "").equals("CE81C0FA94") || (m.get("scope") + "") == "CE81C0FA94") {// 永久占地POW((1+0.013),2.33)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""))
										.setScale(2, BigDecimal.ROUND_HALF_UP);
								;// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 2.33));
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
								finalArea = finalArea.add(convert);
							}
						}
					} else if ((m.get("scope") + "").equals("D8D5AAD9DC") || (m.get("scope") + "") == "D8D5AAD9DC") {// 水库淹没POW((1+0.013),8)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""))
										.setScale(2, BigDecimal.ROUND_HALF_UP);
								;// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
										BigDecimal.ROUND_HALF_UP);
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
								finalArea = finalArea.add(convert);
							}
						}
					} else if ((m.get("scope") + "") == "348F5B68BA" || (m.get("scope") + "").equals("348F5B68BA")) {// 集镇新址占地区POW((1+0.013),8)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""))
										.setScale(2, BigDecimal.ROUND_HALF_UP);
								;// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
										BigDecimal.ROUND_HALF_UP);
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
								finalArea = finalArea.add(convert);
							}
						}
					} else if ((m.get("scope") + "") == "E78D14E7BE" || (m.get("scope") + "").equals("E78D14E7BE")) {// 水库影响POW((1+0.013),8)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(Double.parseDouble(map.get("coefficient") + ""))
										.setScale(2, BigDecimal.ROUND_HALF_UP);
								;// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
										BigDecimal.ROUND_HALF_UP);
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
								finalArea = finalArea.add(convert);
							}
						}
					}
				}
				Double manCount = man.doubleValue();
				people = manCount.intValue();// 最后取整

				String nNum = mp.get("nNum") + "";
				Integer agricultural = Integer.parseInt(nNum);
				if (people > agricultural) {// 不能大于家庭农业人口
					people = agricultural;
				}
				String placeType = mp.get("isLY") + "";
				Boolean isLY = Boolean.valueOf(placeType);
				if (isLY) {// 如果是搬迁到兰永的 则 测算人口为 家庭农业人口
					people = agricultural;
				}
			}
			BigDecimal area = new BigDecimal(0.00);// 折算田
			BigDecimal count = new BigDecimal(0.00);// 逐年补偿人口
			BigDecimal paddyField = new BigDecimal(0.00);// 水田
			BigDecimal dryLand = new BigDecimal(0.00);// 旱地
			BigDecimal fallow = new BigDecimal(0.00);// 休闲地 轮闲地
			BigDecimal matureForest = new BigDecimal(0.00);// 核桃、板栗等成园林
			BigDecimal grape = new BigDecimal(0.00);// 葡萄
			BigDecimal youngGrowth = new BigDecimal(0.00);// 核桃、葡萄、板栗幼园林
			BigDecimal otherGarden = new BigDecimal(0.00);// 其他园地

			/**
			 * 
			 */
			for (Map<String, Object> m : list) {
				if ((m.get("name") + "").indexOf("水田") != -1) {// 水田
					paddyField = paddyField.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("旱地") != -1) {// 旱地
					dryLand = dryLand.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("轮闲地") != -1) {// 轮闲地
					fallow = fallow.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("核桃成园地") != -1) {
					matureForest = matureForest.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("板栗成园地") != -1) {
					matureForest = matureForest.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("葡萄成园地") != -1) {
					grape = matureForest.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("核桃幼园地") != -1) {
					youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("板栗幼园地") != -1) {
					youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("葡萄幼园地") != -1) {
					youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
				} else if ((m.get("name") + "").indexOf("其他园地") != -1) {
					otherGarden = otherGarden.add((BigDecimal) m.get("area"));
				}
			}
			area = area.add(paddyField)
					.add(dryLand.multiply(new BigDecimal(0.6)).add(matureForest)
							.add(grape.multiply(new BigDecimal(1.8))))
					.add(youngGrowth.multiply(new BigDecimal(0.8))).add(otherGarden.multiply(new BigDecimal(0.6)));
			// 计算逐年补偿人口
			if (area.doubleValue() != 0) {
				count = area.divide(new BigDecimal(1.042), 2, BigDecimal.ROUND_HALF_UP);
			}
			mp.put("znbcrk", String.format("%.2f", count));
			mp.put("rk", people);
			mp.put("area", finalArea);
		}
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(result, pageVO);
	}

	/**
	 * 实施
	 * 
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List> popuCount(String region, LyhtPageVO lyhtPageVO) {
		if (StringUtils.isBlank(region))
			region = "";
		try {
			Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
			// 查询所有土地分解审核信息
			Page<Map<String, Object>> page = productionDao.popuCount(region, pageable);
			LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
					page.getTotalElements(), lyhtPageVO.getSorter());

			return new LyhtResultBody<>(page.getContent(), pageVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 改变流程中已经通过或者拒绝的数据
	 */
	public void sh() {
		try {
			ThreadPool.pool.execute(new Runnable() {
				@Override
				public void run() {
					productionAuditService.examine();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据行政区编码查询生产安置列表数据
	 * 
	 * @param uname     户主名称的模糊查询
	 * @param qcityCode 查询条件中的行政区编码
	 * @return
	 */
	public LyhtResultBody<List> findUserList(String uname, String idCard, String qcityCode, String cityName) {
		// sh();
		try {
			List<Map<String, Object>> benchmark = productionDao.findBenchmark();
			List<Map<String, Object>> userData = null, rData = new ArrayList<>(),
					allRegion = productionDao.findAllRegion(); // 查询所有行政区
			List<String> regions = null;
			Boolean qcityCodeNotNull = qcityCode != null && qcityCode.trim().length() > 0,
					nameNotNull = uname != null && uname.trim().length() > 0;
			if (qcityCodeNotNull) {
				regions = findChild(allRegion, qcityCode);
				regions.add(qcityCode);
			}
			// 查询所有土地分解审核信息
			userData = productionDao.findUserList(cityName, uname, idCard);
			// List<Map<String, Object>> list = productionDao.popuCountKY();
			// 查询出所要展示的行政区信息
			List<Map<String, Object>> regionChild = null;
			if (qcityCodeNotNull) {
				// 查询该行政区的所有上级行政区
				regionChild = findParent(allRegion, Arrays.asList(qcityCode), new HashMap<>(), false);
			} else {
				// 如果搜索条件不为空 则展示所有的行政区信息
				regionChild = allRegion;
			}
			// 如果模糊查询中的姓名不为空 则去赛选户主有关的行政区
			if (nameNotNull) {
				List<String> czCityCode = new ArrayList<>();
				for (Map<String, Object> userDatum : userData) {
					if (!czCityCode.contains(userDatum.get("region"))) {
						czCityCode.add(userDatum.get("region") + "");
					}
				}
				List<Map<String, Object>> parentRegion = findParent(allRegion, czCityCode, new HashMap<>(), true);
				List<Map<String, Object>> regionEnd = new ArrayList<>();
				czCityCode = new ArrayList<>();
				for (Map<String, Object> objectMap : parentRegion) {
					String tempcityCode = objectMap.get("cityCode").toString();
					if (regions == null) {
						regionEnd.add(objectMap);
						continue;
					}
					List<String> parentCodes = findParentCodes(allRegion, tempcityCode);
					if (parentCodes.contains(tempcityCode) && !czCityCode.contains(tempcityCode)) {
						czCityCode.add(tempcityCode);
						regionEnd.add(objectMap);
					}
				}
				regionChild = regionEnd;
			}
			// 为行政区添加上对应的数据
			for (Map<String, Object> map : regionChild) {
				String cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
				// 得到该行政区下的所有子行政区编码
				List<String> childs = findChild(allRegion, cityCode);
				// 把当前行政区也添加进去
				childs.add(cityCode);
				// 可以替换为根据行政区直接统计数据
				int tempRK = 0, // 规划水平年生产安置人口合计
						tempFJ = 0, // 已分解户数
						tempYHS = 0; // 已核算人口数
				BigDecimal gddl = new BigDecimal(0.00);
				Boolean hsState = false; // 是否在核算中
				for (Map<String, Object> userDatum : userData) {

					gddl = gddl.add(new BigDecimal(Double.parseDouble(userDatum.get("gddl") + "")));
					// 获得用户的行政区编码
					String ucityCode = userDatum.get("region") != null ? userDatum.get("region").toString() : "";
					// 如果该行政区的子行政区编码包含用户的行政区编码 则把该用户的数据添加至这个行政区
					if (childs.contains(ucityCode)) {
						if (!"2".equals(userDatum.get("isProduce"))) {
							hsState = true;
						} else {
							tempFJ++;
							tempYHS += ((BigInteger) userDatum.get("produce")).intValue();
						}
					}
				}
				for (Map<String, Object> map2 : benchmark) {
					String region = map2.get("region") != null ? map2.get("region").toString() : "";
					if (childs.contains(region)) {
						tempRK += Integer.parseInt(map2.get("count") + "");
					}
				}
				Map<String, Object> temp = new HashMap<>();
				temp.put("jhrkhj", tempRK);
				temp.putAll(map);
				// temp.put("jhrkhj", tempRK);
				temp.put("gddl", gddl);
				temp.put("yfjhs", tempFJ);
				temp.put("yhshs", tempYHS);
				temp.put("hsState", hsState);
				List<String> parents = findParentCodes(allRegion, cityCode);
				if (parents != null && parents.size() > 0) {
					parents.remove(0);
				}
				temp.put("parents", parents);

				if (tempRK + tempFJ + tempYHS > 0) {
					rData.add(temp);
				}
			}

//			JSONArray array = listToTree(JSONArray.parseArray(JSON.toJSONString(rData)), "cityCode", "parentCode", "children");
//			rData = (List<Map<String,Object>>) JSONArray.parse(array.toJSONString());
			rData = toTree(rData, "DCFD44B3E9", true, 0, null, new ArrayList<>());
			return new LyhtResultBody<>(rData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	public JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
		JSONArray r = new JSONArray();
		JSONObject hash = new JSONObject();
		// 将数组转为Object的形式，key为数组中的id
		for (int i = 0; i < arr.size(); i++) {
			JSONObject json = (JSONObject) arr.get(i);
			hash.put(json.getString(id), json);
		}
		// 遍历结果集
		for (int j = 0; j < arr.size(); j++) {
			// 单条记录
			JSONObject aVal = (JSONObject) arr.get(j);
			// 在hash中取出key为单条记录中pid的值
			JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
			// 如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
			if (hashVP != null) {
				// 检查是否有child属性
				if (hashVP.get(child) != null) {
					JSONArray ch = (JSONArray) hashVP.get(child);
					ch.add(aVal);
					hashVP.put(child, ch);
				} else {
					JSONArray ch = new JSONArray();
					ch.add(aVal);
					hashVP.put(child, ch);
				}
			} else {
				r.add(aVal);
			}
		}
		return r;
	}

	/**
	 * 可研
	 * 
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<Map<String, Object>>> popuCountKY() {
		List<Map<String, Object>> regionList = productionDao.findAllRegion(); // 查询所有行政区
		List<Map<String, Object>> list = productionDao.popuCountKY();
		List<Map<String, Object>> rData = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : regionList) {
			String cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			// 得到该行政区下的所有子行政区编码
			List<String> childs = findChild(regionList, cityCode);
			// 把当前行政区也添加进去
			childs.add(cityCode);
			Double hjRK = 0.00, snRK = 0.00, ymRK = 0.00, yxRK = 0.00, xjzRK = 0.00, sumghRK = 0.00, ghsnRK = 0.00,
					ghymRK = 0.00, ghyxRK = 0.00, ghxjzRK = 0.00;
			for (Map<String, Object> map2 : list) {
				String region = map2.get("region") != null ? map2.get("region").toString() : "";
				if (childs.contains(region)) {
					hjRK += Double.parseDouble(map2.get("hjRK") + "");
					snRK += Double.parseDouble(map2.get("snRK") + "");
					ymRK += Double.parseDouble(map2.get("ymRK") + "");
					yxRK += Double.parseDouble(map2.get("yxRK") + "");
					xjzRK += Double.parseDouble(map2.get("xjzRK") + "");
					// sumghRK += Double.parseDouble(map2.get("sumghRk") + "");
					ghsnRK += Double.parseDouble(map2.get("ghsnRK") + "");
					ghymRK += Double.parseDouble(map2.get("ghymRK") + "");
					ghyxRK += Double.parseDouble(map2.get("ghyxRK") + "");
					ghxjzRK += Double.parseDouble(map2.get("ghxjzRK") + "");

				}
			}
			Map<String, Object> temp = new HashMap<>();
			temp.putAll(map);
			temp.put("hjRk", hjRK);
			temp.put("snRk", snRK);
			temp.put("ymRk", ymRK);
			temp.put("yxRk", yxRK);
			temp.put("xjzRk", xjzRK);
			temp.put("sumghRk", ghsnRK + ghymRK + ghyxRK + ghxjzRK);
			temp.put("ghsnRk", ghsnRK);
			temp.put("ghymRk", ghymRK);
			temp.put("ghyxRk", ghyxRK);
			temp.put("ghxjzRk", ghxjzRK);
			List<String> parents = findParentCodes(regionList, cityCode);
			if (parents != null && parents.size() > 0) {
				parents.remove(0);
			}
			temp.put("parentCodes", parents);
			if (Double.parseDouble(temp.get("hjRk") + "") > 0 || Double.parseDouble(temp.get("sumghRk") + "") > 0) {
				rData.add(temp);
			}
		}
		JSONArray array = listToTree(JSONArray.parseArray(JSON.toJSONString(rData)), "cityCode", "parentCode",
				"children");
		rData = (List<Map<String, Object>>) JSONArray.parse(array.toJSONString());
		return new LyhtResultBody<>(rData);
	}

	/**
	 * 根据用户的ownerNm 获该户的人口界定详情
	 * 
	 * @param ownerNm
	 * @return
	 */
	public Map<String, Object> findUserData(String ownerNm) {
		try {
			Map<String, Object> rData = new HashMap<>();
			if (StringUtils.isBlank(ownerNm)) {
				return rData;
			}
			Map<String, Object> userInfo = productionDao.findUserInfo(ownerNm);
			List<Map<String, Object>> personnel = productionDao.findPersonnel(ownerNm);
			List<Map<String, Object>> list = productionDao.getLandInfoByOwnerNm(ownerNm);
			// 测算生产安置人口
			List<Map<String, Object>> coefficients = productionDao.getConvertCoefficient();
			Integer people = 0;
			// 获取小组人均根底当量
			Double equivalen = productionDao.getCultivatedLandEquivalentGroup(userInfo.get("region") + "");
			if (!CommonUtil.isEmpty(equivalen)) {
				BigDecimal landEquivalen = new BigDecimal(equivalen).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal man = new BigDecimal(0);// 人口数量
				/**
				 * 测算生产安置人口
				 */
				for (Map<String, Object> m : list) {// 遍历土地
					if ((m.get("scope") + "").equals("CE81C0FA94") || (m.get("scope") + "") == "CE81C0FA94") {// 永久占地
																												// POW((1+0.013),2.33)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(
										Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 2.33));
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
							}
						}
					} else if ((m.get("scope") + "").equals("D8D5AAD9DC") || (m.get("scope") + "") == "D8D5AAD9DC") {// 水库淹没POW((1+0.013),8)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(
										Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
										BigDecimal.ROUND_HALF_UP);
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
							}
						}
					} else if ((m.get("scope") + "") == "348F5B68BA" || (m.get("scope") + "").equals("348F5B68BA")) {// 集镇新址占地区POW((1+0.013),8)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(
										Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
										BigDecimal.ROUND_HALF_UP);
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
							}
						}
					} else if ((m.get("scope") + "") == "E78D14E7BE" || (m.get("scope") + "").equals("E78D14E7BE")) {// 水库影响POW((1+0.013),8)
						for (Map<String, Object> map : coefficients) {// 折算系数
							if ((m.get("type") + "").equals(map.get("landType") + "")) {// 类型相同
								BigDecimal area = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 取到面积
								BigDecimal coefficient = new BigDecimal(
										Double.parseDouble(map.get("coefficient") + ""));// 取到折算系数
								BigDecimal convert = area.multiply(coefficient).setScale(2, BigDecimal.ROUND_HALF_UP);// 折算后面积
								BigDecimal landEquivalenFamily = convert.divide(landEquivalen, 2,
										BigDecimal.ROUND_HALF_UP);// 计算家庭人均根底当量
																	// 并保留两位小数
								BigDecimal powCount = new BigDecimal(Math.pow((1 + 0.013), 8)).setScale(2,
										BigDecimal.ROUND_HALF_UP);
								// 根据征地范围 及 系数算出最终结果
								BigDecimal count = landEquivalenFamily.multiply(powCount).setScale(2,
										BigDecimal.ROUND_HALF_UP);// 得到最终人数值
								man = man.add(count);// 累加保存
							}
						}
					}
				}
				Double manCount = man.doubleValue();
				people = manCount.intValue();// 最后取整
			}
			// 逐年补偿
			Map<String, Object> info = new HashMap<String, Object>();
			BigDecimal area = new BigDecimal(0.00);// 折算田
			BigDecimal dip = new BigDecimal(0.00);// 陡坡地
			BigDecimal count = new BigDecimal(0.00);// 逐年补偿人口
			BigDecimal paddyField = new BigDecimal(0.00);// 水田
			BigDecimal dryLand = new BigDecimal(0.00);// 旱地
			BigDecimal fallow = new BigDecimal(0.00);// 休闲地 轮闲地
			BigDecimal matureForest = new BigDecimal(0.00);// 核桃、板栗等成园林
			BigDecimal grape = new BigDecimal(0.00);// 葡萄
			BigDecimal youngGrowth = new BigDecimal(0.00);// 核桃、葡萄、板栗幼园林
			BigDecimal otherGarden = new BigDecimal(0.00);// 其他园地
			for (Map<String, Object> m : list) {
				if ((m.get("scope") + "").equals("CE81C0FA94") || (m.get("scope") + "").equals("D8D5AAD9DC")
						|| (m.get("scope") + "").equals("E78D14E7BE") || (m.get("scope") + "").equals("348F5B68BA")
						|| (m.get("scope") + "") == "CE81C0FA94" || (m.get("scope") + "") == "D8D5AAD9DC"
						|| (m.get("scope") + "") == "E78D14E7BE" || (m.get("scope") + "") == "348F5B68BA") {
					if ((m.get("name") + "").indexOf("水田") != -1) {// 水田
						paddyField = paddyField.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("旱地") != -1) {// 旱地
						dryLand = dryLand.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("陡坡地") != -1) {// 旱地
						dip = dip.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("轮闲地") != -1) {// 轮闲地
						fallow = fallow.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("核桃成园地") != -1) {
						matureForest = matureForest.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("板栗成园地") != -1) {
						matureForest = matureForest.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("葡萄成园地") != -1) {
						grape = matureForest.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("核桃幼园地") != -1) {
						youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("板栗幼园地") != -1) {
						youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("葡萄幼园地") != -1) {
						youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("其他园地") != -1) {
						otherGarden = otherGarden.add((BigDecimal) m.get("area"));
					}
				}
			}
			area = area.add(paddyField).add(dip.multiply(new BigDecimal(0.6)))
					.add(dryLand.multiply(new BigDecimal(0.6)).add(matureForest)
							.add(grape.multiply(new BigDecimal(1.8))))
					.add(youngGrowth.multiply(new BigDecimal(0.8))).add(otherGarden.multiply(new BigDecimal(0.6)));
			// 计算逐年补偿人口
			if (area.doubleValue() > 0) {
				count = area.divide(new BigDecimal(1.042), 2, BigDecimal.ROUND_HALF_UP);
			}
			for (String key : userInfo.keySet()) {
				info.put(key, userInfo.get(key));
			}
			String nNum = userInfo.get("nNum") + "";
			Integer agricultural = Integer.parseInt(nNum);
			if (people < agricultural) {// 搬迁到兰永的移民户，逐年补偿指标在 测算值与家庭人口数间取最大值
				people = agricultural;
			}
			String placeType = userInfo.get("isLY") + "";
			Boolean isLY = Boolean.valueOf(placeType);
			if (isLY) {// 如果是搬迁到兰永的 则 测算人口为 家庭农业人口
				people = agricultural;
				count = new BigDecimal(personnel.size());
			}
			info.put("rk", people);
			info.put("znbcrk", count);
			List<Map<String, Object>> landArea = productionDao.findLandArea(ownerNm);
			List<Map<String, Object>> land = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : landArea) {
				if ((m.get("scope") + "").equals("CE81C0FA94") || (m.get("scope") + "").equals("D8D5AAD9DC")
						|| (m.get("scope") + "").equals("E78D14E7BE") || (m.get("scope") + "").equals("348F5B68BA")
						|| (m.get("scope") + "") == "CE81C0FA94" || (m.get("scope") + "") == "D8D5AAD9DC"
						|| (m.get("scope") + "") == "E78D14E7BE" || (m.get("scope") + "") == "348F5B68BA") {// 属于征收
					if ((m.get("name") + "").indexOf("水田") != -1) {// 水田
						land.add(m);
					} else if ((m.get("name") + "").indexOf("旱地") != -1) {// 旱地
						land.add(m);
					} else if ((m.get("name") + "").indexOf("陡坡地") != -1) {// 旱地
						land.add(m);
					} else if ((m.get("name") + "").indexOf("核桃成园地") != -1) {
						land.add(m);
					} else if ((m.get("name") + "").indexOf("板栗成园地") != -1) {
						land.add(m);
					} else if ((m.get("name") + "").indexOf("葡萄成园地") != -1) {
						land.add(m);
					} else if ((m.get("name") + "").indexOf("核桃幼园地") != -1) {
						land.add(m);
					} else if ((m.get("name") + "").indexOf("板栗幼园地") != -1) {
						land.add(m);
					} else if ((m.get("name") + "").indexOf("葡萄幼园地") != -1) {
						land.add(m);
					} else if ((m.get("name") + "").indexOf("其他园地") != -1) {
						land.add(m);
					}
				}
			}

			rData.put("basicsData", userInfo != null ? info : new HashMap<>());
			rData.put("landData", land != null ? land : new ArrayList<>());
			rData.put("personnelData", personnel != null ? personnel : new ArrayList<>());
			return rData;
		} catch (

		Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
	}

	/**
	 * 保存审核数据的同时 提交申请的流程
	 * 
	 * @param ownerNm
	 * @param data
	 * @param request
	 */
	@Transactional
//	public ProductionAuditEntity popuDefinition(String place, String ownerNm, List<Map<String, Object>> data,
	public void popuDefinition(String place, String ownerNm, List<Map<String, Object>> data) {
		// try {
//			// 查询改户人口界定数据
//			Map<String, Object> userData = findUserData(ownerNm);
//			if (userData != null && !userData.isEmpty()) {
//				List<Map<String, Object>> personnelData = (List<Map<String, Object>>) userData.get("personnelData");
//				List<Map<String, Object>> tempUserData = new ArrayList<>();
//				// 替换掉 是否生成安置人口
//				for (Map<String, Object> datum : data) {
//					for (Map<String, Object> personnelDatum : personnelData) {
//						String isProduce = personnelDatum.get("isProduce") != null
//								? personnelDatum.get("isProduce").toString()
//								: null;
//						String nm = personnelDatum.get("nm") != null ? personnelDatum.get("nm").toString() : "";
//						if (nm.equals(datum.get("nm"))) {
//							Map<String, Object> temp = new HashMap<>();
//							temp.putAll(personnelDatum);
//							temp.put("isProduce", datum.get("isProduce"));
//							tempUserData.add(temp);
//						}
//					}
//				}
//				userData.put("personnelData", tempUserData);
//			}
//			ProductionAuditEntity pe = new ProductionAuditEntity();
//			pe.setOwnerNm(ownerNm);
//			productionAuditService.save(pe);
		// 把用户的界定状态修改为界定中
		for (Map<String, Object> map : data) {// 修改家庭成员的 界定
			productionDao.updateUserisProduce(map.get("isProduce") + "", map.get("nm") + "");
		}
		if (StringUtils.isNotBlank(place)) {
//			String[] spilt = place.split(",");
//			String producePlace = "";
//			for (int i = 2; i < spilt.length; i++) {
//				producePlace += spilt[i];
//				if (i != spilt.length-1) {
//					producePlace += ",";
//				}
//			}
			ownerDao.updateProducePlace(place, ownerNm);
			familyDao.updateProducePlace(place, ownerNm);
		}

		// productionDao.updateOwner("0", ownerNm);// 界定中
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
//		}

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
				// 寻找该节点的子节点
				List<Map<String, Object>> children = toTree(mapList, cityCode, false, level + 1, serialS, tempParents);
				data.putAll(map);
				data.put("parents", parents);
				rData.add(data);
				data.put("children", children);
				if (!isOne) {
					data.put("serial", parent + "." + serial);
				} else {
					data.put("serial", serial);
				}
				serial++;
			}
		}
		return rData;
	}

	/**
	 * 根据传入的节点素组 返回节点数组的所有父级
	 * 
	 * @param mapList 所有的行政区数据
	 * @param codes   需要查找父级行政区的行政区的编码
	 * @param czPNode 已经存在的父级行政区
	 * @return
	 */
	public List<Map<String, Object>> findParent(List<Map<String, Object>> mapList, List<String> codes,
			Map<String, Object> czPNode, Boolean flag) {
		List<Map<String, Object>> rData = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			// 如果该行政区的编码存在于传入的行政区数组的编码中 并且czPNode中不存在 则进行父子级行政区的查找
			if (codes.contains(cityCode) && !czPNode.containsKey(cityCode)) {
				Map<String, Object> temp = new HashMap<>();
				czPNode.put(cityCode, cityCode);
				if (flag) {
					temp.put("open", "true");
				} else {
					temp.put("open", "false");
				}
				temp.putAll(map);
				rData.add(temp);
				// 假如父节点不为空 则去查找所有的父级节点
				if (StringUtils.isNotBlank(parentCode)) {
					// 临时存储单个父节点编码
					List<String> pcode = Arrays.asList(parentCode);
					// 返回父节点
					List<Map<String, Object>> node = findParent(mapList, pcode, czPNode, true);
					rData.addAll(node);
				}
			}
		}
		return rData;
	}

	/**
	 * 查询所有子节点code
	 * 
	 * @param mapList
	 * @param code
	 * @return
	 */
	public List<String> findChild(List<Map<String, Object>> mapList, String code) {
		List<String> rData = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			if (parentCode.equals(code)) {
				rData.add(cityCode);
				// 返回子节点
				List<String> node = findChild(mapList, cityCode);
				rData.addAll(node);
			}
		}
		return rData;
	}

	/**
	 * 查询所有父节点code
	 * 
	 * @param mapList
	 * @param cCode
	 * @return
	 */
	public List<String> findParentCodes(List<Map<String, Object>> mapList, String cCode) {
		List<String> rData = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			if (cCode.equals(cityCode) && !"".equals(cityCode)) {
				rData.add(cityCode);
				List<String> node = findParentCodes(mapList, parentCode);
				rData.addAll(node);
			}
		}
		return rData;
	}

	/**
	 * 查询下一级的子节点元素
	 * 
	 * @param mapList
	 * @param region
	 * @param one
	 * @return
	 */
	public List<Map<String, Object>> findOneChild(List<Map<String, Object>> mapList, String region, Boolean one) {
		List<Map<String, Object>> rData = new ArrayList<>();
		for (Map<String, Object> map : mapList) {
			String parentCode = map.get("parentCode") != null ? map.get("parentCode").toString() : "",
					cityCode = map.get("cityCode") != null ? map.get("cityCode").toString() : "";
			if (parentCode.equals(region)) {
				Map<String, Object> temp = new HashMap<>();
				temp.putAll(map);
				rData.add(temp);
				if (one) {
					List<Map<String, Object>> oneChild = findOneChild(mapList, cityCode, false);
					temp.put("isLast", (oneChild == null || oneChild.size() == 0) + "");
				} else {
					return rData;
				}
			} else if (region == null && parentCode.equals("")) {
				Map<String, Object> temp = new HashMap<>();
				List<Map<String, Object>> oneChild = findOneChild(mapList, cityCode, false);
				temp.put("isLast", (oneChild == null || oneChild.size() == 0) + "");
				temp.putAll(map);
				rData.add(temp);
			}
		}
		return rData;
	}

	/**
	 * 点击汇总数字卡片统计
	 */
	public LyhtResultBody<List<ProduceCardStatisticsVO>> getCardStatisiscs(String region) {
		return new LyhtResultBody<>(productionDao.getCardStatisiscs(region));
	}

	/**
	 * 根据流程id查询界定确认信息
	 */
	public LyhtResultBody<ProduceProcessInfoVO> getInfoByTaskId(String taskId) {
		return new LyhtResultBody<>(productionDao.getInfoByTaskId(taskId));
	}

	/**
	 * 得到权属人的逐年补偿人口
	 */
	public BigDecimal getYearByYearPopulation(String ownerNm) {
		AbmOwnerEntity ownerEntity = ownerDao.findByNm(ownerNm);
		List<Map<String, Object>> list = productionDao.getLandInfoByOwnerNm(ownerNm);// 查询权属人土地信息
		BigDecimal area = new BigDecimal(0.00);// 折算田
		BigDecimal count = new BigDecimal(0.00);// 逐年补偿人口
		// 征收
		BigDecimal paddyField = new BigDecimal(0.00);// 水田
		BigDecimal dryLand = new BigDecimal(0.00);// 旱地
		BigDecimal matureForest = new BigDecimal(0.00);// 核桃、板栗等成园林
		BigDecimal grape = new BigDecimal(0.00);// 葡萄
		BigDecimal youngGrowth = new BigDecimal(0.00);// 核桃、葡萄、板栗幼园林
		BigDecimal otherGarden = new BigDecimal(0.00);// 其他园地
		for (Map<String, Object> m : list) {
			if ((m.get("name") + "").indexOf("水田") != -1) {// 水田
				paddyField = paddyField.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("旱地") != -1) {// 旱地
				dryLand = dryLand.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("核桃成园地") != -1) {
				matureForest = matureForest.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("板栗成园地") != -1) {
				matureForest = matureForest.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("葡萄成园地") != -1) {
				grape = grape.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("核桃幼园地") != -1) {
				youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("板栗幼园地") != -1) {
				youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("葡萄幼园地") != -1) {
				youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
			} else if ((m.get("name") + "").indexOf("其他园地") != -1) {
				otherGarden = otherGarden.add((BigDecimal) m.get("area"));
			}
		}
		/**
		 * 1亩旱地折算为0.6亩水田，1亩板栗、核桃成园折算1亩水田，1亩葡萄成园折算1.8亩水田，
		 * 1亩板栗、核桃、葡萄幼园折算0.8亩水田，1亩其他园地折算0.6亩水田
		 */
		area = area.add(paddyField)
				.add(dryLand.multiply(new BigDecimal(0.6)).add(matureForest).add(grape.multiply(new BigDecimal(1.8))))
				.add(youngGrowth.multiply(new BigDecimal(0.8))).add(otherGarden.multiply(new BigDecimal(0.6)));
		// 计算逐年补偿人口
		if (area.doubleValue() > 0) {
			count = area.divide(new BigDecimal(1.042), 2, BigDecimal.ROUND_HALF_UP);
		}
		List<AbmFamilyEntity> findByOwnerNm = familyDao.findByOwnerNm(ownerNm);
		String isLY = ownerEntity.getZu();
		Integer agricultural = ownerEntity.getAp();
		if (StringUtils.isNotBlank(isLY) && isLY.indexOf("兰永") != -1) {// 搬迁到兰永
			if (count.intValue() < agricultural) {
				count = new BigDecimal(findByOwnerNm.size());
			}
		}
		return count;
	}

	/**
	 * 托巴水电站建设区征地补偿补偿协议书
	 * 
	 * @param ownerNm
	 * @param response
	 */
	public void export(String ownerNm, HttpServletResponse response) {
		String path = "D:\\Server\\uploads\\tuoba\\word\\";
		List<Map<String, Object>> list = productionDao.getLandInfoByOwnerNm(ownerNm);// 查询权属人土地信息
		Map<String, Object> info = productionDao.getInfoByOwnerNm(ownerNm);// 查询所需权属人信息
		String filepathString = path + "托巴逐年补偿协议书.doc";
		String destpathString = path + info.get("name") + "-托巴逐年补偿协议书.doc";
		BigDecimal area = new BigDecimal(0.00);// 折算田
		BigDecimal count = new BigDecimal(0.00);// 逐年补偿人口
		BigDecimal total = new BigDecimal(0);// 逐年补偿金额
		BigDecimal cardinal = new BigDecimal(290);// 逐年补偿金额290/人
		String upperTotal = "";
		// 征收
		BigDecimal paddyField = new BigDecimal(0.00);// 水田
		BigDecimal dryLand = new BigDecimal(0.00);// 旱地
		BigDecimal dip = new BigDecimal(0.00);// 陡坡地
		BigDecimal fallow = new BigDecimal(0.00);// 休闲地 轮闲地
		BigDecimal matureForest = new BigDecimal(0.00);// 核桃、板栗等成园林
		BigDecimal grape = new BigDecimal(0.00);// 葡萄
		BigDecimal youngGrowth = new BigDecimal(0.00);// 核桃、葡萄、板栗幼园林
		BigDecimal otherGarden = new BigDecimal(0.00);// 其他园地
		BigDecimal vivavium = new BigDecimal(0.00);// 鱼塘

		// 征用
		BigDecimal paddyField01 = new BigDecimal(0.00);// 水田
		BigDecimal dryLand01 = new BigDecimal(0.00);// 旱地
		BigDecimal dip01 = new BigDecimal(0.00);// 陡坡地
		BigDecimal fallow01 = new BigDecimal(0.00);// 休闲地 轮闲地
		BigDecimal matureForest01 = new BigDecimal(0.00);// 核桃、板栗等成园林
		BigDecimal grape01 = new BigDecimal(0.00);// 葡萄
		BigDecimal youngGrowth01 = new BigDecimal(0.00);// 核桃、葡萄、板栗幼园林
		BigDecimal otherGarden01 = new BigDecimal(0.00);// 其他园地
		BigDecimal vivavium01 = new BigDecimal(0.00);// 鱼塘

		StringBuffer context = new StringBuffer("");
		Map<String, String> map = new HashMap<String, String>();
		map.put("${REGION}", info.get("region") + "");// 地址
		map.put("${NAME}", info.get("name") + "");// 权属人姓名
		String[] landTypes = { "水田", "旱地", "休闲地", "轮闲地", "核桃成园地", "核桃幼园地", "板栗成园地", "板栗幼园地", "葡萄成园地", "葡萄幼园地", "其他园地",
				"鱼塘" };
		List<String> landTypeList = Arrays.asList(landTypes);
		if (list.size() > 0) {

			for (Map<String, Object> m : list) {// 循环判断土地类型 轮闲地 === 休闲地
				/**
				 * 土地征收范围：水库淹没，水库影响，枢纽永久（永久占地），集镇新址 土地征用范围：枢纽临时（临时占地），临时垫高（垫高临时）
				 */
				if ((m.get("scope") + "").equals("CE81C0FA94") || (m.get("scope") + "").equals("D8D5AAD9DC")
						|| (m.get("scope") + "").equals("E78D14E7BE") || (m.get("scope") + "").equals("348F5B68BA")
						|| (m.get("scope") + "") == "CE81C0FA94" || (m.get("scope") + "") == "D8D5AAD9DC"
						|| (m.get("scope") + "") == "E78D14E7BE" || (m.get("scope") + "") == "348F5B68BA") {// 属于征收
					if ((m.get("name") + "").indexOf("水田") != -1) {// 水田
						paddyField = paddyField.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("旱地") != -1) {// 旱地
						dryLand = dryLand.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("陡坡地") != -1) {// 陡坡地
						dip = dip.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("轮闲地") != -1) {// 轮闲地
						fallow = fallow.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("核桃成园地") != -1) {
						matureForest = matureForest.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("板栗成园地") != -1) {
						matureForest = matureForest.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("葡萄成园地") != -1) {
						grape = grape.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("核桃幼园地") != -1) {
						youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("板栗幼园地") != -1) {
						youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("葡萄幼园地") != -1) {
						youngGrowth = youngGrowth.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("其他园地") != -1) {
						otherGarden = otherGarden.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("鱼塘") != -1) {
						vivavium = vivavium.add((BigDecimal) m.get("area"));
					}
				} else {// 征用
					if ((m.get("name") + "").indexOf("水田") != -1) {// 水田
						paddyField01 = paddyField01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("旱地") != -1) {// 旱地
						dryLand01 = dryLand01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("陡坡地") != -1) {// 陡坡地
						dip01 = dip01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("轮闲地") != -1) {// 轮闲地
						fallow01 = fallow01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("核桃成园地") != -1) {
						matureForest01 = matureForest01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("板栗成园地") != -1) {
						matureForest01 = matureForest01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("葡萄成园地") != -1) {
						grape01 = grape01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("核桃幼园地") != -1) {
						youngGrowth01 = youngGrowth01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("板栗幼园地") != -1) {
						youngGrowth01 = youngGrowth01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("葡萄幼园地") != -1) {
						youngGrowth01 = youngGrowth01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("其他园地") != -1) {
						otherGarden01 = otherGarden01.add((BigDecimal) m.get("area"));
					} else if ((m.get("name") + "").indexOf("鱼塘") != -1) {
						vivavium01 = vivavium01.add((BigDecimal) m.get("area"));
					}
				}
			}
			if (paddyField.doubleValue() > 0 || dryLand.doubleValue() > 0 || fallow.doubleValue() > 0
					|| matureForest.doubleValue() > 0 || grape.doubleValue() > 0 || youngGrowth.doubleValue() > 0
					|| otherGarden.doubleValue() > 0 || vivavium.doubleValue() > 0) {// 存在征收地
				context.append("征收乙方");
			} else {// 不存在征收地
				if (paddyField01.doubleValue() > 0 || dryLand01.doubleValue() > 0 || fallow01.doubleValue() > 0
						|| matureForest01.doubleValue() > 0 || grape01.doubleValue() > 0
						|| youngGrowth01.doubleValue() > 0 || otherGarden01.doubleValue() > 0
						|| vivavium01.doubleValue() > 0) {// 存在征用地
					context.append("征用乙方");
				}
			}
			if (paddyField.doubleValue() > 0) {
				context.append("水田" + String.format("%.2f", paddyField) + "亩,");
			}
			if (dryLand.doubleValue() > 0) {
				context.append("旱地" + String.format("%.2f", dryLand) + "亩,");
			}
			if (fallow.doubleValue() > 0) {
				context.append("休闲地" + String.format("%.2f", fallow) + "亩,");
			}
			if (matureForest.doubleValue() > 0) {
				context.append("核桃、板栗等成园林" + String.format("%.2f", matureForest) + "亩,");
			}
			if (grape.doubleValue() > 0) {
				context.append("葡萄成园" + String.format("%.2f", grape) + "亩,");
			}
			if (youngGrowth.doubleValue() > 0) {
				context.append("核桃、葡萄、板栗幼园林" + String.format("%.2f", youngGrowth) + "亩,");
			}
			if (otherGarden.doubleValue() > 0) {
				context.append("其它园地" + String.format("%.2f", otherGarden) + "亩,");
			}
			if (vivavium.doubleValue() > 0) {
				context.append("鱼塘" + String.format("%.2f", vivavium) + "亩,");
			}

			for (Map<String, Object> m : list) {// 不属于以上地类
				if ((m.get("scope") + "").equals("CE81C0FA94") || (m.get("scope") + "").equals("D8D5AAD9DC")
						|| (m.get("scope") + "").equals("E78D14E7BE") || (m.get("scope") + "").equals("348F5B68BA")
						|| (m.get("scope") + "") == "CE81C0FA94" || (m.get("scope") + "") == "D8D5AAD9DC"
						|| (m.get("scope") + "") == "E78D14E7BE" || (m.get("scope") + "") == "348F5B68BA") {
					String name = m.get("name") + "";
					BigDecimal acreage = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if (!landTypeList.contains(name)) {
						context.append(name + acreage + "亩，");
					}
				}
			}
			String str = context.toString();
			StringBuffer buffer = new StringBuffer(str);
			if (paddyField.doubleValue() > 0 || dryLand.doubleValue() > 0 || fallow.doubleValue() > 0
					|| matureForest.doubleValue() > 0 || grape.doubleValue() > 0 || youngGrowth.doubleValue() > 0
					|| otherGarden.doubleValue() > 0 || vivavium.doubleValue() > 0) {// 存在征收地
				if (paddyField01.doubleValue() > 0 || dryLand01.doubleValue() > 0 || fallow01.doubleValue() > 0
						|| matureForest01.doubleValue() > 0 || grape01.doubleValue() > 0
						|| youngGrowth01.doubleValue() > 0 || otherGarden01.doubleValue() > 0
						|| vivavium01.doubleValue() > 0) {// 存在征用地
					str = str.substring(0, str.length() - 1);
					buffer.append("。征用乙方");
				} else {// 不存在征用
					str = str.substring(0, str.length() - 1);
				}
			}

			if (paddyField01.doubleValue() > 0) {
				buffer.append("水田" + String.format("%.2f", paddyField01) + "亩,");
			}
			if (dryLand01.doubleValue() > 0) {
				buffer.append("旱地" + String.format("%.2f", dryLand01) + "亩,");
			}
			if (fallow01.doubleValue() > 0) {
				buffer.append("休闲地" + String.format("%.2f", fallow01) + "亩,");
			}
			if (matureForest01.doubleValue() > 0) {
				buffer.append("核桃、板栗等成园林" + String.format("%.2f", matureForest01) + "亩,");
			}
			if (grape01.doubleValue() > 0) {
				buffer.append("葡萄成园" + String.format("%.2f", grape01) + "亩,");
			}
			if (youngGrowth01.doubleValue() > 0) {
				buffer.append("核桃、葡萄、板栗幼园林" + String.format("%.2f", youngGrowth01) + "亩,");
			}
			if (otherGarden01.doubleValue() > 0) {
				buffer.append("其它园地" + String.format("%.2f", otherGarden01) + "亩,");
			}
			if (vivavium01.doubleValue() > 0) {
				buffer.append("鱼塘" + String.format("%.2f", vivavium01) + "亩,");
			}
			for (Map<String, Object> m : list) {// 不属于以上地类
				if (!(m.get("scope") + "").equals("CE81C0FA94") || !(m.get("scope") + "").equals("D8D5AAD9DC")
						|| !(m.get("scope") + "").equals("E78D14E7BE") || !(m.get("scope") + "").equals("348F5B68BA")
						|| (m.get("scope") + "") != "CE81C0FA94" || (m.get("scope") + "") != "D8D5AAD9DC"
						|| (m.get("scope") + "") != "E78D14E7BE" || (m.get("scope") + "") != "348F5B68BA") {
					String name = m.get("name") + "";
					BigDecimal acreage = new BigDecimal(Double.parseDouble(m.get("area") + "")).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					if (!landTypeList.contains(name)) {
						buffer.append(name + acreage + "亩，");
					}
				}
			}
			String lastStr = buffer.toString();
			lastStr = lastStr.substring(0, lastStr.length() - 1);// 去掉结尾的逗号
			if (paddyField.doubleValue() > 0 || dryLand.doubleValue() > 0 || fallow.doubleValue() > 0
					|| matureForest.doubleValue() > 0 || grape.doubleValue() > 0 || youngGrowth.doubleValue() > 0
					|| otherGarden.doubleValue() > 0 || vivavium.doubleValue() > 0) {// 存在征收
				if (paddyField01.doubleValue() > 0 || dryLand01.doubleValue() > 0 || fallow01.doubleValue() > 0) {// 存在征用地
					map.put("${CONTEXT}", str);
				} else {// 不存在征用
					map.put("${CONTEXT}", str);
				}
			} else {// 不存在征收
				if (paddyField01.doubleValue() > 0 || dryLand01.doubleValue() > 0 || fallow01.doubleValue() > 0
						|| matureForest01.doubleValue() > 0 || grape01.doubleValue() > 0
						|| youngGrowth01.doubleValue() > 0 || otherGarden01.doubleValue() > 0
						|| vivavium01.doubleValue() > 0) {// 存在征用地
					map.put("${CONTEXT}", lastStr);
				} else {// 不存在征用
					map.put("${CONTEXT}", "");
				}
			}

			/**
			 * 1亩旱地折算为0.6亩水田，1亩板栗、核桃成园折算1亩水田，1亩葡萄成园折算1.8亩水田，
			 * 1亩板栗、核桃、葡萄幼园折算0.8亩水田，1亩其他园地折算0.6亩水田
			 */
			area = area.add(paddyField)
					.add(dryLand.multiply(new BigDecimal(0.6)).add(matureForest)
							.add(grape.multiply(new BigDecimal(1.8))))
					.add(youngGrowth.multiply(new BigDecimal(0.8))).add(otherGarden.multiply(new BigDecimal(0.6))).add(dip.multiply(new BigDecimal(0.6)));

		} else {// 没有土地
			map.put("${CONTEXT}", "征地征收乙方土地0亩，征用乙方土地0亩");
		}
		AbmOwnerEntity ownerEntity = ownerDao.findByNm(ownerNm);
		String place = ownerEntity.getZu();
		if (StringUtils.isNotBlank(place) && place.indexOf("兰永") != -1) {
			List<AbmFamilyEntity> findByOwnerNm = familyDao.findByOwnerNm(ownerNm);
			count = new BigDecimal(findByOwnerNm.size());
		}
		// 计算逐年补偿人口
		if (area.doubleValue() > 0) {
			count = area.divide(new BigDecimal(1.042), 2, BigDecimal.ROUND_HALF_UP);
		}
		if (count.doubleValue() > 0) {
			total = cardinal.multiply(count).multiply(new BigDecimal(12));// 计算补偿金额
		}
		upperTotal = AmountToChineseUtil.toChinese(String.format("%.2f", total));
		map.put("${AREA}", String.format("%.2f", area) + "");
		map.put("${COUNT}", String.format("%.2f", count));
		map.put("${TOTAL}", String.format("%.2f", total));
		map.put("${UPPERTOTAL}", upperTotal);
		WordReplaceKeyUtil.replaceAndGenerateWord(filepathString, destpathString, map);// 替换并生成新的文档
		// 转为PDF后 删除源文件 再下载
		// WordTOPDF.wordToPdf(info.get("name") + "-托巴逐年补偿协议书.pdf", destpathString,
		// path, "doc");
		FileDownUtil.getFile(path + info.get("name") + "-托巴逐年补偿协议书.doc", info.get("name") + "-托巴逐年补偿协议书.doc", // 下载新的文档
				response);
		File file = new File(destpathString);// 将生成的文档从内存中删除
		if (file.exists()) {
			file.delete();
		}
//		File file1 = new File(path + info.get("name") + "-托巴逐年补偿协议书.pdf");// 将生成的文档从内存中删除
//		if (file1.exists()) {
//			file1.delete();
//		}
	}

}
