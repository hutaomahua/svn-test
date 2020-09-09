package com.lyht.business.abm.settle.dao;

import java.util.Date;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.lyht.business.abm.settle.entity.SettleInfo;

/**
* @author HuangTianhao
* @date 2019年11月28日 
*/

public interface SettleInfoDao extends JpaRepository<SettleInfo, Integer> {
	//条件分页查询
	  @Query(value =  "select * from t_abm_construction_settle_info info "
	  		+ "where 1=1 and "
	  		+ "IF (:region is not null,1=1,1=1) and "
	  		+ "IF (:regionType is not null,info.region_type = :regionType,1=1) and "
	  		+ "IF (:setttleType is not null,info.setttle_type =:setttleType,1=1) and "
	  		//+ "info.diff =:diff and "
	  		+ "IF (:name is not null,info.name LIKE concat('%',:name,'%'),1=1) "
	  		+ "order by  info.name asc",nativeQuery = true)
	    Page<Map> getList(Pageable pageable, @Param("name") String name, @Param("region") String region, @Param("regionType") String regionType
	    		, @Param("setttleType") String setttleType);

	//name为null查询居民点名，非null判断存在
	  @Query(value = "select * from t_abm_construction_settle_info info "
	  		+ "where 1=1 and "
	  	//	+ "info.diff =:diff and "
	  		+ "IF (:name is not null,info.name = :name,1=1)",nativeQuery = true)
	  List<SettleInfo> getNameList(@Param("name")String name);
	  
	 //根据name返回nm
	  @Query(value = "select nm from t_abm_construction_settle_info info "
	  		+ "where 1=1 and "
	  	//	+ "info.diff =:diff and "
		  	+ "IF (:name is not null,info.name = :name,1=1)",nativeQuery = true)
	   String getNm(@Param("name")String name);
}    
