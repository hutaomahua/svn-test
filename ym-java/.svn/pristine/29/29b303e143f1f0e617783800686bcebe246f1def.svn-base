package com.lyht.business.abm.removal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.removal.vo.AbmAggregateChatVO;

public interface AbmAggregateDao extends JpaRepository<AbmOwnerEntity, Integer> {

	/**
	 * 按模块统计总户数|已完成户数|未完成户数
	 * 
	 * @return
	 */
	@Query(value = "SELECT" 
			+ " '搬迁人口安置界定' AS name, " 
			+ " tb.*," 
			+ " tb.total -  tb.completed AS incomplete" 
			+ " FROM ("
			+ " SELECT " 
			+ " COUNT(1) AS total," 
			+ " IFNULL(SUM((SELECT 1 FROM t_abm_move_process tamp"
			+ " INNER JOIN t_bpm_process tbp ON tamp.process_id = tbp.process_id"
			+ " WHERE tbp.status = 'Approved' AND tamp.owner_nm = tt.nm" 
			+ " GROUP BY tamp.owner_nm"
			+ " )),0) AS completed" 
			+ " FROM t_info_owner_impl tt" 
			+ " ) AS tb" 
			+ " UNION ALL " 
			+ " SELECT "
			+ " '生产安置人口界定' AS name, " 
			+ " tb.*," 
			+ " tb.total -  tb.completed AS incomplete" 
			+ " FROM (" 
			+ " SELECT "
			+ " COUNT(1) AS total," 
			+ " SUM(IF(tt.is_produce = '2', 1, 0)) AS completed" 
			+ " FROM t_info_owner_impl tt"
			+ " ) AS tb" 
			+ " UNION ALL " 
			+ " SELECT " 
			+ " '个人财产费用计算' AS name, " 
			+ " tb.*,"
			+ " tb.total -  tb.completed AS incomplete" 
			+ " FROM (" 
			+ " SELECT " 
			+ " COUNT(1) AS total,"
			+ " IFNULL(SUM((SELECT 1 FROM t_compensation_cost tcc" 
			+ " WHERE tcc.owner_nm = tt.nm AND tcc.status = 2"
			+ " GROUP BY tcc.owner_nm" 
			+ " )),0) AS completed" 
			+ " FROM t_info_owner_impl tt" 
			+ " ) AS tb" 
			+ " UNION ALL "
			+ " SELECT " 
			+ " '个人财产协议签订' AS name, " 
			+ " tb.*," 
			+ " tb.total -  tb.completed AS incomplete" 
			+ " FROM ("
			+ " SELECT " 
			+ " COUNT(1) AS total," 
			+ " IFNULL(SUM((SELECT 1 FROM t_abm_total_state tats"
			+ " WHERE tats.owner_nm = tt.nm AND tats.protocol_state = 1" 
			+ " )),0) AS completed"
			+ " FROM t_info_owner_impl tt" 
			+ " ) AS tb" 
			+ " UNION ALL " 
			+ " SELECT " 
			+ " '个人财产费用兑付' AS name, " 
			+ " tb.*,"
			+ " tb.total -  tb.completed AS incomplete" 
			+ " FROM (" 
			+ " SELECT " 
			+ " COUNT(1) AS total,"
			+ " IFNULL(SUM((SELECT 1 FROM t_abm_payment_info tapi "
			+ " INNER JOIN t_abm_total_state tats ON tapi.owner_nm = tats.owner_nm"
			+ " WHERE tapi.owner_nm = tt.nm AND tats.protocol_state = 1" 
			+ " GROUP BY tapi.owner_nm"
			+ " HAVING IFNULL(SUM(tapi.protocol_surplus),0.0000) = IFNULL(SUM(tapi.protocol_real_payed),0.0000)"
			+ " )),0) AS completed" 
			+ " FROM t_info_owner_impl tt" 
			+ " ) AS tb", nativeQuery = true)
	List<AbmAggregateChatVO> findAggregateChat();

}