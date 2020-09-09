package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostYoungCropsEntity;
import com.lyht.business.cost.vo.CostYoungCropsVO;

/**
 * 成片青苗及果木补偿
 * 
 * @author hxl
 *
 */
public interface CostYoungCropsDao extends JpaRepository<CostYoungCropsEntity, String> {

	/**
	 * 查询成片青苗及果木补偿明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	List<CostYoungCropsEntity> findByOwnerNm(String ownerNm);

	/**
	 * 计算成片青苗及果木补偿费用并返回明细(园地算所有征地范围，耕地只算枢纽永久)
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = " SELECT"
			+ " t.*, CAST(t.price * t.num AS DECIMAL (16, 4)) AS amount"
			+ " FROM"
			+ " (SELECT"
			+ " tt.owner_nm AS ownerNm, pp.name AS projectName,"
			+ " CONCAT('元/', pp.danwei) AS unit, pp.money AS price,"
			+ " SUM(tt.area) AS num"
			+ " FROM t_info_land_impl tt"
			+ " LEFT JOIN pub_project pp ON tt.land_project_nm = pp.id"
			+ " where tt.owner_nm = :ownerNm AND tt.land_project_nm IS NOT NULL"
			+ " AND tt.land_project_nm IS NOT NULL"
			+ " AND pp.money IS NOT NULL"
			+ " AND tt.type_one IN ('745CAF65AC')"//园地
			+ " GROUP BY tt.land_project_nm"
			+ " ) t"
			+ " UNION ALL"
			+ " SELECT"
			+ " t.*, CAST(t.price * t.num AS DECIMAL (16, 4)) AS amount"
			+ " FROM"
			+ " (SELECT"
			+ " tt.owner_nm AS ownerNm, pp.name AS projectName,"
			+ " CONCAT('元/', pp.danwei) AS unit, pp.money AS price,"
			+ " SUM(tt.area) AS num"
			+ " FROM t_info_land_impl tt"
			+ " LEFT JOIN pub_project pp ON tt.land_project_nm = pp.id"
			+ " where tt.owner_nm = :ownerNm AND tt.land_project_nm IS NOT NULL"
			+ " AND tt.land_project_nm IS NOT NULL"
			+ " AND pp.money IS NOT NULL"
			+ " AND tt.type_one IN ('C4B191A6F3')"//耕地
			+ " AND tt.scope = 'CE81C0FA94'"//征地范围为：枢纽永久
			+ " GROUP BY tt.land_project_nm"
			+ " ) t"
			+ " WHERE t.num IS NOT NULL AND t.num != 0", nativeQuery = true)
	List<CostYoungCropsVO> costByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 删除当前户的成片青苗及果木补偿明细
	 * 
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_young_crops WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询当前户是否已计算成片青苗及果木补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_young_crops WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取当前户成片青苗及果木补偿费用总额
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_young_crops WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);

}