package com.lyht.system.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.SysStaffDetail;
import com.lyht.system.dao.SysStaffDao;
import com.lyht.system.pojo.SysAcct;
import com.lyht.system.pojo.SysStaff;
import com.lyht.system.service.SysAcctService;
import com.lyht.system.service.SysStaffService;
import com.lyht.util.CommonUtil;
import com.lyht.util.ExportExcelWrapper;
import com.lyht.util.MD5;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建人： lj
 * 
 * 说明: 人员信息
 */
@Api(value = "/sysStaff", tags = "人员信息")
@RestController
@RequestMapping("/sysStaff")
public class SysStaffController {
	private Logger log = LoggerFactory.getLogger(SysStaffController.class);

	@Autowired
	private SysStaffService services;
	@Autowired
	private SysStaffDao dao;
	@Autowired
	private SysAcctService acctServices;

	@ApiOperation(value = "根据名称模糊查询", notes = "查询")
	@PostMapping("/getStaffLikeName")
	public LyhtResultBody<List<SysStaff>> getStaffLikeName(String name) {
		return services.getStaffLikeName(name);
	}

	/**
	 * 人员信息 数据保存
	 * 
	 * @param request
	 * @param bean
	 * @return
	 *//*
		 * @RequestMapping("/save")
		 * 
		 * @ResponseBody public Map<String,Object> save(SysStaff bean){ Map<String
		 * ,Object> jsonMap = new HashMap<String ,Object>();
		 * jsonMap.put("flag","error"); try { if(CommonUtil.getIntValue(bean.getId()+"")
		 * == 0 ){ bean.setNm(Randomizer.generCode(10)); bean.setStaffType(0); }
		 * 
		 * services.save(bean); jsonMap.put("flag","success"); } catch (Exception e) {
		 * log.error("人员信息数据保存失败！",e); } return jsonMap; }
		 */

	/*	*//**
			 * 人员信息 新增加载方法 , 初始化修改数据
			 * 
			 * @param id
			 * @return
			 */
	/*
	 * @RequestMapping("/add/{id}") public String add(@PathVariable int id,Model
	 * model){ try { if(id > 0 ){ SysStaff bean = services.findEntityById(id);
	 * model.addAttribute("bean",bean);
	 * 
	 * if(CommonUtil.getLength(bean.getDeptCode()) > 0){ SysDept deptBean =
	 * deptServices.findByObject("nm", bean.getDeptCode(), SysDept.class);
	 * model.addAttribute("deptBean",deptBean); } } } catch (Exception e) {
	 * log.error("初始化人员信息维护界面出错！"); } return "page/system/sysStaff/sysStaff_edit"; }
	 * 
	 *//**
		 * 人员信息 详细页
		 * 
		 * @param id
		 * @return
		 */
	/*
	 * @RequestMapping("/show/{id}") public String show(@PathVariable int id,Model
	 * model){ try { if(id > 0 ){ Map map = services.getObject(id);
	 * model.addAttribute("bean",map); } } catch (Exception e) {
	 * log.error("人员信息详细页数据加载出错！"); } return "page/system/sysStaff/sysStaff_show"; }
	 * 
	 *//**
		 * 人员信息删除
		 * 
		 * @param id
		 * @return
		 */
	/*
	 * @RequestMapping("/remove/{ids}")
	 * 
	 * @ResponseBody public Map<String,Object> remove(@PathVariable String ids){
	 * Map<String,Object> map = new HashMap<String,Object>(); try { //批量删除 和 单个删除
	 * services.removeStaffAndRef(ids); map.put("flag", "success"); map.put("msg",
	 * "人员信息数据删除成功！"); } catch (Exception e) { log.error("初始化人员信息维护界面出错！");
	 * map.put("flag", "error"); map.put("msg", "人员信息数据删除失败，请重试！"); } return map; }
	 * 
	 *//**
		 * 人员信息列表加载
		 * 
		 * @return
		 */
	/*
	 * @SuppressWarnings("rawtypes")
	 * 
	 * @RequestMapping("/list") public String list(BaseSearchBean searchBean , Model
	 * model){ try { Page<Map> pageBean = services.getList(searchBean);
	 * model.addAttribute("pageBean",pageBean);
	 * model.addAttribute("searchBean",searchBean); } catch (Exception e) {
	 * log.error("获取人员信息列表失败！",e); } return "page/system/sysStaff/sysStaff_list"; }
	 *//**
		 * 跳转对应的选择页面
		 * 
		 * @return
		 */
	/*
	 * @RequestMapping("/commonselectstaff") public String
	 * commonSelectStaff(HttpServletRequest request,Model model){ try { String nms =
	 * CommonUtil.trim(request.getParameter("nms")); String multiselect =
	 * CommonUtil.trim(request.getParameter("multiselect"));
	 * 
	 * model.addAttribute("nms", nms); model.addAttribute("multiselect",
	 * multiselect); } catch (Exception e) { log.error("人员查询界面初始化失败",e); } return
	 * "page/system/sysStaff/common_select_staff"; }
	 * 
	 *//**
		 * 人员树形 选择
		 * 
		 * @param id
		 * @return
		 *//*
			 * @SuppressWarnings("rawtypes")
			 * 
			 * @RequestMapping("/staffTree")
			 * 
			 * @ResponseBody public List<Map> getDeptStaffTree(){ List<Map> list = null; try
			 * { list = services.findDeptStaffTreeData(); } catch (Exception e) {
			 * log.error("初始化人员信息维护界面出错！"); } return list; }
			 */
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 分页
	 * 
	 * @param sysstaffdetail
	 * @return
	 */
	@PostMapping("/page")
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	public LyhtResultBody<List<SysStaffDetail>> page(LyhtPageVO lyhtPageVO, SysStaffDetail sysstaffdetail) {
		return services.page(lyhtPageVO, sysstaffdetail);
	}

