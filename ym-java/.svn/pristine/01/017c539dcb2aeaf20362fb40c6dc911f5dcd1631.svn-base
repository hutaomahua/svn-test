package com.lyht.business.abm.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.review.entity.PersonalWealthData;
import com.lyht.business.abm.review.service.PersonalWealthDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/abm/personaWealthData")
@Api(value = "/abm/personaWealthData", tags = "实物指标复核申请（个人财产）存储数据类型")
public class PersonalWealthDataController {

	@Autowired
	private PersonalWealthDataService service;
	
	
	@ApiOperation(value = "新增 修改  修改是传入id",notes = "添加 修改")
	@PostMapping("/save")
	public LyhtResultBody<PersonalWealthData> save(PersonalWealthData personalWealthData){
		return service.save(personalWealthData);
	}
	
}
