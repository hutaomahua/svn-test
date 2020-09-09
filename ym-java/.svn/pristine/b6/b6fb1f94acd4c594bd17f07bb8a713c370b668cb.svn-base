package com.lyht.util.tree;

import com.lyht.business.info.vo.InfoMoveAggregateTreeVO;
import com.lyht.business.pub.enums.IndexTypeEnums;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 统计行政区树形工具类
 * @Author: xzp
 * @Date: 2020/8/4 15:14
 **/
public class RegionTreeUtils {

    /*
     * @Description: 获取指标统计树形结构
     * @Author: xzp
     * @Date: 2020/8/4 10:36
     **/
    public List<InfoMoveAggregateTreeVO> indexStatisticsCommonList;
    public List<InfoMoveAggregateTreeVO> indexStatisticsList = new ArrayList<>();
    BigDecimal households, population, reservoirTotalHouseholds, reservoirTotalPopulation, pivotTotalHouseholds, pivotTotalPopulation, newTownHouseholds, newTownPopulation;
    //总户数,总人口,枢纽工程建设区--合计(户数),枢纽工程建设区--合计(人口),水库淹没影响区--合计(户数),水库淹没影响区--合计(人口),集镇新址占地区(户数),集镇新址占地区(人口)

    static List<InfoMoveAggregateTreeVO> childIndexStatistics = new ArrayList<>();

    public static List<InfoMoveAggregateTreeVO> treeCashStatisticsList(List<InfoMoveAggregateTreeVO> menuList, String pCode){
        for(InfoMoveAggregateTreeVO mu: menuList){
            if(null != mu.getParentCode() && pCode.equals(mu.getParentCode())){
                treeCashStatisticsList(menuList, mu.getCityCode());
                childIndexStatistics.add(mu);
            }
        }
        return childIndexStatistics;
    }

