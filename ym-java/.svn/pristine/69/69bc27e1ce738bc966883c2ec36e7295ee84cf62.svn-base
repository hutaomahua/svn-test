package com.lyht.business.process.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.process.dto.response.ProcessQuantityGroupResponse;
import com.lyht.business.process.dto.response.ProcessSelectResponse;
import com.lyht.business.process.dto.response.model.BackStepData;
import com.lyht.business.process.dto.response.model.InquireData;
import com.lyht.business.process.entity.ProcessEntity;
import com.lyht.business.process.service.ProcessInquireService;
import com.lyht.business.process.service.ProcessOperateService;
import com.lyht.business.process.vo.ProcessCountVO;
import com.lyht.business.process.vo.ProcessOperateBatchVO;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.FileDownUtil;
import com.lyht.util.ZipUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * modifier hth 2019.12.15 14:30
 */
@Api(value = "/process", tags = "流程")
@RestController
@RequestMapping("/process")
public class ProcessController {
	private Logger logger = LoggerFactory.getLogger(ProcessController.class);

	@Autowired
	private PubFilesService pubFilesService;

	@Autowired
	private ProcessOperateService processService;

	@Autowired
	private ProcessInquireService processInquireService;

	@Value("${lyht.file.upload.path}")
	private String filePath;

	// 海选接口
	@ApiOperation(value = "返回海选人员列表", notes = "由前端判断，仅确认海选的步骤调用")
	@PostMapping("/selectList")
	public LyhtResultBody<ProcessSelectResponse> selectList(ProcessOperateVO processOperateVO,
			HttpServletRequest request) {
		ProcessSelectResponse res = processService.selectList(processOperateVO, request);
		return new LyhtResultBody<>(res);
	}

	// 流程审批接口
	@ApiOperation(value = "流程审批（通过、拒绝、退回）", notes = "流程审批（通过、拒绝、退回）")
	@PostMapping("/operate")
	public LyhtResultBody<Boolean> sync(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		boolean processOperate = processService.processOperate(processOperateVO, request);
		return new LyhtResultBody<>(processOperate);
	}

	// 流程批量审批接口
	@ApiOperation(value = "流程审批（通过、拒绝、退回）", notes = "流程审批（通过、拒绝、退回）")
	@PostMapping("/operateBatch")
	public LyhtResultBody<Boolean> sync(@RequestBody ProcessOperateBatchVO processOperateVO, HttpServletRequest request) {
		boolean processOperate = true;
		List<ProcessOperateVO> list = processOperateVO.getVoList();
		for (ProcessOperateVO operateVO : list) {
			processOperate = processOperate && processService.processOperate(operateVO, request);
		}
		return new LyhtResultBody<>(processOperate);
	}

	@ApiOperation(value = "流程审批（通过）", notes = "流程审批（通过）")
	@PostMapping("/approve")
	public LyhtResultBody<Boolean> approve(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		boolean processOperate = processService.processApprove(processOperateVO, request);
		return new LyhtResultBody<>(processOperate);
	}

	@ApiOperation(value = "流程审批（拒绝）", notes = "流程审批（拒绝）")
	@PostMapping("/reject")
	public LyhtResultBody<Boolean> reject(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		boolean processOperate = processService.processReject(processOperateVO, request);
		return new LyhtResultBody<>(processOperate);
	}

	@ApiOperation(value = "流程审批（退回）", notes = "流程审批（退回）")
	@PostMapping("/pickback")
	public LyhtResultBody<Boolean> pickback(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		boolean processOperate = processService.processPickback(processOperateVO, request);
		return new LyhtResultBody<>(processOperate);
	}

	// 流程取消
	@ApiOperation(value = "流程取消", notes = "流程取消")
	@PostMapping("/remove")
	public LyhtResultBody<Boolean> remove(String taskId, HttpServletRequest request) {
		boolean processOperate = processService.processRemove(taskId, request);
		return new LyhtResultBody<>(processOperate);
	}

	// 流程发起接口
	@ApiOperation(value = "流程发起（申请流程）", notes = "流程发起（申请流程）")
	@PostMapping("/start")
	public LyhtResultBody<String> start(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		return new LyhtResultBody<String>(processService.processStart(processOperateVO, request));
	}

