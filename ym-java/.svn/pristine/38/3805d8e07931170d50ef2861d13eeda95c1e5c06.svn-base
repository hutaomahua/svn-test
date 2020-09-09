package com.lyht.business.pub.controller;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.service.IndexStatisticsService;
import com.lyht.business.pub.vo.IndexStatisticsParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Description: 指标统计汇总
 * @Author: xzp
 * @Date: 2020/8/4 14:57
 **/
@RestController
@RequestMapping("/index/statistics")
@Api(value = "/IndexStatistics", tags = "指标统计汇总")
public class IndexStatisticsController {

    @Autowired private IndexStatisticsService indexStatisticsService;

    @PostMapping("/getFileByIndexType")
    @ApiOperation(value = "根据指标获取文件信息", notes ="ownerNm:权属人内码,indexType:指标类型(family:家庭成员,houses:房屋,decoration:房屋装修,building:附属设施,trees:零星树木,land:土地),type:请求类型(1基础,2实施)")
    public LyhtResultBody getFileByIndexType(String ownerNm, String indexType, Integer type){
        if(StringUtils.isBlank(ownerNm) || StringUtils.isBlank(indexType) || null == type){
            throw new LyhtRuntimeException("请求参数不能为空");
        }
        return new LyhtResultBody(indexStatisticsService.getFileByIndexType(ownerNm, indexType, type));
    }

    @PostMapping("/getFileListByNm")
    @ApiOperation(value = "根据表名获取文件信息", notes ="根据表名获取文件信息")
    public LyhtResultBody getFileListByNm(String tableName, @RequestParam("nmList") List<String> nmList){
        if(StringUtils.isBlank(tableName) || null == nmList || nmList.isEmpty() || nmList.size() == 0){
            throw new LyhtRuntimeException("请求参数不能为空");
        }
        return new LyhtResultBody(indexStatisticsService.getFileListByNm(tableName, nmList));
    }

    @PostMapping("/getBasicsStatistics")
    @ApiOperation(value = "获取基础数据统计", notes = "获取基础数据统计(返回所有列，前端根据条件做筛选)\n" +
            "请求参数》regionList:行政区内码,indexType:指标类型(family:家庭成员,houses:房屋,decoration:房屋装修,building:附属设施,trees:零星树木,land:土地)\n" +
            "返回参数》[{code:code,pCode:父code,name:名称,unit:单位,total:总计,pivotalTotal:枢纽合计,pivotalTemporary:枢纽临时,pivotalPerpetual:枢纽永久," +
            "reservoirTotal:水库合计,reservoirSubmerge:水库淹没,reservoirInfluence:水库影响,reservoirTemporary:水库临时,townTotal:集镇,children:子级}]")
    public LyhtResultBody getBasicsStatistics(@RequestBody IndexStatisticsParamVO param){
        List<String> regionList = param.getRegionList(), indexList = param.getIndexType();
        if(null == indexList || indexList.isEmpty() || indexList.size() == 0){
            throw new LyhtRuntimeException("请求参数不符合标准");
        }
        if(null != regionList && !regionList.isEmpty() && regionList.size() > 0){
            regionList = regionList.stream().distinct().collect(Collectors.toList());
            List<String> newIndexList = indexList.stream().distinct().collect(Collectors.toList());
            Map data = new ConcurrentHashMap<>(newIndexList.size());
            for (String s : newIndexList){
                List<Map> list = indexStatisticsService.getBasicsStatistics(regionList, s);
                if(null != list){
                    data.put(s + "List", list);
                }
            }
            return new LyhtResultBody(data);
        }else {
            Map data = new ConcurrentHashMap<>(6);
            data.put("familyList", new ArrayList());
            data.put("housesList", new ArrayList());
            data.put("decorationList", new ArrayList());
            data.put("buildingList", new ArrayList());
            data.put("treesList", new ArrayList());
            data.put("landList", new ArrayList());
            return new LyhtResultBody(data);
        }
    }

    @PostMapping("/getImplementStatistics")
    @ApiOperation(value = "获取实施数据统计", notes = "获取实施数据统计(返回所有列，前端根据条件做筛选)\n" +
            "请求参数》regionList:行政区内码,indexType:指标类型(family:家庭成员,houses:房屋,decoration:房屋装修,building:附属设施,trees:零星树木,land:土地)\n" +
            "返回参数》[{code:code,pCode:父code,name:名称,unit:单位,total:总计,pivotalTotal:枢纽合计,pivotalTemporary:枢纽临时,pivotalPerpetual:枢纽永久," +
            "reservoirTotal:水库合计,reservoirSubmerge:水库淹没,reservoirInfluence:水库影响,reservoirTemporary:水库临时,townTotal:集镇,children:子级}]")
    public LyhtResultBody getImplementStatistics(@RequestBody IndexStatisticsParamVO param){
        List<String> regionList = param.getRegionList(), indexList = param.getIndexType();
        if(null == indexList || indexList.isEmpty() || indexList.size() == 0){
            throw new LyhtRuntimeException("请求参数不符合标准");
        }
        if(null != regionList && !regionList.isEmpty() && regionList.size() > 0){
            regionList = regionList.stream().distinct().collect(Collectors.toList());
            List<String> newIndexList = indexList.stream().distinct().collect(Collectors.toList());
            Map data = new ConcurrentHashMap<>(newIndexList.size());
            for (String s : newIndexList){
                List<Map> list = indexStatisticsService.getImplementStatistics(regionList, s);
                if(null != list){
                    data.put(s + "List", list);
                }
            }
            return new LyhtResultBody(data);
        }else {
            Map data = new ConcurrentHashMap<>(6);
            data.put("familyList", new ArrayList());
            data.put("housesList", new ArrayList());
            data.put("decorationList", new ArrayList());
            data.put("buildingList", new ArrayList());
            data.put("treesList", new ArrayList());
            data.put("landList", new ArrayList());
            return new LyhtResultBody(data);
        }
    }

    @PostMapping("/getRegionTrees")
    @ApiOperation(value = "获取行政区树形结构", notes = "获取行政区树形结构\n" +
            "请求参数》type:类型(1基础/2实施)" +
            "返回参数》[{code:城市编码,pCode:父级编码,name:名称,children:子级}]")
    public LyhtResultBody getRegionTrees(Integer type){
        if(null == type || (type != 1 && type != 2)){
            throw new LyhtRuntimeException("参数错误");
        }
        return new LyhtResultBody(indexStatisticsService.getRegionTrees(type));
    }

    @PostMapping("/getScopeTrees")
    @ApiOperation(value = "获取征地范围树形结构", notes = "获取征地范围树形结构\n" +
            "返回参数》[{code:城市编码,pCode:父级编码,name:名称,children:子级}]")
    public LyhtResultBody getScopeTrees(){
        return new LyhtResultBody(indexStatisticsService.getScopeTrees());
    }



}
