package com.lyht.business.abm.paymentManagement.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.paymentManagement.entity.PaymentDetailEntity;
import com.lyht.business.abm.paymentManagement.pojo.ApplyAmountCalculate;
import com.lyht.business.abm.paymentManagement.pojo.ApplyDetail;
import com.lyht.business.abm.paymentManagement.pojo.ApplyInfo;
import com.lyht.business.abm.paymentManagement.pojo.InfoListPojo;
import com.lyht.business.abm.paymentManagement.pojo.InfoPagePojo;
import com.lyht.business.abm.paymentManagement.service.PaymentApplyService;
import com.lyht.business.abm.paymentManagement.to.Msg;
import com.lyht.business.abm.paymentManagement.vo.ApplyAuditVO;
import com.lyht.business.abm.paymentManagement.vo.InfoListVO;
import com.lyht.business.abm.paymentManagement.vo.OwnerPaymentInfoVO;
import com.lyht.business.abm.paymentManagement.vo.OwnerSelectVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentInfoVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentMethodVO;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.system.pojo.SysDept;
import com.lyht.util.SystemUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/payment")
@Api(value = "兑付管理", tags = "兑付管理")
public class PaymentApplyController {
	
	@Autowired
	private PaymentApplyService service;
	
//	@Autowired
//	private PaymentAuditService auditService;
	
	@GetMapping(value = "/paymentStatistics")
    @ApiOperation(value = "兑付主页统计列表", notes = "兑付主页统计列表 levelType:{1:省,2:市,3:县,4:乡,5:村,6:组}")
    public LyhtResultBody<List<Map<String, Object>>> paymentStatistics(Integer levelType) {
		List<Map<String, Object>> list = service.paymentStatistics(levelType);
        return new LyhtResultBody<List<Map<String, Object>>>(list);
    }

	@GetMapping(value = "/allregion")
    @ApiOperation(value = "全部行政区数据", notes = "全部行政区数据 levelType:{1:省,2:市,3:县,4:乡,5:村,6:组}")
    public LyhtResultBody<List<Map<String, Object>>> allRegion(Integer levelType) {
		List<Map<String, Object>> list = service.allRegion(levelType);
        return new LyhtResultBody<List<Map<String, Object>>>(list);
    }
	
	@GetMapping(value = "/statisticsDetail")
    @ApiOperation(value = "统计数据详细", notes = "统计数据详细 region:行政区明文/type:{amount:已发起兑付申请的协议的总额,payed:已申请过协议总额,surplus:待申请的协议总额}")
    public LyhtResultBody<Map<String, Object>> statisticsDetail(String region,String type) {
		Map<String, Object> list = service.statisticsDetail(region,type);
        return new LyhtResultBody<Map<String, Object>>(list);
    }
	
	@GetMapping(value = "/infoList")
    @ApiOperation(value = "兑付信息列表", notes = "兑付信息列表")
	public LyhtResultBody<List<InfoListVO>> infoList(InfoListPojo infoListPojo){
		List<InfoListVO> result = service.infoList(infoListPojo);
		return new LyhtResultBody<List<InfoListVO>>(result);
		
	}
	
	@ApiOperation(value = "权属人兑付信息分页查询", notes = "权属人兑付信息分页查询")
	@PostMapping("/ownerInfoPage")
	public LyhtResultBody<List<OwnerPaymentInfoVO>> ownerPaymentInfoList(LyhtPageVO lyhtPageVO,InfoPagePojo infoPagePojo){
		Page<OwnerPaymentInfoVO> page = service.ownerPaymentInfoList(infoPagePojo,lyhtPageVO);
		List<OwnerPaymentInfoVO> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<List<OwnerPaymentInfoVO>>(list,pageVO);
	}
	
	@ApiOperation(value = "协议兑付信息分页查询", notes = "协议兑付信息分页查询")
	@PostMapping("/protocolInfoPage")
	public LyhtResultBody<List<InfoListVO>> infoPage(LyhtPageVO lyhtPageVO,InfoPagePojo infoPagePojo) {
		Page<InfoListVO> page = service.infoPage(infoPagePojo,lyhtPageVO);
		List<InfoListVO> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<List<InfoListVO>>(list,pageVO);
	}
	
	@GetMapping(value = "/detailList")
    @ApiOperation(value = "兑付申请详情列表", notes = "兑付申请详情列表")
    public LyhtResultBody<List<PaymentDetailEntity>> detailList() {
    	
		List<PaymentDetailEntity> list = service.detailList();

        return new LyhtResultBody<>(list);
    }
	
