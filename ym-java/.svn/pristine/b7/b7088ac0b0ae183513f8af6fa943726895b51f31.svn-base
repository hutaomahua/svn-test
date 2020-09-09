package com.lyht.business.abm.land.dao;

import com.lyht.business.abm.land.entity.LateCountyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface LateCountyDao extends JpaRepository<LateCountyEntity, Integer> {
	
	@SuppressWarnings("rawtypes")
	@Query(value = " select count(b.nm)fcNum,a.* from t_abm_late_county a LEFT JOIN t_abm_late_detail b on a.nm =b.nm "
		  	+ " LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
	  		+ " where 1=1  and" +
	   	     " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
			 "  IF (:name is not null,a.reservoir LIKE CONCAT('%',:name,'%'), 1=1)  "
			 + " group by b.nm order BY a.id ASC  "
	        ,countQuery=" select count(1) from t_abm_late_county a "
	    	  		+ " LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
	        		+ "where 1=1 and " +
	          	 " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
			  	 "  IF (:name is not null,a.reservoir LIKE CONCAT('%',:name,'%'), 1=1)  "
			  		+ " group by b.nm order BY a.id ASC ",
	           nativeQuery = true)       
	Page<Map> getList(@Param("region") String region,@Param("name") String name,Pageable pageable);
	  
}
