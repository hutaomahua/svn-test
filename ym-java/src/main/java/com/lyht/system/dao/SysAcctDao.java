package com.lyht.system.dao;


import com.lyht.system.pojo.SysAcct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月19日 9:51:20
  * 说明:  账户信息
  */
@Repository
public interface SysAcctDao extends JpaRepository<SysAcct, Integer> {

    SysAcct findByName(String name);

    SysAcct findByStaffNm(String staffNm);

    @Query( value = "select * from sys_acct  " +
            "where  1 = 1 and " +
            "IF (:staffName is not null,staff_name LIKE CONCAT('%',:staffName,'%'), 1=1) and  "+
            "IF (:staffLink is not null,staff_link LIKE CONCAT('%',:staffLink,'%'), 1=1) and  "+
            "IF (:remark is not null,remark LIKE CONCAT('%',:remark,'%'), 1=1) order by id  desc ", nativeQuery = true)
    public Page<SysAcct> getListByStaffNameOrStaffLinkOrRemark(@Param("staffName") String staffName, @Param("staffLink") String staffLink, @Param("remark") String remark, Pageable pageable) ;

}
 