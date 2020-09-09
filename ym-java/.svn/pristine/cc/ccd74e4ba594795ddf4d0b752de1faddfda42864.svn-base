package com.lyht.system.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.SysDeptDetail;
import com.lyht.system.bean.SysDeptTree;
import com.lyht.system.dao.SysDeptDao;
import com.lyht.system.pojo.SysDept;
import com.lyht.system.vo.SysDeptVo;
import com.lyht.util.EntityUtils;

/**
 * 创建人： liuamang 脚本日期:2019年2月19日 10:07:14 说明: 单位部门
 */
@Service("/sysDeptService")
public class SysDeptService {

	@Autowired
	private SysDeptDao dao;

	public LyhtResultBody<List<SysDept>> findByDeptTypeTree(String deptType) {
		List<SysDept> list = dao.findByDeptType(deptType);
		JSONArray listToTree = listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "id", "superId", "children");
		list = (List<SysDept>) JSONArray.parse(listToTree.toJSONString());
		return new LyhtResultBody<List<SysDept>>(list);
	}

	public List<SysDeptDetail> findList() {
		// String sql = "select *,(select b.name from sys_dept b where id = a.super_id)
		// as \"super_name\" from sys_dept a order by fcode";
		// return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[] {}),
		// SysDeptDetail.class);
		return dao.findList();
	}

	public SysDept findByNm(String nm) {
		return dao.findByNm(nm);
	}

	public SysDept findById(Integer nm) {
		return dao.findById(nm).get();
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	public SysDept getSonBean(int superId) {
//		String sql = "select * from sys_dept where super_id = ? and limit 1 order by fcode desc ";
//		return dao.findBySQL(sql, new Object[] { superId }, SysDept.class);
		return dao.getSonBean(superId);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	public SysDept getSuperBean() {
//		String sql = "select * from sys_dept where LENGTH(fcode)= 3  order by fcode desc limit 1 ";
//		return dao.findBySQL(sql, new Object[] {}, SysDept.class);
		return dao.getSuperBean();
	}

	/**
	 * 删除当前部门以及子部门
	 * 
	 * @param fcode
	 * @return
	 */
	@Transactional
	public boolean removeDepts(String fcode) {
//		String sql = "delete from sys_dept where fcode like ? or fcode = ?";
//		dao.exectueSQL(sql, new Object[] { fcode + "%", fcode });
		dao.deleteSysDept(fcode);
		return true;
	}

	@SuppressWarnings("rawtypes")
	public List<SysDeptVo> getUsers(Integer id) {
//		String sql = "select id as \"id\",staff_name as \"staff_name\" from sys_staff where dept_code = ?";
//		return dao.findAllByParams(sql, new Object[] { id });
		return dao.getUsers(id);
	}

	/**
	 * 查询最上级的部门
	 * 
	 * @return
	 */
	public List<SysDeptTree> getTopBean01() {
//		String sql = "SELECT  id as \"key\",nm AS \"value\",NAME AS \"title\",fcode as \"fCode\"    FROM sys_dept WHERE super_id IS NULL order by fcode asc";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[] {}), SysDeptTree.class);
		return EntityUtils.toEntityList(dao.getTopBean01(), SysDeptTree.class);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<SysDeptTree> findBySuperId01(int superId) {
//		String sql = "select  id as \"key\",nm AS \"value\",NAME AS \"title\",fcode as \"fCode\"   from sys_dept where super_id = ? order by fcode desc ";
//		return dao.findAllByParams(sql, new Object[] { superId });
		return EntityUtils.toEntityList(dao.findBySuperId01(superId), SysDeptTree.class);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	public List<SysDeptTree> findSon01() {
//		String sql = "select  id as \"key\",nm AS \"value\",NAME AS \"title\",fcode as \"fCode\",super_id as \"SuperId\"    from sys_dept where super_id is not null order by fcode desc ";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[] {}), SysDeptTree.class);
		return EntityUtils.toEntityList(dao.findSon01(), SysDeptTree.class);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	public List<SysDeptTree> findBySuperId(int superId) {
//		String sql = "select  id as \"key\",nm as \"nm\",NAME AS \"title\",id AS \"value\"    from sys_dept where super_id = ? order by fcode desc ";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[] { superId }), SysDeptTree.class);
		return EntityUtils.toEntityList(dao.findBySuperId(superId), SysDeptTree.class);
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	public List<SysDeptTree> findSon() {
//		String sql = "select  id as \"key\",nm as \"nm\",NAME AS \"title\",id AS \"value\",super_id as \"SuperId\"  from sys_dept where super_id is not null order by fcode desc ";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[] {}), SysDeptTree.class);
		return EntityUtils.toEntityList(dao.findSon(), SysDeptTree.class);
	}

	/**
	 * 查询最上级的部门
	 * 
	 * @return
	 */
	public List<SysDeptTree> getTopBean(SysDept sysDept) {
//		List<Object> objValue = new ArrayList<Object>();
//		StringBuffer sql = new StringBuffer(
//				"SELECT  id as \"key\",nm as \"nm\",NAME AS \"title\",id AS \"value\"   FROM sys_dept WHERE 1=1 ");
//		if (CommonUtil.getLength(sysDept.getName()) > 0) {
//			sql.append(" and name like ? ");
//			objValue.add("%" + sysDept.getName() + "%");
//		} else {
//			sql.append(" and super_id IS NULL ");
//		}
//		sql.append(" order by fcode asc");
//		return EntityUtils.toEntityList(dao.findAllByParams(sql.toString(), objValue.toArray()), SysDeptTree.class);
		return EntityUtils.toEntityList(dao.getTopBean(sysDept.getName(), sysDept.getDeptType()), SysDeptTree.class);
	}

	/**
	 * 获取最新的Scode
	 * 
	 * @param superId
	 * @return
	 */
	public SysDept getNewScode(int superId) {
//		String sql = "SELECT * FROM sys_dept WHERE super_id = ? and limit 1 ORDER BY scode DESC ";
//		return dao.findBySQL(sql, new Object[] { superId }, SysDept.class);
		return dao.getNewScode(superId);
	}

	/**
	 * 获取最新的Fcode
	 * 
	 * @param superId
	 * @return
	 */
	public SysDept getNewFcode(int superId) {
		if (superId == 0) {
			SysDept sysDept = new SysDept();
			sysDept.setFcode("");
			return sysDept;
		} else {
			return dao.getNewFcode(superId);
		}
	}

	/**
	 * 获取Fcode
	 * 
	 * @return
	 */
	public SysDept getNewFcodeTwo() {
		// String sql = "SELECT * FROM sys_dept WHERE (super_id IS NULL OR super_id =
		// '') and limit 1 ORDER BY scode DESC";
		return dao.getNewFcodeTwo();
	}

	/**
	 * 获取组织部门详情
	 * 
	 * @param id
	 * @return
	 */

	@Transactional
	public void updateSysDeptSon(String oldfCode, String newfCode, String oldCodes) {
		dao.updateSysDeptSon(oldfCode, newfCode, oldCodes);
	}

	public void save(SysDept sysDept) {
		dao.save(sysDept);
	}

	/**
	 * 分页查询
	 *
	 * @param lyhtPageVO
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public LyhtResultBody<List<SysDeptDetail>> page(LyhtPageVO lyhtPageVO, SysDept sysDept) {
		Pageable pageable = LyhtPageVO.getPageable(lyhtPageVO);
		Page<SysDeptDetail> resultList = dao.findListOrName(sysDept.getName(), pageable);
		LyhtPageVO pageVO = new LyhtPageVO(resultList.getNumber(), resultList.getTotalPages(), resultList.getSize(),
				resultList.getTotalElements(), lyhtPageVO.getSorter());

		return new LyhtResultBody<>(resultList.getContent(), pageVO);
	}

	public JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
		JSONArray r = new JSONArray();
		JSONObject hash = new JSONObject();
		// 将数组转为Object的形式，key为数组中的id
		for (int i = 0; i < arr.size(); i++) {
			JSONObject json = (JSONObject) arr.get(i);
			hash.put(json.getString(id), json);
		}
		// 遍历结果集
		for (int j = 0; j < arr.size(); j++) {
			// 单条记录
			JSONObject aVal = (JSONObject) arr.get(j);
			// 在hash中取出key为单条记录中pid的值
			JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid) + "");
			// 如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
			if (hashVP != null) {
				// 检查是否有child属性
				if (hashVP.get(child) != null) {
					JSONArray ch = (JSONArray) hashVP.get(child);
					ch.add(aVal);
					hashVP.put(child, ch);
				} else {
					JSONArray ch = new JSONArray();
					ch.add(aVal);
					hashVP.put(child, ch);
				}
			} else {
				r.add(aVal);
			}
		}
		return r;
	}

}
