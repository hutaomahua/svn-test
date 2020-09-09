package com.lyht.business.abm.landAllocation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import org.springframework.data.repository.query.Param;

public interface LandPoolDao extends JpaRepository<LandPoolEntity, Integer> {

	@Modifying
	@Query(value = "update t_abm_land_pool set separate_area =:separateArea where id = :id", nativeQuery = true)
	void updateSeparateArea(@Param("separateArea") Double separateArea, @Param("id") Integer id);

	@Modifying
	@Query(value = "update t_abm_land_pool set area =:area where id = :id", nativeQuery = true)
	void updateArea(@Param("area") Double area, @Param("id") Integer id);

	/**
	 * 查询汇总数据
	 * 
	 * @return
	 */
	@Query(value = "SELECT dict.name scopename," + "til.scope 		scope," + "til.all_type 	alltype,"
			+ "til.type_one 	typeone," + "til.type_two 	typetwo," + "til.type_three 	typethree,"
			+ "sum(til.area) 	area " + "from t_info_land til "
			+ "LEFT JOIN pub_region pr on til.region = pr.city_code "
			+ "LEFT JOIN pub_dict_value dict on dict.listnm_sys_dict_cate = 'dict_limits' and til.scope = dict.nm "
			+ "GROUP BY til.scope,til.all_type,til.type_one,til.type_two,til.type_three;", nativeQuery = true)
	List<Map<String, Object>> queryTotalData();

	/**
	 * 查询精确到底层的基础数据
	 */
	@Query(value = "SELECT		 til.region 		region,		 pr.NAME 			regionname,	"
			+ "pr.LEVEL 		regionlevel, pr.level_type 	regionleveltype,  til.scope 		scope,		"
			+ "til.all_type 	alltype,	 til.type_one 	typeone,	 til.type_two 	typetwo,	"
			+ "til.type_three 	typethree,	 sum(til.area)	area 		 FROM				"
			+ "t_info_land til	 LEFT JOIN pub_region pr ON til.region = pr.city_code  GROUP BY			"
			+ "til.region,		 til.scope,		 til.all_type,	 til.type_one,	 til.type_two,	"
			+ "til.type_three	", nativeQuery = true)
	List<Map<String, Object>> queryAllData();

	/**
	 * 查询组的已分解数据
	 * 
	 * @return
	 */
	@Query(value = "SELECT " + "til.region region, " + "pr. NAME regionname, " + "pr. LEVEL regionlevel, "
			+ "pr.level_type regionleveltype, " + "til.scope scope, " + "til.all_type alltype, "
			+ "til.type_one typeone, " + "til.type_two typetwo, " + "til.type_three typethree, " + "sum(til.area) area "
			+ "FROM " + "	t_info_land til " + "LEFT JOIN pub_region pr ON til.region = pr.city_code  "
			+ "where pr.`level` = 'group'  and  til.id_card is not null  " + " GROUP BY til.region, " + " til.scope, "
			+ " til.all_type, " + " til.type_one, " + " til.type_two, " + " til.type_three", nativeQuery = true)
	List<Map<String, Object>> queryGroupSeparateData();

	@Query(value = "SELECT  " 
			+ "til.project_nm 	projectnm,  " 
			+ "til.region 		region,  " 
			+ "pr. NAME 		regionname,  " 
			+ "pr. LEVEL 		regionlevel,  "
			+ "pr.level_type 	regionleveltype,  " 
			+ "til.scope 		scope,  " 
			+ "til.all_type 	alltype,  "
			+ "til.unit 		unit,  " 
			+ "til.type_one 	typeone,  " 
			+ "til.type_two 	typetwo,  " 
			+ "til.type_three 	typethree,  " 
			+ "til.area 		area , "
			+ "til.owner_nm		ownernm,  " 
			+ "til.id_card		idcard,  " 
			+ "til.land_project_nm landprojectnm  " 
			+ "FROM  " + "	t_info_land til  "
			+ "LEFT JOIN pub_region pr ON til.region = pr.city_code   "
			+ "where pr.`level` = 'group'  and  (til.id_card is not null or til.owner_nm is not null)", nativeQuery = true)
	List<Map<String, Object>> queryOwnerLandData();

	/**
	 * 查询土地分解所有统计列表数据
	 * 
	 * @return
	 */
	@Query(value = "SELECT pr.name AS cityName,pr.merger_name mergerName, pr.city_code AS cityCode, "
			+ " pr.parent_code AS parentCode, pr.level, SUM(tp.area) AS totalArea, "
			+ " SUM(tp.separate_area) AS useArea,(SUM(tp.area)-SUM(tp.separate_area)) AS remainArea "
			+ " FROM t_abm_land_pool tp  LEFT JOIN pub_region pr ON tp.region = pr.city_code "
			+ " where pr.merger_name not like CONCAT('云南省,迪庆藏族自治州,香格里拉市','%') and "
			+ " pr.merger_name not like CONCAT('云南省,迪庆藏族自治州,德钦县','%') and pr.level_type >2 GROUP BY pr.merger_name "
			+ " ORDER BY useArea DESC ", nativeQuery = true)
	List<Map<String, Object>> queryStatisticsList();

