package com.lyht.business.abm.paymentManagement.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.paymentManagement.entity.VPaymentConfirmation;
import com.lyht.business.abm.paymentManagement.vo.PaymentConfirmationVO;

public interface VPaymentConfirmationDao extends JpaRepository<VPaymentConfirmation, Integer> {
	
	/**
	 * 根据行政区查询可以发起兑付兑付确认的兑付申请信息
	 * 根据批次内码查询当前批次兑付确认的兑付协议信息
	 * @param region
	 * @param confirmationBatch
	 * @param enable 是否过滤调在兑付确认批次中的记录
	 * @return
	 */
	@Query(value = "SELECT "
		+"applied.nm 					as nm, "
		+"owner.name 					as owner, "
		+"owner.nm 						as ownernm, "
		+"owner.id_card 				as idcard,  "
		+"applied.payee					as payee, "
		+"applied.bank_card 			as bankcard, "
		+"applied.deposit_bank			as depositbank, "
		+"applied.protocol_code			as protocolcode, "
		+"protocol.protocolName			as protocolname, "
		+"applied.protocol_type			as protocoltype, "
		+"paymentInfo.protocol_amount 	as protocolamount, "
		+"applied.apply_amount			as applyamount, "
		+"applied.apply_batch			as applybatch, "
		+"applied.proposer				as proposer, "
		+"applied.apply_time 			as applytime "
		+"from v_payment_applied 		applied  "
		+"LEFT JOIN t_info_owner_impl 	owner 		on applied.owner_nm 		= owner.nm "
		+"LEFT JOIN pub_region 			region 		on owner.region 			= region.city_code "
		+"LEFT JOIN v_protocol_finish 	protocol 	on applied.protocol_code 	= protocol.protocolCode and applied.protocol_type 		= protocol.protocolType "
		+"LEFT JOIN t_abm_payment_info 	paymentInfo on applied.protocol_code 	= paymentInfo.protocol_Code and applied.protocol_type 	= paymentInfo.protocol_Type "
		+"WHERE 1 = 1  "
		+"AND IF 	(:region 			is not null								, REPLACE(region.merger_name,',','') like CONCAT('%',:region,'%')	,1=1) "
		+"AND IF	(:confirmationBatch is not null and :confirmationBatch != '', applied.confirmation_batch = :confirmationBatch					,1=1)"
		+"AND IF	(:enable is not null 			and :enable					, applied.confirmation_batch is null								,1=1);",nativeQuery = true)
	List<PaymentConfirmationVO> paymentConfirmationList(@Param("region")String region,@Param("confirmationBatch")String confirmationBatch,@Param("enable")Boolean enable);

	
	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT "
				+ "applied.payee			payee,"
				+ "applied.bank_card		bankCard,"
				+ "applied.apply_amount		applyAmount,"
				+ "101 						num,"
				+ "owner.id_card			idCard,"
				+ "52 						idType "
			+ "from v_payment_applied applied "
			+ "LEFT JOIN t_info_owner owner on owner.nm =  applied.owner_nm "
			+ "where applied.nm in (:nms);",nativeQuery = true)
	List<Map> paymentDetailList(@Param("nms")List<String> nms);
	
}
