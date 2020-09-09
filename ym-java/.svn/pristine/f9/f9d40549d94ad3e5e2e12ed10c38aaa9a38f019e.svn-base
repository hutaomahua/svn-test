package com.lyht.business.fund.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.fund.entity.FundCost;
import com.lyht.business.fund.vo.FundCostVO;

@Repository
public interface FundCostDao extends JpaRepository<FundCost, Integer> {

	@Query(value = "SELECT c.ID id,c.NM nm,c.payment_time paymentTime,c.amount_type amountType,c.AMOUNT amount,"
			+ "c.ORGAN_TYPE organType,c.organ_name organName,c.unit_price unitPrice,"
			+ "c.REMARK remark,dv.name stage,dv.code stageNm,p.`name` projectName,p.nm projectNm, "
			+ " r.`name` region,r.city_code cityCode,r.merger_name mergerName,"
			+ "c.manager manager,"
			+ " v.code areaNm,v.`name` area,(select count(1) from pub_files where table_pk_column = c.nm) fujian FROM t_fund_cost c"
			+ " left join pub_project p on c.project_nm = p.nm" + 
			" left join pub_region r on c.region = r.city_code " + 
			" left join pub_dict_value v on c.area = v.code"
			+ " left join pub_dict_value dv on c.stage = dv.code"
			+ " WHERE amount_type = :amountType "
			+ " and if(:organType is not null,c.organ_type = :organType,1=1) "
			+ " and if(:word is not null,(p.name like CONCAT('%',:word,'%') or c.organ_name like CONCAT('%',:word,'%')),1=1)"
			+ " order by c.id desc", 
			countQuery = "SELECT count(1) "
					+ " FROM t_fund_cost c"
					+ " left join pub_project p on c.project_nm = p.nm" + 
					" left join pub_region r on c.region = r.city_code " + 
					" left join pub_dict_value v on c.area = v.code"
					+ " left join pub_dict_value dv on c.stage = dv.code"
					+ " WHERE amount_type = :amountType "
					+ " and if(:organType is not null,c.organ_type like :organType,1=1) "
					+ " and if(:word is not null,(p.name like CONCAT('%',:word,'%') or c.organ_name like CONCAT('%',:word,'%')),1=1)", nativeQuery = true)
	public Page<FundCostVO> page(@Param("amountType")String amountType,
			@Param("organType")String organType,@Param("word")String word, Pageable pageable);

}
