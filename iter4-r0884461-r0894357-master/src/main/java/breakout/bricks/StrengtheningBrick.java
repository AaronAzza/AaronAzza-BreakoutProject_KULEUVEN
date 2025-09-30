package breakout.bricks;

import java.awt.Color;

import breakout.balls.Ball;
import breakout.balls.StrongBallBehavior;
import breakout.balls.WeakBallBehavior;
import breakout.math.Point;
import breakout.math.Rectangle;

/**
 * Represents brick that disappear after a single hit
 * and cause the ball to be temporarily strengthened.
 * See {@link breakout.balls.StrongBallBehavior} for what this means.
 */
public class StrengtheningBrick extends BallModifierBrick
{
    public static final Color COLOR = new Color(0, 0, 255); 

    public static final String LABEL = "F";
    /**
     *  @throws IllegalArgumentException |  geometry== null || gridPosition ==null
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     */
    public StrengtheningBrick(Rectangle geometry, Point gridPosition)
    {
        super(geometry, gridPosition);
    }
    /**
     * @post | result == COLOR
     */
    @Override
    public Color getColor()
    {
        return StrengtheningBrick.COLOR;
    }

    /**
     * Specifies what should happen to the ball when this brick is hit.
     * In the case of this class, the ball is given a new behavior, i.e., it is made strong.
     * @pre | ball!= null
     * @mutates_properties | ball.getBehavior()
     * @post | ball.getBehavior() != old(ball.getBehavior())
     * we cant use the equals method or instanceof to check this, so just say that the identity has changed
     */
    @Override 
    public void modifyBall(Ball ball) {
    	StrongBallBehavior behavior = new StrongBallBehavior();
        ball.setBehavior(behavior); 
    }
    /**
     * @post|result == COLOR
     */
    @Override
    public Color getLabelColor()
    {
        return StrengtheningBrick.COLOR;
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
