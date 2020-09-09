package com.lyht.business.abm.removal.controller;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.removal.entity.AbmFamilyEntity;
import com.lyht.business.abm.removal.entity.MoveIdentity;
import com.lyht.business.abm.removal.service.AbmFamilyService;
import com.lyht.business.abm.removal.service.EnterpriseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/enterprise")
@Api(value = "实物指标查询", tags = "实物指标查询")
public class EnterpriseController {
    @Autowired
    EnterpriseService service;
    
    
    @PostMapping(value = "/getQyList")
    @ApiOperation(value = "企业", notes = "企业")
    public LyhtResultBody<List<Map>> getList(LyhtPageVO pageVO, String region,String scope,String name,String nm) {
        return service.getList(pageVO,region,scope,name,nm);
    }
    
    @PostMapping(value = "/getSheBei")
    @ApiOperation(value = "设备  ", notes = "设备  ")
    public LyhtResultBody<List<Map>> getSheBei(LyhtPageVO pageVO, String nm) {
        return service.getSheBei(pageVO,nm);
    }
    
    @PostMapping(value = "/getWuzi")
    @ApiOperation(value = "物资及存货   ", notes = "物资及存货   ")
    public LyhtResultBody<List<Map>> getWuzi(LyhtPageVO pageVO, String nm) {
        return service.getWuzi(pageVO,nm);
    }
    
    @PostMapping(value = "/getFw")
    @ApiOperation(value = "房屋   ", notes = "房屋   ")
    public LyhtResultBody<List<Map>> getFw(LyhtPageVO pageVO, String nm) {
        return service.getFw(pageVO,nm);
    }
    
    @PostMapping(value = "/getFuShu")
    @ApiOperation(value = "附属建筑物   ", notes = "附属建筑物   ")
    public LyhtResultBody<List<Map>> getFuShu(LyhtPageVO pageVO, String nm) {
        return service.getFuShu(pageVO,nm);
    }
    
    @PostMapping(value = "/getGjw")
    @ApiOperation(value = "企业构筑物   ", notes = "企业构筑物   ")
    public LyhtResultBody<List<Map>> getGjw(LyhtPageVO pageVO, String nm) {
        return service.getGjw(pageVO,nm);
    }
    
    @PostMapping(value = "/getGd")
    @ApiOperation(value = "管道、线路  ", notes = "管道、线路   ")
    public LyhtResultBody<List<Map>> getGd(LyhtPageVO pageVO, String nm) {
        return service.getGd(pageVO,nm);
    }
    
    @PostMapping(value = "/getCaiWu")
    @ApiOperation(value = "财务  ", notes = "财务   ")
    public LyhtResultBody<List<Map>> getCaiWu(LyhtPageVO pageVO, String nm) {
        return service.getCaiWu(pageVO,nm);
    }
}
