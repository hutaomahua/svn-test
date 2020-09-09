package com.lyht.business.abm.signed.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.signed.entity.ProtocolEscrow;
import com.lyht.business.abm.signed.vo.EscrowPerviewVO;
import com.lyht.business.abm.signed.vo.GraphVO;
import com.lyht.business.abm.signed.vo.ProtocolStatisticsVO;

/**
 * 资金代管协议
 * 
 * @author wzw
 *
 */
@Repository
public interface ProtocolEscrowDao extends JpaRepository<ProtocolEscrow, Integer> {

	/**
	 * 首页统计
	 */
	@Query(value = " select * from (select name,ROUND(IFNULL((select sum(tt.protocol_amount) from t_abm_protocol_info tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')),0),2) as protocolAmount,  "
			+ " ROUND(IFNULL((select sum(tt.escrow_money) from t_abm_protocol_escrow tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')),0),2) as escrowAmount " + " from pub_region t0  "
			+ " where t0.level_type = :levelType and t0.merger_name like CONCAT(:mergerName,'%') ) as temp "
			+ " where temp.protocolAmount > 0", nativeQuery = true)
	List<GraphVO> getHomePageGraph(@Param("mergerName") String mergerName, @Param("levelType") Integer levelType);

	ProtocolEscrow findByNm(String nm);

	ProtocolEscrow findByOwnerNm(String ownerNm);

	@Modifying
	@Query(value = "update t_abm_protocol_escrow set state = 1 where nm = :nm", nativeQuery = true)
	Integer updateStateFinish(@Param("nm") String nm);

	@Modifying
	@Query(value = "update t_abm_protocol_escrow set state = 1 where process_id = :processId", nativeQuery = true)
	Integer updateStateFinishByProcessId(@Param("processId") String processId);

	@Modifying
	@Query(value = "update t_abm_protocol_escrow set state = 0 where nm = :nm", nativeQuery = true)
	Integer updateStateFail(@Param("nm") String nm);

	/**
	 * 获取当天最大流水号（后两位）
	 */
	@Query(value = "select protocol_code from t_abm_protocol_escrow where date_format(complete_time,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d') order by complete_time desc limit 1", nativeQuery = true)
	public String getProtocolCode();

	/**
	 * 查询 是否能签订资金代管协议 房屋补偿签订状态
	 */
	@Query(value = "select house_status from t_abm_total_state where owner_nm =:ownerNm", nativeQuery = true)
	public Integer getHouseStatus(@Param("ownerNm") String ownerNm);
	
	@Query(value = "select building_status from t_abm_total_state where owner_nm =:ownerNm", nativeQuery = true)
	public Integer getBuildingStatusStatus(@Param("ownerNm") String ownerNm);

	@Query(value = "select count(1) from t_abm_protocol_info where owner_nm = :ownerNm and is_escrow = 1", nativeQuery = true)
	public Integer getISEscrowCount(@Param("ownerNm") String ownerNm);

	/**
	 * 查询 是否能签订资金代管协议 资金代管协议是否签订
	 */
	@Query(value = "select count(1) from t_abm_protocol_escrow where owner_nm =:ownerNm", nativeQuery = true)
	public Integer getEscrowCount(@Param("ownerNm") String ownerNm);

	/**
	 * 根据权属人编码删除资金代管协议
	 */
	@Modifying
	@Query(value = "delete from t_abm_protocol_escrow where owner_nm =:ownerNm", nativeQuery = true)
	public Integer deleteByOwnerNm(@Param("ownerNm") String OwnerNm);

