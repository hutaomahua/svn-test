package com.lyht.business.abm.removal.dao;

import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.vo.PersonaWealthFamilyVO;
import com.lyht.business.info.entity.InfoFamilyEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

public interface AbmFamilyDao extends JpaRepository<AbmFamilyEntity,Integer> {
	
	@Query(value = "select owner_nm ownerNm from t_info_family_impl where nm in (:ownerNms) GROUP BY owner_nm",nativeQuery = true)
	List<String> getOwnerNmByNms(@Param("ownerNms")List<String> ownerNms);

	@Query(value = "select * from t_info_family_impl where id_card = ?1",nativeQuery = true)
	AbmFamilyEntity queryByIdCard(String idCard);
	
	@Modifying
	@Query(value = "update t_info_family_impl set produce_place = :producePlace where owner_nm = :nm",nativeQuery = true)
	Integer updateProducePlace(@Param("producePlace")String producePlace,@Param("nm")String nm);

	@Modifying
	@Query(value = "update t_info_family_impl set gs_state = :gsState where nm = :nm",nativeQuery = true)
	Integer updateGsState(@Param("gsState")Integer gsState,@Param("nm")String nm);
	
	@Modifying
	@Query(value = "update t_info_family_impl set bq_gs_state = :gsState where nm = :nm",nativeQuery = true)
	Integer updateBQGsState(@Param("gsState")Integer gsState,@Param("nm")String nm);
	
	@Query(value = "select count(1) from t_info_family_impl where is_move = '是' and owner_nm = :ownerNm",nativeQuery = true)
	Integer getCountByOwnerNm(@Param("ownerNm")String ownerNm);
	
	
	@Modifying
	@Query(value = "delete from t_info_family_impl where owner_nm = :ownerNm",nativeQuery = true)
	Integer deleteByOwnerNm(@Param("ownerNm")String ownerNm);
	
	
	@Query(value = "select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,tt.`name`,tt.id_card idCard,tt.master_relationship masterRelationship,t2.name relationshipName, " + 
			" tt.scope,t6.name scopeName,tt.gender,tt.national,t3.name nationalName,tt.age,tt.education_level educationLevel,tt.present_occupation presentOccupation,tt.current_school currentSchool, " + 
			" tt.households_home householdsHome,count(file.id) AS fileCount,tt.households_type householdsType,t7.name householdsTypeName,tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,t4.merger_name mergerName,t4.city_code region,tt.remark" +
			" from t_info_family_impl tt " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
			" left join pub_dict_value t2 on tt.master_relationship = t2.nm " + 
			" left join pub_dict_value t3 on tt.national = t3.nm "+ 
			" left join pub_region t4 on tt.region = t4.city_code " + 
			" left join pub_dict_value t6 on tt.scope = t6.nm " +
			" left join pub_dict_value t7 on tt.households_type = t7.nm" +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_family_impl' AND file.table_pk_column = tt.nm  " +
			" where tt.owner_nm= :ownerNm  GROUP BY tt.id ",
			countQuery = "select count(1) from (select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,tt.`name`,tt.id_card idCard,tt.master_relationship relationship,t2.name relationshipName, " + 
					" tt.scope,tt.gender,tt.national,t3.name nationalName,tt.age,tt.education_level educationLevel,tt.present_occupation presentOccupation,tt.current_school currentSchool, " + 
					" tt.households_home householdsHome,tt.households_type householdsType,tt.lgtd,tt.lttd,tt.in_map inMap" + 
					" from t_info_family_impl tt " + 
					" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
					" left join pub_dict_value t2 on tt.master_relationship = t2.nm " + 
					" left join pub_dict_value t3 on tt.national = t3.nm  " + 
					" where tt.owner_nm= :ownerNm) as t0 ",nativeQuery = true)
	Page<PersonaWealthFamilyVO> findByOwnerNmOfPersonaWealth(@Param("ownerNm")String ownerNm,Pageable pageable);//查询权属人的家庭成员.
	
