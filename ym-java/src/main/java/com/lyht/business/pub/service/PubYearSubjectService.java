package com.lyht.business.pub.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyht.business.pub.dao.PubYearSubjectDao;
import com.lyht.business.pub.dao.PubYearTree;
import com.lyht.business.pub.entity.PubYearSubject;
import com.lyht.util.EntityUtils;

@Service("/pubYearSubjectService")
public class PubYearSubjectService {
	@Autowired
	private PubYearSubjectDao dao;
	
	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	public List<PubYearTree> findSon() {
//		String sql = "select  id as \"key\",nm as \"nm\",NAME AS \"title\",id AS \"value\",super_id as \"SuperId\"  from sys_dept where super_id is not null order by fcode desc ";
//		return EntityUtils.toEntityList(dao.findAllByParams(sql, new Object[] {}), SysDeptTree.class);
		return EntityUtils.toEntityList(dao.findSon(), PubYearTree.class);
	}

	/**
	 * 查询最上级的
	 * 
	 * @return
	 */
	public List<PubYearTree> getTopBean(PubYearSubject rntity,String scode) {

		return EntityUtils.toEntityList(dao.getTopBean(rntity.getName(),scode), PubYearTree.class);
	}

	/**
	 * 获取最新的Fcode
	 * 
	 * @param superId
	 * @return
	 */
	public PubYearSubject getNewFcode(int superId) {
		if (superId == 0) {
			PubYearSubject sysDept = new PubYearSubject();
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
	public PubYearSubject getNewScode(int superId) {
//		String sql = "SELECT * FROM sys_dept WHERE super_id = ? and limit 1 ORDER BY scode DESC ";
//		return dao.findBySQL(sql, new Object[] { superId }, SysDept.class);
		return dao.getNewScode(superId);
	}

	/**
	 * 获取Fcode
	 * 
	 * @return
	 */
	public PubYearSubject getNewFcodeTwo() {
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
	 * @return
	 */
	public List<PubYearSubject> getList(Integer superId) {
		List<PubYearSubject> resultList = dao.getList(superId);
		return resultList;
	}

	public List<PubYearSubject> getProjectSelect(){
		return dao.getProjectSelect();
	}

}
