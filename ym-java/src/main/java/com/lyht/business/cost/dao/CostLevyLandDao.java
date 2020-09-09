package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostLevyLandEntity;
import com.lyht.business.cost.vo.CostLevyLandVO;

/**
 * 征收土地补偿
 * 
 * @author hxl
 *
 */
public interface CostLevyLandDao extends JpaRepository<CostLevyLandEntity, String> {

	/**
	 * 查询征收土地补偿明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	List<CostLevyLandEntity> findByOwnerNm(String ownerNm);

	/**
	 * 计算征收土地补偿费用并返回明细(私人性质--不算耕、园地)
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT" 
			+ " t.*, CAST(t.price * t.num AS DECIMAL (16, 4)) AS amount"
			+ " FROM"
			+ " (SELECT"
			+ " tt.owner_nm AS ownerNm, pp.name AS projectName,"
			+ " CONCAT('元/', pp.danwei) AS unit, pp.money AS price,"
			+ " SUM(tt.area) AS num"
			+ " FROM t_info_land_impl tt"
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " where tt.owner_nm = :ownerNm AND tt.project_nm IS NOT NULL"
			+ " AND tt.project_nm IS NOT NULL"
			+ " AND pp.money IS NOT NULL"
			+ " AND tt.scope IN ('D8D5AAD9DC','E78D14E7BE','CE81C0FA94','86BB819E2A')"
			+ " AND tt.type_one NOT IN ('C4B191A6F3','745CAF65AC')"
			+ " GROUP BY tt.project_nm"
			+ " ) t"
			+ " WHERE t.num IS NOT NULL AND t.num != 0", nativeQuery = true)
	List<CostLevyLandVO> costPersonalByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 计算征收土地补偿费用并返回明细(集体性质--都算)
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT"
			+ " t.*, CAST(t.price * t.num AS DECIMAL (16, 4)) AS amount"
			+ " FROM"
			+ " (SELECT"
			+ " tt.owner_nm AS ownerNm, pp.name AS projectName,"
			+ " CONCAT('元/', pp.danwei) AS unit, pp.money AS price,"
			+ " SUM(tt.area) AS num"
			+ " FROM t_info_land_impl tt"
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " where tt.owner_nm = :ownerNm AND tt.project_nm IS NOT NULL"
			+ " AND tt.project_nm IS NOT NULL"
			+ " AND pp.money IS NOT NULL"
			+ " AND tt.scope IN ('D8D5AAD9DC','E78D14E7BE','CE81C0FA94','86BB819E2A')"
			+ " GROUP BY tt.project_nm"
			+ " ) t"
			+ " WHERE t.num IS NOT NULL AND t.num != 0", nativeQuery = true)
	List<CostLevyLandVO> costCollectiveByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 删除当前户的征收土地补偿明细
	 * 
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_levy_land WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询当前户是否已计算征收土地补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_levy_land WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取当前户征收土地补偿费用总额
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_levy_land WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);

}