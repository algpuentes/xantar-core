package com.xantar.xantarcore.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Base64;

import org.junit.jupiter.api.Test;

public class Base64UtilsTest {

	public static byte[] decodeImage(String base64Image) {
		return base64Image != null ? Base64.getDecoder().decode(base64Image) : null;
	}

	public static String encodeImage(byte[] image) {
		return image != null ? Base64.getEncoder().encodeToString(image) : null;
	}

	/*
	 * decodeImage(String)
	 * */
	@Test
	void decodeImage_withNullString_returnsNull() {
		final var result = Base64Utils.decodeImage(null);

		assertNull(result);
	}

	@Test
	void decodeImage_withNotNullString_returnsNotNullValue() {
		final var result = Base64Utils.decodeImage("String");

		assertNotNull(result);
	}

	/*
	 * decodeImage(String)
	 * */
	@Test
	void encodeImage_withNullString_returnsNull() {
		final var result = Base64Utils.encodeImage(null);

		assertNull(result);
	}

	@Test
	void encodeImage_withNotNullString_returnsNotNullValue() {
		final var result = Base64Utils.encodeImage("String".getBytes());

		assertNotNull(result);
	}

	/**
	 * encode and decode
	 * */
	//TODO: add propterty based test
	@Test
	void decode_withEncodedString_returnsOriginalString() {
		final var originalEncodedString = "YmFzaWM=";

		final byte[] decodedImage = Base64Utils.decodeImage(originalEncodedString);

		final var encodedString = Base64Utils.encodeImage(decodedImage);

		assertEquals(originalEncodedString, encodedString, "Reverse operation should not change the originar string.");
	}

}
