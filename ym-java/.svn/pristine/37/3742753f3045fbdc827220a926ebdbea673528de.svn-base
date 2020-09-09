package com.lyht.business.process.service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.process.dto.request.ProcessSyncUserRequest;
import com.lyht.business.process.dto.response.ProcessResponse;
import com.lyht.system.dao.SysStaffDao;
import com.lyht.system.pojo.SysStaff;
import com.lyht.system.vo.SysStaffAcct;

import cn.hutool.core.lang.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessUserSyncService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SysStaffDao sysStaffDao;
	@Autowired
	private ProcessAuthorizationService processService;
	
	@Value("${iwind.process.project.id}")
	private String projectId;//项目ID
	@Value("${iwind.process.department.id}")
	private String departmentId;//部门ID
	@Value("${iwind.process.sync.user.url}")
	private String syncUserUrl;//用户同步接口地址

	/**
	 * 同步用户
	 */
	public boolean userSync() {
		//获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			return false;
		}
		//查询用户、账号信息
		List<SysStaffAcct> findAll = sysStaffDao.findAllByAcctAndStaff();
		if (findAll == null || findAll.isEmpty()) {
			return true;
		}
		String jsonString = JSON.toJSONString(findAll);
		List<SysStaff> sysStaffList = JSON.parseArray(jsonString, SysStaff.class);
		//同步
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		headers.set("_LoginProjectId", projectId);
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		List<ProcessSyncUserRequest> list = new ArrayList<>();
		for (int i = 0; i < findAll.size(); i ++) {
			SysStaffAcct sysStaffAcct = findAll.get(i);
			SysStaff sysStaff = sysStaffList.get(i);
			String name = sysStaffAcct.getStaffName();//用户姓名
			String uuid = sysStaffAcct.getUuid();//uuid
			String account = sysStaffAcct.getAccount();//账号
			String password = sysStaffAcct.getPassword();//密码
			if (StringUtils.isBlank(uuid)) {
				String newUuid = UUID.randomUUID().toString();
				sysStaff.setUuid(newUuid);
				uuid = newUuid;
			}
			ProcessSyncUserRequest processUser = new ProcessSyncUserRequest();
			processUser.setId(uuid);
			processUser.setName(name);
			processUser.setSpellName(account);
			processUser.setAccount(account);
			processUser.setPassword(password);
			processUser.setSysDepartmentId(departmentId);
			list.add(processUser);
		}
		
		sysStaffDao.saveAll(sysStaffList);
		
		HttpEntity<List<ProcessSyncUserRequest>> requestBody = new HttpEntity<>(list, headers);
		ResponseEntity<ProcessResponse> postForEntity = restTemplate.postForEntity(syncUserUrl, requestBody, ProcessResponse.class);
		ProcessResponse body = postForEntity.getBody();
		if (body != null) {
			Integer errorCode = body.getErrorCode();
			if (errorCode == 0) {
				return true;
			}
		}
		return false;
	}
	
}