	/**
	 * 根据行政区编码查询查询子所有的子行政区（简易开发 使用模糊查询行政区全称方式）
	 * 
	 * @param cityCode 行政区编码
	 * @return
	 */
	@Query(value = "SELECT id as id,nm as nm,city_code as cityCode,parent_code as parentCode,"
			+ "NAME as name FROM pub_region WHERE parent_code = :cityCode   and name <> '德钦县' and name <> '香格里拉市'", nativeQuery = true)
	List<Map<String, Object>> queryChildCity(@Param("cityCode") String cityCode);

	/**
	 * 查询所有土地分解到户详情
	 * 
	 * @return
	 */
	@Query(value = "SELECT tli.region as parentCode, IFNULL(tfi.name, tli.owner_nm) as name, "
			+ "INSERT (tfi.id_card, 7, 13, '************') AS idCard,"
			+ " tli.owner_nm AS ownerNm, SUM(tli.area) AS area " + " FROM t_info_land_impl tli "
			+ " INNER JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three' "
			+ " INNER JOIN t_info_family_impl tfi ON tli.id_card = tfi.id_card "
			+ " GROUP BY tli.owner_nm ORDER BY NULL ", nativeQuery = true)
	List<Map<String, Object>> queryLandInfo();

	/**
	 * 根据户主和身份证模糊查询所有土地分解到户详情
	 * 
	 * @param name
	 * @param idCard
	 * @return
	 */
	@Query(value = "SELECT tli.region as parentCode, IFNULL(tfi.name, tli.owner_nm) as name, "
			+ "INSERT (tfi.id_card, 7, 13, '************') AS idCard,"
			+ " tli.owner_nm AS ownerNm, SUM(tli.area) AS area " + " FROM t_info_land_impl tli "
			+ " LEFT JOIN t_info_family_impl tfi ON tli.id_card = tfi.id_card "
			+ " INNER JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three' "
			+ " INNER JOIN (SELECT id_card FROM t_info_family_impl "
			+ " WHERE if(:name is not null or :name != '', name LIKE CONCAT('%', :name,'%'), 1=1) "
			+ " and if(:idCard is not null or :idCard != '', id_card LIKE CONCAT('%', :idCard,'%'), 1=1)) t1 ON t1.id_card = tli.id_card "
			+ " GROUP BY tli.owner_nm ORDER BY NULL ", nativeQuery = true)
	List<Map<String, Object>> queryLandInfo(@Param("name") String name, @Param("idCard") String idCard);

	/**
	 * 根据户主和身份证模糊查询所有土地分解到户详情
	 * 
	 * @param name
	 * @param idCard
	 * @return
	 */
	@Query(value = "SELECT tili.region AS parentCode,  tt.name AS name,"
			+ " IFNULL(tt.id_card,'无') AS idCard,  tt.nm AS ownerNm,"
			+ " IFNULL(SUM(tili.area),0) AS area  FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_info_land_impl tili ON tt.nm = tili.owner_nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code WHERE 1 = 1"
			+ " AND IF(:mergerName IS NOT NULL, pr.merger_name LIKE CONCAT('%', :mergerName,'%'), 1 = 1)"
			+ " AND IF(:name IS NOT NULL, tt.name LIKE CONCAT('%',:name,'%'), 1 = 1)"
			+ " AND IF(:idCard IS NOT NULL, tt.id_card LIKE CONCAT('%',:idCard,'%'), 1 = 1)"
			+ " GROUP BY tt.nm", countQuery = "SELECT count(1) " + " FROM t_info_owner_impl tt"
					+ " LEFT JOIN t_info_land_impl tili ON tt.nm = tili.owner_nm"
					+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1 = 1"
					+ " AND IF(:mergerName IS NOT NULL, pr.merger_name LIKE CONCAT('%', :mergerName, '%'), 1 = 1)"
					+ " AND IF(:name IS NOT NULL, tt.name LIKE CONCAT('%',:name,'%'), 1 = 1)"
					+ " AND IF(:idCard IS NOT NULL, tt.id_card LIKE CONCAT('%',:idCard,'%'), 1 = 1)"
					+ " GROUP BY tt.nm", nativeQuery = true)
	Page<Map<String, Object>> queryLandInfoByRegion(@Param("mergerName") String mergerName, @Param("name") String name,
			@Param("idCard") String idCard, Pageable pageable);

