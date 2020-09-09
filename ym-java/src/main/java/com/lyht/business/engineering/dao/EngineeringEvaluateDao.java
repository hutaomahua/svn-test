package com.lyht.business.engineering.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.engineering.entity.EngineeringEvaluate;
import com.lyht.business.engineering.vo.EngineeringEvaluateVO;

@Repository
public interface EngineeringEvaluateDao extends JpaRepository<EngineeringEvaluate, Integer> {

	@Query(value = "SELECT tt.id,tt.nm,tt.engineering_nm engineeringNm,tt.progress_evaluate progressEvaluate, tt.implement_status status,"
			+ "(CASE tt.implement_status WHEN 0 THEN '未启动' WHEN 1 THEN '正在施工' WHEN 2 THEN '已竣工' END)implementStatus,tt.settled_immigrants settledImmigrants,tt.unsettled_immigrants unsettledImmigrants, "
			+ "tt.Implement_info_date ImplementInfoDate,tt.spent_funds spentFunds,tt.remark,t1.nm createStaff,t1.staff_name createStaffName, "
			+ "t2.nm enginProgressNm,t2.name enginProgressName " + "FROM t_engineering_evaluate tt "
			+ "LEFT JOIN sys_staff t1 on tt.create_staff = t1.nm "
			+ "LEFT JOIN pub_dict_value t2 on tt.engin_progress_nm = t2.nm "
			+ "where tt.engineering_nm = :engineeringNm "
			+ " order by tt.Implement_Info_Date desc ", countQuery = "SELECT COUNT(1)" + "FROM t_engineering_evaluate tt "
					+ "LEFT JOIN sys_staff t1 on tt.create_staff = t1.nm "
					+ "LEFT JOIN pub_dict_value t2 on tt.engin_progress_nm = t2.nm "
					+ "where tt.engineering_nm = :engineeringNm)", nativeQuery = true)
	public Page<EngineeringEvaluateVO> page(@Param("engineeringNm") String engineeringNm, Pageable pageable);

	
	@Modifying
	@Query(value = "delete from t_engineering_evaluate where engineering_nm = :engineeringNm",countQuery = "",nativeQuery = true)
	public int deleteByEngineeringNm(@Param("engineeringNm")String engineeringNm);
	
}
