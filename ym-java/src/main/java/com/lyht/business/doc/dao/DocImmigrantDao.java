package com.lyht.business.doc.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.doc.entity.DocImmigrant;

@Repository
public interface DocImmigrantDao extends JpaRepository<DocImmigrant, Integer> {

	@Query(value = "SELECT i.id,i.nm,d.nm subNm,d.sub_name subName,i.d_number dNumber,i.d_name dName,"
			+ " r.city_code cityCode,r.name region,r.merger_name mergerName,i.info_table infoTable,"
			+ " s.nm as staffNm,s.staff_name as staffName,i.pigeonhole_date pigeonholeDate,"
			+ " i.remark,i.state,(CASE i.info_table when 't_info_owner' THEN o.nm WHEN 't_info_enterprise' THEN e.nm "
			+ " ELSE p.nm END) AS objectNm,(CASE i.info_table when 't_info_owner' THEN o.name WHEN 't_info_enterprise' THEN e.name "
			+ " ELSE p.type_name END) AS objectName,(CASE i.info_table when 't_info_owner' THEN '权属人' WHEN 't_info_enterprise' THEN '企事业单位' "
			+ " ELSE '个体工商户' END) AS objectType , (select count(1) from pub_files where table_pk_column = i.nm) fujian from t_doc_immigrant i"
			+ " left join t_doc_directory d ON i.d_directory_type = d.nm"
			+ " left join sys_staff s on i.staff_nm = s.nm " + " left join t_info_owner o on i.object_nm = o.nm"
			+ " left join t_info_enterprise e on i.object_nm = e.nm"
			+ " left join t_info_individual p on i.object_nm = p.nm"
			+ " left join pub_region r on i.region = r.city_code"
			+ " where 1 = 1 and IF (:type is not null,  d.nm = :type,1=1)"
			+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,',%') ,1=1) "
			+ " order by i.id desc", countQuery = "SELECT COUNT(1) from t_doc_immigrant i"
					+ " left join t_doc_directory d ON i.d_directory_type = d.nm"
					+ " left join sys_staff s on i.staff_nm = s.nm " + " left join t_info_owner o on i.object_nm = o.nm"
					+ " left join t_info_enterprise e on i.object_nm = e.nm"
					+ " left join t_info_individual_producer p on i.object_nm = p.nm"
					+ " where 1 = 1 and IF (:type is not null,  d.nm = :type,1=1)"
					+ " and IF (:word is not null,  i.d_name like CONCAT('%',:word,',%') ,1=1) ", nativeQuery = true)
	public Page<Map> page(@Param("word") String word, @Param("type") String type, Pageable pageable);

}
