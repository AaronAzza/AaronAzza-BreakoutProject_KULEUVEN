package breakout.bricks;

import java.awt.Color;

import breakout.balls.Ball;
import breakout.math.Point;
import breakout.math.Rectangle;

/**
 * Bricks of this type disappear after a single hit and cause the ball to speed up.
 */
public class SpeedUpBrick extends BallModifierBrick
{
    public static final Color COLOR = new Color(255, 0, 0);

    public static final String LABEL = ">>>";
    /**
     * @throws IllegalArgumentException |  geometry== null || gridPosition ==null
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     */
    public SpeedUpBrick(Rectangle geometry, Point gridPosition)
    {
        super(geometry, gridPosition); 
    }
    /**
     * @post | result == COLOR
     */
    @Override
    public Color getColor()
    {
        return SpeedUpBrick.COLOR;
    }

    /**
     * Specifies what should happen to the ball when this brick is hit.
     * In the case of this class, the ball speeds up.
     * See {@link breakout.balls.Ball#slowDown()}.
      * @pre | ball!= null
     * @mutates_properties | ball.getVelocity()
     * @post | ball.getVelocity().getSquaredLength() >= old(ball.getVelocity().getSquaredLength())
     */
    @Override
    public void modifyBall(Ball ball) { 
    	ball.speedUp();
    }
    /**
     * @post| result == COLOR
     */
    @Override
    public Color getLabelColor()
    {
        return SpeedUpBrick.COLOR;
    }
    /**
     * @post | result == LABEL
     */
    @Override
    public String getLabel()
    {
        return ">>>";
    }
}
