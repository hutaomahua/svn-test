package com.lyht.business.abm.paymentManagement.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.paymentManagement.entity.PaymentInfoEntity;
import com.lyht.business.abm.paymentManagement.vo.BankCardVO;
import com.lyht.business.abm.paymentManagement.vo.InfoListVO;
import com.lyht.business.abm.paymentManagement.vo.OwnerInfoVO;
import com.lyht.business.abm.paymentManagement.vo.OwnerPaymentInfoVO;
import com.lyht.business.abm.paymentManagement.vo.PaymentMethodVO;

public interface PaymentInfoDao extends JpaRepository<PaymentInfoEntity, Integer>  {
	
	/**
     * 根据权属人内码查询发起兑付的协议列表
     * @return
     */
	List<PaymentInfoEntity> findByOwnerNm(String ownerNm);

	/**
	 * 查询权属人兑付情况
	 * @param ownerName	户主姓名
	 * @param idCard	身份证号码
	 * @param region	行政区
	 * @param ownerNm 
	 * @param pageable 
	 * @return
	 */
	@Query(value="SELECT owner.nm 			ownernm, "
		+"owner.name 						ownername, "
		+"owner.id_card 					idcard, "
		+"owner.region 						region, "
		+"region.name						regionname,"
		+"region.merger_name				regionmergername,"
		+"paymentInfo.count					count, "
		+"ROUND(paymentInfo.protocol_amount,2)		protocolamount, "
		+"ROUND(paymentInfo.protocol_payed,2)		protocolpayed, "
		+"ROUND(paymentInfo.protocol_real_payed,2)	protocolrealpayed, "
		+"paymentInfo.applying				applying, "
		+"paymentInfo.paying				paying  "
		+"FROM t_info_owner_impl owner  "
		+"RIGHT JOIN (SELECT  "
			+"owner_nm,count(1) count, "
			+"IFNULL(sum(protocol_amount),0) protocol_amount, "
			+"IFNULL(sum(protocol_payed),0) protocol_payed, "
			+"IFNULL(sum(protocol_real_payed),0) protocol_real_payed, "
			+"sum(protocol_amount > IFNULL(protocol_surplus,0)) applying, "
			+"sum(protocol_amount > IFNULL(protocol_real_payed,0)) paying  "
			+"from t_abm_payment_info GROUP BY owner_nm ORDER BY id DESC) paymentInfo on owner.nm = paymentInfo.owner_nm "
		+"LEFT JOIN pub_region 			region 		on owner.region = region.city_code "
		+"where 1 = 1 "
		+"and if(:ownerName is not null and :ownerName 	!= '', owner.name 				like CONCAT('%',:ownerName,'%'), 1 = 1) "
		+"and if(:idCard 	is not null and :idCard 	!= '', owner.id_card 			like CONCAT('%',:idCard,'%') , 1 = 1) "
		+"and if(:region 	is not null and :region 	!= '', region.merger_name 		like CONCAT('%',:region,'%'), 1 = 1) ",
		countQuery = "SELECT count(1) "
			+"FROM t_info_owner_impl owner  "
			+"RIGHT JOIN (SELECT  "
			+"owner_nm,count(1) count, "
			+"IFNULL(sum(protocol_amount),0) protocol_amount, "
			+"IFNULL(sum(protocol_payed),0) protocol_payed, "
			+"IFNULL(sum(protocol_real_payed),0) protocol_real_payed, "
			+"sum(protocol_amount > IFNULL(protocol_surplus,0)) applying, "
			+"sum(protocol_amount > IFNULL(protocol_real_payed,0)) paying  "
			+"from t_abm_payment_info GROUP BY owner_nm ORDER BY id DESC) paymentInfo on owner.nm = paymentInfo.owner_nm "
		+"LEFT JOIN pub_region 			region 		on owner.region = region.city_code "
		+"where 1 = 1 "
		+"and if(:ownerName is not null and :ownerName 	!= '', owner.name 				like CONCAT('%',:ownerName,'%'), 1 = 1) "
		+"and if(:idCard 	is not null and :idCard 	!= '', owner.id_card 			like CONCAT('%',:idCard,'%') , 1 = 1) "
		+"and if(:region 	is not null and :region 	!= '', region.merger_name 		like CONCAT('%',:region,'%'), 1 = 1) ",
		nativeQuery = true)
	Page<OwnerPaymentInfoVO> ownerPaymentInfoList(
			@Param("ownerName")String ownerName,
			@Param("idCard")String idCard,
			@Param("region")String region, 
			Pageable pageable);
	
