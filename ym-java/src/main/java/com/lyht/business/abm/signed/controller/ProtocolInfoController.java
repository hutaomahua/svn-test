package com.lyht.business.abm.signed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.signed.entity.ProtocolInfo;
import com.lyht.business.abm.signed.service.ProtocolInfoService;
import com.lyht.business.abm.signed.vo.OwnerInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolFileInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolInfoAndEscrowVO;
import com.lyht.business.abm.signed.vo.ProtocolInfoVO;
import com.lyht.business.abm.signed.vo.ProtocolItemVO;
import com.lyht.business.abm.signed.vo.ProtocolVO;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.AmountToChineseUtil;
import com.lyht.util.CommonUtil;
import com.lyht.util.FileDownUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * 协议信息表
 * @author wzw
 *
 */
@RequestMapping("/sign/info")
@RestController
@Api(value = "/sign/info", tags = "协议信息表")
public class ProtocolInfoController {

	@Autowired
	private ProtocolInfoService service;
	
	@Autowired
	private PubFilesService pubFilesService;
	
	/**
	 * 根据查询权属人选择资金代管的补偿协议信息
	 * @param ownerNm 权属人nm
	 * @return
	 */
	@ApiOperation(value = "根据查询权属人选择资金代管的补偿协议信息", notes = "查询协议信息")
	@PostMapping("/findProtocolInfo")
	public LyhtResultBody<ProtocolInfo> findProtocolInfo(String ownerNm) {
		return new LyhtResultBody<>(service.findProtocolInfo(ownerNm));
	}

	/**
	 * 根据权属人身份证号查询协议信息
	 * @param idCard 权属人身份证
	 * @return
	 */
	@ApiOperation(value = "根据权属人身份证号查询协议信息", notes = "查询协议信息")
	@PostMapping("/getProtocolByIdCard")
	public LyhtResultBody<List<ProtocolVO>> getProtocolByIdCard(String idCard) {
		return service.getProtocolByIdCard(idCard);
	}

	/**
	 * 流程审批成功 回调接口 根据流程id修改协议状态
	 * processId 流程id uuid
	 */
	@ApiOperation(value = "根据流程id修改协议状态", notes = "修改协议状态")
	@PostMapping("/updateProtocolState")
	public LyhtResultBody<Integer> updateProtocolState(Integer type,String processId,String senderNm) {
		service.updateProtocolState(type,processId,senderNm);
		return new LyhtResultBody<>();
	}

	/**
	 * 根据流程id查询展示信息
	 * 
	 * @param processId 流程id uuid
	 * @return
	 */
	@ApiOperation(value = "通过流程ID查询表单", notes = "通过流程ID查询表单")
	@PostMapping("/getByTaskId")
	public LyhtResultBody<ProtocolInfoAndEscrowVO> getByTaskId(String taskId) {
		return service.getByTaskId(taskId);
	}

	/**
	 * 
	 * 
	 * @param id 补偿协议id
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "发起流程", notes = "发起流程")
	@GetMapping("/start")
	public LyhtResultBody<ProtocolInfo> startChange(HttpServletRequest request, Integer id,String fileNm) {
		ProtocolInfo protocolInfo = service.startChange(request, id,fileNm);
		return new LyhtResultBody<>(protocolInfo);
	}

	/**
	 * 附件信息 数据保存
	 * @param request
	 * @param bean
	 * @return
	 */
	@ApiOperation(value = "附件上传(多个)", notes = "附件上传")
	@PostMapping("/uploads")
	@ResponseBody
	public LyhtResultBody<List<PubFilesEntity>> uploads(HttpServletRequest request,
			@RequestParam(required = false, value = "files") MultipartFile[] files, PubFilesEntity pubFileEntity,
			Integer flag) {
		return service.uploads(request, files, pubFileEntity, flag);
	}

