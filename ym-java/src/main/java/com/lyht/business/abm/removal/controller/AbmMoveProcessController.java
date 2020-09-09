package com.lyht.business.abm.removal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.entity.AbmMoveProcessEntity;
import com.lyht.business.abm.removal.service.AbmMoveService;
import com.lyht.business.abm.removal.vo.AbmMoveProcessInfoVO;
import com.lyht.business.abm.removal.vo.AbmMoveProcessVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/move/process", tags = "搬迁安置流程相关api")
@RestController
@RequestMapping("/abm/move/process")
public class AbmMoveProcessController {

	@Autowired 
	private AbmMoveService abmMoveService;
	
	@PostMapping(value = "/directlyToComplete")
	@ApiOperation(value = "批量确认功能")
	public LyhtResultBody<Object> directlyToComplete(String nms) {
		return abmMoveService.directlyToComplete(nms);
	}

	@ApiOperation(value = "发起搬迁安置确认流程", notes = "发起搬迁安置确认流程")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query"),
			@ApiImplicitParam(name = "remark", value = "备注", paramType = "query") })
	@PostMapping("/start")
	public LyhtResultBody<AbmMoveProcessEntity> startProcess(HttpServletRequest request,
			@RequestParam(required = false, value = "file") MultipartFile file, String ownerNm, String remark) {
		AbmMoveProcessEntity startProcess = abmMoveService.startProcess(request, file, ownerNm, remark);
		return new LyhtResultBody<>(startProcess);
	}

	@ApiOperation(value = "查询当前流程详情(安置界定前)", notes = "查询当前流程详情(安置界定前)")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query"),
			@ApiImplicitParam(name = "processId", value = "流程ID", paramType = "query") })
	@GetMapping("/get")
	public LyhtResultBody<AbmMoveProcessVO> findByOwnerNm(String ownerNm, String processId) {
		AbmMoveProcessVO findByOwnerNm = abmMoveService.findByOwnerNm(ownerNm, processId);
		return new LyhtResultBody<>(findByOwnerNm);
	}

	@ApiOperation(value = "查询当前流程详情(安置界定后)", notes = "查询当前流程详情(安置界定后)")
	@ApiImplicitParam(name = "processId", value = "流程ID", paramType = "query")
	@GetMapping("/detail")
	public LyhtResultBody<AbmMoveProcessInfoVO> getProcessInfo(String processId) {
		AbmMoveProcessInfoVO processInfo = abmMoveService.getProcessInfo(processId);
		return new LyhtResultBody<>(processInfo);
	}

}