	@PostMapping(value = "/auditList")
    @ApiOperation(value = "兑付审核台账列表", notes = "兑付审核台账列表")
    public LyhtResultBody<List<ApplyAuditVO>> auditList(LyhtPageVO lyhtPageVO,InfoPagePojo infoPagePojo) {
		Page<ApplyAuditVO> page = service.auditList(infoPagePojo,lyhtPageVO);
		List<ApplyAuditVO> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<List<ApplyAuditVO>>(list,pageVO);
    }
	
	@GetMapping(value = "/applyAuditInfo")
    @ApiOperation(value = "兑付申请信息", notes = "根据mis流程Id获取兑付申请信息")
	public LyhtResultBody<ApplyAuditVO> paymentAuditInfo(String processId){
		ApplyAuditVO res = service.getByProcessId(processId);
		return new LyhtResultBody<>(res);
	}

	@PostMapping(value = "/apply")
    @ApiOperation(value = "发起兑付申请", notes = "发起兑付申请")
	public LyhtResultBody<Msg<BigDecimal>> apply(ApplyInfo applyInfo,String applyDetail,ProcessOperateVO processOperateVO,HttpServletRequest request){
		List<ApplyDetail> list= JSON.parseArray(applyDetail,ApplyDetail.class);
		applyInfo.setDetailList(list);
		
		Msg<BigDecimal> msg = service.save(applyInfo,processOperateVO,request);
		
		return new LyhtResultBody<Msg<BigDecimal>>(msg);
	}
	
	@PostMapping(value = "/applyAmountCalculate")
    @ApiOperation(value = "申请金额计算", notes = "申请金额计算")
	public LyhtResultBody<Msg<BigDecimal>> applyAmountCalculate(String applyAmountCalculate){
		List<ApplyAmountCalculate> list= JSON.parseArray(applyAmountCalculate,ApplyAmountCalculate.class);
		Msg<BigDecimal> result = new Msg<BigDecimal>(new BigDecimal(0));
		for (ApplyAmountCalculate pojo : list) {
			Msg<BigDecimal> msg = service.ApplyAmountCalculate(pojo.getOwnernm(),pojo.getProtocolCode(),pojo.getProtocolAmount(),pojo.getProtocolsurplus(),pojo.getProtocolType(),pojo.getPlaceType(),pojo.getBatch());
			if(msg.getFlag()){				
				result.setResult(result.getResult().add(msg.getResult()));
			}else{
				return new LyhtResultBody<Msg<BigDecimal>>(msg);
			}
		}
		return new LyhtResultBody<Msg<BigDecimal>>(result);
	}
	
	@GetMapping(value = "/ownerList")
    @ApiOperation(value = "权属人列表", notes = "已签订协议的权属人列表")
	public LyhtResultBody<List<OwnerSelectVO>> ownerList(HttpServletRequest request){
		SysDept loginDept = SystemUtil.getLoginDept(request);//获取当前登陆人单位做权限处理
		List<OwnerSelectVO> msg = service.resolveOwnerList(loginDept);
		return new LyhtResultBody<List<OwnerSelectVO>>(msg);
	}
	
	@GetMapping(value = "/signedProtocol")
    @ApiOperation(value = "已签订的协议", notes = "根据权属人内码获取已签订的协议")
	public LyhtResultBody<List<Map<String, Object>>> signedProtocol(String ownerNm){
		List<Map<String, Object>> protocolList = service.querySignedProtocol(ownerNm,null,null,null,null);
		return new LyhtResultBody<List<Map<String, Object>>>(protocolList);
	}
	
	@GetMapping(value = "/paymentInfo")
    @ApiOperation(value = "兑付信息", notes = "根据权属人内码获取兑付信息")
	public LyhtResultBody<PaymentInfoVO> paymentInfo(String ownerNm,String protocolCode,Integer protocolType,String type,HttpServletRequest request){
		SysDept loginDept = SystemUtil.getLoginDept(request);//获取当前登陆人单位做权限处理
		PaymentInfoVO paymentInfo = service.getPaymentInfo(ownerNm,protocolCode,protocolType,type,loginDept);
		return new LyhtResultBody<PaymentInfoVO>(paymentInfo);
	}
	
	@GetMapping(value = "/paymentMethodDict")
    @ApiOperation(value = "支付方式字典", notes = "支付方式字典")
	public LyhtResultBody<List<PaymentMethodVO>> paymentMethodDict(){
		List<PaymentMethodVO> paymentMethodDict = service.getPaymentMethodDict();
		return new LyhtResultBody<List<PaymentMethodVO>>(paymentMethodDict);
	}
	
}
