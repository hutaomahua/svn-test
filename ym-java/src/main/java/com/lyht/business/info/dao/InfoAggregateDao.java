package com.lyht.business.info.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.vo.InfoAggregateCardVO;
import com.lyht.business.info.vo.InfoOwnerAggregateVO;
import com.lyht.business.info.vo.InfoOwnerDetailVO;
import com.lyht.business.info.vo.InfoOwnerPlaceVO;
import com.lyht.business.info.vo.InfoOwnerSelectVO;
import com.lyht.business.info.vo.InfoOwnerVO;
import com.lyht.business.info.vo.InfoRegionAggregateVO;

public interface InfoAggregateDao extends JpaRepository<InfoOwnerEntity, Integer> {

	/**
	 * 查询权属人下拉框（按阶段查询）
	 * 
	 * @param stage
	 * @return
	 */
	@Query(value = "SELECT id,nm," + " CONCAT(name,'(',IF (id_card is not null, id_card , '无身份证'),')') name,"
			+ " id_card AS idCard,stage FROM t_info_owner " + " WHERE 1=1"
			+ " AND IF(:param IS NOT NULL, (name LIKE CONCAT('%',:param,'%') OR id_card LIKE CONCAT('%',:param,'%')), 1=1)", nativeQuery = true)
	public List<InfoOwnerSelectVO> findByParam(@Param(value = "param") String param);

	/**
	 * 查询权属人明细
	 * 
	 * @param nm
	 * @return
	 */
	@Query(value = "SELECT " + " tt.id AS id,tt.nm AS nm,id_card AS idCard, tt.name AS name,"
			+ " tt.region AS region, tt.scope AS scope,tt.national AS national," + " tt.stage AS stage,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue, pdv1.name AS scopeValue,"
			+ " pdv2.name AS nationalValue, pdv3.name AS stageValue," + " tpr.longitude AS lgtd, tpr.latitude AS lttd,"
			+ " (SELECT IFNULL(COUNT(1),0) FROM t_info_family" + " WHERE owner_nm = :nm) AS population,"
			+ " (SELECT IFNULL(SUM(area),0) AS population FROM t_info_houses" + " WHERE owner_nm = :nm) AS houseArea,"
			+ " (SELECT IFNULL(SUM(area),0) AS population FROM t_info_houses_decoration"
			+ " WHERE owner_nm = :nm) AS houseDecorationArea,"
			+ " (SELECT IFNULL(SUM(area),0) AS population FROM t_info_land" + " WHERE owner_nm = :nm) AS landArea"
			+ " FROM t_info_owner tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.national = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.stage = pdv3.nm" + " WHERE tt.nm = :nm "
			+ " LIMIT 1", nativeQuery = true)
	public InfoOwnerDetailVO findOneByNm(String nm);

	/**
	 * 按姓名和身份证查询（实施）
	 * 
	 * @param name
	 * @param region
	 * @return
	 */
	@Query(value = "SELECT " + " tt.id AS id,tt.nm AS nm,id_card AS idCard, tt.name AS name,"
			+ " tt.region AS region, tt.scope AS scope,tt.national AS national," + " tt.stage AS stage,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue, pdv1.name AS scopeValue,"
			+ " pdv2.name AS nationalValue, pdv3.name AS stageValue," + " tpr.longitude AS lgtd, tpr.latitude AS lttd"
			+ " FROM t_info_owner_impl tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.national = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.stage = pdv3.nm" + " WHERE 1=1"
			+ " AND IF (:name is not null, tt.name LIKE CONCAT('%',:name,'%'), 1=1)"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)", nativeQuery = true)
	public List<InfoOwnerDetailVO> findAllByNameAndRegion(@Param("name") String name, @Param("region") String region);

