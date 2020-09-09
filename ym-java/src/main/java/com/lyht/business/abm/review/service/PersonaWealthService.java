package com.lyht.business.abm.review.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.lyht.business.abm.removal.dao.*;
import com.lyht.business.abm.removal.entity.*;
import com.lyht.business.abm.removal.service.*;
import com.lyht.business.abm.removal.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.history.entity.OwnerInfoHistory;
import com.lyht.business.abm.history.service.OwnerInfoHistoryService;
import com.lyht.business.abm.landAllocation.dao.LandPoolDao;
import com.lyht.business.abm.landAllocation.dao.LandPoolProcessDao;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.landAllocation.service.LandPoolProcessService;
import com.lyht.business.abm.production.service.ProductionAuditService;
import com.lyht.business.abm.review.dao.PersonalWealthDao;
import com.lyht.business.abm.review.dao.PersonalWealthDataDao;
import com.lyht.business.abm.review.entity.PersonalWealth;
import com.lyht.business.abm.review.entity.PersonalWealthData;
import com.lyht.business.abm.review.vo.OwnerDocGetInfoVO;
import com.lyht.business.abm.review.vo.OwnerDocInfoVO;
import com.lyht.business.abm.review.vo.PersonaWealthIndividualObjectVO;
import com.lyht.business.abm.review.vo.PersonaWealthIndividualVO;
import com.lyht.business.abm.review.vo.PersonalWealthDataVO;
import com.lyht.business.abm.review.vo.PersonalWealthGetByIdVO;
import com.lyht.business.abm.review.vo.PersonalWealthInfoVO;
import com.lyht.business.abm.review.vo.PersonalWealthVO;
import com.lyht.business.abm.signed.dao.TotalStateDao;
import com.lyht.business.abm.signed.entity.TotalState;
import com.lyht.business.abm.signed.service.ProtocolInfoService;
import com.lyht.business.message.service.MessageNoticeService;
import com.lyht.business.process.dao.ProcessDao;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.pub.dao.PubDictValueDao;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

/**
 * 实物指标复核申请（个人财产）
 * 
 * @author wzw
 *
 */
@Service
@Transactional
public class PersonaWealthService {
	
	@Autowired
	private MessageNoticeService messageNoticeService ;
	
	@Autowired
	private AbmAgriculturalFacilitiesDao agriculturalFacilitiesDao;
	
	@Autowired
	private AbmHomesteadDao homeSteadDao;
	
	@Autowired
	private ProcessDao processDao;
	
	@Autowired
	private PersonalWealthDao dao;
	
	@Autowired
	private AbmOwnerDao ownerDao;
	
	@Autowired
	private AbmFamilyDao familyDao;
	
	@Autowired
	private AbmLandDao landDao;
	@Autowired
	private AbmLandService abmLandService;
	
	@Autowired
	private AbmHousesDao houseDao;
	
	@Autowired
	private AbmTreesDao treesDao;
	
	@Autowired
	private AbmBuildingDao buildingDao;
	
	@Autowired
	private AbmHousesDecorationDao decorationDao;
	
	@Autowired
	private AbmInfoIndividualDao infoIndividualDao;
	
	@Autowired
	private PubDictValueDao dictDao;
	@Autowired
	private AbmFamilyService abmFamilyService;
	@Autowired
	private AbmHouseService abmHouseService;
	@Autowired
	private AbmTreesService abmTreesService;
	@Autowired
	private AbmBuildingService abmBuildingService;
	@Autowired
	private AbmHouseDecorationService abmHouseDecorationService;
	
	@Autowired
	private ProductionAuditService auditService;
	
	@Autowired
	private LandPoolProcessService landPoolProcessService;
	
	@Autowired
	private PersonalWealthDataDao dataDao;
	
	@Autowired
	private TotalStateDao totalStateDao;
	
	@Autowired
	private ProtocolInfoService protocolInfoService;
	
	@Autowired
	private LandPoolProcessDao landPoolProcessDao;
	
	@Value("${iwind.process.flow.path.ownerVerify}") // 注入配置的流程路径（eclipse debug请转unicode，部署后无影响）
	private String physicalFlowPath;
	
	@Value("${iwind.process.flow.path.change}") // 注入配置的流程路径（eclipse debug请转unicode，部署后无影响）
	private String physicalFlowPath2;//变更申请流程

	@Autowired
	private ProcessOperateService processOperateService; // 注入流程服务实现类
	
	@Autowired
	private LandPoolDao poolDao;
	
	@Autowired
	private OwnerInfoHistoryService historyService;
	
	@Transactional
	public LyhtResultBody<Object> updateTimeCaps(String ownerNm,Integer timeCaps){
		dao.updateTimeCaps(ownerNm, timeCaps);
		return new LyhtResultBody<>();
	}
	
	/**
	 * 跳过复核申请直接界定
	 * @param ids
	 * @return
	 */
	@Transactional
	public LyhtResultBody<Object> DirectlyToComplete(String ids){
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		for (Integer i : idList) {
			dao.updateFhStateOnOwnerById(i, 4);//总复核状态 改为4  拒绝
			ownerDao.updateAbmFamilyById(i, 2);//公示完成
			ownerDao.resetStateBydId(i);//初始化生产安置状态
		}
		return new LyhtResultBody<>();
	}
	
	public void saveHistory(String ownerNm) {
		OwnerInfoHistory infoHistory = new OwnerInfoHistory();
		infoHistory.setOwnerNm(ownerNm);
		AbmOwnerEntity findByNm = ownerDao.findByNm(ownerNm);//权属人信息
		if(findByNm!=null) {
			infoHistory.setInfoJson(JSON.toJSONString(findByNm));
		}
		List<AbmFamilyEntity> family = abmFamilyService.findByOwnerNm(ownerNm);//家庭成员
		if(family.size()>0) {
			infoHistory.setImmigrantPopulationJson(JSON.toJSONString(family));
		}
		List<AbmLandEntity> land = abmLandService.findByOwnerNm(ownerNm);//土地
		if(land.size()>0) {
			infoHistory.setLandJson(JSON.toJSONString(land));
		}
		List<AbmHouseEntity> house = abmHouseService.findByOwnerNm(ownerNm);//房子
		if(house.size() > 0) {
			infoHistory.setHouseJson(JSON.toJSONString(house));
		}
		List<AbmTreesEntity> tree = abmTreesService.findByOwnerNm(ownerNm);//树
		if(tree.size() > 0) {
			infoHistory.setTreeJson(JSON.toJSONString(tree));
		}
		List<AbmBuildingEntity> build = abmBuildingService.findByOwnerNm(ownerNm);//建筑
		if(build.size() > 0) {
			infoHistory.setOtherJson(JSON.toJSONString(build));
		}
		List<AbmHousesDecorationEntity> decoration = abmHouseDecorationService.findByOwnerNm(ownerNm);
		if(decoration.size() > 0) {
			infoHistory.setFitmentJson(JSON.toJSONString(decoration));
		}
		List<AbmInfoIndividualEntity> infoIndividual = infoIndividualDao.findByOwnerNm(ownerNm);
		if(infoIndividual.size() > 0) {
			infoHistory.setIndividualHouseholdJson(JSON.toJSONString(infoIndividual));
		}
		List<AbmAgriculturalFacilitiesEntity> list = agriculturalFacilitiesDao.findByOwnerNm(ownerNm);
		if(list.size() > 0) {
			infoHistory.setAgriculturalFacilitiesJson(JSON.toJSONString(list));
		}
		List<AbmHomesteadEntity> homesteads = homeSteadDao.findByOwnerNm(ownerNm);
		if(homesteads.size() > 0) {
			infoHistory.setHomesteadJson(JSON.toJSONString(homesteads));
		}
		historyService.save(infoHistory);
	}
	
