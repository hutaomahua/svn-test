package com.lyht.business.abm.signed.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.signed.entity.ProtocolEscrow;
import com.lyht.business.abm.signed.service.ProtocolEscrowService;
import com.lyht.business.abm.signed.vo.EscrowPerviewVO;
import com.lyht.business.abm.signed.vo.GraphVO;
import com.lyht.business.abm.signed.vo.ProtocolStatisticsVO;
import com.lyht.business.pub.service.PubFilesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/sign/escrow")
@RestController
@Api(value = "/sign/escrow", tags = "资金代管协议信息表")
public class ProtocolEscrowController {
	@Autowired
	private ProtocolEscrowService service;

	@Autowired
	private PubFilesService pubFilesService;
	
	
	/**
	 * 首页统计 图标  
	 * @param mergerName  行政区 全称
	 * @return
	 */
	@ApiOperation(value = "主页统计查询", notes = "查询")
	@PostMapping("/getHomePageGraph")
	public LyhtResultBody<List<GraphVO>> getHomePageGraph(String mergerName,Integer levelType){
		return service.getHomePageGraph(mergerName,levelType);
	}
	
	@ApiOperation(value = "主页统计查询", notes = "查询")
	@PostMapping("/getStatisticsByParentCode")
	public LyhtResultBody<List<Map<String,Object>>> getStatisticsByParentCode(String parentCode){
		return service.getStatisticsByParentCode(parentCode);
	}
	
	/**
	 * 移民协议汇总页  树形统计查询
	 * 
	 * @return
	 */
	@ApiOperation(value = "主页统计查询", notes = "查询")
	@PostMapping("/getStatistics")
	public LyhtResultBody<List<Map<String,Object>>> getStatistics(){
		return service.getStatistics();
	}
	
	/**
	 * 主页统计查询点击不同列  type: 0（总户数 协议金额） 1(已签订户数，已签订协议金额)  2(资金代管户数,资金代管金额)
	 * @param type 判断点击列
	 * @param region 行政区条件 merger_name
	 * @return
	 */
	@ApiOperation(value = "主页统计查询点击不同列  type: 0（总户数 协议金额） 1(已签订户数，已签订协议金额)  2(资金代管户数,资金代管金额)", notes = "查询")
	@PostMapping("/getTableList")
	public LyhtResultBody<List<ProtocolStatisticsVO>> getTableList(Integer type,String region){
		return service.getTableList(type, region);
	}

	/**
	 * （0：否；1：是）查询资金代管协议是否签订 已签订则不可继续签订 未签订则可签订（前提 已签订房屋补偿协议）
	 * @param ownerNm 权属人nm
	 * @return
	 */
	@ApiOperation(value = "（0：否；1：是）查询资金代管协议是否签订 已签订则不可继续签订 未签订则可签订（前提 已签订房屋补偿协议）", notes = "查询")
	@PostMapping("/getEscrowCount")
	public LyhtResultBody<Integer> getEscrowCount(String ownerNm) {
		return service.getEscrowCount(ownerNm);
	}

	/**
	 * （0：否；1：是）查询是否已签订房屋补偿协议  先判断是否已签订房屋补偿协议 如未签订则点击按钮时提示：未签订房屋补偿协议 
	 * 如已签订房屋补偿协议则继续判断是否已签订资金代管协议 已签订提示：已签订资金代管协议不可重复签订 通过判断则 弹出资金代管协议表单
	 * @param ownerNm 权属人nm
	 * @return
	 */
	@ApiOperation(value = "（0：否 不能签；1：是  可以签）查询是否已签订房屋补偿协议  先判断是否已签订房屋补偿协议 如未签订则点击按钮时提示：未签订房屋补偿协议 ；"
			+ "如已签订房屋补偿协议则继续判断是否已签订资金代管协议 已签订提示：已签订资金代管协议不可重复签订 通过判断则 弹出资金代管协议表单", notes = "查询")
	@PostMapping("/getHouseStatus")
	public LyhtResultBody<Integer> getHouseStatus(String ownerNm) {
		return service.getHouseStatus(ownerNm);
	}

	/**
	 * 新增(id与nm为空),修改(需要id与nm)
	 * @param ProtocolEscrow
	 * @return
	 */
	@ApiOperation(value = "新增(id与nm为空),修改(需要id与nm)", notes = "新增 修改")
	@PostMapping("/save")
	public LyhtResultBody<ProtocolEscrow> save(ProtocolEscrow ProtocolEscrow) {
		return service.save(ProtocolEscrow);
	}

	/**
	 * 根据id进行删除  nm：当前数据nm 用来删除数据附件
	 * @param request
	 * @param id
	 * @param nm
	 * @return
	 */
	@ApiOperation(value = "根据id进行删除  nm：当前数据nm 用来删除数据附件", notes = "单个删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id, String nm) {
		pubFilesService.deleteBytablePkColumn(request, nm);// 根据数据nm删除相关附件
		return service.delete(id,nm);
	}

	/**
	 * 导出 资金代管协议
	 * @param response
	 * @param id 资金代管协议id
	 * @param content 资金代管协议 可编辑的文档开头内容
	 */
	@ApiOperation(value = "根据id  进行导出", notes = "单个导出")
	@GetMapping("/export")
	public void export(HttpServletResponse response, Integer id,  String content) {
		if (StringUtils.isNotBlank(content)) {
			ProtocolEscrow escrow = service.getOne(id);
			escrow.setContent(content);
			service.save(escrow);
		}
		service.export(response, id);
	}

	/**
	 * 预览
	 * @param id资金代管协议id
	 * @param content资金代管协议 可编辑的文档开头内容
	 * @return
	 */
	@ApiOperation(value = "预览", notes = "单个预览")
	@PostMapping("/preview")
	public LyhtResultBody<EscrowPerviewVO> preview(Integer id, String content,String place) {
		if (StringUtils.isNotBlank(content)) {
			ProtocolEscrow escrow = service.getOne(id);
			escrow.setContent(content);
			escrow.setPlace(place);
			service.save(escrow);
		}
		EscrowPerviewVO perviewVO = service.preview(id);
		return new LyhtResultBody<EscrowPerviewVO>(perviewVO);
	}

}
