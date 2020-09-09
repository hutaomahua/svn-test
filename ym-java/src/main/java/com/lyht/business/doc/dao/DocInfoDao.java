package com.lyht.business.doc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.doc.entity.DocInfo;

@Repository
public interface DocInfoDao extends JpaRepository<DocInfo, Integer> {

	@Query(value = "select i.nm,i.id,i.d_code dCode,d.sub_name as subName,d.nm as subNm,sta.nm issueNm,sta.staff_name issueName,"
			+ " i.d_name as dName,s.staff_name as staffName,dept.name as deptName,dept.nm as deptNm,s.nm as staffNm,"
			+ " i.pigeonhole_date pigeonholeDate,r.city_code cityCode,r.name region,r.merger_name mergerName,"
			+ " i.last_time as lastTime,i.source,i.flag,i.remark,0 state,dd.doc_type docType,(select count(1) from pub_files where table_pk_column = i.nm) fujian from t_doc_distribute dd"
			+ " left join t_doc_info i on dd.data_nm = i.nm LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm"
			+ " left join sys_staff s on i.staff_nm = s.nm  left join sys_dept dept on i.dept_nm = dept.nm"
			+ " left join pub_region r on i.region = r.city_code left join sys_staff sta on dd.issue_staff_nm = sta.nm"
			+ " where dd.staff_nm = :staffNm and if(:docType is not null, dd.doc_type = :docType, 1=1)", countQuery = "SELECT COUNT(1) FROM (select i.nm,i.id,i.d_code dCode,d.sub_name as subName,d.nm as subNm,"
					+ " i.d_name as dName,s.staff_name as staffName,dept.name as deptName,dept.nm as deptNm,s.nm as staffNm,"
					+ " i.pigeonhole_date pigeonholeDate,r.city_code cityCode,r.name region,r.merger_name mergerName,"
					+ " i.last_time as lastTime,i.source,i.flag,i.remark,0 state from t_doc_distribute dd"
					+ " left join t_doc_info i on dd.data_nm = i.nm LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm"
					+ " left join sys_staff s on i.staff_nm = s.nm  left join sys_dept dept on i.dept_nm = dept.nm"
					+ " left join pub_region r on i.region = r.city_code "
					+ " where dd.staff_nm = :staffNm and if(:docType is not null, dd.doc_type = :docType, 1=1)) count", nativeQuery = true)
	public Page<Map> shareDoc(@Param("staffNm") String staffNm, @Param("docType") String docType, Pageable pageable);

	@Query(value = "select i.id,i.nm,i.doc_number docNumber,i.d_type dType,i.d_code dCode,dir.sub_name subName,dir.nm dirNm,i.d_name dName,d.`name` deptName,"
			+ " d.nm deptNm,s.staff_name staffName,s.nm staffNm,i.pigeonhole_date pigeonholeDate,i.remark,i.flag,"
			+ " i.last_time lastTime,i.source,o.xingming ownerName,r.merger_name region,r.nm regionNm,r.`name` regionName"
			+ " from t_doc_info i left join pub_region r on i.region = r.nm"
			+ " left join t_doc_directory dir on i.d_directory_type = dir.nm left join t_info_owner o on i.owner_nm = o.nm"
			+ " left join  sys_staff s on i.staff_nm = s.nm left join sys_dept d on i.dept_nm = d.nm  where 1 = 1  "
			+ " if(:word is not null, and d_name like CONCAT('%',:word,'%') or dir.sub_name like CONCAT('%',:word,'%') "
			+ " or s.staff_name like CONCAT('%',:word,'%') or d.`name` like CONCAT('%',:word,'%') or o.xingming like CONCAT('%',:word,'%'))", nativeQuery = true)
	public List<Map> serach(@Param("word") String word);

