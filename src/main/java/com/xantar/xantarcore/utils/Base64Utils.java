package com.xantar.xantarcore.utils;

import java.util.Base64;

public class Base64Utils {

	public static byte[] decodeImage(String base64Image) {
		return base64Image != null ? Base64.getDecoder().decode(base64Image) : null;
	}


	public static String encodeImage(byte[] image) {
		return image != null ? Base64.getEncoder().encodeToString(image) : null;
	}
}
