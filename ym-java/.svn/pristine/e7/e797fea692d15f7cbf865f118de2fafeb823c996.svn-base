package com.lyht.business.fund.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.fund.entity.FundAppropriation;

@Repository
public interface FundAppropriationDao extends JpaRepository<FundAppropriation, Integer> {
	
	@Query(value = "SELECT app.id,app.nm,pro.nm payProjectNm,pro.`name` payProjectName,app.project_name projectName,"
			+ " app.contract_name contractName,app.contract_amount contractAmount,"
			+ " app.pay_time payTime,app.sun_amount sunAmount,app.pay_number payNumber,"
			+ " if(app.is_pay=1,'是','否') isPay,app.is_pay isPayNumber, app.but_the butThe,app.dealing_staff dealingStaff,"
			+ " (select count(1) from pub_files where table_pk_column = app.nm) fujian "
			+ " FROM t_fund_appropriation app "
			+ " left join pub_project pro on app.pay_project_nm = pro.nm"
			+ " where if(:projectName is not null,pro.nm = :projectName,1=1) "
			+ " and if(:word is not null,app.contract_name like CONCAT('%',:word,'%'),1=1)"
			+ " order by app.id desc",countQuery =  "SELECT count(1) "
					+ " FROM t_fund_appropriation app "
					+ " left join pub_project pro on app.pay_project_nm = pro.nm"
					+ " where if(:projectName is not null,pro.nm = :projectName,1=1) "
					+ " and if(:word is not null, app.contract_name like CONCAT('%',:word,'%'),1=1)",
			nativeQuery = true)
	Page<Map> page(@Param("projectName")String projectName,@Param("word")String word,Pageable pageable);
	
}
