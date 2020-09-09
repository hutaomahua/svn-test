package com.lyht.system.service;

import com.lyht.Constants;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.LoadMenu;
import com.lyht.system.bean.SysMenuDetail;
import com.lyht.system.bean.SysMenuTree;
import com.lyht.system.dao.SysMenuDao;
import com.lyht.system.pojo.SysAcct;
import com.lyht.system.pojo.SysMenu;
import com.lyht.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人： liuamang 脚本日期:2019年2月18日 23:08:18 说明: 系统菜单
 */
@Service("/sysMenuService")
public class SysMenuService {

	@Autowired
	private SysMenuDao dao;

	public List<SysMenuDetail> findList() {
//		String sql = "select  a.*,(select b.name as \"name\" from sys_menu b where id = a.super_id) as super_name from sys_menu a order by fcode";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[]{}), SysMenuDetail.class);
		return EntityUtils.toEntityList(dao.findList(), SysMenuDetail.class);
	}

	public SysMenu findById(Integer id) {
		return dao.findById(id).get();
	}

	public void save(SysMenu sysMenu) {
		dao.save(sysMenu);
	}
//	/** 当前登陆人 拥有菜单查询
//	 * @param staffNm
//	 * @return
//	 */
//	public List<Map> findListByLogin(String staffNm){
//		String sql = "select DISTINCT b.* from sys_role_menu_ref a "
//				+ " left join sys_menu b on a.menu_nm = b.nm "
//				+ " where a.role_nm in ("
//				+ " select role_nm from  sys_role_staff_ref  where staff_nm = ?"
//				+ " ) order by fcode asc";
//		return dao.findAllByParams(sql, new Object[]{staffNm});
//	}

	public List<LoadMenu> findListByLoginStaff(String staffNm) {
//		String sql="SELECT name as \"name\" ,menu_url AS \"path\",menu_icon AS \"icon\",id as \"id\",super_id as \"super_id\" FROM sys_menu WHERE isbtn = 0 AND nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm in (SELECT role_nm FROM sys_role_staff_ref WHERE staff_nm= ? )) AND (super_id IS NULL OR  super_id NOT IN (SELECT id FROM sys_menu WHERE nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm in (SELECT role_nm FROM sys_role_staff_ref WHERE staff_nm= ? )))) ORDER BY fcode ASC";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql,new Object[]{staffNm,staffNm}),LoadMenu.class);
		return EntityUtils.toEntityList(dao.findListByLoginStaff(staffNm), LoadMenu.class);
	}

	public List<LoadMenu> findAllList() {
		return EntityUtils.toEntityList(dao.findAllList(), LoadMenu.class);
	}

	public List<SysMenuDetail> findListTop() {
//		String sql="SELECT * FROM sys_menu WHERE isbtn = 0 AND  super_id is null ORDER BY fcode ASC";
//		return dao.findAllByParams(sql,new Object[]{});
		return EntityUtils.toEntityList(dao.findListTop(), SysMenuDetail.class);
	}

	public List<Integer> findSonIds(String findSonIds) {
		return dao.findSonIds(findSonIds + "%");
	}

	/**
	 * 查询最上级的菜单
	 * 
	 * @return
	 */
	public List<SysMenuTree> getTopBean(String name) {
//		StringBuffer sql = new StringBuffer("SELECT nm as \"key\",name as \"title\",id AS \"value\" FROM sys_menu WHERE isbtn = 0 and  super_id IS NULL ");
//		if(name!= null&&!name.equals("")){
//			sql.append(" AND NAME LIKE '%"+name+"%' ");
//		}
//
//		sql.append(" order by fcode asc");
//		return EntityUtils.toEntityList(dao.findAllByParams(sql+"", new Object[]{}),SysDeptTree.class);
		return EntityUtils.toEntityList(dao.getTopBean(name), SysMenuTree.class);
	}

