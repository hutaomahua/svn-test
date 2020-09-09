package com.lyht.system.controller;

import com.alibaba.fastjson.JSON;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.pojo.SysRoleStaffRef;
import com.lyht.system.service.SysRoleStaffRefService;
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
@RequestMapping("/sysRoleStaffRef")
@Api(value="/sysRoleStaffRef", tags="角色人员关联表")
public class SysRoleStaffRefController {
    private Logger log = LoggerFactory.getLogger(SysRoleStaffRefController.class);

    @Autowired
    private SysRoleStaffRefService services;

    /**
     * 查询角色菜单关联表
     * @return
     */
    @PostMapping("/getRoleMenuRef")
    @ApiOperation(value = "根据角色内码查询角色人员关联信息", notes="查询")
    public LyhtResultBody<List<SysRoleStaffRef>> getRoleMenuRef(String roleNm){
            List<SysRoleStaffRef> sysRoleMenuRefs=null;
            try {
                String JsonRe = JSON.toJSONString(services.findRoleStaffRefByRoleNm(roleNm));
                sysRoleMenuRefs=JSON.parseArray(JsonRe,SysRoleStaffRef.class);
            } catch (Exception e) {
                log.error("获取菜单列表失败！",e);
            }
            return new LyhtResultBody<>(sysRoleMenuRefs);
    }

    /**
     * 添加角色人员关系
     * @param roleNm
     * @param staffNm
     * @return
     */
    @PostMapping("/addRoleMenu")
    @ApiOperation(value = "添加角色人员关系，多个人员内码用逗号“,”拼接", notes="添加")
    public LyhtResultBody<List<SysRoleStaffRef>> addRoleMenu(String roleNm, String staffNm){
        try {
            if(CommonUtil.getLength(roleNm)>0){
                services.setRoleStaffRef(roleNm,staffNm);
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
