package com.lyht.business.abm.production.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.production.vo.ProduceCardStatisticsVO;
import com.lyht.business.abm.production.vo.ProduceProcessInfoVO;

/**
 * @version: V1.0
 * @author: hjs
 * @className: ProductionDao
 * @packageName: com.lyht.business.abm.production.dao
 * @description: （类作用）
 * @data: 2020年02月25日 10:37
 * @see []
 **/
public interface ProductionDao extends JpaRepository<LandPoolEntity, Integer> {

	@Query(value = "select * from scazKY", nativeQuery = true)
	List<Map<String, Object>> popuCountKY();

	@Query(value = "select * from scazrk where mergerName like CONCAT('%',?1,'%')", countQuery = "select count(*) from scazRk where mergerName like CONCAT('%',?1,'%')", nativeQuery = true)
	Page<Map<String, Object>> popuCount(String region, Pageable pageable);

//	/**
//	 * 根据行政区编码分页查询生产安置户主列表数据
//	 * 
//	 * @return
//	 */
//	@Query(value = " select *,(select status from t_abm_produce_process tt left join t_bpm_process t1 on tt.process_id = t1.process_id "
//			+ "	where tt.owner_nm = t0.ownerNm ORDER BY tt.create_time desc limit 1) `status` "
//			+ " from (SELECT 'true' as isUser,  tli.region,"
//			+ " tli.region as parentCode,prn.merger_name mergaerName,prn.name cityName,  tfi.name as name,  "
//			+ " IFNULL(INSERT(tfi.id_card, 7, 13, '************'), '无') AS idCard, tli.scope,prn.longitude lgtd,prn.latitude lttd, "
//			+ " pv.name AS scopeNmme,  tli.owner_nm AS ownerNm,  IFNULL(t1.name, '未知类型') AS 'type', "
//			+ " IFNULL(ROUND(SUM(pp.ratio*tli.area),2),0) AS 'area',  IFNULL(FLOOR(  CASE tli.scope  "
//			+ " WHEN 'CE81C0FA94' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),2.33)) "
//			+ " WHEN 'D8D5AAD9DC' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8)) "
//			+ " WHEN '348F5B68BA' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8)) "
//			+ " WHEN 'E78D14E7BE' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8)) "
//			+ " END),0) AS rk,  IFNULL(temp.produce,0) AS produce,   IFNULL(t0.is_produce, 0) as isProduce "
//			+ "  FROM t_info_land_impl tli   INNER JOIN  t_info_family_impl tfi  ON tfi.id_card = tli.id_card  "
//			+ " LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'  "
//			+ " INNER JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL "
//			+ " LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tli.region "
//			+ " LEFT JOIN pub_region prn ON prn.city_code = tfi.region"
//			+ " left join t_info_owner_impl t0 on tli.owner_nm = t0.nm"
//			+ " left join pub_dict_value t1 on t0.households_type = t1.nm"
//			+ " LEFT JOIN t_abm_land_audit tala on t0.nm = tala.nm "
//			+ " LEFT JOIN pub_dict_value pv ON tli.scope =pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits' "
//			+ " LEFT JOIN (SELECT owner_nm,COUNT(*) AS produce FROM t_info_family_impl WHERE is_produce IS NOT NULL GROUP BY owner_nm) temp ON temp.owner_nm = tli.owner_nm "
//			+ "  LEFT JOIN t_info_owner_impl tt on tt.nm = tli.owner_nm"
//			+ " WHERE  1 =1 and ( tala.fg_state = 2 or tala.fg_state is null) and t0.gs_state =2 and IF(:cityName is not null and :cityName != '', prn.merger_name like CONCAT('%', :cityName,'%') , 1=1) "
//			+ " and IF(:idCard is not null and :idCard != '', tfi.id_card like CONCAT('%', :idCard,'%'), 1=1) "
//			+ " and IF(:uname is not null and :uname != '', tfi.name like CONCAT('%', :uname,'%'), 1=1) "
//			+ " GROUP BY tli.id_card) as t0 ", countQuery = " SELECT count(*)    FROM t_info_land_impl tli "
//					+ " INNER JOIN  t_info_family_impl tfi  ON tfi.id_card = tli.id_card  "
//					+ " LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'  "
//					+ " INNER JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL "
//					+ " LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tli.region "
//					+ " LEFT JOIN pub_region prn ON prn.city_code = tfi.region "
//					+ " left join t_info_owner_impl t0 on tli.owner_nm = t0.nm "
//					+ " LEFT JOIN t_abm_land_audit tala on t0.nm = tala.nm"
//					+ " LEFT JOIN pub_dict_value pv ON tli.scope =pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits' "
//					+ " LEFT JOIN (SELECT owner_nm,COUNT(*) AS produce FROM t_info_family_impl WHERE is_produce IS NOT NULL GROUP BY owner_nm) temp ON temp.owner_nm = tli.owner_nm "
//					+ " WHERE  1 =1 and ( tala.fg_state = 2 or tala.fg_state is null) and t0.gs_state =2 and IF(:cityName is not null and :cityName != '', prn.merger_name like CONCAT('%', :cityName,'%'), 1=1) "
//					+ " and IF(:idCard is not null and :idCard != '', tfi.id_card like CONCAT('%', :idCard,'%'), 1=1) "
//					+ " and IF(:uname is not null and :uname != '', tfi.name like CONCAT('%', :uname,'%'), 1=1) "
//					+ " GROUP BY tli.id_card ", nativeQuery = true)
//	Page<Map<String, Object>> findUsersList(@Param("cityName") String cityName, @Param("uname") String uname,
//			@Param("idCard") String idCard, Pageable Pageable);

