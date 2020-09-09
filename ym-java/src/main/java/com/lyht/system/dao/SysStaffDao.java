package com.lyht.system.dao;

import com.lyht.system.bean.SysStaffDetail;
import com.lyht.system.pojo.SysStaff;
import com.lyht.system.vo.SysStaffAcct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 创建人： liuamang 脚本日期:2019年2月19日 10:02:12 说明: 人员信息
 */
@Repository
public interface SysStaffDao extends JpaRepository<SysStaff, Integer> {
	List<SysStaff> findByStaffName(String name);

	SysStaff findByNm(String nm);

	@Query(value = "select id,nm,staff_name from sys_staff where staff_name like '%:name%' ", nativeQuery = true)
	List<SysStaff> getStaffLikeName(@Param("name") String name);

	@Query(value = "select ss.id,ss.nm,ss.remark,ss.sort_num,ss.staff_birthday,ss.staff_code,ss.staff_ethnic,ss.staff_link,ss.staff_name,ss.staff_origin,ss.staff_sex,ss.staff_type,ss.state,sd.name AS dept_code,sa.name AS zhanghao,sd.id as dept_codes,pdv.code as code,pdvs.name as educationName,ss.staff_education as staff_education,pdvss.name as positionName,ss.staff_position     FROM sys_staff ss LEFT JOIN sys_acct sa ON ss.nm = sa.staff_nm left join sys_dept sd ON sd.id = ss.dept_code left join pub_dict_value pdv on pdv.nm = ss.staff_ethnic  LEFT JOIN pub_dict_value pdvs ON pdvs.nm = ss.staff_education  LEFT JOIN pub_dict_value pdvss ON pdvss.nm = ss.staff_position    WHERE ss.id = :id ", nativeQuery = true)
	List<Map> detail(@Param("id") Integer id);

	@Query(value = "select  ss.id,ss.nm,ss.remark,ss.sort_num,ss.staff_birthday,ss.staff_code,"
			+ "ss.staff_ethnic,ss.staff_link,ss.staff_name,ss.staff_origin,ss.staff_sex,"
			+ "ss.staff_type,ss.state,sd.name AS dept_code FROM sys_staff ss LEFT JOIN sys_dept sd ON ss.dept_code = sd.fcode  where 1=1 and "
			+ " IF (:deptCode is not null && LENGTH(:deptCode)>0, ss.dept_code LIKE CONCAT(:deptCode,'%'), 1=1 ) and"
			+ " IF (:searchName is not null && LENGTH(:searchName)>0, ss.staff_name LIKE CONCAT('%',:searchName,'%') or ss.staff_origin LIKE CONCAT(:searchName,'%'), 1=1 ) and "
			+ " IF (:staffType is not null && LENGTH(:staffType)>0, ss.staff_type LIKE CONCAT(:staffType,'%'), 1=1 )  ", nativeQuery = true)
	List<Map> list(@Param("staffType") Integer staffType, @Param("deptCode") String deptCode,
			@Param("searchName") String searchName);

	@Query(value = "SELECT ss.id as id,ss.nm as nm,ss.remark as remark ,ss.sort_num as sort_num,ss.staff_birthday as staff_birthday,ss.staff_code,ss.Ethnic AS staff_ethnic,ss.staff_link as staff_link,ss.staff_name as staff_name,ss.staff_origin as staff_origin,ss.staff_sex as staff_sex,ss.staff_type as staff_type,ss.state as state ,sd.name AS dept_code,pdvs.name as educationName,ss.staff_education as staff_education,pdvss.name as positionName,ss.staff_position "
			+ "FROM (SELECT ssc.*,pdv.name AS Ethnic FROM  sys_staff ssc LEFT JOIN pub_dict_value pdv ON  ssc.staff_ethnic = pdv.nm ) ss LEFT JOIN sys_dept sd   ON ss.dept_code = sd.id LEFT JOIN pub_dict_value pdvs ON pdvs.nm = ss.staff_education  LEFT JOIN pub_dict_value pdvss ON pdvss.nm = ss.staff_position   WHERE 1=1 and if(:nm is not null,ss.nm !=:nm,1=1) and "
			+ " IF (:name is not null && LENGTH(:name)>0, staff_name LIKE CONCAT('%',:name,'%') or staff_origin LIKE CONCAT('%',:name,'%') , 1=1 ) and "
			+ " IF (:deptCode is not null && LENGTH(:deptCode)>0,  sd.id in  (SELECT id FROM sys_dept WHERE fcode LIKE (SELECT CONCAT(fcode,'%') FROM sys_dept sdd WHERE sdd.id = :deptCode)) , 1=1 ) order by ss. ID  desc", countQuery = "SELECT count(1) FROM (SELECT ss.id as id,ss.nm as nm,ss.remark as remark ,ss.sort_num as sort_num,ss.staff_birthday as staff_birthday,ss.staff_code,ss.Ethnic AS staff_ethnic,ss.staff_link as staff_link,ss.staff_name as staff_name,ss.staff_origin as staff_origin,ss.staff_sex as staff_sex,ss.staff_type as staff_type,ss.state as state ,sd.name AS dept_code "
					+ " FROM (SELECT ssc.*,pdv.name AS Ethnic FROM  sys_staff ssc LEFT JOIN pub_dict_value pdv ON  ssc.staff_ethnic = pdv.nm ) ss LEFT JOIN sys_dept sd   ON ss.dept_code = sd.id  WHERE 1=1 and "
					+ " if(:nm is not null,ss.nm !=:nm,1=1) and IF (:name is not null && LENGTH(:name)>0, staff_name LIKE CONCAT('%',:name,'%') or staff_origin LIKE CONCAT('%',:name,'%') , 1=1 ) and "
					+ "  IF (:deptCode is not null && LENGTH(:deptCode)>0,  sd.id in  (SELECT id FROM sys_dept WHERE fcode LIKE (SELECT CONCAT(fcode,'%') FROM sys_dept sdd WHERE sdd.id = :deptCode)) , 1=1 ) order by ss. ID  desc) c", nativeQuery = true)
	Page<Map> page(@Param("name") String name, @Param("deptCode") String deptCode, @Param("nm") String nm,
			Pageable pageable);

