package com.lyht.business.land.controller;

import com.lyht.Constants;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.land.bean.LandProblemDetail;
import com.lyht.business.land.entity.LandProblem;
import com.lyht.business.land.service.LandProblemService;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.Randomizer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用地报批存在问题
 * 
 * @author hxl
 *
 */
@Api(value = "/land/apply/problem", tags = "用地报批存在问题相关api")
@RestController
@RequestMapping("/land/apply/problem")
public class LandProblemController {
//	private static Logger logger = Logger.getLogger(LandProblemController.class);

	@Autowired
	private LandProblemService landProblemService;

	/**
	 * 保存
	 * @param landProblemEntity
	 * @return
	 */
	@ApiOperation(value = "新增（id与nm为空），修改（需要id与nm）", notes = "新增，修改")
	@PostMapping("/update")
	public LyhtResultBody<LandProblem> save(LandProblem landProblemEntity,HttpServletRequest request) {
		SysStaff sysStaff = (SysStaff) request.getSession().getAttribute(Constants.SESSION_STAFF);
		if(landProblemEntity.getId()==null){
			landProblemEntity.setNm(Randomizer.generCode(10));
			landProblemEntity.setCreateStaffNm(sysStaff.getNm());
			landProblemEntity.setCreateTime(new Date());
		}
		LandProblem save = landProblemService.save(landProblemEntity);
		return new LyhtResultBody<>(save);
	}

	/**
	 * 分页
	 * @param lyhtPageVO
	 * @param landProblemDetail
	 * @return
	 */
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<LandProblemDetail>> page(LyhtPageVO lyhtPageVO, LandProblem landProblemDetail) {
		return landProblemService.page(lyhtPageVO, landProblemDetail);
	}
	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "按id删除", notes = "删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(Integer id) {
		landProblemService.delete(id);
		return new LyhtResultBody<>(id);
	}

}