	@Query(value = "SELECT tfi.region,tfi.name,tfi.owner_nm,t.type_two,t.type_three,INSERT (tfi.id_card, 7, 13, '************') AS idCard "
			+ "FROM t_info_land_impl as t " + "LEFT JOIN t_info_family_impl tfi ON t.id_card = tfi.id_card "
			+ " WHERE if(:region is not null or :region != '', t.region =:region ,1=1) "
			+ " and if(:typeAlls is not null or :typeAlls != '', t.all_type = :typeAlls ,1=1) "
			+ " and if(:typeOnes is not null or :typeOnes != '', t.type_one = :typeOnes ,1=1) "
			+ " and if(:typeTwos is not null or :typeTwos != '', t.type_two = :typeTwos ,1=1) "
			+ " and if(:typeThrees is not null or :typeThrees != '', t.type_three = :typeThrees ,1=1) "
			+ "GROUP BY t.owner_nm ORDER BY t.region DESC ", countQuery = "SELECT count(*) "
					+ "FROM t_info_land_impl as t " + "LEFT JOIN t_info_family_impl tfi ON t.id_card = tfi.id_card "
					+ " WHERE if(:region is not null or :region != '', t.region =:region ,1=1) "
					+ " and if(:typeAlls is not null or :typeAlls != '', t.all_type = :typeAlls ,1=1) "
					+ " and if(:typeOnes is not null or :typeOnes != '', t.type_one = :typeOnes ,1=1) "
					+ " and if(:typeTwos is not null or :typeTwos != '', t.type_two = :typeTwos ,1=1) "
					+ " and if(:typeThrees is not null or :typeThrees != '', t.type_three = :typeThrees ,1=1) "
					+ "GROUP BY t.owner_nm ORDER BY t.region DESC ", nativeQuery = true)
	Page<Map<String, Object>> queryOwnerList(@Param("region") String region, @Param("typeAlls") String typeAlls,
			@Param("typeOnes") String typeOnes, @Param("typeTwos") String typeTwo,
			@Param("typeThrees") String typeThrees, Pageable pageable);

	@Query(value = "SELECT til.nm,IFNULL(pdv.name,'无') as typeName,sum1.sum1,(IFNULL(sum1.sum1 ,0)+IFNULL(sum2.sum2,0)) as count1,"
			+ "sum2.sum2,sum3.sum3,sum4.sum4,sum5.sum5,sum6.sum6,(IFNULL(sum3.sum3 ,0)+IFNULL(sum4.sum4,0)+IFNULL(sum5.sum5,0)) as count2 "
			+ "FROM t_abm_land_pool as til " + "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.all_type "
			+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
			+ "LEFT JOIN (SELECT SUM(area) as sum1,all_type FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY all_type,scope) as sum1 on sum1.all_type = til.all_type "
			+ "LEFT JOIN (SELECT SUM(area) as sum2,all_type FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY all_type,scope) as sum2 on sum2.all_type = til.all_type "
			+ "LEFT JOIN (SELECT SUM(area) as sum3,all_type FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY all_type,scope) as sum3 on sum3.all_type = til.all_type "
			+ "LEFT JOIN (SELECT SUM(area) as sum4,all_type FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY all_type,scope) as sum4 on sum4.all_type = til.all_type "
			+ "LEFT JOIN (SELECT SUM(area) as sum5,all_type FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY all_type,scope) as sum5 on sum5.all_type = til.all_type "
			+ "LEFT JOIN (SELECT SUM(area) as sum6,all_type FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY all_type,scope) as sum6 on sum6.all_type = til.all_type "
			+ "GROUP BY til.all_type ", countQuery = "SELECT count (*) " + "FROM t_abm_land_pool as til "
					+ "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.all_type "
					+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
					+ "LEFT JOIN (SELECT SUM(area) as sum1,all_type FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY all_type,scope) as sum1 on sum1.all_type = til.all_type "
					+ "LEFT JOIN (SELECT SUM(area) as sum2,all_type FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY all_type,scope) as sum2 on sum2.all_type = til.all_type "
					+ "LEFT JOIN (SELECT SUM(area) as sum3,all_type FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY all_type,scope) as sum3 on sum3.all_type = til.all_type "
					+ "LEFT JOIN (SELECT SUM(area) as sum4,all_type FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY all_type,scope) as sum4 on sum4.all_type = til.all_type "
					+ "LEFT JOIN (SELECT SUM(area) as sum5,all_type FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY all_type,scope) as sum5 on sum5.all_type = til.all_type "
					+ "LEFT JOIN (SELECT SUM(area) as sum6,all_type FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY all_type,scope) as sum6 on sum6.all_type = til.all_type "
					+ "GROUP BY til.all_type ", nativeQuery = true)
	Page<Map<String, Object>> queryTypeList(@Param("region") String region, Pageable pageable);

