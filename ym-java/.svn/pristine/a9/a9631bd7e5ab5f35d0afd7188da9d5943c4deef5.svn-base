package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostHousesDecorationEntity;
import com.lyht.business.cost.vo.CostHousesDecorationVO;

/**
 * 房屋装修补偿
 * 
 * @author hxl
 *
 */
public interface CostHousesDecorationDao extends JpaRepository<CostHousesDecorationEntity, String> {

	/**
	 * 查询房屋装修补偿明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	List<CostHousesDecorationEntity> findByOwnerNm(String ownerNm);

	/**
	 * 计算房屋装修补偿费用并返回明细
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
			+ " FROM t_info_houses_decoration_impl tt"
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " where tt.owner_nm = :ownerNm"
			+ " AND tt.project_nm IS NOT NULL"
			+ " AND pp.money IS NOT NULL"
			+ " GROUP BY tt.project_nm"
			+ " ) t"
			+ " WHERE t.num IS NOT NULL AND t.num != 0", nativeQuery = true)
	List<CostHousesDecorationVO> findCostByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 删除当前户的房屋装修补偿明细
	 * 
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_houses_decoration WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询当前户是否已计算房屋装修补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_houses_decoration WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取当前户房屋装修补偿费用总额
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_houses_decoration WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);

}