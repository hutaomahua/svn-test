package com.lyht.business.abm.plan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.plan.entity.OwnerVerifyEntity;

public interface OwnerVerifyDao  extends JpaRepository<OwnerVerifyEntity, Integer>{
	 @Query(value = " select * FROM t_abm_owner_verify ORDER BY id DESC LIMIT 0,1 " , nativeQuery = true)
	    List<Map> getCode();
	 
		 @Query(value = " select * FROM t_abm_owner_verify where process_id=:taskId " , nativeQuery = true)
		 OwnerVerifyEntity findByTaskId(@Param("taskId") String taskId);
	 
		 /**
		  * 修改公示状态
		  * @param fhstate
		  * @param processId
		  */
		 @Modifying
		 @Query(value = "UPDATE t_info_owner_impl SET gs_state =:gsState  WHERE nm=:nm ", nativeQuery = true)
		void editGsState(@Param("gsState") String gsState,@Param("nm")String nm);
		 
		 @Modifying
		 @Query(value = "UPDATE t_info_family_impl SET bq_gs_state =:gsState  WHERE owner_nm=:nm ", nativeQuery = true)
		void editBqGsState(@Param("gsState") String gsState,@Param("nm")String nm);
		 
		@Modifying
		@Query(value = "UPDATE t_info_owner_impl SET fh_state =:fhstate  WHERE process_id=:processId ", nativeQuery = true)
		void editFhState(@Param("fhstate") String fhstate,@Param("processId")String processId);
		
		@Modifying
		@Query(value = "UPDATE t_abm_owner_verify SET zf_opinion =:zfOpinion  WHERE process_id=:processId ", nativeQuery = true)
		void editFhxzYj(@Param("zfOpinion") String zfOpinion,@Param("processId")String processId);
		
		@Modifying
		@Query(value = "UPDATE t_abm_owner_verify SET zf_opinion =:ymjOpinion  WHERE process_id=:processId ", nativeQuery = true)
		void editFhzfYj(@Param("ymjOpinion") String ymjOpinion,@Param("processId")String processId);
		
		@Modifying
		@Query(value = "UPDATE t_abm_land_audit SET fg_state =:fgState  WHERE nm = :ownerNm", nativeQuery = true)
		void editfgState(@Param("fgState") String fgState,@Param("ownerNm")String ownerNm);
	 


}
