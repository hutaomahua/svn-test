package com.lyht.business.abm.landAllocation.dao;

import com.lyht.business.abm.landAllocation.entity.LandAuditEntity;
import com.lyht.business.abm.landAllocation.vo.LandAuditVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version: V1.0
 * @author: hjs
 * @className: LandAuditDao
 * @packageName: com.lyht.business.abm.landAllocation.dao
 * @description: （接口作用作用）
 * @data: 2020年02月10日 19:31
 * @see []
 **/
public interface LandAuditDao extends JpaRepository<LandAuditEntity, Integer> {
	List<LandAuditEntity> findByNm(String nm);

	@Query(value = "select t1.nm AS code,t1.name,(tt.area - tt.separate_area-ifnull(sum(t2.area),0)) as area from t_abm_land_pool tt left join pub_dict_value t1 on tt.scope = t1.nm "
			+ " left join t_abm_land_pool_process t2 on tt.id = t2.pool_id  "
			+ "where tt.region = :region and tt.all_type = :allType and tt.type_one = :typeOne "
			+ "and tt.type_two = :typeTwo and tt.type_three = :typeThree ", nativeQuery = true)
	List<Map<String, Object>> queryLandScope(@Param("region") String region, @Param("allType") String allType,
			@Param("typeOne") String typeOne, @Param("typeTwo") String typeTwo, @Param("typeThree") String typeThree);

	@Query(value = "select t1.nm AS code,t1.name,(tt.area - tt.separate_area-ifnull(sum(t2.area),0)) as area from t_abm_land_pool tt left join pub_dict_value t1 on tt.scope = t1.nm  "
			+ "left join t_abm_land_pool_process t2 on tt.id = t2.pool_id  "
			+ "where tt.region = :region and tt.all_type = :allType and tt.type_one = :typeOne "
			+ "and tt.type_two = :typeTwo ", nativeQuery = true)
	List<Map<String, Object>> queryLandScope(@Param("region") String region, @Param("allType") String allType,
			@Param("typeOne") String typeOne, @Param("typeTwo") String typeTwo);

	/* *//**
			 * 查询土地审核列表
			 * 
			 * @return AS userName
			 *//*
				 * @Query(value =
				 * "SELECT tla.id as id, tla.source_region AS sourceRegion, tla.target_region as targetRegion, "
				 * +
				 * " pr.merger_name AS sourceRegionName, if(tli.name is not null, CONCAT(pr2.merger_name, '/', tli.name), pr2.merger_name) AS targetRegionName, tla.nm AS nm, "
				 * +
				 * " tla.flag AS flag, tla.type_level AS typeLevel, tla.land_type AS landType, tla.scope AS scope, "
				 * +
				 * " pv.name AS scopeName,tla.resolve_area AS resolveArea, tla.separate_area AS separateArea, "
				 * +
				 * " tla.audit_code AS auditCode, pv2.name AS auditName, tla.apply_user AS applyUser, "
				 * + " tla.apply_date AS applyDate, " + " tla.process_id as processId, " +
				 * "  tla.remark AS remark " + " FROM t_abm_land_audit tla " +
				 * " LEFT JOIN pub_region pr ON tla.source_region = pr.city_code " +
				 * " LEFT JOIN pub_region pr2 ON tla.target_region = pr2.city_code " +
				 * " LEFT JOIN t_info_family_impl tli ON tla.nm = tli.nm " +
				 * " LEFT JOIN pub_dict_value pv ON tla.scope = pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits'  "
				 * +
				 * " LEFT JOIN pub_dict_value pv2 ON tla.audit_code = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_sh_state' "
				 * +
				 * " WHERE if(:auditCode IS NOT NULL AND :auditCode != '',tla.audit_code =:auditCode, 1=1) "
				 * +
				 * " AND if(:uname IS NOT NULL AND :uname != '',tli.name LIKE CONCAT('%',:uname,'%'), 1=1) "
				 * +
				 * " AND if(:city IS NOT NULL AND :city != '',pr.merger_name LIKE CONCAT('%',:city,'%'), 1=1) "
				 * +
				 * " AND if(:sDate IS NOT NULL and :eDate IS NOT NULL,STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') between :sDate and :eDate, "
				 * +
				 * " (if(:sDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') >= :sDate, 1=1) and if(:eDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') <= :eDate, 1=1)))  order by tla.id desc"
				 * , countQuery = "SELECT count(*) FROM t_abm_land_audit tla " +
				 * " LEFT JOIN pub_region pr ON tla.source_region = pr.city_code " +
				 * " LEFT JOIN pub_region pr2 ON tla.target_region = pr2.city_code " +
				 * " LEFT JOIN t_info_family_impl tli ON tla.nm = tli.nm " +
				 * " LEFT JOIN pub_dict_value pv ON tla.scope = pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits'  "
				 * +
				 * " LEFT JOIN pub_dict_value pv2 ON tla.audit_code = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_sh_state' "
				 * +
				 * " WHERE if(:auditCode IS NOT NULL AND :auditCode != '',tla.audit_code =:auditCode, 1=1) "
				 * +
				 * " AND if(:uname IS NOT NULL AND :uname != '',tli.name LIKE CONCAT('%',:uname,'%'), 1=1) "
				 * +
				 * " AND if(:city IS NOT NULL AND :city != '',pr.merger_name LIKE CONCAT('%',:city,'%'), 1=1) "
				 * +
				 * " AND if(:sDate IS NOT NULL and :eDate IS NOT NULL,STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') between :sDate and :eDate, "
				 * +
				 * " if(:sDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') >= :sDate, if(:eDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') <= :eDate, 1=1)))"
				 * , nativeQuery = true) Page<Map> queryAuditList(@Param("auditCode") String
				 * auditCode, @Param("uname") String uname, @Param("city") String
				 * city, @Param("sDate") Date sDate, @Param("eDate")Date eDate, Pageable
				 * pageable);
				 */
	@Query(value = "SELECT if(tli.name is not null, CONCAT(pr2.merger_name, '/', tli.name), pr2.merger_name) AS targetRegionName "
			+ " FROM t_abm_land_audit tla  " + " LEFT JOIN pub_region pr2 ON tla.target_region = pr2.city_code "
			+ " LEFT JOIN t_info_owner_impl tli ON tla.nm = tli.nm where tla.id =?1 ", nativeQuery = true)
	String getTargetRegionNameForProcess(Integer id);

