package com.lyht.business.abm.wechat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionHouseVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionLandVO;
import com.lyht.business.abm.wechat.vo.AbmWechatRegionOwnerVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeAggregateVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeHouseVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeLandVO;
import com.lyht.business.abm.wechat.vo.AbmWechatScopeOwnerVO;

import java.util.List;

public interface AbmWechatInfoAggregateDao extends JpaRepository<AbmOwnerEntity, Integer> {

	/**
	 * 按征地范围统计
	 * 
	 * @return
	 */
	@Query(value = "SELECT" + " pdv.name AS scope,"
			+ " (SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.scope = pdv.nm) AS households,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.scope = pdv.nm) AS population,"
			+ " (SELECT IFNULL(SUM(tihi.area),0) FROM t_info_houses_impl tihi WHERE tihi.scope = pdv.nm) AS houseArea,"
			+ " (SELECT IFNULL(SUM(tili.area),0) FROM t_info_land_impl tili WHERE tili.scope = pdv.nm) AS landArea"
			+ " FROM pub_dict_value pdv"
			+ " WHERE pdv.listnm_sys_dict_cate = 'dict_limits' AND pdv.name NOT IN('枢纽工程建设区', '水库淹没影响区', '枢纽区')", nativeQuery = true)
	List<AbmWechatScopeAggregateVO> findAggregateByScope();
	
	/**
	 * 征地范围--权属人--列表
	 * 
	 * @return
	 */
	@Query(value = "SELECT" + " pdv.name AS scope,"
			+ " (SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.scope = pdv.nm) AS households,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.scope = pdv.nm) AS population"
			+ " FROM pub_dict_value pdv"
			+ " WHERE pdv.listnm_sys_dict_cate = 'dict_limits' AND pdv.name NOT IN('枢纽工程建设区', '水库淹没影响区', '枢纽区')", nativeQuery = true)
	List<AbmWechatScopeOwnerVO> findAggregateScopeByOwner();
	
	/**
	 * 征地范围--房屋--列表
	 * @return
	 */
	@Query(value = "SELECT" + " pdv.name AS scope,"
			+ " (SELECT IFNULL(SUM(tihi.area),0) FROM t_info_houses_impl tihi WHERE tihi.scope = pdv.nm) AS houseArea"
			+ " FROM pub_dict_value pdv"
			+ " WHERE pdv.listnm_sys_dict_cate = 'dict_limits' AND pdv.name NOT IN('枢纽工程建设区', '水库淹没影响区', '枢纽区')", nativeQuery = true)
	List<AbmWechatScopeHouseVO> findAggregateScopeByHouse();
	
	/**
	 * 征地范围--土地--列表
	 * @return
	 */
	@Query(value = "SELECT" + " pdv.name AS scope,"
			+ " (SELECT IFNULL(SUM(tili.area),0) FROM t_info_land_impl tili WHERE tili.scope = pdv.nm) AS landArea"
			+ " FROM pub_dict_value pdv"
			+ " WHERE pdv.listnm_sys_dict_cate = 'dict_limits' AND pdv.name NOT IN('枢纽工程建设区', '水库淹没影响区', '枢纽区')", nativeQuery = true)
	List<AbmWechatScopeLandVO> findAggregateScopeByLand();

//	/**
//	 * 按行政区域统计
//	 * 
//	 * @param mergerName 行政区--全称
//	 * @param type       指标类型
//	 * @return
//	 */
//	@Query(value = "SELECT * FROM (" + " SELECT" + " pr.merger_name AS mergerName, pr.name AS name,"
//			+ " (SELECT COUNT(1) FROM t_info_owner_impl tioi WHERE tioi.region = pr.city_code) AS households,"
//			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.region = pr.city_code) AS population,"
//			+ " (SELECT IFNULL(SUM(tihi.area),0) FROM t_info_houses_impl tihi WHERE tihi.region = pr.city_code) AS houseArea,"
//			+ " (SELECT IFNULL(SUM(tili.area),0) FROM t_info_land_impl tili WHERE tili.region = pr.city_code) AS landArea"
//			+ " FROM pub_region pr" + " WHERE 1 = 1"
//			+ " AND IF(:mergerName IS NOT NULL, pr.merger_name LIKE CONCAT('%', :mergerName, '%'), 1 = 1)" + " ) tt"
//			+ " WHERE 1 = 1" + " AND IF(:type = '" + AbmWechatConstant.AGGREGATE_TYPE_LAND + "', landArea != 0, 1 = 1)"
//			+ " AND IF(:type = '" + AbmWechatConstant.AGGREGATE_TYPE_HOUSE + "', houseArea != 0, 1 = 1)"
//			+ " AND IF(:type = '" + AbmWechatConstant.AGGREGATE_TYPE_OWNER + "', (households != 0 OR population != 0), 1 = 1)", nativeQuery = true)
//	List<AbmWechatRegionAggregateVO> findAggregateByRegion(@Param("mergerName") String mergerName,
//			@Param("type") String type);

