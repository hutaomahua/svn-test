package com.lyht.business.abm.paymentManagement.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.paymentManagement.entity.PaymentDetailEntity;
import com.lyht.business.abm.paymentManagement.vo.ApplyAuditVO;

public interface PaymentDetailDao extends JpaRepository<PaymentDetailEntity, Integer>  {

	@Query(value = "SELECT "
				+"detail.nm 				nm, "				//记录内码
				+"owner.nm 					ownernm, "			//权属人内码
				+"owner.name 				owner, "			//权属人
				+"movetype.nm 				movetypenm, "		//安置类型字典内码
				+"movetype.name 			movetype, "			//安置类型
				+"protocol.protocolCode 	protocolcode, "		//协议编号
				+"protocol.protocolName 	protocolname, "		//协议名称
				+"detail.protocol_type 		protocoltype, "		//协议类型
				+"protocol.protocolAmount 	protocolamount, "	//协议总额
				+"detail.apply_amount 		applyamount, "		//申请金额
				+"detail.apply_batch 		applybatch, "		//申请批次
				+"detail.apply_time 		applytime, "		//申请时间
				+"detail.payee 				payee, "			//收款人
				+"detail.bank_card 			bankcard, "			//银行卡号
				+"detail.deposit_bank 		depositbank, "		//开户银行
				+"paidmode.nm 				paidmodenm, "		//支付方式内码
				+"paidmode.name 			paidmode, "			//支付方式
				+"detail.proposer 			proposer, "			//申办人
				+"detail.proposer_dept  	proposerdept, "		//申办人单位
				+"detail.process_id 		processid, "		//流程实例ID
				+"bpm.status 				status, "			//流程处理状态
				+"bpm.cn_status 			cnstatus "			//流程处理状态明文
				/*
				 * 	Approved	同意
					Deleted		已取消
					Rejected	拒绝
					Standby		处理中
				 */
				+"from t_abm_payment_detail 	detail  "
				+"LEFT JOIN t_bpm_process 		bpm 		on detail.process_id = bpm.process_id "
				+"LEFT JOIN t_info_owner_impl 	owner 		on owner.nm = detail.owner_nm  "
				+"LEFT JOIN pub_region 			region 		on owner.region = region.city_code "
				+"LEFT JOIN v_protocol_finish 	protocol 	on protocol.protocolCode = detail.protocol_code and protocol.protocolType = detail.protocol_type "
				+"LEFT JOIN pub_dict_value 		movetype 	on movetype.listnm_sys_dict_cate = 'dict_move_type' and movetype.nm = owner.place_type  "
				+"LEFT JOIN pub_dict_value 		paidmode 	on paidmode.listnm_sys_dict_cate = 'dict_paid_mode' and paidmode.nm = detail.payment_method   "
				+"where 1 = 1 "
				+"and if(:region 		is not null and :region 		!= '', region.merger_name 		like CONCAT('%',:region,'%'), 1 = 1) "
				+"and if(:ownername		is not null and :ownername 		!= '', owner.name 				like CONCAT('%',:ownername,'%'), 1 = 1) "
				+"and if(:idcard		is not null and :idcard 		!= '', owner.id_card			like CONCAT('%',:idcard,'%'), 1 = 1) "
				+"and if(:protocolname 	is not null and :protocolname 	!= '', protocol.protocolName 	like CONCAT('%',:protocolname,'%') , 1 = 1) "
				+"and if(:st 			is not null and :st 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(:st,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
				+"and if(:et 			is not null and :et 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(:et,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
				+"ORDER BY detail.apply_time desc "
				,countQuery = "SELECT count(1) "
				+"from t_abm_payment_detail 	detail  "
				+"LEFT JOIN t_bpm_process 		bpm 		on detail.process_id = bpm.process_id "
				+"LEFT JOIN t_info_owner_impl 	owner 		on owner.nm = detail.owner_nm  "
				+"LEFT JOIN pub_region 			region 		on owner.region = region.city_code "
				+"LEFT JOIN v_protocol_finish 	protocol 	on protocol.protocolCode = detail.protocol_code and protocol.protocolType = detail.protocol_type "
				+"LEFT JOIN pub_dict_value 		movetype 	on movetype.listnm_sys_dict_cate = 'dict_move_type' and movetype.nm = owner.place_type  "
				+"LEFT JOIN pub_dict_value 		paidmode 	on paidmode.listnm_sys_dict_cate = 'dict_paid_mode' and paidmode.nm = detail.payment_method   "
				+"where 1 = 1 "
				+"and if(:region 		is not null and :region 		!= '', region.merger_name 		like CONCAT('%',:region,'%'), 1 = 1) "
				+"and if(:ownername		is not null and :ownername 		!= '', owner.name 				like CONCAT('%',:ownername,'%'), 1 = 1) "
				+"and if(:idcard		is not null and :idcard 		!= '', owner.id_card			like CONCAT('%',:idcard,'%'), 1 = 1) "
				+"and if(:protocolname 	is not null and :protocolname 	!= '', protocol.protocolName 	like CONCAT('%',:protocolname,'%') , 1 = 1) "
				+"and if(:st 			is not null and :st 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(:st,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
				+"and if(:et 			is not null and :et 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(:et,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
				,nativeQuery = true)
	Page<ApplyAuditVO> auditList(@Param("region")String region, @Param("ownername")String ownername, 
							@Param("idcard")String idcard, @Param("protocolname")String protocolname, 
							@Param("st")String st, @Param("et")String et,
							Pageable pageable);
	