	// 流程查询接口
	@ApiOperation(value = "查询用户处理过的流程", notes = " 查询用户处理过的流程")
	@PostMapping("/gethandled")
	public LyhtResultBody<List<InquireData>> processGetHandled(String userId, String taskName, LyhtPageVO vo,
			HttpServletRequest request) {
		return processInquireService.processGetHandled(userId, taskName, vo, request);
	}

	@ApiOperation(value = "查询用户所有提交过的流程", notes = "查询用户所有提交过的流程")
	@PostMapping("/getall")
	public LyhtResultBody<List<InquireData>> processGetJoin(String userId, String taskName, LyhtPageVO vo,
			HttpServletRequest request) {
		return processInquireService.processGetJoin(userId, taskName, vo, request);
	}

	@ApiOperation(value = "查询用户待办流程", notes = "查询用户待办流程")
	@PostMapping("/getstandby")
	public LyhtResultBody<List<InquireData>> processGetStandBy(String userId, String taskName, LyhtPageVO vo,
			HttpServletRequest request) {
		return processInquireService.processGetStandBy(userId, taskName, vo, request);
	}

	@ApiOperation(value = "根据流程ID得到流程审批步骤信息", notes = " 根据流程ID得到流程审批步骤信息")
	@PostMapping("/getStatusByTaskId")
	public LyhtResultBody<List<BackStepData>> processGetStandBy(String taskId, HttpServletRequest request) {
		return processInquireService.processGetByTaskId(taskId, request);
	}

	@ApiOperation(value = "根据taskId和stepId查询可以回退的步骤", notes = "根据taskId和stepId查询可以回退的步骤")
	@PostMapping("/getBackSteps")
	public LyhtResultBody<List<BackStepData>> processBackSteps(String taskId, String stepId,
			HttpServletRequest request) {
		return processInquireService.processBackSteps(taskId, stepId, request);
	}

	@ApiOperation(value = "获取流程详情(数据库)", notes = "获取流程详情(数据库)")
	@PostMapping("/getprocesslocal")
	public ProcessEntity getProcessLocal(String taskId) {
		return processInquireService.getProcessLocal(taskId);
	}

	@ApiOperation(value = "查询当前审批步骤信息", notes = "根据taskId查询")
	@PostMapping("/getCurSteps")
	public LyhtResultBody<List<BackStepData>> processCurStep(String taskId, HttpServletRequest request) {
		return processInquireService.processCurStep(taskId, request);
	}

	@ApiOperation(value = "查询某审批步骤信息", notes = "根据stepId查询")
	@PostMapping("/getStepInfo")
	public LyhtResultBody<BackStepData> processGetStepInfo(String stepId, HttpServletRequest request) {
		return processInquireService.getStepInfo(stepId, request);
	}

	@ApiOperation(value = "上传附件", notes = "支持多个上传")
	@PostMapping("/uploadFiles")
	public LyhtResultBody<List<PubFilesEntity>> upload(HttpServletRequest request,
			@RequestParam(required = false, value = "files") MultipartFile[] files, String stepId) {
		PubFilesEntity entity = new PubFilesEntity();
		entity.setTableName("t_bpm_process");
		entity.setTablePkColumn(stepId);
		return pubFilesService.uploads(request, files, entity);
	}

