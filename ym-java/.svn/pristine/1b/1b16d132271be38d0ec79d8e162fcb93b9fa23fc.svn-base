package com.lyht.business.abm.removal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmHouseEntity;
import com.lyht.business.abm.removal.vo.AbmAggregateCardVO;
import com.lyht.business.abm.removal.vo.PersonaWealthHouseVO;

public interface AbmHousesDao extends JpaRepository<AbmHouseEntity, Integer> {
	
	
	@Modifying
	@Query(value = "delete from t_info_houses_impl where owner_nm = :ownerNm",nativeQuery = true)
	Integer deleteByOwnerNm(@Param("ownerNm")String ownerNm);
	
	@Query(value = " select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.area,tt.scope, t6.name scopeName," + 
			" t3.id projectNm,t3.`name` projectName,tt.purpose,tt.house_number houseNumber,tt.structure_type structureType,tt.layer_number layerNumber, " + 
			" tt.layer_height layerHeight,tt.shape,count(file.id) AS fileCount,tt.address,tt.longs,tt.width,tt.decorate_grade decorateGrade,tt.road_mileage roadMileage, " +
			" tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark " + 
			" from t_info_houses_impl tt " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
			" left join pub_region t2 on tt.region = t2.city_code " + 
			" left join pub_project t3 on tt.project_nm = t3.id " +
			" left join pub_dict_value t6 on tt.scope = t6.nm " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_houses_impl' AND file.table_pk_column = tt.nm " +
			" where owner_nm = :ownerNm GROUP BY tt.id  ", countQuery = "select count(1) from (select t1.name as " +
			"ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code cityCode,tt.area,tt.scope, " +
					" t3.id projectId,t3.`name` projectName,tt.purpose,tt.house_number houseNumber,tt.structure_type structureType,tt.layer_number layerNumber, " + 
					" tt.layer_height layerHeight,tt.shape,tt.address,tt.longs,tt.width,tt.decorate_grade decorateGrade,tt.road_mileage roadMileage, " + 
					" tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark " + 
					" from t_info_houses_impl tt " + 
					" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
					" left join pub_region t2 on tt.region = t2.city_code " + 
					" left join pub_project t3 on tt.project_nm = t3.id " + 
					" where owner_nm = :ownerNm) as t0 ", nativeQuery = true)
	public Page<PersonaWealthHouseVO> findByOwnerNmOfPersonaWealth(@Param("ownerNm") String ownerNm, Pageable pageable);
	
	@Query(value = " select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.area,tt.scope , t6.name scopeName," + 
			" t3.id projectNm,t3.`name` projectName,tt.purpose,tt.house_number houseNumber,tt.structure_type structureType,tt.layer_number layerNumber, " + 
			" tt.layer_height layerHeight,tt.shape,tt.address,tt.longs,tt.width,tt.decorate_grade decorateGrade,tt.road_mileage roadMileage, " + 
			" tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,tt.remark  ,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount " + 
			" from t_info_houses_impl tt " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
			" left join pub_region t2 on tt.region = t2.city_code " + 
			" left join pub_project t3 on tt.project_nm = t3.id " +
			" left join pub_dict_value t6 on tt.scope = t6.nm " +
			" where owner_nm = :ownerNm ", nativeQuery = true)
	public List<Map<String,Object>> findByOwnerNmOfPersonaWealth(@Param("ownerNm") String ownerNm);

	/**
	 * 实物指标汇总--房屋
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT" + " pp.name AS project," + " '㎡' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',tt.area,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',tt.area,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',tt.area,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',tt.area,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',tt.area,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',tt.area,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',tt.area,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',tt.area,0)),0) AS newTown " + " FROM pub_project pp"
			+ " LEFT JOIN t_info_houses_impl tt ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003004|^001002002007'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " GROUP BY pp.name" + " UNION ALL" + " SELECT" + " pp.name AS project," + " '㎡' AS unit,"
			+ " 0 AS pivotTotal, " + " 0 AS reservoirTotal, " + " 0 AS flood, " + " 0 AS influence, " + " 0 AS raise, "
			+ " 0 AS temporary, " + " 0 AS permanent, " + " 0 AS newTown " + " FROM pub_project pp" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003004|^001002002007'" + " AND pp.name NOT IN(" + " SELECT" + " pp.name"
			+ " FROM pub_project pp" + " LEFT JOIN t_info_houses_impl tt ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003004|^001002002007'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " GROUP BY pp.name" + " )" + " GROUP BY pp.name", nativeQuery = true)
	List<AbmAggregateCardVO> findHouseAggregate(@Param("mergerName") String mergerName);

	/**
	 * 通过户主nm查询房屋
	 * @param ownerNm
	 * @return
	 */
	@Query(value =" SELECT tindex.*,count(file.id) AS fileCount  " +
			" FROM t_info_houses_impl tindex " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_houses_impl' AND file.table_pk_column = tindex.nm " +
			" WHERE tindex.owner_nm = :ownerNm GROUP BY tindex.id ",nativeQuery = true)
	List<Map> findByOwnerNm(@Param("ownerNm")String ownerNm);

}
