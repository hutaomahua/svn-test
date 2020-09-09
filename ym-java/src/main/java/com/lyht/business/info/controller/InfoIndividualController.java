package com.lyht.business.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.entity.InfoIndividualEntity;
import com.lyht.business.info.service.InfoIndividualService;
import com.lyht.business.info.vo.InfoIndividualVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/info/individual", tags = "个体工商户相关api")
@RestController
@RequestMapping("/info/individual")
public class InfoIndividualController {

	@Autowired
	private InfoIndividualService infoIndividualService;

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "region", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "operatorName", value = "姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "身份证（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "isDirtyData", value = "是否关联户主(0：是；1：否)", paramType = "query"),
			@ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query") })
	@PostMapping("/page")
	public LyhtResultBody<List<InfoIndividualVO>> save(LyhtPageVO lyhtPageVO, String region, String operatorName,
			String idCard, String isDirtyData, String ownerNm) {
		LyhtResultBody<List<InfoIndividualVO>> page = infoIndividualService.page(region, operatorName, idCard,
				isDirtyData, ownerNm, lyhtPageVO);
		return page;
	}

	@ApiOperation(value = "新增；修改（id，nm不能为空）", notes = "新增；修改（id，nm不能为空）")
	@PostMapping("/save")
	public LyhtResultBody<InfoIndividualEntity> save(InfoIndividualEntity infoIndividualEntity) {
		InfoIndividualEntity save = infoIndividualService.save(infoIndividualEntity);
		return new LyhtResultBody<>(save);
	}

	@ApiOperation(value = "删除（id不能为空）", notes = "删除（id不能为空）")
	@ApiImplicitParam(name = "id", value = "ID", paramType = "query")
	@GetMapping("/remove")
	public LyhtResultBody<Integer> save(Integer id) {
		LyhtResultBody<Integer> delete = infoIndividualService.delete(id);
		return delete;
	}

}