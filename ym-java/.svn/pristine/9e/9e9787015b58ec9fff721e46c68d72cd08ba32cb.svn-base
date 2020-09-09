package com.lyht.business.abm.wechat.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.wechat.vo.AbmWechatOwnerVO;

public interface AbmWechatOwnerDao extends JpaRepository<AbmOwnerEntity, Integer> {

	/**
	 * 户主列表查询
	 * 
	 * @param mergerName 行政区域全称
	 * @param scope      征地范围编码
	 * @param name       姓名
	 * @param idCard     身份证号
	 * @return
	 */
	@Query(value = "SELECT " + " tt.id AS id, tt.nm AS nm," + " tt.scope AS scope, tt.name AS name,"
			+ " tt.id_card AS idCard, tt.lttd AS lttd," + " tt.altd AS altd, tt.lgtd AS lgtd," + " tt.in_map AS inMap,"
			+ " tpr.city_code AS cityCode, tpr.merger_name AS mergerName," + " pdv1.name AS scopeValue"
			+ " FROM t_info_owner_impl tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName IS NOT NULL AND :mergerName != '', tpr.merger_name LIKE CONCAT('%',:mergerName,'%'), 1 = 1)"
			+ " AND IF (:scope IS NOT NULL AND :scope != '', tt.scope = :scope, 1 = 1)"
			+ " AND IF (:value IS NOT NULL AND :value != '', ("
			+ " tt.name like CONCAT('%',:value,'%') OR tt.id_card like CONCAT('%',:value,'%')"
			+ " ), 1 = 1)", nativeQuery = true)
	List<AbmWechatOwnerVO> list(@Param("mergerName") String mergerName, @Param("scope") String scope,
			@Param("value") String value);

	/**
	 * 通过nm查询权属人
	 * 
	 * @param nm
	 * @return
	 */
	@Query(value = "SELECT " + " tt.id AS id, tt.nm AS nm," + " tt.scope AS scope, tt.name AS name,"
			+ " tt.id_card AS idCard, tt.lttd AS lttd," + " tt.altd AS altd, tt.lgtd AS lgtd," + " tt.in_map AS inMap,"
			+ " tpr.city_code AS cityCode, tpr.merger_name AS mergerName," + " pdv1.name AS scopeValue"
			+ " FROM t_info_owner_impl tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" + " WHERE tt.nm = :nm"
			+ " LIMIT 1", nativeQuery = true)
	AbmWechatOwnerVO getOwner(@Param("nm") String nm);

	@Query(value = "SELECT " + " tt.id AS id, tt.nm AS nm," + " tt.scope AS scope, tt.name AS name,"
			+ " tt.id_card AS idCard, tt.lttd AS lttd," + " tt.altd AS altd, tt.lgtd AS lgtd," + " tt.in_map AS inMap,"
			+ " tpr.city_code AS cityCode, tpr.merger_name AS mergerName," + " pdv1.name AS scopeValue"
			+ " FROM t_info_owner_impl tt" + " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" + " WHERE 1=1"
			+ " AND IF (:mergerName IS NOT NULL AND :mergerName != '', tpr.merger_name LIKE CONCAT('%',:mergerName,'%'), 1 = 1)"
			+ " AND IF (:scope IS NOT NULL AND :scope != '', tt.scope = :scope, 1 = 1)"
			+ " AND IF (:value IS NOT NULL AND :value != '', ("
			+ " tt.name like CONCAT('%',:value,'%') OR tt.id_card like CONCAT('%',:value,'%')"
			+ " ), 1 = 1)", countQuery = "SELECT" + " COUNT(1)" + " FROM t_info_owner_impl tt"
					+ " LEFT JOIN pub_region tpr ON tt.region = tpr.city_code"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" + " WHERE 1=1"
					+ " AND IF (:mergerName IS NOT NULL AND :mergerName != '', tpr.merger_name LIKE CONCAT('%',:mergerName,'%'), 1 = 1)"
					+ " AND IF (:scope IS NOT NULL AND :scope != '', tt.scope = :scope, 1 = 1)"
					+ " AND IF (:value IS NOT NULL AND :value != '', ("
					+ " tt.name like CONCAT('%',:value,'%') OR tt.id_card like CONCAT('%',:value,'%')"
					+ " ), 1 = 1)", nativeQuery = true)
	Page<AbmWechatOwnerVO> page(@Param("mergerName") String mergerName, @Param("scope") String scope,
			@Param("value") String value, Pageable pageable);

}