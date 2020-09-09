package com.lyht.system.service;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.dao.SysLogDao;
import com.lyht.system.pojo.SysLog;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.CommonUtil;
import com.lyht.util.DateUtil;
import com.lyht.util.SystemUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
  * 创建人： Yanxh 
  * 脚本日期:2020年1月7日
  * 说明:  系统日志
  */
@Service("/sysLogService")
public class SysLogService {
	
	private Logger logs = LoggerFactory.getLogger(SysLogService.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SysLogDao dao;
	
	/**
	 * @param nm				数据内码	必填
	 * @param menuflag			模块		必填
	 * @param dictnmOpttype		操作类型	必填
	 * @param oldData			旧数据		选填
	 * @param newData			新数据		选填
	 * @return LyhtResultBody<String>
	 */
	public LyhtResultBody<String> log(String nm,String menuflag,String dictnmOpttype,String oldData,String newData){
		LyhtResultBody<String> result = new LyhtResultBody<>();
		SysStaff staff 	= SystemUtil.getLoginStaff(request);
		nm 				= CommonUtil.trim(nm);
		menuflag 		= CommonUtil.trim(menuflag);
		dictnmOpttype 	= CommonUtil.trim(dictnmOpttype);
		
		if (staff == null || staff.getStaffName() == null) {//获取不到用户
			logs.error("未获取到登陆信息，操作日志记录失败！");
			return new LyhtResultBody<>(LyhtResultBody.CODE_FAIL, LyhtResultBody.FAILURE, "未获取到登陆信息，操作日志记录失败！");
		}
		if(nm.equals("")){
			logs.error("未指定内码，操作日志记录失败！");
			return new LyhtResultBody<>(LyhtResultBody.CODE_FAIL, LyhtResultBody.FAILURE, "未指定内码，操作日志记录失败！");
		}
		if(menuflag.equals("")){
			logs.error("未指定模块，操作日志记录失败！");
			return new LyhtResultBody<>(LyhtResultBody.CODE_FAIL, LyhtResultBody.FAILURE, "未指定模块，操作日志记录失败！");
		}
		if(dictnmOpttype.equals("")){
			logs.error("未指定操作类型，操作日志记录失败！");
			return new LyhtResultBody<>(LyhtResultBody.CODE_FAIL, LyhtResultBody.FAILURE, "未指定操作类型，操作日志记录失败！");
		}
		
		SysLog sysLog = new SysLog(0, nm, DateUtil.getDateTime(), staff.getStaffName(), staff.getNm(), menuflag, dictnmOpttype, oldData, newData, "", 0, 0);
		
		logs.info("新增"+staff.getStaffName()+"的操作日志信息");
		
		dao.save(sysLog);
		
		return result;
	}
	

	/**
	 * 
	 * @param nm			必填
	 * @param menuflag		必填
	 * @param dictnmOpttype	必填
	 * @return LyhtResultBody<SysLog>
	 */
	public LyhtResultBody<List<SysLog>> list(String nm,String menuflag,String dictnmOpttype){
		LyhtResultBody<List<SysLog>> result = new LyhtResultBody<List<SysLog>>();
		nm 				= CommonUtil.trim(nm);
		menuflag 		= CommonUtil.trim(menuflag);
		dictnmOpttype 	= CommonUtil.trim(dictnmOpttype);
		//内码/模块/操作类型为空直接返回
		if(nm.equals("") || menuflag.equals("") || dictnmOpttype.equals("")){
			result.setMsg("参数不全");
			result.setList(new ArrayList<>());
			return result;
		}
		List<SysLog> list = dao.list(nm, menuflag, dictnmOpttype);
		result.setList(list);
		return result;
	}
	
	/**
	 * 
	 * @param menuflag		选填
	 * @param dictnmOpttype	选填
	 * @param staffName		选填
	 * @param startTime		选填
	 * @param endTime		选填
	 * @return LyhtResultBody<SysLog>
	 */
	public LyhtResultBody<List<SysLog>> list(String menuflag,String dictnmOpttype,String staffName,String startTime,String endTime){
		LyhtResultBody<List<SysLog>> result = new LyhtResultBody<List<SysLog>>();
		menuflag 		= CommonUtil.trim(menuflag);
		dictnmOpttype 	= CommonUtil.trim(dictnmOpttype);
		staffName 		= CommonUtil.trim(staffName);
		startTime 		= CommonUtil.trim(startTime);
		endTime 		= CommonUtil.trim(endTime);

		List<SysLog> list = dao.list(menuflag,dictnmOpttype,staffName ,startTime,endTime);

		result.setList(list);
		return result;
	}
}
 