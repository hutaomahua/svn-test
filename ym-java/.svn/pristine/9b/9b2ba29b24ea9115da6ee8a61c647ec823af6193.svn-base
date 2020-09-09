package com.lyht.business.abm.paymentManagement.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.paymentManagement.pojo.StringListPojo;
import com.lyht.business.abm.paymentManagement.pojo.PaymentConfirmPojo;
import com.lyht.business.abm.paymentManagement.pojo.PaymentConfirmationBatchPojo;
import com.lyht.business.abm.paymentManagement.service.PaymentConfirmationService;
import com.lyht.business.abm.paymentManagement.to.Msg;
import com.lyht.business.abm.paymentManagement.vo.ApplyAuditVO;
import com.lyht.business.abm.paymentManagement.vo.ApplyBatchRecordVO;
import com.lyht.business.abm.paymentManagement.vo.BatchInfoVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentConfirmationBatchVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentConfirmationVO;
import com.lyht.business.abm.paymentManagement.vo.PubFilesVO;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.util.ExportExcelWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/payment/confirmation")
@Api(value = "兑付确认", tags = "兑付确认")
public class PaymentConfirmationController {
	
	@Autowired
	PaymentConfirmationService paymentConfirmationService;
	
	@GetMapping(value = "/confirmationlist")
    @ApiOperation(value = "待兑付确认列表", notes = "待兑付确认列表")
    public LyhtResultBody<List<PaymentConfirmationVO>> confirmationList(String region) {
		List<PaymentConfirmationVO> confirmationList = paymentConfirmationService.confirmationList(region);
        return new LyhtResultBody<List<PaymentConfirmationVO>>(confirmationList);
    }
	
	@PostMapping(value = "/confirm")
    @ApiOperation(value = "兑付确认", notes = "兑付确认")
	public LyhtResultBody<Msg<String>> confirm(PaymentConfirmPojo pojo,HttpServletRequest request){
		Msg<String> msg = paymentConfirmationService.confirm(pojo,request);
		return new LyhtResultBody<Msg<String>>(msg);
	}

	@GetMapping(value = "/audit")
	@ApiImplicitParam(name = "nm",value = "批次内码")
    @ApiOperation(value = "兑付确认审批", notes = "兑付确认审批")
	public LyhtResultBody<Msg<String>> audit(String nm,HttpServletRequest request){
		Msg<String> res = paymentConfirmationService.audit(nm,request);
		return new LyhtResultBody<>(res);
	}
	
	@PostMapping(value = "/infopage")
    @ApiOperation(value = "兑付确认批次列表", notes = "兑付确认批次列表")
	public LyhtResultBody<List<PaymentConfirmationBatchVO>> infoPage(LyhtPageVO lyhtPageVO,PaymentConfirmationBatchPojo pojo){
		Page<PaymentConfirmationBatchVO> page = paymentConfirmationService.infoPage(pojo,lyhtPageVO);
		List<PaymentConfirmationBatchVO> list = page.getContent();
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<List<PaymentConfirmationBatchVO>>(list,pageVO);
	}

	@GetMapping(value = "/batchinfo")
    @ApiOperation(value = "兑付确认详情", notes = "兑付确认详情")
	public LyhtResultBody<BatchInfoVO> batchInfo(String nm){
		BatchInfoVO batchInfo = paymentConfirmationService.batchInfo(nm);
		return new LyhtResultBody<>(batchInfo);
	}
	
	@GetMapping(value = "/batchinfobyprocessId")
    @ApiOperation(value = "兑付确认详情", notes = "根据mis流程Id获取兑付确认")
	public LyhtResultBody<BatchInfoVO> batchinfobyprocessId(String processId){
		BatchInfoVO batchInfo = paymentConfirmationService.getByProcessId(processId);
		return new LyhtResultBody<>(batchInfo);
	}
	
	@ApiOperation(value = "删除批次记录", notes = "删除批次记录")
	@ApiImplicitParam(name="nm",value="兑付确认批次记录内码")
	@GetMapping("/delete")
	public LyhtResultBody<String> delete(String nm,HttpServletRequest request){
		paymentConfirmationService.delete(nm);
		return new LyhtResultBody<>("成功！");
	}
	
