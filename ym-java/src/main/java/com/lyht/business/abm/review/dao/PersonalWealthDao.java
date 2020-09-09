package com.lyht.business.abm.review.dao;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.review.entity.PersonalWealth;
import com.lyht.business.abm.review.vo.OwnerDocGetInfoVO;
import com.lyht.business.abm.review.vo.PersonaWealthIndividualVO;
import com.lyht.business.abm.review.vo.PersonalWealthDataVO;
import com.lyht.business.abm.review.vo.PersonalWealthGetByIdVO;
import com.lyht.business.abm.review.vo.PersonalWealthVO;

/**
 * 实物指标复核申请（个人财产）
 * 
 * @author wzw
 *
 */
@Repository
public interface PersonalWealthDao extends JpaRepository<PersonalWealth, Integer> {
	
	/**
	 * 修改复核记录次数 +1
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "update t_info_owner_impl set review_time = ifnull(review_time,0) + 1 where nm = ?1",nativeQuery = true)
	void updateReviewTime(String ownerNm);
	
	/**
	 * 修改复核记录次数上限
	 * @param nm
	 * @return
	 */
	@Modifying
	@Query(value = "update t_info_owner_impl set time_caps = :timeCaps where nm = :nm",nativeQuery = true)
	void updateTimeCaps(@Param("nm")String ownerNm,@Param("timeCaps")Integer timeCaps);

	PersonalWealth findByNm(String nm);