	@Query(value = "SELECT til.nm,IFNULL(pdv.name,'无') as typeName,sum1.sum1,(IFNULL(sum1.sum1 ,0)+IFNULL(sum2.sum2,0)) as count1,"
			+ "sum2.sum2,sum3.sum3,sum4.sum4,sum5.sum5,sum6.sum6,(IFNULL(sum3.sum3 ,0)+IFNULL(sum4.sum4,0)+IFNULL(sum5.sum5,0)) as count2 "
			+ "FROM t_abm_land_pool as til " + "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.type_one "
			+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
			+ "LEFT JOIN (SELECT SUM(area) as sum1,type_one FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY type_one,scope) as sum1 on sum1.type_one = til.type_one "
			+ "LEFT JOIN (SELECT SUM(area) as sum2,type_one FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY type_one,scope) as sum2 on sum2.type_one = til.type_one "
			+ "LEFT JOIN (SELECT SUM(area) as sum3,type_one FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY type_one,scope) as sum3 on sum3.type_one = til.type_one "
			+ "LEFT JOIN (SELECT SUM(area) as sum4,type_one FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY type_one,scope) as sum4 on sum4.type_one = til.type_one "
			+ "LEFT JOIN (SELECT SUM(area) as sum5,type_one FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY type_one,scope) as sum5 on sum5.type_one = til.type_one "
			+ "LEFT JOIN (SELECT SUM(area) as sum6,type_one FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY type_one,scope) as sum6 on sum6.type_one = til.type_one "
			+ "where til.all_type = :typeNm " + "GROUP BY til.type_one ", countQuery = "SELECT count (*) "
					+ "FROM t_abm_land_pool as til " + "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.type_one "
					+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
					+ "LEFT JOIN (SELECT SUM(area) as sum1,type_one FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY type_one,scope) as sum1 on sum1.type_one = til.type_one "
					+ "LEFT JOIN (SELECT SUM(area) as sum2,type_one FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY type_one,scope) as sum2 on sum2.type_one = til.type_one "
					+ "LEFT JOIN (SELECT SUM(area) as sum3,type_one FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY type_one,scope) as sum3 on sum3.type_one = til.type_one "
					+ "LEFT JOIN (SELECT SUM(area) as sum4,type_one FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY type_one,scope) as sum4 on sum4.type_one = til.type_one "
					+ "LEFT JOIN (SELECT SUM(area) as sum5,type_one FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY type_one,scope) as sum5 on sum5.type_one = til.type_one "
					+ "LEFT JOIN (SELECT SUM(area) as sum6,type_one FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY type_one,scope) as sum6 on sum6.type_one = til.type_one "
					+ "where til.all_type = :typeNm " + "GROUP BY til.type_one ", nativeQuery = true)
	Page<Map<String, Object>> queryOneTypeList(@Param("region") String region, @Param("typeNm") String typeNm,
			Pageable pageable);

	@Query(value = "SELECT til.nm,IFNULL(pdv.name,'无') as typeName,sum1.sum1,(IFNULL(sum1.sum1 ,0)+IFNULL(sum2.sum2,0)) as count1,"
			+ "sum2.sum2,sum3.sum3,sum4.sum4,sum5.sum5,sum6.sum6,(IFNULL(sum3.sum3 ,0)+IFNULL(sum4.sum4,0)+IFNULL(sum5.sum5,0)) as count2 "
			+ "FROM t_abm_land_pool as til " + "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.type_two "
			+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
			+ "LEFT JOIN (SELECT SUM(area) as sum1,type_two FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY type_two,scope) as sum1 on sum1.type_two = til.type_two "
			+ "LEFT JOIN (SELECT SUM(area) as sum2,type_two FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY type_two,scope) as sum2 on sum2.type_two = til.type_two "
			+ "LEFT JOIN (SELECT SUM(area) as sum3,type_two FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY type_two,scope) as sum3 on sum3.type_two = til.type_two "
			+ "LEFT JOIN (SELECT SUM(area) as sum4,type_two FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY type_two,scope) as sum4 on sum4.type_two = til.type_two "
			+ "LEFT JOIN (SELECT SUM(area) as sum5,type_two FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY type_two,scope) as sum5 on sum5.type_two = til.type_two "
			+ "LEFT JOIN (SELECT SUM(area) as sum6,type_two FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY type_two,scope) as sum6 on sum6.type_two = til.type_two "
			+ "where til.type_one = :typeNm " + "GROUP BY til.type_two ", countQuery = "SELECT count (*) "
					+ "FROM t_abm_land_pool as til " + "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.type_two "
					+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
					+ "LEFT JOIN (SELECT SUM(area) as sum1,type_two FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY type_two,scope) as sum1 on sum1.type_two = til.type_two "
					+ "LEFT JOIN (SELECT SUM(area) as sum2,type_two FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY type_two,scope) as sum2 on sum2.type_two = til.type_two "
					+ "LEFT JOIN (SELECT SUM(area) as sum3,type_two FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY type_two,scope) as sum3 on sum3.type_two = til.type_two "
					+ "LEFT JOIN (SELECT SUM(area) as sum4,type_two FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY type_two,scope) as sum4 on sum4.type_two = til.type_two "
					+ "LEFT JOIN (SELECT SUM(area) as sum5,type_two FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY type_two,scope) as sum5 on sum5.type_two = til.type_two "
					+ "LEFT JOIN (SELECT SUM(area) as sum6,type_two FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY type_two,scope) as sum6 on sum6.type_two = til.type_two "
					+ "where til.type_one = :typeNm " + "GROUP BY til.type_two ", nativeQuery = true)
	Page<Map<String, Object>> queryTwoTypeList(@Param("region") String region, @Param("typeNm") String typeNm,
			Pageable pageable);

