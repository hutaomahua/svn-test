package com.lyht.business.process.dto.request;

import com.alibaba.fastjson.JSON;

/**
 * 用户
 * @author hxl
 *
 */
public class ProcessSyncUserRequest {

	// 用户ID
	private String Id;
	// 用户名称
	private String Name;
	// 拼音简码
	private String SpellName;
	// 登录账号
	private String Account;
	// 用户密码
	private String Password;
	// 部门ID
	private String SysDepartmentId;
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSpellName() {
		return SpellName;
	}

	public void setSpellName(String spellName) {
		SpellName = spellName;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
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
