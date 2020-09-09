package com.lyht.system.dao;

import com.lyht.system.pojo.SysRoleStaffRef;
import com.lyht.system.vo.StaffRoleRefVO;
import com.lyht.system.vo.SysRoleStaff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysRoleStaffRefDao extends JpaRepository<SysRoleStaffRef, Integer> {
    List<SysRoleStaffRef> findByStaffNm(String staffNm);

    int deleteByRoleNm(String roleNm);

    int deleteByStaffNm(String staffNm);

    List<SysRoleStaffRef> findByRoleNm(String roleNm);


    @Query(value = "select id as id,code as code,role_nm as role_nm,staff_nm as staff_nm,flag as flag from sys_role_staff_ref where role_nm = :roleNm ",nativeQuery = true)
    List<Map> findRoleStaffRefByRoleNm(@Param("roleNm") String role_nm);
    
    @Query(value = "select "
    		+ " tt.id AS id, tt.code AS code, tt.staff_nm AS staffNm,"
    		+ " tt.role_nm AS roleNm, tt.flag AS flag, tt.uuid AS uuid,"
    		+ " role.uuid AS roleUuid, staff.uuid AS staffUuid"
    		+ " FROM sys_role_staff_ref tt"
    		+ " LEFT JOIN sys_role AS role ON tt.role_nm = role.nm"
    		+ " LEFT JOIN sys_staff AS staff ON tt.staff_nm = staff.nm",nativeQuery = true)
    List<SysRoleStaff> findAllByRoleAndStaff();
    
    @Query(value = " select t1.nm,t1.code,t1.name from sys_role_staff_ref tt " + 
    		" left join sys_role t1 on tt.role_nm = t1.nm " + 
    		" where tt.staff_nm = ?1 ",nativeQuery = true)
    List<StaffRoleRefVO> getRoleRef(String staffNm);


}
