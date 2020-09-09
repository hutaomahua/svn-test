package com.lyht.business.abm.landAllocation.dao.impl;

import com.lyht.business.abm.landAllocation.dao.EmLandPoolDao;
import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @version: V1.0
 * @author: hjs
 * @className: EmLandPoolDaoImpl
 * @packageName: com.lyht.business.abm.landAllocation.dao.impl
 * @description: （类作用）
 * @data: 2020年02月10日 10:45
 * @see []
 **/
@Repository(value="emLandPoolDao")
public class EmLandPoolDaoImpl implements EmLandPoolDao {

    @PersistenceContext//jpa的数据库操作类
    private EntityManager em;
    
    @Override
    public List<Map<String, Object>> queryLandData(String region, String status, int level, Boolean flag) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT pv.name AS landType,  ");
        sql.append(" pv.nm AS landTypeCode, ");
        sql.append(" pv2.nm AS limitsTypeCode,  ");
        sql.append(" pv2.name AS limitsType,");
        //动态拼接要查询的面积类型
        if(flag){
            //需要所有数据
            sql.append(" SUM(tp.area) AS area, ");
            sql.append(" SUM(tp.separate_area) AS useArea, ");
            sql.append(" (SUM(tp.area)-SUM(tp.separate_area)) AS remainArea ");
        }else if("totalArea".equals(status)){
            //总面积
            sql.append(" SUM(tp.area) AS area ");
        }else if("useArea".equals(status)){
            //已使用面积
            sql.append(" SUM(tp.separate_area) AS area ");
        }else if("remainArea".equals(status)){
            //待分解面积
            sql.append(" (SUM(tp.area)-SUM(tp.separate_area)) AS area ");
        }else{
            //如没有匹配的条件 则删除最后一个字符(逗号)
            int len = sql.length();
            sql.delete(len-1, len);
        }
        sql.append(" FROM t_abm_land_pool tp  ");
        //动态拼接下钻的层数
        switch (level){
            case 0:
                sql.append(" INNER JOIN pub_dict_value pv ON tp.all_type = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type'  ");
                break;
            case 1:
                sql.append(" INNER JOIN pub_dict_value pv ON tp.type_one = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type_one'  ");
                break;
            case 2:
                sql.append(" INNER JOIN pub_dict_value pv ON tp.type_two = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type_two'  ");
                break;
            case 3:
                sql.append(" INNER JOIN pub_dict_value pv ON tp.type_three = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type_three'  ");
                break;
        }
        sql.append(" INNER JOIN pub_dict_value pv2 ON tp.scope = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_limits'  ");
        sql.append(" WHERE tp.region = :region GROUP BY pv.nm,pv2.nm ");
        Query nativeQuery = em.createNativeQuery(sql.toString());
        nativeQuery.setParameter("region", region);
        List<Map<String,Object>> list = nativeQuery.unwrap(SQLQuery.class).
                setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryLandChart(String region, String typeAll, String typeOne, String typeTwo, String typeThree, Boolean isLast, String status) {
        boolean typeAllNotBlank = typeAll != null && typeAll.trim().length()>0,
                typeOneNotBlank = typeOne != null && typeOne.trim().length()>0,
                typeTwoNotBlank = typeTwo != null && typeTwo.trim().length()>0,
                typeThreeNotBlank = typeThree != null && typeThree.trim().length()>0;
        String typeNameSql = " tp.all_type AS code, IFNULL(pv.name,'无') AS name,IF(tp.type_one = '' OR tp.type_one IS NULL, 'true', 'false') AS isLast ";
        String groupSql = " GROUP BY all_type ";
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT  ");
        //动态拼接要查询的面积类型
        if(status == null || status.trim().length() == 0){
            //需要所有数据
            sql.append(" SUM(tp.area) AS area, ");
            sql.append(" SUM(tp.separate_area) AS useArea, ");
            sql.append(" (SUM(tp.area)-SUM(tp.separate_area)) AS remainArea, ");
        }else if("totalArea".equals(status)){
            //总面积
            sql.append(" SUM(tp.area) AS area, ");
        }else if("useArea".equals(status)){
            //已使用面积
            sql.append(" SUM(tp.separate_area) AS area, ");
        }else if("remainArea".equals(status)){
            //待分解面积
            sql.append(" (SUM(tp.area)-SUM(tp.separate_area)) AS area, ");
        }
        if(isLast || (typeAllNotBlank && typeOneNotBlank && typeTwoNotBlank && typeThreeNotBlank)){
            typeNameSql = " tp.scope as code, IFNULL(pv5.name,'未知区域') AS name, 'true' AS isScope ";
            groupSql = " GROUP BY all_type,type_one,type_two,type_three,scope ";
        }else if(typeAllNotBlank && typeOneNotBlank && typeTwoNotBlank){
            typeNameSql = " tp.type_three AS code, IFNULL(pv4.name,'无') AS name, 'true' AS isLast ";
            groupSql = " GROUP BY all_type,type_one,type_two,type_three ";
        }else if(typeAllNotBlank && typeOneNotBlank){
            typeNameSql = " tp.type_two AS code, IFNULL(pv3.name,'无') AS name , IF(tp.type_three = '' OR tp.type_three IS NULL, 'true', 'false') AS isLast ";
            groupSql = " GROUP BY all_type,type_one,type_two ";
        }else if(typeAllNotBlank){
            typeNameSql = " tp.type_one AS code, IFNULL(pv2.name,'无') AS name, IF(tp.type_two = '' OR tp.type_two IS NULL, 'true', 'false') AS isLast ";
            groupSql = " GROUP BY all_type,type_one ";
        }
        sql.append(typeNameSql+",tp.scope,pv5.name scopeName");
        sql.append(" FROM t_abm_land_pool tp  ");
        sql.append(" LEFT JOIN pub_dict_value pv ON tp.all_type = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type' ");
        sql.append(" LEFT JOIN pub_dict_value pv2 ON tp.type_one = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_one' ");
        sql.append(" LEFT JOIN pub_dict_value pv3 ON tp.type_two = pv3.nm AND pv3.listnm_sys_dict_cate = 'dict_info_land_type_two'  ");
        sql.append(" LEFT JOIN pub_dict_value pv4 ON tp.type_three = pv4.nm AND pv4.listnm_sys_dict_cate = 'dict_info_land_type_three' ");
        sql.append(" LEFT JOIN pub_dict_value pv5 ON tp.scope =pv5.nm AND pv5.listnm_sys_dict_cate = 'dict_limits'  ");
        sql.append(" WHERE region = :region ");
        sql.append(" and if(:typeAll is not null and :typeAll != '', tp.all_type = :typeAll, 1=1) ");
        sql.append(" and if(:typeOne is not null and :typeOne != '', tp.type_one = :typeOne, 1=1) ");
        sql.append(" and if(:typeTwo is not null and :typeTwo != '', tp.type_two = :typeTwo, 1=1) ");
        sql.append(" and if(:typeThree is not null and :typeThree != '', tp.type_three = :typeThree, 1=1) ");
        sql.append(groupSql);
        Query nativeQuery = em.createNativeQuery(sql.toString());
        nativeQuery.setParameter("typeAll", typeAll);
        nativeQuery.setParameter("typeOne", typeOne);
        nativeQuery.setParameter("typeTwo", typeTwo);
        nativeQuery.setParameter("typeThree", typeThree);
        nativeQuery.setParameter("region", region);
        List<Map<String,Object>> list = nativeQuery.unwrap(SQLQuery.class).
                setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
        return list;
    }