	/**
	 * 分页查询
	 * 
	 * @param ownerName
	 * @param scopeNm
	 * @param region
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT " + " tt.id AS id,tt.nm AS nm,id_card AS idCard, tt.name AS name,"
			+ " tt.region AS region, tt.scope AS scope,tt.national AS national," + " tt.stage AS stage,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue, pdv1.name AS scopeValue,"
			+ " pdv2.name AS nationalValue, pdv3.name AS stageValue," + " tpr.longitude AS lgtd, tpr.latitude AS lttd"
			+ " FROM t_info_owner tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.national = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.stage = pdv3.nm" + " WHERE 1=1"
			+ " AND IF (:ownerName is not null, tt.name like CONCAT('%',:ownerName,'%'), 1=1)"
			+ " AND IF (:scopeNm is not null, tt.scope = :scopeNm, 1=1)"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)", countQuery = "SELECT"
					+ " COUNT(1)" + " FROM t_info_owner tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.national = pdv2.nm"
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.stage = pdv3.nm" + " WHERE 1=1"
					+ " AND IF (:ownerName is not null, tt.name like CONCAT('%',:ownerName,'%'), 1=1)"
					+ " AND IF (:scopeNm is not null, tt.scope = :scopeNm, 1=1)"
					+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)", nativeQuery = true)
	Page<InfoOwnerDetailVO> page(@Param("ownerName") String ownerName, @Param("scopeNm") String scopeNm,
			@Param("region") String region, Pageable pageable);

	/**
	 * 查询安置去向信息(实施)
	 * 
	 * @param name
	 * @param idCard
	 * @return
	 */
	@Query(value = "SELECT " + " tt.nm AS ownerNm, tt.name AS ownerName, tt.is_satisfy AS isSatisfy,"
			+ " tt.xiang AS county, tt.cun AS towns, tt.zu AS village,"
			+ " pdv4.name AS placeType, tt.place_name AS placeName," + " tt.id_card AS idCard,"
			+ " pdv1.name AS zblx, pdv2.name AS nature," + " pdv3.name AS scopeName,"
			+ " REPLACE(tpr.merger_name,',','') AS regionName" + " FROM t_info_owner_impl tt"
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.nature = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.place_type = pdv4.nm" + " WHERE 1=1"
			+ " AND IF (:name is not null, tt.name like CONCAT('%',:name,'%'), 1=1)"
			+ " AND IF (:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%'), 1=1)", nativeQuery = true)
	List<InfoOwnerPlaceVO> findOwnerPlace(@Param("name") String name, @Param("idCard") String idCard);

	/**
	 * 实物指标汇总--按行政区域汇总
	 * 
	 * @return
	 */
	@Query(value = "SELECT " 
			+ " pr.name AS regionName, pr.city_code AS cityCode, pr.parent_code AS parentCode,"
			+ " pr.merger_name AS mergerName, pr.level_type AS level," 
			+ " ("
			+ " SELECT COUNT(1) FROM t_info_family tif" 
			+ " WHERE tif.region = pr.city_code" 
			+ " ) AS population,"
			+ " (" 
			+ " SELECT COUNT(1) FROM t_info_enterprise tie" 
			+ " WHERE tie.region = pr.city_code"
			+ " ) AS enterNumber," 
			+ " (" 
			+ " SELECT IFNULL(SUM(tih.area),0) FROM t_info_houses tih"
			+ " WHERE tih.region = pr.city_code" 
			+ " ) AS houseArea," 
			+ " ("
			+ " SELECT IFNULL(SUM(tihd.area),0) FROM t_info_houses_decoration tihd"
			+ " WHERE tihd.region = pr.city_code" 
			+ " ) AS houseDecorationArea," 
			+ " ("
			+ " SELECT IFNULL(SUM(tit.num),0) FROM t_info_trees tit" 
			+ " WHERE tit.region = pr.city_code"
			+ " ) AS treeNumber," 
			+ " (" 
			+ " SELECT IFNULL(SUM(tihs.area),0) FROM t_info_homestead tihs"
			+ " WHERE tihs.region = pr.city_code" 
			+ " ) AS homesteadArea" 
			+ " FROM pub_region pr"
			+ " WHERE pr.merger_name LIKE CONCAT('%','维西','%')", nativeQuery = true)
	List<InfoRegionAggregateVO> findRegionAggregate();

