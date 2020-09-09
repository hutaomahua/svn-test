package com.lyht.business.pub.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyht.business.pub.dao.PubProjectDao;
import com.lyht.business.pub.dao.PubProjectTree;
import com.lyht.business.pub.entity.PubProjectEntity;
import com.lyht.business.pub.vo.PubProjectParamVO;
import com.lyht.util.EntityUtils;

@Service("/pubProjectService")
public class PubProjectService {
	@Autowired
	private PubProjectDao dao;
	
	
	public List<Map<String,Object>> treeSelect(String code){
		List<Map<String, Object>> list = dao.treeSelect(code);
		JSONArray result = listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "id", "superId",
				"children");
		String jsonStr = JSONObject.toJSONString(result);
		list = (List<Map<String, Object>>) JSONObject.parse(jsonStr);
		return list;
	}
	

	public List<Map<String, Object>> getList() {
		List<Map<String, Object>> list = dao.getList();
		list = toTree(list, "", true, 0, null, new ArrayList<>());
		return list;
	}

	public List<Map<String, Object>> toTree(List<Map<String, Object>> mapList, String pCode, Boolean isOne,
			Integer level, String parent, List<String> parents) {
		List<Map<String, Object>> rData = new ArrayList<>();
		// 定义序号
		int serial = 1;
		for (Map<String, Object> map : mapList) {
			Map<String, Object> data = new HashMap<>();
			String parentCode = map.get("superId") != null ? map.get("superId").toString() : "",
					cityCode = map.get("id") != null ? map.get("id").toString() : "";
			if (parentCode.equals(pCode) || (cityCode.equals(pCode)) && isOne) {
				List<String> tempParents = new ArrayList<>();
				tempParents.addAll(parents);
				tempParents.add(cityCode);
				// 前端处理缩进时使用
				data.put("level", level);
				String serialS = isOne ? serial + "" : parent + "." + serial;

				serial++;
				// 寻找该节点的子节点
				List<Map<String, Object>> children = toTree(mapList, cityCode, false, level + 1, serialS, tempParents);
				data.putAll(map);
				data.put("parents", parents);
				// 判断该节点是否含有子节点 如果没有则设置标识为最后一个节点
				if (children != null && children.size() > 0) {
					data.put("children", children);
				} else {
					data.put("lastNode", true);
				}
				rData.add(data);
			}
		}
		return rData;
	}

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	public List<PubProjectTree> findSon() {
//		String sql = "select  id as \"key\",nm as \"nm\",NAME AS \"title\",id AS \"value\",super_id as \"SuperId\"  from sys_dept where super_id is not null order by fcode desc ";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[] {}), SysDeptTree.class);
		return EntityUtils.toEntityList(dao.findSon(), PubProjectTree.class);
	}

	/**
	 * 查询最上级的
	 * 
	 * @return
	 */
	public List<PubProjectTree> getTopBean(PubProjectEntity rntity, String scode) {
		return EntityUtils.toEntityList(dao.getTopBean(rntity.getName(), scode), PubProjectTree.class);
	}

	/**
	 * ` 获取最新的Fcode
	 * 
	 * @param superId
	 * @return
	 */
	public PubProjectEntity getNewFcode(int superId) {
		if (superId == 0) {
			PubProjectEntity sysDept = new PubProjectEntity();
			sysDept.setFcode("");
			return sysDept;
		} else {
			return dao.getNewFcode(superId);
		}
	}

	/**
	 * 获取最新的Scode
	 * 
	 * @param superId
	 * @return
	 */
	public PubProjectEntity getNewScode(int superId) {
//		String sql = "SELECT * FROM sys_dept WHERE super_id = ? and limit 1 ORDER BY scode DESC ";
//		return dao.findBySQL(sql, new Object[] { superId }, SysDept.class);
		return dao.getNewScode(superId);
	}

	/**
	 * 获取Fcode
	 * 
	 * @return
	 */
	public PubProjectEntity getNewFcodeTwo() {
		// String sql = "SELECT * FROM sys_dept WHERE (super_id IS NULL OR super_id =
		// '') and limit 1 ORDER BY scode DESC";
		return dao.getNewFcodeTwo();
	}

	@Transactional
	public void updateSysDeptSon(String oldfCode, String newfCode, String oldCodes) {
		dao.updateSysDeptSon(oldfCode, newfCode, oldCodes);
	}

	/**
	 * 获取树
	 * 
	 * @return
	 */
	public List<PubProjectEntity> getList(Integer superId) {
		List<PubProjectEntity> resultList = dao.getList(superId);
		return resultList;
	}

	public List<PubProjectEntity> getProjectSelect() {
		return dao.getProjectSelect();
	}

	/**
	 * 条件查询
	 * 
	 * @param pubProjectParamVO
	 * @return
	 */
	public List<PubProjectEntity> query(PubProjectParamVO pubProjectParamVO) {
		if (pubProjectParamVO == null || CollectionUtils.isEmpty(pubProjectParamVO.getFcodeList())) {
			List<PubProjectEntity> findAll = dao.findAll();
			return findAll;
		}

		List<String> fcodeList = pubProjectParamVO.getFcodeList();

		// Specification 复杂查询
		Specification<PubProjectEntity> specification = new Specification<PubProjectEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<PubProjectEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();// 查询条件集
				Expression<String> asFcode = root.get("fcode").as(String.class);
				for (String fcode : fcodeList) {
					predicates.add(cb.like(asFcode, fcode + "%"));
				}
				Predicate[] pres = new Predicate[predicates.size()];
				return cb.or(predicates.toArray(pres));
			}
		};
		List<PubProjectEntity> findAll = dao.findAll(specification);
		return findAll;
	}
	
	/**
	 * 
	 * - listToTree -
	 * <p>
	 * 方法说明
	 * <p>
	 * - 将JSONArray数组转为树状结构 - @param arr 需要转化的数据 - @param id 数据唯一的标识键值 - @param pid
	 * 父id唯一标识键值 - @param child 子节点键值 - @return JSONArray
	 */
	public static JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
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
			JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
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