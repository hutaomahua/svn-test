package com.lyht.business.abm.removal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.removal.vo.AbmAggregateCardVO;
import com.lyht.business.abm.removal.vo.AbmMoveAggregateCardVO;
import com.lyht.business.abm.removal.vo.AbmOwnerAggregateVO;
import com.lyht.business.abm.removal.vo.AbmRegionAggregateVO;
import com.lyht.business.info.entity.InfoFamilyEntity;

public interface AbmOwnerDao extends JpaRepository<AbmOwnerEntity, Integer> {
	
	@Query(value = "select tt.place_name place,t1.`name` from t_info_owner_impl tt " + 
			"left join pub_region t1 on tt.region = t1.city_code where tt.nm = ?1",nativeQuery = true)
	Map<String,Object> getOwnerEscrowInfo(String nm);
	
	@Modifying
	@Query(value = "update t_info_owner_impl set produce_place = :producePlace where nm = :nm", nativeQuery = true)
	Integer updateProducePlace(@Param("producePlace") String producePlace, @Param("nm") String nm);

	@Modifying
	@Query(value = "update t_info_owner_impl set i_population = :count where nm = :ownerNm", nativeQuery = true)
	Integer updateiPopulation(@Param("ownerNm") String ownerNm, @Param("count") Integer count);

	@Modifying
	@Query(value = "update t_info_owner_impl set is_produce = 4 where nm = :ownerNm", nativeQuery = true)
	Integer resetState(@Param("ownerNm") String ownerNm);
	
	@Modifying
	@Query(value = "update t_info_owner_impl set is_produce = 4 where id = :id", nativeQuery = true)
	Integer resetStateBydId(@Param("id") Integer id);
	
	@Query(value = "select SUBSTRING_INDEX(SUBSTRING_INDEX(t1.merger_name,',',4),',',-1) AS country from t_info_owner_impl tt " + 
			"left join pub_region t1 on tt.region = t1.city_code where tt.nm = ?1 ",nativeQuery = true)
	String getCountry(String ownerNm);

	/**
	 * 实物指标汇总--按行政区域汇总
	 * 
	 * @return
	 */
	@Query(value = "SELECT pr.name AS regionName, pr.city_code AS cityCode, pr.parent_code AS parentCode,"
			+ " pr.merger_name AS mergerName, pr.level_type AS level, ("
			+ " SELECT ifnull(sum(tif.i_population),0) FROM t_info_owner_impl tif  WHERE tif.region = pr.city_code ) AS population,"
			+ " ( SELECT COUNT(1) FROM t_info_enterprise_impl tie  WHERE tie.region = pr.city_code"
			+ " ) AS enterNumber, ( SELECT IFNULL(SUM(tih.area),0) FROM t_info_houses_impl tih"
			+ " WHERE tih.region = pr.city_code ) AS houseArea,  ("
			+ " SELECT IFNULL(SUM(tihd.area),0) FROM t_info_houses_decoration_impl tihd"
			+ " WHERE tihd.region = pr.city_code  ) AS houseDecorationArea,  ("
			+ " SELECT IFNULL(SUM(tit.num),0) FROM t_info_trees_impl tit WHERE tit.region = pr.city_code"
			+ " ) AS treeNumber,  ( SELECT IFNULL(SUM(tihs.area),0) FROM t_info_homestead_impl tihs"
			+ " WHERE tihs.region = pr.city_code ) AS homesteadArea  FROM pub_region pr"
			+ " WHERE pr.merger_name LIKE CONCAT('%','维西','%')", nativeQuery = true)
	List<AbmRegionAggregateVO> findRegionAggregate();

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
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_family_impl tt "
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
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_owner_impl tt "
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
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_family_impl tt "
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
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM t_info_family_impl tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code " + " WHERE 1=1"
			+ " AND (tt.households_type != '7EC014AB3E'" + " OR tt.households_type IS NULL)"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<AbmAggregateCardVO> findPopulationAggregate(@Param("mergerName") String mergerName);

