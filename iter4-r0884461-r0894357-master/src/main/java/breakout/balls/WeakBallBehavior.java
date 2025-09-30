package breakout.balls;

import java.awt.Color;

import breakout.BreakoutState;
import breakout.BrickCollision;

/**
 * A weak ball does not call a Brick's hit method.
 * This causes the brick to keep existing.
 * A weak ball therefore merely bounces off objects in the game field.
 * 
 * A weak ball reverts to standard behavior after a certain time.
 */
public class WeakBallBehavior extends TemporaryBehavior
{
    /**
     * Color of balls with weak behavior.
     */
    public static final Color COLOR = Color.GRAY;

    /**
     * How long weak behavior stays active in milliseconds.
     */
    public static final int DURATION = 5000;

    public WeakBallBehavior()
    {
        super(DURATION);
    }

    /**
     * Moves ball to point of impact.
     * Does not call brick's hit method.
     * Updates velocity.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     * @inspects | state
     * @mutates_properties| ball.getGeometry()
     * @mutates_properties | ball.getVelocity()
     * 
     * @post | ball.getVelocity().equals(old(ball.getVelocity()).kiloBounce(collision.getKiloNormal()))
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     */
    @Override
    public void bounceOffBrick(BreakoutState state, Ball ball, BrickCollision collision) {
	    ball.move(collision.getMillisecondsUntilCollision());
	    var newVelocity = ball.getVelocity().kiloBounce(collision.getKiloNormal());
	    ball.setVelocity(newVelocity);
    }

    /**
     * Weak balls are colored gray.
     * @post | result == COLOR
     * 
     */
    @Override
    public Color getColor()
    {
        return WeakBallBehavior.COLOR;
    }
}
