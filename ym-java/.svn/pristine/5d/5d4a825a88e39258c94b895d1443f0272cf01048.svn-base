package com.lyht.business.abm.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.wechat.service.AbmWechatFamilyService;
import com.lyht.business.abm.wechat.vo.AbmWechatFamilyVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/wechat/family", tags = "（微信小程序）人口信息相关api")
@RestController
@RequestMapping("/abm/wechat/family")
public class AbmWechatFamilyController {

	@Autowired
	private AbmWechatFamilyService abmWechatFamilyService;

	@ApiOperation(value = "人口信息查询", notes = "人口信息查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "户主NM", paramType = "query") })
	@PostMapping("/list")
	public LyhtResultBody<List<AbmWechatFamilyVO>> list(String ownerNm) {
		List<AbmWechatFamilyVO> list = abmWechatFamilyService.list(ownerNm);
		return new LyhtResultBody<>(list);
	}

}