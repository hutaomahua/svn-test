package com.lyht.system.service;


import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.SysMenuTree;
import com.lyht.system.bean.SysRoleDetail;
import com.lyht.system.dao.SysRoleDao;
import com.lyht.system.dao.SysRoleMenuRefDao;
import com.lyht.system.dao.SysRoleStaffRefDao;
import com.lyht.system.pojo.SysRole;
import com.lyht.system.pojo.SysRoleMenuRef;
import com.lyht.system.pojo.SysRoleStaffRef;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.CommonUtil;
import com.lyht.util.EntityUtils;
import com.lyht.util.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月18日 15:56:01
  * 说明:  系统角色
  */
@Service("/sysRoleService")
public class SysRoleService {
	
	@Autowired  private SysRoleDao dao;
	@Autowired private SysRoleStaffRefDao sysRoleStaffRefDao;
	@Autowired private SysRoleMenuRefDao sysRoleMenuRefDao;
	
	/** 角色人员关联设置 保存
	 * @param roleNm
	 * @param staffNms
	 * @return
	 */
	@Transactional
	public int setRoleStaffRef(String roleNm , String staffNms){
		String sql = "";
		String [] staffNmArr  = staffNms.split(",");
		int result = 0;
		removeRoleStaffRef(roleNm);//删除角色人员授权
		for(String staffNm : staffNmArr){
			if(CommonUtil.getLength(staffNm) > 0 ){
				SysRoleStaffRef sysRoleStaffRef = new SysRoleStaffRef();
				sysRoleStaffRef.setCode(Randomizer.generCode(10));
				sysRoleStaffRef.setStaffNm(staffNm);
				sysRoleStaffRef.setRoleNm(roleNm);
				sysRoleStaffRef.setFlag(0);
//				sql = "insert into sys_role_staff_ref(code,staff_nm,role_nm,flag) values(?,?,?,?)";
//				dao.exectueSQL(sql, new Object[]{Randomizer.generCode(10),staffNm,roleNm,0 });
				sysRoleStaffRefDao.save(sysRoleStaffRef);
			}
		}
		return result;
	}
	
	/** 删除角色授权人员关联
	 * @param roleNm
	 * @return
	 */
	public int removeRoleStaffRef(String roleNm){
		//String sql = "delete from sys_role_staff_ref where role_nm = ? ";
		return sysRoleStaffRefDao.deleteByRoleNm(roleNm);
	}
	
	/** 查询人员授权
	 * @param roleNm
	 * @return
	 */
	public List<SysRoleStaffRef> findRoleStaffRefByRoleNm(String roleNm){
//		String sql = "select * from sys_role_staff_ref where role_nm = ? ";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql,new Object[]{roleNm}), SysRoleStaffRef.class);
		return sysRoleStaffRefDao.findByRoleNm(roleNm);
	}
	
	/** 查询菜单授权
	 * @param roleNm
	 * @return
	 */
	public List<SysRoleMenuRef> findRoleMenuRefByRoleNm(String roleNm){
//		String sql = "select * from sys_role_menu_ref where role_nm = ? ";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql,new Object[]{roleNm}),SysRoleStaffRef.class);
		return sysRoleMenuRefDao.findByRoleNm(roleNm);
	}
	
	/** 角色菜单关联设置 保存
	 * @param roleNm
	 * @param menuNms
	 * @return
	 */
	@Transactional
	public int setRoleMenuRef(String roleNm , String menuNms){
		String sql = "";
		String [] menuNmArr  = menuNms.split(",");
		int result = 0;
		removeRoleSysMenuRef(roleNm);//删除角色菜单授权
		for(String menuNm : menuNmArr){
			if(CommonUtil.getLength(menuNm) > 0 ){
				SysRoleMenuRef sysRoleMenuRef =new SysRoleMenuRef();
				sysRoleMenuRef.setCode(Randomizer.generCode(10));
				sysRoleMenuRef.setRoleNm(roleNm);
				sysRoleMenuRef.setMenuNm(menuNm);
				sysRoleMenuRef.setFlag(0);
//				sql = "insert into sys_role_menu_ref(code,role_nm,menu_nm,flag) values(?,?,?,?)";
//				dao.exectueSQL(sql, new Object[]{Randomizer.generCode(10),roleNm,menuNm,0 });
				sysRoleMenuRefDao.save(sysRoleMenuRef);
			}
		}
		return result;
	} 
	
	/** 删除角色授权人员关联
	 * @param roleNm
	 * @return
	 */
	public int removeRoleSysMenuRef(String roleNm){
//		String sql = "delete from sys_role_menu_ref where role_nm = ? ";
////		return dao.exectueSQL(sql, new Object[]{roleNm});
		return sysRoleMenuRefDao.deleteByRoleNm(roleNm);
	}
//	/** 角色树
//	 * @return
//	 */
//	public List<Map> findRoleTreeData(){
//		String sql = " SELECT CONCAT('role', id) AS id, nm, name , 'role' AS type, 'role1' AS super_id, '../static/business/img/user.png' AS icon FROM sys_role  ";
//		return dao.findAllByParams(sql, new Object[]{});
//	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/** 查询用户列表信息
	 * @param search
	 * @return
	 */
	public LyhtResultBody<List<SysRoleDetail>> page(LyhtPageVO lyhtPageVO, SysRoleDetail search) {
		if(lyhtPageVO.getCurrent()==null)
			lyhtPageVO.setCurrent(1);
		if(lyhtPageVO.getPageSize()==null)
			lyhtPageVO.setPageSize(10);
		Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent()-1, lyhtPageVO.getPageSize());
		Page<Map> sysRoles = dao.getList(search.getName(),pageable);
		LyhtPageVO pageVO = new LyhtPageVO(sysRoles.getNumber(), sysRoles.getTotalPages(), sysRoles.getSize(),
				sysRoles.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>( EntityUtils.toEntityList(sysRoles.getContent(),SysRoleDetail.class), pageVO);
		
	}
	public Optional<SysRole> deail(Integer id) {
//		 String sql = "select * from sys_role  where id = ?";
//		 List<Map> list = dao.findAllByParams(sql, new Object[]{id});
		  return dao.findById(id);
	}
	public List<SysRole> findRolesByStaffNm(String staff_nm) {
		return dao.findRolesByStaffNm(staff_nm);
	}
	public List<SysMenuTree> findMenusByRoleNm(String role_nm) {
		return EntityUtils.toEntityList(dao.findMenusByRoleNm(role_nm),SysMenuTree.class);
	}
	public List<SysStaff> findStaffsByRoleNm(String role_nm) {
		return  EntityUtils.toEntityList(dao.findStaffsByRoleNm(role_nm),SysStaff.class);
	}
}
 