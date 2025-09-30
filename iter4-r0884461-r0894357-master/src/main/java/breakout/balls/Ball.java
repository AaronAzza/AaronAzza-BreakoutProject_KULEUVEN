package breakout.balls;

import java.awt.Color;

import breakout.BreakoutState;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;
import breakout.ui.Canvas;

/**
 * This class represents a ball.
 * A ball has a geometry (represents the shape and position of the ball),
 * a velocity and a behavior.
 * 
 * @invar | getGeometry() != null
 * @invar | getAllowedArea() != null
 * @invar | getVelocity() != null
 * @invar | getBehavior() != null
 * 
 * The behavior of a ball defines how it interacts with the game field.
 * See for example StandardBehavior, WeakBallBehavior and StrongBallBehavior. 
 */
public class Ball
{
    /**
     * Determines by how much the speed goes up when the ball speeds up.
     * See {@link #speedUp()}.
     */
    public static final int SPEED_UP_FACTOR = 1050;
    
    /**
     * Determines by how much the speed goes down when the ball slows down.
     * See {@link #slowDown()}.
     */
    public static final int SLOW_DOWN_FACTOR = 950;
    
    /**
     * Slowdowns (using {@link #slowDown()}) are only applied if the new speed ends up higher than this value.
     * Note that it is allowed for a ball to have a lower speed, e.g., when using the constructor or {@link #setVelocity(Vector)}. 
     */
    public static final int MINIMUM_SLOWDOWN_SQUARED_SPEED = 5 * 5;
    
    /**
     * Speedups are only applied if the new speed ends up lower than this value.
     * Note that it is allowed for a ball to have a higher speed, e.g., when using the constructor or {@link #setVelocity(Vector)}.
     */
    public static final int MAXIMUM_SPEEDUP_SQUARED_SPEED = 80 * 80;

    /**
     * Determines shape and position of the ball.
     * @invar | geometry != null
     * 
     */
    private Circle geometry;

    /**
     * Expressed in distance per milliseconds.
     * @invar |velocity != null
     */
    private Vector velocity;

    /**
     * Determines how the ball behaves.
     * See subtypes of BallBehavior.
     * @invar | behavior != null
     */
    private BallBehavior behavior;

    /**
     * The ball must at all times fit inside this rectangle.
     * @invar |allowedArea != null 
     * Dit stond er al!!!
     * @representationObject
     */
    private final Rectangle allowedArea;

    /**
     * Constructor.
     * Note that the constructor does not enforce any limitations on the speed of the ball:
     * {@link #MINIMUM_SLOWDOWN_SQUARED_SPEED} and {@link #MAXIMUM_SPEEDUP_SQUARED_SPEED} are not taken into account. 
     * 
     * @throws IllegalArgumentException | allowedArea == null  ||  behavior == null || geometry == null | velocity == null
     * @post | getAllowedArea().equals(allowedArea)
     * @post |  getGeometry() == geometry
     * @post |  getVelocity() == velocity
     * @post | getBehavior() == behavior
     */
    public Ball(Rectangle allowedArea, Circle geometry, Vector velocity, BallBehavior behavior)
    {
    	if (allowedArea == null  ||  behavior == null || geometry == null | velocity == null) 
    		throw new IllegalArgumentException();
    	
    	//!!!!! Flawed_test !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        this.allowedArea = allowedArea.copy();
        this.geometry = geometry;
        this.velocity = velocity;
        this.behavior = behavior;
    }

    /**
     * Returns this ball's location.
     */
    public Circle getGeometry()
    {
        return this.geometry;
    }

    /**
     * Returns this ball's velocity.
     */
    public Vector getVelocity()
    {
        return this.velocity;
    }

    /**
     * Returns the ball's allowed area.
     * @creates | result
     * 
     * 
     */
    public Rectangle getAllowedArea()
    {
    	// !!!!!!!!!!!!!!! flawed test !!!!!!!!!!!!!!!!!!
        return this.allowedArea.copy();
    }

    /**
     * Returns this ball's behavior.
     */
    public BallBehavior getBehavior()
    {
        return this.behavior;
    }

    /**
     * Returns the color this ball should be painted in.
     * Subclasses should override this to make different types of ball visually distinguishable.
     *        
     * @post | result != null
     */
    public Color getColor()
    {
        return Color.WHITE;
    }

    /**
     * Returns this ball's center.
     * @inspects | getGeometry()
     * @post | result.equals(getGeometry().getCenter())
     */
    public Point getCenter()
    {
        return getGeometry().getCenter();
    }

    /**
     * Updates the ball's state.
     * 
     * LEGIT
     * 
     * @pre | state != null
     * @pre | elapsedMilliseconds >= 0
     * @mutates | this
     * @mutates | state
     */
    public void tick(BreakoutState state, long elapsedMilliseconds)
    {
        this.behavior.update(state, this, elapsedMilliseconds);
    }

