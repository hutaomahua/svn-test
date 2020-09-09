package com.lyht.business.abm.land.dao;

import com.lyht.business.abm.land.entity.LatePopulationEntity;
import com.lyht.business.abm.land.entity.RequisitionPlanEntity;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.land.bean.RequisitionPlanDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface LatePopulationDao extends JpaRepository<LatePopulationEntity, Integer> {
	  @Query(value = " select * from t_abm_late_population  a "
	  		+ " LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
	  		+ " where 1=1 and "
	   	    + " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) " 
			+ "order BY a.id ASC "
	        ,countQuery=" select count(1) from t_abm_late_population a "
	    	  		+ " LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
	        		+ "where 1=1 and " 
	    	   	    + " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) " 
			  		+ "order BY a.id ASC ",
	           nativeQuery = true)       
	  Page<LatePopulationEntity> getList(@Param("region") String region,Pageable pageable);
	  
}
