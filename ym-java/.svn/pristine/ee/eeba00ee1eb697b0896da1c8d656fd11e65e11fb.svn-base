package com.lyht.business.engineering.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.engineering.entity.EngineeringSinkBottom;
import com.lyht.business.engineering.vo.EngineeringSinkBottomVO;

public interface EngineeringSinkBottomDao extends JpaRepository<EngineeringSinkBottom, Integer>{

	/**
	 * 分页查询 条件查询
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT tt.id,tt.nm,t1.nm oneLevel,t1.name oneLevelName,t2.nm twoLevel,t2.name twoLevelName,   " + 
			"t3.nm threeLevel,t3.name threeLevelName,t4.nm projectNm,t4.name projectName, " + 
			"t5.city_code region,t5.merger_name mergerName,t5.name regionName,tt.plan_quantities planQuantities,   " + 
			"tt.plan_budget planBudget,tt.responsible_unit responsibleUnit,tt.construction_budget constructionBudget, " + 
			"if(tt.is_replace=1,'是','否') isReplace,tt.replace_unit replaceUnit,tt.design_unit designUnit,   " + 
			"tt.build_unit buildUnit,tt.is_replace `replace`,tt.supervisor_unit supervisorUnit,tt.plan_ownership_number planOwnershipNumber,   " + 
			"tt.coordinate,tt.project_ownership_number projectOwnershipNumber,tt.remark,t6.nm createStaff,   " + 
			"t6.staff_name createStaffName,tt.create_time crateTime,t7.staff_name updateStaffName,tt.update_time updateTime,  " + 
			"(select progress_evaluate from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) progressEvaluate, " + 
			"(CASE (select implement_status from t_engineering_evaluate where engineering_nm = tt.nm  " + 
			"ORDER BY Implement_info_date DESC limit 1) WHEN 0 THEN '未启动' WHEN 1 THEN '正在施工' WHEN 2 THEN '已竣工' END) implementStatus, " + 
			"(select spent_funds from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) spentFunds, " + 
			"(select t8.name enginProgressName from t_engineering_evaluate tee " + 
			"LEFT JOIN pub_dict_value t8 ON tee.engin_progress_nm = t8.nm where engineering_nm = tt.nm " + 
			"ORDER BY Implement_info_date DESC limit 1)enginProgressName, " + 
			"(select implement_Info_Date from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) implementInfoDate, " + 
			"(select count(1) from pub_files where table_pk_column = tt.nm) fujian  " + 
			"FROM t_engineering_sink_bottom as tt  " + 
			"LEFT JOIN pub_dict_value t1 on tt.one_level = t1.`nm`   " + 
			"LEFT JOIN pub_dict_value t2 on tt.two_level = t2.`nm`   " + 
			"LEFT JOIN pub_dict_value t3 on tt.three_level = t3.`nm`   " + 
			"LEFT JOIN pub_project t4 on tt.project_nm = t4.nm   " + 
			"LEFT JOIN pub_region t5 on tt.region = t5.city_code   " + 
			"LEFT JOIN sys_staff t6 on tt.create_staff = t6.nm   " + 
			"LEFT JOIN sys_staff t7 on tt.update_staff = t7.nm  " + 
			"where 1 = 1  and if(:projectName is not null,t4.name like CONCAT('%',:projectName,'%'),1=1)"
			+ " order by tt.create_time desc",
			countQuery = "SELECT COUNT(1)" + 
					"FROM (select t0.*,tee.Implement_info_date,tee.progress_evaluate,tee.implement_status,tee.spent_funds," + 
					"tee.engin_progress_nm" + 
					"from t_engineering_sink_bottom t0 " + 
					"LEFT JOIN t_engineering_evaluate tee on tee.engineering_nm = t0.nm " + 
					"ORDER BY Implement_info_date DESC limit 1) as tt" + 
					"LEFT JOIN pub_dict_value t1 on tt.one_level = t1.`nm` " + 
					"LEFT JOIN pub_dict_value t2 on tt.two_level = t2.`nm` " + 
					"LEFT JOIN pub_dict_value t3 on tt.three_level = t3.`nm` " + 
					"LEFT JOIN pub_project t4 on tt.project_nm = t4.nm " + 
					"LEFT JOIN pub_region t5 on tt.region = t5.city_code " + 
					"LEFT JOIN sys_staff t6 on tt.create_staff = t6.nm " + 
					"LEFT JOIN sys_staff t7 on tt.update_staff = t7.nm" + 
					"LEFT JOIN pub_dict_value t8 ON tt.engin_progress_nm = t8.nm" + 
					"where 1 = 1 and if(:projectName is not null,t4.name like CONCAT('%',:projectName,'%'),1=1)",nativeQuery = true)
	Page<EngineeringSinkBottomVO> page(@Param("projectName")String projectName,Pageable pageable);
	
}
