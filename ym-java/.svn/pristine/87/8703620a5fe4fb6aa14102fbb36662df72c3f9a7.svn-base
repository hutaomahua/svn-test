package com.lyht.business.info.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoHousesEntity;
import com.lyht.business.info.vo.InfoAggregateCardVO;
import com.lyht.business.info.vo.InfoHousesVO;

public interface InfoHousesDao extends JpaRepository<InfoHousesEntity, Integer> {

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
			+ " LEFT JOIN t_info_houses tt ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003004|^001002002007'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " GROUP BY pp.name" + " UNION ALL" + " SELECT" + " pp.name AS project," + " '㎡' AS unit,"
			+ " 0 AS pivotTotal, " + " 0 AS reservoirTotal, " + " 0 AS flood, " + " 0 AS influence, " + " 0 AS raise, "
			+ " 0 AS temporary, " + " 0 AS permanent, " + " 0 AS newTown " + " FROM pub_project pp" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003004|^001002002007'" + " AND pp.name NOT IN(" + " SELECT" + " pp.name"
			+ " FROM pub_project pp" + " LEFT JOIN t_info_houses tt ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pp.fcode REGEXP '^001001003004|^001002002007'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " GROUP BY pp.name" + " )" + " GROUP BY pp.name", nativeQuery = true)
	List<InfoAggregateCardVO> findHouseAggregate(@Param("mergerName") String mergerName);
	
	/**
	 * 分页
	 * @param region
	 * @param ownerName
	 * @param scope
	 * @param isDirtyData 
	 * @param ownerNm 
	 * @param name
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT " 
			+ " tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.zblx AS zblx,"
			+ " tt.owner_nm AS ownerNm, tt.house_number AS houseNumber,"
			+ " tt.purpose AS purpose, tt.structure_type AS structureType,"
			+ " tt.layer_number AS layerNumber, tt.layer_height AS layerHeight,"
			+ " tt.shape AS shape, tt.address AS address,"
			+ " tt.longs AS longs, tt.width AS width,"
			+ " tt.area AS area, tt.decorate_grade AS decorateGrade,"
			+ " tt.road_mileage AS roadMileage, tt.lgtd AS lgtd,"
			+ " tt.lttd AS lttd, tt.altd AS altd,"
			+ " tt.in_map AS inMap, tt.owner_nature AS ownerNature,"
			+ " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser,"
			+ " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue,"
			+ " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS purposeValue,"
			+ " pdv5.name AS shapeValue, pdv6.name AS decorateGradeValue,"
			+ " pdv7.name AS ownerNatureValue, count(file.id) AS fileCount "
			+ " FROM t_info_houses tt" 
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.purpose = pdv4.nm" 
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.shape = pdv5.nm" 
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.decorate_grade = pdv6.nm" 
			+ " LEFT JOIN pub_dict_value pdv7 ON tt.owner_nature = pdv7.nm"
			+ " LEFT JOIN pub_files file ON file.table_name = 't_info_houses' AND file.table_pk_column = tt.nm "
			+ " WHERE 1=1"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:ownerName is not null, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
			+ " AND IF (:scope is not null, tt.scope = :scope, 1=1)"
			+ " AND IF (:projectFcode is not null, tpp.fcode like CONCAT(:projectFcode,'%'), 1=1)"
			+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
			+ " AND IF (:ownerNm is not null, tt.owner_nm = :ownerNm, 1=1)" +
			" group by tt.nm"
			, countQuery = " SELECT"
					+ " COUNT(1)" 
					+ " FROM t_info_houses tt" 
					+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
					+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.purpose = pdv4.nm" 
					+ " LEFT JOIN pub_dict_value pdv5 ON tt.shape = pdv5.nm" 
					+ " LEFT JOIN pub_dict_value pdv6 ON tt.decorate_grade = pdv6.nm" 
					+ " LEFT JOIN pub_dict_value pdv7 ON tt.owner_nature = pdv7.nm" 
					+ " WHERE 1=1"
					+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:ownerName is not null, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
					+ " AND IF (:scope is not null, tt.scope = :scope, 1=1)"
					+ " AND IF (:projectFcode is not null, tpp.fcode like CONCAT(:projectFcode,'%'), 1=1)"
					+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
					+ " AND IF (:ownerNm is not null, tt.owner_nm = :ownerNm, 1=1)"
					, nativeQuery = true)
	Page<InfoHousesVO> page(@Param("region") String region,
			@Param("ownerName") String ownerName, 
			@Param("scope") String scope, 
			@Param("projectFcode") String projectFcode, 
			@Param("isDirtyData") String isDirtyData, 
			@Param("ownerNm") String ownerNm, Pageable pageable);

}