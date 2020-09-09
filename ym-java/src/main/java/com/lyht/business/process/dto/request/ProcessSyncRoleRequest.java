package com.lyht.business.process.dto.request;

import com.alibaba.fastjson.JSON;

/**
 * 岗位
 * @author hxl
 *
 */
public class ProcessSyncRoleRequest {

	// 岗位ID
	private String Id;
	// 岗位名称
	private String PostName;
	// 部门ID
	private String SysDepartmentId;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPostName() {
		return PostName;
	}
	public void setPostName(String postName) {
		PostName = postName;
	}
	public String getSysDepartmentId() {
		return SysDepartmentId;
	}
	public void setSysDepartmentId(String sysDepartmentId) {
		SysDepartmentId = sysDepartmentId;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