	@Query(value = "select * from (select (select status from t_abm_produce_process tt left join t_bpm_process t1 on tt.process_id = t1.process_id  "
			+ "	where tt.owner_nm = t0.nm ORDER BY tt.create_time desc limit 1) `status` ,t0.name,t0.nm ownerNm,'true' as isUser,t2.city_code region,t2.merger_name mergerName,t2.name cityName,t3.name scopeName,t3.nm scope, "
			+ "IFNULL(t0.id_card, '无') AS idCard,IFNULL(t0.is_produce, 0) as isProduce , IF(t0.zu = '兰永', 'true', 'false') AS isLY , "
			+ " (SELECT COUNT(*) FROM t_info_family_impl WHERE households_type = '7EC014AB3E' AND owner_nm = t0.nm) AS nNum , "
			+ "IFNULL(ROUND(SUM(pp.ratio*tli.area),2),0) AS 'area',  IFNULL(FLOOR(  CASE tli.scope   "
			+ " WHEN 'CE81C0FA94' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),2.33))  "
			+ " WHEN 'D8D5AAD9DC' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
			+ " WHEN '348F5B68BA' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
			+ " WHEN 'E78D14E7BE' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
			+ " END),0) AS rk,(SELECT COUNT(*) AS hsrk FROM t_info_family_impl WHERE owner_nm = t0.nm AND is_produce = 1) AS hdrk, "
			+ " if(ts.house_status=1 or ts.house_decoration_status=1 or ts.building_status=1 or ts.agricultural_facilities_status=1 or ts.trees_status=1 "
			+ " or ts.individual_status=1 or ts.relocation_allowance_status=1 or ts.other_status=1 or ts.difficult_status=1 or ts.infrastructure_status=1 or ts.homestead_status=1 "
			+ " or ts.levy_land_status=1 or ts.young_crops_status=1,1,0) as protocolState  from t_info_owner_impl t0  "
			+ " left join t_info_family_impl t1 on t0.nm = t1.owner_nm "
			+ " left join pub_region t2 on t0.region = t2.city_code  "
			+ " left join pub_dict_value t3 on t0.scope = t3.nm "
			+ " left join t_info_land_impl tli on t0.nm = tli.owner_nm "
			+ " LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'  "
			+ " LEFT JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL  "
			+ " LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tli.region  "
			+ " LEFT JOIN t_abm_land_audit tala on t0.nm = tala.nm  "
			+ " left join t_abm_total_state ts on t0.nm = ts.owner_nm  where t0.gs_state =2 "
			+ " and if(:cityName is not null, t2.merger_name like concat('%',:cityName,'%'),1=1)"
			+ " and if(:uname is not null, t0.name like concat('%',:uname,'%'),1=1)  "
			+ " and if(:idCard is not null, t0.id_card like concat('%',:idCard,'%'),1=1) "
			+ " and if(:ownerNm is not null, t0.nm = :ownerNm,1=1)"
			+ " GROUP BY t0.id_card) as tt ORDER BY tt.isProduce,(case when tt.protocolState = 1 then 99 else 1 end)", countQuery = "select count(1) from (select (select status from t_abm_produce_process tt left join t_bpm_process t1 on tt.process_id = t1.process_id  "
					+ "	where tt.owner_nm = t0.nm ORDER BY tt.create_time desc limit 1) `status` ,t0.name,t0.nm ownerNm,'true' as isUser,t2.merger_name mergerName,t2.name cityName,t3.name scopeName,t3.nm scope, "
					+ "IFNULL(INSERT(t0.id_card, 7, 13, '************'), '无') AS idCard,IFNULL(t0.is_produce, 0) as isProduce , "
					+ "IFNULL(ROUND(SUM(pp.ratio*tli.area),2),0) AS 'area',  IFNULL(FLOOR(  CASE tli.scope   "
					+ " WHEN 'CE81C0FA94' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),2.33))  "
					+ " WHEN 'D8D5AAD9DC' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
					+ " WHEN '348F5B68BA' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
					+ " WHEN 'E78D14E7BE' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
					+ " END),0) AS rk  from t_info_owner_impl t0  "
					+ " left join t_info_family_impl t1 on t0.nm = t1.owner_nm "
					+ " left join pub_region t2 on t0.region = t2.city_code "
					+ " left join pub_dict_value t3 on t0.scope = t3.nm "
					+ " left join t_info_land_impl tli on t0.nm = tli.owner_nm "
					+ " LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'  "
					+ " LEFT JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL  "
					+ " LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tli.region  "
					+ " LEFT JOIN t_abm_land_audit tala on t0.nm = tala.nm "
					+ " left join t_abm_total_state ts on t0.nm = ts.owner_nm " + " where t0.gs_state =2"
					+ " and if(:cityName is not null, t2.merger_name like concat('%',:cityName,'%'),1=1)"
					+ " and if(:uname is not null, t0.name like concat('%',:uname,'%'),1=1)  "
					+ " and if(:idCard is not null, t0.id_card like concat('%',:idCard,'%'),1=1)"
					+ " and if(:ownerNm is not null, t0.nm = :ownerNm,1=1) "
					+ " GROUP BY t0.id_card) as ttp", nativeQuery = true)
	Page<Map<String, Object>> findUsersList(@Param("cityName") String cityName, @Param("uname") String uname,
			@Param("idCard") String idCard, @Param("ownerNm")String ownerNm,Pageable Pageable);

//	/**
//	 * 根据行政区编码分页查询生产安置户主列表数据
//	 * 
//	 * @return
//	 */
//	@Query(value = " SELECT  'true' as isUser, tli.region, tli.region as parentCode,"
//			+ " tfi.name as name, IFNULL(INSERT(tfi.id_card, 7, 13, '************'), '无') AS idCard, "
//			+ " tli.scope, pv.name AS scopeNmme, tli.owner_nm AS ownerNm, "
//			+ " IFNULL(pp.type, '未知类型') AS 'type', IFNULL(ROUND(SUM(pp.ratio*tli.area),2),0) AS 'area', "
//			+ " IFNULL(FLOOR( CASE tli.scope  "
//			+ " WHEN 'CE81C0FA94' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),2.33)) "
//			+ " WHEN 'D8D5AAD9DC' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8)) "
//			+ " WHEN '348F5B68BA' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8)) "
//			+ " WHEN 'E78D14E7BE' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8)) "
//			+ " END),0) AS rk, IFNULL(temp.produce,0) AS produce,  IFNULL(tt.is_produce, 0) as isProduce "
//			+ "  FROM t_info_land_impl tli  INNER JOIN  t_info_family_impl tfi  ON tfi.id_card = tli.id_card  "
//			+ " LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'  "
//			+ " INNER JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL "
//			+ " left join t_info_owner_impl tt on tfi.owner_nm = tt.nm"
//			+ " LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tli.region "
//			+ " LEFT JOIN pub_region prn ON prn.city_code = tfi.region "
//			+ " LEFT JOIN pub_dict_value pv ON tli.scope =pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits' "
//			+ " LEFT JOIN (SELECT owner_nm,COUNT(*) AS produce FROM t_info_family_impl WHERE is_produce = 1 GROUP BY owner_nm) temp ON temp.owner_nm = tli.owner_nm "
//			+ " WHERE IF(:regions is not null and :regions != '', prn.merger_name like CONCAT('%', :regions,'%') , 1=1) "
//			+ " and IF(:idCard is not null and :idCard != '', tfi.id_card like CONCAT('%', :idCard,'%'), 1=1) "
//			+ " and IF(:uname is not null and :uname != '', tfi.name like CONCAT('%', :uname,'%'), 1=1) "
//			+ " GROUP BY tli.id_card ", nativeQuery = true)
	@Query(value = "select IFNULL(tioi.is_produce,0) AS isProduce,(SELECT COUNT(1) FROM t_info_family_impl WHERE owner_nm = tioi.nm and (is_produce =0 or is_produce = 1)) AS produce, "
			+ "tioi.region,pr.parent_code parentCode,tioi.name,tioi.id_card AS idCard,pdv.nm scope,pdv.name scopeName,tioi.nm AS ownerNm, "
			+ "IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1) AS gddl,IFNULL(ROUND(SUM(pp.ratio*tli.area),2),0) AS area, "
			+ "ifNull(FLOOR(CASE    "
			+ " WHEN tli.scope = 'CE81C0FA94' AND tli.type_three != '5B1C04D314' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),2.33))  "
			+ " WHEN tli.scope = 'D8D5AAD9DC' AND tli.type_three != '5B1C04D314' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
			+ " WHEN tli.scope = '348F5B68BA' AND tli.type_three != '5B1C04D314' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
			+ " WHEN tli.scope = 'E78D14E7BE' AND tli.type_three != '5B1C04D314' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))  "
			+ " END),0) rk from t_info_owner_impl tioi  " + "left join t_info_land_impl tli on tioi.nm = tli.owner_nm "
			+ "left join pub_dict_value pdv on tioi.scope = pdv.nm  "
			+ "LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three' "
			+ "INNER JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL   "
			+ "left join t_info_family_impl tifi on tifi.owner_nm = tioi.nm  "
			+ "left join pub_region pr on tioi.region = pr.city_code "
			+ "LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tioi.region  "
			+ " WHERE IF(:regions is not null and :regions != '', pr.merger_name like CONCAT('%', :regions,'%') , 1=1) "
			+ " and IF(:idCard is not null and :idCard != '', tioi.id_card like CONCAT('%', :idCard,'%'), 1=1) "
			+ " and IF(:uname is not null and :uname != '', tioi.name like CONCAT('%', :uname,'%'), 1=1) "
			+ "GROUP BY tioi.nm ", nativeQuery = true)
	List<Map<String, Object>> findUserList(@Param("regions") String regions, @Param("uname") String uname,
			@Param("idCard") String idCard);

