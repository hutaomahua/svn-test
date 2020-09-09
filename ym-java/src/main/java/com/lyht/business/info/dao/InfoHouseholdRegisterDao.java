package com.lyht.business.info.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoHouseholdRegisterEntity;
import com.lyht.business.info.vo.InfoHouseholdRegisterVO;

public interface InfoHouseholdRegisterDao extends JpaRepository<InfoHouseholdRegisterEntity, String> {

	@Query(value = "SELECT"
			+ " tt.id AS id, tt.region AS region,"
			+ " tt.owner_id AS ownerId, tt.name AS name,"
			+ " tt.country AS country, tt.village AS village, tt.groups AS groups,"
			+ " tt.master_relationship AS masterRelationship, tt.gender AS gender,"
			+ " tt.birthday AS birthday, tt.id_card AS idCard,"
			+ " tt.account_character AS accountCharacter, tt.account_type AS accountType,"
			+ " tt.national AS national, tt.age AS age,"
			+ " tt.education_level AS educationLevel, tt.living_condition AS livingCondition,"
			+ " tt.home_address AS homeAddress, tt.status AS status,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser,"
			+ " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tihr.name AS ownerName, tihr.id_card AS ownerIdCard,"
			+ " pdv1.name AS masterRelationshipValue, pdv2.name AS accountCharacterValue,"
			+ " pdv3.name AS accountTypeValue, pdv4.name AS nationalValue,"
			+ " pdv5.name AS educationLevelValue"
			+ " FROM t_info_household_register tt"
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN t_info_household_register tihr ON tt.owner_id = tihr.id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.master_relationship = pdv1.nm" 
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.account_character = pdv2.nm" 
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.account_type = pdv3.nm" 
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.national = pdv4.nm" 
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.education_level = pdv5.nm" 
			+ " LEFT JOIN t_info_owner_impl tioi ON tt.id_card = tioi.id_card" 
			+ " LEFT JOIN t_info_family_impl tifi ON tt.id_card = tifi.id_card" 
			+ " WHERE 1=1"
			+ " AND tt.status IS NULL"
			+ " AND tioi.id IS NULL"
			+ " AND tifi.id IS NULL"
			+ " AND IF (:region is not null AND :region != '', REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:name is not null AND :name != '', ("
			+ " tt.name like CONCAT('%',:name,'%') OR tihr.name like CONCAT('%',:name,'%')"
			+ " ), 1=1)"
			+ " AND IF (:idCard is not null AND :idCard != '', ("
			+ " tt.id_card like CONCAT('%',:idCard,'%') OR tihr.id_card like CONCAT('%',:idCard,'%')"
			+ " ), 1=1)"
			+ " AND IF (:accountCharacter is not null AND :accountCharacter != '', tt.account_character = :accountCharacter, 1=1)"
			+ " AND IF (:accountType is not null AND :accountType != '', tt.account_type = :accountType, 1=1)"
			+ " AND IF (:livingCondition is not null AND :livingCondition != '', tt.living_condition = :livingCondition, 1=1)"
			+ " AND IF (:masterRelationship is not null AND :masterRelationship != '', tt.master_relationship = :masterRelationship, 1=1)"
			+ " AND IF (:country is not null AND :country != '', tt.country like CONCAT('%',:country,'%'), 1=1)"
			+ " AND IF (:village is not null AND :village != '', tt.village like CONCAT('%',:village,'%'), 1=1)"
			+ " AND IF (:groups is not null AND :groups != '', tt.groups like CONCAT('%',:groups,'%'), 1=1)"
			, countQuery = "SELECT"
					+ " COUNT(1)"
					+ " FROM t_info_household_register tt"
					+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN t_info_household_register tihr ON tt.owner_id = tihr.id"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.master_relationship = pdv1.nm" 
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.account_character = pdv2.nm" 
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.account_type = pdv3.nm" 
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.national = pdv4.nm" 
					+ " LEFT JOIN pub_dict_value pdv5 ON tt.education_level = pdv5.nm"
					+ " LEFT JOIN t_info_owner_impl tioi ON tt.id_card = tioi.id_card" 
					+ " LEFT JOIN t_info_family_impl tifi ON tt.id_card = tifi.id_card" 
					+ " WHERE 1=1"
					+ " AND tt.status IS NULL"
					+ " AND tioi.id IS NULL"
					+ " AND tifi.id IS NULL"
					+ " AND IF (:region is not null AND :region != '', REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:name is not null AND :name != '', ("
					+ " tt.name like CONCAT('%',:name,'%') OR tihr.name like CONCAT('%',:name,'%')"
					+ " ), 1=1)"
					+ " AND IF (:idCard is not null AND :idCard != '', ("
					+ " tt.id_card like CONCAT('%',:idCard,'%') OR tihr.id_card like CONCAT('%',:idCard,'%')"
					+ " ), 1=1)"
					+ " AND IF (:accountCharacter is not null AND :accountCharacter != '', tt.account_character = :accountCharacter, 1=1)"
					+ " AND IF (:accountType is not null AND :accountType != '', tt.account_type = :accountType, 1=1)"
					+ " AND IF (:livingCondition is not null AND :livingCondition != '', tt.living_condition = :livingCondition, 1=1)"
					+ " AND IF (:masterRelationship is not null AND :masterRelationship != '', tt.master_relationship = :masterRelationship, 1=1)"
					+ " AND IF (:country is not null AND :country != '', tt.country like CONCAT('%',:country,'%'), 1=1)"
					+ " AND IF (:village is not null AND :village != '', tt.village like CONCAT('%',:village,'%'), 1=1)"
					+ " AND IF (:groups is not null AND :groups != '', tt.groups like CONCAT('%',:groups,'%'), 1=1)"
			, nativeQuery = true)
	Page<InfoHouseholdRegisterVO> page(@Param("region") String region,
			@Param("name") String name,
			@Param("idCard") String idCard,
			@Param("accountCharacter") String accountCharacter,
			@Param("accountType") String accountType,
			@Param("livingCondition") String livingCondition,
			@Param("masterRelationship") String masterRelationship,
			@Param("country") String country,
			@Param("village") String village,
			@Param("groups") String groups,
			Pageable pageable);
	
	List<InfoHouseholdRegisterEntity> findByOwnerId(String ownerId);
	
	@Query(value = "SELECT"
			+ " tt.*"
			+ " FROM t_info_household_register tt"
			+ " WHERE 1=1"
			+ " AND tt.owner_id IS NULL"
			+ " AND IF (:name IS NOT NULL, tt.name LIKE CONCAT('%',:name,'%'), 1=1)", nativeQuery = true)
	List<InfoHouseholdRegisterEntity> findOwnerListByName(@Param("name") String name);
}