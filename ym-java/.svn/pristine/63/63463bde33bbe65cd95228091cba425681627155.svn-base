package com.lyht.business.pub.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.entity.PubDictValue;
import com.lyht.business.pub.service.PubDictValueService;
import com.lyht.business.pub.vo.PubDictValueDetail;
import com.lyht.business.pub.vo.PubDictValueTree;
import com.lyht.business.pub.vo.PubDictValueTreeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建人： liuamang 脚本日期:2019年2月21日 22:41:53 说明: 字典
 */
@Slf4j
@RestController
@RequestMapping("/pub/dict/value")
@Api(value = "/pub/dict/value", tags = "字典")
public class PubDictValueController {
	@Autowired
	private PubDictValueService services;

	/**
	 * 条件查询
	 * 
	 * @param pubDictValueDetail
	 * @return
	 */
	@PostMapping("/list")
	@ApiOperation(value = "条件查询", notes = "条件查询")
	public LyhtResultBody<List<PubDictValue>> list(PubDictValueDetail pubDictValueDetail) {
		return services.list(pubDictValueDetail);
	}

	/**
	 * 分页查询
	 * 
	 * @param pubDictValueDetail
	 * @return
	 */
	@PostMapping("/page")
	@ApiOperation(value = "分页", notes = "条件查询")
	public LyhtResultBody<List<PubDictValueDetail>> page(LyhtPageVO lyhtPageVO, PubDictValueDetail pubDictValueDetail) {
		return services.page(lyhtPageVO, pubDictValueDetail);
	}

	/**
	 * 详情
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/deail")
	@ApiOperation(value = "id查询", notes = "详情查询")
	public LyhtResultBody<PubDictValue> deail(Integer id) {
		return services.findById(id);
	}

	/**
	 * 详情
	 * 
	 * @param siteCode
	 * @return
	 */
	@PostMapping("/deailByCode")
	@ApiOperation(value = "code查询", notes = "详情查询")
	public LyhtResultBody<PubDictValue> deailByCode(@RequestBody Map<String, String> siteCode) {
		String code = siteCode.get("siteCode");
		return services.findByCode(code);
	}

	/**
	 * 详情
	 *
	 * @param pubDictValueDetail
	 * @return
	 */
	@GetMapping("/findDictTops")
	@ApiOperation(value = "查询最上级字典", notes = "查询最上级字典")
	public LyhtResultBody<List<PubDictValueTree>> getDictIsNotParent(PubDictValueDetail pubDictValueDetail) {
		return services.getDictIsNotParent(pubDictValueDetail);
	}

	/**
	 * 详情
	 *
	 * @param ParentNm
	 * @return
	 */
	@GetMapping("/findDictByParent")
	@ApiOperation(value = "根据父级内码查询字典", notes = "根据父级内码查询字典")
	public LyhtResultBody<List<PubDictValueTree>> getDictByParent(String ParentNm) {
		return services.getDictByParent(ParentNm);
	}

	/**
	 * 保存
	 * 
	 * @param pubdictvalue
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody<PubDictValue> save(PubDictValue pubdictvalue) {
		return services.save(pubdictvalue);
	}

	/**
	 * 删除getListByCateTree
	 * 
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "按id删除,用逗号“,”拼接", notes = "批量删除和删除")
	@GetMapping("/delete")
	public LyhtResultBody<String> delete(String ids) {
		return services.deleteByIds(ids);
	}

	/**
	 * 根据字典分类名查询字典列表
	 *
	 * @param dictCate
	 * @return
	 */
	@ApiOperation(value = "根据字典分类名查询字典列表", notes = "根据字典分类名查询字典列表")
	@GetMapping("/getListByCate01")
	public LyhtResultBody<List<PubDictValue>> getListByCate01(String dictCate, String parentCode,
			HttpServletRequest request) {
		List<PubDictValue> pubDictValueList = null;
		try {
			pubDictValueList = services.getdictByCate01(dictCate, parentCode, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(pubDictValueList);

	}

	/**
	 * 根据字典分类名查询字典列表
	 *
	 * @param dictCate
	 * @return
	 */
	@ApiOperation(value = "根据字典分类名查询字典列表", notes = "根据字典分类名查询字典列表")
	@GetMapping("/getListByCate")
	public LyhtResultBody<List<PubDictValue>> getListByCate(String dictCate, String parentCode,
			HttpServletRequest request) {
		List<PubDictValue> pubDictValueList = null;
		try {
			pubDictValueList = services.getdictByCate(dictCate, parentCode, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(pubDictValueList);

	}

	/**
	 * 根据字典分类名查询字典列表
	 *
	 * @param dictCate
	 * @return
	 */
	@ApiOperation(value = "根据字典分类名查询字典列表", notes = "根据字典分类名查询字典列表")
	@GetMapping("/getListByCates")
	public LyhtResultBody<List<PubDictValue>> getListByCates(String dictCate, HttpServletRequest request) {
		List<PubDictValue> pubDictValueList = null;
		try {
			pubDictValueList = services.getdictByCate(dictCate, "0", request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(pubDictValueList);

	}

	/**
	 * 根据字典分类名查询字典列表
	 *
	 * @param dictCate
	 * @return
	 */
	@ApiOperation(value = "根据字典分类名查询字典列表及子级", notes = "根据字典分类名查询字典列表及子级")
	@GetMapping("/getListByCateTree")
	public LyhtResultBody<List<PubDictValue>> getListByCateTree(String dictCate, String sonDictCate,
			HttpServletRequest request) {
		List<PubDictValue> pubDictValueList = null;
		try {
			pubDictValueList = services.getdictByCate(dictCate, "", request);
			List<PubDictValueTree> pubDictValueSons = services.getdictByCateSon(sonDictCate);
			for (PubDictValue mp : pubDictValueList) {
				List<PubDictValueTree> children = new ArrayList<>();
				for (PubDictValueTree mpSon : pubDictValueSons) {
					if (StringUtils.equals(mp.getNm() + "", mpSon.getParentNm() + "")) {
						children.add(mpSon);
					}
				}
				if (children.size() > 0) {
					mp.setChildren(children);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		return new LyhtResultBody<>(pubDictValueList);

	}

	@ApiOperation(value = "按字典分类查询（树）", notes = "按字典分类查询（树）")
	@ApiImplicitParam(name = "type", value = "字典分类", paramType = "query")
	@GetMapping("/tree")
	public LyhtResultBody<List<PubDictValueTreeVO>> getTree(String type) {
		List<PubDictValueTreeVO> treeByType = null;
		try {
			treeByType = services.getTreeByType(type);
		} catch (Exception e) {
			log.error("=====PubDictValueController=====Method:==getTree=====param:==" + type, e);
			throw new LyhtRuntimeException("网络异常，请稍后重试！");
		}
		return new LyhtResultBody<>(treeByType);
	}

}
