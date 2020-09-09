package com.lyht.business.abm.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.review.entity.PersonalWealthData;

@Repository
public interface PersonalWealthDataDao extends JpaRepository<PersonalWealthData, Integer> {

	PersonalWealthData findByMasterNm(String masterNm);

	@Modifying
	@Query(value = "delete from t_abm_personal_wealth_data where master_nm = :masterNm", nativeQuery = true)
	void deteteByMasterNm(@Param(value = "masterNm") String masterNm);

	@Modifying
	@Query(value = "update t_abm_personal_wealth_data set immigrant_population_json = :immigrantPopulationJson,land_json=:landJson,house_json=:houseJson,"
			+ "tree_json=:treeJson,other_json=:otherJson,fitment_json=:fitmentJson,individual_household_json=:individualHouseholdJson "
			+ "where id = :id ", nativeQuery = true)
	void updateData(@Param("id") Integer id, @Param("immigrantPopulationJson") String immigrantPopulationJson,
			@Param("landJson") String landJson, @Param("houseJson") String houseJson,
			@Param("treeJson") String treeJson, @Param("otherJson") String otherJson,
			@Param("fitmentJson") String fitmentJson, @Param("individualHouseholdJson") String individualHouseholdJson);

}