	@Query(value = "select t1.name as ownerName,t1.nm ownerNm,tt.id,tt.nm,tt.`name`,tt.id_card idCard,tt.master_relationship masterRelationship,t2.name relationshipName,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount, " + 
			" tt.scope,t6.name scopeName,tt.gender,tt.national,t3.name nationalName,tt.age,tt.education_level educationLevel,tt.present_occupation presentOccupation,tt.current_school currentSchool, " + 
			" tt.households_home householdsHome,tt.households_type householdsType,t7.name householdsTypeName,tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap,t4.merger_name mergerName,t4.city_code region,tt.remark" + 
			" from t_info_family_impl tt " + 
			" left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  " + 
			" left join pub_dict_value t2 on tt.master_relationship = t2.nm " + 
			" left join pub_dict_value t3 on tt.national = t3.nm "+ 
			" left join pub_region t4 on tt.region = t4.city_code " + 
			" left join pub_dict_value t6 on tt.scope = t6.nm " +
			" left join pub_dict_value t7 on tt.households_type = t7.nm " +
			" where tt.owner_nm= :ownerNm ",
			nativeQuery = true)
	List<Map<String,Object>> findByOwnerNmOfPersonaWealth(@Param("ownerNm")String ownerNm);//查询权属人的家庭成员

	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET fh_state =:fhstate,gs_state =:gsstate ,process_id=:processId,fh_nm =:fhNm WHERE nm =:ownernm  ", nativeQuery = true)
	void updateAbmFamily(@Param("ownernm") String ownernm,@Param("fhstate") String fhstate,@Param("gsstate") String gsstate,@Param("processId")String processId,@Param("fhNm")String fhNm);
	
	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET fh_state =:fhstate ,process_id=:processId WHERE nm =:ownernm  ", nativeQuery = true)
	void updateAbmFamily(@Param("ownernm") String ownernm,@Param("fhstate") String fhstate,@Param("processId")String processId);
	
	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET gs_state =:gsstate  WHERE nm =:ownernm  ", nativeQuery = true)
	void updateAbmFamilys(@Param("gsstate") Integer gsstate,@Param("ownernm") String ownernm);
	/**
	 * 修改家庭成员数据状态 0 正常 1删除
	 * @param ownernm
	 * @param fhstate
	 * @param processId
	 * @param fhNm
	 */
	@Modifying
	@Query(value = "UPDATE t_info_family_impl SET state =:state  WHERE id =:id  ", nativeQuery = true)
	void updateState(@Param("state") String state,@Param("id")Integer id);
	
	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET "
			+ " is_satisfy=:isSatisfy "
			+ ", xiang=:xiang  "
			+ ", cun=:cun  "
			+ ", zu=:zu  "
			+ ", place_type=:placeType  "
			+ ", place_name=:placeName  "		
			+ " WHERE nm =:nm  ", nativeQuery = true)
	void updateOwner(@Param("nm") String nm
			,@Param("isSatisfy") String isSatisfy
			,@Param("xiang") String xiang
			,@Param("cun") String cun
			,@Param("zu") String zu
			,@Param("placeType") String placeType
			,@Param("placeName") String placeName);
	@Modifying
	@Query(value = "UPDATE t_info_family_impl SET define=:defind , is_satisfy=:isSatisfy "
			+ ", xiang=:xiang  "
			+ ", cun=:cun  "
			+ ", zu=:zu  "
			+ ", place_type=:placeType  "
			+ ", place_name=:placeName  "
			+ ", to_where=:toWhere  "
			+ "WHERE id =:id  ", nativeQuery = true)
	void updateJieding(@Param("id") Integer id
			,@Param("defind") String defind
			,@Param("isSatisfy") String isSatisfy
			,@Param("xiang") String xiang
			,@Param("cun") String cun
			,@Param("zu") String zu
			,@Param("placeType") String placeType
			,@Param("placeName") String placeName
			,@Param("toWhere") String toWhere);

    @Query(value =" SELECT c.nm,c.state,c.scope,c.age,c.current_school AS currentSchool,c.households_home AS householdsHome,c.master_relationship AS  masterRelationship,c.education_level as educationLevel,c.present_occupation as presentOccupation," +
            "c.households_type AS householdsType,c.lgtd,c.lttd,c.altd,c.in_map AS inMap,c.remark," +
            "c.id,b.NAME AS hzName,pf.merger_name AS regionName,c.region,b.nm AS owner_nm,c.NAME," +
            "c.id_card AS idCard,p.name as national,c.gender,pdv.NAME AS ownerType FROM`t_info_owner_impl` b " +
			"left join pub_dict_value as p on b.national = p.nm "+
            "LEFT JOIN `t_info_family_impl` c ON b.nm = c.owner_nm " +
            "LEFT JOIN pub_region AS pf ON pf.city_code = c.region " +
            "LEFT JOIN pub_dict_value AS pdv ON pdv.nm = c.master_relationship " +
            " WHERE  1 = 1 and IF(:ownerNm is not null, b.nm = :ownerNm ,1=1) ",
            countQuery=" select count(1) from (SELECT c.scope,c.age,c.current_school AS currentSchool,c.households_home AS householdsHome," +
            "c.households_type AS householdsType,c.lgtd,c.lttd,c.altd,c.in_map AS inMap,c.remark," +
            "c.id,b.NAME AS hzName,pf.merger_name AS regionName,c.region,b.nm AS owner_nm,c.NAME," +
            "c.id_card AS idCard,c.national,c.gender,pdv.NAME AS ownerType FROM`t_info_owner_impl` b " +
			"left join pub_dict_value as p on b.national = p.nm "+
            "LEFT JOIN `t_info_family_impl` c ON b.nm = c.owner_nm " +
            "LEFT JOIN pub_region AS pf ON pf.city_code = c.region " +
            "LEFT JOIN pub_dict_value AS pdv ON pdv.nm = c.master_relationship " +
            " WHERE  1 = 1 and IF(:ownerNm is not null, b.nm = :ownerNm ,1=1) ) as a",nativeQuery = true)
    Page<Map> getList(Pageable pageable, @Param("ownerNm") String ownerNm);

    @Query(value =" SELECT " +
            "b.NAME AS hzName,b.nm as owner_nm, " +
            "pf.merger_name AS regionName," +
            "  (select count(*) from t_info_family_impl as c where c.owner_nm=b.nm) as count,"
            + " (SELECT IFNULL(COUNT(1),0) FROM t_info_family_impl"
			+ " WHERE owner_nm = :ownerNm) AS population,"
			+ " (SELECT IFNULL(SUM(area),0) AS population FROM t_info_houses_impl"
			+ " WHERE owner_nm = :ownerNm) AS houseArea,"
			+ " (SELECT IFNULL(SUM(area),0) AS population FROM t_info_houses_decoration_impl"
			+ " WHERE owner_nm = :ownerNm) AS houseDecorationArea,"
			+ " (SELECT IFNULL(SUM(area),0) AS population FROM t_info_land_impl"
			+ " WHERE owner_nm = :ownerNm) AS landArea"
            + " FROM`t_info_owner_impl` b " +
            "LEFT JOIN pub_region AS pf ON pf.city_code = b.region " +
            " WHERE  1 = 1 and IF(:ownerNm is not null, b.nm = :ownerNm ,1=1) "
            + " LIMIT 1"
            ,nativeQuery = true)
    Map getOwnerInfo(@Param("ownerNm") String ownerNm);
    
    @Query(value =" SELECT owner_nm as nm from t_abm_personal_wealth where review_process_id=:taskId LIMIT 1",nativeQuery = true)          
    List<Map> getOwnerNm(@Param("taskId") String taskId);
    
    @Query(value ="select a.*,(select name from t_info_owner b where b.nm=a.owner_nm )as name"
    		+ ",(select name from pub_project c where c.id=a.project_nm )as pcjName, CASE " + 
    		"        WHEN a.longs is not null " + 
    		"            then a.longs " + 
    		"        WHEN a.width is not null " + 
    		"            then a.width " + 
    		"        WHEN a.height is not null " + 
    		"         THEN a.height " + 
    		"				WHEN a.area is not null " + 
    		"         THEN a.area " + 
    		"				WHEN a.volume is not null " + 
    		"         THEN a.volume " + 
    		"				WHEN a.num is not null " + 
    		"         THEN a.num " + 
    		"        ELSE " + 
    		"            0 " + 
    		"    END as number,"
    		+" (select b.merger_name from pub_region b where b.city_code=a.region)regionName "
    		+ " from t_info_building_impl  a where a.owner_nm=:ownerNm and"
    		+ " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "
    		+ " IF (:projectNm is not null,a.project_nm LIKE CONCAT('%',:projectNm,'%'), 1=1)",
    countQuery="select count(1) "
    		+ " from t_info_building_impl  a where a.owner_nm=:ownerNm and"
    		+ " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "
    		+ " IF (:projectNm is not null,a.project_nm LIKE CONCAT('%',:projectNm,'%'), 1=1)"
    		,nativeQuery = true)
    Page<Map> getFSList(Pageable pageable, @Param("ownerNm") String ownerNm,@Param("region") String region,@Param("projectNm") String projectNm);
    
    
    @Query(value ="select a.*,(select name from t_info_owner b where b.nm=a.owner_nm )as name"
    		+ ",(select name from pub_project c where c.id=a.project_nm )as pcjName"
    		+" ,(select b.merger_name from pub_region b where b.city_code=a.region)regionName "
    		+ " from t_info_trees_impl  a where a.owner_nm=:ownerNm and"
    		+ " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "
    		+ " IF (:projectNm is not null,a.project_nm LIKE CONCAT('%',:projectNm,'%'), 1=1)",
    		
    countQuery="select count(1) from t_info_trees_impl  a where a.owner_nm=:ownerNm and"
    		+ " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "
    		+ " IF (:projectNm is not null,a.project_nm LIKE CONCAT('%',:projectNm,'%'), 1=1)"
    		,nativeQuery = true)
    Page<Map> getQMList(Pageable pageable, @Param("ownerNm") String ownerNm,@Param("region") String region,@Param("projectNm") String projectNm);

	@Query(value =" select a.*,'m²' as unit,"
			+ "(select name from t_info_owner b where b.nm=a.owner_nm) as name "
			+ ",(select name from pub_project c where c.id=a.project_nm )as projectName "
			+" ,(select b.merger_name from pub_region b where b.city_code=a.region)regionName "
			+ " from t_info_houses_impl a where a.owner_nm=:ownerNm ",
			countQuery=" select count(1) from t_info_houses_impl a where a.owner_nm=:ownerNm "
			,nativeQuery = true)
    Page<Map> getLandHouse(Pageable pageable,@Param("ownerNm") String ownerNm);
	
	@Query(value =" select  tt.name as tdName1   , t1.name  as tdName2  , t2.name as tdName3   , a.*,"
			+ "(select name from t_info_owner b where b.nm=a.owner_nm) as name "
			+ ",tt.name oneName,t1.name twoName,t2.name threeName"
			+" ,(select b.merger_name from pub_region b where b.city_code=a.region)regionName "
			+ " from t_info_land_impl a "
			+ " left join pub_dict_value tt on a.type_one = tt.nm " + 
			" left join pub_dict_value t1 on a.type_two = t1.nm " + 
			" left join pub_dict_value t2 on a.type_three = t2.nm "
			+ "where a.owner_nm=:ownerNm ",
			countQuery=" select count(1) from t_info_land_impl a where a.owner_nm=:ownerNm "
			,nativeQuery = true)
    Page<Map> getTudi(Pageable pageable,@Param("ownerNm") String ownerNm);
	
    @Query(value ="select "+
			"(select name from t_info_owner a where a.nm=b.owner_nm) as owner_nm1,"+
			"(select name from pub_dict_value a where a.nm=b.house_nature)as house_nature1"+
			" ,(select c.merger_name from pub_region c where c.city_code=b.region)regionName "
			+",b.* from t_info_houses_decoration b "
			+ "where b.owner_nm=:ownerNm"
    		
    ,countQuery="select count(1) from t_info_houses_decoration b "
			+ "where b.owner_nm=:ownerNm"
    		,nativeQuery = true)
    Page<Map> getZXList(Pageable pageable, @Param("ownerNm") String ownerNm);

	
    /**
     * 实物指标权属人列表
     * @param pageable
     * @param ownerNm
     * @return
     */
    @Query(value =" select ifnull(abm.protocol_state,0) as protocolState,  cc.end_time,a.fh_nm as fhNm,a.gs_state as gsState,a.fh_state as fhstate,a.id,a.nm as owner_nm,a.process_id,"+
    			"(select merger_name from pub_region b WHERE b.city_code=a.region)as  region,"+
    			"a.name,id_card,"+
    			"(select name from pub_dict_value b where b.nm=a.national )national,"+
    			"(select name from pub_dict_value b where b.nm=a.households_type )households_type,"+
    			" i_population,"
    			+ "(select name from pub_dict_value b where b.nm=a.scope )scope,in_map "
    			+ "from t_info_owner_impl a LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
    			+ "LEFT JOIN t_abm_total_state abm ON abm.owner_nm=a.nm "
    			+ " LEFT JOIN t_abm_publicity_details cc ON cc.owner_nm=a.nm where 1=1 and "+
    	     " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
            "  IF (:scope is not null,a.scope=:scope, 1=1) and "+
            "  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1) and "+
            "  IF (:fhState is not null,a.fh_state=:fhState, 1=1) and "+
            "  IF (:processId is not null,a.process_id=:processId, 1=1) and "+
            "  IF (:idCard is not null,a.id_card LIKE CONCAT('%',:idCard,'%'), 1=1)   " + 
             " and a.nm not in :nm  " +
             " GROUP BY  a.nm  ORDER BY  a.id,cc.end_time desc "
    ,countQuery=" select count(1) from t_info_owner_impl a LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region"
    		+ " LEFT JOIN t_abm_publicity_details cc ON cc.owner_nm=a.nm where 1=1 and "+
            " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
            "  IF (:scope is not null,a.scope=:scope, 1=1) and "+
            "  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1) and "+
            "  IF (:fhState is not null,a.fh_state=:fhState, 1=1) and "+
            "  IF (:processId is not null,a.process_id=:processId, 1=1) and " +
            "  IF (:idCard is not null,a.id_card LIKE CONCAT('%',:idCard,'%'), 1=1)   "
            +  " and a.nm not in :nm  "
            + " GROUP BY  a.nm  ORDER BY  a.id,cc.end_time desc ",
    		nativeQuery = true)
    Page<Map> getOwnerList(Pageable pageable, @Param("region") String region,@Param("scope") String scope,@Param("name") String name,@Param("fhState") String fhState,@Param("processId")String processId,@Param("nm")List<String>  nm, @Param("idCard") String idCard);

    @Query(value =" select  a.fh_state as fhstate,a.id,a.nm as owner_nm ,"+
			"(select merger_name from pub_region b WHERE b.city_code=a.region)as  region,"+
			" a.name,id_card,"+
			"(select b.name from pub_dict_value b where b.nm=a.national )national,"+
			"(select b.name from pub_dict_value b where b.nm=a.households_type )households_type,"+
			" i_population,"
			+ "(select b.name from pub_dict_value b where b.nm=a.scope )scope,in_map "
			+ "from t_info_owner_impl a LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region where 1=1 and "+
   	     " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
        "  IF (:scope is not null,a.scope=:scope, 1=1) and "+
        "  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1) and "+
        "  IF (:stage is not null,a.stage=:stage, 1=1) "+
         " GROUP BY a.name order BY id ASC  "
         ,countQuery=" select count(1) from t_info_owner_impl a LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region where 1=1 and "+
	     " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
        "  IF (:scope is not null,a.scope=:scope, 1=1) and "+
        "  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1) and "+
        "  IF (:stage is not null,a.stage=:stage, 1=1)  "
        + " GROUP BY a.name order BY a.id ASC ",
		nativeQuery = true)
