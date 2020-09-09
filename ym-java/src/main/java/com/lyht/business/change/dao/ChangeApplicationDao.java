package com.lyht.business.change.dao;

import com.lyht.business.change.entity.ChangeApplication;
import com.lyht.business.change.vo.ChangeApplicationVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeApplicationDao extends JpaRepository<ChangeApplication, Integer> {
	@Query(value = "select   " + "tca.id as id  " + ",tca.nm as nm  " + ",tca.project_name as projectName  "
			+ ",tca.change_project as changeProject  " + ",tca.applicant as applicant  "
			+ ",(select staff_name  from sys_staff where nm = tca.applicant ) as staffName"
			+ ",tca.apply_time as applyTime  " + ",tca.region as region  " + ",tca.change_type as changeType  "
			+ ",tca.design_content_nm as designContentNm  " + ",tca.general_situation as generalSituation  "
			+ ",tca.change_request_type as changeRequestType  " + ",tca.change_need as changeNeed  "
			+ ",tca.main_case as mainCase  " + ",tca.economic_analysis as economicAnalysis  "
			+ ",tca.change_status as changeStatus  " + ",tcdc.content_items as  contentItems"
			+ ",pr.merger_name as mergerName"
			+ ",REPLACE(pr.merger_name,CONCAT(SUBSTRING_INDEX(pr.merger_name,',',3),','),'') AS diming "
			+ ",tca.process_id as processId,tbp.status as processStatus,tbp.cn_status as processCnStatus "
			+ " FROM t_change_application as tca"
			+ " LEFT JOIN t_change_design_content as tcdc ON tca.design_content_nm = tcdc.nm"
			+ " LEFT JOIN pub_region pr ON tca.region=pr.city_code"
			+ " LEFT JOIN t_bpm_process tbp ON tca.process_id=tbp.process_id"
			+ " WHERE tca.change_request_type = :changeRequestType AND IF (:projectName is not null && LENGTH(:projectName)>0, tca.project_name LIKE CONCAT('%',:projectName,'%'), 1=1)  order by tca.id desc", 
			countQuery = "select count(*) "
					+ " FROM t_change_application as tca"
					+ " WHERE tca.change_request_type = :changeRequestType  AND IF (:projectName is not null && LENGTH(:projectName)>0, tca.project_name LIKE CONCAT('%',:projectName,'%'), 1=1)  ", nativeQuery = true)
	Page<ChangeApplicationVO> page(@Param("projectName") String projectName,
			@Param("changeRequestType") Integer changeRequestType, Pageable pageable);

	@Query(value = "select   " + "tca.id as id  " + ",tca.nm as nm  " + ",tca.project_name as projectName  "
			+ ",tca.change_project as changeProject  " + ",tca.applicant as applicant  "
			+ ",(select staff_name  from sys_staff where nm = tca.applicant ) as staffName"
			+ ",tca.apply_time as applyTime  " + ",tca.region as region  " + ",tca.change_type as changeType  "
			+ ",tca.design_content_nm as designContentNm  " + ",tca.general_situation as generalSituation  "
			+ ",tca.change_request_type as changeRequestType  " + ",tca.change_need as changeNeed  "
			+ ",tca.main_case as mainCase  " + ",tca.economic_analysis as economicAnalysis  "
			+ ",tca.change_status as changeStatus  " + ",tcdc.content_items as  contentItems"
			+ ",pr.merger_name as mergerName"
			+ ",REPLACE(pr.merger_name,CONCAT(SUBSTRING_INDEX(pr.merger_name,',',3),','),'') AS diming "
			+ ",tca.process_id as processId,tbp.status as processStatus,tbp.cn_status as processCnStatus "
			+ " FROM t_change_application as tca"
			+ " LEFT JOIN t_change_design_content as tcdc on tca.design_content_nm = tcdc.nm"
			+ " LEFT JOIN pub_region pr on tca.region=pr.city_code"
			+ " LEFT JOIN t_bpm_process tbp ON tca.process_id=tbp.process_id"
			+ " WHERE tca.process_id = :taskId", nativeQuery = true)
	ChangeApplicationVO findByTaskId(@Param("taskId") String taskId);

}
