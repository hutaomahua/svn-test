package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostBuildingEntity;
import com.lyht.business.cost.vo.CostBuildingVO;

/**
 * 附属建筑物补偿
 * 
 * @author hxl
 *
 */
public interface CostBuildingDao extends JpaRepository<CostBuildingEntity, String> {

	/**
	 * 查询附属建筑物补偿明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	List<CostBuildingEntity> findByOwnerNm(String ownerNm);

	/**
	 * 计算附属建筑物补偿费用并返回明细
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
			+ " SUM("
			+ " IF (tt.longs is not null, tt.longs, IF(tt.area is not null, tt.area, IF(tt.volume is not null, tt.volume, IF(tt.num is not null, tt.num, NULL))))"
			+ " ) AS num"
			+ " FROM t_info_building_impl tt"
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " where tt.owner_nm = :ownerNm"
			+ " AND tt.project_nm IS NOT NULL"
			+ " AND pp.money IS NOT NULL"
			+ " GROUP BY tt.project_nm"
			+ " ) t"
			+ " WHERE t.num IS NOT NULL AND t.num != 0", nativeQuery = true)
	List<CostBuildingVO> findCostByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 删除当前户的附属建筑物补偿明细
	 * 
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_building WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询当前户是否已计算附属建筑物补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_building WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取当前户附属建筑物补偿费用总额
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_building WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);

}