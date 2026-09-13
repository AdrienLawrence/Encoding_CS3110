package edu.westga.cs3110.encoding.test.codepoint;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3110.encoding.model.Codepoint;

class CodepointTest {

	@Test
	void shouldCreateValidCodepoint() {
		assertDoesNotThrow(() -> new Codepoint("41"));
	}

	@Test
	void shouldNotAllowNullCodepoint() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint(null));
	}

	@Test
	void shouldNotAllowBlankCodepoint() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint(""));
	}

	@Test
	void shouldNotAllowWhitespaceCodepoint() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint("   "));
	}

	@Test
	void shouldNotAllowNonHexadecimalCodepoint() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint("XYZ"));
	}

	@Test
	void shouldNotAllowCodepointAboveUnicodeRange() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint("110000"));
	}

	@Test
	void shouldNotAllowSurrogateCodepoint() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint("D800"));
	}

	@Test
	void shouldEncodeUTF32() {
		Codepoint codepoint = new Codepoint("1F600");

		assertEquals("0001F600", codepoint.toUTF32());
	}

	@Test
	void shouldEncodeSingleUnitUTF16() {
		Codepoint codepoint = new Codepoint("41");

		assertEquals("0041", codepoint.toUTF16());
	}

	@Test
	void shouldEncodeSurrogatePairUTF16() {
		Codepoint codepoint = new Codepoint("1F600");

		assertEquals("D83DDE00", codepoint.toUTF16());
	}

	@Test
	void shouldEncodeMinimumSurrogatePairUTF16() {
		Codepoint codepoint = new Codepoint("10000");

		assertEquals("D800DC00", codepoint.toUTF16());
	}

	@Test
	void shouldEncodeOneByteUTF8() {
		Codepoint codepoint = new Codepoint("41");

		assertEquals("41", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeMaximumOneByteUTF8() {
		Codepoint codepoint = new Codepoint("7F");

		assertEquals("7F", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeMinimumTwoByteUTF8() {
		Codepoint codepoint = new Codepoint("80");

		assertEquals("C280", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeMaximumTwoByteUTF8() {
		Codepoint codepoint = new Codepoint("7FF");

		assertEquals("DFBF", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeMinimumThreeByteUTF8() {
		Codepoint codepoint = new Codepoint("800");

		assertEquals("E0A080", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeThreeByteUTF8() {
		Codepoint codepoint = new Codepoint("20AC");

		assertEquals("E282AC", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeMinimumFourByteUTF8() {
		Codepoint codepoint = new Codepoint("10000");

		assertEquals("F0908080", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeFourByteUTF8() {
		Codepoint codepoint = new Codepoint("1F600");

		assertEquals("F09F9880", codepoint.toUTF8());
	}

	@Test
	void shouldEncodeMaximumUnicodeCodepointUTF8() {
		Codepoint codepoint = new Codepoint("10FFFF");

		assertEquals("F48FBFBF", codepoint.toUTF8());
	}
}