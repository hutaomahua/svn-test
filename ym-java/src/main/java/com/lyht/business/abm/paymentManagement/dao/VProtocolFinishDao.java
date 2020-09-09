package com.lyht.business.abm.paymentManagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.paymentManagement.entity.VProtocolFinish;
import com.lyht.business.abm.paymentManagement.vo.OwnerSelectVO;

public interface VProtocolFinishDao extends JpaRepository<VProtocolFinish, Integer> {

	VProtocolFinish findByProtocolCode(String protocolCode);


	@Query(value = "SELECT * from v_protocol_finish v where 1 = 1 "
			+ "and if(:ownerNm is not null and :ownerNm != '', v.ownerNm = :ownerNm, 1 = 1)"
			+ "and v.protocolType in :protocolTypeSection ",nativeQuery = true)
	List<VProtocolFinish> getByOwnerNmAndProtocolType(@Param("ownerNm")String ownerNm, @Param("protocolTypeSection")List<String> protocolTypeSection);

	/**
	 * 查询资金代管协议数
	 * @param ownernm
	 */
	@Query(value = "SELECT count(*) num from v_protocol_finish where ownerNm = :ownerNm and protocolType = 1",nativeQuery = true)
	Integer getEscrowProtocolCount(@Param("ownerNm")String ownerNm);

	/**
	 * 根据协议编码与类型精确查询
	 * @param ownernm
	 */
	@Query(value = "SELECT * from v_protocol_finish where protocolCode = :protocolCode and protocolType = :protocolType",nativeQuery = true)
	VProtocolFinish getByCodeAndFlag(@Param("protocolCode")String protocolCode, @Param("protocolType")Integer protocolType);
	
	/**
	 * 查询可发起兑付申请的权属人
	 * @param protocolTyoe 
	 * @return
	 */
	@Query(value = "SELECT owner.nm nm,owner.name name,"
			+"count(1) - ifnull((SELECT count(1) from (SELECT d.owner_nm owner_nm FROM t_abm_payment_detail d LEFT JOIN 	t_bpm_process p on p.process_id = d.process_id where 1 = 1 AND (p.`status` = 'Approved' or p.`status` = 'Standby')) t where t.owner_nm = v.ownernm group by t.owner_nm ),0) count "
		+"from v_protocol_finish v "
		+"LEFT JOIN t_info_owner_impl owner on v.ownernm = owner.nm "
		+"where 1 = 1 "
		+"and if(:protocolTyoe is not null and :protocolTyoe != '', v.protocolType in (:protocolTyoe), 1 = 1) "
		+"group by ownernm "
		+"HAVING count > 0 ",nativeQuery = true)
	List<OwnerSelectVO> getOwnerSelectList(@Param("protocolTyoe")String protocolTyoe); 

}
