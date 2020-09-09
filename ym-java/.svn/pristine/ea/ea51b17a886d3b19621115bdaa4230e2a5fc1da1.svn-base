package com.lyht.business.info.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.removal.service.AbmFamilyService;
import com.lyht.business.abm.removal.service.AbmOwnerService;
import com.lyht.business.info.common.constant.InfoConstant;
import com.lyht.business.info.dao.InfoHouseholdRegisterDao;
import com.lyht.business.info.entity.InfoHouseholdRegisterEntity;
import com.lyht.business.info.vo.InfoHouseholdRegisterVO;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.service.PubDictValueService;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service
public class InfoHouseholdRegisterService {
	private Logger log = LoggerFactory.getLogger(InfoHouseholdRegisterService.class);

	@Autowired
	private PubDictValueService pubDictValueService;

	@Autowired
	private AbmFamilyService abmFamilyService;

	@Autowired
	private AbmOwnerService abmOwnerService;

	@Autowired
	private InfoHouseholdRegisterDao infoHouseholdRegisterDao;

	public LyhtResultBody<List<InfoHouseholdRegisterVO>> page(String region, String name, String idCard,
			String accountCharacter, String accountType, String livingCondition, String masterRelationship,
			String country, String village, String groups, LyhtPageVO lyhtPageVO) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<InfoHouseholdRegisterVO> page = infoHouseholdRegisterDao.page(region, name, idCard, accountCharacter,
				accountType, livingCondition, masterRelationship, country, village, groups, pageable);
		// 结果集
		List<InfoHouseholdRegisterVO> content = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(content, pageVO);
	}

	/**
	 * 新增，修改
	 * 
	 * @param infoHouseholdRegisterEntity
	 * @return
	 */
	public InfoHouseholdRegisterEntity save(InfoHouseholdRegisterEntity infoHouseholdRegisterEntity) {
		// 参数校验
		if (infoHouseholdRegisterEntity == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		InfoHouseholdRegisterEntity save = null;
		try {
			save = infoHouseholdRegisterDao.save(infoHouseholdRegisterEntity);
		} catch (Exception e) {
			log.error("=====InfoHouseholdRegisterService=====Method：save=====param：" + infoHouseholdRegisterEntity, e);
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
	public LyhtResultBody<String> delete(String id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		infoHouseholdRegisterDao.deleteById(id);
		return new LyhtResultBody<>(id);
	}

	/**
	 * 导入excel
	 * 
	 * @param multipartFile
	 * @return
	 */
	public String importExcel(MultipartFile multipartFile) {
		int row = 0;
		try {
			InputStream inputStream = multipartFile.getInputStream();
			ExcelReader reader = ExcelUtil.getReader(inputStream);

			// 文件格式校验
			List<Object> header = reader.readRow(1);// 获取表头
			boolean checkExcel = checkExcel(header);
			if (!checkExcel) {
				throw new LyhtRuntimeException(
						"导入格式错误！表头为第二行与第三行合并行，数据从第四行开始！（表头为“序号”、“村”、“组”、“户主姓名”、“家庭成员”、“与户主关系”、“性别”、“出生年月”、“身份证号”、“户口性质”、“户口类别”、“民族”、“受教育程度”、“”、“”、“居住情况”、“”、“本户地址”）");
			}

			List<List<Object>> rowList = reader.read(3);// 从第三行读取excel

			Map<String, PubDictValue> dictNodeTypeMap = pubDictValueService.getDictMap("dict_node_type");// 户口性质
			Map<String, PubDictValue> dicHouseholdRegistrationTypeMap = pubDictValueService
					.getDictMap("dic_household_registration_type");// 户口类别
			Map<String, PubDictValue> dictEthnicMap = pubDictValueService.getDictMap("dict_ethnic");// 民族
			Map<String, PubDictValue> dictEducationMap = pubDictValueService.getDictMap("dict_education");// 受教育程度
			Map<String, PubDictValue> dictOwnerRelationMap = pubDictValueService.getDictMap("dict_owner_relation");// 与户主关系

			String ownerId = null;

			for (int i = 0; i < rowList.size(); i++) {
				row++;
				List<Object> collist = rowList.get(i);
				InfoHouseholdRegisterEntity infoHouseholdRegisterEntity = new InfoHouseholdRegisterEntity();
				Object country = collist.get(1);// 乡
				infoHouseholdRegisterEntity.setCountry(country != null ? String.valueOf(country).trim() : null);

				Object village = collist.get(2);// 村
				infoHouseholdRegisterEntity.setVillage(village != null ? String.valueOf(village).trim() : null);

				Object groups = collist.get(3);// 组
				infoHouseholdRegisterEntity.setGroups(groups != null ? String.valueOf(groups).trim() : null);

				Object name = collist.get(5);// 姓名
				infoHouseholdRegisterEntity.setName(name != null ? String.valueOf(name).trim() : null);

				Object gender = collist.get(7);// 性别
				infoHouseholdRegisterEntity.setGender(gender != null ? String.valueOf(gender).trim() : null);

				Object birthday = collist.get(8);// 出生年月
				infoHouseholdRegisterEntity.setBirthday(birthday != null ? (Date) birthday : null);

				Object idCard = collist.get(9);// 身份证号
				String idCardString = idCard != null ? String.valueOf(idCard).replace("*", "").trim() : null;
				infoHouseholdRegisterEntity.setIdCard(idCardString);

				Object accountCharacter = collist.get(10);// 户口性质
				PubDictValue accountCharacterDict = dictNodeTypeMap
						.get(accountCharacter != null ? String.valueOf(accountCharacter).trim() : null);
				if (accountCharacterDict != null) {
					String accountCharacterString = accountCharacterDict.getNm();
					infoHouseholdRegisterEntity.setAccountCharacter(accountCharacterString);
				}

				Object accountType = collist.get(11);// 户口类别
				PubDictValue accountTypeDict = dicHouseholdRegistrationTypeMap
						.get(accountType != null ? String.valueOf(accountType).trim() : null);
				if (accountTypeDict != null) {
					String accountTypeString = accountTypeDict.getNm();
					infoHouseholdRegisterEntity.setAccountType(accountTypeString);
				}

				Object national = collist.get(12);// 民族
				PubDictValue nationalDict = dictEthnicMap
						.get(national != null ? String.valueOf(national).trim() : null);
				if (nationalDict != null) {
					String nationalString = nationalDict.getNm();
					infoHouseholdRegisterEntity.setNational(nationalString);
				}

				Object educationLevel = collist.get(13);// 受教育程度
				PubDictValue educationLevelDict = dictEducationMap
						.get(educationLevel != null ? String.valueOf(educationLevel).trim() : null);
				if (educationLevel != null) {
					String educationLevelString = educationLevelDict.getNm();
					infoHouseholdRegisterEntity.setEducationLevel(educationLevelString);
				}

				Object isLiving = collist.get(16);// 居住情况（居住）
				String isLivingString = isLiving != null ? String.valueOf(isLiving).trim() : null;
				if (StringUtils.equals(isLivingString, "居住")) {
					infoHouseholdRegisterEntity.setLivingCondition("1");
				} else {
					infoHouseholdRegisterEntity.setLivingCondition("2");
				}

				Object homeAddress = collist.get(18);// 本户地址
				infoHouseholdRegisterEntity
						.setHomeAddress(homeAddress != null ? String.valueOf(homeAddress).trim() : null);

				Object masterRelationship = collist.get(6);// 与户主关系
				String masterRelationshipStr = masterRelationship != null ? String.valueOf(masterRelationship).trim()
						: null;
				PubDictValue pubDictValue = dictOwnerRelationMap.get(masterRelationshipStr);
				if (pubDictValue != null) {
					String masterRelationshipString = pubDictValue.getNm();
					infoHouseholdRegisterEntity.setMasterRelationship(masterRelationshipString);
				}

				if (StringUtils.equals(masterRelationshipStr, "户主")) {
					infoHouseholdRegisterEntity.setOwnerId(null);
					InfoHouseholdRegisterEntity save = infoHouseholdRegisterDao.save(infoHouseholdRegisterEntity);
					String id = save.getId();
					ownerId = id;
				} else {
					infoHouseholdRegisterEntity.setOwnerId(ownerId);
					infoHouseholdRegisterDao.save(infoHouseholdRegisterEntity);
				}

			}
		} catch (LyhtRuntimeException e) {
			throw e;
		} catch (Exception e) {
			log.error("=====InfoHouseholdRegisterService=====Method：save=====导入失败=====", e);
			throw new LyhtRuntimeException("导入失败，第" + row + "行数据解析失败！");
		}
		return "成功";
	}

	private boolean checkExcel(List<Object> header) {
		if (CollectionUtils.isEmpty(header)) {
			return false;
		}
		Object headerCountry = header.get(1);// 乡
		if (headerCountry == null || !StringUtils.contains(headerCountry.toString(), "乡")) {
			return false;
		}
		Object headerVillage = header.get(2);// 村
		if (headerVillage == null || !StringUtils.contains(headerVillage.toString(), "村")) {
			return false;
		}
		Object headerGroups = header.get(3);// 组
		if (headerGroups == null || !StringUtils.contains(headerGroups.toString(), "组")) {
			return false;
		}
		Object headerOwnerName = header.get(4);// 户主姓名
		if (headerOwnerName == null || !StringUtils.contains(headerOwnerName.toString(), "户主姓名")) {
			return false;
		}
		Object headerName = header.get(5);// 家庭成员
		if (headerName == null || !StringUtils.contains(headerName.toString(), "家庭成员")) {
			return false;
		}
		Object headerMasterRelationship = header.get(6);// 与户主关系
		if (headerMasterRelationship == null || !StringUtils.contains(headerMasterRelationship.toString(), "与户主关系")) {
			return false;
		}
		Object headerGender = header.get(7);// 性别
		if (headerGender == null || !StringUtils.contains(headerGender.toString(), "性别")) {
			return false;
		}
		Object headerBirthday = header.get(8);// 出生年月
		if (headerBirthday == null || !StringUtils.contains(headerBirthday.toString(), "出生年月")) {
			return false;
		}
		Object headerIdCard = header.get(9);// 身份证号
		if (headerIdCard == null || !StringUtils.contains(headerIdCard.toString(), "身份证号")) {
			return false;
		}
		Object headerAccountCharacter = header.get(10);// 户口性质
		if (headerAccountCharacter == null || !StringUtils.contains(headerAccountCharacter.toString(), "户口性质")) {
			return false;
		}
		Object headerAccountType = header.get(11);// 户口类别
		if (headerAccountType == null || !StringUtils.contains(headerAccountType.toString(), "户口类别")) {
			return false;
		}
		Object headerNational = header.get(12);// 民族
		if (headerNational == null || !StringUtils.contains(headerNational.toString(), "民族")) {
			return false;
		}
		Object headerEducationLevel = header.get(13);// 受教育程度
		if (headerEducationLevel == null || !StringUtils.contains(headerEducationLevel.toString(), "受教育程度")) {
			return false;
		}
		Object headerIsLiving = header.get(16);// 居住情况
		if (headerIsLiving == null || !StringUtils.contains(headerIsLiving.toString(), "居住情况")) {
			return false;
		}
		Object headerHomeAddress = header.get(18);// 本户地址
		if (headerHomeAddress == null || !StringUtils.contains(headerHomeAddress.toString(), "本户地址")) {
			return false;
		}
		return true;
	}

	/**
	 * 拉取户籍信息，同步到实施部分-户主与家庭成员
	 * 
	 * @param ownerId
	 * @return
	 */
	@Transactional(rollbackOn = Exception.class)
	public void syncOwner(String ownerId, String region) {
		if (StringUtils.isBlank(region)) {
			throw new LyhtRuntimeException("请选择行政区");
		}
		// 户主
		Optional<InfoHouseholdRegisterEntity> findById = infoHouseholdRegisterDao.findById(ownerId);
		if (!findById.isPresent()) {
			throw new LyhtRuntimeException("户籍不存在，请重试！");
		}
		InfoHouseholdRegisterEntity owner = findById.get();
		String ownerIdCard = owner.getIdCard();// 身份证号
//		String ownerAccountCharacter = owner.getAccountCharacter();//户口性质
		String ownerAccountType = owner.getAccountType();// 户口类别
		Integer ownerAge = owner.getAge();// 年龄
//		Date ownerBirthday = owner.getBirthday();//出生日期
		String ownerEducationLevel = owner.getEducationLevel();// 文化程度
		String ownerGender = owner.getGender();// 性别
		String ownerHomeAddress = owner.getHomeAddress();// 户口所在地
//		String ownerLivingCondition = owner.getLivingCondition();//居住情况（1.居住；2.未居住）
		String ownerMasterRelationship = owner.getMasterRelationship();// 与户主关系
		String ownerName = owner.getName();// 姓名
		String ownerNational = owner.getNational();// 民族
		String ownerRemark = owner.getRemark();// 备注
		String ownerStatus = owner.getStatus();// 状态

		AbmOwnerEntity findByIdCard = abmOwnerService.findByIdCard(ownerIdCard);
		// 如果不存在则新增户主,否则修改户主信息
		if (findByIdCard == null) {
			AbmOwnerEntity abmOwnerEntity = new AbmOwnerEntity();
			abmOwnerEntity.setIdCard(ownerIdCard);
			abmOwnerEntity.setHouseholdsType(ownerAccountType);
			abmOwnerEntity.setAge(ownerAge);
			abmOwnerEntity.setEducationLevel(ownerEducationLevel);
			abmOwnerEntity.setGender(ownerGender);
			abmOwnerEntity.setHouseholdsHome(ownerHomeAddress);
			abmOwnerEntity.setName(ownerName);
			abmOwnerEntity.setNational(ownerNational);
			abmOwnerEntity.setRegion(region);
			abmOwnerEntity.setRemark(ownerRemark);
			abmOwnerEntity.setStatus(ownerStatus);
			findByIdCard = abmOwnerService.save(abmOwnerEntity);
		} else {
			findByIdCard.setIdCard(ownerIdCard);
			findByIdCard.setHouseholdsType(ownerAccountType);
			findByIdCard.setAge(ownerAge);
			findByIdCard.setEducationLevel(ownerEducationLevel);
			findByIdCard.setGender(ownerGender);
			findByIdCard.setHouseholdsHome(ownerHomeAddress);
			findByIdCard.setName(ownerName);
			findByIdCard.setNational(ownerNational);
			findByIdCard.setRegion(region);
			findByIdCard.setRemark(ownerRemark);
			findByIdCard.setStatus(ownerStatus);
			findByIdCard = abmOwnerService.save(findByIdCard);
		}

		String ownerNm = findByIdCard.getNm();

		// 家庭成员
		List<InfoHouseholdRegisterEntity> family = infoHouseholdRegisterDao.findByOwnerId(ownerId);
		for (InfoHouseholdRegisterEntity infoHouseholdRegisterEntity : family) {
//			String accountCharacter = infoHouseholdRegisterEntity.getAccountCharacter();//户口性质
			String accountType = infoHouseholdRegisterEntity.getAccountType();// 户口类别
			Integer age = infoHouseholdRegisterEntity.getAge();// 年龄
//			Date birthday = infoHouseholdRegisterEntity.getBirthday();//出生日期
			String educationLevel = infoHouseholdRegisterEntity.getEducationLevel();// 文化程度
			String gender = infoHouseholdRegisterEntity.getGender();// 性别
			String homeAddress = infoHouseholdRegisterEntity.getHomeAddress();// 户口所在地
			String idCard = infoHouseholdRegisterEntity.getIdCard();// 身份证号
//			String livingCondition = infoHouseholdRegisterEntity.getLivingCondition();//居住情况（1.居住；2.未居住）
			String masterRelationship = infoHouseholdRegisterEntity.getMasterRelationship();// 与户主关系
			String name = infoHouseholdRegisterEntity.getName();// 姓名
			String national = infoHouseholdRegisterEntity.getNational();// 民族
			String remark = infoHouseholdRegisterEntity.getRemark();// 备注
			String status = infoHouseholdRegisterEntity.getStatus();// 状态

			AbmFamilyEntity findByOwnerNmAndIdCard = abmFamilyService.findByOwnerNmAndIdCard(ownerNm, idCard);
			// 如果不存在新增家庭成员，否则修改家庭成员信息
			if (findByOwnerNmAndIdCard == null) {
				AbmFamilyEntity abmFamilyEntity = new AbmFamilyEntity();
				abmFamilyEntity.setOwnerNm(ownerNm);
				abmFamilyEntity.setMasterRelationship(masterRelationship);
				abmFamilyEntity.setIdCard(idCard);
				abmFamilyEntity.setHouseholdsType(accountType);
				abmFamilyEntity.setAge(age);
				abmFamilyEntity.setEducationLevel(educationLevel);
				abmFamilyEntity.setGender(gender);
				abmFamilyEntity.setHouseholdsHome(homeAddress);
				abmFamilyEntity.setName(name);
				abmFamilyEntity.setNational(national);
				abmFamilyEntity.setRegion(region);
				abmFamilyEntity.setRemark(remark);
				abmFamilyEntity.setStatus(status);
				abmFamilyService.save(abmFamilyEntity);
			} else {
				findByOwnerNmAndIdCard.setOwnerNm(ownerNm);
				findByOwnerNmAndIdCard.setMasterRelationship(masterRelationship);
				findByOwnerNmAndIdCard.setIdCard(idCard);
				findByOwnerNmAndIdCard.setHouseholdsType(accountType);
				findByOwnerNmAndIdCard.setAge(age);
				findByOwnerNmAndIdCard.setEducationLevel(educationLevel);
				findByOwnerNmAndIdCard.setGender(gender);
				findByOwnerNmAndIdCard.setHouseholdsHome(homeAddress);
				findByOwnerNmAndIdCard.setName(name);
				findByOwnerNmAndIdCard.setNational(national);
				findByOwnerNmAndIdCard.setRegion(region);
				findByOwnerNmAndIdCard.setRemark(remark);
				findByOwnerNmAndIdCard.setStatus(status);
				abmFamilyService.save(findByOwnerNmAndIdCard);
			}
			infoHouseholdRegisterEntity.setStatus(InfoConstant.HOUSEHOLD_REGISTER_SYNCHRONIZED);
			infoHouseholdRegisterDao.save(infoHouseholdRegisterEntity);
		}

		AbmFamilyEntity findByOwnerNmAndIdCard = abmFamilyService.findByOwnerNmAndIdCard(ownerNm, ownerIdCard);
		// 如果当前户主不存在与家庭成员中，新增家庭成员；否则，修改对应信息
		if (findByOwnerNmAndIdCard == null) {
			AbmFamilyEntity abmFamilyEntity = new AbmFamilyEntity();
			abmFamilyEntity.setOwnerNm(ownerNm);
			abmFamilyEntity.setMasterRelationship(ownerMasterRelationship);
			abmFamilyEntity.setIdCard(ownerIdCard);
			abmFamilyEntity.setHouseholdsType(ownerAccountType);
			abmFamilyEntity.setAge(ownerAge);
			abmFamilyEntity.setEducationLevel(ownerEducationLevel);
			abmFamilyEntity.setGender(ownerGender);
			abmFamilyEntity.setHouseholdsHome(ownerHomeAddress);
			abmFamilyEntity.setName(ownerName);
			abmFamilyEntity.setNational(ownerNational);
			abmFamilyEntity.setRegion(region);
			abmFamilyEntity.setRemark(ownerRemark);
			abmFamilyEntity.setStatus(ownerStatus);
			abmFamilyService.save(abmFamilyEntity);
		} else {
			findByOwnerNmAndIdCard.setOwnerNm(ownerNm);
			findByOwnerNmAndIdCard.setMasterRelationship(ownerMasterRelationship);
			findByOwnerNmAndIdCard.setIdCard(ownerIdCard);
			findByOwnerNmAndIdCard.setHouseholdsType(ownerAccountType);
			findByOwnerNmAndIdCard.setAge(ownerAge);
			findByOwnerNmAndIdCard.setEducationLevel(ownerEducationLevel);
			findByOwnerNmAndIdCard.setGender(ownerGender);
			findByOwnerNmAndIdCard.setHouseholdsHome(ownerHomeAddress);
			findByOwnerNmAndIdCard.setName(ownerName);
			findByOwnerNmAndIdCard.setNational(ownerNational);
			findByOwnerNmAndIdCard.setRegion(region);
			findByOwnerNmAndIdCard.setRemark(ownerRemark);
			findByOwnerNmAndIdCard.setStatus(ownerStatus);
			abmFamilyService.save(findByOwnerNmAndIdCard);
		}

		owner.setStatus(InfoConstant.HOUSEHOLD_REGISTER_SYNCHRONIZED);
		infoHouseholdRegisterDao.save(owner);

	}

	/**
	 * 查询户主下拉数据
	 * 
	 * @param name
	 * @return
	 */
	public List<InfoHouseholdRegisterEntity> ownerList(String name) {
		List<InfoHouseholdRegisterEntity> findOwnerListByName = infoHouseholdRegisterDao.findOwnerListByName(name);
		return findOwnerListByName;
	}

}
