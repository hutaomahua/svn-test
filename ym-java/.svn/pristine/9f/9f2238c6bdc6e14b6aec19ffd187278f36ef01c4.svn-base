package com.lyht.system.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.Constants;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.Authors;
import com.lyht.system.bean.LoadMenu;
import com.lyht.system.pojo.SysAcct;
import com.lyht.system.pojo.SysDept;
import com.lyht.system.pojo.SysStaff;
import com.lyht.system.service.SysAcctService;
import com.lyht.system.service.SysDeptService;
import com.lyht.system.service.SysMenuService;
import com.lyht.system.service.SysRolePartitionService;
import com.lyht.system.service.SysRoleStaffRefService;
import com.lyht.system.service.SysStaffService;
import com.lyht.system.vo.StaffRoleRefVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.MD5;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="/login", tags="登陆")
@RestController
@RequestMapping("/login")
public class LoginController {
	private Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired  private SysAcctService acctService;
	@Autowired  private SysStaffService staffService;
	@Autowired  private SysDeptService deptService;
	@Autowired  private SysMenuService menuService;
	@Autowired  private SysRoleStaffRefService sysRoleStaffRefService;
	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/** 登出页面
	 * @return
	 */
	@PostMapping("/loginOut")
	@ApiOperation(value = "登出", notes="登出")
	public LyhtResultBody loginOut(HttpServletRequest request){
		try {
				request.getSession().removeAttribute(Constants.SESSION_ACCT);
				request.getSession().removeAttribute(Constants.SESSION_STAFF);
				request.getSession().removeAttribute(Constants.SESSION_DEPT);
			} catch (Exception e) {
				log.error("登出失败！",e);
				throw  new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
			}
			return new LyhtResultBody();
	}

