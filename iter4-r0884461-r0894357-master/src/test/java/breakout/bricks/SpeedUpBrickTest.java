package breakout.bricks;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.BreakoutState;
import breakout.BrickGrid;
import breakout.balls.Ball;
import breakout.balls.BallBehavior;
import breakout.balls.StandardBehavior;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;

class SpeedUpBrickTest {

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
		brickGrid.addSpeedUpBrick(gridPosition);
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
		
	}
	@Test
	void testSpeedUpBrickInstance() {
		assertTrue(brick instanceof SpeedUpBrick);
	}

	@Test
	void testGetColor() {
		assertEquals(new Color(255, 0, 0), brick.getColor());
	}

	@Test
	void testGetLabelColor() {
		assertEquals(new Color(255, 0, 0), ((SpeedUpBrick) brick).getLabelColor());
	}

	@Test
	void testGetLabel() {
		assertEquals(">>>", ((SpeedUpBrick) brick).getLabel());
	}

	@Test
	void testModifyBall() {
		double initialSpeed = ball.getVelocity().getSquaredLength();
		((SpeedUpBrick) brick).modifyBall(ball);
		assertTrue(ball.getVelocity().getSquaredLength() >= initialSpeed, 
			"Ball's velocity should increase after hitting a SpeedUpBrick");
	}
	@Test
	void testHit1() {
		brickWidth = 50;
		brickHeight = 20;
		width = 1000;
		height = 1000;
		var brickGrid = new BrickGrid(width, height, brickWidth, brickHeight);
		var paddleHalfWidth = brickWidth;
		var paddleSpeed = brickWidth/10 ;
		Point gridPosition = new Point(10, 10);
		brickGrid.addSpeedUpBrick(gridPosition);
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
		 assertTrue(state.getBrickGrid().containsBrickAt(gridPosition));

	        
	        brick.hit(state, ball);

	        
	        assertEquals(1,state.getBricks().size());

	        
	        assertNull(state.getBrickGrid().getBrickAt(gridPosition));
	        
	        
	    }
	@Test
	void testHit2() {
		brickWidth = 50;
		brickHeight = 20;
		width = 1000;
		height = 1000;
		var brickGrid = new BrickGrid(width, height, brickWidth, brickHeight);
		var paddleHalfWidth = brickWidth;
		var paddleSpeed = brickWidth/10 ;
		Point gridPosition = new Point(10, 10);
		brickGrid.addSpeedUpBrick(gridPosition);
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
		//Deleting the brick with the method remove() gives the same result
        var oldBricks = new ArrayList<>(state.getBricks());
        
        
        oldBricks.remove(brick);

       
        brick.hit(state, ball);

        
        assertTrue(oldBricks.stream().allMatch(state.getBricks()::contains));
		
	}
	
}
