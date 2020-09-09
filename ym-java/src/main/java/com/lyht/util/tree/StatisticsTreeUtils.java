package com.lyht.util.tree;

import com.lyht.business.pub.enums.IndexTypeEnums;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 统计汇总树形工具类
 * @Author: xzp
 * @Date: 2020/8/4 15:14
 **/
public class StatisticsTreeUtils {

    /*
     * @Description: 获取指标统计树形结构
     * @Author: xzp
     * @Date: 2020/8/4 10:36
     **/
    public List<Map> indexStatisticsCommonList;
    public List<Map> indexStatisticsList = new ArrayList<>();
    BigDecimal total, pivotalTotal, pivotalTemporary, pivotalPerpetual, reservoirTotal, reservoirSubmerge, reservoirInfluence, reservoirTemporary, townTotal;
    //总计,枢纽合计,枢纽临时,枢纽永久,水库合计,水库淹没,水库影响,水库临时,集镇
    String unit;

    static List<Map> childIndexStatistics = new ArrayList<>();

    public static List<Map> treeCashStatisticsList(List<Map> menuList, String pCode){
        for(Map mu: menuList){
            if(null != mu.get("pCode") && pCode.equals(mu.get("pCode").toString())){
                treeCashStatisticsList(menuList, mu.get("code").toString());
                childIndexStatistics.add(mu);
            }
        }
        return childIndexStatistics;
    }

