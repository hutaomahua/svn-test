package com.lyht.business.abm.landAllocation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.landAllocation.entity.LandPoolProcess;

@Repository
public interface LandPoolProcessDao extends JpaRepository<LandPoolProcess, Integer> {

	/**
	 * 当某数据流程通过后 删除表中数据  以防出现计算偏差
	 * @param taskId
	 * @param poolId
	 */
	@Modifying
	@Query(value = "delete from t_abm_land_pool_process where wealth_nm = :wealthNm and pool_id = :poolId", nativeQuery = true)
	void deleteByWealthNmAndPoolId(@Param("wealthNm") String wealthNm, @Param("poolId") Integer poolId);
	
	/**
	 * 当某数据流程通过后 删除表中数据  以防出现计算偏差
	 * @param taskId
	 * @param poolId
	 */
	@Modifying
	@Query(value = "delete from t_abm_land_pool_process where audit_id = :auditId and pool_id = :poolId", nativeQuery = true)
	void deleteByAuditNmAndPoolId(@Param("auditId") Integer auditId, @Param("poolId") Integer poolId);

	/**
	 * 查询剩余可分解面积
	 * @param poolId
	 * @return
	 */
	@Query(value = "select (select IFNULL(area-separate_area,0) from t_abm_land_pool where id = :poolId) - " + 
			"(select IFNULL(sum(area),0) from t_abm_land_pool_process where pool_id = :poolId)",nativeQuery = true)
	Double getResidualArea(@Param("poolId") Integer poolId);
	
	@Query(value = "select * from t_abm_land_pool_process where audit_id = :auditId and pool_id = :poolId",nativeQuery = true)
	LandPoolProcess queryByAuditNmAndPoolId(@Param("auditId") Integer auditId, @Param("poolId") Integer poolId);
	
	
}