	/**
	 * 查询权属人信息
	 * @param ownerNm
	 * @return
	 */
	@Query(value="SELECT  "
			+"tio.nm as nm, "
			+"tio.name as name, "
			+"tio.region as region, "
			+"tio.place_type as placetypenm, "
			+"dict.code as placetypecode, "
			+"dict.name as placetypename  "
			+"from t_info_owner_impl tio  "
			+"LEFT JOIN pub_dict_value dict on dict.listnm_sys_dict_cate = 'dict_move_type' and dict.nm = tio.place_type  "
			+"where tio.nm = :ownerNm ",nativeQuery = true)
	OwnerInfoVO ownerInfo(@Param("ownerNm")String ownerNm);

	/**
	 * 查询权属人银行卡信息
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT "
			+"bc.bank_nm 		as banknm, "
			+"bc.bank_number 	as banknumber, "
			+"dict.name 		as bankname, "
			+"bc.open_bank 		as openbank, "
			+"bank_owner 		as bankowner, "
			+"bc.bank_phone 	as bankphone  "
			+"from t_bank_crad_info bc "
			+"LEFT JOIN pub_dict_value dict on dict.listnm_sys_dict_cate = 'dict_bank' and dict.nm = bc.bank_nm "
			+"where bc.owner_nm = :ownerNm ",nativeQuery = true)
	List<BankCardVO> queryBankCardByOwnerNm(@Param("ownerNm")String ownerNm);

	/**
	 * 获取字典表中的支付方式列表
	 */
	@Query(value = "SELECT nm,name from pub_dict_value where listnm_sys_dict_cate = 'dict_paid_mode'",nativeQuery = true)
	List<PaymentMethodVO> getPaymentMethodDict();

	//根据协议类型与编号查询
	@Query(value = "SELECT * from t_abm_payment_info where protocol_code = :protocolCode and protocol_type = :protocolType",nativeQuery = true)
	PaymentInfoEntity findByCodeAndType(@Param("protocolCode")String protocolCode, @Param("protocolType")Integer protocolType);

	/**
	 * 查询全部行政区
	 */
	@Query(value = "SELECT  "
			+"region.name 					region, "
			+"region.city_code 				citycode, "
			+"region.parent_code 			parentcode, "
			+"region.level 					regionlevel "
			+"from t_abm_payment_info info "
			+"LEFT JOIN t_info_owner_impl owner on info.owner_nm = owner.nm "
			+"LEFT JOIN pub_region region on owner.region = region.city_code "
			+"GROUP BY region.city_code "
			+"HAVING region.city_code is not null",nativeQuery = true)
	List<Map<String,Object>> finAllRegion();
	
	/**
	 * 查询全部数据并按行政区分组
	 */
	@Query(value = "SELECT  "
			+"region.name 										region, "
			+"region.city_code 									citycode, "
			+"region.parent_code 								parentcode, "
			+"region.level 										regionlevel,"
			+"IFNULL(count(distinct owner.nm)			,0)		households,"
			+"ROUND(IFNULL(sum(info.protocol_amount)	,0),2) 	amount, "
			+"ROUND(IFNULL(sum(info.protocol_payed)		,0),2) 	payed, "
			+"ROUND(IFNULL(sum(info.protocol_surplus)	,0),2) 	surplus,  "
			+"ROUND(IFNULL(sum(info.protocol_real_payed),0),2) 	realpayed, "
			+"ROUND(IFNULL(sum(info.protocol_payed - ifnull(info.protocol_real_payed,0)),0),2) realsurplus "
			+"FROM t_abm_payment_info info "
			+"LEFT JOIN t_info_owner_impl owner on info.owner_nm = owner.nm "
			+"LEFT JOIN pub_region region on owner.region = region.city_code "
			+"GROUP BY region.city_code "
			+"HAVING region.city_code is not null",nativeQuery = true)
	List<Map<String,Object>> finAllGroupByRegion();
	
