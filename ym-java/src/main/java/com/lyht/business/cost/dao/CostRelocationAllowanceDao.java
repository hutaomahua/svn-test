package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostRelocationAllowanceEntity;

/**
 * 搬迁补助
 * @author hxl
 *
 */
public interface CostRelocationAllowanceDao extends JpaRepository<CostRelocationAllowanceEntity, String> {

	/**
	 * 查询搬迁补助明细
	 * @param ownerNm
	 * @return
	 */
	List<CostRelocationAllowanceEntity> findByOwnerNm(String ownerNm);
	
	/**
	 * 查询搬迁补助明细(搬迁类型)
	 * @param ownerNm
	 * @return
	 */
	List<CostRelocationAllowanceEntity> findByOwnerNmAndType(String ownerNm, String type);
	
	/**
	 * 删除当前户的搬迁补助明细
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_relocation_allowance WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 查询当前户是否已计算搬迁补助费用
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_relocation_allowance WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 获取当前户搬迁补助费用总额
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_relocation_allowance WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);
	
}