	/**
	 * 根据masterNm查询保存的Json数据 家庭成员
	 */
	@Query(value = " select immigrant_population_json jsonString,t1.review_state state  from t_abm_personal_wealth_data tt"
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ " where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getImmigrantPopulationJsonByMasterNm(@Param("masterNm") String masterNm);

	/**
	 * 根据masterNm查询保存的Json数据 土地
	 */
	@Query(value = "select land_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt"
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ " where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getLandJsonByMasterNm(@Param("masterNm") String masterNm);
	
	/**
	 * 根据masterNm查询保存的Json数据 土地
	 */
	@Query(value = "select agricultural_facilities_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt"
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ " where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getAgriculturalFacilitiesJsonByMasterNm(@Param("masterNm") String masterNm);
	
	/**
	 * 根据masterNm查询保存的Json数据 土地
	 */
	@Query(value = "select homestead_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt"
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ " where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getHomesteadJsonByMasterNm(@Param("masterNm") String masterNm);

	/**
	 * 根据masterNm查询保存的house_json数据 房屋数据json
	 */
	@Query(value = "select house_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt"
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ " where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getHouseJsonByMasterNm(@Param("masterNm") String masterNm);

	/**
	 * 根据masterNm查询保存的Json数据 零星树木json
	 */
	@Query(value = "select tree_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt"
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ " where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getTreeJsonByMasterNm(@Param("masterNm") String masterNm);

	/**
	 * 根据masterNm查询保存的Json数据 其他附属数据json
	 */
	@Query(value = "select other_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt "
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ "where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getOtherJsonByMasterNm(@Param("masterNm") String masterNm);

	/**
	 * 根据masterNm查询保存的Json数据 房屋装修json
	 */
	@Query(value = "select fitment_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt "
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ " where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getFitmentJsonByMasterNm(@Param("masterNm") String masterNm);

	/**
	 * 根据masterNm查询保存的Json数据 个体户json
	 */
	@Query(value = "select individual_household_json jsonString,t1.review_state state from t_abm_personal_wealth_data tt "
			+ " left join t_abm_personal_wealth t1 on tt.master_nm = t1.nm  "
			+ " left join t_bpm_process t2 on t1.change_process_id = t2.process_id "
			+ "where master_nm = :masterNm", nativeQuery = true)
	PersonalWealthDataVO getIndividualHouseholdJsonByMasterNm(@Param("masterNm") String masterNm);

	/**
	 * 根据id查询移民档案信息
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select tt.review_reason reviewReason,t1.name,t1.nm,t1.id_card idCard,t2.staff_name reviewer, "
			+ " tt.application_number applicationNumber,tt.review_project reviewProject,tt.scope, "
			+ " concat(t3.file_name,'.',t3.file_type) reviewFileName,t3.file_Url reviewFileUrl,"
			+ "concat(t4.file_name,'.',t4.file_type) changeFileName,t4.file_Url changeFileUrl from t_abm_personal_wealth tt  "
			+ " left join t_info_owner_impl t1 on tt.owner_nm = t1.nm   "
			+ " left join sys_staff t2 on tt.reviewer = t2.nm  "
			+ " left join pub_files t3 on tt.review_process_id = t3.table_pk_column "
			+ " left join pub_files t4 on tt.change_process_id = t4.table_pk_column "
			+ " where tt.id = :id ", nativeQuery = true)
	PersonalWealthGetByIdVO findPersonalWealthById(@Param("id") Integer id);

	/**
	 * 查询权属人个体户信息
	 * 
	 * @param ownerNm
	 * @param pageable
	 * @return
	 */
	@Query(value = "select tt.id,tt.nm,t2.city_code region,t2.merger_name mergerName,tt.`status`,tt.remark,tt.zblx,t3.name scopeName,t3.nm scope, "
			+ "t1.name ownerName,t1.nm ownerNm,tt.operator_name operatorName,tt.register_number registerNumber,tt.register_validity registerValidity, "
			+ "tt.id_card idCard,tt.phone_number phoneNumber,tt.premises,tt.lease_owner_nm leaseOwnerNm,t4.name leaseOwnerName,tt.industry_type_code industryTypeCode, "
			+ "tt.money_amount moneyAmount,tt.main_activity mainActivity,tt.practitioners,tt.oper_cond operCond,tt.competent_dept competentDept, "
			+ "tt.operating_area operatingArea,tt.operating_remork operatingRemork,tt.is_survey isSurvey,tt.altd,tt.in_map inMap,t5.name stageName,t5.nm stage, "
			+ "tt.type_name typeName,tt.lease_area leaseArea,tt.irs_register_number irsRegisterNumber,tt.tax_register_number taxRegisterNumber, "
			+ "tt.operator_abode operatorAbode,tt.opening_years openingYears,tt.reasons_remork reasonsRemork,tt.business_scope businessScope, "
			+ "t6.name indiTypeName,t6.nm indiType,tt.brick,tt.blender,tt.crusher,tt.move_material_num moveMaterialNum,tt.move_vehicle_number moveVehicleNumber, "
			+ "tt.move_material_money moveMaterialMoney,tt.closure_assistance_area closureAssistanceArea,tt.closure_assistance_money closureAssistanceMoney,tt.compensation_amount compensationAmount, "
			+ "(select count(1) from pub_files where table_pk_column = tt.nm) as fileCount from t_info_individual_impl tt left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ "left join pub_region t2 on tt.region = t2.city_code "
			+ "left join pub_dict_value t3 on tt.scope = t3.nm "
			+ "left join t_info_owner_impl t4 on tt.lease_owner_nm = t4.nm  "
			+ "left join pub_dict_value t5 on tt.stage = t5.nm "
			+ "left join pub_dict_value t6 on tt.indi_type = t6.nm "
			+ "where tt.owner_nm = :ownerNm ", nativeQuery = true)
	List<Map<String, Object>> findPersonaWealthIndividual(@Param("ownerNm") String ownerNm);

	/**
	 * 查询权属人个体户信息
	 * 
	 * @param ownerNm
	 * @param pageable
	 * @return
	 */
	@Query(value = "select tt.id,tt.nm,t2.city_code region,t2.merger_name mergerName,tt.`status`,tt.remark,tt.zblx,t3.name scopeName,t3.nm scope, "
			+ "t1.name ownerName,t1.nm ownerNm,tt.operator_name operatorName,tt.register_number registerNumber,tt.register_validity registerValidity, "
			+ "tt.id_card idCard,tt.phone_number phoneNumber,tt.premises,tt.lease_owner_nm leaseOwnerNm,t4.name leaseOwnerName,tt.industry_type_code industryTypeCode, "
			+ "tt.money_amount moneyAmount,tt.main_activity mainActivity,tt.practitioners,tt.oper_cond operCond,tt.competent_dept competentDept, "
			+ "tt.operating_area operatingArea,tt.operating_remork operatingRemork,tt.is_survey isSurvey,tt.altd,tt.in_map inMap,t5.name stageName,t5.nm stage, "
			+ "tt.type_name typeName,tt.lease_area leaseArea,tt.irs_register_number irsRegisterNumber,tt.tax_register_number taxRegisterNumber, "
			+ "tt.operator_abode operatorAbode,tt.opening_years openingYears,tt.reasons_remork reasonsRemork,tt.business_scope businessScope, "
			+ "t6.name indiTypeName,t6.nm indiType,tt.brick,tt.blender,tt.crusher,tt.move_material_num moveMaterialNum,tt.move_vehicle_number moveVehicleNumber, "
			+ "tt.move_material_money moveMaterialMoney,tt.closure_assistance_area closureAssistanceArea,tt.closure_assistance_money closureAssistanceMoney,tt.compensation_amount compensationAmount "
			+ "from t_info_individual_impl tt  " + "left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
			+ "left join pub_region t2 on tt.region = t2.city_code "
			+ "left join pub_dict_value t3 on tt.scope = t3.nm "
			+ "left join t_info_owner_impl t4 on tt.lease_owner_nm = t4.nm  "
			+ "left join pub_dict_value t5 on tt.stage = t5.nm "
			+ "left join pub_dict_value t6 on tt.indi_type = t6.nm "
			+ "where tt.owner_nm = :ownerNm ", countQuery = "select count(1) from (select tt.id,tt.nm,t2.city_code region,t2.merger_name mergerName,tt.`status`,tt.remark,tt.zblx,t3.name scopeName,t3.nm scope, "
					+ "t1.name ownerName,t1.nm ownerNm,tt.operator_name operatorName,tt.register_number registerNumber,tt.register_validity registerValidity, "
					+ "tt.id_card idCard,tt.phone_number phoneNumber,tt.premises,tt.lease_owner_nm leaseOwnerNm,t4.name leaseOwnerName,tt.industry_type_code industryTypeCode, "
					+ "tt.money_amount moneyAmount,tt.main_activity mainActivity,tt.practitioners,tt.oper_cond operCond,tt.competent_dept competentDept, "
					+ "tt.operating_area operatingArea,tt.operating_remork operatingRemork,tt.is_survey isSurvey,tt.altd,tt.in_map inMap,t5.name stageName,t5.nm stage, "
					+ "tt.type_name typeName,tt.lease_area leaseArea,tt.irs_register_number irsRegisterNumber,tt.tax_register_number taxRegisterNumber, "
					+ "tt.operator_abode operatorAbode,tt.opening_years openingYears,tt.reasons_remork reasonsRemork,tt.business_scope businessScope, "
					+ "t6.name indiTypeName,t6.nm indiType,tt.brick,tt.blender,tt.crusher,tt.move_material_num moveMaterialNum,tt.move_vehicle_number moveVehicleNumber, "
					+ "tt.move_material_money moveMaterialMoney,tt.closure_assistance_area closureAssistanceArea,tt.closure_assistance_money closureAssistanceMoney,tt.compensation_amount compensationAmount "
					+ "from t_info_individual_impl tt  " + "left join t_info_owner_impl t1 on tt.owner_nm = t1.nm  "
					+ "left join pub_region t2 on tt.region = t2.city_code "
					+ "left join pub_dict_value t3 on tt.scope = t3.nm "
					+ "left join t_info_owner_impl t4 on tt.lease_owner_nm = t4.nm  "
					+ "left join pub_dict_value t5 on tt.stage = t5.nm "
					+ "left join pub_dict_value t6 on tt.indi_type = t6.nm "
					+ "where tt.owner_nm = :ownerNm ) as t0", nativeQuery = true)
	Page<PersonaWealthIndividualVO> findPersonaWealthIndividual(@Param("ownerNm") String ownerNm, Pageable pageable);

	/**
	 * 查询移民档案信息
	 */
	@Query(value = " SELECT b.NAME AS ownerName,b.nm ownerNm,pf.merger_name AS mergerName,pf.city_code cityCode,(SELECT IFNULL(COUNT(1), 0) FROM t_info_family_impl AS c  "
			+ " WHERE c.owner_nm = b.nm and c.is_satisfy = 2 ) AS familyCount,(SELECT	IFNULL(COUNT(1), 0)	FROM t_info_family_impl	WHERE owner_nm = b.nm	) AS population,  "
			+ " (SELECT IFNULL(SUM(area), 0) AS population FROM t_info_houses_impl WHERE owner_nm = b.nm) AS houseArea,  "
			+ " (SELECT IFNULL(SUM(area), 0) AS population FROM t_info_houses_decoration_impl	WHERE owner_nm = b.nm) AS houseDecorationArea,  "
			+ " (SELECT IFNULL(SUM(area), 0) AS population FROM t_info_land_impl WHERE owner_nm = b.nm) AS landArea, "
			+ " (select review_project from t_abm_personal_wealth where owner_nm = b.nm order BY review_time desc limit 1) reviewProject, "
			+ " b.id_card idCard,tt.name scopeName,b.scope,b.national,t1.name nationalName,b.gender,b.households_home householdsHome,b.households_type householdsType, "
			+ " b.lgtd,b.lttd,b.in_map inMap,b.altd, "
			+ " (select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = (select review_process_id from t_abm_personal_wealth where owner_nm = b.nm order BY review_time desc limit 1)) reviewFileName,  "
			+ " (select file_url from pub_files where table_pk_column = (select review_process_id from t_abm_personal_wealth where owner_nm = b.nm order BY review_time desc limit 1)) reviewFileUrl,  "
			+ " (select CONCAT(file_name,'.',file_type) from pub_files where table_pk_column = (select change_process_id from t_abm_personal_wealth where owner_nm = b.nm order BY review_time desc limit 1)) changeFileName,  "
			+ " (select file_url from pub_files where table_pk_column = (select change_process_id from t_abm_personal_wealth where owner_nm = b.nm order BY review_time desc limit 1)) changeFileUrl  "
			+ " FROM	`t_info_owner_impl` b LEFT JOIN pub_region AS pf ON pf.city_code = b.region  "
			+ " left join pub_dict_value tt on b.scope = tt.nm left join pub_dict_value t1 on b.national = t1.nm  "
			+ " WHERE b.nm = :ownerNm", nativeQuery = true)
	public OwnerDocGetInfoVO getOwnerDocInfo(@Param("ownerNm") String ownerNm);

	/**
	 * 根据复核流程id查询复核记录
	 * 
	 * @param reviewProcessId
	 * @return
	 */
	public PersonalWealth findByReviewProcessId(String reviewProcessId);

	/**
	 * 根据变更流程id查询复核记录
	 * 
	 * @param ownerNm
	 * @return
	 */
	public PersonalWealth findByChangeProcessId(String changeProcessId);

	/**
	 * 查询权属人姓名
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "select name from t_info_owner_impl where nm = :ownerNm", nativeQuery = true)
	public String getOwnerName(@Param("ownerNm") String ownerNm);

	/**
	 * 将总复核状态更新
	 * 
	 * @param ownerNm
	 * @param reviewState
	 * @return
	 */
	@Modifying
	@Query(value = "update t_info_owner_impl set fh_state = :reviewState where nm = :ownerNm", nativeQuery = true)
	public Integer updateFhStateOnOwner(@Param("ownerNm") String ownerNm, @Param("reviewState") Integer reviewState);
	
	@Modifying
	@Query(value = "update t_info_owner_impl set fh_state = :reviewState where id = :id", nativeQuery = true)
	public Integer updateFhStateOnOwnerById(@Param("id") Integer id, @Param("reviewState") Integer reviewState);

	/**
	 * 将总复核状态更新 重新发起复核时 权属人公式总状态 更新为0
	 * 
	 * @param ownerNm
	 * @param reviewState
	 * @return
	 */
	@Modifying
	@Query(value = "update t_info_owner_impl set fh_state = :reviewState,gs_state = 0 where nm = :ownerNm", nativeQuery = true)
	public Integer updateFhStateGsStateOnOwner(@Param("ownerNm") String ownerNm,
			@Param("reviewState") Integer reviewState);
	
	/**
	 * 将总复核状态更新 重新发起复核时 权属人公式总状态 更新为0
	 * 
	 * @param ownerNm
	 * @param reviewState
	 * @return
	 */
	@Modifying
	@Query(value = "update t_abm_personal_wealth set review_State = :reviewState where id = :id", nativeQuery = true)
	public Integer updateReviewState(@Param("id") Integer id,
			@Param("reviewState") Integer reviewState);

	/**
	 * 查询到当日最大流水号
	 * 
	 * @return
	 */
	@Query(value = "select application_number from t_abm_personal_wealth where date_format(review_time,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d') order by review_time desc limit 1", nativeQuery = true)
	public String findTheLastId();

	/**
	 * 查询权属人最新复核申请
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "select * from t_abm_personal_wealth where owner_nm = :ownerNm order by review_time desc limit 1", nativeQuery = true)
	public PersonalWealth findTheLastInfoByOwnerNm(@Param("ownerNm") String ownerNm);

	@Query(value = " select * from (select tt.id,tt.nm,t1.merger_name mergerName,t1.city_code AS region,tt.name,tt.id_card idCard,t2.name national,t3.name householdsType, "
			+ " t4.name scopeName,t4.nm scope,IFNULL(tt.fh_state,0) reviewState,"
			+ " (select id from t_abm_personal_wealth where owner_nm = tt.nm order BY review_time desc limit 1) reviewId,"
			+ " (select review_process_id from t_abm_personal_wealth where owner_nm = tt.nm order BY review_time desc limit 1) reviewProcessId,"
			+ " (select change_process_id from t_abm_personal_wealth where owner_nm = tt.nm order BY review_time desc limit 1) changeProcessId,"
			+ " (select nm from t_abm_personal_wealth where owner_nm = tt.nm order BY review_time desc limit 1) masterNm,tt.split_household_state as splitHouseholdState,"
			+ " (select count(1) from t_info_family_impl where owner_nm = tt.nm) as iPopulation,"
			+ "   tt.gs_state gsState,if(tt.review_time = tt.time_caps,'false','true') AS whether,tt.time_caps AS timeCaps,"
			+ " if(ts.house_status=1 or ts.house_decoration_status=1 or ts.building_status=1 or ts.agricultural_facilities_status=1 or ts.trees_status=1 "
			+ "  or ts.individual_status=1 or ts.relocation_allowance_status=1 or ts.other_status=1 or ts.difficult_status=1 or ts.infrastructure_status=1 or ts.homestead_status=1 "
			+ " or ts.levy_land_status=1 or ts.young_crops_status=1,1,0) as protocolStatus,"
			+ "(select tbp.status from t_abm_split_household tash "
			+ "	left join t_bpm_process tbp on tash.process_id = tbp.process_id "
			+ "	where tash.owner_nm = tt.nm order by tash.create_time desc limit 1 ) as splitStatus, "
			+ "	(select tbp.cn_status AS cnStatus from t_abm_split_household tash "
			+ "	left join t_bpm_process tbp on tash.process_id = tbp.process_id "
			+ "	where tash.owner_nm = tt.nm order by tash.create_time desc limit 1 ) as splitCnStatus ,IFNULL(t5.protocol_state,0)  protocolState"
			+ " from t_info_owner_impl tt left join pub_region t1 on tt.region = t1.city_code "
			+ " left join pub_dict_value t2 on tt.national = t2.nm "
			+ " left join pub_dict_value t3 on tt.households_type = t3.nm "
			+ " left join pub_dict_value t4 on tt.scope = t4.nm"
			+ " LEFT JOIN t_abm_total_state ts ON tt.nm = ts.owner_nm "
			+ " left join t_abm_total_state t5 on tt.nm = t5.owner_nm where 1 = 1"
			+ " AND IF(:region IS NOT NULL AND :region != '', t1.merger_name like CONCAT('%',:region,'%'), 1=1) "
			+ " AND IF(:name IS NOT NULL AND :name != '', tt.name like CONCAT('%',:name,'%'), 1=1) "
			+ " AND IF(:scope IS NOT NULL AND :scope != '', t4.nm = :scope , 1=1 ) "
			+ " AND IF(:ownerNm IS NOT NULL AND :ownerNm != '', tt.nm = :ownerNm , 1=1 )"
			+ " AND IF(:idCard IS NOT NULL AND :idCard != '', tt.id_card like CONCAT('%',:idCard,'%'), 1=1)) as t0  "
			+ " ORDER BY (case when t0.protocolState = 1&&t0.reviewState = 0 then 7  when t0.reviewState = 3 then 1 when t0.reviewState = -4 then 2 when t0.reviewState = 2 then 3 when t0.reviewState = 1 then 4  "
			+ " when t0.reviewState = 0 then 5 else 6 end)  ", countQuery = " select count(1) from ( select tt.nm,t1.merger_name mergerName,tt.name,tt.id_card idCard,t2.name national,t3.name householdsType, "
					+ " t4.name scope,t4.nm scopeNm,IFNULL((select review_state from t_abm_personal_wealth where owner_nm = tt.nm order BY review_time desc limit 1),0) reviewState"
					+ " from t_info_owner_impl tt left join pub_region t1 on tt.region = t1.city_code "
					+ " left join pub_dict_value t2 on tt.national = t2.nm "
					+ " left join pub_dict_value t3 on tt.households_type = t3.nm "
					+ " left join pub_dict_value t4 on tt.scope = t4.nm "
					+ " left join t_abm_total_state t5 on tt.nm = t5.owner_nm where 1 = 1 "
					+ " AND IF(:region IS NOT NULL AND :region != '', t1.merger_name like CONCAT('%',:region,'%'), 1=1) "
					+ " AND IF(:name IS NOT NULL AND :name != '', tt.name like CONCAT('%',:name,'%'), 1=1) "
					+ " AND IF(:scope IS NOT NULL AND :scope != '', t4.nm = :scope , 1=1 ) "
					+ " AND IF(:ownerNm IS NOT NULL AND :ownerNm != '', tt.nm = :ownerNm , 1=1 )"
					+ " AND IF(:idCard IS NOT NULL AND :idCard != '', tt.id_card like CONCAT('%',:idCard,'%'), 1=1) "
					+ " ORDER BY tt.id) as t0 ", nativeQuery = true)
	public Page<PersonalWealthVO> page(@Param("region") String region, @Param("name") String name,
			@Param("scope") String scope, @Param("idCard") String idCard,@Param("ownerNm")String nm ,Pageable pageable);

}
