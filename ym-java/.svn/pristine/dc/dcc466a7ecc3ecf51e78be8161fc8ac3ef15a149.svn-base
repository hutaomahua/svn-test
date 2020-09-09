package com.lyht.system.controller;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.pojo.SysRolePartitionRef;
import com.lyht.system.service.SysRolePartitionService;
import com.lyht.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysRolePartition")
@Api(value="/sysRolePartition", tags="角色人员关联表")
public class SysRolePartitionRefController {
    private Logger log = LoggerFactory.getLogger(SysRolePartitionRefController.class);

    @Autowired
    private SysRolePartitionService services;

    /**
     * 查询角色菜单关联表
     * @return
     */
    @PostMapping("/getRolePartitionRef")
    @ApiOperation(value = "根据角色内码查询角色人员关联信息", notes="查询")
    public LyhtResultBody<List<SysRolePartitionRef>> getRolePartitionRef(String roleNm){
            List<SysRolePartitionRef> sysRolePartitionRefs=null;
            try {
                String JsonRe = JSON.toJSONString(services.findRolePartitionRefByRoleNm(roleNm));
                sysRolePartitionRefs=JSON.parseArray(JsonRe,SysRolePartitionRef.class);
            } catch (Exception e) {
                log.error("获取菜单列表失败！",e);
            }
            return new LyhtResultBody<>(sysRolePartitionRefs);
    }

    /**
     * 添加角色人员关系
     * @param roleNm
     * @param partitionNm
     * @return
     */
    @PostMapping("/addRolePartition")
    @ApiOperation(value = "添加角色人员关系，多个人员内码用逗号“,”拼接", notes="添加")
    public LyhtResultBody<List<SysRolePartitionRef>> addRoleMenu(String roleNm, String partitionNm){
        try {
            if(CommonUtil.getLength(roleNm)>0){
                services.setRolePartitionRef(roleNm,partitionNm);
            }else{
                throw  new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
            }
        } catch (Exception e) {
            log.error("添加角色人员失败！",e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
        }
        return new LyhtResultBody<>();
    }
}
