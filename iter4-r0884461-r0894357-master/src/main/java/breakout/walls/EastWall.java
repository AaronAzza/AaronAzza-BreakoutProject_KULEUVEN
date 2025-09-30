package breakout.walls;

import breakout.Collision;
import breakout.balls.Ball;
import breakout.math.Vector;

/**
 * Represents the east wall.
 * It is infinitely long, i.e., it ranges from -infinity to +infinity in the vertical direction.
 * It keeps balls from escaping the field through the right side of the game field.
 */
public class EastWall extends VerticalWall
{
    /**
     * Constructor.
     * @post | getXCoordinate() == xCoordinate
     */
    public EastWall(long xCoordinate)
    {
        super(xCoordinate);
    }
    
    /**
     * LEGIT
     * @pre | ball!=null
     * @post| result == null | (ball.getVelocity().x() > 0 && ball.getGeometry().getRightmostPoint().x() <= getXCoordinate())
     * @post| result != null| !(ball.getVelocity().x() > 0 && ball.getGeometry().getRightmostPoint().x() <= getXCoordinate())
     * 
     */
    @Override
    public Collision findCollision(Ball ball)
    {
        var ballPosition = ball.getGeometry().getRightmostPoint();
        var ballVelocity = ball.getVelocity();

        if ( ballVelocity.x() > 0 && ballPosition.x() <= getXCoordinate() )
        {
            var t = (this.getXCoordinate() - ballPosition.x()) / ballVelocity.x();

            return new Collision(t, Vector.KILO_LEFT);
        }

        return null;
    }
    /**
     * @post | result != null
     * @post | result.isUnitVector()
     * @post| result == Vector.LEFT
     */
    public Vector getNormal()
    {
        return Vector.LEFT;
    }
}
