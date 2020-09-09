package com.lyht.business.abm.land.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.bean.RequisitionPlanDetail;
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.business.abm.land.entity.LatePeopleEntity;
import com.lyht.business.abm.land.entity.RequisitionPlanEntity;
import com.lyht.business.abm.land.service.LatePeopleService;
import com.lyht.util.ExportExcelWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/late/people")
@Api(value = "后期扶持人口(人)", tags = "后期扶持人口（人）")
public class LatePeopleController {

    private Logger log = LoggerFactory.getLogger(LatePeopleService.class);
  
    
    @Autowired
    LatePeopleService latepeopleservice;
    @Autowired
   	private PubRegionDao pubRegionDao;
    @Autowired
    ProjectPlanDao projectPlanDao;

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", notes = "保存")
    public LyhtResultBody<LatePeopleEntity> saveYearAnnualPlan(LatePeopleEntity planEntity, HttpServletRequest request) {
	PubRegionEntity parent = pubRegionDao.findByCityCode(planEntity.getRegion());
		
    	planEntity.setRegionName(parent.getMergerName());
    	LatePeopleEntity resultSet = latepeopleservice.saveYearAnnualPlan(planEntity);
        return new LyhtResultBody<>(resultSet);
    }

    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询列表 条件查询", notes = "查询列表 条件查询")
    public LyhtResultBody<List<LatePeopleEntity>> getList(LyhtPageVO pageVO, String region, String name) {
    	if(null!=region&&!"".equals(region))
        {
        	List<Map> list=projectPlanDao.regionName(region);
        	if(list.size()>0)
        	{
            	region=list.get(0).get("merger_name").toString();

        	}
        }
    	return latepeopleservice.getList(pageVO, region,name);
    }

    @ApiOperation(value = "按id删除", notes = "删除")
    @PostMapping("/delete")
    public LyhtResultBody<Integer> delete(Integer id) {
        return latepeopleservice.delete(id);
    }


  
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
    @GetMapping("/batch")
    public LyhtResultBody<String> batchDel(String ids) {
        return latepeopleservice.batchDel(ids);
    }


   

   
   
    
   
}
