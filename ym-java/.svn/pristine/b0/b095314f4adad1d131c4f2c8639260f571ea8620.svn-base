package com.lyht.business.process.service;

import com.alibaba.fastjson.JSON;
import com.lyht.business.process.dto.request.ProcessSyncRoleUserRequest;
import com.lyht.business.process.dto.response.ProcessResponse;
import com.lyht.system.dao.SysRoleStaffRefDao;
import com.lyht.system.pojo.SysRoleStaffRef;
import com.lyht.system.vo.SysRoleStaff;

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
public class ProcessRoleUserSyncService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SysRoleStaffRefDao sysRoleStaffRefDao;
	@Autowired
	private ProcessAuthorizationService processService;
	
	@Value("${iwind.process.project.id}")
	private String projectId;//项目ID
	@Value("${iwind.process.department.id}")
	private String departmentId;//部门ID
	@Value("${iwind.process.sync.post.user.url}")
	private String syncPostUserUrl;//岗位与用户关联关系同步接口地址

	/**
	 * 同步岗位与用户关联关系
	 */
	public boolean roleUserSync() {
		//获取token
		String token = processService.getToken();
		if (StringUtils.isBlank(token)) {
			return false;
		}
		//查询岗位与用户关联关系
		List<SysRoleStaff> findAllByRoleAndStaff = sysRoleStaffRefDao.findAllByRoleAndStaff();
		if (findAllByRoleAndStaff == null || findAllByRoleAndStaff.isEmpty()) {
			return true;
		}
		String jsonString = JSON.toJSONString(findAllByRoleAndStaff);
		List<SysRoleStaffRef> sysRoleStaffRefList = JSON.parseArray(jsonString, SysRoleStaffRef.class);
		//同步
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		headers.set("_LoginProjectId", projectId);
		headers.set("_TenantId", projectId);// 租户ID 5-28更新
		
		List<ProcessSyncRoleUserRequest> list = new ArrayList<>();
		for (int i = 0; i < findAllByRoleAndStaff.size(); i ++) {
			SysRoleStaff sysRoleStaff = findAllByRoleAndStaff.get(i);
			SysRoleStaffRef sysRoleStaffRef = sysRoleStaffRefList.get(i);
			String roleUuid = sysRoleStaff.getRoleUuid();//岗位ID
			String staffUuid = sysRoleStaff.getStaffUuid();//用户ID
			if (StringUtils.isNoneBlank(roleUuid, staffUuid)) {
				String uuid = sysRoleStaff.getUuid();//uuid
				if (StringUtils.isBlank(uuid)) {
					String newUuid = UUID.randomUUID().toString();
					sysRoleStaffRef.setUuid(newUuid);
					uuid = newUuid;
				}
				ProcessSyncRoleUserRequest processRoleUser = new ProcessSyncRoleUserRequest();
				processRoleUser.setId(uuid);
				processRoleUser.setSysDepartmentPostId(roleUuid);
				processRoleUser.setSysUserId(staffUuid);
				list.add(processRoleUser);
			}
		}
		if (list == null || list.isEmpty()) {
			return true;
		}
		
		sysRoleStaffRefDao.saveAll(sysRoleStaffRefList);
		
		HttpEntity<List<ProcessSyncRoleUserRequest>> requestBody = new HttpEntity<>(list, headers);
		ResponseEntity<ProcessResponse> postForEntity = restTemplate.postForEntity(syncPostUserUrl, requestBody, ProcessResponse.class);
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
