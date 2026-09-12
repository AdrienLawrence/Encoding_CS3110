package edu.westga.cs3110.encoding.model;

public class Codepoint {
	
	private final String codepoint;
	
	private static final int UTF16_OFFSET = 0x10000;
	
	public Codepoint(String codepoint) {
		
		if (codepoint == null || codepoint.isBlank()) {
			throw new IllegalArgumentException("Codepoint cannot be blank");
		}
		
		int value;
		
		try {
			
			value = Integer.parseUnsignedInt(codepoint, 16);
			
		} catch (NumberFormatException e) {
			
			throw new IllegalArgumentException("Codepoint must be hexadecimal");
			
		}
		
		if (value > 0x10FFFF) {
			throw new IllegalArgumentException("Codepoint is outside the Unicode range");
		}
		this.codepoint = codepoint;
	}
	
	public String toUTF32() {
		
		int value = Integer.parseUnsignedInt(this.codepoint, 16);
		
		return String.format("%08X", value);
	}
	
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
	
	public String toUTF8() {
		
		return "";
	}
	
}
