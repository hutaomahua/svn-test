package com.lyht.business.abm.plan.controlle;

import com.alibaba.fastjson.JSONArray;
import com.lyht.Constants;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.abm.plan.bean.YearAnnualPlanDetail;
import com.lyht.business.abm.plan.dao.YearAnnualPlanDao;
import com.lyht.business.abm.plan.entity.PubNianDuTree;
import com.lyht.business.abm.plan.entity.YearAnnualPlanAdjustEntity;
import com.lyht.business.abm.plan.entity.YearAnnualPlanEntity;
import com.lyht.business.abm.plan.service.YearAnnualPlanAdjustService;
import com.lyht.business.abm.plan.service.YearAnnualPlanService;
import com.lyht.business.abm.removal.entity.MoveApproveEntity;
import com.lyht.business.pub.dao.PubRegionDao;
import com.lyht.business.pub.dao.PubYearTree;
import com.lyht.business.pub.entity.PubRegionEntity;
import com.lyht.business.pub.entity.PubYearSubject;
import com.lyht.business.pub.service.PubRegionService;
import com.lyht.system.pojo.SysAcct;
import com.lyht.system.pojo.SysDept;
import com.lyht.system.pojo.SysStaff;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping(value = "/yearplan")
@Api(value = "年度计划编制", tags = "年度计划编制")
public class YearAnnualPlanController {

    @Autowired
    YearAnnualPlanService yearAnnualPlanService;
    @Autowired
    YearAnnualPlanAdjustService yearAnnualPlanAdjustService;
    @Autowired
    YearAnnualPlanDao yearAnnualPlanDao;
    @Autowired
	private PubRegionDao pubRegionDao;
    
    @SuppressWarnings({"unchecked","rawtypes"})
    @PostMapping(value = "/saveNianDu")
    @ApiOperation(value = " 搬迁安置去向表 ", notes = "搬迁安置去向")
    public List<Map> saveQuXiang(String list) {
    	List<Map> list1 = new ArrayList<>();
    	Map map=new HashMap<>();
    	try
    	{
    		map.put("code", 200);
    		list1.add(map);
    		List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(list);
    		String years=listObjectFir.get(0).getOrDefault(("year").toString(), "".toString());
    		String regions=listObjectFir.get(0).getOrDefault(("region").toString(), "".toString());

        	List<Map> lists=yearAnnualPlanService.isPresence(regions, years);
        	if(lists.size()>0)
        	{
        		yearAnnualPlanDao.delYear(regions, years);;
        	}
            for (int i = 0; i < listObjectFir.size(); i++) {
/*            	String id=listObjectFir.get(i).getOrDefault(("id").toString(), "".toString());
*/
            	String nm=listObjectFir.get(i).getOrDefault(("id").toString(), "".toString());
            	String unitNm=listObjectFir.get(i).getOrDefault("unitNm".toString(), "".toString());
            	String year=listObjectFir.get(i).getOrDefault("year".toString(), "".toString());
            	String region=listObjectFir.get(i).getOrDefault("region".toString(), "".toString());

            	String taskName=listObjectFir.get(i).getOrDefault("taskName".toString(), "".toString());
            	String budgetWan=listObjectFir.get(i).getOrDefault("budgetWan".toString(), "".toString());
            	String completTaskNumber=listObjectFir.get(i).getOrDefault("completTaskNumber".toString(), "".toString());
            	String sumTaskNumber=listObjectFir.get(i).getOrDefault("sumTaskNumber".toString(), "".toString());
            	String sketch=listObjectFir.get(i).getOrDefault("sketch".toString(), "".toString());
            	String capitalTwo=listObjectFir.get(i).getOrDefault("capitalTwo".toString(), "".toString());
            	String lastyearCapitalTwo=listObjectFir.get(i).getOrDefault("lastyearCapitalTwo".toString(), "".toString());
            	String newCapital=listObjectFir.get(i).getOrDefault("newCapital".toString(), "".toString());
            
            	if(unitNm.equals("")&&taskName.equals("")&&budgetWan.equals("")
            			&&completTaskNumber.equals("")&&sumTaskNumber.equals("")
            			&&sketch.equals("")&&capitalTwo.equals("")&&lastyearCapitalTwo.equals("")
            			&&newCapital.equals(""))
            		continue;
            

            	
            		YearAnnualPlanEntity entity=new YearAnnualPlanEntity();
                	entity.setNm(nm);
                	entity.setUnitNm(unitNm);
                	entity.setRegion(region);
                	entity.setYear(year);


                	entity.setTaskName(taskName);
                	
                	if(!budgetWan.equals(""))
                	{
                		BigDecimal budgetWans=new BigDecimal(budgetWan);
                    	entity.setBudgetWan(budgetWans);
                	}
                	
                	
                	entity.setCompletTaskNumber(completTaskNumber);
                	entity.setSumTaskNumber(sumTaskNumber);              	
                	entity.setSketch(sketch);
                	
                	if(!capitalTwo.equals(""))
                	{
                		BigDecimal capitalTwos=new BigDecimal(capitalTwo);
                    	entity.setCapitalTwo(capitalTwos);
                	}
                	
                	if(!lastyearCapitalTwo.equals(""))
                	{
	                	BigDecimal lastyearCapitalTwos=new BigDecimal(lastyearCapitalTwo);
	                	entity.setLastyearCapitalTwo(lastyearCapitalTwos);
                	}
                	if(!newCapital.equals(""))
                	{
                		BigDecimal newCapitals=new BigDecimal(newCapital);
                    	entity.setNewCapital(newCapitals);
                	}
                	
                	
                	yearAnnualPlanDao.save(entity);
            		
            	
            	
            	

    		}
    	}catch(Exception e)
    	{
    		map.put("code", 500);
    		list1.add(map);
    	}
    	 
    	
		return list1;
      
    }
    
    
    @PostMapping(value = "/save")
    @ApiOperation(value = "保存", notes = "保存")
    public LyhtResultBody<YearAnnualPlanEntity> saveYearAnnualPlan(YearAnnualPlanEntity yearAnnualPlanEntity,HttpServletRequest request) {
        YearAnnualPlanEntity resultSet = yearAnnualPlanService.saveYearAnnualPlan(yearAnnualPlanEntity);
        return new LyhtResultBody<>(resultSet);
    }

    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询列表 条件查询", notes = "查询列表 条件查询")
    public LyhtResultBody< List<YearAnnualPlanDetail>> getList() {
        return yearAnnualPlanService.getList();
    }

    
    @ApiOperation(value = "按id删除", notes = "删除")
    @PostMapping("/delete")
    public LyhtResultBody<Integer> delete(Integer id) {
        return yearAnnualPlanService.delete(id);
    }
    
    
   
    
    @GetMapping(value = "/details")
    @ApiOperation(value = "按id查询", notes = "详情")
    public LyhtResultBody<List<YearAnnualPlanEntity>> details(LyhtPageVO pageVO,String id) {
        return yearAnnualPlanService.getDetail(pageVO,id);
    }