	/**
	 * 实物指标汇总--人口
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT" + " '总人口' AS project," + " '人' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',1,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',1,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',1,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',1,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',1,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',1,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',1,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_family tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code " + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '总户数' AS project," + " '户' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',1,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',1,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',1,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',1,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',1,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',1,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',1,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_owner tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code " + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '其中农业' AS project," + " '人' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',1,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',1,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',1,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',1,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',1,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',1,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',1,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_family tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code " + " WHERE 1=1"
			+ " AND tt.households_type = '7EC014AB3E'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '其中非农' AS project," + " '人' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',1,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',1,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',1,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',1,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',1,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',1,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',1,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_family tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code " + " WHERE 1=1"
			+ " AND (tt.households_type != '7EC014AB3E'" + " OR tt.households_type IS NULL)"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<InfoAggregateCardVO> findPopulationAggregate(@Param("mergerName") String mergerName);

	/**
	 * 实物指标汇总--户主/权属人
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT t.* FROM (" + " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx,"
			+ " pdv2.name AS scope," + " 'owner' AS type," + " (" + " SELECT COUNT(1) FROM t_info_family tif"
			+ " WHERE tif.owner_nm = tt.nm" + " ) AS population," + " ("
			+ " SELECT IFNULL(SUM(tih.area),0) FROM t_info_houses tih" + " WHERE tih.owner_nm = tt.nm"
			+ " ) AS houseArea," + " (" + " SELECT IFNULL(SUM(tihd.area),0) FROM t_info_houses_decoration tihd"
			+ " WHERE tihd.owner_nm = tt.nm" + " ) AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tit.num),0) FROM t_info_trees tit" + " WHERE tit.owner_nm = tt.nm"
			+ " ) AS treeNumber," + " (" + " SELECT IFNULL(SUM(tihs.area),0) FROM t_info_homestead tihs"
			+ " WHERE tihs.owner_nm = tt.nm" + " ) AS homesteadArea" + " FROM t_info_owner tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx," + " pdv2.name AS scope,"
			+ " 'enter' AS type," + " 0 AS population," + " ("
			+ " SELECT IFNULL(SUM(tich.area),0) FROM t_info_companies_house tich" + " WHERE tich.enter_nm = tt.nm"
			+ " ) AS houseArea," + " 0 AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tiet.num),0) FROM t_info_enter_trees tiet" + " WHERE tiet.enter_nm = tt.nm"
			+ " ) AS treeNumber," + " 0 AS homesteadArea" + " FROM t_info_enterprise tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " ) t LIMIT :current,:pageSize", nativeQuery = true)
	List<InfoOwnerAggregateVO> findOwnerAggregate(@Param("mergerName") String mergerName,
			@Param("current") Integer current, @Param("pageSize") Integer pageSize);

	@Query(value = "SELECT COUNT(1) FROM (" + " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx,"
			+ " pdv2.name AS scope," + " 'owner' AS type," + " (" + " SELECT COUNT(1) FROM t_info_family tif"
			+ " WHERE tif.owner_nm = tt.nm" + " ) AS population," + " ("
			+ " SELECT IFNULL(SUM(tih.area),0) FROM t_info_houses tih" + " WHERE tih.owner_nm = tt.nm"
			+ " ) AS houseArea," + " (" + " SELECT IFNULL(SUM(tihd.area),0) FROM t_info_houses_decoration tihd"
			+ " WHERE tihd.owner_nm = tt.nm" + " ) AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tit.num),0) FROM t_info_trees tit" + " WHERE tit.owner_nm = tt.nm"
			+ " ) AS treeNumber," + " (" + " SELECT IFNULL(SUM(tihs.area),0) FROM t_info_homestead tihs"
			+ " WHERE tihs.owner_nm = tt.nm" + " ) AS homesteadArea" + " FROM t_info_owner tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx," + " pdv2.name AS scope,"
			+ " 'enter' AS type," + " 0 AS population," + " ("
			+ " SELECT IFNULL(SUM(tich.area),0) FROM t_info_companies_house tich" + " WHERE tich.enter_nm = tt.nm"
			+ " ) AS houseArea," + " 0 AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tiet.num),0) FROM t_info_enter_trees tiet" + " WHERE tiet.enter_nm = tt.nm"
			+ " ) AS treeNumber," + " 0 AS homesteadArea" + " FROM t_info_enterprise tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " ) t", nativeQuery = true)
	int countOwnerAggregate(@Param("mergerName") String mergerName);

	/**
	 * 分页查询
	 * 
	 * @param region
	 * @param scope
	 * @param name
	 * @param idCard
	 * @param isDirtyData
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT " + " tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.name AS name,"
			+ " tt.id_card AS idCard, tt.gender AS gender," + " tt.national AS national, tt.age AS age,"
			+ " tt.immigrant AS immigrant, tt.ap AS ap," + " tt.non_ap AS nonAp, tt.i_population AS iPopulation,"
			+ " tt.education_level AS educationLevel, tt.present_occupation AS presentOccupation,"
			+ " tt.current_school AS currentSchool, tt.households_home AS householdsHome,"
			+ " tt.households_type AS householdsType, tt.lgtd AS lgtd," + " tt.lttd AS lttd, tt.altd AS altd,"
			+ " tt.in_map AS inMap, tt.stage AS stage," + " tt.nature AS nature, tt.owner_type AS ownerType,"
			+ " tt.dos_number AS dosNumber, tt.process_id AS processId," + " tt.fh_state AS fhState, tt.cun AS cun,"
			+ " tt.xiang AS xiang, tt.zu AS zu," + " tt.place_type AS placeType, tt.place_name AS placeName,"
			+ " tt.is_satisfy AS isSatisfy, tt.fh_nm AS fhNm," + " tt.gs_state AS gsState, tt.status AS status,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser," + " tt.remark AS remark, tt.zblx AS zblx,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tpp.name AS projectValue, " + " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS nationalValue,"
			+ " pdv5.name AS educationLevelValue, pdv6.name AS householdsTypeValue,"
			+ " pdv7.name AS natureValue, pdv8.name AS ownerTypeValue" + " FROM t_info_owner tt"
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.national = pdv4.nm"
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.education_level = pdv5.nm"
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.households_type = pdv6.nm"
			+ " LEFT JOIN pub_dict_value pdv7 ON tt.nature = pdv7.nm"
			+ " LEFT JOIN pub_dict_value pdv8 ON tt.owner_type = pdv8.nm" + " WHERE 1=1"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:scope is not null, tt.scope = :scope, 1=1)"
			+ " AND IF (:name is not null, tt.name like CONCAT('%',:name,'%'), 1=1)"
			+ " AND IF (:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
			+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tt.id_card IS NULL, tt.id_card IS NOT NULL), 1=1)"
			+ " AND IF (:nm is not null, tt.nm = :nm, 1=1)", countQuery = " SELECT" + " COUNT(1)"
					+ " FROM t_info_owner tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.national = pdv4.nm"
					+ " LEFT JOIN pub_dict_value pdv5 ON tt.education_level = pdv5.nm"
					+ " LEFT JOIN pub_dict_value pdv6 ON tt.households_type = pdv6.nm"
					+ " LEFT JOIN pub_dict_value pdv7 ON tt.nature = pdv7.nm"
					+ " LEFT JOIN pub_dict_value pdv8 ON tt.owner_type = pdv8.nm" + " WHERE 1=1"
					+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:scope is not null, tt.scope = :scope, 1=1)"
					+ " AND IF (:name is not null, tt.name like CONCAT('%',:name,'%'), 1=1)"
					+ " AND IF (:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
					+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tt.id_card IS NULL, tt.id_card IS NOT NULL), 1=1)"
					+ " AND IF (:nm is not null, tt.nm = :nm, 1=1)", nativeQuery = true)
	Page<InfoOwnerVO> page(@Param("region") String region, @Param("scope") String scope, @Param("name") String name,
			@Param("idCard") String idCard, @Param("isDirtyData") String isDirtyData, @Param("nm") String nm,
			Pageable pageable);

	List<InfoOwnerEntity> findAllByPlaceTypeAndRegionIn(String placeType, List<String> regions);

	List<InfoOwnerEntity> findAllByPlaceTypeAndRegionInAndIsSatisfy(String placeType, List<String> regions,
			String isSatisfy);
	
}