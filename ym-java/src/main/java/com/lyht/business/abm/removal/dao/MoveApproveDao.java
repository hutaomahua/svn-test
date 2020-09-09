package com.lyht.business.abm.removal.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lyht.business.abm.removal.entity.AbmHouseEntity;
import com.lyht.business.abm.removal.entity.MoveApproveEntity;

public interface MoveApproveDao extends JpaRepository<MoveApproveEntity,Integer> {

}