	@PostMapping("/getAuthor")
	@ApiOperation(value = "获取用户信息", notes="用户信息")
	public LyhtResultBody<Authors> GetAuthor(HttpServletRequest request){
		Authors reMap=new Authors();
		try {
			Object obj = request.getSession().getAttribute(Constants.SESSION_ACCT);//获取session中的当前登录的用户信息
			Object obj1 = request.getSession().getAttribute(Constants.SESSION_STAFF);//获取session中的当前登录的账户信息
			Object obj2 = request.getSession().getAttribute(Constants.SESSION_DEPT);//获取session中的当前登录的部门信息
			SysAcct sysAcct = (SysAcct) obj;
			SysStaff sysStaff = (SysStaff) obj1;
			SysDept sysDept = (SysDept) obj2;
			List<StaffRoleRefVO> list = sysRoleStaffRefService.getRoleRef(sysStaff.getNm());
			reMap.setSysAcct(sysAcct);
			reMap.setSysStaff(sysStaff);
			reMap.setSysDept(sysDept);
			reMap.setRoleList(list);
		} catch (Exception e) {

			log.error("获取用户信息失败！",e);
		}
		return new LyhtResultBody<Authors>(reMap);
	}
	/**
	 * 登陆校验
	 * @param acct
	 * @param servletRequest
	 * @return
	 */
	@PostMapping("/login")
	@ApiOperation(value = "登陆验证", notes="登陆")
	public LyhtResultBody<Map<String, Object>> checkAcct(SysAcct acct, String ip, String loginType, ServletRequest servletRequest, HttpServletResponse response){
		int i = 0;
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpSession session = request.getSession();
		Map<String, Object> result = null;
		try {
			SysAcct acctBean = acctService.findByName( CommonUtil.trim(acct.getName()));
			//判断用户是否存在
			if(acctBean == null || CommonUtil.getIntValue(acctBean.getId()+"")==0){
				i=1;
				throw new LyhtRuntimeException(LyhtExceptionEnums.LOGINNAMEFALSE);
			}
			//判断密码是否正确
			if(!acctBean.getPwd().equals(MD5.getInstance().getMD5ofStr(acct.getPwd()))){
				i=2;
				throw new LyhtRuntimeException(LyhtExceptionEnums.LOGINPWDFALSE);
			}
			//查询部门及用户信息
			//加载session域值 帐号、人员、部门
			SysStaff  staff = staffService.findByNm(CommonUtil.trim(acctBean.getStaffNm()));

			SysDept dept = new SysDept();
			if(staff!= null && CommonUtil.getLength(staff.getDeptCode())>0 ){
				dept = deptService.findById(Integer.parseInt(CommonUtil.trim(staff.getDeptCode())));//查询部门信息
			}
			List<StaffRoleRefVO> roleList = sysRoleStaffRefService.getRoleRef(staff.getNm());
			Timestamp ts = null;
			Date date = new Date();
			ts = Timestamp.valueOf(sdf.format(date));
			//List<SysRolePartitionRef> sysRolePartitions =sysRolePartitionService.findRolePartitionRefByRequest(staff.getNm());
			//拼接用户管理区域
//			StringBuffer  stringBuffer = new StringBuffer("(");
//			for (SysRolePartitionRef sysRolePartitionRef :sysRolePartitions){
//				stringBuffer.append("'"+sysRolePartitionRef.getPartitionNm()+"',");
//			}
//			stringBuffer.delete(stringBuffer.length()-1,stringBuffer.length());
//			stringBuffer.append(")");
//			String staffJson = JSON.toJSONString(staff);
			//存到session中
			session.setAttribute(Constants.SESSION_STAFF, staff);
			session.setAttribute(Constants.SESSION_DEPT, dept);
			session.setAttribute(Constants.SESSION_ACCT,acctBean);
			session.setAttribute(Constants.ROLE_LIST,roleList);
//			String encodeCookie = URLEncoder.encode(staffJson,"utf-8");
//			Cookie cookie = new Cookie(Constants.SESSION_STAFF, encodeCookie);
//			cookie.setPath("/");
//			response.addCookie(cookie); // 服务器返回给浏览器cookie以便下次判断
			result = new HashMap<>();
			result.put("JSESSIONID", session.getId());
			result.put("roleList", roleList);
		} catch (Exception e) {
			log.error("登陆验证失败！",e);
			if(i==1){
				throw new LyhtRuntimeException(LyhtExceptionEnums.LOGINNAMEFALSE);
			}else if(i==2){
				throw new LyhtRuntimeException(LyhtExceptionEnums.LOGINPWDFALSE);
			}else {
				throw new LyhtRuntimeException(LyhtExceptionEnums.LOGINFALSE);
			}
		}
		return new LyhtResultBody<>(result);
	}


	/** 界面加载，菜单查询
	 * @return
	 */
	@PostMapping("/index")
	@ApiOperation(value = "界面加载", notes="菜单加载")
	public LyhtResultBody<List<LoadMenu>> indexLoad(HttpServletRequest request){
		List<LoadMenu> list = null;
		try {
			list=menuService.loadMenu(request);
		} catch (Exception e) {
			log.error("主页数据加载失败，请检查!",e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(list);
	}
	private Boolean arraySearch(String menuId, List<String> strings) {
		for (String x : strings) {
			if (x.equals(menuId)) {
				return false;
			}
		}
		return true;
	}

	@PostMapping("/saveAuthor")
	@ApiOperation(value = "修改用户信息", notes="用户信息")
	public LyhtResultBody<Authors> saveAuthor(SysStaff sysstaff, String zhanghao, HttpServletRequest request){
		Authors reMap=new Authors();
		try {
			Object obj = request.getSession().getAttribute(Constants.SESSION_ACCT);//获取session中的当前登录的用户信息
			Object obj2 = request.getSession().getAttribute(Constants.SESSION_DEPT);//获取session中的当前登录的部门信息
			SysAcct sysAcct = (SysAcct) obj;
			SysDept sysDept = (SysDept) obj2;
			staffService.save(sysstaff);
			request.getSession().setAttribute(Constants.SESSION_STAFF, sysstaff);
			reMap.setSysAcct(sysAcct);
			reMap.setSysStaff(sysstaff);
			reMap.setSysDept(sysDept);
		} catch (Exception e) {

			log.error("修改用户信息失败！",e);
		}
		return new LyhtResultBody<>(reMap);
	}
}
