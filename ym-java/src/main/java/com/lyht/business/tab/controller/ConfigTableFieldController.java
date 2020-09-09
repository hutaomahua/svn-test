package com.lyht.business.tab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.tab.entity.ConfigTableFieldEntity;
import com.lyht.business.tab.service.ConfigTableFieldService;
import com.lyht.business.tab.vo.DatebaseFiledListVO;
import com.lyht.business.tab.vo.DatebaseTableColumnVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/config/field", tags = "系统表字段注册相关api")
@RestController
@RequestMapping("/config/field")
public class ConfigTableFieldController {

	@Autowired
	private ConfigTableFieldService configTableFieldService;

	@ApiOperation(value = "新增表字段", notes = "新增表字段")
	@PostMapping("/add")
	public LyhtResultBody<ConfigTableFieldEntity> createTable(@RequestBody ConfigTableFieldEntity configTableFieldEntity) {
		ConfigTableFieldEntity result = configTableFieldService.addTableField(configTableFieldEntity);
		return new LyhtResultBody<>(result);
	}
	
	@ApiOperation(value = "修改表字段", notes = "修改表字段")
	@PostMapping("/update")
	public LyhtResultBody<ConfigTableFieldEntity> updateTable(@RequestBody ConfigTableFieldEntity configTableFieldEntity) {
		ConfigTableFieldEntity result = configTableFieldService.updateTableField(configTableFieldEntity);
		return new LyhtResultBody<>(result);
	}
	
	@ApiOperation(value = "删除表字段", notes = "删除表字段")
	@PostMapping("/delete")
	public LyhtResultBody<Integer> deleteTable(Integer id) {
		Integer deleteTableField = configTableFieldService.deleteTableField(id);
		return new LyhtResultBody<>(deleteTableField);
	}
	
	@ApiOperation(value = "分页查询（按配置表id查询对应表字段）", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<ConfigTableFieldEntity>> page(Integer configTableId, LyhtPageVO lyhtPageVO) {
		return configTableFieldService.pageByTableId(configTableId, lyhtPageVO);
	}
	
	@ApiOperation(value = "查询（按配置表id查询对应表字段）", notes = "查询")
	@PostMapping("/list")
	public LyhtResultBody<List<ConfigTableFieldEntity>> list(Integer configTableId) {
		List<ConfigTableFieldEntity> list = configTableFieldService.listByTableId(configTableId);
		return new LyhtResultBody<>(list);
	}
	
	@ApiOperation(value = "按字段类型查询列表", notes = "按字段类型查询列表")
	@PostMapping("/type")
	public LyhtResultBody<DatebaseFiledListVO> getFiledTypeList(@RequestParam(name="configTableId", required = true) Integer configTableId, @RequestParam(name="stage", required = false) String stage) {
		DatebaseFiledListVO filedTypeList = configTableFieldService.getFiledTypeList(configTableId, stage);
		return new LyhtResultBody<>(filedTypeList);
	}
	
	@ApiOperation(value = "数据库字段查询（按表名查询，可按字段名筛选）", notes = "数据库字段查询")
	@PostMapping("/system/columns")
	public LyhtResultBody<List<DatebaseTableColumnVO>> listByTableName(@RequestParam(required = true) String tableName, String fieldName) {
		List<DatebaseTableColumnVO> listByTableName = configTableFieldService.listByTableName(tableName, fieldName);
		return new LyhtResultBody<>(listByTableName);
	}
}