	/**
	 * 行政区--权属人--列表
	 * @return
	 */
	@Query(value = "SELECT * FROM (" 
			+ " SELECT" 
			+ " pr.merger_name AS mergerName, pr.name AS name,"
			+ " pr.city_code AS cityCode,"
			+ " ("
			+ " SELECT"
			+ " COUNT(1)"
			+ " FROM t_info_owner_impl tioi"
			+ " LEFT JOIN pub_region tpr ON tioi.region = tpr.city_code"
			+ " WHERE tpr.merger_name LIKE CONCAT(pr.merger_name,'%')" 
			+ " ) AS households,"
			+ " ("
			+ " SELECT"
			+ " COUNT(1)"
			+ " FROM t_info_family_impl tifi"
			+ " INNER JOIN pub_region tpr ON tifi.region = tpr.city_code"
			+ " WHERE tpr.merger_name LIKE CONCAT(pr.merger_name,'%')" 
			+ " ) AS population"
			+ " FROM pub_region pr" 
			+ " WHERE 1 = 1"
			+ " AND IF(:parentCode IS NOT NULL AND :parentCode != '', pr.parent_code = :parentCode, 1 = 1)" 
			+ " ) tt"
			+ " WHERE tt.households != 0 OR tt.population != 0", nativeQuery = true)
	List<AbmWechatRegionOwnerVO> findAggregateRegionByOwner(@Param("parentCode") String parentCode);
	
	/**
	 * 行政区--房屋--列表
	 * @return
	 */
	@Query(value = "SELECT * FROM (" 
			+ " SELECT" 
			+ " pr.merger_name AS mergerName, pr.name AS name,"
			+ " pr.city_code AS cityCode,"
			+ " ("
			+ " SELECT"
			+ " IFNULL(SUM(tihi.area),0)"
			+ " FROM t_info_houses_impl tihi"
			+ " LEFT JOIN pub_region tpr ON tihi.region = tpr.city_code"
			+ " WHERE tpr.merger_name LIKE CONCAT(pr.merger_name,'%')" 
			+ " ) AS houseArea"
			+ " FROM pub_region pr" 
			+ " WHERE 1 = 1"
			+ " AND IF(:parentCode IS NOT NULL AND :parentCode != '', pr.parent_code = :parentCode, 1 = 1)" 
			+ " ) tt"
			+ " WHERE tt.houseArea != 0", nativeQuery = true)
	List<AbmWechatRegionHouseVO> findAggregateRegionByHouse(@Param("parentCode") String parentCode);
	
	/**
	 * 行政区--土地--列表
	 * @return
	 */
	@Query(value = "SELECT * FROM (" 
			+ " SELECT" 
			+ " pr.merger_name AS mergerName, pr.name AS name,"
			+ " pr.city_code AS cityCode,"
			+ " ("
			+ " SELECT"
			+ " IFNULL(SUM(tili.area),0)"
			+ " FROM t_info_land_impl tili"
			+ " LEFT JOIN pub_region tpr ON tili.region = tpr.city_code"
			+ " WHERE tpr.merger_name LIKE CONCAT(pr.merger_name,'%')" 
			+ " ) AS landArea"
			+ " FROM pub_region pr" 
			+ " WHERE 1 = 1"
			+ " AND IF(:parentCode IS NOT NULL AND :parentCode != '', pr.parent_code = :parentCode, 1 = 1)" 
			+ " ) tt"
			+ " WHERE tt.landArea != 0", nativeQuery = true)
	List<AbmWechatRegionLandVO> findAggregateRegionByLand(@Param("parentCode") String parentCode);

}