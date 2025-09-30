package breakout;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.bricks.GrowPaddleBrick;
import breakout.bricks.ShrinkPaddleBrick;
import breakout.bricks.SlowDownBrick;
import breakout.bricks.SpeedUpBrick;
import breakout.bricks.StandardBrick;
import breakout.bricks.StrengtheningBrick;
import breakout.bricks.WeakeningBrick;
import breakout.bricks.lock.LockedBrick;
import breakout.bricks.lock.MasterBrick;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.paddles.Paddle;

class BrickGridTest {
	BrickGrid bg;
	
	@BeforeEach
	void setUp() {
		bg = new BrickGrid(9, 10, 10000, 2000);
	}
	
	@Test
	void testConstr() {
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(0, 10, 10000, 2000));
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(-1, 10, 10000, 2000));
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(10, 0, 10000, 2000));
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(10, -1, 10000, 2000));
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(10, 10, 0, 2000));
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(10, 10, -10, 2000));
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(10, 10, 10000, 0));
		assertThrows(IllegalArgumentException.class, () -> new BrickGrid(10, 10, 10000, -20));
		assertDoesNotThrow(() -> new BrickGrid(10, 10, 10000, 2000));
	}
	
	@Test
	void testGetters() {
		assertEquals(10000, bg.getBrickWidth());
		assertEquals(2000, bg.getBrickHeight());
		assertEquals(9, bg.getColumnCount());
		assertEquals(10, bg.getRowCount());
		assertEquals(bg.getColumnCount() * bg.getBrickWidth(), bg.getWidth());
		assertEquals(bg.getRowCount() * bg.getBrickHeight(), bg.getHeight());
	}
	
	@Test
	void testBrickAt() {
//		assertEquals(null, bg.getBrickAtGridPositionOrNull(new Point(-10000000,0)));
		assertNull(bg.getBrickAtGridPositionOrNull(new Point(0,0)));
		StandardBrick br = bg.addStandardBrick(new Point(0,0));
		assertEquals(br, bg.getBrickAt(new Point(0,0)));
		assertNull(bg.getBrickAt(new Point(1,0)));
		assertEquals(br, bg.getBrickAtGridPositionOrNull(new Point(0,0)));
		assertTrue(bg.containsBrickAt(new Point(0,0)));
		assertFalse(bg.containsBrickAt(new Point(5,5)));
	}
	
	@Test
	void testValidPost() {
		assertTrue(bg.isValidGridPosition(new Point(0,0)));
		assertFalse(bg.isValidGridPosition(new Point(-1,0)));
	}
	
	@Test
	void testAddBrick1() {
		StandardBrick br1 = bg.addStandardBrick(new Point(0,0));
		assertEquals(br1, bg.getBrickAt(new Point(0, 0)));
		
		StandardBrick br2 = bg.addStandardBrick(new Point(1,0));
		assertEquals(br2, bg.getBrickAt(new Point(1, 0)));
		
		GrowPaddleBrick br3 = bg.addGrowPaddleBrick(new Point(2,0));
		assertEquals(br3, bg.getBrickAt(new Point(2, 0)));
		
		ShrinkPaddleBrick br4 = bg.addShrinkPaddleBrick(new Point(3,0));
		assertEquals(br4, bg.getBrickAt(new Point(3, 0)));
		
		WeakeningBrick br5 = bg.addWeakeningBrick(new Point(4,0));
		assertEquals(br5, bg.getBrickAt(new Point(4, 0)));
		
		StrengtheningBrick br6 = bg.addStrengtheningBrick(new Point(5,0));
		assertEquals(br6, bg.getBrickAt(new Point(5, 0)));
		
		SpeedUpBrick br7 = bg.addSpeedUpBrick(new Point(6,0));
		assertEquals(br7, bg.getBrickAt(new Point(6, 0)));
	}
	
	@Test
	void testAddBrick2() {
		SlowDownBrick br1 = bg.addSlowDownBrick(new Point(0,0));
		assertEquals(br1, bg.getBrickAt(new Point(0, 0)));
		
		MasterBrick br2 = bg.addMasterBrick(new Point(1,0), new ArrayList<LockedBrick>());
		assertEquals(br2, bg.getBrickAt(new Point(1, 0)));
		
		LockedBrick br3 = bg.addLockedBrick(new Point(2,0));
		assertEquals(br3, bg.getBrickAt(new Point(2, 0)));
		
		assertEquals(3, bg.getBricks().size());
		assertFalse(bg.isEmpty());
		bg.removeBrick(br3);
		assertFalse(bg.getBricks().contains(br3));
		bg.removeBrickAt(new Point(1,0));
		assertFalse(bg.getBricks().contains(br2));
	}
	
	@Test
	void testEmpty() {
		assertTrue(bg.isEmpty());
	}
	
	@Test
	void testBoundRect() {
		assertEquals(new Rectangle(0, 0, bg.getWidth(), bg.getHeight()), bg.getBoundingRectangle());
	}
	

}
