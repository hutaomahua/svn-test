package com.lyht.business.tab.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.tab.service.ConfigTableOperateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/config/table/operate", tags = "系统表数据操作相关api")
@RestController
@RequestMapping("/config/table/operate")
public class ConfigTableOperateController {

	@Autowired
	private ConfigTableOperateService configTableOperateService;

	@ApiOperation(value = "自定义新增", notes = "自定义新增")
	@PostMapping("/insert")
	public LyhtResultBody<Map<String, Object>> insert(@RequestParam Map<String, Object> parameterMap, HttpServletRequest request) {
		Map<String, Object> insert = configTableOperateService.insert(parameterMap, request);
		return new LyhtResultBody<>(insert);
	}
	
	@ApiOperation(value = "自定义修改", notes = "自定义修改")
	@PostMapping("/update")
	public LyhtResultBody<Map<String, Object>> update(@RequestParam Map<String, Object> parameterMap, HttpServletRequest request) {
		Map<String, Object> update = configTableOperateService.update(parameterMap, request);
		return new LyhtResultBody<>(update);
	}
	
	@ApiOperation(value = "删除表", notes = "删除表")
	@PostMapping("/delete")
	public LyhtResultBody<Map<String, Object>> deleteTable(@RequestParam Map<String, Object> parameterMap) {
		configTableOperateService.delete(parameterMap);
		return new LyhtResultBody<>();
	}
	
	@ApiOperation(value = "查询", notes = "查询")
	@PostMapping("/list")
	public LyhtResultBody<List<Map<String, Object>>> list(@RequestParam Map<String, Object> parameterMap) {
		List<Map<String, Object>> list = configTableOperateService.list(parameterMap);
		return new LyhtResultBody<>(list);
	}
	
	@ApiOperation(value = "分页查询（可按表名查询）", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<Map<String, Object>>> page(@RequestParam Map<String, Object> parameterMap) {
		return configTableOperateService.page(parameterMap);
	}
	
	@ApiOperation(value = "分页查询（详情页）（按表名与传参条件查询）", notes = "分页查询（详情页）")
	@PostMapping("/detail/page")
	public LyhtResultBody<List<Map<String, Object>>> detailPage(@RequestParam Map<String, Object> parameterMap) {
		return configTableOperateService.detailPage(parameterMap);
	}
}
