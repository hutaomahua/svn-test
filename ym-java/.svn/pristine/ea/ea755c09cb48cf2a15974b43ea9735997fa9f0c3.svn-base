package com.lyht.business.info.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lyht.business.info.entity.InfoStatistics;
import com.lyht.business.info.vo.InfoStatisticsDetailVO;

@Repository
public interface InfoStatisticsDao extends JpaRepository<InfoStatistics, Integer> {

	@Modifying
	@Query(value = "DELETE from t_info_statistics", nativeQuery = true)
	public void deleteStatistics();

	@Query(value = "select A,B,C,D,E,F,G,H,I,J,K from (select '涉及县级行政区' as 'A','个' as 'B', (select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%')) dig "
			+ "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'C', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '临时占地' or t2.name = '永久占地') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'D', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '临时占地' ) dig "
			+ "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'E', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '永久占地'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'F', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '水库影响区' or t2.name = '水库淹没区' or t2.name = '垫高临时用地区') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'G', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库影响区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'H', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库淹没区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'I', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '垫高临时用地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'J', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '集镇新址占地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 3) ls where ls.dig >0) 'K') as ss ", nativeQuery = true)
	public InfoStatisticsDetailVO line01();// 查询涉及行政县

	@Query(value = "select  A,B,C,D,E,F,G,H,I,J,K from (select '涉及乡级行政区' as 'A','个' as 'B', (select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%')) dig "
			+ "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'C', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '临时占地' or t2.name = '永久占地') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'D', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '临时占地'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'E', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '永久占地'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'F', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '水库影响区' or t2.name = '水库淹没区' or t2.name = '垫高临时用地区') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'G', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库影响区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'H', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库淹没区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'I', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '垫高临时用地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'J', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '集镇新址占地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 4) ls where ls.dig >0) 'K') as ss", nativeQuery = true)
	public InfoStatisticsDetailVO line02();// 涉及行政乡

	@Query(value = " select A,B,C,D,E,F,G,H,I,J,K from (select '涉及行政村' as 'A','个' as 'B', (select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%')) dig "
			+ "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'C', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '临时占地' or t2.name = '永久占地') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'D', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '临时占地'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'E', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '永久占地'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'F', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '水库影响区' or t2.name = '水库淹没区' or t2.name = '垫高临时用地区') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'G', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库影响区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'H', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库淹没区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'I', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '垫高临时用地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'J', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '集镇新址占地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 5) ls where ls.dig >0) 'K') as ss", nativeQuery = true)
	public InfoStatisticsDetailVO line03();// 涉及行政村

	@Query(value = "select A,B,C,D,E,F,G,H,I,J,K from (select '涉及组' as 'A','个' as 'B', (select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%')) dig "
			+ "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'C', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '临时占地' or t2.name = '永久占地') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'D', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '临时占地'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'E', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '永久占地'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'F', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and (t2.name = '水库影响区' or t2.name = '水库淹没区' or t2.name = '垫高临时用地区') "
			+ ") dig " + "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'G', "
			+ "(select count(1) from ( " + "select t0.merger_name,(select count(1) from ( "
			+ "select region,scope from t_info_family  " + "UNION ALL " + "select region,scope from t_info_land "
			+ ") tt " + "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库影响区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'H', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '水库淹没区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'I', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '垫高临时用地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'J', " + "(select count(1) from ( "
			+ "select t0.merger_name,(select count(1) from ( " + "select region,scope from t_info_family  "
			+ "UNION ALL " + "select region,scope from t_info_land " + ") tt "
			+ "left join pub_region t1 on tt.region = t1.city_code "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "where t1.merger_name like CONCAT(t0.merger_name,'%') and t2.name = '集镇新址占地区'  " + ") dig "
			+ "from pub_region t0 where t0.level_type = 6) ls where ls.dig >0) 'K') as ss", nativeQuery = true)
	public InfoStatisticsDetailVO line04();// 涉及行政组

