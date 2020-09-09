package com.lyht.business.abm.removal.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.dao.PublicityDao;
import com.lyht.business.abm.removal.dao.AbmBuildingDao;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.AbmHousesDao;
import com.lyht.business.abm.removal.dao.AbmHousesDecorationDao;
import com.lyht.business.abm.removal.dao.AbmLandDao;
import com.lyht.business.abm.removal.dao.AbmTreesDao;
import com.lyht.business.abm.removal.entity.AbmBuildingEntity;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmHouseEntity;
import com.lyht.business.abm.removal.entity.AbmHousesDecorationEntity;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.abm.removal.entity.AbmTreesEntity;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.system.dao.SysLogDao;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

@Transactional
@Service
public class AbmFamilyService {
	private Logger log = LoggerFactory.getLogger(AbmFamilyService.class);

	@Autowired
	AbmFamilyDao abmFamilyDao;

	@Autowired
	SysLogDao logDao;

	@Autowired
	AbmHousesDecorationDao zxDao;

	@Autowired
	AbmHousesDao houseDao;

	@Autowired
	AbmBuildingDao subsidiaryDao;

	@Autowired
	AbmTreesDao treeDao;

	@Autowired
	AbmLandDao tudiDao;

	@Autowired
	PublicityDao publicityDaoao;

	public LyhtResultBody<AbmFamilyEntity> queryByIdCard(String idCard, Integer id) {
		LyhtResultBody<AbmFamilyEntity> resultBody = new LyhtResultBody<AbmFamilyEntity>();
		AbmFamilyEntity abmFamilyEntity = abmFamilyDao.queryByIdCard(idCard);
		if (!CommonUtil.isEmpty(id)) {// 如果是修改 则判断传入的身份证和id 查询到的数据是否一致
			AbmFamilyEntity abmFamily = abmFamilyDao.getOne(id);
			if (abmFamilyEntity != null) {
				if (abmFamilyEntity.getId() == abmFamily.getId()) {
					return resultBody;
				}
			}
		} else {// 新增
			if (abmFamilyEntity != null) {
				resultBody.setMsg("身份证已存在");
				resultBody.setFlag(false);
			}
		}
		return resultBody;
	}

	public void updateJieding(Integer id, String defind, String isSatisfy, String xiang, String cun, String zu,
			String placeType, String placeName, String toWhere) {
		abmFamilyDao.updateJieding(id, defind, isSatisfy, xiang, cun, zu, placeType, placeName, toWhere);
	}

	public void updateOwner(String nm, String isSatisfy, String xiang, String cun, String zu, String placeType,
			String placeName) {
		abmFamilyDao.updateOwner(nm, isSatisfy, xiang, cun, zu, placeType, placeName);
		;
	}

