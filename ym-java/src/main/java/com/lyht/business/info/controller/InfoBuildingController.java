package com.lyht.business.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.entity.InfoBuildingEntity;
import com.lyht.business.info.service.InfoBuildingService;
import com.lyht.business.info.vo.InfoBuildingVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/info/building", tags = "附属建筑物相关api")
@RestController
@RequestMapping("/info/building")
public class InfoBuildingController {

	@Autowired
	private InfoBuildingService infoBuildingService;

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "region", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "ownerName", value = "户主姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "征地范围编码", paramType = "query"),
			@ApiImplicitParam(name = "projectFcode", value = "项目fcode", paramType = "query"),
			@ApiImplicitParam(name = "isDirtyData", value = "是否关联户主(0：是；1：否)", paramType = "query"),
			@ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query") })
	@PostMapping("/page")
	public LyhtResultBody<List<InfoBuildingVO>> save(LyhtPageVO lyhtPageVO, String region, String ownerName,
			String scope, String projectFcode, String isDirtyData, String ownerNm) {
		LyhtResultBody<List<InfoBuildingVO>> page = infoBuildingService.page(region, ownerName, scope, projectFcode,
				isDirtyData, ownerNm, lyhtPageVO);
		return page;
	}

	@ApiOperation(value = "新增；修改（id，nm不能为空）", notes = "新增；修改（id，nm不能为空）")
	@PostMapping("/save")
	public LyhtResultBody<InfoBuildingEntity> save(InfoBuildingEntity infoBuildingEntity) {
		InfoBuildingEntity save = infoBuildingService.save(infoBuildingEntity);
		return new LyhtResultBody<>(save);
	}

	@ApiOperation(value = "删除（id不能为空）", notes = "删除（id不能为空）")
	@ApiImplicitParam(name = "id", value = "ID", paramType = "query")
	@GetMapping("/remove")
	public LyhtResultBody<Integer> save(Integer id) {
		LyhtResultBody<Integer> delete = infoBuildingService.delete(id);
		return delete;
	}

}