package com.lyht.business.abm.wechat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.wechat.vo.AbmWechatFamilyVO;

import java.util.List;

public interface AbmWechatFamilyDao extends JpaRepository<AbmFamilyEntity, Integer> {

	/**
	 * 人口信息查询
	 * 
	 * @param ownerNm 户主nm
	 * @return
	 */
	@Query(value = "SELECT " + " tt.id AS id, tt.nm AS nm," + " tt.name AS name,"
			+ " tt.id_card AS idCard, tt.gender AS gender," + " tt.national AS national, tt.age AS age,"
			+ " tt.master_relationship AS masterRelationship," + " tt.lgtd AS lgtd, tt.lttd AS lttd,"
			+ " tt.altd AS altd, tt.in_map AS inMap,"
			+ " pdv1.name AS nationalValue, pdv2.name AS masterRelationshipValue" + " FROM t_info_family tt"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.national = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.master_relationship = pdv2.nm" + " WHERE 1=1"
			+ " AND IF (:ownerNm IS NOT NULL, tt.owner_nm = :ownerNm, 1=1)", nativeQuery = true)
	List<AbmWechatFamilyVO> list(@Param("ownerNm") String ownerNm);

}