	/**
	 * 导出Excel
	 * @return
	 */
	@ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
	@SuppressWarnings("rawtypes")
	@GetMapping("/export")
	public LyhtResultBody<String> exportExcel(HttpServletResponse response, String nms) {
		List<Map> list = paymentConfirmationService.paymentDetailList(nms);
		String title = "支付清单";
		String fileName = title;
		String[] headers 		= { "姓名",	"账号",		"金额",			"序号",	"证件号码",	"证件类型",	"应发金额" };
		String[] columnNames 	= { "payee","bankCard",	"applyAmount",	"num",	"idCard",	"idType",	"applyAmount" };
		try {
			ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
			exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
					ExportExcelWrapper.EXCEl_FILE_2007);
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>("导出成功");
	}
	
	/**
	 * 附件信息 数据保存
	 * @param request
	 * @param files
	 * @param pojo
	 * @return
	 */
	@ApiOperation(value = "附件上传", notes = "附件上传(多个)(参数：兑付申请记录内码数组，附件)")
	@PostMapping("/uploads")
	@ResponseBody
	public LyhtResultBody<List<PubFilesEntity>> uploads(HttpServletRequest request,
			@RequestParam(required = false, value = "files") MultipartFile[] files, StringListPojo pojo) {
		return paymentConfirmationService.uploads(request, files, pojo.getData());
	}
	
	
	/**
	 * 根据兑付批次内码查询与之关联的兑付申请记录附件
	 * @param pojo
	 * @return
	 */
	@ApiOperation(value = "根据兑付批次内码查询与之关联的兑付申请记录附件", notes = "根据兑付批次内码查询与之关联的兑付申请记录附件（参数：兑付确认批次内码数组）")
	@PostMapping("/findFileDetailsByConfirmationBatchNms")
	public LyhtResultBody<List<PubFilesVO>> findFileDetailsByConfirmationBatchNms(StringListPojo pojo) {
		List<PubFilesVO> res = paymentConfirmationService.findFileDetailsByConfirmationBatchNms(pojo.getData());
		return new LyhtResultBody<>(res);
	}
	
	/**
	 * 根据申请记录内码查询附件列表
	 * @param pojo
	 * @return
	 */
	@ApiOperation(value = "根据申请记录内码查询附件列表", notes = "根据申请记录内码查询附件列表（参数：兑付申请记录内码数组）")
	@PostMapping("/findFileDetailsByApplyNms")
	public LyhtResultBody<List<PubFilesVO>> findFileDetailsByApplyNms(StringListPojo pojo) {
		List<PubFilesVO> res = paymentConfirmationService.findFileDetailsByApplyNms(pojo.getData());
		return new LyhtResultBody<>(res);
	}
	
	/**
	 * 根据附件内码查询关联兑付申请记录
	 * @param fileNm
	 * @return
	 */
	@ApiOperation(value = "根据附件内码查询关联兑付申请记录", notes = "根据附件内码查询关联兑付申请记录（参数：附件内码数组）")
	@GetMapping("/findApplyDetailsByFileNms")
	public LyhtResultBody<List<ApplyBatchRecordVO>> findApplyDetailsByFileNms(StringListPojo pojo) {
		List<ApplyBatchRecordVO> res = paymentConfirmationService.findApplyDetailsByFileNms(pojo.getData());
		return new LyhtResultBody<>(res);
	}
	
	/**
	 * 根据附件内码删除附件，并解除关联
	 * @param fileNm
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "附件删除", notes = "根据附件内码删除附件，并解除关联")
	@ApiImplicitParam(name="fileNm",value="附件内码")
	@GetMapping("/delfile")
	public LyhtResultBody<Msg<String>> delFile(String tablePkColumn,HttpServletRequest request){
		Msg<String> msg = paymentConfirmationService.delFile(tablePkColumn);
		return new LyhtResultBody<>(msg);
	}
	
	@ApiOperation(value = "解除附件", notes = "解除附件关联")
	@ApiImplicitParams({
		@ApiImplicitParam(name="fileTablePkColumn",value="附件表关联码"),
		@ApiImplicitParam(name="mergeTablePkColumn",value="关联表关联码")
	})
	@GetMapping("/delMerge")
	public LyhtResultBody<Msg<String>> delMerge(String fileTablePkColumn,String mergeTablePkColumn,HttpServletRequest request){
		Msg<String> msg = paymentConfirmationService.delMerge(fileTablePkColumn,mergeTablePkColumn,request);
		return new LyhtResultBody<>(msg);
	}
}