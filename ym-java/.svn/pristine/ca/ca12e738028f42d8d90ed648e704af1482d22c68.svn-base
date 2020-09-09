package com.lyht.business.engineering.controller;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.engineering.entity.EngineeringNewTown;
import com.lyht.business.engineering.service.EngineeringNewTownService;
import com.lyht.business.engineering.vo.EngineeringNewTownVO;
import com.lyht.business.engineering.vo.EngineeringNewTownBean;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.FileDownUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "/engineering/newTown", tags = "集镇新址建设实施信息表Api")
@RestController
@RequestMapping("/engineering/newTown")
public class EngineeringNewTownController {

	@Autowired
	private EngineeringNewTownService service;
	
	@Autowired
	private PubFilesService pubFilesService;

	@Value("${lyht.file.upload.path}")
	private String filePath;
	/**
	 * 分页
	 * @param lyhtPageVO
	 * @param engineeringNewTownVo
	 * @return
	 */
	@ApiOperation(value = "分页查询，排序查询，条件查询", notes = "分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<EngineeringNewTownVO>> page(LyhtPageVO lyhtPageVO, EngineeringNewTownVO engineeringNewTownVo) {
		return service.page(lyhtPageVO, engineeringNewTownVo);
	}
	/**
	 * 文件下载
	 *
	 * @param request
	 * @param response
	 * @param pubFileEntity
	 */
	@ApiOperation(value = "文件下载", notes = "文件下载")
	@GetMapping("/XYQDdownload/{fileName}")
	public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
		String fileNames = "";
		if(fileName.equals("fskhaz")){
			fileNames = "托巴水电站-分散靠后安置";
		}else if(fileName.equals("hbaz")){
			fileNames = "托巴水电站-货币安置";
		}else if(fileName.equals("jzaz")){
			fileNames = "托巴水电站-集中安置";
		}else if(fileName.equals("zjdg")){
			fileNames = "资金代管协议";
		}
		FileDownUtil.getFile(filePath + "/doc/",
				fileNames + "." + "docx", response);
	}
	/**
	 * 单个删除
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	@ApiOperation(value = "删除", notes = "删除")
	public LyhtResultBody<Integer> delete(HttpServletRequest request,Integer id) {
		// 参数校验
		if (id == null) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
		EngineeringNewTown engineeringNewTown = service.findOneById(id);
		pubFilesService.deleteBytablePkColumn(request, engineeringNewTown.getNm());
		return service.delete(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/batchDel")
	@ApiOperation(value = "批量删除", notes = "批量删除")
	public LyhtResultBody<String> batchDel(String ids) {
		return service.batchDel(ids);
	}
	
	/**
	 * 添加 修改
	 * @param engineeringNewTown
	 * @return
	 */
	@ApiOperation(value = "新增修改", notes = "新增修改 修改必须传入id,nm")
	@PostMapping("/save")
	public LyhtResultBody<EngineeringNewTown> save(EngineeringNewTown engineeringNewTown) {
		return service.save(engineeringNewTown);
	}
	
	
}
