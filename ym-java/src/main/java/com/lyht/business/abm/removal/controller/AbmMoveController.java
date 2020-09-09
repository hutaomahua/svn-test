package com.lyht.business.abm.removal.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.service.AbmMoveService;
import com.lyht.business.abm.removal.vo.AbmMoveDefinitionVO;
import com.lyht.business.abm.removal.vo.AbmMoveFamilyVO;
import com.lyht.business.abm.removal.vo.AbmMoveOwnerVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/move", tags = "搬迁安置相关api")
@RestController
@RequestMapping("/abm/move")
public class AbmMoveController {

	@Autowired
	private AbmMoveService abmMoveService;

	@ApiOperation(value = "搬迁安置户主信息分页查询", notes = "搬迁安置户主信息分页查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerName", value = "戶主姓名", paramType = "query"),
			@ApiImplicitParam(name = "idCard", value = "户主身份证号", paramType = "query"),
			@ApiImplicitParam(name = "mergerName", value = "行政区全称", paramType = "query") })
	@PostMapping("/owner/page")
	public LyhtResultBody<List<AbmMoveOwnerVO>> pageMoveOwner(LyhtPageVO lyhtPageVO, String ownerName, String idCard,
			String mergerName) {
		LyhtResultBody<List<AbmMoveOwnerVO>> pageMoveFamily = abmMoveService.pageMoveOwner(lyhtPageVO, ownerName,
				idCard, mergerName);
		return pageMoveFamily;
	}

	@ApiOperation(value = "搬迁安置家庭成员信息分页查询", notes = "搬迁安置家庭成员信息分页查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "戶主NM", paramType = "query") })
	@PostMapping("/family/page")
	public LyhtResultBody<List<AbmMoveFamilyVO>> pageMoveFamily(LyhtPageVO lyhtPageVO, String ownerNm) {
		LyhtResultBody<List<AbmMoveFamilyVO>> pageMoveFamily = abmMoveService.pageMoveFamily(lyhtPageVO, ownerNm);
		return pageMoveFamily;
	}

	@ApiOperation(value = "搬迁安置家庭成员信息查询", notes = "搬迁安置家庭成员信息查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "戶主NM", paramType = "query") })
	@PostMapping("/family/list")
	public LyhtResultBody<List<AbmMoveFamilyVO>> listMoveFamily(String ownerNm) {
		List<AbmMoveFamilyVO> pageMoveFamily = abmMoveService.listMoveFamily(ownerNm);
		return new LyhtResultBody<>(pageMoveFamily);
	}

	@ApiOperation(value = "搬迁安置去向及人口界定", notes = "搬迁安置去向及人口界定")
	@PostMapping("/definition")
	public LyhtResultBody<Boolean> definition(AbmMoveDefinitionVO abmMoveDefinitionVO) {
		boolean flag = abmMoveService.definitionBefore(abmMoveDefinitionVO);
		return new LyhtResultBody<>(flag);
	}

	@ApiOperation(value = "导出搬迁安置去向及人口界定表", notes = "导出搬迁安置去向及人口界定表")
	@GetMapping("/export")
	public void exportExcel(HttpServletResponse response, String ownerNm, String fileName) {
		abmMoveService.exportExcel(response, ownerNm, fileName);
	}
	
}