package com.lyht.business.doc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.Constants;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.doc.entity.DocInfo;
import com.lyht.business.doc.entity.DocStatist;
import com.lyht.business.doc.service.DocDistributeService;
import com.lyht.business.doc.service.DocInfoService;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.system.pojo.SysDept;
import com.lyht.system.pojo.SysStaff;
import com.lyht.util.ExportExcelWrapper;
import com.lyht.util.ImportExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据档案权限：
 * 先按部门划分权限（各参建单位只能看到自己单位的档案数据）。
 * 用户可选择某条档案数据，共享给某用户查阅
 * （可共享给多个人，被共享人不允许在共享文档，被共享人只有查看档案的权限）。
 * @author wzw
 *
 */
@RequestMapping("/doc/info")
@Api(value = "/doc/info", tags = "档单管理相关Api")
@RestController
public class DocInfoController {

	private static Logger logger = LoggerFactory.getLogger(DocInfoController.class);

	@Autowired
	private DocInfoService infoService;

	@Autowired
	private PubFilesService pubFilesService;
	
	@Autowired
	private DocDistributeService distributeService;
	
	@ApiOperation(value = "档案分享分页查询", notes = "分页查询")
	@PostMapping("/shareDoc")
	public LyhtResultBody<List<Map>> shareDoc(LyhtPageVO lyhtPageVO, String staffNm, String docType){
		return infoService.shareDoc(lyhtPageVO, staffNm, docType);
	}
	
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "分页查询，排序查询，条件查询，数字档案模块dType档案类别 subName 内容分类  word 模糊匹配 权限查询 "
			+ " state字段 1可编辑 0不可编辑", notes = "分页查询")
	@PostMapping("/getList")
	public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO,HttpServletRequest request, String subName, String word, String dType) {
        Object obj1 =  request.getSession().getAttribute(Constants.SESSION_STAFF);//获取session中的当前登录的账户信息
        Object obj2 = request.getSession().getAttribute(Constants.SESSION_DEPT);//获取session中的当前登录的部门信息
        SysStaff sysStaff = (SysStaff) obj1;
        SysDept sysDept = (SysDept) obj2;
        return infoService.page(lyhtPageVO, subName, word, dType, sysDept.getFcode(), sysStaff.getNm());
	}
	
//	@SuppressWarnings("rawtypes")
//	@ApiOperation(value = "分页查询，排序查询，条件查询，数字档案模块dType档案类别 subName 内容分类  word 模糊匹配 level行政区匹配 ", notes = "分页查询")
//	@PostMapping("/getList")
//	public LyhtResultBody<List<Map>> getList(LyhtPageVO lyhtPageVO, String subName, String word, String dType,
//			String level) {
//		return infoService.page(lyhtPageVO, subName, word, dType);
//	}

	@ApiOperation(value = "新增(id与nm为空),修改(需要id与nm)", notes = "新增 修改")
	@PostMapping("/save")
	public LyhtResultBody<DocInfo> save(DocInfo docInfo) {
		return infoService.save(docInfo);
	}

	@ApiOperation(value = "根据id进行删除", notes = "单个删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id, String nm) {
		pubFilesService.deleteBytablePkColumn(request, nm);
		distributeService.deteleByDataNm(nm);
		return infoService.delete(id);
	}

	@ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除")
	@GetMapping("/batchDel")
	public LyhtResultBody<String> batchDel(HttpServletRequest request, String ids) {
		List<DocInfo> infoList = infoService.getByids(ids);
		for (DocInfo docInfo : infoList) {
			pubFilesService.deleteBytablePkColumn(request, docInfo.getNm());
		}
		return infoService.batchDel(ids);
	}

	/**
	 * 导入Excel数据
	 * 
	 * @param multipartFile
	 */
	@ApiOperation(value = "按Excel对应模板导入数据", notes = "Excel导入")
	@PostMapping("/import")
	public void importExcel(@RequestParam("file") MultipartFile multipartFile) {
		try {
			List<DocInfo> list = ImportExcelUtil.importExcel(multipartFile.getInputStream(), ImportExcelUtil.PATTERN, 0,
					1, 0, DocInfo.class);
			infoService.importExcelData(list);
		} catch (Exception e) {
			logger.error("excel导入失败：", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
	}

	/**
	 * 导出Excel
	 * 
	 * @return
	 */
	@ApiOperation(value = "按查询条件导出Excel dType档案类别 subName 内容分类  word 模糊匹配 level行政区匹配 ", notes = "Excel导出")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/export")
	public void exportExcel(HttpServletResponse response, String subName, String word, String dType, String level) {
		System.out.println("export");
		List<Map> list = infoService.list(subName, word, dType, level);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).put("key", i+1);
		}
		String title = dType;
		String fileName = title;
		String[] headers = { "序号", "编号", "内容分类", "档案名称", "责任部门", "归档人", "上传时间", "最后更新时间" };
		String[] columnNames = { "key", "dCode", "subName", "dName", "deptName", "staffName", "pigeonholeDate",
				"lastTime" };
		try {
			ExportExcelWrapper<List<Map>> exportExcelWrapper = new ExportExcelWrapper<>();
			exportExcelWrapper.exportExcel(fileName, title, headers, columnNames, list, response,
					ExportExcelWrapper.EXCEl_FILE_2007);
		} catch (Exception e) {
			logger.error("excel导出失败：", e);
			throw new LyhtRuntimeException(LyhtExceptionEnums.INVALID_PARAMETERS);
		}
	}

	@ApiOperation(value = "按类型统计", notes = "分页查询")
	@PostMapping("/type/statist")
	public LyhtResultBody<List<DocStatist>> getTypeStatist(DocStatist docStatist) {
		List<DocStatist> docStatistList = infoService.getTypeStatist(docStatist);
		return new LyhtResultBody<>(docStatistList);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/serach")
	@ApiOperation(value = "Serach 模糊查询", notes = "模糊分页查询")
	public LyhtResultBody<List<Map>> serach(String word) {
		System.out.println(word);
		return infoService.serach(word);
	}

}