	/**
	 * 实物指标汇总--户主/权属人
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT t.* FROM (" + " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx,"
			+ " pdv2.name AS scope," + " 'owner' AS type," + " (" + " SELECT COUNT(1) FROM t_info_family_impl tif"
			+ " WHERE tif.owner_nm = tt.nm" + " ) AS population," + " ("
			+ " SELECT IFNULL(SUM(tih.area),0) FROM t_info_houses_impl tih" + " WHERE tih.owner_nm = tt.nm"
			+ " ) AS houseArea," + " (" + " SELECT IFNULL(SUM(tihd.area),0) FROM t_info_houses_decoration_impl tihd"
			+ " WHERE tihd.owner_nm = tt.nm" + " ) AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tit.num),0) FROM t_info_trees_impl tit" + " WHERE tit.owner_nm = tt.nm"
			+ " ) AS treeNumber," + " (" + " SELECT IFNULL(SUM(tihs.area),0) FROM t_info_homestead_impl tihs"
			+ " WHERE tihs.owner_nm = tt.nm" + " ) AS homesteadArea" + " FROM t_info_owner_impl tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx," + " pdv2.name AS scope,"
			+ " 'enter' AS type," + " 0 AS population," + " ("
			+ " SELECT IFNULL(SUM(tich.area),0) FROM t_info_companies_house_impl tich" + " WHERE tich.enter_nm = tt.nm"
			+ " ) AS houseArea," + " 0 AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tiet.num),0) FROM t_info_enter_trees_impl tiet" + " WHERE tiet.enter_nm = tt.nm"
			+ " ) AS treeNumber," + " 0 AS homesteadArea" + " FROM t_info_enterprise_impl tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " ) t LIMIT :current,:pageSize", nativeQuery = true)
	List<AbmOwnerAggregateVO> findOwnerAggregate(@Param("mergerName") String mergerName,
			@Param("current") Integer current, @Param("pageSize") Integer pageSize);

	@Query(value = "SELECT COUNT(1) FROM (" + " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx,"
			+ " pdv2.name AS scope," + " 'owner' AS type," + " (" + " SELECT COUNT(1) FROM t_info_family_impl tif"
			+ " WHERE tif.owner_nm = tt.nm" + " ) AS population," + " ("
			+ " SELECT IFNULL(SUM(tih.area),0) FROM t_info_houses_impl tih" + " WHERE tih.owner_nm = tt.nm"
			+ " ) AS houseArea," + " (" + " SELECT IFNULL(SUM(tihd.area),0) FROM t_info_houses_decoration_impl tihd"
			+ " WHERE tihd.owner_nm = tt.nm" + " ) AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tit.num),0) FROM t_info_trees_impl tit" + " WHERE tit.owner_nm = tt.nm"
			+ " ) AS treeNumber," + " (" + " SELECT IFNULL(SUM(tihs.area),0) FROM t_info_homestead_impl tihs"
			+ " WHERE tihs.owner_nm = tt.nm" + " ) AS homesteadArea" + " FROM t_info_owner_impl tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " tt.nm AS nm," + " tt.name AS name," + " pdv1.name AS zblx," + " pdv2.name AS scope,"
			+ " 'enter' AS type," + " 0 AS population," + " ("
			+ " SELECT IFNULL(SUM(tich.area),0) FROM t_info_companies_house_impl tich" + " WHERE tich.enter_nm = tt.nm"
			+ " ) AS houseArea," + " 0 AS houseDecorationArea," + " ("
			+ " SELECT IFNULL(SUM(tiet.num),0) FROM t_info_enter_trees_impl tiet" + " WHERE tiet.enter_nm = tt.nm"
			+ " ) AS treeNumber," + " 0 AS homesteadArea" + " FROM t_info_enterprise_impl tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)"
			+ " ) t", nativeQuery = true)
	int countOwnerAggregate(@Param("mergerName") String mergerName);

	@Modifying
	@Query(value = " update t_info_owner_impl set fh_state=:fhState where process_id=:processId ", nativeQuery = true)
	void update(@Param("fhState") String fhState, @Param("processId") String processId);

	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET gs_state =:gsstate  WHERE nm =:ownernm  ", nativeQuery = true)
	int updateAbmFamily(@Param("ownernm") String ownernm, @Param("gsstate") String gsstate);
	
	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET gs_state =:gsState  WHERE id =:id  ", nativeQuery = true)
	int updateAbmFamilyById(@Param("id") Integer id, @Param("gsState") Integer gsState);

	/**
	 * 查询对应身份证的户主是否存在
	 * 
	 * @param idCard
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_info_owner_impl tt WHERE tt.id_card = :idCard", nativeQuery = true)
	int countByIdCard(@Param("idCard") String idCard);

	/**
	 * 搬迁安置--汇总卡片
	 * 
	 * @return
	 */
	@Query(value = "SELECT" + " '1' AS serialNumber," + " '集中安置' AS placeMethod," + " NULL AS placeType,"
			+ " NULL AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_type IN('5866417EC7','E0AD4AB501'))) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_type IN('5866417EC7','E0AD4AB501'))) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1' AS serialNumber," + " NULL AS placeMethod," + " '农村集中安置' AS placeType,"
			+ " NULL AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_type = '5866417EC7')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_type = '5866417EC7')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.1' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '兰永' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%兰永%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%兰永%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.2' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '苍浦底' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%苍浦底%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%苍浦底%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.3' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '杵打' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%杵打%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%杵打%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.4' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '窝怒' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%窝怒%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%窝怒%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.5' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '洛吉古' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%洛吉古%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%洛吉古%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.6' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '小谷田' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%小谷田%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%小谷田%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.7' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '阿米俄' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%阿米俄%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%阿米俄%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.1.8' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '白浪统' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%白浪统%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%白浪统%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.2' AS serialNumber," + " NULL AS placeMethod," + " '集镇集中安置' AS placeType,"
			+ " NULL AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_type = 'E0AD4AB501')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_type = 'E0AD4AB501')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.2.1' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '白济汛' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%白济汛%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%白济汛%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '1.2.2' AS serialNumber," + " NULL AS placeMethod," + " NULL AS placeType,"
			+ " '康普' AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_name LIKE '%康普%')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_name LIKE '%康普%')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '2' AS serialNumber," + " '分散安置' AS placeMethod," + " NULL AS placeType,"
			+ " NULL AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_type IN('2E8EDB1C69','C7B441FEE9'))) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_type IN('2E8EDB1C69','C7B441FEE9'))) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '2.1' AS serialNumber," + " NULL AS placeMethod," + " '分散后靠' AS placeType,"
			+ " NULL AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_type = '2E8EDB1C69')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_type = '2E8EDB1C69')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '2.2' AS serialNumber," + " NULL AS placeMethod," + " '分散货币' AS placeType,"
			+ " NULL AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND tioi.place_type = 'C7B441FEE9')) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND tifi.place_type = 'C7B441FEE9')) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)"
			+ " UNION ALL" + " SELECT" + " '3' AS serialNumber," + " '其他' AS placeMethod," + " NULL AS placeType,"
			+ " NULL AS placeName,"
			+ " SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code AND (tioi.place_type IS NULL OR tioi.place_type NOT IN('5866417EC7','E0AD4AB501','2E8EDB1C69','C7B441FEE9')))) AS households,"
			+ " SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code AND (tifi.place_type IS NULL OR tifi.place_type NOT IN('5866417EC7','E0AD4AB501','2E8EDB1C69','C7B441FEE9')))) AS population"
			+ " FROM pub_region pr" + " WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)", nativeQuery = true)
	List<AbmMoveAggregateCardVO> findMoveAggregateCard(@Param("mergerName") String mergerName);
	
