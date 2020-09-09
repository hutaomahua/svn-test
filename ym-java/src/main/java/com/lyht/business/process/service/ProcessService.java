package com.lyht.business.process.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.process.common.constant.ProcessConstant;
import com.lyht.business.process.common.enums.LyhtProcessExceptionEnums;
import com.lyht.business.process.dao.ProcessDao;
import com.lyht.business.process.dao.ProcessLogDao;
import com.lyht.business.process.dto.request.ProcessOperateRequest;
import com.lyht.business.process.dto.response.ProcessMessage;
import com.lyht.business.process.dto.response.model.InfoData;
import com.lyht.business.process.dto.response.model.StartFlowData;
import com.lyht.business.process.entity.ProcessEntity;
import com.lyht.business.process.entity.ProcessLogEntity;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.business.process.vo.ProcessStartVO;
import com.lyht.util.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * modifier hth 2019.12.15 14:30
 */

@Service
public class ProcessService {
	private Logger log = LoggerFactory.getLogger(ProcessService.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ProcessDao processDao;
	@Autowired
	private ProcessLogDao processLogDao;

	@Autowired
	private ProcessAuthorizationService processService;
	
	@Autowired
	private CallBackService CallBackService;

	@Value("${iwind.process.project.id}")
	private String projectId;// 项目ID
	@Value("${iwind.process.department.id}")
	private String departmentId;// 部门ID
	@Value("${iwind.process.operate.url}")
	private String processOperateUrl;/// 流程审批的接口地址

	@Value("${iwind.process.start.url}")
	private String startUrl;// 启动url

	@Value("${iwind.process.remove.url}")
	private String removeUrl;// \.add\(new BigDecimal(amount)\)URL

	@Value("${iwind.process.get.url}")
	private String processInfoUrl;// 获取流程信息

	/**
	 * 流程取消
	 * 
	 * @return
	 */
	public boolean processRemove(String taskId, HttpServletRequest request) {
		if (StringUtils.isBlank(taskId)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TASKID_IS_NOT_NULL);
		}
		Optional<ProcessEntity> findById = processDao.findById(taskId);
		ProcessEntity processEntity =  null;
		if(findById.isPresent()) {
			processEntity = findById.get();
		}
		if (processEntity == null) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_NOT_EXIST);
		}
		String status = processEntity.getStatus();
		if (StringUtils.equalsIgnoreCase(status, ProcessConstant.PROCESS_APPROVED)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_CANCLED_ALREAD);
		}
		
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("taskId", taskId);
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		// 流程日志信息
		ProcessLogEntity processLogEntity = new ProcessLogEntity();
		processLogEntity.setApiContentType(MediaType.APPLICATION_JSON_VALUE);
		processLogEntity.setApiMethod(HttpMethod.GET.toString());
		processLogEntity.setApiUrlParam(JSON.toJSONString(urlParams));
		processLogEntity.setApiUrl(removeUrl);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(removeUrl, HttpMethod.GET, entity, String.class,
					urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<Boolean> body = JSON.parseObject(obj, new TypeReference<ProcessMessage<Boolean>>() {
			});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					// 保存当前流程信息
					processEntity.setProcessId(taskId);
					processEntity.setStatus(ProcessConstant.PROCESS_DELETED);
					processEntity.setCnStatus(ProcessConstant.PROCESS_DELETED_CN);
					processDao.save(processEntity);
					// 记录日志
					processLogEntity.setIsRequest(true);
					processLogEntity.setApiResponse(obj);
					processLogEntity.setApiResponseStatus(body.getErrorCode());
					processLogDao.save(processLogEntity);
					return true;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessService=====Method：processRemove=====", e);
		}
		// 记录日志
		processLogEntity.setIsRequest(false);
		processLogDao.save(processLogEntity);
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE);

	}

	/**
	 * 流程发起
	 * 
	 * @return
	 */
	public String processStart(ProcessStartVO processStartVO, HttpServletRequest request) {
		String user = processStartVO.getUser();// 用户ID
		if (StringUtils.isBlank(user)) {
			String userID = SystemUtil.getLoginStaffUuid(request);
			if (StringUtils.isBlank(userID)) {
				throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
			}
			user = userID;
		}
		String comment = processStartVO.getComment();// 审批意见
		String attachment = processStartVO.getAttachment();// 审批附件地址
		String flowPath = processStartVO.getFlowPath();// 流程地址
		Map<String, String> data = processStartVO.getData();// 附加数据
		if (data == null) {
			data = new HashMap<>();
		}
		// 附加数据--部门ID（必填）
		// 发起、审核请求格式通用
		data.put("DepartmentId", departmentId);
		data.put("FormUrl", "www.baidu.com");
		data.put("ReadUrl", "www.sina.com");
		data.put("RecordUser", user);
		ProcessOperateRequest processOperateRequest = new ProcessOperateRequest();
		processOperateRequest.setUser(user);
		processOperateRequest.setResult(ProcessConstant.PROCESS_APPROVED); // 流程发起默认approved，否则将报错
		processOperateRequest.setAttachment(attachment);
		processOperateRequest.setComment(comment);
		processOperateRequest.setData(data);
		// step2：流程接口调用
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		headers.set("Pragma", "no-cache");// 项目ID

		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("flowPath", flowPath);
		HttpEntity<ProcessOperateRequest> entity = new HttpEntity<>(processOperateRequest, headers);

		// 流程日志信息
		ProcessLogEntity processLogEntity = new ProcessLogEntity();
		processLogEntity.setApiContentType(MediaType.APPLICATION_JSON_VALUE);
		processLogEntity.setApiMethod(HttpMethod.POST.toString());
		processLogEntity.setApiUrlParam(JSON.toJSONString(urlParams));
		processLogEntity.setApiBodyParam(JSON.toJSONString(processOperateRequest));
		processLogEntity.setApiUrl(startUrl);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(startUrl, HttpMethod.POST, entity, String.class,
					urlParams);
			String p1 = getForEntity.getBody();
			ProcessMessage<StartFlowData> body = JSON.parseObject(p1,
					new TypeReference<ProcessMessage<StartFlowData>>() {
					});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					StartFlowData result = body.getData();
					// step3：保存当前流程信息
					if (result != null) {
						String taskId = result.getTaskId();
						InfoData processInfo = getProcessInfo(taskId);
						if (processInfo != null) {
							ProcessEntity processEntity = new ProcessEntity();
							processEntity.setApplicant(processInfo.getApplicant());
							processEntity.setApplyTime(processInfo.getApplyAt());
							processEntity.setCompleteTime(processInfo.getCompleteAt());
							processEntity.setName(processInfo.getName());
							processEntity.setProcessId(taskId);
							processEntity.setReadUrl(processInfo.getReadUrl());
							processEntity.setRemark(processInfo.getDescription());
							processEntity.setStatus(processInfo.getState());
							processEntity.setCnStatus(processInfo.getChsState());
							processEntity.setProcessData(processInfo.getTaskData());
							processEntity.setAgent(processInfo.getAgent());
							processEntity.setInstCla(processInfo.getInstanceClass());
							processEntity.setOwnerSystem(processInfo.getOwnerSystem());
							processEntity.setFormOutSide(processInfo.getFromOutside());
							processEntity.setProcessOperateData(JSON.toJSONString(data));

							processDao.save(processEntity);
						}
						// step4:记录日志
						processLogEntity.setIsRequest(true);
						processLogEntity.setApiResponse(JSON.toJSONString(body));
						processLogEntity.setApiResponseStatus(body.getErrorCode());
						processLogDao.save(processLogEntity);
						return taskId;
					}
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessService=====Method：processStart=====", e);
		}

		// step4:记录日志
		processLogEntity.setIsRequest(false);
		processLogDao.save(processLogEntity);
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE);
	}

	/**
	 * 流程审批操作
	 * 
	 * @return
	 */
	public boolean processOperate(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		// step1：参数校验及处理
		String taskId = processOperateVO.getTaskId();// 流程ID
		String stepId = processOperateVO.getStepId();// 步骤ID
		String backStepId = processOperateVO.getBackStepId();// 退回到指定步骤ID
		if (StringUtils.isAnyBlank(taskId, stepId)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TASKID_AND_STEPID_IS_NOT_NULL);
		}
		String result = processOperateVO.getResult();// 审批动作
		if (StringUtils.isBlank(result) || !(StringUtils.equalsAny(result, ProcessConstant.PROCESS_APPROVED,
				ProcessConstant.PROCESS_REJECTED, ProcessConstant.PROCESS_PICKBACK))) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.RESULT_IS_NOT_NULL);
		}
		String user = processOperateVO.getUser();// 用户ID
		if (StringUtils.isBlank(user)) {
			String userID = SystemUtil.getLoginStaffUuid(request);
			if (StringUtils.isBlank(userID)) {
				throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
			}
			user = userID;
		}
		String comment = processOperateVO.getComment();// 审批意见
		String attachment = processOperateVO.getAttachment();// 审批附件地址
		String selectId = processOperateVO.getSelectId(); //海选uuid
		Map<String, String> data = processOperateVO.getData();// 附加数据
		if (data == null) {
			data = new HashMap<>();
		}
		// 发起、审核请求格式通用
		data.put("DepartmentId", departmentId);
		data.put("FormUrl", "www.baidu.com");
		data.put("ReadUrl", "www.sina.com");
		data.put("RecordUser", user);
		if(!StringUtils.isBlank(selectId) ) {
			data.put("workflow_selectedApprovedUser", selectId);
		}
