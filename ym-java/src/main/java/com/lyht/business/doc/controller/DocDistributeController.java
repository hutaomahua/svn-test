package com.lyht.business.doc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.doc.entity.DocDistribute;
import com.lyht.business.doc.pojo.DocDistributeVO;
import com.lyht.business.doc.service.DocDistributeService;
import com.lyht.util.EntityUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/doc/distribute", tags = "档案管理 档案分发Api")
@RequestMapping("/doc/distribute")
@RestController
public class DocDistributeController {

	@Autowired
	private DocDistributeService service;
	
	@ApiOperation(value = "查询 分页查询 已共享文档", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<DocDistributeVO>> page(LyhtPageVO lyhtPageVO,String issueStaffNm,String docType){
		return service.page(lyhtPageVO, issueStaffNm,docType);
	}
	
	@ApiOperation(value = "查询 分页查询 被共享文档", notes = "分页查询")
	@PostMapping("/page01")
	public LyhtResultBody<List<DocDistributeVO>> page01(LyhtPageVO lyhtPageVO,String staffNm,String docType){
		return service.page01(lyhtPageVO, staffNm,docType);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "批量新增(id与nm为空),修改(需要id与nm)", notes = "批量新增 ")
	@PostMapping("/batchSave")
	public LyhtResultBody<List<DocDistribute>> batchSave(String  strs){
		List<Map> ms =  (List<Map>)JSON.parse(strs);
		List<DocDistribute> distributes = EntityUtils.toEntityList(ms, DocDistribute.class);
		return service.batchSave(distributes);
	}
	
	@ApiOperation(value = "新增(id与nm为空),修改(需要id与nm)", notes = "新增 修改")
	@PostMapping("/save")
	public LyhtResultBody<DocDistribute> save(DocDistribute docDistribute) {
		return service.save(docDistribute);
	}

	@ApiOperation(value = "根据id进行删除", notes = "单个删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(Integer id) {
		return service.delete(id);
	}

	@ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
	@GetMapping("/batchDel")
	public LyhtResultBody<String> batchDel(String ids) {
		return service.batchDel(ids);
	}
	
}
