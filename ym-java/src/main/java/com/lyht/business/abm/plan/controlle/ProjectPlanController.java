package com.lyht.business.abm.plan.controlle;

import com.lyht.Constants;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.dao.YearAnnualPlanDao;
import com.lyht.business.abm.plan.entity.ProjectPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;
import com.lyht.business.abm.plan.service.ProjectPlanAdjustService;
import com.lyht.business.abm.plan.service.ProjectPlanService;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.system.pojo.SysStaff;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/projectplan")
@Api(value = "工程计划编制", tags = "工程计划编制")
public class ProjectPlanController {

    @Autowired
    ProjectPlanAdjustService projectPlanAdjustService;
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    private PubRegionDao pubRegionDao;
    @Autowired
    YearAnnualPlanDao yearAnnualPlanDao;
    @Autowired
    ProjectPlanDao projectPlanDao;

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", notes = "保存")
    public LyhtResultBody<ProjectPlanEntity> saveYearAnnualPlan(ProjectPlanEntity planEntity,HttpServletRequest request) {
    	Object obj1 = request.getSession().getAttribute(Constants.SESSION_STAFF);//获取session中的当前登录的账户信息
		SysStaff sysStaff = (SysStaff) obj1;
		 Date date = new Date();   
		  
		 Timestamp timestamp = new Timestamp(date.getTime()); //2013-01-14 22:45:36.484  
		
		if(planEntity.getId()==null) 
		{
			PubRegionEntity parent = pubRegionDao.findByCityCode(planEntity.getRegion());
			
			planEntity.setRegionName(parent.getMergerName());
			if(sysStaff!=null) 
			{
				planEntity.setCreateStaff(sysStaff.getStaffName());//创建人
			}

			
		}else {
			PubRegionEntity parent = pubRegionDao.findByCityCode(planEntity.getRegion());
			
			planEntity.setRegionName(parent.getMergerName());
			if(sysStaff!=null) 
			{
				planEntity.setUpdateStaff(sysStaff==null?sysStaff.getStaffName():"测试接口");//修改人

			}
			if(planEntity.getState()!=null)
			{
				if(planEntity.getState().matches("[\u4E00-\u9FA5]+"))
        		{
        			List<Map> list=yearAnnualPlanDao.getCode(planEntity.getState());
        			if(list.size()>0)
        			{
        				planEntity.setState(list.get(0).get("code").toString());

        			}

        		}
			}
			/*planEntity.setUpdateTime(timestamp);*/
		}
		ProjectPlanEntity resultSet = projectPlanService.saveYearAnnualPlan(planEntity);
        return new LyhtResultBody<>(resultSet);
    }

    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询列表 条件查询", notes = "查询列表 条件查询")
    public LyhtResultBody<List<ProjectPlanEntity>> getList(LyhtPageVO pageVO,  String region,String area,String taskName,String nm) {
        if(null!=region&&!"".equals(region))
        {
        	List<Map> list=projectPlanDao.regionName(region);
        	region=list.get(0).get("merger_name").toString();
        }
    	
    	return projectPlanService.getList(pageVO,region,area,taskName,nm);
    }

    @ApiOperation(value = "按id删除", notes = "删除")
    @PostMapping("/delete")
    public LyhtResultBody<Integer> delete(Integer id) {
        return projectPlanService.delete(id);
    }

    @GetMapping(value = "/details")
    @ApiOperation(value = "按id查询", notes = "详情")
    public LyhtResultBody<List<ProjectPlanEntity>> details(LyhtPageVO pageVO,String id) {
        return projectPlanService.getDetail(pageVO,id);
    }

    /**
     * 批量删除
     * @param ids
     * @return3
     */
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
    @GetMapping("/batch")
    public LyhtResultBody<String> batchDel(String ids) {
        return projectPlanService.batchDel(ids);
    }

    @PostMapping(value = "/getNm")
    @ApiOperation(value = "调整记录 条件查询", notes = "调整记录  按nm查询")
    public LyhtResultBody<List<Map>> getNm(String nm) {
        List<Map> list=projectPlanAdjustService.getNm(nm);
        return new LyhtResultBody<>(list);
    }

}
