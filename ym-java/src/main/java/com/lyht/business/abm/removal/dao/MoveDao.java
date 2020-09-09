package com.lyht.business.abm.removal.dao;

import com.lyht.business.abm.removal.bean.MoveIdDetail;
import com.lyht.business.abm.removal.entity.MoveIdentity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 搬迁安置
 */
public interface MoveDao extends JpaRepository<MoveIdentity, Integer> {

	@Query(value = " select REPLACE(pf.merger_name,CONCAT(SUBSTRING_INDEX(pf.merger_name,',',3),','),'')  as regionName,tif.region,tmi.owner_nm as ownerNm,tmi.research,tmi.Impl_direction as implDirection,tif.id_card as idCard "
			+ "from t_abm_move_identity as tmi left  join  `t_info_owner_impl` as tif  on  tif.`name`= tmi.owner_nm left join   pub_region  as pf on pf.city_code=tif.region "
			+ "  where 1= 1 and IF (:ownerName is not null,tmi.owner_nm like CONCAT('%',:ownerName,'%'), 1=1)  order by  tmi.id desc ", countQuery = " select count(1) from ( select tmi.* "
					+ " from t_abm_move_identity as tmi left  join  `t_info_owner_impl` as tif  on  tif.`name`= tmi.owner_nm left join   pub_region  as pf on pf.city_code=tif.region where 1= 1 and IF(:ownerName is not null,tmi.owner_nm like CONCAT('%',:ownerName,'%'), 1=1)  order by tmi.id  desc) c ", nativeQuery = true)
	Page<MoveIdDetail> getList(Pageable pageable, @Param("ownerName") String ownerName);

	@Query(value = " select * from  t_abm_move_identity ", nativeQuery = true)
	List<Map> getListss();

	@Modifying
	@Query(value = " update t_abm_move_identity set research_place_type=:type,research='' where id=:id ", nativeQuery = true)
	void updateAnzhi(@Param("type") String type, @Param("id") Integer id);

	@Modifying
	@Query(value = " update t_abm_move_identity set research_place_type=:type where id=:id ", nativeQuery = true)
	void updateAnzhiss(@Param("type") String type, @Param("id") Integer id);

	// 搬迁安置表关联 户主表
	@Query(value = " select a.id as idNo,(select name from pub_dict_value b where b.nm=c.stage)stageName,c.stage"
			+ ",(select name from pub_dict_value b where b.nm=c.define)defineName,c.define,"
			+ "c.xiang as xiangs,c.cun as cuns,c.zu as zus,c.place_type as placeType,c.place_name as placeName,c.to_where as toWhere,"
			+ "(select name from pub_dict_value c where c.nm=b.scope)scopeName,"
			+ "(select name from pub_dict_value c where c.nm=c.households_type)householdsType,"
			+ "c.is_satisfy as isSatisfy,c.scope,c.age,c.master_relationship as masterRelationship,c.education_level as educationLevel,"
			+ "a.xiang,a.cun,a.zu,c.present_occupation as presentOccupation,c.current_school as currentSchool,c.households_home as householdsHome,"
			+ "c.lgtd,c.lttd,c.altd,c.in_map as inMap,c.remark,c.id,c.bq_gs_state,c.nm,b.name as hzName,REPLACE(pf.merger_name,CONCAT(SUBSTRING_INDEX(pf.merger_name,',',3),','),'')  as regionName,c.region,b.nm as owner_nm,c.name,"
			+ "c.id_card as idCard,a.research,a.research_place_type,a.Impl_direction as implDirection,c.national, "
			+ "  c.gender ,pdv.name AS ownerType,a.remarks from  t_abm_move_identity a"
			+ "  left JOIN `t_info_owner_impl` b on a.owner_nm=b.id_card left JOIN `t_info_family_impl` c on b.nm=c.owner_nm "
			+ "   left join   pub_region  as pf on pf.city_code=c.region"
			+ "  LEFT JOIN pub_dict_value AS pdv ON pdv.nm = c.master_relationship   "
			+ "  where 1=1   and IF (:home is not null,b.nm =:home, 1=1) "
			+ " and a.owner_nm=(SELECT id_card FROM t_abm_move_identity  a right join  t_info_owner_impl b ON a.owner_nm = b.id_card  WHERE 1 = 1 and IF (:region is not null,b.region LIKE CONCAT('%',:region,'%'), 1=1) and IF (:home is not null,b.nm =:home, 1=1)   "
			+ " and IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), if(:counts != 0,a.id = :counts,b.id_card is not null) )  LIMIT 0,1  ) "
	/* " and  b.id_card is not null LIMIT :counts,1 ) " */
			, countQuery = "select count(1) from ( select c.scope,c.age,c.master_relationship as masterRelationship,c.education_level as educationLevel, "
					+ "           c.present_occupation as presentOccupation,c.current_school as currentSchool,c.households_home as householdsHome, "
					+ "            c.households_type as householdsType,c.lgtd,c.lttd,c.altd,c.in_map as inMap,c.remark,c.id,b.name as hzName,REPLACE(pf.merger_name,CONCAT(SUBSTRING_INDEX(pf.merger_name,',',3),','),'')  as regionName,c.region,b.nm as owner_nm,c.name, "
					+ "            c.id_card as idCard,a.research,a.Impl_direction as implDirection,c.national,  "
					+ "              c.gender ,pdv.name AS ownerType,a.remarks from  t_abm_move_identity a "
					+ "              left JOIN `t_info_owner_impl` b on a.owner_nm=b.id_card left JOIN `t_info_family_impl` c on b.nm=c.owner_nm  "
					+ "               left join   pub_region  as pf on pf.city_code=c.region"
					+ "   LEFT JOIN pub_dict_value AS pdv ON pdv.nm = c.master_relationship "
					+ "              where 1=1    "
					+ " and a.owner_nm=(SELECT id_card FROM t_abm_move_identity  a right join  t_info_owner_impl b ON a.owner_nm = b.id_card  WHERE 1 = 1 and IF (:region is not null,b.region LIKE CONCAT('%',:region,'%'), 1=1) and IF (:home is not null,b.nm =:home, 1=1)   "
					+ " and IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), if(:counts != 0,a.id = :counts,b.id_card is not null) )  LIMIT 0,1  ) "
					+
					/* " and  b.id_card is not null LIMIT :counts,1 ) " */
					" ) as a", nativeQuery = true)
	Page<Map> getListKy(@Param("region") String region, @Param("name") String name, @Param("counts") int count,
			@Param("home") String home, Pageable pageable);

