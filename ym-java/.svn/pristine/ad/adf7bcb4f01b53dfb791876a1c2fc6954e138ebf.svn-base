package com.lyht.business.land.dao;


import com.lyht.business.land.entity.LandApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月19日 9:51:20
  * 说明:  账户信息
  */
@Repository
public interface LandApplyDao extends JpaRepository<LandApply, Integer> {

    /**
     * 分页查询
     * @param name
     * @param pageable
     * @return
     */
    @Query(value = "SELECT dk.name as dikuai, tt.*,REPLACE(tpr.merger_name,CONCAT(SUBSTRING_INDEX(tpr.merger_name,',',3),','),'') AS diming,( SELECT pdv1.`name` AS jindu  FROM  t_land_process AS pls " +
            " LEFT JOIN pub_dict_value AS pdv1 ON pdv1.nm = pls.process_step WHERE " +
            "1=1 AND pls.land_nm = tt.nm and  pls.id =(  SELECT MAX(pls.id)  FROM t_land_process AS pls LEFT JOIN pub_dict_value AS pdv1 ON pdv1.nm = pls.process_step WHERE 1 = 1  AND pls.land_nm = tt.nm )  ) AS jindu, " +
            " (select p_text FROM t_land_problem as tlp where tlp.land_nm = tt.nm ORDER BY create_time DESC  LIMIT 1) as pText " +
            " FROM t_land_apply AS tt  LEFT JOIN pub_region AS tpr ON tpr.city_code = tt.region   LEFT JOIN pub_dict_value AS dk ON dk.nm = tt.land  where 1=1 and " +
            " IF (:name is not null && LENGTH(:name)>0, tt.batch_name LIKE CONCAT('%',:name,'%'), 1=1) and" +
            " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
            " IF (:applyType is not null, tt.apply_type = :applyType , 1=1) order by tt.id desc ",
            countQuery = "SELECT count(1) FROM (SELECT tt.* FROM t_land_apply as tt  LEFT JOIN pub_region AS tpr ON tpr.city_code = tt.region  where 1=1 and " +
                    " IF (:name is not null && LENGTH(:name)>0,  tt.batch_name LIKE CONCAT('%',:name,'%') , 1=1) and" +
                    " IF (:region is not null && LENGTH(:region)>0, tpr.merger_name LIKE CONCAT('%',:region,'%'), 1=1) and" +
                    " IF (:applyType is not null, tt.apply_type = :applyType , 1=1) order by tt.id desc) c",
            nativeQuery = true)
    Page<Map> findListByLike(@Param("region") String region, @Param("name") String name, @Param("applyType")String applyType, Pageable pageable);

    /**
     * 查询所有
     * @param applyType
     * @return
     */
    @Query(value = "SELECT dk.name as dikuai, tt.*,tpr.merger_name AS diming,( SELECT pdv1.`name` AS jindu  FROM  t_land_process AS pls " +
            " LEFT JOIN pub_dict_value AS pdv1 ON pdv1.nm = pls.process_step WHERE 1=1 AND pls.land_nm = tt.nm and  pls.id =(  SELECT MAX(pls.id)  FROM t_land_process AS pls LEFT JOIN pub_dict_value AS pdv1 ON pdv1.nm = pls.process_step WHERE 1 = 1  AND pls.land_nm = tt.nm )  ) AS jindu " +
            " FROM t_land_apply AS tt  LEFT JOIN pub_region AS tpr ON tpr.city_code = tt.region   LEFT JOIN pub_dict_value AS dk ON dk.nm = tt.land  where 1=1 and " +
            " IF (:applyType is not null, tt.apply_type = :applyType , 1=1) order by tt.xuhao ASC ",
            nativeQuery = true)
    List<Map> findByApplyType( @Param("applyType")String applyType);
    /**
     * 通过模糊查询查询全部
     * @param applyType
     * @return
     */
    List<LandApply> findByApplyTypeLike(String applyType);
}
 