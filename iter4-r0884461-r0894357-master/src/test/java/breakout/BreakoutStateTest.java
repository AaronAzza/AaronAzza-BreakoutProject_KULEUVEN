package breakout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Vector;

import java.util.ArrayList;

import breakout.balls.Ball;
import breakout.balls.StandardBehavior;
import breakout.bricks.Brick;
import breakout.bricks.StandardBrick;
import breakout.paddles.Paddle;
import breakout.util.SpecUtil;
import breakout.walls.Wall;

public class BreakoutStateTest {

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

    
    @Test
    void testGetPaddle() {
    	assertNotNull(state.getPaddle());
    }
    
    @Test
    void testIsGameOverWonLostRemoveBall() { 
    	assertFalse(state.getBalls().isEmpty());
    	assertTrue(state.isGameWon());
    	assertTrue(state.isGameOver());
    	state.getBrickGrid().addStandardBrick(new Point(0,0));
    	assertFalse(state.isGameOver());
    	assertFalse(state.isGameOver());
    	state.removeBall(state.getBalls().get(0));
    	assertTrue(state.isGameLost());
    	assertFalse(state.isGameWon());
    	assertTrue(state.isGameOver());
    	assertTrue(state.getBalls().isEmpty());
    }
    
    @Test
    void testGetWalls() {
    	assertEquals(3, state.getWalls().size());
    	assertFalse(state.getWalls().contains(null));
    }
    
    @Test
    void testGetBricks() {
    	assertTrue(state.getBricks().isEmpty());
    	state.getBrickGrid().addStandardBrick(new Point(0,0));
    	assertEquals(1, state.getBricks().size());
    }
    
    @Test
    void testGetBrickGrid() {
    	assertNotNull(state.getBrickGrid());
    }

}
