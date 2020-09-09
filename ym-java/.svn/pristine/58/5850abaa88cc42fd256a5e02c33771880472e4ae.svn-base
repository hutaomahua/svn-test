package com.lyht.business.cost.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.cost.entity.CostIndividualEntity;
import com.lyht.business.cost.vo.CostIndividualVO;

/**
 * 个体工商户补偿
 * @author hxl
 *
 */
public interface CostIndividualDao extends JpaRepository<CostIndividualEntity, String> {

	/**
	 * 查询个体工商户补偿明细
	 * @param ownerNm
	 * @return
	 */
	List<CostIndividualEntity> findByOwnerNm(String ownerNm);
	
	/**
	 * 计算个体工商户补偿费用并返回明细
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT" + 
			" tt.owner_nm AS ownerNm, tt.type_name AS projectName," + 
			" tt.move_material_num AS moveMaterialNum, tt.move_vehicle_number AS moveVehicleNumber," + 
			" tt.move_material_money AS moveMaterialMoney, tt.closure_assistance_area AS closureAssistanceArea," + 
			" tt.closure_assistance_money AS closureAssistanceMoney, tt.compensation_amount AS amount" + 
			" FROM t_info_individual_impl tt" + 
			" where tt.owner_nm = :ownerNm", nativeQuery = true)
	List<CostIndividualVO> findCostByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 删除当前户的个体工商户补偿明细
	 * @param ownerNm
	 */
	@Modifying
	@Query(value = "DELETE FROM t_cost_individual WHERE owner_nm = :ownerNm", nativeQuery = true)
	void deleteByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 查询当前户是否已计算个体工商户补偿费用
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT COUNT(1) FROM t_cost_individual WHERE owner_nm = :ownerNm", nativeQuery = true)
	int countByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 获取当前户个体工商户补偿费用总额
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT SUM(amount) FROM t_cost_individual WHERE owner_nm = :ownerNm", nativeQuery = true)
	BigDecimal amountByOwnerNm(@Param("ownerNm") String ownerNm);
	
}