	@Query(value = "SELECT til.nm,IFNULL(pdv.name,'无') as typeName,sum1.sum1,(IFNULL(sum1.sum1 ,0)+IFNULL(sum2.sum2,0)) as count1,"
			+ "sum2.sum2,sum3.sum3,sum4.sum4,sum5.sum5,sum6.sum6,(IFNULL(sum3.sum3 ,0)+IFNULL(sum4.sum4,0)+IFNULL(sum5.sum5,0)) as count2 "
			+ "FROM t_abm_land_pool as til " + "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.type_three "
			+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
			+ "LEFT JOIN (SELECT SUM(area) as sum1,type_three FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY type_three,scope) as sum1 on sum1.type_three = til.type_three "
			+ "LEFT JOIN (SELECT SUM(area) as sum2,type_three FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY type_three,scope) as sum2 on sum2.type_three = til.type_three "
			+ "LEFT JOIN (SELECT SUM(area) as sum3,type_three FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY type_three,scope) as sum3 on sum3.type_three = til.type_three "
			+ "LEFT JOIN (SELECT SUM(area) as sum4,type_three FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY type_three,scope) as sum4 on sum4.type_three = til.type_three "
			+ "LEFT JOIN (SELECT SUM(area) as sum5,type_three FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY type_three,scope) as sum5 on sum5.type_three = til.type_three "
			+ "LEFT JOIN (SELECT SUM(area) as sum6,type_three FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY type_three,scope) as sum6 on sum6.type_three = til.type_three "
			+ "where til.type_two = :typeNm and til.type_three != \"\" "
			+ "GROUP BY til.type_three ", countQuery = "SELECT count (*) " + "FROM t_abm_land_pool as til "
					+ "LEFT JOIN pub_dict_value as pdv ON pdv.nm = til.type_three "
					+ "INNER JOIN pub_region as pr ON pr.city_code = til.region AND pr.merger_name LIKE CONCAT('%', :region,'%') "
					+ "LEFT JOIN (SELECT SUM(area) as sum1,type_three FROM t_info_land_impl WHERE scope = \"24ACBF9107\" GROUP BY type_three,scope) as sum1 on sum1.type_three = til.type_three "
					+ "LEFT JOIN (SELECT SUM(area) as sum2,type_three FROM t_info_land_impl WHERE scope = \"CE81C0FA94\" GROUP BY type_three,scope) as sum2 on sum2.type_three = til.type_three "
					+ "LEFT JOIN (SELECT SUM(area) as sum3,type_three FROM t_info_land_impl WHERE scope = \"D8D5AAD9DC\" GROUP BY type_three,scope) as sum3 on sum3.type_three = til.type_three "
					+ "LEFT JOIN (SELECT SUM(area) as sum4,type_three FROM t_info_land_impl WHERE scope = \"E78D14E7BE\" GROUP BY type_three,scope) as sum4 on sum4.type_three = til.type_three "
					+ "LEFT JOIN (SELECT SUM(area) as sum5,type_three FROM t_info_land_impl WHERE scope = \"D18482159A\" GROUP BY type_three,scope) as sum5 on sum5.type_three = til.type_three "
					+ "LEFT JOIN (SELECT SUM(area) as sum6,type_three FROM t_info_land_impl WHERE scope = \"348F5B68BA\" GROUP BY type_three,scope) as sum6 on sum6.type_three = til.type_three "
					+ "where til.type_two = :typeNm and til.type_three != \"\" "
					+ "GROUP BY til.type_three ", nativeQuery = true)
	Page<Map<String, Object>> queryThreeTypeList(@Param("region") String region, @Param("typeNm") String typeNm,
			Pageable pageable);

	@Query(value = "SELECT SUM(area) FROM  t_info_land_impl where type_two = :landType and owner_nm = :ownerNm ", nativeQuery = true)
	Object countOwnerTypeTwo(@Param("landType") String landType, @Param("ownerNm") String ownerNm);

	@Query(value = "SELECT SUM(area) FROM  t_info_land_impl where type_three = :landType and owner_nm = :ownerNm ", nativeQuery = true)
	Object countOwnerTypeThree(@Param("landType") String landType, @Param("ownerNm") String ownerNm);

	@Query(value = "SELECT SUM(area) as sum1,owner_nm,type_two FROM  t_info_land_impl where region = :region GROUP BY owner_nm,type_two ;", nativeQuery = true)
	List<Map<String, Object>> countList(@Param("region") String region);

