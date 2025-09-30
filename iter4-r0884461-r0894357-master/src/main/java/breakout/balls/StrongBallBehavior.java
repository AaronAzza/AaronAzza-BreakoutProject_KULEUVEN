package breakout.balls;

import java.awt.Color;

import breakout.BreakoutState;
import breakout.BrickCollision;

/**
 * A strong ball will use the Brick's strongHit method instead of just hit.
 * If the brick disappears, the ball continues on its original path, i.e., it does not bounce off the brick it just hit.
 * If the brick survives the strong hit, the ball bounces off normally.
 * 
 * A strong ball reverts to standard behavior after a certain time.
 */
public class StrongBallBehavior extends TemporaryBehavior
{
    /**
     * Color of balls with strong behavior.
     */
    public static final Color COLOR = Color.RED;

    /**
     * How long strong behavior stays active in milliseconds.
     */
    public static final int DURATION = 5000;

    /**
     * Constructor.
     * 
     *
     */
    public StrongBallBehavior()
    {
        super(DURATION);
    }

    /**
     * Moves this ball to the point of impact.
     * Calls {@link breakout.bricks.Brick#strongHit}, and only if the brick survives, updates the velocity. 
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     * @mutates_properties | ball.getGeometry()
     * @mutates_properties | ball.getVelocity()
     * @post | !old(ball.getVelocity()).equals(ball.getVelocity()) ?
     *		 | ball.getVelocity().equals(old(ball.getVelocity()).kiloBounce(collision.getKiloNormal())):
     *		 | ball.getVelocity().equals(old(ball.getVelocity()))
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     * 
     */
    @Override
    public void bounceOffBrick(BreakoutState state, Ball ball, BrickCollision collision){
    	ball.move(collision.getMillisecondsUntilCollision());
    	if(collision.getBrick().strongHit(state, ball)) {
    		var newVelocity = ball.getVelocity().kiloBounce(collision.getKiloNormal()); 
        	ball.setVelocity(newVelocity);}
    		
    	
    }

    /**
     * Strong behavior is represented by the color red, see {@link #COLOR}.
     * @post | result == COLOR
     * 
     */
    @Override
    public Color getColor()
    {
        return StrongBallBehavior.COLOR;
    }
}
