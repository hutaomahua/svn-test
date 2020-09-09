package com.lyht.business.letter.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import com.lyht.business.letter.entity.Letters;

/**
 * @author HuangTianhao
 * @date 2019年10月30日17:08:14
 */

public interface LettersDao extends JpaRepository<Letters, Integer> {
	/**
	 * 分类查询（已限制）和模糊查询（根据多项数据）
	 * 
	 * @param ptext
	 * @param category
	 * @param pageable
	 * @return
	 */
	@Query(value = "select * from t_abm_letters as tt where 1=1 and "
			+ "IF (:category is not null,exists (select name from pub_dict_value where name = :category),1=1) and "
			+ "IF (:category is not null,tt.category = :category,1=1) and "
			+ "IF (:ptext is not null,tt.code LIKE concat('%',:ptext,'%') "
			+ "or tt.water LIKE concat('%',:ptext,'%') or tt.type LIKE concat('%',:ptext,'%') "
			+ "or tt.name LIKE concat('%',:ptext,'%') or tt.phone LIKE concat('%',:ptext,'%') "
			+ "or tt.theme LIKE concat('%',:ptext,'%') or tt.visits_addr LIKE concat('%',:ptext,'%') "
			+ "or tt.undertake LIKE concat('%',:ptext,'%') or tt.undertake_unit LIKE concat('%',:ptext,'%') "
			+ "or tt.undertake_phone LIKE concat('%',:ptext,'%') or tt.through_name LIKE concat('%',:ptext,'%'), 1=1)"
			+ "order by  tt.name asc", nativeQuery = true) 
	 Page<Letters> pageByNameLike(@Param("ptext") String ptext,@Param("category")  String category, Pageable pageable);

//	@SuppressWarnings("rawtypes")
	@Query(value = "select l.id,l.nm,l.name,l.phone,l.visits_time visitsTime,l.dwdz,l.matter,l.visits_content visitsContent, " +
			" l.sex,l.job,l.undertake,r.city_code cityCode, " +
			" r.merger_name mergerName " + 
			" from t_abm_letters l left join pub_region r on l.region = r.city_code"+
			" where 1 = 1 and IF(:wordKey is not null && LENGTH(:wordKey)>0, l.name  LIKE CONCAT('%',:wordKey,'%') ,1=1) order by l.id desc  ",
			countQuery = " select count(1) from (select l.* from t_abm_letters l left join pub_region r on l.region = r.city_code"
					+ " where 1 = 1 and  IF(:wordKey is not null && LENGTH(:wordKey)>0, l.name  LIKE CONCAT('%',:wordKey,'%') ,1=1)) as a ",nativeQuery=true)
	 Page<Map> page(Pageable pageable,@Param("wordKey")String wordKey);
	
	
	/**
	 * 查询所有分类
	 * 
	 * @return
	 * 
	 * @Query(value = "select DISTINCT category from t_abm_letters", nativeQuery =
	 *              true) public List<String> getCategoty();
	 */
}
