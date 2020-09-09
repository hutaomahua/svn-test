package com.lyht.business.info.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoLandEntity;
import com.lyht.business.info.vo.InfoLandAggregateCardVO;
import com.lyht.business.info.vo.InfoLandAggregateVO;
import com.lyht.business.info.vo.InfoLandVO;

public interface InfoLandDao extends JpaRepository<InfoLandEntity, Integer> {

	@Query(value = "SELECT " + " tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope," + " tt.owner_nm AS ownerNm, tt.land_nm AS landNm,"
			+ " tt.area AS area, tt.unit AS unit," + " tt.lgtd AS lgtd, tt.lttd AS lttd,"
			+ " tt.altd AS altd, tt.in_map AS inMap," + " tt.owner_nature AS ownerNature, tt.type_one AS typeOne,"
			+ " tt.type_two AS typeTwo, tt.type_three AS typeThree,"
			+ " tt.all_type AS allType, tt.land_project_nm AS landProjectNm,"
			+ " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser," + " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue," + " pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS ownerNatureValue,"
			+ " pdv5.name AS typeOneValue, pdv6.name AS typeTwoValue,"
			+ " pdv7.name AS typeThreeValue, pdv8.name AS allTypeValue," + " pdv9.name AS unitValue, count(file.id) AS fileCount "
			+ " FROM t_info_land tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm"
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.type_one = pdv5.nm"
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.type_two = pdv6.nm"
			+ " LEFT JOIN pub_dict_value pdv7 ON tt.type_three = pdv7.nm"
			+ " LEFT JOIN pub_dict_value pdv8 ON tt.all_type = pdv8.nm"
			+ " LEFT JOIN pub_dict_value pdv9 ON tt.unit = pdv9.nm"
			+ " LEFT JOIN pub_files file ON file.table_name = 't_info_land' AND file.table_pk_column = tt.nm " + " WHERE 1=1"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:ownerName is not null, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
			+ " AND IF (:scope is not null, tt.scope = :scope, 1=1)"
			+ " AND IF (:projectFcode is not null, tpp.fcode like CONCAT(:projectFcode,'%'), 1=1)"
			+ " AND IF (:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
			+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
			+ " AND IF (:ownerNm is not null, tt.owner_nm = :ownerNm, 1=1)" +
			" group by tt.nm", countQuery = " SELECT" + " COUNT(1)"
					+ " FROM t_info_land tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
					+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm"
					+ " LEFT JOIN pub_dict_value pdv5 ON tt.type_one = pdv5.nm"
					+ " LEFT JOIN pub_dict_value pdv6 ON tt.type_two = pdv6.nm"
					+ " LEFT JOIN pub_dict_value pdv7 ON tt.type_three = pdv7.nm"
					+ " LEFT JOIN pub_dict_value pdv8 ON tt.all_type = pdv8.nm"
					+ " LEFT JOIN pub_dict_value pdv9 ON tt.unit = pdv9.nm" + " WHERE 1=1"
					+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:ownerName is not null, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
					+ " AND IF (:scope is not null, tt.scope = :scope, 1=1)"
					+ " AND IF (:projectFcode is not null, tpp.fcode like CONCAT(:projectFcode,'%'), 1=1)"
					+ " AND IF (:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
					+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
					+ " AND IF (:ownerNm is not null, tt.owner_nm = :ownerNm, 1=1)", nativeQuery = true)
	Page<InfoLandVO> page(@Param("region") String region, @Param("ownerName") String ownerName,
			@Param("scope") String scope, @Param("projectFcode") String projectFcode, @Param("idCard") String idCard,
			@Param("isDirtyData") String isDirtyData, @Param("ownerNm") String ownerNm, Pageable pageable);

