package breakout.bricks;

import breakout.BreakoutState;
import breakout.balls.Ball;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.util.SpecUtil;
//import logicalcollections.LogicalList;

/**
 * Superclass for all bricks that modify the ball (e.g., make it go faster) when the brick is hit.
 * These bricks are also single-life, i.e., they disappear after one hit.
 */
public abstract class BallModifierBrick extends LabeledBrick
{
	/**
	 * @throws IllegalArgumentException |  geometry== null || gridPosition ==null
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition 
	 */
    public BallModifierBrick(Rectangle geometry, Point gridPosition)
    {
        super(geometry, gridPosition); 
    }

    /**
     * Removes the brick from the game state and calls the modifyBall method.
     * @pre |state != null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @inspects | state
     * @mutates_properties| state.getBricks()
     * @post |old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null
     */
    @Override
    public void hit(BreakoutState state, Ball ball)
    {
        state.getBrickGrid().removeBrick(this);
        modifyBall(ball);
    }
    
    /**
     * Called when a ball hits this brick.
     * Subclasses can override this method to specify what should happen with the ball.
     * @pre | ball!= null
     * @mutates | ball
     */
    public abstract void modifyBall(Ball ball);
}
