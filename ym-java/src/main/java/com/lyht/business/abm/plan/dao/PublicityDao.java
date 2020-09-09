package com.lyht.business.abm.plan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.plan.entity.PublicityEntity;
import com.lyht.business.abm.plan.vo.PublicityFamilyVO;

public interface PublicityDao extends JpaRepository<PublicityEntity, Integer> {

	List<PublicityEntity> findByState(String state);

	PublicityEntity findByNm(String nm);

	@Query(value = " select m.state,  (select merger_name from pub_region b WHERE b.city_code=m.region)as  regionName,(select count(1) from pub_files where table_pk_column =m.nm) as fujian,"
			+ " m.code,m.name as homeName,m.type typeNm,tt.name as type,m.start_time,m.end_time,m.cz_name,m.cz_time,m.create_time,m.remarks,m.id as gsId,m.nm,m.region as region "
			+ " from t_abm_publicity m  LEFT JOIN pub_region AS tpr ON tpr.name = m.region left join pub_dict_value tt on m.type = tt.nm where 1=1  and "
			+ " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and"
			+ "  IF (:homeName is not null,m.name LIKE CONCAT('%',:homeName,'%'), 1=1) and  "
			+ "  IF (:zbType is not null,m.zb_type LIKE CONCAT('',:zbType,''), 1=1) and "
			+ "  IF (:startTime is not null && LENGTH(:startTime)>0,DATE_FORMAT( m.start_time, '%Y-%m-%d' ) >= DATE_FORMAT(:startTime, '%Y-%m-%d' ), 1=1) and  "
			+ "  IF (:endTime is not null && LENGTH(:endTime)>0,DATE_FORMAT( m.end_time, '%Y-%m-%d' ) <= DATE_FORMAT(:endTime, '%Y-%m-%d' ), 1=1)   "
			+ "  GROUP BY m.nm  order BY m.id DESC", countQuery = " select count(1)  "
					+ " from t_abm_publicity m  LEFT JOIN pub_region AS tpr ON tpr.name = m.region where 1=1  and "
					+ " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and"
					+ "  IF (:homeName is not null,m.name LIKE CONCAT('%',:homeName,'%'), 1=1) and "
					+ "  IF (:zbType is not null,m.zb_type LIKE CONCAT('',:zbType,''), 1=1) and "
					+ "  IF (:startTime is not null && LENGTH(:startTime)>0,DATE_FORMAT( m.start_time, '%Y-%m-%d' ) >= DATE_FORMAT(:startTime, '%Y-%m-%d' ), 1=1) and  "
					+ "  IF (:endTime is not null && LENGTH(:endTime)>0,DATE_FORMAT( m.end_time, '%Y-%m-%d' ) <= DATE_FORMAT(:endTime, '%Y-%m-%d' ), 1=1)   "
					+ "  GROUP BY m.nm  order BY m.id DESC", nativeQuery = true)
	Page<Map> getList(Pageable pageable, @Param("region") String region, @Param("homeName") String homeName,
			@Param("zbType") String zbType, @Param("startTime") String startTime, @Param("endTime") String endTime);

	@Query(value = " select  a.nm as owner_nm ,"
			+ " (select merger_name from pub_region b WHERE b.city_code=a.region)as  region," + " a.name,id_card,"
			+ " (select b.name from pub_dict_value b where b.nm=a.national )national,	"
			+ " (select b.name from pub_dict_value b where b.nm=a.scope )scope ,"
			+ " m.code,m.name as homeName,m.type,m.start_time,m.end_time,m.create_time,m.recor,m.unit,m.remarks,m.id as gsId"
			+ " from t_abm_publicity m LEFT JOIN t_info_owner a on m.nm=a.nm LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region where 1=1 and zb_type='1' and "
			+ " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and"
			+ "  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1) and "
			+ "  IF (:homeName is not null,m.name LIKE CONCAT('%',:homeName,'%'), 1=1)  "
			+ "   order BY m.id ASC  ", nativeQuery = true)
	List<Map> getListExcl(@Param("region") String region, @Param("name") String name,
			@Param("homeName") String homeName);

