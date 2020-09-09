package com.lyht.business.pub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.pub.entity.PubLayerEntity;

public interface PubLayerDao extends JpaRepository<PubLayerEntity, Integer> {
	
	@Query(value="SELECT * FROM pub_layer"
			+ " WHERE IF (:name is not null, name LIKE CONCAT('%',:name,'%'), 1=1) "
			,nativeQuery = true)
	public List<PubLayerEntity> findAllByName(@Param("name")String name);
	
}
