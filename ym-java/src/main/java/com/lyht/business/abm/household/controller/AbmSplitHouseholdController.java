package com.lyht.business.abm.household.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.household.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.household.entity.AbmSplitHouseholdEntity;
import com.lyht.business.abm.household.service.AbmSplitHouseholdService;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "分户", tags = "分户")
@RequestMapping(value = "/abm/split/household")
@RestController
public class AbmSplitHouseholdController {

	@Autowired
	private AbmSplitHouseholdService abmSplitHouseholdService;

	@ApiOperation(value = "开始分户", notes = "开始分户")
	@ApiImplicitParam(name = "splitOwnerNm", value = "分户--户主nm", paramType = "query")
	@PostMapping(value = "/start")
	public LyhtResultBody<List<AbmSplitHouseholdNodeVO>> startSplit(String splitOwnerNm, Integer type) {
		List<AbmSplitHouseholdNodeVO> startSplit = abmSplitHouseholdService.startSplit(splitOwnerNm, type);
		return new LyhtResultBody<>(startSplit);
	}

	@ApiOperation(value = "获取分户下拉框数据", notes = "获取分户下拉框数据")
	@PostMapping(value = "/spliting/data")
	public LyhtResultBody<List<AbmFamilyEntity>> getSplitData(AbmSplitDataParamVO abmSplitDataParamVO) {
		List<AbmFamilyEntity> splitData = abmSplitHouseholdService.getSplitData(abmSplitDataParamVO);
		return new LyhtResultBody<>(splitData);
	}

	@ApiOperation(value = "分户操作", notes = "分户操作")
	@PostMapping(value = "/spliting")
	public LyhtResultBody<List<AbmSplitHouseholdNodeVO>> splitHousehold(
			AbmSplitHouseholdParamVO abmSplitHouseholdParamVO) {
		List<AbmSplitHouseholdNodeVO> splitHousehold = abmSplitHouseholdService
				.splitHousehold(abmSplitHouseholdParamVO);
		return new LyhtResultBody<>(splitHousehold);
	}

	@ApiOperation(value = "指标划分操作", notes = "指标划分操作")
	@PostMapping(value = "/division")
	public LyhtResultBody<List<AbmSplitHouseholdNodeVO>> divisionData(AbmSplitDivisionParamVO abmSplitDivisionParamVO) {
		List<AbmSplitHouseholdNodeVO> divisionData = abmSplitHouseholdService.divisionData(abmSplitDivisionParamVO);
		return new LyhtResultBody<>(divisionData);
	}

	@ApiOperation(value = "修改家庭成员--与户主关系", notes = "修改家庭成员--与户主关系")
	@PostMapping(value = "/update/relationship")
	public LyhtResultBody<List<AbmSplitHouseholdNodeVO>> updateMasterRelationship(
			AbmSplitUpdateParamVO abmSplitUpdateParamVO) {
		List<AbmSplitHouseholdNodeVO> updateMasterRelationship = abmSplitHouseholdService
				.updateMasterRelationship(abmSplitUpdateParamVO);
		return new LyhtResultBody<>(updateMasterRelationship);
	}

	@ApiOperation(value = "数据拆分", notes = "数据拆分")
	@PostMapping(value = "/spliting/number")
	public LyhtResultBody<List<AbmSplitHouseholdNodeVO>> splitNumber(AbmSplitNumberParamVO abmSplitNumberParamVO) {
		List<AbmSplitHouseholdNodeVO> splitNumber = abmSplitHouseholdService.splitNumber(abmSplitNumberParamVO);
		return new LyhtResultBody<>(splitNumber);
	}

	@ApiOperation(value = "退出分户操作", notes = "退出分户操作")
	@ApiImplicitParam(name = "splitOwnerNm", value = "分户--户主nm", paramType = "query")
	@PostMapping(value = "/quit")
	public LyhtResultBody<Boolean> quitSplit(String splitOwnerNm) {
		boolean quitSplit = abmSplitHouseholdService.quitSplit(splitOwnerNm);
		return new LyhtResultBody<>(quitSplit);
	}

	@ApiOperation(value = "发起分户流程", notes = "发起分户流程")
	@ApiImplicitParam(name = "splitOwnerNm", value = "分户--户主nm", paramType = "query")
	@PostMapping(value = "/start/process")
	public LyhtResultBody<AbmSplitHouseholdEntity> startProcess(HttpServletRequest request, String splitOwnerNm) {
		AbmSplitHouseholdEntity startProcess = abmSplitHouseholdService.startProcess(request, splitOwnerNm);
		return new LyhtResultBody<>(startProcess);
	}

	@ApiOperation(value = "获取分户流程详情", notes = "获取分户流程详情")
	@ApiImplicitParam(name = "processId", value = "流程ID", paramType = "query")
	@PostMapping(value = "/process/get")
	public LyhtResultBody<AbmSplitHouseholdVO> get(String ownerNm, String processId) {
		AbmSplitHouseholdVO splitInfo = abmSplitHouseholdService.getSplitInfo(ownerNm, processId);
		return new LyhtResultBody<>(splitInfo);
	}

	@ApiOperation(value = "发起分户申请--流程", notes = "发起分户申请--流程")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "分户--户主nm", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", paramType = "query") })
	@PostMapping(value = "/apply/start/process")
	public LyhtResultBody<AbmSplitHouseholdEntity> startProcess(HttpServletRequest request,
			@RequestParam(required = false, value = "files") MultipartFile[] files, String ownerNm, String remark) {
		AbmSplitHouseholdEntity startApplyProcess = abmSplitHouseholdService.startApplyProcess(request, files, ownerNm, remark);
		return new LyhtResultBody<>(startApplyProcess);
	}

	@ApiOperation(value = "获取分户申请--流程详情", notes = "获取分户申请--流程详情")
	@ApiImplicitParam(name = "processId", value = "流程ID", paramType = "query")
	@PostMapping(value = "/apply/process/get")
	public LyhtResultBody<Map> get(String processId) {
		Map splitApplyInfo = abmSplitHouseholdService.getSplitApplyInfo(processId);
		return new LyhtResultBody<>(splitApplyInfo);
	}

	@ApiOperation(value = "分户暂存功能", notes = "分户暂存功能")
	@PostMapping(value = "/storage/household")
	public LyhtResultBody storageHousehold(String ownerNm, @RequestBody List<StorageHouseholdVO> list){
		if(StringUtils.isBlank(ownerNm)){
			throw new LyhtRuntimeException("参数不能为空");
		}
		abmSplitHouseholdService.storageHousehold(ownerNm, list);
		return new LyhtResultBody<>();
	}

	@ApiOperation(value = "清除分户暂存功能", notes = "清除分户暂存功能")
	@PostMapping(value = "/clear/storage/household")
	public LyhtResultBody<Void> clearStorageHousehold(String ownerNm){
		if(StringUtils.isBlank(ownerNm)){
			throw new LyhtRuntimeException("参数不能为空");
		}
		abmSplitHouseholdService.clearStorageHousehold(ownerNm);
		return new LyhtResultBody<>();
	}

	@ApiOperation(value = "分户暂存数据存储", notes = "分户暂存数据存储")
	@PostMapping(value = "/storage/data/household")
	public LyhtResultBody<List<StorageHouseholdVO>> storageDataHousehold(@RequestBody List<AbmSplitHouseholdNodeVO> abmSplitHouseholdNodeVO, String ownerNm){
		return new LyhtResultBody(abmSplitHouseholdService.storageDataHousehold(abmSplitHouseholdNodeVO, ownerNm));
	}



}
