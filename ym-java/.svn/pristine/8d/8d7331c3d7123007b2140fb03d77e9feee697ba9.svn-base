package com.lyht.business.abm.plan.controlle;

import com.lyht.Constants;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;
import com.lyht.business.abm.plan.service.YearAnnualPlanAdjustService;
import com.lyht.business.abm.plan.service.YearAnnualPlanService;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 	年度计划编制
 *  @author lj
 *           2019/10/22
 */
@RestController
@RequestMapping(value = "/yearplanlog")
@Api(value = "年度计划调整日志", tags = "年度计划调整日志")
public class YearAnnualPlanAdjustController {

    @Autowired
    YearAnnualPlanAdjustService yearAnnualPlanAdjustService;



    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询列表 条件查询", notes = "查询列表 条件查询")
    public LyhtResultBody<List<Map>> getList(LyhtPageVO pageVO, String region,String name) {
        return yearAnnualPlanAdjustService.getList(pageVO,region,name);
    }
   
}
