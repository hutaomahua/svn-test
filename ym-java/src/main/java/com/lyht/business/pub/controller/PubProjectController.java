package com.lyht.business.pub.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubProjectDao;
import com.lyht.business.pub.dao.PubProjectTree;
import com.lyht.business.pub.entity.PubProjectEntity;
import com.lyht.business.pub.service.PubProjectService;
import com.lyht.business.pub.vo.PubProjectParamVO;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/pubProject/date")
@Api(value = "/pubProject/date", tags = "补偿项目基础")
public class PubProjectController {
	private Logger log = LoggerFactory.getLogger(PubProjectController.class);
	@Autowired
	private PubProjectService services;

	@Autowired
	private PubProjectDao dao;
	
	@PostMapping("/treeSelect")
	@ApiOperation(value = "树状结构查询treeselect", notes = "查询")
	public LyhtResultBody<List<Map<String,Object>>> treeSelect(String code){
		return new LyhtResultBody<>(services.treeSelect(code));
	}
	
	@PostMapping("/getTree")
	@ApiOperation(value = "树状结构查询treeselect", notes = "查询")
	public LyhtResultBody<List<Map<String,Object>>> getList(){
		return new LyhtResultBody<>(services.getList());
	}


	@PostMapping("/newTreeList")
	@ResponseBody
	@ApiOperation(value = "树状结构查询", notes = "查询")
	public LyhtResultBody<List<PubProjectTree>> newTreeList(PubProjectEntity entity, String[] scode) {
		List<PubProjectTree> list = new ArrayList<>();

		try {
			if (scode != null) {
				List<String> mlist = Arrays.asList(scode);
				for (int i = 0; i < mlist.size(); i++) {
					System.out.println(mlist.get(i));
					List<PubProjectTree> list1 = services.getTopBean(entity, mlist.get(i));
					list.addAll(list1);
				}
			} else {
				list = services.getTopBean(entity, null);
			}
			List<PubProjectTree> mapList = services.findSon();
			// 拼接查询
			for (PubProjectTree mp : list) {
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
	private List<PubProjectTree> getTreeList(String parentId, List<PubProjectTree> sonList) {
		List<PubProjectTree> list = new ArrayList<>();
		if (sonList.size() > 0) {
			for (int i = 0; i < sonList.size(); i++) {
				PubProjectTree mp = sonList.get(i);
				// 当前自己为自己时进入方法
				if (StringUtils.equals(mp.getSuperId(), parentId)) {
					List<PubProjectTree> m = getTreeList(mp.getKey(), sonList);
					mp.setChildren(m);
					list.add(mp);
				}
			}
		}
		return list;
	}

	private List<PubProjectTree> deleteChildren(List<PubProjectTree> list) {
		for (int i = 0; i < list.size(); i++) {
			PubProjectTree mp = list.get(i);
			List<PubProjectTree> sonChildren = mp.getChildren();
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
	 * 保存
	 *
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody save(PubProjectEntity sysdept) {
		int i = 0;
		try {
			if (CommonUtil.getIntValue(sysdept.getId() + "") == 0) {
				sysdept.setNm(Randomizer.generCode(10));
				int superId = 0;
				if (null != sysdept.getSuperId()) {
					superId = sysdept.getSuperId();
				}
				// 查询父级
				PubProjectEntity fDept = services.getNewFcode(superId);
				// 查询最高的子集
				PubProjectEntity sDept = services.getNewScode(superId);
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
				sysdept.setFlag(0);
				dao.save(sysdept);
			} else {
				Optional<PubProjectEntity> findById = dao.findById(sysdept.getId());
				PubProjectEntity sysDepts = findById.get();
				Integer oldId = sysDepts.getId();
				Integer oldSuperId = sysDepts.getId();
				Integer newSuperId = sysdept.getSuperId();
				if (oldId.equals(newSuperId)) {
					i = 1;
					throw new LyhtRuntimeException(LyhtExceptionEnums.SUPER_ERROR);
				}
				if (!oldSuperId.equals(newSuperId)) {
					
					sysDepts.setSuperId(newSuperId);
					String oldfCode = sysDepts.getFcode();
					int superId = 0;
					if (null != sysdept.getSuperId()) {
						superId = sysdept.getSuperId();
					}
					PubProjectEntity fDept = services.getNewFcode(superId);
					PubProjectEntity sDept = services.getNewScode(superId);
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
					// 将所有下级部门的fCode进行改变
					services.updateSysDeptSon(oldfCode, sysDepts.getFcode(), oldfCode);
				}
				sysDepts.setName(sysdept.getName());// 项目名称
				sysDepts.setType(sysdept.getType());// 项目类型
				sysDepts.setMoney(sysdept.getMoney());// 单价
				sysDepts.setDanwei(sysdept.getDanwei());// 单位
				sysDepts.setMemo(sysdept.getMemo());// 备注
				sysDepts.setTarget(sysdept.getTarget());// 指标
				sysDepts.setSerialNumber(sysdept.getSerialNumber());//
				sysdept.setFlag(0);
				dao.save(sysDepts);
			}

		} catch (Exception e) {
			log.error("保存失败！", e);
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
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody();

	}

	/**
	 * 详情
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据ID查部门详情", notes = "根据ID查询")
	@GetMapping("/findById")
	public LyhtResultBody<PubProjectEntity> findById(Integer id) {
		PubProjectEntity sysDept = null;
		try {
			Optional<PubProjectEntity> findById = dao.findById(id);
			sysDept = findById.get();
		} catch (Exception e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(sysDept);

	}

	@ApiOperation(value = "查询（按父级id查询父级的所有子集）", notes = "查询（按父级id查询父级的所有子集）")
	@PostMapping("/list")
	public LyhtResultBody<List<PubProjectEntity>> getList(Integer superId) {
		List<PubProjectEntity> list = services.getList(superId);
		return new LyhtResultBody<>(list);
	}

	@ApiOperation(value = "查询一级项目", notes = "查询一级项目")
	@GetMapping("/getProjectSelect")
	public LyhtResultBody<List<PubProjectEntity>> getProjectSelect() {
		List<PubProjectEntity> list = null;
		try {
			list = services.getProjectSelect();
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(list);

	}
	
	@ApiOperation(value = "条件查询", notes = "条件查询")
	@PostMapping("/query")
	public LyhtResultBody<List<PubProjectEntity>> query(PubProjectParamVO pubProjectParamVO) {
		List<PubProjectEntity> list = services.query(pubProjectParamVO);
		return new LyhtResultBody<>(list);
	}

}
