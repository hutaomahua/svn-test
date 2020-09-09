package com.lyht.system.service;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.SysStaffDetail;
import com.lyht.system.dao.SysStaffDao;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 创建人： liuamang 脚本日期:2019年2月19日 10:08:25 说明: 人员信息
 */
@Service("/sysStaffService")
public class SysStaffService {

	@Autowired
	private SysStaffDao dao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LyhtResultBody<List<SysStaff>> getStaffLikeName(String name) {
//		String sql = "select id as \"id\",nm as \"nm\",staff_name as \"staffName\" from " + "sys_staff where staff_name like '%" + name + "%' ";
//		List<Map> staffs = dao.findAllByParams(sql.toString(), new Object[] {});
//		String jsonString = JSON.toJSONString(staffs);
//		List<SysStaff> list = (List<SysStaff>)JSON.parse(jsonString);
		return new LyhtResultBody<>(dao.getStaffLikeName(name));
	}

	public void save(SysStaff sysStaff) {
		dao.save(sysStaff);
	}
//	/**
//	 * 查询角色列表信息
//	 *
//	 * @param searchBean
//	 * @return
//	 */
//	public Page<Map> getList(BaseSearchBean searchBean) {
//		// SQL 语句
//		List<Object> objValue = new ArrayList<Object>();
//		StringBuffer sql = new StringBuffer(
//				" select staff.*,c.name as acct_name,b.name as dept_name,FUN_GetDictValue(staff.staff_sex,'dict_sex') as staff_sex_name ,"
//						+ " FUN_GetDictValue(staff.staff_ethnic,'dict_ethnic') as staff_ethnic_name from sys_staff staff  "
//						+ " left join sys_dept b on staff.dept_code = b.nm "
//						+ " left join sys_acct c on staff.nm = c.staff_nm" + " where 1=1 ");
//		if (CommonUtil.getLength(searchBean.getSearchName()) > 0) {
//			sql.append(" and staff.name like ? ");
//			objValue.add("%" + searchBean.getSearchName() + "%");
//		}
//		sql.append(" order by id  desc ");
//
//		return dao.findPageByParams(sql.toString(),
//				new PageRequest(searchBean.getCurrPage() - 1, searchBean.getPageSize()), objValue.toArray());
//	}
//
//	/**
//	 * 查询单个学生对象，含字典表、角色、帐号信息
//	 *
//	 * @param nm
//	 * @return
//	 */
//	public Map getObject(int id) {
//		String sql = "select  "
//				+ " (select group_concat(name) from sys_role where nm in (select role_nm from sys_role_staff_ref where staff_nm =staff.nm )) as role_names , "
//				+ " FUN_GetDictValue(staff.staff_sex,'dict_sex') as sex_name, "
//				+ " FUN_GetDictValue(staff.staff_ethnic,'dict_ethnic') as ethnic_name, "
//				+ "acct.name as acct_name ,dept.name as dept_name , "
//				+ " staff.* from sys_staff staff left join sys_acct acct on staff.nm = acct.staff_nm "
//				+ "left join sys_dept dept on dept.nm = staff.dept_code" + " where staff.id = ?";
//		List<Map> list = dao.findAllByParams(sql, new Object[] { id });
//		if (list.size() > 0)
//			return list.get(0);
//		else
//			return new HashMap();
//	}

//	/**
//	 * 删除人员 以及相关关联关系表数据 帐号 权限
//	 *
//	 * @param ids
//	 * @return
//	 */
//	@Transactional
//	public boolean removeStaffAndRef(String ids) {
//		boolean flag = false;
//		if (CommonUtil.getLength(CommonUtil.trim(ids)) > 0) {
//			String[] idArr = ids.split(",");
//
//			deleteEntity(ids);// 删除所有人员
//
//			for (String id : idArr) {
//				// 删除帐号
//				String deleteSql = "delete from sys_acct where staff_nm = (select nm from sys_staff where id = ? )";
//				dao.exectueSQL(deleteSql, new Object[] { id });
//
//				// 删除授权信息
//			}
//		}
//		return flag;
//	}

	/**
	 * 人员部门组合树
	 *
	 * @return
	 */
	public List<Map> detail(Integer id) {
//		String sql = " select CONCAT('dept',id) as id , nm,name ,'dept' as type ,CONCAT('dept',super_id) as super_id,'../static/business/img/home.png' as icon from sys_dept union all select CONCAT('staff',id) as id,nm,staff_name, 'staff' as type ,(select CONCAT('dept',super_id) from sys_dept where nm = staff.dept_code) as super_id,'../static/business/img/user.png' as icon from sys_staff staff ";
//		return dao.findByHQL(sql, new Object[] {});
		return dao.detail(id);
	}

	/**
	 * 查询用户列表信息
	 * 
	 * @param sysstaffdetail
	 * @return
	 */
	public LyhtResultBody<List<SysStaffDetail>> page(LyhtPageVO lyhtPageVO, SysStaffDetail sysstaffdetail) {
		if (lyhtPageVO.getCurrent() == null)
			lyhtPageVO.setCurrent(1);
		if (lyhtPageVO.getPageSize() == null)
			lyhtPageVO.setPageSize(10);
		Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent() - 1, lyhtPageVO.getPageSize());
		Page<Map> resultList = dao.page(sysstaffdetail.getSearchName(), sysstaffdetail.getDept_code(),
				sysstaffdetail.getNm(), pageable);
		LyhtPageVO pageVO = new LyhtPageVO(resultList.getNumber(), resultList.getTotalPages(), resultList.getSize(),
				resultList.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(EntityUtils.toEntityList(resultList.getContent(), SysStaffDetail.class), pageVO);

	}

