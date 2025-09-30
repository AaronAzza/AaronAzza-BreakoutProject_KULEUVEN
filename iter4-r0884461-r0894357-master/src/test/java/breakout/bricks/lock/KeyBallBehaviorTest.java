package breakout.bricks.lock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.BreakoutState;
import breakout.BrickCollision;
import breakout.BrickGrid;
import breakout.balls.StandardBehavior;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Vector;
import breakout.paddles.Paddle;

class KeyBallBehaviorTest {

	BrickGrid brickGrid;
    BreakoutState state;
    long paddleHalfWidth;
    long paddleSpeed;
    LockedBrick lb1;
//    LockedBrick lb2;
    MasterBrick mb1;
//    MasterBrick mb2;
    KeyBallBehavior kb;
    
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
        lb1 = brickGrid.addLockedBrick(new Point(0,0));
//        lb2 = brickGrid.addLockedBrick(new Point(1,0));
        ArrayList<LockedBrick> l = new ArrayList<>();
        l.add(lb1);
//        l.add(lb2);
        mb1 = brickGrid.addMasterBrick(new Point(2,0), l);
//        mb2 = brickGrid.addMasterBrick(new Point(3,0), l);
        mb1.hit(state, state.getBalls().get(0));
        state.removeBall(state.getBalls().get(0));
        kb = (KeyBallBehavior) state.getBalls().get(0).getBehavior();
    }
    
    @Test
    void testContr() {
    	assertThrows(IllegalArgumentException.class, () -> new KeyBallBehavior(null));
    }
    
    @Test
    void testBounceOffBrick() {
    	state.getBrickGrid().addStandardBrick(new Point(5,5));
    	BrickCollision col = new BrickCollision(100, new Vector(1000, 0), lb1);
    	kb.bounceOffBrick(state, state.getBalls().get(0), col);
    	// brick should now be destroyed
    	assertFalse(state.getBricks().contains(lb1));
    }
    
    @Test
    void testBounceOffWall() {
    	state.getBrickGrid().addStandardBrick(new Point(5,5));
    	BrickCollision col = new BrickCollision(100, new Vector(1000, 0), lb1);
    	kb.bounceOffWall(state, state.getBalls().get(0), col);
    	assertNotNull(state.getBalls().get(0).getVelocity());
    	kb.ballLost(state, state.getBalls().get(0));
    	assertNull(kb.getLockedBrick().getKeyBallBehavior());
    }
    
}
