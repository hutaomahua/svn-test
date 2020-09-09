package com.lyht.business.pub.dao;


import com.lyht.business.pub.entity.PubProjectInfo;
import com.lyht.business.pub.vo.PubProjectInfoDetail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月21日 22:38:35
  * 说明:  字典分类
  */
@Repository
public interface PubProjectInfoDao extends JpaRepository<PubProjectInfo, Integer> {

    @Query(value = "select ppi.*, tpr.merger_name AS diming ,pdv.name as isStart, pdvS.name as isComplete from pub_project_info ppi " +
            "LEFT JOIN pub_region  tpr ON ppi.region = tpr.city_code " +
            "LEFT JOIN pub_dict_value  pdv ON ppi.is_start=pdv.id " +
            "LEFT JOIN pub_dict_value  pdvS ON ppi.is_complete=pdvS.id  where 1=1 and " +
            "IF (:mergerName is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:mergerName,'%'), 1=1) and " +
            "IF (:pName is not null, ppi.p_name like CONCAT('%',:pName,'%'), 1=1)  " +
            " order by ppi.id  desc",
            countQuery = "select ppi.*, tpr.merger_name AS diming ,pdv.name as isStart, pdvS.name as isComplete from pub_project_info ppi " +
                    "LEFT JOIN pub_region  tpr ON ppi.region = tpr.city_code " +
                    "LEFT JOIN pub_dict_value  pdv ON ppi.is_start=pdv.id " +
                    "LEFT JOIN pub_dict_value  pdvS ON ppi.is_complete=pdvS.id  where 1=1 and " +
                    "IF (:mergerName is not null, REPLACE(tpr.merger_name,',','') like CONCAT('%',:mergerName,'%'), 1=1) and " +
                    "IF (:pName is not null, ppi.p_name like CONCAT('%',:pName,'%'), 1=1)  " +
                    " order by ppi.id  desc",
            nativeQuery = true)
    Page<PubProjectInfoDetail> page(@Param("mergerName") String mergerName, @Param("pName") String pName, Pageable pageable);

    @Query(value = "select * from pub_project_info  where 1=1 and " +
            "IF (:regions is not null, REPLACE(region,',','') like CONCAT('%',:regions,'%'), 1=1) and " +
            "IF (:pName is not null, p_name like CONCAT('%',:pName,'%'), 1=1)  " +
            " order by ppi.id  desc",
            nativeQuery = true)
    List<PubProjectInfo> list(@Param("regions") String regions, @Param("pName") String pName);

}
 