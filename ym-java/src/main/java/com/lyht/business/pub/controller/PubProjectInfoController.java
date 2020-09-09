package com.lyht.business.pub.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.dao.PubProjectInfoDao;
import com.lyht.business.pub.entity.PubProjectInfo;
import com.lyht.business.pub.service.PubProjectInfoService;
import com.lyht.business.pub.vo.PubProjectInfoDetail;
import com.lyht.util.CommonUtil;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 创建人： lj
 * 
 * 说明: 字典分类
 */
@RestController
@RequestMapping("/pubProjectInfo")
@Api(value = "/pubProjectInfo", tags = "工程项目")
public class PubProjectInfoController {
	private Logger log = LoggerFactory.getLogger(PubProjectInfoController.class);

	@Autowired
	private PubProjectInfoService services;

	@Autowired
	private PubProjectInfoDao dao;

	/**
	 * 分页
	 * 
	 * @param pubdictnamedetail
	 * @param lyhtPageVO
	 * @return
	 */
	@PostMapping("/page")
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	public LyhtResultBody<List<PubProjectInfoDetail>> page(LyhtPageVO lyhtPageVO, PubProjectInfo pubdictnamedetail) {
		return services.page(lyhtPageVO, pubdictnamedetail);
	}

	@PostMapping("/list")
	@ApiOperation(value = "条件查询", notes = "条件查询")
	public LyhtResultBody<List<PubProjectInfo>> list(PubProjectInfo pubdictnamedetail) {
		return services.list(pubdictnamedetail);
	}

	/**
	 * 详情
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/deail")
	@ApiOperation(value = "id查询", notes = "详情查询")
	public LyhtResultBody<PubProjectInfo> deail(Integer id) {
		PubProjectInfo bean = null;
		try {
			if (id > 0) {
				bean = services.findById(id);
			} else {
				throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
			}

		} catch (Exception e) {
			log.error("工程项目查询详情失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>(bean);
	}

	/**
	 * 保存
	 *
	 * @param pubprojectinfo
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/save")
	public LyhtResultBody<PubProjectInfo> save(PubProjectInfo pubprojectinfo) {
		try {
			if (CommonUtil.getIntValue(pubprojectinfo.getId() + "") == 0) {
				pubprojectinfo.setNm(Randomizer.generCode(10));
				pubprojectinfo.setFlag(0);
				dao.save(pubprojectinfo);
			}

			else {
				dao.save(pubprojectinfo);
			}

		} catch (Exception e) {
			log.error("工程项目保存失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<PubProjectInfo>();
	}

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "按id删除", notes = "批量删除和删除")
	@GetMapping("/delete")
	public LyhtResultBody<PubProjectInfo> delete(String ids) {
		try {

			services.deleteEntity(ids);

		} catch (Exception e) {
			log.error("工程项目删除失败！", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
		}
		return new LyhtResultBody<>();

	}
	
}
