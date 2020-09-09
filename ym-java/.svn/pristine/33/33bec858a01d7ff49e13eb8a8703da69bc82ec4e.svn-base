package com.lyht.business.abm.landAllocation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.landAllocation.common.constant.LandResolveConstant;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import com.lyht.business.abm.landAllocation.vo.LandResolveAggregateVO;

public interface LandResolveDao extends JpaRepository<LandPoolEntity, Integer> {

	/**
	 * 
	 *	面积汇总查询，按大类分组
	 * @param cityCode	行政区域编码
	 * @param areaType	查询的面积类型
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" 
			+ " IF(pdv1.name IS NOT NULL, pdv1.name, '无') AS project," 
			+ " '亩' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS newTown " 
			+ " FROM t_abm_land_pool tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.all_type = pdv1.nm" 
			+ " WHERE 1=1" 
			+ " AND IF(:cityCode IS NOT NULL, tt.region = :cityCode, 1=1)"
			+ " GROUP BY tt.all_type"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND (tb.pivotTotal != 0 OR tb.reservoirTotal != 0 OR tb.newTown != 0)", nativeQuery = true)
	List<LandResolveAggregateVO> findAggregateAll(@Param("cityCode") String cityCode,
			@Param("areaType") String areaType);
	
	/**
	 *	面积汇总查询，按一级分类分组
	 * @param cityCode	行政区域编码
	 * @param areaType	查询的面积类型
	 * @param landType	土地大类的编码
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" 
			+ " pdv1.name AS project," 
			+ " '亩' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS newTown " 
			+ " FROM t_abm_land_pool tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.type_one = pdv1.nm" 
			+ " WHERE 1=1" 
			+ " AND IF(:cityCode IS NOT NULL, tt.region = :cityCode, 1=1)"
			+ " AND IF(:landType IS NOT NULL, tt.all_type = :landType, 1=1)"
			+ " GROUP BY tt.type_one"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND (tb.pivotTotal != 0 OR tb.reservoirTotal != 0 OR tb.newTown != 0)", nativeQuery = true)
	List<LandResolveAggregateVO> findAggregateByAllType(@Param("cityCode") String cityCode,
			@Param("areaType") String areaType, @Param("landType") String landType);
	
	/**
	 *	面积汇总查询，按二级分类分组
	 * @param cityCode	行政区域编码
	 * @param areaType	查询的面积类型
	 * @param landType	土地一级分类的编码
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" 
			+ " pdv1.name AS project," 
			+ " '亩' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS newTown " 
			+ " FROM t_abm_land_pool tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.type_two = pdv1.nm" 
			+ " WHERE 1=1" 
			+ " AND IF(:cityCode IS NOT NULL, tt.region = :cityCode, 1=1)"
			+ " AND IF(:landType IS NOT NULL, tt.type_one = :landType, 1=1)"
			+ " GROUP BY tt.type_two"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND (tb.pivotTotal != 0 OR tb.reservoirTotal != 0 OR tb.newTown != 0)", nativeQuery = true)
	List<LandResolveAggregateVO> findAggregateByTypeOne(@Param("cityCode") String cityCode,
			@Param("areaType") String areaType, @Param("landType") String landType);
	
	/**
	 *	面积汇总查询，按三级分类分组
	 * @param cityCode	行政区域编码
	 * @param areaType	查询的面积类型
	 * @param landType	土地二级分类的编码
	 * @return
	 */
	@Query(value = "SELECT * FROM ("
			+ " SELECT" 
			+ " pdv1.name AS project," 
			+ " '亩' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',"
			+ " IF(:areaType = '" + LandResolveConstant.AREA_TYPE_ALL + " ', tt.area, IF(:areaType = '" + LandResolveConstant.AREA_TYPE_RESOLVE + " ', tt.separate_area, tt.area - tt.separate_area))"
			+ " ,0)),0) AS newTown " 
			+ " FROM t_abm_land_pool tt "
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code "
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.type_three = pdv1.nm" 
			+ " WHERE 1=1" 
			+ " AND IF(:cityCode IS NOT NULL, tt.region = :cityCode, 1=1)"
			+ " AND IF(:landType IS NOT NULL, tt.type_two = :landType, 1=1)"
			+ " GROUP BY tt.type_three"
			+ " ORDER BY pdv1.sorted"
			+ " ) tb"
			+ " WHERE tb.project != '无' AND (tb.pivotTotal != 0 OR tb.reservoirTotal != 0 OR tb.newTown != 0)", nativeQuery = true)
	List<LandResolveAggregateVO> findAggregateByTypeTwo(@Param("cityCode") String cityCode,
			@Param("areaType") String areaType, @Param("landType") String landType);

}
