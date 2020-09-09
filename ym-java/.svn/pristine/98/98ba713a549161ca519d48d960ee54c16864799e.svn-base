package com.lyht.business.pub.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.pub.entity.PubYearSubject;

@Repository
public interface PubYearSubjectDao extends JpaRepository<PubYearSubject, Integer> {
	@Modifying
	@Query(value = "UPDATE t_abm_year_subject SET fcode = REPLACE(fcode,:oldfCode,:newfCode) WHERE fcode LIKE :oldCodes%", nativeQuery = true)
	void updateSysDeptSon(@Param("oldfCode") String oldfCode, @Param("newfCode") String newfCode,
			@Param("oldCodes") String oldCodes);

	// 查询最上级
	@Query(value = "SELECT  id as 'key',nm as nm,name AS title,id AS value ,serial_number  AS serialNumber   FROM t_abm_year_subject WHERE 1=1  and "
			+ " IF (?1 is not null, name LIKE CONCAT('%',?1,'%'), super_id IS NULL )  "
			+ "and  IF (?2 is not null, scode in (?2) ,1=1)"
			+ "order by fcode asc ", nativeQuery = true)
	List<Map> getTopBean(@Param("name") String name,@Param("scode")String scodes);

	  @Query(value = "select id from t_abm_year_subject where fcode  LIKE CONCAT(:fcodes,'%')  order by fcode asc",nativeQuery = true)
	    List<Integer> getSonIds(@Param("fcodes") String fcodes);

	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	@Query(value = "select  id as 'key',nm as nm,name AS title,id AS value,super_id as SuperId,serial_number  AS serialNumber,money,danwei  "
			+ "from t_abm_year_subject where super_id is not null "
			+ "order by fcode ASC ", nativeQuery = true)
	List<Map> findSon();

	@Query(value = "SELECT * FROM t_abm_year_subject WHERE super_id = :SuperId ORDER BY scode DESC  limit 1", nativeQuery = true)
	PubYearSubject getNewScode(@Param("SuperId") int SuperId);

	@Query(value = "SELECT * FROM t_abm_year_subject WHERE id = :id  ORDER BY scode DESC limit 1", nativeQuery = true)
	PubYearSubject getNewFcode(@Param("id") int id);

    @Query(value = "SELECT * FROM t_abm_year_subject WHERE (super_id IS NULL OR super_id = '') ORDER BY scode DESC limit 1", nativeQuery = true)
	PubYearSubject getNewFcodeTwo();
	
	@Query(value = "SELECT"
			+ " tt.*"
			+ " FROM t_abm_year_subject AS tt"
			+ " WHERE 1=1"
			+ " AND IF (:superId is not null, tt.super_id = :superId, (tt.super_id is null OR tt.super_id = ''))"
			+ " ORDER BY tt.scode ASC", nativeQuery = true)
	public List<PubYearSubject> getList(Integer superId);
	
	
	@Query(value = " SELECT * FROM t_abm_year_subject WHERE serial_number in('一','二','三','四','五','六','七') ORDER BY scode ASC " ,nativeQuery = true)
	List<PubYearSubject> getProjectSelect();

}
