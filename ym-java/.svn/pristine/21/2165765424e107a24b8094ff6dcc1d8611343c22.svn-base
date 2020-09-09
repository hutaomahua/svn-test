package com.lyht.business.abm.plan.controlle;

import com.lyht.Constants;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.entity.ProjectPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
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

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/projectplanlog")
@Api(value = "工程计划编制记录", tags = "工程计划编制记录")
public class ProjectPlanAdjustController {

    @Autowired
    ProjectPlanAdjustService projectPlanAdjustService;
    @Autowired
    ProjectPlanService projectPlanService;
    @Autowired
    private PubRegionDao pubRegionDao;


  

    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询列表 条件查询", notes = "调整台帐")
    public LyhtResultBody<List<Map>> getList(LyhtPageVO pageVO,  String region,String area,String taskName) {
        return projectPlanAdjustService.getList(pageVO,region,area,taskName);
    }
    
    @PostMapping(value = "/geLogtList")
    @ApiOperation(value = "查询列表 条件查询", notes = "调整日志")
    public LyhtResultBody<List<Map>> getLogList(LyhtPageVO pageVO,  String region,String area,String taskName) {
        return projectPlanAdjustService.getLogList(pageVO,region,area,taskName);
    }

  
    
    @ApiOperation(value = "按id查询", notes = "详情")
    @GetMapping("/details")
    public List<ProjectPlanAdjustEntity> details(Integer id) {
        return projectPlanAdjustService.getDetails(id);
    }

    @ApiOperation(value = "调整前后数据", notes = "nm查询")
    @GetMapping("/getSuccessively")
    public LyhtResultBody<List<Map>> getSuccessively(String nm,String id) {
        List<Map> list=null;

        list=projectPlanAdjustService.getSuccessively(nm,id);
        return new LyhtResultBody<>(list);


    }
    @PostMapping(value = "/tzjh")
    @ApiOperation(value = "调整计划cause(调整原因)type（调整计划类别）", notes = "调整计划-带1的调整前数据")
    public LyhtResultBody<ProjectPlanEntity> saveYearAnnualPlan(ProjectPlanEntity projectPlanEntity, HttpServletRequest request, String cause, String type) {

        Object obj1 = request.getSession().getAttribute(Constants.SESSION_STAFF);//获取session中的当前登录的账户信息
        SysStaff sysStaff = (SysStaff) obj1;
        Date date = new Date();
        PubRegionEntity parent = pubRegionDao.findByCityCode(projectPlanEntity.getRegion());

        projectPlanEntity.setRegionName(parent.getMergerName());
        Timestamp timestamp = new Timestamp(date.getTime()); //2013-01-14 22:45:36.484

        if(sysStaff!=null)
        {
            projectPlanEntity.setUpdateStaff(sysStaff==null?sysStaff.getStaffName():"测试接口");//修改人

        }
        projectPlanEntity.setUpdateTime(timestamp);

        ProjectPlanEntity resultSet = projectPlanService.tzjh(projectPlanEntity,sysStaff,cause,type);
        return new LyhtResultBody<>(resultSet);
    }

}
