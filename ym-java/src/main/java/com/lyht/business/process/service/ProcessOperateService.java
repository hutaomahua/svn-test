package com.lyht.business.process.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.household.common.constant.AbmHouseholdConst;
import com.lyht.business.abm.household.dao.AbmSplitHouseholdDao;
import com.lyht.business.abm.household.vo.AbmSplitHouseholdProcessVO;
import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.removal.service.AbmOwnerService;
import com.lyht.business.process.common.constant.ProcessConstant;
import com.lyht.business.process.common.enums.LyhtProcessExceptionEnums;
import com.lyht.business.process.dao.ProcessDao;
import com.lyht.business.process.dao.ProcessLogDao;
import com.lyht.business.process.dto.request.ProcessOperateRequest;
import com.lyht.business.process.dto.response.ProcessMessage;
import com.lyht.business.process.dto.response.ProcessSelectResponse;
import com.lyht.business.process.dto.response.model.InfoData;
import com.lyht.business.process.dto.response.model.StartFlowData;
import com.lyht.business.process.entity.ProcessEntity;
import com.lyht.business.process.entity.ProcessLogEntity;
import com.lyht.business.process.vo.ProcessOperateVO;
import com.lyht.system.service.SysRoleService;
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
public class ProcessOperateService {
	private Logger log = LoggerFactory.getLogger(ProcessService.class);

	@Autowired
	private AbmSplitHouseholdDao abmSplitHouseholdDao;

	@Autowired
	private AbmOwnerService abmOwnerService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ProcessDao processDao;
	
	@Autowired
	private ProcessLogDao processLogDao;

	@Autowired  
	private SysRoleService sysRoleService;
	
	@Autowired
	private ProcessAuthorizationService processService;
	
	@Autowired
	private ProcessInquireService processInquireService;
	
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
	private String removeUrl;// 取消流程URL

	@Value("${iwind.process.get.url}")
	private String processInfoUrl;// 获取流程信息