	@Query(value = "select '房屋面积' as 'A','m²' as 'B', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt) 'C', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  "
			+ "where tt.scope = '临时占地' or tt.scope = '永久占地' " + ") 'D', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '临时占地') 'E', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '永久占地') 'F', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  "
			+ "where tt.scope = '水库淹没区' OR tt.scope = '水库影响区' OR tt.scope = '垫高临时用地区') 'G', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '水库淹没区') 'H', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '水库影响区') 'I', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '垫高临时用地区') 'J', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t2.name scope from t_info_houses tt "
			+ "left join pub_dict_value t2 on tt.scope = t2.nm " + "UNION ALL "
			+ "SELECT tt.region,tt.area area,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  "
			+ "where tt.scope = '集镇新址占地区') 'K'", nativeQuery = true)
	public InfoStatisticsDetailVO houseArea();// 房屋面积总

	@Query(value = "select A,B,C,D,E,F,G,H,I,J,K from (select t0.project as 'A','m²' as 'B', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.project = t0.project) 'C', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  "
			+ "where (tt.scope = '临时占地' or tt.scope = '永久占地') " + "and tt.project = t0.project) 'D', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '临时占地' "
			+ "and tt.project = t0.project) 'E', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '永久占地' "
			+ "and tt.project = t0.project) 'F', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  "
			+ "where (tt.scope = '水库淹没区' OR tt.scope = '水库影响区' OR tt.scope = '垫高临时用地区') "
			+ "and tt.project = t0.project) 'G', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '水库淹没区' "
			+ "and tt.project = t0.project) 'H', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '水库影响区' "
			+ "and tt.project = t0.project) 'I', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '垫高临时用地区' "
			+ "and tt.project = t0.project) 'J', "
			+ "(select sum(tt.area) from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) AS tt  " + "where tt.scope = '集镇新址占地区' "
			+ "and tt.project = t0.project) 'K' "
			+ " from (select tt.region,tt.area area,t1.name project,t2.name scope from t_info_houses tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "left join pub_dict_value t2 on tt.scope = t2.nm "
			+ "UNION ALL "
			+ "SELECT tt.region,tt.area area,tt.all_type AS project,t1.name scope FROM t_info_companies_house tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm) t0 " + "group by project)as ss", nativeQuery = true)
	public List<InfoStatisticsDetailVO> houseAreaProject();// 房屋面积 项目

	@Query(value = "select '人口与户数' as 'A'", nativeQuery = true)
	public InfoStatisticsDetailVO population01();// 人口与总户数

	@Query(value = " select A,B,C,D,E,F,G,H,I,J,K from ("
			+ "select '移民户' as 'A','户' as 'B',(select count(1) from t_info_owner) as 'C', "
			+ "(select count(1) from t_info_owner tt " + "left join pub_dict_value t1 on tt.scope = t1.nm "
			+ "where t1.NAME = '临时占地' or t1.name = '永久占地') as 'D', " + "(select count(1) from t_info_owner tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm " + "where t1.NAME = '临时占地') as 'E', "
			+ "(select count(1) from t_info_owner tt " + "left join pub_dict_value t1 on tt.scope = t1.nm "
			+ "where t1.name = '永久占地') as 'F', " + "(select count(1) from t_info_owner tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm "
			+ "where t1.NAME = '水库淹没区' or t1.name = '水库影响区' or t1.name = '垫高临时用地区') as 'G', "
			+ "(select count(1) from t_info_owner tt " + "left join pub_dict_value t1 on tt.scope = t1.nm "
			+ "where t1.NAME = '水库淹没区' ) as 'H', " + "(select count(1) from t_info_owner tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm " + "where t1.name = '水库影响区' ) as 'I', "
			+ "(select count(1) from t_info_owner tt " + "left join pub_dict_value t1 on tt.scope = t1.nm "
			+ "where t1.name = '垫高临时用地区') as 'J', " + "(select count(1) from t_info_owner tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm "
			+ "where t1.NAME = '集镇新址占地区') as 'K') as ss", nativeQuery = true)
	public InfoStatisticsDetailVO population02();// 移民户

