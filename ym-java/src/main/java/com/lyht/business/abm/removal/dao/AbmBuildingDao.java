package com.lyht.business.abm.removal.dao;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmBuildingEntity;
import com.lyht.business.abm.removal.vo.PersonaWealthBuildingVO;

public interface AbmBuildingDao extends JpaRepository<AbmBuildingEntity,Integer>{
	
	@Modifying
	@Query(value = "delete from t_info_building_impl where owner_nm = :ownerNm",nativeQuery = true)
	Integer deleteByOwnerNm(@Param("ownerNm")String ownerNm);
	
//	@Query(value = " select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.scope,t6.name scopeName,tt.specifications, "
//			+ " t3.id projectNm,t3.`name` projectName,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as num,tt.unit, "
//			+ " tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark from t_info_building_impl tt "
//			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
//			+ " left join pub_region t2 on tt.region = t2.city_code "
//			+ " left join pub_project t3 on tt.project_nm = t3.id"
//			+ " left join pub_dict_value t6 on tt.scope = t6.nm "
//			+ " where owner_nm = :ownerNm", countQuery = " select count(1) from (select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code cityCode,tt.scope,tt.specifications, "
//					+ " t3.id projectId,t3.`name` projectName,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as num,tt.unit, "
//					+ " tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark " + "from t_info_building_impl tt "
//					+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
//					+ " left join pub_region t2 on tt.region = t2.city_code "
//					+ " left join pub_project t3 on tt.project_nm = t3.id"
//					+ " where owner_nm = :ownerNm) as t0", nativeQuery = true)
	@Query(value = "select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.scope,t6.name scopeName,tt.specifications,  " + 
			" t3.id projectNm,t3.`name` projectName,tt.area , tt.volume,count(file.id) AS fileCount , tt.longs ,tt.num ,tt.unit,  " +
			" tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark from t_info_building_impl tt  " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm   " + 
			" left join pub_region t2 on tt.region = t2.city_code  " + 
			" left join pub_project t3 on tt.project_nm = t3.id " + 
			" left join pub_dict_value t6 on tt.scope = t6.nm  " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_owner_impl' AND file.table_pk_column = tt.nm " +
			" where owner_nm = :ownerNm GROUP BY tt.id ",countQuery ="select count(1) from (select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.scope,t6.name scopeName,tt.specifications,  " +
					" t3.id projectNm,t3.`name` projectName,tt.area , tt.volume , tt.longs ,tt.num ,tt.unit,  " + 
					" tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark from t_info_building_impl tt  " + 
					" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm   " + 
					" left join pub_region t2 on tt.region = t2.city_code  " + 
					" left join pub_project t3 on tt.project_nm = t3.id " + 
					" left join pub_dict_value t6 on tt.scope = t6.nm  " + 
					" where owner_nm = :ownerNm) as temp",nativeQuery = true)
	Page<PersonaWealthBuildingVO> findByOwnerNmOfPersonaWealth(@Param("ownerNm") String ownerNm, Pageable pageable);

	@Query(value = " select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.scope,t6.name scopeName,tt.specifications, " + 
			" t3.id projectNm,t3.`name` projectName,tt.unit , tt.area ,tt.volume,tt.longs , tt.num , " + 
			" tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark  ,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount from t_info_building_impl tt " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
			" left join pub_region t2 on tt.region = t2.city_code " + 
			" left join pub_project t3 on tt.project_nm = t3.id" + 
			" left join pub_dict_value t6 on tt.scope = t6.nm " + 
			" where owner_nm = :ownerNm", nativeQuery = true)
	List<Map<String,Object>> findByOwnerNmOfPersonaWealth(@Param("ownerNm") String ownerNm);

	/**
	 * 通过户主nm查询附属建筑物
	 * @param ownerNm
	 * @return
	 */
	@Query(value =" SELECT tindex.*,count(file.id) AS fileCount  " +
			" FROM t_info_building_impl tindex " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_building_impl' AND file.table_pk_column = tindex.nm " +
			" WHERE tindex.owner_nm = :ownerNm GROUP BY tindex.id ",nativeQuery = true)
	List<Map> findByOwnerNm(@Param("ownerNm")String ownerNm);
}
