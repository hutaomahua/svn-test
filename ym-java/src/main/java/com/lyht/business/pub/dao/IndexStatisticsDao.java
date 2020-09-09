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
 * @Description:
 * @Author: xzp
 * @Date: 2020/8/4 15:13
 **/
@Repository
public interface IndexStatisticsDao extends JpaRepository<PubDictValue, Integer> {

    @Query(value = "SELECT SUM(s.amount) AS amount, s.code, s.pCode, s.name, s.level FROM ( " +
            "SELECT COUNT(distinct tio.id) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type AS level " +
            "FROM t_info_family tindex  " +
            "JOIN pub_region re ON tindex.region = re.city_code   " +
            "JOIN t_info_owner tio ON tindex.owner_nm = tio.nm  " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT SUM(tindex.area) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type " +
            "FROM t_info_houses tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm " +
            "GROUP BY re.id UNION ALL " +
            "SELECT SUM(tindex.area) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type " +
            "FROM t_info_houses_decoration tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT ((CASE WHEN tindex.area > 0 THEN tindex.area WHEN tindex.volume > 0 THEN tindex.volume WHEN " +
            "tindex.num > 0 THEN tindex.num END)) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, " +
            "re.level_type " +
            "FROM t_info_building tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT SUM(tindex.num) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type " +
            "FROM t_info_trees tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm  " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT SUM(tindex.area) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type  " +
            "FROM t_info_land tindex  " +
            "JOIN pub_region re ON tindex.region = re.city_code  " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm GROUP BY re.id " +
            "UNION SELECT 0 AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, level_type FROM " +
            "pub_region re WHERE level_type > 3 " +
            ") s GROUP BY s.code  " +
            "UNION SELECT 0 AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, 1 FROM pub_region re " +
            "WHERE city_code = 'E06A0AEF47' ",nativeQuery = true)
    List<Map> getRegionTressBasics();

    @Query(value = "SELECT SUM(s.amount) AS amount, s.code, s.pCode, s.name, s.level FROM ( " +
            "SELECT COUNT(distinct tio.id) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type AS level " +
            "FROM t_info_family_impl tindex  " +
            "JOIN pub_region re ON tindex.region = re.city_code   " +
            "JOIN t_info_owner tio ON tindex.owner_nm = tio.nm  " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT SUM(tindex.area) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type " +
            "FROM t_info_houses_impl tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm " +
            "GROUP BY re.id UNION ALL " +
            "SELECT SUM(tindex.area) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type " +
            "FROM t_info_houses_decoration_impl tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT ((CASE WHEN tindex.area > 0 THEN tindex.area WHEN tindex.volume > 0 THEN tindex.volume WHEN " +
            "tindex.num > 0 THEN tindex.num END)) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, " +
            "re.level_type " +
            "FROM t_info_building_impl tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT SUM(tindex.num) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type " +
            "FROM t_info_trees_impl tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code " +
            "JOIN pub_project dtype ON dtype.id = tindex.project_nm  " +
            "GROUP BY re.id UNION ALL  " +
            "SELECT SUM(tindex.area) AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, re" +
            ".level_type " +
            "FROM t_abm_land_pool tindex " +
            "JOIN pub_region re ON tindex.region = re.city_code AND tindex.region_level = 'group' GROUP BY re.id " +
            "UNION SELECT 0 AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, level_type FROM " +
            "pub_region re WHERE level_type > 3 " +
            ") s GROUP BY s.code  " +
            "UNION SELECT 0 AS amount, re.city_code AS code, re.parent_code AS pCode, re.name, 1 FROM pub_region re " +
            "WHERE city_code = 'E06A0AEF47' ",nativeQuery = true)
    List<Map> getRegionTressImplement();

    @Query(value = " SELECT table_pk_column AS nm, COUNT(*) AS fileCount FROM pub_files " +
            " WHERE table_name = :tableName AND table_pk_column IN(:nmList)" +
            " GROUP BY table_pk_column",nativeQuery = true)
    List<Map> queryFileListByNm(@Param("tableName") String tableName, @Param("nmList") List<String> nmList);

}
