package breakout.balls;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.BreakoutState;
import breakout.BrickCollision;
import breakout.BrickGrid;
import breakout.bricks.Brick;
import breakout.bricks.StandardBrick;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;

class WeakBallBehaviorTest {

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
	BrickCollision collision;
	Vector vector;
	Point gridPositionNew;
	Brick brickNew;
	
	
	
	
	
	
	

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
		Point gridPositionNew= new Point (120,120);
		brickGrid.addStandardBrick(gridPositionNew);
		brickGrid.addLockedBrick(gridPosition);
		var state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);  
		brick= state.getBrickGrid().getBrickAt(gridPosition);
		brickNew= state.getBrickGrid().getBrickAt(gridPositionNew);
		center = new Point(30,30);
		radius = 5;
		geometry = new Circle(center, radius);
		behavior = new WeakBallBehavior();
		velocity = new Vector (22,23);
		state.addBall(geometry, velocity, behavior);
		ball= state.getBalls().getFirst();
		vector= new Vector(1000,0);
		collision= new BrickCollision(100,vector,brick);
		
	}
		
		
	
	
	@Test
    void testBounceOffLockedBrick() {
		brickWidth = 50;
		brickHeight = 20;
		width = 1000;
		height = 1000;
		var brickGrid = new BrickGrid(width, height, brickWidth, brickHeight);
		var paddleHalfWidth = brickWidth;
		var paddleSpeed = brickWidth/10 ;
		Point gridPosition = new Point(10, 10);
		Point gridPositionNew= new Point (120,120);
		brickGrid.addStandardBrick(gridPositionNew);
		brickGrid.addLockedBrick(gridPosition);
		var state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);  
		brick= state.getBrickGrid().getBrickAt(gridPosition);
		brickNew= state.getBrickGrid().getBrickAt(gridPositionNew);
		center = new Point(30,30);
		radius = 5;
		geometry = new Circle(center, radius);
		behavior = new WeakBallBehavior();
		velocity = new Vector (22,23);
		state.addBall(geometry, velocity, behavior);
		ball= state.getBalls().getFirst();
		vector= new Vector(1000,0);
		collision= new BrickCollision(100,vector,brick);
        // Pre-assertions to ensure initial state
        assertEquals(velocity, ball.getVelocity(), "Initial velocity should match");
        
        // Execute the bounceOffBrick method
        behavior.bounceOffBrick(state, ball, collision);
        
        // The ball should bounce off the locked brick, but the brick should remain unaffected
        var expectedNewVelocity = velocity.kiloBounce(collision.getKiloNormal());
        assertEquals(expectedNewVelocity, ball.getVelocity(), "Velocity should update after bouncing off a locked brick");
        assertEquals(brick, state.getBrickGrid().getBrickAt(gridPosition), "The locked brick should not be affected by the weak ball");
    }

    @Test
    void testBounceOffStandardBrick() {
    	brickWidth = 50;
		brickHeight = 20;
		width = 1000;
		height = 1000;
		var brickGrid = new BrickGrid(width, height, brickWidth, brickHeight);
		var paddleHalfWidth = brickWidth;
		var paddleSpeed = brickWidth/10 ;
		Point gridPosition = new Point(10, 10);
		Point gridPositionNew= new Point (120,120);
		brickGrid.addStandardBrick(gridPositionNew);
		brickGrid.addLockedBrick(gridPosition);
		var state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);  
		brick= state.getBrickGrid().getBrickAt(gridPosition);
		brickNew= state.getBrickGrid().getBrickAt(gridPositionNew);
		center = new Point(30,30);
		radius = 5;
		geometry = new Circle(center, radius);
		behavior = new WeakBallBehavior();
		velocity = new Vector (22,23);
		state.addBall(geometry, velocity, behavior);
		ball= state.getBalls().getFirst();
		vector= new Vector(1000,0);
		
        // Create a collision with the standard brick
        BrickCollision collisionNew = new BrickCollision(100, vector, brickNew);
        
        // Pre-assertions to ensure initial state
        assertEquals(velocity, ball.getVelocity(), "Initial velocity should match");

        // Execute the bounceOffBrick method
        behavior.bounceOffBrick(state, ball, collisionNew);

        // The ball should bounce off the standard brick, but the brick should remain unaffected
        var expectedNewVelocity = velocity.kiloBounce(collisionNew.getKiloNormal());
        assertEquals(expectedNewVelocity, ball.getVelocity(), "Velocity should update after bouncing off a standard brick");
        assertEquals(brickNew, state.getBrickGrid().getBrickAt(gridPositionNew), "The standard brick should not be affected by the weak ball");
    }
    
    

}