	@Query(value = " (SELECT CONCAT('sum',pdv.nm)as nm ,pdv.name,pdv.sorted,'2' level FROM t_info_land_impl as til LEFT JOIN pub_dict_value as pdv on til.type_two = pdv.nm "
			+ "WHERE til.type_three = \"\" and til.region = :region GROUP BY pdv.nm order by pdv.sorted) UNION "
			+ "(SELECT CONCAT('sum',pdv.nm)as nm ,pdv.name,pdv.sorted,'3' level FROM t_info_land_impl as til LEFT JOIN pub_dict_value as pdv on til.type_three = pdv.nm "
			+ "WHERE til.type_three != \"\" and til.region = :region GROUP BY pdv.nm order by pdv.sorted) ", nativeQuery = true)
	List<Map<String, Object>> queryTypeList(@Param("region") String region);

	/**
	 * 根据行政区编码查询户主信息
	 * 
	 * @param region
	 * @return
	 */
	@Query(value = "SELECT ifnull(abm.protocol_state,0) as protocolState, tfi.nm AS nm, tfi.name AS name, "
			+ " true as isUser, " + " tfi.region AS parentCode " + " FROM t_info_family_impl tfi "
			+ " LEFT JOIN pub_dict_value pv ON tfi.master_relationship = pv.nm AND pv.listnm_sys_dict_cate = 'dict_owner_relation' "
			+ " WHERE tfi.master_relationship = 'E2A29C1823' and  tfi.region = :region ", nativeQuery = true)
	List<Map<String, Object>> queryAllUserInfo(@Param("region") String region);

	/**
	 * 根据权属人查询该户土地面积情况
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT IFNULL(pv2.name, '未知类型') as landType, iFNULL(pv3.name, '未知区域') as limitsType, sum(tli.area) as area "
			+ " FROM  t_info_land_impl tli " + " LEFT JOIN t_info_family_impl tfi ON tli.id_card = tfi.id_card "
			+ " LEFT JOIN pub_region pr ON pr.city_code = tli.region "
			+ " LEFT JOIN pub_dict_value pv ON tfi.master_relationship = pv.nm AND pv.listnm_sys_dict_cate = 'dict_owner_relation' "
			+ " INNER JOIN pub_dict_value pv2 ON tli.type_three = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_three' "
			+ " LEFT JOIN pub_dict_value pv3 ON tli.scope = pv3.nm AND pv3.listnm_sys_dict_cate = 'dict_limits' "
			+ " WHERE tli.owner_nm = :ownerNm " + " GROUP BY landType,limitsType ", nativeQuery = true)
	List<Map<String, Object>> queryUserLandInfo(@Param("ownerNm") String ownerNm);

	@Query(value = " SELECT  " + " IFNULL(IF(pv4.name IS NOT NULL,  " + " CONCAT(IF(pv3.name IS NOT NULL,  "
			+ "  CONCAT((CONCAT(pv.name,'/',pv2.name)), '/', pv3.name),  "
			+ "   (CONCAT(pv.name,'/',pv2.name))), '/', pv4.name), "
			+ " IF(pv3.name IS NOT NULL, CONCAT((CONCAT(pv.name,'/',pv2.name)),'/',pv3.name), (CONCAT(pv.name,'/',pv2.name)))), '未知类型') AS landType, "
			+ " pv5.name AS scope, "
			+ " IF(:status = 'totalArea', SUM(tp.area), IF(:status = 'useArea',  SUM(tp.separate_area), IF(:status = 'remainArea', (SUM(tp.area)-SUM(tp.separate_area)), 0))) as area "
			+ "FROM t_abm_land_pool tp "
			+ "LEFT JOIN pub_dict_value pv ON tp.all_type = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type'   "
			+ "LEFT JOIN pub_dict_value pv2 ON tp.type_one = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_one'   "
			+ "LEFT JOIN pub_dict_value pv3 ON tp.type_two = pv3.nm AND pv3.listnm_sys_dict_cate = 'dict_info_land_type_two'    "
			+ "LEFT JOIN pub_dict_value pv4 ON tp.type_three = pv4.nm AND pv4.listnm_sys_dict_cate = 'dict_info_land_type_three'   "
			+ "LEFT JOIN pub_dict_value pv5 ON tp.scope =pv5.nm AND pv5.listnm_sys_dict_cate = 'dict_limits'  "
			+ "WHERE region = :region  " + "GROUP BY landType ", countQuery = " select count(*) from (" + " SELECT  "
					+ " IFNULL(IF(pv4.name IS NOT NULL,  " + " CONCAT(IF(pv3.name IS NOT NULL,  "
					+ "  CONCAT((CONCAT(pv.name,'/',pv2.name)), '/', pv3.name),  "
					+ "   (CONCAT(pv.name,'/',pv2.name))), '/', pv4.name), "
					+ " IF(pv3.name IS NOT NULL, CONCAT((CONCAT(pv.name,'/',pv2.name)),'/',pv3.name), (CONCAT(pv.name,'/',pv2.name)))), '未知类型') AS landType, "
					+ " pv5.name AS scope, "
					+ " IF(:status = 'totalArea', SUM(tp.area), IF(:status = 'useArea',  SUM(tp.separate_area), IF(:status = 'remainArea', (SUM(tp.area)-SUM(tp.separate_area)), 0))) as area "
					+ "FROM t_abm_land_pool tp "
					+ "LEFT JOIN pub_dict_value pv ON tp.all_type = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type'   "
					+ "LEFT JOIN pub_dict_value pv2 ON tp.type_one = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_one'   "
					+ "LEFT JOIN pub_dict_value pv3 ON tp.type_two = pv3.nm AND pv3.listnm_sys_dict_cate = 'dict_info_land_type_two'    "
					+ "LEFT JOIN pub_dict_value pv4 ON tp.type_three = pv4.nm AND pv4.listnm_sys_dict_cate = 'dict_info_land_type_three'   "
					+ "LEFT JOIN pub_dict_value pv5 ON tp.scope =pv5.nm AND pv5.listnm_sys_dict_cate = 'dict_limits'  "
					+ "WHERE region = :region  " + "GROUP BY landType ) t ", nativeQuery = true)
	Page<Map> queryLandInfoTable(@Param("region") String region, @Param("status") String status, Pageable pageable);

	/**
	 * 根据行政区编码查询行政区全称
	 * 
	 * @param region 行政区编码
	 * @return
	 */
	@Query(value = "select merger_name FROM pub_region WHERE city_code = :region ", nativeQuery = true)
	String findRegionMergerName(@Param("region") String region);

