package com.lyht.business.abm.appropriation.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.appropriation.entity.Appropriation;
import com.lyht.business.abm.appropriation.vo.AppropriationVO;

@Repository
public interface AppropriationDao extends JpaRepository<Appropriation, Integer> {

	@Query(value = "  select tt.id,tt.nm,tt.application_no applicationNo,t8.nm protocolNm,t8.protocol_name protocolName,t8.protocol_no protocolNo,t1.nm deptNm,"
			+ " t1.name deptName,tt.applicant,tt.protocol_amount protocolAmount,tt.paid_amount paidAmount,tt.unpaid_amount unpaidAmount,tt.apply_amount applyAmount,"
			+ " tt.receivables_object receivablesObject,t2.nm moneyType,t2.name moneyTypeName,tt.apply_time applyTime,tt.paid_time paidTime,tt.paid_money paidMoney,"
			+ " tt.apply_people applyPeople,tt.paid_cause paidCause,tt.remark,tt.flag,t6.bank_number cardNumber,t7.name openingBankName,"
			+ " t5.nm paidMode,t5.name paidModeName,t7.nm openingBank from t_abm_appropriation tt left join sys_dept t1 on tt.dept_nm = t1.nm"
			+ " left join pub_dict_value t2 on tt.money_type = t2.nm"
			+ " left join pub_dict_value t5 on tt.paid_mode = t5.nm "
			+ " left join t_bank_crad_info t6 on tt.bank_card = t6.nm"
			+ " left join pub_dict_value t7 on t6.bank_nm = t7.nm "
			+ " left join t_abm_move_protocol t8 on t8.nm = tt.protocol_nm where 1 = 1"
			+ " and if(:protocolNo is not null, t8.protocol_no like CONCAT('%',:protocolNo,'%') , 1 = 1)"
			+ " and if(:startTime is not null, tt.apply_time BETWEEN :startTime and :endTime, 1 = 1) "
			+ " and if(:protocolName is not null, t8.protocol_name like CONCAT('%',:protocolName,'%') , 1 = 1)"
			+ " order by tt.apply_time desc ", countQuery = "SELECT COUNT(1) "
					+ " left join pub_dict_value t2 on tt.money_type = t2.nm"
					+ " left join pub_dict_value t5 on tt.paid_mode = t5.nm "
					+ " left join t_bank_crad_info t6 on tt.bank_card = t6.nm"
					+ " left join pub_dict_value t7 on t6.bank_nm = t7.nm "
					+ " left join t_abm_move_protocol t8 on t8.nm = tt.protocol_nm where 1 = 1"
					+ " and if(:applicationNo is not null, tt.application_no like CONCAT('%',:applicationNo,'%') , 1 = 1)"
					+ " and if(:startTime is not null, tt.apply_time BETWEEN :startTime and :endTime, 1 = 1) "
					+ " and if(:protocolName is not null, t8.protocol_name like CONCAT('%',:protocolName,'%') , 1 = 1)", nativeQuery = true)
	public Page<AppropriationVO> page(@Param("protocolNo") String protocolNo,
			@Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("protocolName") String protocolName, Pageable pageable);

	/**
	 * 查询当前最大编号
	 */
	@Query(value = "select application_no from t_abm_appropriation order by id DESC limit 1", nativeQuery = true)
	public String getLargeAppNo();
}