	@ApiOperation(value = "文件下载", notes = "展示图片时isDownload为false，下载时isDownload为true，传入单个nm返回文件下载，传入多个nm以英文逗号分隔，返回压缩包下载")
	@GetMapping("/download")
	public List<Map<String, String>> download(HttpServletRequest request, HttpServletResponse response, String nms,
			boolean isDownload) {
		List<PubFilesEntity> fileList = processInquireService.getFileEntities(nms);
		List<Map<String, String>> result = new ArrayList<>();
		// 下载文件
		if (isDownload) {
			// 单文件下载
			if (fileList.size() == 1) {
				PubFilesEntity entity = fileList.get(0);
				FileDownUtil.getFile(filePath + entity.getFileUrl(), entity.getFileName() + "." + entity.getFileType(),
						response);
				return null;
			}
			// 批量下载
			else {
				List<String> fileUrls = new ArrayList<>();
				for (PubFilesEntity entity : fileList) {
					fileUrls.add(entity.getFileUrl());
				}
				ZipOutputStream out = null;
				try {
					// 打包的文件名
					String packageName = "附件.zip";
					// 打包下载
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition",
							"attachment;filename=" + URLEncoder.encode(packageName, "UTF-8"));
					out = new ZipOutputStream(response.getOutputStream());
					// 设置压缩编码
					out.setEncoding("UTF-8");
					if (!CollectionUtils.isEmpty(fileUrls)) {
						for (String fileUrl : fileUrls) {
							ZipUtils.doZip(new File(filePath + fileUrl), out, "");
							response.flushBuffer();
						}
					} else {
						logger.error("文件下载失败！文件不存在");
						throw new LyhtRuntimeException(LyhtExceptionEnums.DOWNLOAD_FAILURE);
					}
				} catch (IOException e) {
					throw new LyhtRuntimeException(LyhtExceptionEnums.DOWNLOAD_FAILURE);
				} finally {
					try {
						out.close();
					} catch (IOException e) {
						logger.error("文件下载失败！：", e);
						throw new LyhtRuntimeException(LyhtExceptionEnums.DOWNLOAD_FAILURE);
					}
				}
			}
		}
		// 展示图片
		else {
			for (PubFilesEntity entity : fileList) {
				String fileType = entity.getFileType();
				String content = "^(?i)bmp,jpg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp";
				int isMatch = content.indexOf(fileType);
				Map<String, String> map = new HashMap<>();
				if (isMatch != -1) {// 是图片
					map.put("name", entity.getFileName() + "." + fileType);
					map.put("url", entity.getFileUrl());
					map.put("nm", entity.getNm());
				} else {
					map.put("name", entity.getFileName() + "." + fileType);
					map.put("url", "");
					map.put("nm", entity.getNm());
				}
				result.add(map);
			}
		}
		return result;
	}

	@ApiOperation(value = "通过用户id获取(流程名称丶待办数量丶已通过数量丶已拒绝数量丶已提交数量),按流程名称分组", notes = "通过用户id获取(流程名称丶待办数量丶已通过数量丶已拒绝数量丶已提交数量),按流程名称分组")
	@GetMapping("/quantity")
	public LyhtResultBody<List<ProcessQuantityGroupResponse>> getQuantityByUserId(HttpServletRequest request) {
		List<ProcessQuantityGroupResponse> processGetQuantityByUserId = processInquireService
				.processGetQuantityByUserId(request);
		return new LyhtResultBody<>(processGetQuantityByUserId);
	}

	@ApiOperation(value = "通过用户id获取待办丶已办丶已提交 数量", notes = "通过用户id获取待办丶已办丶已提交 数量")
	@GetMapping("/count")
	public LyhtResultBody<ProcessCountVO> getCountByUserId(HttpServletRequest request) {
		ProcessCountVO processCountVO = processInquireService.processCountByUserId(request);
		return new LyhtResultBody<>(processCountVO);
	}

	@ApiOperation(value = "通过用户id获取待办数量", notes = "通过用户id获取待办数量")
	@GetMapping("/standby/count")
	public LyhtResultBody<Long> getStandbyTaskCountByUserId(HttpServletRequest request) {
		Long count = processInquireService.processGetStandbyTaskCountByUserId(request);
		return new LyhtResultBody<>(count);
	}

	@ApiOperation(value = "通过用户id获取已办数量", notes = "通过用户id获取已办数量")
	@GetMapping("/handled/count")
	public LyhtResultBody<Long> getHandledTaskCountByUserId(HttpServletRequest request) {
		Long count = processInquireService.processGetHandledTaskCountByUserId(request);
		return new LyhtResultBody<>(count);
	}

	@ApiOperation(value = "通过用户id获取已提交数量", notes = "通过用户id获取已提交数量")
	@GetMapping("/submit/count")
	public LyhtResultBody<Long> getMyJoinTaskCountByUserId(HttpServletRequest request) {
		Long count = processInquireService.processGetMyJoinTaskCountByUserId(request);
		return new LyhtResultBody<>(count);
	}

}