	@Query(value = "SELECT '1' AS serialNumber, '集中安置' AS placeMethod, NULL AS placeType,   " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_type IN('5866417EC7','E0AD4AB501'))) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_type IN('5866417EC7','E0AD4AB501'))) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1' AS serialNumber, NULL AS placeMethod, '农村集中安置' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_type = '5866417EC7')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_type = '5866417EC7')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.1' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '兰永' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%兰永%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%兰永%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.2' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '苍浦底' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%苍浦底%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%苍浦底%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.3' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '杵打' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%杵打%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%杵打%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.4' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '窝怒' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%窝怒%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%窝怒%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.5' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '洛吉古' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%洛吉古%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%洛吉古%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.6' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '小谷田' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%小谷田%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%小谷田%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.7' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '阿米俄' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%阿米俄%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%阿米俄%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.8' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '白浪统' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%白浪统%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%白浪统%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL  " + 
			" SELECT '1.2' AS serialNumber, NULL AS placeMethod, '集镇集中安置' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_type = 'E0AD4AB501')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_type = 'E0AD4AB501')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.2.1' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '白济汛' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%白济汛%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%白济汛%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.2.2' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '康普' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_name LIKE '%康普%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_name LIKE '%康普%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '2' AS serialNumber, '分散安置' AS placeMethod, NULL AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_type IN('2E8EDB1C69','C7B441FEE9'))) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_type IN('2E8EDB1C69','C7B441FEE9'))) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '2.1' AS serialNumber, NULL AS placeMethod, '分散后靠' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_type = '2E8EDB1C69')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_type = '2E8EDB1C69')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '2.2' AS serialNumber, NULL AS placeMethod, '分散货币' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND tioi.place_type = 'C7B441FEE9')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND tifi.place_type = 'C7B441FEE9')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '3' AS serialNumber, '其他' AS placeMethod, NULL AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and tioi.is_satisfy = 2 AND (tioi.place_type IS NULL OR tioi.place_type NOT IN('5866417EC7','E0AD4AB501','2E8EDB1C69','C7B441FEE9')))) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and tifi.is_satisfy = 2 AND (tifi.place_type IS NULL OR tifi.place_type NOT IN('5866417EC7','E0AD4AB501','2E8EDB1C69','C7B441FEE9')))) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)",nativeQuery = true)
	List<AbmMoveAggregateCardVO> findMoveAggregateCardIsSatisfy(@Param("mergerName") String mergerName);
	
	@Query(value = "SELECT '1' AS serialNumber, '集中安置' AS placeMethod, NULL AS placeType,   " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy <> 2 OR tioi.is_satisfy is null )AND tioi.place_type IN('5866417EC7','E0AD4AB501'))) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy <> 2 or tifi.is_satisfy is null) AND tifi.place_type IN('5866417EC7','E0AD4AB501'))) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1' AS serialNumber, NULL AS placeMethod, '农村集中安置' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_type = '5866417EC7')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_type = '5866417EC7')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.1' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '兰永' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%兰永%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%兰永%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.2' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '苍浦底' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%苍浦底%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%苍浦底%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.3' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '杵打' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%杵打%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%杵打%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.4' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '窝怒' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%窝怒%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%窝怒%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.5' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '洛吉古' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%洛吉古%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%洛吉古%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.6' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '小谷田' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%小谷田%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%小谷田%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.7' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '阿米俄' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%阿米俄%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%阿米俄%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.1.8' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '白浪统' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%白浪统%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%白浪统%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL  " + 
			" SELECT '1.2' AS serialNumber, NULL AS placeMethod, '集镇集中安置' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_type = 'E0AD4AB501')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_type = 'E0AD4AB501')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.2.1' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '白济汛' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%白济汛%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_name LIKE '%白济汛%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '1.2.2' AS serialNumber, NULL AS placeMethod, NULL AS placeType, " + 
			" '康普' AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_name LIKE '%康普%')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null )AND tifi.place_name LIKE '%康普%')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '2' AS serialNumber, '分散安置' AS placeMethod, NULL AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null )AND tioi.place_type IN('2E8EDB1C69','C7B441FEE9'))) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null )AND tifi.place_type IN('2E8EDB1C69','C7B441FEE9'))) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '2.1' AS serialNumber, NULL AS placeMethod, '分散后靠' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_type = '2E8EDB1C69')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_type = '2E8EDB1C69')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '2.2' AS serialNumber, NULL AS placeMethod, '分散货币' AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND tioi.place_type = 'C7B441FEE9')) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND tifi.place_type = 'C7B441FEE9')) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1) " + 
			" UNION ALL SELECT '3' AS serialNumber, '其他' AS placeMethod, NULL AS placeType, " + 
			" NULL AS placeName, " + 
			" SUM((SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code and (tioi.is_satisfy = 2 OR tioi.is_satisfy is null) AND (tioi.place_type IS NULL OR tioi.place_type NOT IN('5866417EC7','E0AD4AB501','2E8EDB1C69','C7B441FEE9')))) AS households, " + 
			" SUM((SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code and (tifi.is_satisfy = 2 OR tifi.is_satisfy is null) AND (tifi.place_type IS NULL OR tifi.place_type NOT IN('5866417EC7','E0AD4AB501','2E8EDB1C69','C7B441FEE9')))) AS population " + 
			" FROM pub_region pr WHERE 1 = 1 " + 
			" AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)",nativeQuery = true)
	List<AbmMoveAggregateCardVO> findMoveAggregateCardIsNotSatisfy(@Param("mergerName") String mergerName);
	
	/**
	 * 通过nm查询户主
	 * 
	 * @param nm
	 * @return
	 */
	AbmOwnerEntity findByNm(String nm);

	/**
	 * 查询对应身份证的户主是否存在
	 * 
	 * @param idCard
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_info_owner_impl tt WHERE tt.nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	@Query(value = "SELECT * FROM t_info_owner_impl tt WHERE tt.id_card = :idCard LIMIT 1", nativeQuery = true)
	AbmOwnerEntity findByIdCard(@Param("idCard") String idCard);

	/**
	 * 通过nm查询户主
	 * 
	 * @param nms
	 * @return
	 */
	@Query(value = "select * from t_info_owner_impl where nm in (:nms)", nativeQuery = true)
	List<Map> getAbmOwnerImplByNms(@Param("nms") List<String> nms);

	@Query(value = "select td.owner_nm from t_abm_publicity_details td where td.nm=:nm", nativeQuery = true)
	List<String> getOwnerNms(@Param("nm") String nm);

	@Query(value = "select tt.nm from t_abm_publicity_details td left join t_abm_land_audit tt on td.owner_nm = tt.id where td.nm=:nm  GROUP BY tt.nm", nativeQuery = true)
	List<String> getOwnerNmsLand(@Param("nm") String nm);

	@Query(value = "select tp.remarks from t_abm_publicity tp where tp.nm=:nm", nativeQuery = true)
	String getRemark(@Param("nm") String nm);

	@Query(value = "SELECT DISTINCT(tf.owner_nm)as ownerNms FROM t_info_family_impl tf LEFT JOIN t_abm_publicity_details td on tf.nm=td.owner_nm where td.nm=:nm", nativeQuery = true)
	List<String> getOwnerNmsForPopulationAnnouncement(@Param("nm") String nm);

	@Query(value = "select tp.remarks as remark,tp.unit as unit from t_abm_publicity tp where tp.nm=:nm", nativeQuery = true)
	List<Map> getRemarkAndUnit(@Param("nm") String nm);

	@Query(value = "select pr3.name as street,pr2.name as village,pr1.name as zu,t_owner.name as ownerName from t_info_owner_impl t_owner left JOIN pub_region pr1 on t_owner.region=pr1.city_code LEFT JOIN pub_region pr2 on pr1.parent_code=pr2.city_code LEFT JOIN pub_region pr3 on pr2.parent_code=pr3.city_code where t_owner.nm in (:nms)", nativeQuery = true)
	List<Map> getRegionsAndNameByNms(@Param("nms") List<String> nms);
	
	@Query(value = "select count(1) from t_info_family_impl where owner_nm = ?1 and households_type = '7EC014AB3E'",nativeQuery = true)
	Integer getApCount(String ownerNm);
	
	@Query(value = "select count(1) from t_info_family_impl where owner_nm = ?1 and households_type = '7B66B22D8C'",nativeQuery = true)
	Integer getNonApCount(String ownerNm);
	
	@Query(value = "select count(1) from t_info_family_impl where owner_nm = ?1",nativeQuery = true)
	Integer getIPopulation(String ownerNm);
	
	@Modifying
	@Query(value = "update t_info_family_impl set age = :age where id = :id",nativeQuery = true)
	void updateAge(Integer age,Integer id);
	
	@Query(value = "select * from t_info_family_impl where LENGTH(id_card) = 15",nativeQuery = true)
	List<AbmOwnerEntity> findIdCardLength15();
	
	@Query(value = "select * from t_info_family_impl where LENGTH(id_card) = 18",nativeQuery = true)
	List<AbmOwnerEntity> findIdCardLength18();
	
}