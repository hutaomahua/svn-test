package com.lyht.business.fund.controller;

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
import com.lyht.business.fund.entity.FundAppropriation;
import com.lyht.business.fund.service.FundAppropriationService;
import com.lyht.business.fund.vo.FundAppropriationDetail;
import com.lyht.business.pub.service.PubFilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/fund/appropriation", tags = "资金拨付台账")
@RestController
@RequestMapping("/fund/appropriation")
public class FundAppropriationController {
	
	@Autowired
	private FundAppropriationService service;
	
	@Autowired
	private PubFilesService pubFilesService;
	
	/**
	 * 查询列表
	 */
	@ApiOperation(value = "查询 模糊查询 分页查询", notes = "查询 条件查询")
	@PostMapping("/page")
	public LyhtResultBody<List<FundAppropriationDetail>> getList( LyhtPageVO lyhtPageVO,String payProjectName,String word){
		return service.page(lyhtPageVO,payProjectName, word);
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
		FundAppropriation app = service.findOneById(id);
		pubFilesService.deleteBytablePkColumn(request, app.getNm());
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
	public LyhtResultBody<FundAppropriation> save(FundAppropriation fundAppropriation) {
		return service.save(fundAppropriation);
	}
	
	
}
