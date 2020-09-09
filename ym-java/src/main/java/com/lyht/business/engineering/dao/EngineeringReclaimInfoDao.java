package com.lyht.business.engineering.dao;

import com.lyht.business.engineering.entity.EngineeringReclaimInfo;
import com.lyht.business.engineering.vo.EngineeringReclaimInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EngineeringReclaimInfoDao extends JpaRepository<EngineeringReclaimInfo, Integer>{
    /**
     * 分页查询
     * @param name
     * @param pageable
     * @return
     */
    @Query(value = "select " +
            "teri.id as id" +
            ",teri.nm as nm" +
            ",teri.land_nm as landNm" +
            ",teri.region as region" +
            ",teri.major_type as majorType" +
            ",teri.area_nm as areaNm" +
            ",teri.plan_quantities as planQuantities" +
            ",teri.plan_budget as planBudget" +
            ",teri.responsible_unit as responsibleUnit" +
            ",teri.construction_budget as constructionBudget" +
            ",teri.is_replace as isReplace" +
            ",teri.replace_unit as replaceUnit" +
            ",teri.design_unit as designUnit" +
            ",teri.build_unit as buildUnit" +
            ",teri.supervisor_unit as supervisorUnit" +
            ",teri.plan_ownership_number as planOwnershipNumber" +
            ",teri.coordinate as coordinate" +
            ",teri.project_ownership_number as projectOwnershipNumber" +
            ",teri.remark as remark" +
            ",teri.create_staff as createStaff" +
            ",teri.create_time as createTime" +
            ",teri.update_staff as updateStaff" +
            ",teri.update_time as updateTime " +
            ",teri.Implement_info_date as Implement_info_date" +
            ",teri.spent_funds as spent_funds" +
            ",teri.immigrants as immigrants" +
            ",teri.progress_evaluate as progressEvaluate" +
            ",area.`name` AS areaName,magor.`name` AS magorName,land.`name` AS landName,REPLACE(pr.merger_name,CONCAT(SUBSTRING_INDEX(pr.merger_name,',',3),','),'') AS diming,pr.merger_name as mergerName " +
            " from (select tt.*,(select progress_evaluate from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as progress_evaluate,(select Implement_info_date from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as Implement_info_date,(select spent_funds from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as spent_funds , (select (settled_immigrants+unsettled_immigrants) from t_engineering_evaluate where engineering_nm = tt.nm ORDER BY Implement_info_date DESC limit 1) as immigrants from t_engineering_reclaim_info tt ) teri " +
            " LEFT JOIN pub_dict_value area on teri.area_nm = area.nm " +
            " LEFT JOIN pub_dict_value magor on teri.major_type = magor.nm " +
            " LEFT JOIN pub_dict_value land on teri.land_nm = land.nm " +
            " LEFT JOIN pub_region pr on teri.region=pr.city_code  " +
            " where 1=1  " +
            " and IF (:region is not null && LENGTH(:region)>0, pr.merger_name LIKE CONCAT('%',:region,'%'), 1=1)  " +
            " and IF (:landNm is not null && LENGTH(:landNm)>0, teri.land_nm LIKE CONCAT('%',:landNm,'%'), 1=1) " +
            " order by teri.id desc ",
            countQuery = "SELECT count(1) FROM (select teri.*,area.`name` AS areaName,magor.`name` AS magorName,land.`name` AS landName,pr.`name` AS diming " +
                    " from t_engineering_reclaim_info teri " +
                    " LEFT JOIN pub_dict_value area on teri.area_nm = area.nm " +
                    " LEFT JOIN pub_dict_value magor on teri.major_type = magor.nm " +
                    " LEFT JOIN pub_dict_value land on teri.land_nm = land.nm " +
                    " LEFT JOIN pub_region pr on teri.region=pr.city_code  " +
                    " where 1=1  " +
                    " and IF (:region is not null && LENGTH(:region)>0, pr.merger_name LIKE CONCAT('%',:region,'%'), 1=1)  " +
                    " and IF (:landNm is not null && LENGTH(:landNm)>0, teri.land_nm LIKE CONCAT('%',:landNm,'%'), 1=1) " +
                    " order by teri.id desc ) c",
            nativeQuery = true)
    Page<EngineeringReclaimInfoVO> findListByLike(@Param("region") String region, @Param("landNm") String landNm, Pageable pageable);

}