	@Query(value = " SELECT (select name from SYS_ROLE where nm IN (select role_nm from SYS_ROLE_STAFF_REF where STAFF_nm = ss.nm) and ROWNUM=1 )  as roleName,(select count(*) from DEV_RUN_LOG where STAFF_ID = ss.id and PROCESS_STATUS = 'FB87618D0D') as runlogNumber, ss.id,ss.nm,ss.remark ,ss.sort_num,ss.staff_birthday,ss.staff_code as staff_code,ss.Ethnic AS staff_ethnic,ss.staff_link as staff_link,ss.staff_name as staff_name,ss.staff_origin as staff_origin,ss.staff_sex as staff_sex,ss.staff_type as staff_type,ss.state as state ,sd.name AS dept_code"
			+ " FROM (SELECT ssc.*,pdv.name AS Ethnic FROM  sys_staff ssc LEFT JOIN pub_dict_value pdv ON  ssc.staff_ethnic = pdv.nm ) ss "
			+ " LEFT JOIN sys_dept sd   ON ss.dept_code = sd.id  WHERE 1=1  and "
			+ " IF (:name is not null && LENGTH(:name)>0, staff_name LIKE CONCAT('%',:name,'%') or staff_origin LIKE CONCAT('%',:name,'%') , 1=1 ) and "
			+ " IF (:deptCode is not null  && LENGTH(:deptCode)>0,  sd.id in  (SELECT id FROM sys_dept WHERE fcode LIKE (SELECT CONCAT(fcode,'%') FROM sys_dept sdd WHERE sdd.id = :deptCode)) , 1=1 ) order by ss. ID  desc", countQuery = "SELECT count(1) FROM (SELECT * "
					+ "  FROM (SELECT ssc.*,pdv.name AS Ethnic FROM  sys_staff ssc LEFT JOIN pub_dict_value pdv ON  ssc.staff_ethnic = pdv.nm ) ss "
					+ "   LEFT JOIN sys_dept sd   ON ss.dept_code = sd.id  WHERE 1=1  and "
					+ "  IF (:name is not null  && LENGTH(:name)>0, staff_name LIKE CONCAT('%',:name,'%') or staff_origin LIKE CONCAT('%',:name,'%') , 1=1 ) and "
					+ "  IF (:deptCode is not null  && LENGTH(:deptCode)>0,  sd.id in  (SELECT id FROM sys_dept WHERE fcode LIKE (SELECT CONCAT(fcode,'%') FROM sys_dept sdd WHERE sdd.id = :deptCode)) , 1=1 ) order by ss. ID  desc) c", nativeQuery = true)
	Page<SysStaffDetail> pages(@Param("name") String name, @Param("deptCode") String deptCode, Pageable pageable);
	
	@Query(value = "select  ss.id AS id, ss.nm AS nm, ss.staff_type AS staffType, ss.staff_code AS staffCode,"
			+ " ss.staff_name AS staffName, ss.staff_sex AS staffSex, ss.staff_ethnic AS staffEthnic,"
			+ " ss.staff_birthday AS staffBirthday,ss.staff_link AS staffLink, ss.staff_origin AS staffOrigin,"
			+ " ss.staff_education AS staffEducation, ss.staff_position AS staffPosition,"
			+ " ss.dept_Code AS deptCode, ss.sort_num AS sortNum, ss.remark AS remark, ss.state AS state,"
			+ " ss.uuid AS uuid, acct.name AS account, acct.pwd AS password"
			+ " FROM sys_staff ss "
			+ " LEFT JOIN sys_acct acct ON ss.nm = acct.staff_nm", nativeQuery = true)
	List<SysStaffAcct> findAllByAcctAndStaff();
	
	SysStaff findByUuid(String uuid);



	@Query(value = "SELECT nm FROM sys_staff where dept_code = (SELECT id FROM sys_dept where nm = :deptNm) ", nativeQuery = true)
	List<String> queryStaffNmByDeptNm(@Param("deptNm") String deptNm);

}
