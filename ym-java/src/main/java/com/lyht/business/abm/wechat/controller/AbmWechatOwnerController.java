package com.lyht.business.abm.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.wechat.service.AbmWechatOwnerService;
import com.lyht.business.abm.wechat.vo.AbmWechatOwnerDetailVO;
import com.lyht.business.abm.wechat.vo.AbmWechatOwnerVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/wechat/owner", tags = "（微信小程序）权属人相关api")
@RestController
@RequestMapping("/abm/wechat/owner")
public class AbmWechatOwnerController {

	@Autowired
	private AbmWechatOwnerService abmWechatOwnerService;

	@ApiOperation(value = "权属人列表查询", notes = "权属人列表查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "mergerName", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "征地范围编码", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "户主姓名与身份证号（模糊查询）", paramType = "query") })
	@PostMapping("/list")
	public LyhtResultBody<List<AbmWechatOwnerVO>> list(String mergerName, String scope, String value) {
		List<AbmWechatOwnerVO> list = abmWechatOwnerService.list(mergerName, scope, value);
		return new LyhtResultBody<>(list);
	}

	@ApiOperation(value = "权属人列表查询（分页）", notes = "权属人列表查询（分页）")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "mergerName", value = "行政区域（模糊查询）", paramType = "query"),
			@ApiImplicitParam(name = "scope", value = "征地范围编码", paramType = "query"),
			@ApiImplicitParam(name = "value", value = "户主姓名与身份证号（模糊查询）", paramType = "query") })
	@PostMapping("/page")
	public LyhtResultBody<List<AbmWechatOwnerVO>> page(LyhtPageVO lyhtPageVO, String mergerName, String scope,
			String value) {
		LyhtResultBody<List<AbmWechatOwnerVO>> result = abmWechatOwnerService.page(lyhtPageVO, mergerName, scope, value);
		return result;
	}

	@ApiOperation(value = "权属人详情查询", notes = "权属人详情查询")
	@ApiImplicitParam(name = "ownerNm", value = "户主nm", paramType = "query")
	@PostMapping("/get")
	public LyhtResultBody<AbmWechatOwnerDetailVO> getOwnerDetail(String ownerNm) {
		AbmWechatOwnerDetailVO ownerDetail = abmWechatOwnerService.getOwnerDetail(ownerNm);
		return new LyhtResultBody<>(ownerDetail);
	}

}