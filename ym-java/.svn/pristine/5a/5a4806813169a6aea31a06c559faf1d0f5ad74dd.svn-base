package com.lyht.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lyht.Constants;
import com.lyht.system.pojo.SysAcct;
import com.lyht.system.pojo.SysDept;
import com.lyht.system.pojo.SysStaff;

/**
 * 返回对应系Session统值
 */
public class SystemUtil {
	private static Logger log = LoggerFactory.getLogger(SystemUtil.class);

	/**
	 * 获取当前系统登陆人员对象
	 * 
	 * @param hsdSession
	 * @return
	 */
	public static SysStaff getLoginStaff(HttpServletRequest request) {
		SysStaff staff = new SysStaff();
		try {
			Object obj = request.getSession().getAttribute(Constants.SESSION_STAFF);
			if (obj instanceof SysStaff) {
				staff = (SysStaff) obj;
			}
		} catch (Exception e) {
			log.error("========获取当前系统登陆人员对象出错", e);
			e.getStackTrace();
		}
		return staff;
	}
	
	/**
	 * 获取当前系统登陆人员对象
	 * 
	 * @param hsdSession
	 * @return
	 */
	public static String getLoginStaffUuid(HttpServletRequest request) {
		String uuid = null;
		try {
			Object obj = request.getSession().getAttribute(Constants.SESSION_STAFF);
			if (obj instanceof SysStaff) {
				SysStaff staff = (SysStaff) obj;
				uuid = staff.getUuid();
			}
		} catch (Exception e) {
			log.error("========获取当前系统登陆人员对象出错", e);
		}
		return uuid;
	}

	/**
	 * 获取当前系统登陆人员对象
	 * 
	 * @param hsdSession
	 * @return
	 */
	public static SysAcct getLoginAcct(HttpServletRequest request) {
		SysAcct acct = new SysAcct();
		try {
			Object obj = request.getSession().getAttribute(Constants.SESSION_ACCT);
			if (obj instanceof SysAcct) {
				acct = (SysAcct) obj;
			}
		} catch (Exception e) {
			log.error("========获取当前系统登陆人员帐号对象出错", e);
			e.getStackTrace();
		}
		return acct;
	}

	/**
	 * 获取当前系统登陆人员对象
	 * 
	 * @param hsdSession
	 * @return
	 */
	public static SysDept getLoginDept(HttpServletRequest request) {
		SysDept dept = new SysDept();
		try {
			Object obj = request.getSession().getAttribute(Constants.SESSION_DEPT);
			if (obj instanceof SysDept) {
				dept = (SysDept) obj;
			}
		} catch (Exception e) {
			log.error("========获取当前系统登陆人员部门出错", e);
			e.getStackTrace();
		}
		return dept;
	}

	/**
	 * 获取当前登陆人员 内码
	 * 
	 * @param hsdSession
	 * @return
	 */
	public static String getLoginStaffNm(HttpServletRequest request) {
		String userNm;
		try{
			userNm = CommonUtil.trim(getLoginStaff(request).getNm());
		}catch (Exception e){
			userNm = "F57FBDC89E";
		}
		return userNm;
	}

	/**
	 * 获取当前登录人员名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginStaffName(HttpServletRequest request) {
		return CommonUtil.trim(getLoginStaff(request).getStaffName());
	}

	/**
	 * 获取当前登陆人员帐号内码
	 * 
	 * @param hsdSession
	 * @return
	 */
	public static String getLoginAcctNm(HttpServletRequest request) {
		return CommonUtil.trim(getLoginAcct(request).getNm());
	}

	/**
	 * 获取当前登录人员帐号名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginAcctName(HttpServletRequest request) {
		return CommonUtil.trim(getLoginAcct(request).getName());
	}

	/**
	 * 获取当前登陆人员部门内码
	 * 
	 * @param hsdSession
	 * @return
	 */
	public static String getLoginDeptNm(HttpServletRequest request) {
		return CommonUtil.trim(getLoginDept(request).getNm());
	}

	/**
	 * 获取当前登录人员部门名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginDeptName(HttpServletRequest request) {
		return CommonUtil.trim(getLoginDept(request).getName());
	}
}
