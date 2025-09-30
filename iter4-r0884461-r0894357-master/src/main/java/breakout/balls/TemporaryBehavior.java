package breakout.balls;

import breakout.BreakoutState;
import breakout.util.SpecUtil;

/**
 * Convenience supertype for temporary behaviors.
 * After a certain amount of time has elapsed, a temporary behavior
 * causes the ball to revert back to the standard behavior.
 * @invar| getTimeLeft() >= 0
 * 
 */
public abstract class TemporaryBehavior extends BallBehavior 
{
    /**
     * Number of milliseconds this behavior is still active.
     * @invar | timeLeft >=0
     * 
     */
    private int timeLeft;

    
    /**
     * Constructor
     * @throws IllegalArgumentException | duration < 0
     * @post | getTimeLeft()== duration
     * 
     */
    public TemporaryBehavior(int duration) {
    if(duration < 0)
    	throw new IllegalArgumentException("The duration has to be greater than 0");
    this.timeLeft=duration;
    }

    public int getTimeLeft()
    {
        return this.timeLeft;
    }

    /**
     * LEGIT
     * 
     * @pre | state != null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | elapsedMilliseconds >= 0
     * @mutates | this
     * @mutates | state
     * @mutates | ball
     * @post | getTimeLeft() == Math.max(0, old(getTimeLeft()) - elapsedMilliseconds)
     * @post | SpecUtil.implies(getTimeLeft() == 0, ball.getBehavior() != this)
     */
    @Override
    public void update(BreakoutState state, Ball ball, long elapsedMilliseconds)
    {
        if ( timeLeft <= elapsedMilliseconds )
        {
            super.update(state, ball, timeLeft);
            var standardBehavior = new StandardBehavior();
            ball.setBehavior(standardBehavior);
            ball.tick(state, elapsedMilliseconds - timeLeft);
            timeLeft = 0;
        }
        else
        {
            super.update(state, ball, elapsedMilliseconds);
            timeLeft -= elapsedMilliseconds;
        }
    }
}
