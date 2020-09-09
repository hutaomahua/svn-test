package com.lyht.business.abm.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.wechat.service.AbmWechatDictionaryService;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionWithScopeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/wechat/dictionary", tags = "（微信小程序）字典相关api")
@RestController
@RequestMapping("/abm/wechat/dictionary")
public class AbmWechatDictionaryController {

	@Autowired
	private AbmWechatDictionaryService abmWechatDictionaryService;

	@ApiOperation(value = "查询“行政区以及征地范围”字典", notes = "查询“行政区以及征地范围”字典")
	@GetMapping("/region/with/scope")
	public LyhtResultBody<AbmWechatRegionWithScopeVO> getRegionAndScope() {
		AbmWechatRegionWithScopeVO regionAndScope = abmWechatDictionaryService.getRegionAndScope();
		return new LyhtResultBody<>(regionAndScope);
	}

}
