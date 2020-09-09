package com.lyht.business.abm.signed.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.signed.entity.TotalState;

/**
 * 协议总状态表
 * 
 * @author wzw
 *
 */
@Repository
public interface TotalStateDao extends JpaRepository<TotalState, Integer> {

	@Query(value = "select * from t_abm_total_state where owner_nm = :ownerNm", nativeQuery = true)
	TotalState findByOwnerNm(@Param("ownerNm") String ownerNm);

	@Query(value = "select count(1) from t_abm_total_state where owner_Nm =:ownerNm", nativeQuery = true)
	Integer getCountByOwnerNm(@Param("ownerNm") String ownerNm);

	@Modifying
	@Query(value = "update t_abm_total_state set protocol_state = 1 where owner_nm =:ownerNm", nativeQuery = true)
	Integer updateProtocolStateToFinish(@Param("ownerNm") String ownerNm);

	@Modifying
	@Query(value = "update t_abm_total_state set protocol_state = 0 where owner_nm =:ownerNm", nativeQuery = true)
	Integer updateProtocolStateToFail(@Param("ownerNm") String ownerNm);

	@Modifying
	@Query(value = " update t_abm_total_state set move_status = 0 where owner_nm =:ownerNm "
			+ " and house_status <> 1 and house_decoration_status <> 1 and building_status <> 1 and agricultural_facilities_status <> 1 and trees_status <> 1  "
			+ " and individual_status <> 1 and relocation_allowance_status <> 1 "
			+ " and other_status <> 1 and difficult_status <> 1 and infrastructure_status <> 1 and homestead_status <> 1 and levy_land_status <> 1  "
			+ " and young_crops_status <> 1 ",nativeQuery = true)
	Integer updateMoveStatus(@Param("ownerNm") String ownerNm);

	@Modifying
	@Query(value = " update t_abm_total_state set total = (select (IFNULL(house_amount,0)+IFNULL(house_decoration_amount,0)+IFNULL(building_amount,0)+IFNULL(agricultural_facilities_amount,0)+IFNULL(trees_amount,0)+IFNULL(individual_amount,0) "
			+ "+IFNULL(relocation_allowance_amount,0)+IFNULL(other_amount,0)+IFNULL(difficult_amount,0)+IFNULL(infrastructure_amount,0)+IFNULL(homestead_amount,0)+IFNULL(levy_land_amount,0) "
			+ "+IFNULL(young_crops_amount,0)) as amount from t_compensation_cost where owner_nm = :ownerNm) "
			+ "where owner_nm = :ownerNm ", nativeQuery = true)
	Integer updateTotal(@Param("ownerNm") String ownerNm);

}
