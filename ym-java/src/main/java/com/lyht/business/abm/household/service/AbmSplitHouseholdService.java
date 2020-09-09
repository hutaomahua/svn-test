package com.lyht.business.abm.household.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lyht.business.abm.household.vo.*;
import com.lyht.business.abm.production.dao.ProduceProcessDao;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import com.lyht.business.abm.review.service.PersonaWealthService;
import com.lyht.business.abm.signed.dao.ProtocolInfoDao;
import com.lyht.business.abm.signed.entity.ProtocolInfo;
import com.lyht.business.info.dao.InfoOwnerDao;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.vo.InfoOwnerDetailVO;
import com.lyht.business.message.service.MessageNoticeService;
import com.lyht.util.SystemUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.config.redis.RedisUtil;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.household.common.constant.AbmHouseholdConst;
import com.lyht.business.abm.household.common.enums.SplitHouseholdExceptionEnums;
import com.lyht.business.abm.household.dao.AbmSplitHouseholdDao;
import com.lyht.business.abm.household.entity.AbmSplitHouseholdEntity;
import com.lyht.business.abm.removal.entity.AbmBuildingEntity;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmHouseEntity;
import com.lyht.business.abm.removal.entity.AbmHousesDecorationEntity;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.removal.entity.AbmTreesEntity;
import com.lyht.business.abm.removal.service.AbmBuildingService;
import com.lyht.business.abm.removal.service.AbmFamilyService;
import com.lyht.business.abm.removal.service.AbmHouseDecorationService;
import com.lyht.business.abm.removal.service.AbmHouseService;
import com.lyht.business.abm.removal.service.AbmLandService;
import com.lyht.business.abm.removal.service.AbmOwnerService;
import com.lyht.business.abm.removal.service.AbmTreesService;
import com.lyht.business.process.common.constant.ProcessConstant;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.Randomizer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AbmSplitHouseholdService {

	@Value("${iwind.process.flow.path.split.apply}")
	private String spiltHouseholdApplyFlowPath;// 分户申请流程路径
	@Value("${iwind.process.flow.path.split.household}")
	private String spiltHouseholdFlowPath;// 分户流程路径

	@Autowired
	private AbmSplitHouseholdDao abmSplitHouseholdDao;

	@Autowired
	private ProcessOperateService processOperateService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private PubFilesService pubFilesService;

	@Autowired
	private AbmOwnerService abmOwnerService;
	@Autowired
	private AbmFamilyService abmFamilyService;
	@Autowired
	private AbmLandService abmLandService;
	@Autowired
	private AbmHouseService abmHouseService;
	@Autowired
	private AbmTreesService abmTreesService;
	@Autowired
	private AbmBuildingService abmBuildingService;
	@Autowired
	private AbmHouseDecorationService abmHouseDecorationService;

	/**
	 * 分户申请--流程发起
	 *
	 * @param request
	 * @param file
	 * @param ownerNm
	 * @param remark
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public AbmSplitHouseholdEntity startApplyProcess(HttpServletRequest request, MultipartFile[] files, String ownerNm, String remark) {
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("分户申请-户主编码不能为空！");
		}
		AbmOwnerEntity owner = abmOwnerService.findByNm(ownerNm);
		if (owner == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
		String splitHouseholdState = owner.getSplitHouseholdState();
		if (StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_DOING)) {
			throw new LyhtRuntimeException("该户的“分户申请”流程正在处理中！");
		}
		if (splitHouseholdState != null && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_NORMAL) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_HAVE_LEDGER)) {
			throw new LyhtRuntimeException("该户正在分户操作中！");
		}
		if (files == null || files.length == 0) {
			throw new LyhtRuntimeException("请上传签字确认表！");
		}

		AbmSplitHouseholdEntity abmSplitHouseholdEntity = null;
		try {
			// 发起分户申请流程
			ProcessOperateVO processOperateVO = new ProcessOperateVO();
			processOperateVO.setFlowPath(spiltHouseholdApplyFlowPath);
			String ownerName = owner.getName();
			if (StringUtils.isNotBlank(ownerName)) {
				Map<String, String> data = new HashMap<>();
				data.put("name", ownerName + "-分户申请-" + ownerNm);
				processOperateVO.setData(data);
				log.debug("=====分户申请流程==========" + ownerName);
			}
			//processOperateVO.setUser("fc88cd7f-66fb-4541-bb2b-65dc385e5b4e");// 未登录测试用
			String processId = null;
			try {
				processId = processOperateService.processStart(processOperateVO, request);
			} catch (Exception e) {
				throw new LyhtRuntimeException("流程引擎请求超时，请检查网络后重试！");
			}

			// 保存附件，获取附件路径
			PubFilesEntity pubFilesEntity = new PubFilesEntity();
			pubFilesEntity.setTableName(AbmHouseholdConst.SPLIT_TABLE_NAME);
			pubFilesEntity.setTablePkColumn(AbmHouseholdConst.SPLIT_PREFIX + ownerNm);
			int i = 1, size = files.length;
			StringBuffer str = new StringBuffer();
			for(MultipartFile file : files){
				PubFilesEntity upload = pubFilesService.upload(request, file, pubFilesEntity);
				str.append(upload.getNm());
				if(i != size){
					str.append(",");
				}
				i++;
			}
			// 保存分户申请记录
			abmSplitHouseholdEntity = new AbmSplitHouseholdEntity();
			abmSplitHouseholdEntity.setOwnerNm(ownerNm);
			abmSplitHouseholdEntity.setApplyProcessId(processId);
			System.err.println("保存文件内码:" + str);
			abmSplitHouseholdEntity.setSignFileUrl(str.toString());
			abmSplitHouseholdEntity.setRemark(remark);
			abmSplitHouseholdEntity = abmSplitHouseholdDao.save(abmSplitHouseholdEntity);

			owner.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_APPLY_DOING);
			abmOwnerService.save(owner);
		} catch (LyhtRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new LyhtRuntimeException("流程发起失败！");
		}
		return abmSplitHouseholdEntity;
	}

	/**
	 * 分户申请--流程回调
	 *
	 * @param processId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean applyProcessCallback(String processId, HttpServletRequest request) {
		AbmSplitHouseholdProcessVO findProcessByApplyProcessId = abmSplitHouseholdDao.findProcessByApplyProcessId(processId);
		if (findProcessByApplyProcessId == null) {
			return false;
		}
		String ownerNm = findProcessByApplyProcessId.getOwnerNm();
		AbmOwnerEntity owner = abmOwnerService.findByNm(ownerNm);
		if (owner == null) {
			return false;
		}
		String applyProcessStatus = findProcessByApplyProcessId.getApplyProcessStatus();
		System.err.println("分户申请--流程回调状态-" + applyProcessStatus + ",processId:" + processId);
		Map data = new HashMap(2);
		data.put("name", "分户申请");
		data.put("info", findProcessByApplyProcessId);
		String jsonString = JSON.toJSONString(data);
		String sessionNm = SystemUtil.getLoginStaffNm(request);
		if (StringUtils.equalsIgnoreCase(applyProcessStatus, ProcessConstant.PROCESS_APPROVED)) {// 通过，设置状态为“分户申请已通过”
			owner.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED);
			abmOwnerService.save(owner);
			try{
				messageNoticeService.sendMessageNoticeByUser(sessionNm, ownerNm, "分户申请通过", jsonString, "SHOW");
				messageNoticeService.sendMessageNoticeByUser(sessionNm, sessionNm, "分户申请通过", jsonString, "SHOW");
				messageNoticeService.sendMessageNoticeByRole(sessionNm, "BCA5AA572F", "分户申请通过", jsonString, "JUMP");
			}catch (Exception e){
				System.err.println("消息推送-个人财产分户申请通过，错误原因：" + e.getMessage());
			}
			return true;
		} else if (StringUtils.equalsIgnoreCase(applyProcessStatus, ProcessConstant.PROCESS_REJECTED)) {// 拒绝，设置状态为“正常”
			owner.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_NORMAL);
			abmOwnerService.save(owner);
			try{
				messageNoticeService.sendMessageNoticeByUser(sessionNm, ownerNm, "分户申请拒绝", jsonString, "SHOW");
				messageNoticeService.sendMessageNoticeByUser(sessionNm, sessionNm, "分户申请拒绝", jsonString, "SHOW");
			}catch (Exception e) {
				System.err.println("消息推送-个人财产分户申请拒绝，错误原因：" + e.getMessage());
			}
			return true;
		}
		return false;
	}


	@Autowired private PersonaWealthService service;

	/**
	 * 开始分户，缓存对应户的数据
	 *
	 * @param splitOwnerNm 分户的户主nm
	 */
	public List<AbmSplitHouseholdNodeVO> startSplit(String splitOwnerNm, Integer type) {
		// --------------------step1：校验
		if (StringUtils.isBlank(splitOwnerNm)) {
			throw new LyhtRuntimeException("分户-户主编码不能为空！");
		}
		String key = AbmHouseholdConst.HOUSEHOLD_KEY + splitOwnerNm;
		Object object = redisUtil.get(key);
		/*if (object != null) {
			throw new LyhtRuntimeException("同一时间段内，只允许一个人对当前户做分户操作！");
		}*/
		AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO = getAbmSplitHouseholdNodeVO(splitOwnerNm);
		AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
		if (owner == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
		String splitHouseholdState = owner.getSplitHouseholdState();
		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_STORAGE)) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}

		// --------------------step2：获取分户的数据缓存对应户的数据,开始分户
		List<AbmSplitHouseholdNodeVO> list = new ArrayList<>();
		if(null == object || type > 1){
			list.add(abmSplitHouseholdNodeVO);
			String jsonString = JSON.toJSONString(list);
			boolean set = redisUtil.set(key, jsonString);
			if (!set) {
				throw new LyhtRuntimeException("数据缓存失败，请稍后重试！");
			}
			return list;
		}else {
			list = JSON.parseArray(object.toString(), AbmSplitHouseholdNodeVO.class);
			return list;
		}
	}

	/**
	 * 获取可分户的下拉数据
	 *
	 * @param splitOwnerNm 分户的户主nm
	 * @param ownerNm      当前户内的户主nm
	 * @return
	 */
	public List<AbmFamilyEntity> getSplitData(AbmSplitDataParamVO abmSplitDataParamVO) {
		// --------------------step1：校验
		if (abmSplitDataParamVO == null) {
			throw new LyhtRuntimeException("参数不能为空！");
		}
		String splitOwnerNm = abmSplitDataParamVO.getSplitOwnerNm();
		if (StringUtils.isBlank(splitOwnerNm)) {
			throw new LyhtRuntimeException("分户编码不能为空！");
		}
		String ownerNm = abmSplitDataParamVO.getOwnerNm();
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("分户对应户主编码不能为空！");
		}
		AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(splitOwnerNm);
		if (abmOwnerEntity == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
		String splitHouseholdState = abmOwnerEntity.getSplitHouseholdState();
		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_STORAGE)) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}

		String key = AbmHouseholdConst.HOUSEHOLD_KEY + splitOwnerNm;
		Object object = redisUtil.get(key);
		if (object == null) {
			throw new LyhtRuntimeException(SplitHouseholdExceptionEnums.NOT_EXIST_OR_HAS_EXPIRED);
		}
		// --------------------step2：获取下拉数据
		String value = object.toString();
		List<AbmSplitHouseholdNodeVO> list = JSON.parseArray(value, AbmSplitHouseholdNodeVO.class);
		List<AbmFamilyEntity> result = null;
		for (AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO : list) {
			AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
			if (owner != null) {// 判断户主是否为空
				String nm = owner.getNm();
				if (StringUtils.equals(nm, ownerNm)) {// 找到当前分户数据中的指定户主
					List<AbmFamilyEntity> familyList = abmSplitHouseholdNodeVO.getFamilyList();
					if (CollectionUtils.isNotEmpty(familyList)) {
						for (AbmFamilyEntity abmFamilyEntity : familyList) {
							String masterRelationship = abmFamilyEntity.getMasterRelationship();
							if (!StringUtils.equals(masterRelationship, AbmHouseholdConst.MASTER_RELATIONSHIP_OWNER)) {// 获取可分户数据（户主除外）
								if (result == null) {
									result = new ArrayList<>();
								}
								result.add(abmFamilyEntity);
							}
						}
					}
				}
			}
		}
		// --------------------step3：返回数据
		return result;
	}

	/**
	 * 分户
	 *
	 * @param splitOwnerNm 分户的户主nm
	 * @param ownerNm      当前户内的户主nm
	 * @param familyNm     分户前的家庭成员nm
	 * @return
	 */
	public List<AbmSplitHouseholdNodeVO> splitHousehold(AbmSplitHouseholdParamVO abmSplitHouseholdParamVO) {
		// --------------------step1：校验
		if (abmSplitHouseholdParamVO == null) {
			throw new LyhtRuntimeException("参数不能为空！");
		}
		String splitOwnerNm = abmSplitHouseholdParamVO.getSplitOwnerNm();
		if (StringUtils.isBlank(splitOwnerNm)) {
			throw new LyhtRuntimeException("分户编码不能为空！");
		}
		String ownerNm = abmSplitHouseholdParamVO.getOwnerNm();
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("分户对应户主编码不能为空！");
		}
		String familyNm = abmSplitHouseholdParamVO.getFamilyNm();
		if (StringUtils.isBlank(familyNm)) {
			throw new LyhtRuntimeException("被分户的家庭成员编码不能为空！");
		}
		AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(splitOwnerNm);
		if (abmOwnerEntity == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
		String splitHouseholdState = abmOwnerEntity.getSplitHouseholdState();
		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_STORAGE)) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}

		String key = AbmHouseholdConst.HOUSEHOLD_KEY + splitOwnerNm;
		Object object = redisUtil.get(key);
		if (object == null) {
			throw new LyhtRuntimeException(SplitHouseholdExceptionEnums.NOT_EXIST_OR_HAS_EXPIRED);
		}
		// --------------------step2：分户
		String value = object.toString();
		List<AbmSplitHouseholdNodeVO> list = JSON.parseArray(value, AbmSplitHouseholdNodeVO.class);
		AbmOwnerEntity newOwner = null;
		AbmFamilyEntity splitFamily = null;
		for (AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO : list) {
			AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
			if (owner != null) {// 判断户主是否为空
				String nm = owner.getNm();
				if (StringUtils.equals(nm, ownerNm)) {// 找到当前分户数据中的指定户主
					List<AbmFamilyEntity> familyList = abmSplitHouseholdNodeVO.getFamilyList();
					if (CollectionUtils.isNotEmpty(familyList)) {
						for (AbmFamilyEntity abmFamilyEntity : familyList) {
							String familyNumber = abmFamilyEntity.getNm();
							if (StringUtils.equals(familyNumber, familyNm)) {// 找到分户的家庭成员
								// 生成新的户主
								newOwner = getOwnerByFamily(abmFamilyEntity);
								if(StringUtils.isBlank(newOwner.getScope())){
									newOwner.setScope(owner.getScope());
								}
								newOwner.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED);
								// 修改户主编码为新户主的编码
								String newOwnerNM = newOwner.getNm();
								abmFamilyEntity.setOwnerNm(newOwnerNM);
								abmFamilyEntity.setMasterRelationship(AbmHouseholdConst.MASTER_RELATIONSHIP_OWNER);
								splitFamily = abmFamilyEntity;
							}
						}
						familyList.remove(splitFamily);// 删除已分户的家庭成员
					}
				}
			}
		}
		if (newOwner == null) {
			throw new LyhtRuntimeException("分户的家庭成员不存在！");
		}
		// --------------------step3：重新保存数据,并设置过期时间
		AbmSplitHouseholdNodeVO newNode = new AbmSplitHouseholdNodeVO();
		List<AbmFamilyEntity> newFamilyList = new ArrayList<>();
		newFamilyList.add(splitFamily);
		newNode.setFamilyList(newFamilyList);
		newNode.setOwner(newOwner);
		list.add(newNode);
		String jsonString = JSON.toJSONString(list);
		boolean set = redisUtil.set(key, jsonString);
		if (!set) {
			throw new LyhtRuntimeException("数据缓存失败，请稍后重试！");
		}
		// --------------------step4：返回数据
		return list;
	}

	/**
	 * 家庭成员 转 户主
	 *
	 * @param abmFamilyEntity
	 * @return
	 */
	private AbmOwnerEntity getOwnerByFamily(AbmFamilyEntity abmFamilyEntity) {
		if (abmFamilyEntity == null) {
			return null;
		}
		AbmOwnerEntity abmOwnerEntity = new AbmOwnerEntity();
		abmOwnerEntity.setNm(Randomizer.generCode(10));
		String name = abmFamilyEntity.getName();
		abmOwnerEntity.setName(name);
		String idCard = abmFamilyEntity.getIdCard();
		abmOwnerEntity.setIdCard(idCard);
		Integer age = abmFamilyEntity.getAge();
		abmOwnerEntity.setAge(age);
		String gender = abmFamilyEntity.getGender();
		abmOwnerEntity.setGender(gender);
		String national = abmFamilyEntity.getNational();
		abmOwnerEntity.setNational(national);
		String currentSchool = abmFamilyEntity.getCurrentSchool();
		abmOwnerEntity.setCurrentSchool(currentSchool);
		String educationLevel = abmFamilyEntity.getEducationLevel();
		abmOwnerEntity.setEducationLevel(educationLevel);
		String presentOccupation = abmFamilyEntity.getPresentOccupation();
		abmOwnerEntity.setPresentOccupation(presentOccupation);
		String householdsHome = abmFamilyEntity.getHouseholdsHome();
		abmOwnerEntity.setHouseholdsHome(householdsHome);
		String householdsType = abmFamilyEntity.getHouseholdsType();
		abmOwnerEntity.setHouseholdsType(householdsType);
		String region = abmFamilyEntity.getRegion();
		abmOwnerEntity.setRegion(region);
		String scope = abmFamilyEntity.getScope();
		abmOwnerEntity.setScope(scope);
		String altd = abmFamilyEntity.getAltd();
		abmOwnerEntity.setAltd(altd);
		String lgtd = abmFamilyEntity.getLgtd();
		abmOwnerEntity.setLgtd(lgtd);
		String lttd = abmFamilyEntity.getLttd();
		abmOwnerEntity.setLttd(lttd);
		String inMap = abmFamilyEntity.getInMap();
		abmOwnerEntity.setInMap(inMap);
		String stage = abmFamilyEntity.getStage();
		abmOwnerEntity.setStage(stage);
		String remark = abmFamilyEntity.getRemark();
		abmOwnerEntity.setRemark(remark);
		String xiang = abmFamilyEntity.getXiang();
		abmOwnerEntity.setXiang(xiang);
		String cun = abmFamilyEntity.getCun();
		abmOwnerEntity.setCun(cun);
		String zu = abmFamilyEntity.getZu();
		abmOwnerEntity.setZu(zu);
		String placeName = abmFamilyEntity.getPlaceName();
		abmOwnerEntity.setPlaceName(placeName);
		String placeType = abmFamilyEntity.getPlaceType();
		abmOwnerEntity.setPlaceType(placeType);
		String define = abmFamilyEntity.getDefine();
		abmOwnerEntity.setDefine(define);
		String producePlace = abmFamilyEntity.getProducePlace();
		abmOwnerEntity.setProducePlace(producePlace);
		String zblx = abmFamilyEntity.getZblx();
		abmOwnerEntity.setZblx(zblx);
		abmOwnerEntity.setIPopulation(1);// 新户主人口数默认为1
		return abmOwnerEntity;
	}

	/**
	 * 指标数据划分
	 *
	 * @param splitOwnerNm 分户的户主nm
	 * @param preOwnerNm   指标划分前的户主nm
	 * @param afterOwnerNm 指标划分后的户主nm
	 * @param type         指标类型
	 * @param dataNm       被划分的数据nm
	 * @return
	 */
	public List<AbmSplitHouseholdNodeVO> divisionData(AbmSplitDivisionParamVO abmSplitDivisionParamVO) {
		// --------------------step1：校验
		if (abmSplitDivisionParamVO == null) {
			throw new LyhtRuntimeException("参数不能为空！");
		}
		String splitOwnerNm = abmSplitDivisionParamVO.getSplitOwnerNm();
		if (StringUtils.isBlank(splitOwnerNm)) {
			throw new LyhtRuntimeException("分户编码不能为空！");
		}
		String preOwnerNm = abmSplitDivisionParamVO.getPreOwnerNm();
		if (StringUtils.isBlank(preOwnerNm)) {
			throw new LyhtRuntimeException("划分前的户主编码不能为空！");
		}
		String afterOwnerNm = abmSplitDivisionParamVO.getAfterOwnerNm();
		if (StringUtils.isBlank(afterOwnerNm)) {
			throw new LyhtRuntimeException("划分后的户主编码不能为空！");
		}
		if (StringUtils.equals(preOwnerNm, afterOwnerNm)) {
			throw new LyhtRuntimeException("请勿划分给同一户主！");
		}
		String type = abmSplitDivisionParamVO.getType();
		if (!StringUtils.equalsAnyIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_FAMILY,
				AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_HOUSE, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_LAND,
				AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_BUILDING, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_TREES,
				AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_DECORATION, "facilities", "homestead")) {
			throw new LyhtRuntimeException("指标数据类型错误！");
		}
		List<String> dataNm = abmSplitDivisionParamVO.getDataNm();
		if (dataNm == null || dataNm.isEmpty()) {
			throw new LyhtRuntimeException("未选中划分的数据！");
		}
		AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(splitOwnerNm);
		if (abmOwnerEntity == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
		String splitHouseholdState = abmOwnerEntity.getSplitHouseholdState();
		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_STORAGE)) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}

		String key = AbmHouseholdConst.HOUSEHOLD_KEY + splitOwnerNm;
		Object object = redisUtil.get(key);
		if (object == null) {
			throw new LyhtRuntimeException(SplitHouseholdExceptionEnums.NOT_EXIST_OR_HAS_EXPIRED);
		}
		// --------------------step2：划分
		String value = object.toString();
		List<AbmSplitHouseholdNodeVO> list = JSON.parseArray(value, AbmSplitHouseholdNodeVO.class);

		// 划分数据
		List<AbmFamilyEntity> divisionFamilyList = null;
		List<AbmHouseEntity> divisionHouseList = null;
		List<AbmLandEntity> divisionLandList = null;
		List<AbmTreesEntity> divisionTreesList = null;
		List<AbmBuildingEntity> divisionBuildingList = null;
		List<AbmHousesDecorationEntity> divisionHousesDecorationList = null;
		List<Map> facilitiesList = new ArrayList<>();
		List<Map> homesteadList = new ArrayList<>();

		// 获取并从当前户删除划分数据
		for (AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO : list) {
			AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
			if (owner != null) {// 判断户主是否为空
				String nm = owner.getNm();
				if (StringUtils.equals(nm, preOwnerNm)) {// 找到划分前
					if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_FAMILY)) {
						List<AbmFamilyEntity> familyList = abmSplitHouseholdNodeVO.getFamilyList();
						if (CollectionUtils.isNotEmpty(familyList)) {
							for (AbmFamilyEntity abmFamilyEntity : familyList) {
								String number = abmFamilyEntity.getNm();
								if (dataNm.contains(number)) {// 找到被划分的数据
									if (divisionFamilyList == null) {
										divisionFamilyList = new ArrayList<>();
									}
									// 替换权属人，获取被划分的数据
									abmFamilyEntity.setOwnerNm(afterOwnerNm);
									divisionFamilyList.add(abmFamilyEntity);
									abmFamilyEntity.setMasterRelationship(null);//户主关系
								}
							}
							familyList.removeAll(divisionFamilyList);// 删除被划分走的数据
						}
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_HOUSE)) {
						List<AbmHouseEntity> houseList = abmSplitHouseholdNodeVO.getHouseList();
						if (CollectionUtils.isNotEmpty(houseList)) {
							for (AbmHouseEntity abmHouseEntity : houseList) {
								String number = abmHouseEntity.getNm();
								if (dataNm.contains(number)) {// 找到被划分的数据
									if (divisionHouseList == null) {
										divisionHouseList = new ArrayList<>();
									}
									// 替换权属人，获取被划分的数据
									abmHouseEntity.setOwnerNm(afterOwnerNm);
									divisionHouseList.add(abmHouseEntity);
								}
							}
							houseList.removeAll(divisionHouseList);// 删除被划分走的数据
						}
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_LAND)) {
						List<AbmLandEntity> landList = abmSplitHouseholdNodeVO.getLandList();
						if (CollectionUtils.isNotEmpty(landList)) {
							for (AbmLandEntity abmLandEntity : landList) {
								String number = abmLandEntity.getNm();
								if (dataNm.contains(number)) {// 找到被划分的数据
									if (divisionLandList == null) {
										divisionLandList = new ArrayList<>();
									}
									// 替换权属人，获取被划分的数据
									abmLandEntity.setOwnerNm(afterOwnerNm);
									divisionLandList.add(abmLandEntity);
								}
							}
							landList.removeAll(divisionLandList);// 删除被划分走的数据
						}
					} else if (StringUtils.equalsIgnoreCase(type,
							AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_BUILDING)) {
						List<AbmBuildingEntity> buildingList = abmSplitHouseholdNodeVO.getBuildingList();
						if (CollectionUtils.isNotEmpty(buildingList)) {
							for (AbmBuildingEntity abmBuildingEntity : buildingList) {
								String number = abmBuildingEntity.getNm();
								if (dataNm.contains(number)) {// 找到被划分的数据
									if (divisionBuildingList == null) {
										divisionBuildingList = new ArrayList<>();
									}
									// 替换权属人，获取被划分的数据
									abmBuildingEntity.setOwnerNm(afterOwnerNm);
									divisionBuildingList.add(abmBuildingEntity);
								}
							}
							buildingList.removeAll(divisionBuildingList);// 删除被划分走的数据
						}
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_TREES)) {
						List<AbmTreesEntity> treeList = abmSplitHouseholdNodeVO.getTreeList();
						if (CollectionUtils.isNotEmpty(treeList)) {
							for (AbmTreesEntity abmTreesEntity : treeList) {
								String number = abmTreesEntity.getNm();
								if (dataNm.contains(number)) {// 找到被划分的数据
									if (divisionTreesList == null) {
										divisionTreesList = new ArrayList<>();
									}
									// 替换权属人，获取被划分的数据
									abmTreesEntity.setOwnerNm(afterOwnerNm);
									divisionTreesList.add(abmTreesEntity);
								}
							}
							treeList.removeAll(divisionTreesList);// 删除被划分走的数据
						}
					} else if (StringUtils.equalsIgnoreCase(type,
							AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_DECORATION)) {
						List<AbmHousesDecorationEntity> housesDecorationList = abmSplitHouseholdNodeVO
								.getHousesDecorationList();
						if (CollectionUtils.isNotEmpty(housesDecorationList)) {
							for (AbmHousesDecorationEntity abmHousesDecorationEntity : housesDecorationList) {
								String number = abmHousesDecorationEntity.getNm();
								if (dataNm.contains(number)) {// 找到被划分的数据
									if (divisionHousesDecorationList == null) {
										divisionHousesDecorationList = new ArrayList<>();
									}
									// 替换权属人，获取被划分的数据
									abmHousesDecorationEntity.setOwnerNm(afterOwnerNm);
									divisionHousesDecorationList.add(abmHousesDecorationEntity);
								}
							}
							housesDecorationList.removeAll(divisionHousesDecorationList);// 删除被划分走的数据
						}
					}else if("facilities".equals(type)){//农副业设施
						List<Map> newFacilitiesList = abmSplitHouseholdNodeVO.getFacilitiesList();
						if (CollectionUtils.isNotEmpty(newFacilitiesList)) {
							for (Map s : newFacilitiesList) {
								String number = (String) s.get("nm");
								if (dataNm.contains(number)) {// 找到被划分的数据
									// 替换权属人，获取被划分的数据
									s.put("ownerNm", afterOwnerNm);
									facilitiesList.add(s);
								}
							}
							newFacilitiesList.removeAll(facilitiesList);// 删除被划分走的数据
						}
					}else if("homestead".equals(type)){//宅基地
						List<Map> newHomesteadList = abmSplitHouseholdNodeVO.getHomesteadList();
						if (CollectionUtils.isNotEmpty(newHomesteadList)) {
							for (Map s : newHomesteadList) {
								String number = (String) s.get("nm");
								if (dataNm.contains(number)) {// 找到被划分的数据
									// 替换权属人，获取被划分的数据
									s.put("ownerNm", afterOwnerNm);
									homesteadList.add(s);
								}
							}
							newHomesteadList.removeAll(homesteadList);// 删除被划分走的数据
						}
					}
				}
			}
		}

		// 数据划分到对应户主指标下
		for (AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO : list) {
			AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
			if (owner != null) {// 判断户主是否为空
				String nm = owner.getNm();
				if (StringUtils.equals(nm, afterOwnerNm)) {// 找到划分后的户主
					if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_FAMILY)) {
						List<AbmFamilyEntity> familyList = abmSplitHouseholdNodeVO.getFamilyList();
						if (familyList == null) {
							familyList = new ArrayList<>();
						}
						familyList.addAll(divisionFamilyList);
						abmSplitHouseholdNodeVO.setFamilyList(familyList);
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_HOUSE)) {
						List<AbmHouseEntity> houseList = abmSplitHouseholdNodeVO.getHouseList();
						if (houseList == null) {
							houseList = new ArrayList<>();
						}
						houseList.addAll(divisionHouseList);
						abmSplitHouseholdNodeVO.setHouseList(houseList);
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_LAND)) {
						List<AbmLandEntity> landList = abmSplitHouseholdNodeVO.getLandList();
						if (landList == null) {
							landList = new ArrayList<>();
						}
						landList.addAll(divisionLandList);
						abmSplitHouseholdNodeVO.setLandList(landList);
					} else if (StringUtils.equalsIgnoreCase(type,
							AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_BUILDING)) {
						List<AbmBuildingEntity> buildingList = abmSplitHouseholdNodeVO.getBuildingList();
						if (buildingList == null) {
							buildingList = new ArrayList<>();
						}
						buildingList.addAll(divisionBuildingList);
						abmSplitHouseholdNodeVO.setBuildingList(buildingList);
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_TREES)) {
						List<AbmTreesEntity> treeList = abmSplitHouseholdNodeVO.getTreeList();
						if (treeList == null) {
							treeList = new ArrayList<>();
						}
						treeList.addAll(divisionTreesList);
						abmSplitHouseholdNodeVO.setTreeList(treeList);
					} else if (StringUtils.equalsIgnoreCase(type,
							AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_DECORATION)) {
						List<AbmHousesDecorationEntity> housesDecorationList = abmSplitHouseholdNodeVO
								.getHousesDecorationList();
						if (housesDecorationList == null) {
							housesDecorationList = new ArrayList<>();
						}
						housesDecorationList.addAll(divisionHousesDecorationList);
						abmSplitHouseholdNodeVO.setHousesDecorationList(housesDecorationList);
					}else if("facilities".equals(type)){
						List<Map> newFacilitiesList = abmSplitHouseholdNodeVO.getFacilitiesList();
						newFacilitiesList.addAll(facilitiesList);
						abmSplitHouseholdNodeVO.setFacilitiesList(newFacilitiesList);
					}else if("homestead".equals(type)){
						List<Map> newHomesteadList = abmSplitHouseholdNodeVO.getHomesteadList();
						newHomesteadList.addAll(facilitiesList);
						abmSplitHouseholdNodeVO.setHomesteadList(newHomesteadList);
					}
				}
			}
		}

		// --------------------step3：重新保存数据,并设置过期时间
		String jsonString = JSON.toJSONString(list);
		boolean set = redisUtil.set(key, jsonString);
		if (!set) {
			throw new LyhtRuntimeException("数据缓存失败，请稍后重试！");
		}
		// --------------------step4：返回数据
		return list;
	}

	/**
	 * 修改家庭成员--与户主关系
	 *
	 * @param splitOwnerNm         分户的户主nm
	 * @param ownerNm              当前户内的户主nm
	 * @param familyNm             家庭成员nm
	 * @param masterRelationshipNm 与户主关系字典nm
	 * @return
	 */
	public List<AbmSplitHouseholdNodeVO> updateMasterRelationship(AbmSplitUpdateParamVO abmSplitUpdateParamVO) {
		// --------------------step1：校验
		if (abmSplitUpdateParamVO == null) {
			throw new LyhtRuntimeException("参数不能为空！");
		}
		String splitOwnerNm = abmSplitUpdateParamVO.getSplitOwnerNm();
		if (StringUtils.isBlank(splitOwnerNm)) {
			throw new LyhtRuntimeException("分户编码不能为空！");
		}
		String ownerNm = abmSplitUpdateParamVO.getOwnerNm();
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("分户对应户主编码不能为空！");
		}
		String familyNm = abmSplitUpdateParamVO.getFamilyNm();
		if (StringUtils.isBlank(familyNm)) {
			throw new LyhtRuntimeException("被分户的家庭成员编码不能为空！");
		}
		String masterRelationshipNm = abmSplitUpdateParamVO.getMasterRelationshipNm();
		if (StringUtils.isBlank(masterRelationshipNm)
				|| StringUtils.equals(AbmHouseholdConst.MASTER_RELATIONSHIP_OWNER, masterRelationshipNm)) {
			throw new LyhtRuntimeException("与户主关系：不能为空，并且不能为户主！");
		}
		AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(splitOwnerNm);
		if (abmOwnerEntity == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
		String splitHouseholdState = abmOwnerEntity.getSplitHouseholdState();
		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_STORAGE)) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}

		String key = AbmHouseholdConst.HOUSEHOLD_KEY + splitOwnerNm;
		Object object = redisUtil.get(key);
		if (object == null) {
			throw new LyhtRuntimeException(SplitHouseholdExceptionEnums.NOT_EXIST_OR_HAS_EXPIRED);
		}
		// --------------------step2：修改与户主关系
		String value = object.toString();
		List<AbmSplitHouseholdNodeVO> list = JSON.parseArray(value, AbmSplitHouseholdNodeVO.class);
		for (AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO : list) {
			AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
			if (owner != null) {// 判断户主是否为空
				String nm = owner.getNm();
				if (StringUtils.equals(nm, ownerNm)) {// 找到对应户主
					List<AbmFamilyEntity> familyList = abmSplitHouseholdNodeVO.getFamilyList();
					if (CollectionUtils.isNotEmpty(familyList)) {
						for (AbmFamilyEntity abmFamilyEntity : familyList) {
							String familyNumber = abmFamilyEntity.getNm();
							if (StringUtils.equals(familyNumber, familyNm)) {// 找到要修改的家庭成员
								String masterRelationship = abmFamilyEntity.getMasterRelationship();
								if (StringUtils.equals(AbmHouseholdConst.MASTER_RELATIONSHIP_OWNER,
										masterRelationship)) {
									throw new LyhtRuntimeException("户主不能修改与户主关系！");
								}
								// 修改与户主关系
								abmFamilyEntity.setMasterRelationship(masterRelationshipNm);
							}
						}
					}
				}
			}
		}
		// --------------------step3：重新保存数据,并设置过期时间
		String jsonString = JSON.toJSONString(list);
		boolean set = redisUtil.set(key, jsonString);
		if (!set) {
			throw new LyhtRuntimeException("数据缓存失败，请稍后重试！");
		}
		// --------------------step4：返回数据
		return list;
	}

	/**
	 * 数量拆分
	 *
	 * @param splitOwnerNm 分户的户主nm
	 * @param ownerNm      当前户内的户主nm
	 * @param type         指标类型
	 * @param dataNm       被拆分的数据nm
	 * @param longs        长
	 * @param area         面积
	 * @param volume       体积
	 * @param num          数量
	 * @return
	 */
	public List<AbmSplitHouseholdNodeVO> splitNumber(AbmSplitNumberParamVO abmSplitNumberParamVO) {
		// --------------------step1：校验
		if (abmSplitNumberParamVO == null) {
			throw new LyhtRuntimeException("参数不能为空！");
		}
		String splitOwnerNm = abmSplitNumberParamVO.getSplitOwnerNm();
		if (StringUtils.isBlank(splitOwnerNm)) {
			throw new LyhtRuntimeException("分户编码不能为空！");
		}
		String ownerNm = abmSplitNumberParamVO.getOwnerNm();
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("被拆分的户主编码不能为空！");
		}
		String type = abmSplitNumberParamVO.getType();
		if (!StringUtils.equalsAnyIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_HOUSE,
				AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_LAND, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_BUILDING,
				AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_TREES,
				AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_DECORATION, "facilities", "homestead")) {
			throw new LyhtRuntimeException("指标数据类型错误！");
		}
		String dataNm = abmSplitNumberParamVO.getDataNm();
		if (dataNm == null || dataNm.isEmpty()) {
			throw new LyhtRuntimeException("被拆分的数据编码不能为空！");
		}
		List<BigDecimal> longsList = abmSplitNumberParamVO.getLongsList();
		List<BigDecimal> areaList = abmSplitNumberParamVO.getAreaList();
		List<BigDecimal> volumeList = abmSplitNumberParamVO.getVolumeList();
		List<BigDecimal> numList = abmSplitNumberParamVO.getNumList();
		if (CollectionUtils.isEmpty(longsList) && CollectionUtils.isEmpty(areaList)
				&& CollectionUtils.isEmpty(volumeList) && CollectionUtils.isEmpty(numList)) {
			throw new LyhtRuntimeException("拆分的数据数量不能为空！");
		}
		AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(splitOwnerNm);
		if (abmOwnerEntity == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
		String splitHouseholdState = abmOwnerEntity.getSplitHouseholdState();
		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_STORAGE)) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}

		String key = AbmHouseholdConst.HOUSEHOLD_KEY + splitOwnerNm;
		Object object = redisUtil.get(key);
		if (object == null) {
			throw new LyhtRuntimeException(SplitHouseholdExceptionEnums.NOT_EXIST_OR_HAS_EXPIRED);
		}
		// --------------------step2：划分
		String value = object.toString();
		List<AbmSplitHouseholdNodeVO> list = JSON.parseArray(value, AbmSplitHouseholdNodeVO.class);

		// 拆分数据
		List<AbmHouseEntity> splitHouseList = null;
		List<AbmLandEntity> splitLandList = null;
		List<AbmTreesEntity> splitTreesList = null;
		List<AbmBuildingEntity> splitBuildingList = null;
		List<AbmHousesDecorationEntity> splitHousesDecorationList = null;
		List<Map> facilitiesList = new ArrayList<>();
		List<Map> homesteadList = new ArrayList<>();

		// 获取并从当前户删除被拆分的数据
		for (AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO : list) {
			AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
			if (owner != null) {// 判断户主是否为空
				String nm = owner.getNm();
				if (StringUtils.equals(nm, ownerNm)) {// 找到要拆分的户主
					if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_HOUSE)) {
						List<AbmHouseEntity> houseList = abmSplitHouseholdNodeVO.getHouseList();
						if (houseList != null && !houseList.isEmpty()) {
							AbmHouseEntity remove = null;
							Integer removeId = null;
							String removeNm = null;
							for (AbmHouseEntity abmHouseEntity : houseList) {
								String number = abmHouseEntity.getNm();
								if (StringUtils.equals(dataNm, number)) {// 找到被划分的数据
									BigDecimal area = abmHouseEntity.getArea();
									// 校验数据
									decimalCheck(area, areaList);
									// 被删除数据
									remove = abmHouseEntity;
									removeId = abmHouseEntity.getId();
									removeNm = abmHouseEntity.getNm();
									// 拆分后数据
									if (CollectionUtils.isNotEmpty(areaList)) {
										if (splitHouseList == null) {
											splitHouseList = new ArrayList<>();
										}
										String jsonString = JSON.toJSONString(abmHouseEntity);
										for (BigDecimal bigDecimal : areaList) {
											AbmHouseEntity cloneObject = JSON.parseObject(jsonString,
													AbmHouseEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setArea(bigDecimal);// 给面积赋值
											splitHouseList.add(cloneObject);
										}
									}
								}
							}
							if (remove != null) {
								houseList.remove(remove);// 删除被拆分走的数据
							}
							if (CollectionUtils.isNotEmpty(splitHouseList)) {
								// 把原始数据的id、nm赋值给第一条，防止原始数据关联的信息丢失
								AbmHouseEntity abmHouseEntity = splitHouseList.get(0);
								abmHouseEntity.setId(removeId);
								abmHouseEntity.setNm(removeNm);
								// 保存拆分后数据
								houseList.addAll(splitHouseList);
							}
						}
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_LAND)) {
						List<AbmLandEntity> landList = abmSplitHouseholdNodeVO.getLandList();
						if (landList != null && !landList.isEmpty()) {
							AbmLandEntity remove = null;
							Integer removeId = null;
							String removeNm = null;
							for (AbmLandEntity abmLandEntity : landList) {
								String number = abmLandEntity.getNm();
								if (StringUtils.equals(dataNm, number)) {// 找到被划分的数据
									BigDecimal area = abmLandEntity.getArea();
									// 校验数据
									decimalCheck(area, areaList);
									// 被删除数据
									remove = abmLandEntity;
									removeId = abmLandEntity.getId();
									removeNm = abmLandEntity.getNm();
									// 拆分后数据
									if (CollectionUtils.isNotEmpty(areaList)) {
										if (splitLandList == null) {
											splitLandList = new ArrayList<>();
										}
										String jsonString = JSON.toJSONString(abmLandEntity);
										for (BigDecimal bigDecimal : areaList) {
											AbmLandEntity cloneObject = JSON.parseObject(jsonString,
													AbmLandEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setArea(bigDecimal);// 给面积赋值
											splitLandList.add(cloneObject);
										}
									}
								}
							}
							if (remove != null) {
								landList.remove(remove);// 删除被划分走的数据
							}
							if (CollectionUtils.isNotEmpty(splitLandList)) {
								// 把原始数据的id、nm赋值给第一条，防止原始数据关联的信息丢失
								AbmLandEntity abmLandEntity = splitLandList.get(0);
								abmLandEntity.setId(removeId);
								abmLandEntity.setNm(removeNm);
								// 保存拆分后数据
								landList.addAll(splitLandList);
							}
						}
					} else if (StringUtils.equalsIgnoreCase(type,
							AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_BUILDING)) {
						List<AbmBuildingEntity> buildingList = abmSplitHouseholdNodeVO.getBuildingList();
						if (buildingList != null && !buildingList.isEmpty()) {
							AbmBuildingEntity remove = null;
							Integer removeId = null;
							String removeNm = null;
							for (AbmBuildingEntity abmBuildingEntity : buildingList) {
								String number = abmBuildingEntity.getNm();
								if (StringUtils.equals(dataNm, number)) {// 找到被划分的数据
									if (splitBuildingList == null) {
										splitBuildingList = new ArrayList<>();
									}
									BigDecimal longs = abmBuildingEntity.getLongs();
									BigDecimal area = abmBuildingEntity.getArea();
									BigDecimal volume = abmBuildingEntity.getVolume();
									BigDecimal num = abmBuildingEntity.getNum();
									// 校验数据
									decimalCheck(longs, longsList);
									decimalCheck(area, areaList);
									decimalCheck(volume, volumeList);
									numberCheck(num, numList);
									// 被删除数据
									remove = abmBuildingEntity;
									removeId = abmBuildingEntity.getId();
									removeNm = abmBuildingEntity.getNm();
									// 拆分后数据
									if (CollectionUtils.isNotEmpty(longsList)) {
										String jsonString = JSON.toJSONString(abmBuildingEntity);
										for (BigDecimal bigDecimal : longsList) {
											AbmBuildingEntity cloneObject = JSON.parseObject(jsonString,
													AbmBuildingEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setLongs(bigDecimal);// 给面积赋值
											splitBuildingList.add(cloneObject);
										}
									}
									if (CollectionUtils.isNotEmpty(areaList)) {
										String jsonString = JSON.toJSONString(abmBuildingEntity);
										for (BigDecimal bigDecimal : areaList) {
											AbmBuildingEntity cloneObject = JSON.parseObject(jsonString,
													AbmBuildingEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setArea(bigDecimal);// 给长度赋值
											splitBuildingList.add(cloneObject);
										}
									}
									if (CollectionUtils.isNotEmpty(volumeList)) {
										String jsonString = JSON.toJSONString(abmBuildingEntity);
										for (BigDecimal bigDecimal : volumeList) {
											AbmBuildingEntity cloneObject = JSON.parseObject(jsonString,
													AbmBuildingEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setVolume(bigDecimal);// 给体积赋值
											splitBuildingList.add(cloneObject);
										}
									}
									if (CollectionUtils.isNotEmpty(numList)) {
										String jsonString = JSON.toJSONString(abmBuildingEntity);
										for (BigDecimal bigDecimal : numList) {
											AbmBuildingEntity cloneObject = JSON.parseObject(jsonString,
													AbmBuildingEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setNum(bigDecimal);// 给数量赋值
											splitBuildingList.add(cloneObject);
										}
									}
								}
							}
							if (remove != null) {
								buildingList.remove(remove);// 删除被划分走的数据
							}
							if (CollectionUtils.isNotEmpty(splitBuildingList)) {
								// 把原始数据的id、nm赋值给第一条，防止原始数据关联的信息丢失
								AbmBuildingEntity abmBuildingEntity = splitBuildingList.get(0);
								abmBuildingEntity.setId(removeId);
								abmBuildingEntity.setNm(removeNm);
								// 保存拆分后数据
								buildingList.addAll(splitBuildingList);
							}
						}
					} else if (StringUtils.equalsIgnoreCase(type, AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_TREES)) {
						List<AbmTreesEntity> treeList = abmSplitHouseholdNodeVO.getTreeList();
						if (treeList != null && !treeList.isEmpty()) {
							AbmTreesEntity remove = null;
							Integer removeId = null;
							String removeNm = null;
							for (AbmTreesEntity abmTreesEntity : treeList) {
								String number = abmTreesEntity.getNm();
								if (StringUtils.equals(dataNm, number)) {// 找到被划分的数据
									BigDecimal num = abmTreesEntity.getNum();
									// 校验数据
									decimalCheck(num, numList);
									// 被删除数据
									remove = abmTreesEntity;
									removeId = abmTreesEntity.getId();
									removeNm = abmTreesEntity.getNm();
									// 拆分后数据
									if (CollectionUtils.isNotEmpty(numList)) {
										if (splitTreesList == null) {
											splitTreesList = new ArrayList<>();
										}
										String jsonString = JSON.toJSONString(abmTreesEntity);
										for (BigDecimal bigDecimal : numList) {
											AbmTreesEntity cloneObject = JSON.parseObject(jsonString,
													AbmTreesEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setNum(bigDecimal);// 给数量赋值
											splitTreesList.add(cloneObject);
										}
									}
								}
							}
							if (remove != null) {
								treeList.remove(remove);// 删除被划分走的数据
							}
							if (CollectionUtils.isNotEmpty(splitTreesList)) {
								// 把原始数据的id、nm赋值给第一条，防止原始数据关联的信息丢失
								AbmTreesEntity abmTreesEntity = splitTreesList.get(0);
								abmTreesEntity.setId(removeId);
								abmTreesEntity.setNm(removeNm);
								// 保存拆分后数据
								treeList.addAll(splitTreesList);
							}
						}
					} else if (StringUtils.equalsIgnoreCase(type,
							AbmHouseholdConst.SPLIT_HOUSEHOLD_DATA_TYPE_DECORATION)) {
						List<AbmHousesDecorationEntity> housesDecorationList = abmSplitHouseholdNodeVO
								.getHousesDecorationList();
						if (housesDecorationList != null && !housesDecorationList.isEmpty()) {
							AbmHousesDecorationEntity remove = null;
							Integer removeId = null;
							String removeNm = null;
							for (AbmHousesDecorationEntity abmHousesDecorationEntity : housesDecorationList) {
								String number = abmHousesDecorationEntity.getNm();
								if (StringUtils.equals(dataNm, number)) {// 找到被划分的数据
									BigDecimal area = abmHousesDecorationEntity.getArea();
									// 校验数据
									decimalCheck(area, areaList);
									// 被删除数据
									remove = abmHousesDecorationEntity;
									removeId = abmHousesDecorationEntity.getId();
									removeNm = abmHousesDecorationEntity.getNm();
									// 拆分后数据
									if (CollectionUtils.isNotEmpty(areaList)) {
										if (splitHousesDecorationList == null) {
											splitHousesDecorationList = new ArrayList<>();
										}
										String jsonString = JSON.toJSONString(abmHousesDecorationEntity);
										for (BigDecimal bigDecimal : areaList) {
											AbmHousesDecorationEntity cloneObject = JSON.parseObject(jsonString,
													AbmHousesDecorationEntity.class);
											cloneObject.setId(null);// 清空id
											cloneObject.setNm(Randomizer.generCode(10));// 重置nm
											cloneObject.setArea(bigDecimal);// 给面积赋值
											splitHousesDecorationList.add(cloneObject);
										}
									}
								}
							}
							if (remove != null) {
								housesDecorationList.remove(remove);// 删除被划分走的数据
							}
							if (CollectionUtils.isNotEmpty(splitHousesDecorationList)) {
								// 把原始数据的id、nm赋值给第一条，防止原始数据关联的信息丢失
								AbmHousesDecorationEntity abmHousesDecorationEntity = splitHousesDecorationList.get(0);
								abmHousesDecorationEntity.setId(removeId);
								abmHousesDecorationEntity.setNm(removeNm);
								// 保存拆分后数据
								housesDecorationList.addAll(splitHousesDecorationList);
							}
						}
					}else if("facilities".equals(type)){
						List<Map> newFacilitiesList = abmSplitHouseholdNodeVO.getFacilitiesList();
						if (newFacilitiesList != null && !newFacilitiesList.isEmpty()) {
							Map remove = null;
							Integer removeId = null;
							String removeNm = null;
							for (Map s : newFacilitiesList) {
								String number = (String) s.get("nm");
								if (StringUtils.equals(dataNm, number)) {// 找到被划分的数据
									BigDecimal area = (BigDecimal) s.get("area");
									// 校验数据
									decimalCheck(area, areaList);
									// 被删除数据
									remove = s;
									removeId = (Integer) s.get("id");
									removeNm = (String) s.get("nm");
									// 拆分后数据
									if (CollectionUtils.isNotEmpty(areaList)) {
										String jsonString = JSON.toJSONString(s);
										for (BigDecimal bigDecimal : areaList) {
											Map cloneObject = JSON.parseObject(jsonString, Map.class);
											cloneObject.put("id", null);// 清空id
											cloneObject.put("nm", Randomizer.generCode(10));
											cloneObject.put("area", bigDecimal);
											facilitiesList.add(cloneObject);
										}
									}
								}
							}
							if (remove != null) {
								newFacilitiesList.remove(remove);// 删除被划分走的数据
							}
							if (CollectionUtils.isNotEmpty(facilitiesList)) {
								// 把原始数据的id、nm赋值给第一条，防止原始数据关联的信息丢失
								Map m = facilitiesList.get(0);
								m.put("id", removeId);
								m.put("nm", removeNm);
								// 保存拆分后数据
								newFacilitiesList.addAll(facilitiesList);
							}
						}
					}else if("homestead".equals(type)){
						List<Map> newHomesteadList = abmSplitHouseholdNodeVO.getHomesteadList();
						if (newHomesteadList != null && !newHomesteadList.isEmpty()) {
							Map remove = null;
							Integer removeId = null;
							String removeNm = null;
							for (Map s : newHomesteadList) {
								String number = (String) s.get("nm");
								if (StringUtils.equals(dataNm, number)) {// 找到被划分的数据
									BigDecimal area = (BigDecimal) s.get("area");
									// 校验数据
									decimalCheck(area, areaList);
									// 被删除数据
									remove = s;
									removeId = (Integer) s.get("id");
									removeNm = (String) s.get("nm");
									// 拆分后数据
									if (CollectionUtils.isNotEmpty(areaList)) {
										String jsonString = JSON.toJSONString(s);
										for (BigDecimal bigDecimal : areaList) {
											Map cloneObject = JSON.parseObject(jsonString, Map.class);
											cloneObject.put("id", null);// 清空id
											cloneObject.put("nm", Randomizer.generCode(10));
											cloneObject.put("area", bigDecimal);
											homesteadList.add(cloneObject);
										}
									}
								}
							}
							if (remove != null) {
								newHomesteadList.remove(remove);// 删除被划分走的数据
							}
							if (CollectionUtils.isNotEmpty(homesteadList)) {
								// 把原始数据的id、nm赋值给第一条，防止原始数据关联的信息丢失
								Map m = facilitiesList.get(0);
								m.put("id", removeId);
								m.put("nm", removeNm);
								// 保存拆分后数据
								newHomesteadList.addAll(homesteadList);
							}
						}
					}
				}
			}
		}

		// --------------------step3：重新保存数据,并设置过期时间
		String jsonString = JSON.toJSONString(list);
		boolean set = redisUtil.set(key, jsonString);
		if (!set) {
			throw new LyhtRuntimeException("数据缓存失败，请稍后重试！");
		}
		// --------------------step4：返回数据
		return list;
	}

	/**
	 * 小数校验（长、面积、体积等）
	 *
	 * @param preNumber
	 * @param number
	 */
	private void decimalCheck(BigDecimal preNumber, List<BigDecimal> number) {
		if (number != null && preNumber != null) {
			BigDecimal total = BigDecimal.valueOf(0);
			for (BigDecimal bigDecimal : number) {
				// 校验是否大于>=0.0001
				if (bigDecimal.compareTo(BigDecimal.valueOf(0.0001)) < 0) {
					throw new LyhtRuntimeException("小数必须>=0.0001");
				}
				// 校验小数位数是否超过4位
				String string = bigDecimal.stripTrailingZeros().toPlainString();
				int index = string.indexOf(".");
				int lenght = (index < 0 ? 0 : string.length() - index - 1);
				if (lenght > 4) {
					throw new LyhtRuntimeException("小数位数必须超过四位");
				}
				total = total.add(bigDecimal);
			}
			if (preNumber.compareTo(total) != 0) {
				throw new LyhtRuntimeException("拆分前总数与拆分后总数不一致");
			}
		}
	}

	/**
	 * 整数校验（数量）
	 *
	 * @param preNumber
	 * @param number
	 */
	private void numberCheck(BigDecimal preNumber, List<BigDecimal> number) {
		if (number != null) {
			BigDecimal total = BigDecimal.valueOf(0);
			for (BigDecimal bigDecimal : number) {
				// 校验是否是整数
				try {
					int intValue = bigDecimal.intValue();
					// 校验是否大于>=1
					if (intValue < 1) {
						throw new LyhtRuntimeException("整数必须>=1");
					}
					total = total.add(bigDecimal);
				} catch (Exception e) {
					throw new LyhtRuntimeException("数量必须是整数");
				}
			}
			if (preNumber.compareTo(total) != 0) {
				throw new LyhtRuntimeException("拆分前总数与拆分后总数不一致");
			}
		}
	}

	/**
	 * 退出分户操作
	 *
	 * @param ownerNm
	 * @return
	 */
	public boolean quitSplit(String splitOwnerNm) {
		// step1：校验
		if (splitOwnerNm == null) {
			throw new LyhtRuntimeException("退出分户--户主编码不能为空!");
		}
		AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(splitOwnerNm);
		if (abmOwnerEntity == null) {
			throw new LyhtRuntimeException("户主不存在！");
		}
//		String splitHouseholdState = abmOwnerEntity.getSplitHouseholdState();
//		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED)) {
//			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
//		}

		String key = AbmHouseholdConst.HOUSEHOLD_KEY + splitOwnerNm;
		Object object = redisUtil.get(key);
		if (object == null) {
			throw new LyhtRuntimeException("找不到对应的分户数据，无需退出！");
		}
		// step2：删除分户数据，退出分户操作
		redisUtil.del(key);
		// step3：返回
		return true;
	}

	/**
	 * 发起分户流程，并保存分户数据
	 *
	 * @param abmSplitHouseholdVO
	 */
	@Transactional(rollbackFor = Exception.class)
	public AbmSplitHouseholdEntity startProcess(HttpServletRequest request, String ownerNm) {
		if (ownerNm == null) {
			throw new LyhtRuntimeException("发起分户流程--户主编码不能为空!");
		}
		AbmSplitHouseholdProcessVO findByOwnerNm = abmSplitHouseholdDao.findByOwnerNm(ownerNm);
		if (findByOwnerNm == null) {
			throw new LyhtRuntimeException("户主不存在，无法发起流程!");
		}
		String splitHouseholdState = findByOwnerNm.getSplitHouseholdState();
		if (!StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_APPLY_APPROVED) && !StringUtils.equalsIgnoreCase(splitHouseholdState, AbmHouseholdConst.SPLIT_STATE_STORAGE)) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}
		String key = AbmHouseholdConst.HOUSEHOLD_KEY + ownerNm;
		Object object = redisUtil.get(key);
		if (object == null) {
			throw new LyhtRuntimeException(SplitHouseholdExceptionEnums.NOT_EXIST_OR_HAS_EXPIRED);
		}

		AbmSplitHouseholdEntity result = abmSplitHouseholdDao.getByOwnerNm(ownerNm);
		if (result == null) {
			throw new LyhtRuntimeException("请发起分户申请，并审批通过后，再做分户操作！");
		}
		try {
			// 发起分户流程
			ProcessOperateVO processOperateVO = new ProcessOperateVO();
			processOperateVO.setFlowPath(spiltHouseholdFlowPath);
			String ownerName = findByOwnerNm.getOwnerName();
			if (StringUtils.isNotBlank(ownerName)) {
				Map<String, String> data = new HashMap<>();
				data.put("name", ownerName + "-分户提交-" + ownerNm);
				processOperateVO.setData(data);
				log.debug("=====分户流程==========" + ownerName);
			}
			//processOperateVO.setUser("fc88cd7f-66fb-4541-bb2b-65dc385e5b4e");//未登录测试用
			String processId = null;
			try {
				processId = processOperateService.processStart(processOperateVO, request);
			} catch (Exception e) {
				throw new LyhtRuntimeException("流程引擎请求超时，请检查网络后重试！");
			}

			// 修改分户信息
			result.setProcessId(processId);
			result.setSplitJsonData(object.toString());
			result = abmSplitHouseholdDao.save(result);

			// 修改分户状态为“分户流程处理中”
			AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(ownerNm);
			abmOwnerEntity.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_DOING);
			abmOwnerService.save(abmOwnerEntity);
		} catch (Exception e) {
			throw new LyhtRuntimeException("流程发起失败!");
		}
		return result;
	}

	@Autowired private AbmOwnerDao abmOwnerDao;

	/**
	 * 分户流程通过，进行分户操作
	 *
	 * @param ownerNm
	 */
	@Transactional(rollbackFor = Exception.class)
	public void approvedCallback(String processId) {
		AbmSplitHouseholdEntity findByProcessId = abmSplitHouseholdDao.findByProcessId(processId);
		if (findByProcessId != null) {
			String splitJsonData = findByProcessId.getSplitJsonData();
			AbmOwnerEntity infoOwner = abmOwnerDao.findByNm(findByProcessId.getOwnerNm());
			List<AbmSplitHouseholdNodeVO> list = JSON.parseArray(splitJsonData, AbmSplitHouseholdNodeVO.class);
			int nonAp = 0, ap = 0;
			if (CollectionUtils.isNotEmpty(list)) {
				for (AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO : list) {
					AbmOwnerEntity owner = abmSplitHouseholdNodeVO.getOwner();
					owner.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_HAVE_LEDGER);
					abmOwnerService.save(owner);
					List<AbmFamilyEntity> familyList = abmSplitHouseholdNodeVO.getFamilyList();
					//农业人口
					if("7EC014AB3E".equals(owner.getHouseholdsType())) { ap++; }else { nonAp++; }
					if (CollectionUtils.isNotEmpty(familyList)) {
						for(AbmFamilyEntity s : familyList){
							AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(s.getOwnerNm());
							if(null != abmOwnerEntity){
								abmOwnerEntity.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_HAVE_LEDGER);
								abmOwnerService.save(abmOwnerEntity);
							}
						}
						abmFamilyService.saveAll(familyList);
					}
					List<AbmLandEntity> landList = abmSplitHouseholdNodeVO.getLandList();
					if (CollectionUtils.isNotEmpty(landList)) {
						abmLandService.saveAll(landList);
					}
					List<AbmHouseEntity> houseList = abmSplitHouseholdNodeVO.getHouseList();
					if (CollectionUtils.isNotEmpty(houseList)) {
						abmHouseService.saveAll(houseList);
					}
					List<AbmTreesEntity> treeList = abmSplitHouseholdNodeVO.getTreeList();
					if (CollectionUtils.isNotEmpty(treeList)) {
						abmTreesService.saveAll(treeList);
					}
					List<AbmBuildingEntity> buildingList = abmSplitHouseholdNodeVO.getBuildingList();
					if (CollectionUtils.isNotEmpty(buildingList)) {
						abmBuildingService.saveAll(buildingList);
					}
					List<AbmHousesDecorationEntity> housesDecorationList = abmSplitHouseholdNodeVO
							.getHousesDecorationList();
					if (CollectionUtils.isNotEmpty(housesDecorationList)) {
						abmHouseDecorationService.saveAll(housesDecorationList);
					}
				}
				if(null != infoOwner){
					infoOwner.setImmigrant(1);
					infoOwner.setAp(ap);
					infoOwner.setNonAp(nonAp);
					infoOwner.setIPopulation(ap + nonAp);
					abmOwnerDao.save(infoOwner);
				}
			}
		}
	}

	@Autowired
	private MessageNoticeService messageNoticeService;

	@Autowired
	private ProtocolInfoDao dao;

	/**
	 * 分户流程回调
	 * @param processId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean splitCallback(String processId, HttpServletRequest request) {
		AbmSplitHouseholdProcessVO findProcessByProcessId = abmSplitHouseholdDao.findProcessByProcessId(processId);
		if (findProcessByProcessId == null) {
			return false;
		}
		String ownerNm = findProcessByProcessId.getOwnerNm();
		AbmOwnerEntity owner = abmOwnerService.findByNm(ownerNm);
		if (owner == null) {
			return false;
		}
		String processStatus = findProcessByProcessId.getProcessStatus();
		System.err.println("分户流程回调状态-" + processStatus + ",processId:" + processId);
		Map data = new HashMap(2);
		data.put("name", "分戶提交");
		data.put("info", findProcessByProcessId);
		String jsonString = JSON.toJSONString(data);
		String sessionNm = SystemUtil.getLoginStaffNm(request);
		if (StringUtils.equalsIgnoreCase(processStatus, ProcessConstant.PROCESS_APPROVED)) {
			approvedCallback(processId);
			owner.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_HAVE_LEDGER);
			abmOwnerService.save(owner);
			try{
				messageNoticeService.sendMessageNoticeByUser(sessionNm, ownerNm, "分戶提交通过", jsonString, "SHOW");
				messageNoticeService.sendMessageNoticeByUser(sessionNm, sessionNm, "分戶提交通过", jsonString, "SHOW");
				messageNoticeService.sendMessageNoticeByRole(sessionNm, "BCA5AA572F", "分戶提交通过", jsonString, "SHOW");
			}catch (Exception e) {
				System.err.println("消息推送-个人财产分户提交确认通过，错误原因：" + e.getMessage());
			}
			return true;
		}else if (StringUtils.equalsIgnoreCase(processStatus, ProcessConstant.PROCESS_REJECTED)) {
			owner.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_STORAGE);
			abmOwnerService.save(owner);
			try{
				messageNoticeService.sendMessageNoticeByUser(sessionNm, ownerNm, "分戶提交拒绝", jsonString, "SHOW");
				messageNoticeService.sendMessageNoticeByUser(sessionNm, sessionNm, "分戶提交拒绝", jsonString, "SHOW");
				messageNoticeService.sendMessageNoticeByRole(sessionNm, "BCA5AA572F", "分戶提交拒绝", jsonString, "JUMP");
			}catch (Exception e) {
				System.err.println("消息推送-个人财产分户提交确认拒绝，错误原因：" + e.getMessage());
			}
			return true;
		}
		return false;
	}

	/**
	 * 获取分户--流程详情
	 *
	 * @param processId
	 * @return
	 */
	public AbmSplitHouseholdVO getSplitInfo(String ownerNm, String processId) {
		AbmSplitHouseholdProcessVO info = null;
		if (StringUtils.isNotBlank(ownerNm)) {
			info = abmSplitHouseholdDao.findByOwnerNm(ownerNm);
		}
		if (StringUtils.isNotBlank(processId)) {
			info = abmSplitHouseholdDao.findProcessByProcessId(processId);
		}
		if (info != null) {
			String jsonString = JSON.toJSONString(info);
			AbmSplitHouseholdVO abmSplitHouseholdVO = JSON.parseObject(jsonString, AbmSplitHouseholdVO.class);
			String splitJsonData = info.getSplitJsonData();
			if (StringUtils.isNotBlank(splitJsonData)) {
				List<AbmSplitHouseholdNodeVO> nodes = JSON.parseArray(splitJsonData, AbmSplitHouseholdNodeVO.class);
				abmSplitHouseholdVO.setNodes(nodes);
			}
			return abmSplitHouseholdVO;
		}
		return null;
	}

	/**
	 * 获取分户申请--流程详情
	 * @param applyProcessId
	 * @return
	 */
	public Map getSplitApplyInfo(String applyProcessId) {
		Map data = new HashMap(2);
		AbmSplitHouseholdProcessVO info = abmSplitHouseholdDao.findProcessByApplyProcessId(applyProcessId);
		data.put("info", info);
		if(null != info && null != info.getSignFileUrl()){
			data.put("fileList", abmSplitHouseholdDao.findProcessFilesByApplyProcessId(info.getSignFileUrl()));
		}
		return data;
	}

	public AbmSplitHouseholdNodeVO getAbmSplitHouseholdNodeVO(String ownerNm){
		AbmSplitHouseholdNodeVO abmSplitHouseholdNodeVO = new AbmSplitHouseholdNodeVO();
		AbmOwnerEntity owner = abmOwnerService.findByNm(ownerNm);
		List<AbmFamilyEntity> familyList = abmFamilyService.findByOwnerNm(ownerNm);
		List<AbmLandEntity> landList = abmLandService.findByOwnerNm(ownerNm);
		List<AbmHouseEntity> houseList = abmHouseService.findByOwnerNm(ownerNm);
		List<AbmTreesEntity> treeList = abmTreesService.findByOwnerNm(ownerNm);
		List<AbmBuildingEntity> buildingList = abmBuildingService.findByOwnerNm(ownerNm);
		List<AbmHousesDecorationEntity> housesDecorationList = abmHouseDecorationService.findByOwnerNm(ownerNm);
		abmSplitHouseholdNodeVO.setOwner(owner);
		abmSplitHouseholdNodeVO.setFamilyList(familyList);
		abmSplitHouseholdNodeVO.setLandList(landList);
		abmSplitHouseholdNodeVO.setHouseList(houseList);
		abmSplitHouseholdNodeVO.setTreeList(treeList);
		abmSplitHouseholdNodeVO.setBuildingList(buildingList);
		abmSplitHouseholdNodeVO.setHousesDecorationList(housesDecorationList);
		abmSplitHouseholdNodeVO.setFacilitiesList(service.findByOwnerNmAgriculturalFacilities(ownerNm, "t_info_agricultural_facilities_impl"));
		abmSplitHouseholdNodeVO.setHomesteadList(service.findByOwnerNmHomestead(ownerNm, "t_info_homestead_impl"));
		return abmSplitHouseholdNodeVO;
	}

	/*
	 * @Description: 分户暂存数据存储
	 * @Author: xzp
	 * @Date: 2020/9/7 15:23
	 **/
	public List<AbmSplitHouseholdNodeVO> storageDataHousehold(List<AbmSplitHouseholdNodeVO> abmSplitHouseholdNodeVO, String ownerNm){
		if(null != abmSplitHouseholdNodeVO && !abmSplitHouseholdNodeVO.isEmpty() && abmSplitHouseholdNodeVO.size() > 0){
			List<AbmSplitHouseholdNodeVO> list = new ArrayList<>(abmSplitHouseholdNodeVO.size());
			for(AbmSplitHouseholdNodeVO ss : abmSplitHouseholdNodeVO){
				if(null != ss.getOwner() && null != ss.getOwner().getNm()){
					AbmSplitHouseholdNodeVO abm = getAbmSplitHouseholdNodeVO(ss.getOwner().getNm());
					list.add(getAbmSplitHouseholdInfo(ss, abm));
				}
			}
			String key = AbmHouseholdConst.HOUSEHOLD_KEY + ownerNm;
			String jsonString = JSON.toJSONString(list);
			redisUtil.set(key, jsonString);
			return list;
		}else {
			return abmSplitHouseholdNodeVO;
		}
	}

	public AbmSplitHouseholdNodeVO getAbmSplitHouseholdInfo(AbmSplitHouseholdNodeVO abm, AbmSplitHouseholdNodeVO newAbm){
		if(null != abm && null != newAbm){
			try{
				if(abm.getFamilyList() != null && newAbm.getFamilyList() != null){
					for(AbmFamilyEntity s1 : abm.getFamilyList()){
						for(AbmFamilyEntity s2 : newAbm.getFamilyList()){
							if(s1.getId().equals(s2.getId()) && s1.getNm().equals(s2.getNm())){ s1.setFileCount(s2.getFileCount()); }
						}
					}
				}
				if(abm.getLandList() != null && newAbm.getLandList() != null){
					for(AbmLandEntity s1 : abm.getLandList()){
						for(AbmLandEntity s2 : newAbm.getLandList()){
							if(s1.getId().equals(s2.getId()) && s1.getNm().equals(s2.getNm())){ s1.setFileCount(s2.getFileCount()); }
						}
					}
				}
				if(abm.getHouseList() != null && newAbm.getHouseList() != null){
					for(AbmHouseEntity s1 : abm.getHouseList()){
						for(AbmHouseEntity s2 : newAbm.getHouseList()){
							if(s1.getId().equals(s2.getId()) && s1.getNm().equals(s2.getNm())){ s1.setFileCount(s2.getFileCount()); }
						}
					}
				}
				if(abm.getTreeList() != null && newAbm.getTreeList() != null){
					for(AbmTreesEntity s1 : abm.getTreeList()){
						for(AbmTreesEntity s2 : newAbm.getTreeList()){
							if(s1.getId().equals(s2.getId()) && s1.getNm().equals(s2.getNm())){ s1.setFileCount(s2.getFileCount()); }
						}
					}
				}
				if(abm.getBuildingList() != null && newAbm.getBuildingList() != null){
					for(AbmBuildingEntity s1 : abm.getBuildingList()){
						for(AbmBuildingEntity s2 : newAbm.getBuildingList()){
							if(s1.getId().equals(s2.getId()) && s1.getNm().equals(s2.getNm())){ s1.setFileCount(s2.getFileCount()); }
						}
					}
				}
				if(abm.getHousesDecorationList() != null && newAbm.getHousesDecorationList() != null){
					for(AbmHousesDecorationEntity s1 : abm.getHousesDecorationList()){
						for(AbmHousesDecorationEntity s2 : newAbm.getHousesDecorationList()){
							if(s1.getId().equals(s2.getId()) && s1.getNm().equals(s2.getNm())){ s1.setFileCount(s2.getFileCount()); }
						}
					}
				}
				if(abm.getFacilitiesList() != null && newAbm.getFacilitiesList() != null){
					for(Map s1 : abm.getFacilitiesList()){
						for(Map s2 : newAbm.getFacilitiesList()){
							if(s1.get("id").equals(s2.get("id")) && s1.get("nm").equals(s2.get("nm"))){ s1.put("fileCount", s2.get("fileCount")); }
						}
					}
				}
				if(abm.getHomesteadList() != null && newAbm.getHomesteadList() != null){
					for(Map s1 : abm.getHomesteadList()){
						for(Map s2 : newAbm.getHomesteadList()){
							if(s1.get("id").equals(s2.get("id")) && s1.get("nm").equals(s2.get("nm"))){ s1.put("fileCount", s2.get("fileCount")); }
						}
					}
				}
			}catch (Exception e){
				throw new LyhtRuntimeException("暂存数据报错，错误原因：" + e.getMessage());
			}
		}
		return abm;
	}

	/*
	 * @Description: 分户暂存功能
	 * @Author: xzp
	 * @Date: 2020/8/4 8:30
	 * @param 》ownerNm:发起人内码,list:指标参数
	 * @return 》
	 **/
	@Transactional
	public void storageHousehold(String ownerNm, List<StorageHouseholdVO> list){
		AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(ownerNm);
		if(null != abmOwnerEntity){
			if(null != list && !list.isEmpty() && list.size() > 0){
				abmOwnerEntity.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_STORAGE);
			}else {
				abmOwnerEntity.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_NORMAL);
			}
			abmOwnerService.save(abmOwnerEntity);
		}
	}

	/*
	 * @Description: 清除分户暂存功能
	 * @Author: xzp
	 * @Date: 2020/8/4 8:30
	 * @param 》ownerNm:发起人内码
	 * @return 》
	 **/
	@Transactional
	public void clearStorageHousehold(String ownerNm){
		String key = AbmHouseholdConst.HOUSEHOLD_KEY + ownerNm;
		redisUtil.set(key, null);
	}

}