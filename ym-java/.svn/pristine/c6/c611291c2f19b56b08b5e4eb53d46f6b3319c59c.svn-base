package com.lyht.business.letter.controller;

import java.io.IOException;
import java.util.List;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.land.controller.LandApplyController;
import com.lyht.util.CustomException;
import com.lyht.util.FileDownUtil;
import com.lyht.util.ImportExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.letter.entity.Letters;
import com.lyht.business.letter.service.LettersService;
import com.lyht.business.letter.vo.LettersVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;

/**
 * @author HuangTianhao
 * @date 2019年10月30日17:08:14
 */

@RequestMapping("/letters")
@Api(value = "/letters", tags = "信访表相关api")
@RestController
public class LettersController {
	private static Logger logger = Logger.getLogger(LettersController.class);

	@Value("${lyht.file.upload.path}")
	private String filePath;
	@Autowired
	private LettersService lettersService;
	
	@PostMapping("/page")
	public LyhtResultBody<List<LettersVO>> page( LyhtPageVO lyhtPageVO, LettersVO lettersVO){
		return lettersService.page(lyhtPageVO, lettersVO);
	}

	@ApiOperation(value = "新增(id与nm为空),修改(id不为空)", notes = "新增 修改")
	@PostMapping("/save")
	public LyhtResultBody<Letters> add(Letters letters) {
		return lettersService.addLetters(letters);
	}

	@ApiOperation(value = "根据id删除", notes = "单项删除")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(Integer id) {
		return lettersService.delete(id);
	}
/*
	@ApiOperation(value = "根据id查询", notes = "单项查询")
	@PostMapping("/get")
	public LyhtResultBody<Letters> get(Integer id) {
		return lettersService.getLettersById(id);
	}
	*/
	@ApiOperation(value = "传参为模糊查询", notes = "分类分页或模糊查询分页")
	public LyhtResultBody<List<Letters>> pageNameLike(String ptext,String category, LyhtPageVO lyhtPageVO) {
		return lettersService.pageByNameLike(ptext,category,lyhtPageVO);
	}
	    /**
     * 导入Excel数据
     *
     * @param multipartFile
     */
    @ApiOperation(value = "按Excel对应模板导入数据", notes = "Excel导入")
    @PostMapping("/importExcel")
    public LyhtResultBody<List<Letters>> importExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException, CustomException {
        List<Letters> result =null;
        String []  cloums = {"name","visitsTime","phone","sex","job","region","dwdz","matter","visitsContent","undertake"};
        List<Letters> list = ImportExcelUtil.importLetterExcel(multipartFile.getInputStream(),cloums,"yyyy-MM-dd",1,2,0,Letters.class);
        try {
            result =lettersService.importList(list);
        } catch (Exception e) {
            logger.error("excel导入失败：", e);
            throw new LyhtRuntimeException(LyhtExceptionEnums.FAILURE);
        }
        return new LyhtResultBody<>(result);
    }
	/**
	 * 下载导出模板
	 * @param
	 * @return
	 */
	@ApiOperation(value = "下载导出模板", notes = "导出模板")
	@GetMapping("/download")
	public void findApplyChartByRegion(@RequestParam("fileName")String fileName, HttpServletResponse response) {
		FileDownUtil.getFile(filePath+"/word/" +fileName,fileName, response);
	}
/*	@ApiOperation(value = "查询所有分类", notes = "查询所有分类")
	@PostMapping("/category")
public LyhtResultBody<List<Letters>> getCategoty() {
		List<String> list = lettersService.getCategoty();
		List<Letters> list2 = new  ArrayList<Letters>();
		for(String c:list) {
			//System.out.println(c);
			Letters l = new Letters();
			l.setCategory(c);
			list2.add(l);
		}
		LyhtResultBody<List<Letters>> lyht = new LyhtResultBody<List<Letters>>();
		lyht.setList(list2);
		return lyht;
	}
	
*/
	
}
