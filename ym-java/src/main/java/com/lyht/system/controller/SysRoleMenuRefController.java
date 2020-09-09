package com.lyht.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.pojo.SysRoleMenuRef;
import com.lyht.system.service.SysRoleMenuRefService;
import com.lyht.util.CommonUtil;

import java.util.List;

@RestController
@RequestMapping("/sysRoleMenuRef")
@Api(value="/sysRoleMenuRef", tags="角色菜单关联表")
public class SysRoleMenuRefController {
    private Logger log = LoggerFactory.getLogger(SysRoleMenuRefController.class);

    @Autowired
    private SysRoleMenuRefService services;

    /**
     * 查询角色菜单关联表
     * @return
     */
    @PostMapping("/getRoleMenuRef")
    @ApiOperation(value = "根据角色内码查询角色菜单关联信息", notes="查询")
    public LyhtResultBody<List<SysRoleMenuRef>> getRoleMenuRef(String roleNm){
        List<SysRoleMenuRef> list = null;
        try {
            list = services.findRoleMenuRefByRoleNm(roleNm);
        } catch (Exception e) {
            log.error("获取菜单列表失败！",e);
        }
        return new LyhtResultBody<>(list);
    }

    /**
     * 添加角色菜单关系
     * @param roleNm
     * @param menuNm
     * @return
     */
    @PostMapping("/addRoleMenu")
    @ApiOperation(value = "添加角色菜单关系，多个菜单内码用逗号“,”拼接", notes="添加")
    public LyhtResultBody<SysRoleMenuRef> addRoleMenu(String roleNm, String menuNm){
        try {
            if(CommonUtil.getLength(roleNm)>0){
                services.setRoleMenuRef(roleNm,menuNm);
            }else{
                throw  new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
            }
        } catch (Exception e) {
            log.error("添加角色菜单失败！",e);
            throw  new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
        }
        return new LyhtResultBody<>();
    }
}