	/**
	 * 查询传入行政区的所有子行政区编码
	 * 
	 * @param region
	 * @return
	 */
	@Query(value = "SELECT city_code FROM pub_region WHERE FIND_IN_SET(city_code,getRegionChildren(:region))", nativeQuery = true)
	List<String> findRegionAllChild(@Param("region") String region);

	/**
	 * 查询传入行政区的下级行政区
	 * 
	 * @param region
	 * @return
	 */
	@Query(value = "SELECT p.name as name, p.city_code as cityCode,"
			+ " p.parent_code as parentCode, p.level_type as level, "
			+ " IFNULL((SELECT 'false' FROM pub_region pr WHERE pr.parent_code = p.city_code GROUP BY pr.parent_code), 'true') AS isLast "
			+ " FROM pub_region p WHERE IF(:region is not null, parent_code = :region, parent_code is null)", nativeQuery = true)
	List<Map<String, Object>> findRegionChild(@Param("region") String region);

	/**
	 * 查询传入行政区的下级行政区
	 * 
	 * @return
	 */
	@Query(value = "SELECT p.name as name, p.city_code as cityCode ,"
			+ " p.parent_code as parentCode, p.level_type as level, "
			+ " IFNULL((SELECT 'false' FROM pub_region pr WHERE pr.parent_code = p.city_code GROUP BY pr.parent_code), 'true') AS isLast  "
			+ " FROM pub_region p where p.merger_name like CONCAT('云南省,迪庆藏族自治州,维西傈僳族自治县','%')  ", nativeQuery = true)
	List<Map<String, Object>> findAllRegion();

