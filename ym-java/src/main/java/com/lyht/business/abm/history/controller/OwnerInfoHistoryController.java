package com.lyht.business.abm.history.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.history.service.OwnerInfoHistoryService;
import com.lyht.business.abm.history.vo.OwnerInfoHistoryVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/history")
@Api(value = "/history", tags = "历史记录")
public class OwnerInfoHistoryController {
	
	@Autowired
	private OwnerInfoHistoryService service;
	
	
	@ApiOperation(value = "分页查询，排序查询，条件查询 根据户主查询", notes = "新增 修改")
	@PostMapping("/page")
	public LyhtResultBody<List<OwnerInfoHistoryVO>> page(LyhtPageVO lyhtPageVO,String name,String startTime,String endTime,String nm) {
		return service.page(lyhtPageVO,name,startTime,endTime,nm);
	}

}
