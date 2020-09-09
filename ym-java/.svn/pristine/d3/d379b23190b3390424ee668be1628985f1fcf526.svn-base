package com.lyht.business.pub.dao;
 

import com.lyht.business.pub.entity.PubDictName;
import com.lyht.business.pub.vo.PubDictValueVO;

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
  * 脚本日期:2019年2月21日 22:38:35
  * 说明:  字典分类
  */
@Repository
public interface PubDictNameDao extends JpaRepository<PubDictName, Integer> {
    int countByCode(String code);
    @Query(value = "select * from pub_dict_name  where 1=1 and " +
            " IF (:name is not null and :code is not null,( code like CONCAT('%',:code,'%') or name like CONCAT('%',:name,'%') ) , 1=1) order by id  desc",
            countQuery = "SELECT count(1) FROM (select * from pub_dict_name where 1=1 and " +
                    " IF (:name is not null and :code is not null,( code like CONCAT('%',:code,'%') or name like CONCAT('%',:name,'%') ) , 1=1)  order by id  desc) c",
            nativeQuery = true)
    Page<Map> page(@Param("name") String name, @Param("code") String code, Pageable pageable);

    @Query(value = "SELECT pdv.* FROM pub_dict_value  pdv, pub_dict_name  pdn  WHERE pdv.listnm_sys_dict_cate = pdn.code AND 1=1 and " +
            "IF (:code is not null, pdn.code = :code , 1=1) and " +
            "IF (:id is not null, pdn.id = :id , 1=1) and " +
            "IF (:nm is not null, pdn.nm = :nm , 1=1) " +
            "order by id  desc",
            countQuery = "SELECT count(1) FROM (SELECT pdv.* FROM pub_dict_value  pdv, pub_dict_name  pdn  WHERE pdv.listnm_sys_dict_cate = pdn.code AND 1=1 and " +
                    " IF (:code is not null, pdn.code = :code , 1=1) and " +
                    " IF (:id is not null, pdn.id = :id , 1=1) and " +
                    " IF (:nm is not null, pdn.nm = :nm , 1=1)" +
                    " order by id  desc) c",
            nativeQuery = true)
    List<PubDictValueVO> getPubDictValue(@Param("id") Integer id, @Param("nm") String nm, @Param("code") String code);
}
 