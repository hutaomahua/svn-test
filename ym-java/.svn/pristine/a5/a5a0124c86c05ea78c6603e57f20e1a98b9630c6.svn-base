package com.lyht.system.dao;

import com.lyht.system.bean.RolePartitionRefDetail;
import com.lyht.system.pojo.SysRolePartitionRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysRolePartitionRefDao extends JpaRepository<SysRolePartitionRef, Integer> {

    int deleteByPartitionNm(String partitionNm);

    int deleteByRoleNm(String roleNm);

    @Query(value = "select id,code,role_nm,partition_nm,flag from sys_role_partition_ref where role_nm = :roleNm ",nativeQuery = true)
    List<RolePartitionRefDetail> findRolePartitionRefByRoleNm(@Param("roleNm") String roleNm);

    @Query(value =  "select * from sys_role_partition_ref where 1 = 1 and " +
                    "IF (:roleNms is not null, role_nm in (:roleNms), 1=1)",nativeQuery = true)
    List<Map> findRolePartitionRefByRequest(@Param("roleNms") String roleNms);

}
