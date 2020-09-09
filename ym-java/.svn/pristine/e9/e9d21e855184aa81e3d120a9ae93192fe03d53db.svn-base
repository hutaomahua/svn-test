package com.lyht.business.info.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoFamilyEntity;
import com.lyht.business.info.entity.InfoOwnerEntity;
import com.lyht.business.info.vo.InfoFamilyVO;

import java.util.List;
import java.util.Map;


public interface InfoFamilyDao extends JpaRepository<InfoFamilyEntity, Integer> {
	
	@Query(value = "SELECT " 
			+ " tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.name AS name,"
			+ " tt.owner_nm AS ownerNm, tt.id_card AS idCard, tt.gender AS gender,"
			+ " tt.national AS national, tt.age AS age,"
			+ " tt.master_relationship AS masterRelationship, tt.education_level AS educationLevel,"
			+ " tt.present_occupation AS presentOccupation, tt.current_school AS currentSchool,"
			+ " tt.households_home AS householdsHome, tt.households_type AS householdsType,"
			+ " tt.lgtd AS lgtd, tt.lttd AS lttd,"
			+ " tt.altd AS altd, tt.in_map AS inMap,"
			+ " tt.is_move AS isMove, tt.is_produce AS isProduce,"
			+ " tt.fh_state AS fhState, tt.define AS define,"
			+ " tt.is_satisfy AS isSatisfy, tt.xiang AS xiang,"
			+ " tt.cun AS cun, tt.zu AS zu,"
			+ " tt.place_type AS placeType, tt.place_name AS placeName,"
			+ " tt.place_address AS placeAddress,"
			+ " tt.to_where AS toWhere, tt.bq_gs_state AS bqGsState,"
			+ " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser,"
			+ " tt.remark AS remark, tt.zblx AS zblx,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue,"
			+ " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS nationalValue,"
			+ " pdv5.name AS educationLevelValue, pdv6.name AS householdsTypeValue,"
			+ " pdv7.name AS masterRelationshipValue, pdv8.name AS placeTypeValue," +
			"count(file.id) AS fileCount"
			+ " FROM t_info_family tt" 
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.national = pdv4.nm" 
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.education_level = pdv5.nm" 
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.households_type = pdv6.nm" 
			+ " LEFT JOIN pub_dict_value pdv7 ON tt.master_relationship = pdv7.nm" 
			+ " LEFT JOIN pub_dict_value pdv8 ON tt.place_type = pdv8.nm" +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_family' AND file.table_pk_column = tt.nm "
			+ " WHERE 1=1"
			+ " AND IF (:region is not null AND :region != '', REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:scope is not null AND :scope != '', tt.scope = :scope, 1=1)"
			+ " AND IF (:name is not null AND :name != '', ("
			+ " tt.name like CONCAT('%',:name,'%') OR tio.name like CONCAT('%',:name,'%')"
			+ " ), 1=1)"
			+ " AND IF (:idCard is not null AND :idCard != '', tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
			+ " AND IF (:isDirtyData IS NOT NULL AND :isDirtyData != '', IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
			+ " AND IF (:ownerNm is not null AND :ownerNm != '', tt.owner_nm = :ownerNm, 1=1)" +
			" GROUP BY tt.nm"
			, countQuery = " SELECT"
					+ " COUNT(1)" 
					+ " FROM t_info_family tt" 
					+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
					+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.national = pdv4.nm" 
					+ " LEFT JOIN pub_dict_value pdv5 ON tt.education_level = pdv5.nm" 
					+ " LEFT JOIN pub_dict_value pdv6 ON tt.households_type = pdv6.nm" 
					+ " LEFT JOIN pub_dict_value pdv7 ON tt.master_relationship = pdv7.nm" 
					+ " LEFT JOIN pub_dict_value pdv8 ON tt.place_type = pdv8.nm" 
					+ " WHERE 1=1"
					+ " AND IF (:region is not null AND :region != '', REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:scope is not null AND :scope != '', tt.scope = :scope, 1=1)"
					+ " AND IF (:name is not null AND :name != '', ("
					+ " tt.name like CONCAT('%',:name,'%') OR tio.name like CONCAT('%',:name,'%')"
					+ " ), 1=1)"
					+ " AND IF (:idCard is not null AND :idCard != '', tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
					+ " AND IF (:isDirtyData IS NOT NULL AND :isDirtyData != '', IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
					+ " AND IF (:ownerNm is not null AND :ownerNm != '', tt.owner_nm = :ownerNm, 1=1)"
					, nativeQuery = true)
	Page<InfoFamilyVO> page(@Param("region") String region,
			@Param("scope") String scope, 
			@Param("name") String name, 
			@Param("idCard") String idCard,
			@Param("isDirtyData") String isDirtyData,
			@Param("ownerNm") String ownerNm, Pageable pageable);
	
