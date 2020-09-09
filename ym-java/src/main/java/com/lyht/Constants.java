package com.lyht;

public class Constants {
	/**
	 * 默认的分页数据尺寸
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	/**
	 * 用户登录信息存于Session中的账户信息，com.lyht.impl.SysAcctBean
	 */
	public static final String SESSION_ACCT = "session_acct";
	/**
	 * 人员信息
	 */
	public static final String SESSION_STAFF = "session_staff";
	/**
	 * 人员部门信息
	 */
	public static final String SESSION_DEPT = "session_dept";
	/**
	 * 人员角色信息
	 */
	public static final String ROLE_LIST = "role_list";
	/**
	 * 重复提交验证值
	 */
	public static final String SESSION_TOKEN = "session_toekn_value";

	/**
	 * 验证码
	 */
	public static final String SESSION_YZM = "session_yzm_random";

	/**
	 * 复核公示结束
	 */
	public static final String fhstate = "5";

}