	/**
	 * 根据流程id 查询 提交变更的内容
	 */
	public LyhtResultBody<PersonalWealthData> findByChangeProcessId(String taskId){
		PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
		PersonalWealthData personalWealthData = dataDao.findByMasterNm(personalWealth.getNm());
		return new LyhtResultBody<>(personalWealthData);
	}
	
	/**
	 * 根据id查询移民档案信息
	 * @param id
	 * @return
	 */
	public LyhtResultBody<PersonalWealthInfoVO> getById(String taskId,Integer id){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByReviewProcessId(taskId);
			if(personalWealth==null) {
				personalWealth = dao.findByChangeProcessId(taskId);
			}
			if(personalWealth!=null) {
				id = personalWealth.getId();
			}
		}
		// 参数校验
		if (StringUtils.isBlank(id+"")) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		PersonalWealthGetByIdVO personalWealth = dao.findPersonalWealthById(id);//用VO接收dao 反参
		PersonalWealthInfoVO info = new PersonalWealthInfoVO();//准备class 将接口类转成class类 对字段进行操作
		if(personalWealth!= null) {//将拼接的征地范围 复核项目  循环查出拼接
			StringBuffer reviewProject = new StringBuffer("");
			StringBuffer scope =  new StringBuffer("");
			if(StringUtils.isNotBlank(personalWealth.getReviewProject())) {
				String [] project = personalWealth.getReviewProject().split(",");
				
				for (String str : project) {
					PubDictValue dict = dictDao.findByNm(str);
					reviewProject.append(dict.getName()+",");
				}
			}
			if(StringUtils.isNoneBlank(personalWealth.getScope())) {
				if(StringUtils.isNotBlank(personalWealth.getReviewProject())) {
					String [] scopes = personalWealth.getScope().split(",");
					
					for (String str : scopes) {
						PubDictValue dict = dictDao.findByNm(str);
						scope.append(dict.getName()+",");
					}
				}
			}
			//将拼好的数据 存进返回对象中
			info.setReviewProject(reviewProject.toString().substring(0,reviewProject.length()-1));
			if(StringUtils.isNotBlank(scope)) {
				info.setScope(scope.toString().substring(0,scope.length()-1));
			}
			info.setName(personalWealth.getName());
			info.setNm(personalWealth.getNm());
			info.setApplicationNumber(personalWealth.getApplicationNumber());
			info.setIdCard(personalWealth.getIdCard());
			info.setReviewer(personalWealth.getReviewer());
			info.setReviewReason(personalWealth.getReviewReason());
			info.setReviewFileName(personalWealth.getReviewFileName());
			info.setReviewFileUrl(personalWealth.getReviewFileUrl());
			info.setChangeFileName(personalWealth.getChangeFileName());
			info.setChangeFileUrl(personalWealth.getChangeFileUrl());
		}
		return new LyhtResultBody<>(info);
	}
	
	/**
	 * 查询权属个体户信息
	 * @param ownerNm
	 * @param lyhtPageVO 分页查询
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthIndividualVO>> findByOwnerNmIndividual(String ownerNm,LyhtPageVO lyhtPageVO){
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);//分页参数
		Page<PersonaWealthIndividualVO> page = dao.findPersonaWealthIndividual(ownerNm,pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<PersonaWealthIndividualVO> list = (List<PersonaWealthIndividualVO>) JSON.parse(jsonString);//转成VO
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	@PersistenceContext private EntityManager entityManager;

	public List<Map> findByOwnerNmAgriculturalFacilities(String ownerNm, String tableName){
		Query dataQuery = entityManager.createNativeQuery("SELECT tt.id, tt.nm, re.merger_name AS mergerName, pro.name AS " +
				"projectName, dscope.name AS scopeName, tt.unit, SUM(tt.num) AS num, tt.owner_nm AS ownerNm,count(file.id) AS fileCount " +
				" FROM " + tableName + " tt " +
				" LEFT JOIN pub_project pro ON tt.project_nm = pro.id " +
				" LEFT JOIN pub_region re ON tt.region = re.city_code " +
				" LEFT JOIN pub_dict_value dscope ON dscope.nm = tt.scope " +
				" LEFT JOIN pub_files file ON file.table_name = \'"+ tableName +"\' AND file.table_pk_column = tt.nm " +
				" WHERE tt.owner_nm = \'" + ownerNm + "\' " +
				" GROUP BY tt.id ");
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = dataQuery.getResultList();
		return list;
	}

	public List<Map> findByOwnerNmHomestead(String ownerNm, String tableName){
		Query dataQuery = entityManager.createNativeQuery("SELECT tt.id, tt.nm, re.merger_name AS mergerName, pro.name AS " +
				"projectName, dscope.name AS scopeName, tt.owner_nm AS ownerNm,count(file.id) AS fileCount, SUM(tt.area) AS area " +
				" FROM " + tableName + " tt " +
				" LEFT JOIN pub_project pro ON tt.project_nm = pro.id " +
				" LEFT JOIN pub_region re ON tt.region = re.city_code " +
				" LEFT JOIN pub_dict_value dscope ON dscope.nm = tt.scope " +
				" LEFT JOIN pub_files file ON file.table_name = \'"+ tableName +"\' AND file.table_pk_column = tt.nm " +
				" WHERE tt.owner_nm = \'" + ownerNm + "\' " +
				" GROUP BY tt.id ");
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map> list = dataQuery.getResultList();
		return list;
	}

	/**
	 * 查询权属人房屋装修信息
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthDecorationVO>> findByOwnerNmDecoration(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);//分页参数
		Page<PersonaWealthDecorationVO> page = decorationDao.findByOwnerNmOfPersonaWealth(ownerNm,pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<PersonaWealthDecorationVO> list = (List<PersonaWealthDecorationVO>) JSON.parse(jsonString);//转VO
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 查询权属人其他附属建筑信息
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthBuildingVO>> findByOwnerNmBuilding(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);//分页参数
		Page<PersonaWealthBuildingVO> page = buildingDao.findByOwnerNmOfPersonaWealth(ownerNm,pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<PersonaWealthBuildingVO> list = (List<PersonaWealthBuildingVO>) JSON.parse(jsonString);//转VO
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 查询权属人零星树木信息
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthTreesVO>> findByOwnerNmTrees(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<PersonaWealthTreesVO> page = treesDao.findByOwnerNmOfPersonaWealth(ownerNm,pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<PersonaWealthTreesVO> list = (List<PersonaWealthTreesVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 查询权属人房屋信息
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthHouseVO>> findByOwnerNmHouse(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<PersonaWealthHouseVO> page = houseDao.findByOwnerNmOfPersonaWealth(ownerNm,pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<PersonaWealthHouseVO> list = (List<PersonaWealthHouseVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<List<PersonaWealthHouseVO>>(list, pageVO);
	}
	
	/**
	 * 查询权属人的土地信息
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthLandVO>> findByOwnerNmLand(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<PersonaWealthLandVO> page = landDao.findByOwnerNmOfPersonaWealth(ownerNm,pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<PersonaWealthLandVO> list = (List<PersonaWealthLandVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 查询权属人的家庭成员
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthFamilyVO>> findByOwnerNmFamily(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){//查询权属人的家庭成员
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<PersonaWealthFamilyVO> page = familyDao.findByOwnerNmOfPersonaWealth(ownerNm,pageable);
		List<PersonaWealthFamilyVO> list = (List<PersonaWealthFamilyVO>) page.getContent();
		if(list.size() == 1) {
			for (PersonaWealthFamilyVO personaWealthFamilyVO : list) {
				if(StringUtils.isBlank(personaWealthFamilyVO.getName())) {
					return new LyhtResultBody<>();
				}
			}
		}
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 根据权属人nm查询移民档案信息
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<OwnerDocInfoVO> getOwnerDocInfo(String taskId,String ownerNm) {
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByReviewProcessId(taskId);
			if(personalWealth==null) {
				personalWealth = dao.findByChangeProcessId(taskId);
			}
			ownerNm = personalWealth.getOwnerNm();
		}
		OwnerDocGetInfoVO ownerDocGetInfoVO = dao.getOwnerDocInfo(ownerNm);//通过权属人nm查询权属人信息 vo接受dao反参
		StringBuffer reviewProject = new StringBuffer("");
		OwnerDocInfoVO ownerDocInfoVO = new OwnerDocInfoVO();//实例class对象 将VO 转成class  对拼接参数进行操作
		if(ownerDocGetInfoVO!=null) {//将拼接而成的复核项目 查出 排好
			if(StringUtils.isNotBlank(ownerDocGetInfoVO.getReviewProject())){
				String [] project = ownerDocGetInfoVO.getReviewProject().split(",");
				
				for (String str : project) {
					PubDictValue dict = dictDao.findByNm(str);
					reviewProject.append(dict.getName()+",");
				}
			}
			ownerDocInfoVO.setReviewProject(reviewProject.toString());
			ownerDocInfoVO.setOwnerName(ownerDocGetInfoVO.getOwnerName());
			ownerDocInfoVO.setOwnerNm(ownerDocGetInfoVO.getOwnerNm());
			ownerDocInfoVO.setMergerName(ownerDocGetInfoVO.getMergerName());
			ownerDocInfoVO.setFamilyCount(ownerDocGetInfoVO.getFamilyCount());
			ownerDocInfoVO.setPopulation(ownerDocGetInfoVO.getPopulation());
			ownerDocInfoVO.setHouseArea(ownerDocGetInfoVO.getHouseArea());
			ownerDocInfoVO.setCityCode(ownerDocGetInfoVO.getCityCode());
			ownerDocInfoVO.setHouseDecorationArea(ownerDocGetInfoVO.getHouseDecorationArea());
			ownerDocInfoVO.setLandArea(ownerDocGetInfoVO.getLandArea());
			ownerDocInfoVO.setIdCard(ownerDocGetInfoVO.getIdCard());
			ownerDocInfoVO.setScopeName(ownerDocGetInfoVO.getScopeName());
			ownerDocInfoVO.setNationalName(ownerDocGetInfoVO.getNationalName());
			ownerDocInfoVO.setGerder(ownerDocGetInfoVO.getGender());
			ownerDocInfoVO.setReviewFileName(ownerDocGetInfoVO.getReviewFileName());
			ownerDocInfoVO.setReviewFileUrl(ownerDocGetInfoVO.getReviewFileUrl());
			ownerDocInfoVO.setChangeFileName(ownerDocGetInfoVO.getChangeFileName());
			ownerDocInfoVO.setChangeFileUrl(ownerDocGetInfoVO.getChangeFileUrl());
			ownerDocInfoVO.setScope(ownerDocGetInfoVO.getScope());
			ownerDocInfoVO.setNational(ownerDocGetInfoVO.getNational());
			ownerDocInfoVO.setHouseholdsHome(ownerDocGetInfoVO.getHouseholdsHome());
			ownerDocInfoVO.setHouseholdsType(ownerDocGetInfoVO.getHouseholdsType());
			ownerDocInfoVO.setLttd(ownerDocGetInfoVO.getLttd());
			ownerDocInfoVO.setLgtd(ownerDocGetInfoVO.getLgtd());
			ownerDocInfoVO.setAltd(ownerDocGetInfoVO.getAltd());
			ownerDocInfoVO.setInMap(ownerDocGetInfoVO.getInMap());
		}
		
	
		return new LyhtResultBody<OwnerDocInfoVO>(ownerDocInfoVO);
	}
	
	/**
	 * 复核流程审批结果
	 */
	@Transactional
	public LyhtResultBody<Integer> reviewProcessAuditSuccessful(String processId,Integer flag,Integer isSuccess,String senderNm){		// 参数校验
		if (StringUtils.isBlank(processId)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		Integer count = 0;
		if(flag == 0) {//复核申请
			String status = processDao.findStatusByTaskId(processId);//拿到流程状态
			if(status.equals("Approved")||status == "Approved"||status.equals("Rejected")||status == "Rejected"
						||status.equals("PickBack")||status == "PickBack") {
				//根据复核流程Id查询到复核信息
				PersonalWealth personaWealth = dao.findByReviewProcessId(processId);
				//接收人nm1 户主
				String toStaffNm01 = personaWealth.getOwnerNm();
				//接收人nm2  申请人
				String toStaffNm02 = personaWealth.getReviewer();
				//角色nm
				String roleNm= "BCA5AA572F";
				String jsonString = JSON.toJSONString(personaWealth);
				if(isSuccess==2) {//复核申请通过
					personaWealth.setReviewState(2);//将复核id改为完成状态 2 
					personaWealth = dao.save(personaWealth);
					count = dao.updateFhStateOnOwner(personaWealth.getOwnerNm(), 2);//总复核·状态 也改为2  完成
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("name", "个人财产复核申请");
					map.put("info", personaWealth);
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm01, "复核申请通过", jsonString, "SHOW");
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm02, "复核申请通过", jsonString, "SHOW");
					messageNoticeService.sendMessageNoticeByRole(senderNm, roleNm, "复核申请通过",JSON.toJSONString(map), "JUMP");
				}else if(isSuccess==0){//复核申请拒绝
					//根据复核流程Id查询到复核信息
					personaWealth.setReviewState(-2);//将复核id改为拒绝状态 -2
					personaWealth = dao.save(personaWealth);
					count = dao.updateFhStateOnOwner(personaWealth.getOwnerNm(), -2);//总复核状态 也改为-2 拒绝
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm01, "复核申请拒绝", jsonString, "SHOW");
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm02, "复核申请拒绝", jsonString, "SHOW");
				}else if(isSuccess==1) {//复核申请驳回
					//根据复核流程Id查询到复核信息
					personaWealth.setReviewState(-1);//将复核id改为驳回状态 -1
					personaWealth = dao.save(personaWealth);
					count = dao.updateFhStateOnOwner(personaWealth.getOwnerNm(), -1);//总复核状态 也改为-1  驳回
				}else if(isSuccess==3) {//复核申请取消
					//根据复核流程Id查询到复核信息
					personaWealth.setReviewState(0);
					personaWealth = dao.save(personaWealth);
					count = dao.updateFhStateOnOwner(personaWealth.getOwnerNm(), -1);//总复核状态 也改为-1  驳回
				}
				
				
			}
			
		}else {//变更申请
				PersonalWealth personalWealth = dao.findByChangeProcessId(processId);
				//根据复核流程Id查询到复核信息
				//接收人nm1 户主
				String toStaffNm01 = personalWealth.getOwnerNm();
				//接收人nm2  申请人
				String toStaffNm02 = personalWealth.getReviewer();
				//角色nm
				String roleNm= "BCA5AA572F";
				String jsonString = JSON.toJSONString(personalWealth);
				if(isSuccess==2) {//变更申请通过
					//将变更前数据保存至记录表
					saveHistory(personalWealth.getOwnerNm());
//					personalWealth = dao.save(personalWealth);
					//personalWealth.setReviewState(4);//将复核id改为完成状态 2 
					dao.updateReviewState(personalWealth.getId(),4);
					//将数据库中改动的json 取出  转为List集合  然后 删除对应权属人名字对应项所有记录  再将转好的List集合 一并新增到表中
					//dao.updateFhStateOnOwner(personalWealth.getOwnerNm(), 4);//总复核状态 也改为4  完成
					AbmOwnerEntity findByNm = ownerDao.findByNm(personalWealth.getOwnerNm());
					findByNm.setFhState("4");
					ownerDao.save(findByNm);
					jsonToData(personalWealth);//变更数据
					dataDao.deteteByMasterNm(personalWealth.getNm());
					updateOwnerInfo(personalWealth.getOwnerNm());
					Map<String,Object> data = new HashMap<String, Object>();
					data.put("name", "个人财产复核确认");
					data.put("info", personalWealth);
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm01, "变更申请通过", jsonString, "SHOW");
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm02, "变更申请通过", jsonString, "SHOW");
					messageNoticeService.sendMessageNoticeByRole(senderNm, roleNm, "变更申请通过", JSON.toJSONString(data), "JUMP");
				}else if(isSuccess==0){//变更申请拒绝
					//根据复核流程Id查询到复核信息
//					personalWealth.setReviewState(-4);//将复核id改为拒绝状态 -4
					dao.updateReviewState(personalWealth.getId(),-4);
					//personalWealth = dao.save(personalWealth);
					landPoolProcessService.deleteData(personalWealth);
					dataDao.deteteByMasterNm(personalWealth.getNm());
					count = dao.updateFhStateOnOwner(personalWealth.getOwnerNm(), -4);//总复核状态 也改为2  拒绝
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm01, "变更申请拒绝", jsonString, "SHOW");
					messageNoticeService.sendMessageNoticeByUser(senderNm, toStaffNm02, "变更申请拒绝", jsonString, "SHOW");
				}else if(isSuccess==1) {//变更申请驳回
					//根据复核流程Id查询到复核信息
//					personalWealth.setReviewState(-3);//将复核id改为驳回状态 -3
					dao.updateReviewState(personalWealth.getId(),-3);
				//	personalWealth = dao.save(personalWealth);
					landPoolProcessService.deleteData(personalWealth);
					dataDao.deteteByMasterNm(personalWealth.getNm());
					count = dao.updateFhStateOnOwner(personalWealth.getOwnerNm(), 2);//总复核状态 也改为-3  驳回
				}else if(isSuccess==3) {//变更申请取消
					//根据复核流程Id查询到复核信息
					personalWealth.setReviewState(2);
					dao.updateReviewState(personalWealth.getId(),2);
					//personalWealth = dao.save(personalWealth);
					landPoolProcessService.deleteData(personalWealth);
					dataDao.deteteByMasterNm(personalWealth.getNm());
					count = dao.updateFhStateOnOwner(personalWealth.getOwnerNm(), 2);//总复核状态 也改为-3  驳回
				}
				
		}
		return new LyhtResultBody<>(count);
	}
	

	/**
	 * 点击变更流程时拿到权属人最后一条复核信息
	 */
	public LyhtResultBody<PersonalWealth> findTheLastInfoByOwnerNm(String ownerNm) {
		// 参数校验
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}		
		return new LyhtResultBody<>(dao.findTheLastInfoByOwnerNm(ownerNm));
	}

	/**
	 * 个人财产主页列表
	 * 
	 * @param lyhtPageVO
	 * @param region
	 * @param name
	 * @param scope
	 * @param idCard
	 * @return
	 */
	public LyhtResultBody<List<PersonalWealthVO>> page(LyhtPageVO lyhtPageVO, String region, String name, String scope,
			String idCard,String nm) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<PersonalWealthVO> page = dao.page(region, name, scope, idCard,nm, pageable);
		String jsonString = JSON.toJSONString(page.getContent());
		List<PersonalWealthVO> list = (List<PersonalWealthVO>) JSON.parse(jsonString);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}

	/**
	 * 添加 修改 发起复核申请/变更申请  流程
	 * @param 
	 * @return
	 */
	@Transactional
	public LyhtResultBody<PersonalWealth> save(PersonalWealth personaWealth, Integer flag, HttpServletRequest request) {
		// 参数校验
		if (personaWealth == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		if (flag == 0) {// 发起复核申请
			// 内码赋值
			String nm = personaWealth.getNm();
			if (StringUtils.isBlank(nm)) {
				personaWealth.setNm(Randomizer.generCode(10));
			}
			// 获取当天最大流水号 复核编号生产规则 FH+当前年月日+四位顺序流水号 例如:FH200101010001 2001年01月01日0001号复核申请
			String code = dao.findTheLastId();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			if (StringUtils.isBlank(code)) {// 不存在 从0001开始
				personaWealth.setApplicationNumber("FH" + sdf1.format(date) + "0001");
			} else {// 存在则 +1
				code = code.substring(code.length() - 4, code.length());
				if (Integer.parseInt(code) < 9999) {
					personaWealth.setApplicationNumber(
							"FH" + sdf1.format(date) + String.format("%04d", Integer.parseInt(code) + 1));
				} else {
					new LyhtRuntimeException(LyhtExceptionEnums.THE_MAX_NUMBER);
				}
			}
			personaWealth.setReviewState(1);// 复核流程发起 状态值为1复核申请发起状态

			String ownerName = dao.getOwnerName(personaWealth.getOwnerNm());// 根据nm查询权属人基本信息
			HashMap<String, String> taskData = new HashMap<>();
			taskData.put("name", ownerName + "权属人复核申请"+personaWealth.getApplicationNumber());// 自定义流程名称
			ProcessOperateVO processOperateVO = new ProcessOperateVO();
			processOperateVO.setFlowPath(physicalFlowPath);
			processOperateVO.setData(taskData);
			String processId = processOperateService.processStart(processOperateVO, request);// 发起流程获取流程ID
			personaWealth.setReviewProcessId(processId);

			dao.updateFhStateGsStateOnOwner(personaWealth.getOwnerNm(), 1);// 修改总复核状态为1  以及更新公式状态
			PersonalWealth result = dao.save(personaWealth);
			auditService.reset(personaWealth.getOwnerNm());//重置数据
//			moveService.reset(personaWealth.getOwnerNm());//重置数据
			protocolInfoService.deleteByProject(personaWealth.getReviewProject(), personaWealth.getOwnerNm());
			dao.updateReviewTime(personaWealth.getOwnerNm());//复核记录次数+1
			return new LyhtResultBody<>(result);
		} else {// 发起变更申请 
			PersonalWealth personaWealth1 = dao.getOne(personaWealth.getId());//拿到传入的id  将原数据查出
			PersonalWealthData data = dataDao.findByMasterNm(personaWealth1.getNm());
			//无变更
			if(data == null) {
				personaWealth1.setReviewState(4);// 变更发起 状态更改为 3 变更审核发起
				//将数据库中改动的json 取出  转为List集合  然后 删除对应权属人名字对应项所有记录  再将转好的List集合 一并新增到表中
				PersonalWealth result = dao.save(personaWealth1);
				dao.updateFhStateOnOwner(personaWealth1.getOwnerNm(),4);//总复核状态 也改为4  变更完成
				return new LyhtResultBody<>(result);
			}else {//有变更
				String ownerName = dao.getOwnerName(personaWealth1.getOwnerNm());// 根据nm查询权属人基本信息
				HashMap<String, String> taskData = new HashMap<>();
				taskData.put("name", ownerName + "权属人变更申请");// 自定义流程名称
				ProcessOperateVO processOperateVO = new ProcessOperateVO();
				processOperateVO.setFlowPath(physicalFlowPath2);
				processOperateVO.setData(taskData);
				String processId = processOperateService.processStart(processOperateVO, request);// 发起流程获取流程ID
				personaWealth1.setChangeProcessId(processId);// 将变更流程id放入
				personaWealth1.setReviewState(3);// 变更发起 状态更改为 3 变更审核发起
				dao.updateFhStateOnOwner(personaWealth1.getOwnerNm(), 3);// 修改总复核状态为 3
				PersonalWealth result = dao.save(personaWealth1);
				return new LyhtResultBody<>(result);
			}
		}
	}
	
	
	/**
	 * 权属人变更申请成功  变更数据  将存储在数据库中的 json数据取出  更新到对应表中
	 * @param json
	 * @return
	 */
	@Transactional
	public void jsonToData(PersonalWealth personalWealth) {
		PersonalWealthData personalWealthData =dataDao.findByMasterNm(personalWealth.getNm());
		if(personalWealthData!= null ) {
			if(StringUtils.isNotBlank(personalWealthData.getImmigrantPopulationJson())) {//移民人口JSON 如果移民人口存在变更 则更改数据
				List<AbmFamilyEntity> list = (List<AbmFamilyEntity>) JSON.parseArray(personalWealthData.getImmigrantPopulationJson(),AbmFamilyEntity.class);
				 List<AbmFamilyEntity> findByOwnerNm = familyDao.findByOwnerNm(personalWealth.getOwnerNm());
				if(list.size()>0) {
					for (AbmFamilyEntity abmFamilyEntity : findByOwnerNm) {
						familyDao.delete(abmFamilyEntity);
						if(abmFamilyEntity.getMasterRelationship().equals("E2A29C1823")) {//户主信息修改
							AbmOwnerEntity abmOwnerEntity = ownerDao.findByNm(abmFamilyEntity.getOwnerNm());
							abmOwnerEntity.setRegion(abmFamilyEntity.getRegion());
							abmOwnerEntity.setIdCard(abmFamilyEntity.getIdCard());
							abmOwnerEntity.setAge(abmFamilyEntity.getAge());
							abmOwnerEntity.setName(abmFamilyEntity.getName());
							ownerDao.save(abmOwnerEntity);
						}
					}
					for (AbmFamilyEntity abmFamilyEntity : list) {
						if(StringUtils.isBlank(abmFamilyEntity.getNm())) {
							abmFamilyEntity.setNm(Randomizer.generCode(10));
						}
						if(CommonUtil.isEmpty(abmFamilyEntity.getId())) {
							abmFamilyEntity.setIsMove("是");
						}
						
						abmFamilyEntity.setOwnerNm(personalWealth.getOwnerNm());
					}
					familyDao.saveAll(list); 
					Integer count = familyDao.getCountByOwnerNm(personalWealth.getOwnerNm());//查询当前权属人家庭移民人口数量
					ownerDao.updateiPopulation(personalWealth.getOwnerNm(), count);//修改当前权属人家庭移民人口数量
				}
			} if(StringUtils.isNotBlank(personalWealthData.getLandJson())) {//土地
				List<AbmLandEntity> list = (List<AbmLandEntity>) JSON.parseArray(personalWealthData.getLandJson(),AbmLandEntity.class);
				/**
				 * 同步土地分解数据池中的数据
				 */
				if(list.size()>0) {
					for (AbmLandEntity abmLandEntity : list) {
						if(StringUtils.isBlank(abmLandEntity.getNm())) {
							abmLandEntity.setNm(Randomizer.generCode(10));
						}
						//删除已成功改变的数据
						LandPoolEntity landPoolEntity = poolDao.queryBySixCondition(abmLandEntity.getRegion(), abmLandEntity.getScope(), abmLandEntity.getAllType(),
								abmLandEntity.getTypeOne(), abmLandEntity.getTypeTwo(), abmLandEntity.getTypeThree());
						landPoolProcessDao.deleteByWealthNmAndPoolId(personalWealth.getNm(), landPoolEntity.getId());
						abmLandEntity.setOwnerNm(personalWealth.getOwnerNm());
						landDao.delete(abmLandEntity);
					}
					landDao.saveAll(list);
					updateSeparateArea(list, personalWealth.getOwnerNm());//同步土地分解数据
					
				}
				
			} if(StringUtils.isNotBlank(personalWealthData.getHouseJson())) {//房屋
				List<AbmHouseEntity> list = (List<AbmHouseEntity>) JSON.parseArray(personalWealthData.getHouseJson(),AbmHouseEntity.class);
				if(list.size()>0) {
					for (AbmHouseEntity abmHouseEntity : list) {
						if(StringUtils.isBlank(abmHouseEntity.getNm())) {
							abmHouseEntity.setNm(Randomizer.generCode(10));
						}
						abmHouseEntity.setOwnerNm(personalWealth.getOwnerNm());
						houseDao.delete(abmHouseEntity);
					}
					houseDao.saveAll(list);
				}
			} if(StringUtils.isNotBlank(personalWealthData.getTreeJson())) {//零星树木
				List<AbmTreesEntity> list = (List<AbmTreesEntity>) JSON.parseArray(personalWealthData.getTreeJson(),AbmTreesEntity.class);
				if(list.size()>0) {
					
					for (AbmTreesEntity abmTreeEntity	: list) {
						if(StringUtils.isBlank(abmTreeEntity.getNm())) {
							abmTreeEntity.setNm(Randomizer.generCode(10));
						}
						abmTreeEntity.setOwnerNm(personalWealth.getOwnerNm());
						treesDao.delete(abmTreeEntity);
					}
					treesDao.saveAll(list);
				}
			} if(StringUtils.isNotBlank(personalWealthData.getOtherJson())) {//附属建筑物
				List<AbmBuildingEntity> list = (List<AbmBuildingEntity>) JSON.parseArray(personalWealthData.getOtherJson(),AbmBuildingEntity.class);
				if(list.size()>0) {
					for (AbmBuildingEntity abmBuildingEntity : list) {
						if(StringUtils.isBlank(abmBuildingEntity.getNm())) {
							abmBuildingEntity.setNm(Randomizer.generCode(10));
						}
						abmBuildingEntity.setOwnerNm(personalWealth.getOwnerNm());
						buildingDao.delete(abmBuildingEntity);
					}
					buildingDao.saveAll(list);
				}
			} if(StringUtils.isNotBlank(personalWealthData.getFitmentJson())) {//房屋装修
				List<AbmHousesDecorationEntity> list = (List<AbmHousesDecorationEntity>) JSON.parseArray(personalWealthData.getFitmentJson(),AbmHousesDecorationEntity.class);
				if(list.size()>0) {
					for (AbmHousesDecorationEntity abmHousesDecorationEntity : list) {
						if(StringUtils.isBlank(abmHousesDecorationEntity.getNm())) {
							abmHousesDecorationEntity.setNm(Randomizer.generCode(10));
						}
						abmHousesDecorationEntity.setOwnerNm(personalWealth.getOwnerNm());
						decorationDao.delete(abmHousesDecorationEntity);
					}
					decorationDao.saveAll(list);
				}
			} if(StringUtils.isNotBlank(personalWealthData.getIndividualHouseholdJson())) {//个体户
				List<AbmInfoIndividualEntity> list = (List<AbmInfoIndividualEntity>) JSON.parseArray(personalWealthData.getIndividualHouseholdJson(),AbmInfoIndividualEntity.class);
				if(list.size()>0) {
					for (AbmInfoIndividualEntity abmInfoIndividualEntity : list) {
						if(StringUtils.isBlank(abmInfoIndividualEntity.getNm())) {
							abmInfoIndividualEntity.setNm(Randomizer.generCode(10));
						}
						abmInfoIndividualEntity.setOwnerNm(personalWealth.getOwnerNm());
						infoIndividualDao.delete(abmInfoIndividualEntity);
					}
					infoIndividualDao.saveAll(list);
				}
			}if(StringUtils.isNotBlank(personalWealthData.getAgriculturalFacilitiesJson())) {//个体户
				List<AbmAgriculturalFacilitiesEntity> list = (List<AbmAgriculturalFacilitiesEntity>) JSON.parseArray(personalWealthData.getAgriculturalFacilitiesJson(),AbmAgriculturalFacilitiesEntity.class);
				if(list.size()>0) {
					for (AbmAgriculturalFacilitiesEntity entity : list) {
						if(StringUtils.isBlank(entity.getNm())) {
							entity.setNm(Randomizer.generCode(10));
						}
						entity.setOwnerNm(personalWealth.getOwnerNm());
						agriculturalFacilitiesDao.delete(entity);
					}
					agriculturalFacilitiesDao.saveAll(list);
				}
			}if(StringUtils.isNotBlank(personalWealthData.getHomesteadJson())) {//个体户
				List<AbmHomesteadEntity> list = (List<AbmHomesteadEntity>) JSON.parseArray(personalWealthData.getHomesteadJson(),AbmHomesteadEntity.class);
				if(list.size()>0) {
					for (AbmHomesteadEntity entity : list) {
						if(StringUtils.isBlank(entity.getNm())) {
							entity.setNm(Randomizer.generCode(10));
						}
						entity.setOwnerNm(personalWealth.getOwnerNm());
						homeSteadDao.delete(entity);
					}
					homeSteadDao.saveAll(list);
				}
			}
		}
	}
	/**
	 * 同步分解池中可拆分面积
	 * list 变更后数据
	 */
	public void updateSeparateArea(List<AbmLandEntity> newData,String ownerNm) {
		List<AbmLandEntity> oldData =abmLandService.findByOwnerNm(ownerNm);//未变更前数据
		Integer i = 0;
		System.out.println(oldData.size());
		for (AbmLandEntity abmLandEntity : oldData) {
			String region = abmLandEntity.getRegion();
			String scope = abmLandEntity.getScope();
			String allType = abmLandEntity.getAllType();
			String typeOne = abmLandEntity.getTypeOne();
			String typeTwo = abmLandEntity.getTypeTwo();
			String typeThree = abmLandEntity.getTypeThree();
			//查到当前可分解值
			Double separateArea = poolDao.querySeparateArea(region,scope,allType,typeOne,typeTwo,typeThree);
			Double oldArea = abmLandEntity.getArea().doubleValue();
			Double newArea = 0.00;
			if(i<newData.size()) {
				newArea = newData.get(i).getArea().doubleValue();
			}
			Double count = oldArea - newArea;
			//计算变更后总值
			Double sum = separateArea + count;
			//调用sql变更数据库
			poolDao.updateSeparateArea(sum, region, scope, allType, typeOne, typeTwo, typeThree);
			i++;
		}
	}
	
	/**
	 * 查询权属个体户信息
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthIndividualObjectVO>> findByOwnerNmIndividual(String taskId,String ownerNm,String masterNm){
		List<PersonaWealthIndividualObjectVO> list = new ArrayList<PersonaWealthIndividualObjectVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getIndividualHouseholdJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成 而且存在数据
					list = (List<PersonaWealthIndividualObjectVO>) JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonaWealthIndividualObjectVO.class);
					if(list.size()==0) {
						List<Map<String,Object>> listMap = dao.findPersonaWealthIndividual(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonaWealthIndividualObjectVO>) JSON.parse(jsonString);
					} 
				}
			}
		}
		if(list.size()==0){
			List<Map<String,Object>> listMap = dao.findPersonaWealthIndividual(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonaWealthIndividualObjectVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人房屋装修信息
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthDecorationObjectVO>> findByOwnerNmDecoration(String taskId,String ownerNm,String masterNm){
		List<PersonaWealthDecorationObjectVO> list = new ArrayList<PersonaWealthDecorationObjectVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getFitmentJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成 而且存在数据
					list = (List<PersonaWealthDecorationObjectVO>) JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonaWealthDecorationObjectVO.class);
					if(list.size()==0) {
						List<Map<String,Object>> listMap = decorationDao.findByOwnerNmOfPersonaWealth(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonaWealthDecorationObjectVO>) JSON.parse(jsonString);
					} 
				}
			}
		}
		if(list.size()==0){
			List<Map<String,Object>> listMap = decorationDao.findByOwnerNmOfPersonaWealth(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonaWealthDecorationObjectVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人其他附属建筑信息
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthBuildingObjectVO>> findByOwnerNmBuilding(String taskId,String ownerNm,String masterNm){
		List<PersonaWealthBuildingObjectVO> list = new ArrayList<PersonaWealthBuildingObjectVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO= dao.getOtherJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成 而且存在数据
					list = JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonaWealthBuildingObjectVO.class);
					if(list.size()==0) {
						List<Map<String,Object>> listMap = buildingDao.findByOwnerNmOfPersonaWealth(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonaWealthBuildingObjectVO>) JSON.parse(jsonString);
					}
					
				}
			}
		}
		if(list.size() == 0) {
			List<Map<String,Object>> listMap = buildingDao.findByOwnerNmOfPersonaWealth(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonaWealthBuildingObjectVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人零星树木信息
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthTreesObjectVO>> findByOwnerNmTrees(String taskId,String ownerNm,String masterNm){
		List<PersonaWealthTreesObjectVO> list = new ArrayList<PersonaWealthTreesObjectVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getTreeJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成 而且存在数据
					list = JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonaWealthTreesObjectVO.class);
						if(list.size()==0) {
							List<Map<String,Object>> listMap = treesDao.findByOwnerNmOfPersonaWealth(ownerNm);
							String jsonString = JSON.toJSONString(listMap);
							list = (List<PersonaWealthTreesObjectVO>) JSON.parse(jsonString);
						} 
				}
			}
		}
		if(list.size() == 0){
			List<Map<String,Object>> listMap = treesDao.findByOwnerNmOfPersonaWealth(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonaWealthTreesObjectVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人房屋信息
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthHouseObjectVO>> findByOwnerNmHouse(String taskId,String ownerNm,String masterNm){
		List<PersonaWealthHouseObjectVO> list = new ArrayList<PersonaWealthHouseObjectVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getHouseJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成
					list = JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonaWealthHouseObjectVO.class);
					if(list.size() == 0) {
						List<Map<String,Object>> listMap = houseDao.findByOwnerNmOfPersonaWealth(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonaWealthHouseObjectVO>) JSON.parse(jsonString);
					}
				}
			}
		}
		if(list.size()==0){
			List<Map<String,Object>> listMap = houseDao.findByOwnerNmOfPersonaWealth(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonaWealthHouseObjectVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人的土地信息
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthLandObjectVO>> findByOwnerNmLand(String taskId,String ownerNm,String masterNm){
		List<PersonaWealthLandObjectVO> list = new ArrayList<PersonaWealthLandObjectVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getLandJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成
					list = JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonaWealthLandObjectVO.class);
					if(list.size()==0) {
						List<Map<String,Object>> listMap = landDao.findByOwnerNmOfPersonaWealth(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonaWealthLandObjectVO>) JSON.parse(jsonString);
					} 
				}
			}
		}
		if(list.size() == 0){
			List<Map<String,Object>> listMap = landDao.findByOwnerNmOfPersonaWealth(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonaWealthLandObjectVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人的家庭成员
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonaWealthFamilyObjectVO>> findByOwnerNmFamily(String taskId,String ownerNm,String masterNm){//查询权属人的家庭成员
		List<PersonaWealthFamilyObjectVO> list = new ArrayList<PersonaWealthFamilyObjectVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getImmigrantPopulationJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成
					list = JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonaWealthFamilyObjectVO.class);
					if(list.size()==0) {
						List<Map<String,Object>> listMap = familyDao.findByOwnerNmOfPersonaWealth(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonaWealthFamilyObjectVO>) JSON.parse(jsonString);
					} 
				}
			}
		}
		if(list.size()==0){
			List<Map<String,Object>> listMap = familyDao.findByOwnerNmOfPersonaWealth(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonaWealthFamilyObjectVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人的农副业设施
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonalWealthAgriculturalFacilitiesVO>> findByOwnerNmAgriculturalFacilities(String taskId,String ownerNm,String masterNm){//查询权属人的家庭成员
		List<PersonalWealthAgriculturalFacilitiesVO> list = new ArrayList<PersonalWealthAgriculturalFacilitiesVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getImmigrantPopulationJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成
					list = JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonalWealthAgriculturalFacilitiesVO.class);
					if(list.size()==0) {
						List<Map<String,Object>> listMap = agriculturalFacilitiesDao.findByOwnerNmAgriculturalFacilities(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonalWealthAgriculturalFacilitiesVO>) JSON.parse(jsonString);
					} 
				}
			}
		}
		if(list.size()==0){
			List<Map<String,Object>> listMap = agriculturalFacilitiesDao.findByOwnerNmAgriculturalFacilities(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonalWealthAgriculturalFacilitiesVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人农副业设施
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<AbmAgriculturalFacilitiesVO>> findByOwnerNmAgriculturalFacilities(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);//分页参数
		Page<AbmAgriculturalFacilitiesVO> page = agriculturalFacilitiesDao.page(ownerNm,pageable);
		List<AbmAgriculturalFacilitiesVO> list = (List<AbmAgriculturalFacilitiesVO>) page.getContent();//转VO
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 查询权属人的宅基地
	 * @param ownerNm
	 * @return
	 */
	public LyhtResultBody<List<PersonalWealthHomesteadVO>> findByOwnerNmHomestead(String taskId,String ownerNm,String masterNm){//查询权属人的家庭成员
		List<PersonalWealthHomesteadVO> list = new ArrayList<PersonalWealthHomesteadVO>();
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		if(StringUtils.isNotBlank(masterNm)) {//如果已经存在变更数据
			PersonalWealthDataVO personalWealthDataVO = dao.getImmigrantPopulationJsonByMasterNm(masterNm);
			if(personalWealthDataVO!=null) {
				if(personalWealthDataVO.getState()!= 4&&StringUtils.isNotBlank(personalWealthDataVO.getJsonString())) {//未变更完成
					list = JSONArray.parseArray(personalWealthDataVO.getJsonString(), PersonalWealthHomesteadVO.class);
					if(list.size()==0) {
						List<Map<String,Object>> listMap = homeSteadDao.list(ownerNm);
						String jsonString = JSON.toJSONString(listMap);
						list = (List<PersonalWealthHomesteadVO>) JSON.parse(jsonString);
					} 
				}
			}
		}
		if(list.size()==0){
			List<Map<String,Object>> listMap = homeSteadDao.list(ownerNm);
			String jsonString = JSON.toJSONString(listMap);
			list = (List<PersonalWealthHomesteadVO>) JSON.parse(jsonString);
		}
		return new LyhtResultBody<>(list);
	}
	
	/**
	 * 查询权属人宅基地
	 * @param ownerNm
	 * @param lyhtPageVO
	 * @return
	 */
	public LyhtResultBody<List<AbmHomesteadVO>> findByOwnerNmHomestead(String taskId,String ownerNm,LyhtPageVO lyhtPageVO){
		if(StringUtils.isNotBlank(taskId)){
			PersonalWealth personalWealth = dao.findByChangeProcessId(taskId);
			ownerNm = personalWealth.getOwnerNm();
		}
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);//分页参数
		Page<AbmHomesteadVO> page = homeSteadDao.page(ownerNm,pageable);
		List<AbmHomesteadVO> list = (List<AbmHomesteadVO>) page.getContent();//转VO
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(list, pageVO);
	}
	
	/**
	 * 根据流程id 查询 提交变更的内容
	 */
	public LyhtResultBody<PersonalWealthData> findDataByMasterNm(String nm){
		PersonalWealthData personalWealthData = dataDao.findByMasterNm(nm);
		return new LyhtResultBody<>(personalWealthData);
	}
	
	/**
	 * 查询 权属人可复核项
	 */
	public LyhtResultBody<List<PubDictValue>> getPubDictValue(String ownerNm){
		TotalState totalState = totalStateDao.findByOwnerNm(ownerNm);
		List<PubDictValue> list = dictDao.findByListnmSysDictCate("dict_fh_project");
		List<PubDictValue> result = new ArrayList<PubDictValue>();
		if(totalState == null) {
			result = list;
		}else {
			if(totalState.getHouseStatus() !=1) {//房屋
				for (PubDictValue pubDictValue : list) {
					if(pubDictValue.getName().equals("房屋")) {
						result.add(pubDictValue);
					}
				}
			}
			if(totalState.getLevyLandStatus() !=1) {//土地
				for (PubDictValue pubDictValue : list) {
					if(pubDictValue.getName().equals("土地")) {
						result.add(pubDictValue);
					}
				}
			}
			if(totalState.getYoungCropsStatus() !=1) {//零星树木
				for (PubDictValue pubDictValue : list) {
					if(pubDictValue.getName().equals("零星树木")) {
						result.add(pubDictValue);
					}
				}
			}
			if(totalState.getBuildingStatus() !=1) {//附属建筑物
				for (PubDictValue pubDictValue : list) {
					if(pubDictValue.getName().equals("其他附属")) {
						result.add(pubDictValue);
					}
				}
			}
			if(totalState.getHouseDecorationStatus() !=1) {//房屋装修
				for (PubDictValue pubDictValue : list) {
					if(pubDictValue.getName().equals("装修")) {
						result.add(pubDictValue);
					}
				}
			}
			if(totalState.getIndividualStatus() !=1) {//个体户
				for (PubDictValue pubDictValue : list) {
					if(pubDictValue.getName().equals("个体工商户")) {
						result.add(pubDictValue);
					}
				}
			}
			if(totalState.getOtherStatus() !=1) {//移民人口
				for (PubDictValue pubDictValue : list) {
					if(pubDictValue.getName().equals("移民人口")) {
						result.add(pubDictValue);
					}
				}
			}
		}
		return new LyhtResultBody<>(result);
	}
	
	@Transactional
	public void updateOwnerInfo(String ownerNm) {
		List<AbmFamilyEntity> findByOwnerNm = abmFamilyService.findByOwnerNm(ownerNm);
		Integer ap = ownerDao.getApCount(ownerNm);
		Integer nonAp = ownerDao.getNonApCount(ownerNm);
		Integer iPopulation = ownerDao.getIPopulation(ownerNm);
		Integer immigrant = 0;
		if(findByOwnerNm.size()>0) {
			immigrant = 1;
		}
		AbmOwnerEntity findByNm = ownerDao.findByNm(ownerNm);
		findByNm.setAp(ap);
		findByNm.setNonAp(nonAp);
		findByNm.setIPopulation(iPopulation);
		findByNm.setImmigrant(immigrant);
		ownerDao.save(findByNm);
	}
}