	/**
	 * 分页
	 *
	 * @param sysstaffdetail
	 * @return
	 */
	@PostMapping("/runlogPage")
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	public LyhtResultBody<List<SysStaffDetail>> pages(LyhtPageVO lyhtPageVO, SysStaffDetail sysstaffdetail) {
		return services.pages(lyhtPageVO, sysstaffdetail);
	}

	/**
	 * 查询全部用户
	 *
	 * @param sysstaffdetail
	 * @return
	 */
	@PostMapping("/lists")
	@ApiOperation(value = "查询全部用户", notes = "查询全部用户")
	public LyhtResultBody<List<SysStaffDetail>> lists(SysStaffDetail sysstaffdetail) {
		return new LyhtResultBody<>(services.lists(sysstaffdetail));
	}
	/**
	 * 导出Excel
	 *
	 * @param sysstaffdetail
	 * @return
	 */
	@ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
	@SuppressWarnings("rawtypes")
	@GetMapping("/export")
	public void exportExcel(HttpServletResponse response, SysStaffDetail sysstaffdetail) {
		List<Map> list = services.list(sysstaffdetail);
		String title = "人员信息";
		String fileName = title;
		String[] headers = { "内码", "名称", "状态", "性别", "民族", "生日", "联系电话", "籍贯", "所属单位" };
		String[] columnNames = { "nm", "staff_name", "staff_type", "staff_sex", "staff_ethnic", "staff_birthday",
				"staff_link", "staff_origin", "dept_code" };
		try {
			ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
			exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
					ExportExcelWrapper.EXCEl_FILE_2007);
		} catch (Exception e) {
			log.error("excel导出失败：", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
	}

	/**
	 * 详情
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/deail")
	@ApiOperation(value = "id查询", notes = "详情查询")
	public LyhtResultBody deail(Integer id) {
		List<Map> list = new ArrayList<>();
		try {
			list = services.detail(id);
		} catch (Exception e) {
			log.error("用户查询详情失败！", e);
			throw  new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody(list);
	}
	
	/**
	 * 验证账号是否存在
	 */
	@ApiOperation(value = "账号重复验证", notes = "账号重复验证")
	@PostMapping("/isExit")
	public LyhtResultBody<Object> isExit(String name,String nm) {
		LyhtResultBody<Object> lyhtResultBody = new LyhtResultBody<Object>();
		SysAcct sysAcct = acctServices.findByName(CommonUtil.trim(name));
		if(!CommonUtil.isEmpty(nm)) {//修改
			SysAcct sysAcct2 = acctServices.findByStaffNm(nm);
			if(sysAcct != null &&CommonUtil.getIntValue(sysAcct.getId() + "") != 0) {
				if(sysAcct.getId() != sysAcct2.getId()) {
					lyhtResultBody.setMsg("该账号已存在");
					lyhtResultBody.setFlag(false);
				}
			}
		}else {//新增
			if (sysAcct != null &&CommonUtil.getIntValue(sysAcct.getId() + "") != 0) {
				lyhtResultBody.setMsg("该账号已存在");
				lyhtResultBody.setFlag(false);
			}
		}
		
		return lyhtResultBody;
	}

	/**
	 * 保存
	 * 
	 * @param sysstaff
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody save(SysStaff sysstaff, String zhanghao) {
		int errCode = 0;
		try {
			if (CommonUtil.getIntValue(sysstaff.getId() + "") == 0) {

				sysstaff.setNm(Randomizer.generCode(10));
				sysstaff.setStaffType(0);
				// 创建账户
				SysAcct sysAcct = acctServices.findByName(CommonUtil.trim(zhanghao));
				// 判断账号是否存在
				if (sysAcct == null || CommonUtil.getIntValue(sysAcct.getId() + "") == 0) {
					sysAcct = new SysAcct();
					sysAcct.setNm(Randomizer.generCode(10));
					sysAcct.setFlag(0);
					sysAcct.setSysflag(0);
					sysAcct.setName(zhanghao);
					sysAcct.setYxq(new Date(new java.util.Date().getTime()));
					sysAcct.setPwd(MD5.getInstance().getMD5ofStr("123456"));
					sysAcct.setStaffNm(sysstaff.getNm());
					acctServices.save(sysAcct);
				}else{
					errCode=1;
					throw  new LyhtRuntimeException(LyhtExceptionEnums.LOGINNAMEHAVEFALSE);
				}
				dao.save(sysstaff);
			} else {
				SysStaff staff = dao.getOne(sysstaff.getId());
				sysstaff.setUuid(staff.getUuid());
				dao.save(sysstaff);
			}

		} catch (Exception e) {
			log.error("用户保存失败！", e);
			if(errCode==1){
				throw  new LyhtRuntimeException(LyhtExceptionEnums.LOGINNAMEHAVEFALSE);
			}else{
				throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
			}
		}
		return new LyhtResultBody();
	}

	/**
	 * 初始化密码
	 * 
	 * @return
	 */
	@ApiOperation(value = "初始化密码", notes = "初始化密码")
	@PostMapping("/resetPassword")
	public LyhtResultBody resetPassword(SysStaff sysstaff) {
		try {
			SysAcct sysAcct = acctServices.findByStaffNm(sysstaff.getNm());
			sysAcct.setPwd(MD5.getInstance().getMD5ofStr("123456"));
			acctServices.save(sysAcct);
		} catch (Exception e) {
			log.error("初始化密码失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "按id删除", notes = "删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(Integer id) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			log.error("用户删除失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
	@GetMapping("/batch")
	public LyhtResultBody<String> batchDel(String ids) {
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
			List<SysStaff> deleteOwners = dao.findAllById(idList);
			List<SysAcct> sysAccts = new ArrayList<>();
			for (SysStaff sysStaff:deleteOwners){
				sysAccts.add(acctServices.findByStaffNm(sysStaff.getNm()));
			}
			dao.deleteInBatch(deleteOwners);
			for (SysAcct sysAcct:sysAccts){
				acctServices.deleteEntityById(sysAcct.getId());
			}
		} catch (Exception e) {
			log.error("用户删除失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.SERVER_ERROR);
		}
		return new LyhtResultBody<>(ids);
	}
}