	/**
	 * 根据传入的行政区编码 查询改行政区下的所有子行政区编码（以字符串形式返回','分割）
	 * 
	 * @param region
	 * @return
	 */
	@Query(value = "SELECT getRegionChildren(:region)", nativeQuery = true)
	String findStringRegionChild(@Param("region") String region);

	/**
	 * 根据传入的行政区编码 查询该行政区的所有父级行政区
	 * 
	 * @param region
	 * @return
	 */
	@Query(value = " SELECT 'true' as open,p.name as name, p.city_code as cityCode, p.parent_code as parentCode, p.level_type as level, "
			+ " IFNULL((SELECT 'false' FROM pub_region pr WHERE pr.parent_code = p.city_code GROUP BY pr.parent_code), 'true') AS isLast "
			+ " FROM pub_region p WHERE FIND_IN_SET(city_code,getRegionParent(:region))", nativeQuery = true)
	List<Map<String, Object>> findRegion(@Param("region") String region);

	/**
	 * 根据行政区id 查询该行政区所有的父级行政区code
	 * 
	 * @param region
	 * @return
	 */
	@Query(value = " SELECT p.city_code as cityCode" + " FROM pub_region p "
			+ " WHERE FIND_IN_SET(city_code,getRegionParent(:region)) ", nativeQuery = true)
	List<String> findParent(@Param("region") String region);

