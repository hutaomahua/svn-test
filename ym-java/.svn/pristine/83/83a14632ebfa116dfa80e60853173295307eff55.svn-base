package com.lyht.system.dao;


import com.lyht.system.pojo.SysMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月18日 23:07:54
  * 说明:  系统菜单
  */
@Repository
public interface SysMenuDao extends JpaRepository<SysMenu, Integer> {
	@Query(value = "select super_id,ISBTN as isbtn,MENUFLAG as menuflag,MENU_URL as menu_url,MENU_ICON as menu_icon,FLAG as flag,name as name,fcode as fcode,scode as scode,id as id,nm as nm,id as 'key',name as title,id AS value " + 
			"from sys_menu ",nativeQuery = true)
	List<Map<String, Object>> tree();

    @Query(value = "select  a.*,(select b.name as name from sys_menu b where id = a.super_id) as super_name from sys_menu a order by fcode",nativeQuery = true)
    List<Map> findList();

    @Query(value = "SELECT name as name ,menu_url AS path,menu_icon AS icon,id as id,super_id as super_id FROM sys_menu WHERE isbtn = 0 AND nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm in (SELECT role_nm FROM sys_role_staff_ref WHERE staff_nm= :staffNm )) AND super_id IS NULL  ORDER BY fcode ASC",nativeQuery = true)
    List<Map> findListByLoginStaff(@Param("staffNm") String staffNm);

    @Query(value = "SELECT name as name ,menu_url AS path,menu_icon AS icon,id as id,super_id as super_id FROM sys_menu WHERE isbtn = 0 AND super_id IS NULL  ORDER BY fcode ASC",nativeQuery = true)
    List<Map> findAllList();

    @Query(value = "SELECT * FROM sys_menu WHERE isbtn = 0 AND  super_id is null ORDER BY fcode ASC",nativeQuery = true)
    List<Map> findListTop();
    @Query(value = "SELECT * FROM sys_menu WHERE  fcode like :fcodeLike ORDER BY fcode ASC",nativeQuery = true)
    List<Integer> findSonIds(@Param("fcodeLike") String fcodeLike);

    @Query(value = "SELECT nm as 'key',name as title,id AS value,super_id as super_id,id as id FROM sys_menu WHERE isbtn = 0 and  super_id IS NULL and " +
            "IF (:name is not null, NAME LIKE CONCAT('%',:name,'%'), 1=1) order by fcode asc",nativeQuery = true)
    List<Map> getTopBean(@Param("name") String searchName);

    @Query(value = "SELECT  ISBTN as isbtn,MENUFLAG as menuflag,MENU_URL as menu_url,MENU_ICON as menu_icon,FLAG as flag,name as name,fcode as fcode,scode as scode,id as id,nm as nm,id as 'key',name as title,id AS value FROM sys_menu WHERE super_id IS NULL  and " +
            "IF (:name is not null, NAME LIKE CONCAT('%',:name,'%'), 1=1) order by fcode asc",nativeQuery = true)
    List<Map> getTopBeanTwo(@Param("name") String searchName);

    @Query(value = "select son.*,son.id as 'key',son.name as title,son.id AS value,father.name as superName from sys_menu as son left join sys_menu as father on son.super_id = father.id where son.super_id = :superId order by son.fcode desc ",nativeQuery = true)
    List<Map> findBySuperId(@Param("superId") int superId);

    @Query(value = "SELECT name as name ,menu_url AS path,menu_icon AS icon,id as id,super_id as super_id,isbtn  AS hideInMenu FROM sys_menu WHERE isbtn = 0 AND  super_id IS NOT NULL AND nm IN (SELECT menu_nm FROM sys_role_menu_ref WHERE role_nm in (SELECT role_nm FROM sys_role_staff_ref WHERE  staff_nm = :staffNm ) ) ORDER BY fcode ASC",nativeQuery = true)
    List<Map> findBySuperIdNew(@Param("staffNm")String staffNm);

    @Query(value = "SELECT name as name ,menu_url AS path,menu_icon AS icon,id as id,super_id as super_id,isbtn  AS hideInMenu FROM sys_menu WHERE isbtn = 0 AND  super_id IS NOT NULL ORDER BY fcode ASC",nativeQuery = true)
    List<Map> findAllBySuperId();