	/**
	 * 根据行政区编码查询户主信息
	 * 
	 * @param region
	 * @return
	 *//*
		 * @Query(value =
		 * "SELECT ifnull(abm.protocol_state,0) as protocolState, tfi.nm AS nm, tfi.name AS name, "
		 * + " true as isUser, " + " tfi.region AS parentCode " +
		 * " FROM t_info_family_impl tfi " +
		 * "LEFT JOIN t_abm_total_state abm ON abm.owner_nm=tfi.nm "+
		 * " LEFT JOIN pub_dict_value pv ON tfi.master_relationship = pv.nm AND pv.listnm_sys_dict_cate = 'dict_owner_relation' "
		 * +
		 * " WHERE tfi.master_relationship = 'E2A29C1823'  and  tfi.region = :region ",
		 * nativeQuery = true) List<Map<String, Object>>
		 * queryAllUserInfos(@Param("region") String region);
		 */

	/**
	 * 根据行政区编码查询户主信息
	 * 
	 * @author 成良歌
	 * @param region
	 * @return
	 */
	@Query(value = "SELECT 0 as protocolState, toi.nm AS nm, toi.name AS name, " + " true as isUser, "
			+ " toi.region AS parentCode " + " FROM t_info_owner_impl toi"
			+ " WHERE toi.region = :region  AND toi.nm NOT IN (SELECT toi.nm FROM t_abm_total_state abm LEFT JOIN t_info_owner_impl toi ON abm.owner_nm = toi.nm WHERE toi.region = :region AND abm.owner_nm = toi.nm AND (abm.levy_land_status != 0 or abm.production_status != 0))", nativeQuery = true)
	List<Map<String, Object>> queryAllUserInfos(@Param("region") String region);

	/**
	 * 查询六个条件下可分解面积
	 * 
	 * @param region
	 * @param scope
	 * @param allType
	 * @param typeOne
	 * @param typeTwo
	 * @param typeThree
	 * @return
	 */
	@Query(value = "select ifnull(tt.area - tt.separate_area,0) area from t_abm_land_pool tt  "
			+ "where tt.region = :region and tt.scope = :scope  "
			+ "and all_type = :allType and type_one = :typeOne and type_two = :typeTwo and type_three = :typeThree", nativeQuery = true)
	Double queryArea(@Param("region") String region, @Param("scope") String scope, @Param("allType") String allType,
			@Param("typeOne") String typeOne, @Param("typeTwo") String typeTwo, @Param("typeThree") String typeThree);

	/**
	 * 查询六个条件下可分解面积
	 * 
	 * @param region
	 * @param scope
	 * @param allType
	 * @param typeOne
	 * @param typeTwo
	 * @param typeThree
	 * @return
	 */
	@Query(value = "select tt.separate_area area from t_abm_land_pool tt  "
			+ "where tt.region = :region and tt.scope = :scope  "
			+ "and all_type = :allType and type_one = :typeOne and type_two = :typeTwo and if(:typeThree is not null,type_three = :typeThree,1=1)", nativeQuery = true)
	Double querySeparateArea(@Param("region") String region, @Param("scope") String scope,
			@Param("allType") String allType, @Param("typeOne") String typeOne, @Param("typeTwo") String typeTwo,
			@Param("typeThree") String typeThree);