	/**
	 * 公示详情
	 * 
	 * @param pageable
	 * @param region
	 * @param homeName
	 * @return
	 */

	@Query(value = "select aa.id,aa.nm as nms,a.nm,a.name,a.id_card,a.i_population,(select count(1) from pub_files where table_pk_column =a.nm) as fujian,"
			+ "(SELECT name from pub_dict_value cc where cc.nm=a.scope)scope," + "a.i_population as ymNum,"
			+ "(select count(b.is_satisfy) from t_info_family_impl b where b.owner_nm=aa.owner_nm and is_satisfy='2' ) as ycNum,"
			+ "(select SUM(b.area) from t_info_houses_impl b where b.owner_nm=aa.owner_nm) as houseArea ,"
			+ "(select SUM(b.area) from t_info_land_impl b where b.owner_nm=aa.owner_nm) as tdArea ,"
			+ "(select SUM(b.num) from t_info_trees_impl b where b.owner_nm=aa.owner_nm) as treeNum ,"
			+ "(select SUM(b.area) from t_info_building_impl b where b.owner_nm=aa.owner_nm) as fsArea "
			+ "	from t_abm_publicity_details aa " + "LEFT JOIN t_info_owner_impl a ON aa.owner_nm=a.nm  where 1=1 "
			+ " and IF (:nm is not null,aa.nm LIKE CONCAT('',:nm,''), 1=1)  "
			+ "order BY aa.id  ", countQuery = "select count(1) from t_abm_publicity_details aa  "
					+ "LEFT JOIN t_info_owner_impl a ON aa.owner_nm=a.nm where 1=1 "
					+ " and IF (:nm is not null,aa.nm LIKE CONCAT('',:nm,''), 1=1)  "
					+ "order BY aa.id  ", nativeQuery = true)
	Page<Map> getListDetails(Pageable pageable, @Param("nm") String nm);

	@Query(value = " select * FROM t_abm_publicity ORDER BY id DESC LIMIT 0,1 ", nativeQuery = true)
	List<Map> getGsCode();

	@Modifying
	@Query(value = " UPDATE t_abm_publicity SET state = :state,start_time = date_format(NOW(), '%Y-%m-%d') WHERE nm=:nm  ", nativeQuery = true)
	void updateState(@Param("state") String state, @Param("nm") String nm);

	@Query(value = " select * FROM  t_abm_publicity WHERE nm=:nm  ", nativeQuery = true)
	PublicityEntity getNm(@Param("nm") String nm);

	@Query(value = "select tt.name,tt.id_card idCard,tt.nm from t_info_family_impl tt " + 
			"left join pub_region t1 on tt.region = t1.city_code " + 
			"where tt.owner_nm in(select nm from t_info_owner_impl where is_produce = 2) " + 
			"and t1.merger_name like concat('%',:region,'%') and ((tt.gs_state != -1 and tt.gs_state != 1) or tt.gs_state is null)", nativeQuery = true)
	public List<PublicityFamilyVO> getPublicityFamily(@Param("region") String region);

	@Query(value = "SELECT " + " tla.resolve_area as resolveArea, " + "ppr.name as region, "
			+ "tla.process_id as processId, " + "	tli.name as name, " + "	tli.id_card as idCard, "
			+ "tla.nm AS ownerNm " + "FROM t_abm_land_audit tla "
			+ "LEFT JOIN pub_region AS ppr ON ppr.city_code = tla.source_region  "
			+ "LEFT JOIN t_info_family_impl tli ON tla.nm = tli.nm "
			+ "LEFT JOIN pub_region AS tpr ON tpr.city_code = tla.target_region "
			+ "where 1=1  and tla.audit_code = '64FABB5F0D' and tla.fg_state ='0' " + "and tli.name is not null  "
			+ "AND if(:region IS NOT NULL AND :region != '',tpr.merger_name LIKE CONCAT('%',:region,'%') , 1=1) ", nativeQuery = true)
	List<Map> getUserList(@Param("region") String region);

