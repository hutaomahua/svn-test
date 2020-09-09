package com.lyht.business.doc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.doc.entity.DocImmigrant;
import com.lyht.business.doc.service.DocImmigrantService;
import com.lyht.business.pub.service.PubFilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/doc/immigrant")
@Api(value = "/doc/immigrant", tags = "档案管理 移民档案相关Api")
@RestController
public class DocImmigrantController {

	@Autowired
	private DocImmigrantService service;
	
	@Autowired
	private PubFilesService pubFilesService;
	
	@PostMapping("/page")
	public LyhtResultBody<List<Map>> page(LyhtPageVO lyhtPageVO, String word, String type){
		return service.page(lyhtPageVO, word, type);
	}
	
	@ApiOperation(value = "新增(id与nm为空),修改(需要id与nm)", notes = "新增 修改")
	@PostMapping("/save")
	public LyhtResultBody<DocImmigrant> save(DocImmigrant docImmigrant) {
		return service.save(docImmigrant);
	}

	@ApiOperation(value = "根据id进行删除", notes = "单个删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id, String nm) {
		pubFilesService.deleteBytablePkColumn(request, nm);
		return service.delete(id);
	}

	@ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
	@GetMapping("/batchDel")
	public LyhtResultBody<String> batchDel(HttpServletRequest request, String ids) {
		List<DocImmigrant> infoList = service.getByids(ids);
		for (DocImmigrant docImmigrant : infoList) {
			pubFilesService.deleteBytablePkColumn(request, docImmigrant.getNm());
		}
		return service.batchDel(ids);
	}
	
}
