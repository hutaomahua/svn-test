package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostEntity;
import com.lyht.business.cost.vo.CostAggregateCardVO;
import com.lyht.business.cost.vo.CostAggregateChatVO;
import com.lyht.business.cost.vo.CostAggregateVO;
import com.lyht.business.cost.vo.CostDetailVO;
import com.lyht.business.cost.vo.CostFamilyVO;
import com.lyht.business.cost.vo.CostOwnerVO;
import com.lyht.business.process.common.constant.ProcessConstant;

/**
 * 补偿费用总表
 * 
 * @author hxl
 *
 */
public interface CostDao extends JpaRepository<CostEntity, String> {

	/**
	 * 获取当前户总补偿记录
	 * 
	 * @param ownerNm
	 * @return
	 */
	CostEntity findByOwnerNm(String ownerNm);

	/**
	 * 查询当前户总补偿记录明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT"
			+ " tt.id AS id, IFNULL(tt.move_amount,0) AS moveAmount, IFNULL(tt.production_amount,0) AS productionAmount,"
			+ " tt.status AS status, tt.house_status AS houseStatus, tt.house_decoration_status AS houseDecorationStatus,"
			+ " tt.building_status AS buildingStatus, tt.agricultural_facilities_status AS agriculturalFacilitiesStatus,"
			+ " tt.trees_status AS treesStatus, tt.individual_status AS individualStatus,"
			+ " tt.relocation_allowance_status AS relocationAllowanceStatus,"
			+ " tt.other_status AS otherStatus, tt.difficult_status AS difficultStatus,"
			+ " tt.infrastructure_status AS infrastructureStatus,"
			+ " tt.homestead_status AS homesteadStatus, tt.levy_land_status AS levyLandStatus,"
			+ " tt.young_crops_status AS youngCropsStatus, tt.produce_population_status AS producePopulationStatus,"
			+ " tt.produce_land_status AS produceLandStatus,"
			+ " (IFNULL(tt.house_amount,0) + IFNULL(tt.house_decoration_amount,0)) AS houseAmount,"
			+ " (IFNULL(tt.building_amount,0) + IFNULL(tt.agricultural_facilities_amount,0)) AS buildingAmount,"
			+ " IFNULL(tt.trees_amount,0) AS treesAmount,tt.individual_amount AS individualAmount,"
			+ " IFNULL(tt.young_crops_amount,0) AS youngCropsAmount,"
			+ " (IFNULL(tt.relocation_allowance_amount,0) + IFNULL(tt.other_amount,0)"
			+ " + IFNULL(tt.difficult_amount,0) + IFNULL(tt.infrastructure_amount,0)"
			+ " + IFNULL(tt.homestead_amount,0)) AS otherAmount," + " tpr.city_code AS regionNm,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS region,"
			+ " pdv1.nm AS scopeNm, pdv1.name AS scope, pdv2.name AS zblx,"
			+ " tio.nm AS ownerNm, tio.name AS ownerName, tio.id_card AS idCard" + " FROM t_info_owner_impl tio"
			+ " LEFT JOIN t_compensation_cost tt ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region tpr ON tio.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tio.scope = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tio.zblx = pdv2.nm"
			+ " WHERE tio.nm = :ownerNm LIMIT 1", nativeQuery = true)
	CostDetailVO findCostByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 分页查询
	 * 
	 * @param ownerNm
	 * @param scopeNm
	 * @param region
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT" + " tt.id AS id, tt.move_amount AS moveAmount, tt.production_amount AS productionAmount,"
			+ " tt.status AS status, tt.house_status AS houseStatus, tt.house_decoration_status AS houseDecorationStatus,"
			+ " tt.building_status AS buildingStatus, tt.agricultural_facilities_status AS agriculturalFacilitiesStatus,"
			+ " tt.trees_status AS treesStatus, tt.individual_status AS individualStatus,"
			+ " tt.relocation_allowance_status AS relocationAllowanceStatus,"
			+ " tt.other_status AS otherStatus, tt.difficult_status AS difficultStatus,"
			+ " tt.infrastructure_status AS infrastructureStatus,"
			+ " tt.homestead_status AS homesteadStatus, tt.levy_land_status AS levyLandStatus,"
			+ " tt.young_crops_status AS youngCropsStatus, tt.produce_population_status AS producePopulationStatus,"
			+ " tt.produce_land_status AS produceLandStatus,"
			+ " (IFNULL(tt.house_amount,0) + IFNULL(tt.house_decoration_amount,0)) AS houseAmount,"
			+ " (IFNULL(tt.building_amount,0) + IFNULL(tt.agricultural_facilities_amount,0)) AS buildingAmount,"
			+ " IFNULL(tt.trees_amount,0) AS treesAmount,tt.individual_amount AS individualAmount,"
			+ " IFNULL(tt.young_crops_amount,0) AS youngCropsAmount,"
			+ " (IFNULL(tt.relocation_allowance_amount,0) + IFNULL(tt.other_amount,0)"
			+ " + IFNULL(tt.difficult_amount,0) + IFNULL(tt.infrastructure_amount,0)"
			+ " + IFNULL(tt.homestead_amount,0)) AS otherAmount," + " tpr.city_code AS regionNm,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS region,"
			+ " pdv1.nm AS scopeNm, pdv1.name AS scope, pdv2.name AS zblx,"
			+ " tio.nm AS ownerNm, tio.name AS ownerName, tio.id_card AS idCard" + " FROM t_info_owner_impl tio"
			+ " LEFT JOIN t_compensation_cost tt ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region tpr ON tio.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tio.scope = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tio.zblx = pdv2.nm" + " WHERE 1=1" 
			+ " AND tio.is_satisfy = '2' AND tio.move_state = '0' AND tio.place_type IS NOT NULL"// 搬迁安置
			+ " AND tio.is_produce = '2'"//生产安置
			+ " AND IF (:ownerName is not null, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
			+ " AND IF (:scopeNm is not null, tio.scope = :scopeNm, 1=1)"
			+ " AND IF (:status is not null, IF (:status = 0, (tt.status = 0 OR tt.status IS NULL), tt.status = :status), 1=1)"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:idCard is not null, tio.id_card like CONCAT('%',:idCard,'%'), 1=1)", countQuery = "SELECT"
					+ " COUNT(1)" + " FROM t_info_owner_impl tio"
					+ " LEFT JOIN t_compensation_cost tt ON tt.owner_nm = tio.nm"
					+ " LEFT JOIN pub_region tpr ON tio.region = tpr.city_code"
					+ " LEFT JOIN pub_dict_value pdv1 ON tio.scope = pdv1.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON tio.zblx = pdv2.nm" + " WHERE 1=1"
					+ " AND tio.is_satisfy = '2' AND tio.move_state = '0' AND tio.place_type IS NOT NULL"// 搬迁安置
					+ " AND tio.is_produce = '2'"//生产安置
					+ " AND IF (:ownerName is not null, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
					+ " AND IF (:scopeNm is not null, tio.scope = :scopeNm, 1=1)"
					+ " AND IF (:status is not null, IF (:status = 0, (tt.status = 0 OR tt.status IS NULL), tt.status = :status), 1=1)"
					+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:idCard is not null, tio.id_card like CONCAT('%',:idCard,'%'), 1=1)", nativeQuery = true)
	Page<CostDetailVO> page(@Param("ownerName") String ownerName, @Param("scopeNm") String scopeNm,
			@Param("region") String region, @Param("status") Integer status, @Param("idCard") String idCard,
			Pageable pageable);

	/**
	 * 删除当前户的补偿费用记录
	 * 
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_compensation_cost WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询当前户是否有补偿费用记录
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_compensation_cost WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取当前户补偿总费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT " + " CAST(IFNULL(move_amount,0) + IFNULL(production_amount,0) AS DECIMAL(12,4)) AS amount"
			+ " FROM t_compensation_cost" + " WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取搬迁安置总补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM ("
			+ " SELECT SUM(amount) AS amount FROM t_cost_houses WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_houses_decoration WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_building WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_agricultural_facilities WHERE owner_nm = :ownerNm"
			+ " UNION ALL" + " SELECT SUM(amount) AS amount FROM t_cost_trees WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_individual WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_relocation_allowance WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_other WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_infrastructure WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_homestead WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_young_crops WHERE owner_nm = :ownerNm"
			// 征收土地暂不计入补偿费用
//			+ " UNION ALL"
//			+ " SELECT SUM(amount) AS amount FROM t_cost_levy_land WHERE owner_nm = :ownerNm"
			+ " ) cost", nativeQuery = true)
	BigDecimal costMoveAmountByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取生产安置总补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM ("
			+ " SELECT SUM(amount) AS amount FROM t_cost_produce_population WHERE owner_nm = :ownerNm" + " UNION ALL"
			+ " SELECT SUM(amount) AS amount FROM t_cost_produce_land WHERE owner_nm = :ownerNm"
			+ " ) cost", nativeQuery = true)
	BigDecimal costProductionAmountByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询移民户信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT " + " tt.nm AS ownerNm, tt.name AS ownerName, tt.is_satisfy AS isSatisfy,"
			+ " tt.xiang AS county, tt.cun AS towns, tt.zu AS village,"
			+ " tt.place_name AS placeName, tt.id_card AS idCard," + " pdv1.name AS zblx, pdv2.name AS nature,"
			+ " pdv3.name AS scopeName, pdv4.name AS placeType," + " pdv4.code AS placeTypeCode,"
			+ " IF(tt.cun = '兰永', '兰永', '非兰永') AS produceType,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl WHERE owner_nm = :ownerNm AND is_satisfy='2') AS moveNum,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl WHERE owner_nm = :ownerNm AND is_produce='1') AS produceNum,"
			+ " REPLACE(tpr.merger_name,',','') AS regionName,"
			+ " REPLACE(SUBSTRING_INDEX(SUBSTRING_INDEX(tpr.merger_name,',',4),',',-2),',','') AS governmentRegion"
			+ " FROM t_info_owner_impl tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.nature = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.place_type = pdv4.nm"
			+ " WHERE tt.nm = :ownerNm", nativeQuery = true)
	CostOwnerVO findCostOwner(@Param("ownerNm") String ownerNm);

	/**
	 * 查询家庭成员信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT " + " tt.name AS name, tt.gender AS gender, tt.id_card AS idCard," + " tt.remark AS remark,"
			+ " pdv1.name AS householdsType, pdv2.name AS masterRelationship,"
			+ " pdv3.name AS national, pdv4.name AS educationLevel," + " IF (tt.is_satisfy = '2','是','否') AS isMove,"
			+ " IF (tt.is_produce = 1,'是','否') AS isProduce" + " FROM t_info_family_impl tt"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.households_type = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.master_relationship = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.national = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.education_level = pdv4.nm"
			+ " WHERE tt.owner_nm = :ownerNm", nativeQuery = true)
	List<CostFamilyVO> findCostFamily(@Param("ownerNm") String ownerNm);

	@Query(value = "SELECT" + " pr.name AS regionName," + " pr.city_code AS cityCode,"
			+ " pr.parent_code AS parentCode," + " pr.merger_name AS mergerName," + " pr.level_type AS level,"
			+ " IFNULL(SUM(tcc.move_amount),0) AS amount," + " ("
			+ " IFNULL(SUM(tcc.house_amount),0) + IFNULL(SUM(tcc.house_decoration_amount),0)" + " ) AS houseAmount,"
			+ " (" + " IFNULL(SUM(tcc.building_amount),0) + IFNULL(SUM(tcc.agricultural_facilities_amount),0)"
			+ " ) AS buildingAmount," + " IFNULL(SUM(tcc.trees_amount),0) AS treesAmount,"
			+ " IFNULL(SUM(tcc.young_crops_amount),0) AS youngCropsAmount," + " ("
			+ " IFNULL(SUM(tcc.individual_amount),0) + IFNULL(SUM(tcc.relocation_allowance_amount),0)"
			+ " + IFNULL(SUM(tcc.other_amount),0) + IFNULL(SUM(tcc.difficult_amount),0)"
			+ " + IFNULL(SUM(tcc.infrastructure_amount),0) + IFNULL(SUM(tcc.homestead_amount),0)" + " ) AS otherAmount"
			+ " FROM pub_region pr" + " LEFT JOIN t_info_owner_impl tio ON tio.region = pr.city_code"
			+ " LEFT JOIN t_compensation_cost tcc ON tio.nm = tcc.owner_nm"
			+ " WHERE pr.merger_name LIKE CONCAT('%','维西','%')" + " AND pr.level_type != '3'"
			+ " GROUP BY pr.city_code", nativeQuery = true)
	List<CostAggregateVO> findCostAggregate();

	@Query(value = "SELECT" + " pr.name AS regionName," + " pr.city_code AS cityCode,"
			+ " pr.parent_code AS parentCode," + " pr.merger_name AS mergerName," + " pr.level_type AS level,"
			+ " IFNULL(SUM(tcc.move_amount),0) AS amount," + " ("
			+ " IFNULL(SUM(tcc.house_amount),0) + IFNULL(SUM(tcc.house_decoration_amount),0)" + " ) AS houseAmount,"
			+ " (" + " IFNULL(SUM(tcc.building_amount),0) + IFNULL(SUM(tcc.agricultural_facilities_amount),0)"
			+ " ) AS buildingAmount," + " IFNULL(SUM(tcc.trees_amount),0) AS treesAmount,"
			+ " IFNULL(SUM(tcc.young_crops_amount),0) AS youngCropsAmount," + " ("
			+ " IFNULL(SUM(tcc.individual_amount),0) + IFNULL(SUM(tcc.relocation_allowance_amount),0)"
			+ " + IFNULL(SUM(tcc.other_amount),0) + IFNULL(SUM(tcc.difficult_amount),0)"
			+ " + IFNULL(SUM(tcc.infrastructure_amount),0) + IFNULL(SUM(tcc.homestead_amount),0)" + " ) AS otherAmount"
			+ " FROM pub_region pr" + " LEFT JOIN t_info_owner_impl tio ON tio.region = pr.city_code"
			+ " LEFT JOIN t_compensation_cost tcc ON tio.nm = tcc.owner_nm"
			+ " WHERE pr.merger_name LIKE CONCAT('%','维西','%')" + " AND pr.level_type = '3'" + " GROUP BY pr.city_code"
			+ " LIMIT 1", nativeQuery = true)
	CostAggregateVO findCostAggregateRoot();

	@Query(value = "SELECT" + " '总计' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.move_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.move_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.move_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.move_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.move_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.move_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.move_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.move_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<CostAggregateCardVO> findCostAggregateAll(@Param("mergerName") String mergerName);

	@Query(value = "SELECT" + " '房屋' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.house_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.house_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.house_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.house_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.house_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.house_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.house_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.house_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '房屋装修' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.house_decoration_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.house_decoration_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.house_decoration_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.house_decoration_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.house_decoration_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.house_decoration_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.house_decoration_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.house_decoration_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<CostAggregateCardVO> findCostAggregateHouse(@Param("mergerName") String mergerName);

	@Query(value = "SELECT" + " '附属建筑物' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.building_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.building_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.building_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.building_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.building_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.building_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.building_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.building_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '农副业设施' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.agricultural_facilities_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.agricultural_facilities_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.agricultural_facilities_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.agricultural_facilities_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.agricultural_facilities_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.agricultural_facilities_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.agricultural_facilities_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.agricultural_facilities_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<CostAggregateCardVO> findCostAggregateBuilding(@Param("mergerName") String mergerName);

	@Query(value = "SELECT" + " '零星果木' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.trees_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.trees_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.trees_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.trees_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.trees_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.trees_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.trees_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.trees_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<CostAggregateCardVO> findCostAggregateTrees(@Param("mergerName") String mergerName);

	@Query(value = "SELECT" + " '成片青苗及林木' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.young_crops_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.young_crops_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.young_crops_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.young_crops_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.young_crops_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.young_crops_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.young_crops_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.young_crops_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<CostAggregateCardVO> findCostAggregateYoungCrops(@Param("mergerName") String mergerName);

	@Query(value = "SELECT" + " '个体工商户' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.individual_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.individual_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.individual_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.individual_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.individual_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.individual_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.individual_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.individual_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '搬迁补助' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.relocation_allowance_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.relocation_allowance_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.relocation_allowance_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.relocation_allowance_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.relocation_allowance_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.relocation_allowance_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.relocation_allowance_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.relocation_allowance_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '其他补助' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.other_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.other_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.other_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.other_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.other_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.other_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.other_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.other_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '困难户补助' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.difficult_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.difficult_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.difficult_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.difficult_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.difficult_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.difficult_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.difficult_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.difficult_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '基础设施补助' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.infrastructure_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.infrastructure_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.infrastructure_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.infrastructure_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.infrastructure_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.infrastructure_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.infrastructure_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.infrastructure_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)" + " UNION ALL"
			+ " SELECT" + " '宅基地补助' AS project," + " '元' AS unit,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC' || tio.scope = 'E78D14E7BE' || tio.scope = 'D18482159A',tcc.homestead_amount,0)),0) AS pivotTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94' || tio.scope = '24ACBF9107',tcc.homestead_amount,0)),0) AS reservoirTotal,"
			+ " IFNULL(SUM(IF(tio.scope = 'D8D5AAD9DC',tcc.homestead_amount,0)),0) AS flood,"
			+ " IFNULL(SUM(IF(tio.scope = 'E78D14E7BE',tcc.homestead_amount,0)),0) AS influence,"
			+ " IFNULL(SUM(IF(tio.scope = 'D18482159A',tcc.homestead_amount,0)),0) AS raise,"
			+ " IFNULL(SUM(IF(tio.scope = 'CE81C0FA94',tcc.homestead_amount,0)),0) AS temporary,"
			+ " IFNULL(SUM(IF(tio.scope = '24ACBF9107',tcc.homestead_amount,0)),0) AS permanent,"
			+ " IFNULL(SUM(IF(tio.scope = '348F5B68BA',tcc.homestead_amount,0)),0) AS newTown"
			+ " FROM t_compensation_cost tcc" + " LEFT JOIN t_info_owner_impl tio ON tcc.owner_nm = tio.nm"
			+ " LEFT JOIN pub_region pr ON tio.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name LIKE CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<CostAggregateCardVO> findCostAggregateOther(@Param("mergerName") String mergerName);

	/**
	 * 查询当前户主是否已搬迁安置
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT" + " COUNT(1)" + " FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_move_process tamp ON tt.nm = tamp.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp ON tamp.process_id = tbp.process_id" + " WHERE tt.nm = :ownerNm"
			+ " AND (tbp.status IS NULL || (tbp.status != '" + ProcessConstant.PROCESS_APPROVED
			+ "' AND tbp.status != '" + ProcessConstant.PROCESS_STANDBY + "'))", nativeQuery = true)
	int costCheck(@Param("ownerNm") String ownerNm);

	/**
	 * 按行政区统计(门户统计图)
	 * 
	 * @param parentCode
	 * @return
	 */
	@Query(value = "SELECT * FROM " + " (SELECT" + " pr.name AS name, " + " pr.city_code AS cityCode,"
			+ " pr.parent_code AS parentCode, " + " pr.merger_name AS mergerName, " + " pr.level_type AS level," + " ("
			+ " SELECT " + " IFNULL(SUM(tcc.move_amount),0) " + " FROM t_compensation_cost tcc"
			+ " LEFT JOIN t_info_owner_impl tio ON tio.nm = tcc.owner_nm"
			+ " LEFT JOIN pub_region tpr ON tio.region = tpr.city_code"
			+ " WHERE tpr.merger_name LIKE CONCAT(pr.merger_name,'%')" + " ) as amount" + " FROM pub_region pr "
			+ " WHERE 1 = 1"
			+ " AND IF(:parentCode IS NOT NULL AND :parentCode != '', pr.parent_code = :parentCode, 1 = 1)" + " ) tt"
			+ " WHERE tt.amount != 0", nativeQuery = true)
	List<CostAggregateChatVO> findCostChat(@Param("parentCode") String parentCode);

}