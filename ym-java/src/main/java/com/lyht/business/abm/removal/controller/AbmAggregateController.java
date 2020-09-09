package com.lyht.business.abm.removal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.service.AbmAggregateService;
import com.lyht.business.abm.removal.vo.AbmAggregateCardVO;
import com.lyht.business.abm.removal.vo.AbmAggregateChatVO;
import com.lyht.business.abm.removal.vo.AbmAggregateTreeVO;
import com.lyht.business.abm.removal.vo.AbmOwnerAggregateVO;
import com.lyht.business.abm.removal.vo.AbmRegionTreeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/removal/aggregate", tags = "(实施)实物指标汇总api")
@RestController
@RequestMapping("/abm/removal/aggregate")
public class AbmAggregateController {

	@Autowired
	private AbmAggregateService abmAggregateService;

	@ApiOperation(value = "实物指标汇总树--行政区域", notes = "实物指标汇总树--行政区域")
	@GetMapping("/region/tree")
	public LyhtResultBody<List<AbmRegionTreeVO>> findRegionTree() {
		List<AbmRegionTreeVO> findRegionTree = abmAggregateService.findRegionTree();
		return new LyhtResultBody<>(findRegionTree);
	}

	@ApiOperation(value = "实物指标汇总--人口", notes = "实物指标汇总--人口")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/population")
	public LyhtResultBody<List<AbmAggregateCardVO>> findPopulationAggregate(String mergerName) {
		List<AbmAggregateCardVO> findPopulationAggregate = abmAggregateService.findPopulationAggregate(mergerName);
		return new LyhtResultBody<>(findPopulationAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--企事业单位", notes = "实物指标汇总树--企事业单位")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/enter")
	public LyhtResultBody<List<AbmAggregateTreeVO>> findEnterAggregate(String mergerName) {
		List<AbmAggregateTreeVO> findEnterAggregate = abmAggregateService.findEnterAggregate(mergerName);
		return new LyhtResultBody<>(findEnterAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--房屋", notes = "实物指标汇总树--房屋")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/house")
	public LyhtResultBody<List<AbmAggregateTreeVO>> findHouseAggregate(String mergerName) {
		List<AbmAggregateTreeVO> findEnterAggregate = abmAggregateService.findHouseAggregate(mergerName);
		return new LyhtResultBody<>(findEnterAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--房屋装修", notes = "实物指标汇总树--房屋装修")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/house/decoration")
	public LyhtResultBody<List<AbmAggregateTreeVO>> findHouseDecorationAggregate(String mergerName) {
		List<AbmAggregateTreeVO> findHouseDecorationAggregate = abmAggregateService
				.findHouseDecorationAggregate(mergerName);
		return new LyhtResultBody<>(findHouseDecorationAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--零星树木", notes = "实物指标汇总树--零星树木")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/trees")
	public LyhtResultBody<List<AbmAggregateTreeVO>> findTreesAggregate(String mergerName) {
		List<AbmAggregateTreeVO> findTreesAggregate = abmAggregateService.findTreesAggregate(mergerName);
		return new LyhtResultBody<>(findTreesAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--宅基地", notes = "实物指标汇总树--宅基地")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/homestead")
	public LyhtResultBody<List<AbmAggregateCardVO>> findHomesteadAggregate(String mergerName) {
		List<AbmAggregateCardVO> findTreesAggregate = abmAggregateService.findHomesteadAggregate(mergerName);
		return new LyhtResultBody<>(findTreesAggregate);
	}

	@ApiOperation(value = "实物指标汇总--户主/企事业单位", notes = "实物指标汇总--户主/企事业单位")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@PostMapping("/owner")
	public LyhtResultBody<List<AbmOwnerAggregateVO>> findOwnerAggregate(LyhtPageVO lyhtPageVO, String mergerName) {
		LyhtResultBody<List<AbmOwnerAggregateVO>> findOwnerAggregate = abmAggregateService
				.findOwnerAggregate(mergerName, lyhtPageVO);
		return findOwnerAggregate;
	}

	@ApiOperation(value = "实物指标汇总--卡片", notes = "实物指标汇总--卡片")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "卡片类型(population：人口；enter：企事业单位；house：房屋；decoration：房屋装修；trees：零星树木；homestead：宅基地；)", paramType = "query") })
	@GetMapping("/card")
	public LyhtResultBody<List<AbmAggregateTreeVO>> findAggregateCard(String mergerName, String type) {
		List<AbmAggregateTreeVO> findAggregateCard = abmAggregateService.findAggregateCard(mergerName, type);
		return new LyhtResultBody<>(findAggregateCard);
	}

	@ApiOperation(value = "按模块统计户数", notes = "按模块统计户数")
	@GetMapping("/chat")
	public LyhtResultBody<List<AbmAggregateChatVO>> findAggregateChat() {
		List<AbmAggregateChatVO> findAggregateChat = abmAggregateService.findAggregateChat();
		return new LyhtResultBody<>(findAggregateChat);
	}

}