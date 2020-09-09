package com.lyht.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileToBase64 {
	
	public static void main(String[] args) {
		String base64 = encryptToBase64("D:\\Server\\uploads\\tuoba\\word\\托巴逐年补偿协议书.doc");
		System.out.println(base64);
	}
	
	public static String encryptToBase64(String filePath) {
		if (filePath == null) {
			return null;
		}
		try {
			byte[] b = Files.readAllBytes(Paths.get(filePath));
			return Base64.getEncoder().encodeToString(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		return null;
	}
}
