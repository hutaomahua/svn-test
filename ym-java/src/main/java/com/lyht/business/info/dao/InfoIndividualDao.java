package com.lyht.business.info.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoIndividualEntity;
import com.lyht.business.info.vo.InfoIndividualVO;

public interface InfoIndividualDao extends JpaRepository<InfoIndividualEntity, Integer> {

	/**
	 * 分页查询
	 * @param region
	 * @param operatorName
	 * @param idCard
	 * @param isDirtyData 
	 * @param ownerNm 
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT " 
			+ " tt.id AS id, tt.nm AS nm, tt.project_nm AS projectNm,"
			+ " tt.region AS region, tt.scope AS scope, tt.zblx AS zblx,"
			+ " tt.owner_nm AS ownerNm, tt.type_name AS typeName,"
			+ " tt.indi_type AS indiType, tt.register_number AS registerNumber,"
			+ " tt.register_validity AS registerValidity, tt.operator_name AS operatorName,"
			+ " tt.id_card AS idCard, tt.phone_number AS phoneNumber,"
			+ " tt.premises AS premises, tt.lease_owner_nm AS leaseOwnerNm,"
			+ " tt.lease_area AS leaseArea, tt.industry_type_code AS industryTypeCode,"
			+ " tt.money_amount AS moneyAmount, tt.main_activity AS mainActivity,"
			+ " tt.practitioners AS practitioners, tt.oper_cond AS operCond,"
			+ " tt.competent_dept AS competentDept, tt.operating_area AS operatingArea,"
			+ " tt.operating_remork AS operatingRemork, tt.is_survey AS isSurvey,"
			+ " tt.irs_register_number AS irsRegisterNumber, tt.tax_register_number AS taxRegisterNumber,"
			+ " tt.operator_abode AS operatorAbode, tt.opening_years AS openingYears,"
			+ " tt.reasons_remork AS reasonsRemork, tt.business_scope AS businessScope,"
			+ " tt.brick AS brick, tt.blender AS blender,"
			+ " tt.crusher AS crusher, tt.move_material_num AS moveMaterialNum,"
			+ " tt.move_vehicle_number AS moveVehicleNumber, tt.move_material_money AS moveMaterialMoney,"
			+ " tt.closure_assistance_area AS closureAssistanceArea, tt.closure_assistance_money AS closureAssistanceMoney,"
			+ " tt.compensation_amount AS compensationAmount, tt.altd AS altd,"
			+ " tt.in_map AS inMap,"
			+ " tt.status AS status, tt.stage AS stage,"
			+ " tt.create_time AS createTime, tt.create_user AS createUser,"
			+ " tt.modify_time AS modifyTime, tt.modify_user AS modifyUser,"
			+ " tt.remark AS remark,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue,"
			+ " tpp.name AS projectValue, tio.name AS ownerValue,"
			+ " pdv1.name AS zblxValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS scopeValue, pdv4.name AS indiTypeValue,count(file.id) AS fileCount "
			+ " FROM t_info_individual tt" 
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
			+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.indi_type = pdv4.nm" +
			" LEFT JOIN pub_files file ON file.table_name = 't_info_individual' AND file.table_pk_column = tt.nm "
			+ " WHERE 1=1"
			+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
			+ " AND IF (:operatorName is not null, tt.operator_name like CONCAT('%',:operatorName,'%'), 1=1)"
			+ " AND IF (:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
			+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
			+ " AND IF (:ownerNm is not null, tt.owner_nm = :ownerNm, 1=1) GROUP BY tt.id "
			, countQuery = " SELECT"
					+ " COUNT(1)"
					+ " FROM t_info_individual tt" 
					+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_project tpp ON tt.project_nm = tpp.id"
					+ " LEFT JOIN t_info_owner tio ON tt.owner_nm = tio.nm"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.zblx = pdv1.nm" 
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm" 
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.scope = pdv3.nm" 
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.indi_type = pdv4.nm" 
					+ " WHERE 1=1"
					+ " AND IF (:region is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:region,'%'), 1=1)"
					+ " AND IF (:operatorName is not null, tt.operator_name like CONCAT('%',:operatorName,'%'), 1=1)"
					+ " AND IF (:idCard is not null, tt.id_card like CONCAT('%',:idCard,'%'), 1=1)"
					+ " AND IF (:isDirtyData IS NOT NULL, IF(:isDirtyData = '0', tio.name IS NOT NULL, tio.name IS NULL), 1=1)"
					+ " AND IF (:ownerNm is not null, tt.owner_nm = :ownerNm, 1=1)"
					, nativeQuery = true)
	Page<InfoIndividualVO> page(@Param("region") String region,
			@Param("operatorName") String operatorName, 
			@Param("idCard") String idCard,
			@Param("isDirtyData") String isDirtyData, 
			@Param("ownerNm") String ownerNm, Pageable pageable);
	
}