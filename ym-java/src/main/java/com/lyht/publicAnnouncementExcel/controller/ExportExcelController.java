package com.lyht.publicAnnouncementExcel.controller;

import com.lyht.publicAnnouncementExcel.service.ExportExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

/*
* @author ChengLiangge
* @time 2020/5/12
* @description:导出公示表excel
* */
@RestController
@RequestMapping(value = "/publicAnnouncementExcel")
@Api(value = "公示表", tags = "公示表")
public class ExportExcelController {
    @Autowired
    private ExportExcelService exportExcelService;

    @GetMapping("/exportRelocation")
    @ApiOperation(value = "按查询条件导出搬迁安置人口核定公示表Excel", notes = "Excel导出")
    @ApiImplicitParams({@ApiImplicitParam(name = "nm", paramType = "query", required = true,value = "t_abm_publicity表中字段nm"),
            @ApiImplicitParam(name = "region", paramType = "query", required = false,value = "行政区"),
            @ApiImplicitParam(name = "start_time", paramType = "query", required = false,value = "公示开始时间"),
            @ApiImplicitParam(name = "end_time", paramType = "query", required = false,value = "公示结束时间")})
    public void exportRelocationExcel(HttpServletResponse response, @RequestParam(value = "nm") String nm,@RequestParam(value = "region") String region,
                                      @RequestParam(value = "start_time") String start_time,@RequestParam(value = "end_time") String end_time) {

         exportExcelService.exportRelocation(response,nm,start_time,end_time,region);

    }

    @GetMapping("/exportProduce")
    @ApiOperation(value = "按查询条件导出生产安置人口核定公示表Excel", notes = "Excel导出")
    @ApiImplicitParams({@ApiImplicitParam(name = "nm", paramType = "query", required = true,value = "t_abm_publicity表中字段nm"),
            @ApiImplicitParam(name = "region", paramType = "query", required = false,value = "行政区"),
            @ApiImplicitParam(name = "start_time", paramType = "query", required = false,value = "公示开始时间"),
            @ApiImplicitParam(name = "end_time", paramType = "query", required = false,value = "公示结束时间")})
    public void exportProduceExcel(HttpServletResponse response, @RequestParam(value = "nm") String nm,@RequestParam(value = "region") String region,
                                   @RequestParam(value = "start_time") String start_time,@RequestParam(value = "end_time") String end_time) {
        exportExcelService.exportProduce(response,nm,start_time,end_time,region);

    }

    @GetMapping("/exportResettlement")
    @ApiOperation(value = "按查询条件导出移民安置人口核定公示表Excel", notes = "Excel导出")
    @ApiImplicitParams({@ApiImplicitParam(name = "nm", paramType = "query", required = true,value = "t_abm_publicity表中字段nm"),
            @ApiImplicitParam(name = "region", paramType = "query", required = false,value = "行政区"),
            @ApiImplicitParam(name = "start_time", paramType = "query", required = false,value = "公示开始时间"),
            @ApiImplicitParam(name = "end_time", paramType = "query", required = false,value = "公示结束时间")})
    public void exportResettlementExcel(HttpServletResponse response, @RequestParam(value = "nm") String nm,@RequestParam(value = "region") String region,
                                        @RequestParam(value = "start_time") String start_time,@RequestParam(value = "end_time") String end_time) {
        exportExcelService.exportResettlement(response,nm,start_time,end_time,region);

    }

    @GetMapping("/exportPhysicalIndex")
    @ApiOperation(value = "按查询条件导出实物指标公示表Excel", notes = "Excel导出")
    @ApiImplicitParams({@ApiImplicitParam(name = "nm", paramType = "query", required = true,value = "t_abm_publicity表中字段nm"),
                        @ApiImplicitParam(name = "code", paramType = "query", required = false,value = "编号"),
                        @ApiImplicitParam(name = "start_time", paramType = "query", required = false,value = "公示开始时间"),
                        @ApiImplicitParam(name = "end_time", paramType = "query", required = false,value = "公示结束时间")})
    public void exportPhysicalIndexExcel(HttpServletResponse response, @RequestParam(value = "nm") String nm,@RequestParam(value = "code") String code,
                                         @RequestParam(value = "start_time") String start_time,@RequestParam(value = "end_time") String end_time) {
        exportExcelService.exportPhysicalIndex(response,nm,code,start_time,end_time);

    }
    
    @GetMapping("/exportPhysicalIndexLand")
    @ApiOperation(value = "按查询条件导出实物指标土地公示表Excel", notes = "Excel导出")
    public void exportPhysicalIndexExcelLand(HttpServletResponse response, @RequestParam(value = "nm") String nm,@RequestParam(value = "code") String code,
                                         @RequestParam(value = "start_time") String start_time,@RequestParam(value = "end_time") String end_time) {
        exportExcelService.exportPhysicalIndexLand(response,nm,code,start_time,end_time);

    }


}