	/**
	 * 
	 */
	@Modifying
	@Query(value = "update t_abm_land_pool set separate_area = :area " + " where region = :region and scope = :scope  "
			+ " and all_type = :allType and type_one = :typeOne and type_two = :typeTwo and if(:typeThree is not null,type_three = :typeThree,1=1)", nativeQuery = true)
	void updateSeparateArea(@Param("area") Double area, @Param("region") String region, @Param("scope") String scope,
			@Param("allType") String allType, @Param("typeOne") String typeOne, @Param("typeTwo") String typeTwo,
			@Param("typeThree") String typeThree);

	@Query(value = "select * from t_abm_land_pool tt  " + "where tt.region = :region and tt.scope = :scope  "
			+ "and all_type = :allType and type_one = :typeOne and type_two = :typeTwo and if(:typeThree is not null && :typeThree != '',type_three = :typeThree,1=1)", nativeQuery = true)
	LandPoolEntity queryBySixCondition(@Param("region") String region, @Param("scope") String scope,
			@Param("allType") String allType, @Param("typeOne") String typeOne, @Param("typeTwo") String typeTwo,
			@Param("typeThree") String typeThree);

	/**
	 * 系统设置 设置可分解状态查询列表
	 */
	@Query(value = "SELECT tt.id,t0. NAME region,t1. NAME scope,t2. NAME allType,t3. NAME typeone, "
			+ " t4. NAME typeTwo,t5. NAME typeThree,ROUND(tt.area,2) area,ROUND(tt.separate_area,2) separateArea,"
			+ " ROUND(tt.area - tt.separate_area,2) surplusArea,tt.`status` FROM t_abm_land_pool tt "
			+ " LEFT JOIN pub_region t0 ON tt.region = t0.city_code "
			+ " LEFT JOIN pub_dict_value t1 ON tt.scope = t1.nm LEFT JOIN pub_dict_value t2 ON tt.all_type = t2.nm "
			+ " LEFT JOIN pub_dict_value t3 ON tt.type_one = t3.nm LEFT JOIN pub_dict_value t4 ON tt.type_two = t4.nm "
			+ " LEFT JOIN pub_dict_value t5 ON tt.type_three = t5.nm "
			+ " where 1 = 1 and if(:scope is not null,tt.scope = :scope,1=1) and if(:region is not null,t0.merger_name like concat(:region,'%'),1=1)"
			+ " and if(:allType is not null,tt.all_type = :allType,1=1) and if(:typeOne is not null,tt.type_one = :typeOne,1=1) "
			+ " and if(:typeTwo is not null,tt.type_two = :typeTwo,1=1) and if(:typeThree is not null,tt.type_three = :typeThree,1=1)"
			+ " ORDER BY t0.level_type", countQuery = "select count(1) from (SELECT tt.id,t0. NAME region,t1. NAME scope,t2. NAME allType,t3. NAME typeone, "
					+ " t4. NAME typeTwo,t5. NAME typeThree,ROUND(tt.area,2) area,ROUND(tt.separate_area,2) separateArea,"
					+ " ROUND(tt.area - tt.separate_area,2) surplusArea,tt.`status` FROM t_abm_land_pool tt "
					+ " LEFT JOIN pub_region t0 ON tt.region = t0.city_code "
					+ " LEFT JOIN pub_dict_value t1 ON tt.scope = t1.nm LEFT JOIN pub_dict_value t2 ON tt.all_type = t2.nm "
					+ " LEFT JOIN pub_dict_value t3 ON tt.type_one = t3.nm LEFT JOIN pub_dict_value t4 ON tt.type_two = t4.nm "
					+ " LEFT JOIN pub_dict_value t5 ON tt.type_three = t5.nm "
					+ " where 1 = 1 and if(:scope is not null,tt.scope = :scope,1=1) and if(:region is not null,t0.merger_name like concat(:region,'%'),1=1)"
					+ " and if(:allType is not null,tt.all_type = :allType,1=1) and if(:typeOne is not null,tt.type_one = :typeOne,1=1) "
					+ " and if(:typeTwo is not null,tt.type_two = :typeTwo,1=1) and if(:typeThree is not null,tt.type_three = :typeThree,1=1)) as temp", nativeQuery = true)
	Page<Map<String, Object>> page(@Param("scope") String scope, @Param("region") String region,
			@Param("allType") String allType, @Param("typeOne") String typeOne, @Param("typeTwo") String typeTwo,
			@Param("typeThree") String typeThree, Pageable pageable);

	@Query(value = "select * from t_abm_land_pool where region = :region and if(:typeThree is null,"
			+ " type_two = :typeTwo,type_three = :typeThree)  and scope = :scope ", nativeQuery = true)
	LandPoolEntity queryByFourCondition(@Param("region") String region, @Param("scope") String scope,
			@Param("typeTwo") String typeTwo, @Param("typeThree") String typeThree);

}
