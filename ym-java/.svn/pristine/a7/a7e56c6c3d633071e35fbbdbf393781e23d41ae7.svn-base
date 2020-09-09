package com.lyht.business.abm.production.dao;

import com.lyht.business.abm.production.entity.ProductionAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @version: V1.0
 * @author: hjs
 * @className: ProductionAuditDao
 * @packageName: com.lyht.business.abm.landAllocation.dao
 * @description: （接口作用作用）
 * @data: 2020年02月10日 19:31
 * @see []
 **/
public interface ProductionAuditDao extends JpaRepository<ProductionAuditEntity, Integer> {
	
	@Modifying
	@Query(value = "delete from t_abm_production_audit where owner_nm = :ownerNm",nativeQuery = true)
	Integer deleteByOwnerNm(@Param("ownerNm")String ownerNm);



    /**
     * 查询在人口表中是界定中的 并且在流程表里面不是处理中状态
     * @return
     */
    @Query(value = " SELECT ta.place,tfi.owner_nm as ownerNm, tfi.is_produce as isProduce, ta.process_id AS processId, tp.status AS status, tp.cn_status AS cnStatus FROM t_abm_production_audit ta  " +
            " LEFT JOIN t_info_family_impl tfi ON ta.owner_nm = tfi.owner_nm " +
            " INNER JOIN t_bpm_process tp ON ta.process_id = tp.process_id  " +
            " WHERE tfi.is_produce = '3' AND tp.status <> 'Standby' " +
            " GROUP BY ta.id ", nativeQuery = true)
    List<Map<String, Object>> findIncomplete();

    /**
     * 根据流程id 查询该次流程的相关数据
     * @param processId
     * @return
     */
    @Query(value = "SELECT " + 
    		"	tt.process_id AS processId, " + 
    		"	tt.process_operate_data AS processOperateData, " + 
    		"	tt.NAME, " + 
    		"	DATE_FORMAT( " + 
    		"		tt.apply_time, " + 
    		"		'%Y-%m-%d %H:%i:%S' " + 
    		"	) AS applyTime, " + 
    		"concat(t1.file_name,t1.file_type)fileName,t1.file_Url fileUrl " + 
    		"FROM " + 
    		"	t_bpm_process tt " + 
    		"left join pub_files t1 on tt.process_id = t1.table_pk_column " + 
    		"WHERE " + 
    		"	process_id = :processId ", nativeQuery = true)
    Map<String, Object> findDataByProcessId(@Param("processId") String processId);
    
}
