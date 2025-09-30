package breakout.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointTest {
	Point p;
	Vector v;
	
	@BeforeEach
	void initEach()	{
		p = new Point(5, 10);
		v = new Vector(3, 4);
	}

    @Test
    void testConstructorAndGetters() {
        assertEquals(5, p.x());
        assertEquals(10, p.y());
    }

    @Test
    void testAdd() {
        Point p2 = p.add(v);
        assertEquals(8, p2.x());
        assertEquals(14, p2.y());
    }

    @Test
    void testSubtract() {
        Point p2 = p.subtract(v);
        assertEquals(2, p2.x());
        assertEquals(6, p2.y());
    }

    @Test
    void testMoveDown() {
        Point pDown = p.moveDown(3);
        assertEquals(5, pDown.x());
        assertEquals(13, pDown.y()); // y is 3 hoger geworden! (HUD assenstelsel)
    }

    @Test
    void testMoveUp() {
        Point pUp = p.moveUp(3);
        assertEquals(5, pUp.x());
        assertEquals(7, pUp.y());
    }

    @Test
    void testMoveLeft() {
        Point pLeft = p.moveLeft(2);
        assertEquals(3, pLeft.x());
        assertEquals(10, pLeft.y());
    }

    @Test
    void testMoveRight() {
        Point pRight = p.moveRight(2);
        assertEquals(7, pRight.x());
        assertEquals(10, pRight.y());
    }
}