	/**
	 * 查询最上级的菜单
	 * 
	 * @return
	 */
	public List<SysMenuTree> getTopBeanTwo(String name) {
//		StringBuffer sql = new StringBuffer("SELECT  ISBTN as \"isbtn\",MENUFLAG as \"menuflag\",MENU_URL as \"menu_url\",MENU_ICON as \"menu_icon\",FLAG as \"flag\",name as \"name\",fcode as \"fcode\",scode as \"scode\",id as \"id\",nm as \"nm\",nm as \"key\",name as \"title\",id AS \"value\" FROM sys_menu WHERE super_id IS NULL ");
//		if(name!= null&&!name.equals("")){
//			sql.append(" AND NAME LIKE '%"+name+"%' ");
//		}
//
//		sql.append(" order by fcode asc");
//		return EntityUtils.toEntityList(dao.findAllByParams(sql+"", new Object[]{}), SysMenuTree.class);
		return EntityUtils.toEntityList(dao.getTopBeanTwo(name), SysMenuTree.class);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	public List<Map> findBySuperId(int superId) {
//		String sql = "select son.*,son.id as \"key\",son.name as \"title\",son.id AS \"value\",father.name as \"superName\" from sys_menu as son left join sys_menu as father on son.super_id = father.id where son.super_id = ? order by son.fcode desc ";
//		return  dao.findAllByParams(sql, new Object[]{superId});
		return dao.findBySuperId(superId);
	}

	/**
	 * 页面初始加载二级菜单
	 * 
	 * @param staffNm
	 * @return
	 */
	public List<LoadMenu> findBySuperIdNew(String staffNm) {
//		String sql = "SELECT name as \"name\" ,menu_url AS \"path\",menu_icon AS \"icon\",id as \"id\",super_id as \"super_id\",isbtn  AS \"hideInMenu\" FROM sys_menu WHERE isbtn = 0 AND  super_id IS NOT NULL AND nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm in (SELECT role_nm FROM sys_role_staff_ref WHERE  staff_nm = ? ) ) ORDER BY fcode ASC";//nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm =(SELECT role_nm FROM sys_role_staff_ref WHERE staff_nm= ? )) AND
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[]{staffNm}),LoadMenu.class);
		return EntityUtils.toEntityList(dao.findBySuperIdNew(staffNm), LoadMenu.class);
	}

	public List<LoadMenu> findAllBySuperId() {
//		String sql = "SELECT name as \"name\" ,menu_url AS \"path\",menu_icon AS \"icon\",id as \"id\",super_id as \"super_id\",isbtn  AS \"hideInMenu\" FROM sys_menu WHERE isbtn = 0 AND  super_id IS NOT NULL AND nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm in (SELECT role_nm FROM sys_role_staff_ref WHERE  staff_nm = ? ) ) ORDER BY fcode ASC";//nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm =(SELECT role_nm FROM sys_role_staff_ref WHERE staff_nm= ? )) AND
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[]{staffNm}),LoadMenu.class);
		return EntityUtils.toEntityList(dao.findAllBySuperId(), LoadMenu.class);
	}

	public List<LoadMenu> findByIdLogin(Integer id) {
//		String sql = "SELECT name as \"name\" ,menu_url AS \"path\",menu_icon AS \"icon\",id as \"id\",super_id as \"super_id\" FROM sys_menu WHERE id = ?  ORDER BY fcode ASC";
//		return  EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[]{id}),LoadMenu.class);
		return EntityUtils.toEntityList(dao.findByIdLogin(id), LoadMenu.class);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	public SysMenu getSonBean(int superId) {
//		String sql = "select * from sys_menu where super_id = ? and rownum = 1  order by fcode desc";
//		return  dao.findBySQL(sql, new Object[]{superId}, SysMenu.class);
		return dao.getSonBean(superId);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	public SysMenu getSuperBean() {
//		String sql = "select * from sys_menu where LENGTH(fcode)= 3 and rownum = 1  order by fcode desc ";
//		return  dao.findBySQL(sql, new Object[]{}, SysMenu.class);
		return dao.getSuperBean();
	}

	/**
	 * 获取最新的Scode
	 * 
	 * @param superId
	 * @return
	 */
	public SysMenu getNewScode(Integer superId) {
		String sql = "";
		if (superId == 0) {
//			sql = "SELECT * FROM sys_menu WHERE super_id IS NULL and rownum = 1  ORDER BY fcode DESC";
//			return  dao.findBySQL(sql, new Object[]{}, SysMenu.class);
			return dao.getSysMenuBySuperIdIsNull();
		} else {
//			sql = "SELECT * FROM sys_menu WHERE super_id = ? and rownum = 1  ORDER BY fcode DESC";
//			return  dao.findBySQL(sql, new Object[]{superId}, SysMenu.class);
			return dao.getSysMenuBySuperId(superId);
		}
	}

	public List<SysMenu> findSonScodes(Integer superId, String fix, String scode) {
		return dao.findSonScodes(superId, fix, scode);
	}

	/**
	 * 页面初始加载二级菜单
	 * 
	 * @return
	 */
	public List<SysMenuTree> findByNew() {
//		String sql = "SELECT nm as \"key\",name as \"title\",nm AS \"value\",super_id as \"super_id\" FROM sys_menu WHERE isbtn = 0 and super_id IS NOT NULL ORDER BY scode ASC";
//		return  EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[]{}),SysDeptTree.class);
		return EntityUtils.toEntityList(dao.findByNew(), SysMenuTree.class);
	}

	public List<SysMenuTree> findByNewTwo() {
//		String sql = "SELECT  ISBTN as \"isbtn\",MENUFLAG as \"menuflag\",MENU_URL as \"menu_url\",MENU_ICON as \"menu_icon\",FLAG as \"flag\",name as \"name\",fcode as \"fcode\",scode as \"scode\",id as \"id\",nm as \"nm\", nm as \"key\",name as \"title\",nm AS \"value\",super_id as \"super_id\" FROM sys_menu WHERE super_id IS NOT NULL ORDER BY fcode ASC";
//		return  EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[]{}),SysMenuTree.class);
		return EntityUtils.toEntityList(dao.findByNewTwo(), SysMenuTree.class);
	}

	/**
	 * 获取最新的Fcode
	 * 
	 * @param superId
	 * @return
	 */
	public SysMenu getNewFcode(Integer superId) {

		if (superId == 0) {
			SysMenu sysMenu = new SysMenu();
			sysMenu.setFcode("");
			return sysMenu;
		} else {
			return dao.getNewFcode(superId);
		}

	}

	/**
	 * 删除当前菜单以及子菜单
	 * 
	 * @param fcode
	 * @return
	 */
	@Transactional
	public boolean removeDepts(String fcode) {
		// 查出需要删除的菜单及子菜单
		List<SysMenu> mlist = dao.findByFcode(fcode + "%", fcode);
		if (mlist.size() == 0) {
			return false;
		}
		// 遍历删除授权关系
		for (SysMenu mp : mlist) {
//			String deleteRefSql = "DELETE FROM sys_role_menu_ref WHERE menu_nm = ?";
//			dao.exectueSQL(deleteRefSql, new Object[]{mp.get("nm")});
			dao.deleteRefSql(mp.getNm());
		}
		// 删除菜单及子菜单
//		String sql = "delete from sys_menu where fcode like ? or fcode = ?";
//		dao.exectueSQL(sql, new Object[]{fcode+"%",fcode});
		return dao.deleteByFcodes(fcode + "%", fcode) > 0 ? true : false;
	}

	/**
	 * 分页查询
	 *
	 * @param lyhtPageVO
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public LyhtResultBody<List<SysMenu>> page(LyhtPageVO lyhtPageVO, SysMenu sysMenu) {
		// 查询sql
//		StringBuffer querySql = new StringBuffer(" select * from sys_menu where 1=1 ");
//		// 条件
//		List<Object> objValue = new ArrayList<Object>();
//		if (sysMenu != null) {
//			// 名称
//			if (StringUtils.isNotBlank(sysMenu.getName())) {
//				querySql.append(" and name like ? ");
//				objValue.add(" %" + sysMenu.getName() + "% ");
//			}
//		}
//		// 分页,排序
//		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
//
//		dao.findPageAndSortByParams(querySql.toString(), pageable, objValue.toArray());
		// 结果集
//		String jsonString = JSON.toJSONString(page.getContent());
		// List<SysMenu> resultList = JSON.parseArray(jsonString, SysMenu.class);
		Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent() - 1, lyhtPageVO.getPageSize());
		Page<SysMenu> page = dao.findListByLike(sysMenu.getName(), pageable);
		LyhtPageVO pageVO = new LyhtPageVO(page.getNumber(), page.getTotalPages(), page.getSize(),
				page.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(page.getContent(), pageVO);
	}

	public List<LoadMenu> loadMenu(HttpServletRequest request) {
		List<LoadMenu> list = null;
		// 获取session中的当前登录的用户信息
		Object obj = request.getSession().getAttribute(Constants.SESSION_ACCT);
		if (obj != null) {
			SysAcct acct = (SysAcct) obj;
			// 获取父级菜单
			if (acct.getName().equals("admins")) {
				list = this.findAllList();
			} else {
				list = this.findListByLoginStaff(acct.getStaffNm());
			}
			// 获取子级菜单
			List<LoadMenu> mapList = null;
			if (acct.getName().equals("admins")) {
				mapList = this.findAllBySuperId();
			} else {
				mapList = this.findBySuperIdNew(acct.getStaffNm());
			}
			for (int i = 0; i < mapList.size(); i++) {
				mapList.get(i).setChildren(getTreeList(mapList.get(i).getId(), mapList));
			}
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setChildren(getTreeList(list.get(i).getId(), mapList));
			}
			// 去重使用的集合
			// List<String> sonMenuS=new ArrayList<>();
			// 遍历父级菜单
//			for (int i = 0;i<list.size();i++){
//				//接收到当前对象
//				LoadMenu mp = list.get(i);
//				//判断对象是不是父级菜单
//				if (mp.getSuper_id() != null && !mp.getSuper_id().equals("")){
//					//判断子级菜单是否已遍历过了
//					if(arraySearch(mp.getSuper_id()+"",sonMenuS)){
//						//添加到遍历集合里防止重复
//						sonMenuS.add(mp.getSuper_id()+"");
//						//获得子级菜单的父级
//						LoadMenu mpr =  this.findByIdLogin(Integer.parseInt(mp.getSuper_id()+"")).get(0);
//						List<LoadMenu> mList = new ArrayList<>();
//						//添加菜单
//						mList.add(mp);
//						for(int x=0;x<list.size();x++){
//							//接收到当前for循环对象
//							LoadMenu mpSon = list.get(x);
//							//判断出子级菜单
//							if (mpSon.getSuper_id() != null && !mpSon.getSuper_id().equals("")){
//								if(mpSon.getId().equals(mp.getId())){
//									continue;
//								}
//								//判断是否是同一子集的菜单
//								if(mpSon.getSuper_id().equals(mp.getSuper_id())){
//									//添加到父级菜单中
//									mList.add(mpSon);
//									//从原始目录中删除该项
//									list.remove(x);
//									x-=1;
//								}
//							}
//						}
//						//将子级菜单添加到父级菜单下
//						mpr.setChildren(mList);
//						//mp.put("authority",acct);//将当前用户添加到菜单下
//						list.set(i,mpr);
//						continue;
//					}
//				}
//				List<LoadMenu> mList = new ArrayList<>();
//				//循环查找父级菜单下的子级并添加进菜单集合中
//				for (int j = 0 ; j<mapList.size();j++){
//					if(mapList.get(j).getSuper_id() == mp.getId()){
//						int hideInMenu = Integer.parseInt(mapList.get(j).getHideInMenu()+"0");
//						if(hideInMenu>0){
//							mapList.get(j).setHideInMenu(true);
//						}else {
//							mapList.get(j).setHideInMenu(false);
//						}
//						mList.add(mapList.get(j));
//					}
//				}
//				//将子级菜单添加到父级菜单下
//				mp.setChildren(mList);
//				//mp.put("authority",acct);//将当前用户添加到菜单下
//				list.set(i,mp);
//			}
		}
		return list;
	}

	private List<SysMenuTree> deleteChildren(List<SysMenuTree> list) {
		for (int i = 0; i < list.size(); i++) {
			SysMenuTree mp = list.get(i);
			List<SysMenuTree> sonChildren = mp.getChildren();
			if (sonChildren.size() == 0) {
				mp.setChildren(null);
			} else {
				mp.setChildren(deleteChildren(sonChildren));
			}
			list.set(i, mp);
		}
		return list;
	}

	// 递归查询
	private List<LoadMenu> getTreeList(String parentId, List<LoadMenu> sonList) {
		List<LoadMenu> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				LoadMenu mp = sonList.get(i);
				// 当前自己为自己时进入方法
				if (StringUtils.equals(mp.getSuper_id(), parentId)) {
					List<LoadMenu> m = getTreeList(mp.getId(), sonList);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}

	private Boolean arraySearch(String menuId, List<String> strings) {
		for (String x : strings) {
			if (x.equals(menuId)) {
				return false;
			}
		}
		return true;
	}

	public LyhtResultBody<List<Map<String, Object>>> tree() {
		List<Map<String, Object>> list = dao.tree();
		list = toTree(list, "", true, 0, null, new ArrayList<>());
		return new LyhtResultBody<>(list);
	}

	/**
	 * 转为树形结构
	 *
	 * @param mapList
	 * @param pCode   父节点编码
	 * @param isOne   是否第一次进入
	 * @param level   级别
	 * @param parent  父节点的序列号 1.1.2
	 * @param parents 配合前端做查询时展开所用到的，为当前节点所有父节点编码
	 * @return
	 */
	public List<Map<String, Object>> toTree(List<Map<String, Object>> mapList, String pCode, Boolean isOne, int level,
			String parent, List<String> parents) {
		List<Map<String, Object>> rData = new ArrayList<>();
		// 定义序号
		int serial = 1;
		for (Map<String, Object> map : mapList) {
			Map<String, Object> data = new HashMap<>();
			String parentCode = map.get("super_id") != null ? map.get("super_id").toString() : "",
					cityCode = map.get("id") != null ? map.get("id").toString() : "";
			if (parentCode.equals(pCode) || (cityCode.equals(pCode)) && isOne) {
				List<String> tempParents = new ArrayList<>();
				tempParents.addAll(parents);
				tempParents.add(cityCode);
				// 前端处理缩进时使用
				data.put("level", level);
				String serialS = isOne ? serial + "" : parent + "." + serial;
				// 寻找该节点的子节点
				List<Map<String, Object>> children = toTree(mapList, cityCode, false, level + 1, serialS, tempParents);
				data.putAll(map);
				data.put("parents", parents);
				rData.add(data);
				data.put("children", children);
				if (!isOne) {
					data.put("serial", parent + "." + serial);
				} else {
					data.put("serial", serial);
				}
				serial++;
			}
		}
		return rData;
	}

}
