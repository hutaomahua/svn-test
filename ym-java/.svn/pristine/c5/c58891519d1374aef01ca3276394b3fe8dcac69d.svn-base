package com.lyht.business.abm.appropriation.controller;

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
import com.lyht.business.abm.appropriation.entity.Appropriation;
import com.lyht.business.abm.appropriation.service.AppropriationService;
import com.lyht.business.abm.appropriation.vo.AppropriationVO;
import com.lyht.business.abm.protocol.entity.BankCradInfo;
import com.lyht.business.abm.protocol.service.BankCradInfoService;
import com.lyht.business.pub.service.PubFilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/abm/appropriation", tags = "实施管理资金拨付")
@RestController
@RequestMapping("/abm/appropriation")
public class AppropriationController {
	
	@Autowired
	private AppropriationService service;
	
	@Autowired 
	private BankCradInfoService cardService;
	
	@Autowired
	private PubFilesService pubFilesService;
	
	@ApiOperation(value = "查询 模糊查询 分页查询", notes = "查询 条件查询")
	@PostMapping("/page")
	public LyhtResultBody<List<AppropriationVO>> page(AppropriationVO appropriationVO,LyhtPageVO lyhtPageVO){
		return service.page(appropriationVO,lyhtPageVO);
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
		Appropriation Appropriation = service.findOneById(id);
		pubFilesService.deleteBytablePkColumn(request, Appropriation.getNm());//删除该条信息下所有附件
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
	public LyhtResultBody<Appropriation> save(Appropriation appropriation,BankCradInfo bankCradInfo) {
		return service.save(appropriation,bankCradInfo);
	}

}
