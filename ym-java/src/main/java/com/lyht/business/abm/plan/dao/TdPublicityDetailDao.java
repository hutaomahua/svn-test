package com.lyht.business.abm.plan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.entity.TdPublicityDetailEntity;

public interface TdPublicityDetailDao extends JpaRepository<TdPublicityDetailEntity, Integer>{

	@Query(value = " select * from  t_td_publicity_details WHERE nm = :nm ", nativeQuery = true)
	List<TdPublicityDetailEntity> findNm( @Param("nm") String nm);

	@Query(value = "SELECT  "
					+"ppr.name as region, "
					+"abm.resolve_area as resolveArea, "
					+"tla.process_id as processId, "
					+"tla.owner_nm as ownerNm, "
		 			+"tli.name as name, "
		 			+"tli.id_card as idCard, "
			 		+"abm.nm AS nm  "
			 		+"FROM t_td_publicity_details tla "
					+"LEFT JOIN	t_abm_land_audit abm on  abm.process_id =tla.process_id "
					+"LEFT JOIN pub_region AS ppr ON ppr.city_code = abm.source_region "
			 		+"LEFT JOIN t_info_family_impl tli ON abm.nm = tli.nm "
			 		+"LEFT JOIN pub_region AS tpr ON tpr.city_code = abm.target_region "
			 		+"where 1=1  and  tla.nm= :nm", nativeQuery = true)
	List<Map> detaiList( @Param("nm") String nm);

}
