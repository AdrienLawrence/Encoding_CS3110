package edu.westga.cs3110.encoding.model;

public class Codepoint {
	
	private final String codepoint;
	
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
		
		return "";
	}
	
	public String toUTF16() {
		
		return "";
	}
	
	public String toUTF8() {
		
		return "";
	}
	
}
