package com.lyht.business.abm.plan.dao;

import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPlanDao extends JpaRepository<ProjectPlanEntity, Integer> {

  

	  @Query(value = " select a.cause,a.type,a.id,a.nm,task_name,task_content,region,REPLACE(region_name,CONCAT(SUBSTRING_INDEX(region_name,',',3),','),'')region_name,start_date,complet_date,unit_number,sum_task_number, "
  			+"sum_invest,create_staff,a.create_time,a.update_staff,a.update_time,"
  			+ "duty_unit,"
  			+ "control_unit,"+
  		   "(select name from pub_dict_value b where a.task_type=b.code)as task_type,"
  		   + "(select name from pub_dict_value b where a.state=b.code)as state "
  		   + " from t_abm_project_implement a LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "+
  		  " where  1 = 1  and " +
          " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
           "  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
           "  IF (:nm is not null,a.nm=:nm, 1=1) and "+

           "  IF (:area is not null,a.task_type LIKE CONCAT('%',:area,'%'), 1=1)  order BY a.id ASC ",
        countQuery=" select count(*) from t_abm_project_implement a LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "+
  		  " where  1 = 1  and " +
          " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
           "  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
           "  IF (:nm is not null,a.nm=:nm, 1=1) and "+
           "  IF (:area is not null,a.task_type LIKE CONCAT('%',:area,'%'), 1=1)  order BY a.id ASC",
           nativeQuery = true)       
	  
  Page<ProjectPlanEntity> getList(@Param("region") String region,@Param("area") String area,@Param("taskName") String taskName,@Param("nm")String nm,Pageable pageable);
  
    @Query(value = " select a.cause,a.type,a.id,nm,task_name,region,region_name,start_date,complet_date,unit_number,sum_task_number,plan_task_number, " + 
 			"plan_invest,sum_invest,remark,state,sort,create_staff,create_time,update_staff,update_time,"+
 		   "(select name from pub_dict_value b where a.area=b.code)as area from t_abm_project_implement a  where a.id=:id", nativeQuery = true)
    Page<ProjectPlanEntity> getDetail(@Param("id") String id,Pageable pageable);

    @Query(value = " select * FROM t_abm_project_implement where  id = :id " , nativeQuery = true)
    ProjectPlanEntity getDetails(@Param("id") Integer id);
    
    @Query(value = " select name FROM sys_dept where  nm = :nm " , nativeQuery = true)
    List<Map> getDeptName(@Param("nm") Integer nm);
    
    
    @Query(value = " select nm FROM t_abm_project_implement where  task_content = :str " , nativeQuery = true)
    List<Map> getTaskNm(@Param("str") String str);
    
    @Query(value = " select merger_name FROM pub_region where  city_code = :region " , nativeQuery = true)
    List<Map> regionName(@Param("region") String region);
    
}