	/**
	 * 查询协议信息列表
	 * @param protocolName 
	 * @param ownerName 
	 * @param region 
	 */
	@Query(value = "SELECT "
			+"info.nm nm, "
			+"owner.nm 					ownernm, "
			+"owner.name 				owner, "
			+"movetype.nm 				movetypenm, "
			+"movetype.name 			movetype, "
			+"protocol.protocolName 	protocolname, "
			+"protocol.protocolCode 	protocolcode, "
			+"ROUND(info.protocol_amount,2) 		protocolamount, "
			+"ROUND(info.protocol_payed,2)		protocolpayed, "
			+"ROUND(info.protocol_surplus,2) 		protocolsurplus, "
			+"info.protocol_type		protocoltype, "
			+"protocol.fileName			filename, "
			+"protocol.fileUrl			fileurl "
			+"FROM t_abm_payment_info info  "
			+"LEFT JOIN t_info_owner_impl owner on owner.nm = info.owner_nm "
			+"LEFT JOIN v_protocol_finish protocol on protocol.protocolCode = info.protocol_code and protocol.protocolType = info.protocol_type  "
			+"LEFT JOIN pub_dict_value 		movetype on movetype.listnm_sys_dict_cate = 'dict_move_type' and movetype.nm = owner.place_type  "
			+"where 1 = 1 "
			+"and if(:region 		is not null and :region 		!= '', owner.region 			= :region , 1 = 1) "
			+"and if(:ownerName 	is not null and :ownerName 		!= '', owner.name 				like CONCAT('%',:ownerName,'%'), 1 = 1) "
			+"and if(:protocolName 	is not null and :protocolName 	!= '', protocol.protocolName 	like CONCAT('%',:protocolName,'%') , 1 = 1) "
			+"ORDER BY ownernm,protocolamount desc",nativeQuery = true)
	List<InfoListVO> infoList(@Param("region")String region, @Param("ownerName")String ownerName, @Param("protocolName")String protocolName);

