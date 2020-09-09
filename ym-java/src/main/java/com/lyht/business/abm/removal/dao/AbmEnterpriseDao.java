package com.lyht.business.abm.removal.dao;

import com.lyht.business.abm.removal.entity.AbmEnterpriseEntity;
import com.lyht.business.abm.removal.vo.AbmAggregateCardVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AbmEnterpriseDao extends JpaRepository<AbmEnterpriseEntity, Integer> {

	@Modifying
	@Query(value = "UPDATE t_info_enterprise_impl SET fh_state =:fhstate  WHERE nm =:ownernm  ", nativeQuery = true)
	void updateEnterprise(@Param("ownernm") String ownernm, @Param("fhstate") String fhstate);

	@Query(value = "select fh_state as fhstate,id,nm,(select name from pub_project b where b.id=a.project_nm)project_nm,name,"
			+ "(select name from pub_region b where b.city_code=a.region)as region,"
			+ "(select name from pub_dict_value b where a.scope=b.nm)as scope,"
			+ "legal_person,id_card,register_time,register_number,in_map,register_money,phone_number "
			+ "from t_info_enterprise_impl as a where 1=1 and "
			+ "  IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "
			+ "  IF (:scope is not null,a.scope=:scope, 1=1) and "
			+ "  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1) and "
			+ "  IF (:nm is not null,a.nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = " select count(1) from t_info_enterprise_impl a where 1=1 and "
					+ "  IF (:region is not null,a.region LIKE CONCAT('%',:region,'%'), 1=1) and "
					+ "  IF (:scope is not null,a.scope=:scope, 1=1) and "
					+ "  IF (:name is not null,a.name LIKE CONCAT('%',:name,'%'), 1=1) and "
					+ "  IF (:nm is not null,a.nm=:nm, 1=1)  " + " order BY id ASC ", nativeQuery = true)
	Page<Map> getList(Pageable pageable, @Param("region") String region, @Param("scope") String scope,
			@Param("name") String name, @Param("nm") String nm);

	// 设备
	@Query(value = "select a.*,(select name from pub_dict_value b where a.device_type=b.nm)deviceType,"
			+ "(select name from pub_project b where b.id=a.project_nm )prjectName "
			+ " from t_info_device_impl a where 1=1 and  " + "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = "select count(1) from t_info_device_impl a where 1=1 and  "
					+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  " + "order BY id ASC ", nativeQuery = true)
	Page<Map> getSheBei(Pageable pageable, @Param("nm") String nm);

	// 物资及存货
	@Query(value = "select a.*," + "(select name from pub_project b where b.id=a.project_nm )prjectName "

			+ " from t_info_supplies_impl a where 1=1 and  " + "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = "select count(1) from t_info_supplies_impl a where 1=1 and  "
					+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  " + "order BY id ASC ", nativeQuery = true)
	Page<Map> getWuzi(Pageable pageable, @Param("nm") String nm);

	// 房屋
	@Query(value = "select a.*," + "(select name from pub_project b where b.id=a.project_nm )prjectName "

			+ "from t_info_companies_house_impl a where 1=1 and  " + "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = "select count(1) from t_info_companies_house_impl a where 1=1 and  "
					+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  " + "order BY id ASC ", nativeQuery = true)
	Page<Map> getFw(Pageable pageable, @Param("nm") String nm);

	// 附属建筑物
	@Query(value = "select * from t_info_enter_building_impl a where 1=1 and  "
			+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = "select count(1) from t_info_enter_building_impl a where 1=1 and  "
					+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  " + "order BY id ASC ", nativeQuery = true)
	Page<Map> getFuShu(Pageable pageable, @Param("nm") String nm);

	// 企业构筑物及其他辅助设施调查表
	@Query(value = "select * from t_info_enter_structures_impl a where 1=1 and  "
			+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = "select count(1) from t_info_enter_structures_impl a where 1=1 and  "
					+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  " + "order BY id ASC ", nativeQuery = true)
	Page<Map> getGjw(Pageable pageable, @Param("nm") String nm);

	// 管道、线路
	@Query(value = "select * from t_info_enter_pipe_impl a where 1=1 and  "
			+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = "select count(1) from t_info_enter_pipe_impl a where 1=1 and  "
					+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  " + "order BY id ASC ", nativeQuery = true)
	Page<Map> getGd(Pageable pageable, @Param("nm") String nm);

	// 财务
	@Query(value = "select * from t_info_enter_business_impl a where 1=1 and  "
			+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  "
			+ "order BY id ASC ", countQuery = "select count(1) from t_info_enter_business_impl a where 1=1 and  "
					+ "  IF (:nm is not null,a.enter_nm=:nm, 1=1)  " + "order BY id ASC ", nativeQuery = true)
	Page<Map> getCaiWu(Pageable pageable, @Param("nm") String nm);

	/**
	 * 实物指标汇总--企事业单位
	 * 
	 * @param mergerName
	 * @return
	 */
	@Query(value = "SELECT" + " pdv.name AS project," + " '个' AS unit,"
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC' || tt.scope = 'E78D14E7BE' || tt.scope = 'D18482159A',1,0)),0) AS pivotTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94' || tt.scope = '24ACBF9107',1,0)),0) AS reservoirTotal, "
			+ " IFNULL(SUM(IF(tt.scope = 'D8D5AAD9DC',1,0)),0) AS flood, "
			+ " IFNULL(SUM(IF(tt.scope = 'E78D14E7BE',1,0)),0) AS influence, "
			+ " IFNULL(SUM(IF(tt.scope = 'D18482159A',1,0)),0) AS raise, "
			+ " IFNULL(SUM(IF(tt.scope = 'CE81C0FA94',1,0)),0) AS temporary, "
			+ " IFNULL(SUM(IF(tt.scope = '24ACBF9107',1,0)),0) AS permanent, "
			+ " IFNULL(SUM(IF(tt.scope = '348F5B68BA',1,0)),0) AS newTown " + " FROM pub_dict_value pdv"
			+ " LEFT JOIN t_info_enterprise_impl tt ON tt.enter_type = pdv.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pdv.listnm_sys_dict_cate = 'dict_enterprise_type'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " GROUP BY pdv.nm"
			+ " UNION ALL" + " SELECT" + " pdv.name AS project," + " '个' AS unit," + " 0 AS pivotTotal, "
			+ " 0 AS reservoirTotal, " + " 0 AS flood, " + " 0 AS influence, " + " 0 AS raise, " + " 0 AS temporary, "
			+ " 0 AS permanent, " + " 0 AS newTown " + " FROM pub_dict_value pdv" + " WHERE 1=1"
			+ " AND pdv.listnm_sys_dict_cate = 'dict_enterprise_type'" + " AND pdv.nm NOT IN(" + " SELECT" + " pdv.nm"
			+ " FROM pub_dict_value pdv" + " LEFT JOIN t_info_enterprise_impl tt ON tt.enter_type = pdv.nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code" + " WHERE 1=1"
			+ " AND pdv.listnm_sys_dict_cate = 'dict_enterprise_type'"
			+ " AND IF (:mergerName is not null, pr.merger_name like CONCAT(:mergerName,'%'), 1=1)" + " GROUP BY pdv.nm"
			+ " )" + " GROUP BY pdv.nm", nativeQuery = true)
	List<AbmAggregateCardVO> findEnterAggregate(@Param("mergerName") String mergerName);
}
