package com.lyht.business.abm.paymentManagement.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.paymentManagement.entity.PaymentConfirmationBatch;
import com.lyht.business.abm.paymentManagement.vo.PaymentConfirmationBatchVO;

public interface PaymentConfirmationDao extends JpaRepository<PaymentConfirmationBatch, Integer> {

	@Query(value = "SELECT "
			+ "batch.nm 			nm, "
			+ "batch.region 		region, "
			+ "batch.batch_num 		batchnm, "
			+ "batch.proposer 		proposer, "
			+ "batch.confirm_time	confirmtime, "
			+ "batch.process_id		processid, "
			+ "batch.remark			remark, "
			+ "detail.count			protocolnum, "
			+ "detail.sum 			protocolamount "
			+ "from t_abm_payment_confirmation_batch batch "
				+ "LEFT JOIN "
					+ "(SELECT "
					+ "count(1) count, "
					+ "sum(apply_amount) sum, "
					+ "confirmation_batch confirmation_batch "
					+ "from t_abm_payment_detail GROUP BY confirmation_batch) detail "
				+ "on batch.nm = detail.confirmation_batch",nativeQuery = true)
	List<PaymentConfirmationBatchVO> batchList();

	@Query(value = "SELECT "
			+ "batch.nm 			nm, "
			+ "batch.region 		region, "
			+ "batch.batch_num 		batchnm, "
			+ "batch.proposer 		proposer, "
			+ "batch.confirm_time	confirmtime, "
			+ "batch.process_id		processid, "
			+ "process.status 		status, "
			+ "process.cn_status 	cnstatus, "
			+ "batch.remark			remark, "
			+ "detail.count			protocolnum, "
			+ "detail.sum 			protocolamount "
			+ "from t_abm_payment_confirmation_batch batch "
				+ "LEFT JOIN "
					+ "(SELECT "
					+ "count(1) count, "
					+ "sum(apply_amount) sum, "
					+ "confirmation_batch confirmation_batch "
					+ "from t_abm_payment_detail GROUP BY confirmation_batch) detail "
				+ "on batch.nm = detail.confirmation_batch "
				+ "LEFT JOIN pub_region 	region 	on region.city_code 	= batch.region "
				+ "LEFT JOIN t_bpm_process 	process on process.process_id 	= batch.process_id "
				+ "LEFT JOIN "
					+ "(SELECT owner.name ownername,owner.id_card idcard,protocol.protocolName protocolname,detail.confirmation_batch confirmationbatch from t_abm_payment_detail detail "
					+ "LEFT JOIN v_protocol_finish protocol on protocol.protocolCode = detail.protocol_code and protocol.protocolType = detail.protocol_type "
					+ "LEFT JOIN t_info_owner_impl owner on owner.nm = detail.owner_nm "
					+ "WHERE 1 = 1 "
					+ "AND detail.confirmation_batch is not null and detail.confirmation_batch != '' "
					+ "and if(:ownername 	is not null and :ownername 		!= '', owner.name 				like CONCAT('%',:ownername,'%')		, 1 = 1) "
					+ "and if(:idcard 		is not null and :idcard 		!= '', owner.id_card 			like CONCAT('%',:idcard,'%')		, 1 = 1) "
					+ "and if(:protocolname is not null and :protocolname 	!= '', protocol.protocolName 	like CONCAT('%',:protocolname,'%')	, 1 = 1) "
					+ "GROUP BY detail.confirmation_batch) ownerprotocol "
				+ "on ownerprotocol.confirmationbatch = batch.nm "
			+ "where 1 = 1 "
			+ "and if(:region 		is not null and :region			!= '', REPLACE(region.merger_name,',','')	like CONCAT('%',:region,'%'), 1 = 1) "
			+ "and if(:batchnum 	is not null and :batchnum 		!= '', batch.batch_num		 				like CONCAT('%',:batchnum,'%'), 1 = 1) "
			+ "and if(:ownername 	is not null and :ownername 		!= '', ownerprotocol.ownername 				like CONCAT('%',:ownername,'%'), 1 = 1) "
			+ "and if(:idcard 		is not null and :idcard 		!= '', ownerprotocol.idcard 				like CONCAT('%',:idcard,'%'), 1 = 1) "
			+ "and if(:protocolname is not null and :protocolname 	!= '', ownerprotocol.protocolname 			like CONCAT('%',:protocolname,'%'), 1 = 1) "
			+ "and if(:st 			is not null and :st 			!= '', DATE_FORMAT(batch.confirm_time,'%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(:st,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
			+ "and if(:et 			is not null and :et 			!= '', DATE_FORMAT(batch.confirm_time,'%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(:et,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
			,
	countQuery = "SELECT "
			+ "count(1) "
			+ "from t_abm_payment_confirmation_batch batch "
			+ "LEFT JOIN "
				+ "(SELECT "
					+ "count(1) count, "
					+ "sum(apply_amount) sum, "
					+ "confirmation_batch confirmation_batch "
					+ "from t_abm_payment_detail GROUP BY confirmation_batch) detail "
				+ "on batch.nm = detail.confirmation_batch "
				+ "LEFT JOIN pub_region 	region 	on region.city_code 	= batch.region "
				+ "LEFT JOIN t_bpm_process 	process on process.process_id 	= batch.process_id "
				+ "LEFT JOIN "
					+ "(SELECT owner.name ownername,owner.id_card idcard,protocol.protocolName protocolname,detail.confirmation_batch confirmationbatch from t_abm_payment_detail detail "
					+ "LEFT JOIN v_protocol_finish protocol on protocol.protocolCode = detail.protocol_code and protocol.protocolType = detail.protocol_type "
					+ "LEFT JOIN t_info_owner_impl owner on owner.nm = detail.owner_nm "
					+ "WHERE 1 = 1 "
					+ "AND detail.confirmation_batch is not null and detail.confirmation_batch != '' "
					+ "and if(:ownername 	is not null and :ownername 		!= '', owner.name 				like CONCAT('%',:ownername,'%')		, 1 = 1) "
					+ "and if(:idcard 		is not null and :idcard 		!= '', owner.id_card 			like CONCAT('%',:idcard,'%')		, 1 = 1) "
					+ "and if(:protocolname is not null and :protocolname 	!= '', protocol.protocolName 	like CONCAT('%',:protocolname,'%')	, 1 = 1) "
					+ "GROUP BY detail.confirmation_batch) ownerprotocol "
				+ "on ownerprotocol.confirmationbatch = batch.nm "
			+ "where 1 = 1 "
			+ "and if(:region 		is not null and :region			!= '', REPLACE(region.merger_name,',','')	like CONCAT('%',:region,'%'), 1 = 1) "
			+ "and if(:batchnum 	is not null and :batchnum 		!= '', batch.batch_num		 				like CONCAT('%',:batchnum,'%'), 1 = 1) "
			+ "and if(:ownername 	is not null and :ownername 		!= '', ownerprotocol.ownername 				like CONCAT('%',:ownername,'%'), 1 = 1) "
			+ "and if(:idcard 		is not null and :idcard 		!= '', ownerprotocol.idcard 				like CONCAT('%',:idcard,'%'), 1 = 1) "
			+ "and if(:protocolname is not null and :protocolname 	!= '', ownerprotocol.protocolname 			like CONCAT('%',:protocolname,'%'), 1 = 1) "
			+ "and if(:st 			is not null and :st 			!= '', DATE_FORMAT(batch.confirm_time,'%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(:st,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
			+ "and if(:et 			is not null and :et 			!= '', DATE_FORMAT(batch.confirm_time,'%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(:et,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
			,
	nativeQuery = true)
	Page<PaymentConfirmationBatchVO> infoPage(@Param("region")String region,@Param("batchnum")String batchnum,@Param("ownername")String ownername,
			@Param("idcard")String idcard,@Param("protocolname")String protocolname,
			@Param("st")String st,@Param("et")String et, Pageable pageable);

	PaymentConfirmationBatch getByNm(String nm);
	Integer deleteByNm(String nm);

	PaymentConfirmationBatch getByProcessId(String processId);

}
