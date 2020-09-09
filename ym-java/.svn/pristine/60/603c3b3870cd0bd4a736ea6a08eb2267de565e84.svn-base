package com.lyht.business.abm.land.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.land.bean.RequisitionPlanDetail;
import com.lyht.business.abm.land.vo.RequisitionPlanVo;
import com.lyht.business.abm.plan.dao.ProjectPlanDao;
import com.lyht.business.abm.plan.entity.ProjectPlanEntity;
import com.lyht.business.abm.plan.service.ProjectPlanService;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.business.abm.land.entity.ReDeailsEntity;
import com.lyht.business.abm.land.entity.RequisitionPlanEntity;
import com.lyht.business.abm.land.service.ReDeailsService;
import com.lyht.business.abm.land.service.RequisitionPlanService;
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
@RequestMapping(value = "/requisition/plan")
@Api(value = "征地进度", tags = "征地进度")
public class RequisitionPlanController {

    private Logger log = LoggerFactory.getLogger(RequisitionPlanService.class);
    @Autowired
    RequisitionPlanService landPlanService;

    @Autowired
    ProjectPlanService projectPlanService;
    
    @Autowired
    private PubRegionDao pubRegionDao;
    @Autowired
    private ProjectPlanDao  projectplandao;
    @Autowired
    ReDeailsService redeailsservice;
    @Autowired
    ProjectPlanDao projectPlanDao;

    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", notes = "保存")
    public LyhtResultBody<RequisitionPlanEntity> saveYearAnnualPlan(RequisitionPlanEntity planEntity, HttpServletRequest request) {
    	RequisitionPlanController con=new RequisitionPlanController();
    	
    	if(con.isCNChar(planEntity.getTaskContent())==true)
    	{
    		List<Map> list= projectplandao.getTaskNm(planEntity.getTaskContent());
    		if(list.size()>0){
    			planEntity.setTaskContent(list.get(0).get("nm").toString());
    		}
    	}
    	  PubRegionEntity parent = pubRegionDao.findByCityCode(planEntity.getRegion());

    	  planEntity.setRegionName(parent.getMergerName());
        RequisitionPlanEntity resultSet = landPlanService.saveYearAnnualPlan(planEntity);
        return new LyhtResultBody<>(resultSet);
    }
    
  
    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询列表 条件查询", notes = "查询列表 条件查询")
    public LyhtResultBody<List<Map>> getList(LyhtPageVO pageVO, RequisitionPlanVo requisitionPlanVo) {
    	if(null!=requisitionPlanVo.getRegion()&&!"".equals(requisitionPlanVo.getRegion()))
        {
        	List<Map> list=projectPlanDao.regionName(requisitionPlanVo.getRegion());
        	if(list.size()>0)
         	{
        		requisitionPlanVo.setRegion(list.get(0).get("merger_name").toString());
         	}
        }
        return landPlanService.getList(pageVO, requisitionPlanVo);
    }
    
    @PostMapping(value = "/getHdList")
    @ApiOperation(value = "核定人口条件查询", notes = "查询列表 条件查询")
    public LyhtResultBody<List<Map>> getHdList(LyhtPageVO pageVO, String region,String name) {
    	 if(null!=region&&!"".equals(region))
         {
         	List<Map> list=projectPlanDao.regionName(region);
         	if(list.size()>0)
         	{
             	region=list.get(0).get("merger_name").toString();

         	}
         }
        return landPlanService.getHdList(pageVO, region,name,null);
    }
    
    

    @ApiOperation(value = "按id删除", notes = "删除")
    @PostMapping("/delete")
    public LyhtResultBody<Integer> delete(Integer id) {
        return landPlanService.delete(id);
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return3
     */
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
    @GetMapping("/batch")
    public LyhtResultBody<String> batchDel(String ids) {
        return landPlanService.batchDel(ids);
    }


    @ApiOperation(value = "按id查询", notes = "详情")
    @GetMapping("/details")
    public LyhtResultBody<Map<String,Object>> details(Integer id) {

        return new LyhtResultBody<>(landPlanService.getDetails(id));

    }

    @ApiOperation(value = "按查询条件导出Excel", notes = "Excel导出")
    @SuppressWarnings("rawtypestwo")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response, RequisitionPlanVo requisitionPlanVo) {
        List<Map> list = landPlanService.list(requisitionPlanVo);
        String title = "中南院智慧征地移民平台进度管理报表";
        String fileName = title;
        String[] headers = { "总序","分序","地区","施工编号（标段）所属地块","征地面积(亩)","","控制节点","","","","工期(月)", "临时用地报批进展","","","","","","","","","", "征地交地进展","","","","","","工作进展、存在问题、解决措施","","" };
        String[] columnNames = {"land_order","point_order","tbname", "sgbh", "yjzd", "lszd", "gcyd_time", "gskzjd", "dfzfjdsj", "sjjtkzsj", "gq" ,"hx","kjt","zdht","ghyj","fkfa","fkbh","ldbg","jbnt","pfwj","lsydpf","djfz","swfh","pg","htdf","wbqk","jcsg","gzjz","czwt","jjcs"};
        try {
            ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
            exportExcelWrapper.exportExcelTwo(fileName, title, headers, columnNames, list, response,
                    ExportExcelWrapper.EXCEL_FILE_2003);
        } catch (Exception e) {
            log.error("excel导出失败：", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
        }
    }
    @PostMapping(value = "/getProjectList")
    @ApiOperation(value = "选择工作名称带出工作内容，工作类别，结束时间", notes = "nm为查询条件")
    public LyhtResultBody<List<ProjectPlanEntity>> getProjectList(LyhtPageVO pageVO,  String region,String area,String taskName,String nm) {
        return projectPlanService.getList(pageVO,region,area,taskName,nm);
    }
    
    @GetMapping(value = "/selectName")
    @ApiOperation(value = "查询出所有的工作名称", notes = "查询出所有的工作名称")
    public LyhtResultBody<List<Map>> selectName(String dictCate) {
        List<Map> list=landPlanService.selectName(dictCate);
        return new LyhtResultBody<>(list);
    }
    
    
    @PostMapping(value = "/saveDeails")
    @ApiOperation(value = "详情保存", notes = "详情保存")
    public LyhtResultBody<ReDeailsEntity> saveDeails(ReDeailsEntity planEntity, HttpServletRequest request) { 	    	 
    	ReDeailsEntity resultSet = redeailsservice.saveYearAnnualPlan(planEntity);
        return new LyhtResultBody<>(resultSet);
    }

    @PostMapping(value = "/getListDeails")
    @ApiOperation(value = "查询列表 详情", notes = "查询列表详情")
    public LyhtResultBody<List<ReDeailsEntity>> getListDeails(LyhtPageVO pageVO, String nm) {
        return redeailsservice.getList(pageVO, nm);
    }
    
    @ApiOperation(value = "详情按id删除", notes = "删除")
    @GetMapping("/deleteDeails")
    public LyhtResultBody<Integer> deleteDeails(Integer id) {
        return redeailsservice.delete(id);
    }
    
    public static boolean isCNChar(String s){
        boolean booleanValue = false;
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c > 128){
                booleanValue = true;
                break;
            }
        }
        return booleanValue;
    }
}
