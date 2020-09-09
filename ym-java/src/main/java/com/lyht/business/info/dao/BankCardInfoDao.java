package com.lyht.business.info.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lyht.business.info.entity.BankCardInfoEntity;
import com.lyht.business.info.vo.BankVO;

public interface BankCardInfoDao extends JpaRepository<BankCardInfoEntity, Integer> {

	/**
	 * 获取字典表中的银行列表
	 */
	@Query(value = "SELECT nm,name from pub_dict_value where listnm_sys_dict_cate = 'dict_bank'",nativeQuery = true)
	List<BankVO> getBankDict();

}