	@Query(value = "select A,B,C,D,E,F,G,H,I,J,K from (select '移民人口' as 'A','人' AS 'B',(SELECT COUNT(1) FROM t_info_family tt ) AS 'C',(SELECT COUNT(1) FROM t_info_family tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm  "
			+ "WHERE t1.NAME = '临时占地' or t1.name = '永久占地') AS 'D', " + "(SELECT COUNT(1) FROM t_info_family tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm  " + "WHERE t1.NAME = '临时占地') AS 'E', "
			+ "(SELECT COUNT(1) FROM t_info_family tt  " + "left join pub_dict_value t1 on tt.scope = t1.nm  "
			+ "WHERE t1.name = '永久占地') AS 'F', " + "(SELECT COUNT(1) FROM t_info_family tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm  "
			+ "WHERE  t1.NAME = '水库淹没区' or t1.name = '水库影响区' or t1.name = '垫高临时用地区') 'G', "
			+ "(SELECT COUNT(1) FROM t_info_family tt  " + "left join pub_dict_value t1 on tt.scope = t1.nm  "
			+ "WHERE  t1.NAME = '水库淹没区' ) 'H', " + "(SELECT COUNT(1) FROM t_info_family tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm  " + "WHERE  t1.NAME = '水库影响区')'I', "
			+ "(SELECT COUNT(1) FROM t_info_family tt  " + "left join pub_dict_value t1 on tt.scope = t1.nm  "
			+ "WHERE t1.name = '垫高临时用地区') 'J', " + "(SELECT COUNT(1) FROM t_info_family tt  "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm  "
			+ "WHERE t1.name = '集镇新址占地区') 'K') as ss", nativeQuery = true)
	public InfoStatisticsDetailVO population03();// 移民人口

	@Query(value = "select A,B,C,D,E,F,G,H,I,J,K from (select '农业人口' as 'A','人' as 'B',(select sum(ap) from t_info_owner ) as 'C', "
			+ "(select sum(ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.name = t1.NAME = '临时占地' or t1.name = '永久占地') as 'D', " + "(select sum(ap) from t_info_owner tt "
			+ "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  " + "where t1.name = t1.NAME = '临时占地' ) as 'E', "
			+ "(select sum(ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.name = t1.NAME = '永久占地' ) as 'F', " + "(select sum(ap) from t_info_owner tt "
			+ "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.NAME = '水库淹没区' or t1.name = '水库影响区' or t1.name = '垫高临时用地区') as 'G', "
			+ "(select sum(ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.NAME = '水库淹没区' ) as 'H', " + "(select sum(ap) from t_info_owner tt "
			+ "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  " + "where t1.name = '水库影响区') as 'I', "
			+ "(select sum(ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.name = '垫高临时用地区') as 'J', " + "(select sum(ap) from t_info_owner tt "
			+ "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.NAME = '集镇新址占地区') as 'K') as ss", nativeQuery = true)
	public InfoStatisticsDetailVO population04();// 农业人口

	@Query(value = "select A,B,C,D,E,F,G,H,I,J,K from (select '非农业人口' as 'A','人' as 'B',(select sum(non_ap) from t_info_owner ) as 'C', "
			+ "(select sum(non_ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.name = t1.NAME = '临时占地' or t1.name = '永久占地') as 'D', "
			+ "(select sum(non_ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.name = t1.NAME = '临时占地' ) as 'E', " + "(select sum(non_ap) from t_info_owner tt "
			+ "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  " + "where t1.name = t1.NAME = '永久占地' ) as 'F', "
			+ "(select sum(non_ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.NAME = '水库淹没区' or t1.name = '水库影响区' or t1.name = '垫高临时用地区') as 'G', "
			+ "(select sum(non_ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.NAME = '水库淹没区' ) as 'H', " + "(select sum(non_ap) from t_info_owner tt "
			+ "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  " + "where t1.name = '水库影响区') as 'I', "
			+ "(select sum(non_ap) from t_info_owner tt " + "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  "
			+ "where t1.name = '垫高临时用地区') as 'J', " + "(select sum(non_ap) from t_info_owner tt "
			+ "LEFT JOIN pub_dict_value t1 on tt.scope = t1.nm  " + "where t1.NAME = '集镇新址占地区') as 'K') as ss "
			+ "", nativeQuery = true)
	public InfoStatisticsDetailVO population05();// 非农业人口

