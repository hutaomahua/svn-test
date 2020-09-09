package com.lyht.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeUtil {

	public static final String IMGPATH = "C:\\Users\\NING MEI\\Pictures\\Saved Pictures\\MyQRCode.PNG"; // 本地测试路径
	
	// 二维码尺寸
	private static final int QRCODE_SIZE = 1200;
	// LOGO宽度
	private static final int WIDTH = 300;
	// LOGO高度
	private static final int HEIGHT = 300;

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 00);
		map.put("nm", "123123123");
		String jsonString = JSON.toJSONString(map);
		generateQRCodeImage(jsonString, 300, 300, IMGPATH);
	}
	
	/**
	 * 生成二维码
	 * @param map
	 */
	public static void generateQRCode(Map<String,Object> map,String qrcodeFilePath) {
		String jsonString = JSON.toJSONString(map);
		try {
			QRCodeUtil.generateQRCodeImage(jsonString, 300, 300, qrcodeFilePath);//生成图片
		} catch (WriterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
		
		Path path = FileSystems.getDefault().getPath(filePath);
		
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		
	}
}
