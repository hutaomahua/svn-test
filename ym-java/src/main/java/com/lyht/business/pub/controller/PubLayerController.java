package com.lyht.business.pub.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.entity.PubLayerEntity;
import com.lyht.business.pub.service.PubLayerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pub/layer")
@Api(value = "/pub/layer", tags = "图层数据")
public class PubLayerController {
	@Autowired
	private PubLayerService services;

	@GetMapping("/list")
	@ApiOperation(value = "条件查询", notes = "条件查询")
	public LyhtResultBody<List<PubLayerEntity>> list(String name) {
		LyhtResultBody<List<PubLayerEntity>> list = services.list(name);
		return list;
	}

	@GetMapping("/detail")
	@ApiOperation(value = "根据id查询详情", notes = "根据id查询详情")
	public LyhtResultBody<PubLayerEntity> deail(Integer id) {
		LyhtResultBody<PubLayerEntity> findById = services.findById(id);
		return findById;
	}

	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody<PubLayerEntity> save(PubLayerEntity pubLayerEntity) {
		LyhtResultBody<PubLayerEntity> save = services.save(pubLayerEntity);
		return save;
	}

	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
	@GetMapping("/delete/batch")
	public LyhtResultBody<String> delete(String ids) {
		LyhtResultBody<String> deleteByIds = services.deleteByIds(ids);
		return deleteByIds;
	}

	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(Integer id) {
		LyhtResultBody<Integer> deleteById = services.deleteById(id);
		return deleteById;
	}

	@ApiOperation(value = "清空整张表", notes = "清空整张表")
	@GetMapping("/clear")
	public LyhtResultBody<Boolean> clear() {
		LyhtResultBody<Boolean> clear = services.clear();
		return clear;
	}

}
