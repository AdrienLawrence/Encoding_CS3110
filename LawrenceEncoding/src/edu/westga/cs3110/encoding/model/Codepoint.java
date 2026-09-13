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
		
		if (!isValidCodepoint(value)) {
			
			throw new IllegalArgumentException("Codepoint is outside the valid Unicode range");
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
		
	    int value = Integer.parseUnsignedInt(this.codepoint, 16);

	    if (value <= 0x7F) {
	    	
	        return String.format("%02X", value);
	        
	    } else if (value <= 0x7FF) {
	    	
	        int firstByte = 0xC0 | (value >> 6);
	        int secondByte = 0x80 | (value & 0x3F);

	        return String.format("%02X%02X", firstByte, secondByte);
	        
	    } else if (value <= 0xFFFF) {
	    	
	        int firstByte = 0xE0 | (value >> 12);
	        int secondByte = 0x80 | ((value >> 6) & 0x3F);
	        int thirdByte = 0x80 | (value & 0x3F);

	        return String.format("%02X%02X%02X", firstByte, secondByte, thirdByte);
	        
	    } else {
	    	
	        int firstByte = 0xF0 | (value >> 18);
	        int secondByte = 0x80 | ((value >> 12) & 0x3F);
	        int thirdByte = 0x80 | ((value >> 6) & 0x3F);
	        int fourthByte = 0x80 | (value & 0x3F);

	        return String.format("%02X%02X%02X%02X", firstByte, secondByte, thirdByte, fourthByte);
	        
	    }
	}
	
	private static boolean isValidCodepoint(int value) {
		
		return (value <= 0x10FFFF) && !(value >= 0xD800 && value <= 0xDFFF);
		
	}
	
}