	/**
	 * 附件信息 单个删除
	 * @param request
	 * @param id 补偿协议id
	 * @return
	 */
	@ApiOperation(value = "按id删除 附件", notes = "删除附件")
	@GetMapping("/deleteFile")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id) {
		return service.delete(request, id);
	}

	/**
	 * 
	 * @param flag 协议完成的总状态  0：未完成 ，1：完成
	 * @param region  行政区模糊查询 merger_name
	 * @param word   模糊查询
	 * @param idCard 权属人身份证号查询
	 * @param lyhtPageVO 分页查询参数
	 * @return
	 */
	@ApiOperation(value = "移民协议签订主页查询 条件查询 分页查询  1、region:行政区模糊查询  ,2:word:模糊查询（安置类型、户主姓名）  idCard 身份证号查询", notes = "page")
	@PostMapping("/page")
	public LyhtResultBody<List<OwnerInfoVO>> page(Integer flag, String region, String word, String idCard,String nm,
			LyhtPageVO lyhtPageVO) {
		return service.page(flag, region, word, idCard,nm, lyhtPageVO);
	}
	
	@ApiOperation(value = "移民协议签订项名称、金额及状态查询   参数 1、nm:权属人nm 必要参数", notes = "page")
	@PostMapping("/getProtocolItem")
	/**
	 * 移民协议签订项名称、金额及状态查询
	 * @param nm 权属人nm
	 * @return
	 */
	public LyhtResultBody<List<ProtocolItemVO>> getProtocolItem(String nm) {
		return service.getProtocolItem(nm);
	}
	/**
	 * 返回 true 为 disable =true  否则 disable = false
	 * @param nm
	 * @return
	 */
	@ApiModelProperty(value = " 判断所有项目签订后，应当禁用确定按钮。")
	@PostMapping("/getIsDisable")
	public LyhtResultBody<Boolean> getIsDisable(String nm) {
		return service.getIsDisable(nm);
	}
	
	/**
	 * 根据 nm 查询权属人查询补偿信息
	 */
	@ApiOperation(value = "根据 nm 查询权属人查询补偿信息   参数nm:权属人nm 必要参数", notes = "page")
	@PostMapping("/getOwnerInfoByNm")
	public LyhtResultBody<OwnerInfoVO> getOwnerInfoByNm(String nm) {
		return service.getOwnerInfoByNm(nm);
	}

	/**
	 * 协议签订页面 列表
	 */
	@ApiOperation(value = " 协议签订页面 列表 nm", notes = "page")
	@PostMapping("/getInfoByPage")
	public LyhtResultBody<List<ProtocolInfoVO>> getInfoByPage(String nm, LyhtPageVO lyhtPageVO) {
		return service.getInfoByPage(nm, lyhtPageVO);
	}

	@ApiOperation(value = "新增(id与nm为空),修改(需要id与nm)", notes = "新增 修改")
	@PostMapping("/save")
	public LyhtResultBody<ProtocolInfo> save(ProtocolInfo ProtocolInfo) {
		return service.save(ProtocolInfo);
	}

	@ApiOperation(value = "根据id进行删除 nm:数据nm  根据nm 删除数据相关附件", notes = "单个删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id, String nm) {
		pubFilesService.deleteBytablePkColumn(request, nm);// 根据数据nm删除相关附件
		return service.delete(id, nm);
	}

	/**
	 * 导出
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "传入导出数据的ID 导出协议", notes = "单个导出")
	@GetMapping("/export")
	public void export(HttpServletResponse response, Integer id) {
		service.exportWord(response,id);
	}

	/**
	 * 预览
	 */
	@ApiOperation(value = "传入导出数据的ID 预览", notes = "预览")
	@PostMapping("/preview")
	public LyhtResultBody<Map<String, Object>> preview(Integer id) {
		Map<String, Object> exportWordVO = service.preview(id);
		// 总金额
		Double column23 = 0.0;
		if (!CommonUtil.isEmpty(exportWordVO.get("column10"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column10") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column11"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column11") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column12"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column12") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column13"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column13") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column14"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column14") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column15"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column15") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column11"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column16") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column17"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column17") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column18"))
				&& Double.parseDouble(exportWordVO.get("column18") + "") > 0) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column18") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column19"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column19") + "");
		}
		if (!CommonUtil.isEmpty(exportWordVO.get("column20"))) {
			column23 = column23 + Double.parseDouble(exportWordVO.get("column20") + "");
		}
		Map<String, Object> map = new HashMap<String, Object>();

		for (Map.Entry<String, Object> entry : exportWordVO.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		String column22 = AmountToChineseUtil.toChinese(String.format("%.2f", column23) + "");
		map.put("column22", String.format("%.2f", column22));
		map.put("column23", column23);
		return new LyhtResultBody<Map<String, Object>>(map);
	}
	
	@ApiOperation(value = "展示所有模板列表", notes = "查询")
	@PostMapping("/getProtocolFileInfo")
	public LyhtResultBody<List<ProtocolFileInfoVO>> getProtocolFileInfo() {
		return service.getProtocolFileInfo();
	}
	
	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param pubFileEntity
	 */
	@ApiOperation(value = "文件下载", notes = "文件下载")
	@GetMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response, String fileUrl,String fileName) {
	    FileDownUtil.getFile(fileUrl,
				fileName, response);
	}

}
