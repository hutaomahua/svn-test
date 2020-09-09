package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostInfrastructureEntity;

/**
 * 搬迁安置基础设施补助
 * @author hxl
 *
 */
public interface CostInfrastructureDao extends JpaRepository<CostInfrastructureEntity, String> {

	/**
	 * 查询搬迁安置基础设施补助明细
	 * @param ownerNm
	 * @return
	 */
	List<CostInfrastructureEntity> findByOwnerNm(String ownerNm);
	
	/**
	 * 删除当前户的搬迁安置基础设施补助明细
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_infrastructure WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 查询当前户是否已计算搬迁安置基础设施补助费用
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_infrastructure WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 获取当前户搬迁安置基础设施补助费用总额
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_infrastructure WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);
	
}