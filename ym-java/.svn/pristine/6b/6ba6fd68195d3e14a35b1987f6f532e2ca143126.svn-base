package com.lyht.business.abm.wechat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lyht.business.abm.removal.entity.AbmOwnerEntity;
import com.lyht.business.abm.wechat.vo.AbmWechatInfoVO;

import java.util.List;

public interface AbmWechatInfoDao extends JpaRepository<AbmOwnerEntity, Integer> {

	/**
	 * 人口信息查询
	 * 
	 * @param ownerNm 户主nm
	 * @return
	 */
	@Query(value = "SELECT" 
			+ " '房屋' AS type," 
			+ " pp.name AS project,"
			+ " pp.danwei AS unit," 
			+ " pdv1.name AS scope," 
			+ " CONCAT(0 + CAST(IFNULL(tt.area, 0) as char), '（', IFNULL(pp.danwei, '㎡'), '）') AS value," 
			+ " tt.area AS num"
			+ " FROM t_info_houses_impl tt" 
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" 
			+ " WHERE tt.owner_nm = :ownerNm" 
			+ " UNION ALL"
			+ " SELECT" 
			+ " '房屋装修' AS type," 
			+ " pp.name AS project," 
			+ " pp.danwei AS unit,"
			+ " pdv1.name AS scope," 
			+ " CONCAT(0 + CAST(IFNULL(tt.area, 0) as char), '（', IFNULL(pp.danwei, '㎡'), '）') AS value," 
			+ " tt.area AS num"
			+ " FROM t_info_houses_decoration_impl tt" 
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" 
			+ " WHERE tt.owner_nm = :ownerNm" 
			+ " UNION ALL"
			+ " SELECT" 
			+ " '零星树木' AS type," 
			+ " pp.name AS project," 
			+ " pp.danwei AS unit,"
			+ " pdv1.name AS scope," 
			+ " CONCAT(0 + CAST(IFNULL(tt.num, 0) as char), '（', IFNULL(pp.danwei, '株'), '）') AS value," 
			+ " tt.num AS num"
			+ " FROM t_info_trees_impl tt" 
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" 
			+ " WHERE tt.owner_nm = :ownerNm" 
			+ " UNION ALL"
			+ " SELECT" 
			+ " '附属建筑' AS type," 
			+ " pp.name AS project," 
			+ " pp.danwei AS unit,"
			+ " pdv1.name AS scope,"
			+ " CONCAT(0 + CAST(IF(tt.num IS NOT NULL, tt.num, IF(tt.area IS NOT NULL, tt.area, IF(tt.volume IS NOT NULL, tt.volume, IF(tt.longs IS NOT NULL, tt.longs, 0)))) as char), '（', IFNULL(pp.danwei, '无'), '）') AS value," 
			+ " IF(tt.num IS NOT NULL, tt.num, IF(tt.area IS NOT NULL, tt.area, IF(tt.volume IS NOT NULL, tt.volume, IF(tt.longs IS NOT NULL, tt.longs, 0)))) AS num"
			+ " FROM t_info_building_impl tt" 
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm" 
			+ " WHERE tt.owner_nm = :ownerNm" 
			+ " UNION ALL"
			+ " SELECT" 
			+ " '土地' AS type," 
			+ " pp.name AS project," 
			+ " pp.danwei AS unit,"
			+ " pdv1.name AS scope," 
			+ " CONCAT(0 + CAST(IFNULL(tt.area, 0) as char), '（', IFNULL(pp.danwei, '亩'), '）') AS value," 
			+ " tt.area AS num"
			+ " FROM t_info_land_impl tt" 
			+ " LEFT JOIN pub_project pp ON tt.project_nm = pp.id"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.scope = pdv1.nm"
			+ " WHERE tt.owner_nm = :ownerNm", nativeQuery = true)
	List<AbmWechatInfoVO> list(@Param("ownerNm") String ownerNm);

}