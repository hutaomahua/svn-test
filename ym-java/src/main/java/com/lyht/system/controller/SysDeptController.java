package com.lyht.system.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.SysDeptDetail;
import com.lyht.system.bean.SysDeptTree;
import com.lyht.system.dao.SysDeptDao;
import com.lyht.system.pojo.SysDept;
import com.lyht.system.service.SysDeptService;
import com.lyht.system.vo.SysDeptVo;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 创建人： liuamang 脚本日期:2019年2月19日 22:09:36 说明: 单位部门
 */
@Api(value = "/sysDept", tags = "部门信息")
@RestController
@RequestMapping("/sysDept")
public class SysDeptController {
	private Logger log = LoggerFactory.getLogger(SysDeptController.class);

	@Autowired
	private SysDeptService services;
	@Autowired
	private SysDeptDao dao;
	
	@PostMapping("/findByDeptTypeTree")
	@ApiOperation(value = "根据机构类型获取树形组织下拉框", notes = "树")
	public LyhtResultBody<List<SysDept>> findByDeptTypeTree(String deptType){
		return services.findByDeptTypeTree(deptType);
	}
	/**
	 * 单位部门 数据保存
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	/*
	 * @RequestMapping("/save")
	 * 
	 * @ResponseBody public Map<String,Object> save(SysDept bean,HttpServletRequest
	 * request){ Map<String ,Object> jsonMap = new HashMap<String ,Object>();
	 * jsonMap.put("flag","error"); try { //新增处理
	 * if(CommonUtil.getIntValue(CommonUtil.trim(request.getParameter("type")))> 0){
	 * bean.setNm(Randomizer.generCode(10)); SysDept sBean = null; //新增子节点处理方式
	 * if(CommonUtil.getIntValue(CommonUtil.trim(request.getParameter("type")))==1
	 * ){ //获取当前数据下最大的数据编码 下一节点 sBean = services.getSonBean(bean.getId());
	 * bean.setSuperId(bean.getId()); String newScode = ""; if(sBean == null){
	 * newScode = "001"; }else{ String scode =
	 * (CommonUtil.getIntValue(sBean.getScode())+1)+""; newScode = scode; //补全三位编号
	 * for(int i = 0 ; i <= scode.length();i++){ newScode= "0"+newScode; } }
	 * bean.setScode(newScode); bean.setFcode(bean.getFcode()+newScode); //新增根节点处理方式
	 * }else
	 * if(CommonUtil.getIntValue(CommonUtil.trim(request.getParameter("type")))==2){
	 * //获取当前数据下最大的数据编码 跟节点 sBean = services.getSuperBean(); bean.setSuperId(null);
	 * String newScode = ""; if(sBean == null){ newScode = "001"; }else{ String
	 * scode = (CommonUtil.getIntValue(sBean.getScode())+1)+""; newScode = scode;
	 * //补全三位编号 for(int i = 0 ; i <= scode.length();i++){ newScode= "0"+newScode; }
	 * } bean.setScode(newScode); bean.setFcode(newScode); } bean.setFlag(0);
	 * bean.setId(null); }
	 * 
	 * services.save(bean); jsonMap.put("flag","success"); } catch (Exception e) {
	 * log.error("单位部门数据保存失败！",e); } return jsonMap; }
	 * 
	 *//**
		 * 单位部门 新增加载方法 , 初始化修改数据
		 * 
		 * @param id
		 * @return
		 */
	/*
	 * @RequestMapping("/add/{id}") public String add(@PathVariable int id,Model
	 * model){ try { if(id > 0 ){ SysDept bean = services.findEntityById(id);
	 * model.addAttribute("bean",bean); } } catch (Exception e) {
	 * log.error("初始化单位部门维护界面出错！",e); } return "page/system/sysDept/sysDept_edit"; }
	 * 
	 *//**
		 * 单位部门删除
		 * 
		 * @param id
		 * @return
		 *//*
			 * @RequestMapping("/remove/{fcode}")
			 * 
			 * @ResponseBody public Map<String,Object> remove(@PathVariable String fcode){
			 * Map<String,Object> map = new HashMap<String,Object>(); try { //批量删除 和 单个删除
			 * services.removeDepts(fcode); map.put("flag", "success"); map.put("msg",
			 * "单位部门数据删除成功！"); } catch (Exception e) { log.error("初始化单位部门维护界面出错！",e);
			 * map.put("flag", "error"); map.put("msg", "单位部门数据删除失败，请重试！"); } return map; }
			 */

