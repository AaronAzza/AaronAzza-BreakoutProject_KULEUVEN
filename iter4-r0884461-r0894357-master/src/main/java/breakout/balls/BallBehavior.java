package breakout.balls;

import java.awt.Color;

import breakout.BreakoutState;
import breakout.BrickCollision;
import breakout.Collision;
import breakout.ui.Canvas;
//import logicalcollections.LogicalList;
import breakout.util.SpecUtil;

/**
 * Supertype for ball behavior.
 */
public abstract class BallBehavior
{
	
	
	
	
	
    /**
     * This method computes what happens to the ball in the next elapsedMilliseconds.
     * @pre | state != null
     * @pre | ball != null
     * @pre | elapsedMilliseconds>= 0
     * @pre | state.getBalls().contains(ball)
     * 
     * 
     */
    public void update(BreakoutState state, Ball ball, long elapsedMilliseconds)
    {
        // This implementation is LEGIT

        while ( elapsedMilliseconds > 0 )
        {
           var closestWallCollision = findClosestWallCollision(state, ball);
            var paddleCollision = findPaddleCollision(state, ball);
            var closestBrickCollision = findClosestBrickCollision(state, ball);

            if ( isClosestCollision(closestWallCollision, paddleCollision, closestBrickCollision) && closestWallCollision.getMillisecondsUntilCollision() <= elapsedMilliseconds )
            {
                bounceOffWall(state, ball, closestWallCollision);
                elapsedMilliseconds -= closestWallCollision.getMillisecondsUntilCollision();
            }
            else if ( isClosestCollision(paddleCollision, closestWallCollision, closestBrickCollision) && paddleCollision.getMillisecondsUntilCollision() <= elapsedMilliseconds )
            { 
                bounceOffPaddle(state, ball, paddleCollision);
                elapsedMilliseconds -= paddleCollision.getMillisecondsUntilCollision();
            }
            else if ( isClosestCollision(closestBrickCollision, closestWallCollision, paddleCollision) && closestBrickCollision.getMillisecondsUntilCollision() <= elapsedMilliseconds )
            {
                bounceOffBrick(state, ball, closestBrickCollision);
                elapsedMilliseconds -= closestBrickCollision.getMillisecondsUntilCollision();
            }
            else
            {
                ball.move(elapsedMilliseconds);
                elapsedMilliseconds = 0;
            }
        }

        if ( state.isBallLost(ball) )
        {
            ballLost(state, ball);
        }
    }

    /**
     * LEGIT
     */
    private boolean isClosestCollision(Collision c1, Collision c2, Collision c3)
    {
        var t1 = c1 == null ? Long.MAX_VALUE : c1.getMillisecondsUntilCollision();
        var t2 = c2 == null ? Long.MAX_VALUE : c2.getMillisecondsUntilCollision();
        var t3 = c3 == null ? Long.MAX_VALUE : c3.getMillisecondsUntilCollision();

        return t1 < t2 && t1 < t3;
    }

    /**
     * LEGIT
     */
    private Collision findClosestWallCollision(BreakoutState state, Ball ball)
    {
        Collision closestCollision = null;

        for ( var wall : state.getWalls() )
        {
            var collision = wall.findCollision(ball);
            closestCollision = Collision.getEarliestCollision(closestCollision, collision);
        }

        return closestCollision;
    }

    /**
     * LEGIT
     */
    private Collision findPaddleCollision(BreakoutState state, Ball ball)
    {
        var paddle = state.getPaddle();

        return paddle.findCollision(ball);
    }

    /**
     * LEGIT
     */
    private BrickCollision findClosestBrickCollision(BreakoutState state, Ball ball)
    {
        var brickGrid = state.getBrickGrid();

        return brickGrid.findEarliestCollision(ball);
    }

    /**
     * Default implementation of bouncing off a wall.
     * Can be overriden in subclasses so that different subtypes describe different behaviors.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     * @inspect | state
     * @mutates_properties| ball.getGeometry()
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     * 
     * This default implementation does the following:
     * - It moves the ball to the point of impact.
     * - If reflects the velocity to make the ball bounce off the wall.
     */
    public void bounceOffWall(BreakoutState state, Ball ball, Collision collision)
    {
        // This implementation is LEGIT
        ball.move(collision.getMillisecondsUntilCollision());
        var newVelocity = ball.getVelocity().kiloBounce(collision.getKiloNormal());
        ball.setVelocity(newVelocity);
    }

    /**
     * Default implementation of bouncing off the paddle, which causes the ball
     * the bounce off the paddle using standard physics.
     * Can be overriden in subclasses if another behavior is wanted.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     *
     * @mutates_properties| ball.getGeometry()
     * @mutates_properties | ball.getVelocity()
     *  
     *
     * @post | ball.getVelocity().equals(old(ball.getVelocity()).kiloBounce(collision.getKiloNormal()))
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     *
     */
    public void bounceOffPaddle(BreakoutState state, Ball ball, Collision collision)
    {
        ball.move(collision.getMillisecondsUntilCollision());
        var newVelocity = ball.getVelocity().kiloBounce(collision.getKiloNormal());
        ball.setVelocity(newVelocity);
    }

    /**
     * Default implementation of bouncing off a brick.
     * Can be overriden in subclasses so that different subtypes describe different behaviors.
     *
     * This default implementation does the following:
     * - It moves the ball to the point of impact.
     * - It tells the brick it has been hit.
     * - It reflects the velocity so as to make the ball bounce off the brick.
     * 
     * @pre | state!= null
     * @pre | ball != null
     * @pre | collision != null
     * @pre | state.getBalls().contains(ball)
     *  
     *  The ball.getVelocity() does not change in the StrongBallBehavior class
     * @mutates_properties| ball.getGeometry()
     *
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     */
    public void bounceOffBrick(BreakoutState state, Ball ball, BrickCollision collision)
    {
        // This implementation is LEGIT
        ball.move(collision.getMillisecondsUntilCollision());
        collision.getBrick().hit(state, ball);
        var newVelocity = ball.getVelocity().kiloBounce(collision.getKiloNormal());
        ball.setVelocity(newVelocity);
    }

    /**
     * Called when the ball is lost, i.e., ventures outside the playing field.
     *
     * This default implementation removes the ball from the game state.
     * Subtypes can override this method.
     *@pre | state!= null
     *@pre | ball != null
     *@pre | state.getBalls().contains(ball)
     *  
     *@mutates_properties | state.getBalls()
     *  // representationObject
     *@post | SpecUtil.sameListsWithElementRemoved(old(state.getBalls()), state.getBalls(), ball)
     *
     */
    public void ballLost(BreakoutState state, Ball ball)
    {
        // This implementation is LEGIT
        state.removeBall(ball);
    }

    /**
     * Paints the given ball on the given canvas.
     * Can be overriden in subclasses to give each behavior its own appearance.
     *
     * LEGIT
     *
     * @pre | canvas != null
     * @pre | ball != null
     * @mutates | canvas
     */
    public void paint(Canvas canvas, Ball ball)
    {
        canvas.drawFilledCircle(getColor(), ball.getGeometry());
    }

    /**
     * Default color to use is yellow.
     * Used by paint to determine which color to draw the ball.
     * Can be overriden in subclasses so as to represent different behaviors by different ball colors.
     * @post | result != null
     * 
     */
    public Color getColor()
    {
        return Color.YELLOW;
    }
}
