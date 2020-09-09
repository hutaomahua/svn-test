package com.lyht.business.abm.wechat.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lyht.business.abm.removal.entity.AbmHouseEntity;

public interface AbmWechatHouseDao extends JpaRepository<AbmHouseEntity, Integer> {

	@Query(value = "SELECT" + " IFNULL(SUM(tt.area),0)" + " FROM t_info_houses_impl tt", nativeQuery = true)
	BigDecimal findTotalArea();

}