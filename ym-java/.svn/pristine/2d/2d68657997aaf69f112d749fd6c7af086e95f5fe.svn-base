package com.lyht.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lyht.Constants;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.pojo.SysAcct;
import com.lyht.system.pojo.SysStaff;
import com.lyht.system.service.SysAcctService;
import com.lyht.system.service.SysStaffService;
import com.lyht.util.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月23日 18:31:03
  * 说明:  账户信息
  */

@Api(value="/sysAcct", tags="账户管理")
@Controller
@RequestMapping("/sysAcct")
public class SysAcctController {
	private Logger log = LoggerFactory.getLogger(SysAcctController.class);
	
	@Autowired  private SysAcctService services;
	@Autowired  private SysStaffService staffServices;
	
	/**账户信息 数据保存
	 * @param bean
	 * @return
	 */
	@PostMapping("/save")
	@ResponseBody
	public  Map<String,Object> save(SysAcct bean){
		Map<String ,Object> jsonMap = new HashMap<String ,Object>();
		jsonMap.put("flag","error");
		try {  
			if(CommonUtil.getIntValue(bean.getId()+"") == 0 ){
				bean.setNm(Randomizer.generCode(10));
				bean.setFlag(0);
				bean.setSysflag(0);
				bean.setYxq(new Date(new java.util.Date().getTime()));
				bean.setPwd(MD5.getInstance().getMD5ofStr(bean.getPwd()));
			}
			
			//如果空值密码则设置密码为123456
			if(CommonUtil.getIntValue(bean.getId()+"") == 0 ){
				bean.setPwd(MD5.getInstance().getMD5ofStr("123456"));
			}  
			
			services.save(bean);  
			jsonMap.put("flag","success");
		} catch (Exception e) {
			log.error("账户信息数据保存失败！",e);
		}
		return jsonMap;
	}
 
	/** 账户信息 新增加载方法 , 初始化修改数据
	 * @param id
	 * @return
	 */
	@PostMapping("/add/{staffNm}")
	public String add(@PathVariable String staffNm,Model model){
		try {
			SysAcct bean  = services.findByStaffNm(staffNm);
			
			SysStaff staff = staffServices.findByNm(staffNm);
			if(bean ==null)bean = new SysAcct();
			
			bean.setStaffNm(staffNm);
			model.addAttribute("bean",bean);
			model.addAttribute("staff",staff);
		} catch (Exception e) {
			log.error("初始化账户信息维护界面出错！",e);
		}
		return "page/system/sysStaff/acct";
	}
	
	
	/** 账户密码初始化
	 * @param staffNm
	 * @return 
	 */
	@GetMapping("/init/{staffNm}")
	@ResponseBody
	public Map<String,Object> initPwd(@PathVariable String staffNm){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//批量删除 和 单个删除
			SysAcct bean  = services.findByStaffNm(staffNm);
			bean.setPwd(MD5.getInstance().getMD5ofStr("123456"));
			services.save(bean);
			map.put("flag", "success");
			map.put("msg", "密码初始化成功！");
		} catch (Exception e) {
			log.error("初始化账户密码出错！",e);
			map.put("flag", "error");
			map.put("msg", "初始化账户密码出错，请重试！");
		}
		return map;
	}
	
	/** 修改密码 
	 * @return
	 */
	@GetMapping("/pwd")
	public String toModifyPwd(){
		return "page/system/sysStaff/modify_pwd";
	}

	/**
	 * 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @param newPwdQ
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "修改密码", notes="修改密码")
	@PostMapping("/modityPwd")
	@ResponseBody
	public LyhtResultBody<SysAcct> changePwd(String oldPwd, String newPwd, String newPwdQ, HttpServletRequest request){
		int i = 0 ;
		try {
			//获取当前需要修改的帐号对象
			SysAcct acct = SystemUtil.getLoginAcct(request);
			//获取输入密码/新密码
			String oldPwd1 = CommonUtil.trim(oldPwd);
			String newPwd1 = CommonUtil.trim(newPwd);
			String newPwdQ1 = CommonUtil.trim(newPwdQ);
			
			if(!MD5.getInstance().getMD5ofStr(oldPwd1).equals(acct.getPwd())){
				i = 1;
				throw new LyhtRuntimeException(LyhtExceptionEnums.OLD_PASSWORD_ERROR);
			}else if(!newPwd1.equals(newPwdQ1)){
				i = 2;
				throw new LyhtRuntimeException(LyhtExceptionEnums.ENTERED_PASSWORDS_DIFFER);
			}else{
				acct.setPwd(MD5.getInstance().getMD5ofStr(newPwdQ1));
				services.save(acct);
				request.getSession().removeAttribute(Constants.SESSION_ACCT);
				request.getSession().removeAttribute(Constants.SESSION_STAFF);
				request.getSession().removeAttribute(Constants.SESSION_DEPT);
			}
			
		} catch (Exception e) {
			log.error("修改账户密码出错！",e);
			if(i==0){
				throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
			}else if(i==1){
				throw new LyhtRuntimeException(LyhtExceptionEnums.OLD_PASSWORD_ERROR);
			}else if(i==2){
				throw new LyhtRuntimeException(LyhtExceptionEnums.ENTERED_PASSWORDS_DIFFER);
			}
		}
		return new LyhtResultBody<>();
	}
}
