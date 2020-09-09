package com.lyht.system.dao;


import com.lyht.system.bean.SysMenuTree;
import com.lyht.system.pojo.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月18日 15:47:40
  * 说明:  系统角色
  */
@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Integer> {

    @Query(value = " select (select count(*) from sys_menu where nm in( select menu_nm from SYS_ROLE_MENU_REF where role_nm = role.nm)) as menus,(select group_concat(STAFF_NAME) from sys_staff where nm in(select staff_nm from SYS_ROLE_STAFF_REF where role_nm = role.NM)) as staffs,role.* , " +
            "(select group_concat(staff_name)  from sys_staff where nm in (select staff_nm from sys_role_staff_ref where role_nm =role.nm )) as staff_name ," +
            "(select  count(id)  from sys_role_menu_ref where role_nm =role.nm )  as menu_count " +
            "from sys_role  role   where 1=1 and IF (:name is not null, role.name LIKE CONCAT('%',:name,'%'), 1=1) order by id  desc ",
            countQuery = "SELECT count(1) FROM (select (select count(*) from sys_menu where nm in( select menu_nm from SYS_ROLE_MENU_REF where role_nm = role.nm)) as menus,(select group_concat(STAFF_NAME) from sys_staff where nm in(select staff_nm from SYS_ROLE_STAFF_REF where role_nm = role.NM)) as staffs,role.* , " +
                    " (select group_concat(staff_name)  from sys_staff where nm in (select staff_nm from sys_role_staff_ref where role_nm =role.nm )) as staff_name , " +
                    " (select  count(id)  from sys_role_menu_ref where role_nm =role.nm )  as menu_count " +
                    " from sys_role  role   where 1=1 and IF (:name is not null, role.name LIKE CONCAT('%',:name,'%'), 1=1) order by id  desc) c",nativeQuery = true)
    Page<Map> getList(@Param("name") String name, Pageable pageable);

    @Query(value = "select * from SYS_ROLE where nm in (select Role_NM from SYS_ROLE_STAFF_REF where Staff_nm = :staffNm)",nativeQuery = true)
    List<SysRole> findRolesByStaffNm(@Param("staffNm") String staffNm);

    @Query(value = "select (select b.name from sys_menu b where b.id = a.SUPER_ID) as superName,a.* from SYS_MENU a where a.nm in (select MENU_NM from SYS_ROLE_MENU_REF where role_nm = :roleNm)",nativeQuery = true)
    List<Map> findMenusByRoleNm(@Param("roleNm") String roleNm);

    @Query(value = "select ss.*  from SYS_STAFF ss where ss.nm in (select STAFF_NM from SYS_ROLE_STAFF_REF where ROLE_NM = :roleNm)",nativeQuery = true)
    List<Map> findStaffsByRoleNm(@Param("roleNm") String roleNm);

    Optional<SysRole> findById(Integer id);
}
 