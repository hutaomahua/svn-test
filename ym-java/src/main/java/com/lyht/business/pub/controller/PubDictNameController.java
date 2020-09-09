package com.lyht.business.pub.controller;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.entity.PubDictName;
import com.lyht.business.pub.service.PubDictNameService;
import com.lyht.business.pub.vo.PubDictNameDetail;
import com.lyht.business.pub.vo.PubDictValueVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 创建人： lj
 * 
 * 说明: 字典分类
 */
@RestController
@RequestMapping("/pubDictName")
@Api(value = "/pubDictName", tags = "字典分类")
public class PubDictNameController {
	@Autowired
	private PubDictNameService services;

	/**
	 * 分页
	 * 
	 * @param pubdictnamedetail
	 * @param lyhtPageVO
	 * @return
	 */
	@PostMapping("/page")
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	public LyhtResultBody<List<PubDictNameDetail>> page(LyhtPageVO lyhtPageVO, PubDictNameDetail pubdictnamedetail) {
		return services.page(lyhtPageVO, pubdictnamedetail);
	}

	/**
	 * 详情
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "id查询", notes = "详情查询")
	@GetMapping("/deail")
	public LyhtResultBody<PubDictName> deail(Integer id) {
		return services.detail(id);
	}

	/**
	 * 保存
	 * 
	 * @param pubdictname
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody<PubDictName> save(PubDictName pubdictname) {
		return services.save(pubdictname);
	}

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "按id删除", notes = "批量删除和删除")
	@GetMapping("/delete")
	public LyhtResultBody<String> delete(String ids) {
		return services.deleteByIds(ids);
	}

	/**
	 * 通过分类查询字典
	 * 
	 */
	@ApiOperation(value = "查询分类下的字典信息", notes = "查询")
	@PostMapping("/findPubDiveValue")
	public LyhtResultBody<List<PubDictValueVO>> findPubDiveValue(PubDictNameDetail pubDictNameDetail) {
		return services.getPubDictValiue(pubDictNameDetail);
	}

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询所有分类", notes = "查询")
	@GetMapping("/list")
	public LyhtResultBody<List<PubDictName>> findAll() {
		return services.findAll();
	}
}
