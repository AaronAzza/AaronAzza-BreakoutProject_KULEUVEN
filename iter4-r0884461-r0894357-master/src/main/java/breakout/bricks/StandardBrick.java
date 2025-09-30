package breakout.bricks;

import java.awt.Color;

import breakout.BreakoutState;
import breakout.balls.Ball;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.util.SpecUtil;
//import logicalcollections.LogicalList;

/**
 * Represents bricks that disappear after a single hit and have no kind of effect other than that,
 * e.g., they don't change the paddle or ball in any way.
 */
public class StandardBrick extends Brick
{
    public static final Color COLOR = new Color(128, 128, 128); 
    /**
    *   @throws IllegalArgumentException |  geometry== null || gridPosition ==null
    *   @post| getGeometry() == geometry
    *   @post | getGridPosition() == gridPosition
    */
    public StandardBrick(Rectangle geometry, Point gridPosition){
        super(geometry, gridPosition);
        if (geometry== null || gridPosition ==null)
    		throw new IllegalArgumentException();
       
    }
    /**
     * @post | result == COLOR
     */
    @Override
    public Color getColor()
    {
        return StandardBrick.COLOR;
    }

    /**
     * Called when a ball hits this brick.
     * @pre |state != null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @mutates_properties| state.getBricks()
     * @post |old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null
     * 
     * Removes the brick from the state.
     */
    @Override
    public void hit(BreakoutState state, Ball ball){
    	state.getBrickGrid().removeBrick(this);
    	
    }
}