	// 搬迁安置表关联 户主表
	@Query(value = " select c.NAME,b.NAME AS hzName,pdv.NAME AS ownerType,c.gender,c.id_card AS idCard from  t_abm_move_identity a "
			+ "  left JOIN `t_info_owner_impl` b on a.owner_nm=b.id_card left JOIN `t_info_family_impl` c on b.nm=c.owner_nm "
			+ "  LEFT JOIN pub_dict_value AS pdv ON pdv.nm = c.master_relationship   "
			+ "  where 1=1  and IF (:isno is not null,isno = 1, 1=1) "
			+ " and a.owner_nm in (SELECT id_card FROM t_abm_move_identity left join  t_info_owner_impl ON owner_nm = id_card  WHERE 1 = 1 and IF (:name is not null,name LIKE CONCAT('%',:name,'%'), 1=1) and IF (:region is not null,region LIKE CONCAT('%',:region,'%'), 1=1) and  id_card is not null  )  ", nativeQuery = true)
	List<Map> list(@Param("region") String region, @Param("name") String name, @Param("isno") Integer isno);

	@Query(value = " select * from t_abm_move_identity where id=:id ", nativeQuery = true)
	MoveIdentity getId(@Param("id") int id);

	@Query(value = " select nm from t_info_owner where id_card=:idCard ", nativeQuery = true)
	List<Map> getNm(@Param("idCard") String idCard);

	@Query(value = " select name from pub_region where level_type=:type and "
			+ "  IF (:name is not null,merger_name LIKE CONCAT('%',:name,'%'), 1=1) ", nativeQuery = true)
	List<Map> getRegion(@Param("type") Integer type, @Param("name") String name);

