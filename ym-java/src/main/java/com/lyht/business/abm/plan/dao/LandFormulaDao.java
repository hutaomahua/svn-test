package com.lyht.business.abm.plan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lyht.business.abm.plan.entity.TdPublicityEntity;

public interface LandFormulaDao extends JpaRepository<TdPublicityEntity, Integer> {

	@Query(value = " select m.state as state,  (select merger_name from pub_region b WHERE b.city_code=m.region)as  regionName,(select count(1) from pub_files where table_pk_column =m.nm) as fujian,"
			+ " m.code,m.name as homeName,m.type,m.start_time,m.end_time,m.cz_name,m.cz_time,m.create_time,m.remarks,m.id as gsId,m.nm,m.region as region "
			+ " from t_td_publicity m  LEFT JOIN pub_region AS tpr ON tpr.city_code = m.region where 1=1  and "
			+ " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and"
			+ " IF (:homeName is not null,m.name LIKE CONCAT('%',:homeName,'%'), 1=1) and  "
			+ "  IF (:startTime is not null && LENGTH(:startTime)>0,DATE_FORMAT( m.start_time, '%Y-%m-%d' ) >= DATE_FORMAT(:startTime, '%Y-%m-%d' ), 1=1) and  "
			+ "  IF (:endTime is not null && LENGTH(:endTime)>0,DATE_FORMAT( m.end_time, '%Y-%m-%d' ) <= DATE_FORMAT(:endTime, '%Y-%m-%d' ), 1=1)   "
			+ " order BY m.id ASC", countQuery = " select count(1)  "
					+ " from t_td_publicity m  LEFT JOIN pub_region AS tpr ON tpr.city_code = m.region where 1=1  and "
					+ " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and"
					+ " IF (:homeName is not null,m.name LIKE CONCAT('%',:homeName,'%'), 1=1) and  "
					+ "  IF (:startTime is not null && LENGTH(:startTime)>0,DATE_FORMAT( m.start_time, '%Y-%m-%d' ) >= DATE_FORMAT(:startTime, '%Y-%m-%d' ), 1=1) and  "
					+ "  IF (:endTime is not null && LENGTH(:endTime)>0,DATE_FORMAT( m.end_time, '%Y-%m-%d' ) <= DATE_FORMAT(:endTime, '%Y-%m-%d' ), 1=1)   "
					+ " order BY m.id ASC", nativeQuery = true)
	Page<Map> getList(Pageable pageable, @Param("region") String region, @Param("homeName") String homeName,
			@Param("startTime") String startTime, @Param("endTime") String endTime);

	@Query(value = " select * FROM t_td_publicity ORDER BY id DESC LIMIT 0,1 ", nativeQuery = true)
	List<Map> getGsCode();

	@Query(value = " select t2.`name`,tt.id ownerNm,t2.id_card idCard,tt.resolve_area as resolveArea,tt.process_id as processId,tt.nm  ,t0.`name` as targetRegion,t1.name as region from t_abm_land_audit tt "
			+ " left join pub_region t0 on tt.target_region = t0.city_code "
			+ " LEFT JOIN pub_region AS t1 ON t1.city_code = tt.source_region "
			+ " left join t_info_owner_impl t2 ON tt.nm = t2.nm "
			+ " where tt.fg_state = 0 and tt.audit_code = '64FABB5F0D' and tt.nm is not null and tt.nm != '' "
			+ " and t0.merger_name like concat('%',:region,'%')"
			+ " group by t2.nm", nativeQuery = true)
	List<Map> getUserList(@Param("region") String region);

	@Transactional
	@Modifying
	@Query(value = " DELETE FROM t_td_publicity_details WHERE nm = :nm ", nativeQuery = true)
	void deleteNm(@Param("nm") String nm);

}
