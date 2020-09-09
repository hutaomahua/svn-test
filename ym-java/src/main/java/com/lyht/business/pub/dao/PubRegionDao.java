package com.lyht.business.pub.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lyht.business.pub.entity.PubRegionEntity;

@Repository
public interface PubRegionDao extends JpaRepository<PubRegionEntity, Integer> {
	
	@Query(value = "select city_code cityCode,parent_code parentCode,name from pub_region where level_type >2 and merger_name like '云南省,迪庆藏族自治州,维西傈僳族自治县%'",nativeQuery = true)
	public List<Map<String,Object>> getTypeThanThree();
	
	@Query(value = "SELECT CITY_CODE FROM pub_region WHERE parent_code = :parentCode order by city_code desc LIMIT 1 ",nativeQuery = true)
	public String getCountByCityCode(@Param("parentCode")String parentCode);
	
	@Query(value="SELECT * FROM pub_region WHERE merger_name LIKE CONCAT(:mergerName,',%')",nativeQuery = true)
	public List<PubRegionEntity> getChildList(@Param("mergerName")String mergerName);

	@Query(value="SELECT * FROM pub_region WHERE merger_name LIKE CONCAT('%',:mergerName)",nativeQuery = true)
	public List<PubRegionEntity> getRegion(@Param("mergerName")String mergerName);
	
	@Modifying
	@Query(value = "UPDATE pub_region set merger_name= REPLACE(merger_name,:oldmerger,:newmerger),merger_short_name =  REPLACE(merger_short_name,:oldshort,:newshort) where merger_name like CONCAT(:oldmerger,',%')",nativeQuery = true)
	public Integer updateSons(@Param("oldmerger") String oldmerger,@Param("newmerger") String newmerger,@Param("oldshort") String oldshort,@Param("newshort") String newshort);
	
	List<PubRegionEntity> findByLevelType(Integer levelType);
	
	@Query(value = "select * from pub_region where level_type =:levelType and merger_name like '云南省,迪庆藏族自治州,维西傈僳族自治县%'",nativeQuery = true		)
	List<PubRegionEntity> findByLevelType1(Integer levelType);

	@Query(value = "select max(level_type) from pub_region ",nativeQuery = true)
	public Integer getMinLevelType();

	PubRegionEntity findByCityCode(String cityCode);

	List<PubRegionEntity> findByCityCodeIn(Set<String> cityCode);

	@Query(value = "select * from pub_region  where 1=1 and merger_name not like '云南省,迪庆藏族自治州,德钦县%' and merger_name not like '云南省,迪庆藏族自治州,香格里拉市%' and " +
			"IF (:id is not null,id = :id , 1=1) and " +
			"IF (:parentCode is not null, parent_code = :parentCode, 1=1) and " +
			"IF (:cityCode is not null,city_code = :cityCode, 1=1) and " +
			"IF (:levelType is not null,level_type = :levelType, 1=1) and " +
			"IF (:mergerName is not null, REPLACE(merger_name,',','') like CONCAT('%',:mergerName,'%'), 1=1)  " +
			" order by id  desc",
			nativeQuery = true)
	List<PubRegionEntity> list(@Param("id") Integer id, @Param("cityCode") String cityCode, @Param("parentCode") String parentCode, @Param("mergerName") String mergerName, @Param("levelType") Integer levelType);
	
	@Query(value="SELECT * FROM pub_region"
			+ " WHERE IF (:name is not null, REPLACE(merger_name,',','') LIKE CONCAT('%',:name,'%'), 1=1)",nativeQuery = true)
	public List<PubRegionEntity> findAllByName(@Param("name")String name);
	
	@Query(value="SELECT * FROM pub_region"
			+ " WHERE IF(:mergerName is not null, merger_name LIKE CONCAT(:mergerName,'%'), 1=1)"
			+ " ORDER BY level_type",nativeQuery = true)
	public List<PubRegionEntity> findByMergerName(@Param("mergerName")String mergerName);
	
	
	/**
	 * 添加人：Yanxh
	 * 获取倒序的行政区序列
	 */
	@Query(value = "SELECT level FROM pub_region where 1 = 1 "
			+ "and if(:levelType is not null, level_type >= :levelType , 1 = 1)"
			+ "GROUP BY level ORDER BY level_type desc",nativeQuery = true)
	public List<String> queryLevelList(@Param("levelType")Integer levelType);
	/**
	 * 添加人：Yanxh
	 * 获取指定级别的行政区对象
	 */
	@Query(value = "SELECT * FROM pub_region WHERE level = :level ",nativeQuery = true)
	public List<PubRegionEntity> queryRegionByLevel(@Param("level")String level);
	
