package edu.westga.cs3110.encoding.model;

/**
 * 
 * Codepoint class that takes in a string and parses it to a codepoint
 * and encodes it with different methods
 * 
 * @author CS 3110
 * 
 * @version Fall 2026
 */
public class Codepoint {

	private static final int UTF16_OFFSET = 0x10000;

	private final String codepoint;

	/**
	 * Creates a codepoint object from a given input string
	 * 
	 * @param codepoint the input string
	 */
	public Codepoint(String codepoint) {

		if (codepoint == null || codepoint.isBlank()) {

			throw new IllegalArgumentException("Codepoint cannot be blank");

		}

		int value;

		try {

			value = Integer.parseUnsignedInt(codepoint, 16);

		} catch (NumberFormatException err) {

			throw new IllegalArgumentException("Codepoint must be hexadecimal");

		}

		if (!isValidCodepoint(value)) {

			throw new IllegalArgumentException("Codepoint is outside the valid Unicode range");

		}

		this.codepoint = codepoint;

	}

	/**
	 * Creates a UTF32 Encoding with the codepoint field
	 * 
	 * @return a hexadecimal string encoding for the codepoint in UTF32
	 */
	public String toUTF32() {

		int value = Integer.parseUnsignedInt(this.codepoint, 16);

		return String.format("%08X", value);

	}

	/**
	 * Creates a UTF16 Encoding with the codepoint field
	 * 
	 * @return a hexadecimal string encoding for the codepoint in UTF16
	 */
	public String toUTF16() {

		int value = Integer.parseUnsignedInt(this.codepoint, 16);

		if (value <= 0xFFFF) {

			return String.format("%04X", value);

		}

		value -= UTF16_OFFSET;

		int leftBits = 0xD800 + (value >> 10);

		int rightBits = 0xDC00 + (value & 0x3FF);

		return String.format("%04X%04X", leftBits, rightBits);

	}

	/**
	 * Creates a UTF8 Encoding with the codepoint field
	 * 
	 * @return a hexademcial string encoding for the codepoint in UTF8
	 */
	public String toUTF8() {

		int value = Integer.parseUnsignedInt(this.codepoint, 16);

		if (value <= 0x7F) {

			return encodeOneByteUTF8(value);

		} else if (value <= 0x7FF) {

			return encodeTwoByteUTF8(value);

		} else if (value <= 0xFFFF) {

			return encodeThreeByteUTF8(value);

		} else {

			return encodeFourByteUTF8(value);

		}

	}

	private static String encodeOneByteUTF8(int value) {

		return String.format("%02X", value);

	}

	private static String encodeTwoByteUTF8(int value) {

		int firstByte = 0xC0 | (value >> 6);

		int secondByte = continuationByte(value, 0);

		return String.format("%02X%02X", firstByte, secondByte);

	}

	private static String encodeThreeByteUTF8(int value) {

		int firstByte = 0xE0 | (value >> 12);

		int secondByte = continuationByte(value, 6);

		int thirdByte = continuationByte(value, 0);

		return String.format("%02X%02X%02X", firstByte, secondByte, thirdByte);

	}

	private static String encodeFourByteUTF8(int value) {

		int firstByte = 0xF0 | (value >> 18);

		int secondByte = continuationByte(value, 12);

		int thirdByte = continuationByte(value, 6);

		int fourthByte = continuationByte(value, 0);

		return String.format("%02X%02X%02X%02X", firstByte, secondByte, thirdByte, fourthByte);

	}

	private static int continuationByte(int value, int shift) {

		return 0x80 | ((value >> shift) & 0x3F);

	}

	private static boolean isValidCodepoint(int value) {

		return (value <= 0x10FFFF) && !(value >= 0xD800 && value <= 0xDFFF);

	}

}