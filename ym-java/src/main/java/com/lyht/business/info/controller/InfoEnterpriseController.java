package com.lyht.business.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.info.service.InfoEnterpriseService;
import com.lyht.business.info.vo.InfoEnterpriseDetailVO;

import io.swagger.annotations.Api;

@Api(value = "/info/enterprise", tags = "企事业单位相关api")
@RestController
@RequestMapping("/info/enterprise")
public class InfoEnterpriseController {

	@Autowired
	private InfoEnterpriseService infoEnterpriseService;
	
	@PostMapping("/get")
	public LyhtResultBody<InfoEnterpriseDetailVO> list(String enterpriseNm) {
		InfoEnterpriseDetailVO findOneByNm = infoEnterpriseService.findOneByNm(enterpriseNm);
		return new LyhtResultBody<>(findOneByNm); 
	}

}