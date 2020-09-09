package com.lyht.business.process.dao;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lyht.business.process.entity.ProcessEntity;

import org.springframework.data.repository.query.Param;

public interface ProcessDao extends JpaRepository<ProcessEntity, String> {
	@Query(value = "select * from t_bpm_process tt where 1=1 and "
			+ "tt.applicant = :applicant and "
			+" IF (:name is not null,tt.name LIKE concat('%',:name,'%'),1=1) "
			+ "order by  tt.name asc",
			nativeQuery = true)
	Page<ProcessEntity> searchByName(@Param("applicant") String applicant,@Param("name") String name, Pageable pageable);
	
    @Query(value = "select ss.nm from sys_role ss where ss.uuid = :uuid",nativeQuery = true)
    String findNmByUuid(@Param("uuid") String uuid);
    
    @Query(value = "select status from t_bpm_process where process_id = :taskId",nativeQuery = true)
    String findStatusByTaskId(@Param("taskId")String taskId);
    
    ProcessEntity findByProcessId(String processId);
}

