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

import com.lyht.business.abm.removal.entity.AbmLandEntity;
import com.lyht.business.abm.removal.vo.PersonaWealthLandVO;

public interface AbmLandDao extends JpaRepository<AbmLandEntity,Integer>{
	
	@Modifying
	@Query(value = "delete from t_info_land_impl where owner_nm = :ownerNm",nativeQuery = true)
	Integer deleteByOwnerNm(@Param("ownerNm")String ownerNm);

	@Query(value = "select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region, t7.name allTypeName,tt.all_type allType," + 
			" t3.nm typeOne,t3.name oneName,t4.nm typeTwo,t4.name twoName,t5.nm typethree,t5.name threeName,tt.area,tt.scope,t6.name scopeName,tt.land_nm landNm,tt.unit,tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark,count(file.id) AS fileCount " +
			" from t_info_land_impl tt " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
			" left join pub_region t2 on tt.region = t2.city_code " + 
			" left join pub_dict_value t3 on tt.type_one = t3.nm  " + 
			" left join pub_dict_value t4 on tt.type_two = t4.nm  " + 
			" left join pub_dict_value t5 on tt.type_three = t5.nm " +
			" left join pub_dict_value t6 on tt.scope = t6.nm " + 
			" left join pub_dict_value t7 on tt.all_type = t7.nm " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_land_impl' AND file.table_pk_column = tt.nm " +
			" where owner_nm = :ownerNm  GROUP BY tt.id ",countQuery = "select count(1) from(select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code cityCode, " +
					" t3.nm oneNm,t3.name oneName,t4.nm twoNm,t4.name twoName,t5.nm threeNm,t5.name threeName,tt.area,tt.scope,tt.land_nm landNm,tt.unit,tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark " + 
					" from t_info_land_impl tt " + 
					" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
					" left join pub_region t2 on tt.region = t2.city_code " + 
					" left join pub_dict_value t3 on tt.type_one = t3.nm  " + 
					" left join pub_dict_value t4 on tt.type_two = t4.nm  " + 
					" left join pub_dict_value t5 on tt.type_three = t5.nm " + 
					" where owner_nm = :ownerNm) as t0 ",nativeQuery = true)
	public Page<PersonaWealthLandVO> findByOwnerNmOfPersonaWealth(@Param("ownerNm")String ownerNm,Pageable pageable);
	
	@Query(value = "select * from t_info_land_impl where owner_nm = :ownerNm and type_two = :typeTwo limit 1",nativeQuery = true)
	AbmLandEntity getByOwnerNmAndTypeTwo(@Param("ownerNm")String ownerNm,@Param("typeTwo")String typeTwo);
	
	@Query(value = "select * from t_info_land_impl where owner_nm = :ownerNm and type_three = :typeThree limit 1",nativeQuery = true)
	AbmLandEntity getByOwnerNmAndTypeThree(@Param("ownerNm")String ownerNm,@Param("typeThree")String typeThree);
	
	
	@Query(value = "select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region, t7.name allTypeName,tt.all_type allType ,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount ," + 
			" t3.nm typeOne,t3.name oneName,t4.nm typeTwo,t4.name twoName,t5.nm typeThree,t5.name threeName,tt.area,tt.scope,t6.name scopeName,tt.land_nm landNm,tt.unit,tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark " + 
			" from t_info_land_impl tt " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
			" left join pub_region t2 on tt.region = t2.city_code " + 
			" left join pub_dict_value t3 on tt.type_one = t3.nm  " + 
			" left join pub_dict_value t4 on tt.type_two = t4.nm  " + 
			" left join pub_dict_value t5 on tt.type_three = t5.nm " +
			" left join pub_dict_value t6 on tt.scope = t6.nm " + 
			" left join pub_dict_value t7 on tt.all_type = t7.nm " +
			" where owner_nm = :ownerNm ",nativeQuery = true)
	public List<Map<String,Object>> findByOwnerNmOfPersonaWealth(@Param("ownerNm")String ownerNm);

	/**
	 * 通过户主nm查询土地
	 * @param ownerNm
	 * @return
	 */
	@Query(value =" SELECT tindex.*,count(file.id) AS fileCount  " +
			" FROM t_info_land_impl tindex " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_land_impl' AND file.table_pk_column = tindex.nm " +
			" WHERE tindex.owner_nm = :ownerNm GROUP BY tindex.id ",nativeQuery = true)
	List<Map> findByOwnerNm(@Param("ownerNm")String ownerNm);
	
}
