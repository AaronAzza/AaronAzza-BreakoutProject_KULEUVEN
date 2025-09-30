package breakout.bricks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

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

public class SturdyBrickTest {
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
		brickGrid.addSturdyBrick(gridPosition,3);
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
	    void testConstructorAndGetColor() {
	        assertThrows(IllegalArgumentException.class, () -> new SturdyBrick(new Rectangle(new Point(10, 10), new Point(20, 20)), new Point(1, 1), -1), "Lives must be greater than 0");
	        
	        SturdyBrick brickNew = new SturdyBrick(new Rectangle(new Point(10, 10), new Point(20, 20)), new Point(1, 1), 3);
	        assertEquals(3, brickNew.getLivesLeft(), "Lives should be initialized correctly");
	        
	        assertEquals(new Color(92, 64, 51), brickNew.getColor(), "Color for 3 lives should be correct");
	        
	        ((SturdyBrick) brickNew).hit(state, ball); // Reduce lives to 2
	        
	        assertEquals(new Color(123, 63, 0), brickNew.getColor(), "Color for 2 lives should be correct");
	        
	        ((SturdyBrick) brickNew).hit(state, ball); // Reduce lives to 1
	        
	        assertEquals(new Color(160, 82, 45), brickNew.getColor(), "Color for 1 life should be correct");
	    }

	    

	    @Test
	    void testHit() {
	    	brickWidth = 50;
			brickHeight = 20;
			width = 1000;
			height = 1000;
			var brickGrid = new BrickGrid(width, height, brickWidth, brickHeight);
			var paddleHalfWidth = brickWidth;
			var paddleSpeed = brickWidth/10 ;
			Point gridPosition = new Point(10, 10);
			brickGrid.addSturdyBrick(gridPosition,3);
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
			
			
	        int initialLives = ((SturdyBrick) brick).getLivesLeft();
	        ((SturdyBrick) brick).hit(state, ball);
	        assertEquals(initialLives - 1, ((SturdyBrick) brick).getLivesLeft(), "Lives should be decremented by 1");
	        
	        // If hit again when lives are 1
	        ((SturdyBrick) brick).hit(state, ball);
	        ((SturdyBrick) brick).hit(state, ball);
	        assertNull(state.getBrickGrid().getBrickAt(gridPosition), "Brick should be removed from the grid when lives reach 0");
	    }

	    @Test
	    void testStrongHit() {
	    	brickWidth = 50;
			brickHeight = 20;
			width = 1000;
			height = 1000;
			var brickGrid = new BrickGrid(width, height, brickWidth, brickHeight);
			var paddleHalfWidth = brickWidth;
			var paddleSpeed = brickWidth/10 ;
			Point gridPosition = new Point(10, 10);
			brickGrid.addSturdyBrick(gridPosition,3);
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
			
			
	        int initialLives = ((SturdyBrick) brick).getLivesLeft();
	        assertEquals(3, initialLives);
	        ((SturdyBrick) brick).strongHit(state, ball);
	        assertEquals(0, ((SturdyBrick) brick).getLivesLeft(), "Lives should be set to 0 on strong hit");
	        assertNull(state.getBrickGrid().getBrickAt(gridPosition), "Brick should be removed from the grid on strong hit");
	    }

	    @Test
	    void testGetLabel() {
	        assertEquals("3", ((SturdyBrick) brick).getLabel(), "Label should match the number of lives");
	        ((SturdyBrick) brick).hit(state, ball); // Reduce lives to 2
	        assertEquals("2", ((SturdyBrick) brick).getLabel(), "Label should update to the correct number of lives");
	        ((SturdyBrick) brick).hit(state, ball); // Reduce lives to 1
	        assertEquals("1", ((SturdyBrick) brick).getLabel(), "Label should update to the correct number of lives");
	    }

	    @Test
	    void testGetLabelColor() {
	        assertEquals(Color.WHITE, ((SturdyBrick) brick).getLabelColor(), "Label color should be correct");
	    }
}
