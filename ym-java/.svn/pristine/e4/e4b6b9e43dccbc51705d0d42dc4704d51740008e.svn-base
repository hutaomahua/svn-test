package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostHomesteadEntity;
import com.lyht.business.cost.vo.CostHomesteadVO;

/**
 * 宅基地补偿
 * 
 * @author hxl
 *
 */
public interface CostHomesteadDao extends JpaRepository<CostHomesteadEntity, String> {

	/**
	 * 查询宅基地补偿明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	List<CostHomesteadEntity> findByOwnerNm(String ownerNm);

	/**
	 * 查询宅基地补偿明细（按类型）
	 * 
	 * @param ownerNm
	 * @return
	 */
	List<CostHomesteadEntity> findByOwnerNmAndType(String ownerNm, String type);

	/**
	 * 计算宅基地补偿费用（分散安置）并返回明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT s.* ," +
			" CAST(s.area * s.price AS DECIMAL (16, 4)) AS amount" +
			" FROM ( " +
			" SELECT t.ownerNm, t.projectName, t.unit, t.price," +
			" CAST(t.area * 0.0015 AS DECIMAL (16, 4)) AS area," +
			" 'dispersed' AS type " +
			" FROM" +
			" (SELECT" +
			" tt.owner_nm AS ownerNm, '宅基地补偿' AS projectName," +
			" '元/亩' AS unit, 47600 AS price," +
			" SUM(tt.area) AS area " +
			" FROM t_info_homestead_impl tt" +
			" where tt.owner_nm = :ownerNm " +
			" GROUP BY tt.project_nm ) t" +
			" WHERE t.area IS NOT NULL AND t.area != 0 ) s ", nativeQuery = true)
	List<CostHomesteadVO> costDispersedByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 计算宅基地补偿费用（集中安置）并返回明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT s.*, " +
			" CAST(s.price * s.surplusArea AS DECIMAL (16, 4)) AS amount " +
			" FROM (" +
			"SELECT t.ownerNm, t.projectName, t.unit, t.price, t.deductionArea," +
			" CAST(t.area * 0.0015 AS DECIMAL (16, 4)) AS area," +
			"'focus' AS type," +
			" CAST(t.area * 0.0015 - t.deductionArea AS DECIMAL (16, 4)) AS surplusArea" +
			" FROM (SELECT" +
			" tt.owner_nm AS ownerNm, '宅基地补偿' AS projectName," +
			" '元/亩' AS unit, 47600 AS price, 0.35 AS deductionArea," +
			" SUM(tt.area) AS area" +
			" FROM t_info_homestead_impl tt" +
			" where tt.owner_nm = :ownerNm " +
			" GROUP BY tt.project_nm ) t" +
			" WHERE t.area IS NOT NULL AND t.area != 0 ) s ", nativeQuery = true)
	List<CostHomesteadVO> costFocusByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 删除当前户的宅基地补偿明细
	 * 
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_homestead WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询当前户是否已计算宅基地补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_homestead WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取当前户宅基地补偿费用总额
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_homestead WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);

}