	/**
	 * 新增、修改
	 * 
	 * @param familyEntity
	 * @return
	 */
	public LyhtResultBody<AbmFamilyEntity> save(AbmFamilyEntity familyEntity) {
		AbmFamilyEntity resultSet = null;
		try {
			if (familyEntity.getId() == null) {
				// 新增的时候判断
				familyEntity.setNm(Randomizer.generCode(10));
			}
			resultSet = abmFamilyDao.save(familyEntity);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=save=====Params:" + familyEntity + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(resultSet);
	}

	/**
	 * 按身份证号与户主nm查询家庭成员
	 * 
	 * @param ownerNm
	 * @param idCard
	 * @return
	 */
	public AbmFamilyEntity findByOwnerNmAndIdCard(String ownerNm, String idCard) {
		AbmFamilyEntity abmFamilyEntity = abmFamilyDao.findByOwnerNmAndIdCard(ownerNm, idCard);
		return abmFamilyEntity;
	}

	public LyhtResultBody<AbmFamilyEntity> saveAnZhi(AbmFamilyEntity familyEntity, HttpServletRequest request) {
		AbmFamilyEntity resultSet = null;
		try {
			familyEntity.setStage("B8F4137189");// 实施阶段
			if (familyEntity.getId() == null) {

				familyEntity.setIsSatisfy("2");// 符合
				familyEntity.setNm(Randomizer.generCode(10));
			} else {

			}
			resultSet = abmFamilyDao.save(familyEntity);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=save=====Params:" + familyEntity + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(resultSet);
	}

	public LyhtResultBody<Integer> delete(Integer id) {
		try {
			abmFamilyDao.updateState("1", id);
//            AbmFamilyEntity resultSet=abmFamilyDao.getOne(id);
//            abmFamilyDao.delete(resultSet);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(id);

	}

	public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, String ownerNm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getList(pageable, ownerNm);
		List<Map> list = new ArrayList<>();
		list.addAll(page.getContent());
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	public LyhtResultBody<List<Map>> getJiaTi(LyhtPageVO lyhtPageVO, String region, String name) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getJiaTi(pageable, region, name);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	public LyhtResultBody<List<Map>> getFSList(LyhtPageVO lyhtPageVO, String ownerNm, String region, String projectNm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getFSList(pageable, ownerNm, region, projectNm);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	public LyhtResultBody<List<Map>> getQMList(LyhtPageVO lyhtPageVO, String ownerNm, String region, String projectNm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getQMList(pageable, ownerNm, region, projectNm);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	public LyhtResultBody<List<Map>> getZXList(LyhtPageVO lyhtPageVO, String ownerNm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getZXList(pageable, ownerNm);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	public LyhtResultBody<Map> getOwnerInfo(String ownerNm) {
		Map map = abmFamilyDao.getOwnerInfo(ownerNm);
		return new LyhtResultBody<>(map);
	}

	public List<Map> getOwnerNm(String taskId) {
		return abmFamilyDao.getOwnerNm(taskId);
	}

	public LyhtResultBody<List<Map>> getLandHouse(LyhtPageVO lyhtPageVO, String ownerNm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getLandHouse(pageable, ownerNm);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	public LyhtResultBody<List<Map>> getTudi(LyhtPageVO lyhtPageVO, String ownerNm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getTudi(pageable, ownerNm);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 实物指标权属人列表
	 * 
	 * @param lyhtPageVO
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<Map>> getOwnerList(LyhtPageVO lyhtPageVO, String region, String scope, String name,
			String stage, String processId, String nm, String idCard) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		boolean flag = false;
		List<String> str = new ArrayList<>();
		if (!"".equals(nm) && null != nm) {
			Page<Map> page1 = publicityDaoao.getListDetails(pageable, nm);
			List<Map> list1 = page1.getContent();
			if (list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					str.add(list1.get(i).get("nm").toString());
				}
				flag = true;
			} else {
				str.add("99999");
			}
		} else {
			str.add("99999");
		}
		Page<Map> page = abmFamilyDao.getOwnerList(pageable, region, scope, name, stage, processId, str, idCard);
		List<Map> list = page.getContent();

		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	public LyhtResultBody<List<Map>> getOwnerLists(LyhtPageVO lyhtPageVO, String region, String scope, String name,
			String stage) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<Map> page = abmFamilyDao.getOwnerLists(pageable, region, scope, name, stage);
		List<Map> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);

	}

	/////////////////////////////////// 房屋
	public LyhtResultBody<AbmHouseEntity> save(AbmHouseEntity entity) {
		AbmHouseEntity resultSet = null;
		try {
			if (entity.getId() == null) {
				entity.setNm(Randomizer.generCode(10));

			}
			entity.setStage("B8F4137189");// 实施阶段
			resultSet = houseDao.save(entity);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=save=====Params:" + entity + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(resultSet);
	}

	public LyhtResultBody<Integer> deleteHouse(Integer id) {
		try {
			AbmHouseEntity resultSet = houseDao.getOne(id);
			houseDao.delete(resultSet);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(id);

	}

	/////////////////////////////////// 土地
	public LyhtResultBody<AbmLandEntity> save(AbmLandEntity entity) {
		AbmLandEntity resultSet = null;
		try {
			if (entity.getId() == null) {
				entity.setNm(Randomizer.generCode(10));
			}
			entity.setStage("B8F4137189");// 实施阶段
			resultSet = tudiDao.save(entity);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=save=====Params:" + entity + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(resultSet);
	}

	public LyhtResultBody<Integer> deleteTudi(Integer id) {
		try {
			AbmLandEntity resultSet = tudiDao.getOne(id);
			tudiDao.delete(resultSet);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(id);

	}

	/////////////////////////////////// 房屋装修
	public LyhtResultBody<AbmHousesDecorationEntity> save(AbmHousesDecorationEntity entity) {
		AbmHousesDecorationEntity resultSet = null;
		try {
			if (entity.getId() == null) {
				entity.setNm(Randomizer.generCode(10));
			}
			entity.setStage("B8F4137189");// 实施阶段
			resultSet = zxDao.save(entity);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=save=====Params:" + entity + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(resultSet);
	}

	public LyhtResultBody<Integer> deleteZx(Integer id) {
		try {
			AbmHousesDecorationEntity resultSet = zxDao.getOne(id);
			zxDao.delete(resultSet);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(id);

	}

	/////////////////////////////////// 树木
	public LyhtResultBody<AbmTreesEntity> save(AbmTreesEntity entity) {
		AbmTreesEntity resultSet = null;
		try {
			if (entity.getId() == null) {
				entity.setNm(Randomizer.generCode(10));
			}
			entity.setStage("B8F4137189");// 实施阶段
			resultSet = treeDao.save(entity);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=save=====Params:" + entity + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(resultSet);
	}

	public LyhtResultBody<Integer> deleteTree(Integer id) {
		try {
			AbmTreesEntity resultSet = treeDao.getOne(id);
			treeDao.delete(resultSet);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(id);

	}

	/////////////////////////////////// 附属建筑
	public LyhtResultBody<AbmBuildingEntity> save(AbmBuildingEntity entity) {
		AbmBuildingEntity resultSet = null;
		try {
			if (entity.getId() == null) {
				entity.setNm(Randomizer.generCode(10));
			}
			entity.setStage("B8F4137189");// 实施阶段
			resultSet = subsidiaryDao.save(entity);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=save=====Params:" + entity + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(resultSet);
	}

	public LyhtResultBody<Integer> deleteSubsidiary(Integer id) {
		try {
			AbmBuildingEntity resultSet = subsidiaryDao.getOne(id);
			subsidiaryDao.delete(resultSet);
		} catch (Exception e) {
			log.error("=====AbmFamilyService=====Method=delete=====Params:" + id + "=====", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody(id);

	}

	public LyhtResultBody<List<Map>> getOwnerAllList(String region) {
		List<Map> ownerAllList = abmFamilyDao.getOwnerAllList(region);
		return new LyhtResultBody<>(ownerAllList);

	}

	public AbmFamilyEntity getOwnerDetails(String ownerNm) {
		AbmFamilyEntity map = abmFamilyDao.getOwnerDetails(ownerNm);
		return map;
	}

	/**
	 * 通过户主nm查询家庭成员
	 * 
	 * @param ownerNm
	 * @return
	 */
	public List<AbmFamilyEntity> findByOwnerNm(String ownerNm) {
		return JSON.parseArray(JSON.toJSONString(abmFamilyDao.getByOwnerNm(ownerNm)), AbmFamilyEntity.class);
	}

	/**
	 * 批量新增，修改
	 * 
	 * @param familyList
	 * @return
	 */
	public List<AbmFamilyEntity> saveAll(List<AbmFamilyEntity> familyList) {
		List<AbmFamilyEntity> saveAll = abmFamilyDao.saveAll(familyList);
		return saveAll;
	}

	/**
	 * 凌晨一点更新家庭成员年龄信息
	 */
	@Scheduled(cron = "0 0 1 * * *")
	@Transactional
	public void updateAge() {
		updateAge15();
		updateAge18();
	}
	
	@Transactional
	public void updateAge15() {
		List<AbmFamilyEntity> list = abmFamilyDao.findIdCardLength15();
		for (AbmFamilyEntity entity : list) {//身份证十五位数
			String idCard = entity.getIdCard();
			String start = "19"+idCard.substring(6, 12);
			Boolean bool = true;
			Integer age = 0;
			for (int i = start.length(); --i >= 0;) {// 过滤字符串 是否为纯数字
				if (!Character.isDigit(start.charAt(i))) {
					bool = false;
				}
			}
			if (bool) {
				age = getAge(start);
			}
			abmFamilyDao.updateAge(age, entity.getId());
		}
	}
	
	@Transactional
	public void updateAge18() {
		List<AbmFamilyEntity> entitys = abmFamilyDao.findIdCardLength18();
		for (AbmFamilyEntity entity : entitys) {//身份证十八位数
			String idCard = entity.getIdCard();
			String start = idCard.substring(6, 14);
			Boolean bool = true;
			Integer age = 0;
			for (int i = start.length(); --i >= 0;) {// 过滤字符串 是否为纯数字
				if (!Character.isDigit(start.charAt(i))) {
					bool = false;
				}
			}
			if (bool) {
				age = getAge(start);
			}
			abmFamilyDao.updateAge(age, entity.getId());
		}
	}

	public Integer getAge(String start) {
		Integer age = 0;
		Date birthday = null;
		try {
			birthday = new SimpleDateFormat("yyyyMMdd").parse(start);
			String birth = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String today = sdf.format(date);
			Calendar bef = Calendar.getInstance();
			Calendar aft = Calendar.getInstance();
			bef.setTime(sdf.parse(birth));
			aft.setTime(sdf.parse(today));
			age = aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			log.error("更新年龄失败", e);
			e.printStackTrace();
		}
		return age;
	}


}
