package com.lyht.business.info.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoAgriculturalFacilitiesEntity;
import com.lyht.business.info.vo.InfoAgriculturalFacilitiesVO;

public interface InfoAgriculturalFacilitiesDao extends JpaRepository<InfoAgriculturalFacilitiesEntity, Integer> {

	/**
	 * 分页
	 * @param region
	 * @param ownerName
	 * @param scope
	 * @param projectFcode
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT " 
			+ " tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.zblx AS zblx,"
			+ " tt.owner_nm AS ownerNm, tt.specifications AS specifications,"
			+ " tt.unit AS unit, tt.longs AS longs,"
			+ " tt.width AS width, tt.height AS height,"
			+ " tt.area AS area, tt.volume AS volume,"
			+ " tt.num AS num, tt.lgtd AS lgtd,"
			+ " tt.lttd AS lttd, tt.altd AS altd,"
			+ " tt.in_map AS inMap, tt.owner_nature AS ownerNature,"
			+ " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser,"
			+ " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue,"
			+ " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS ownerNatureValue,"
			+ " pdv5.name AS specificationsValue, pdv6.name AS unitValue,count(file.id) AS fileCount "
			+ " FROM t_info_agricultural_facilities tt" 
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm" 
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.specifications = pdv5.nm" 
			+ " LEFT JOIN pub_dict_value pdv6 ON tt.unit = pdv6.nm" +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_agricultural_facilities' AND file.table_pk_column = tt.nm "
			+ " WHERE 1=1"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:ownerName is not null, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
			+ " AND IF (:scope is not null, tt.scope = :scope, 1=1)"
			+ " AND IF (:projectFcode is not null, tpp.fcode like CONCAT(:projectFcode,'%'), 1=1)"
			+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1) GROUP BY tt.id "
			, countQuery = " SELECT"
					+ " COUNT(1)"
					+ " FROM t_info_agricultural_facilities tt" 
					+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
					+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.owner_nature = pdv4.nm" 
					+ " LEFT JOIN pub_dict_value pdv5 ON tt.specifications = pdv5.nm" 
					+ " LEFT JOIN pub_dict_value pdv6 ON tt.unit = pdv6.nm" 
					+ " WHERE 1=1"
					+ " AND IF (:region IS NOT NULL, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:ownerName IS NOT NULL, tio.name like CONCAT('%',:ownerName,'%'), 1=1)"
					+ " AND IF (:scope IS NOT NULL, tt.scope = :scope, 1=1)"
					+ " AND IF (:projectFcode IS NOT NULL, tpp.fcode like CONCAT(:projectFcode,'%'), 1=1)"
					+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
					, nativeQuery = true)
	Page<InfoAgriculturalFacilitiesVO> page(@Param("region") String region,
			@Param("ownerName") String ownerName, 
			@Param("scope") String scope, 
			@Param("projectFcode") String projectFcode, 
			@Param("isDirtyData") String isDirtyData, 
			Pageable pageable);
	
}