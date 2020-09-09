package com.lyht.business.abm.removal.controller;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.lyht.base.common.advice.LyhtExceptionHandler;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.service.RequisitionPlanService;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.entity.OwnerVerifyEntity;
import com.lyht.business.abm.plan.service.OwnerVerifyService;
import com.lyht.business.abm.removal.dao.AbmFamilyDao;
import com.lyht.business.abm.removal.dao.AbmHousesDao;
import com.lyht.business.abm.removal.dao.AbmBuildingDao;
import com.lyht.business.abm.removal.dao.AbmTreesDao;
import com.lyht.business.abm.removal.dao.AbmLandDao;
import com.lyht.business.abm.removal.dao.AbmHousesDecorationDao;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.AbmHouseEntity;
import com.lyht.business.abm.removal.entity.MoveIdentity;
import com.lyht.business.abm.removal.entity.AbmBuildingEntity;
import com.lyht.business.abm.removal.entity.AbmTreesEntity;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.abm.removal.entity.AbmHousesDecorationEntity;
import com.lyht.business.abm.removal.service.AbmFamilyService;
import com.lyht.business.abm.signed.entity.TotalState;
import com.lyht.business.abm.signed.service.TotalStateService;
import com.lyht.business.change.vo.ChangeApplicationVO;
import com.lyht.system.dao.SysLogDao;
import com.lyht.system.pojo.SysLog;
import com.lyht.util.DateUtil;
import com.lyht.util.SystemUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "/move/family")
@Api(value = "实施阶段人口管理", tags = "实施阶段人口管理")
public class AbmFamilyController {
	@Autowired
	TotalStateService totalStateService;
	@Autowired
	AbmFamilyService abmFamilyService;
	@Autowired
	AbmFamilyDao abmfamilydao;
	@Autowired
	OwnerVerifyService ownerverifyService;
	@Autowired
	ProjectPlanDao projectPlanDao;
	@Autowired
	RequisitionPlanService requisitionPlanService;
	@Autowired
	AbmFamilyDao abmFamilyDao;
	@Autowired
	SysLogDao logDao;
	@Autowired
	AbmLandDao tudiDao;
	@Autowired
	AbmHousesDao houseDao;
	@Autowired
	AbmTreesDao treeDao;
	@Autowired
	AbmBuildingDao subsidiaryDao;
	@Autowired
	AbmHousesDecorationDao zxDao;
	
	@ApiOperation(value = "身份证重复校验", notes = "身份证重复校验")
	@PostMapping("/queryByIdCard")
	public LyhtResultBody<AbmFamilyEntity> queryByIdCard(String idCard,Integer id) {
		return abmFamilyService.queryByIdCard(idCard,id);
	}

	@ApiOperation(value = "通过流程ID查询表单", notes = "通过流程ID查询表单")
	@PostMapping("/get")
	public LyhtResultBody<OwnerVerifyEntity> page(String taskId) {
		OwnerVerifyEntity entity = ownerverifyService.findByTaskId(taskId);
		return new LyhtResultBody<>(entity);
	}

	@PostMapping(value = "/saveVerify")
	@ApiOperation(value = "权属人复核申请", notes = "权属人复核申请")
	public LyhtResultBody<OwnerVerifyEntity> saveVerify(OwnerVerifyEntity entity, String flag,
			HttpServletRequest request) {
		return ownerverifyService.save(entity, flag, request);
	}

	@PostMapping(value = "/save")
	@ApiOperation(value = " 新增人口信息 ", notes = "新增人口信息")
	public LyhtResultBody<AbmFamilyEntity> save(AbmFamilyEntity abmFamilyEntity) {
		return abmFamilyService.save(abmFamilyEntity);
	}