	/**
	 * 查询土地审核列表
	 * 
	 * @author 成良歌，将之前的表t_info_family_impl换成t_info_owner_impl
	 * @return AS userName
	 */
	@Query(value = "SELECT tla.id as id, tla.source_region AS sourceRegion, tla.target_region as targetRegion, "
			+ " pr.merger_name AS sourceRegionName, if(tli.name is not null, CONCAT(pr2.merger_name, '/', tli.name), pr2.merger_name) AS targetRegionName, tla.nm AS nm, "
			+ " tla.flag AS flag, tla.type_level AS typeLevel, tla.land_type AS landType,pv3.name AS landTypeName, tla.scope AS scope, "
			+ " pv.name AS scopeName,tla.resolve_area AS resolveArea, tla.separate_area AS separateArea, "
			+ " tla.audit_code AS auditCode, pv2.name AS auditName, tla.apply_user AS applyUser, "
			+ " tla.apply_date AS applyDate,  tla.process_id as processId, " + "  tla.remark AS remark "
			+ " FROM t_abm_land_audit tla  LEFT JOIN pub_region pr ON tla.source_region = pr.city_code "
			+ " LEFT JOIN pub_region pr2 ON tla.target_region = pr2.city_code "
			+ " LEFT JOIN t_info_owner_impl tli ON tla.nm = tli.nm "
			+ " LEFT JOIN pub_dict_value pv ON tla.scope = pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits'  "
			+ " LEFT JOIN pub_dict_value pv2 ON tla.audit_code = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_sh_state'"
			+ " LEFT JOIN pub_dict_value pv3 on tla.land_type = pv3.nm  "
			+ " WHERE if(:auditCode IS NOT NULL AND :auditCode != '',tla.audit_code =:auditCode, 1=1) "
			+ " AND if(:uname IS NOT NULL AND :uname != '',tli.name LIKE CONCAT('%',:uname,'%'), 1=1) "
			+ " AND if(:city IS NOT NULL AND :city != '',pr.merger_name LIKE CONCAT('%',:city,'%'), 1=1) "
			+ " AND if(:sDate IS NOT NULL and :eDate IS NOT NULL,STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') between :sDate and :eDate, "
			+ " (if(:sDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') >= :sDate, 1=1) and if(:eDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') <= :eDate, 1=1)))  "
			+ " ORDER BY (case when tla.audit_code = '36845517AF' then 1 when tla.audit_code = 'D2B5A40E22' then 2 when tla.audit_code = 'D30E1EE520' then 3 else 4 end), tla.apply_date desc ", countQuery = "SELECT count(*) FROM t_abm_land_audit tla "
					+ " LEFT JOIN pub_region pr ON tla.source_region = pr.city_code "
					+ " LEFT JOIN pub_region pr2 ON tla.target_region = pr2.city_code "
					+ " LEFT JOIN t_info_owner_impl tli ON tla.nm = tli.nm "
					+ " LEFT JOIN pub_dict_value pv ON tla.scope = pv.nm AND pv.listnm_sys_dict_cate = 'dict_limits'  "
					+ " LEFT JOIN pub_dict_value pv2 ON tla.audit_code = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_sh_state' "
					+ " WHERE if(:auditCode IS NOT NULL AND :auditCode != '',tla.audit_code =:auditCode, 1=1) "
					+ " AND if(:uname IS NOT NULL AND :uname != '',tli.name LIKE CONCAT('%',:uname,'%'), 1=1) "
					+ " AND if(:city IS NOT NULL AND :city != '',pr.merger_name LIKE CONCAT('%',:city,'%'), 1=1) "
					+ " AND if(:sDate IS NOT NULL and :eDate IS NOT NULL,STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') between :sDate and :eDate, "
					+ " if(:sDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') >= :sDate, if(:eDate IS NOT NULL, STR_TO_DATE(tla.apply_date, '%Y-%m-%d %H:%i:%s') <= :eDate, 1=1)))", nativeQuery = true)
	Page<Map> queryAuditList(@Param("auditCode") String auditCode, @Param("uname") String uname,
			@Param("city") String city, @Param("sDate") Date sDate, @Param("eDate") Date eDate, Pageable pageable);

