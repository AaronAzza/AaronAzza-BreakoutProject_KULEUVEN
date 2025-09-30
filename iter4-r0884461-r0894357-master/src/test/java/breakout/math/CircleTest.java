package breakout.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CircleTest {
	Circle testCircle;
	Point center;
	int radius;
	
	@BeforeEach
	void initEach() {
		center = new Point(0,0);
		radius = 2;
		testCircle = new Circle(center, radius);
	}
	
	@Test
	void testConstructor() {
		assertThrows(IllegalArgumentException.class, () -> new Circle(center, -1));
		assertThrows(IllegalArgumentException.class, () -> new Circle(null, 3));
	}
	
	@Test
	void testGetCenter() {
		assertEquals(center, testCircle.getCenter());
	}
	
	@Test
	void testGetRadius() {
		assertEquals(radius, testCircle.getRadius());
	}
	
	@Test
	void testGetRightmostPoint() {
		assertEquals(new Point(2, 0), testCircle.getRightmostPoint());
	}
	
	@Test
	void testGetLeftmostPoint() {
		assertEquals(new Point(-2, 0), testCircle.getLeftmostPoint());
	}
	
	@Test
	void testGetTopmostPoint() {
		assertEquals(new Point(0, -2), testCircle.getTopmostPoint());
	}
	
	@Test
	void testGetBottommostPoint() {
		assertEquals(new Point(0, 2), testCircle.getBottommostPoint());
	}
	
	@Test
	void getLeft() {
		assertEquals(-2, testCircle.getLeft());
	}
	
	@Test
	void getTop() {
		assertEquals(-2, testCircle.getTop());
	}
	
	@Test
	void getRight()	{
		assertEquals(2, testCircle.getRight());
	}
	
	@Test
	void getBottom() {
		assertEquals(2, testCircle.getBottom());
	}
	
	@Test
	void getBoundingRectangle() {
		Rectangle r = new Rectangle(-2, -2, 4, 4);
		assertEquals(r, testCircle.getBoundingRectangle());
	}
	
	@Test
	void move() {
		Vector v = Vector.DOWN;
		Point newCenter = new Point(0, 1);
		Circle other = new Circle(newCenter, radius);
		
		assertEquals(other, testCircle.move(v));
		assertTrue(testCircle != testCircle.move(v));
		assertEquals(center, testCircle.getCenter());
		assertEquals(radius, testCircle.getRadius());
	}
	
	@Test
	void moveTo() {
		Point newCenter = new Point(1, 2);
		Circle other = new Circle(newCenter, radius);
		
		assertEquals(other, testCircle.moveTo(newCenter));
		assertTrue(testCircle != testCircle.moveTo(newCenter));
		assertEquals(center, testCircle.getCenter());
		assertEquals(radius, testCircle.getRadius());
	}
	
}
