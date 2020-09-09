package com.lyht.system.dao;


import com.lyht.system.bean.SysDeptDetail;
import com.lyht.system.pojo.SysDept;
import com.lyht.system.vo.SysDeptVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月19日 9:52:43
  * 说明:  单位部门
  */
@Repository
public interface SysDeptDao extends JpaRepository<SysDept, Integer> {
	
	@Query(value = "select * from sys_dept where dept_type = ?",nativeQuery = true)
	List<SysDept> findByDeptType(String deptType);

    SysDept findByNm(String nm);
    @Override
    Optional<SysDept> findById(Integer id);

    @Modifying
    @Query(value="UPDATE sys_dept SET fcode = REPLACE(fcode,:oldfCode,:newfCode) WHERE fcode LIKE :oldCodes%" ,nativeQuery = true)
    void updateSysDeptSon(@Param("oldfCode") String oldfCode, @Param("newfCode") String newfCode, @Param("oldCodes") String oldCodes) ;

    @Query(value = "select  *,(select b.name from sys_dept b where id = a.super_id) as super_name from sys_dept a order by fcode", nativeQuery = true)
    List<SysDeptDetail> findList();

    @Query(value = "SELECT * FROM sys_dept where 1=1 and " +
            " IF (:name is not null && LENGTH(:name)>0, NAME LIKE CONCAT('%',:name,'%'), 1=1) order by id  desc",
            countQuery = "SELECT count(1) FROM (SELECT * FROM sys_dept where 1=1 and " +
                    " IF (:name is not null && LENGTH(:name)>0, NAME LIKE CONCAT('%',:name,'%'), 1=1) order by id  desc) c",
            nativeQuery = true)
    Page<SysDeptDetail> findListOrName(@Param("name") String name, Pageable pageable);

    @Query(value = " select * from sys_dept where super_id = :superId order by fcode desc limit 1 ",nativeQuery = true)
    SysDept getSonBean(@Param("superId") int superId);

    @Query(value = "select * from sys_dept where LENGTH(fcode)= 3  order by fcode desc limit 1 ",nativeQuery = true)
    SysDept getSuperBean();

    @Query(value = "select id from sys_dept where fcode  LIKE CONCAT(:fcodes,'%')  order by fcode desc",nativeQuery = true)
    List<Integer> getSonIds(@Param("fcodes") String fcodes);

    @Modifying
    @Query(value = "delete from sys_dept where fcode  LIKE CONCAT(:fcodes,'%') or fcode = :fcodes",nativeQuery = true)
    void deleteSysDept(@Param("fcodes") String fcode);

    @Query(value = "select id as id,staff_name as staff_name from sys_staff where dept_code = :deptCode ",nativeQuery = true)
    List<SysDeptVo> getUsers(@Param("deptCode") Integer deptCode);

    @Query(value = "SELECT  id AS 'key',nm AS value,NAME AS title,fcode as fCode   FROM sys_dept WHERE super_id IS NULL order by fcode asc",nativeQuery = true)
    List<Map> getTopBean01();

    @Query(value = "select  id as 'key',nm AS value,NAME AS title,fcode as fCode   from sys_dept where super_id = :superId order by fcode desc ",nativeQuery = true)
    List<Map> findBySuperId01(@Param("superId") int superId);

    @Query(value = "select  id as 'key',nm AS value,NAME AS title,fcode as fCode,super_id as SuperId    from sys_dept where super_id is not null order by fcode desc ",nativeQuery = true)
    List<Map> findSon01();

    @Query(value = "select  id as 'key',nm as nm,NAME AS title,id AS value from sys_dept where super_id = :SuperId order by fcode desc ",nativeQuery = true)
    List<Map> findBySuperId(@Param("SuperId") int SuperId);

    @Query(value = "select  sd.id as 'key',sd.nm as nm,sd.NAME AS title,sd.id AS value,sd.super_id as SuperId ,dk.name as deptType  FROM sys_dept sd left join pub_dict_value  dk  on sd.dept_type = dk.nm where sd.super_id is not null order by sd.fcode desc ",nativeQuery = true)
    List<Map> findSon();

    @Query(value = "SELECT  sd.id as 'key',sd.nm as nm,sd.NAME AS title,sd.id AS value ,dk.name as deptType  FROM sys_dept sd left join pub_dict_value  dk  on sd.dept_type = dk.nm WHERE 1=1  and " +
            " IF (:name is not null  && LENGTH(:name)>0, sd.NAME LIKE CONCAT('%',:name,'%'), sd.super_id IS NULL ) and" +
            " IF (:deptType is not null  && LENGTH(:deptType)>0, sd.dept_type LIKE CONCAT('%',:deptType,'%'), 1 = 1 )   order by sd.fcode asc ",nativeQuery = true)
    List<Map> getTopBean(@Param("name") String name,@Param("deptType")String deptType);

    @Query(value = "SELECT * FROM sys_dept WHERE super_id = :SuperId ORDER BY scode DESC  limit 1",nativeQuery = true)
    SysDept getNewScode(@Param("SuperId") int SuperId);

    @Query(value = "SELECT * FROM sys_dept WHERE id = :id  ORDER BY scode DESC limit 1",nativeQuery = true)
    SysDept getNewFcode(@Param("id") int id);

    @Query(value = "SELECT * FROM sys_dept WHERE (super_id IS NULL OR super_id = '') ORDER BY scode DESC limit 1", nativeQuery = true)
    SysDept getNewFcodeTwo();
}
 