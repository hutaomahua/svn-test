package com.lyht.business.info.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.info.entity.InfoEnterpriseEntity;
import com.lyht.business.info.vo.InfoAggregateCardVO;
import com.lyht.business.info.vo.InfoEnterpriseDetailVO;

public interface InfoEnterpriseDao extends JpaRepository<InfoEnterpriseEntity, Integer> {
	
	@Query(value = "SELECT "
			+ " tt.id AS id,tt.nm AS nm,tt.register_number AS registerNumber, tt.name AS name,"
			+ " tt.oper_cond AS operCond, tt.register_money AS registerMoney, tt.register_time AS registerTime,"
			+ " tt.region AS region, tt.scope AS scope, tt.stage AS stage,"
			+ " REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS regionValue, pdv1.name AS scopeValue "
			+ " FROM t_info_enterprise tt"
			+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " WHERE tt.nm = :nm", nativeQuery = true)
	public InfoEnterpriseDetailVO findOneByNm(String nm);
	
	/**
	 * 实物指标汇总--企事业单位
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT" + " pdv.name AS project," + " '个' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',1,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',1,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',1,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',1,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',1,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',1,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',1,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM pub_dict_value pdv"
			+ " LEFT JOIN t_info_enterprise tt ON tt.enter_type = pdv.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pdv.listnm_sys_dict_cate = 'dict_enterprise_type'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " GROUP BY pdv.nm"
			+ " UNION ALL" + " SELECT" + " pdv.name AS project," + " '个' AS unit," + " 0 AS pivotTotal, "
			+ " 0 AS reservoirTotal, " + " 0 AS flood, " + " 0 AS influence, " + " 0 AS raise, " + " 0 AS temporary, "
			+ " 0 AS permanent, " + " 0 AS newTown " + " FROM pub_dict_value pdv" + " WHERE 1=1"
			+ " AND pdv.listnm_sys_dict_cate = 'dict_enterprise_type'" + " AND pdv.nm NOT IN(" + " SELECT" + " pdv.nm"
			+ " FROM pub_dict_value pdv" + " LEFT JOIN t_info_enterprise tt ON tt.enter_type = pdv.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pdv.listnm_sys_dict_cate = 'dict_enterprise_type'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " GROUP BY pdv.nm"
			+ " )" + " GROUP BY pdv.nm", nativeQuery = true)
	List<InfoAggregateCardVO> findEnterAggregate(@Param("mergerName") String mergerName);
	
}