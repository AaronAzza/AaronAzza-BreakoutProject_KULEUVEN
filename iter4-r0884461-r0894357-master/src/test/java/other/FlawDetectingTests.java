package other;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import breakout.BreakoutState;
import breakout.BrickCollision;
import breakout.BrickGrid;
import breakout.balls.Ball;
import breakout.balls.BallBehavior;
import breakout.balls.StandardBehavior;
import breakout.bricks.lock.KeyBallBehavior;
import breakout.bricks.lock.LockedBrick;
import breakout.bricks.lock.MasterBrick;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;
import breakout.util.SpecUtil;
import breakout.walls.Wall;

@Timeout(5)
public class FlawDetectingTests
{
	
	
	

	// breakoutState.getBricks: gaf eerst null terug, niet zeker of eig flaw...
	
	// BrickCollision constructor tried to set KiloNormal vector and brick to null, and time until collision to 0 instead of given arguments
	// not sure if flaw tbh
	
	// LockedBrick() constructor stelt masterBricks array in op null ipv lege ArrayList!:
	
	// MasterBrick() constructor stelt lockedBricks array in op null ipv given ArrayList!, moet ook gelinkt worden!:
	
	// MasterBrick.getLockedBricks(): repr exp!
	
	// MasterBrick.LinkLock didnt change the speedModifiers of the balls it calculated it for
	// also didnt remove all occurances of the link when double linked!! (I also changed LinkLock to not store double links so this might be hard to catch in tests)
	
	// keyBallBehavior constructor set speedModifier to 0 instead of the invar formula
	
	
	BrickGrid brickGrid;
    BreakoutState state;
    long paddleHalfWidth;
    long paddleSpeed;
    
    @BeforeEach
    void beforeEach()
    {
        brickGrid = new BrickGrid(10, 10, 10000, 2000);
        
        paddleHalfWidth = 50;
        paddleSpeed = 10;
        state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);
        var brickGrid = state.getBrickGrid();
        var ballRadius = 500;
        var ballPosition = brickGrid.getBoundingRectangle().getBottomCenter().add(new Vector(0, -2 * ballRadius));
        var geometry = new Circle(ballPosition, ballRadius);
        var velocity = new Vector(25, -25);
        var behavior = new StandardBehavior();
        state.addBall(geometry, velocity, behavior);
    }
    
    @Test
    void testGetBallsAddBall() {
        assertEquals(1, state.getBalls().size());
        assertTrue(!SpecUtil.containsDuplicateObjects(state.getBalls()));
        assertTrue(state.getBalls().stream().allMatch(b -> b != null && b.getAllowedArea().equals(state.getBoundingRectangle())));
        var brickGrid = state.getBrickGrid();
        var ballRadius = 500;
        var ballPosition = brickGrid.getBoundingRectangle().getBottomCenter().add(new Vector(0, -10 * ballRadius));
        var geometry = new Circle(ballPosition, ballRadius);
        var velocity = new Vector(25, -25);
        var behavior = new StandardBehavior();
        state.addBall(geometry, velocity, behavior);
        assertEquals(2, state.getBalls().size());
    }
    
    // breakoutState.getBalls: repr exp!
    @Test
    void testReprExpGetBallsBreakoutState() {
    	ArrayList<Ball> l = state.getBalls(); 
    	l.clear();
    	assertFalse(state.getBalls().equals(l));
    }
    
    // breakoutState.getWalls: repr exp!
    @Test
    void flaw2() {
    	ArrayList<Wall> l = state.getWalls(); 
    	l.clear();
    	assertFalse(state.getBalls().equals(l));
    }
    
    @Test
    void TestConstructorBalls() {
	    Ball testBall;
	    Circle geometry;
	    Vector velocity;
	    BallBehavior behavior;
	    Rectangle allowedArea;
	    Point center;
	    int radius;
	
	    allowedArea= new Rectangle (100,20,100,20); 
	    center = new Point(0,0);
	    radius = 3;
	    geometry = new Circle(center, radius);
	    velocity = new Vector (2,2);
	    behavior = new StandardBehavior();
	    testBall = new Ball(allowedArea, geometry, velocity, behavior);
	
	    assertEquals(new Rectangle (100,20,100,20),testBall.getAllowedArea());
	    allowedArea= new Rectangle (10,20,100,20); // No representation exposure
	    assertEquals(new Rectangle (100,20,100,20),testBall.getAllowedArea());
    }
    
    // lockedBrick.getMasterBricks: repr exp!
    @Test
    void flaws() {
    	brickGrid = new BrickGrid(10, 10, 10000, 2000);
        paddleHalfWidth = 50;
        paddleSpeed = 10;
        state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);
        var brickGrid = state.getBrickGrid();
        var ballRadius = 500;
        var ballPosition = brickGrid.getBoundingRectangle().getBottomCenter().add(new Vector(0, -2 * ballRadius));
        var geometry = new Circle(ballPosition, ballRadius);
        var velocity = new Vector(25, -25);
        var behavior = new StandardBehavior();
        state.addBall(geometry, velocity, behavior);
        LockedBrick lb1 = brickGrid.addLockedBrick(new Point(0,0));
        LockedBrick lb2 = brickGrid.addLockedBrick(new Point(1,0));
        ArrayList<LockedBrick> l = new ArrayList<>();
        l.add(lb1);
        l.add(lb2);
        MasterBrick mb1 = brickGrid.addMasterBrick(new Point(2,0), l);
//        mb2 = brickGrid.addMasterBrick(new Point(3,0), l);
        mb1.hit(state, state.getBalls().get(0));
        state.removeBall(state.getBalls().get(0));
        KeyBallBehavior kb = (KeyBallBehavior) state.getBalls().get(0).getBehavior();
        state.getBrickGrid().addStandardBrick(new Point(5,5));
        BrickCollision col = new BrickCollision(100, new Vector(1000, 0), lb1);
    	kb.bounceOffBrick(state, state.getBalls().get(0), col);
    	Ball b = state.getBalls().get(0);
    	
    	
    }
}
