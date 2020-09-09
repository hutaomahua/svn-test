package com.lyht.business.abm.removal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmAgriculturalFacilitiesEntity;
import com.lyht.business.abm.removal.vo.AbmAgriculturalFacilitiesVO;

public interface AbmAgriculturalFacilitiesDao extends JpaRepository<AbmAgriculturalFacilitiesEntity, Integer> {

	/**
	 * 
	 * @param ownerName
	 * @return
	 */
	@Query(value = "SELECT tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.zblx AS zblx,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount ,"
			+ " tt.owner_nm AS ownerNm, tt.specifications AS specifications, tt.unit AS unit, tt.longs AS longs,"
			+ " tt.width AS width, tt.height AS height," + " tt.area AS area, tt.volume AS volume,"
			+ " tt.num AS num, tt.lgtd AS lgtd," + " tt.lttd AS lttd, tt.altd AS altd,"
			+ " tt.in_map AS inMap, tt.owner_nature AS ownerNature," + " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser," + " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS mergerName,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue," + " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS ownerNatureValue,"
			+ " pdv5.name AS specificationsValue, pdv6.name AS unitValue"
			+ " FROM t_info_agricultural_facilities_impl tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm"
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.specifications = pdv5.nm"
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.unit = pdv6.nm WHERE tio.nm =:ownerName"
			, nativeQuery = true)
	List<AbmAgriculturalFacilitiesVO> list(@Param("ownerName") String ownerName);
	
	/**
	 * 
	 * @param ownerName
	 * @return
	 */
	@Query(value = "SELECT tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.zblx AS zblx,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount ,"
			+ " tt.owner_nm AS ownerNm, tt.specifications AS specifications, tt.unit AS unit, tt.longs AS longs,"
			+ " tt.width AS width, tt.height AS height," + " tt.area AS area, tt.volume AS volume,"
			+ " tt.num AS num, tt.lgtd AS lgtd," + " tt.lttd AS lttd, tt.altd AS altd,"
			+ " tt.in_map AS inMap, tt.owner_nature AS ownerNature," + " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser," + " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS mergerName,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue," + " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS ownerNatureValue,"
			+ " pdv5.name AS specificationsValue, pdv6.name AS unitValue"
			+ " FROM t_info_agricultural_facilities_impl tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm"
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.specifications = pdv5.nm"
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.unit = pdv6.nm WHERE tio.nm =:ownerName"
			, nativeQuery = true)
	List<Map<String,Object>> findByOwnerNmAgriculturalFacilities(@Param("ownerName") String ownerName);
	
	
	/**
	 * 分页
	 * 
	 * @param region
	 * @param ownerName
	 * @param scope
	 * @param projectFcode
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.zblx AS zblx,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount ,"
			+ " tt.owner_nm AS ownerNm, tt.specifications AS specifications, tt.unit AS unit, tt.longs AS longs,"
			+ " tt.width AS width, tt.height AS height," + " tt.area AS area, tt.volume AS volume,"
			+ " tt.num AS num, tt.lgtd AS lgtd," + " tt.lttd AS lttd, tt.altd AS altd,"
			+ " tt.in_map AS inMap, tt.owner_nature AS ownerNature," + " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser," + " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS mergerName,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue," + " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS ownerNatureValue,"
			+ " pdv5.name AS specificationsValue, pdv6.name AS unitValue"
			+ " FROM t_info_agricultural_facilities_impl tt  LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm"
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.specifications = pdv5.nm"
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.unit = pdv6.nm WHERE tio.nm =:ownerName"
			,countQuery = "select count(1) from (SELECT tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
					+ " tt.region AS region, tt.scope AS scope, tt.zblx AS zblx,(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount ,"
					+ " tt.owner_nm AS ownerNm, tt.specifications AS specifications, tt.unit AS unit, tt.longs AS longs,"
					+ " tt.width AS width, tt.height AS height," + " tt.area AS area, tt.volume AS volume,"
					+ " tt.num AS num, tt.lgtd AS lgtd," + " tt.lttd AS lttd, tt.altd AS altd,"
					+ " tt.in_map AS inMap, tt.owner_nature AS ownerNature," + " tt.status AS status, tt.stage AS stage,"
					+ " tt.create_time AS createTime, tt.create_user AS createUser,"
					+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser," + " tt.remark AS remark,"
					+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS mergerName,"
					+ " tpp.name AS projectValue, tio.name AS ownerValue," + " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
					+ " pdv3.name AS scopeValue, pdv4.name AS ownerNatureValue,"
					+ " pdv5.name AS specificationsValue, pdv6.name AS unitValue"
					+ " FROM t_info_agricultural_facilities_impl tt  LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
					+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm"
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm"
					+ " LEFT JOIN pub_dict_value pdv5 ON tt.specifications = pdv5.nm"
					+ " LEFT JOIN pub_dict_value pdv6 ON tt.unit = pdv6.nm WHERE tio.nm =:ownerName) as temp", nativeQuery = true)
	Page<AbmAgriculturalFacilitiesVO> page(@Param("ownerName") String ownerName,Pageable pageable);
	
	
	List<AbmAgriculturalFacilitiesEntity> findByOwnerNm(String ownerNm);

}