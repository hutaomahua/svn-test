package com.lyht.business.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.entity.InfoHouseholdRegisterEntity;
import com.lyht.business.info.service.InfoHouseholdRegisterService;
import com.lyht.business.info.vo.InfoHouseholdRegisterVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/info/household/register", tags = "户籍相关api")
@RestController
@RequestMapping("/info/household/register")
public class InfoHouseholdRegisterController {

	@Autowired
	private InfoHouseholdRegisterService infoHouseholdRegisterService;

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "region", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "身份证号（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "accountCharacter", value = "户口性质", paramType = "query"),
			@ApiImplicitParam(name = "accountType", value = "户口类型", paramType = "query"),
			@ApiImplicitParam(name = "livingCondition", value = "居住情况（1：居住；2：未居住）", paramType = "query"),
			@ApiImplicitParam(name = "masterRelationship", value = "与户主关系", paramType = "query"),
			@ApiImplicitParam(name = "country", value = "乡（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "village", value = "村（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "groups", value = "组（模糊查询）", paramType = "query") })
	@PostMapping("/page")
	public LyhtResultBody<List<InfoHouseholdRegisterVO>> page(String region, String name, String idCard,
			String accountCharacter, String accountType, String livingCondition, String masterRelationship,
			String country, String village, String groups, LyhtPageVO lyhtPageVO) {
		LyhtResultBody<List<InfoHouseholdRegisterVO>> page = infoHouseholdRegisterService.page(region, name, idCard,
				accountCharacter, accountType, livingCondition, masterRelationship, country, village, groups,
				lyhtPageVO);
		return page;
	}

	@ApiOperation(value = "新增；修改（id不能为空）", notes = "新增；修改（id不能为空）")
	@PostMapping("/save")
	public LyhtResultBody<InfoHouseholdRegisterEntity> save(InfoHouseholdRegisterEntity infoHouseholdRegisterEntity) {
		InfoHouseholdRegisterEntity save = infoHouseholdRegisterService.save(infoHouseholdRegisterEntity);
		return new LyhtResultBody<>(save);
	}

	@ApiOperation(value = "查询户主下拉数据", notes = "查询户主下拉数据")
	@ApiImplicitParam(name = "name", value = "姓名", paramType = "query")
	@PostMapping("/owner/list")
	public LyhtResultBody<List<InfoHouseholdRegisterEntity>> ownerList(String name) {
		List<InfoHouseholdRegisterEntity> ownerList = infoHouseholdRegisterService.ownerList(name);
		return new LyhtResultBody<>(ownerList);
	}

	@ApiOperation(value = "删除（id不能为空）", notes = "删除（id不能为空）")
	@ApiImplicitParam(name = "id", value = "ID", paramType = "query")
	@GetMapping("/remove")
	public LyhtResultBody<String> remove(String id) {
		LyhtResultBody<String> delete = infoHouseholdRegisterService.delete(id);
		return delete;
	}

	@ApiOperation(value = "excel导入", notes = "excel导入")
	@ApiImplicitParam(name = "file", value = "excle", paramType = "form", dataType = "file")
	@PostMapping("/excel/import")
	public LyhtResultBody<String> importExcel(@RequestParam("file") MultipartFile multipartFile) {
		String importExcel = infoHouseholdRegisterService.importExcel(multipartFile);
		return new LyhtResultBody<>(importExcel);
	}

	@ApiOperation(value = "拉取户籍信息，同步到实施部分-户主与家庭成员", notes = "拉取户籍信息，同步到实施部分-户主与家庭成员")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerId", value = "户主ID", paramType = "query"),
			@ApiImplicitParam(name = "region", value = "行政区域cityCode", paramType = "query") })
	@GetMapping("/sync/owner")
	public LyhtResultBody<Boolean> syncOwner(String ownerId, String region) {
		infoHouseholdRegisterService.syncOwner(ownerId, region);
		return new LyhtResultBody<>(true);
	}

}