    public List<Map> getIndexStatisticsList(List<Map> menu, String indexType, Integer type, boolean b, Map data) {
        this.indexStatisticsCommonList = menu;
        Map<String, Object> mapArr;
        for (Map m : menu) {
            if (null != m.get("pCode") && m.get("pCode").toString().equals("0")) {
                mapArr = new LinkedHashMap<>();
                mapArr.put("code", m.get("code"));
                mapArr.put("pCode", m.get("pCode"));
                mapArr.put("name", m.get("name"));
                mapArr.put("unit", m.get("unit"));
                total = BigDecimal.ZERO;
                pivotalTotal = BigDecimal.ZERO;
                pivotalTemporary = BigDecimal.ZERO;
                pivotalPerpetual = BigDecimal.ZERO;
                reservoirTotal = BigDecimal.ZERO;
                reservoirSubmerge = BigDecimal.ZERO;
                reservoirInfluence = BigDecimal.ZERO;
                reservoirTemporary = BigDecimal.ZERO;
                townTotal = BigDecimal.ZERO;
                List<Map> listMap = getIndexStatisticsChildrenList(m.get("code").toString(), indexType);
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.put("children", listMap);
                }
                BigDecimal pivotalTemporaryNew = pivotalTemporary.add((BigDecimal) m.get("pivotalTemporary"));
                BigDecimal pivotalPerpetualNew = pivotalPerpetual.add((BigDecimal) m.get("pivotalPerpetual"));
                BigDecimal reservoirSubmergeNew = reservoirSubmerge.add((BigDecimal) m.get("reservoirSubmerge"));
                BigDecimal reservoirInfluenceNew = reservoirInfluence.add((BigDecimal) m.get("reservoirInfluence"));
                BigDecimal reservoirTemporaryNew = reservoirTemporary.add((BigDecimal) m.get("reservoirTemporary"));
                BigDecimal townTotalNew = townTotal.add((BigDecimal) m.get("townTotal"));
                if(!indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_FAMILY.getKey()) && !indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_BUILDING.getKey())){
                    if(b && 2 == type && indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_LAND.getKey()) && null != data){
                        mapArr.put("total", data.get("total"));
                        mapArr.put("pivotalTotal", data.get("pivotalTotal"));
                        mapArr.put("pivotalTemporary", data.get("pivotalTemporary"));
                        mapArr.put("pivotalPerpetual", data.get("pivotalPerpetual"));
                        mapArr.put("reservoirTotal", data.get("reservoirTotal"));
                        mapArr.put("reservoirSubmerge", data.get("reservoirSubmerge"));
                        mapArr.put("reservoirInfluence", data.get("reservoirInfluence"));
                        mapArr.put("reservoirTemporary", data.get("reservoirTemporary"));
                        mapArr.put("townTotal", data.get("townTotal"));
                    }else {
                        mapArr.put("total", total.add((BigDecimal) m.get("total")));
                        mapArr.put("pivotalTotal", pivotalTotal.add((BigDecimal) m.get("pivotalTotal")));
                        mapArr.put("pivotalTemporary", pivotalTemporaryNew);
                        mapArr.put("pivotalPerpetual", pivotalPerpetualNew);
                        mapArr.put("reservoirTotal", reservoirTotal.add((BigDecimal) m.get("reservoirTotal")));
                        mapArr.put("reservoirSubmerge", reservoirSubmergeNew);
                        mapArr.put("reservoirInfluence", reservoirInfluenceNew);
                        mapArr.put("reservoirTemporary", reservoirTemporaryNew);
                        mapArr.put("townTotal", townTotalNew);
                    }

                }
                if(pivotalTemporaryNew.compareTo(BigDecimal.ZERO) == 1 || pivotalPerpetualNew.compareTo(BigDecimal.ZERO) == 1
                        || reservoirSubmergeNew.compareTo(BigDecimal.ZERO) == 1 || reservoirInfluenceNew.compareTo(BigDecimal.ZERO) == 1
                        || reservoirTemporaryNew.compareTo(BigDecimal.ZERO) == 1 || townTotalNew.compareTo(BigDecimal.ZERO) == 1){
                    indexStatisticsList.add(mapArr);
                }
            }
        }
        return indexStatisticsList;
    }

    public List<Map> getIndexStatisticsChildrenList(String code, String indexType) {
        List<Map> lists = new ArrayList<>();
        Map<String, Object> mapArr;
        for (Map m : indexStatisticsCommonList) {
            if (null != m.get("pCode") && code.equals(m.get("pCode").toString())) {
                mapArr = new LinkedHashMap<>();
                mapArr.put("code", m.get("code"));
                mapArr.put("pCode", m.get("pCode"));
                mapArr.put("name", m.get("name"));
                mapArr.put("unit", m.get("unit"));
                childIndexStatistics = new ArrayList<>();
                List<Map> list = treeCashStatisticsList(indexStatisticsCommonList, m.get("code").toString());
                BigDecimal totalChild = (BigDecimal) m.get("total"),
                        pivotalTotalChild = (BigDecimal) m.get("pivotalTotal"),
                        pivotalTemporaryChild = (BigDecimal) m.get("pivotalTemporary"),
                        pivotalPerpetualChild = (BigDecimal) m.get("pivotalPerpetual"),
                        reservoirTotalChild = (BigDecimal) m.get("reservoirTotal"),
                        reservoirSubmergeChild = (BigDecimal) m.get("reservoirSubmerge"),
                        reservoirInfluenceChild = (BigDecimal) m.get("reservoirInfluence"),
                        reservoirTemporaryChild = (BigDecimal) m.get("reservoirTemporary"),
                        townTotalChild = (BigDecimal) m.get("townTotal");
                int n = 0;
                for(Map s : list){
                    if(n == 0 && indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_BUILDING.getKey())){
                        unit = (String) s.get("unit");
                    }
                    if(indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_BUILDING.getKey()) && unit.equals(s.get("unit"))){
                        n++;
                    }
                    totalChild = totalChild.add((BigDecimal) s.get("total"));
                    pivotalTotalChild = pivotalTotalChild.add((BigDecimal) s.get("pivotalTotal"));
                    pivotalTemporaryChild = pivotalTemporaryChild.add((BigDecimal) s.get("pivotalTemporary"));
                    pivotalPerpetualChild = pivotalPerpetualChild.add((BigDecimal) s.get("pivotalPerpetual"));
                    reservoirTotalChild = reservoirTotalChild.add((BigDecimal) s.get("reservoirTotal"));
                    reservoirSubmergeChild = reservoirSubmergeChild.add((BigDecimal) s.get("reservoirSubmerge"));
                    reservoirInfluenceChild = reservoirInfluenceChild.add((BigDecimal) s.get("reservoirInfluence"));
                    reservoirTemporaryChild = reservoirTemporaryChild.add((BigDecimal) s.get("reservoirTemporary"));
                    townTotalChild = townTotalChild.add((BigDecimal) s.get("townTotal"));
                }
                if(!indexType.equals(IndexTypeEnums.INDEX_TYPE_ENUMS_BUILDING.getKey()) || null == list || n == list.size()){
                    total = total.add((BigDecimal) m.get("total"));
                    pivotalTotal = pivotalTotal.add((BigDecimal) m.get("pivotalTotal"));
                    pivotalTemporary = pivotalTemporary.add((BigDecimal) m.get("pivotalTemporary"));
                    pivotalPerpetual = pivotalPerpetual.add((BigDecimal) m.get("pivotalPerpetual"));
                    reservoirTotal = reservoirTotal.add((BigDecimal) m.get("reservoirTotal"));
                    reservoirSubmerge = reservoirSubmerge.add((BigDecimal) m.get("reservoirSubmerge"));
                    reservoirInfluence = reservoirInfluence.add((BigDecimal) m.get("reservoirInfluence"));
                    reservoirTemporary = reservoirTemporary.add((BigDecimal) m.get("reservoirTemporary"));
                    townTotal = townTotal.add((BigDecimal) m.get("townTotal"));
                    mapArr.put("total", totalChild);
                    mapArr.put("pivotalTotal", pivotalTotalChild);
                    mapArr.put("pivotalTemporary", pivotalTemporaryChild);
                    mapArr.put("pivotalPerpetual", pivotalPerpetualChild);
                    mapArr.put("reservoirTotal", reservoirTotalChild);
                    mapArr.put("reservoirSubmerge", reservoirSubmergeChild);
                    mapArr.put("reservoirInfluence", reservoirInfluenceChild);
                    mapArr.put("reservoirTemporary", reservoirTemporaryChild);
                    mapArr.put("townTotal", townTotalChild);
                }
                List<Map> listMap = getIndexStatisticsChildrenList(m.get("code").toString(), indexType);
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.put("children", listMap);
                }
                if(pivotalTemporaryChild.compareTo(BigDecimal.ZERO) == 1 || pivotalPerpetualChild.compareTo(BigDecimal.ZERO) == 1
                || reservoirSubmergeChild.compareTo(BigDecimal.ZERO) == 1 || reservoirInfluenceChild.compareTo(BigDecimal.ZERO) == 1
                || reservoirTemporaryChild.compareTo(BigDecimal.ZERO) == 1 || townTotalChild.compareTo(BigDecimal.ZERO) == 1){
                    lists.add(mapArr);
                }
            }
        }
        return lists;
    }

}
