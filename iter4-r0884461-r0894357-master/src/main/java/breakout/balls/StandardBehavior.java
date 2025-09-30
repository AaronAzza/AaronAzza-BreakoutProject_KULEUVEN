package breakout.balls;

import java.awt.Color;
import java.util.ArrayList;

import breakout.BreakoutState;
import breakout.BrickCollision;
import breakout.Collision;
import breakout.util.SpecUtil;
//import logicalcollections.LogicalList;

/**
 * Implements the standard behavior of a ball.
 * 
 */
public class StandardBehavior extends BallBehavior
{
    public final static Color COLOR = Color.WHITE;
    
    /**
     * This method computes what happens to the ball in the next elapsedMilliseconds.
     * @pre | state != null
     * @pre | ball != null
     * @pre | elapsedMilliseconds>= 0
     * @pre | state.getBalls().contains(ball)
     *  
     *  @mutates | this
     *  @mutates | state
     *  @mutates | ball
     */
    @Override
    public void update(BreakoutState state, Ball ball, long elapsedMilliseconds)
    {
        super.update(state,  ball,  elapsedMilliseconds);
    }

    /**
     * Moves the ball to the point of impact with the wall and updates the velocity.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     *
     * @mutates_properties | ball.getGeometry()
     * @mutates_properties | ball.getVelocity()
     *
     * @post | ball.getVelocity().equals(old(ball.getVelocity()).kiloBounce(collision.getKiloNormal()))
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     */
    @Override
    public void bounceOffWall(BreakoutState state, Ball ball, Collision collision)
    {
        super.bounceOffWall(state, ball, collision);
    }

    /**
     * Moves the ball to the point of impact with the paddle and updates the velocity.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     *
     * @mutates_properties| ball.getGeometry().getCenter()
     * @mutates_properties | ball.getVelocity()
     *
     * @post | ball.getVelocity().equals(old(ball.getVelocity()).kiloBounce(collision.getKiloNormal()))
     * @post | !(ball.getGeometry().getCenter().equals(old(ball.getGeometry().getCenter())))
     */
    @Override
    public void bounceOffPaddle(BreakoutState state, Ball ball, Collision collision)
    {
        super.bounceOffPaddle(state, ball, collision);
    }

    /**
     * Moves the ball to the point of impact with the brick, hits the brick and updates the velocity.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     *
     * @mutates_properties | ball.getGeometry()
     * @mutates_properties | ball.getVelocity()
<<<<<<< HEAD
=======
     * // de state kan veranderen wanner de hit() methode opgeroepen wordt, de brick verdwijnt dan bij alle soorten bricken behalve sturdybrick
>>>>>>> branch 'master' of https://gitlab.kuleuven.be/distrinet/education/ogp/projects/2023-2024/student/iter4/ogp/iter4-r0884461-r0894357.git
     *   
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     */
    @Override
    public void bounceOffBrick(BreakoutState state, Ball ball, BrickCollision collision)
    {
        super.bounceOffBrick(state, ball, collision);
    }

    /**
     * Called when the ball is lost, i.e., ventures outside the playing field.
     * A ball with standard behavior removes itself from the game state.
     * @pre | state!= null
     *@pre | ball != null
     *@pre | state.getBalls().contains(ball)
      @mutates_properties | state.getBalls()
     *@post | SpecUtil.sameListsWithElementRemoved(old(state.getBalls()), state.getBalls(), ball)
     */
    @Override
    public void ballLost(BreakoutState state, Ball ball)
    {
        super.ballLost(state, ball);
    }

    /**
     * Standard behavior is represented by the color white.
     * See {@link #COLOR}.
     * @post | result != null
     * 
     */
    @Override
    public Color getColor()
    {
        return COLOR;
    }
}