// 		data.put("changeType", "1");
		// 附加数据--部门ID（必填）

		ProcessOperateRequest processOperateRequest = new ProcessOperateRequest();
		processOperateRequest.setUser(user);
		processOperateRequest.setResult(result);
		processOperateRequest.setAttachment(attachment);
		processOperateRequest.setComment(comment);
		processOperateRequest.setData(data);

		// step2：流程接口调用
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("taskId", taskId);
		urlParams.put("stepId", stepId);
		urlParams.put("backStepId", backStepId);

		HttpEntity<ProcessOperateRequest> entity = new HttpEntity<>(processOperateRequest, headers);

		// 流程日志信息
		ProcessLogEntity processLogEntity = new ProcessLogEntity();
		processLogEntity.setApiContentType(MediaType.APPLICATION_JSON_VALUE);
		processLogEntity.setApiMethod(HttpMethod.POST.toString());
		processLogEntity.setApiUrlParam(JSON.toJSONString(urlParams));
		processLogEntity.setApiBodyParam(JSON.toJSONString(processOperateRequest));
		processLogEntity.setApiUrl(processOperateUrl);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(processOperateUrl, HttpMethod.POST, entity,
					String.class, urlParams);
			String p1 = getForEntity.getBody();
			ProcessMessage<StartFlowData> body = JSON.parseObject(p1,
					new TypeReference<ProcessMessage<StartFlowData>>() {
					});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					// step3：保存当前流程信息，判断流程状态，如流程结束相应回调
					InfoData processInfo = getProcessInfo(taskId);
					CallBackService.callBack(taskId,stepId,token,processInfo, request);
					if (processInfo != null) {
						ProcessEntity processEntity = new ProcessEntity();
						processEntity.setApplicant(processInfo.getApplicant());
						processEntity.setApplyTime(processInfo.getApplyAt());
						processEntity.setCompleteTime(processInfo.getCompleteAt());
						processEntity.setName(processInfo.getName());
						processEntity.setProcessId(taskId);
						processEntity.setReadUrl(processInfo.getReadUrl());
						processEntity.setRemark(processInfo.getDescription());
						processEntity.setStatus(processInfo.getState());
						processEntity.setCnStatus(processInfo.getChsState());
						processEntity.setProcessData(processInfo.getTaskData());
						processEntity.setAgent(processInfo.getAgent());
						processEntity.setInstCla(processInfo.getInstanceClass());
						processEntity.setOwnerSystem(processInfo.getOwnerSystem());
						processEntity.setFormOutSide(processInfo.getFromOutside());
						processDao.save(processEntity);
					}
					// step4:记录日志
					processLogEntity.setIsRequest(true);
					processLogEntity.setApiResponse(JSON.toJSONString(body));
					processLogEntity.setApiResponseStatus(body.getErrorCode());
					processLogDao.save(processLogEntity);
					if (processInfo.getState() != ProcessConstant.PROCESS_STANDBY) {
						// do something
					}
					return true;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessService=====Method：processOperate=====", e);
		}
		// step4:记录日志
		processLogEntity.setIsRequest(false);
		processLogDao.save(processLogEntity);
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_PROCEED_FAILURE);
	}

	/**
	 * 流程审批通过操作
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean processApprove(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		ProcessEntity entity = getProcessLocal(processOperateVO.getTaskId());
		Map<String, String> parse = (Map<String, String>) JSON.parse(entity.getProcessOperateData());
		processOperateVO.setData(parse);
		processOperateVO.setResult(ProcessConstant.PROCESS_APPROVED);
		return processOperate(processOperateVO, request);

	}

	/**
	 * 流程审批拒绝操作
	 * 
	 * @return
	 */
	public boolean processReject(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		processOperateVO.setResult(ProcessConstant.PROCESS_REJECTED);
		return processOperate(processOperateVO, request);
	}

	/**
	 * 流程审批回退操作
	 * 
	 * @return
	 */
	public boolean processPickback(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		processOperateVO.setResult(ProcessConstant.PROCESS_PICKBACK);
		return processOperate(processOperateVO, request);
	}

	/**
	 * 获取流程信息
	 * 
	 * @param taskId
	 * @return
	 */
	public InfoData getProcessInfo(String taskId) {
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("taskId", taskId);

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(processInfoUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String p1 = getForEntity.getBody();
			ProcessMessage<InfoData> body = JSON.parseObject(p1, new TypeReference<ProcessMessage<InfoData>>() {
			});
			if (body != null) {
				return body.getData();
			}
		} catch (Exception e) {
			log.error("=====ProcessService=====Method：getProcessInfo=====", e);
		}
		return null;
	}

	/**
	 * 获取流程详情(数据库)
	 * 
	 * @param taskId
	 * @return
	 */
	public ProcessEntity getProcessLocal(String taskId) {
		Optional<ProcessEntity> findById = processDao.findById(taskId);

		ProcessEntity processEntity = null;
		if (findById.isPresent()) {
			processEntity = findById.get();
		}
		return processEntity;
	}
	

}