	@Query(value = "select tt.protocol_name name,if(tt.place is not null,tt.place,t1.place_name ) as place, t1.name ownerName,TT.homestead_code homesteadCode,tt.house_type houseType,  "
			+ "CAST(tt.placement_money AS DECIMAL(19,2)) placementMoney,CAST(tt.escrow_money AS DECIMAL(19,2)) escrowMoney,t3.`name` company,tt.content  "
			+ "from t_abm_protocol_escrow tt   left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ "left join sys_dept t3 on tt.company = t3.nm  where tt.id = :id", nativeQuery = true)
	Map<String, Object> export(@Param("id") Integer id);

	@Query(value = "select tt.protocol_name name,if(tt.place is not null,tt.place,t1.place_name ) as place, t1.name ownerName,TT.homestead_code homesteadCode,tt.house_type houseType, "
			+ "CAST(tt.placement_money AS DECIMAL(19,2)) placementMoney,CAST(tt.escrow_money AS DECIMAL(19,2)) escrowMoney,t3.`name` company,tt.content "
			+ "from t_abm_protocol_escrow tt   left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ "left join sys_dept t3 on tt.company = t3.nm  where tt.id = :id", nativeQuery = true)
	EscrowPerviewVO preview(@Param("id") Integer id);

	@Query(value = "select * from (select t0.city_code cityCode,t0.parent_code parentCode,t0.name,t0.merger_name mergerName,  "
			+ " (select count(1) from t_compensation_cost tt  "
			+ "left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ "left join pub_region t2 on t1.region = t2.city_code  "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')) households,  "
			+ " round((select IFNULL(sum(amount),0) from(  "
			+ "select owner_nm,(IFNULL(house_amount,0)+IFNULL(house_decoration_amount,0)+IFNULL(building_amount,0)  "
			+ "+IFNULL(agricultural_facilities_amount,0)+IFNULL(trees_amount,0)+IFNULL(individual_amount,0)  "
			+ "+IFNULL(relocation_allowance_amount,0)+IFNULL(other_amount,0)+IFNULL(difficult_amount,0)  "
			+ "+IFNULL(infrastructure_amount,0)+IFNULL(homestead_amount,0)  "
			+ "+IFNULL(levy_land_amount,0)+IFNULL(young_crops_amount,0)+IFNULL(production_amount,0)) as amount from t_compensation_cost) dd  "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm  "
			+ " left join pub_region d2 on d1.region = d2.city_code  "
			+ " where d2.merger_name like CONCAT(t0.merger_name,'%')),2) total,  "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt   "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')) as protocolHolds,  "
			+ " round((select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')),2) protocoled,   "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')) escrowCount,  "
			+ " round((select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ " left join pub_region t2 on t1.region = t2.city_code  "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')),2) escrowAmount  "
			+ " from pub_region t0 where t0.merger_name like CONCAT('云南省,迪庆藏族自治州,维西傈僳族自治县','%') ) as tt "
			+ " where (tt.households+tt.total+tt.protocolHolds+tt.protocoled+tt.escrowCount+tt.escrowAmount)>0", nativeQuery = true)
	public List<Map<String, Object>> getStatistics();

	@Query(value = "select t0.city_code cityCode,t0.parent_code parentCode,t0.name,t0.merger_name mergerName, "
			+ " (SELECT count(1) from t_info_owner_impl tt " + " left join pub_region t1 on tt.region = t1.city_code  "
			+ " where t1.merger_name like CONCAT(t0.merger_name,'%')) households, "
			+ " FORMAT((select IFNULL(sum(amount),0) from( "
			+ "select owner_nm,(IFNULL(house_amount,0)+IFNULL(house_decoration_amount,0)+IFNULL(building_amount,0) "
			+ "+IFNULL(agricultural_facilities_amount,0)+IFNULL(trees_amount,0)+IFNULL(individual_amount,0) "
			+ "+IFNULL(relocation_allowance_amount,0)+IFNULL(other_amount,0)+IFNULL(difficult_amount,0) "
			+ "+IFNULL(infrastructure_amount,0)+IFNULL(homestead_amount,0) "
			+ "+IFNULL(levy_land_amount,0)+IFNULL(young_crops_amount,0)+IFNULL(production_amount,0)) as amount from t_compensation_cost) dd "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm "
			+ " left join pub_region d2 on d1.region = d2.city_code "
			+ " where d2.merger_name like CONCAT(t0.merger_name,'%')),2) total, "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')) as protocolHolds, "
			+ " FORMAT((select IFNULL(sum(protocoled_amount),0) from t_abm_total_state tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')),2) protocoled,  "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')) escrowCount, "
			+ " FORMAT((select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " where t2.merger_name like CONCAT(t0.merger_name,'%')),2) escrowAmount "
			+ " from pub_region t0  if(:parentCode is null, where parent_code is null,where parent_code = :parentCode)", nativeQuery = true)
	public List<Map<String, Object>> getStatisticsByParentCode(@Param("parentCode") String parentCode);

