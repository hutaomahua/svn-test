package com.lyht.business.pub.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.base.common.vo.LyhtPageVO;
import com.lyht.base.common.vo.response.LyhtResultBody;
import com.lyht.business.pub.entity.PubFilesEntity;
import com.lyht.business.pub.service.PubFilesService;
import com.lyht.util.FileDownUtil;
import com.lyht.util.ZipUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 附件
 * 
 * @author hxl
 *
 */
@Api(value = "/pub/files", tags = "附件相关api")
@RestController
@RequestMapping("/pub/files")
public class PubFilesController {
	private Logger logger = LoggerFactory.getLogger(PubFilesController.class);
	
	@Autowired
	private PubFilesService pubFilesService;

	@Value("${lyht.file.upload.path}")
	private String filePath;

	/**
	 * 附件信息 数据保存
	 * 
	 * @param request
	 * @param bean
	 * @return
	 */
	@ApiOperation(value = "附件上传(多个)", notes = "附件上传")
	@PostMapping("/uploads")
	@ResponseBody
	public LyhtResultBody<List<PubFilesEntity>> uploads(HttpServletRequest request,
			@RequestParam(required = false, value = "files") MultipartFile[] files, PubFilesEntity pubFileEntity) {
		return pubFilesService.uploads(request, files, pubFileEntity);
	}

	/**
	 * 附件信息 单个删除
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "按id删除 附件", notes = "删除附件")
	@GetMapping("/delete")
	public LyhtResultBody<Integer> delete(HttpServletRequest request, Integer id) {
		return pubFilesService.delete(request, id);
	}

	/**
	 * 附件信息 批量删除
	 * 
	 * @param request
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "批量删除，id以英文逗号拼接", notes = "批量删除附件")
	@GetMapping("/batch")
	@ResponseBody
	public LyhtResultBody<String> batchDel(HttpServletRequest request, String ids) {
		return pubFilesService.batchDel(request, ids);
	}

	/**
	 * 附件详情
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "按id查询附件详情", notes = "附件详情")
	@GetMapping("/detail")
	public LyhtResultBody<PubFilesEntity> detail(Integer id) {
		return pubFilesService.findById(id);
	}

	/**
	 * 查询多个id对应的附件详情
	 * 
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "查询附件详情，id以英文逗号拼接", notes = "附件详情（多个）")
	@GetMapping("/many")
	public LyhtResultBody<List<PubFilesEntity>> findByIds(String ids) {
		return pubFilesService.findByIds(ids);
	}
	
	/**
	 * 附件信息列表（多条件、分页）
	 * 
	 * @return
	 */
	@ApiOperation(value = "附件    分页查询，条件查询", notes = "附件分页查询")
	@PostMapping("/page")
	public LyhtResultBody<List<PubFilesEntity>> page(LyhtPageVO lyhtPageVO, PubFilesEntity pubFileEntity) {
		return pubFilesService.page(lyhtPageVO, pubFileEntity);
	}

	/**
	 * 附件信息列表（多条件）
	 * 
	 * @return
	 */
	@ApiOperation(value = "附件    条件查询", notes = "附件条件查询")
	@PostMapping("/list")
	public LyhtResultBody<List<PubFilesEntity>> list(PubFilesEntity pubFileEntity) {
		return pubFilesService.list(pubFileEntity);
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @param response
	 * @param pubFileEntity
	 */
	@ApiOperation(value = "文件下载", notes = "文件下载")
	@GetMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response, PubFilesEntity pubFileEntity) {
	    FileDownUtil.getFile(filePath + pubFileEntity.getFileUrl(),
				pubFileEntity.getFileName() + "." + pubFileEntity.getFileType(), response);
	}

	/**
	 * 查询文件个数
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询文件个数", notes = "查询文件个数")
	@PostMapping("/count")
	public LyhtResultBody<Integer> count(PubFilesEntity pubFileEntity) {
		return pubFilesService.count(pubFileEntity);
	}

	/**
	 * 多文件压缩下载
	 * 
	 * @param req
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/batch/download")
	public void batchDownload(HttpServletResponse response, @RequestParam(value="fileUrls[]") List<String>  fileUrls) {
		ZipOutputStream out = null;
		try {
			// 打包的文件名
			String packageName = "附件.zip";
			// 打包下载
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(packageName, "UTF-8"));
			out = new ZipOutputStream(response.getOutputStream());
			//设置压缩编码
			out.setEncoding("UTF-8");
			if (!CollectionUtils.isEmpty(fileUrls)) {
				for (String fileUrl : fileUrls) {
					ZipUtils.doZip(new File(filePath + fileUrl), out, "");
					response.flushBuffer();
				}
			} else {
				logger.error("文件下载失败！文件不存在");
				throw new LyhtRuntimeException(LyhtExceptionEnums.DOWNLOAD_FAILURE);
			}
		} catch (IOException e) {
			throw new LyhtRuntimeException(LyhtExceptionEnums.DOWNLOAD_FAILURE);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error("文件下载失败！：", e);
				throw new LyhtRuntimeException(LyhtExceptionEnums.DOWNLOAD_FAILURE);
			}
		}
	}
}
