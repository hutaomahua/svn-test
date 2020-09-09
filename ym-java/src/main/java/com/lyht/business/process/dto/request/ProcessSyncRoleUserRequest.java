package com.lyht.business.process.dto.request;

import com.alibaba.fastjson.JSON;

/**
 * 岗位与用户关联关系
 * 
 * @author hxl
 *
 */
public class ProcessSyncRoleUserRequest {

	// ID
	private String Id;
	// 岗位ID
	private String SysDepartmentPostId;
	// 用户ID
	private String SysUserId;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getSysDepartmentPostId() {
		return SysDepartmentPostId;
	}

	public void setSysDepartmentPostId(String sysDepartmentPostId) {
		SysDepartmentPostId = sysDepartmentPostId;
	}

	public String getSysUserId() {
		return SysUserId;
	}

	public void setSysUserId(String sysUserId) {
		SysUserId = sysUserId;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
