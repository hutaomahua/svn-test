package com.lyht.business.pub.dao;
 

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.pub.entity.PubSet;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月21日 22:38:58
  * 说明:  系统设置
  */
@Repository
public interface PubSetDao extends JpaRepository<PubSet, Integer> {
    @Query(value = "select * from pub_set  where 1=1 and " +
            "IF (:name is not null, name like CONCAT('%',:pName,'%'), 1=1)  " +
            " order by id  desc",
            countQuery = "select * from pub_set  where 1=1 and " +
                    "IF (:name is not null, name like CONCAT('%',:name,'%'), 1=1)  " +
                    " order by id  desc",
            nativeQuery = true)
    Page<PubSet> page(@Param("name") String name, Pageable pageable);
}
 