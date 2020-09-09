package com.lyht.business.abm.household.controller;

import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.household.service.AbmMergeOwnerService;
import com.lyht.business.abm.household.vo.MergeOwnerFamilyKeyValueVO;
import com.lyht.business.abm.household.vo.MergeOwnerKeyValueVO;
import com.lyht.business.abm.household.vo.MergeParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: xzp
 * @Date: 2020/8/20 14:41
 **/
@Api(value = "权属人合并", tags = "权属人合并")
@RequestMapping(value = "/abm/merge/owner")
@RestController
public class AbmMergeOwnerController {

    @Autowired private AbmMergeOwnerService abmMergeOwnerService;

    @ApiOperation(value = "权属人合并(保存)", notes = "权属人合并(保存)")
    @PostMapping(value = "/save")
    public LyhtResultBody save(@RequestBody MergeParamVO param){
        String ownerNm = param.getOwnerNm();
        List<MergeOwnerKeyValueVO> list = param.getList();
        List<MergeOwnerFamilyKeyValueVO> familyList = param.getFamilyList();
        if(StringUtils.isBlank(ownerNm) || null == list || list.isEmpty() || list.size() == 0 || null == familyList || familyList.isEmpty() || familyList.size() == 0){
            throw new LyhtRuntimeException("参数不能为空");
        }
        abmMergeOwnerService.save(ownerNm, list, familyList);
        return new LyhtResultBody();
    }

    @ApiOperation(value = "获取家庭人口", notes = "获取家庭人口")
    @PostMapping(value = "/getFamilyList")
    public LyhtResultBody getFamilyList(@RequestParam("nm") List<String> nm){
        if(null == nm || nm.isEmpty() || nm.size() == 0){
            throw new LyhtRuntimeException("参数不能为空");
        }
        return new LyhtResultBody(abmMergeOwnerService.getFamilyList(nm));
    }



}
