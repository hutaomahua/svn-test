package com.lyht.business.abm.removal.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmEnterBuildingEntity;
import com.lyht.business.abm.removal.vo.PersonaWealthBuildingVO;

public interface AbmEnterBuildingDao extends JpaRepository<AbmEnterBuildingEntity, Integer> {

}
