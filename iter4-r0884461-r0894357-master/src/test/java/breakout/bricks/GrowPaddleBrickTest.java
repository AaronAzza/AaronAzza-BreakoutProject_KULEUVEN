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
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Vector;
import breakout.paddles.Paddle;

class GrowPaddleBrickTest {

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
	Paddle paddle;
	
	
	
	

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
		brickGrid.addGrowPaddleBrick(gridPosition);
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
		paddle=state.getPaddle();
		
	}
	@Test
    void testGrowPaddleBrickInstance() {
        assertTrue(brick instanceof GrowPaddleBrick);
    }

    @Test
    void testGetColor() {
        assertEquals(new Color(0, 255, 0), brick.getColor());
    }

    @Test
    void testGetLabelColor() {
        assertEquals(new Color(0, 255, 0), ((GrowPaddleBrick) brick).getLabelColor());
    }

    @Test
    void testGetLabel() {
        assertEquals("<>", ((GrowPaddleBrick) brick).getLabel());
    }

    @Test
    void testModifyPaddle() {
        // Set up initial paddle width
        long initialWidth = paddle.getHalfWidth();
        Point center= paddle.clamp(paddle.getTopCenter());
        // Hit the brick
        ((GrowPaddleBrick) brick).modifyPaddle(paddle);
        // Verify that the paddle width has decreased
        assertTrue(paddle.getHalfWidth() >= initialWidth);
        assertTrue(paddle.getTopCenter().equals(center));
    
    }

}
