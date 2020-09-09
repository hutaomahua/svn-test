package com.lyht.business.info.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
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
import com.lyht.business.info.dao.InfoFamilyDao;
import com.lyht.business.info.entity.InfoFamilyEntity;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.vo.InfoFamilyVO;
import com.lyht.util.Randomizer;

@Service
public class InfoFamilyService {
	private Logger log = LoggerFactory.getLogger(InfoFamilyService.class);

	@Autowired
	private InfoFamilyDao infoFamilyDao;

	/**
	 * 分页查询（所有字段）
	 * 
	 * @param region
	 * @param scope
	 * @param name
	 * @param idCard
	 * @param isDirtyData
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<InfoFamilyVO>> page(String region, String scope, String name, String idCard,
			String isDirtyData, String ownerNm, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoFamilyVO> page = infoFamilyDao.page(region, scope, name, idCard, isDirtyData, ownerNm, pageable);
		// 结果集
		List<InfoFamilyVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 条件查询
	 * 
	 * @param region
	 * @param ownerName
	 * @param scope
	 * @param name
	 * @param idCard
	 * @param isDirtyData
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<InfoFamilyVO>> list(String region, String scope, String name, String idCard,
			String isDirtyData, String ownerNm) {
		List<InfoFamilyVO> list = infoFamilyDao.list(region, scope, name, idCard, isDirtyData, ownerNm);
		return new LyhtResultBody<>(list);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoFamilyEntity
	 * @return
	 */
	public InfoFamilyEntity save(InfoFamilyEntity infoFamilyEntity) {
		// 参数校验
		if (infoFamilyEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoFamilyEntity save = null;
		try {
			// 内码赋值
			String nm = infoFamilyEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoFamilyEntity.setNm(Randomizer.generCode(10));
			}
			save = infoFamilyDao.save(infoFamilyEntity);
		} catch (Exception e) {
			log.error("=====InfoFamilyService=====Method：save=====param：" + infoFamilyEntity, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return save;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public LyhtResultBody<Integer> delete(Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		infoFamilyDao.deleteById(id);
		return new LyhtResultBody<>(id);
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
		List<InfoFamilyEntity> list = infoFamilyDao.findIdCardLength15();
		List<InfoFamilyEntity> result = new ArrayList<InfoFamilyEntity>();
		for (InfoFamilyEntity entity : list) {//身份证十五位数
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
			infoFamilyDao.updateAge(age, entity.getId());
		}
	}
	
	@Transactional
	public void updateAge18() {
		List<InfoFamilyEntity> entitys = infoFamilyDao.findIdCardLength18();
		for (InfoFamilyEntity entity : entitys) {//身份证十八位数
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
			infoFamilyDao.updateAge(age, entity.getId());
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

	public AbmOwnerEntity queryOwnerInfoByNm(String ownerNm){
		Map map = infoFamilyDao.queryOwnerInfoByNm(ownerNm);
		String str = JSON.toJSONString(map);
		return JSON.parseObject(str, AbmOwnerEntity.class);
	}

}
