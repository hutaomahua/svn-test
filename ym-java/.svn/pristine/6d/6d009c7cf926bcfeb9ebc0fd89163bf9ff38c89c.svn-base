package com.lyht.business.abm.review.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lyht.base.common.exception.LyhtRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.vo.AbmAgriculturalFacilitiesVO;
import com.lyht.business.abm.removal.vo.AbmHomesteadVO;
import com.lyht.business.abm.removal.vo.PersonaWealthBuildingObjectVO;
import com.lyht.business.abm.removal.vo.PersonaWealthBuildingVO;
import com.lyht.business.abm.removal.vo.PersonaWealthDecorationObjectVO;
import com.lyht.business.abm.removal.vo.PersonaWealthDecorationVO;
import com.lyht.business.abm.removal.vo.PersonaWealthFamilyObjectVO;
import com.lyht.business.abm.removal.vo.PersonaWealthFamilyVO;
import com.lyht.business.abm.removal.vo.PersonaWealthHouseObjectVO;
import com.lyht.business.abm.removal.vo.PersonaWealthHouseVO;
import com.lyht.business.abm.removal.vo.PersonaWealthLandObjectVO;
import com.lyht.business.abm.removal.vo.PersonaWealthLandVO;
import com.lyht.business.abm.removal.vo.PersonaWealthTreesObjectVO;
import com.lyht.business.abm.removal.vo.PersonaWealthTreesVO;
import com.lyht.business.abm.removal.vo.PersonalWealthAgriculturalFacilitiesVO;
import com.lyht.business.abm.removal.vo.PersonalWealthHomesteadVO;
import com.lyht.business.abm.review.entity.PersonalWealth;
import com.lyht.business.abm.review.entity.PersonalWealthData;
import com.lyht.business.abm.review.service.PersonaWealthService;
import com.lyht.business.abm.review.vo.OwnerDocInfoVO;
import com.lyht.business.abm.review.vo.PersonaWealthIndividualObjectVO;
import com.lyht.business.abm.review.vo.PersonaWealthIndividualVO;
import com.lyht.business.abm.review.vo.PersonalWealthInfoVO;
import com.lyht.business.abm.review.vo.PersonalWealthVO;
import com.lyht.business.pub.entity.PubDictValue;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 实物指标复核申请（个人财产）
 * 
 * @author wzw
 */
@RestController
@RequestMapping("/abm/personaWealth")
@Api(value = "/abm/personaWealth", tags = "实物指标复核申请（个人财产）")
public class PersonaWealthController {

	@Autowired
	private PersonaWealthService service;
	
	@ApiOperation(value = "自定义添加该记录复核上限", notes = "修改")
	@PostMapping("/updateTimeCaps")
	public LyhtResultBody<Object> updateTimeCaps(String ownerNm,Integer timeCaps){
		return service.updateTimeCaps(ownerNm, timeCaps);
	}
	
	@ApiOperation(value = "批量跳过实物指标步骤", notes = "修改")
	@PostMapping("/directlyToComplete")
	public LyhtResultBody<Object> DirectlyToComplete(String ids){
		return service.DirectlyToComplete(ids);
	}
	
