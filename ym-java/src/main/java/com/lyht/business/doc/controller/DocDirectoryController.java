package com.lyht.business.doc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.doc.dao.DocDirectoryDao;
import com.lyht.business.doc.entity.DocDirectory;
import com.lyht.business.doc.pojo.DocDirectoryDetail;
import com.lyht.business.doc.pojo.DocDirectoryVO;
import com.lyht.business.doc.service.DocDirectoryService;
import com.lyht.util.ExportExcelWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/doc/directory")
@Api(value = "/doc/directory", tags = "档案管理 档案字典相关Api")
@RestController
public class DocDirectoryController {
	private static Logger logger = LoggerFactory.getLogger(DocDirectoryController.class);

	@Autowired
	DocDirectoryService directoryService;
	
	@Autowired
	DocDirectoryDao dao;

	@ApiOperation(value = "档案编号", notes = "档案编号")
	@PostMapping("/dcode")
	public LyhtResultBody<String> dcode(DocDirectory docDirectory) {
		return directoryService.dcode(docDirectory);
	}

	@ApiOperation(value = "保存", notes = "保存")
	@PostMapping("/save")
	public LyhtResultBody<DocDirectory> save(DocDirectory docDirectory) {
		System.out.println(docDirectory);
		String oldFCode = docDirectory.getfCode();
		DocDirectory after = new DocDirectory();
		if (StringUtils.isNotBlank(docDirectory.getNm())) {// 修改
			after = directoryService.update(docDirectory);
			dao.save(docDirectory);
			directoryService.updateDirSon(oldFCode, after.getfCode(),after.getDocType());
		} else {//新增
			after = directoryService.save(docDirectory);
		}
		return new LyhtResultBody<>(after);
		//return directoryService.save1(docDirectory);
	}

	@ApiOperation(value = "删除 存在子类  子类一并删除", notes = "删除")
	@GetMapping("/delete")
	public LyhtResultBody<String> deleteByFCode(String fCode) {
		return directoryService.deleteByFCode(fCode);
	}

	@ApiOperation(value = "查询所有内容分类 字典", notes = "查询")
	@PostMapping("/page")
	public LyhtResultBody<List<DocDirectory>> page(LyhtPageVO lyhtPageVO, String docType) {
		return directoryService.page(docType, lyhtPageVO);
	}

	@ApiOperation(value = "查询所有内容分类", notes = "查询")
	@PostMapping("/getList")
	public LyhtResultBody<List<DocDirectory>> list() {
		return directoryService.list();
	}

	// 获取下拉框
	@ApiOperation(value = "查询所有内容分类 下拉框字典 字典页使用 value值为id", notes = "查询")
	@PostMapping("/getSelf")
	public List<DocDirectoryVO> select() {
		return directoryService.getSuperIdSelect();
	}

	// 获取下拉框 档案使用value值为nm
	@ApiOperation(value = "查询所有内容分类 下拉框字典 档案使用value值为nm", notes = "查询")
	@PostMapping("/getDocDir")
	public List<DocDirectoryDetail> getDocDir(String docType) {
		return directoryService.getDocDirSuper(docType);
	}

	/**
	 * 导出Excel
	 * 
	 * @return
	 */
	@ApiOperation(value = "按查询条件导出Excel dType档案类别 subName 内容分类  word 模糊匹配 level行政区匹配 ", notes = "Excel导出")
	@SuppressWarnings("rawtypes")
	@GetMapping("/export")
	public void exportExcel(HttpServletResponse response) {
		List<Map> list = directoryService.getList();
		String title = "数字档案分类字典";
		String fileName = title;
		String[] headers = { "名称", "编号", "备注" };
		String[] columnNames = { "subName", "subCode", "remark" };
		try {
			ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
			exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
					ExportExcelWrapper.EXCEl_FILE_2007);
		} catch (Exception e) {
			logger.error("excel导出失败：", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
	}
}
