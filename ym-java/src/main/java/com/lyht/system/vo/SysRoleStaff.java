package com.lyht.system.vo;

/**
 * 角色及用户
 * @author hxl
 *
 */
public interface SysRoleStaff {
	
	//角色
	Integer getId();
    String getCode();
    String getStaffNm();
    String getRoleNm();
    Integer getFlag();
    String getUuid();
    //用户
    String getRoleUuid();
    String getStaffUuid();
    
}
