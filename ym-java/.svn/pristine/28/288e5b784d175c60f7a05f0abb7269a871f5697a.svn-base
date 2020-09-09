package com.lyht.business.abm.removal.dao;

import com.lyht.business.abm.removal.entity.AbmMoveProcessEntity;
import com.lyht.business.abm.removal.vo.AbmMoveFamilyVO;
import com.lyht.business.abm.removal.vo.AbmMoveOwnerVO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AbmMoveDao extends JpaRepository<AbmMoveProcessEntity, Integer> {

	/**
	 * 删除对应户主的搬迁安置流程信息
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Modifying
	@Query(value = "DELETE FROM t_abm_move_process WHERE owner_nm = :ownerNm", nativeQuery = true)
	int deleteByOwnerNm(String ownerNm);

	/**
	 * 搬迁安置家庭成员信息分页查询
	 * 
	 * @param ownerNm
	 * @param pageable
	 * @return
	 */
	@Query(value = "SELECT " + " tt.nm AS nm, tt.name AS name, tt.master_relationship AS masterRelationship,"
			+ " tt.owner_nm AS ownerNm, tt.id_card AS idCard, tt.is_satisfy AS isSatisfy,"
			+ " tt.define AS define, tt.stage AS stage, tt.place_type AS placeType,"
			+ " tt.place_name AS placeName, tt.place_address AS placeAddress,"
			+ " tt.xiang AS county, tt.cun AS town,"
			+ " tt.zu AS village, tt.to_where AS toWhere, tt.remark AS remark,"
			+ " pdv1.name AS masterRelationshipValue, pdv2.name AS stageValue," + " pdv3.name AS placeTypeValue"
			+ " FROM t_info_family_impl tt" + " LEFT JOIN pub_dict_value pdv1 ON tt.master_relationship = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.place_type = pdv3.nm"
			+ " WHERE tt.owner_nm = :ownerNm", countQuery = "SELECT" + " COUNT(1)" + " FROM t_info_family_impl tt"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.master_relationship = pdv1.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.place_type = pdv3.nm"
					+ " WHERE tt.owner_nm = :ownerNm", nativeQuery = true)
	Page<AbmMoveFamilyVO> pageMoveFamily(@Param("ownerNm") String ownerNm, Pageable pageable);

	/**
	 * 搬迁安置家庭成员信息查询
	 * 
	 * @param ownerNm
	 * @return
	 */
	@Query(value = "SELECT " + " tt.nm AS nm, tt.name AS name, tt.master_relationship AS masterRelationship,"
			+ " tt.owner_nm AS ownerNm, tt.id_card AS idCard, tt.is_satisfy AS isSatisfy,"
			+ " tt.define AS define, tt.stage AS stage, tt.place_type AS placeType,"
			+ " tt.place_name AS placeName, tt.place_address AS placeAddress,"
			+ " tt.xiang AS county, tt.cun AS town,"
			+ " tt.zu AS village, tt.to_where AS toWhere, tt.remark AS remark," + " tt.gender AS gender, tt.age AS age,"
			+ " pdv1.name AS masterRelationshipValue, pdv2.name AS stageValue,"
			+ " pdv3.name AS placeTypeValue, pdv4.name AS householdsType," + " pdv5.name AS national"
			+ " FROM t_info_family_impl tt" + " LEFT JOIN pub_dict_value pdv1 ON tt.master_relationship = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.stage = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.place_type = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.households_type = pdv4.nm"
			+ " LEFT JOIN pub_dict_value pdv5 ON tt.national = pdv5.nm"
			+ " WHERE tt.owner_nm = :ownerNm", nativeQuery = true)
	List<AbmMoveFamilyVO> listMoveFamily(@Param("ownerNm") String ownerNm);

	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET " + " is_satisfy=:isSatisfy," + " xiang=:county," + " cun=:town,"
			+ " zu=:village," + " define=:define," + " place_type=:placeType," + " place_name=:placeName,"
			+ " to_where=:toWhere," + " place_address=:placeAddress" + " WHERE nm =:nm  ", nativeQuery = true)
	int updateOwner(@Param("nm") String nm, @Param("define") String define, @Param("isSatisfy") String isSatisfy,
			@Param("county") String county, @Param("town") String town, @Param("village") String village,
			@Param("placeType") String placeType, @Param("placeName") String placeName,
			@Param("toWhere") String toWhere, @Param("placeAddress") String placeAddress);

	@Modifying
	@Query(value = "UPDATE t_info_family_impl SET" + " define=:define," + " is_satisfy=:isSatisfy," + " xiang=:county,"
			+ " cun=:town," + " zu=:village," + " place_type=:placeType," + " place_name=:placeName,"
			+ " to_where=:toWhere," + " place_address=:placeAddress" + " WHERE nm =:nm  ", nativeQuery = true)
	int updateFamily(@Param("nm") String nm, @Param("define") String define, @Param("isSatisfy") String isSatisfy,
			@Param("county") String county, @Param("town") String town, @Param("village") String village,
			@Param("placeType") String placeType, @Param("placeName") String placeName,
			@Param("toWhere") String toWhere, @Param("placeAddress") String placeAddress);

	@Modifying
	@Query(value = "UPDATE t_info_owner_impl SET move_state=:moveState  WHERE nm =:nm  ", nativeQuery = true)
	int updateOwnerMoveState(@Param("nm") String nm, @Param("moveState") String moveState);

	/**
	 * 搬迁安置户主信息分页查询
	 * 
	 * @param ownerName
	 * @param idCard
	 * @param mergerName
	 * @param pageable
	 * @return
	 */
	@Query(value = "select * from (SELECT tt.nm AS nm, tt.name AS name, tt.id_card AS idCard,"
			+ " tt.is_satisfy AS isSatisfy, tt.place_type AS placeType,"
			+ " tt.place_name AS placeName, tt.place_address AS placeAddress,"
			+ " tt.to_where AS toWhere,"
			+ " tt.define AS define, tt.households_type AS householdsType,"
			+ " tt.national AS national, tt.scope AS scope," + " tt.move_state AS moveState,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.is_satisfy = '2' AND tifi.owner_nm = tt.nm) as iPopulation,"
			+ " pdv1.name AS placeTypeValue, pdv2.name AS householdsTypeValue,"
			+ " pdv3.name AS nationalValue, pdv4.name AS scopeValue,"
			+ " pr.city_code AS cityCode, pr.merger_name AS mergerName, "
			+ " if(ts.house_status=1 or ts.house_decoration_status=1 or ts.building_status=1 or ts.agricultural_facilities_status=1 or ts.trees_status=1 "
			+ " or ts.individual_status=1 or ts.relocation_allowance_status=1 or ts.other_status=1 or ts.difficult_status=1 or ts.infrastructure_status=1 or ts.homestead_status=1 "
			+ " or ts.levy_land_status=1 or ts.young_crops_status=1,1,0) as protocolState FROM t_info_owner_impl tt"
			+ " LEFT JOIN t_abm_total_state ts ON tt.nm = ts.owner_nm"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.place_type = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.households_type = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.national = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.scope = pdv4.nm  WHERE 1 = 1  AND tt.gs_state = '2'"// 已复核公式
			+ " AND IF(:ownerName IS NOT NULL AND :ownerName != '', tt.name LIKE CONCAT('%',:ownerName,'%'), 1 = 1)"
			+ " AND IF(:idCard IS NOT NULL AND :idCard != '', tt.id_card LIKE CONCAT('%',:idCard,'%'), 1 = 1)"
			+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)) as tt ORDER BY (case when tt.protocolState = 1 then 99 else 1 end)", countQuery = "SELECT"
					+ " COUNT(1)" + " FROM t_info_owner_impl tt"
					+ " LEFT JOIN t_abm_total_state ts ON tt.nm = ts.owner_nm"
					+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
					+ " LEFT JOIN pub_dict_value pdv1 ON tt.place_type = pdv1.nm"
					+ " LEFT JOIN pub_dict_value pdv2 ON tt.households_type = pdv2.nm"
					+ " LEFT JOIN pub_dict_value pdv3 ON tt.national = pdv3.nm"
					+ " LEFT JOIN pub_dict_value pdv4 ON tt.scope = pdv4.nm" + " WHERE 1 = 1" + " AND tt.gs_state = '2'"// 已复核公式
					+ " AND IF(:ownerName IS NOT NULL AND :ownerName != '', tt.name LIKE CONCAT('%',:ownerName,'%'), 1 = 1)"
					+ " AND IF(:idCard IS NOT NULL AND :idCard != '', tt.id_card LIKE CONCAT('%',:idCard,'%'), 1 = 1)"
					+ " AND IF(:mergerName IS NOT NULL AND :mergerName != '', pr.merger_name LIKE CONCAT(:mergerName,'%'), 1 = 1)", nativeQuery = true)
	Page<AbmMoveOwnerVO> pageMoveOwner(@Param("ownerName") String ownerName, @Param("idCard") String idCard,
			@Param("mergerName") String mergerName, Pageable pageable);

	@Query(value = "SELECT " + " tt.nm AS nm, tt.name AS name, tt.id_card AS idCard,"
			+ " tt.is_satisfy AS isSatisfy, tt.place_type AS placeType,"
			+ " tt.place_name AS placeName, tt.place_address AS placeAddress,"
			+ " tt.to_where AS toWhere,"
			+ " tt.define AS define, tt.households_type AS householdsType,"
			+ " tt.national AS national, tt.scope AS scope," + " tt.move_state AS moveState,"
			+ " (SELECT COUNT(1) FROM t_info_family_impl tifi WHERE tifi.is_satisfy = '2' AND tifi.owner_nm = tt.nm) as iPopulation,"
			+ " pdv1.name AS placeTypeValue, pdv2.name AS householdsTypeValue,"
			+ " pdv3.name AS nationalValue, pdv4.name AS scopeValue,"
			+ " pr.city_code AS cityCode, pr.merger_name AS mergerName" + " FROM t_info_owner_impl tt"
			+ " LEFT JOIN pub_region pr ON tt.region = pr.city_code"
			+ " LEFT JOIN pub_dict_value pdv1 ON tt.place_type = pdv1.nm"
			+ " LEFT JOIN pub_dict_value pdv2 ON tt.households_type = pdv2.nm"
			+ " LEFT JOIN pub_dict_value pdv3 ON tt.national = pdv3.nm"
			+ " LEFT JOIN pub_dict_value pdv4 ON tt.scope = pdv4.nm" + " WHERE tt.nm = :nm"
			+ " LIMIT 1", nativeQuery = true)
	AbmMoveOwnerVO findMoveOwnerByNm(@Param("nm") String nm);

}