	/**
	 * 根据变更申请流程查询JSON变更数据
	 */
	@ApiOperation(value = "根据变更申请流程查询JSON变更数据", notes = "查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "taskId", paramType = "query", value = "流程id", required = true), })
	@PostMapping("/findByChangeProcessId")
	public LyhtResultBody<PersonalWealthData> findByChangeProcessId(String taskId) {
		return service.findByChangeProcessId(taskId);
	}

	/**
	 * 查看复核申请
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据id查询复核申请记录信息以及变更记录", notes = "查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", paramType = "query", value = "复核记录id reviewId", required = true), })
	@PostMapping("/getById")
	public LyhtResultBody<PersonalWealthInfoVO> getById(String taskId, Integer id) {
		return service.getById(taskId, id);
	}

	/**
	 * 查询权属人的个体户信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的个体户信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmIndividual")
	public LyhtResultBody<List<PersonaWealthIndividualVO>> findByOwnerNmIndividual(String ownerNm, LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmIndividual(ownerNm, lyhtPageVO);
	}

	@ApiOperation(value = "查询权属人的农副业设施", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/find/OwnerNmAgriculturalFacilities")
	public LyhtResultBody findByOwnerNmAgriculturalFacilities(String ownerNm, String tableName) {
		if(StringUtils.isBlank(ownerNm) || StringUtils.isBlank(tableName)){
			throw new LyhtRuntimeException("参数不能为空");
		}
		return new LyhtResultBody(service.findByOwnerNmAgriculturalFacilities(ownerNm, tableName));
	}

	@ApiOperation(value = "查询权属人的宅基地", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/find/OwnerNmHomestead")
	public LyhtResultBody findByOwnerNmHomestead(String ownerNm, String tableName) {
		if(StringUtils.isBlank(ownerNm) || StringUtils.isBlank(tableName)){
			throw new LyhtRuntimeException("参数不能为空");
		}
		return new LyhtResultBody(service.findByOwnerNmHomestead(ownerNm, tableName));
	}

	/**
	 * 查询权属人的房屋装修信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的房屋装修", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmDecoration")
	public LyhtResultBody<List<PersonaWealthDecorationVO>> findByOwnerNmDecoration(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmDecoration(taskId, ownerNm, lyhtPageVO);
	}

	/**
	 * 查询权属人的附属建筑物信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的附属建筑物信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmBuilding")
	public LyhtResultBody<List<PersonaWealthBuildingVO>> findByOwnerNmBuilding(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmBuilding(taskId, ownerNm, lyhtPageVO);
	}

	/**
	 * 查询权属人的零星树木信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的零星树木信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmTrees")
	public LyhtResultBody<List<PersonaWealthTreesVO>> findByOwnerNmTrees(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmTrees(taskId, ownerNm, lyhtPageVO);
	}

	/**
	 * 查询权属人的房屋信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的房屋信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmHouse")
	public LyhtResultBody<List<PersonaWealthHouseVO>> findByOwnerNmHouse(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmHouse(taskId, ownerNm, lyhtPageVO);
	}

	/**
	 * 查询权属人的土地信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的土地信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmLand")
	public LyhtResultBody<List<PersonaWealthLandVO>> findByOwnerNmLand(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmLand(taskId, ownerNm, lyhtPageVO);
	}

	/**
	 * 查询权属人的家庭成员
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的家庭成员", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmFamily")
	public LyhtResultBody<List<PersonaWealthFamilyVO>> findByOwnerNmFamily(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmFamily(taskId, ownerNm, lyhtPageVO);
	}

	/**
	 * 根据nm查询移民档案信息
	 */
	@ApiOperation(value = "根据nm查询移民档案信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/getOwnerDocInfo")
	public LyhtResultBody<OwnerDocInfoVO> getOwnerDocInfo(String taskId, String ownerNm) {
		return service.getOwnerDocInfo(taskId, ownerNm);
	}

	/**
	 * 复核流程审核通过
	 */
	@ApiOperation(value = "复核流程审核后调用 flag:0：复核申请，1：变更申请；isSuccess:0：拒绝，1：驳回，2：成功 3:取消", notes = "流程通过")
	@ApiImplicitParams({ @ApiImplicitParam(name = "processId", paramType = "query", value = "流程id", required = true),
			@ApiImplicitParam(name = "flag", paramType = "query", value = "流程类型  复核流程/变更流程 0：复核申请/复核流程，1：变更申请/变更流程", required = true),
			@ApiImplicitParam(name = "isSuccess", paramType = "query", value = "审核后状态", required = true) })
	@PostMapping("/reviewProcessAuditSuccessful")
	public LyhtResultBody<Integer> reviewProcessAuditSuccessful(String processId, Integer flag, Integer isSuccess,String senderNm) {
		return service.reviewProcessAuditSuccessful(processId, flag, isSuccess,senderNm);
	}

	/**
	 * 点击变更流程时拿到权属人最后一条复核信息
	 */
	@ApiOperation(value = "根据权属人nm查到权属人最近的一条复核流程信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "权属人nm", required = true) })
	@PostMapping("/findTheLastInfoByOwnerNm")
	public LyhtResultBody<PersonalWealth> findTheLastInfoByOwnerNm(String ownerNm) {
		return service.findTheLastInfoByOwnerNm(ownerNm);
	}

	@ApiOperation(value = "个人财产列表", notes = "查询")
	@PostMapping("/page")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "region", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "户主姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "征地范围编码", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "身份证号（模糊查询）", paramType = "query"), 
			@ApiImplicitParam(name = "nm", value = "户主nm", paramType = "query"),})
	public LyhtResultBody<List<PersonalWealthVO>> page(LyhtPageVO lyhtPageVO, String region, String name, String scope,
			String idCard,String nm) {
		return service.page(lyhtPageVO, region, name, scope, idCard,nm);
	}

