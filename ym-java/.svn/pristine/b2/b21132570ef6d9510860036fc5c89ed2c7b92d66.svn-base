package com.lyht.publicAnnouncementExcel.common;

import com.bstek.ureport.export.ExportConfigure;
import com.bstek.ureport.export.ExportConfigureImpl;
import com.bstek.ureport.export.ExportManager;
import com.google.common.collect.ImmutableMap;
import com.lyht.base.common.enums.imp.LyhtExceptionEnums;
import com.lyht.base.common.exception.LyhtRuntimeException;
import com.lyht.business.abm.removal.dao.AbmOwnerDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Ureport {
	@Resource(name = ExportManager.BEAN_ID)
	private ExportManager exportManager;

	@Value("${lyht.ureport.path}")
	private String filepath;

	/**
	 * 生成文件通用方法
	 * 
	 * @param relativePath 目标文件名
	 * @param templateName 模板名称
	 * @param param        参数
	 * @param outputStream 输出流
	 */
	public void createReport(String relativePath, String templateName, String fileName, Map<String, Object> param,
			OutputStream outputStream, HttpServletResponse response) {
		try {
			ExportConfigure exportConfigure = new ExportConfigureImpl(templateName, ImmutableMap.copyOf(param),
					outputStream);
			if (relativePath.endsWith(".xlsx")) {
				exportManager.exportExcel(exportConfigure);
			} else if (relativePath.endsWith(".pdf")) {
				exportManager.exportPdf(exportConfigure);
			} else if (relativePath.endsWith(".doc")) {
				exportManager.exportWord(exportConfigure);
			}
			// 下载
			FileUtil.download(relativePath, fileName, true, response);
			// 删除临时目录
			// FileUtil.delete(relativePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
