package com.lyht.business.info.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.service.InfoMoveAggregateService;
import com.lyht.business.info.vo.InfoMoveAggregateCardVO;
import com.lyht.business.info.vo.InfoMoveAggregateParamVO;
import com.lyht.business.info.vo.InfoMoveAggregateTreeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/info/move/aggregate", tags = "基础数据--搬迁安置汇总api")
@RestController
@RequestMapping("/info/move/aggregate")
public class InfoMoveAggregateController {

	@Autowired
	private InfoMoveAggregateService infoMoveAggregateService;

	@ApiOperation(value = "搬迁安置汇总树--行政区域", notes = "搬迁安置汇总树--行政区域")
	@GetMapping("/region/tree")
	public LyhtResultBody<List<InfoMoveAggregateTreeVO>> findRegionTree() {
		List<InfoMoveAggregateTreeVO> findRegionTree = infoMoveAggregateService.findRegionTree();
		return new LyhtResultBody<>(findRegionTree);
	}

	/*@ApiOperation(value = "导入数据", notes = "")
	@GetMapping("/insert/region/tree")
	public LyhtResultBody findRegionTree2() throws IOException {
		infoMoveAggregateService.findRegionTree2();
		return new LyhtResultBody();
	}*/

	@ApiOperation(value = "搬迁安置--汇总卡片", notes = "搬迁安置--汇总卡片")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "mergerName", value = "行政区域全称", paramType = "query") })
	@GetMapping("/card")
	public LyhtResultBody<List<InfoMoveAggregateCardVO>> findMoveAggregateCard(
			InfoMoveAggregateParamVO infoMoveAggregateParamVO) {
		List<InfoMoveAggregateCardVO> findMoveAggregateCard = infoMoveAggregateService
				.findMoveAggregateCard(infoMoveAggregateParamVO);
		return new LyhtResultBody<>(findMoveAggregateCard);
	}

}