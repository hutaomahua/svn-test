package com.lyht.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.pojo.SysLog;
import com.lyht.system.service.SysLogService;

/**
 * 创建人： Yanxh 
 * 脚本日期:2020年1月7日
 * 说明:  系统日志
 */
@Api(value="/sysLog", tags="日志信息")
@RestController
@RequestMapping("/sysLog")
public class SysLogController {
	
	@Autowired  
	private SysLogService services;
	
	/**
	 * 日志记录
	 * 
	 * @param syslog
	 * @return
	 */
	@PostMapping("/log")
	@ApiOperation(value = "日志记录", notes="日志记录")
	public LyhtResultBody<String> log(String nm,String menuflag,String dictnmOpttype,String oldData,String newData) {
		return services.log(nm,menuflag,dictnmOpttype,oldData,newData);
	}

	@GetMapping("/list")
	@ApiOperation(value = "日志查询", notes="日志查询，根据内码/模块/操作类型做针对于单条数据的精确查询")
	public LyhtResultBody<List<SysLog>> list(String nm,String menuflag,String dictnmOpttype){
		return services.list(nm,menuflag,dictnmOpttype);
	}
	
	@PostMapping("/list")
	@ApiOperation(value = "日志查询", notes="日志查询，根据模块/操作类型/用户内码/开始时间/结束时间做条件查询")
	public LyhtResultBody<List<SysLog>> list(String menuflag,String dictnmOpttype,String staffName,String startTime,String endTime){
		return services.list(menuflag,dictnmOpttype,staffName,startTime,endTime);
	}
}
