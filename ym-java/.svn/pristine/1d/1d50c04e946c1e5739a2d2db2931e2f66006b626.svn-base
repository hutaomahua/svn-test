package com.lyht.system.dao;

import com.lyht.system.pojo.SysRoleMenuRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMenuRefDao extends JpaRepository<SysRoleMenuRef, Integer> {

    List<SysRoleMenuRef> findByRoleNm(String roleNm);

    int deleteByRoleNm(String roleNm);


    @Query(value = "select nm from sys_menu where id in (select super_id from sys_menu where nm in (?1))",nativeQuery = true)
    List<String> findSuperNm(String[] nms);
}