	@Query(value = "select '附属建筑物' as 'A'", nativeQuery = true)
	public InfoStatisticsDetailVO building00();// 附属建筑物

	@Query(value = "SELECT qq.project 'A',qq.unit 'B',(select sum(total) from(select tt.scope scope,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as total,t1.name project  "
			+ "from t_info_building tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.mianji total,tt.name project from t_info_enter_structures tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '临时占地' and dd.project = qq.project) 'E' "
			+ ",(select sum(total) from(select tt.scope scope,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as total,t1.name project  "
			+ "from t_info_building tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.mianji total,tt.name project from t_info_enter_structures tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '永久占地' and dd.project = qq.project) 'F' "
			+ ",(select sum(total) from(select tt.scope scope,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as total,t1.name project  "
			+ "from t_info_building tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.mianji total,tt.name project from t_info_enter_structures tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '水库淹没区' and dd.project = qq.project) 'H' "
			+ ",(select sum(total) from(select tt.scope scope,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as total,t1.name project  "
			+ "from t_info_building tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.mianji total,tt.name project from t_info_enter_structures tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '水库影响区' and dd.project = qq.project) 'I' "
			+ ",(select sum(total) from(select tt.scope scope,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as total,t1.name project  "
			+ "from t_info_building tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.mianji total,tt.name project from t_info_enter_structures tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '垫高临时用地区' and dd.project = qq.project) 'J' "
			+ ",(select sum(total) from(select tt.scope scope,(case tt.unit when '㎡' then tt.area when 'm³' then tt.volume when'm' then tt.longs else tt.num end) as total,t1.name project  "
			+ "from t_info_building tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.mianji total,tt.name project from t_info_enter_structures tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '集镇新址占地区' and dd.project = qq.project) 'K' "
			+ " FROM (select t1.name project,tt.unit unit " + "from t_info_building tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "GROUP BY project " + "UNION ALL "
			+ "select tt.name project,'m²' unit from t_info_enter_structures tt "
			+ "GROUP BY project) as qq GROUP BY qq.project " + " " + "", nativeQuery = true)
	public List<InfoStatisticsDetailVO> building01();// 附属建筑物 各项

	@Query(value = "SELECT '零星树木' as 'A',qq.unit 'B',(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm " + "where d1.name = '临时占地' ) 'E' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm " + "where d1.name = '永久占地' ) 'F' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm " + "where d1.name = '水库淹没区' ) 'H' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm " + "where d1.name = '水库影响区' ) 'I' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm " + "where d1.name = '垫高临时用地区' ) 'J' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm " + "where d1.name = '集镇新址占地区' ) 'K' "
			+ "FROM (select tt.unit unit " + "from t_info_trees tt LIMIT 1) as qq ", nativeQuery = true)
	public InfoStatisticsDetailVO trees00();// 零星树木