	/**
	 * 个人财产 查询存在可公示人的行政区信息
	 */

	@Query(value = "select name,cityCode,parentCode from (select (select count(1) from t_info_owner_impl tt "
			+ "left join pub_region t1 on tt.region = t1.city_code where tt.fh_state = 4 and tt.gs_state = 0 "
			+ "and t1.merger_name LIKE CONCAT('%',tt.merger_name,'%')) count,tt.name,tt.city_code cityCode,tt.parent_code parentCode from pub_region tt where level_type >3) as t0 where t0.count > 0 ", nativeQuery = true)
	List<Map<String, Object>> getPubRegionInfo();

	@Query(value = "select name,cityCode,parentCode from (select (select count(1) from (select * from t_info_family_impl  " + 
			"where owner_nm in(select nm from t_info_owner_impl where is_produce = 2) and ((gs_state != -1 and gs_state != 1) or gs_state is null) group by region) tt  " + 
			"left join pub_region t1 on tt.region = t1.city_code  where t1.merger_name LIKE CONCAT('%',tt.merger_name,'%')) count,tt.name,tt.city_code cityCode,tt.parent_code parentCode from pub_region tt where level_type >3) as t0 where t0.count > 0  ", nativeQuery = true)
	List<Map<String, Object>> getPubRegionInfoMove();

	@Query(value = "select name,cityCode,parentCode from (select (select count(*) from t_abm_land_audit tt"
			+ " left join pub_region t0 on tt.target_region = t0.city_code"
			+ " LEFT JOIN pub_region AS t1 ON t1.city_code = tt.source_region"
			+ " left join t_info_owner_impl t2 ON tt.nm = t2.nm"
			+ " where tt.fg_state = 0 and tt.audit_code = '64FABB5F0D' and tt.nm is not null and tt.nm != ''"
			+ " and t0.merger_name like concat(tt.merger_name,'%')) count,tt.name,tt.city_code cityCode,tt.parent_code parentCode from pub_region tt where tt.level_type >3) as t0 where t0.count > 0", nativeQuery = true)
	List<Map<String, Object>> getPubRegionInfoLand();

	@Query(value = "select t1.name,t1.id_card idCard from t_abm_publicity_details tt "
			+ "left join t_info_family_impl t1 on tt.owner_nm = t1.nm " + "where tt.nm =?1", nativeQuery = true)
	List<Map<String, Object>> getByDetailsNm(String nm);
	
	
	/**
	 * 搬迁安置公示 查询存在被公示人的行政区
	 * @return
	 */
	@Query(value = "select name,cityCode,parentCode from (select (select count(1) from (select * from t_info_family_impl  " + 
			"where owner_nm in(select nm from t_info_owner_impl where move_state = 0) and ((bq_gs_state != -1 and bq_gs_state != 1) or bq_gs_state is null) group by region) tt  " + 
			"left join pub_region t1 on tt.region = t1.city_code  where t1.merger_name LIKE CONCAT('%',tt.merger_name,'%')) count,tt.name,tt.city_code cityCode,tt.parent_code parentCode from pub_region tt where level_type >3) as t0 where t0.count > 0  ", nativeQuery = true)
	List<Map<String,Object>> getPubRegionInfoRemoval();
	
	/**
	 * 根据行政区查询被公示人 搬迁安置
	 * @param region
	 * @return
	 */
	@Query(value = "select tt.nm,tt.id_card idCard,tt.name  from t_info_family_impl  tt   " + 
			"left join pub_region t1 on tt.region = t1.city_code  where t1.merger_name LIKE CONCAT('%',:region,'%') " + 
			"and owner_nm in(select nm from t_info_owner_impl where move_state = 0) and ((tt.bq_gs_state != -1 and tt.bq_gs_state != 1) or tt.bq_gs_state is null) ", nativeQuery = true)
	List<PublicityFamilyVO> getPubRegionInfoRemovalFamily(@Param("region")String region);
}
