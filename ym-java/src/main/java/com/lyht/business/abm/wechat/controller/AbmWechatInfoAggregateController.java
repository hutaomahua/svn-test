package com.lyht.business.abm.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.wechat.service.AbmWechatInfoAggregateService;
import com.lyht.business.abm.wechat.vo.AbmWechatInfoAggregateVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionChatVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionHouseVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionLandVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionOwnerVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeAggregateVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeChatVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeHouseVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeLandVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeOwnerVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/wechat/info/aggregate", tags = "（微信小程序）指标统计相关api")
@RestController
@RequestMapping("/abm/wechat/info/aggregate")
public class AbmWechatInfoAggregateController {

	@Autowired
	private AbmWechatInfoAggregateService abmWechatInfoAggregateService;

	@ApiOperation(value = "总计查询", notes = "总计查询")
	@GetMapping("/total")
	public LyhtResultBody<AbmWechatInfoAggregateVO> total() {
		AbmWechatInfoAggregateVO total = abmWechatInfoAggregateService.total();
		return new LyhtResultBody<>(total);
	}

	@ApiOperation(value = "按征地范围统计", notes = "按征地范围统计")
	@GetMapping("/scope")
	public LyhtResultBody<List<AbmWechatScopeAggregateVO>> findAggregateByScope() {
		List<AbmWechatScopeAggregateVO> findAggregateByScope = abmWechatInfoAggregateService.findAggregateByScope();
		return new LyhtResultBody<>(findAggregateByScope);
	}

//	@ApiOperation(value = "按行政区域统计", notes = "按行政区域统计")
//	@ApiImplicitParams(value = {
//			@ApiImplicitParam(name = "mergerName", value = "行政区--全称(例：云南省,迪庆藏族自治州,维西傈僳族自治县)", paramType = "query"),
//			@ApiImplicitParam(name = "type", value = "指标类型(owner：权属人；house：房屋；土地：land)", paramType = "query") })
//	@GetMapping("/region")
//	public LyhtResultBody<List<AbmWechatRegionAggregateVO>> findAggregateByRegion(String mergerName, String type) {
//		List<AbmWechatRegionAggregateVO> findAggregateByRegion = abmWechatInfoAggregateService
//				.findAggregateByRegion(mergerName, type);
//		return new LyhtResultBody<>(findAggregateByRegion);
//	}

	@ApiOperation(value = "行政区--权属人--列表", notes = "行政区--权属人--列表")
	@ApiImplicitParam(name = "parentCode", value = "行政区--上级编码", paramType = "query")
	@GetMapping("/region/by/owner")
	public LyhtResultBody<List<AbmWechatRegionOwnerVO>> findAggregateRegionByOwner(String parentCode) {
		List<AbmWechatRegionOwnerVO> findAggregateRegionByOwner = abmWechatInfoAggregateService
				.findAggregateRegionByOwner(parentCode);
		return new LyhtResultBody<>(findAggregateRegionByOwner);
	}

	@ApiOperation(value = "行政区--权属人--图", notes = "行政区--权属人--图")
	@ApiImplicitParam(name = "parentCode", value = "行政区--上级编码", paramType = "query")
	@GetMapping("/region/chat/by/owner")
	public LyhtResultBody<List<AbmWechatRegionChatVO>> findChatRegionByOwner(String parentCode) {
		List<AbmWechatRegionChatVO> findChatRegionByOwner = abmWechatInfoAggregateService
				.findChatRegionByOwner(parentCode);
		return new LyhtResultBody<>(findChatRegionByOwner);
	}

	@ApiOperation(value = "行政区--房屋--列表", notes = "行政区--房屋--列表")
	@ApiImplicitParam(name = "parentCode", value = "行政区--上级编码", paramType = "query")
	@GetMapping("/region/by/house")
	public LyhtResultBody<List<AbmWechatRegionHouseVO>> findAggregateRegionByHouse(String parentCode) {
		List<AbmWechatRegionHouseVO> findAggregateRegionByHouse = abmWechatInfoAggregateService
				.findAggregateRegionByHouse(parentCode);
		return new LyhtResultBody<>(findAggregateRegionByHouse);
	}