	/**
	 * 单位部门列表加载
	 * 
	 * @return
	 */
	@PostMapping("/treeList")
	@ResponseBody
	@ApiOperation(value = "组织树", notes = "树")
	public LyhtResultBody<List<SysDeptDetail>> treeList() {
		List<SysDeptDetail> list = null;
		try {
			list = services.findList();
		} catch (Exception e) {
			log.error("获取单位部门列表失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(list);
	}

	@PostMapping("/list")
	@ResponseBody
	@ApiOperation(value = "根据条件模糊查询", notes = "查询")
	public LyhtResultBody<List<SysDeptDetail>> page(LyhtPageVO lyhtPageVO, SysDept sysDept) {
		return services.page(lyhtPageVO, sysDept);
	}

	@PostMapping("/getDeptTree")
	@ResponseBody
	@ApiOperation(value = "树状结构查询 value为nm", notes = "查询")
	public LyhtResultBody<List<SysDeptTree>> getDeptTree() {
		List<SysDeptTree> list = null;
		try {
			list = services.getTopBean01();
			List<SysDeptTree> mapList = services.findSon01();
			for (SysDeptTree sysDeptTree : list) {
				sysDeptTree.setChildren(findTreeList(sysDeptTree.getKey(), mapList));
			}
		} catch (Exception e) {
			log.error("获取单位部门树结构失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(list);
	}

	// 递归查询
	private List<SysDeptTree> findTreeList(String parentId, List<SysDeptTree> sonList) {
		List<SysDeptTree> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				SysDeptTree mp = sonList.get(i);
				if (mp.getSuperId().equals(parentId)) {
					List<SysDeptTree> m = findTreeList(mp.getKey(), sonList);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}

	@PostMapping("/newTreeList")
	@ResponseBody
	@ApiOperation(value = "树状结构查询", notes = "查询")
	public LyhtResultBody<List<SysDeptTree>> newTreeList(SysDept sysDept) {
		List<SysDeptTree> list = null;
		try {
			list = services.getTopBean(sysDept);
			List<SysDeptTree> mapList = services.findSon();
			// 拼接查询
			for (SysDeptTree mp : list) {
				// 查询子集
				mp.setChildren(getTreeList(mp.getKey(), mapList));
			}
			list = deleteChildren(list);
		} catch (Exception e) {
			log.error("获取单位部门树结构失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(list);
	}

	// 递归查询
	private List<SysDeptTree> getTreeList(String parentId, List<SysDeptTree> sonList) {
		List<SysDeptTree> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				SysDeptTree mp = sonList.get(i);
				// 当前自己为自己时进入方法
				if (StringUtils.equals(mp.getSuperId(), parentId)) {
					List<SysDeptTree> m = getTreeList(mp.getKey(), sonList);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}

	private List<SysDeptTree> deleteChildren(List<SysDeptTree> list) {
		for (int i = 0; i < list.size(); i++) {
			SysDeptTree mp = list.get(i);
			List<SysDeptTree> sonChildren = mp.getChildren();
			if (sonChildren.size() == 0) {
				mp.setChildren(null);
			} else {
				mp.setChildren(deleteChildren(sonChildren));
			}
			list.set(i, mp);
		}
		return list;
	}
	/**
	 * 跳转部门页面
	 * 
	 * @return
	 *//*
		 * @RequestMapping("/dept") public String toDeptPage(){ return
		 * "page/system/sysDept/dept"; }
		 */

	/**
	 * 查询最高级的部门
	 * 
	 * @return
	 */
	@PostMapping("/topList")
	@ResponseBody
	@ApiOperation(value = "查询最高级的部门", notes = "查询")
	public LyhtResultBody<List<SysDeptTree>> topList(SysDept sysDept) {
		List<SysDeptTree> list = null;
		try {
			list = services.getTopBean(sysDept);
		} catch (Exception e) {
			log.error("获取单位部门列表失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(list);
	}

	/**
	 * 查询当前部门的下级部门
	 * 
	 * @return
	 */
	@PostMapping("/listBySuperId")
	@ResponseBody
	@ApiOperation(value = "查询当前部门的下级部门", notes = "查询")
	public LyhtResultBody<List<SysDeptTree>> listBySuperId(Integer id) {
		List<SysDeptTree> list = null;
		try {
			list = services.findBySuperId(id);
		} catch (Exception e) {
			log.error("获取单位部门列表失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(list);
	}

	/**
	 * 保存
	 *
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody save(SysDept sysdept) {
		int i = 0;
		try {
			if (CommonUtil.getIntValue(sysdept.getId() + "") == 0) {
				sysdept.setNm(Randomizer.generCode(10));
				int superId = 0;
				if (null != sysdept.getSuperId()) {
					superId = sysdept.getSuperId();
				}
				// 查询父级
				SysDept fDept = services.getNewFcode(superId);
				// 查询最高的子集
				SysDept sDept = services.getNewScode(superId);
				String str = "";
				try {
					// 获取子集scode
					str = String.format("%03d", Integer.parseInt(sDept.getScode()) + 1);
				} catch (Exception e) {
					// 判断有无父级
					if (null == sysdept.getSuperId()) {
						// 没有父级就本身为父级，查询最上级的Scode
						str = String.format("%03d", Integer.parseInt(services.getNewFcodeTwo().getScode()) + 1);
					} else {
						// 没有子集就默认001
						str = String.format("%03d", 1);
					}
				}
				sysdept.setFcode(fDept.getFcode() + str);
				sysdept.setScode(str);
				sysdept.setSuperId(fDept.getId());
				services.save(sysdept);
			} else {
				Optional<SysDept> findById = dao.findById(sysdept.getId());
				SysDept sysDepts = findById.get();
				List<Integer> sonIds = dao.getSonIds(sysDepts.getFcode());
				for(Integer ids : sonIds){
					if (ids.equals(sysdept.getSuperId())) {
						i = 1;
						throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
					}
				}
				sysDepts.setSuperId(sysdept.getSuperId());
				String oldfCode = sysDepts.getFcode();
				int superId = 0;
				if (null != sysdept.getSuperId()) {
					superId = sysdept.getSuperId();
				}
				SysDept fDept = services.getNewFcode(superId);
				SysDept sDept = services.getNewScode(superId);
				String str = "";
				try {
					str = String.format("%03d", Integer.parseInt(sDept.getScode()) + 1);
				} catch (Exception e) {
					if (null == sysdept.getSuperId()) {
						str = String.format("%03d", Integer.parseInt(services.getNewFcodeTwo().getScode()) + 1);
					} else {
						str = String.format("%03d", 1);
					}
				}
				sysDepts.setFcode(fDept.getFcode() + str);
				sysDepts.setScode(str);
				sysDepts.setSuperId(fDept.getId());
				sysDepts.setRemark(sysdept.getRemark());
				sysDepts.setName(sysdept.getName());
				sysDepts.setDeptType(sysdept.getDeptType());
				services.save(sysDepts);
				// 将所有下级部门的fCode进行改变
				services.updateSysDeptSon(oldfCode, sysDepts.getFcode(), oldfCode);
			}

		} catch (Exception e) {
			log.error("部门保存失败！", e);
			if (i == 0) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
			} else if (i == 1) {
				throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
			}

		}
		return new LyhtResultBody();
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "按id删除", notes = "删除")
	@GetMapping("/delete")
	public LyhtResultBody delete(Integer id) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			log.error("部门删除失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody();

	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID查部门详情", notes = "根据ID查询")
	@GetMapping("/findById")
	public LyhtResultBody<SysDept> findById(Integer id) {
		SysDept sysDept = null;
		try {
			Optional<SysDept> findById = dao.findById(id);
			sysDept = findById.get();
		} catch (Exception e) {
			log.error("部门详情获取失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(sysDept);

	}

	/**
	 * 查询部门下的员工
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "查询部门下的员工，id以英文逗号拼接", notes = "查询部门下的员工")
	@GetMapping("/findUserByDept")
	public LyhtResultBody<List<SysDeptVo>> findUserByDept(String ids) {
		List<SysDeptVo> reList = new ArrayList<>();
		try {
			List<Integer> idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
			List<SysDept> owners = dao.findAllById(idList);
			for (SysDept sysDept : owners) {
				List<SysDeptVo> List = services.getUsers(sysDept.getId());
				reList.addAll(List);
			}
		} catch (Exception e) {
			log.error("查询部门下的员工失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(reList);
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
	@GetMapping("/batch")
	public LyhtResultBody batchDel(String ids) {
		List<Integer> idList = null;
		try {
			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
			List<SysDept> deleteOwners = dao.findAllById(idList);
			for (SysDept sysDept : deleteOwners) {
				services.removeDepts(sysDept.getFcode());
			}
		} catch (Exception e) {
			log.error("部门删除失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody();
	}
}
