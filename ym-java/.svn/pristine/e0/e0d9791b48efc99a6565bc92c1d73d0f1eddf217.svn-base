package com.lyht.business.abm.wechat.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lyht.business.abm.removal.entity.AbmLandEntity;

public interface AbmWechatLandDao extends JpaRepository<AbmLandEntity, Integer> {

	@Query(value = "SELECT" + " IFNULL(SUM(tt.area),0)" + " FROM t_info_land_impl tt", nativeQuery = true)
	BigDecimal findTotalArea();

}