package com.lyht.business.abm.plan.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.landAllocation.dao.LandAuditDao;
import com.lyht.business.abm.landAllocation.entity.LandAuditEntity;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.dao.PublicityDao;
import com.lyht.business.abm.plan.dao.PublicityDetailDao;
import com.lyht.business.abm.plan.entity.PublicityDetailEntity;
import com.lyht.business.abm.plan.entity.PublicityEntity;
import com.lyht.business.abm.plan.vo.PublicityFamilyVO;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.message.service.MessageNoticeService;
import com.lyht.business.pub.dao.PubDictValueDao;
import com.lyht.util.DateUtil;
import com.lyht.util.Randomizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PublicityService {

	@Autowired
	PubDictValueDao pubDictValueDao;

	@Autowired
	private LandAuditDao landAuditDao;
	@Autowired
	private MessageNoticeService messageNoticeService;

	@Autowired
	private AbmOwnerDao abmOwnerDao;
	@Autowired
	private PublicityDao dao;
	@Autowired
	private PublicityDetailDao detailDao;
	@Autowired
	private ProjectPlanDao projectPlanDao;
	@Autowired
	private OwnerVerifyService ownerVerifyService;

	@Autowired
	private AbmFamilyDao familyDao;

	@Autowired
	private AbmOwnerDao ownerDao;

	@Transactional
	public LyhtResultBody<PublicityEntity> save(PublicityEntity entity, String list) {
		String nm = Randomizer.generCode(10);

//		PubRegionEntity region = regionDao.findByName(entity.getRegion());
//		entity.setRegion(region.getCityCode());
//		entity.setRegionName(region.getName());
		List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(list);
		if (listObjectFir.size() > 0) {
			for (int i = 0; i < listObjectFir.size(); i++) {
				PublicityDetailEntity pub = new PublicityDetailEntity();

				String ownerNm = String.valueOf(listObjectFir.get(i).get("ownerNm"));
				String zbType = listObjectFir.get(i).getOrDefault(("zb_type").toString(), "zb_type".toString());
				String endTime = listObjectFir.get(i).getOrDefault(("end_time").toString(), "end_time".toString());

				pub.setOwnerNm(ownerNm);
				pub.setZbType(zbType);
				pub.setNm(nm);
				pub.setEndTime(endTime);
				detailDao.save(pub);
				if (Integer.parseInt(entity.getZbType()) == 2) {// 人口公式
					if (entity.getType().equals("52C2A14B90")) {// 生产安置
						familyDao.updateGsState(-1, pub.getOwnerNm());
					} else if (entity.getType().equals("BFCFDCE543")) {// 搬迁安置
						familyDao.updateBQGsState(-1, pub.getOwnerNm());
					} else if (entity.getType().equals("6598659035")) {// 逐年补偿

					}
				} else if (Integer.parseInt(entity.getZbType()) == 1) {
					if (entity.getType().equals("E571237B71")) {// 土地
						String id = pub.getOwnerNm();
						landAuditDao.updateFgState(Integer.parseInt(id), 1);
					} else if (entity.getType().equals("D5A8B0DB4F")) {
						ownerVerifyService.editGsState("-1", ownerNm);
					} // 个人财产
				}
			}
		}

		PublicityEntity result = null;

		// ownerVerifyService.editGsState("1", entity.getNm());//1 公示中
		entity.setNm(nm);
		entity.setState("-1");// 公示状态 -1待公式 1公示中 2公示结束
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		entity.setCreateTime(sdf.format(date));
		String time = "";
		List<Map> code = dao.getGsCode();
		if (code.size() > 0) {
			String s = code.get(0).get("code").toString();
			Integer i = Integer.valueOf(s.substring(s.length() - 4));
			i = i + 1;
			String str = i.toString();
			if (str.length() == 1) {
				time = "GS" + sdf1.format(date) + "000" + str;
			}
			if (str.length() == 2) {
				time = "GS" + sdf1.format(date) + "00" + str;
			}
			if (str.length() == 3) {
				time = "GS" + sdf1.format(date) + "0" + str;
			}
			if (str.length() == 4) {
				time = "GS" + sdf1.format(date) + str;
			}
			entity.setCode(time);
		} else {
			time = "GS" + sdf1.format(date) + "0001";
			entity.setCode(time);
		}
		result = dao.save(entity);
		List<Map> listMap = projectPlanDao.regionName(result.getRegion());
		if (listMap.size() > 0) {
			result.setRegionName(listMap.get(0).get("merger_name").toString());
		}

		return new LyhtResultBody<>(result);

	}

	public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, String region, String name, String zbType,
			String startTime, String endTime) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = dao.getList(pageable, region, name, zbType, startTime, endTime);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	public LyhtResultBody<List<Map>> getListDetails(LyhtPageVO lyhtPageVO, String nm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = dao.getListDetails(pageable, nm);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	@Transactional
	public LyhtResultBody<String> deletePublicity(String nm, Integer type) {
		PublicityEntity resultSet = dao.findByNm(nm);
//	        	if(type==1){//实物指标
//		            ownerVerifyService.editGsState("0", nm);//0 未发布
//
//	        	}
//	        	if(type==2){//搬迁人口
//	                ownerVerifyService.editBqGsState("0", nm);//1 公示中
//
//	        	}
		List<PublicityDetailEntity> pub = detailDao.getNms(resultSet.getNm());
		for (PublicityDetailEntity publicityDetailEntity : pub) {
			ownerVerifyService.editGsState("0", publicityDetailEntity.getOwnerNm());// 0 未发布
			if (type == 0) {// 个人财产
				ownerDao.updateAbmFamily(publicityDetailEntity.getOwnerNm(), "0");
			} else if (type == 1) {// 土地;
				landAuditDao.updateFgState(Integer.parseInt(publicityDetailEntity.getOwnerNm()), 0);
			} else if (type == 2) {// 人口
				familyDao.updateGsState(0, publicityDetailEntity.getOwnerNm());
			}
		}
		dao.delete(resultSet);
		detailDao.deleteNm(resultSet.getNm());

		return new LyhtResultBody(nm);

	}

	public LyhtResultBody<Integer> deleteDetail(Integer id) {
		try {
			PublicityDetailEntity resultSet = detailDao.getOne(id);
			detailDao.delete(resultSet);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(id);

	}

	public PublicityDetailEntity getOne(Integer id) {
		return detailDao.getOne(id);
	}

	public List<Map> getListExcl(String region, String name, String homeName) {
		return dao.getListExcl(region, name, homeName);
	}

	@Transactional(noRollbackFor = Exception.class)
	public void updateState(PublicityEntity publicityEntity, String senderNm) {

		String nm = publicityEntity.getNm();

		PublicityEntity pubs = dao.getNm(nm);// 查到记录
		String dictName = pubDictValueDao.getDictName("dict_day");
		// String endDate = getFetureDate(Integer.parseInt(dictName));
		pubs.setState("1");
		pubs.setStartTime(DateUtil.getDate());
		// pubs.setEndTime(endDate);
//		String state="1";//公示状态 0 未公示 1公示中 2公示结束
//		dao.updateState(state ,nm);//更新t_abm_publicity表
		List<PublicityDetailEntity> list = detailDao.getNms(nm);
		String jsonString = JSON.toJSONString(pubs);
		for (PublicityDetailEntity pub : list) {
			String ownerNm = pub.getOwnerNm();
			abmOwnerDao.updateAbmFamily(ownerNm, "1");// 更新人员t_info_owner_impl表
			messageNoticeService.sendMessageNoticeByUser(senderNm, ownerNm, "实物指标公式开始", jsonString, "SHOW");
		}

	}

	public List<Map> findList(String nm) {
		PublicityEntity publicityEntity = dao.findByNm(nm);
		List<Map> list = new ArrayList<Map>();
		if (publicityEntity.getType().equals("D5A8B0DB4F")) {// 个人财产
			list = detailDao.getNm(nm);
		} else {// 土地
			list = detailDao.getByNm(nm);
		}
		return list;
	}

	public void delete(String nm) {
		List<PublicityDetailEntity> pub = detailDao.getNms(nm);
		for (PublicityDetailEntity publicityDetailEntity : pub) {
			ownerVerifyService.editGsState("0", publicityDetailEntity.getOwnerNm());// 0 未发布
		}
		detailDao.deleteNm(nm);
	}

	@Transactional
	public void endPublicity(int id, String senderNm) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		PublicityEntity resultSet = dao.getOne(id);
		resultSet.setState("2");
		resultSet.setEndTime(df.format(new Date()));
		dao.save(resultSet);
		Integer count = detailDao.updateEndTime(resultSet.getNm(), df.format(new Date()));// 修改关联表中结束时间
		List<PublicityDetailEntity> detailList = detailDao.findByNm(resultSet.getNm());
		String jsonString = JSON.toJSONString(resultSet);
		for (PublicityDetailEntity publicityDetailEntity : detailList) {
			String ownerNm = publicityDetailEntity.getOwnerNm();
			abmOwnerDao.updateAbmFamily(ownerNm, "2");// 更新人员t_info_owner_impl表
			abmOwnerDao.resetState(ownerNm);// 初始化生产安置状态
			messageNoticeService.sendMessageNoticeByUser(senderNm, ownerNm, "实物指标公式结束", jsonString, "SHOW");
		}

	}

	@Transactional
	public void endPublicityLand(int id) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		PublicityEntity resultSet = dao.getOne(id);
		resultSet.setState("2");
		resultSet.setEndTime(df.format(new Date()));
		dao.save(resultSet);
		List<PublicityDetailEntity> findNm = detailDao.findByNm(resultSet.getNm());
		Integer count = detailDao.updateEndTime(resultSet.getNm(), df.format(new Date()));// 修改关联表中结束时间
		for (PublicityDetailEntity td : findNm) {
			ownerVerifyService.editfgState("2", td.getOwnerNm());
		}
	}

	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	/**
	 * 移民人口公示 查询所有已完成生产安置的家庭成员
	 */
	public LyhtResultBody<List<PublicityFamilyVO>> getPublicityFamily(String region) {
		return new LyhtResultBody<>(dao.getPublicityFamily(region));
	}

	/**
	 * 移民人口公示 查询所有已完成搬迁安置的家庭成员
	 */
	public LyhtResultBody<List<PublicityFamilyVO>> getPubRegionInfoRemovalFamily(String region) {
		return new LyhtResultBody<>(dao.getPubRegionInfoRemovalFamily(region));
	}

	/**
	 * 移民人口公示开始 id:公示批次id
	 */
	@Transactional
	public LyhtResultBody<PublicityEntity> startPublic(Integer id, Integer type, String senderNm) {
		// 根据批次id查询到公示
		Optional<PublicityEntity> optional = dao.findById(id);
		PublicityEntity entity = optional.get();
		String jsonString = JSON.toJSONString(entity);
		if (type == 0) {// 开始
			// 修改公示批次的公式状态
			dao.updateState("1", entity.getNm());
			// 根据公示批次 查询到所有有关的人
			List<PublicityDetailEntity> list = detailDao.findByNm(entity.getNm());
			if (entity.getType().equals("52C2A14B90")) {// 生产安置
				String ownerNm = "";
				for (PublicityDetailEntity publicityDetailEntity : list) {// 逐个修改公示状态
					familyDao.updateGsState(1, publicityDetailEntity.getOwnerNm());
					ownerNm = ownerNm + publicityDetailEntity.getOwnerNm() + ",";
				}
				List<String> nms = Arrays.asList(ownerNm.split(","));
				List<String> ownerNms = familyDao.getOwnerNmByNms(nms);// 取得所有公示人的户主nm
				for (String string : ownerNms) {
					messageNoticeService.sendMessageNoticeByUser(senderNm, string, "生产安置人口公式开始", jsonString, "SHOW");
				}
			} else if (entity.getType().equals("BFCFDCE543")) {// 搬迁安置
				String ownerNm = "";
				for (PublicityDetailEntity publicityDetailEntity : list) {// 逐个修改公示状态
					familyDao.updateBQGsState(1, publicityDetailEntity.getOwnerNm());
					ownerNm = ownerNm + publicityDetailEntity.getOwnerNm() + ",";
				}
				List<String> nms = Arrays.asList(ownerNm.split(","));
				List<String> ownerNms = familyDao.getOwnerNmByNms(nms);// 取得所有公示人的户主nm
				for (String string : ownerNms) {
					messageNoticeService.sendMessageNoticeByUser(senderNm, string, "搬迁安置人口公式开始", jsonString, "SHOW");
				}
			} else if (entity.getType().equals("6598659035")) {// 逐年补偿

			}

		} else {// 结束
			// 根据公示批次 查询到所有有关的人
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			entity.setEndTime(format.format(new Date()));
			entity.setState("2");
			dao.save(entity);
			List<PublicityDetailEntity> list = detailDao.findByNm(entity.getNm());
			if (entity.getType().equals("52C2A14B90")) {// 生产安置
				String ownerNm = "";
				for (PublicityDetailEntity publicityDetailEntity : list) {// 逐个修改公示状态
					familyDao.updateGsState(2, publicityDetailEntity.getOwnerNm());
					ownerNm = ownerNm + publicityDetailEntity.getOwnerNm() + ",";
				}
				List<String> nms = Arrays.asList(ownerNm.split(","));
				List<String> ownerNms = familyDao.getOwnerNmByNms(nms);// 取得所有公示人的户主nm
				for (String string : ownerNms) {
					messageNoticeService.sendMessageNoticeByUser(senderNm, string, "生产安置人口公式结束", jsonString, "SHOW");
				}
			} else if (entity.getType().equals("BFCFDCE543")) {// 搬迁安置
				String ownerNm = "";
				for (PublicityDetailEntity publicityDetailEntity : list) {// 逐个修改搬迁安置公示状态
					familyDao.updateBQGsState(2, publicityDetailEntity.getOwnerNm());
					ownerNm = ownerNm + publicityDetailEntity.getOwnerNm() + ",";
				}
				List<String> nms = Arrays.asList(ownerNm.split(","));
				List<String> ownerNms = familyDao.getOwnerNmByNms(nms);// 取得所有公示人的户主nm
				for (String string : ownerNms) {
					messageNoticeService.sendMessageNoticeByUser(senderNm, string, "搬迁安置人口公式结束", jsonString, "SHOW");
				}
			} else if (entity.getType().equals("6598659035")) {// 逐年补偿

			}

		}
		return new LyhtResultBody<>(entity);
	}

	/**
	 * 土地公示
	 * 
	 * @param region
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public LyhtResultBody<List<Map>> getUserList(String region) {
		List<Map> userList = dao.getUserList(region);
		return new LyhtResultBody<>(userList);
	}

	/**
	 * 个人财产 查询存在可公示人的行政区
	 */
	public LyhtResultBody<List<Map<String, Object>>> getPubRegionInfo() {
		List<Map<String, Object>> list = dao.getPubRegionInfo();
		JSONArray result = listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "cityCode", "parentCode",
				"children");
		String jsonStr = JSONObject.toJSONString(result);
		list = (List<Map<String, Object>>) JSONObject.parse(jsonStr);
		return new LyhtResultBody<>(list);
	}

	/**
	 * 
	 */
	public LyhtResultBody<List<Map<String, Object>>> getPubRegionInfoMove() {
		List<Map<String, Object>> list = dao.getPubRegionInfoMove();
		JSONArray result = listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "cityCode", "parentCode",
				"children");
		String jsonStr = JSONObject.toJSONString(result);
		list = (List<Map<String, Object>>) JSONObject.parse(jsonStr);
		return new LyhtResultBody<List<Map<String, Object>>>(list);
	}

	/**
	 * 土地分解
	 */
	public LyhtResultBody<List<Map<String, Object>>> getPubRegionInfoLand() {
		List<Map<String, Object>> list = dao.getPubRegionInfoLand();
		JSONArray result = listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "cityCode", "parentCode",
				"children");
		String jsonStr = JSONObject.toJSONString(result);
		list = (List<Map<String, Object>>) JSONObject.parse(jsonStr);
		return new LyhtResultBody<>(list);
	}

	/**
	 * 
	 * - listToTree -
	 * <p>
	 * 方法说明
	 * <p>
	 * - 将JSONArray数组转为树状结构 - @param arr 需要转化的数据 - @param id 数据唯一的标识键值 - @param pid
	 * 父id唯一标识键值 - @param child 子节点键值 - @return JSONArray
	 */
	public static JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
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

	public LyhtResultBody<List<Map<String, Object>>> getByDetailsNm(String nm) {
		return new LyhtResultBody<>(dao.getByDetailsNm(nm));
	}

	/**
	 * 查询存在搬迁安置公示的行政区
	 * 
	 * @return
	 */
	public LyhtResultBody<List<Map<String, Object>>> getPubRegionInfoRemoval() {
		List<Map<String, Object>> list = dao.getPubRegionInfoRemoval();
		JSONArray result = listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "cityCode", "parentCode",
				"children");
		String jsonStr = JSONObject.toJSONString(result);
		list = (List<Map<String, Object>>) JSONObject.parse(jsonStr);
		return new LyhtResultBody<List<Map<String, Object>>>(list);
	}

}
