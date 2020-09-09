package com.lyht.business.abm.plan.dao;

import com.lyht.business.abm.plan.entity.ProjectPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPlanAdjustDao extends JpaRepository<ProjectPlanAdjustEntity, Integer> {

  
    @Query(value = " SELECT a.*,(select name from pub_dict_value c where a.type=c.code)  AS adjustType,b.plan_adjust_date AS adjustDate,b.adjust_staff_nm as staffName, "
    		+ " (select name from pub_dict_value c where a.state=c.code)as flag "
    		+ " FROM t_abm_project_implement a left JOIN  t_abm_project_implement_log b on a.nm=b.data_nm " +
    		  " where  1 = 1  and " +
    		  " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "+
             "  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
             "  IF (:area is not null,a.task_type LIKE CONCAT('%',:area,'%'), 1=1)  group by a.nm order BY a.update_time desc ",

             countQuery="SELECT a.*,(select name from pub_dict_value c where a.type=c.code)  AS adjustType,b.plan_adjust_date AS adjustDate,b.adjust_staff_nm as staffName, "
    		+ " (select name from pub_dict_value c where a.state=c.code)as flag "
    		+ " FROM t_abm_project_implement a left JOIN  t_abm_project_implement_log b on a.nm=b.data_nm " +
    		  " where  1 = 1  and " +
    		  " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "+
             "  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
             "  IF (:area is not null,a.task_type LIKE CONCAT('%',:area,'%'), 1=1)  group by a.nm order BY a.update_time desc", nativeQuery = true)

    Page<Map> getList(@Param("region") String region,@Param("area") String area,@Param("taskName") String taskName,Pageable pageable);
      
    
    @Query(value = " SELECT a.id,a.data_nm as dataNm,a.task_name as taskName,a.plan_invest as planInvest,"
    		+ " a.sum_invest as sumInvest,a.plan_adjust_date as planAdjustDate,"
    		+ " a.adjust_cause as adjustCause,a.adjust_staff_nm as adjustStaffNm,"
    		+"(select name from pub_dict_value c where a.adjust_type=c.code)as adjustType,"
    		+ "(select name from pub_dict_value c where a.adjust_state=c.code)as adjust_state,a.start_Date as startDate"
    		+ "  FROM t_abm_project_implement_log a  " + 
    		  " where  1 = 1  and " +

    		  " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "+
             "  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
             "  IF (:area is not null,a.area LIKE CONCAT('%',:area,'%'), 1=1)  order BY id ASC ",
             
 countQuery="SELECT a.id,a.data_nm as dataNm,a.task_name as taskName,a.plan_invest as planInvest,"
    		+ " a.sum_invest as sumInvest,a.plan_adjust_date as planAdjustDate,"
    		+ " a.adjust_cause as adjustCause,a.adjust_staff_nm as adjustStaffNm,"
    		+"(select name from pub_dict_value c where a.adjust_type=c.code)as adjustType,"
    		+ "(select name from pub_dict_value c where a.adjust_state=c.code)as adjust_state,a.start_Date as startDate"
    		+ "  FROM t_abm_project_implement_log a  " + 
    		  " where  1 = 1  and " +

    		  " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "+
             "  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
             "  IF (:area is not null,a.area LIKE CONCAT('%',:area,'%'), 1=1)  order BY a.update_time desc ", nativeQuery = true)       
    Page<Map> getLogList(@Param("region") String region,@Param("area") String area,@Param("taskName") String taskName,Pageable pageable);
    
  
    
    @Query(value = " select * FROM t_abm_project_implement_log where  id = :id " , nativeQuery = true)   		        
    List<ProjectPlanAdjustEntity> getDetails(@Param("id") Integer id);

    //调整记录
    //调整记录
    @Query(value = " select a.id,a.plan_adjust_date as planAdjustDate,a.adjust_staff_nm as adjustStaffNm,(select name from pub_dict_value b where a.adjust_type=b.code) as adjustType,"
    		+ "a.adjust_cause as adjustCause,(select name from pub_dict_value b where a.adjust_state=b.code) as adjustState "
    		+ "FROM t_abm_project_implement_log  a where  a.data_nm = :nm order by a.id  DESC " , nativeQuery = true)   
    List<Map> getNm(@Param("nm") String nm);


    //调整前后记录
    @Query(value = "SELECT "
            +"a.task_name as taskName1,a.region_name as region1 ,a.cause,"
    		+"(select name from pub_dict_value c where a.task_type=c.code)as area1 ,a.complet_date as completDate1,  "
            +"a.start_Date as startDate1,"
            +"a.unit_number as unitNumber1,a.sum_task_number as sumTaskNumber1, "
            +"a.sum_invest as sumInvest1,"
            +"a.task_content as taskContent1,"
            
            +"a.duty_unit as dutyUnit1,"
            +"a.control_unit as controlUnit1,"
            
            +"b.task_name as taskName,b.region_name as region , "
    		+"(select name from pub_dict_value c where b.task_type=c.code)as area ,b.complet_date as completDate,  "
            +"b.start_Date as startDate,"
            +"b.unit_number as unitNumber,b.sum_task_number as sumTaskNumber, "
            +"b.sum_invest as sumInvest,"
            +"b.task_content as taskContent,"
            +"b.duty_unit as dutyUnit,"
            +"b.control_unit as controlUnit,"
       
            +"b.adjust_staff_nm as staffName,(select name from pub_dict_value c where b.adjust_type=c.code)  as adjustType, "
    		+"b.plan_adjust_date as adjustDate, (select name from pub_dict_value c where b.adjust_state=c.code)  as adjustState "
            + "FROM t_abm_project_implement a  inner JOIN   t_abm_project_implement_log b "
            + " on a.nm=b.data_nm"
            + " where b.plan_adjust_date=( select MAX(plan_adjust_date) from t_abm_project_implement_log where data_nm=:nm )"
            + " and a.nm=:nm and IF (:id is not null,a.id=:id , 1=1) " , nativeQuery = true)
    List<Map> getSuccessively(@Param("nm") String nm,@Param("id") String id);



}