	@PostMapping(value = "/saveAnZhi")
	@ApiOperation(value = " 安置方案新增人口信息 ", notes = "安置方案")
	public LyhtResultBody<AbmFamilyEntity> saveAnZhi(AbmFamilyEntity abmFamilyEntity, HttpServletRequest request) {
		System.out.println("1111111111111111111111111" + abmFamilyEntity.getIdCard());
		String ownerNm = abmFamilyEntity.getOwnerNm();
		if(ownerNm == null) {
			 throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		TotalState findByOwnerNm = totalStateService.findByOwnerNm(ownerNm);
		if(findByOwnerNm.getProtocolState().equals("1")) {
			 throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return abmFamilyService.saveAnZhi(abmFamilyEntity, request);
	}

	@PostMapping(value = "/saveJieding")
	@ApiOperation(value = " 安置方案新增人口信息 ", notes = "安置方案")
	public List<Map> saveJieding(String list) {
		List<Map> list1 = new ArrayList<>();
		Map map = new HashMap<>();
		try {
			map.put("code", 200);
			list1.add(map);
			List<Map<String, String>> listObjectFir = (List<Map<String, String>>) JSONArray.parse(list);
			for (int i = 0; i < listObjectFir.size(); i++) {
				String defind = listObjectFir.get(i).getOrDefault("defind".toString(), "id".toString());
				String id = listObjectFir.get(i).getOrDefault(("id").toString(), "defind".toString());

				String xiang = listObjectFir.get(i).getOrDefault("xiang".toString(), "xiang".toString());
				String cun = listObjectFir.get(i).getOrDefault("cun".toString(), "cun".toString());
				String zu = listObjectFir.get(i).getOrDefault("zu".toString(), "zu".toString());
				String placeType = listObjectFir.get(i).getOrDefault("placeType".toString(), "placeType".toString());
				String placeName = listObjectFir.get(i).getOrDefault("placeName".toString(), "placeName".toString());
				String toWhere = listObjectFir.get(i).getOrDefault("toWhere".toString(), "toWhere".toString());
				String nm = listObjectFir.get(i).getOrDefault("nm".toString(), "nm".toString());

				if (i == 0) {
					if (defind.length() > 3) {
						abmFamilyService.updateOwner(nm, "2", xiang, cun, zu, placeType, placeName);
					}
					if (defind.length() < 3) {
						abmFamilyService.updateOwner(nm, "1", xiang, cun, zu, placeType, placeName);
					}
				}
				Integer ids = Integer.parseInt(id);
				if (defind.length() > 3) {
					abmFamilyService.updateJieding(ids, defind, "2", xiang, cun, zu, placeType, placeName, toWhere);
				}
				if (defind.length() < 3) {
					abmFamilyService.updateJieding(ids, defind, "1", xiang, cun, zu, placeType, placeName, toWhere);
				}

			}
		} catch (Exception e) {
			map.put("code", 500);
			list1.add(map);
		}

		return list1;

	}

	@PostMapping(value = "/saveHouse")
	@ApiOperation(value = " 新增房屋 ", notes = "新增房屋")
	public LyhtResultBody<AbmHouseEntity> save(AbmHouseEntity entity) {
		return abmFamilyService.save(entity);
	}

	@PostMapping(value = "/saveTudi")
	@ApiOperation(value = " 新增土地 ", notes = "新增土地")
	public LyhtResultBody<AbmLandEntity> save(AbmLandEntity entity) {
		return abmFamilyService.save(entity);
	}

	@PostMapping(value = "/saveZx")
	@ApiOperation(value = " 新增房屋装修 ", notes = "新增房屋装修")
	public LyhtResultBody<AbmHousesDecorationEntity> save(AbmHousesDecorationEntity entity) {
		return abmFamilyService.save(entity);
	}

	@PostMapping(value = "/saveTree")
	@ApiOperation(value = " 新增数木 ", notes = "新增数木 ")
	public LyhtResultBody<AbmTreesEntity> save(AbmTreesEntity entity) {
		return abmFamilyService.save(entity);
	}

	@PostMapping(value = "/saveSubsidiary")
	@ApiOperation(value = " 附属建筑 ", notes = "新增数木 ")
	public LyhtResultBody<AbmBuildingEntity> save(AbmBuildingEntity entity) {
		return abmFamilyService.save(entity);
	}

	@GetMapping(value = "/deleteHouse")
	@ApiOperation(value = " 删除房屋信息 ", notes = " 删除房屋信息 ")
	public LyhtResultBody<Integer> deleteHouse(Integer id) {
		return abmFamilyService.deleteHouse(id);
	}

	@GetMapping(value = "/deleteTudi")
	@ApiOperation(value = " 删除土地信息 ", notes = " 删除土地信息 ")
	public LyhtResultBody<Integer> deleteTudi(Integer id) {
		return abmFamilyService.deleteTudi(id);
	}

	@GetMapping(value = "/deleteZx")
	@ApiOperation(value = " 删除房屋装修 ", notes = " 删除房屋装修 ")
	public LyhtResultBody<Integer> deleteZx(Integer id) {
		return abmFamilyService.deleteZx(id);
	}

	@GetMapping(value = "/deleteTree")
	@ApiOperation(value = " 删除树木 ", notes = " 删除树木 ")
	public LyhtResultBody<Integer> deleteTree(Integer id) {
		return abmFamilyService.deleteTree(id);
	}

	@GetMapping(value = "/deleteSubsidiary")
	@ApiOperation(value = " 删除附属建筑 ", notes = " 删除附属建筑 ")
	public LyhtResultBody<Integer> deleteSubsidiary(Integer id) {
		return abmFamilyService.deleteSubsidiary(id);
	}

	@GetMapping(value = "/delete")
	@ApiOperation(value = " 删除人口信息 ", notes = " 删除人口信息 ")
	public LyhtResultBody<Integer> delete(Integer id) {
		return abmFamilyService.delete(id);
	}

	@PostMapping(value = "/getListPopulation")
	@ApiOperation(value = " 获取人口信息 ", notes = " 获取人口信息 ")
	public LyhtResultBody<List<Map>> getList(LyhtPageVO pageVO, String ownerNm) {
		return abmFamilyService.getList(pageVO, ownerNm);
	}

	@PostMapping(value = "/getListSubsidiary")
	@ApiOperation(value = " 获取附属建筑 ", notes = " 获取附属建筑信息 ")
	public LyhtResultBody<List<Map>> getFSList(LyhtPageVO pageVO, String ownerNm, String region, String projectNm) {
		return abmFamilyService.getFSList(pageVO, ownerNm, region, projectNm);
	}

	@PostMapping(value = "/getSelectOwnerList")
	@ApiOperation(value = " 下拉权属人 ", notes = " 下拉权属人  ")
	public LyhtResultBody<List<Map>> getOwnerLists(LyhtPageVO pageVO, String region, String scope, String name,
			String stage) {
		if (null != region && !"".equals(region)) {
			List<Map> list = projectPlanDao.regionName(region);
			if (list.size() > 0) {
				region = list.get(0).get("merger_name").toString();

			}
		}
		return abmFamilyService.getOwnerLists(pageVO, region, scope, name, stage);
	}

	@PostMapping(value = "/getListTree")
	@ApiOperation(value = " 青苗 ", notes = " 青苗 ")
	public LyhtResultBody<List<Map>> getQMList(LyhtPageVO pageVO, String ownerNm, String region, String projectNm) {
		return abmFamilyService.getQMList(pageVO, ownerNm, region, projectNm);
	}

	@PostMapping(value = "/getZXList")
	@ApiOperation(value = " 房屋装修", notes = " 装修 ")
	public LyhtResultBody<List<Map>> getZXList(LyhtPageVO pageVO, String ownerNm) {
		return abmFamilyService.getZXList(pageVO, ownerNm);
	}

	@PostMapping(value = "/getOwnerInfo")
	@ApiOperation(value = " 权属人信息 ", notes = " 权属人信息 ")
	public LyhtResultBody<Map> getOwnerInfo(String ownerNm, String taskId) {
		if (StringUtils.isNotBlank(taskId)) {
			List<Map> list = abmFamilyService.getOwnerNm(taskId);
			if (list != null && !list.isEmpty()) {
				ownerNm = list.get(0).get("nm").toString();
			}
		}
		if (StringUtils.isBlank(ownerNm)) {
			throw new LyhtRuntimeException("找不到对应的权属人！");
		}
		return abmFamilyService.getOwnerInfo(ownerNm);
	}

	@PostMapping(value = "/getLandHouse")
	@ApiOperation(value = " 获取权属人房屋 ", notes = " 获取权属人房屋 ")
	public LyhtResultBody<List<Map>> getLandHouse(LyhtPageVO pageVO, String ownerNm) {
		return abmFamilyService.getLandHouse(pageVO, ownerNm);
	}

	@PostMapping(value = "/getTudi")
	@ApiOperation(value = " 获取权属人土地", notes = "获取权属人土地")
	public LyhtResultBody<List<Map>> getTudi(LyhtPageVO pageVO, String ownerNm) {
		return abmFamilyService.getTudi(pageVO, ownerNm);
	}

	@PostMapping(value = "/getOwnerList")
	@ApiOperation(value = " 实物指标权属人 ", notes = " 获取实物指标权属人  ")
	public LyhtResultBody<List<Map>> getOwnerList(LyhtPageVO pageVO, String region, String scope, String name,
			String stage, String processId, String flag, String nm, String idCard) {
		LyhtResultBody<List<Map>> map = null;
//		if (null != region && !"".equals(region)) {
//			List<Map> list = projectPlanDao.regionName(region);
//			if (list.size() > 0) {
//				region = list.get(0).get("merger_name").toString();
//			}
//		}
		if (flag != null) {
			if (flag.equals("bq")) {
				map = requisitionPlanService.getHdList(pageVO, region, name, nm);
			} else {
				map = abmFamilyService.getOwnerList(pageVO, region, scope, name, stage, processId, nm, idCard);
			}
		} else {
			map = abmFamilyService.getOwnerList(pageVO, region, scope, name, stage, processId, nm, idCard);
		}
		return map;
	}

	@PostMapping(value = "/getJiaTi")
	@ApiOperation(value = " 家庭成员列表 ", notes = " 家庭成员列表  ")
	public LyhtResultBody<List<Map>> getJiaTi(LyhtPageVO pageVO, String region, String name) {
		return abmFamilyService.getJiaTi(pageVO, region, name);
	}

	@PostMapping(value = "/saveLog")
	@ApiOperation(value = " 日志 ", notes = "日志")
	public void saveLog(AbmFamilyEntity abmFamilyEntity, AbmLandEntity tudiEntity, AbmHouseEntity houseEntity,
			AbmTreesEntity treeEntity, AbmBuildingEntity subsidiaryEntity, AbmHousesDecorationEntity zxEntity,
			String flag, HttpServletRequest request) {
		if ("renkou".equals(flag)) {
			if (abmFamilyEntity.getId() != null) {
				AbmFamilyEntity logEntity = abmFamilyDao.getOne(abmFamilyEntity.getId());
				List<AbmFamilyEntity> list = new ArrayList<AbmFamilyEntity>();
				list.add(logEntity);
				SysLog log = new SysLog();
				log.setNm(abmFamilyEntity.getOwnerNm());// 数据nm
				log.setLogtime(DateUtil.getDateTime());// 操作时间
				log.setName(SystemUtil.getLoginStaffName(request));// 操作人
				log.setOpernm(abmFamilyEntity.getNm());// 操作nm
				log.setMenuflag("/move/family/saveAnZhi");// 模块标识
				log.setDictnmOpttype("人口");// 操作类型
				log.setOlddata(list.toString());// 旧数据
				log.setNewdata("");// 新数据
				logDao.save(log);
			}
		}
		if ("tudi".equals(flag)) {
			if (tudiEntity.getId() != null) {
				AbmLandEntity logEntity = tudiDao.getOne(tudiEntity.getId());
				List<AbmLandEntity> list = new ArrayList<AbmLandEntity>();
				list.add(logEntity);
				SysLog log = new SysLog();
				log.setNm(tudiEntity.getOwnerNm());// 数据nm
				log.setLogtime(DateUtil.getDateTime());// 操作时间
				log.setName(SystemUtil.getLoginStaffName(request));// 操作人
				log.setOpernm(tudiEntity.getNm());// 操作nm
				log.setMenuflag("/move/family/saveAnZhi");// 模块标识
				log.setDictnmOpttype("土地");// 操作类型
				log.setOlddata(list.toString());// 旧数据
				log.setNewdata("");// 新数据
				logDao.save(log);
			}
		}
		if ("fw".equals(flag)) {
			if (houseEntity.getId() != null) {
				AbmHouseEntity logEntity = houseDao.getOne(houseEntity.getId());
				List<AbmHouseEntity> list = new ArrayList<AbmHouseEntity>();
				list.add(logEntity);
				SysLog log = new SysLog();
				log.setNm(houseEntity.getOwnerNm());// 数据nm
				log.setLogtime(DateUtil.getDateTime());// 操作时间
				log.setName(SystemUtil.getLoginStaffName(request));// 操作人
				log.setOpernm(houseEntity.getNm());// 操作nm
				log.setMenuflag("/move/family/saveAnZhi");// 模块标识
				log.setDictnmOpttype("房屋");// 操作类型
				log.setOlddata(list.toString());// 旧数据
				log.setNewdata("");// 新数据
				logDao.save(log);
			}
		}
		if ("tree".equals(flag)) {
			if (treeEntity.getId() != null) {
				AbmTreesEntity logEntity = treeDao.getOne(treeEntity.getId());
				List<AbmTreesEntity> list = new ArrayList<AbmTreesEntity>();
				list.add(logEntity);
				SysLog log = new SysLog();
				log.setNm(treeEntity.getOwnerNm());// 数据nm
				log.setLogtime(DateUtil.getDateTime());// 操作时间
				log.setName(SystemUtil.getLoginStaffName(request));// 操作人
				log.setOpernm(treeEntity.getNm());// 操作nm
				log.setMenuflag("/move/family/saveAnZhi");// 模块标识
				log.setDictnmOpttype("树木");// 操作类型
				log.setOlddata(list.toString());// 旧数据
				log.setNewdata("");// 新数据
				logDao.save(log);
			}
		}
		if ("fushu".equals(flag)) {
			if (subsidiaryEntity.getId() != null) {
				AbmBuildingEntity logEntity = subsidiaryDao.getOne(subsidiaryEntity.getId());
				List<AbmBuildingEntity> list = new ArrayList<AbmBuildingEntity>();
				list.add(logEntity);
				SysLog log = new SysLog();
				log.setNm(subsidiaryEntity.getOwnerNm());// 数据nm
				log.setLogtime(DateUtil.getDateTime());// 操作时间
				log.setName(SystemUtil.getLoginStaffName(request));// 操作人
				log.setOpernm(subsidiaryEntity.getNm());// 操作nm
				log.setMenuflag("/move/family/saveAnZhi");// 模块标识
				log.setDictnmOpttype("附属建筑");// 操作类型
				log.setOlddata(list.toString());// 旧数据
				log.setNewdata("");// 新数据
				logDao.save(log);
			}
		}
		if ("zx".equals(flag)) {
			if (subsidiaryEntity.getId() != null) {
				AbmHousesDecorationEntity logEntity = zxDao.getOne(zxEntity.getId());
				List<AbmHousesDecorationEntity> list = new ArrayList<AbmHousesDecorationEntity>();
				list.add(logEntity);
				SysLog log = new SysLog();
				log.setNm(subsidiaryEntity.getOwnerNm());// 数据nm
				log.setLogtime(DateUtil.getDateTime());// 操作时间
				log.setName(SystemUtil.getLoginStaffName(request));// 操作人
				log.setOpernm(subsidiaryEntity.getNm());// 操作nm
				log.setMenuflag("/move/family/saveAnZhi");// 模块标识
				log.setDictnmOpttype("房屋装修");// 操作类型
				log.setOlddata(list.toString());// 旧数据
				log.setNewdata("");// 新数据
				logDao.save(log);
			}
		}

	}
	
	
	@PostMapping(value = "/getOwnerAllList")
	@ApiOperation(value = " 实物指标所有权属人 ", notes = " 获取所有实物指标权属人  ")
	public LyhtResultBody<List<Map>> getOwnerAllList(String region) {
		LyhtResultBody<List<Map>> map = abmFamilyService.getOwnerAllList( region);
		return map;
	}
	
	@PostMapping(value = "/getOwnerDetails")
	@ApiOperation(value = " 权属人基本信息 ", notes = " 权属人基本信息 ")
	public LyhtResultBody<AbmFamilyEntity> getOwnerDetails(String ownerNm) {
		AbmFamilyEntity abmFamilyEntity = abmFamilyService.getOwnerDetails(ownerNm);
		return new LyhtResultBody<>(abmFamilyEntity);
	}

}
