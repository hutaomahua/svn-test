package com.lyht.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
/**
 * 文件转pdf
 * @author NING MEI
 *
 */
public class WordTOPDF {
	
	public static void main(String[] args) {
		String path = "";
		path = wordToPdf("托巴逐年补偿协议书.pdf","D:\\Server\\uploads\\tuoba\\word\\托巴逐年补偿协议书.doc", "D:\\","doc");
	}
	
	

	/**
	 * 将之前对应的word文件转换成pdf，然后预览pdf文件
	 * orgFile :生成文件文件名.后缀   orgPath:转换文件   toPath:生成文件地址 ，suffix:转换文件后缀
	 */
	public static String wordToPdf(String orgFile, String orgPath, String toPath, String suffix) {
		// 转换之后的pdf文件
		String targetFile = orgFile.replace(suffix, "pdf");
		File inputWord = new File(orgPath);
		File outputFile = new File(toPath + targetFile);
		try {
			InputStream docxInputStream = new FileInputStream(inputWord);
			OutputStream outputStream = new FileOutputStream(outputFile);
			IConverter converter = LocalConverter.builder().build();
			if (suffix.equals("doc")) {
				converter.convert(docxInputStream).as(DocumentType.DOC).to(outputStream).as(DocumentType.PDF).execute();
			} else if (suffix.equals("docx")) {
				converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF)
						.execute();
			} else if (suffix.equals("txt")) {
				converter.convert(docxInputStream).as(DocumentType.TEXT).to(outputStream).as(DocumentType.PDF)
						.execute();
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toPath + targetFile;
	}

}
