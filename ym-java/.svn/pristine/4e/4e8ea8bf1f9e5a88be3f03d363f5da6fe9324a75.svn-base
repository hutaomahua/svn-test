package com.lyht.business.land.dao;

import com.lyht.business.land.entity.LandPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author HuangAn
 * @date 2019/10/15 10:16
 */
@Repository
public interface LandPlanDao extends JpaRepository<LandPlan, Integer> {
    /**
     * 查询报批进度
     * @param nm
     * @param applyType
     * @param processStep
     * @return
     */
    @Query(value = " SELECT    pdv.sorted as step, tt.id, tl.nm as nm , tl.land_name  as landName,tt.process_step as processStep ,tt.create_time as registerDate ,tt.p_text as remark,pf.file_url as fileUrl , " +
            " pf.file_type AS fileType , pf.file_name as fileName FROM    t_land_apply as tl inner JOIN t_land_process AS tt ON tl.nm = tt.land_nm " +
            " left JOIN pub_files AS pf ON tt.nm = pf.table_pk_column   left JOIN  pub_dict_value as pdv on tt.process_step=pdv.nm  WHERE 1=1 and" +
            " IF (:nm is not null && LENGTH(:nm)>0, tl.nm = :nm, 1=1 ) and " +
            " IF (:applyType is not null && LENGTH(:applyType)>0, tl.apply_type = :applyType, 1=1 ) and " +
            " IF (:processStep is not null && LENGTH(:processStep)>0, tt.process_step = :processStep, 1=1 ) " +
            "  group by tt.id  desc  ",
            nativeQuery = true)
    List<Map> list(@Param("nm") String nm, @Param("applyType")String applyType,@Param("processStep")String processStep);

    @Query(value = "select  tt.*  from  t_land_process AS tt inner join  pub_dict_value as pdv on tt.process_step=pdv.nm  WHERE 1=1 and " +
            " IF (:landNm is not null && LENGTH(:landNm)>0, tt.land_nm = :landNm, 1=1 ) and " +
            " IF (:processStep is not null && LENGTH(:processStep)>0, tt.process_step = :processStep, 1=1 ) ",
            nativeQuery = true)
    List<Map> byLandNm(@Param("landNm") String landNm,@Param("processStep")String processStep);
}
