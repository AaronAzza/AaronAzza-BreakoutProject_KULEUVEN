package breakout;

import static breakout.GameMapParser.OGP_MAP;
import static breakout.GameMapParser.OOP_MAP;
import static breakout.GameMapParser.descr1;
import static breakout.GameMapParser.descr2;
import static breakout.GameMapParser.descr3;
import static breakout.GameMapParser.descr4;
import static breakout.GameMapParser.descr5;
import static breakout.GameMapParser.descr6;
import static breakout.GameMapParser.descr7;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameMapParserTest {
	@BeforeEach
	void setUp() {
	}
	
	@Test
	void descrTest() {
		assertNotNull(GameMapParser.descr1);
		assertNotNull(GameMapParser.descr2);
		assertNotNull(GameMapParser.descr3);
		assertNotNull(GameMapParser.descr4);
		assertNotNull(GameMapParser.descr5);
		assertNotNull(GameMapParser.descr6);
		assertNotNull(GameMapParser.descr7);
		assertNotNull(GameMapParser.OGP_MAP);
		assertNotNull(GameMapParser.OOP_MAP);
		
		assertTrue(Arrays.stream(GameMapParser.descr1).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.descr2).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.descr3).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.descr4).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.descr5).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.descr6).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.descr7).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.OGP_MAP).allMatch(line -> line != null));
		assertTrue(Arrays.stream(GameMapParser.OOP_MAP).allMatch(line -> line != null));
		
		assertTrue(GameMapParser.descr1.length >= 1);
		assertTrue(GameMapParser.descr2.length >= 1);
		assertTrue(GameMapParser.descr3.length >= 1);
		assertTrue(GameMapParser.descr4.length >= 1);
		assertTrue(GameMapParser.descr5.length >= 1);
		assertTrue(GameMapParser.descr6.length >= 1);
		assertTrue(GameMapParser.descr7.length >= 1);
		assertTrue(GameMapParser.OGP_MAP.length >= 1);
		assertTrue(GameMapParser.OOP_MAP.length >= 1);
		
		assertTrue(IntStream.range(1, descr1.length).allMatch(i -> descr1[0].length() == descr1[i].length()));
		assertTrue(IntStream.range(1, descr2.length).allMatch(i -> descr2[0].length() == descr2[i].length()));
		assertTrue(IntStream.range(1, descr3.length).allMatch(i -> descr3[0].length() == descr3[i].length()));
		assertTrue(IntStream.range(1, descr4.length).allMatch(i -> descr4[0].length() == descr4[i].length()));
		assertTrue(IntStream.range(1, descr5.length).allMatch(i -> descr5[0].length() == descr5[i].length()));
		assertTrue(IntStream.range(1, descr6.length).allMatch(i -> descr6[0].length() == descr6[i].length()));
		assertTrue(IntStream.range(1, descr7.length).allMatch(i -> descr7[0].length() == descr7[i].length()));
		assertTrue(IntStream.range(1, OGP_MAP.length).allMatch(i -> OGP_MAP[0].length() == OGP_MAP[i].length()));
		assertTrue(IntStream.range(1, OOP_MAP.length).allMatch(i -> OOP_MAP[0].length() == OOP_MAP[i].length()));
	}
	
	@Test
	void parseTest() {
		BreakoutState s = GameMapParser.parse(descr1, 5000, 1000);
		assertNotNull(s);
		assertEquals(descr1.length, s.getBrickGrid().getRowCount());
		assertEquals(descr1[0].length(), s.getBrickGrid().getColumnCount());
		assertEquals(5000, s.getBrickGrid().getBrickWidth());
		assertEquals(1000, s.getBrickGrid().getBrickHeight());
		assertEquals(1, s.getBalls().size());
		assertEquals(5000, s.getPaddle().getHalfWidth());
		assertEquals(50, s.getPaddle().getSpeed());
	}

}