	/**
	 * 总户数 总金额
	 */
	@Query(value = "select '总户数' AS project,'户' unit,(snls+snyj) AS snTotal,snls,snyj,(skym+skyx+dgls) AS skTotal, skym,skyx,dgls,jzxz from (select (SELECT count(1) from t_info_owner_impl tt "
			+ " left join pub_region t1 on tt.region = t1.city_code  "
			+ " left join pub_dict_value t2 on tt.scope = t2.nm "
			+ " where t1.merger_name like CONCAT(:region,'%') and t2.name = '临时占地' ) AS snls, "
			+ " (SELECT count(1) from t_info_owner_impl tt " + " left join pub_region t1 on tt.region = t1.city_code  "
			+ " left join pub_dict_value t2 on tt.scope = t2.nm "
			+ " where t1.merger_name like CONCAT(:region,'%') and t2.name = '永久占地' ) AS snyj, "
			+ " (SELECT count(1) from t_info_owner_impl tt " + " left join pub_region t1 on tt.region = t1.city_code  "
			+ " left join pub_dict_value t2 on tt.scope = t2.nm "
			+ " where t1.merger_name like CONCAT(:region,'%') and t2.name = '水库淹没区' ) AS skym, "
			+ " (SELECT count(1) from t_info_owner_impl tt " + " left join pub_region t1 on tt.region = t1.city_code  "
			+ " left join pub_dict_value t2 on tt.scope = t2.nm "
			+ " where t1.merger_name like CONCAT(:region,'%') and t2.name = '水库影响区' ) AS skyx, "
			+ " (SELECT count(1) from t_info_owner_impl tt " + " left join pub_region t1 on tt.region = t1.city_code  "
			+ " left join pub_dict_value t2 on tt.scope = t2.nm "
			+ " where t1.merger_name like CONCAT(:region,'%') and t2.name = '垫高临时用地区' ) AS dgls, "
			+ " (SELECT count(1) from t_info_owner_impl tt " + " left join pub_region t1 on tt.region = t1.city_code  "
			+ " left join pub_dict_value t2 on tt.scope = t2.nm "
			+ " where t1.merger_name like CONCAT(:region,'%') and t2.name = '集镇新址占地区' ) AS jzxz) as dd "
			+ " UNION ALL  "
			+ " select '协议总金额' AS project,'元' unit,FORMAT((snls+snyj),2) AS snTotal,FORMAT(snls,2),FORMAT(snyj,2),FORMAT((skym+skyx+dgls),2) AS skTotal, FORMAT(skym,2),FORMAT(skyx,2),FORMAT(dgls,2),FORMAT(jzxz,2) from ( "
			+ " select (select IFNULL(sum(amount),0) from( "
			+ " select owner_nm,amount from t_cost_building UNION ALL  "
			+ " select owner_nm,amount from t_cost_agricultural_facilities UNION ALL  "
			+ " select owner_nm,amount from t_cost_trees UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses UNION ALL  "
			+ " select owner_nm,amount from t_cost_individual UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses_decoration UNION ALL  "
			+ " select owner_nm,amount from t_cost_other UNION ALL  "
			+ " select owner_nm,amount from t_cost_infrastructure UNION ALL  "
			+ " select owner_nm,amount from t_cost_homestead UNION ALL  "
			+ " select owner_nm,amount from t_cost_levy_land UNION ALL  "
			+ " select owner_nm,amount from t_cost_young_crops UNION ALL  "
			+ " select owner_nm,amount from t_cost_relocation_allowance UNION ALL "
			+ " select owner_nm,production_amount from t_compensation_cost) dd "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm "
			+ " left join pub_region d2 on d1.region = d2.city_code "
			+ " left join pub_dict_value d3 on d1.scope = d3.nm "
			+ " where d2.merger_name like CONCAT(:region,'%') and d3.name = '临时占地') snls, "
			+ " (select IFNULL(sum(amount),0) from( " + " select owner_nm,amount from t_cost_building UNION ALL  "
			+ " select owner_nm,amount from t_cost_agricultural_facilities UNION ALL  "
			+ " select owner_nm,amount from t_cost_trees UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses UNION ALL  "
			+ " select owner_nm,amount from t_cost_individual UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses_decoration UNION ALL  "
			+ " select owner_nm,amount from t_cost_other UNION ALL  "
			+ " select owner_nm,amount from t_cost_infrastructure UNION ALL  "
			+ " select owner_nm,amount from t_cost_homestead UNION ALL  "
			+ " select owner_nm,amount from t_cost_levy_land UNION ALL  "
			+ " select owner_nm,amount from t_cost_young_crops UNION ALL  "
			+ " select owner_nm,amount from t_cost_relocation_allowance  UNION ALL  "
			+ " select owner_nm,production_amount from t_compensation_cost) dd "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm "
			+ " left join pub_region d2 on d1.region = d2.city_code "
			+ " left join pub_dict_value d3 on d1.scope = d3.nm "
			+ " where d2.merger_name like CONCAT(:region,'%') and d3.name = '永久占地') snyj, "
			+ " (select IFNULL(sum(amount),0) from( " + " select owner_nm,amount from t_cost_building UNION ALL  "
			+ " select owner_nm,amount from t_cost_agricultural_facilities UNION ALL  "
			+ " select owner_nm,amount from t_cost_trees UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses UNION ALL  "
			+ " select owner_nm,amount from t_cost_individual UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses_decoration UNION ALL  "
			+ " select owner_nm,amount from t_cost_other UNION ALL  "
			+ " select owner_nm,amount from t_cost_infrastructure UNION ALL  "
			+ " select owner_nm,amount from t_cost_homestead UNION ALL  "
			+ " select owner_nm,amount from t_cost_levy_land UNION ALL  "
			+ " select owner_nm,amount from t_cost_young_crops UNION ALL  "
			+ " select owner_nm,amount from t_cost_relocation_allowance  UNION ALL "
			+ " select owner_nm,production_amount from t_compensation_cost) dd "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm "
			+ " left join pub_region d2 on d1.region = d2.city_code "
			+ " left join pub_dict_value d3 on d1.scope = d3.nm "
			+ " where d2.merger_name like CONCAT(:region,'%') and d3.name = '水库淹没区') skym, "
			+ " (select IFNULL(sum(amount),0) from( " + " select owner_nm,amount from t_cost_building UNION ALL  "
			+ " select owner_nm,amount from t_cost_agricultural_facilities UNION ALL  "
			+ " select owner_nm,amount from t_cost_trees UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses UNION ALL  "
			+ " select owner_nm,amount from t_cost_individual UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses_decoration UNION ALL  "
			+ " select owner_nm,amount from t_cost_other UNION ALL  "
			+ " select owner_nm,amount from t_cost_infrastructure UNION ALL  "
			+ " select owner_nm,amount from t_cost_homestead UNION ALL  "
			+ " select owner_nm,amount from t_cost_levy_land UNION ALL  "
			+ " select owner_nm,amount from t_cost_young_crops UNION ALL  "
			+ " select owner_nm,amount from t_cost_relocation_allowance  UNION ALL "
			+ " select owner_nm,production_amount from t_compensation_cost) dd "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm "
			+ " left join pub_region d2 on d1.region = d2.city_code "
			+ " left join pub_dict_value d3 on d1.scope = d3.nm "
			+ " where d2.merger_name like CONCAT(:region,'%') and d3.name = '水库影响区') skyx, "
			+ " (select IFNULL(sum(amount),0) from( " + " select owner_nm,amount from t_cost_building UNION ALL  "
			+ " select owner_nm,amount from t_cost_agricultural_facilities UNION ALL  "
			+ " select owner_nm,amount from t_cost_trees UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses UNION ALL  "
			+ " select owner_nm,amount from t_cost_individual UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses_decoration UNION ALL  "
			+ " select owner_nm,amount from t_cost_other UNION ALL  "
			+ " select owner_nm,amount from t_cost_infrastructure UNION ALL  "
			+ " select owner_nm,amount from t_cost_homestead UNION ALL  "
			+ " select owner_nm,amount from t_cost_levy_land UNION ALL  "
			+ " select owner_nm,amount from t_cost_young_crops UNION ALL  "
			+ " select owner_nm,amount from t_cost_relocation_allowance  UNION ALL  "
			+ " select owner_nm,production_amount from t_compensation_cost) dd "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm "
			+ " left join pub_region d2 on d1.region = d2.city_code "
			+ " left join pub_dict_value d3 on d1.scope = d3.nm "
			+ " where d2.merger_name like CONCAT(:region,'%') and d3.name = '垫高临时用地区') dgls, "
			+ " (select IFNULL(sum(amount),0) from( " + " select owner_nm,amount from t_cost_building UNION ALL  "
			+ " select owner_nm,amount from t_cost_agricultural_facilities UNION ALL  "
			+ " select owner_nm,amount from t_cost_trees UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses UNION ALL  "
			+ " select owner_nm,amount from t_cost_individual UNION ALL  "
			+ " select owner_nm,amount from t_cost_houses_decoration UNION ALL  "
			+ " select owner_nm,amount from t_cost_other UNION ALL  "
			+ " select owner_nm,amount from t_cost_infrastructure UNION ALL  "
			+ " select owner_nm,amount from t_cost_homestead UNION ALL  "
			+ " select owner_nm,amount from t_cost_levy_land UNION ALL  "
			+ " select owner_nm,amount from t_cost_young_crops UNION ALL  "
			+ " select owner_nm,amount from t_cost_relocation_allowance  UNION ALL "
			+ " select owner_nm,production_amount from t_compensation_cost) dd "
			+ " left join t_info_owner_impl d1 on dd.owner_nm = d1.nm "
			+ " left join pub_region d2 on d1.region = d2.city_code "
			+ " left join pub_dict_value d3 on d1.scope = d3.nm "
			+ " where d2.merger_name like CONCAT(:region,'%') and d3.name = '集镇新址占地区') jzxz) as dd", nativeQuery = true)
	public List<ProtocolStatisticsVO> getHoldsAndProtocolAmount(@Param("region") String region);

