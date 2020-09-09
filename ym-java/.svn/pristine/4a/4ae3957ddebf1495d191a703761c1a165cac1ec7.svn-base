package com.lyht.business.abm.removal.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.bean.MoveIdDetail;
import com.lyht.business.abm.removal.entity.MoveIdentity;
import com.lyht.business.abm.removal.entity.MoveKyEntity;

public interface MoveKyDao extends JpaRepository<MoveKyEntity, Integer>{
	
	  
   @Query(value =" select * from t_abm_move_ky  a where 1=1 and "
   		+ " IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "+
		 "  a.region=(select region from t_abm_move_ky where 1=1 LIMIT :counts,1 ) "
    ,nativeQuery = true)
   Page<Map> getList(Pageable pageable, @Param("region") String region,@Param("counts")int count);
   

}
