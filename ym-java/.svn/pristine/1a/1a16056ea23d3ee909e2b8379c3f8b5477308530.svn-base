package com.lyht.business.rests.dao;


import com.lyht.business.rests.pojo.LetterManagerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LetterManageDao  extends JpaRepository<LetterManagerEntity, Integer> {

    @Query(value = " select pr.name as diming ,trl.*  " +
            " from t_rests_letter as trl  " +
            " left join  pub_region as pr on trl.region=pr.city_code  " +
            " where 1=1 and " +
            " IF (:parameter is not null && LENGTH(:parameter)>0, replace(pr.merger_name,',','') LIKE CONCAT('%',:parameter,'%') " +
            " or trl.name like CONCAT('%',:parameter,'%') " +
            "  or trl.phone like  CONCAT('%',:parameter,'%')" +
            "  or trl.dwdz like  CONCAT('%',:parameter,'%')  , 1=1)  ",
            countQuery = "SELECT count(1) FROM (select trl.* " +
                    " from t_rests_letter as trl    " +
                    " left join  pub_region as pr on trl.region=pr.city_code  " +
                    " where 1=1 and  " +
                    " IF (:parameter is not null && LENGTH(:parameter)>0, replace(pr.merger_name,',','') LIKE CONCAT('%',:parameter,'%') " +
                    " or trl.name like CONCAT('%',:parameter,'%') " +
                    "  or trl.phone like  CONCAT('%',:parameter,'%') " +
                    "  or trl.dwdz like  CONCAT('%',:parameter,'%')  , 1=1) ) c",nativeQuery = true)
    Page<Map> findPageAndSortByParams(@Param("parameter") String parameter,Pageable pageable);

    /**
     * 查询所有
     * @param parameter
     * @return
     */
    @Query(value = " select pds.name as job,pr.name as diming,pdv.name as sex,trl.* " +
            " from t_rests_letter as trl left join pub_dict_value as pdv on trl.sex_nm=pdv.nm   " +
            "  left join  pub_region as pr on trl.region=pr.city_code  left join  pub_dict_value as pds on trl.job_nm=pds.nm  where 1=1 and" +
            " IF (:parameter is not null && LENGTH(:parameter)>0, replace(pr.merger_name,',','') LIKE CONCAT('%',:parameter,'%') " +
            " or trl.name like CONCAT('%',:parameter,'%') " +
            " or trl.phone like  CONCAT('%',:parameter,'%') " +
            " or trl.dwdz like  CONCAT('%',:parameter,'%')  , 1=1)",
            nativeQuery = true)
    List<Map> list(@Param("parameter") String parameter);

}
