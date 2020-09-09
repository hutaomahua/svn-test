package com.lyht.business.process.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.process.common.constant.ProcessConstant;
import com.lyht.business.process.common.enums.LyhtProcessExceptionEnums;
import com.lyht.business.process.dao.ProcessDao;
import com.lyht.business.process.dto.response.ProcessInquireResponse;
import com.lyht.business.process.dto.response.ProcessMessage;
import com.lyht.business.process.dto.response.ProcessQuantityGroupResponse;
import com.lyht.business.process.dto.response.model.BackStepData;
import com.lyht.business.process.dto.response.model.InfoData;
import com.lyht.business.process.dto.response.model.InquireData;
import com.lyht.business.process.entity.ProcessEntity;
import com.lyht.business.process.vo.ProcessCountVO;
import com.lyht.business.pub.dao.PubFilesDao;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.system.dao.SysStaffDao;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.SystemUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HuangTianhao
 * @date 2019年12月15日
 */
@Slf4j
@Service
public class ProcessInquireService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private SysStaffDao sysStaffDao;
	@Autowired
	private PubFilesDao pubFilesDao;
	@Autowired
	private ProcessDao processDao;

	@Value("${iwind.process.getstandby.url}")
	private String standByUrl;// 查询用户待办流程接口
	@Value("${iwind.process.gethandled.url}")
	private String getHandledUrl;// 查询用户处理过的流程接口
	@Value("${iwind.process.getjoin.url}")
	private String getJoinUrl;// 查询用户所有提交过的流程接口
	@Value("${iwind.process.getStatus.url}")
	private String getStatusUrl;// 根据流程ID得到流程审批步骤信息
	@Value("${iwind.process.project.id}")
	private String projectId;// 查询用户所有提交过的流程接口
	@Value("${iwind.process.getBackSteps.url}")
	private String getBackStepsUrl;// 查询所有可以回退的步骤
	@Value("${iwind.process.getCurStep.url}")
	private String getCurStepUrl;// 查询当前审批步骤信息
	@Value("${iwind.process.getStepInfo.url}")
	private String getStepInfoUrl;// 查询某步骤信息
	@Value("${iwind.process.get.url}")
	private String processInfoUrl;// 获取流程信息
	@Value("${iwind.process.getQuantity.url}")
	private String getQuantityUrl;// 通过用户id获取(流程名称丶待办数量丶已通过数量丶已拒绝数量丶已提交数量),按流程名称分组
	@Value("${iwind.process.getStandbyTaskCount.url}")
	private String getStandbyTaskCountUrl;// 通过用户id获取待办数量
	@Value("${iwind.process.getHandledTaskCount.url}")
	private String getHandledTaskCountUrl;// 通过用户id获取已办数量
	@Value("${iwind.process.getMyJoinTaskCount.url}")
	private String getMyJoinTaskCountUrl;// 通过用户id获取已提交数量

	@Autowired
	private ProcessAuthorizationService processService;

	/**
	 * 接口返回用户待办流程
	 * 
	 * @return
	 */
	public LyhtResultBody<List<InquireData>> processGetStandBy(String userId, String taskName, LyhtPageVO vo,
			HttpServletRequest request) {
		String url = standByUrl;
		return processGet(userId, taskName, vo, request, url);

	}

	/**
	 * 接口返回用户处理过的流程
	 * 
	 * @return
	 */
	public LyhtResultBody<List<InquireData>> processGetJoin(String userId, String taskName, LyhtPageVO vo,
			HttpServletRequest request) {
		String url = getJoinUrl;
		return processGet(userId, taskName, vo, request, url);
	}

	/**
	 * 接口返回用户所有提交过的流程
	 * 
	 * @return
	 */
	public LyhtResultBody<List<InquireData>> processGetHandled(String userId, String taskName, LyhtPageVO vo,
			HttpServletRequest request) {
		String url = getHandledUrl;
		return processGet(userId, taskName, vo, request, url);
	}

	/**
	 * 接口返回用户实现方法
	 * 
	 * @return
	 */
	public LyhtResultBody<List<InquireData>> processGet(String id, String taskName, LyhtPageVO vo,
			HttpServletRequest request, String url) {
		// 分页格式兼容
		Integer pageIndex = vo.getCurrent();
		Integer pageSize = vo.getPageSize();
		if (pageIndex == null) {
			pageIndex = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		// 检查用户名
		if (StringUtils.isBlank(id)) {
			String userID = SystemUtil.getLoginStaffUuid(request);
			if (StringUtils.isBlank(userID)) {
				throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
			}
			id = userID;
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部8
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("filterContent", taskName); // 模糊查询流程名
		urlParams.put("userId", id);
		urlParams.put("pageIndex", pageIndex);
		urlParams.put("pageSize", pageSize);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class,
					urlParams);
			String obj = getForEntity.getBody();
			ProcessInquireResponse<List<InquireData>> body = JSON.parseObject(obj,
					new TypeReference<ProcessInquireResponse<List<InquireData>>>() {
					});
			if (body != null) {
				LyhtResultBody<List<InquireData>> result = new LyhtResultBody<>();
				Boolean success = body.getSuccess();
				if (success) {
					List<InquireData> list = body.getData();
					// 返回显示自定义name(8-28流程平台配置表单描述后，discription将返回配置字段，不再需要本地处理)
//					for (int i = 0; i < list.size(); i++) {
//						list.set(i, customData(list.get(i)));
//					}
					result.setList(list);
					LyhtPageVO pageVO = new LyhtPageVO(body.getPage() - 1, body.getPages(), body.getSize(),
							Long.parseLong(String.valueOf(body.getTotal())), null);
					result.setPagination(pageVO);
					return result;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processGet=====", e);
		}
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE);
	}

	/**
	 * 自定义返回数据
	 * 
	 * @return
	 */
	public InquireData customData(InquireData entity) {
		String name = entity.getName();
		try {
			ProcessEntity info = getProcessLocal(entity.getTaskId());
			String data = info.getProcessOperateData();
			// 替换name
			String regex = "(?<=\"name\":\").*?(?=\")";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(data);
			while (matcher.find()) {
				System.out.println(matcher.group().trim());
				if (StringUtils.isNotEmpty(matcher.group().trim())) {
					name = matcher.group().trim();
				}
			}
			// 替换name 结束
		} catch (Exception e) {
		} finally {
			if (entity != null) {
				entity.setDescription(name);
			}
		}
		return entity;
	}

	/**
	 * 根据流程ID得到流程审批步骤信息
	 * 
	 * @return
	 */
	public LyhtResultBody<List<BackStepData>> processGetByTaskId(String taskId, HttpServletRequest request) {
		String id = taskId;
		// 检查流程名
		if (StringUtils.isBlank(id)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TASKID_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部8
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("taskId", id);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getStatusUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<List<BackStepData>> body = JSON.parseObject(obj,
					new TypeReference<ProcessMessage<List<BackStepData>>>() {
					});
			if (body != null) {
				LyhtResultBody<List<BackStepData>> result = new LyhtResultBody<>();
				Boolean success = body.getSuccess();
				if (success) {
					List<BackStepData> list = body.getData();
					// 处理多人审批一人通过时的重复返回数据;
					list = dataForm(list);
					result.setList(list);
					return result;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processGetByTaskId=====", e);
		}
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE);
	}

	public List<BackStepData> dataForm(List<BackStepData> list) {
		// 处理多人审批一人通过时的重复返回数据;
		// 根据返回的uuid 查询对应员工姓名、职位，覆盖原 Modifyuser、Departmentid 字段
		List<BackStepData> newList = new ArrayList<>();
		String preName = "";
		for (int i = 0; i < list.size(); i++) {
			BackStepData data = list.get(i);
			String curName = data.getName();
			if (preName.equals(curName)) {
				BackStepData preData = newList.get(newList.size() - 1);
				preData.setModifyUser("");
				preData.setDepartmentId("");
				preData.setModifyTime("");
			} else {
				if (i == list.size() - 1 && !data.getState().equals(ProcessConstant.PROCESS_STANDBY)) {
					data.setModifyUser("");
					data.setDepartmentId("");
					data.setModifyTime("");
				} else {
					SysStaff staff = sysStaffDao.findByUuid(data.getOwner());
					if (staff != null) {
						data.setModifyUser(staff.getStaffName());
						data.setDepartmentId(staff.getStaffPosition());
					} else {
						data.setModifyUser("");
						data.setDepartmentId("");
					}

				}
				newList.add(data);
			}
			preName = curName;
		}
		return newList;
	}

	/**
	 * 根据taskId和stepId查询可以回退的步骤
	 * 
	 * @return
	 */
	public LyhtResultBody<List<BackStepData>> processBackSteps(String taskId, String stepId,
			HttpServletRequest request) {
		String id = taskId;
		// 检查流程名
		if (StringUtils.isBlank(id) || StringUtils.isBlank(stepId)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TASKID_AND_STEPID_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部8
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("taskId", id);
		urlParams.put("activitieStepId", stepId);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getBackStepsUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<List<BackStepData>> body = JSON.parseObject(obj,
					new TypeReference<ProcessMessage<List<BackStepData>>>() {
					});
			if (body != null) {
				LyhtResultBody<List<BackStepData>> result = new LyhtResultBody<>();
				Boolean success = body.getSuccess();
				if (success) {
					result.setList(body.getData());
					return result;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processBackSteps=====", e);
		}
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE);
	}

	/**
	 * 查询当前审批步骤信息
	 * 
	 * @return
	 */
	public LyhtResultBody<List<BackStepData>> processCurStep(String taskId, HttpServletRequest request) {
		String id = taskId;
		// 检查流程名
		if (StringUtils.isBlank(id)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TASKID_AND_STEPID_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部8
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("taskId", id);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getCurStepUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<List<BackStepData>> body = JSON.parseObject(obj,
					new TypeReference<ProcessMessage<List<BackStepData>>>() {
					});

			List<BackStepData> list = dataForm(body.getData());
			if (body != null) {
				LyhtResultBody<List<BackStepData>> result = new LyhtResultBody<>();
				Boolean success = body.getSuccess();
				if (success) {
					result.setList(list);
					return result;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processCurStep=====", e);
		}
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE);
	}

	/**
	 * 查询某审批步骤信息
	 * 
	 * @return
	 */
	public LyhtResultBody<BackStepData> getStepInfo(String stepId, HttpServletRequest request) {
		String id = stepId;
		// 检查流程名
		if (StringUtils.isBlank(id)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TASKID_AND_STEPID_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部8
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("stepId", id);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getStepInfoUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<BackStepData> body = JSON.parseObject(obj,
					new TypeReference<ProcessMessage<BackStepData>>() {
					});
			BackStepData data = body.getData();
			SysStaff staff = sysStaffDao.findByUuid(data.getHandler());
			if (staff != null) {
				data.setModifyUser(staff.getStaffName());
				data.setDepartmentId(staff.getStaffPosition());
			} else {
				data.setModifyUser("");
				data.setDepartmentId("");
			}
			body.setData(data);
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					return new LyhtResultBody<>(data);
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：getStepInfo=====", e);
		}
		throw new LyhtRuntimeException(LyhtProcessExceptionEnums.PROCESS_FAILURE);
	}

	/**
	 * 取得文件实体
	 * 
	 * @param nms 内码集合(逗号分隔)
	 * @return
	 */
	public List<PubFilesEntity> getFileEntities(String nms) {
		String[] list = nms.split(",");
		List<PubFilesEntity> fileList = new ArrayList<>();
		for (String nm : list) {
			Optional<PubFilesEntity> entity = pubFilesDao.findByNm(nm);
			fileList.add(entity.get());
		}
		return fileList;
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

	/**
	 * 通过用户id获取(流程名称丶待办数量丶已通过数量丶已拒绝数量丶已提交数量),按流程名称分组
	 * 
	 * @param request
	 * @return
	 */
	public List<ProcessQuantityGroupResponse> processGetQuantityByUserId(HttpServletRequest request) {
		// 获取用户id
		String userID = SystemUtil.getLoginStaffUuid(request);
//		userID = "7c07eb74-5410-4a7d-bcca-2f7931233871";
		if (StringUtils.isBlank(userID)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("userId", userID);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getQuantityUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<List<ProcessQuantityGroupResponse>> body = JSON.parseObject(obj,
					new TypeReference<ProcessMessage<List<ProcessQuantityGroupResponse>>>() {
					});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					List<ProcessQuantityGroupResponse> list = body.getData();
					return list;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processGetByTaskId=====", e);
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.CONNECTION_TIME_OUT);
		}
		return null;
	}

	/**
	 * 通过用户id获取待办数量
	 * 
	 * @param request
	 * @return
	 */
	public Long processGetStandbyTaskCountByUserId(HttpServletRequest request) {
		// 获取用户id
		String userID = SystemUtil.getLoginStaffUuid(request);
//		userID = "7c07eb74-5410-4a7d-bcca-2f7931233871";
		if (StringUtils.isBlank(userID)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("userId", userID);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getStandbyTaskCountUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<Long> body = JSON.parseObject(obj, new TypeReference<ProcessMessage<Long>>() {
			});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					Long data = body.getData();
					return data;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processGetStandbyTaskCountByUserId=====", e);
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.CONNECTION_TIME_OUT);
		}
		return null;
	}

	/**
	 * 通过用户id获取已办数量
	 * 
	 * @param request
	 * @return
	 */
	public Long processGetHandledTaskCountByUserId(HttpServletRequest request) {
		// 获取用户id
		String userID = SystemUtil.getLoginStaffUuid(request);
//		userID = "7c07eb74-5410-4a7d-bcca-2f7931233871";
		if (StringUtils.isBlank(userID)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("userId", userID);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getHandledTaskCountUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<Long> body = JSON.parseObject(obj, new TypeReference<ProcessMessage<Long>>() {
			});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					Long data = body.getData();
					return data;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processGetHandledTaskCountByUserId=====", e);
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.CONNECTION_TIME_OUT);
		}
		return null;
	}

	/**
	 * 通过用户id获取已提交数量
	 * 
	 * @param request
	 * @return
	 */
	public Long processGetMyJoinTaskCountByUserId(HttpServletRequest request) {
		// 获取用户id
		String userID = SystemUtil.getLoginStaffUuid(request);
//		userID = "7c07eb74-5410-4a7d-bcca-2f7931233871";
		if (StringUtils.isBlank(userID)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.USER_IS_NOT_NULL);
		}
		// 获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.TOKEN_CREATE_FAILURE);
		}
		// 设置头部
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);// token
		headers.set("_LoginProjectId", projectId);// 项目ID
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		// 设置url参数
		Map<String, Object> urlParams = new HashMap<>();
		urlParams.put("userId", userID);
		// 请求接口
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		try {
			ResponseEntity<String> getForEntity = restTemplate.exchange(getMyJoinTaskCountUrl, HttpMethod.GET, entity,
					String.class, urlParams);
			String obj = getForEntity.getBody();
			ProcessMessage<Long> body = JSON.parseObject(obj, new TypeReference<ProcessMessage<Long>>() {
			});
			if (body != null) {
				Boolean success = body.getSuccess();
				if (success) {
					Long data = body.getData();
					return data;
				}
			}
		} catch (Exception e) {
			log.error("=====ProcessInquireService=====Method：processGetMyJoinTaskCountByUserId=====", e);
			throw new LyhtRuntimeException(LyhtProcessExceptionEnums.CONNECTION_TIME_OUT);
		}
		return null;
	}

	/**
	 * 通过用户id获取待办丶已办丶已提交 数量
	 * 
	 * @param request
	 * @return
	 */
	public ProcessCountVO processCountByUserId(HttpServletRequest request) {
		Long standby = processGetStandbyTaskCountByUserId(request);
		Long handled = processGetHandledTaskCountByUserId(request);
		Long submit = processGetMyJoinTaskCountByUserId(request);
		ProcessCountVO processCountVO = new ProcessCountVO();
		processCountVO.setStandby(standby);
		processCountVO.setHandled(handled);
		processCountVO.setSubmit(submit);
		return processCountVO;
	}

}
