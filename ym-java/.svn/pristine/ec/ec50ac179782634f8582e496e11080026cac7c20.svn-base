package com.lyht.business.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.service.InfoOwnerService;
import com.lyht.business.info.vo.InfoOwnerDetailVO;
import com.lyht.business.info.vo.InfoOwnerPlaceVO;
import com.lyht.business.info.vo.InfoOwnerSelectVO;
import com.lyht.business.info.vo.InfoOwnerVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/info/owner", tags = "权属人相关api")
@RestController
@RequestMapping("/info/owner")
public class InfoOwnerController {

	@Autowired
	private InfoOwnerService infoOwnerService;

//	@PostMapping("/updateAge")
////	public void updateAge() {
//		infoOwnerService.updateAge();
//	}

	
	@ApiOperation(value = "户主下拉框查询", notes = "户主下拉框查询")
	@ApiImplicitParam(name = "param", value = "身份证与姓名（模糊查询）", paramType = "query")
	@PostMapping("/list")
	public LyhtResultBody<List<InfoOwnerSelectVO>> list(String param) {
		List<InfoOwnerSelectVO> list = infoOwnerService.findByParam(param);
		return new LyhtResultBody<List<InfoOwnerSelectVO>>(list);
	}

	@ApiOperation(value = "根据权属人NM查询详情", notes = "根据权属人NM查询详情")
	@PostMapping("/get")
	public LyhtResultBody<InfoOwnerDetailVO> get(String ownerNm) {
		InfoOwnerDetailVO findOneByNm = infoOwnerService.findOneByNm(ownerNm);
		return new LyhtResultBody<>(findOneByNm);
	}

	@ApiOperation(value = "根据名称模糊查询权属人（含经纬度）", notes = "根据名称模糊查询权属人（含经纬度）")
	@PostMapping("/by/name")
	public LyhtResultBody<List<InfoOwnerDetailVO>> listByName(String name) {
		List<InfoOwnerDetailVO> findAllByName = infoOwnerService.findAllByNameAndRegion(name, null);
		return new LyhtResultBody<>(findAllByName);
	}

	@ApiOperation(value = "根据行政区域查询权属人（模糊查询）", notes = "根据行政区域查询权属人（模糊查询）")
	@ApiImplicitParam(name = "region", value = "行政区域与（模糊查询）", paramType = "query")
	@GetMapping("/by/region")
	public LyhtResultBody<List<InfoOwnerDetailVO>> listByRegion(String region) {
		List<InfoOwnerDetailVO> findAllByRegion = infoOwnerService.findAllByNameAndRegion(null, region);
		return new LyhtResultBody<>(findAllByRegion);
	}

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "region", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "征地范围编码", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "户主姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "身份证（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "isDirtyData", value = "身份证是否为空(0：是；1：否)", paramType = "query"),
			@ApiImplicitParam(name = "placeType", value = "安置类型编码", paramType = "query") })
	@PostMapping("/page")
	public LyhtResultBody<List<InfoOwnerVO>> page(LyhtPageVO lyhtPageVO, String region, String scope, String name,
			String idCard, String isDirtyData, String nm, String placeType) {
		LyhtResultBody<List<InfoOwnerVO>> page = infoOwnerService.page(region, scope, name, idCard, isDirtyData, nm,
				placeType, lyhtPageVO);
		return page;
	}

	@ApiOperation(value = "新增；修改（id，nm不能为空）", notes = "新增；修改（id，nm不能为空）")
	@PostMapping("/save")
	public LyhtResultBody<InfoOwnerEntity> save(InfoOwnerEntity infoOwnerEntity) {
		InfoOwnerEntity save = infoOwnerService.save(infoOwnerEntity);
		return new LyhtResultBody<>(save);
	}

	@ApiOperation(value = "删除（id不能为空）", notes = "删除（id不能为空）")
	@ApiImplicitParam(name = "id", value = "ID", paramType = "query")
	@GetMapping("/remove")
	public LyhtResultBody<Integer> save(Integer id) {
		LyhtResultBody<Integer> delete = infoOwnerService.delete(id);
		return delete;
	}

	@ApiOperation(value = "安置信息查询", notes = "安置信息查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "name", value = "户主姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "身份证号（模糊查询）", paramType = "query") })
	@GetMapping("/place")
	public LyhtResultBody<List<InfoOwnerPlaceVO>> findOwnerPlace(String name, String idCard) {
		List<InfoOwnerPlaceVO> findOwnerPlace = infoOwnerService.findOwnerPlace(name, idCard);
		return new LyhtResultBody<>(findOwnerPlace);
	}

}