package com.lyht.business.pub.controller;

import com.lyht.business.pub.dao.PubSetDao;
import com.lyht.business.pub.entity.PubSet;
import com.lyht.business.pub.service.PubSetService;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
  * 创建人： liuamang 
  * 脚本日期:2019年2月21日 22:42:09
  * 说明:  系统设置
  */
@Controller
@RequestMapping("/pubSet")
@Api(value="/sysStaffRoleData", tags="系统设置表（暂时没用）")
public class PubSetController {
	private Logger log = LoggerFactory.getLogger(PubSetController.class);
	
	@Autowired  private PubSetService services;
	@Autowired  private PubSetDao dao;
	/**系统设置 数据保存
	 * @param request
	 * @param bean
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public  Map<String,Object> save(PubSet bean){
		Map<String ,Object> jsonMap = new HashMap<String ,Object>();
		jsonMap.put("flag","error");
		try {  
			if(CommonUtil.getIntValue(bean.getId()+"") == 0 ){
				bean.setNm(Randomizer.generCode(10));
			}

			dao.save(bean);
			jsonMap.put("flag","success");
		} catch (Exception e) {
			log.error("系统设置数据保存失败！",e);
		}
		return jsonMap;
	}
 
	/**系统设置 新增加载方法 , 初始化修改数据
	 * @param id
	 * @return
	 */
	@RequestMapping("/add/{id}")
	public String add(@PathVariable int id,Model model){
		try {
			if(id > 0 ){
				PubSet bean  = dao.findById(id).get();
				model.addAttribute("bean",bean);
			}
		} catch (Exception e) {
			log.error("初始化系统设置维护界面出错！");
		}
		return "page/pub/pubSet/pubSet_edit";
	}
    
   /**系统设置 详细页
	 * @param id
	 * @return
	 */
	@RequestMapping("/show/{id}")
	public String show(@PathVariable int id,Model model){
		try {
			if(id > 0 ){
				PubSet bean  = dao.findById(id).get();
				model.addAttribute("bean",bean);
			}
		} catch (Exception e) {
			log.error("系统设置详细页数据加载出错！");
		}
		return "page/pub/pubSet/pubSet_show";
	}
	
	/** 系统设置删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/remove/{ids}")
	@ResponseBody
	public Map<String,Object> remove(@PathVariable String ids){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//批量删除 和 单个删除
			services.deleteEntity(ids);
			map.put("flag", "success");
			map.put("msg", "系统设置数据删除成功！");
		} catch (Exception e) {
			log.error("初始化系统设置维护界面出错！");
			map.put("flag", "error");
			map.put("msg", "系统设置数据删除失败，请重试！");
		}
		return map;
	}
	
}
