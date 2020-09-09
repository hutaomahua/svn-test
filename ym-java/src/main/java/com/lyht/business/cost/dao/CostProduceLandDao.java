package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostProduceLandEntity;
import com.lyht.business.cost.vo.CostProduceLandVO;

/**
 * 生产安置土地补偿
 * 
 * @author hxl
 *
 */
public interface CostProduceLandDao extends JpaRepository<CostProduceLandEntity, String> {

	/**
	 * 查询生产安置土地补偿明细
	 * 
	 * @param ownerNm
	 * @return
	 */
	List<CostProduceLandEntity> findByOwnerNm(String ownerNm);
	
	/**
	 * 获取生产安置土地信息
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT" 
			+ " tt.owner_nm AS ownerNm, pdv.name AS projectName,"
			+ " '元/亩·月' AS unit, IFNULL(SUM(tt.area),0) AS num"
			+ " FROM t_info_land_impl tt"
			+ " LEFT JOIN pub_dict_value pdv ON tt.type_three = pdv.nm"
			+ " WHERE pdv.name IS NOT NULL"
			+ " AND tt.owner_nm = :ownerNm"
			+ " GROUP BY tt.type_three"
			+ " UNION"
			+ " SELECT"
			+ " tt.owner_nm AS ownerNm, pdv.name AS projectName,"
			+ " '元/亩·月' AS unit, IFNULL(SUM(tt.area),0) AS num"
			+ " FROM t_info_land_impl tt"
			+ " LEFT JOIN pub_dict_value pdv ON tt.type_two = pdv.nm"
			+ " WHERE pdv.name IS NOT NULL AND pdv.name = '水田'"
			+ " AND tt.owner_nm = :ownerNm"
			+ " GROUP BY tt.type_two"
			, nativeQuery = true)
	List<CostProduceLandVO> costByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 删除当前户的生产安置土地补偿明细
	 * 
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_produce_land WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 查询当前户是否已计算生产安置土地补偿费用
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_produce_land WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);

	/**
	 * 获取当前户生产安置土地补偿费用总额
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_produce_land WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);

}