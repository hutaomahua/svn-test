package com.lyht.business.abm.wechat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.wechat.service.AbmWechatInfoService;
import com.lyht.business.abm.wechat.vo.AbmWechatInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/wechat/info", tags = "（微信小程序）指标信息相关api")
@RestController
@RequestMapping("/abm/wechat/info")
public class AbmWechatInfoController {

	@Autowired
	private AbmWechatInfoService abmWechatInfoService;

	@ApiOperation(value = "指标信息查询", notes = "指标信息查询")
	@ApiImplicitParams(value = { @ApiImplicitParam(name = "ownerNm", value = "户主nm", paramType = "query") })
	@PostMapping("/list")
	public LyhtResultBody<List<AbmWechatInfoVO>> list(String ownerNm) {
		List<AbmWechatInfoVO> list = abmWechatInfoService.list(ownerNm);
		return new LyhtResultBody<>(list);
	}

}