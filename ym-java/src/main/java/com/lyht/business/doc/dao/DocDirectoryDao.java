package com.lyht.business.doc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.doc.entity.DocDirectory;

@Repository
public interface DocDirectoryDao extends JpaRepository<DocDirectory, Integer> {

	@Modifying
	@Query(value = " delete from t_doc_directory where f_code like ?1", nativeQuery = true)
	public void deleteByFCode(String fCode);

	@Modifying
	@Query(value = "UPDATE t_doc_directory SET f_code = REPLACE(f_code,:oldfCode,:newfCode),doc_type = :docType WHERE f_code LIKE :oldCodes%", nativeQuery = true)
	void updateDirSon(@Param("oldfCode") String oldfCode, @Param("newfCode") String newfCode,
			@Param("oldCodes") String oldCodes, @Param("docType") String docType);

	@Modifying
	@Query(value = "update t_doc_directory set doc_type = :docType,sub_code = :subCode,sub_name = :subName,remark=:remark,"
			+ " super_id= :superId,s_code = :sCode,f_code=:fCode where id = :id", nativeQuery = true)
	void updateDir(@Param("docType") String docType, @Param("subCode") String subCode, @Param("subName") String subName,
			@Param("remark") String remark, @Param("superId") Integer superId, @Param("id") Integer id,
			@Param("sCode") String sCode, @Param("fCode") String fCode);

	// 查询所有父级1
	@Query(value = "SELECT * "
			+ "from t_doc_directory where super_id is null and if(:docType is not null,doc_type = :docType,1=1) order by f_code", countQuery = "select count(id) from t_doc_directory where super_id is null ", nativeQuery = true)
	public Page<DocDirectory> page(@Param("docType") String docType, Pageable pageable);

	// 查询对应子级1
	@Query(value = "select * from t_doc_directory where super_id =?1 order by f_code", nativeQuery = true)
	public List<DocDirectory> getChild(Integer superId);

	// 查询所有父级1
	@Query(value = "SELECT id as 'key',id as 'value',sub_name as 'title' "
			+ "from t_doc_directory where super_id is null order by f_code", nativeQuery = true)
	public List<Map> getSuperIdSelect();

	// 查询对应子级1
	@Query(value = "SELECT id as 'key',id as 'value',sub_name as 'title' "
			+ "from t_doc_directory where super_id  =?1 order by f_code", nativeQuery = true)
	public List<Map> getChildSelect(Integer superId);

	// 查询所有父级1
	@Query(value = "SELECT id as 'key',nm as 'value',sub_name as 'title' "
			+ "from t_doc_directory where super_id is null and doc_type = ?1 order by f_code", nativeQuery = true)
	public List<Map> getDocDirSuper(String docType);

	// 查询对应子级1
	@Query(value = "SELECT id as 'key',nm as 'value',sub_name as 'title' "
			+ "from t_doc_directory where super_id  =?1 order by f_code", nativeQuery = true)
	public List<Map> getDocDirChild(Integer superId);

	/**
	 * 导出
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "select id as 'key',nm,sub_code subCode,sub_name subName,s_code sCode,f_code fCode,super_id superId,remark "
			+ "from t_doc_directory order by f_code", nativeQuery = true)
	public List<Map> getList();

	/**
	 * 查询当前父类下最大s_code
	 */
	@Query(value = "select s_code from t_doc_directory where super_id= ?1  ORDER BY s_code DESC limit 1", nativeQuery = true)
	public String findMaxSCodeBySuperId(Integer superId);

	/**
	 * 找到无父类科目的最大短编码
	 * 
	 * @return
	 */
	@Query(value = "select s_code from t_doc_directory where super_id is null order by s_code desc limit 1", nativeQuery = true)
	public String findMaxSCode();

	/**
	 * 查询最上级的部门
	 * 
	 * @return
	 */
	@Query(value = "SELECT  id AS 'value',nm AS 'key',sub_name AS title,f_code as fCode  "
			+ "  FROM t_doc_directory WHERE super_id IS NULL ORDER BY f_code ASC", nativeQuery = true)
	public List<Map> getTopBean01();

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "select  id as 'value',nm as 'key',sub_name AS title,f_code AS fCode    "
			+ "from t_doc_directory where super_id = :superId order by f_code desc ", nativeQuery = true)
	public List<Map> findBySuperId01(@Param("superId") int superId);

	/**
	 * 查询最上级的部门
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT sub_code as subCode,id AS 'key',nm AS 'value',sub_name AS title,f_code as fCode FROM t_doc_directory WHERE super_id IS NULL ORDER BY f_code ASC", nativeQuery = true)
	public List<Map> getTopBean();

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "select  sub_code as subCode,id as 'key',nm as 'value',sub_name AS title,f_code AS fCode "
			+ " from t_doc_directory where super_id = :superId order by f_code desc ", nativeQuery = true)
	public List<Map> findBySuperId(@Param("superId") int superId);

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @param superId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "select  id as 'key',nm as 'value',sub_name AS title,f_code AS fCode  from t_doc_directory where super_id = :id order by f_code desc", nativeQuery = true)
	public List<Map> findBySuperIdAndLike(@Param("id") int id);

	/**
	 * 档案编号
	 * 
	 * @param docDirectory
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT sub_code code FROM t_doc_directory WHERE nm=:nm", nativeQuery = true)
	public List<Map> findSubCode(@Param("nm") String nm);

	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT COUNT(1) count FROM t_doc_info " + "WHERE 1 = 1 AND d_code IS NOT NULL"
			+ "AND d_code LIKE :code", nativeQuery = true)
	public List<Map> findCountList(@Param("code") String code);
}
