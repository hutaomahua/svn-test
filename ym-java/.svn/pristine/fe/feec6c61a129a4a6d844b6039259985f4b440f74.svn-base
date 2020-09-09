package com.lyht.system.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.system.bean.SysMenuDetail;
import com.lyht.system.bean.SysMenuTree;
import com.lyht.system.pojo.SysMenu;
import com.lyht.system.service.SysMenuService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人： liuamang 脚本日期:2019年2月19日 22:09:10 说明: 系统菜单
 */
@Api(value = "/sysMenu", tags = "菜单信息")
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
	private Logger log = LoggerFactory.getLogger(SysMenuController.class);

	@Autowired
	private SysMenuService services;
	///////////////////////////////////////// new菜单//////////////////////////////////////////////////////

	/**
	 * 菜单列表加载
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/treeList")
	@ApiOperation(value = "组织树", notes = "树")
	public Map treeList() {
		List<SysMenuDetail> list = null;
		Map map = new HashMap<>();
		try {
			list = services.findList();
			map.put("code", 0);
			map.put("msg", "成功");
			map.put("list", list);
		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "失败");
			map.put("list", null);

			log.error("获取菜单列表失败！", e);
		}
		return map;
	}

	/**
	 * 按条件模糊查询
	 * 
	 * @return
	 */
	@PostMapping("/list")
	@ApiOperation(value = "根据条件模糊查询", notes = "查询")
	public LyhtResultBody<List<SysMenu>> page(LyhtPageVO lyhtPageVO, SysMenu sysMenu) {
		return services.page(lyhtPageVO, sysMenu);
	}

	/**
	 * 树状结构查询
	 * 
	 * @return
	 */
	@PostMapping("/newTreeList")
	@ResponseBody
	@ApiOperation(value = "树状结构查询", notes = "查询")
	public LyhtResultBody<List<SysMenuTree>> newTreeList(String name) {
		List<SysMenuTree> list = null;
		try {
			list = services.getTopBean(name);
			List<SysMenuTree> mapList = services.findByNew();// 获取子级菜单
			for(int i=0;i<mapList.size();i++){
				mapList.get(i).setChildren(getTreeLists(mapList.get(i).getId(), mapList,i));
			}
			for (SysMenuTree mp : list) {
				// 查询子集
				mp.setChildren(getTreeLists(mp.getId(), mapList,-1));
			}
//			for (int i = 0; i < list.size(); i++) {// 遍历父级菜单
//				SysDeptTree mp = list.get(i);// 接收到当前对象
//				List<SysDeptTree> mList = new ArrayList<>();
//				for (int j = 0; j < mapList.size(); j++) {// 循环查找父级菜单下的子级并添加进菜单集合中
//					if (StringUtils.equals(mapList.get(j).getSuperId() + "", mp.getValue() + "")) {
//						mList.add(mapList.get(j));
//					}
//				}
//				mp.setChildren(mList);// 将子级菜单添加到父级菜单下
//				list.set(i, mp);
//			}
		} catch (Exception e) {
			log.error("获取菜单树结构失败！", e);
		}
		return new LyhtResultBody<List<SysMenuTree>>(list);
	}

	/**
	 * 树状结构查询
	 * 
	 * @return
	 */
	@PostMapping("/newTreePage")
	@ResponseBody
	@ApiOperation(value = "菜单管理树状结构查询", notes = "查询")
	public LyhtResultBody<List<Map<String,Object>>> newTreePage(String name) {
		
		return services.tree();
//		List<SysMenuTree> list = null;
//		try {
//			list = services.getTopBeanTwo(name);
//			List<SysMenuTree> mapList = services.findByNewTwo();
//			for (SysMenuTree mp : list) {
//				// 查询子集
//				mp.setChildren(getTreeList(mp.getKey(), mapList));
//			}
//			list = deleteChildren(list);
//		} catch (Exception e) {
//			log.error("获取菜单树结构失败！", e);
//		}
//		return new LyhtResultBody<List<SysMenuTree>>(list);
	}
	
	// 递归查询
	private List<SysMenuTree> getTreeList(String parentId, List<SysMenuTree> sonList) {
		List<SysMenuTree> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				SysMenuTree mp = sonList.get(i);
				// 当前自己为自己时进入方法
				if (StringUtils.equals(mp.getSuper_id(), parentId)) {
					List<SysMenuTree> m = getTreeList(mp.getKey(), sonList);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}
	// 递归查询
	private List<SysMenuTree> getTreeLists(String parentId, List<SysMenuTree> sonList,int j) {
		List<SysMenuTree> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				SysMenuTree mp = sonList.get(i);
				// 当前自己为自己时进入方法
				if (StringUtils.equals(mp.getSuper_id(), parentId)&&i!=j) {
					List<SysMenuTree> m = getTreeLists(mp.getId(), sonList,i);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}
	private List<SysMenuTree> deleteChildren(List<SysMenuTree> list) {
		for (int i = 0; i < list.size(); i++) {
			SysMenuTree mp = list.get(i);
			List<SysMenuTree> sonChildren = mp.getChildren();
			if (sonChildren.size() == 0) {
				mp.setChildren(null);
			} else {
				mp.setChildren(deleteChildren(sonChildren));
			}
			list.set(i, mp);
		}
		return list;
	}
	// 递归查询
	private List<Map> getTreeList(int superid) {
		List<Map> list = null;
		list = services.findBySuperId(superid);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> mp = list.get(i);
				List<Map> m = getTreeList(Integer.parseInt(mp.get("id") + ""));
				mp.put("children", m);
			}
		}
		return list;
	}

	/**
	 * 保存
	 *
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody<List<SysMenu>> save(SysMenu sysMenu) {
		int errCode = 0;
		try {
			Integer superId = 0;
			if (null != sysMenu.getSuperId()) {
				superId = sysMenu.getSuperId();
			}

			SysMenu sMenu = services.getNewScode(superId);
			SysMenu fMenu = services.getNewFcode(superId);
			Integer fix = getScode(sysMenu,sMenu);
			sysMenu.setFcode(getFcode(fMenu,fix));
			sysMenu.setScode(fix + "");
			sysMenu.setFlag(0);
			sysMenu.setMenuflag("0");

			List<SysMenu> menuList = new ArrayList<SysMenu>();
			int a = 0;
			//查询小一级的排序
			if (CommonUtil.getIntValue(sysMenu.getId() + "") == 0) {
				sysMenu.setNm(Randomizer.generCode(10));
				menuList = services.findSonScodes(superId,fix+"",null);
				services.save(sysMenu);
			} else {
				SysMenu ySysMenu = services.findById(sysMenu.getId());
				//获取子集数组，防止父级id为其子集
				List<Integer> sonIds = services.findSonIds(sysMenu.getFcode());
				for(Integer ids : sonIds){
					if (ids.equals(sysMenu.getSuperId())) {
						errCode = 1;
						throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
					}
				}
				Integer scodea = Integer.parseInt(ySysMenu.getScode());
				if(scodea>=fix){
					menuList = services.findSonScodes(superId,fix+"",null);
				}else{
					a=1;
					menuList = services.findSonScodes(superId,null,fix+"");
				}
				services.save(sysMenu);
			}
			chongpaixu(menuList,fMenu,sysMenu,a);
		} catch (Exception e) {
			log.error("菜单保存失败！", e);
			switch (errCode){
				case 0:
					throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
				case 1:
					throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
				default:
					throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
			}
		}
		return new LyhtResultBody<List<SysMenu>>();
	}
	//下级重排序
	public void chongpaixu(List<SysMenu> menuList,SysMenu fMenu,SysMenu sysMenu,int a){
		Integer scode  = Integer.parseInt(sysMenu.getScode());
		for (SysMenu menu : menuList){
			//Integer scodes =  Integer.parseInt(menu.getScode())+1;
			if(!menu.getId().equals(sysMenu.getId())){
				if(a == 1){
					scode =  Integer.parseInt(menu.getScode())-1;
				}else {
					scode+=1;
				}
				menu.setScode(scode+"");
				menu.setFcode(getFcode(fMenu,scode));
				services.save(menu);
				System.out.println(menu.toString());
			}
		}
	}
	//获取短编码
	public Integer getScode(SysMenu sysMenu,SysMenu sMenu){
		Integer fix = 0;
		if (sysMenu.getScode() != null && CommonUtil.getLength(sysMenu.getScode()) > 0) {
			fix = Integer.parseInt(sysMenu.getScode());
		} else {
			try {
				fix = Integer.parseInt(sMenu.getScode()) + 1;
			} catch (Exception e) {
				fix = 1;
			}
		}
		return fix;
	}
	//获取长编码
	public String getFcode(SysMenu fMenu,Integer scodes){
		String fcodes = "";
		try {
			fcodes = fMenu.getFcode() + String.format("%03d", scodes);
		} catch (Exception e) {
			fcodes = String.format("%03d", scodes);
		}
		return fcodes;
	}
	/**
	 * 删除
	 *
	 * @param fcode
	 * @return
	 */
	@ApiOperation(value = "按fcode删除,删除当前菜单以及子菜单", notes = "删除")
	@GetMapping("/delete")
	public LyhtResultBody<List<Map>> delete(String fcode) {
		try {
			services.removeDepts(fcode);
		} catch (Exception e) {
			log.error("菜单删除失败！", e);
		}
		return new LyhtResultBody<List<Map>>();

	}

	/**
	 * 批量删除
	 *
	 * @param fcodes
	 * @return
	 */
	@ApiOperation(value = "批量删除，fcodes以英文逗号拼接", notes = "批量删除")
	@GetMapping("/batch")
	public LyhtResultBody<List<Map>> batchDel(String fcodes) {
		List<Integer> idList = null;
		try {
//			idList = CommonUtil.parseIntegerList(ids);// 转换id整形数组
//			List<SysMenu> deleteOwners = dao.findAll(idList);
//			dao.deleteInBatch(deleteOwners);
			String[] fcodess = fcodes.split(",");
			for (String fcode : fcodess) {
				services.removeDepts(fcode);
			}
		} catch (Exception e) {
			log.error("菜单删除失败！", e);
		}
		return new LyhtResultBody<List<Map>>();
	}

}