    public List<InfoMoveAggregateTreeVO> getRegionTreeList(List<InfoMoveAggregateTreeVO> menu, List<InfoMoveAggregateTreeVO> menu2) {
        this.indexStatisticsCommonList = menu;
        for (InfoMoveAggregateTreeVO m : menu) {
            if (null != m.getParentCode() && m.getLevel().equals("3")) {
                InfoMoveAggregateTreeVO mapArr = m;
                households = BigDecimal.ZERO;
                population = BigDecimal.ZERO;
                reservoirTotalHouseholds = BigDecimal.ZERO;
                reservoirTotalPopulation = BigDecimal.ZERO;
                pivotTotalHouseholds = BigDecimal.ZERO;
                pivotTotalPopulation = BigDecimal.ZERO;
                newTownHouseholds = BigDecimal.ZERO;
                newTownPopulation = BigDecimal.ZERO;
                List<InfoMoveAggregateTreeVO> listMap = getRegionTreeChildrenList(m.getCityCode(), menu2);
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.setChildren(listMap);
                }
                mapArr.setHouseholds(households.add(m.getHouseholds()));
                mapArr.setPopulation(population.add(m.getPopulation()));
                mapArr.setReservoirTotalHouseholds(reservoirTotalHouseholds.add(m.getReservoirTotalHouseholds()));
                mapArr.setReservoirTotalPopulation(reservoirTotalPopulation.add(m.getReservoirTotalPopulation()));
                mapArr.setPivotTotalHouseholds(pivotTotalHouseholds.add(m.getPivotTotalHouseholds()));
                mapArr.setPivotTotalPopulation(pivotTotalPopulation.add(m.getPivotTotalPopulation()));
                mapArr.setNewTownHouseholds(newTownHouseholds.add(m.getNewTownHouseholds()));
                mapArr.setNewTownPopulation(newTownPopulation.add(m.getNewTownPopulation()));
                mapArr.setHouseholdsProgramme(new BigDecimal(2442));
                mapArr.setPopulationProgramme(new BigDecimal(9297));
                mapArr.setReservoirTotalHouseholdsProgramme(new BigDecimal(140));
                mapArr.setReservoirTotalPopulationProgramme(new BigDecimal(550));
                mapArr.setPivotTotalHouseholdsProgramme(new BigDecimal(2253));
                mapArr.setPivotTotalPopulationProgramme(new BigDecimal(8561));
                mapArr.setNewTownHouseholdsProgramme(new BigDecimal(49));
                mapArr.setNewTownPopulationProgramme(new BigDecimal(186));
                if(households.add(m.getHouseholds()).compareTo(BigDecimal.ZERO) == 1 || population.add(m.getPopulation()).compareTo(BigDecimal.ZERO) == 1){
                    indexStatisticsList.add(mapArr);
                }
            }
        }
        return indexStatisticsList;
    }

    public List<InfoMoveAggregateTreeVO> getRegionTreeChildrenList(String code, List<InfoMoveAggregateTreeVO> menu2) {
        List<InfoMoveAggregateTreeVO> lists = new ArrayList<>();
        for (InfoMoveAggregateTreeVO m : indexStatisticsCommonList) {
            if (null != m.getParentCode() && code.equals(m.getParentCode())) {
                InfoMoveAggregateTreeVO mapArr = m;
                childIndexStatistics = new ArrayList<>();
                List<InfoMoveAggregateTreeVO> list = treeCashStatisticsList(indexStatisticsCommonList, m.getCityCode());
                BigDecimal householdsChild = m.getHouseholds(),
                        populationChild = m.getPopulation(),
                        reservoirTotalHouseholdsChild = m.getReservoirTotalHouseholds(),
                        reservoirTotalPopulationChild = m.getReservoirTotalPopulation(),
                        pivotTotalHouseholdsChild = m.getPivotTotalHouseholds(),
                        pivotTotalPopulationChild = m.getPivotTotalPopulation(),
                        newTownHouseholdsChild = m.getNewTownHouseholds(),
                        newTownPopulationChild = m.getNewTownPopulation();
                for(InfoMoveAggregateTreeVO s : list){
                    householdsChild = householdsChild.add(s.getHouseholds());
                    populationChild = populationChild.add(s.getPopulation());
                    reservoirTotalHouseholdsChild = reservoirTotalHouseholdsChild.add(s.getReservoirTotalHouseholds());
                    reservoirTotalPopulationChild = reservoirTotalPopulationChild.add(s.getReservoirTotalPopulation());
                    pivotTotalHouseholdsChild = pivotTotalHouseholdsChild.add(s.getPivotTotalHouseholds());
                    pivotTotalPopulationChild = pivotTotalPopulationChild.add(s.getPivotTotalPopulation());
                    newTownHouseholdsChild = newTownHouseholdsChild.add(s.getNewTownHouseholds());
                    newTownPopulationChild = newTownPopulationChild.add(s.getNewTownPopulation());
                }
                households = households.add(m.getHouseholds());
                population = population.add(m.getPopulation());
                reservoirTotalHouseholds = reservoirTotalHouseholds.add(m.getReservoirTotalHouseholds());
                reservoirTotalPopulation = reservoirTotalPopulation.add(m.getReservoirTotalPopulation());
                pivotTotalHouseholds = pivotTotalHouseholds.add(m.getPivotTotalHouseholds());
                pivotTotalPopulation = pivotTotalPopulation.add(m.getPivotTotalPopulation());
                newTownHouseholds = newTownHouseholds.add(m.getNewTownHouseholds());
                newTownPopulation = newTownPopulation.add(m.getNewTownPopulation());
                mapArr.setHouseholds(householdsChild);
                mapArr.setPopulation(populationChild);
                mapArr.setReservoirTotalHouseholds(reservoirTotalHouseholdsChild);
                mapArr.setReservoirTotalPopulation(reservoirTotalPopulationChild);
                mapArr.setPivotTotalHouseholds(pivotTotalHouseholdsChild);
                mapArr.setPivotTotalPopulation(pivotTotalPopulationChild);
                mapArr.setNewTownHouseholds(newTownHouseholdsChild);
                mapArr.setNewTownPopulation(newTownPopulationChild);
                mapArr.setParentCodes(getParentCategoryObject(m.getCityCode(), m.getParentCodes()));
                for(InfoMoveAggregateTreeVO ss : menu2){
                    if(mapArr.getCityCode().equals(ss.getCityCode())){
                        mapArr.setHouseholdsProgramme(ss.getHouseholdsProgramme());
                        mapArr.setPopulationProgramme(ss.getPopulationProgramme());
                        mapArr.setReservoirTotalHouseholdsProgramme(ss.getReservoirTotalHouseholdsProgramme());
                        mapArr.setReservoirTotalPopulationProgramme(ss.getReservoirTotalPopulationProgramme());
                        mapArr.setPivotTotalHouseholdsProgramme(ss.getPivotTotalHouseholdsProgramme());
                        mapArr.setPivotTotalPopulationProgramme(ss.getPivotTotalPopulationProgramme());
                        mapArr.setNewTownHouseholdsProgramme(ss.getNewTownHouseholdsProgramme());
                        mapArr.setNewTownPopulationProgramme(ss.getNewTownPopulationProgramme());
                    }
                }
                List<InfoMoveAggregateTreeVO> listMap = getRegionTreeChildrenList(m.getCityCode(), menu2);
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.setChildren(listMap);
                }
                if(householdsChild.compareTo(BigDecimal.ZERO) == 1 || populationChild.compareTo(BigDecimal.ZERO) == 1){
                    lists.add(mapArr);
                }
            }
        }
        return lists;
    }

    public List<String> getParentCategoryObject(String code, List<String> list){
        if(null == list){ list = new ArrayList<>(); }
        if(!"E06A0AEF47".equals(code)) {
            for (InfoMoveAggregateTreeVO s : commonList) {
                if(s.getCityCode().equals(code)){
                    list.add(s.getParentCode());
                    list.addAll(getParentCategoryObject(s.getParentCode(), list));
                }
            }
        }
        list = list.stream().distinct().collect(Collectors.toList());
        return list;
    }

    public List<InfoMoveAggregateTreeVO> commonList;
    public List<InfoMoveAggregateTreeVO> statisticsList = new ArrayList<>();

    public List<InfoMoveAggregateTreeVO> getRegionTree(List<InfoMoveAggregateTreeVO> menu) {
        this.commonList = menu;
        for (InfoMoveAggregateTreeVO m : menu) {
            if (null != m.getParentCode() && m.getCityCode().equals("E06A0AEF47")) {
                InfoMoveAggregateTreeVO mapArr = m;
                List<InfoMoveAggregateTreeVO> listMap = getRegionChildrenList(m.getCityCode());
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.setChildren(listMap);
                }
                statisticsList.add(mapArr);
            }
        }
        return statisticsList;
    }

    public List<InfoMoveAggregateTreeVO> getRegionChildrenList(String code) {
        List<InfoMoveAggregateTreeVO> lists = new ArrayList<>();
        for (InfoMoveAggregateTreeVO m : commonList) {
            if (null != m.getParentCode() && code.equals(m.getParentCode())) {
                InfoMoveAggregateTreeVO mapArr = m;
                mapArr.setParentCodes(getParentCategoryObject(m.getCityCode(), m.getParentCodes()));
                List<InfoMoveAggregateTreeVO> listMap = getRegionChildrenList(m.getCityCode());
                if(null != listMap && !listMap.isEmpty() && listMap.size() > 0){
                    mapArr.setChildren(listMap);
                }
                lists.add(mapArr);
            }
        }
        return lists;
    }

}
