package com.lyht.business.abm.protocol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.protocol.entity.BankCradInfo;

@Repository
public interface BankCradInfoDao extends JpaRepository<BankCradInfo, Integer>{

	@Query(value = "SELECT nm FROM t_bank_crad_info WHERE BANK_NM = :bank and bank_number = :number",nativeQuery = true)
	String findByBankNumberAndBank(@Param("number")String bankNumber,@Param("bank")String bank);
	
}