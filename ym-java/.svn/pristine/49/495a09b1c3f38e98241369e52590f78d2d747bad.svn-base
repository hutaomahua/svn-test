package com.lyht.system.dao;

import com.lyht.system.pojo.SysLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 创建人： Yanxh 
 * 脚本日期:2020年1月7日
 * 说明:  系统日志
 */
@Repository
public interface SysLogDao extends JpaRepository<SysLog, Integer> {

	@Query(value="select * FROM sys_log sl where 1 = 1 "+
				"and sl.nm 				= :nm "+
				"and sl.menuflag 		= :menuflag "+
				"and sl.dictnm_opttype 	= :dictnmOpttype "+
				" GROUP BY opernm  order by sl.logtime desc ",
				nativeQuery = true)
	List<SysLog> list(@Param("nm") String nm,@Param("menuflag") String menuflag,@Param("dictnmOpttype") String dictnmOpttype);

	@Query(value="select * FROM sys_log sl where 1 = 1 "+
			"and if(:menuflag 		is not null and :menuflag 		!= '', sl.menuflag 			like CONCAT('%',:menuflag,'%') 		, 1 = 1) "+
			"and if(:dictnmOpttype 	is not null and :dictnmOpttype 	!= '', sl.dictnm_opttype 	like CONCAT('%',:dictnmOpttype,'%') , 1 = 1) "+
			"and if(:staffName 		is not null and :staffName 		!= '', sl.name 				like CONCAT('%',:staffName,'%') 	, 1 = 1) "+
			"and if(:startTime 		is not null and :startTime 		!= '', sl.logtime 			BETWEEN :startTime and :endTime		, 1 = 1) "+
			"order by sl.logtime desc ",
			nativeQuery = true)
	List<SysLog> list(	@Param("menuflag") 		String menuflag,
						@Param("dictnmOpttype") String dictnmOpttype,
						@Param("staffName") 	String staffName,
						@Param("startTime") 	String startTime,
						@Param("endTime") 		String endTime);
}
 