	/**
	 * 查询协议信息列表分页
	 * @param protocolName 
	 * @param ownerName 
	 * @param region 
	 */
	@Query(value = "SELECT "
			+"protocol.id 				id, "
			+"info.nm 					nm, "
			+"owner.nm 					ownernm, "
			+"owner.name 				owner, "
			+"movetype.nm 				movetypenm, "
			+"movetype.name 			movetype, "
			+"protocol.protocolName 	protocolname, "
			+"protocol.protocolCode 	protocolcode, "
			+"ROUND(info.protocol_amount,2) 		protocolamount, "
			+"ROUND(info.protocol_payed,2)		protocolpayed, "
			+"ROUND(info.protocol_surplus,2)		protocolsurplus, "
			+"info.protocol_type		protocoltype, "
			+"info.batch				batch, "
			+"info.is_next				isnext, "
			+"detail.apply_time			applytime, "
			+"protocol.fileName			filename, "
			+"protocol.fileUrl			fileurl, "
			+"bpm.status 				status, "			//流程处理状态
			+"bpm.cn_status 			cnstatus "			//流程处理状态明文
			+"FROM t_abm_payment_info info  "
//			and detail.auditor_state_1 = 2 and detail.auditor_state_2 = 2 and detail.auditor_state_3 = 2 and detail.auditor_state_4 = 2 and detail.auditor_state_5 = 2 and detail.auditor_state_5 = 2 
			+"LEFT JOIN t_abm_payment_detail detail on info.protocol_code = detail.protocol_code and info.protocol_type = detail.protocol_type "
			+"LEFT JOIN t_bpm_process 		bpm 		on detail.process_id = bpm.process_id "
			+"LEFT JOIN t_info_owner_impl 	owner 		on owner.nm 	= info.owner_nm "
			+"LEFT JOIN pub_region 			region 		on owner.region = region.city_code "
			+"LEFT JOIN v_protocol_finish 	protocol 	on protocol.protocolCode = info.protocol_code and protocol.protocolType = info.protocol_type  "
			+"LEFT JOIN pub_dict_value 		movetype 	on movetype.listnm_sys_dict_cate = 'dict_move_type' and movetype.nm = owner.place_type  "
			+"where 1 = 1 "
			+"and detail.process_id in (select process_id from t_bpm_process bpm where bpm.status = 'Approved') "
			+"and info.batch = detail.apply_batch "
			+"and owner.nm = :ownernm "
			+"and if(:region 		is not null and :region 		!= '', region.merger_name 		like CONCAT('%',:region,'%'), 1 = 1) "
			+"and if(:ownername		is not null and :ownername 		!= '', owner.name 				like CONCAT('%',:ownername,'%'), 1 = 1) "
			+"and if(:idcard		is not null and :idcard 		!= '', owner.id_card			like CONCAT('%',:idcard,'%'), 1 = 1) "
			+"and if(:protocolname 	is not null and :protocolname 	!= '', protocol.protocolName 	like CONCAT('%',:protocolname,'%') , 1 = 1) "
			+"and if(:st 			is not null and :st 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(:st,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
			+"and if(:et 			is not null and :et 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(:et,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
			+"ORDER BY ownernm,protocolamount desc",
			countQuery = "SELECT "
					+"count(1) "
					+"FROM t_abm_payment_info info  "
//					and detail.auditor_state_1 = 2 and detail.auditor_state_2 = 2 and detail.auditor_state_3 = 2 and detail.auditor_state_4 = 2 and detail.auditor_state_5 = 2 and detail.auditor_state_5 = 2 
					+"LEFT JOIN t_abm_payment_detail detail on info.protocol_code = detail.protocol_code and info.protocol_type = detail.protocol_type "
					+"LEFT JOIN t_info_owner_impl 	owner 		on owner.nm 	= info.owner_nm "
					+"LEFT JOIN pub_region 			region 		on owner.region = region.city_code "
					+"LEFT JOIN v_protocol_finish 	protocol 	on protocol.protocolCode = info.protocol_code and protocol.protocolType = info.protocol_type  "
					+"LEFT JOIN pub_dict_value 		movetype 	on movetype.listnm_sys_dict_cate = 'dict_move_type' and movetype.nm = owner.place_type  "
					+"where 1 = 1 "
					+"and owner.nm = :ownernm "
					+"and if(:region 		is not null and :region 		!= '', region.merger_name 		like CONCAT('%',:region,'%'), 1 = 1) "
					+"and if(:ownername		is not null and :ownername 		!= '', owner.name 				like CONCAT('%',:ownername,'%'), 1 = 1) "
					+"and if(:idcard		is not null and :idcard 		!= '', owner.id_card			like CONCAT('%',:idcard,'%'), 1 = 1) "
					+"and if(:protocolname 	is not null and :protocolname 	!= '', protocol.protocolName 	like CONCAT('%',:protocolname,'%') , 1 = 1) "
					+"and if(:st 			is not null and :st 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(:st,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
					+"and if(:et 			is not null and :et 			!= '', DATE_FORMAT(detail.apply_time,'%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(:et,'%Y-%m-%d %H:%i:%s') , 1 = 1) "
			,nativeQuery = true)
	Page<InfoListVO> infoPage(@Param("ownernm")String ownernm,@Param("region")String region, @Param("ownername")String ownername, 
							@Param("idcard")String idcard, @Param("protocolname")String protocolname, 
							@Param("st")String st, @Param("et")String et,
							Pageable pageable);
	/**
	 * 获取全部征地范围类型
	 */
	@Query(value = "SELECT nm,name,parent_nm parentNm from pub_dict_value dict where dict.listnm_sys_dict_cate = 'dict_limits'" , nativeQuery = true)
	
	List<Map<String,Object>> getAllScope();
	/**
	 * 根据按照征地范围统计兑付申请金额
	 */
	@Query(value = "SELECT "
				+"dict.name				 						scope, "
				+"dict.nm				   						scopeNm, "
				+"count(distinct owner.nm) 	 					households, "
				+"ROUND(IFNULL(sum(info.protocol_amount),0),2)	protocolAmount, "
				+"ROUND(IFNULL(sum(info.protocol_payed),0),2) 	protocolPayed, "
				+"ROUND(IFNULL(sum(info.protocol_surplus),0),2) protocolSurplus "
				+"FROM "
				+"t_abm_payment_info info "
				+"LEFT JOIN t_info_owner_impl owner on owner .nm = info.owner_nm "
				+"LEFT JOIN pub_region 		region 	on owner.region 				= region.city_code "
				+"LEFT JOIN pub_dict_value 	dict 	on dict.listnm_sys_dict_cate 	= 'dict_limits' and dict.nm = owner.scope  "
				+"where 1 = 1 "
				+"and if(:region is not null and :region != '', region.merger_name like CONCAT('%',:region,'%'), 1 = 1) "
				+"GROUP BY owner.scope" , nativeQuery = true)
	List<Map<String,Object>> getStatisticsDetailByRegion(@Param("region")String region);

}
