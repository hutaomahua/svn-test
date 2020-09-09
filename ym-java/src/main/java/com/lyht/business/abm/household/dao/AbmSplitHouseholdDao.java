package com.lyht.business.abm.household.dao;

import com.lyht.business.abm.household.entity.AbmSplitHouseholdEntity;
import com.lyht.business.abm.household.vo.AbmSplitHouseholdProcessVO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AbmSplitHouseholdDao extends JpaRepository<AbmSplitHouseholdEntity, String> {
	
	/**
	 * 根据户主编码查询分户详情
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT" 
			+ " tt.nm AS ownerNm, tt.name AS ownerName, "
			+ " tt.id_card AS idCard, tt.split_household_state AS splitHouseholdState,"
			+ " pdv1.name AS national, pdv2.name AS scope,"
			+ " pr.merger_name AS mergerName,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.owner_nm = tt.nm) AS population,"
			+ " (SELECT SUM(IFNULL(tihi.area,0)) FROM t_info_houses_impl tihi WHERE tihi.owner_nm = tt.nm) AS houseArea,"
			+ " (SELECT IFNULL(SUM(tihdi.area),0) FROM t_info_houses_decoration_impl tihdi WHERE tihdi.owner_nm = tt.nm) AS houseDecorationArea,"
			+ " (SELECT IFNULL(SUM(tili.area),0) FROM t_info_land_impl tili WHERE tili.owner_nm = tt.nm) AS landArea,"
			+ " tash.process_id AS processId, tash.split_json_data AS splitJsonData,"
			+ " tash.apply_process_id AS applyProcessId, tash.remark AS remark,"
			+ " pf.nm AS signFileNm, pf.file_name AS signFileName,"
			+ " pf.file_type AS signFileType, pf.file_url AS signFileUrl," 
			+ " tbp1.name AS processName, tbp1.apply_time AS processApplyTime,"
			+ " tbp1.status AS processStatus, tbp1.cn_status AS processCnStatus,"
			+ " tbp2.name AS applyProcessName, tbp2.apply_time AS applyProcessApplyTime,"
			+ " tbp2.status AS applyProcessStatus, tbp2.cn_status AS applyProcessCnStatus"
			+ " FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_split_household tash ON tt.nm = tash.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp1 ON tash.process_id = tbp1.process_id"
			+ " LEFT JOIN t_bpm_process tbp2 ON tash.apply_process_id = tbp2.process_id"
			+ " LEFT JOIN pub_files pf ON tash.sign_file_url = pf.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.national = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm"
			+ " WHERE tt.nm = :ownerNm"
			+ " ORDER BY tash.create_time DESC"
			+ " LIMIT 1", nativeQuery = true)
	AbmSplitHouseholdProcessVO findByOwnerNm(@Param("ownerNm") String ownerNm);
	
	/**
	 * 根据分户-流程ID查询分户详情
	 * @param processId
	 * @return
	 */
	@Query(value = "SELECT" 
			+ " tt.nm AS ownerNm, tt.name AS ownerName, "
			+ " tt.id_card AS idCard, tt.split_household_state AS splitHouseholdState,"
			+ " pdv1.name AS national, pdv2.name AS scope,"
			+ " pr.merger_name AS mergerName,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.owner_nm = tt.nm) AS population,"
			+ " (SELECT SUM(IFNULL(tihi.area,0)) FROM t_info_houses_impl tihi WHERE tihi.owner_nm = tt.nm) AS houseArea,"
			+ " (SELECT IFNULL(SUM(tihdi.area),0) FROM t_info_houses_decoration_impl tihdi WHERE tihdi.owner_nm = tt.nm) AS houseDecorationArea,"
			+ " (SELECT IFNULL(SUM(tili.area),0) FROM t_info_land_impl tili WHERE tili.owner_nm = tt.nm) AS landArea,"
			+ " tash.process_id AS processId, tash.split_json_data AS splitJsonData,"
			+ " tash.apply_process_id AS applyProcessId, tash.remark AS remark,"
			+ " pf.nm AS signFileNm, pf.file_name AS signFileName,"
			+ " pf.file_type AS signFileType, pf.file_url AS signFileUrl," 
			+ " tbp1.name AS processName, tbp1.apply_time AS processApplyTime,"
			+ " tbp1.status AS processStatus, tbp1.cn_status AS processCnStatus,"
			+ " tbp2.name AS applyProcessName, tbp2.apply_time AS applyProcessApplyTime,"
			+ " tbp2.status AS applyProcessStatus, tbp2.cn_status AS applyProcessCnStatus"
			+ " FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_split_household tash ON tt.nm = tash.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp1 ON tash.process_id = tbp1.process_id"
			+ " LEFT JOIN t_bpm_process tbp2 ON tash.apply_process_id = tbp2.process_id"
			+ " LEFT JOIN pub_files pf ON tash.sign_file_url = pf.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.national = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm"
			+ " WHERE tash.process_id = :processId"
			+ " ORDER BY tash.create_time DESC"
			+ " LIMIT 1", nativeQuery = true)
	AbmSplitHouseholdProcessVO findProcessByProcessId(@Param("processId") String processId);
	
	/**
	 * 根据分户申请-流程ID查询分户详情
	 * @param applyProcessId
	 * @return
	 */
	@Query(value = "SELECT" 
			+ " tt.nm AS ownerNm, tt.name AS ownerName, "
			+ " tt.id_card AS idCard, tt.split_household_state AS splitHouseholdState,"
			+ " pdv1.name AS national, pdv2.name AS scope,"
			+ " pr.merger_name AS mergerName,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.owner_nm = tt.nm) AS population,"
			+ " (SELECT SUM(IFNULL(tihi.area,0)) FROM t_info_houses_impl tihi WHERE tihi.owner_nm = tt.nm) AS houseArea,"
			+ " (SELECT IFNULL(SUM(tihdi.area),0) FROM t_info_houses_decoration_impl tihdi WHERE tihdi.owner_nm = tt.nm) AS houseDecorationArea,"
			+ " (SELECT IFNULL(SUM(tili.area),0) FROM t_info_land_impl tili WHERE tili.owner_nm = tt.nm) AS landArea,"
			+ " tash.process_id AS processId, tash.split_json_data AS splitJsonData,"
			+ " tash.apply_process_id AS applyProcessId, tash.remark AS remark,"
			+ " pf.nm AS signFileNm, pf.file_name AS signFileName,"
			+ " pf.file_type AS signFileType, tash.sign_file_url AS signFileUrl,"
			+ " tbp1.name AS processName, tbp1.apply_time AS processApplyTime,"
			+ " tbp1.status AS processStatus, tbp1.cn_status AS processCnStatus,"
			+ " tbp2.name AS applyProcessName, tbp2.apply_time AS applyProcessApplyTime,"
			+ " tbp2.status AS applyProcessStatus, tbp2.cn_status AS applyProcessCnStatus"
			+ " FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_split_household tash ON tt.nm = tash.owner_nm"
			+ " LEFT JOIN t_bpm_process tbp1 ON tash.process_id = tbp1.process_id"
			+ " LEFT JOIN t_bpm_process tbp2 ON tash.apply_process_id = tbp2.process_id"
			+ " LEFT JOIN pub_files pf ON tash.sign_file_url = pf.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.national = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.scope = pdv2.nm"
			+ " WHERE tash.apply_process_id = :applyProcessId"
			+ " ORDER BY tash.create_time DESC"
			+ " LIMIT 1", nativeQuery = true)
	AbmSplitHouseholdProcessVO findProcessByApplyProcessId(@Param("applyProcessId") String applyProcessId);

	@Query(value = " SELECT nm AS signFileNm, file_name AS signFileName, file_type AS signFileType, file_url AS signFileUrl " +
			" FROM pub_files WHERE nm IN( " +
			" SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(:signFileUrl,',',help_topic_id+1),',',-1) AS num " +
			" FROM mysql.help_topic WHERE help_topic_id < LENGTH(:signFileUrl)-LENGTH(REPLACE(:signFileUrl,',',''))+1) ", nativeQuery = true)
	List<Map> findProcessFilesByApplyProcessId(@Param("signFileUrl") String signFileUrl);

	AbmSplitHouseholdEntity findByProcessId(String processId);
	
	AbmSplitHouseholdEntity findByApplyProcessId(String applyProcessId);
	
	@Query(value = "SELECT"
			+ " *"
			+ " FROM t_abm_split_household tt"
			+ " WHERE tt.owner_nm = :ownerNm"
			+ " ORDER BY tt.create_time DESC"
			+ " LIMIT 1", nativeQuery = true)
	AbmSplitHouseholdEntity getByOwnerNm(@Param("ownerNm") String ownerNm);

	@Modifying
	@Query(value = "UPDATE t_info_family_impl t JOIN (SELECT index_nm, recipient FROM t_abm_family_splitting WHERE " +
			"index_type = 1 AND status = 0 AND initiator = :initiator) s ON t.owner_nm = :initiator AND t.nm = s" +
			".index_nm SET t.owner_nm = s.recipient"
			, nativeQuery = true)
	void updateIndexByFamilySplitting1(@Param("initiator") String initiator);

	@Modifying
	@Query(value = "UPDATE t_info_land_impl t JOIN (SELECT index_nm, recipient FROM t_abm_family_splitting WHERE " +
			"index_type = 2 AND status = 0 AND initiator = :initiator) s ON t.owner_nm = :initiator AND t.nm = s.index_nm " +
			"SET t .owner_nm = s.recipient", nativeQuery = true)
	void updateIndexByFamilySplitting2(@Param("initiator") String initiator);

	@Modifying
	@Query(value = "UPDATE t_info_houses_impl t JOIN (SELECT index_nm, recipient FROM t_abm_family_splitting WHERE " +
			"index_type = 3 AND status = 0 AND initiator = :initiator) s ON t.owner_nm = :initiator AND t.nm = s" +
			".index_nm SET t.owner_nm = s.recipient", nativeQuery = true)
	void updateIndexByFamilySplitting3(@Param("initiator") String initiator);

	@Modifying
	@Query(value = "UPDATE t_info_trees_impl t JOIN (SELECT index_nm, recipient FROM t_abm_family_splitting WHERE " +
			"index_type = 4 AND status = 0 AND initiator = :initiator) s ON t.owner_nm = :initiator AND t.nm = s.index_nm" +
			" SET t.owner_nm = s.recipient", nativeQuery = true)
	void updateIndexByFamilySplitting4(@Param("initiator") String initiator);

	@Modifying
	@Query(value = "UPDATE t_info_building_impl t JOIN (SELECT index_nm, recipient FROM t_abm_family_splitting WHERE " +
			"index_type = 5 AND status = 0 AND initiator = :initiator) s ON t.owner_nm = :initiator AND t.nm =" +
			" s.index_nm SET t.owner_nm = s.recipient", nativeQuery = true)
	void updateIndexByFamilySplitting5(@Param("initiator") String initiator);

	@Modifying
	@Query(value = "UPDATE t_info_houses_decoration_impl t JOIN (SELECT index_nm, recipient FROM " +
			"t_abm_family_splitting WHERE index_type = 6 AND status = 0 AND initiator = :initiator) s ON t.owner_nm = :initiator AND t.nm " +
			"= s.index_nm SET t.owner_nm = s.recipient", nativeQuery = true)
	void updateIndexByFamilySplitting6(@Param("initiator") String initiator);

	@Modifying
	@Query(value = "INSERT INTO `t_abm_family_splitting` (`initiator`, `recipient`, `index_type`, `index_nm`) " +
			"VALUES (:initiator, :recipient, :indexType, :indexNm)", nativeQuery = true)
	void insertFamilySplitting(@Param("initiator")String initiator, @Param("recipient")String recipient, @Param("indexType")Integer indexType, @Param("indexNm")String indexNm);

	@Modifying
	@Query(value = "UPDATE t_abm_family_splitting SET status = 1 WHERE initiator = :initiator AND status = 0", nativeQuery = true)
	void updateFamilySplitting(@Param("initiator") String initiator);

	@SuppressWarnings("rawtypes")
	@Query(value = "SELECT initiator, recipient, index_type AS type, index_nm AS nm FROM t_abm_family_splitting " +
			"WHERE initiator = :initiator AND status = 0 ORDER BY recipient", nativeQuery = true)
	List<Map> queryFamilySplittingByOwnerNm(@Param("initiator") String initiator);

	@Query(value = "SELECT recipient FROM t_abm_family_splitting WHERE initiator = :initiator AND status = 0 GROUP BY recipient", nativeQuery = true)
	List<String> queryRecipientList(@Param("initiator") String initiator);



	@Query(value = "SELECT SUM(s.num) FROM (SELECT COUNT(*) num FROM t_abm_protocol_escrow WHERE state = 1 AND owner_nm = :ownerNm UNION ALL " +
			"SELECT COUNT(*) num FROM t_abm_protocol_info WHERE state = 1 AND owner_nm = :ownerNm) s ", nativeQuery = true)
	int queryOwnerAgreementCount(@Param("ownerNm") String ownerNm);

	@Query(value = "SELECT owner_nm FROM t_abm_split_household WHERE process_id = :processId LIMIT 1", nativeQuery = true)
	String queryOwnerNmByProcessId(@Param("processId") String processId);

}
