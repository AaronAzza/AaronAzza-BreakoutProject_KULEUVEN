package breakout.walls;

import breakout.Collision;
import breakout.balls.Ball;
import breakout.math.Vector;

/**
 * Represents the west wall.
 * It is infinitely long, i.e., it ranges from -infinity to +infinity in the vertical direction.
 * It keeps balls from escaping the field through the left side of the game field.
 */
public class WestWall extends VerticalWall
{
    /**
     * Constructor.
     * @post | getXCoordinate() == xCoordinate
     */
    public WestWall(long xCoordinate)
    {
        super(xCoordinate);
    }

    /**
     * LEGIT
     * @pre | ball!=null
     * @post| result == null | (ball.getVelocity().x() <0 && ball.getGeometry().getRightmostPoint().x() >= getXCoordinate())
     * @post| result != null| !(ball.getVelocity().x() < 0 && ball.getGeometry().getRightmostPoint().x() >= getXCoordinate())
     * 
     */
    @Override
    public Collision findCollision(Ball ball)
    {
        var ballPosition = ball.getGeometry().getLeftmostPoint();
        var ballVelocity = ball.getVelocity();

        if ( ballVelocity.x() < 0 && ballPosition.x() >= getXCoordinate() )
        {
            var t = (ballPosition.x() - getXCoordinate()) / -ballVelocity.x();
            
            return new Collision(t, Vector.KILO_LEFT);
        }

        return null;
    }
    /**
     * @post | result != null
     * @post | result.isUnitVector()
     * @post | result == Vector.RIGHT
     */
    public Vector getNormal()
    {
        return Vector.RIGHT;
    }
}