    @Override
    public List<Map<String, Object>> queryLandType(String region, String typeAll, String typeOne, String typeTwo, String typeThree, Boolean isLast) {
        boolean typeAllNotBlank = typeAll != null && typeAll.trim().length()>0,
                typeOneNotBlank = typeOne != null && typeOne.trim().length()>0,
                typeTwoNotBlank = typeTwo != null && typeTwo.trim().length()>0,
                typeThreeNotBlank = typeThree != null && typeThree.trim().length()>0;
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        String typeNameSql = " tp.all_type AS code, IFNULL(pv.name,'无') AS name, IF(tp.type_one = '' OR tp.type_one IS NULL, 'true', 'false') AS LAST, ";
        String groupSql = " GROUP BY all_type ";
        if(isLast || (typeAllNotBlank && typeOneNotBlank && typeTwoNotBlank && typeThreeNotBlank)){
            typeNameSql = " tp.scope as code, IFNULL(pv5.name,'无') as name, ";
            groupSql = " GROUP BY all_type,type_one,type_two,type_three,scope ";
        }else if(typeAllNotBlank && typeOneNotBlank && typeTwoNotBlank){
            typeNameSql = " tp.type_three AS code, IFNULL(pv4.name,'无') AS name, 'true' AS isLast, ";
            groupSql = " GROUP BY all_type,type_one,type_two,type_three ";
        }else if(typeAllNotBlank && typeOneNotBlank){
            typeNameSql = " tp.type_two AS code, IFNULL(pv3.name,'无') AS name, IF(tp.type_three = '' OR tp.type_three IS NULL, 'true', 'false') AS isLast,";
            groupSql = " GROUP BY all_type,type_one,type_two ";
        }else if(typeAllNotBlank){
            typeNameSql = " tp.type_one AS code, IFNULL(pv2.name,'无') AS name, IF(tp.type_two = '' OR tp.type_two IS NULL, 'true', 'false') AS isLast, ";
            groupSql = " GROUP BY all_type,type_one ";
        }
        sql.append(typeNameSql);
        sql.append("  SUM(tp.area) AS area, ");
        sql.append("  SUM(tp.separate_area) AS useArea, ");
        //sql.append("  SUM(tp.area) - SUM(tp.separate_area) AS remainArea  ");
        sql.append("SUM(tp.area) - SUM(tp.separate_area)-IFNULL((select sum(area) from t_abm_land_pool_process where pool_id = tp.id),0) AS remainArea");
        sql.append(" FROM t_abm_land_pool tp ");
        sql.append(" LEFT JOIN pub_dict_value pv ON tp.all_type = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type' ");
        sql.append(" LEFT JOIN pub_dict_value pv2 ON tp.type_one = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_one' ");
        sql.append(" LEFT JOIN pub_dict_value pv3 ON tp.type_two = pv3.nm AND pv3.listnm_sys_dict_cate = 'dict_info_land_type_two'  ");
        sql.append(" LEFT JOIN pub_dict_value pv4 ON tp.type_three = pv4.nm AND pv4.listnm_sys_dict_cate = 'dict_info_land_type_three' ");
        sql.append(" LEFT JOIN pub_dict_value pv5 ON tp.scope =pv5.nm AND pv5.listnm_sys_dict_cate = 'dict_limits'  ");
        sql.append(" WHERE tp.status = 0 and tp.region = :region ");
        sql.append(" and if(:typeAll is not null and :typeAll != '', tp.all_type = :typeAll, 1=1) ");
        sql.append(" and if(:typeOne is not null and :typeOne != '', tp.type_one = :typeOne, 1=1) ");
        sql.append(" and if(:typeTwo is not null and :typeTwo != '', tp.type_two = :typeTwo, 1=1) ");
        sql.append(" and if(:typeThree is not null and :typeThree != '', tp.type_three = :typeThree, 1=1) ");
        sql.append(groupSql);
        sql.append(" HAVING remainArea  > 0 ");
        Query nativeQuery = em.createNativeQuery(sql.toString());
        nativeQuery.setParameter("typeAll", typeAll);
        nativeQuery.setParameter("typeOne", typeOne);
        nativeQuery.setParameter("typeTwo", typeTwo);
        nativeQuery.setParameter("typeThree", typeThree);
        nativeQuery.setParameter("region", region);
        List<Map<String,Object>> list = nativeQuery.unwrap(SQLQuery.class).
                setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
        return list;
    }

