package com.lyht.business.abm.production.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.production.entity.ProduceProcess;
import com.lyht.business.abm.production.vo.ProduceProcessVO;


public interface ProduceProcessDao extends JpaRepository<ProduceProcess, Integer> {
	
	@Query(value = "SELECT" 
			+ " tt.nm AS ownerNm, tt.name AS ownerName, "
			+ " tt.id_card AS idCard, tt.place_name AS placeName, "
			+ " pdv1.name AS placeType, tbp.process_id AS processId, "
			+ " tbp.name AS processName, tbp.apply_time AS processApplyTime,"
			+ " tbp.status AS processStatus, tbp.cn_status AS processCnStatus,"
			+ " tamp.sign_file_url AS signFileUrl" 
			+ " FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_produce_process tamp ON tt.nm = tamp.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp ON tamp.process_id = tbp.process_id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.place_type = pdv1.nm"
			+ " WHERE tt.nm = :ownerNm"
			+ " LIMIT 1", nativeQuery = true)
	ProduceProcessVO findByOwnerNm(@Param("ownerNm")String ownerNm);
	
	@Query(value = "select * from t_abm_produce_process where owner_nm = ? order by create_time desc limit 1",nativeQuery = true)
	ProduceProcess queryByOwnerNm(String ownerNm);
	
	ProduceProcess findByProcessId(String processId);

}
