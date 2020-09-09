package com.lyht.business.fund.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyht.business.fund.entity.FundEngineering;

@Repository
public interface FundEngineeringDao extends JpaRepository<FundEngineering, Integer>{

}