	// 搬迁可研
	@Query(value = " select b.name,b.nm as owner_nm," + "pr.name as region,"
			+ "(select name from pub_dict_value a where a.nm=b.scope )as  scope,"
			+ "(select name from pub_dict_value a where a.nm=b.national )national,"
			+ "b.id_card,a.research_place_type,a.research,a.remarks from t_abm_move_identity a "
			+ " inner JOIN t_info_owner b on a.owner_nm=b.id_card"
			+ " LEFT JOIN pub_region pr  ON b.region=pr.city_code" + "  where 1=1 and"
			+ "  IF (:region is not null,pr.merger_name LIKE CONCAT('%',(SELECT merger_name FROM pub_region WHERE city_code = :region), 1=1) and "
			+ "  IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1) and "
			+ "  IF (:research is not null,a.research LIKE CONCAT('%',:research,'%'), 1=1) and "
			+ "  IF (:researchType is not null,a.research_place_type LIKE CONCAT('%',:researchType,'%'), 1=1) ", countQuery = " select count(1) from t_abm_move_identity a "
					+ " inner JOIN t_info_owner b on a.owner_nm=b.id_card "
					+ " LEFT JOIN pub_region pr  ON b.region=pr.city_code" + " where 1=1 and"
					+ "  IF (:region is not null,pr.merger_name LIKE CONCAT('%',(SELECT merger_name FROM pub_region WHERE city_code = :region), 1=1) and "
					+ "  IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1) and "
					+ "  IF (:research is not null,a.research LIKE CONCAT('%',:research,'%'), 1=1) and "
					+ "  IF (:researchType is not null,a.research_place_type LIKE CONCAT('%',:researchType,'%'), 1=1) ", nativeQuery = true)
	Page<Map> getBQKY(Pageable pageable, @Param("region") String region, @Param("name") String name,
			@Param("research") String research, @Param("researchType") String researchType);

	// 显示搬迁人口关联权属人列表
	@Query(value = " select b.name,b.nm as owner_nm," + "pr.merger_name as region,"
			+ "(select name from pub_dict_value a where a.nm=b.scope )as  scope,"
			+ "(select name from pub_dict_value a where a.nm=b.national )national,"
			+ "b.id_card,a.research_place_type,a.research,a.remarks" + " from t_info_owner_impl b "
			+ " LEFT JOIN t_abm_move_identity a on a.owner_nm=b.id_card "
			+ " LEFT JOIN pub_region pr  ON b.region=pr.city_code" + " where trim(a.owner_nm)!='' and"
			+ "  IF (:region is not null,pr.merger_name LIKE CONCAT('%',(SELECT merger_name FROM pub_region WHERE city_code = :region),'%'), 1=1) and "
			+ "  IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1) and "
			+ "  IF (:research is not null,a.research LIKE CONCAT('%',:research,'%'), 1=1) and "
			+ "  IF (:researchType is not null,a.research_place_type LIKE CONCAT('%',:researchType,'%'), 1=1) AND"
			+ "  IF (:idCard is not null, b.id_card LIKE CONCAT('%',:idCard,'%'), 1=1) ", countQuery = " select count(1) from t_info_owner_impl b "
					+ " LEFT JOIN t_abm_move_identity a on a.owner_nm=b.id_card"
					+ "  LEFT JOIN pub_region pr  ON b.region=pr.city_code" + "  where trim(a.owner_nm)!='' and"
					+ "  IF (:region is not null,pr.merger_name LIKE CONCAT('%',(SELECT merger_name FROM pub_region WHERE city_code = :region),'%'), 1=1) and "
					+ "  IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1) and "
					+ "  IF (:research is not null,a.research LIKE CONCAT('%',:research,'%'), 1=1) and "
					+ "  IF (:researchType is not null,a.research_place_type LIKE CONCAT('%',:researchType,'%'), 1=1) AND "
					+ "  IF (:idCard is not null, b.id_card LIKE CONCAT('%',:idCard,'%'), 1=1) ", nativeQuery = true)
	Page<Map> getHomeSs(Pageable pageable, @Param("region") String region, @Param("name") String name,
			@Param("research") String research, @Param("researchType") String researchType,
			@Param("idCard") String idCard);

