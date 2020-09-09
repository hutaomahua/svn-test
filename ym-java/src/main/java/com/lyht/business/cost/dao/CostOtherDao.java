package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostOtherEntity;

/**
 * 其他补助及困难户补助
 * @author hxl
 *
 */
public interface CostOtherDao extends JpaRepository<CostOtherEntity, String> {
	
	/**
	 * 查询明细（按类型）
	 * @param ownerNm
	 * @param type
	 * @return
	 */
	List<CostOtherEntity> findByOwnerNmAndType(String ownerNm, String type);

	/**
	 * 查询其他补助明细
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT * FROM t_cost_other WHERE owner_nm = :ownerNm AND type != 'difficult'", nativeQuery = true)
	List<CostOtherEntity> findOtherCostByOwnerNm(@Param("ownerNm")String ownerNm);
	
	/**
	 * 查询困难户补助明细
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT * FROM t_cost_other WHERE owner_nm = :ownerNm AND type = 'difficult'", nativeQuery = true)
	List<CostOtherEntity> findDifficultCostByOwnerNm(@Param("ownerNm")String ownerNm);
	
	/**
	 * 删除当前户的其他补助明细
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_other WHERE owner_nm = :ownerNm AND type != 'difficult'", nativeQuery = true)
	void deleteOtherCostByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 删除当前户的困难户补助明细
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_other WHERE owner_nm = :ownerNm AND type = 'difficult'", nativeQuery = true)
	void deleteDifficultCostByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 查询当前户是否已计算其他补助费用
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_other WHERE owner_nm = :ownerNm AND type != 'difficult'", nativeQuery = true)
	int countOtherCostByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 查询当前户是否已计算困难户补助费用
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_other WHERE owner_nm = :ownerNm AND type = 'difficult'", nativeQuery = true)
	int countDifficultCostByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 获取当前户其他补助费用总额
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_other WHERE owner_nm = :ownerNm AND type != 'difficult'", nativeQuery = true)
	BigDecimal amountOtherCostByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 获取当前户困难户补助费用总额
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_other WHERE owner_nm = :ownerNm AND type = 'difficult'", nativeQuery = true)
	BigDecimal amountDifficultCostByOwnerNm(@Param("ownerNm") String ownerNm);
	
}