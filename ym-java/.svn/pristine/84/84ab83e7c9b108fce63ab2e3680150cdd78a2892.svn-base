package com.lyht.business.abm.plan.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.plan.entity.ProjectPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;

/**
 * 	实施计划编制日志
 *
 * @author lj
 * 2019/10/22
 *
 */
public interface YearAnnualPlanAdjustDao extends JpaRepository<YearAnnualPlanAdjustEntity, Integer> {

	  @Query(value = 
			  " select * from t_abm_year_plan_log where 1=1 and "+
    		  " IF (:region is not null,region LIKE CONCAT('%',:region,'%'), 1=1) and "+
    		  " IF (:name is not null,name LIKE CONCAT('%',:name,'%'), 1=1) "
    		  + "  order BY id ASC  "
	        ,countQuery=
	         " select count(1) from t_abm_year_plan_log where 1=1 and "+
	         " IF (:region is not null,region LIKE CONCAT('%',:region,'%'), 1=1) and "+
	         " IF (:name is not null,name LIKE CONCAT('%',:name,'%'), 1=1) "
	         + "  order BY id ASC  "
	          , nativeQuery = true)       	  
	  Page<Map> getList(@Param("region") String region,@Param("name") String name,Pageable pageable);
	  
    
   
   
}
