package com.lyht.business.abm.land.dao;

import com.lyht.business.abm.land.entity.RequisitionPlanEntity;
import com.lyht.business.abm.land.bean.RequisitionPlanDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface RequisitionPlanDao extends JpaRepository<RequisitionPlanEntity, Integer> {

    @Query(value = " select SUM(b.sum_task_number)as  sumTaskNumber,SUM(b.sum_invest)as sumInvest ,a.task_name as taskName,a.id,a.nm,region,REPLACE(region_name,CONCAT(SUBSTRING_INDEX(region_name,',',3),','),'') as regionName,task_type as taskType,(select task_content from t_abm_project_implement b where a.task_content=b.nm)taskContent,start_date as startDate,complet_date as completDate,progress,unit_number as  unitNumber,duty_unit as dutyUnit,control_unit as controlUnit "
    		+ "from t_abm_schedule_weekly a left  JOIN t_abm_schedule_details b on a.nm=b.nm "
    		+ "LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
    		+   " where  1 = 1  and " +
            " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
    		"  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
    		"  IF (:taskType is not null,a.task_type LIKE CONCAT('%',:taskType,'%'), 1=1) GROUP BY a.nm "
            ,countQuery=" select count(1) from  t_abm_schedule_weekly a left  JOIN t_abm_schedule_details b on a.nm=b.nm  "
            		+ "LEFT JOIN pub_region AS tpr ON tpr.city_code = a.region "
            		+ " where  1 = 1  and " +
                    " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
            		"  IF (:taskName is not null,a.task_name LIKE CONCAT('%',:taskName,'%'), 1=1) and "+
            		"  IF (:taskType is not null,a.task_type LIKE CONCAT('%',:taskType,'%'), 1=1)  GROUP BY a.nm "
            ,nativeQuery = true)
    Page<Map> getList(Pageable pageable, @Param("region") String region, @Param("taskName") String taskName, @Param("taskType") String taskType);


    //核定人口
    @SuppressWarnings("rawtypes")
	@Query(value = " select (select merger_name from pub_region c where c.city_code=b.region )region, "
    			+ " (select name from pub_dict_value a where a.nm=b.national )national,"+
				 " (select name from pub_dict_value a where a.nm=b.scope )scope, "+
				 " (select name from pub_dict_value a where a.nm=b.households_type )households_type, "+
				 " b.i_population ,count(c.is_satisfy) ycNum,  b.name,b.id_card,b.nm as owner_nm  "+
				 " from  t_info_owner b  "+
				 " left JOIN t_info_family_impl c on b.nm=c.owner_nm "+
				 "  LEFT JOIN pub_region AS tpr ON tpr.city_code = b.region "+
				 "	where 1=1 and c.is_satisfy='2'    "
    	   	     + " and IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) " +
         		   "  and IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1)  "
         		 +  " and b.nm not in :nm  "

    			 + " GROUP BY  b.nm "
            ,countQuery=" select count(1) from  t_info_owner b  "+
   				 " left JOIN t_info_family_impl c on b.nm=c.owner_nm "+
   				 "  LEFT JOIN pub_region AS tpr ON tpr.city_code = b.region "+
   				 "	where 1=1 and c.is_satisfy='2'    "
       	   	     + " and IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) " +
       	      " and IF (:name is not null,b.name LIKE CONCAT('%',:name,'%'), 1=1)  "
      		 +  "  and b.nm not in :nm  "
      		 + " GROUP BY  b.nm "
            ,nativeQuery = true)
    Page<Map> getHdList(Pageable pageable, @Param("region") String region, @Param("name") String name , @Param("nm")List<String>  nm);


    @Query(value = "select  (@i\\:=@i+1)xuhao , "
    		+ "(select REPLACE(a.merger_name,CONCAT(SUBSTRING_INDEX(a.merger_name,',',3),','),'') from pub_region a  where a.city_code=b.region)region,"
    		+ "b.name,b.id_card,b.i_population ,"
    		+ "(select count(b.is_satisfy) from t_info_family_impl b where b.owner_nm=a.owner_nm and is_satisfy='2')ymNum "
    		+ "from t_abm_publicity_details a  "
    		+ "left  join t_info_owner b on a.owner_nm=b.nm ,(SELECT @i\\:=0) AS i where a.nm=:nm "	   	    
			,nativeQuery = true)
    List<Map> getHdListExcl( @Param("nm") String nm);

    @Query(value = " SELECT REPLACE(pr.merger_name,CONCAT(SUBSTRING_INDEX(pr.merger_name,',',3),','),'')  as mergerName,a.* FROM t_abm_schedule_weekly a " +
            " left join pub_region as pr on a.region=pr.city_code  " +
            " where  1 = 1 and a.id = :id    " +
            " order BY a.id ASC ",
          nativeQuery = true)
    Map<String,Object> getDetails(@Param("id")Integer id);

    @Query(value = " SELECT  REPLACE(pr.merger_name,CONCAT(SUBSTRING_INDEX(pr.merger_name,',',3),','),'')  as tabdiming,REPLACE(pr.merger_name,CONCAT(SUBSTRING_INDEX(pr.merger_name,',',3),','),'')  as diming,pr.name as tbname, " +
            "  a.* " +
            "  FROM t_abm_schedule_weekly as  a " +
            " left join pub_region as pr on a.region=pr.city_code  " +
            " where  1 = 1 and IF (:region is not null,a.region like CONCAT('%',:region,'%'), 1=1) " +
            " order BY a.id ASC ",
            nativeQuery = true)
    List<Map> list(@Param("region")String region);
    
    @Query(value = " select task_content as taskName,nm from t_abm_project_implement where task_name=:taskName ",
            nativeQuery = true)
    List<Map> selectName(@Param("taskName")String taskName);
}