Page<Map> getOwnerLists(Pageable pageable, @Param("region") String region, @Param("scope") String scope, @Param("name") String name, @Param("stage") String stage);

    /**
     * 家庭成员列表
     * @param pageable
     * @return
     */
    @Query(value =
    		"select name,gender,id_card,(select name from pub_region b where  b.city_code=a.region )region,"
    		+ "(select name from t_info_owner b where b.nm=a.owner_nm )huzhu,households_home, "
    		+ "(select name from pub_dict_value b WHERE b.nm=a.national)national,"
    		+ "(select name from pub_dict_value b WHERE b.nm=a.education_level)education_level,"
    		+ "(select name from pub_dict_value b WHERE b.nm=a.master_relationship)master_relationship, "
    		+ " (select name from pub_dict_value b where b.nm=a.scope )scope,in_map "
    		+ " from t_info_family a WHERE stage='0E48D6F81C' and "+
			"  IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and"+
			"  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1)  "
    		,countQuery=
				"select count(1) "
				+ " from t_info_family a WHERE stage='0E48D6F81C' and "+
				"  IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and"+
				"  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1)  "

		,nativeQuery = true)
Page<Map> getJiaTi(Pageable pageable,@Param("region")String region,@Param("name")String name);

	/**
	 * 根据人员内码查询
	 * @param nm
	 * @return
	 */
	AbmFamilyEntity findByNm(String nm);

	
	  /**
     * 实物指标权属人列表
     * @param pageable
     * @param ownerNm
     * @return
     */
    @Query(value =" select tt.nm as ownerNm,tt.name,tt.id_card idCard from t_info_owner_impl tt " + 
    		"left join pub_region t1 on tt.region = t1.city_code " + 
    		"where tt.fh_state = 4 and tt.gs_state = 0 " + 
    		"and t1.merger_name LIKE CONCAT('%',:region,'%')",nativeQuery = true)
	List<Map> getOwnerAllList(@Param("region") String region);

    
    @Query(value =" SELECT * from t_info_owner_impl where b.nm = :ownerNm ",nativeQuery = true)          
    AbmFamilyEntity getOwnerDetails(@Param("ownerNm") String ownerNm);
    
    @Query(value =" SELECT * FROM t_info_family_impl tt WHERE tt.owner_nm = :ownerNm AND tt.id_card = :idCard LIMIT 1",nativeQuery = true)    
	AbmFamilyEntity findByOwnerNmAndIdCard(@Param("ownerNm") String ownerNm, @Param("idCard") String idCard);
    
    @Modifying
	@Query(value = "update t_info_family_impl set is_produce = 0 where owner_nm = :ownerNm",nativeQuery = true)
	Integer resetState(@Param("ownerNm")String ownerNm);

    /**
//     * 通过户主nm查询家庭成员
//     * @param ownerNm
//     * @return
//     */
	@Query(value =" SELECT tindex.*,count(file.id) AS fileCount  " +
			" FROM t_info_family_impl tindex " +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_family_impl' AND file.table_pk_column = tindex.nm " +
			" WHERE tindex.owner_nm = :ownerNm GROUP BY tindex.id ",nativeQuery = true)
	List<Map> getByOwnerNm(@Param("ownerNm")String ownerNm);
	
	List<AbmFamilyEntity> findByOwnerNm(String ownerNm);
	
	@Modifying
	@Query(value = "update t_info_family_impl set age = :age where id = :id",nativeQuery = true)
	void updateAge(Integer age,Integer id);
	
	@Query(value = "select * from t_info_family_impl where LENGTH(id_card) = 15",nativeQuery = true)
	List<AbmFamilyEntity> findIdCardLength15();
	
	@Query(value = "select * from t_info_family_impl where LENGTH(id_card) = 18",nativeQuery = true)
	List<AbmFamilyEntity> findIdCardLength18();
	
	
}
