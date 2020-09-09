package com.lyht.business.abm.landAllocation.dao;

import com.lyht.business.abm.landAllocation.entity.LandPoolEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @version: V1.0
 * @author: hjs
 * @className: EmLandPoolDao
 * @packageName: com.lyht.business.abm.landAllocation.dao
 * @description: （接口作用作用）
 * @data: 2020年02月10日 10:45
 * @see []
 **/
public interface EmLandPoolDao {

    /**
     * 动态查询土地分解详情数据
     * @param region 机构编码
     * @param status 状态
     * @param level 下钻到的层数
     * @param flag 是否需要所有数据
     * @return
     */
    List<Map<String, Object>> queryLandData(String region, String status, int level, Boolean flag);

    /**
     * 查询土地分解图表数据
     * @param region
     * @param typeAll
     * @param typeOne
     * @param typeTwo
     * @param typeThree
     * @param isLast
     * @param status
     * @return
     */
    List<Map<String, Object>> queryLandChart(String region, String typeAll, String typeOne, String typeTwo, String typeThree, Boolean isLast, String status);

    /**
     * 根据行政区编码获得所有的土地类型面积
     * @param region
     * @param typeAll
     * @param typeOne
     * @param typeTwo
     * @param typeThree
     * @return
     */
    List<Map<String, Object>> queryLandType(String region, String typeAll, String typeOne, String typeTwo, String typeThree, Boolean isLast);

    /**
     * 根据级别和类型编码查询类型
     * @param level
     * @param landType
     * @return
     */
    Map<String, Object> findLandType(int level, String landType);

    /**
     * 根据条件精确查询土地分解池中的数据
     * @param level 土地类型级别
     * @param region 行政区编码
     * @param scope 征地范围
     * @param landType 土地类型
     * @return
     */
    LandPoolEntity findLand(int level, String region, String scope, String landType);

    /**
     * 修改土地分解池面积
     * @param id 主键
     * @param resolveArea 分解的面积
     */
    void updateLandPool(Integer id, BigDecimal resolveArea);

    /**
     * 对子行政区的土地面积进行相加
     * @param id
     * @param resolveArea 分解面积
     * @param retain 是否不加以分解面积
     */
    void addLandArea(Integer id, BigDecimal resolveArea, Boolean retain);

}
