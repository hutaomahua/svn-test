package com.lyht.business.abm.removal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmHomesteadEntity;
import com.lyht.business.abm.removal.vo.AbmAggregateCardVO;
import com.lyht.business.abm.removal.vo.AbmHomesteadVO;

public interface AbmHomesteadDao extends JpaRepository<AbmHomesteadEntity, Integer> {

	/**
	 * 实物指标汇总--宅基地
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT" + " '宅基地' AS project," + " '㎡' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',tt.area,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',tt.area,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',tt.area,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',tt.area,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',tt.area,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',tt.area,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',tt.area,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',tt.area,0)),0) AS newTown " + " FROM t_info_homestead_impl tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)", nativeQuery = true)
	List<AbmAggregateCardVO> findHomesteadAggregate(@Param("mergerName") String mergerName);
	
	@Query(value = "select tt.id,tt.nm,tt.project_nm projectNm,t1.`name` projectName,tt.region,t2.merger_name mergerName,tt.status,tt.owner_nature ownerNature,t5.name ownerNatureName, " + 
			"tt.remark,tt.stage,tt.zblx,t3.name zblxName,tt.scope,t4.name scopeName,tt.owner_nm ownerNm,tt.area,tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap, (select count(1) from pub_files where table_pk_column = tt.nm) as fileCount " + 
			"from t_info_homestead_impl tt  " + 
			"left join pub_project t1 on tt.project_nm = t1.id " + 
			"left join pub_region t2 on tt.region = t2.city_code " + 
			"left join pub_dict_value t3 on tt.zblx = t3.nm " + 
			"left join pub_dict_value t4 on tt.scope = t4.nm " + 
			"left join pub_dict_value t5 on tt.owner_nature = t5.nm " + 
			"where tt.owner_nm = ?1 " + 
			"",nativeQuery = true)
	List<Map<String,Object>> list(String ownerNm);
	
	@Query(value = "select tt.id,tt.nm,tt.project_nm projectNm,t1.`name` projectName,tt.region,t2.merger_name mergerName,tt.status,tt.owner_nature ownerNature,t5.name ownerNatureName, " + 
			"tt.remark,tt.stage,tt.zblx,t3.name zblxName,tt.scope,t4.name scopeName,tt.owner_nm ownerNm,tt.area,tt.lgtd,tt.lttd,tt.altd,tt.in_map inMap, (select count(1) from pub_files where table_pk_column = tt.nm) as fileCount " + 
			"from t_info_homestead_impl tt  " + 
			"left join pub_project t1 on tt.project_nm = t1.id " + 
			"left join pub_region t2 on tt.region = t2.city_code " + 
			"left join pub_dict_value t3 on tt.zblx = t3.nm " + 
			"left join pub_dict_value t4 on tt.scope = t4.nm " + 
			"left join pub_dict_value t5 on tt.owner_nature = t5.nm " + 
			"where tt.owner_nm = ?1 " + 
			"",nativeQuery = true)
	Page<AbmHomesteadVO> page(String ownerNm,Pageable pageable);
	
	List<AbmHomesteadEntity> findByOwnerNm(String ownerNm);

}