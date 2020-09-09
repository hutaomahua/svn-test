package com.lyht.business.cost.controller;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.cost.service.CostService;
import com.lyht.business.cost.vo.CostAggregateCardVO;
import com.lyht.business.cost.vo.CostAggregateChatVO;
import com.lyht.business.cost.vo.CostAggregateTreeVO;
import com.lyht.business.cost.vo.CostDetailVO;
import com.lyht.business.cost.vo.CostVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cost")
@Api(value = "/cost", tags = "总补偿费用")
public class CostController {

	@Autowired
	private CostService costService;

	@GetMapping("/by/owner")
	@ApiOperation(value = "按户主内码计算补偿费用(整户)", notes = "按户主内码计算补偿费用(整户)")
	public LyhtResultBody<CostVO> costByOwnerNm(String ownerNm) {
		CostVO costByOwnerNm = costService.costByOwnerNm(ownerNm);
		return new LyhtResultBody<>(costByOwnerNm);
	}

	@GetMapping("/detail")
	@ApiOperation(value = "按户主内码查询明细", notes = "按户主内码查询明细")
	public LyhtResultBody<CostVO> list(String ownerNm) {
		CostVO findCostDetailByOwnerNm = costService.findCostDetailByOwnerNm(ownerNm);
		return new LyhtResultBody<>(findCostDetailByOwnerNm);
	}

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<CostDetailVO>> save(LyhtPageVO lyhtPageVO,
			@ApiParam(value = "户主姓名（模糊查询）") String ownerName, @ApiParam(value = "征地范围NM") String scopeNm,
			@ApiParam(value = "行政区域（模糊查询）") String region, @ApiParam(value = "状态(0：正常，1：部分已计算，2：计算完成)") Integer status,
			@ApiParam(value = "身份证（模糊查询）") String idCard) {
		LyhtResultBody<List<CostDetailVO>> page = costService.page(ownerName, scopeNm, region, status, idCard,
				lyhtPageVO);
		return page;
	}

//	@ApiOperation(value = "新增（id为空），修改（id必填）", notes = "新增，修改")
//	@PostMapping("/save")
//	public LyhtResultBody<CostEntity> save(CostEntity costEntity) {
//		CostEntity save = costService.save(costEntity);
//		return new LyhtResultBody<>(save);
//	}
//
//	@ApiOperation(value = "批量删除(按id删除,用逗号“,”拼接)", notes = "批量删除(按id删除,用逗号“,”拼接)")
//	@GetMapping("/delete/batch")
//	public LyhtResultBody<String> deleteAll(String ids) {
//		String deleteAll = costService.deleteAll(ids);
//		return new LyhtResultBody<>(deleteAll);
//	}
//
//	@ApiOperation(value = "删除(按id删除)", notes = "删除(按id删除)")
//	@GetMapping("/delete")
//	public LyhtResultBody<String> delete(String id) {
//		String delete = costService.delete(id);
//		return new LyhtResultBody<>(delete);
//	}

	@GetMapping("/export")
	@ApiOperation(value = "导出补偿费用手册（移民户登记手册）", notes = "导出补偿费用手册（移民户登记手册）")
	public void export(String ownerNm, HttpServletResponse response) {
		costService.export(ownerNm, response);
	}

	@ApiOperation(value = "汇总（树）", notes = "汇总（树）")
	@GetMapping("/aggregate/tree")
	public LyhtResultBody<List<CostAggregateTreeVO>> findCostAggregate() {
		CostAggregateTreeVO findCostAggregate = costService.findCostAggregate();
		List<CostAggregateTreeVO> list = new ArrayList<>();
		list.add(findCostAggregate);
		return new LyhtResultBody<>(list);
	}

	@ApiOperation(value = "汇总（分项卡片）", notes = "汇总（分项卡片）")
	@PostMapping("/aggregate/card")
	public LyhtResultBody<List<CostAggregateCardVO>> findCostAggregateCard(
			@ApiParam(value = "行政区域全称") String mergerName,
			@ApiParam(value = "卡片类型(all：总计；house：房屋及房屋装修；building：附属建筑物及农副业设施；trees：零星果木；crops：成片青苗及林木；other：其他补偿补助。)") String type) {
		List<CostAggregateCardVO> findCostAggregateCard = costService.findCostAggregateCard(mergerName, type);
		return new LyhtResultBody<>(findCostAggregateCard);
	}

	@ApiOperation(value = "按行政区统计(门户页统计图)", notes = "按行政区统计(门户页统计图)")
	@ApiImplicitParam(name = "parentCode", value = "父级编码", paramType = "query")
	@PostMapping("/aggregate/chat")
	public LyhtResultBody<List<CostAggregateChatVO>> findCostChat(String parentCode) {
		List<CostAggregateChatVO> findCostChat = costService.findCostChat(parentCode);
		return new LyhtResultBody<>(findCostChat);
	}

}
