package com.lyht.business.pub.service;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.pub.dao.IndexStatisticsDao;
import com.lyht.business.pub.dao.PubDictValueDao;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.enums.IndexTypeEnums;
import com.lyht.util.tree.AddressTreeUtils;
import com.lyht.util.tree.StatisticsTreeUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: xzp
 * @Date: 2020/8/4 15:13
 **/
@Service("/indexStatisticsService")
public class IndexStatisticsService {

    @Autowired private PubRegionDao pubRegionDao;
    @Autowired private PubDictValueDao pubDictValueDao;
    @Autowired private IndexStatisticsDao indexStatisticsDao;
    @PersistenceContext private EntityManager entityManager;

    public List<Map> getFileByIndexType(String ownerNm, String indexType, Integer type){
        String tableName;
        if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_FAMILY.getKey())){
            tableName = "t_info_family";
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_HOUSES.getKey())){
            tableName = "t_info_houses";
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_DECORATION.getKey())){
            tableName = "t_info_houses_decoration";
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_BUILDING.getKey())){
            tableName = "t_info_building";
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_TREES.getKey())){
            tableName = "t_info_trees";
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_LAND.getKey())){
            tableName = "t_info_land";
        }else if(indexType.equals("individual")){
            tableName = "t_info_individual";
        }else if(indexType.equals("facilities")){
            tableName = "t_info_agricultural_facilities";
        }else if(indexType.equals("homestead")){
            tableName = "t_info_homestead";
        }else {
            return new ArrayList<>();
        }
        if(type > 1){
            tableName = tableName + "_impl";
        }
        Query dataQuery = entityManager.createNativeQuery("SELECT fi.* FROM pub_files fi JOIN " + tableName + " tindex ON fi.table_name = '" + tableName + "' AND fi.table_pk_column = tindex.nm AND tindex.owner_nm = '" + ownerNm +"' ");
        dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = dataQuery.getResultList();
        return list;
    }

    public List<Map> getFileListByNm(String tableName, List<String> nmList){
        return indexStatisticsDao.queryFileListByNm(tableName, nmList);
    }

    /*
     * @Description: 获取行政区树形结构
     * @Author: xzp
     * @Date: 2020/8/4 15:19
     * @param 》type:类型(1基础/2实施)
     * @return 》[{code:城市编码,pCode:父级编码,name:名称,children:子级}]
     **/
    public List<Map> getRegionTrees(Integer type){
        List<Map> list;
        if(type == 1){
            list = indexStatisticsDao.getRegionTressBasics();
        }else {
            list = indexStatisticsDao.getRegionTressImplement();
        }
        return new AddressTreeUtils().getRegionList(list);
    }

    /*
     * @Description: 获取征地范围树形结构
     * @Author: xzp
     * @Date: 2020/8/4 15:19
     * @param 》
     * @return 》[{code:城市编码,pCode:父级编码,name:名称,children:子级}]
     **/
    public List<Map> getScopeTrees(){
        List<Map> list = pubDictValueDao.queryScopeList();
        return new AddressTreeUtils().getAddressList(list);
    }

    /*
     * @Description: 获取基础数据统计
     * @Author: xzp
     * @Date: 2020/8/4 15:17
     * @param 》regionList:行政区内码,indexType:指标类型
     * @return 》[{code:code,pCode:父code,name:名称,unit:单位,total:总计,pivotalTotal:枢纽合计,pivotalTemporary:24ACBF9107,pivotalPerpetual:CE81C0FA94,
                reservoirTotal:水库合计,reservoirSubmerge:水库淹没,reservoirInfluence:水库影响,reservoirTemporary:水库临时,townTotal:集镇,children:子级}]
     **/
    public List<Map> getBasicsStatistics(List<String> regionList, String indexType){
        String paramSql;
        String regionSql = getParamSqlStr(regionList);//获取行政区拼接条件
        if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_FAMILY.getKey())){
            paramSql = getFamilyIndexSql("t_info_family", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_HOUSES.getKey())){
            paramSql = getCommonIndexSql("t_info_houses", "tindex.area", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_DECORATION.getKey())){
            paramSql = getCommonIndexSql("t_info_houses_decoration", "tindex.area", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_BUILDING.getKey())){
            //paramSql = getCommonIndexSql("t_info_building", "(CASE WHEN tindex.area > 0 THEN tindex.area WHEN tindex.volume > 0 THEN tindex.volume WHEN tindex.num > 0 THEN tindex.num END)", regionSql);
            paramSql = getCommonIndexSql("t_info_building", "tindex.num", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_TREES.getKey())){
            paramSql = getCommonIndexSql("t_info_trees", "tindex.num", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_LAND.getKey())){
            paramSql = getLandIndexSql(regionSql);
        }else {
            return null;
        }
        Query dataQuery = entityManager.createNativeQuery(paramSql);
        dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = dataQuery.getResultList();
        return new StatisticsTreeUtils().getIndexStatisticsList(list, indexType, 1, false, null);
    }

    /*
     * @Description: 获取实施数据统计
     * @Author: xzp
     * @Date: 2020/8/4 15:17
     * @param 》regionList:行政区内码,indexType:指标类型
     * @return 》[{code:code,pCode:父code,name:名称,unit:单位,total:总计,pivotalTotal:枢纽合计,pivotalTemporary:24ACBF9107,pivotalPerpetual:CE81C0FA94,
                reservoirTotal:水库合计,reservoirSubmerge:水库淹没,reservoirInfluence:水库影响,reservoirTemporary:水库临时,townTotal:集镇,children:子级}]
     **/
    public List<Map> getImplementStatistics(List<String> regionList, String indexType){
        String paramSql;
        String regionSql = getParamSqlStr(regionList);//获取行政区拼接条件
        if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_FAMILY.getKey())){
            paramSql = getFamilyIndexSql("t_info_family_impl", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_HOUSES.getKey())){
            paramSql = getCommonIndexSql("t_info_houses_impl", "tindex.area", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_DECORATION.getKey())){
            paramSql = getCommonIndexSql("t_info_houses_decoration_impl", "tindex.area", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_BUILDING.getKey())){
            paramSql = getCommonIndexSql("t_info_building_impl", "tindex.num", regionSql);
            //paramSql = getCommonIndexSql("t_info_building_impl", "(CASE WHEN tindex.area > 0 THEN tindex.area WHEN tindex.volume > 0 THEN tindex.volume WHEN tindex.num > 0 THEN tindex.num END)", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_TREES.getKey())){
            paramSql = getCommonIndexSql("t_info_trees_impl", "tindex.num", regionSql);
        }else if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_LAND.getKey())){
            paramSql = getLandImplIndexSql(regionSql);
        }else {
            return null;
        }
        Query dataQuery = entityManager.createNativeQuery(paramSql);
        dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = dataQuery.getResultList();
        Query dataQuery2 = entityManager.createNativeQuery("SELECT dd.*, " +
                "SUM(dd.pivotalTemporary + dd.pivotalPerpetual + dd.reservoirSubmerge + dd.reservoirInfluence + dd" +
                ".reservoirTemporary + dd.townTotal) AS total,  " +
                "SUM(dd.pivotalTemporary + dd.pivotalPerpetual) AS pivotalTotal,  " +
                "SUM(dd.reservoirSubmerge + dd.reservoirInfluence + dd.reservoirTemporary) AS reservoirTotal   " +
                "FROM ( SELECT  " +
                "SUM(IF(s.nm = '24ACBF9107', s.amount, 0)) AS pivotalTemporary, " +
                "SUM(IF(s.nm = 'CE81C0FA94', s.amount, 0)) AS pivotalPerpetual,  " +
                "SUM(IF(s.nm = 'D8D5AAD9DC', s.amount, 0)) AS reservoirSubmerge,  " +
                "SUM(IF(s.nm = 'E78D14E7BE', s.amount, 0)) AS reservoirInfluence,  " +
                "SUM(IF(s.nm = 'D18482159A', s.amount, 0)) AS reservoirTemporary,  " +
                "SUM(IF(s.nm = '348F5B68BA', s.amount, 0)) AS townTotal  " +
                "FROM ( SELECT SUM(area) AS amount, d.name, d.nm FROM t_abm_land_pool t JOIN pub_dict_value d on t" +
                ".scope = d.nm WHERE region_level = 'province' GROUP BY t.scope ) s ) dd ");
        dataQuery2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list2 = dataQuery2.getResultList();
        Map data = new HashMap(1);
        if(null != list2){
            data = list2.get(0);
        }
        return new StatisticsTreeUtils().getIndexStatisticsList(list, indexType, 2, regionList.contains("E06A0AEF47"), data);
    }

    /*
     * @Description: 获取人口指标SQL拼接
     * @Author: xzp
     * @Date: 2020/8/5 10:29
     * @param 》tableName:表名,regionSql:行政区条件
     * @return 》通用人口SQL
     **/
    public String getFamilyIndexSql(String tableName, String regionSql){
        if("t_info_family".equals(tableName)){
            tableName = "t_info_owner";
        }else {
            tableName = "t_info_owner_impl";
        }
        StringBuffer sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("d.*, ");
            sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual + d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary + d.townTotal) AS total, ");
            sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual) AS pivotalTotal, ");
            sql.append("SUM(d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary) AS reservoirTotal ");
            sql.append("FROM ( ");
            sql.append("SELECT ");
            sql.append("s.name, s.code, s.pCode, s.unit, ");
            sql.append("CAST(SUM(IF(s.scopeName = '24ACBF9107', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalTemporary, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'CE81C0FA94', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalPerpetual, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'D8D5AAD9DC', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirSubmerge, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'E78D14E7BE', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirInfluence, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'D18482159A', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirTemporary, ");
            sql.append("CAST(SUM(IF(s.scopeName = '348F5B68BA', s.amount, 0)) AS DECIMAL(10, 4)) AS townTotal ");
            sql.append("FROM ( ");
            sql.append("SELECT 2 AS code, '移民户' AS name, 1 AS pCode, dscope.nm AS scopeName, SUM(tindex.immigrant) AS amount, '户' AS unit ");
            sql.append("FROM " + tableName + " tindex ");
            sql.append("JOIN pub_dict_value dscope ON tindex.scope = dscope.nm ");
            sql.append("AND dscope.listnm_sys_dict_cate = 'dict_limits' ");
            sql.append("JOIN pub_region re ON tindex.region = re.city_code ");
            sql.append(regionSql);
            sql.append("GROUP BY tindex.id ");
            sql.append("UNION ALL SELECT 4 AS code, '农业人口' AS name, 3 AS pCode, dscope.nm AS scopeName, SUM(tindex.ap) AS amount, '人' AS unit ");
            sql.append("FROM " + tableName + " tindex ");
            sql.append("JOIN pub_dict_value dscope ON tindex.scope = dscope.nm ");
            sql.append("AND dscope.listnm_sys_dict_cate = 'dict_limits' ");
            sql.append("JOIN pub_region re ON tindex.region = re.city_code ");
            sql.append(regionSql);
            sql.append("GROUP BY tindex.id ");
            sql.append("UNION ALL SELECT 5 AS code, '非农业人口' AS name, 3 AS pCode, dscope.nm AS scopeName, SUM(tindex.non_ap) AS amount, '人' AS unit ");
            sql.append("FROM " + tableName + " tindex ");
            sql.append("JOIN pub_dict_value dscope ON tindex.scope = dscope.nm ");
            sql.append("AND dscope.listnm_sys_dict_cate = 'dict_limits' ");
            sql.append("JOIN pub_region re ON tindex.region = re.city_code ");
            sql.append(regionSql);
            sql.append("GROUP BY tindex.id ");
            sql.append("UNION ALL SELECT 1, '人口与户数', 0, '', 0 , '' ");
            sql.append("UNION ALL SELECT 2, '移民户', 1, '', 0 , '户' ");
            sql.append("UNION ALL SELECT 3, '移民人口', 1, '', 0 , '人' ");
            sql.append("UNION ALL SELECT 4, '农业人口', 3, '', 0 , '人' ");
            sql.append("UNION ALL SELECT 5, '非农业人口', 3, '', 0 , '人' ");
            sql.append(") s ");
            sql.append("GROUP BY s.code ");
            sql.append(") d GROUP BY d.code");
            /*sql.append("SELECT ");
            sql.append("d.*, ");
            sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual + d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary + d.townTotal) AS total, ");
            sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual) AS pivotalTotal, ");
            sql.append("SUM(d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary) AS reservoirTotal ");
            sql.append("FROM ( ");
            sql.append("SELECT ");
            sql.append("s.name, s.code, s.pCode, s.unit, ");
            sql.append("CAST(SUM(IF(s.scopeName = '24ACBF9107', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalTemporary, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'CE81C0FA94', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalPerpetual, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'D8D5AAD9DC', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirSubmerge, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'E78D14E7BE', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirInfluence, ");
            sql.append("CAST(SUM(IF(s.scopeName = 'D18482159A', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirTemporary, ");
            sql.append("CAST(SUM(IF(s.scopeName = '348F5B68BA', s.amount, 0)) AS DECIMAL(10, 4)) AS townTotal ");
            sql.append("FROM ( ");
            sql.append("SELECT 2 AS code, '移民户' AS name, 1 AS pCode, dscope.nm AS scopeName, COUNT(DISTINCT tio.id) AS amount, '户' AS unit ");
            sql.append("FROM " + tableName + " tindex ");
            sql.append("JOIN pub_dict_value dscope ON tindex.scope = dscope.nm ");
            sql.append("AND dscope.listnm_sys_dict_cate = 'dict_limits' ");
            sql.append("JOIN pub_region re ON tindex.region = re.city_code ");
            sql.append(regionSql);
            sql.append("JOIN t_info_owner tio ON tindex.owner_nm = tio.nm ");
            sql.append("GROUP BY dscope.id ");
            sql.append("UNION ALL SELECT 4 AS code, '农业人口' AS name, 3 AS pCode, dscope.nm AS scopeName, COUNT(tindex.id) AS amount, '人' AS unit ");
            sql.append("FROM " + tableName + " tindex ");
            sql.append("JOIN pub_dict_value dscope ON tindex.scope = dscope.nm ");
            sql.append("AND dscope.listnm_sys_dict_cate = 'dict_limits' AND tindex.households_type = '7EC014AB3E' ");
            sql.append("JOIN pub_region re ON tindex.region = re.city_code ");
            sql.append(regionSql);
            sql.append("GROUP BY dscope.id ");
            sql.append("UNION ALL SELECT 5 AS code, '非农业人口' AS name, 3 AS pCode, dscope.nm AS scopeName, COUNT(tindex.id) AS amount, '人' AS unit ");
            sql.append("FROM " + tableName + " tindex ");
            sql.append("JOIN pub_dict_value dscope ON tindex.scope = dscope.nm ");
            sql.append("AND tindex.households_type = '7B66B22D8C' AND dscope.listnm_sys_dict_cate = 'dict_limits' ");
            sql.append("JOIN pub_region re ON tindex.region = re.city_code ");
            sql.append(regionSql);
            sql.append("GROUP BY dscope.id ");
            sql.append("UNION ALL SELECT 1, '人口与户数', 0, '', 0 , '' ");
            sql.append("UNION ALL SELECT 2, '移民户', 1, '', 0 , '户' ");
            sql.append("UNION ALL SELECT 3, '移民人口', 1, '', 0 , '人' ");
            sql.append("UNION ALL SELECT 4, '农业人口', 3, '', 0 , '人' ");
            sql.append("UNION ALL SELECT 5, '非农业人口', 3, '', 0 , '人' ");
            sql.append(") s ");
            sql.append("GROUP BY s.code ");
            sql.append(") d GROUP BY d.code");*/
        return sql.toString();
    }

    /*
     * @Description: 获取土地指标SQL拼接
     * @Author: xzp
     * @Date: 2020/8/5 10:29
     * @param 》regionSql:行政区条件
     * @return 》土地实施指标SQL
     **/
    public String getLandIndexSql(String regionSql){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT d.*, ");
        sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual + d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary + d.townTotal) AS total, ");
        sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual) AS pivotalTotal, ");
        sql.append("SUM(d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary) AS reservoirTotal ");
        sql.append("FROM ( ");
        sql.append("SELECT s.name, s.code, s.pCode, '亩' AS unit,");
        sql.append("CAST(SUM(IF(s.type = '24ACBF9107', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalTemporary, ");
        sql.append("CAST(SUM(IF(s.type = 'CE81C0FA94', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalPerpetual, ");
        sql.append("CAST(SUM(IF(s.type = 'D8D5AAD9DC', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirSubmerge, ");
        sql.append("CAST(SUM(IF(s.type = 'E78D14E7BE', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirInfluence, ");
        sql.append("CAST(SUM(IF(s.type = 'D18482159A', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirTemporary, ");
        sql.append("CAST(SUM(IF(s.type = '348F5B68BA', s.amount, 0)) AS DECIMAL(10, 4)) AS townTotal ");
        sql.append("FROM (");
        sql.append("SELECT '24ACBF9107' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_info_land tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = '24ACBF9107'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id UNION ALL ");
        sql.append("SELECT 'CE81C0FA94' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_info_land tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'CE81C0FA94'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT 'D8D5AAD9DC' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_info_land tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'D8D5AAD9DC'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id UNION ALL ");
        sql.append("SELECT 'E78D14E7BE' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_info_land tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'E78D14E7BE'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT 'D18482159A' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_info_land tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'D18482159A'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT '348F5B68BA' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_info_land tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = '348F5B68BA'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT '', 'landgg', '土地', '0', 0 ");
        sql.append("UNION ALL SELECT '', nm, name, 'landgg', 0 FROM pub_dict_value WHERE listnm_sys_dict_cate = 'dict_info_land_type_one' ");
        sql.append("UNION ALL SELECT '', d.nm, d.name, t.type_one, 0 FROM pub_dict_value d LEFT JOIN t_info_land t ON d.nm = t.type_two WHERE d.listnm_sys_dict_cate = 'dict_info_land_type_two' ");
        sql.append("UNION ALL SELECT '', d.nm, d.name, t.type_two, 0 FROM pub_dict_value d LEFT JOIN t_info_land t ON d.nm = t.type_three WHERE d.listnm_sys_dict_cate = 'dict_info_land_type_three' ");
        sql.append(") s GROUP BY s.code");
        sql.append(") d GROUP BY d.code");
        return sql.toString();
    }

    /*
     * @Description: 获取土地实施指标SQL拼接
     * @Author: xzp
     * @Date: 2020/8/5 10:29
     * @param 》regionSql:行政区条件
     * @return 》土地实施指标SQL
     **/
    public String getLandImplIndexSql(String regionSql){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT d.*, ");
        sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual + d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary + d.townTotal) AS total, ");
        sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual) AS pivotalTotal, ");
        sql.append("SUM(d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary) AS reservoirTotal ");
        sql.append("FROM ( ");
        sql.append("SELECT s.name, s.code, s.pCode, '亩' AS unit,");
        sql.append("CAST(SUM(IF(s.type = '24ACBF9107', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalTemporary, ");
        sql.append("CAST(SUM(IF(s.type = 'CE81C0FA94', s.amount, 0)) AS DECIMAL(10, 4)) AS pivotalPerpetual, ");
        sql.append("CAST(SUM(IF(s.type = 'D8D5AAD9DC', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirSubmerge, ");
        sql.append("CAST(SUM(IF(s.type = 'E78D14E7BE', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirInfluence, ");
        sql.append("CAST(SUM(IF(s.type = 'D18482159A', s.amount, 0)) AS DECIMAL(10, 4)) AS reservoirTemporary, ");
        sql.append("CAST(SUM(IF(s.type = '348F5B68BA', s.amount, 0)) AS DECIMAL(10, 4)) AS townTotal ");
        sql.append("FROM (");
        sql.append("SELECT '24ACBF9107' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_abm_land_pool tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = '24ACBF9107' AND tindex.region_level = 'group'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id UNION ALL ");
        sql.append("SELECT 'CE81C0FA94' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_abm_land_pool tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'CE81C0FA94' AND tindex.region_level = 'group'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT 'D8D5AAD9DC' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_abm_land_pool tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'D8D5AAD9DC' AND tindex.region_level = 'group'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id UNION ALL ");
        sql.append("SELECT 'E78D14E7BE' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_abm_land_pool tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'E78D14E7BE' AND tindex.region_level = 'group'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT 'D18482159A' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_abm_land_pool tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = 'D18482159A' AND tindex.region_level = 'group'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT '348F5B68BA' AS type, dtype.nm AS code, dtype.name, tindex.type_one AS pCode, SUM(tindex.area) AS amount ");
        sql.append("FROM t_abm_land_pool tindex ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code AND tindex.scope = '348F5B68BA' AND tindex.region_level = 'group'");
        sql.append(regionSql);
        sql.append("JOIN pub_dict_value dtype ON tindex.type_two = dtype.nm GROUP BY tindex.id ");
        sql.append("UNION ALL SELECT '', 'landgg', '土地', '0', 0 ");
        sql.append("UNION ALL SELECT '', nm, name, 'landgg', 0 FROM pub_dict_value WHERE listnm_sys_dict_cate = 'dict_info_land_type_one' ");
        sql.append("UNION ALL SELECT '', d.nm, d.name, t.type_one, 0 FROM pub_dict_value d LEFT JOIN t_abm_land_pool t ON d.nm = t.type_two WHERE d.listnm_sys_dict_cate = 'dict_info_land_type_two' ");
        sql.append("UNION ALL SELECT '', d.nm, d.name, t.type_two, 0 FROM pub_dict_value d LEFT JOIN t_abm_land_pool t ON d.nm = t.type_three WHERE d.listnm_sys_dict_cate = 'dict_info_land_type_three' ");
        sql.append(") s GROUP BY s.code");
        sql.append(") d GROUP BY d.code");
        return sql.toString();
    }

    /*
     * @Description: 获取通用指标SQL拼接
     * @Author: xzp
     * @Date: 2020/8/5 10:29
     * @param 》tableName:表名,amountColumnName:数量列名,regionSql:行政区条件
     * @return 》通用指标SQL
     **/
    public String getCommonIndexSql(String tableName, String amountColumnName, String regionSql){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("d.*, ");
        sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual + d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary + d.townTotal) AS total, ");
        sql.append("SUM(d.pivotalTemporary + d.pivotalPerpetual) AS pivotalTotal, ");
        sql.append("SUM(d.reservoirSubmerge + d.reservoirInfluence + d.reservoirTemporary) AS reservoirTotal ");
        sql.append("FROM ( ");
        sql.append("SELECT ");
        sql.append("s.name, s.code, s.pCode, s.unit, ");
        sql.append("SUM(IF(s.scopeName = '24ACBF9107', s.amount, 0)) AS pivotalTemporary, ");
        sql.append("SUM(IF(s.scopeName = 'CE81C0FA94', s.amount, 0)) AS pivotalPerpetual, ");
        sql.append("SUM(IF(s.scopeName = 'D8D5AAD9DC', s.amount, 0)) AS reservoirSubmerge, ");
        sql.append("SUM(IF(s.scopeName = 'E78D14E7BE', s.amount, 0)) AS reservoirInfluence, ");
        sql.append("SUM(IF(s.scopeName = 'D18482159A', s.amount, 0)) AS reservoirTemporary, ");
        sql.append("SUM(IF(s.scopeName = '348F5B68BA', s.amount, 0)) AS townTotal ");
        sql.append("FROM ( ");
        sql.append("SELECT dtype.id AS code, dtype.name, dtype.super_id AS pCode, dscope.nm AS scopeName, SUM(" + amountColumnName + ") AS amount ");//列数量
        sql.append(getIndexTypeUnit(tableName));//展示单位字段
        sql.append("FROM " + tableName + " tindex ");
        sql.append("JOIN pub_dict_value dscope ON tindex.scope = dscope.nm ");
        sql.append("AND dscope.listnm_sys_dict_cate = 'dict_limits' ");
        sql.append("JOIN pub_region re ON tindex.region = re.city_code ");
        sql.append(regionSql);//行政区条件WHERE IN()
        sql.append("JOIN pub_project dtype ON dtype.id = tindex.project_nm ");//JOIN指标类型
        sql.append("GROUP BY tindex.id ");
        sql.append(getUnionIndexTypeSql(tableName));//JOIN UNION ALL 所有指标
        sql.append(") s ");
        sql.append("GROUP BY s.code ");
        sql.append(") d GROUP BY d.code");
        return sql.toString();
    }

    /*
     * @Description: 获取统计汇总单位
     * @Author: xzp
     * @Date: 2020/8/5 11:17
     * @param 》tableName:表名
     * @return 》展示单位字段
     **/
    public String getIndexTypeUnit(String tableName){
        String str;
        switch (tableName) {
            case "t_info_houses" :
                str = ", 'm2' AS unit ";
                break;
            case "t_info_houses_decoration" :
                str = ", 'm2' AS unit ";
                break;
            case "t_info_building" :
                str = ", tindex.unit ";
                break;
            case "t_info_trees" :
                str = ", tindex.unit ";
                break;
            case "t_info_land" :
                str = ", '亩' AS unit ";
                break;
            case "t_info_houses_impl" :
                str = ", 'm2' AS unit ";
                break;
            case "t_info_houses_decoration_impl" :
                str = ", 'm2' AS unit ";
                break;
            case "t_info_building_impl" :
                str = ", tindex.unit ";
                break;
            case "t_info_trees_impl" :
                str = ", tindex.unit ";
                break;
            case "t_abm_land_pool" :
                str = ", '亩' AS unit ";
                break;
            default:
                str = ", ' ' AS unit ";
        }
        return str;
    }

    /*
     * @Description: 获取所有指标类型
     * @Author: xzp
     * @Date: 2020/8/5 10:41
     * @param 》tableName:表名
     * @return 》指标类型
     **/
    public String getUnionIndexTypeSql(String tableName){
        Integer id;
        String str, unit;
        switch (tableName) {
            case "t_info_houses" :
                id = 163;str = "房屋面积";unit = "m2";break;
            case "t_info_houses_decoration" :
                id = 272;str = "房屋装修";unit = "m2";break;
            case "t_info_building" :
                id = 355;str = "其他附属物";unit = " ";break;
            case "t_info_trees" :
                id = 287;str = "零星树木";unit = "亩";break;
            case "t_info_land" :
                id = 87;str = "土地";unit = "亩";break;
            case "t_info_houses_impl" :
                id = 163;str = "房屋面积";unit = "m2";break;
            case "t_info_houses_decoration_impl" :
                id = 272;str = "房屋装修";unit = "m2";break;
            case "t_info_building_impl" :
                id = 355;str = "其他附属物";unit = " ";break;
            case "t_info_trees_impl" :
                id = 287;str = "零星树木";unit = "亩";break;
            case "t_abm_land_pool" :
                id = 87;str = "土地";unit = "亩";break;
            default:
                throw new LyhtRuntimeException("表名不在范围内");
        }
        StringBuffer strSql = new StringBuffer();
        if("t_info_houses_impl".equals(tableName) || "t_info_houses".equals(tableName)){
            strSql.append(" UNION ALL SELECT id, name, IF(super_id = '341', 'housegg2', 'housegg1'), '', 0, 'm2' FROM pub_project WHERE id IN(SELECT dtype.super_id AS pCode FROM " + tableName + " tindex JOIN pub_project dtype ON dtype.id = tindex.project_nm GROUP BY dtype.super_id) ");
            strSql.append(" UNION ALL SELECT 'housegg', '房屋面积', 0, '', 0, 'm2' ");
            strSql.append(" UNION ALL SELECT 'housegg1', '农村', 'housegg', '', 0, 'm2' ");
            strSql.append(" UNION ALL SELECT 'housegg2', '集镇', 'housegg', '', 0, 'm2' ");
            return strSql.toString();
        }else if("t_info_trees_impl".equals(tableName) || "t_info_trees".equals(tableName)){
            strSql.append(" UNION ALL SELECT id, name, IF(super_id IN (SELECT t3.id FROM ( SELECT t1.id, t1.super_id, t1.name, IF(find_in_set(t1.super_id, @pids) > 0, @pids \\:= concat(@pids, ',', t1.id), 0) as ischild FROM (SELECT id, super_id, name FROM pub_project) t1, (SELECT @pids \\:= 447 id) t2 ) t3 WHERE t3.ischild != 0 ), 'treesgg2', 'treesgg1'), '', 0, '株' FROM pub_project WHERE id IN(SELECT dtype.super_id AS pCode FROM " + tableName + " tindex JOIN pub_project dtype ON dtype.id = tindex.project_nm GROUP BY dtype.super_id) ");
            strSql.append(" UNION ALL SELECT 'treesgg', '零星树木', 0, '', 0, '株' ");
            strSql.append(" UNION ALL SELECT 'treesgg1', '农村', 'treesgg', '', 0, '株' ");
            strSql.append(" UNION ALL SELECT 'treesgg2', '集镇', 'treesgg', '', 0, '株' ");
            return strSql.toString();
        }else if("t_info_building_impl".equals(tableName) || "t_info_building".equals(tableName)){
            strSql.append(" UNION ALL SELECT id, name, CASE id WHEN 355 THEN 'buigg2' WHEN 182 THEN 'buigg1' ELSE super_id END super_id, '', 0, danwei FROM pub_project WHERE id IN(SELECT dtype.super_id AS pCode FROM " + tableName + " tindex JOIN pub_project dtype ON dtype.id = tindex.project_nm GROUP BY dtype.super_id) ");
            strSql.append(" UNION ALL SELECT 'buigg', '附属建筑物', 0, '', 0, '' ");
            strSql.append(" UNION ALL SELECT 'buigg1', '农村', 'buigg', '', 0, '' ");
            strSql.append(" UNION ALL SELECT 'buigg2', '集镇', 'buigg', '', 0, '' ");
            return strSql.toString();
        }
        strSql.append("UNION ALL SELECT t3.id, t3.name, t3.super_id, '', 0, \'" + unit + "\' FROM ( ");
        strSql.append("SELECT t1.id, t1.super_id, t1.name, IF(find_in_set(t1.super_id, @pids) > 0, @pids \\:= concat(@pids, ',', t1.id), 0) as ischild ");
        strSql.append("FROM (SELECT id, super_id, name FROM pub_project) t1, (SELECT @pids \\:= " + id + " id) t2 ");
        strSql.append(") t3 WHERE t3.ischild != 0 ");
        strSql.append("UNION ALL SELECT ");
        strSql.append(id);
        strSql.append(", \'");
        strSql.append(str);
        strSql.append("\', 0, '', 0, \'" + unit + "\' ");
        return strSql.toString();
    }

    /*
     * @Description: 获取行政区拼接条件
     * @Author: xzp
     * @Date: 2020/8/5 10:11
     * @param 》regionList:行政区条件
     * @return 》拼接语句
     **/
    public String getParamSqlStr(List<String> regionList){
        StringBuffer paramSql = new StringBuffer();
        if(null != regionList && !regionList.isEmpty() && regionList.size() > 0){
            if(regionList.contains("E06A0AEF47")){
                paramSql.append(" ");
                return paramSql.toString();
            }
            /*if(2 == type && indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_LAND.getKey())){
                int i = 1, size = regionList.size();
                paramSql.append(" AND re.city_code IN (");
                for (String s : regionList){
                    paramSql.append("\'");
                    paramSql.append(s);
                    paramSql.append("\'");
                    if(i != size){
                        paramSql.append(",");
                    }
                    i ++;
                }
                paramSql.append(") ");
                return paramSql.toString();
            }*/
            //获取行政区所有子集
            List<Map> childrenList = new ArrayList<>();
            for (String s : regionList){
                Query dataQuery = entityManager.createNativeQuery(getRegionParamSql(s));
                dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                childrenList.addAll(dataQuery.getResultList());
            }
            childrenList = childrenList.stream().distinct().collect(Collectors.toList());
            if(childrenList.contains("E06A0AEF47")){
                paramSql.append(" ");
                return paramSql.toString();
            }
            //WEHRE IN 条件拼接
            int size = childrenList.size(), i = 1;
            paramSql.append(" AND re.city_code IN (");
            for (Map s : childrenList){
                paramSql.append("\'");
                paramSql.append(s.get("city_code"));
                paramSql.append("\'");
                if(i != size){
                    paramSql.append(",");
                }
                i ++;
            }
            paramSql.append(") ");
        }else {
            paramSql.append(" ");
        }
        return paramSql.toString();
    }

    public String getRegionParamSql(String code){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t3.city_code FROM ( ");
        sql.append("SELECT t1.city_code, IF(find_in_set(t1.parent_code, @pids) > 0, @pids \\:= concat(@pids, ',', t1.city_code), '0') as ischild ");
        sql.append("FROM (SELECT city_code, parent_code FROM pub_region) t1, (SELECT @pids \\:= \'" + code + "\' city_code) t2 ");
        sql.append(") t3 WHERE t3.ischild != '0' GROUP BY t3.city_code ");
        sql.append("UNION ALL SELECT city_code FROM pub_region WHERE city_code = \'" + code + "\' ");
        return sql.toString();
    }

}
