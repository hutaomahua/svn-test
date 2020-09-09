package com.lyht.business.engineering.dao;

import com.lyht.business.engineering.entity.EngineeringNewTown;
import com.lyht.business.engineering.vo.EngineeringNewTownVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EngineeringNewTownDao extends JpaRepository<EngineeringNewTown, Integer>{
    /**
     * 分页查询
     * @param name
     * @param pageable
     * @return
     */
    @Query(value = "select tent.major_type as majorType,tent.town_name as townName,tent.set_type as setType,tent.project_place_scale as projectPlaceScale,tent.project_budget as projectBudget,tent.responsible_unit as responsibleUnit" +
            ",tent.is_replace as isReplace" +
            ",tent.replace_unit as replaceUnit" +
            ",tent.design_unit as designUnit" +
            ",tent.build_unit as buildUnit" +
            ",tent.supervisor_unit as supervisorUnit" +
            ",tent.plan_ownership_number as planOwnershipNumber" +
            ",tent.project_ownership_number as projectOwnershipNumber" +
            ",tent.lag_reason as lagReason" +
            ",tent.coordinate as coordinate" +
            ",tent.remark as remark" +
            ",tent.dealing_staff as dealingStaff" +
            ",tent.create_staff as createStaff" +
            ",tent.create_time as createTime" +
            ",tent.update_staff as updateStaff" +
            ",tent.update_time as updateTime" +
            ",tent.Implement_info_date as Implement_info_date" +
            ",tent.spent_funds as spent_funds" +
            ",tent.immigrants as immigrants" +
            ",tent.progress_evaluate as progressEvaluate" +
            ",tent.construction_budget as constructionBudget,tent.id,tent.area_nm as areaNm,tent.nm" +
            " ,area.`name` AS areaName,magor.`name` AS magorName,set_t.`name` AS setName,tent.region AS region,pr.merger_name as mergerName,REPLACE(pr.merger_name,CONCAT(SUBSTRING_INDEX(pr.merger_name,',',3),','),'') AS diming" +
            " from (select tt.*,(select progress_evaluate from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as progress_evaluate,(select Implement_info_date from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as Implement_info_date,(select spent_funds from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as spent_funds , (select (settled_immigrants+unsettled_immigrants) from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as immigrants from t_engineering_new_town tt ) tent" +
            " LEFT JOIN pub_dict_value area on tent.area_nm = area.nm" +
            " LEFT JOIN pub_dict_value magor on tent.major_type = magor.nm" +
            " LEFT JOIN pub_dict_value set_t on tent.set_type = set_t.nm" +
            " LEFT JOIN pub_region pr on tent.region=pr.city_code " +
            " where 1=1 " +
            " and IF (:region is not null && LENGTH(:region)>0, pr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) " +
            " and IF (:name is not null && LENGTH(:name)>0, tent.town_name LIKE CONCAT('%',:name,'%'), 1=1) " +
            " order by tent.id asc ",
            countQuery = "SELECT count(1) FROM (select tent.*,area.`name` AS areaName,magor.`name` AS magorName,set_t.`name` AS setName,pr.`name` AS diming " +
                    " from (select tt.*,tee.Implement_info_date,tee.spent_funds,(tee.settled_immigrants+tee.unsettled_immigrants) as immigrants from t_engineering_new_town tt LEFT JOIN t_engineering_evaluate tee on tee.engineering_nm = tt.nm   ORDER BY Implement_info_date DESC limit 1) tent" +
                    " LEFT JOIN pub_dict_value area on tent.area_nm = area.nm " +
                    " LEFT JOIN pub_dict_value magor on tent.major_type = magor.nm " +
                    " LEFT JOIN pub_dict_value set_t on tent.set_type = set_t.nm " +
                    " LEFT JOIN pub_region pr on tent.region=pr.city_code  " +
                    " where 1=1  " +
                    " and IF (:region is not null && LENGTH(:region)>0, pr.merger_name LIKE CONCAT('%',:region,'%'), 1=1)  " +
                    " and IF (:name is not null && LENGTH(:name)>0, tent.town_name LIKE CONCAT('%',:name,'%'), 1=1) ) c",
            nativeQuery = true)
    Page<EngineeringNewTownVO> findListByLike(@Param("region") String region, @Param("name") String name, Pageable pageable);

}
