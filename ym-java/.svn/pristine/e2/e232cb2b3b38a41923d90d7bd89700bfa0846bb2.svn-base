package com.lyht.system.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.SysMenuTree;
import com.lyht.system.bean.SysRoleDetail;
import com.lyht.system.dao.SysRoleDao;
import com.lyht.system.pojo.SysRole;
import com.lyht.system.pojo.SysStaff;
import com.lyht.system.service.SysMenuService;
import com.lyht.system.service.SysRoleService;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 创建人： liuamang 脚本日期:2019年2月18日 17:57:52 说明: 系统角色
 */
@RestController
@RequestMapping("/sysRole")
@Api(value = "/sysRole", tags = "角色信息")
public class SysRoleController {
	private Logger log = LoggerFactory.getLogger(SysRoleController.class);

	@Autowired private SysRoleService services;

	@Autowired private SysRoleDao dao;

	@Autowired private SysMenuService sysMenuService;



	/**
	 * 角色人员关联设置 保存
	 * 
	 * @return
	 */
	@GetMapping("/setRoleStaffRef")
	@ResponseBody
	public LyhtResultBody setRoleStaffRef(HttpServletRequest request) {

		try {
			String roleNm = CommonUtil.trim(request.getParameter("roleNm"));
			String staffNms = CommonUtil.trim(request.getParameter("staffNms"));
			services.setRoleStaffRef(roleNm, staffNms);
		} catch (Exception e) {
			log.error("角色配置人员出错！");
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody();
	}

	/**
	 * 获取角色授权信息
	 * 
	 * @return
	 */
	@GetMapping("/getRoleRef")
	@ResponseBody
	public List roleRef(HttpServletRequest request) {
		List mapList = new ArrayList();
		try {
			String roleNm = CommonUtil.trim(request.getParameter("roleNm"));
			String type = CommonUtil.trim(request.getParameter("type"));
			if (type.equals("staff")) {
				mapList = services.findRoleStaffRefByRoleNm(roleNm);
			} else {
				mapList = services.findRoleMenuRefByRoleNm(roleNm);
			}
		} catch (Exception e) {
			log.error(" 获取角色授权信息，出错!", e);
		}
		return mapList;
	}

	/**
	 * 角色人员关联设置 保存
	 * 
	 * @return
	 */
	@GetMapping("/setRoleMenuRef")
	@ResponseBody
	public LyhtResultBody setRoleMenuRef(HttpServletRequest request) {
		try {
			String roleNm = CommonUtil.trim(request.getParameter("roleNm"));
			String menuNms = CommonUtil.trim(request.getParameter("menuNms"));
			services.setRoleMenuRef(roleNm, menuNms);
		} catch (Exception e) {
			log.error("角色配置菜单出错！");
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody();
	}
	////////////////////////////////////////////////// 基础增删改查//////////////////////////////////////////////////////////////////
	/**
	 * 分页
	 * 
	 * @param lyhtPageVO
	 * @param sysroledetail
	 * @return
	 */
	@PostMapping("/page")
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	public LyhtResultBody<List<SysRoleDetail>> page(LyhtPageVO lyhtPageVO, SysRoleDetail sysroledetail) {
		return services.page(lyhtPageVO, sysroledetail);
	}

	/**
	 * 详情
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/deail")
	@ApiOperation(value = "id查询", notes = "详情查询")
	public LyhtResultBody<SysRole> deail(Integer id) {
		Optional<SysRole> list = null;
		SysRole sysRoleList = null;
		try {
			list = services.deail(id);
			sysRoleList = list.get();

		} catch (Exception e) {

			log.error("用户查询详情失败！", e);
		}
		return new LyhtResultBody<>(sysRoleList);
	}

	/**
	 * 保存
	 * 
	 * @param sysrole
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody<List<Map>> save(SysRole sysrole) {
		try {
			SysRole role = null;
			if (CommonUtil.getIntValue(sysrole.getId() + "") == 0) {
				sysrole.setNm(Randomizer.generCode(10));
				sysrole.setCode(Randomizer.generCode(6));
				sysrole.setFlag(0);
				dao.save(sysrole);
			} else {
				Optional<SysRole> findById = dao.findById(sysrole.getId());
				role = findById.get();
				role.setMemo(sysrole.getMemo());
				role.setName(sysrole.getName());
				dao.save(role);
			}

		} catch (Exception e) {
			log.error("角色保存失败！", e);
		}
		return new LyhtResultBody<List<Map>>();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "按id删除", notes = "删除")
	@GetMapping("/delete")
	public LyhtResultBody delete(Integer id) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			log.error("角色删除失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody();
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
	@GetMapping("/batch")
	public void batchDel(String ids) {
		List<Integer> idList = null;
		try {
			// 转换id整形数组
			idList = CommonUtil.parseIntegerList(ids);
			List<SysRole> deleteOwners = dao.findAllById(idList);
			dao.deleteInBatch(deleteOwners);
		} catch (Exception e) {
			log.error("用户删除失败！", e);
		}
	}
	/**
	 * 根据人员查询所拥有的角色
	 * @param staffNm
	 * @return
	 */
	@PostMapping("/findRolesByStaffNm")
	@ApiOperation(value = "根据人员查询所拥有的角色", notes="查询")
	public LyhtResultBody<List<SysRole>> findRolesByStaffNm(String staffNm) {
		return new LyhtResultBody<>(services.findRolesByStaffNm(staffNm));
	}

	/**
	 * 根据角色查询所拥有的菜单
	 * @param roleNm
	 * @return
	 */
	@PostMapping("/findMenusByRoleNm")
	@ApiOperation(value = "根据角色查询所拥有的菜单", notes="查询")
	public LyhtResultBody<List<SysMenuTree>> findMenusByRoleNm(String roleNm) {
		List<SysMenuTree>  list = null;
		try {
			list = sysMenuService.getTopBeanTwo(null);
			List<SysMenuTree> mapList =  services.findMenusByRoleNm(roleNm);
			for (int i=0;i<mapList.size();i++){
				mapList.get(i).setChildren(getTreeList(mapList.get(i).getId(),mapList,i));
			}
			for (int i=0;i<list.size();i++){
				list.get(i).setChildren(getTreeList(list.get(i).getId(),mapList,-1));
			}
			//获取子级菜单
//			for (int i = 0;i<list.size();i++){
//				//遍历父级菜单
//				SysMenuTree mp = list.get(i);
//				//接收到当前对象
//				List<SysMenuTree> mList = new ArrayList<>();
//				for (int j = 0 ; j<mapList.size();j++){
//					//循环查找父级菜单下的子级并添加进菜单集合中
//					SysMenuTree mps = mapList.get(j);
//					if(StringUtils.equals(mps.getSuper_id(),mp.getId()+"")){
//						mps.setSuperName(mp.getName());
//						mList.add(mps);
//					}
//				}
//				//将子级菜单添加到父级菜单下
//				mp.setChildren(mList);
//				list.set(i,mp);
//			}
			for(int i = 0 ; i<list.size() ; i++){
				if(list.get(i).getChildren().size()==0){
					list.remove(list.get(i));
					i--;
				}
			}
		}catch (Exception e){
			log.error("获取菜单树结构失败！",e);
		}
//		for(int i =0 ; i<list.size();i++){
//			List<SysMenuTree> mList = list.get(i).getChildren();
//			if(mList==null||mList.size()==0){
//				list.remove(i);
//				i--;
//			}
//		}
		return new LyhtResultBody<>(list);
	}
	// 递归查询
	private List<SysMenuTree> getTreeList(String parentId, List<SysMenuTree> sonList,int j) {
		List<SysMenuTree> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				SysMenuTree mp = sonList.get(i);
				// 当前自己为自己时进入方法
				if (StringUtils.equals(mp.getSuper_id(), parentId)&&i!=j) {
					List<SysMenuTree> m = getTreeList(mp.getId(), sonList,i);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}
	/**
	 * 根据角色查询所绑定的人员
	 * @param roleNm
	 * @return
	 */
	@PostMapping("/findStaffsByRoleNm")
	@ApiOperation(value = "根据角色查询所绑定的人员", notes="查询")
	public LyhtResultBody<List<SysStaff>> findStaffsByRoleNm(String roleNm) {
		return new LyhtResultBody<>(services.findStaffsByRoleNm(roleNm));
	}
}