	/**
	 * 分页查询
	 * 
	 * @param type
	 * @param word
	 * @param dType
	 * @param level
	 * @return
	 */
	@Query(value = "select i.nm,i.id,i.d_code dCode,d.sub_name as subName,d.nm as subNm,"
			+ " i.d_name as dName,s.staff_name as staffName,dept.name as deptName,dept.nm as deptNm,s.nm as staffNm,"
			+ " i.pigeonhole_date pigeonholeDate,r.city_code cityCode,r.name region,r.merger_name mergerName,"
			+ " i.last_time as lastTime,i.source,i.flag,i.remark, 1 state from t_doc_info i"
			+ " LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm left join sys_staff s on i.staff_nm = s.nm "
			+ " left join sys_dept dept on i.dept_nm = dept.nm left join pub_region r on i.region = r.city_code "
			+ " WHERE i.d_type = :dType and IF (:type is not null,  d.nm = :type,1=1)"
			+ " and dept.fcode like CONCAT('%',:fCode,'%')"
			+ " and IF (:type is not null,  d.nm = :type,1=1) "
			+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,'%') or dept.name like CONCAT('%',:word,'%') or s.staff_name like CONCAT('%',:word,'%'),1=1) "
			+ " UNION"
			+ " select i.nm,i.id,i.d_code dCode,d.sub_name as subName,d.nm as subNm,"
			+ " i.d_name as dName,s.staff_name as staffName,dept.name as deptName,dept.nm as deptNm,s.nm as staffNm,"
			+ " i.pigeonhole_date pigeonholeDate,r.city_code cityCode,r.name region,r.merger_name mergerName,"
			+ " i.last_time as lastTime,i.source,i.flag,i.remark,0 state from t_doc_distribute dd"
			+ " left join t_doc_info i on dd.data_nm = i.nm LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm "
			+ " left join sys_staff s on i.staff_nm = s.nm  left join sys_dept dept on i.dept_nm = dept.nm"
			+ " left join pub_region r on i.region = r.city_code where dd.staff_nm = :staffNm"
			+ " and i.d_type = :dType "
			+ " and IF (:type is not null,  d.nm = :type,1=1) "
			+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,'%') or dept.name like CONCAT('%',:word,'%') or s.staff_name like CONCAT('%',:word,'%'),1=1)"
			+ " order by pigeonholeDate desc",
			countQuery = "SELECT (select count(1) from t_doc_info i"
					+ " LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm "
					+ " left join sys_staff s on i.staff_nm = s.nm left join sys_dept dept on i.dept_nm = dept.nm"
					+ " left join pub_region r on i.region = r.city_code "
					+ " WHERE i.d_type = :dType and IF (:type is not null,  d.nm = :type,1=1)"
					+ " and dept.fcode like CONCAT('%',:fCode,'%') "
					+ " and IF (:type is not null,  d.nm = :type,1=1) "
					+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,'%') or dept.name like CONCAT('%',:word,'%') or s.staff_name like CONCAT('%',:word,'%'),1=1) "
					+ " + "
					+ " (select count(1) from t_doc_distribute dd "
					+ " left join t_doc_info i on dd.data_nm = i.nm LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm "
					+ " left join sys_staff s on i.staff_nm = s.nm left join sys_dept dept on i.dept_nm = dept.nm"
					+ " left join pub_region r on i.region = r.city_code where dd.issue_staff_nm = :staffNm"
					+ " and i.d_type = :dType and IF (:type is not null,  d.nm = :type,1=1) "
					+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,'%') or dept.name like CONCAT('%',:word,'%') or s.staff_name like CONCAT('%',:word,'%'),1=1)",nativeQuery = true)
//	@Query(value = "select i.nm,i.id,i.d_code dCode,d.sub_name as subName,d.nm as subNm,"
//			+ " i.d_name as dName,s.staff_name as staffName,"
//			+ " dept.name as deptName,dept.nm as deptNm,s.nm as staffNm,i.pigeonhole_date pigeonholeDate,"
//			+ " r.city_code cityCode,r.name region,r.merger_name mergerName,"
//			+ " i.last_time as lastTime,i.source,i.flag,i.remark,(select count(1) from pub_files where table_pk_column = i.nm) fujian  "
//			+ "from t_doc_info i "
//			+ " LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm  "
//			+ " left join sys_staff s on i.staff_nm = s.nm  " + " left join sys_dept dept on i.dept_nm = dept.nm "
//			+ "  left join pub_region r on i.region = r.city_code "
//			+ " WHERE i.d_type = :dType and IF (:type is not null,  d.nm = :type,1=1) "
//			+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,',%') or dept.name like CONCAT('%',:word,',%') ,1=1) "
//			+ "	order by i.pigeonhole_date desc ", countQuery = "Select count(1)"
//					+ " from t_doc_info i LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm "
//					+ " left join sys_staff s on i.staff_nm = s.nm "
//					+ "	left join sys_dept dept on i.dept_nm = dept.nm"
//					+ " left join pub_region r on i.region = r.city_code "
//					+ " WHERE i.d_type = :dType and IF (:type is not null,  d.nm = :type,1=1) "
//					+ " and IF (:word is not null,  d_name like CONCAT('%',:word,',%') or dept.name like CONCAT('%',:word,',%') ,1=1) "
//					+ " order by i.pigeonhole_date desc ", nativeQuery = true)
	public Page<Map> page(@Param("type") String type, @Param("word") String word, @Param("dType") String dType, 
            @Param("fCode")String fCode,@Param("staffNm")String staffNm,
            Pageable pageable);

	@Query(value = "select i.nm,i.id,i.d_code dCode,d.sub_name as subName,d.nm as subNm,"
			+ " i.d_name as dName,s.staff_name as staffName,"
			+ " dept.name as deptName,dept.nm as deptNm,s.nm as staffNm,i.pigeonhole_date pigeonholeDate,"
			+ " r.city_code cityCode,r.name region,r.merger_name mergerName,"
			+ " i.last_time as lastTime,i.source,i.flag,i.remark from t_doc_info i "
			+ " LEFT JOIN t_doc_directory d on i.d_directory_type = d.nm  "
			+ " left join sys_staff s on i.staff_nm = s.nm  " + " left join sys_dept dept on i.dept_nm = dept.nm "
			+ "  left join pub_region r on i.region = r.city_code "
			+ " WHERE i.d_type = :dType and IF (:type is not null,  d.nm = :type,1=1) "
			+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,'%') or dept.name like CONCAT('%',:word,'%'),1=1) "
			+ "	order by i.pigeonhole_date desc ", nativeQuery = true)
	public List<Map> list(@Param("type") String type, @Param("word") String word, @Param("dType") String dType);

	/**
	 * 获取分了统计图表信息
	 * 
	 * @param docStatist
	 * @return
	 */
	@Query(value = "SELECT  tt.d_type AS type,COUNT(1) AS count FROM t_doc_info tt"
			+ " LEFT JOIN pub_region AS tpr ON tt.region = tpr.nm WHERE 1=1 "
			+ " IF (:diming is not null, and REPLACE(tpr.merger_name,',','') like CONCAT(:diming,'%')) GROUP BY tt.d_type", nativeQuery = true)
	public List<Map> getTypeStatist(@Param("diming") String diming);

}