    @Query(value = "SELECT name as name ,menu_url AS path,menu_icon AS icon,id as id,super_id as super_id FROM sys_menu WHERE id = :id  ORDER BY fcode ASC",nativeQuery = true)
    List<Map> findByIdLogin(@Param("id") Integer id);

    @Query(value = "select * from sys_menu where super_id = :superId and rownum = 1  order by fcode desc",nativeQuery = true)
    SysMenu getSonBean(@Param("superId") int superId);

    @Query(value = "select * from sys_menu where LENGTH(fcode)= 3 and rownum = 1  order by fcode desc ",nativeQuery = true)
    SysMenu getSuperBean();

    @Query(value = "SELECT * FROM sys_menu where 1=1 and " +
            " IF (:name is not null, NAME LIKE CONCAT('%',:name,'%'), 1=1) order by fcode  asc",
            countQuery = "SELECT count(1) FROM (SELECT * FROM sys_menu where 1=1 and " +
                    " IF (:name is not null, NAME LIKE CONCAT('%',:name,'%'), 1=1) order by fcode  asc) c",
            nativeQuery = true)
    Page<SysMenu> findListByLike(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT * FROM sys_menu WHERE super_id IS NULL  ORDER BY fcode DESC limit 1",nativeQuery = true)
    SysMenu getSysMenuBySuperIdIsNull();

    @Query(value = "SELECT * FROM sys_menu WHERE super_id = :superId ORDER BY fcode DESC limit 1",nativeQuery = true)
    SysMenu getSysMenuBySuperId(@Param("superId") int superId);

    @Query(value = "SELECT nm as 'key',name as title,nm AS value,super_id as super_id,id FROM sys_menu WHERE isbtn = 0 and super_id IS NOT NULL ORDER BY scode ASC",nativeQuery = true)
    List<Map> findByNew();

    @Query(value = "SELECT * FROM sys_menu WHERE  IF (:scode is not null, convert(scode, decimal(10.4)) <= convert(:scode, decimal(10.4)),1 = 1 ) and IF (:superId is not null, super_id = :superId, super_id is null ) and  IF (:fix is not null, convert(scode, decimal(10.4)) >= convert(:fix, decimal(10.4)),1 = 1 ) ORDER BY convert(scode, decimal(10.4)) ASC",nativeQuery = true)
    List<SysMenu> findSonScodes(@Param("superId") Integer superId,@Param("fix") String fix,@Param("scode") String scode);

    @Query(value = "SELECT  son.ISBTN as isbtn,son.MENUFLAG as menuflag,son.MENU_URL as menu_url,son.MENU_ICON as menu_icon,son.FLAG as flag,son.name as name,son.fcode as fcode,son.scode as scode,son.id as id,son.nm as nm,son.id as 'key',son.name as title,son.id AS value,son.super_id as super_id,father.name as superName FROM sys_menu as son left join sys_menu as father on son.super_id = father.id  WHERE son.super_id IS NOT NULL ORDER BY son.fcode ASC",nativeQuery = true)
    List<Map> findByNewTwo();

    @Query(value = "SELECT * FROM sys_menu WHERE id = :superId  ORDER BY fcode DESC limit 1",nativeQuery = true)
    SysMenu getNewFcode(@Param("superId") int superId);

    @Query(value = "select * from sys_menu where fcode like :fcodeLike or fcode = :fcode",nativeQuery = true)
    List<SysMenu> findByFcode(@Param("fcodeLike") String fcodeLike,@Param("fcode") String fcode);
    @Modifying
    @Query(value = "DELETE FROM sys_role_menu_ref WHERE menu_nm = :nm",nativeQuery = true)
    int deleteRefSql(@Param("nm")String nm);
    @Modifying
    @Query(value = "delete from sys_menu where fcode like :fcodeLike or fcode = :fcode",nativeQuery = true)
    int deleteByFcodes(@Param("fcodeLike") String fcodeLike,@Param("fcode") String fcode);
}
 