package com.lyht.business.abm.settle.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.settle.entity.SettleInfo;
import com.lyht.business.abm.settle.entity.SettleStatus;
import com.lyht.business.abm.settle.service.SettleInfoService;
import com.lyht.business.abm.settle.service.SettleStatusService;
import com.lyht.business.abm.settle.vo.SettleInfoVo;
import com.lyht.business.abm.settle.vo.SettleVo;
import com.lyht.business.letter.entity.Letters;
import com.lyht.business.letter.vo.LettersVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author HuangTianhao
* @date 2019年11月28日 
*/

@RequestMapping("/settle")
@Api(value = "/settle", tags = "居民点相关api")
@RestController
public class SettleInfoController {
	@Autowired
	private SettleInfoService infoService;
	@Autowired
	private SettleStatusService statusService;
	
	@ApiOperation(value = "条件分页查询-居民点信息info", notes = "")
	@PostMapping("/listinfo")
	public LyhtResultBody<List<Map>> getList( LyhtPageVO lyhtPageVO, SettleVo vo){
		return infoService.getList(lyhtPageVO, vo);
	}
	
	@ApiOperation(value = "条件分页查询-居民点状态信息status", notes = "timeStart不为空")
	@PostMapping("/liststatus")
	public LyhtResultBody<List<Map>> getStatusList( LyhtPageVO lyhtPageVO, SettleVo vo){
		return statusService.getList(lyhtPageVO, vo);
	}
	
	@ApiOperation(value = "查询所有居民点名称info", notes = "查询所有居民点名称")
	@PostMapping("/listinfoname")
	public LyhtResultBody<List<String>> getNameList(){
		return infoService.getNameList();
	}
	
	@ApiOperation(value = "新增/修改info", notes = "新增name不为空，修改id、name不为空")
	@PostMapping("/saveinfo")
	public LyhtResultBody<SettleInfo> addInfo( SettleInfo info){
		return infoService.addInfo(info);
	}
	
	@ApiOperation(value = "新增/修改status", notes = "新增name不为空，修改id、name不为空")
	@PostMapping("/savestatus")
	public LyhtResultBody<SettleStatus> addStatus(SettleStatus status){
		return statusService.addStatus(status);
	}
	
	@ApiOperation(value = "删除status", notes = "根据id删除")
	@PostMapping("/deletestatus")
	public LyhtResultBody<Integer> deleteStatus(Integer id){
		return statusService.deleteById(id);
	}
	
	@ApiOperation(value = "删除info", notes = "根据id删除info项，关联删除相应status项")
	@PostMapping("/deleteinfo")
	public LyhtResultBody<Integer> deleteInfo(Integer id){
		return infoService.deleteInfo(id);
	}
	
	@ApiOperation(value = "批量删除info", notes = "传入id用英文逗号分隔，关联删除相应status项")
	@PostMapping("/batchinfo")
	public LyhtResultBody<String> batchInfo(String ids){
		return infoService.batchInfo(ids);
	}
	
	@ApiOperation(value = "批量删除status", notes = "传入id用英文逗号分隔")
	@PostMapping("/batchstatus")
	public LyhtResultBody<String> batchStatus(String ids){
	return statusService.batchStatus(ids);
	
	}




}
