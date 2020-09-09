package com.lyht.business.doc.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.doc.entity.DocDistribute;

public interface DocDistributeDao extends JpaRepository<DocDistribute, Integer> {

	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT d.ID id,d.NM nm,d.source_table sourceTable,i.nm dataNm,i.d_name dName,d.doc_type docType,d.issue_time issueTime,"
			+ " s.staff_name staffName,s.nm staffNm,iss.staff_name issueName,iss.nm issueNm,(select count(1) from pub_files where table_pk_column = i.nm) fujian"
			+ " FROM t_doc_distribute d left join t_doc_info i on d.data_nm = i.nm"
			+ " left join sys_staff s on d.staff_nm = s.nm left join sys_staff iss on d.issue_staff_nm = iss.nm"
			+ " where d.issue_staff_nm = :issueNm and doc_type = :docType ", countQuery = "SELECT count(1)"
					+ " FROM t_doc_distribute d left join t_doc_info i on d.data_nm = i.nm"
					+ " left join sys_staff s on d.staff_nm = s.nm left join sys_staff iss on d.issue_staff_nm = iss.nm"
					+ " where d.issue_staff_nm = :issueNm and doc_type = :docType", nativeQuery = true)
	public Page<Map> page(@Param("issueNm") String issueNm, @Param("docType") String docType, Pageable pageable);

	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT d.ID id,d.NM nm,d.source_table sourceTable,i.nm dataNm,i.d_name dName,d.doc_type docType,d.issue_time issueTime,"
			+ " s.staff_name staffName,s.nm staffNm,iss.staff_name issueName,iss.nm issueNm"
			+ " FROM t_doc_distribute d left join t_doc_info i on d.data_nm = i.nm"
			+ " left join sys_staff s on d.staff_nm = s.nm left join sys_staff iss on d.issue_staff_nm = iss.nm"
			+ " where d.staff_nm = :staffNm and doc_type = :docType ", countQuery = "SELECT count(1)"
					+ " FROM t_doc_distribute d left join t_doc_info i on d.data_nm = i.nm"
					+ " left join sys_staff s on d.staff_nm = s.nm left join sys_staff iss on d.issue_staff_nm = iss.nm"
					+ " where d.staff_nm = :staffNm and doc_type = :docType", nativeQuery = true)
	public Page<Map> page01(@Param("staffNm") String staffNm, @Param("docType") String docType, Pageable pageable);

	@Query(value = "SELECT COUNT(1) FROM t_doc_distribute WHERE data_nm = :dataNm AND staff_nm = :staffNm", nativeQuery = true)
	public Integer getCount(@Param("dataNm") String dataNm,@Param("staffNm") String staffNm);

	@Modifying
	@Query(value = "DELETE FROM t_doc_distribute WHERE DATA_NM = :dataNm ", nativeQuery = true)
	public Integer deteleByDataNm(@Param("dataNm") String dataNm);
}