	/**
	 * 实物指标汇总--土地--树
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT" + " pr.city_code AS cityCode," + " pr.parent_code AS parentCode,"
			+ " pr.merger_name AS mergerName," + " pr.name AS name," + " pr.level_type AS level,"
			+ " SUM(IFNULL(tt.area,0)) AS total, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',tt.area,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',tt.area,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',tt.area,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',tt.area,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',tt.area,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',tt.area,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',tt.area,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',tt.area,0)),0) AS newTown " + " FROM pub_region pr "
			+ " LEFT JOIN t_info_land tt ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " GROUP BY pr.city_code", nativeQuery = true)
	List<InfoLandAggregateVO> findLandAggregate(@Param("mergerName") String mergerName);

	/**
	 * 实物指标汇总--土地--大类
	 * 
	 * @param mergerName
	 * @param scopes
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" 
			+ " IF(pdv1.name IS NOT NULL, pdv1.name, '无') AS project," 
			+ " tt.all_type AS landType,"
			+ " '亩' AS unit," 
			+ " IFNULL(SUM(tt.area),0) AS area " 
			+ " FROM t_info_land tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.all_type = pdv1.nm" 
			+ " WHERE 1=1"
			+ " AND IF(:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)"
			+ " AND IF(:isAll = false, tt.scope IN(:scopes), 1=1)" 
			+ " GROUP BY tt.all_type"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND tb.area != 0", nativeQuery = true)
	List<InfoLandAggregateCardVO> findAggregateAll(@Param("mergerName") String mergerName,
			@Param("scopes") List<String> scopes, @Param("isAll") boolean isAll);

	/**
	 * 实物指标汇总--土地--一级分类
	 * 
	 * @param mergerName
	 * @param scopes
	 * @param landType
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" 
			+ " IF(pdv1.name IS NOT NULL, pdv1.name, '无') AS project," + " tt.type_one AS landType,"
			+ " '亩' AS unit," + " IFNULL(SUM(tt.area),0) AS area " + " FROM t_info_land tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.type_one = pdv1.nm" + " WHERE 1=1"
			+ " AND IF(:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " AND IF(:isAll = false, tt.scope IN(:scopes), 1=1)"
			+ " AND IF(:landType IS NOT NULL, tt.all_type = :landType, 1=1)"
			+ " GROUP BY tt.type_one"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND tb.area != 0", nativeQuery = true)
	List<InfoLandAggregateCardVO> findAggregateByAllType(@Param("mergerName") String mergerName, @Param("scopes") List<String> scopes, @Param("landType") String landType, @Param("isAll") boolean isAll);

	/**
	 * 实物指标汇总--土地--二级分类
	 * 
	 * @param mergerName
	 * @param scopes
	 * @param landType
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" + " IF(pdv1.name IS NOT NULL, pdv1.name, '无') AS project," + " tt.type_two AS landType,"
			+ " '亩' AS unit," + " IFNULL(SUM(tt.area),0) AS area " + " FROM t_info_land tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.type_two = pdv1.nm" + " WHERE 1=1"
			+ " AND IF(:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " AND IF(:isAll = false, tt.scope IN(:scopes), 1=1)"
			+ " AND IF(:landType IS NOT NULL, tt.type_one = :landType, 1=1)"
			+ " GROUP BY tt.type_two"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND tb.area != 0", nativeQuery = true)
	List<InfoLandAggregateCardVO> findAggregateByTypeOne(@Param("mergerName") String mergerName, @Param("scopes") List<String> scopes, @Param("landType") String landType, @Param("isAll") boolean isAll);

	/**
	 * 实物指标汇总--土地--三级分类
	 * 
	 * @param mergerName
	 * @param scopes
	 * @param landType
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" + " IF(pdv1.name IS NOT NULL, pdv1.name, '无') AS project,"
			+ " tt.type_three AS landType," + " '亩' AS unit," + " IFNULL(SUM(tt.area),0) AS area "
			+ " FROM t_info_land tt " + " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.type_three = pdv1.nm" + " WHERE 1=1"
			+ " AND IF(:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " AND IF(:isAll = false, tt.scope IN(:scopes), 1=1)"
			+ " AND IF(:landType IS NOT NULL, tt.type_two = :landType, 1=1)"
			+ " GROUP BY tt.type_three"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND tb.area != 0", nativeQuery = true)
	List<InfoLandAggregateCardVO> findAggregateByTypeTwo(@Param("mergerName") String mergerName, @Param("scopes") List<String> scopes, @Param("landType") String landType, @Param("isAll") boolean isAll);

}