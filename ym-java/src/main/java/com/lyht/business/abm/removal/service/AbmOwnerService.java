package com.lyht.business.abm.removal.service;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.util.Randomizer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

@Transactional
@Service
public class AbmOwnerService {
	private Logger log = LoggerFactory.getLogger(AbmOwnerService.class);

	@Autowired
	private AbmOwnerDao abmOwnerDao;

	/**
	 * 查询对应身份证号的户主
	 * 
	 * @param idCard
	 * @return
	 */
	public AbmOwnerEntity findByIdCard(String idCard) {
		AbmOwnerEntity abmOwnerEntity = abmOwnerDao.findByIdCard(idCard);
		return abmOwnerEntity;
	}

	/**
	 * 查询对应身份证号的户主是否存在
	 * 
	 * @param idCard
	 * @return
	 */
	public int countByIdCard(String idCard) {
		int countByIdCard = abmOwnerDao.countByIdCard(idCard);
		return countByIdCard;
	}

	/**
	 * 查询对应户主编码的户主是否存在
	 * 
	 * @param ownerNm
	 * @return
	 */
	public int countByOwnerNm(String ownerNm) {
		int countByOwnerNm = abmOwnerDao.countByOwnerNm(ownerNm);
		return countByOwnerNm;
	}

	/**
	 * 新增，修改
	 * 
	 * @param abmOwnerEntity
	 * @return
	 */
	public AbmOwnerEntity save(AbmOwnerEntity abmOwnerEntity) {
		// 参数校验
		if (abmOwnerEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		AbmOwnerEntity save = null;
		try {
			// 内码赋值
			String nm = abmOwnerEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				abmOwnerEntity.setNm(Randomizer.generCode(10));
			}
			save = abmOwnerDao.save(abmOwnerEntity);
		} catch (Exception e) {
			log.error("=====AbmOwnerService=====Method：save=====param：" + abmOwnerEntity, e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return save;
	}

	/**
	 * 通过nm查询户主
	 * 
	 * @param nm
	 * @return
	 */
	public AbmOwnerEntity findByNm(String nm) {
		AbmOwnerEntity findByNm = abmOwnerDao.findByNm(nm);
		return findByNm;
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
		List<AbmOwnerEntity> list = abmOwnerDao.findIdCardLength15();
		for (AbmOwnerEntity entity : list) {// 身份证十五位数
			String idCard = entity.getIdCard();
			String start = "19" + idCard.substring(6, 12);
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
			abmOwnerDao.updateAge(age, entity.getId());
		}
	}

	@Transactional
	public void updateAge18() {
		List<AbmOwnerEntity> entitys = abmOwnerDao.findIdCardLength18();
		for (AbmOwnerEntity entity : entitys) {// 身份证十八位数
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
			abmOwnerDao.updateAge(age, entity.getId());
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