    /**
     * Moves the ball elapsedMilliseconds into the future.
     * This method does not take into account collisions with other elements:
     * it simply moves the ball in a straight line.
     * @pre | elapsedMilliseconds >= 0
     * @mutates_properties| getGeometry().getCenter()
     * @post | !(getGeometry().getCenter().equals(old(getGeometry().getCenter())))
     * 
     */
    public void move(long elapsedMilliseconds)
    {
        setGeometry(computeDestination(elapsedMilliseconds));
    }

    /**
     * Computes the position the ball would be after elapsedMilleconds time passes.
     * Does not take into account collisions with other elements. 
     * @pre | elapsedMilliseconds>=0
     *  @post | result != null
     */
    public Circle computeDestination(long elapsedMilliseconds)
    {
        return getGeometry().move(this.velocity.multiply(elapsedMilliseconds));
    }

    /**
     * @pre | geometry != null
     * @post | getGeometry().getCenter().equals( geometry.getCenter())
     * @mutates_properties | getGeometry().getCenter()
     * Updates the ball's geometry.
     */
    public void setGeometry(Circle geometry)
    {
        this.geometry = geometry;
    }

    /**
     * Updates the ball's velocity.
     * Note that {@link #MINIMUM_SLOWDOWN_SQUARED_SPEED} and {@link #MAXIMUM_SPEEDUP_SQUARED_SPEED}
     * are not taken into account by this method.
     * 
     * @pre | velocity != null
     * @mutates_properties| getVelocity()
     * @post | getVelocity().equals(velocity)
     * 
     */
    public void setVelocity(Vector velocity)
    {
        this.velocity = velocity;
    }

    /**
     * Checks that the given {@code velocity} is between {@link #MINIMUM_SLOWDOWN_SQUARED_SPEED} and {@link #MAXIMUM_SPEEDUP_SQUARED_SPEED}.
     * 
     * @pre | velocity != null
     *  @post | result == false ||  (MINIMUM_SLOWDOWN_SQUARED_SPEED <= velocity.getSquaredLength() && velocity.getSquaredLength() <= MAXIMUM_SPEEDUP_SQUARED_SPEED)
     */
    public boolean isValidScaledVelocity(Vector velocity)
    {
        var squaredLength = velocity.getSquaredLength();

        return MINIMUM_SLOWDOWN_SQUARED_SPEED <= squaredLength && squaredLength <= MAXIMUM_SPEEDUP_SQUARED_SPEED;
    }

    /**
     * Paints this ball onto the canvas.
     * 
     * LEGIT
     * 
     * @pre | canvas != null
     * @mutates | canvas
     */
    public void paint(Canvas canvas)
    {
        this.behavior.paint(canvas, this);
    }

    /**
     * Sets the ball's behavior.
     * @pre | behavior != null
     * @post | getBehavior().equals(behavior)
     * @mutates_properties | getBehavior()
     * @post | getBehavior() == behavior
     */
    public void setBehavior(BallBehavior behavior)
    {
        this.behavior = behavior;
    }

    /**
     * Scales the ball's speed.
     * Only has an effect if the new speed would be between
     * {@link #MINIMUM_SLOWDOWN_SQUARED_SPEED} and {@link #MAXIMUM_SPEEDUP_SQUARED_SPEED}.
     * 
     * LEGIT
     */
    private void scaleVelocity(int kilofactor)
    {
        var scaledVelocity = this.velocity.multiply(kilofactor).divide(1000);

        if ( isValidScaledVelocity(scaledVelocity) )
        {
            setVelocity(this.velocity.multiply(kilofactor).divide(1000));
        }
    }

    /**
     * Increases this ball's speed by {@link #SPEED_UP_FACTOR} if the ball's updated speed speed would not exceed {@link #MAXIMUM_SPEEDUP_SQUARED_SPEED}.
     * 
     * LEGIT
     * 
     * @post | old(getVelocity().multiply(SPEED_UP_FACTOR).divide(1000).getSquaredLength()) <= MAXIMUM_SPEEDUP_SQUARED_SPEED ? getVelocity().equals(old(getVelocity().multiply(SPEED_UP_FACTOR).divide(1000))) : getVelocity().equals(old(getVelocity())) 
     * @mutates_properties | getVelocity()
     */
    public void speedUp()
    {
        scaleVelocity(SPEED_UP_FACTOR);
    }

    /**
     * Decreases this ball's speed by {@link #SLOW_DOWN_FACTOR} if the ball's updated speed would not be not lower than {@link #MINIMUM_SLOWDOWN_SQUARED_SPEED}.
     * 
     * LEGIT
     * 
     * @post | old(getVelocity().multiply(SLOW_DOWN_FACTOR).divide(1000).getSquaredLength()) >= MINIMUM_SLOWDOWN_SQUARED_SPEED ? getVelocity().equals(old(getVelocity().multiply(SLOW_DOWN_FACTOR).divide(1000))) : getVelocity().equals(old(getVelocity())) 
     * @mutates_properties | getVelocity()
     */
    public void slowDown()
    {
        scaleVelocity(SLOW_DOWN_FACTOR);
    }
}