	/**
	 * 查询用户列表信息
	 *
	 * @param sysstaffdetail
	 * @return
	 */
	public LyhtResultBody<List<SysStaffDetail>> pages(LyhtPageVO lyhtPageVO, SysStaffDetail sysstaffdetail) {
		// SQL 语句
//		List<Object> objValue = new ArrayList<Object>();
//		StringBuffer sql = new StringBuffer(
//				"SELECT (select name from SYS_ROLE where nm IN (select role_nm from SYS_ROLE_STAFF_REF where STAFF_nm = ss.nm) and ROWNUM=1 )  as \"roleName\",(select count(*) from DEV_RUN_LOG where STAFF_ID = ss.id and PROCESS_STATUS = 'FB87618D0D') as \"runlogNumber\", ss.id as \"id\",ss.nm as \"nm\",ss.remark as \"remark\" ,ss.sort_num as \"sort_num\",ss.staff_birthday as \"staff_birthday\",ss.staff_code as \"staff_code\",ss.Ethnic AS \"staff_ethnic\",ss.staff_link as \"staff_link\",ss.staff_name as \"staff_name\",ss.staff_origin as \"staff_origin\",ss.staff_sex as \"staff_sex\",ss.staff_type as \"staff_type\",ss.state as \"state\" ,sd.name AS \"dept_code\" "
//						+ "FROM (SELECT ssc.*,pdv.name AS Ethnic FROM  sys_staff ssc LEFT JOIN pub_dict_value pdv ON  ssc.staff_ethnic = pdv.code ) ss " +
//						"LEFT JOIN sys_dept sd   ON ss.dept_code = sd.id  WHERE 1=1 ");
//		if (CommonUtil.getLength(sysstaffdetail.getDeptCode()) > 0) {
//			sql.append(" and  sd.id in  (SELECT id FROM sys_dept WHERE fcode LIKE (SELECT CONCAT(fcode,'%') FROM sys_dept sdd WHERE sdd.id = ?)) ");
//			objValue.add(sysstaffdetail.getDeptCode());
//		}
//		if (CommonUtil.getLength(sysstaffdetail.getSearchName()) > 0) {
//			sql.append(" and staff_name like ? ");
//			objValue.add("%" + sysstaffdetail.getSearchName() + "%");
//		}
//		if (CommonUtil.getLength(sysstaffdetail.getSearchName()) > 0) {
//			sql.append(" or staff_origin like ? ");
//			objValue.add(sysstaffdetail.getSearchName() + "%");
//		}
//
//		sql.append(" order by ss. ID  desc ");
//
//		// 分页,排序
//		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
//		Page<Map> page = dao.findPageByParams(sql.toString(), pageable, objValue.toArray());
//		// 结果集
//		String jsonString = JSON.toJSONString(page.getContent());
//		List<SysStaffDetail> resultList = (List<SysStaffDetail>) JSON.parse(jsonString);
		Pageable pageable = PageRequest.of(lyhtPageVO.getCurrent(), lyhtPageVO.getPageSize());
		Page<SysStaffDetail> resultList = dao.pages(sysstaffdetail.getSearchName(), sysstaffdetail.getDept_code(),
				pageable);
		LyhtPageVO pageVO = new LyhtPageVO(resultList.getNumber(), resultList.getTotalPages(), resultList.getSize(),
				resultList.getTotalElements(), lyhtPageVO.getSorter());
		return new LyhtResultBody<>(resultList.getContent(), pageVO);

	}
//	public List<Map> deail(Integer id) {
//		String sql = "select ss.id as \"id\",ss.nm as \"nm\",ss.remark as \"remark\",ss.sort_num as \"sort_num\",ss.staff_birthday as \"staff_birthday\",ss.staff_code as \"staff_code\",ss.staff_ethnic as \"staff_ethnic\",ss.staff_link as \"staff_link\",ss.staff_name as \"staff_name\",ss.staff_origin as \"staff_origin\",ss.staff_sex as \"staff_sex\",ss.staff_type as \"staff_type\",ss.state as \"state\",sd.name AS \"dept_code\",sa.name AS \"zhanghao\",sd.id as \"dept_codes\",pdv.code as \"code\"  FROM sys_staff ss LEFT JOIN sys_acct sa ON ss.nm = sa.staff_nm left join sys_dept sd ON sd.id = ss.dept_code left join pub_dict_value pdv on pdv.code = ss.staff_ethnic  WHERE ss.id = ?";
//		return dao.findAllByParams(sql, new Object[] { id });
//	}

	/**
	 * 查询所有
	 *
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Map> list(SysStaffDetail sysstaffdetail) {
		return dao.list(sysstaffdetail.getStaff_type(), sysstaffdetail.getDept_code(), sysstaffdetail.getSearchName());
	}

	/**
	 * 查询所有
	 *
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<SysStaffDetail> lists(SysStaffDetail sysstaffdetail) {
		return EntityUtils.toEntityList(
				dao.list(sysstaffdetail.getStaff_type(), sysstaffdetail.getDept_code(), sysstaffdetail.getSearchName()),
				SysStaffDetail.class);
	}

	public SysStaff findByNm(String nm) {
		return dao.findByNm(nm);
	}

	public SysStaff findByUuid(String uuid) {
		return dao.findByUuid(uuid);
	}
}
