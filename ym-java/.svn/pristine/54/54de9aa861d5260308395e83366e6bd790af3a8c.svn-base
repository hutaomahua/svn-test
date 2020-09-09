package com.lyht.business.abm.removal.dao;

import com.lyht.business.abm.removal.entity.AbmLandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * @version: V1.0
 * @author: hjs
 * @className: TudiEntityDao
 * @packageName: com.lyht.business.abm.removal.dao
 * @description: （类作用）
 * @data: 2020年02月13日 11:42
 * @see []
 **/
public interface TudiEntityDao {

    /**
     * 保存
     * @param tudiEntity
     */
    void save(AbmLandEntity tudiEntity);

    /**
     * 根据各项条件精确查询用户的某种土地
     * @param idCard 用户的身份证
     * @param level 土地类型级别
     * @param region 区域
     * @param scope 征地范围
     * @param landType 土地类型
     * @return
     */
    AbmLandEntity findByCondition(String idCard, int level, String region, String scope, String landType);

    /**
     * 对用户的土地面积进行相加
     * @param id
     * @param resolveArea 分解面积
     */
    void addLandArea(Integer id, BigDecimal resolveArea);
}