	/**
	 * 根据户主的ownerNm 查询该户的生产安置人口基础信息
	 * 
	 * @param ownerNm
	 * @return
	 */
//	@Query(value = " SELECT   tfi.region AS region,  SUBSTRING_INDEX(pr.merger_name,',',1) AS province,   "
//			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',2),',',-1)) AS city,   "
//			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',3),',',-1)) AS county,   "
//			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',4),',',-1)) AS country,   "
//			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',5),',',-1)) AS village,   "
//			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',6),',',-1)) AS 'group',   "
//			+ "(SELECT name FROM t_info_owner_impl WHERE nm = :ownerNm) AS name, COUNT(*) AS zrk,   "
//			+ "(SELECT COUNT(*) FROM t_info_family_impl WHERE households_type = '7EC014AB3E' AND owner_nm = :ownerNm) AS nNum ,   "
//			+ "(SELECT COUNT(*) FROM t_info_family_impl WHERE households_type = '7B66B22D8C' AND owner_nm = :ownerNm) AS fyNum ,   "
//			+ "IFNULL(FLOOR( CASE tli.scope    "
//			+ " WHEN 'CE81C0FA94' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),2.33))   "
//			+ " WHEN 'D8D5AAD9DC' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))   "
//			+ " WHEN '348F5B68BA' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))   "
//			+ " WHEN 'E78D14E7BE' THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))   "
//			+ " END),0) AS rk,   "
//			+ " (SELECT COUNT(*) AS hsrk FROM t_info_family_impl WHERE owner_nm = :ownerNm AND  is_produce = 1) AS hdrk,   "
//			+ " tfi.is_move AS isMove,   pdv.name AS placeType,   tfi.to_where AS toWhere,   "
//			+ " if(tfi.produce_place is not null,tfi.produce_place,IF(tfi.is_move = '是' AND tfi.is_produce = 2,   	  "
//			+ " IF(tfi.cun IS NOT NULL AND tfi.cun != '',    "
//			+ " IF(tfi.zu IS NOT NULL AND tfi.zu != '', CONCAT(tfi.xiang, ',', CONCAT(tfi.cun,',',tfi.zu)),CONCAT(tfi.xiang, ',', tfi.cun)), tfi.xiang) ,pr.merger_name))   "
//			+ " AS placeName,tfi.place_name place,   IF(tfi.zu = '兰永', 'true', 'false') AS isLY ,t9.name scopeName   FROM t_info_family_impl tfi    "
//			+ "  LEFT JOIN pub_dict_value pv ON tfi.scope =pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits'  "
//			+ "left JOIN t_info_owner_impl tioi on tioi.nm = tfi.owner_nm "
//			+ " left join pub_dict_value pdv on tfi.place_type = pdv.nm    "
//			+ "  LEFT JOIN pub_region pr ON pr.city_code = tfi.region left join pub_dict_value t9 on tfi.scope = t9.nm   "
//			+ "left join t_info_land_impl tli on tfi.owner_nm = tli.owner_nm   "
//			+ " LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'    "
//			+ " LEFT JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL   "
//			+ " LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tli.region   "
//			//+ " WHERE tioi.nm = :ownerNm GROUP BY tli.owner_nm "
//			, nativeQuery = true)
	@Query(value = "SELECT   tfi.region AS region,  SUBSTRING_INDEX(pr.merger_name,',',1) AS province,    "
			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',2),',',-1)) AS city,    "
			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',3),',',-1)) AS county,    "
			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',4),',',-1)) AS country,    "
			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',5),',',-1)) AS village,    "
			+ "(SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(pr.merger_name,',',6),',',-1)) AS 'group',    "
			+ "tfi.name AS name, (select count(*) from t_info_family_impl where owner_nm = tfi.nm) zrk, "
			+ "(SELECT COUNT(*) FROM t_info_family_impl WHERE households_type = '7EC014AB3E' AND owner_nm = tfi.nm) AS nNum ,    "
			+ "(SELECT COUNT(*) FROM t_info_family_impl WHERE households_type = '7B66B22D8C' AND owner_nm = tfi.nm) AS fyNum ,    "
			+ "IFNULL(FLOOR( CASE  "
			+ " WHEN (tli.scope ='CE81C0FA94' AND tli.type_three != '5B1C04D314') THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),2.33))    "
			+ " WHEN (tli.scope ='D8D5AAD9DC' AND tli.type_three != '5B1C04D314') THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))    "
			+ " WHEN (tli.scope ='348F5B68BA' AND tli.type_three != '5B1C04D314') THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))    "
			+ " WHEN (tli.scope ='E78D14E7BE' AND tli.type_three != '5B1C04D314') THEN (IFNULL(SUM(pp.ratio*tli.area)/IFNULL(ROUND(ti.plowland_count/ti.farmer_count,2),1), 0) * POW((1+0.013),8))    "
			+ " END),0) AS rk,    "
			+ " (SELECT COUNT(*) AS hsrk FROM t_info_family_impl WHERE owner_nm = tfi.nm AND  is_produce = 1 AND households_type = '7EC014AB3E') AS hdrk,    "
			+ " tifi.is_move AS isMove,   pdv.name AS placeType,   tfi.to_where AS toWhere,    "
			+ " if(tfi.produce_place is not null,tfi.produce_place,IF(tifi.is_move = '是' AND tfi.is_produce = 2,   	   "
			+ " IF(tfi.cun IS NOT NULL AND tfi.cun != '',     "
			+ " IF(tfi.zu IS NOT NULL AND tfi.zu != '', CONCAT(tfi.xiang, ',', CONCAT(tfi.cun,',',tfi.zu)),CONCAT(tfi.xiang, ',', tfi.cun)), tfi.xiang) ,pr.merger_name))    "
			+ " AS placeName,tfi.place_name place,   IF(tfi.zu = '兰永', 'true', 'false') AS isLY ,t9.name scopeName   FROM t_info_owner_impl tfi     "
			+ "  LEFT JOIN pub_dict_value pv ON tfi.scope =pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits'   "
			+ " left join t_info_family_impl tifi on tfi.nm = tifi.owner_nm "
			+ " left join pub_dict_value pdv on tfi.place_type = pdv.nm     "
			+ "  LEFT JOIN pub_region pr ON pr.city_code = tfi.region "
			+ " left join pub_dict_value t9 on tfi.scope = t9.nm    "
			+ " left join t_info_land_impl tli on tfi.nm = tli.owner_nm    "
			+ " LEFT JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'     "
			+ " LEFT JOIN pub_project pp ON pp.name = pv2.name AND pp.ratio IS NOT NULL    "
			+ " LEFT JOIN t_abm_produce_benchmark_impl ti ON ti.region = tli.region "
			+ "WHERE tfi.nm = :ownerNm GROUP BY tfi.nm", nativeQuery = true)
	Map<String, Object> findUserInfo(@Param("ownerNm") String ownerNm);

