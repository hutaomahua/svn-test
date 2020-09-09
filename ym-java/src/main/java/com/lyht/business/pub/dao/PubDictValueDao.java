package com.lyht.business.pub.dao;

import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.vo.PubDictValueTree;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 创建人： liuamang 脚本日期:2019年2月21日 22:26:14 说明: 字典
 */
@Repository
public interface PubDictValueDao extends JpaRepository<PubDictValue, Integer> {

	PubDictValue findByNm(String nm);

	PubDictValue findByCode(String code);

	List<PubDictValue> findByListnmSysDictCate(String dictCate);

	@Query(value = "select a.*,b.name as dictName from pub_dict_value a  left join pub_dict_name b on a.listnm_sys_dict_cate = b.code  where 1=1 and "
			+ " IF (:name is not null, name LIKE CONCAT('%',:name,'%'), 1=1) and "
			+ " IF (:code is not null, listnm_sys_dict_cate =:code , 1=1) "
			+ "order by id  desc", countQuery = "SELECT count(1) FROM (select a.* from pub_dict_value a  left join pub_dict_name b on a.listnm_sys_dict_cate = b.code  where 1=1 and "
					+ " IF (:name is not null, name LIKE CONCAT('%',:name,'%'), 1=1) and "
					+ " IF (:code is not null, listnm_sys_dict_cate = :code , 1=1)"
					+ " order by id  desc) c", nativeQuery = true)
	Page<Map> getList(@Param("name") String name, @Param("code") String code, Pageable pageable);

	@Query(value = "SELECT tt.* , pdn.name AS parentName,pdn.code as parentCode,pdvs.name as parentNames FROM pub_dict_value  tt LEFT JOIN pub_dict_name  pdn on tt.listnm_sys_dict_cate = pdn.code  LEFT JOIN pub_dict_value  pdvs on tt.parent_nm = pdvs.nm  WHERE 1=1 and "
			+ "IF (:code is not null, tt.code = :code, 1=1) and " + "IF (:id is not null, tt.id = :id, 1=1) and "
			+ "IF (:nm is not null, tt.nm = :nm, 1=1) and "
			+ "IF (:dictCate is not null && LENGTH(:dictCate)>0, tt.listnm_sys_dict_cate = :dictCate, 1=1) and "
			+ "IF (:name is not null, tt.name LIKE CONCAT('%',:name,'%'), 1=1)"
			+ "order by id  desc", countQuery = "select count(1) from (SELECT tt.* , pdn.name AS parentName,pdn.code as parentCode,pdvs.name as parentNames FROM pub_dict_value  tt LEFT JOIN pub_dict_name  pdn on tt.listnm_sys_dict_cate = pdn.code  LEFT JOIN pub_dict_value  pdvs on tt.parent_nm = pdvs.nm  WHERE 1=1 and "
					+ "IF (:code is not null, tt.code = :code, 1=1) and " + "IF (:id is not null, tt.id = :id, 1=1) and "
					+ "IF (:nm is not null, tt.nm = :nm, 1=1) and "
					+ "IF (:dictCate is not null && LENGTH(:dictCate)>0, tt.listnm_sys_dict_cate = :dictCate, 1=1) and "
					+ "IF (:name is not null, tt.name LIKE CONCAT('%',:name,'%'), 1=1)"
					+ "order by id  desc) as temp", nativeQuery = true)
	Page<Map> page(@Param("code") String code, @Param("id") Integer id, @Param("nm") String nm,
			@Param("dictCate") String dictCate, @Param("name") String name, Pageable pageable);

	@Query(value = "SELECT * FROM pub_dict_value WHERE listnm_sys_dict_cate = :code and "
			+ " IF (:parentCode is not null, parent_nm = (select nm from pub_dict_value where code = :parentCode ) , 1=1) ", nativeQuery = true)
	List<PubDictValue> getdictByCate(@Param("parentCode") String parentCode, @Param("code") String code);
	
	@Query(value = "SELECT * FROM pub_dict_value"
			+ " WHERE listnm_sys_dict_cate = :type"
			+ " ORDER BY level", nativeQuery = true)
	List<PubDictValue> findByType(@Param("type") String type);

	@Query(value = "SELECT * FROM pub_dict_value WHERE listnm_sys_dict_cate = :code and "
			+ "parent_nm = :parentCode ", nativeQuery = true)
	List<PubDictValue> getdictByCate01(@Param("parentCode") String parentCode, @Param("code") String code);

	@Query(value = "SELECT code,name ,parent_nm AS parentNm,pdn.code as parentCode  FROM pub_dict_value WHERE  parent_nm is not null and parent_nm != '' and listnm_sys_dict_cate = :dictCate ", nativeQuery = true)
	List<PubDictValueTree> getdictByCateSon(@Param("dictCate") String dictCate);

	@Query(value = "SELECT * FROM pub_dict_value AS tt  WHERE 1=1 and "
			+ "IF (:code is not null, tt.code = :code, 1=1) and " + "IF (:id is not null, tt.id = :id, 1=1) and "
			+ "IF (:nm is not null, tt.nm = :nm, 1=1) and "
			+ "IF (:dictCate is not null, tt.listnm_sys_dict_cate = :dictCate, 1=1) and "
			+ "IF (:parentNm is not null, tt.parent_nm = :parentNm, 1=1)  ORDER BY tt.sorted ASC, tt.id ASC ", nativeQuery = true)
	List<PubDictValue> list(@Param("code") String code, @Param("id") Integer id, @Param("nm") String nm,
			@Param("dictCate") String dictCate, @Param("parentNm") String parentNm);

	@Query(value = "SELECT pdv.code,pdv.name AS label,pdv.nm AS value,(SELECT COUNT(*) FROM pub_dict_value wc WHERE wc.parent_nm = pdv.nm) AS isLeaf FROM pub_dict_value pdv WHERE (pdv.parent_nm IS NULL or pdv.parent_nm='' ) and "
			+ "IF (:dictCate is not null, pdv.listnm_sys_dict_cate = :dictCate, 1=1)", nativeQuery = true)
	List<Map> getDictIsNotParent(@Param("dictCate") String dictCate);

	@Query(value = "SELECT pdv.code ,pdv.name AS label,pdv.nm AS value,(SELECT COUNT(*) FROM pub_dict_value wc WHERE wc.parent_nm = pdv.nm) AS isLeaf FROM pub_dict_value pdv WHERE pdv.parent_nm = :parentNm ", nativeQuery = true)
	List<Map> getDictByParent(@Param("parentNm") String parentNm);

	@Query(value = "SELECT  name FROM pub_dict_value  WHERE listnm_sys_dict_cate=:dictDay  ", nativeQuery = true)
	String getDictName(@Param("dictDay") String dictDay);

	@Query(value = "SELECT * FROM pub_dict_value" + " WHERE listnm_sys_dict_cate='dict_limits'"
			+ " AND name NOT IN('枢纽工程建设区', '水库淹没影响区', '枢纽区')", nativeQuery = true)
	List<PubDictValue> getScope();

	@Query(value = "SELECT tt.name FROM pub_dict_value tt" + " WHERE tt.nm=:nm" + " LIMIT 1", nativeQuery = true)
	String findNameByNm(@Param("nm") String nm);


	@Query(value = "SELECT nm AS code, name, parent_nm AS pCode, level FROM pub_dict_value WHERE listnm_sys_dict_cate = 'dict_limits'", nativeQuery = true)
	List<Map> queryScopeList();


}