	// 显示搬迁人口关联权属人列表
	@Query(value = " SELECT " + " b.name, b.nm AS owner_nm, b.is_satisfy AS state,"
			+ " ( SELECT COUNT( * ) FROM t_info_family_impl WHERE owner_nm = b.nm ) AS gsCount,"
			+ " b.place_type AS placeType, b.place_name AS placeName,"
			+ " b.i_population AS iPopulation, b.immigrant AS immigrant,"
			+ " pr.merger_name AS region, pdv4.name AS householdsType," + " pdv2.name AS  scope, pdv3.name AS national,"
			+ " b.id_card, a.research_place_type, a.research, a.remarks," + " pdv1.name AS placeTypeValue,"
			+ " tbp.status AS processStatus, tbp.cn_status AS processCnStatus," + " tamp.sign_file_url AS signFileUrl"
			+ " FROM t_info_owner_impl b " + " LEFT JOIN t_abm_move_identity a ON a.owner_nm=b.id_card"
			+ " LEFT JOIN pub_region pr  ON b.region=pr.city_code"
			+ " LEFT JOIN t_abm_move_process tamp ON b.nm = tamp.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp ON tamp.process_id = tbp.process_id"
			+ " LEFT JOIN pub_dict_value pdv1 ON b.place_type=pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON b.scope=pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON b.national=pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON b.households_type=pdv4.nm" + " WHERE 1=1"
//				+ " AND b.gs_state = '2'" //根据公示状态过滤 2020年3月24日演示暂时去掉
			+ " AND IF (:region is not null,pr.merger_name LIKE CONCAT('%',(SELECT merger_name FROM pub_region WHERE city_code = :region),'%'), 1=1) and "
			+ " IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1) and "
			+ " IF (:research is not null,a.research LIKE CONCAT('%',:research,'%'), 1=1) and "
			+ " IF (:researchType is not null,a.research_place_type LIKE CONCAT('%',:researchType,'%'), 1=1) AND"
			+ " IF (:idCard is not null, b.id_card LIKE CONCAT('%',:idCard,'%'), 1=1) ", countQuery = " SELECT"
					+ " COUNT(1) " + " FROM t_info_owner_impl b "
					+ " LEFT JOIN t_abm_move_identity a ON a.owner_nm=b.id_card"
					+ " LEFT JOIN pub_region pr  ON b.region=pr.city_code"
					+ " LEFT JOIN t_abm_move_process tamp ON b.nm = tamp.owner_nm"
					+ " LEFT JOIN t_bpm_process tbp ON tamp.process_id = tbp.process_id"
					+ " LEFT JOIN pub_dict_value pdv1 ON b.place_type=pdv1.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON b.scope=pdv2.nm"
					+ " LEFT JOIN pub_dict_value pdv3 ON b.national=pdv3.nm"
					+ " LEFT JOIN pub_dict_value pdv4 ON b.households_type=pdv4.nm" + " WHERE 1=1"
//					+ " AND b.gs_state = '2'"+ //根据公示状态过滤 2020年3月24日演示暂时去掉
					+ " AND IF (:region is not null,pr.merger_name LIKE CONCAT('%',(SELECT merger_name FROM pub_region WHERE city_code = :region),'%'), 1=1) and "
					+ " IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1) and "
					+ " IF (:research is not null,a.research LIKE CONCAT('%',:research,'%'), 1=1) and "
					+ " IF (:researchType is not null,a.research_place_type LIKE CONCAT('%',:researchType,'%'), 1=1) AND "
					+ " IF (:idCard is not null, b.id_card LIKE CONCAT('%',:idCard,'%'), 1=1) ", nativeQuery = true)
	Page<Map> getHomeSss(Pageable pageable, @Param("region") String region, @Param("name") String name,
			@Param("research") String research, @Param("researchType") String researchType,
			@Param("idCard") String idCard);

