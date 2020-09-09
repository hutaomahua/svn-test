package com.lyht.business.abm.removal.dao;

import com.lyht.business.abm.removal.entity.AbmMoveProcessEntity;
import com.lyht.business.abm.removal.vo.AbmMoveProcessVO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AbmMoveProcessDao extends JpaRepository<AbmMoveProcessEntity, Integer> {
	
	@Query(value = "SELECT"
			+ " *"
			+ " FROM t_abm_move_process tt"
			+ " WHERE tt.owner_nm = :ownerNm"
			+ " ORDER BY tt.create_time DESC"
			+ " LIMIT 1", nativeQuery = true)
	AbmMoveProcessEntity getByOwnerNm(@Param("ownerNm") String ownerNm);

	@Query(value = "SELECT" 
			+ " tt.nm AS ownerNm, tt.name AS ownerName, "
			+ " tt.id_card AS idCard, tt.place_name AS placeName,"
			+ " tt.place_address AS placeAddress,"
			+ " tt.xiang AS county, tt.cun AS town," 
			+ "	tt.zu AS village,"
			+ " tt.define AS define, tt.to_where AS toWhere,"
			+ " tt.i_population AS iPopulation, "
			+ " tt.is_satisfy AS isSatisfy,"
			+ " tt.move_state AS moveState,"
			+ " pr.merger_name AS mergerName,"
			+ " pdv1.name AS placeType, pdv2.name AS scope,"
			+ " pdv3.name AS householdType,"
			+ " tbp.process_id AS processId, "
			+ " tbp.name AS processName, tbp.apply_time AS processApplyTime,"
			+ " tbp.status AS processStatus, tbp.cn_status AS processCnStatus,"
			+ " tamp.move_json_data AS moveJsonData, tamp.remark AS remark," 
			+ " pf.nm AS signFileNm, pf.file_name AS signFileName,"
			+ " pf.file_type AS signFileType, pf.file_url AS signFileUrl" 
			+ " FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_move_process tamp ON tt.nm = tamp.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp ON tamp.process_id = tbp.process_id"
			+ " LEFT JOIN pub_files pf ON tamp.sign_file_url = pf.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.place_type = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.households_type = pdv3.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " WHERE tt.nm = :ownerNm"
			+ " ORDER BY tamp.create_time DESC"
			+ " LIMIT 1", nativeQuery = true)
	AbmMoveProcessVO findByOwnerNm(@Param("ownerNm") String ownerNm);
	
	@Modifying
	@Query(value = "update t_info_owner_impl set move_state = :moveState where nm = :nm AND id > 0 ", nativeQuery = true)
	void updateOwner(@Param("moveState") String moveState, @Param("nm") String nm);
	
	@Query(value = "SELECT" 
			+ " tt.nm AS ownerNm, tt.name AS ownerName, "
			+ " tt.id_card AS idCard, tt.place_name AS placeName, "
			+ " tt.place_address AS placeAddress,"
			+ " tt.xiang AS county, tt.cun AS town," 
			+ "	tt.zu AS village,"
			+ " tt.define AS define, tt.to_where AS toWhere,"
			+ " tt.i_population AS iPopulation, "
			+ " tt.is_satisfy AS isSatisfy,"
			+ " tt.move_state AS moveState,"
			+ " pr.merger_name AS mergerName,"
			+ " pdv1.name AS placeType, pdv2.name AS scope,"
			+ " pdv3.name AS householdType,"
			+ " tbp.process_id AS processId, "
			+ " tbp.name AS processName, tbp.apply_time AS processApplyTime,"
			+ " tbp.status AS processStatus, tbp.cn_status AS processCnStatus,"
			+ " tamp.move_json_data AS moveJsonData, tamp.remark AS remark,"
			+ " pf.nm AS signFileNm, pf.file_name AS signFileName,"
			+ " pf.file_type AS signFileType, pf.file_url AS signFileUrl" 
			+ " FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_move_process tamp ON tt.nm = tamp.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp ON tamp.process_id = tbp.process_id"
			+ " LEFT JOIN pub_files pf ON tamp.sign_file_url = pf.nm"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.place_type = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.households_type = pdv3.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " WHERE tamp.process_id = :processId"
			+ " ORDER BY tamp.create_time DESC"
			+ " LIMIT 1", nativeQuery = true)
	AbmMoveProcessVO findByProcessId(@Param("processId") String processId);
	
	@Modifying
	@Query(value = "DELETE FROM t_abm_move_process WHERE owner_nm = :ownerNm", nativeQuery = true)
	int deleteByOwnerNm(@Param("ownerNm") String ownerNm);

}
