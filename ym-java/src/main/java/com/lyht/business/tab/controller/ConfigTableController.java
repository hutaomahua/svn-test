package com.lyht.business.tab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.tab.entity.ConfigTableEntity;
import com.lyht.business.tab.service.ConfigTableService;
import com.lyht.business.tab.vo.DatebaseTableVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/config/table", tags = "系统表注册相关api")
@RestController
@RequestMapping("/config/table")
public class ConfigTableController {

	@Autowired
	private ConfigTableService configTableService;

	@ApiOperation(value = "创建表（含默认字段-ID、内码、项目、指标类型、行政区域、阶段、数据状态、创建时间、修改时间、创建人、修改人、备注）", notes = "创建表")
	@PostMapping("/create")
	public LyhtResultBody<ConfigTableEntity> createTable(ConfigTableEntity configTableEntity) {
		ConfigTableEntity result = configTableService.createTable(configTableEntity);
		return new LyhtResultBody<>(result);
	}
	
	@ApiOperation(value = "修改表信息", notes = "修改表信息")
	@PostMapping("/update")
	public LyhtResultBody<ConfigTableEntity> updateTable(ConfigTableEntity configTableEntity) {
		ConfigTableEntity result = configTableService.updateTable(configTableEntity);
		return new LyhtResultBody<>(result);
	}
	
	@ApiOperation(value = "删除表", notes = "删除表")
	@PostMapping("/delete")
	public LyhtResultBody<ConfigTableEntity> deleteTable(Integer id) {
		configTableService.deleteTable(id);
		return new LyhtResultBody<>();
	}
	
	@ApiOperation(value = "分页查询（可按表名查询）", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<ConfigTableEntity>> page(LyhtPageVO lyhtPageVO, ConfigTableEntity configTableEntity) {
		return configTableService.page(lyhtPageVO, configTableEntity);
	}
	
	@ApiOperation(value = "查询（可按表名查询）", notes = "查询")
	@PostMapping("/list")
	public LyhtResultBody<List<ConfigTableEntity>> list(ConfigTableEntity configTableEntity) {
		List<ConfigTableEntity> list = configTableService.list(configTableEntity);
		return new LyhtResultBody<>(list);
	}
	
	@ApiOperation(value = "详情查询（ID）", notes = "详情查询")
	@PostMapping("/one")
	public LyhtResultBody<ConfigTableEntity> findById(Integer id) {
		ConfigTableEntity configTableEntity = configTableService.findById(id);
		return new LyhtResultBody<>(configTableEntity);
	}
	
	@ApiOperation(value = "数据库表名查询（可按表名查询）", notes = "数据库表名查询")
	@PostMapping("/system/tables")
	public LyhtResultBody<List<DatebaseTableVO>> systemTables(String tableName) {
		List<DatebaseTableVO> list = configTableService.listByTableName(tableName);
		return new LyhtResultBody<>(list);
	}
}