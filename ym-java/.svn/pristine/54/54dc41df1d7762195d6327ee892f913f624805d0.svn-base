package com.lyht.business.pub.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lyht.business.pub.entity.PubProjectEntity;

@Repository
public interface PubProjectDao extends JpaRepository<PubProjectEntity, Integer> {

	@Query(value = "select id,super_id as superId,name,nm from pub_project where fcode like concat('%',?1,'%')", nativeQuery = true)
	List<Map<String, Object>> treeSelect(String code);

	@Query(value = "select id,super_id as superId,name,nm from pub_project", nativeQuery = true)
	List<Map<String, Object>> getList();

	@Modifying
	@Query(value = "UPDATE pub_project SET fcode = REPLACE(fcode,:oldfCode,:newfCode) WHERE fcode LIKE :oldCodes%", nativeQuery = true)
	void updateSysDeptSon(@Param("oldfCode") String oldfCode, @Param("newfCode") String newfCode,
			@Param("oldCodes") String oldCodes);

	// 查询最上级
	@Query(value = "SELECT  id as 'key',nm as nm,name AS title,id AS value ,serial_number  AS serialNumber   FROM pub_project WHERE 1=1  and "
			+ " IF (?1 is not null, name LIKE CONCAT('%',?1,'%'), super_id IS NULL )  "
			+ "and  IF (?2 is not null, scode in (?2) ,1=1)" + "order by fcode asc ", nativeQuery = true)
	List<Map> getTopBean(@Param("name") String name, @Param("scode") String scodes);

	@Query(value = "select * from pub_project where name = :name and if(:fcode is not null &&:fcode != '',fcode like concat('%',:fcode,'%'),1=1) ", nativeQuery = true)
	PubProjectEntity queryByNameAndFCode(@Param("name") String name, @Param("fcode") String fcode);
	
	/**
	 * 查询上级节点最后的排序号对象
	 * 
	 * @return
	 */
	@Query(value = "select  id as 'key',nm as nm,name AS title,id AS value,super_id as SuperId,serial_number  AS serialNumber,money,danwei  "
			+ "from pub_project where super_id is not null " + "order by fcode ASC ", nativeQuery = true)
	List<Map> findSon();

	@Query(value = "SELECT * FROM pub_project WHERE super_id = :SuperId ORDER BY scode DESC  limit 1", nativeQuery = true)
	PubProjectEntity getNewScode(@Param("SuperId") int SuperId);

	@Query(value = "SELECT * FROM pub_project WHERE id = :id  ORDER BY scode DESC limit 1", nativeQuery = true)
	PubProjectEntity getNewFcode(@Param("id") int id);

	@Query(value = "SELECT * FROM pub_project WHERE (super_id IS NULL OR super_id = '') ORDER BY scode DESC limit 1", nativeQuery = true)
	PubProjectEntity getNewFcodeTwo();

	@Query(value = "SELECT" + " tt.*" + " FROM pub_project AS tt" + " WHERE 1=1"
			+ " AND IF (:superId is not null, tt.super_id = :superId, (tt.super_id is null OR tt.super_id = ''))"
			+ " ORDER BY tt.scode ASC", nativeQuery = true)
	public List<PubProjectEntity> getList(@Param("superId") Integer superId);

	@Query(value = " SELECT * FROM pub_project WHERE serial_number in('一','二','三','四','五','六','七') ORDER BY scode ASC ", nativeQuery = true)
	List<PubProjectEntity> getProjectSelect();

	/**
	 * 多条件查询
	 * 
	 * @param userSpecification
	 * @return
	 */
	List<PubProjectEntity> findAll(Specification<PubProjectEntity> specification);

}