	/**
	 * 根据户主的ownerNm 查询该户的所有土地面积情况
	 * 
	 * @param ownerNm
	 * @return
	 */
//	@Query(value = " SELECT pv2.name, SUM(tli.area) as area, IFNULL(SUM(ROUND(tli.area*pp.ratio, 2)), 0) as zsArea FROM t_info_land_impl  tli "
//			+ "INNER JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three'  "
//			+ "INNER JOIN pub_project pp ON pp.name = pv2.name AND ratio IS NOT NULL "
//			+ "WHERE tli.owner_nm = :ownerNm group by pv2.name", nativeQuery = true)
	@Query(value = "select t1.name,tt.area,zsArea ,scope  " + 
			"from (select tl.type_two as type,sum(tl.area) area,tl.scope,IFNULL(SUM(ROUND(tl.area*pp.coefficient, 2)), 0) as zsArea from t_info_land_impl tl  " + 
			"left join pub_dict_value pdv on tl.type_two = pdv.nm   " + 
			"left JOIN t_abm_convert_coefficient pp ON pp.land_type = pdv.nm  " + 
			"where tl.owner_nm = :ownerNm and (tl.type_three is null or tl.type_three = '' or pdv.name ='水田') group by tl.type_two,tl.scope   " + 
			"UNION    " + 
			"select tl.type_three as type,sum(tl.area) area,tl.scope,IFNULL(SUM(ROUND(tl.area*pp.coefficient, 2)), 0) as zsArea from t_info_land_impl tl  " + 
			"left join pub_dict_value pdv on tl.type_three = pdv.nm  " + 
			"left JOIN t_abm_convert_coefficient pp ON pp.land_type = pdv.nm  " + 
			"where tl.owner_nm = :ownerNm and tl.type_three is not null and tl.type_three != '' and pdv.name != '水田' group by tl.type_three,tl.scope) tt   " + 
			"left join pub_dict_value t1 on tt.type = t1.nm  " + 
			"", nativeQuery = true)
	List<Map<String, Object>> findLandArea(@Param("ownerNm") String ownerNm);