	/**
	 * 已签订户数 已签订协议金额
	 */
	@Query(value = "select '已签订户数' AS project,'户' unit,(snls+snyj) AS snTotal,snls,snyj,(skym+skyx+dgls) AS skTotal, skym,skyx,dgls,jzxz from (select  "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '临时占地') AS snls, "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '永久占地') AS snyj, "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '水库淹没区' ) AS skym, "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '水库影响区') AS skyx, "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '垫高临时用地区') AS dgls, "
			+ " (select count(1) from (select owner_nm,count(1) from t_abm_protocol_info GROUP BY owner_nm) as tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '集镇新址占地区' ) AS jzxz) as dd "
			+ " UNION ALL  "
			+ " select '已签订协议总金额' AS project,'元' unit,FORMAT((snls+snyj),2) AS snTotal,FORMAT(snls,2),FORMAT(snyj,2),FORMAT((skym+skyx+dgls),2) AS skTotal, FORMAT(skym,2),FORMAT(skyx,2),FORMAT(dgls,2),FORMAT(jzxz,2)  from ( "
			+ " select (select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '临时占地') snls, "
			+ " (select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '永久占地') snyj, "
			+ " (select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '水库淹没区') skym, "
			+ " (select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '水库影响区') skyx, "
			+ " (select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '垫高临时用地区') dgls, "
			+ " (select IFNULL(sum(protocol_amount),0) from t_abm_protocol_info tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '集镇新址占地区') jzxz) as dd", nativeQuery = true)
	public List<ProtocolStatisticsVO> getProtocolHoldAndAmount(@Param("region") String region);

	/**
	 * 资金代管户数 代管金额
	 */
	@Query(value = "select '资金代管户数' AS project,'户' unit,(snls+snyj) AS snTotal,snls,snyj,(skym+skyx+dgls) AS skTotal, skym,skyx,dgls,jzxz from (select  "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%')  and t3.name = '临时占地') AS snls, "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%')  and t3.name = '永久占地') AS snyj, "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%')  and t3.name = '水库淹没区' ) AS skym, "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%')  and t3.name = '水库影响区') AS skyx, "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%')  and t3.name = '垫高临时用地区') AS dgls, "
			+ " (SELECT count(1) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%')  and t3.name = '集镇新址占地区' ) AS jzxz) as dd "
			+ " UNION ALL  "
			+ " select '资金代管金额' AS project,'元' unit,FORMAT((snls+snyj),2) AS snTotal,FORMAT(snls,2),FORMAT(snyj,2),FORMAT((skym+skyx+dgls),2) AS skTotal, FORMAT(skym,2),FORMAT(skyx,2),FORMAT(dgls,2),FORMAT(jzxz,2)  from ( "
			+ " select (select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '临时占地') snls, "
			+ " (select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '永久占地') snyj, "
			+ " (select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '水库淹没区') skym, "
			+ " (select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '水库影响区') skyx, "
			+ " (select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '垫高临时用地区') dgls, "
			+ " (select IFNULL(sum(escrow_money),0) from t_abm_protocol_escrow tt "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " left join pub_region t2 on t1.region = t2.city_code "
			+ " left join pub_dict_value t3 on t1.scope = t3.nm  "
			+ " where t2.merger_name like CONCAT(:region,'%') and t3.name = '集镇新址占地区') jzxz) as dd ", nativeQuery = true)
	public List<ProtocolStatisticsVO> getEscrowHoldAndAmount(@Param("region") String region);

}
