package com.lyht.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilImg {

	public final static String FILE_PATH_NAME = "D:\\WorkSpace\\Code\\tuoba\\file";

	public static void main(String[] args) {
		String excelFile = FILE_PATH_NAME + "\\1.xlsx";
		FileOutputStream fileOut = null;
		BufferedImage bufferedImage = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 255, 255, (short) 3, 1, (short) 4, 5);
		try {
			bufferedImage = ImageIO.read(new File(FILE_PATH_NAME + "\\1.png"));
			ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
			FileInputStream fileInputStream = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			AddPictureToExcel(workbook, byteArrayOutputStream,anchor);
			fileOut = new FileOutputStream(excelFile);
			workbook.write(fileOut);
			System.out.print(excelFile + "  Success!");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void PictureToExcel(String filePathExcel,String filePathPicture,String pictureType,XSSFClientAnchor anchor) {
		String excelFile =filePathExcel;
		FileOutputStream fileOut = null;
		BufferedImage bufferedImage = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			bufferedImage = ImageIO.read(new File(filePathPicture));
			ImageIO.write(bufferedImage, pictureType, byteArrayOutputStream);
			FileInputStream fileInputStream = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			AddPictureToExcel(workbook, byteArrayOutputStream,anchor);
			fileOut = new FileOutputStream(excelFile);
			workbook.write(fileOut);
			System.out.print(excelFile + "  Success!");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				fileOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void AddPictureToExcel(XSSFWorkbook workbook, ByteArrayOutputStream byteArrayOutputStream,XSSFClientAnchor anchor) {
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		drawing.createPicture(anchor,
				workbook.addPicture(byteArrayOutputStream.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG));
	}
}