	/**
	 * 根据户主ownerNm 查询该户的所有人员信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = " SELECT tfi.name AS name, IFNULL(tfi.id_card, '无') AS idCard, "
			+ " pv.name AS gx, tfi.age, IFNULL(pv2.name,'非农') AS hkType, tfi.nm, "
			+ " tfi.gender AS xb,  pv3.name AS mz,  ifnull(tfi.is_produce,-1) AS isProduce FROM t_info_family_impl  tfi "
			+ " LEFT JOIN pub_dict_value pv ON tfi.master_relationship = pv.nm AND pv.listnm_sys_dict_cate = 'dict_owner_relation'  "
			+ " LEFT JOIN pub_dict_value pv2 ON tfi.households_type = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_node_type'  "
			+ " LEFT JOIN pub_dict_value pv3 ON tfi.national = pv3.nm AND pv3.listnm_sys_dict_cate = 'dict_ethnic'  "
			+ " WHERE owner_nm = :ownerNm ", nativeQuery = true)
	List<Map<String, Object>> findPersonnel(@Param("ownerNm") String ownerNm);

	/**
	 * 修改用户的界定状态
	 * 
	 * @param isProduce 0待界定 1不通过 2通过 3界定中
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "update t_info_family_impl set is_produce = :isProduce where owner_nm = :ownerNm", nativeQuery = true)
	void updateUser(@Param("isProduce") String isProduce, @Param("ownerNm") String ownerNm);

	@Modifying
	@Query(value = "update t_info_owner_impl set is_produce = :isProduce where nm = :ownerNm", nativeQuery = true)
	void updateOwner(@Param("isProduce") String isProduce, @Param("ownerNm") String ownerNm);

	@Query(value = "select owner_nm from t_abm_produce_process where process_id = ?1", nativeQuery = true)
	String getOwnerNmByTaskId(String taskId);

	/**
	 * 修改用户的界定状态
	 * 
	 * @param isProduce 是否是生产安置人口 0:不是 1：是
	 * @param nm        内码
	 */
	@Modifying
	@Query(value = "update t_info_family_impl set is_produce = :isProduce where nm = :nm", nativeQuery = true)
	void updateUserisProduce(@Param("isProduce") String isProduce, @Param("nm") String nm);

	/**
	 * 点击汇总数字时显示卡片
	 */
	@Query(value = "select tt.name,count(t1.id) as households,(select count(1) from t_info_family_impl where owner_nm  in (select t1.nm from pub_dict_value pdv  "
			+ " left join t_info_owner_impl t1 on pdv.nm = t1.place_type and t1.is_produce = 2  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where pdv.name = tt.name and t2.merger_name like CONCAT('%','云南省','%')))as people "
			+ " from pub_dict_value tt  "
			+ " left join t_info_owner_impl t1 on tt.nm = t1.place_type and t1.is_produce = 2  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where listnm_sys_dict_cate = 'dict_move_type' and t2.merger_name like CONCAT('%','云南省','%')  "
			+ " GROUP BY tt.name ", nativeQuery = true)
	List<ProduceCardStatisticsVO> getCardStatisiscs(@Param("region") String region);