    @Override
    public Map<String, Object> findLandType(int level, String landType) {
        if(StringUtils.isBlank(landType) && (level < 0 && level >3)){
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT tp.all_type as allType, ");
        sql.append(" pv.name as allTypeName,");
        sql.append(" tp.type_one as typeOne, ");
        sql.append(" pv2.name as typeOneName,");
        sql.append(" tp.type_two as typeTwo, ");
        sql.append(" pv3.name as typeTwoName,");
        sql.append(" tp.type_three as typeThree, ");
        sql.append(" pv4.name as typeThreeName");
        sql.append(" FROM t_abm_land_pool tp ");
        sql.append(" JOIN pub_dict_value pv ON tp.all_type = pv.nm AND pv.listnm_sys_dict_cate = 'dict_info_land_type' ");
        sql.append(" LEFT JOIN pub_dict_value pv2 ON tp.type_one = pv2.nm AND pv2.listnm_sys_dict_cate = 'dict_info_land_type_one' ");
        sql.append(" LEFT JOIN pub_dict_value pv3 ON tp.type_two = pv3.nm AND pv3.listnm_sys_dict_cate = 'dict_info_land_type_two' ");
        sql.append(" LEFT JOIN pub_dict_value pv4 ON tp.type_three = pv4.nm AND pv4.listnm_sys_dict_cate = 'dict_info_land_type_three' ");
        sql.append(" where ");
        switch (level){
            case 0:
                sql.append(" tp.all_type = :landType ");
                break;
            case 1:
                sql.append(" tp.type_one = :landType  ");
                break;
            case 2:
                sql.append(" tp.type_two = :landType ");
                break;
            case 3:
                sql.append(" tp.type_three = :landType ");
                break;
        }
        Query nativeQuery = em.createNativeQuery(sql.toString());
        nativeQuery.setParameter("landType", landType);
        List<Map<String,Object>> list = nativeQuery.unwrap(SQLQuery.class).
                setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public LandPoolEntity findLand(int level, String region, String scope, String landType) {
        if(level < 0 && level > 3){
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" FROM LandPoolEntity ");
        sql.append(" where ");
        switch (level){
            case 0:
                sql.append(" allType = :landType ");
                sql.append(" and (typeOne is null or typeOne = '') ");
                sql.append(" and (typeTwo is null or typeTwo = '') ");
                sql.append(" and (typeThree is null or typeThree = '') ");
                break;
            case 1:
                sql.append(" typeOne = :landType ");
                sql.append(" and (typeTwo is null or typeTwo = '') ");
                sql.append(" and (typeThree is null or typeThree = '') ");
                break;
            case 2:
                sql.append(" typeTwo = :landType ");
                sql.append(" and (typeThree is null or typeThree = '') ");
                break;
            case 3:
                sql.append(" typeThree = :landType ");
                break;
        }
        sql.append(" and region = :region and scope = :scope");
        Query nativeQuery = em.createQuery(sql.toString());
        nativeQuery.setParameter("landType", landType).setParameter("region",region).setParameter("scope",scope);
        List<LandPoolEntity> list = nativeQuery.getResultList();
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void updateLandPool(Integer id, BigDecimal resolveArea) {
        String sql = "update t_abm_land_pool set separate_area = separate_area + :resolveArea where id = :id ";
        Query nativeQuery = em.createNativeQuery(sql.toString());
        nativeQuery.setParameter("resolveArea", resolveArea);
        nativeQuery.setParameter("id", id);
        nativeQuery.executeUpdate();
    }

    @Override
    @Transactional
    public void addLandArea(Integer id, BigDecimal resolveArea, Boolean retain) {
        StringBuffer sql = new StringBuffer();
        sql.append(" update t_abm_land_pool ");
        sql.append(" set area = area + :resolveArea  ");
        if(!retain){
            sql.append(" ,separate_area = separate_area + :resolveArea ");
        }
        sql.append(" where id = :id  ");
        Query nativeQuery = em.createNativeQuery(sql.toString());
        nativeQuery.setParameter("resolveArea", resolveArea);
        nativeQuery.setParameter("id", id);
        nativeQuery.executeUpdate();
    }

}