	@Query(value = "SELECT "
			+"detail.nm 				nm, "				//记录内码
			+"owner.nm 					ownernm, "			//权属人内码
			+"owner.name 				owner, "			//权属人
			+"movetype.nm 				movetypenm, "		//安置类型字典内码
			+"movetype.name 			movetype, "			//安置类型
			+"protocol.protocolCode 	protocolcode, "		//协议编号
			+"protocol.protocolName 	protocolname, "		//协议名称
			+"detail.protocol_type 		protocoltype, "		//协议类型
			+"protocol.protocolAmount 	protocolamount, "	//协议总额
			+"detail.apply_amount 		applyamount, "		//申请金额
			+"detail.apply_batch 		applybatch, "		//申请批次
			+"detail.apply_time 		applytime, "		//申请时间
			+"detail.payee 				payee, "			//收款人
			+"detail.bank_card 			bankcard, "			//银行卡号
			+"detail.deposit_bank 		depositbank, "		//开户银行
			+"paidmode.nm 				paidmodenm, "		//支付方式内码
			+"paidmode.name 			paidmode, "			//支付方式
			+"detail.proposer 			proposer, "			//申办人
			+"detail.proposer_dept  	proposerdept, "		//申办人单位
			+"detail.process_id 		processid, "		//流程实例ID
			+"bpm.status 				status, "			//流程处理状态
			+"bpm.cn_status 			cnstatus "			//流程处理状态明文
			/*
			 * 	Approved	同意
				Deleted		已取消
				Rejected	拒绝
				Standby		处理中
			 */
			
			+"from t_abm_payment_detail 	detail  "
			+"LEFT JOIN t_bpm_process 		bpm 		on detail.process_id = bpm.process_id "
			+"LEFT JOIN t_info_owner_impl 	owner 		on owner.nm = detail.owner_nm  "
			+"LEFT JOIN pub_region 			region 		on owner.region = region.city_code "
			+"LEFT JOIN v_protocol_finish 	protocol 	on protocol.protocolCode = detail.protocol_code and protocol.protocolType = detail.protocol_type "
			+"LEFT JOIN pub_dict_value 		movetype 	on movetype.listnm_sys_dict_cate = 'dict_move_type' and movetype.nm = owner.place_type  "
			+"LEFT JOIN pub_dict_value 		paidmode 	on paidmode.listnm_sys_dict_cate = 'dict_paid_mode' and paidmode.nm = detail.payment_method   "
			+"where detail.process_id = :processId "
			,nativeQuery = true)
	ApplyAuditVO getDetailByProcessId(@Param("processId")String processId);
	
	PaymentDetailEntity findByNm(String nm);
	
	PaymentDetailEntity findByProcessId(String processId);

	@Query(value = "SELECT * from t_abm_payment_detail where nm in (:nms);",nativeQuery = true)
	List<PaymentDetailEntity> findByNms(@Param("nms")List<String> nms);

	List<PaymentDetailEntity> findByConfirmationBatch(String confirmationBatch);
	
	@Modifying
	@Query(value = "UPDATE t_abm_payment_detail set confirmation_batch = null where confirmation_batch = :confirmationBatch",nativeQuery = true)
	Integer updateConfirmationBatchAsNull(@Param("confirmationBatch")String confirmationBatch);

}