	/**
	 * 根据流程查询界定确认信息
	 */
	@Query(value = "select t1.name,t2.`name` scope,t1.id_card idCard,t3.merger_name mergerName,tt.create_time createTime,t4.name households,  "
			+ "(select file_url from pub_files where table_pk_column = CONCAT('produce_',tt.owner_nm)  order by upload_time desc LIMIT 1) fileUrl, "
			+ "(select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column =  CONCAT('produce_',tt.owner_nm)  order by upload_time desc LIMIT 1) fileName   "
			+ "from t_abm_produce_process tt  left join t_info_owner_impl t1 on tt.owner_nm = t1.nm    "
			+ "left join pub_dict_value t2 on t1.scope = t2.nm    "
			+ "left join pub_region t3 on t1.region = t3.city_code     "
			+ "left join pub_dict_value t4 on t1.households_type = t4.nm    "
			+ "where tt.process_id = ?1 ", nativeQuery = true)
	ProduceProcessInfoVO getInfoByTaskId(String taskId);

	/**
	 * 托巴水电站建设区征地补偿补偿协议书 导出 查询权属人土地信息
	 * 
	 * @param ownerNm
	 * @return
	 */
//	@Query(value = "select t1.name,tt.area,(select name from pub_project where fcode = left(t2.fcode, 12)) as type " + 
//			"from (select tl.type_two as type,sum(tl.area) area,tl.project_nm from t_info_land_impl tl " + 
//			"left join pub_dict_value pdv on tl.type_two = pdv.nm  " + 
//			" where tl.owner_nm = ?1 and (tl.type_three is null or tl.type_three = '' or pdv.name ='旱地') group by tl.type_two,tl.all_type  " + 
//			"UNION   " + 
//			"select tl.type_three as type,sum(tl.area) area,tl.project_nm  from t_info_land_impl tl " + 
//			"left join pub_dict_value pdv on tl.type_two = pdv.nm  " + 
//			"where tl.owner_nm = ?1 and tl.type_three is not null and tl.type_three != '' and pdv.name != '旱地' group by tl.type_three,tl.all_type) tt  " + 
//			"left join pub_dict_value t1 on tt.type = t1.nm left join pub_project t2 on tt.project_nm = t2.id ",nativeQuery = true)
	@Query(value = "select tt.type,t1.name,tt.area,t2.nm scope"
			+ " from (select tl.type_two as type,sum(tl.area) area,tl.scope from t_info_land_impl tl  "
			+ " left join pub_dict_value pdv on tl.type_two = pdv.nm   "
			+ " where tl.owner_nm = ?1 and (tl.type_three is null or tl.type_three = '' or pdv.name ='水田') group by tl.type_two,tl.scope   "
			+ " UNION    " + " select tl.type_three as type,sum(tl.area) area,tl.scope  from t_info_land_impl tl  "
			+ " left join pub_dict_value pdv on tl.type_three = pdv.nm   "
			+ " where tl.owner_nm = ?1 and tl.type_three is not null and tl.type_three != '' and pdv.name != '水田' group by tl.type_three,tl.scope) tt   "
			+ " left join pub_dict_value t1 on tt.type = t1.nm left join pub_dict_value t2 on tt.scope = t2.nm ", nativeQuery = true)
	List<Map<String, Object>> getLandInfoByOwnerNm(String ownerNm);

	@Query(value = "select tt.name,SUBSTRING_INDEX(t1.merger_name,',',-3) AS region from t_info_owner_impl tt "
			+ "LEFT JOIN pub_region t1 on tt.region = t1.city_code where tt.nm = ?1", nativeQuery = true)
	Map<String, Object> getInfoByOwnerNm(String ownerNm);

	/**
	 * 查询折算系数
	 * 
	 * @return
	 */
	@Query(value = "select land_type AS landType,coefficient from t_abm_convert_coefficient", nativeQuery = true)
	List<Map<String, Object>> getConvertCoefficient();

	/**
	 * 获取小组人均耕地当量
	 * 
	 * @param region
	 * @return
	 */
	@Query(value = "select ifnull(ROUND(plowland_count/farmer_count,2),0) from t_abm_produce_benchmark_impl where region =?1 limit 1", nativeQuery = true)
	Double getCultivatedLandEquivalentGroup(String region);
	
	@Modifying
	@Query(value = "update t_abm_produce_benchmark_impl set people_counting = ?2 where region = ?1",nativeQuery = true)
	void u(String region,Double count);
	
	@Query(value = "select region,people_counting count from t_abm_produce_benchmark_impl ",nativeQuery = true)
	List<Map<String,Object>> findBenchmark();
}
