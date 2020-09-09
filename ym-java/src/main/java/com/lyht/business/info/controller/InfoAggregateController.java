package com.lyht.business.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.service.InfoAggregateService;
import com.lyht.business.info.vo.InfoAggregateCardVO;
import com.lyht.business.info.vo.InfoAggregateTreeVO;
import com.lyht.business.info.vo.InfoLandAggregateCardVO;
import com.lyht.business.info.vo.InfoLandAggregateParamVO;
import com.lyht.business.info.vo.InfoLandAggregateTreeVO;
import com.lyht.business.info.vo.InfoOwnerAggregateVO;
import com.lyht.business.info.vo.InfoRegionTreeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/info/aggregate", tags = "实物指标汇总api")
@RestController
@RequestMapping("/info/aggregate")
public class InfoAggregateController {

	@Autowired
	private InfoAggregateService infoAggregateService;

	@ApiOperation(value = "实物指标汇总树--行政区域", notes = "实物指标汇总树--行政区域")
	@GetMapping("/region/tree")
	public LyhtResultBody<List<InfoRegionTreeVO>> findRegionTree() {
		List<InfoRegionTreeVO> findRegionTree = infoAggregateService.findRegionTree();
		return new LyhtResultBody<>(findRegionTree);
	}

	@ApiOperation(value = "实物指标汇总--人口", notes = "实物指标汇总--人口")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/population")
	public LyhtResultBody<List<InfoAggregateCardVO>> findPopulationAggregate(String mergerName) {
		List<InfoAggregateCardVO> findPopulationAggregate = infoAggregateService.findPopulationAggregate(mergerName);
		return new LyhtResultBody<>(findPopulationAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--企事业单位", notes = "实物指标汇总树--企事业单位")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/enter")
	public LyhtResultBody<List<InfoAggregateTreeVO>> findEnterAggregate(String mergerName) {
		List<InfoAggregateTreeVO> findEnterAggregate = infoAggregateService.findEnterAggregate(mergerName);
		return new LyhtResultBody<>(findEnterAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--房屋", notes = "实物指标汇总树--房屋")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/house")
	public LyhtResultBody<List<InfoAggregateTreeVO>> findHouseAggregate(String mergerName) {
		List<InfoAggregateTreeVO> findEnterAggregate = infoAggregateService.findHouseAggregate(mergerName);
		return new LyhtResultBody<>(findEnterAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--房屋装修", notes = "实物指标汇总树--房屋装修")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/house/decoration")
	public LyhtResultBody<List<InfoAggregateTreeVO>> findHouseDecorationAggregate(String mergerName) {
		List<InfoAggregateTreeVO> findHouseDecorationAggregate = infoAggregateService
				.findHouseDecorationAggregate(mergerName);
		return new LyhtResultBody<>(findHouseDecorationAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--零星树木", notes = "实物指标汇总树--零星树木")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/trees")
	public LyhtResultBody<List<InfoAggregateTreeVO>> findTreesAggregate(String mergerName) {
		List<InfoAggregateTreeVO> findTreesAggregate = infoAggregateService.findTreesAggregate(mergerName);
		return new LyhtResultBody<>(findTreesAggregate);
	}

	@ApiOperation(value = "实物指标汇总树--宅基地", notes = "实物指标汇总树--宅基地")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@GetMapping("/homestead")
	public LyhtResultBody<List<InfoAggregateCardVO>> findHomesteadAggregate(String mergerName) {
		List<InfoAggregateCardVO> findTreesAggregate = infoAggregateService.findHomesteadAggregate(mergerName);
		return new LyhtResultBody<>(findTreesAggregate);
	}
	
	@ApiOperation(value = "实物指标汇总--户主/企事业单位", notes = "实物指标汇总--户主/企事业单位")
	@ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query")
	@PostMapping("/owner")
	public LyhtResultBody<List<InfoOwnerAggregateVO>> findOwnerAggregate(LyhtPageVO lyhtPageVO, String mergerName) {
		LyhtResultBody<List<InfoOwnerAggregateVO>> findOwnerAggregate = infoAggregateService.findOwnerAggregate(mergerName, lyhtPageVO);
		return findOwnerAggregate;
	}

	@ApiOperation(value = "实物指标汇总--卡片", notes = "实物指标汇总--卡片")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query"),
			@ApiImplicitParam(name = "type", value = "卡片类型(population：人口；enter：企事业单位；house：房屋；decoration：房屋装修；trees：零星树木；homestead：宅基地；)", paramType = "query") })
	@GetMapping("/card")
	public LyhtResultBody<List<InfoAggregateTreeVO>> findAggregateCard(String mergerName, String type) {
		List<InfoAggregateTreeVO> findAggregateCard = infoAggregateService.findAggregateCard(mergerName, type);
		return new LyhtResultBody<>(findAggregateCard);
	}
	
	@ApiOperation(value = "土地图表汇总查询", notes = "土地图表汇总查询")
	@PostMapping(value = "/land/by/level")
	public LyhtResultBody<List<InfoLandAggregateCardVO>> findLandAggregateByLevel(
			InfoLandAggregateParamVO infoLandAggregateParamVO) {
		List<InfoLandAggregateCardVO> findLandAggregateByLevel = infoAggregateService.findLandAggregateByLevel(infoLandAggregateParamVO);
		return new LyhtResultBody<>(findLandAggregateByLevel);
	}
	
	@ApiOperation(value = "土地按行政区汇总", notes = "土地按行政区汇总")
	@ApiImplicitParam(name = "mergerName", value = "行政区--全称", paramType = "query")
	@PostMapping(value = "/land")
	public LyhtResultBody<List<InfoLandAggregateTreeVO>> findLandAggregate(String mergerName) {
		List<InfoLandAggregateTreeVO> findLandAggregate = infoAggregateService.findLandAggregate(mergerName);
		return new LyhtResultBody<>(findLandAggregate);
	}

}