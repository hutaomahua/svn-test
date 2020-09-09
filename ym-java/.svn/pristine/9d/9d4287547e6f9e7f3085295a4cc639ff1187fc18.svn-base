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

import com.lyht.business.abm.removal.entity.AbmHousesDecorationEntity;
import com.lyht.business.abm.removal.vo.AbmAggregateCardVO;
import com.lyht.business.abm.removal.vo.PersonaWealthDecorationVO;

public interface AbmHousesDecorationDao extends JpaRepository<AbmHousesDecorationEntity, Integer> {

	@Modifying
	@Query(value = "delete from t_info_houses_decoration_impl where owner_nm = :ownerNm", nativeQuery = true)
	Integer deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	@Query(value = " select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.scope,t6.name scopeName,tt.area,tt.remark, t3.`name` projectName,tt.project_nm projectNm,"
			+ " tt.layer_number layerNumber,tt.house_nature houseNature,t5.name houseNatureName,tt.structure_type structureType,tt.purpose,tt.measurement_1 measurement1,tt.measurement_2 measurement2,tt.decorate_grade decorateGrade,count(file.id) AS fileCount "
			+ " from t_info_houses_decoration_impl tt " + " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on tt.region = t2.city_code "
			+ "left join pub_dict_value t5 on tt.house_nature = t5.nm"
			+ " left join pub_dict_value t6 on tt.scope = t6.nm "
			+ " left join pub_project t3 on tt.project_nm = t3.id" +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_houses_decoration_impl' AND file.table_pk_column = tt.nm " +
			" where owner_nm = :ownerNm GROUP BY tt.id ", countQuery = " select count(1) from (select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code cityCode,tt.scope,tt.area,tt.remark, "
					+ " tt.layer_number layerNumber,tt.house_nature,tt.structure_type,tt.purpose,tt.measurement_1 measurement1,tt.measurement_2 measurement2,tt.decorate_grade decorateGrade "
					+ " from t_info_houses_decoration_impl tt "
					+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm"
					+ " left join pub_dict_value t5 on tt.house_nature = t5.nm  "
					+ " left join pub_region t2 on tt.region = t2.city_code "
					+ " left join pub_project t3 on tt.project_nm = t3.id where owner_nm = :ownerNm) as t0", nativeQuery = true)
	Page<PersonaWealthDecorationVO> findByOwnerNmOfPersonaWealth(@Param("ownerNm") String ownerNm, Pageable pageable);

	@Query(value = " select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,t2.merger_name mergerName,t2.city_code region,tt.scope,t6.name scopeName,tt.area,tt.remark, t3.`name` projectName,tt.project_nm projectNm,"
			+ " tt.layer_number layerNumber,tt.house_nature,tt.structure_type,tt.purpose,tt.measurement_1 measurement1,tt.measurement_2 measurement2,tt.decorate_grade decorateGrade,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount "
			+ " from t_info_houses_decoration_impl tt " + " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on tt.region = t2.city_code "
			+ " left join pub_dict_value t6 on tt.scope = t6.nm "
			+ " left join pub_project t3 on tt.project_nm = t3.id where owner_nm = :ownerNm", nativeQuery = true)
	List<Map<String, Object>> findByOwnerNmOfPersonaWealth(@Param("ownerNm") String ownerNm);

	/**
	 * 实物指标汇总--房屋装修
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
			+ " LEFT JOIN t_info_houses_decoration_impl tt ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003007|^001002002009'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " GROUP BY pp.name" + " UNION ALL" + " SELECT" + " pp.name AS project," + " '㎡' AS unit,"
			+ " 0 AS pivotTotal, " + " 0 AS reservoirTotal, " + " 0 AS flood, " + " 0 AS influence, " + " 0 AS raise, "
			+ " 0 AS temporary, " + " 0 AS permanent, " + " 0 AS newTown " + " FROM pub_project pp" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003007|^001002002009'" + " AND pp.name NOT IN(" + " SELECT" + " pp.name"
			+ " FROM pub_project pp" + " LEFT JOIN t_info_houses_decoration_impl tt ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003007|^001002002009'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " GROUP BY pp.name" + " )" + " GROUP BY pp.name", nativeQuery = true)
	List<AbmAggregateCardVO> findHouseDecorationAggregate(@Param("mergerName") String mergerName);

	/**
	 * 通过户主nm查询房屋装修
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value =" SELECT tindex.*,count(file.id) AS fileCount  " +
			" FROM t_info_houses_decoration_impl tindex " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_houses_decoration_impl' AND file.table_pk_column = tindex.nm " +
			" WHERE tindex.owner_nm = :ownerNm GROUP BY tindex.id ",nativeQuery = true)
	List<Map> findByOwnerNm(@Param("ownerNm")String ownerNm);

}