	@Query(value = "SELECT " 
			+ " tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.name AS name,"
			+ " tt.owner_nm AS ownerNm, tt.id_card AS idCard, tt.gender AS gender,"
			+ " tt.national AS national, tt.age AS age,"
			+ " tt.master_relationship AS masterRelationship, tt.education_level AS educationLevel,"
			+ " tt.present_occupation AS presentOccupation, tt.current_school AS currentSchool,"
			+ " tt.households_home AS householdsHome, tt.households_type AS householdsType,"
			+ " tt.lgtd AS lgtd, tt.lttd AS lttd,"
			+ " tt.altd AS altd, tt.in_map AS inMap,"
			+ " tt.is_move AS isMove, tt.is_produce AS isProduce,"
			+ " tt.fh_state AS fhState, tt.define AS define,"
			+ " tt.is_satisfy AS isSatisfy, tt.xiang AS xiang,"
			+ " tt.cun AS cun, tt.zu AS zu,"
			+ " tt.place_type AS placeType, tt.place_name AS placeName,"
			+ " tt.place_address AS placeAddress,"
			+ " tt.to_where AS toWhere, tt.bq_gs_state AS bqGsState,"
			+ " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser,"
			+ " tt.remark AS remark, tt.zblx AS zblx,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue,"
			+ " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS nationalValue,"
			+ " pdv5.name AS educationLevelValue, pdv6.name AS householdsTypeValue,"
			+ " pdv7.name AS masterRelationshipValue, pdv8.name AS placeTypeValue"
			+ " FROM t_info_family tt" 
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.national = pdv4.nm" 
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.education_level = pdv5.nm" 
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.households_type = pdv6.nm" 
			+ " LEFT JOIN pub_dict_value pdv7 ON tt.master_relationship = pdv7.nm" 
			+ " LEFT JOIN pub_dict_value pdv8 ON tt.place_type = pdv8.nm" 
			+ " WHERE 1=1"
			+ " AND IF (:region is not null AND :region != '', REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:scope is not null AND :scope != '', tt.scope = :scope, 1=1)"
			+ " AND IF (:name is not null AND :name != '', ("
			+ " tt.name like CONCAT('%',:name,'%') OR tio.name like CONCAT('%',:name,'%')"
			+ " ), 1=1)"
			+ " AND IF (:idCard is not null AND :idCard != '', tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
			+ " AND IF (:isDirtyData IS NOT NULL AND :isDirtyData != '', IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
			+ " AND IF (:ownerNm is not null AND :ownerNm != '', tt.owner_nm = :ownerNm, 1=1)"
			, nativeQuery = true)
	List<InfoFamilyVO> list(@Param("region") String region,
			@Param("scope") String scope, 
			@Param("name") String name, 
			@Param("idCard") String idCard,
			@Param("isDirtyData") String isDirtyData,
			@Param("ownerNm") String ownerNm);


	List<InfoFamilyEntity> findAllByRegionInAndPlaceNameIsNotNull(List<String> regions);

	List<InfoFamilyEntity> findAllByPlaceTypeAndRegionIn(String placeType, List<String> regions);

	List<InfoFamilyEntity> findAllByPlaceTypeAndRegionInAndIsSatisfy(String placeType, List<String> regions,String isSatisfy);


	@Modifying
	@Query(value = " UPDATE `t_info_family` SET `master_relationship` = :relation WHERE `nm` = :nm AND id > 0 ", nativeQuery = true)
	void updateFamilyByNm(@Param("nm") String nm, @Param("relation") String relation);

	@Query(value = " SELECT fa.region, re.name AS regionName, d1.nm AS scope, d1.name AS scopeName, fa.name,   " +
			" fa.id_card AS idCard, fa.nm, fa.master_relationship AS relation, fa.owner_nm AS ownerNm, d2.name AS relationName  " +
			" FROM t_info_family fa  " +
			" LEFT JOIN t_info_owner ow ON fa.owner_nm = ow.nm " +
			" LEFT JOIN pub_region re ON fa.region = re.city_code  " +
			" LEFT JOIN pub_dict_value d1 ON IF(fa.scope is null, ow.scope, fa.scope) = d1.nm   " +
			" LEFT JOIN pub_dict_value d2 ON fa.master_relationship = d2.nm  " +
			" WHERE fa.owner_nm IN(:list)  ", nativeQuery = true)
	List<Map> queryFamilyListByOwnerNm(@Param("list") List<String> list);

	@Query(value = "SELECT id, nm, project_nm AS projectNm, region, scope, name, gender, age, zblx FROM t_info_family WHERE nm = :nm", nativeQuery = true)
	Map queryOwnerInfoByNm(@Param("nm") String nm);
	
	@Modifying
	@Query(value = "update t_info_family set age = :age where id = :id",nativeQuery = true)
	void updateAge(Integer age,Integer id);
	
	@Query(value = "select * from t_info_family where LENGTH(id_card) = 15",nativeQuery = true)
	List<InfoFamilyEntity> findIdCardLength15();
	
	@Query(value = "select * from t_info_family where LENGTH(id_card) = 18",nativeQuery = true)
	List<InfoFamilyEntity> findIdCardLength18();

}