	@ApiOperation(value = "行政区--房屋--图", notes = "行政区--房屋--图")
	@ApiImplicitParam(name = "parentCode", value = "行政区--上级编码", paramType = "query")
	@GetMapping("/region/chat/by/house")
	public LyhtResultBody<List<AbmWechatRegionChatVO>> findChatRegionByHouse(String parentCode) {
		List<AbmWechatRegionChatVO> findChatRegionByHouse = abmWechatInfoAggregateService
				.findChatRegionByHouse(parentCode);
		return new LyhtResultBody<>(findChatRegionByHouse);
	}

	@ApiOperation(value = "行政区--土地--列表", notes = "行政区--土地--列表")
	@ApiImplicitParam(name = "parentCode", value = "行政区--上级编码", paramType = "query")
	@GetMapping("/region/by/land")
	public LyhtResultBody<List<AbmWechatRegionLandVO>> findAggregateRegionByLand(String parentCode) {
		List<AbmWechatRegionLandVO> findAggregateRegionByLand = abmWechatInfoAggregateService
				.findAggregateRegionByLand(parentCode);
		return new LyhtResultBody<>(findAggregateRegionByLand);
	}

	@ApiOperation(value = "行政区--土地--图", notes = "行政区--土地--图")
	@ApiImplicitParam(name = "parentCode", value = "行政区--上级编码", paramType = "query")
	@GetMapping("/region/chat/by/land")
	public LyhtResultBody<List<AbmWechatRegionChatVO>> findChatRegionByLand(String parentCode) {
		List<AbmWechatRegionChatVO> findChatRegionByLand = abmWechatInfoAggregateService
				.findChatRegionByLand(parentCode);
		return new LyhtResultBody<>(findChatRegionByLand);
	}

	@ApiOperation(value = "征地范围--权属人--列表", notes = "征地范围--权属人--列表")
	@GetMapping("/scope/by/owner")
	public LyhtResultBody<List<AbmWechatScopeOwnerVO>> findAggregateScopeByOwner() {
		List<AbmWechatScopeOwnerVO> findAggregateScopeByOwner = abmWechatInfoAggregateService
				.findAggregateScopeByOwner();
		return new LyhtResultBody<>(findAggregateScopeByOwner);
	}

	@ApiOperation(value = "征地范围--权属人--图", notes = "征地范围--权属人--图")
	@GetMapping("/scope/chat/by/owner")
	public LyhtResultBody<List<AbmWechatScopeChatVO>> findChatRegionByOwner() {
		List<AbmWechatScopeChatVO> findChatScopeByOwner = abmWechatInfoAggregateService.findChatScopeByOwner();
		return new LyhtResultBody<>(findChatScopeByOwner);
	}

	@ApiOperation(value = "征地范围--房屋--列表", notes = "征地范围--房屋--列表")
	@GetMapping("/scope/by/house")
	public LyhtResultBody<List<AbmWechatScopeHouseVO>> findAggregateScopeByHouse() {
		List<AbmWechatScopeHouseVO> findAggregateScopeByHouse = abmWechatInfoAggregateService
				.findAggregateScopeByHouse();
		return new LyhtResultBody<>(findAggregateScopeByHouse);
	}

	@ApiOperation(value = "征地范围--房屋--图", notes = "征地范围--房屋--图")
	@GetMapping("/scope/chat/by/house")
	public LyhtResultBody<List<AbmWechatScopeChatVO>> findChatRegionByHouse() {
		List<AbmWechatScopeChatVO> findChatScopeByHouse = abmWechatInfoAggregateService.findChatScopeByHouse();
		return new LyhtResultBody<>(findChatScopeByHouse);
	}

	@ApiOperation(value = "征地范围--土地--列表", notes = "征地范围--土地--列表")
	@GetMapping("/scope/by/land")
	public LyhtResultBody<List<AbmWechatScopeLandVO>> findAggregateScopeByLand() {
		List<AbmWechatScopeLandVO> findAggregateScopeByLand = abmWechatInfoAggregateService.findAggregateScopeByLand();
		return new LyhtResultBody<>(findAggregateScopeByLand);
	}

	@ApiOperation(value = "征地范围--土地--图", notes = "征地范围--土地--图")
	@GetMapping("/scope/chat/by/land")
	public LyhtResultBody<List<AbmWechatScopeChatVO>> findChatRegionByLand() {
		List<AbmWechatScopeChatVO> findChatScopeByLand = abmWechatInfoAggregateService.findChatScopeByLand();
		return new LyhtResultBody<>(findChatScopeByLand);
	}

}