	/**
	 * 流程发起
	 * 
	 * @return
	 */
	public String processStart(ProcessOperateVO processOperateVO, HttpServletRequest request) {
		Map<String, Object> urlParams = new HashMap<>();//仅适配公共方法
		String flowPath = processOperateVO.getFlowPath();
		urlParams.put("flowPath", flowPath);
		ProcessLogEntity processLogEntity = new ProcessLogEntity();
		HttpEntity<ProcessOperateRequest> entity = Operate(processOperateVO,"Approved",urlParams,request,processLogEntity);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(startUrl, HttpMethod.POST, entity, String.class,
					urlParams);
			String p1 = getForEntity.getBody();
			ProcessMessage<StartFlowData> body = JSON.parseObject(p1,
					new TypeReference<ProcessMessage<StartFlowData>>() {
					});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success != null && success) {
					StartFlowData result = body.getData();
					// step3：保存当前流程信息
					if (result != null) {
						String taskId = result.getTaskId();
						saveInfo(taskId,entity.getBody().getData());
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
		ProcessLogEntity processLogEntity = new ProcessLogEntity();
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
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("taskId", taskId);
		urlParams.put("stepId", stepId);
		urlParams.put("backStepId", backStepId);
		HttpEntity<ProcessOperateRequest> entity = Operate(processOperateVO,result,urlParams,request,processLogEntity);
		//审批前回调
		CallBackService.callBack(taskId,stepId,entity.getHeaders().getFirst("token"),
				processInquireService.getProcessInfo(taskId), request);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(processOperateUrl, HttpMethod.POST, entity,
					String.class, urlParams);
			String p1 = getForEntity.getBody();
			ProcessMessage<StartFlowData> body = JSON.parseObject(p1,
					new TypeReference<ProcessMessage<StartFlowData>>() {
					});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success != null && success) {
					// step3：保存当前流程信息，开启回调接口
					saveInfo(taskId,entity.getBody().getData());
					//审批后回调
					CallBackService.callBack(taskId,stepId,entity.getHeaders().getFirst("token"),processInquireService.getProcessInfo(taskId), request);
					// step4:记录日志
					processLogEntity.setIsRequest(true);
					processLogEntity.setApiResponse(JSON.toJSONString(body));
					processLogEntity.setApiResponseStatus(body.getErrorCode());
					processLogDao.save(processLogEntity);
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
		ProcessEntity entity = processInquireService.getProcessLocal(processOperateVO.getTaskId());
		if(entity!=null) {
			Map<String, String> parse = (Map<String, String>) JSON.parse(entity.getProcessOperateData());
			processOperateVO.setData(parse);
		}
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
	 * 流程取消
	 * 
	 * @return
	 */
	public boolean processRemove(String taskId, HttpServletRequest request) {
		ProcessLogEntity processLogEntity = new ProcessLogEntity();
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
		processLogEntity.setApiContentType(MediaType.APPLICATION_JSON_VALUE);
		processLogEntity.setApiMethod(HttpMethod.GET.toString());
		processLogEntity.setApiUrlParam(JSON.toJSONString(urlParams));
		processLogEntity.setApiUrl(removeUrl);
		String msg = null;
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(removeUrl, HttpMethod.GET, entity, String.class,
					urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<Boolean> body = JSON.parseObject(obj, new TypeReference<ProcessMessage<Boolean>>() {
			});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					//分户取消
					saveHouseholdStatus(taskId);
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
			msg = e.getMessage();
		}
		// 记录日志
		processLogEntity.setIsRequest(false);
		processLogDao.save(processLogEntity);
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE + "---" + msg);
	}

	public void saveHouseholdStatus(String processId){
		//根据流程ID查看分户-户主内码
		String ownerNm = abmSplitHouseholdDao.queryOwnerNmByProcessId(processId);
		if(null != ownerNm){
			//根据户主内码查询户主信息
			AbmOwnerEntity abmOwnerEntity = abmOwnerService.findByNm(ownerNm);
			if(null != abmOwnerEntity){
				//将分户状态设置为暂存
				abmOwnerEntity.setSplitHouseholdState(AbmHouseholdConst.SPLIT_STATE_STORAGE);
				abmOwnerService.save(abmOwnerEntity);
			}
		}
	}

	/**
	 * 返回海选列表
	 * 
	 * @return
	 */
	public ProcessSelectResponse selectList(ProcessOperateVO processOperateVO, HttpServletRequest request){
		//ResponseEntity<String> getForEntity;
		ProcessLogEntity processLogEntity;
		//若发起流程时需海选
		if(!StringUtils.isBlank(processOperateVO.getFlowPath())) {
			Map<String, Object> urlParams = new HashMap<>();
			String flowPath = processOperateVO.getFlowPath();
			urlParams.put("flowPath", flowPath);
			processLogEntity = new ProcessLogEntity();
			HttpEntity<ProcessOperateRequest> entity = Operate(processOperateVO,"Approved",urlParams,request,processLogEntity);
			try {
				ResponseEntity<String> getForEntity = restTemplate.exchange(startUrl, HttpMethod.POST, entity, String.class,
						urlParams);
				String p = getForEntity.getBody();
				ProcessMessage<StartFlowData> body = JSON.parseObject(p,
						new TypeReference<ProcessMessage<StartFlowData>>() {
						});
				if(body.getErrorCode()==600) {
					String msg = body.getMessage();
					ProcessSelectResponse res = new ProcessSelectResponse();
				    String[] handle = msg.split("&");
				    res.setStepName(handle[1]);
				    res.setNumble(Integer.parseInt(handle[5]));
				    String deptNm = processDao.findNmByUuid(handle[2]);
					//Optional<SysStaff> findByUuid = processDao.findByUuid(handle[2]);
				    res.setList(sysRoleService.findStaffsByRoleNm(deptNm));
					// step4:记录日志
					processLogEntity.setIsRequest(false);
					processLogDao.save(processLogEntity);
					return res;
				}
			} catch (Exception e) {
				log.error("=====ProcessService=====Method：processStart=====", e);
			}
		}else {  //下一步审批需要海选
			processLogEntity = new ProcessLogEntity();
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
			Map<String, Object> urlParams = new HashMap<>();
			urlParams.put("taskId", taskId);
			urlParams.put("stepId", stepId);
			urlParams.put("backStepId", backStepId);
			HttpEntity<ProcessOperateRequest> entity = Operate(processOperateVO,result,urlParams,request,processLogEntity);
			try {
				ResponseEntity<String> getForEntity = restTemplate.exchange(processOperateUrl, HttpMethod.POST, entity,
						String.class, urlParams);
				String p = getForEntity.getBody();
				ProcessMessage<StartFlowData> body = JSON.parseObject(p,
						new TypeReference<ProcessMessage<StartFlowData>>() {
						});
				if(body.getErrorCode()==600) {
					String msg = body.getMessage();
					ProcessSelectResponse res = new ProcessSelectResponse();
				    String[] handle = msg.split("&");
				    res.setStepName(handle[1]);
				    res.setNumble(Integer.parseInt(handle[5]));
				    String deptNm = processDao.findNmByUuid(handle[2]);
				    res.setList(sysRoleService.findStaffsByRoleNm(deptNm));
					// step4:记录日志
					processLogEntity.setIsRequest(false);
					processLogDao.save(processLogEntity);
					return res;
				}
			} catch (Exception e) {
				log.error("=====ProcessService=====Method：processOperate=====", e);
			}
		}		
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_PROCEED_FAILURE);	
	}

