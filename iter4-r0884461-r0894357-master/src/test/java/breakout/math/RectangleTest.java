package breakout.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RectangleTest {
	Point tl;
	Point br;
	Rectangle r1;
	Rectangle r2;
	
	@BeforeEach
	void initEach() {
		tl = new Point(2, 3);
		br = new Point(10, 10);
		r1 = new Rectangle(tl, br);
		r2 = new Rectangle(0, 0, 10, 5);
	}
	
	@Test
	void testConstructorsAndGetters() {
		assertThrows(IllegalArgumentException.class, () -> new Rectangle(null, br));
		assertThrows(IllegalArgumentException.class, () -> new Rectangle(tl, null));
		assertThrows(IllegalArgumentException.class, () -> new Rectangle(new Point(0,0), new Point(1, -1)));
		assertThrows(IllegalArgumentException.class, () -> new Rectangle(new Point(0,0), new Point(-2, 2)));
		
		assertThrows(IllegalArgumentException.class, () -> new Rectangle(0, 0, -2, 1));
		assertThrows(IllegalArgumentException.class, () -> new Rectangle(0, 0, 1, -3));
	}
	
	@Test
	void testGetters1() {
		assertEquals(2, r1.getLeft());
		assertEquals(10, r1.getRight());
		assertEquals(3, r1.getTop());
		assertEquals(10, r1.getBottom());
		assertEquals(8, r1.getWidth());
		assertEquals(7, r1.getHeight());
		assertEquals(tl, r1.getTopLeft());
		assertEquals(br, r1.getBottomRight());
	}
	
	@Test
	void testGetters2() {
		assertEquals(0, r2.getLeft());
		assertEquals(10, r2.getRight());
		assertEquals(0, r2.getTop());
		assertEquals(5, r2.getBottom());
		assertEquals(10, r2.getWidth());
		assertEquals(5, r2.getHeight());
		assertEquals(new Point(0,0), r2.getTopLeft());
		assertEquals(new Point(10, 5), r2.getBottomRight());
	}
	
	@Test
	void testSetLeft() {
		r1.setLeft(-1); // move far left
		assertEquals(-1, r1.getLeft());
		assertEquals(7, r1.getRight());
		assertEquals(3, r1.getTop());
		assertEquals(10, r1.getBottom());
		assertEquals(8, r1.getWidth());
		assertEquals(7, r1.getHeight());
		assertEquals(new Point(-1, 3), r1.getTopLeft());
		assertEquals(new Point(7, 10), r1.getBottomRight());
		
		
		r2.setLeft(0); // same left!
		assertEquals(0, r2.getLeft());
		assertEquals(10, r2.getRight());
		assertEquals(0, r2.getTop());
		assertEquals(5, r2.getBottom());
		assertEquals(10, r2.getWidth());
		assertEquals(5, r2.getHeight());
		assertEquals(new Point(0,0), r2.getTopLeft());
		assertEquals(new Point(10, 5), r2.getBottomRight());
		
		r2.setLeft(3); // move right
		assertEquals(3, r2.getLeft());
		assertEquals(13, r2.getRight());
		assertEquals(0, r2.getTop());
		assertEquals(5, r2.getBottom());
		assertEquals(10, r2.getWidth());
		assertEquals(5, r2.getHeight());
		assertEquals(new Point(3,0), r2.getTopLeft());
		assertEquals(new Point(13, 5), r2.getBottomRight());
	}
	
	@Test
	void testSetTop() {
		r2.setTop(1); //move 1 down
		assertEquals(0, r2.getLeft());
		assertEquals(10, r2.getRight());
		assertEquals(1, r2.getTop());
		assertEquals(6, r2.getBottom());
		assertEquals(10, r2.getWidth());
		assertEquals(5, r2.getHeight());
		assertEquals(new Point(0,1), r2.getTopLeft());
		assertEquals(new Point(10, 6), r2.getBottomRight());
	}
	
	@Test
	void testSetWidth() {
		r2.setWidth(8);
		assertEquals(0, r2.getLeft());
		assertEquals(8, r2.getRight());
		assertEquals(0, r2.getTop());
		assertEquals(5, r2.getBottom());
		assertEquals(8, r2.getWidth());
		assertEquals(5, r2.getHeight());
		assertEquals(new Point(0,0), r2.getTopLeft());
		assertEquals(new Point(8, 5), r2.getBottomRight());
	}
	
	@Test
	void testSetHeight() {
		r2.setHeight(6);
		assertEquals(0, r2.getLeft());
		assertEquals(10, r2.getRight());
		assertEquals(0, r2.getTop());
		assertEquals(6, r2.getBottom());
		assertEquals(10, r2.getWidth());
		assertEquals(6, r2.getHeight());
		assertEquals(new Point(0,0), r2.getTopLeft());
		assertEquals(new Point(10, 6), r2.getBottomRight());
	}
	
	@Test
	void testContains1() {
		// check if point is inside rectangle
		Point p1 = new Point(0, 0);
		Point p2 = new Point(3, 0);
		Point p3 = new Point(2, 3);
		Point p4 = new Point(10, 5);
		Point p5 = new Point(-1, -1);
		
		assertTrue(r2.contains(p1));
		assertTrue(r2.contains(p2));
		assertTrue(r2.contains(p3));
		assertTrue(r2.contains(p4));
		assertFalse(r2.contains(p5));
	}
	
	@Test
	void testContains2() {
		Rectangle r11 = new Rectangle(new Point(0,0), new Point(10,5));
		Rectangle r22 = new Rectangle(new Point(0,0), new Point(10,6));
		Rectangle r3 = new Rectangle(new Point(1,0), new Point(3,4));
		Rectangle r4 = new Rectangle(new Point(-1,-1), new Point(50,10));
		Rectangle r5 = new Rectangle(new Point(3,2), new Point(9,4));
		
		assertTrue(r2.contains(r11));
		assertFalse(r2.contains(r22));
		assertTrue(r2.contains(r3));
		assertFalse(r2.contains(r4));
		assertTrue(r2.contains(r5));
	}
	
	@Test
	void testCopy() {
		Rectangle r3 = r2.copy();
		Rectangle r4 = r3.copy();
		assertEquals(r3, r2);
		assertEquals(r4, r2);
		assertEquals(r3, r4);
	}
	
	@Test
	void testGrowHeight() {
		Rectangle r3 = r2.growHeight(20);
		assertEquals(r3.getHeight(), r2.getHeight() + 20);
	}
}