	@ApiOperation(value = "新增(id与nm为空),修改(需要id与nm) 复核流程变更流程发起。 flag:流程发起类型 0：复核申请，1：变更申请", notes = "新增 修改")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "flag", paramType = "query", value = "流程类型  复核流程/变更流程 0：复核申请/复核流程，1：变更申请/变更流程", required = true),
			@ApiImplicitParam(name = "personaWealth", paramType = "query", value = "复核记录对象", required = true) })
	@PostMapping("/save")
	public LyhtResultBody<PersonalWealth> save(PersonalWealth personalWealth, Integer flag, HttpServletRequest request) {
		return service.save(personalWealth, flag, request);
	}

	/**
	 * 查询权属人的个体户信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的个体户信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmIndividualList")
	public LyhtResultBody<List<PersonaWealthIndividualObjectVO>> findByOwnerNmIndividual(String taskId,String ownerNm,String masterNm) {
		return service.findByOwnerNmIndividual(taskId,ownerNm,masterNm);
	}

	/**
	 * 查询权属人的房屋装修信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的房屋装修", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmDecorationList")
	public LyhtResultBody<List<PersonaWealthDecorationObjectVO>> findByOwnerNmDecoration(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmDecoration(taskId, ownerNm,masterNm);
	}

	/**
	 * 查询权属人的附属建筑物信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的附属建筑物信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmBuildingList")
	public LyhtResultBody<List<PersonaWealthBuildingObjectVO>> findByOwnerNmBuilding(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmBuilding(taskId, ownerNm,masterNm);
	}

	/**
	 * 查询权属人的零星树木信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的零星树木信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmTreesList")
	public LyhtResultBody<List<PersonaWealthTreesObjectVO>> findByOwnerNmTrees(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmTrees(taskId, ownerNm,masterNm);
	}

	/**
	 * 查询权属人的房屋信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的房屋信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmHouseList")
	public LyhtResultBody<List<PersonaWealthHouseObjectVO>> findByOwnerNmHouse(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmHouse(taskId, ownerNm,masterNm);
	}

	/**
	 * 查询权属人的土地信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的土地信息", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmLandList")
	public LyhtResultBody<List<PersonaWealthLandObjectVO>> findByOwnerNmLand(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmLand(taskId, ownerNm,masterNm);
	}

	/**
	 * 查询权属人的家庭成员
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的家庭成员", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmFamilyList")
	public LyhtResultBody<List<PersonaWealthFamilyObjectVO>> findByOwnerNmFamily(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmFamily(taskId, ownerNm,masterNm);
	}
	
	/**
	 * 查询权属人的农副业设施
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的农副业设施", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmAgriculturalFacilitiesList")
	public LyhtResultBody<List<PersonalWealthAgriculturalFacilitiesVO>> findByOwnerNmAgriculturalFacilitiesList(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmAgriculturalFacilities(taskId, ownerNm,masterNm);
	}
	
	/**
	 * 查询权属人的农副业设施
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人农副业设施   分页", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmAgriculturalFacilities")
	public LyhtResultBody<List<AbmAgriculturalFacilitiesVO>> findByOwnerNmAgriculturalFacilities(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmAgriculturalFacilities(taskId, ownerNm, lyhtPageVO);
	}
	
	
	/**
	 * 查询权属人的宅基地
	 * 	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的宅基地  分页", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmHomestead")
	public LyhtResultBody<List<AbmHomesteadVO>> findByOwnerNmHomestead(String taskId, String ownerNm,
			LyhtPageVO lyhtPageVO) {
		return service.findByOwnerNmHomestead(taskId, ownerNm, lyhtPageVO);
	}
	
	/**
	 * 查询权属人的农副业设施
	 * 
	 * @param ownerNm
	 * @return
	 */
	@ApiOperation(value = "查询权属人的宅基地", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findByOwnerNmHomesteadList")
	public LyhtResultBody<List<PersonalWealthHomesteadVO>> findByOwnerNmHomesteadList(String taskId, String ownerNm,String masterNm) {
		return service.findByOwnerNmHomestead(taskId, ownerNm,masterNm);
	}
	/**
	 * 查询数据信息findDataByMasterNm
	 */
	@ApiOperation(value = "查询权属人的家庭成员", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/findDataByMasterNm")
	public LyhtResultBody<PersonalWealthData> findDataByMasterNm(String masterNm) {
		return service.findDataByMasterNm(masterNm);
	}
	
	/**
	 * 查询 权属人可复核项
	 */
	@ApiOperation(value = "查询权属人可复核项", notes = "查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "ownerNm", paramType = "query", value = "户主nm", required = true) })
	@PostMapping("/getPubDictValue")
	public LyhtResultBody<List<PubDictValue>> getPubDictValue(String ownerNm){
		return service.getPubDictValue(ownerNm);
	}
	
}
