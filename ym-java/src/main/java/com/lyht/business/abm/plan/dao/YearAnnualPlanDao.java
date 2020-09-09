package com.lyht.business.abm.plan.dao;


import com.lyht.business.abm.plan.bean.YearAnnualPlanDetail;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 	年度计划编制
 *
 * @author lj
 *           2019/10/22
 *
 */
public interface YearAnnualPlanDao extends JpaRepository<YearAnnualPlanEntity, Integer> {

    @Query(value = " select * from t_abm_year_plan ", nativeQuery = true)
    List<YearAnnualPlanEntity> getList();

 // 查询最上级
 	@Query(value = "SELECT   a.id as 'key',a.nm as nm,a.name AS title,a.id AS value,"
 			+ "b.unit_nm as unitNm,b.task_name as taskName,b.budget_wan as budgetWan,"+
 			"b.complet_task_number as completTaskNumber,b.sum_task_number as sumTaskNumber,"+
 			"b.sketch,b.capital_two as capitalTwo,b.lastyear_capital_two as lastyearCapitalTwo,b.new_capital  as newCapital"
 			+ "  FROM t_abm_year_subject a LEFT JOIN t_abm_year_plan b  on a.nm=b.nm WHERE 1=1 and super_id IS NULL  and "
 			+ " IF (?1 is not null, b.year LIKE CONCAT('%',?1,'%'), 1=1 ) and "
 			+ " IF (?2 is not null, b.region LIKE CONCAT('%',?2,'%'),  1=1 )  "
 			+ "and  IF (?3 is not null, scode in (?3) ,1=1)"
 			+ "order by fcode asc ", nativeQuery = true)
 	List<Map> getTopBean(@Param("name") String name,@Param("region") String region,@Param("scode")String scodes);

 	@Query(value = "select  a.id as 'key',a.nm as nm,a.name AS title,a.id AS value,a.super_id as SuperId, "
 			+ "b.unit_nm as unitNm,b.task_name as taskName,b.budget_wan as budgetWan,"+
 			"b.complet_task_number as completTaskNumber,b.sum_task_number as sumTaskNumber,"+
 			"b.sketch,b.capital_two as capitalTwo,b.lastyear_capital_two as lastyearCapitalTwo,b.new_capital  as newCapital"
			+ " from t_abm_year_subject a LEFT JOIN t_abm_year_plan b  on a.nm=b.nm where super_id is not null and "
			+ " IF (?1 is not null, b.year LIKE CONCAT('%',?1,'%'), 1=1 ) and "
 			+ " IF (?2 is not null, b.region LIKE CONCAT('%',?2,'%'),  1=1 )  "
			+ "order by fcode ASC ", nativeQuery = true)
	List<Map> findSon(@Param("name") String name,@Param("region") String region);
    @Query(value = " select `id`," +
            "`nm`," +
            "`sort`," +
            "`project_nm` as projectNm," +
            "`unit_nm` AS unitNm," +
            "`task_name` as taskName," +
            "`budget_wan` AS budgetwan," +
            "`complet_task_number` AS completTaskNumber," +
            "`capital` AS capital," +
            "`residue_invest` AS residueInvest," +
            "`lastyear_capital` AS lastyearCapital," +
            "`sum_task_number` AS sumTaskNumber," +
            "`sketch` AS sketch," +
            "`capital_two` AS capitalTwo," +
            "`lastyear_capital_two` AS lastyearCapitalTwo," +
            "`new_capital` AS newCapital ," +
            "`to_be_capital` AS toBeCapital," +
            "`residue_sum_capital` AS residueSumCapital," +
            "`duty_unit` AS  dutyUnit," +
            "`complet_date` AS completDate," +
            "`supervision_unit` AS supervisionUnit," +
            "`parent_id` AS parentId," +
            "`update_staff`," +
            "`create_staff`," +
            "`create_time`," +
            "`start_date`," +
            "`update_time` AS updateTime  FROM t_abm_year_plan where  id = :id ", nativeQuery = true)
   YearAnnualPlanEntity getDetails(@Param("id") Integer id);
   
   @Query(value = " select a.cause,a.type,a.id,nm,task_name,region,region_name,start_date,complet_date,unit_number,sum_task_number,plan_task_number, " + 
			"plan_invest,sum_invest,remark,state,sort,create_staff,create_time,update_staff,update_time,"+
		   "(select name from pub_dict_value b where a.area=b.code)as area from t_abm_year_plan a  where a.id=:id"
		   , nativeQuery = true)   		        
   Page<YearAnnualPlanEntity> getDetail(@Param("id") String id,Pageable pageable);
   
   @Query(value = " select code FROM pub_dict_value where  name = :name " , nativeQuery = true)   		        
   List<Map> getCode(@Param("name") String name);
   
   //判断是否添加了年计划
   @Query(value = "select * FROM t_abm_year_plan where  region=:region and year=:year " , nativeQuery = true)   		        
   List<Map> isPresence(@Param("region") String region,@Param("year") String year);
   
   @Transactional
   @Modifying
   @Query(value = " delete from t_abm_year_plan where region =:region and year=:year ",nativeQuery = true)
   void delYear(@Param("region")String region,@Param("year")String year);
   
}
