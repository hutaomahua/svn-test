package com.lyht.business.process.service;

import com.lyht.business.process.dto.request.ProcessSyncRoleRequest;
import com.lyht.business.process.dto.response.ProcessResponse;
import com.lyht.system.dao.SysRoleDao;
import com.lyht.system.pojo.SysRole;

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
public class ProcessRoleSyncService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private ProcessAuthorizationService processService;
	
	@Value("${iwind.process.project.id}")
	private String projectId;//项目ID
	@Value("${iwind.process.department.id}")
	private String departmentId;//部门ID
	@Value("${iwind.process.sync.post.url}")
	private String syncPostUrl;//岗位同步接口地址

	/**
	 * 同步角色
	 */
	public boolean roleSync() {
		//获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			return false;
		}
		//查询角色信息
		List<SysRole> findAll = sysRoleDao.findAll();
		if (findAll == null || findAll.isEmpty()) {
			return true;
		}
		//同步
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		headers.set("_LoginProjectId", projectId);
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		
		List<ProcessSyncRoleRequest> list = new ArrayList<>();
		for (SysRole sysRole : findAll) {
			String name = sysRole.getName();
			String uuid = sysRole.getUuid();
			if (StringUtils.isBlank(uuid)) {
				String newUuid = UUID.randomUUID().toString();
				sysRole.setUuid(newUuid);
				uuid = newUuid;
			}
			ProcessSyncRoleRequest processRole = new ProcessSyncRoleRequest();
			processRole.setSysDepartmentId(departmentId);
			processRole.setId(uuid);
			processRole.setPostName(name);
			list.add(processRole);
		}
		
		sysRoleDao.saveAll(findAll);
		
		HttpEntity<List<ProcessSyncRoleRequest>> requestBody = new HttpEntity<>(list, headers);
		ResponseEntity<ProcessResponse> postForEntity = restTemplate.postForEntity(syncPostUrl, requestBody, ProcessResponse.class);
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
