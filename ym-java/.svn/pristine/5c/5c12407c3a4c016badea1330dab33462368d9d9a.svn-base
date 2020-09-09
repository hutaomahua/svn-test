package com.lyht.business.abm.removal.dao.impl;

import com.lyht.business.abm.removal.dao.TudiEntityDao;
import com.lyht.business.abm.removal.entity.AbmLandEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * @version: V1.0
 * @author: hjs
 * @className: TudiEntityDaoImpl
 * @packageName: com.lyht.business.abm.removal.dao.impl
 * @description: （类作用）
 * @data: 2020年02月13日 11:52
 * @see []
 **/
@Repository(value="tudiEntityDao")
public class TudiEntityDaoImpl implements TudiEntityDao {

    @PersistenceContext//jpa的数据库操作类
    private EntityManager em;

    @Override
    @Transactional
    public void save(AbmLandEntity tudiEntity) {
        em.persist(tudiEntity);
        em.flush();
    }

    @Override
    public AbmLandEntity findByCondition(String idCard, int level, String region, String scope, String landType) {
        if(level < 0 && level > 3){
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" FROM AbmLandEntity ");
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
        sql.append(" and region = :region and scope = :scope and idCard = :idcard");
        Query nativeQuery = em.createQuery(sql.toString());
        nativeQuery.setParameter("landType", landType).setParameter("region",region).
                setParameter("scope",scope).setParameter("idcard", idCard);
        List<AbmLandEntity> list = nativeQuery.getResultList();
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void addLandArea(Integer id, BigDecimal resolveArea) {
        StringBuffer sql = new StringBuffer();
        sql.append(" update t_info_land_impl ");
        sql.append(" set area = area + :resolveArea  ");
        sql.append(" where id = :id  ");
        Query nativeQuery = em.createNativeQuery(sql.toString());
        nativeQuery.setParameter("resolveArea", resolveArea);
        nativeQuery.setParameter("id", id);
        nativeQuery.executeUpdate();
    }
}
