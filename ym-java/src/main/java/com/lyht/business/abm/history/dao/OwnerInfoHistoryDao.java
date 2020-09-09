package com.lyht.business.abm.history.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.abm.history.entity.OwnerInfoHistory;
import com.lyht.business.abm.history.vo.OwnerInfoHistoryVO;

@Repository
public interface OwnerInfoHistoryDao extends JpaRepository<OwnerInfoHistory, Integer> {

	@Query(value = " select t1.`name` ,t1.id_card AS idCard,tt.info_json infoJson,tt.immigrant_population_json AS immigrantPopulationJson, "
			+ " tt.land_json AS landJson,tt.house_json AS houseJson,tt.tree_json AS treeJson,tt.other_json AS otherJson,tt.fitment_json AS fitmentJson, "
			+ " tt.individual_household_json AS individualHouseholdJson,t2.staff_name AS changeOperatorName,tt.operator_time AS operatorTime "
			+ " from t_abm_owner_info_history tt left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
			+ " LEFT JOIN sys_staff t2 on tt.change_operator = t2.nm  "
			+ " WHERE 1=1 and if(:name is not null, t1.name like concat('%',:name,'%') ,1=1) and if(:nm is not null && :nm != '', tt.owner_nm =:nm ,1=1)"
			+ " and if(:startTime is not null && :endTime is not null && :startTime != '' && :endTime != '',tt.operator_time between :startTime and :endTime,1=1)",
			countQuery = "select count(1) from (select t1.`name` ,t1.id_card AS idCard,tt.info_json infoJson,tt.immigrant_population_json AS immigrantPopulationJson, "
					+ " tt.land_json AS landJson,tt.house_json AS houseJson,tt.tree_json AS treeJson,tt.other_json AS otherJson,tt.fitment_json AS fitmentJson, "
					+ " tt.individual_household_json AS individualHouseholdJson,t2.staff_name AS changeOperatorName,tt.operator_time AS operatorTime "
					+ " from t_abm_owner_info_history tt left join t_info_owner_impl t1 on tt.owner_nm = t1.nm "
					+ " LEFT JOIN sys_staff t2 on tt.change_operator = t2.nm  "
					+ " WHERE 1=1 and if(:name is not null, t1.name like concat('%',:name,'%') ,1=1) and if(:nm is not null && :nm != '', tt.owner_nm =:nm ,1=1)"
					+ " and if(:startTime is not null && :endTime is not null && :startTime != '' && :endTime != '',tt.operator_time between :startTime and :endTime,1=1)) as temp", nativeQuery = true)
	Page<OwnerInfoHistoryVO> page(@Param("name") String name, @Param("startTime") String startTime,
			@Param("endTime") String endTime,@Param("nm")String nm, Pageable pageable);

}