	// 搬迁去向
	@Query(value = " select (select name from pub_dict_value b where b.nm=c.stage)stageName,c.stage,(select name from pub_dict_value b where b.nm=c.define)defineName,c.define,c.is_satisfy as isSatisfy,c.scope,c.age,c.master_relationship as masterRelationship,c.education_level as educationLevel,"
			+ "c.present_occupation as presentOccupation,c.current_school as currentSchool,c.households_home as householdsHome,"
			+ "(select name from pub_dict_value b where b.nm=c.households_type)householdsType,c.lgtd,c.lttd,c.altd,c.in_map as inMap,c.remark,b.nm as ownerNm,c.id,b.name as hzName,REPLACE(pf.merger_name,CONCAT(SUBSTRING_INDEX(pf.merger_name,',',3),','),'')  as regionName,c.region,c.id as owner_nm,c.name,"
			+ "c.id_card as idCard,a.research,a.Impl_direction as implDirection,c.national, "
			+ "  c.gender ,pdv.name AS ownerType,a.remarks,"
			+ "	move.id as moveId,move.to_where as toWhere,move.place_type as placeType,move.place_name as placeName,move.place_addr as placeAddr "
			+ " from  t_abm_move_identity a"
			+ "  left JOIN `t_info_owner_impl` b on a.owner_nm=b.id_card left JOIN `t_info_family_impl` c on b.nm=c.owner_nm "
			+ "   left join   pub_region  as pf on pf.city_code=c.region"
			+ "  LEFT JOIN pub_dict_value AS pdv ON pdv.nm = c.master_relationship "
			+ " left join t_abm_move_approve as move on move.owner_nm=c.id  " + "  where 1=1 and c.is_satisfy='2'  "
			+ " and a.owner_nm=(SELECT id_card FROM t_abm_move_identity left join  t_info_owner_impl ON owner_nm = id_card  WHERE 1 = 1 and IF (:name is not null,name LIKE CONCAT('%',:name,'%'), 1=1) and IF (:region is not null,region LIKE CONCAT('%',:region,'%'), 1=1) and  id_card is not null LIMIT :counts,1 )  ", countQuery = "select count(1) from ( select c.scope,c.age,c.master_relationship as masterRelationship,c.education_level as educationLevel, "
					+ "           c.present_occupation as presentOccupation,c.current_school as currentSchool,c.households_home as householdsHome, "
					+ "            c.households_type as householdsType,c.lgtd,c.lttd,c.altd,c.in_map as inMap,c.remark,c.id,b.name as hzName,REPLACE(pf.merger_name,CONCAT(SUBSTRING_INDEX(pf.merger_name,',',3),','),'')  as regionName,c.region,c.id as owner_nm,c.name, "
					+ "            c.id_card as idCard,a.research,a.Impl_direction as implDirection,c.national,  "
					+ "              c.gender ,pdv.name AS ownerType,a.remarks,"
					+ "	move.to_where as toWhere,move.place_type as placeType,move.place_name as placeName,move.place_addr as placeAddr "

					+ " from  t_abm_move_identity a "
					+ "              left JOIN `t_info_owner_impl` b on a.owner_nm=b.id_card left JOIN `t_info_family_impl` c on b.nm=c.owner_nm  "
					+ "               left join   pub_region  as pf on pf.city_code=c.region"
					+ "   LEFT JOIN pub_dict_value AS pdv ON pdv.nm = c.master_relationship "
					+ " left join t_abm_move_approve as move on move.owner_nm=c.id  "
					+ "              where 1=1 and c.is_satisfy='2'  "
					+ "             and a.owner_nm=(SELECT id_card FROM t_abm_move_identity left join  t_info_owner_impl ON owner_nm = id_card  WHERE 1 = 1 and IF (:name is not null,name LIKE CONCAT('%',:name,'%'), 1=1) and IF (:region is not null,region LIKE CONCAT('%',:region,'%'), 1=1) and  id_card is not null LIMIT :counts,1 ) and "
					+ " ) as a", nativeQuery = true)
	Page<Map> getQxList(@Param("region") String region, @Param("name") String name, @Param("counts") int count,
			Pageable pageable);
	
	@Query(value = "SELECT" +
			" (select" +
			"  COALESCE(sum(immigrant),0)" +
			" from t_info_owner_impl ioi " +
			" where pr.city_code = ioi.region and ioi.is_satisfy = '2'" +
			" ) as satisfyHourse," +
			" (select" +
			"  COALESCE(sum(immigrant),0)" +
			" from t_info_owner_impl ioi " +
			" where pr.city_code = ioi.region and (ioi.is_satisfy != '2' or ioi.is_satisfy is NULL)" +
			" ) as notSatisfyHourse," +
			" (select" +
			"  COALESCE(sum(immigrant),0)" +
			" from t_info_owner_impl ioi " +
			" where pr.city_code = ioi.region" +
			" ) as sumHourse," +
			" (select" +
			" COALESCE(sum(i_population),0)" +
			" from t_info_owner_impl ifi " +
			" where pr.city_code = ifi.region and ifi.is_satisfy = '2'" +
			" ) as satisfyNumber," +
			" (select" +
			" COALESCE(sum(i_population),0)" +
			" from t_info_owner_impl ifi " +
			" where pr.city_code = ifi.region and (ifi.is_satisfy != '2' or ifi.is_satisfy is NULL)" +
			" ) as notSatisfyNumber," +
			" (select" +
			" COALESCE(sum(i_population),0)" +
			" from t_info_owner_impl ifi " +
			" where pr.city_code = ifi.region" +
			" ) as sumNumber," +
			" pr.city_code as citycode  ,pr.parent_code as  parentcode , pr.name as name, pr.level as level,pr.merger_name as  mergerShortName" +
			" FROM" +
			" pub_region  pr WHERE pr.merger_name Like CONCAT('%', '维西', '%')",nativeQuery = true)
	List<Map<String,Object>> getRemovalCount();

}