	@Query(value = "SELECT qq.project 'A',qq.unit 'B',(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '临时占地' and dd.project = qq.project) 'E' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '永久占地' and dd.project = qq.project) 'F' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '水库淹没区' and dd.project = qq.project) 'H' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '水库影响区' and dd.project = qq.project) 'I' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '垫高临时用地区' and dd.project = qq.project) 'J' "
			+ ",(select sum(total) from(select tt.scope scope,tt.num as total,t1.name project  "
			+ "from t_info_trees tt " + "left join pub_project t1 on tt.project_nm = t1.id " + "UNION ALL "
			+ "select tt.scope scope,tt.num total,tt.all_type project from t_info_enter_trees tt) as dd "
			+ "left JOIN pub_dict_value d1 on dd.scope = d1.nm "
			+ "where d1.name = '集镇新址占地区' and dd.project = qq.project) 'K' "
			+ " FROM (select t1.name project,tt.unit unit " + "from t_info_trees tt "
			+ "left join pub_project t1 on tt.project_nm = t1.id " + "GROUP BY project " + "UNION ALL "
			+ "select tt.all_type project,tt.unit unit from t_info_enter_trees tt "
			+ "GROUP BY project) as qq GROUP BY qq.project " + " " + "", nativeQuery = true)
	public List<InfoStatisticsDetailVO> trees01();// 零星树木 各项

	@Query(value = "select t6.name as 'A',t0.unit 'B',(select sum(area) from (select tt.type_one project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_one AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '临时占地') as 'E',  " + 
			"(select sum(area) from (select tt.type_one project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_one AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '永久占地') as 'F',  " + 
			"(select sum(area) from (select tt.type_one project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_one AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '水库淹没区') as 'H',  " + 
			"(select sum(area) from (select tt.type_one project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_one AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '水库影响区') as 'I',  " + 
			"(select sum(area) from (select tt.type_one project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_one AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '垫高临时用地区') as 'J',  " + 
			"(select sum(area) from (select tt.type_one project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_one AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '集镇新址占地区') as 'K'  " + 
			"from (select tt.type_one project,tt.unit unit from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm GROUP BY tt.type_one UNION ALL  " + 
			"SELECT tt.type_one AS project,tt.unit unit FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm GROUP BY tt.type_one) as t0 " + 
			"left JOIN pub_dict_value t6 on t0.project = t6.nm ", nativeQuery = true)
	public List<InfoStatisticsDetailVO> land01();// 土地 一级