    /**
     * 批量删除
     * @param ids
     * @return3
     *

     */
    @Transactional(rollbackFor=Exception.class)
    @ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
    @PostMapping("/batch")
    public LyhtResultBody<String> batchDel(String ids) {
        return yearAnnualPlanService.batchDel(ids);
    }

    
    
    
    
    
    
    
    
    
    
	@PostMapping("/newTreeList")
	@ResponseBody
	@ApiOperation(value = "树状结构查询", notes = "查询")
	public LyhtResultBody<List<PubNianDuTree>> newTreeList(String year,String region,String[] scode) {
		List<PubNianDuTree> list = new ArrayList<>() ;
				
		try {			
			  if(scode!=null){
				    List<String> mlist = Arrays.asList(scode);
				  for (int i = 0; i < mlist.size(); i++) {
					  System.out.println(mlist.get(i));
					  List<PubNianDuTree>  list1 = yearAnnualPlanService.getTopBean(year,region,mlist.get(i));
					  list.addAll(list1);
					}  
			  }else{
				  list = yearAnnualPlanService.getTopBean(year,region,null);
			  }
			List<PubNianDuTree> mapList = yearAnnualPlanService.findSon(year,region);
			// 拼接查询
			for (PubNianDuTree mp : list) {
				// 查询子集
				mp.setChildren(getTreeList(mp.getKey(), mapList));
			}
			list = deleteChildren(list);
		} catch (Exception e) {

			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(list);
	}

	// 递归查询
	private List<PubNianDuTree> getTreeList(String parentId, List<PubNianDuTree> sonList) {
		List<PubNianDuTree> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				PubNianDuTree mp = sonList.get(i);
				// 当前自己为自己时进入方法
				if (StringUtils.equals(mp.getSuperId(), parentId)) {
					List<PubNianDuTree> m = getTreeList(mp.getKey(), sonList);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}
    
    
	private List<PubNianDuTree> deleteChildren(List<PubNianDuTree> list) {
		for (int i = 0; i < list.size(); i++) {
			PubNianDuTree mp = list.get(i);
			List<PubNianDuTree> sonChildren = mp.getChildren();
			if (sonChildren.size() == 0) {
				mp.setChildren(null);
			} else {
				mp.setChildren(deleteChildren(sonChildren));
			}
			list.set(i, mp);
		}
		return list;
	}
    
    
	
	@PostMapping("/isPresence")
	@ResponseBody
	@ApiOperation(value = "判断是否添加了年计划", notes = "查询")
	public List<Map> isPresence(String year,String region) {
		List<Map> list1 = new ArrayList<>();
    	Map map=new HashMap<>();
		List<Map> list=yearAnnualPlanService.isPresence(region, year);
		if(list.size()>0)
		{
			//返回500已存在 不可再添加
			 map.put("code", 500);
	    	 list1.add(map);
		}else{
			 map.put("code", 200);
	    	 list1.add(map);
		}
		return list1;
	}
}