	/**
	 * 根据行政区编码查询行政区级别
	 * 
	 * @param region 行政区编码
	 * @return
	 */
	@Query(value = "select level FROM pub_region WHERE city_code = :region ", nativeQuery = true)
	String findRegionLevel(@Param("region") String region);

	/*
	 * @Query(value = "SELECT * FROM t_info_family_impl tfi WHERE nm = :nm ",
	 * nativeQuery = true) Map<String, Object> findFamilyInfo(@Param("nm")String
	 * nm);
	 */

	/**
	 * @author 成良歌
	 * @param nm
	 * @return
	 */
	@Query(value = "SELECT * FROM t_info_owner_impl tfi WHERE nm = :nm ", nativeQuery = true)
	Map<String, Object> findOwnerInfo(@Param("nm") String nm);

	/**
	 * 根据流程id 查找土地审核的内容
	 * 
	 * @param processId
	 * @return
	 */
	LandAuditEntity findByProcessId(@Param("processId") String processId);

	/**
	 * 查询在土地分解审核表中未完成的数据 并且在流程表里面不是处理中状态
	 * 
	 * @return
	 */
	@Query(value = "SELECT ta.id as id, ta.process_id as processId, tp.status as status, tp.cn_status as cnStatus FROM t_abm_land_audit ta "
			+ " INNER JOIN t_bpm_process tp ON ta.process_id = tp.process_id "
			+ " WHERE ta.audit_code = 'D30E1EE520' AND tp.status <> 'Standby' ", nativeQuery = true)
	List<Map<String, Object>> findIncomplete();

	/**
	 * 根据流程id查询在土地分解审核表中未完成的数据 并且在流程表里面不是处理中状态
	 * 
	 * @return
	 */
	@Query(value = "SELECT ta.id as id, ta.process_id as processId, tp.status as status, tp.cn_status as cnStatus FROM t_abm_land_audit ta "
			+ " INNER JOIN t_bpm_process tp ON ta.process_id = tp.process_id "
			+ " WHERE ta.audit_code = 'D30E1EE520' AND tp.status <> 'Standby' and ta.process_id=:processId  ", nativeQuery = true)
	List<Map<String, Object>> findIncompleteByTaskId(@Param("processId") String processId);

	@Query(value = "select t0.merger_name sourceRegion,t1.merger_name targetRegion,t2.name landType,tt.resolve_area resolveArea,tt.separate_area separateArea  "
			+ "from t_abm_land_audit tt " + "left join pub_region t0 on tt.source_region = t0.city_code "
			+ "left join pub_region t1 on tt.target_region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.land_type = t2.nm  "
			+ "where tt.process_id = :taskId ", nativeQuery = true)
	LandAuditVO getDataByTaskId(@Param("taskId") String taskId);

	@Modifying
	@Query(value = "update t_abm_land_audit set fg_state = :fgState where id = :id", nativeQuery = true)
	void updateFgState(@Param("id")Integer id,@Param("fgState")Integer fgState);
	
}
