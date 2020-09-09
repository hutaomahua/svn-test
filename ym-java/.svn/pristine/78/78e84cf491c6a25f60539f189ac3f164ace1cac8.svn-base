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
import com.lyht.business.process.service.ProcessUserSyncService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/process/user", tags = "流程--用户api")
@RestController
@RequestMapping("/process/user")
public class ProcessUserSyncController {
	private Logger log = LoggerFactory.getLogger(ProcessUserSyncController.class);
	
	@Autowired
	private ProcessUserSyncService processUserSyncService;

	/**
	 * 用户同步
	 * @return
	 */
	@ApiOperation(value = "用户同步", notes = "用户同步")
	@GetMapping("/sync")
	public LyhtResultBody<Boolean> sync() {
		boolean userSync = false;
		try {
			userSync = processUserSyncService.userSync();
		} catch (Exception e) {
			log.error("=====ProcessUserSyncController=====Method:sync=====状态：" + userSync + "=====", e);
		}
		if (!userSync) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(userSync);
	}

}