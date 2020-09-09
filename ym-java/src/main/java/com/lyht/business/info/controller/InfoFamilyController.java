package com.lyht.business.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.entity.InfoFamilyEntity;
import com.lyht.business.info.service.InfoFamilyService;
import com.lyht.business.info.vo.InfoFamilyVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/info/family", tags = "家庭成员相关api")
@RestController
@RequestMapping("/info/family")
public class InfoFamilyController {

	@Autowired
	private InfoFamilyService infoFamilyService;

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "region", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "征地范围编码", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "身份证（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "isDirtyData", value = "是否关联户主(0：是；1：否)", paramType = "query"),
			@ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query") })
	@PostMapping("/page")
	public LyhtResultBody<List<InfoFamilyVO>> page(LyhtPageVO lyhtPageVO, String region, String scope, String name,
			String idCard, String isDirtyData, String ownerNm) {
		LyhtResultBody<List<InfoFamilyVO>> page = infoFamilyService.page(region, scope, name, idCard, isDirtyData,
				ownerNm, lyhtPageVO);
		return page;
	}

	@ApiOperation(value = "条件查询", notes = "条件查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "region", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "征地范围编码", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "身份证（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "isDirtyData", value = "是否关联户主(0：是；1：否)", paramType = "query"),
			@ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query") })
	@PostMapping("/list")
	public LyhtResultBody<List<InfoFamilyVO>> list(String region, String scope, String name, String idCard,
			String isDirtyData, String ownerNm) {
		LyhtResultBody<List<InfoFamilyVO>> page = infoFamilyService.list(region, scope, name, idCard, isDirtyData,
				ownerNm);
		return page;
	}

	@ApiOperation(value = "新增；修改（id，nm不能为空）", notes = "新增；修改（id，nm不能为空）")
	@PostMapping("/save")
	public LyhtResultBody<InfoFamilyEntity> save(InfoFamilyEntity infoFamilyEntity) {
		InfoFamilyEntity save = infoFamilyService.save(infoFamilyEntity);
		return new LyhtResultBody<>(save);
	}

	@ApiOperation(value = "删除（id不能为空）", notes = "删除（id不能为空）")
	@ApiImplicitParam(name = "id", value = "ID", paramType = "query")
	@GetMapping("/remove")
	public LyhtResultBody<Integer> delete(Integer id) {
		LyhtResultBody<Integer> delete = infoFamilyService.delete(id);
		return delete;
	}

}