	/**
	 * 添加人：Yanxh
	 * 根据行政区编码获取父级行政区
	 */
	
	@Query(value = "SELECT p.* FROM pub_region c "
				+"LEFT JOIN pub_region p on c.parent_code = p.city_code "
				+"WHERE 1 = 1 "
				+"and if(:levelType is not null, p.level_type >= :levelType , 1 = 1)"
				+"and c.city_code = :region ",nativeQuery = true)
	public PubRegionEntity getParentRegionByCityCode(@Param("region")String region,@Param("levelType")Integer levelType);	
	
	/**
	 * 添加人：Yanxh
	 * 根据行政区编码获取父级行政区
	 */
	
	@Query(value = "SELECT level FROM pub_region where city_code = :cityCode",nativeQuery = true)
	public String getRegionLevelByCityCode(@Param("cityCode")String cityCode);

	@Query(value = "SELECT" +
			" (select" +
			" count(IF(ioi.is_satisfy = '2', 1, NULL))" +
			" from t_info_owner_impl ioi " +
			" where pr.city_code = ioi.region" +
			" ) as satisfyHourse," +
			" (select" +
			" count(IF(ioi.is_satisfy != '2' or ioi.is_satisfy is null, 1, NULL))" +
			" from t_info_owner_impl ioi " +
			" where pr.city_code = ioi.region" +
			" ) as NotSatisfyHourse," +
			" (select" +
			" count(ioi.id)" +
			" from t_info_owner_impl ioi " +
			" where pr.city_code = ioi.region" +
			" ) as SumHourse," +
			" (select" +
			" count(IF(ifi.is_satisfy = '2', 1, NULL))" +
			" from t_info_family_impl ifi " +
			" where pr.city_code = ifi.region" +
			" ) as satisfyNumber," +
			" (select" +
			" count(IF(ifi.is_satisfy != '2' or ifi.is_satisfy is null, 1, NULL))" +
			" from t_info_family_impl ifi " +
			" where pr.city_code = ifi.region" +
			" ) as notSatisfyNumber," +
			" (select" +
			" count(ifi.id)" +
			" from t_info_family_impl ifi " +
			" where pr.city_code = ifi.region" +
			" ) as sumNumber," +
			" pr.city_code as citycode  ,pr.parent_code as  parentcode , pr.name, pr.level,pr.merger_name as  mergerShortName" +
			" FROM" +
			" pub_region  pr " +
			" WHERE" +
			" level_type = 6",nativeQuery = true)
	List<Map<String,Object>> getRemovalCount();



	List<PubRegionEntity> findAllByParentCodeIn(List<String> parentCode);


	@Query(value = "select city_Code FROM pub_region WHERE name in (:strings)",nativeQuery = true)
	ArrayList<String> findRegionsByName(@Param("strings") ArrayList<String> strings);

	@Query(value = "select city_Code as cityCode,name FROM pub_region",nativeQuery = true)
	List<Map<String,Object>> findAll(int i);
	
	@Query(value = "SELECT * FROM pub_region pr WHERE pr.name in(:names)"
			+ " AND IF (:levelType IS NOT NULL, pr.level_type >= :levelType, 1=1)"
			,nativeQuery = true)
	List<PubRegionEntity> findAllByNames(@Param("names") List<String> names, @Param("levelType") Integer levelType);
	
	PubRegionEntity findByName(String name);

	@Query(value = "select * from pub_region where name = ?1 and level_type=6 limit 1",nativeQuery = true)
	PubRegionEntity findByNameAndLastLevel(String name);

	@Query(value = "SELECT city_code AS code, parent_code AS pCode, name, 1 AS level FROM pub_region WHERE city_code = 'E06A0AEF47'" +
			"UNION ALL SELECT city_code AS code, parent_code AS pCode, name, level_type AS level FROM pub_region WHERE level_type > 3 ",nativeQuery = true)
	List<Map> queryRegionList();

}
