package breakout.bricks;

import java.awt.Color;

import breakout.balls.Ball;
import breakout.balls.WeakBallBehavior;
import breakout.math.Point;
import breakout.math.Rectangle;

/**
 * Represents brick that disappear after a single hit
 * and cause the ball to be temporarily weakened.
 * See {@link breakout.balls.WeakBallBehavior} for what this means.
 */
public class WeakeningBrick extends BallModifierBrick
{
    public static final Color COLOR = new Color(255, 0, 0); 

    public static final String LABEL = "W";
    /**
     *  @throws IllegalArgumentException |  geometry== null || gridPosition ==null
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     */
    public WeakeningBrick(Rectangle geometry, Point gridPosition)
    { 
        super(geometry, gridPosition);
    }
    /**
     * @post | result ==  COLOR
     */
    @Override
    public Color getColor()
    {
        return WeakeningBrick.COLOR;
    }

    /**
     * Specifies what should happen to the ball when this brick is hit.
     * In the case of this class, the ball is given a new behavior, i.e., it is made weak.
     * @pre | ball!= null
     * @mutates_properties | ball.getBehavior()
     * @post|  ball.getBehavior().equals(new WeakBallBehavior())
     * 
     * 
     */
    @Override
    public void modifyBall(Ball ball) {
    WeakBallBehavior behavior= new WeakBallBehavior();
    ball.setBehavior(behavior);
    
    }
    /**
     * @post | result == COLOR
     */
    @Override
    public Color getLabelColor()
    {
        return WeakeningBrick.COLOR;
    }
    /**
     * @post| result == LABEL
     */
    @Override
    public String getLabel()
    {
        return LABEL;
    }
}
