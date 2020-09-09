package com.lyht.business.engineering.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.engineering.entity.EngineeringSinkBottom;
import com.lyht.business.engineering.service.EngineeringEvaluateService;
import com.lyht.business.engineering.service.EngineeringSinkBottomService;
import com.lyht.business.engineering.vo.EngineeringSinkBottomVO;
import com.lyht.business.pub.service.PubFilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/engineering/sinkBottom", tags = "库底清理实施信息表")
@RestController
@RequestMapping("/engineering/sinkBottom")
public class EngineeringSinkBottomController {

	@Autowired
	private EngineeringSinkBottomService service;
	
	@Autowired
	private EngineeringEvaluateService evaluateService;
	
	@Autowired
	private PubFilesService pubFilesService;
	
	@ApiOperation(value = "查询 模糊查询 分页查询", notes = "查询 条件查询")
	@PostMapping("/page")
	public LyhtResultBody<List<EngineeringSinkBottomVO>> page(EngineeringSinkBottomVO engineeringSinkBottomVO,LyhtPageVO lyhtPageVO){
		return service.page(engineeringSinkBottomVO,lyhtPageVO);
	}

	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	@ApiOperation(value = "删除", notes = "删除")
	public LyhtResultBody<Integer> delete(HttpServletRequest request,Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		EngineeringSinkBottom engineeringSinkBottom = service.findOneById(id);
		evaluateService.deleteByEngineeringNm(engineeringSinkBottom.getNm());//删除该条信息下所有实施进度信息
		pubFilesService.deleteBytablePkColumn(request, engineeringSinkBottom.getNm());//删除该条信息下所有附件
		return service.delete(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/batchDel")
	@ApiOperation(value = "批量删除", notes = "批量删除")
	public LyhtResultBody<String> batchDel(String ids) {
		return service.batchDel(ids);
	}
	
	/**
	 * 添加 修改
	 * @param FundCost
	 * @return
	 */
	@ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
	@PostMapping("/save")
	public LyhtResultBody<EngineeringSinkBottom> save(EngineeringSinkBottom engineeringSinkBottom) {
		return service.save(engineeringSinkBottom);
	}
	
	
}
