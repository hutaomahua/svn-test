package com.lyht.business.process.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.process.service.ProcessRoleSyncService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/process/role", tags = "流程--岗位api")
@RestController
@RequestMapping("/process/role")
public class ProcessRoleSyncController {
	private Logger log = LoggerFactory.getLogger(ProcessRoleSyncController.class);
	
	@Autowired
	private ProcessRoleSyncService processRoleSyncService;

	/**
	 * 岗位同步
	 * @return
	 */
	@ApiOperation(value = "岗位同步", notes = "岗位同步")
	@GetMapping("/sync")
	public LyhtResultBody<Boolean> sync() {
		boolean roleSync = false;
		try {
			roleSync = processRoleSyncService.roleSync();
		} catch (Exception e) {
			log.error("=====ProcessRoleSyncController=====Method:sync=====状态：" + roleSync + "=====", e);
		}
		if (!roleSync) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(roleSync);
	}

}