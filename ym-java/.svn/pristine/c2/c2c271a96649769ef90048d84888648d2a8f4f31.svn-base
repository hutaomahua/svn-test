package com.lyht.business.fund.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.fund.entity.FundCost;
import com.lyht.business.fund.service.FundCostService;
import com.lyht.business.fund.vo.FundCostVO;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/fund/cost")
@RestController
@Api(value = "/fund/info", tags = "资金管理 独立费用/预备费用")
public class FundCostController {

	@Autowired
	private FundCostService service;

	@Autowired
	private PubFilesService pubFilesService;

	/**
	 * 分页查询
	 * 
	 * @param amountType 费用类型 独立费用/预备费用
	 * @param organType  类型 企业/地方政府
	 * @param word       模糊查询 企业/地方政府名称 项目名称
	 * @return
	 */
	@PostMapping("/page")
	@ApiOperation(value = "列表 费用查询", notes = "Excel导入 amountType:独立费用/预备费用  organType类别查询  word 模糊查询")
	public LyhtResultBody<List<FundCostVO>> page(LyhtPageVO lyhtPageVO, String amountType, String organType,
			String word) {
		if(StringUtils.isBlank(word)) {
			word = null;
		}
		return service.page(lyhtPageVO, amountType, organType, word);
	}

	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	@ApiOperation(value = "删除", notes = "删除")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		FundCost cost = service.getById(id);
		pubFilesService.deleteBytablePkColumn(request, cost.getNm());
		return service.delete(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@GetMapping("/batchDel")
	@ApiOperation(value = "批量删除", notes = "批量删除")
	public LyhtResultBody<String> batchDel(HttpServletRequest request, String ids) {
		if (StringUtils.isBlank(ids)) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		for (Integer id : idList) {
			FundCost cost = service.getById(id);
			pubFilesService.deleteBytablePkColumn(request, cost.getNm());
		}
		return service.batchDel(ids);
	}

	/**
	 * 添加 修改
	 * 
	 * @param t_fund_cost
	 * @return
	 */
	@PostMapping("/save")
	@ApiOperation(value = "新增修改", notes = "新增修改")
	public LyhtResultBody<FundCost> save(FundCost fundCost) {
		return service.save(fundCost);
	}

}
