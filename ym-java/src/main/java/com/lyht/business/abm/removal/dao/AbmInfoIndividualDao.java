package com.lyht.business.abm.removal.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmInfoIndividualEntity;

public interface AbmInfoIndividualDao extends JpaRepository<AbmInfoIndividualEntity, Integer> {

	@Modifying
	@Query(value = "delete from t_info_individual_impl where owner_nm = :ownerNm",nativeQuery = true)
	Integer deleteByOwnerNm(@Param("ownerNm")String ownerNm);
	
	List<AbmInfoIndividualEntity> findByOwnerNm(String ownerNm);
	
}
