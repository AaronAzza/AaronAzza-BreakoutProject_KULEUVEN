package breakout.bricks;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.BreakoutState;
import breakout.BrickGrid;
import breakout.balls.Ball;
import breakout.balls.BallBehavior;
import breakout.balls.StandardBehavior;
import breakout.balls.WeakBallBehavior;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;

class WeakeningBrickTest{
	int brickWidth;
	int brickHeight;
	BreakoutState state;
	BrickGrid brickGrid;
	int width;
	int height;
    Point gridPosition;
    Circle geometry;
	Vector velocity;
	BallBehavior behavior;
	int radius;
	Point center;
	Brick brick;
	Ball ball;
	Rectangle geometry1;
	
	
	

	@BeforeEach
	void initEach(){
		brickWidth = 50;
		brickHeight = 20;
		width = 1000;
		height = 1000;
		var brickGrid = new BrickGrid(width, height, brickWidth, brickHeight);
		var paddleHalfWidth = brickWidth;
		var paddleSpeed = brickWidth/10 ;
		Point gridPosition = new Point(10, 10);
		brickGrid.addWeakeningBrick(gridPosition);
		brickGrid.addStandardBrick(new Point( 200,200));
		var state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);  
		brick= state.getBrickGrid().getBrickAt(gridPosition);
		center = new Point(30,30);
		radius = 5;
		geometry = new Circle(center, radius);
		behavior = new StandardBehavior();
		velocity = new Vector (2,2);
		state.addBall(geometry, velocity, behavior);
		ball= state.getBalls().getFirst();
		geometry1 = new Rectangle(new Point(10, 10), new Point(10, 10));
		
    
	}
    @Test
    void testStandardBrick() {
    	assertTrue( brick instanceof WeakeningBrick);
    	
    }
    @Test
    void testConstructorWithInvalidArguments() {
      
        assertThrows(IllegalArgumentException.class, () -> {
            new WeakeningBrick(null, gridPosition);
        });
        
        
        assertThrows(IllegalArgumentException.class, () -> {
            new WeakeningBrick(geometry1, null);
        });
    }
    @Test
	void testGetColor() {
		assertEquals(new Color(255, 0, 0) , brick.getColor());
	}

	@Test
	void testGetLabelColor() {
		assertEquals(new Color(255, 0, 0) , ((WeakeningBrick) brick).getLabelColor()) ;
	}

	@Test
	void testGetLabel() {
		assertEquals("W", ((WeakeningBrick) brick).getLabel());
	}

	@Test
	void testModifyBall() {
		assertTrue(ball.getBehavior() instanceof StandardBehavior);
		((WeakeningBrick) brick).modifyBall(ball);
		assertTrue(ball.getBehavior() instanceof WeakBallBehavior);
	}
}