	/**
	 * 流程公共逻辑
	 * 
	 * @return
	 */
	public HttpEntity<ProcessOperateRequest> Operate(ProcessOperateVO processOperateVO, String result,Map<String, Object> urlParams,HttpServletRequest request,ProcessLogEntity processLogEntity) {
		String user = processOperateVO.getUser();
		// 获取登录用户ID
		if (StringUtils.isBlank(user)) {
			String userID = SystemUtil.getLoginStaffUuid(request);
			if (StringUtils.isBlank(userID)) {
				throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
			}
			user = userID;
		}	
		//适配bpm引擎接口请求格式要求
		Map<String, String> data = processOperateVO.getData();// 附加数据
		if (data == null) {
			data = new HashMap<>();
		}
		data.put("departmentId", departmentId);
		data.put("FormUrl", "www.baidu.com");
		data.put("ReadUrl", "www.sina.com");
		data.put("recordUser", user);
		//获取VO数据
		String comment = processOperateVO.getComment();// 审批意见
		String attachment = processOperateVO.getAttachment();// 审批附件地址
		String selectId = processOperateVO.getSelectId(); //海选uuid
		ProcessOperateRequest processOperateRequest = new ProcessOperateRequest();
		processOperateRequest.setUser(user);
		processOperateRequest.setResult(result); //配置操作结果
		processOperateRequest.setAttachment(attachment);
		processOperateRequest.setComment(comment);
		processOperateRequest.setData(data);
		//如配置海选，保存海选id
		if(!StringUtils.isBlank(selectId) ) {
			data.put("workflow_selectedApprovedUser", selectId);
		}
		// 获取token拼接请求头
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 记录流程日志信息
		processLogEntity.setApiContentType(MediaType.APPLICATION_JSON_VALUE);
		processLogEntity.setApiMethod(HttpMethod.POST.toString());
		processLogEntity.setApiUrlParam(JSON.toJSONString(urlParams));
		processLogEntity.setApiBodyParam(JSON.toJSONString(processOperateRequest));
		processLogEntity.setApiUrl(processOperateUrl);	
		processOperateRequest.setData(data);
		return new HttpEntity<>(processOperateRequest, headers);
	}
	
	/**
	 * 保存流程日志
	 * 
	 * @return
	 */
	public void saveInfo(String taskId,Map<String,String> data){
		// step3：保存当前流程信息
			InfoData processInfo = processInquireService.getProcessInfo(taskId);
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
	}
		
//	/**
//	 * 获取流程信息
//	 * 
//	 * @param taskId
//	 * @return
//	 */
//	public InfoData getProcessInfo(String taskId) {
//		// 获取token
//		String token = processService.getToken();
//		if (StringUtils.isBlank(token)) {
//			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
//		}
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization", "Bearer " + token);// token
//		headers.set("_LoginProjectId", projectId);// 项目ID
//
//		Map<String, Object> urlParams = new HashMap<>();
//		urlParams.put("taskId", taskId);
//
//		HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//		try {
//			ResponseEntity<String> getForEntity = restTemplate.exchange(processInfoUrl, HttpMethod.GET, entity,
//					String.class, urlParams);
//			String p1 = getForEntity.getBody();
//			ProcessMessage<InfoData> body = JSON.parseObject(p1, new TypeReference<ProcessMessage<InfoData>>() {
//			});
//			if (body != null) {
//				return body.getData();
//			}
//		} catch (Exception e) {
//			log.error("=====ProcessService=====Method：getProcessInfo=====", e);
//		}
//		return null;
//	}
//
//	/**
//	 * 获取流程详情(数据库)
//	 * 
//	 * @param taskId
//	 * @return
//	 */
//	public ProcessEntity getProcessLocal(String taskId) {
//		Optional<ProcessEntity> findById = processDao.findById(taskId);
//
//		ProcessEntity processEntity = null;
//		if (findById.isPresent()) {
//			processEntity = findById.get();
//		}
//		return processEntity;
//	}
	

}
