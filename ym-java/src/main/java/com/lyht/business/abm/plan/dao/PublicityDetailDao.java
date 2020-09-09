package com.lyht.business.abm.plan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lyht.business.abm.plan.entity.PublicityDetailEntity;
import com.lyht.business.abm.plan.entity.PublicityEntity;

public interface PublicityDetailDao extends JpaRepository<PublicityDetailEntity, Integer> {

	@Query(value = "select ifnull(abm.protocol_state,0) as protocolState, "
			+ "cc.end_time,a.fh_nm as fhNm,a.gs_state as gsState,a.fh_state as fhstate, "
			+ "a.id,a.nm as owner_nm,a.process_id, "
			+ " (select merger_name from pub_region b WHERE b.city_code=a.region)as  region, a.name,id_card idCard, "
			+ "(select name from pub_dict_value b where b.nm=a.national )national, "
			+ "(select name from pub_dict_value b where b.nm=a.households_type )households_type,i_population, "
			+ "(select name from pub_dict_value b where b.nm=a.scope )scope,in_map "
			+ "	from t_abm_publicity_details cc LEFT JOIN t_info_owner_impl a ON cc.owner_nm=a.nm "
			+ "	LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
			+ "LEFT JOIN t_abm_total_state abm ON abm.owner_nm=a.nm where cc.nm = :nm ", nativeQuery = true)
	List<Map> getNm(@Param("nm") String nm);
	
	@Query(value = " select t2.name,t2.id_card idCard from t_abm_publicity_details tt " + 
			" left join t_abm_land_audit t1 on tt.owner_nm = t1.id " + 
			" left join t_info_owner_impl t2 on t1.nm = t2.nm " + 
			" where tt.nm = ?1 ",nativeQuery = true)
	List<Map> getByNm(String nm);
	
	@Transactional
	@Modifying
	@Query(value = " DELETE FROM t_abm_publicity_details WHERE nm = :nm ", nativeQuery = true)
	void deleteNm(@Param("nm") String nm);

	@Query(value = " select * "
//    		+ "ab.id as  id, ab.nm as nm,ab.owner_nm as owneNm,ab.zb_type as zbType,ab.end_time as endTime "
			+ "from t_abm_publicity_details ab " + "left join t_info_owner_impl t on ab.owner_nm = t.nm "
			+ " where ab.nm = :nm ", nativeQuery = true)
	List<PublicityDetailEntity> getNms(@Param("nm") String nm);

	List<PublicityDetailEntity> findByNm(String nm);

	@Modifying
	@Query(value = "update t_abm_publicity_details set end_time =:date where nm =:nm", nativeQuery = true)
	Integer updateEndTime(@Param("nm") String nm, @Param("date") String date);

}
