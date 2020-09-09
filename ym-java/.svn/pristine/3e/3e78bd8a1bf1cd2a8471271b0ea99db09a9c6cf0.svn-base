package com.lyht.business.info.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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
import com.lyht.business.info.dao.InfoOwnerDao;
import com.lyht.business.info.entity.InfoFamilyEntity;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.vo.InfoOwnerDetailVO;
import com.lyht.business.info.vo.InfoOwnerPlaceVO;
import com.lyht.business.info.vo.InfoOwnerSelectVO;
import com.lyht.business.info.vo.InfoOwnerVO;
import com.lyht.util.Randomizer;

@Service
public class InfoOwnerService {
	private Logger log = LoggerFactory.getLogger(InfoOwnerService.class);

	@Autowired
	private InfoOwnerDao infoOwnerDao;

	/**
	 * 权属人下拉框（按阶段查询）
	 * 
	 * @param infoOwnerEntity
	 * @return
	 */
	public List<InfoOwnerSelectVO> findByParam(String param) {
		List<InfoOwnerSelectVO> list = infoOwnerDao.findByParam(param);
		return list;
	}

	/**
	 * 查询户主详情
	 * 
	 * @param nm
	 * @return
	 */
	public InfoOwnerDetailVO findOneByNm(String nm) {
		if (StringUtils.isBlank(nm)) {
			return null;
		}
		return infoOwnerDao.findOneByNm(nm);
	}

	/**
	 * 分页查询（部分字段）
	 * 
	 * @param ownerName
	 * @param scopeNm
	 * @param region
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<InfoOwnerDetailVO>> page(String ownerName, String scopeNm, String region,
			LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoOwnerDetailVO> page = infoOwnerDao.page(ownerName, scopeNm, region, pageable);
		// 结果集
		List<InfoOwnerDetailVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 分页查询（所有字段）
	 * 
	 * @param region
	 * @param scope
	 * @param name
	 * @param idCard
	 * @param isDirtyData
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<InfoOwnerVO>> page(String region, String scope, String name, String idCard,
			String isDirtyData, String nm, String placeType, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoOwnerVO> page = infoOwnerDao.page(region, scope, name, idCard, isDirtyData, nm, placeType, pageable);
		// 结果集
		List<InfoOwnerVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoOwnerEntity
	 * @return
	 */
	public InfoOwnerEntity save(InfoOwnerEntity infoOwnerEntity) {
		// 参数校验
		if (infoOwnerEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoOwnerEntity save = null;
		try {
			// 内码赋值
			String nm = infoOwnerEntity.getNm();
			if (StringUtils.isBlank(nm)) {
				infoOwnerEntity.setNm(Randomizer.generCode(10));
			}
			save = infoOwnerDao.save(infoOwnerEntity);
		} catch (Exception e) {
			log.error("=====InfoOwnerService=====Method：save=====param：" + infoOwnerEntity, e);
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
		infoOwnerDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 按权属人姓名、身份证号，模糊查询安置信息(实施)
	 * 
	 * @param name
	 * @param idCard
	 * @return
	 */
	public List<InfoOwnerPlaceVO> findOwnerPlace(String name, String idCard) {
		List<InfoOwnerPlaceVO> findOwnerPlace = infoOwnerDao.findOwnerPlace(name, idCard);
		return findOwnerPlace;
	}

	/**
	 * 查询权属人(姓名、行政区域)(实施)
	 * 
	 * @param region
	 * @return
	 */
	public List<InfoOwnerDetailVO> findAllByNameAndRegion(String name, String region) {
		List<InfoOwnerDetailVO> findAllByNameAndRegion = infoOwnerDao.findAllByNameAndRegion(name, region);
		return findAllByNameAndRegion;
	}
	
	@Scheduled(cron = "0 0 1 * * *")
	@Transactional
	public void updateAge() {
		updateAge15();
		updateAge18();
	}
	
	@Transactional
	public void updateAge15() {
		List<InfoOwnerEntity> list = infoOwnerDao.findIdCardLength15();
		for (InfoOwnerEntity entity : list) {//身份证十五位数
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
			infoOwnerDao.updateAge(age, entity.getId());
		}
	}
	
	@Transactional
	public void updateAge18() {
		List<InfoOwnerEntity> entitys = infoOwnerDao.findIdCardLength18();
		for (InfoOwnerEntity entity : entitys) {//身份证十八位数
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
			infoOwnerDao.updateAge(age, entity.getId());
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