	@Query(value = " select t6.name as 'A',t0.unit 'B',(select sum(area) from (select tt.type_two project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_two AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '临时占地') as 'E',  " + 
			"(select sum(area) from (select tt.type_two project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_two AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '永久占地') as 'F',  " + 
			"(select sum(area) from (select tt.type_two project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_two AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '水库淹没区') as 'H',  " + 
			"(select sum(area) from (select tt.type_two project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_two AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '水库影响区') as 'I',  " + 
			"(select sum(area) from (select tt.type_two project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_two AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '垫高临时用地区') as 'J',  " + 
			"(select sum(area) from (select tt.type_two project,t2.name scope,tt.area area from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm UNION ALL  " + 
			"SELECT tt.type_two AS project,t1.name scope,tt.area area FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm) as ss where ss.project =t0.project and ss.scope = '集镇新址占地区') as 'K'  " + 
			"from (select tt.type_two project,tt.unit unit from t_info_land tt  " + 
			"left join pub_dict_value t2 on tt.scope = t2.nm GROUP BY tt.type_two UNION ALL  " + 
			"SELECT tt.type_two AS project,tt.unit unit FROM t_info_enter_land tt  " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm GROUP BY tt.type_two) as t0 " + 
			"left JOIN pub_dict_value t6 on t0.project = t6.nm ", nativeQuery = true)
	public List<InfoStatisticsDetailVO> land02();// 土地 二级

	@Query(value = "select '企业和个体工商户'  as 'A'", nativeQuery = true)
	public InfoStatisticsDetailVO enter();// 企业和个体工商户

	@Query(value = "select '企业' as 'A','家' as 'B', (select count(1) from t_info_enterprise tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '临时占地') as 'E',  "
			+ "(select count(1) from t_info_enterprise tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '永久占地') as 'F',  "
			+ "(select count(1) from t_info_enterprise tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库淹没区') as 'H',  "
			+ "(select count(1) from t_info_enterprise tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库影响区') as 'I',  "
			+ "(select count(1) from t_info_enterprise tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '垫高临时用地区') as 'J',  "
			+ "(select count(1) from t_info_enterprise tt "
			+ "left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '集镇新址占地区') as 'K' "
			+ "", nativeQuery = true)
	public InfoStatisticsDetailVO enterprise();// 企业

	@Query(value = "select '个体工商户' as 'A','家' as 'B', (select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '临时占地') + " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '临时占地') as 'E', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '永久占地') + " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '永久占地') as 'F', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库淹没区') + " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库淹没区')  as 'H', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库影响区') + " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库影响区')  as 'I', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '垫高临时用地区') + " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '垫高临时用地区')  as 'J', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '集镇新址占地区') + " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '集镇新址占地区')  as 'K'", nativeQuery = true)
	public InfoStatisticsDetailVO individual();// 个体工商户总数

	@Query(value = "select '生产型个体工商户' as 'A','户' as 'B', (select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '临时占地')  as 'E', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '永久占地')as 'F', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库淹没区')  as 'H', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库影响区')   as 'I', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '垫高临时用地区') as 'J', " + 
			"(select count(1) from t_info_individual tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '集镇新址占地区')  as 'K'", nativeQuery = true)
	public InfoStatisticsDetailVO individual01();// 生产型个体工商户

	@Query(value = "select '经营型个体工商户' as 'A','户' as 'B', (select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '临时占地')  as 'E', " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '永久占地')as 'F', " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库淹没区')  as 'H', " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '水库影响区')   as 'I', " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '垫高临时用地区') as 'J', " + 
			"(select count(1) from t_info_individual_business tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm where t1.name = '集镇新址占地区')  as 'K'", nativeQuery = true)
	public InfoStatisticsDetailVO individual02();// 经营型个体工商户
	
	@Query(value = "select t0.project 'A','km' as 'B', " + 
			"(select sum(length) from (select t1.name scope,tt.reconstruction_size length,t2.name project from t_info_transport_engineer tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm " + 
			"left join pub_dict_value t2 on tt.two_type = t2.nm) as ss " + 
			"where ss.project = t0.project and ss.scope = '临时占地') as 'E', " + 
			"(select sum(length) from (select t1.name scope,tt.reconstruction_size length,t2.name project from t_info_transport_engineer tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm " + 
			"left join pub_dict_value t2 on tt.two_type = t2.nm) as ss " + 
			"where ss.project = t0.project and ss.scope = '永久占地') as 'F', " + 
			"(select sum(length) from (select t1.name scope,tt.reconstruction_size length,t2.name project from t_info_transport_engineer tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm " + 
			"left join pub_dict_value t2 on tt.two_type = t2.nm) as ss " + 
			"where ss.project = t0.project and ss.scope = '水库淹没区') as 'H', " + 
			"(select sum(length) from (select t1.name scope,tt.reconstruction_size length,t2.name project from t_info_transport_engineer tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm " + 
			"left join pub_dict_value t2 on tt.two_type = t2.nm) as ss " + 
			"where ss.project = t0.project and ss.scope = '水库影响区') as 'I', " + 
			"(select sum(length) from (select t1.name scope,tt.reconstruction_size length,t2.name project from t_info_transport_engineer tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm " + 
			"left join pub_dict_value t2 on tt.two_type = t2.nm) as ss " + 
			"where ss.project = t0.project and ss.scope = '垫高临时用地区') as 'J', " + 
			"(select sum(length) from (select t1.name scope,tt.reconstruction_size length,t2.name project from t_info_transport_engineer tt " + 
			"left join pub_dict_value t1 on tt.scope = t1.nm " + 
			"left join pub_dict_value t2 on tt.two_type = t2.nm) as ss " + 
			"where ss.project = t0.project and ss.scope = '集镇新址占地区') as 'K' " + 
			"from (select t2.name project from t_info_transport_engineer tt " + 
			"left join pub_dict_value t2 on tt.two_type = t2.nm group by t2.name) as t0", nativeQuery = true)
	public List<InfoStatisticsDetailVO> transport();// 交通运输工程
}
