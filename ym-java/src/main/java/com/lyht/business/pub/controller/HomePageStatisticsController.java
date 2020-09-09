package com.lyht.business.pub.controller;

import com.lyht.base.common.vo.response.LyhtResultBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 统计
 * @Author: xzp
 * @Date: 2020/8/4 11:48
 **/
@RestController
@RequestMapping("/home/page/statistics")
@Api(value = "/HomePageStatistics", tags = "首页统计")
public class HomePageStatisticsController {

    /*@PostMapping("/movingResettlement")
    @ApiOperation(value = "获取搬迁安置统计数据", notes = "获取搬迁安置统计数据\n" +
            "返回参数》accomplish_household:已完成(户),accomplish_person:已完成(人)," +
            "sum_household:共计(户),sum_person:共计(人)")
    public LyhtResultBody movingResettlement(){
        Map data = new HashMap<>(4);
        data.put("accomplish_household", 1000);//已完成(户)
        data.put("accomplish_person", 4122);//已完成(人)
        data.put("sum_household", 2444);//共计(户)
        data.put("sum_person", 9297);//共计(人)
        return new LyhtResultBody(data);
    }*/

    /*@PostMapping("/productionArrangement")
    @ApiOperation(value = "获取生产安置统计数据", notes = "获取生产安置统计数据\n" +
            "返回参数》accomplish_person:已完成(人),sum_person:共计(人)")
    public LyhtResultBody productionArrangement (){
        Map data = new HashMap<>(2);
        data.put("accomplish_person", 1132);//已完成(人)
        data.put("sum_person", 9297);//共计(人)
        return new LyhtResultBody(data);
    }*/

    /*@PostMapping("/settlements")
    @ApiOperation(value = "获取安置点建设情况统计数据", notes = "获取安置点建设情况统计数据\n" +
            "返回参数》(这个没看懂先这样)settlements1:已完成农村集中安置点,settlements2:集镇安置点,settlements3:农村集中安置点")
    public LyhtResultBody settlements (){
        Map data = new HashMap<>(3);
        data.put("settlements1", 3);//已完成农村集中安置点
        data.put("settlements2", 2);//集镇安置点
        data.put("settlements3", 8);//农村集中安置点
        return new LyhtResultBody(data);
    }*/

}
