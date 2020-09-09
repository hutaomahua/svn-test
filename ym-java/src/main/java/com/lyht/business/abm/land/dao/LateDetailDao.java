package com.lyht.business.abm.land.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.land.entity.LateCountyEntity;
import com.lyht.business.abm.land.entity.LateDetailEntity;

public interface LateDetailDao  extends JpaRepository<LateDetailEntity, Integer> {
	
	  @Query(value = " select * from t_abm_late_detail  a where 1=1 and nm=:nm " 
		  	
		  		+ "order BY id ASC "
      ,countQuery=" select count(1) from t_abm_late_detail a where 1=1 and nm=:nm " 
    		
		  		+ " order BY id ASC ",
         nativeQuery = true)       
Page<LateDetailEntity> getList(@Param("nm") String nm,Pageable pageable);

}
