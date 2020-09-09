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
import com.lyht.business.process.service.ProcessRoleUserSyncService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/process/role/user", tags = "流程--岗位与用户关联关系api")
@RestController
@RequestMapping("/process/role/user")
public class ProcessRoleUserSyncController {
	private Logger log = LoggerFactory.getLogger(ProcessRoleUserSyncController.class);
	
	@Autowired
	private ProcessRoleUserSyncService processRoleUserSyncService;

	/**
	 * 岗位与用户关联关系同步
	 * @return
	 */
	@ApiOperation(value = "岗位与用户关联关系同步", notes = "岗位与用户关联关系同步")
	@GetMapping("/sync")
	public LyhtResultBody<Boolean> sync() {
		boolean roleUserSync = false;
		try {
			roleUserSync = processRoleUserSyncService.roleUserSync();
		} catch (Exception e) {
			log.error("=====ProcessRoleUserSyncController=====Method:sync=====状态：" + roleUserSync + "=====", e);
		}
		if (!roleUserSync) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(roleUserSync);
	}

}